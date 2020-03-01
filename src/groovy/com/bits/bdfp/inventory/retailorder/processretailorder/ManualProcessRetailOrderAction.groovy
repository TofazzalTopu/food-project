package com.bits.bdfp.inventory.retailorder.processretailorder

import com.bits.bdfp.customer.CustomerStock
import com.bits.bdfp.customer.CustomerStockTransaction
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.demandorder.InvoiceDetails
import com.bits.bdfp.inventory.product.ProductPriceService
import com.bits.bdfp.inventory.retailorder.RetailOrder
import com.bits.bdfp.inventory.retailorder.RetailOrderService
import com.bits.bdfp.inventory.sales.DistributionPointTerritorySubArea
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.ObjectUtil
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

/**
 * Created by mdalinaser.khan on 1/31/16.
 */
@Component("manualProcessRetailOrderAction")
class ManualProcessRetailOrderAction extends Action {
    public static final Log log = LogFactory.getLog(this)
    private final String MESSAGE_HEADER = 'Message'
    @Autowired
    RetailOrderService retailOrderService
    @Autowired
    ProductPriceService productPriceService
    @Autowired
    SpringSecurityService   springSecurityService

    public Object preCondition(def params, Object object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            Map mapInstance = [:]
            Float totalInvoiceAmount = 0
            ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
            params.put("retailOrderIds", params.id)
            RetailOrder retailOrder = retailOrderService.read(Long.parseLong(params.id))
            if(!retailOrder){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Retail Order not found")
            }
            retailOrder.actualDeliveryDate = new Date()
            // Check Stock Availability
//            List result = retailOrderService.listRetailsOrderDetailsForProcessing(params)
//            for(int i = 0; i < result.size(); i++){
//                double diff =  Double.parseDouble(result[i].orderQuantity.toString()) - Double.parseDouble(result[i].availableQuantity.toString())
//                if( diff > 0.0 ){
//                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Item " + result[i].productName + ": inadequate")
//                }
//            }


            // Prepare Retail Invoice
            Invoice retailInvoice = new Invoice(isIncentiveCalculated: false)
            retailInvoice.retailOrder = retailOrder
            // Get DP
            DistributionPointTerritorySubArea distributionPointTerritorySubArea = DistributionPointTerritorySubArea.findByTerritorySubArea(retailOrder.territorySubArea)
            if(distributionPointTerritorySubArea){
                retailInvoice.distributionPoint = distributionPointTerritorySubArea.distributionPoint
            }
            retailInvoice.userCreated = applicationUser
            retailInvoice.transactionDate = new Date()
            retailInvoice.defaultCustomer = retailOrder.orderPlacedFor
            retailInvoice.vat = 0.0
            retailInvoice.ait = 0.0
            retailInvoice.discount = 0.00
            retailInvoice.serviceCharge = 0.00
            retailInvoice.paidAmount = 0.00
            retailInvoice.isActive = true
            retailInvoice.isDirectInvoice = false
            // Generate Retail Invoice No
            Date dateNow = new Date()
            retailInvoice.dateCreated = dateNow
            SimpleDateFormat formatMonth = new SimpleDateFormat ("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat ("YY")
            String currentYear = formatYear.format(dateNow)
            retailInvoice.code = CodeGenerationUtil.instance.generateCode(retailOrder.enterprise, "RETAIL_INVOICE", "", "", "", "", "", currentMonth, currentYear, "", "")
            List<CustomerStockTransaction> customerStockTransactionList = ObjectUtil.instantiateObjects(params.items, CustomerStockTransaction.class)
            int orderDetailsCount = customerStockTransactionList.size()
            if(orderDetailsCount <= 0){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "No Items are selected")
            }
            List<InvoiceDetails> invoiceDetailsList = new ArrayList<InvoiceDetails>()
            List<CustomerStock> customerStockList = new ArrayList<CustomerStock>()
            InvoiceDetails invoiceDetails = null
            CustomerStock customerStock = null
            Float totalVat = 0.00
            customerStockTransactionList.each {  customerStockTransaction ->
                customerStock = customerStockTransaction.customerStock
                // Get updated product price
                Map productPrice = productPriceService.getProductPriceByCustomerAndProduct(retailOrder.orderPlacedFor.id, customerStock.finishProduct.id, retailOrder.territorySubArea.id)
                customerStockTransaction.unitPrice = productPrice.productPriceWithVat
                customerStock.outQuantity += customerStockTransaction.outQuantity
                customerStock.userUpdated = applicationUser
                customerStockList.add(customerStock)

                customerStockTransaction.userCreated = applicationUser
                customerStockTransaction.inQuantity = 0
                customerStockTransaction.inInvoice = null
                customerStockTransaction.outInvoice = retailInvoice
                customerStockTransaction.transactionDate = new Date()
                totalInvoiceAmount += customerStockTransaction.outQuantity * customerStockTransaction.unitPrice

                if(!customerStockTransaction.validate()){
                    throw new RuntimeException(this.getValidationErrorMessage(customerStockTransaction).messageBody[0].toString())
                }
                // Prepare Invoice Details
                invoiceDetails = new InvoiceDetails(isIncentiveCalculated: false)
                invoiceDetails.batchNumber = customerStockTransaction.customerStock.batchNo
                invoiceDetails.finishProduct = customerStockTransaction.customerStock.finishProduct
                invoiceDetails.invoice = retailInvoice
                invoiceDetails.quantity = customerStockTransaction.outQuantity
                invoiceDetails.unitPrice = customerStockTransaction.unitPrice
                invoiceDetails.dpUnitPrice = productPrice.dpPrice
                invoiceDetails.unitVat = productPrice.productPriceWithVat - productPrice.productPriceWithoutVat
                totalVat += invoiceDetails.unitVat * invoiceDetails.quantity
                if(!invoiceDetails.validate()){
                    throw new RuntimeException(this.getValidationErrorMessage(invoiceDetails).messageBody[0].toString())
                }
                invoiceDetailsList.add(invoiceDetails)
            }
            retailInvoice.invoiceAmount = totalInvoiceAmount
            retailInvoice.vat = totalVat
            retailOrder.invoice = retailInvoice
            if(!retailInvoice.validate()){
                throw new RuntimeException(this.getValidationErrorMessage(retailInvoice).messageBody[0].toString())
            }
            mapInstance.put("retailInvoice", retailInvoice)
            mapInstance.put("retailInvoiceDetails", invoiceDetailsList)
            mapInstance.put("customerStockList", customerStockList)
            mapInstance.put("customerStockTransactionList", customerStockTransactionList)
            mapInstance.put("retailOrder", retailOrder)
            Invoice createdInvoice = (Invoice) retailOrderService.manualProcessRetailOrder(mapInstance)
            if (createdInvoice) {
                return this.getMessage(createdInvoice, Message.SUCCESS, "Retail Order:" + retailOrder.orderNo  +" Processed Successfully, InvoiceNo:" + createdInvoice.code + "\nInvoiceId=" + createdInvoice.id)
            } else {
                return this.getMessage(createdInvoice, Message.ERROR, this.FAIL_SAVE)
            }
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}
