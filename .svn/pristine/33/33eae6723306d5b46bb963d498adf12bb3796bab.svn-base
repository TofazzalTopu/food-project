package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalStatus
import com.bits.bdfp.bonus.QuantityBasedBonus
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.demandorder.DemandOrderStatus
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.demandorder.InvoiceDetails
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointStock
import com.bits.bdfp.inventory.sales.DistributionPointStockTransaction
import com.bits.bdfp.inventory.sales.DistributionPointWarehouse
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import groovy.sql.Sql
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource
import java.text.SimpleDateFormat

class RegisterFinishGoodService{

    static transactional = false
    DataSource dataSource
    Sql sql
    SpringSecurityService  springSecurityService
    @Autowired
    SessionFactory sessionFactory

    @Transactional(readOnly = true)
    public List listReceivableOrder(Object params) {
        try{
            ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
            sql = new Sql(dataSource)
            String filterCondition = ""
            if(params.invoiceNo){
                filterCondition = " AND `invoice`.`code` = '${params.invoiceNo}'"
            }
            if(params.fromDate && params.toDate){
                filterCondition += " AND DATE(`invoice`.`date_created`) BETWEEN STR_TO_DATE('${params.fromDate}', '${ApplicationConstants.DATE_FORMAT_DB}') AND STR_TO_DATE('${params.toDate}', '${ApplicationConstants.DATE_FORMAT_DB}')"
            }
            String  strSql = """
                SELECT DISTINCT
                    `invoice`.`id`
                    , `invoice`.`code` AS `invoiceNo`
                    , `primary_demand_order`.`order_no` AS `orderNo`
                    , `distribution_point`.`name` AS `customer`
                    , `primary_demand_order`.`order_date` AS `orderDate`
                    , `invoice`.`invoice_amount` AS `invoiceAmount`

                FROM
                    `loading_slip_details`
                    INNER JOIN `loading_slip`
                        ON (`loading_slip_details`.`loading_slip_id` = `loading_slip`.`id`)
                    INNER JOIN `invoice`
                        ON (`loading_slip_details`.`invoice_id` = `invoice`.`id`)
                    INNER JOIN `primary_demand_order`
                        ON (`invoice`.`primary_demand_order_id` = `primary_demand_order`.`id`)
                    INNER JOIN `distribution_point`
                        ON (`primary_demand_order`.`distribution_point_id` = `distribution_point`.`id`)
                WHERE `primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.IN_TRANSIT}'
                    AND `primary_demand_order`.`distribution_point_id` in (Select `distribution_point_id` from `application_user_distribution_point` where `application_user_id` = ${applicationUser.id})
                    ${filterCondition}
                    ORDER BY `invoice`.`id`
            """
            List list = sql.rows(strSql)
            return list
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listReceivableOrderDetails(Object params) {
        sql = new Sql(dataSource)
        String  strSql = """
            SELECT
                `finish_product`.`id`
                , `finish_product`.`name` AS `item`
                , `invoice_details`.`batch_number`
                , `invoice_details`.`quantity`
                , `invoice_details`.`unit_price` AS `amount`
            FROM
                `invoice_details`
                INNER JOIN `invoice`
                    ON (`invoice_details`.`invoice_id` = `invoice`.`id`)
                INNER JOIN `finish_product`
                    ON (`invoice_details`.`finish_product_id` = `finish_product`.`id`)
                WHERE `invoice`.`id` =:id
                AND invoice.is_active = true
                ORDER BY `invoice_details`.`id`
        """
        List list = sql.rows(strSql, params)
        return list
    }

    @Transactional
    public Object receiveFinishGood(Object params){
        try {
            DistributionPointStockTransaction distributionPointStockTransaction = null
            Invoice invoice = Invoice.get(Long.parseLong(params.invoiceId))
            if(invoice){
                ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
                SubWarehouse subWarehouse = SubWarehouse.get(Long.parseLong(params.subWarehouseId))
                List<InvoiceDetails> invoiceDetailsList = InvoiceDetails.findAllByInvoice(invoice)
                invoiceDetailsList.each { invoiceDetails ->
                    FinishProduct finishProduct = invoiceDetails.finishProduct
                    DistributionPointStock distributionPointStock = DistributionPointStock.findWhere(subWarehouse: subWarehouse, finishProduct: finishProduct, batchNo:invoiceDetails.batchNumber)
                    if(distributionPointStock){
                        // Update In Quantity
                        distributionPointStock.inQuantity += invoiceDetails.quantity
                        distributionPointStock.userUpdated = applicationUser
                        distributionPointStock.save(validate: false)
                    }else{
                        distributionPointStock = new DistributionPointStock()
                        distributionPointStock.subWarehouse = subWarehouse
                        distributionPointStock.finishProduct = finishProduct
                        distributionPointStock.batchNo = invoiceDetails.batchNumber
                        distributionPointStock.inQuantity = invoiceDetails.quantity
                        distributionPointStock.outQuantity = 0
                        distributionPointStock.userCreated = applicationUser
                        distributionPointStock.save(validate: false, insert: true)
                    }
                    distributionPointStockTransaction = new DistributionPointStockTransaction()
                    distributionPointStockTransaction.distributionPointStock = distributionPointStock
                    distributionPointStockTransaction.inQuantity = invoiceDetails.quantity
                    distributionPointStockTransaction.outQuantity = 0
                    distributionPointStockTransaction.unitPrice = invoiceDetails.unitPrice
                    distributionPointStockTransaction.inInvoice = invoice
                    distributionPointStockTransaction.transactionDate = new Date()
                    distributionPointStockTransaction.userCreated = applicationUser
                    distributionPointStockTransaction.save(validate: false, insert: true)
                }

                //BONUS**************************************************************************************
                List<QuantityBasedBonus> quantityBasedBonusList = QuantityBasedBonus.findAllByInvoice(invoice)
                Warehouse warehouse = Warehouse.read(Long.parseLong(params.warehouseId))
                SubWarehouse subWarehouseBonus = SubWarehouse.findWhere(warehouse: warehouse, subWarehouseType: SubWarehouseType.read(ApplicationConstants.BONUS_TYPE_INVENTORY_ID))
                for(int i = 0; i < quantityBasedBonusList.size(); i++){
                    DistributionPointStock distributionPointStock = DistributionPointStock.findWhere(subWarehouse: subWarehouseBonus, finishProduct: quantityBasedBonusList[i].finishProduct, batchNo: quantityBasedBonusList[i].batchNo)
                    if(distributionPointStock){
                        // Update In Quantity
                        distributionPointStock.inQuantity += quantityBasedBonusList[i].quantity
                        distributionPointStock.userUpdated = applicationUser
                        distributionPointStock.save(validate: false)
                    }else{
                        distributionPointStock = new DistributionPointStock()
                        distributionPointStock.subWarehouse = subWarehouseBonus
                        distributionPointStock.finishProduct = quantityBasedBonusList[i].finishProduct
                        distributionPointStock.batchNo = quantityBasedBonusList[i].batchNo
                        distributionPointStock.inQuantity = quantityBasedBonusList[i].quantity
                        distributionPointStock.outQuantity = 0
                        distributionPointStock.userCreated = applicationUser
                        distributionPointStock.save(validate: false, insert: true)
                    }
                    distributionPointStockTransaction = new DistributionPointStockTransaction()
                    distributionPointStockTransaction.distributionPointStock = distributionPointStock
                    distributionPointStockTransaction.inQuantity = quantityBasedBonusList[i].quantity
                    distributionPointStockTransaction.outQuantity = 0
                    distributionPointStockTransaction.unitPrice = 0
                    distributionPointStockTransaction.inInvoice = invoice
                    distributionPointStockTransaction.transactionDate = new Date()
                    distributionPointStockTransaction.userCreated = applicationUser
                    distributionPointStockTransaction.save(validate: false, insert: true)
                }

                /*************************** COA Start *************/
                Date dateNow = new Date()
                SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
                String currentMonth = formatMonth.format(dateNow)
                SimpleDateFormat formatYear = new SimpleDateFormat("YY")
                String currentYear = formatYear.format(dateNow)
                SimpleDateFormat formatDay = new SimpleDateFormat ("DD")
                String currentDay = formatDay.format(dateNow)

                JournalDetails journalDetails = null
                ChartOfAccountsMapping chartOfAccountsMapping = null
                CustomerMaster defaultCustomer = invoice.defaultCustomer
                EnterpriseConfiguration enterprise = defaultCustomer.enterpriseConfiguration
                DistributionPoint distributionPoint = DistributionPointWarehouse.findByDefaultCustomer(defaultCustomer).distributionPoint
                Journal journalHead = new Journal(enterprise: enterprise, userCreated: applicationUser, isActive: true)
                journalHead.tableName =  sessionFactory.getClassMetadata(Invoice).tableName
//                    GrailsDomainBinder.getMapping(CustomerPayment).table.name
                journalHead.transactionDate = new Date()
                journalHead.journalStatus = JournalStatus.CHECKED
                journalHead.transactionNo = invoice.code
                journalHead.journalNo = CodeGenerationUtil.instance.generateCode(enterprise, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
                journalHead.moduleName = ApplicationConstants.MODULE_RECEIVE_FINISH_GOODS
                if(!journalHead.validate()){
                    throw new RuntimeException(journalHead.errors.allErrors.toString())
                }
                journalHead.save(validate: false, insert: true)

                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.PURCHASE)
                if(!chartOfAccountsMapping){
                    throw new RuntimeException("Purchase Head is not Mapped with Charts of Accounts")
                }
                journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                journalDetails.prefixCode = ""
                journalDetails.postfixCode = distributionPoint.code  // DP
                journalDetails.debitAmount = invoice.invoiceAmount
                journalDetails.creditAmount = 0.00
                journalDetails.particular = "Receive Invoice for Customer: [" + defaultCustomer.code + "] " + defaultCustomer.name
                journalDetails.save(validate: false, insert: true)

                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_PAYABLE)
                if(!chartOfAccountsMapping){
                    throw new RuntimeException("Accounts Payable Head is not Mapped with Charts of Accounts")
                }
                journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                journalDetails.prefixCode = invoice.distributionPoint.code // Factory
                journalDetails.postfixCode = distributionPoint.code // DP
                journalDetails.debitAmount = 0.00
                journalDetails.creditAmount = invoice.invoiceAmount
                journalDetails.particular = "Receive Invoice for Customer: [" + defaultCustomer.code + "] " + defaultCustomer.name
                journalDetails.save(validate: false, insert: true)

                /***************************************************/

                PrimaryDemandOrder primaryDemandOrder = invoice.primaryDemandOrder
                primaryDemandOrder.demandOrderStatus = DemandOrderStatus.DELIVERED
                primaryDemandOrder.save()
                return primaryDemandOrder
            }
            return null
        }
        catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

}
