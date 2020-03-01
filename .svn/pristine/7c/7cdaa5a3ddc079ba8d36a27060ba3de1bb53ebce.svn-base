package com.bits.bdfp.inventory.sales

import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.finance.CustomerAccount
import com.bits.bdfp.finance.CustomerPayment
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.demandorder.InvoiceDetails
import com.bits.bdfp.inventory.setup.PosCustomer
import com.bits.bdfp.inventory.warehouse.FinishGoodStock
import com.bits.bdfp.inventory.warehouse.FinishGoodStockTransaction
import com.bits.bdfp.inventory.warehouse.Warehouse
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Action
import com.docu.security.ApplicationUser
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional
import javax.sql.DataSource

class InvoiceService {

    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public boolean create(Object data, Object params) {
        try {
            Invoice directInvoice = (Invoice) data.get('directInvoice')
            directInvoice.save(validate: false, insert: true)
            List<InvoiceDetails> invoiceDetailsList = (List<InvoiceDetails>) data.get('directInvoiceDetails')
            invoiceDetailsList.each { invoiceDetails->
                invoiceDetails.save(validate: false, insert: true)
            }

            Journal journal = (Journal) data.get('journal')
            journal.save(validate: false, insert: true)

            List<JournalDetails> journalDetailsList = (List<JournalDetails>) data.get('journalDetailsList')
            journalDetailsList.each { journalDetails->
                journalDetails.save(validate: false, insert: true)
            }

            List<FinishGoodStock> finishGoodStockList = (List<FinishGoodStock>) data.get('finishGoodStockList')
            finishGoodStockList.each { finishGoodStock->
                finishGoodStock.save()
            }
            List<FinishGoodStockTransaction> finishGoodStockTransactionList = (List<FinishGoodStockTransaction>) data.get('finishGoodStockTransactionList')
            finishGoodStockTransactionList.each { finishGoodStockTransaction->
                finishGoodStockTransaction.save(validate: false, insert: true)
            }
            List<DistributionPointStock> distributionPointStockList = (List<DistributionPointStock>) data.get('distributionPointStockList')
            distributionPointStockList.each { distributionPointStock->
                distributionPointStock.save()
            }

            List<DistributionPointStockTransaction> distributionPointStockTransactionList = (List<DistributionPointStockTransaction>) data.get('distributionPointStockTransactionList')
            distributionPointStockTransactionList.each { distributionPointStockTransaction->
                distributionPointStockTransaction.save(validate: false, insert: true)
            }

            CustomerAccount customerAccount = (CustomerAccount) data.get('customerAccount')
            customerAccount.save()

            CustomerPayment customerPayment = (CustomerPayment) data.get('customerPayment')
            customerPayment.save(validate: false, insert: true)

            return true
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listCustomer(Object params) {
        sql = new Sql(dataSource)
        String search = ''
        if (params.searchKey) (
                search = """ AND (`customer_master`.`name` LIKE '%${params.searchKey}%'
                            OR `customer_master`.`code` LIKE '%${params.searchKey}%')
                        """
        )
        if (params.isCustomer == 'false') {
            String strSql = """
                            SELECT `customer_master`.`id`,`customer_master`.`name`,`customer_master`.`code`,
                                `customer_category`.`name` AS category
                            FROM `customer_master`
                            INNER JOIN `customer_category` ON `customer_category`.`id` = `customer_master`.`category_id`
                            WHERE `customer_master`.`enterprise_configuration_id` = ${params.entId}
                                ${search}
                          """
            List objList = sql.rows(strSql)
            return objList
        } else {
//            String strSql = """
//                            SELECT `distribution_point`.`is_factory`
//                            FROM `distribution_point`
//                            INNER JOIN `application_user_distribution_point`
//                                ON `application_user_distribution_point`.`distribution_point_id` = `distribution_point`.`id`
//                            WHERE `application_user_distribution_point`.`application_user_id` = ${params.appId}
//                          """
//            List objList = sql.rows(strSql)
            String strSql = """
                            SELECT `customer_territory_sub_area`.`territory_sub_area_id`
                            FROM `customer_territory_sub_area`
                            WHERE `customer_territory_sub_area`.`customer_master_id` = ${params.isCustomer}
                          """
            List geoList = sql.rows(strSql)
            String cond = """ (`customer_territory_sub_area`.`territory_sub_area_id` = ${
                geoList[0].territory_sub_area_id
            }"""
            for (int i = 1; i < geoList.size(); i++) {
                cond = cond + """ OR `customer_territory_sub_area`.`territory_sub_area_id` = ${
                    geoList[i].territory_sub_area_id
                }"""
            }
            cond = cond + """)"""
//            if (objList[0].is_factory == true) {
            strSql = """
                            SELECT DISTINCT `customer_master`.`id`,`customer_master`.`name`,`customer_master`.`code`,
                                `customer_category`.`name` AS category
                            FROM `customer_master`
                            INNER JOIN `customer_category` ON `customer_category`.`id` = `customer_master`.`category_id`
                            INNER JOIN `customer_territory_sub_area`
                                ON `customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`
                            WHERE `customer_master`.`id` != ${params.isCustomer}
                                AND ${cond}
                                ${search}
                          """
            List objList = sql.rows(strSql)
//            } else {
//                strSql = """
//                            SELECT DISTINCT `customer_master`.`id`,`customer_master`.`name`,`customer_master`.`code`,
//                                `customer_category`.`name` AS category
//                            FROM `customer_master`
//                            INNER JOIN `customer_category` ON `customer_category`.`id` = `customer_master`.`category_id`
//                            WHERE `customer_master`.`category_id` = 3
//                                AND `customer_master`.`id` != ${params.isCustomer}
//                                AND ${cond}
//                                ${search}
//                          """
//                objList = sql.rows(strSql)
//            }
            return objList
        }
    }

    @Transactional(readOnly = true)
    public List listUnadjustedInvoice(Object params) {
        sql = new Sql(dataSource)
        String cond = ''
        String strSql = ''

        if (params.customerId) {
            cond = """ AND `invoice`.`default_customer_id` = ${params.customerId}"""
        } else {
            strSql = """
                            SELECT `customer_territory_sub_area`.`territory_sub_area_id`
                            FROM `customer_territory_sub_area`
                            INNER JOIN `application_user` ON `application_user`.`customer_master_id` = `customer_territory_sub_area`.`customer_master_id`
                            WHERE `application_user`.`id` = ${params.appId}
                          """
            List geoList = sql.rows(strSql)
            if(geoList) {
                cond = """ AND (`customer_territory_sub_area`.`territory_sub_area_id` = ${
                    geoList[0].territory_sub_area_id
                }"""
                for (int i = 1; i < geoList.size(); i++) {
                    cond = cond + """ OR `customer_territory_sub_area`.`territory_sub_area_id` = ${
                        geoList[i].territory_sub_area_id
                    }"""
                }
                cond = cond + """)"""
            }else{
                cond = """ AND `customer_territory_sub_area`.`territory_sub_area_id` = 0"""
            }
        }

        if (params.dateFrom && params.dateTo) {
            cond = cond + """ AND DATE(`invoice`.`date_created`) BETWEEN STR_TO_DATE('${params.dateFrom}','%d-%m-%Y')
              AND STR_TO_DATE('${params.dateTo}','%d-%m-%Y')"""
        }

        strSql = """
                            SELECT `invoice`.`id`,`invoice`.`code`,ROUND(`invoice`.`invoice_amount`,4) AS invoice_amount,
                                ROUND(`invoice`.`invoice_amount`-`invoice`.`paid_amount`,4) AS due_amount,
                                `customer_master`.`id` as customer_id,`customer_master`.`name`,
                                `customer_master`.`code` as customer_code,DATE_FORMAT(`invoice`.`date_created`,'%d-%m-%Y') AS date_created
                            FROM `invoice`
                            INNER JOIN `customer_master` ON `customer_master`.`id` = `invoice`.`default_customer_id`
                            INNER JOIN `customer_territory_sub_area`
                                ON `customer_territory_sub_area`.`customer_master_id` = `invoice`.`default_customer_id`
                            WHERE  invoice.is_active = true
                                AND `invoice`.`invoice_amount`-`invoice`.`paid_amount` > 0
                                ${cond}
                            GROUP BY `invoice`.`id`
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List listUnadjustedInvoiceByCustomerId(Object params) {
        sql = new Sql(dataSource)
        String cond = ''
        String strSql = ''
        List objList
        if (params.customerId) {
            strSql = """
                           SELECT `invoice`.`id`,`invoice`.`code`, `invoice`.`code`, `invoice`.`transaction_date`,
                           `invoice`.`invoice_amount`,
                           ROUND((`invoice`.`invoice_amount`-`invoice`.`paid_amount`),4) AS due_amount
                           FROM `invoice`
                           WHERE invoice.is_active = true
                               AND `invoice`.`default_customer_id`=${params.customerId}
                               AND ROUND(`invoice`.`invoice_amount`-`invoice`.`paid_amount`,4) > 0
                               AND (is_bill =0 OR is_bill IS NULL);

                      """
            /*AND ROUND(`invoice`.`invoice_amount`-`invoice`.`paid_amount`,4) > 0*/
            //FROM `invoice` WHERE CODE=15703160034 ${params.customerId}
             objList = sql.rows(strSql)
        }


        return objList
    }

    @Transactional(readOnly = true)
    public Map productBatchAvailabilityForDirectInvoice(Action action, Object params) {
        sql = new Sql(dataSource)
        String strLIMIT = "";
        String offSet = ""
        List objList = new ArrayList()

        if (action.resultPerPage != -1) {
            strLIMIT = """LIMIT ${action.resultPerPage}"""
            offSet = """
                    OFFSET ${action.start}
                   """
        } else {
            action.resultPerPage = -1;
        }

        String strSql = ""

        CustomerMaster customerMaster = CustomerMaster.get(Long.parseLong(params.customerId))
        PosCustomer posCustomer = PosCustomer.findByCustomerMaster(customerMaster)
        if(!posCustomer){
            return [objList: objList, count: 0]
        }
        DistributionPointWarehouse distributionPointWarehouse = DistributionPointWarehouse.findByDistributionPoint(posCustomer.distributionPoint)
        if(!distributionPointWarehouse){
            return [objList: objList, count: 0]
        }
        Warehouse warehouse = distributionPointWarehouse.warehouse

        if(distributionPointWarehouse.distributionPoint.isFactory){
            // Get batch wise data from factory stock
            strSql = """
                SELECT `finish_good_stock`.`id`, `finish_good_stock`.`batch_no`,
                    SUM(`finish_good_stock`.`in_quantity` - `finish_good_stock`.`out_quantity`) AS `quantity`, DATE_FORMAT(`finish_good_stock`.`date_created`, '${ApplicationConstants.DATE_FORMAT_DB}') AS `batch_date`
                FROM `finish_good_stock`
                    INNER JOIN `sub_warehouse` ON (`finish_good_stock`.`sub_warehouse_id` = `sub_warehouse`.`id`)
                WHERE `sub_warehouse`.`warehouse_id` = ${warehouse.id}
                    AND `sub_warehouse`.`sub_warehouse_type_id` = ${ApplicationConstants.SALABLE_TYPE_INVENTORY_ID}
                    AND `finish_good_stock`.`finish_product_id` = ${params.id}
                    AND `finish_good_stock`.`in_quantity` > `finish_good_stock`.`out_quantity`
                GROUP BY `finish_good_stock`.`id`, `finish_good_stock`.`batch_no`
                ORDER BY `finish_good_stock`.`id`
                ${strLIMIT}
                ${offSet}
            """
        } else {
            // Get batch wise data from DP stock
            strSql = """
                SELECT `distribution_point_stock`.`id`, `distribution_point_stock`.`batch_no`,
                    SUM(`distribution_point_stock`.`in_quantity` - `distribution_point_stock`.`out_quantity`) AS `quantity`, DATE_FORMAT(`distribution_point_stock`.`date_created`, '%d-%m-%Y') AS `batch_date`
                FROM `distribution_point_stock`
                    INNER JOIN `sub_warehouse` ON (`distribution_point_stock`.`sub_warehouse_id` = `sub_warehouse`.`id`)
                WHERE `sub_warehouse`.`warehouse_id` = ${warehouse.id}
                    AND `sub_warehouse`.`sub_warehouse_type_id` = ${ApplicationConstants.SALABLE_TYPE_INVENTORY_ID}
                    AND `distribution_point_stock`.`finish_product_id` = ${params.id}
                    AND `distribution_point_stock`.`in_quantity` > `distribution_point_stock`.`out_quantity`
                GROUP BY `distribution_point_stock`.`id`, `distribution_point_stock`.`batch_no`
                ORDER BY `distribution_point_stock`.`id`
                ${strLIMIT}
                ${offSet}
            """
        }
        objList = sql.rows(strSql)
        int resultCount = 0
        if (objList && objList.size() > 0) {
            resultCount = objList.size()
        }

        return [objList: objList, count: resultCount]
    }

    @Transactional(readOnly = true)
    public List listPrintSecondaryInvoiceAutoComplete(String orderNO, ApplicationUser applicationUser) {
        sql = new Sql(dataSource)
        String strSql = """
            SELECT invoice.id, invoice.code AS `invoice_no`, customer_master.name
            FROM `invoice`
                INNER JOIN `customer_master` ON (customer_master.id = invoice.`default_customer_id`)
            WHERE invoice.is_active = true
                AND invoice.`secondary_demand_order_id` IS NOT NULL
                AND `invoice`.`user_created_id` = ${applicationUser.id}
                AND `invoice`.`code` LIKE '%${orderNO}%'
            ORDER BY `invoice`.`date_created` DESC
        """
        return sql.rows(strSql)
    }

    @Transactional(readOnly = true)
    public Object getDpByCustomerAndGeo(Long customerId) {
        sql = new Sql(dataSource)
        String strSql = """
            SELECT dptsa.distribution_point_id AS dpId
            FROM customer_territory_sub_area ctsa
            INNER JOIN distribution_point_territory_sub_area dptsa
                    ON dptsa.territory_sub_area_id = ctsa.territory_sub_area_id
            WHERE ctsa.customer_master_id = ${customerId}
            LIMIT 1
        """
        return sql.firstRow(strSql)
    }
}
