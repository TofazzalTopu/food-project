package com.bits.bdfp.rest

import com.bits.bdfp.customer.CustomerStock
import com.bits.bdfp.customer.CustomerStockTransaction
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.demandorder.InvoiceDetails
import com.bits.bdfp.inventory.product.ProductPriceService
import com.bits.bdfp.inventory.retailorder.RetailOrder
import com.bits.bdfp.inventory.retailorder.RetailOrderDetails
import com.bits.bdfp.inventory.retailorder.RetailOrderService
import com.bits.bdfp.inventory.sales.DistributionPointTerritorySubArea
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
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
@Component("autoProcessRetailOrderByMobileAction")
class AutoProcessRetailOrderByMobileAction extends Action {
    public static final Log log = LogFactory.getLog(this)
    private final String MESSAGE_HEADER = 'Message'
    @Autowired
    RetailOrderService retailOrderService
    @Autowired
    ProductPriceService productPriceService
    @Autowired
    SpringSecurityService   springSecurityService
    @Autowired
    ListProductForCheckAllQuantityAction listProductForCheckAllQuantityAction

    public Object preCondition(def params, Object object) {
        return null
    }

    public List execute(def params, def object) {
        try {
            List listMsg = []
            String successResult = ""
            String unSuccessResult = ""
            Date dateNow = new Date()
            SimpleDateFormat formatMonth = new SimpleDateFormat ("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat ("YY")
            String currentYear = formatYear.format(dateNow)
            ApplicationUser applicationUser = (ApplicationUser) object
            // Check Stock Availability
//            List result = retailOrderService.listRetailsOrderDetailsForProcessing(params)
            List result = listProductForCheckAllQuantityAction.execute(params,applicationUser)
            for(int i = 0; i < result.size(); i++){
                double diff =  Double.parseDouble(result[i].orderQuantity.toString()) - Double.parseDouble(result[i].availableQuantity.toString())
                if( diff > 0.0 ){
//                    Message message = this.getMessage(MESSAGE_HEADER, Message.ERROR, "Item " + result[i].productName + ": inadequate")
                    listMsg.add([success:false,message:"Item " + result[i].productName + ": inadequate"])
                    return listMsg
                }
            }
            String retailOrderNumberList = params.retailOrderNumbers
            String[] retailOrderNumbers = retailOrderNumberList.split(",")
            for(int j = 0 ; j < retailOrderNumbers.length; j++){
                Map mapInstance = [:]
                Float totalInvoiceAmount = 0
                List<CustomerStockTransaction> customerStockTransactionList = new  ArrayList<CustomerStockTransaction>()
                List<CustomerStock> customerStockListToBeUpdated = new ArrayList<CustomerStock>()
                RetailOrder retailOrder = RetailOrder.findByOrderNo(retailOrderNumbers[j])
//                RetailOrder retailOrder = retailOrderService.read(Long.parseLong(retailOrderNumbers[j]))
                if(!retailOrder){
//                    Message message = this.getMessage(MESSAGE_HEADER, Message.ERROR, "Retail Order not found")
                    listMsg.add([success:false,message:"System not found the Retail Order for '"+retailOrderNumbers[j]+"'"])
                    return listMsg
                }
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
                retailInvoice.dateCreated = new Date()

                retailInvoice.code = CodeGenerationUtil.instance.generateCode(retailOrder.enterprise, "RETAIL_INVOICE", "", "", "", "", "", currentMonth, currentYear, "", "")

                List<RetailOrderDetails> retailOrderDetailsList = RetailOrderDetails.findAllByRetailOrder(retailOrder)
                retailOrderDetailsList.each { retailOrderDetails ->
                    float quantity = retailOrderDetails.quantity
                    float tempQty = 0
                    List<CustomerStock> customerStockList = CustomerStock.withCriteria() {
                        eq("deliveryMan.id", applicationUser.customerMasterId)
                        eq("finishProduct", retailOrderDetails.finishProduct)
                        gtProperty("inQuantity", "outQuantity")
                    }
                    // Get updated product price
                    Map productPrice = productPriceService.getProductPriceByCustomerAndProduct(retailOrder.orderPlacedFor.id, retailOrderDetails.finishProduct.id, retailOrder.territorySubArea.id)
                    retailOrderDetails.rate = productPrice.productPriceWithVat
                    totalInvoiceAmount += retailOrderDetails.quantity * retailOrderDetails.rate
                    int totalItems = customerStockList.size()
                    for(int i = 0; i <totalItems; i++){
                        if(quantity > 0){
                            CustomerStockTransaction customerStockTransaction = new CustomerStockTransaction()
                            customerStockTransaction.unitPrice = retailOrderDetails.rate
                            customerStockTransaction.customerStock = customerStockList[i]
                            customerStockTransaction.userCreated = applicationUser
                            customerStockTransaction.inQuantity = 0
                            customerStockTransaction.inInvoice = null
                            customerStockTransaction.outInvoice = retailInvoice
                            customerStockTransaction.transactionDate = new Date()
                            customerStockList[i].userUpdated = applicationUser
                            customerStockListToBeUpdated.add(customerStockList[i])
                            tempQty = quantity
                            quantity -= (customerStockList[i].inQuantity - customerStockList[i].outQuantity)
                            if(quantity >= 0){
                                customerStockTransaction.outQuantity = customerStockList[i].inQuantity - customerStockList[i].outQuantity
                                customerStockList[i].outQuantity = customerStockList[i].inQuantity
                                customerStockTransactionList.add(customerStockTransaction)
                            }else {
                                customerStockTransaction.outQuantity = tempQty
                                customerStockList[i].outQuantity += tempQty
                                customerStockTransactionList.add(customerStockTransaction)
                                break
                            }
                        }
                    }
                }
                InvoiceDetails invoiceDetails = null
                Float totalVat = 0.00
                List<InvoiceDetails> invoiceDetailsList = new ArrayList<InvoiceDetails>()
                customerStockTransactionList.each {  customerStockTransaction ->
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
                    Map productPrice = productPriceService.getProductPriceByCustomerAndProduct(retailOrder.orderPlacedFor.id, invoiceDetails.finishProduct.id, retailOrder.territorySubArea.id)
                    invoiceDetails.unitVat = productPrice.productPriceWithVat - productPrice.productPriceWithoutVat
                    totalVat += invoiceDetails.unitPrice * invoiceDetails.quantity
                    if(!invoiceDetails.validate()){
                        throw new RuntimeException(this.getValidationErrorMessage(invoiceDetails).messageBody[0].toString())
                    }
                    invoiceDetailsList.add(invoiceDetails)
                }

                retailInvoice.invoiceAmount = totalInvoiceAmount
                retailInvoice.vat = totalVat
                if(!retailInvoice.validate()){
                    throw new RuntimeException(this.getValidationErrorMessage(retailInvoice).messageBody[0].toString())
                }
                mapInstance.put("retailInvoice", retailInvoice)
                mapInstance.put("retailInvoiceDetails", invoiceDetailsList)
                mapInstance.put("customerStockList", customerStockListToBeUpdated)
                mapInstance.put("customerStockTransactionList", customerStockTransactionList)
                mapInstance.put("retailOrder", retailOrder)
                Invoice createdInvoice = (Invoice) retailOrderService.manualProcessRetailOrder(mapInstance)
                Map mapMsg = [:]
                if (createdInvoice) {
                    mapMsg.success = true
                    mapMsg.message = "Order: " + retailOrder.orderNo + " processed with Invoice No: " + createdInvoice.code + ";"
                    mapMsg.retailOrderNo = retailOrder.orderNo
                    mapMsg.createdInvoiceNo = createdInvoice.code
//                    successResult += "Order: " + retailOrder.orderNo + " processed with Invoice No: " + createdInvoice.code + ";"
//                    return this.getMessage(createdInvoice, Message.SUCCESS, "Manual Order Processed Successfully, InvoiceNo:" + createdInvoice.code)
                } else {
                    mapMsg.success = false
                    mapMsg.message = "Order: " + retailOrder.orderNo + " process failed;"
                    mapMsg.retailOrderNo = retailOrder.orderNo
//                    unSuccessResult += "Order: " + retailOrder.orderNo + " failed;"
//                    return this.getMessage(createdInvoice, Message.ERROR, this.FAIL_SAVE)
                }
                listMsg.add(mapMsg)
            }
//            return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, successResult + unSuccessResult)
            return listMsg
        }
        catch (Exception ex) {
            List list = []
            log.error(ex.message)
            list.add([success:false,message:ex.message])
//            return this.getMessage(MESSAGE_HEADER, Message.ERROR, ex.message)
            return list
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}
