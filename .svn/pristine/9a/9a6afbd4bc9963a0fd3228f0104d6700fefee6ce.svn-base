package com.bits.bdfp.rest

import com.bits.bdfp.finance.CustomerAccount
import com.bits.bdfp.finance.CustomerPayment
import com.bits.bdfp.finance.CustomerPaymentInvoice
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetails
import com.bits.bdfp.util.ApplicationConstants
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource
import groovy.sql.Sql

class RestDataService {

    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional(readOnly = true)
    public List getGeoLocationByApplicationUser(Object applicationUser) {
        try {
            String sql = """
                SELECT DISTINCT
                    `territory_sub_area`.`id`
                    , `territory_sub_area`.`geo_location`
                    , `territory_sub_area`.`para_or_locality`
                    , `territory_sub_area`.`road`
                FROM
                    `customer_territory_sub_area`
                    INNER JOIN `territory_sub_area`
                        ON (`customer_territory_sub_area`.`territory_sub_area_id` = `territory_sub_area`.`id`)
                    INNER JOIN `customer_master`
                        ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`)
                    INNER JOIN `application_user`
                        ON (`application_user`.`customer_master_id` = `customer_master`.`id`)
                WHERE `application_user`.`id` = ${applicationUser.id}
            """
            Sql db = new Sql(dataSource)
            return db.rows(sql)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public SecondaryDemandOrder createSecondaryDemandOrder(Map map) {
        try {
            SecondaryDemandOrder secondaryDemandOrder = (SecondaryDemandOrder) map.get('secondaryDemandOrder')
            secondaryDemandOrder = secondaryDemandOrder.save(validate: false, insert: true)
            List<SecondaryDemandOrderDetails> secondaryDemandOrderDetailList = map.get('secondaryDemandOrderDetails')
            secondaryDemandOrderDetailList.each { secondaryDemandOrderDetails ->
                secondaryDemandOrderDetails.save(validate: false, insert: true)
            }

            String[] retailOrderNumbers = map.get('retailOrderNumbers')
            String condition = ""
            for (int i = 0; i < retailOrderNumbers.length; i++) {
                if (retailOrderNumbers[i]) {
                    if (condition != ""){
                        condition += " OR"
                    }else{
                        condition += " AND ("
                    }
                    condition += """ `retail_order`.`order_no` = '${retailOrderNumbers[i]}' """
                }
            }
            if(condition != ""){
                condition += " )"
            }

            sql = new Sql(dataSource)
            String query = """
                UPDATE `retail_order`
                    SET `retail_order`.`secondary_demand_order_id` = ${secondaryDemandOrder.id},
                    `retail_order`.`delivery_date` = '${DateUtil.getDatabaseDateFormatAsString(secondaryDemandOrder.dateDeliver, ApplicationConstants.DATE_FORMAT_Y_M_D)}'
                WHERE 1 = 1
                    ${condition}
            """
            sql.execute(query)
            return secondaryDemandOrder
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List getProductListForCheckAllQuantity(Object params, Object applicationUser){
        try {
            String retailOrderNumbers = ""
            String[] retailOrderNumberList = params.retailOrderNumbers.split(",")

            for(int i=0; i<retailOrderNumberList.length; i++){
                if(retailOrderNumbers){
                    retailOrderNumbers += ",'"+retailOrderNumberList[i]+"'"
                }else{
                    retailOrderNumbers = "'"+retailOrderNumberList[i]+"'"
                }
            }

            String sql = """
                SELECT
                    `finish_product`.`id`
                    , `finish_product`.`code` AS `productCode`
                    , `finish_product`.`name` AS `productName`
                    , SUM(`retail_order_details`.`quantity`) AS `orderQuantity`
                    , IFNULL(( SELECT SUM(IFNULL(`customer_stock`.`in_quantity`, 0)) - SUM(IFNULL(`customer_stock`.`out_quantity`, 0))
                        FROM `customer_stock`
                        WHERE `customer_stock`.`finish_product_id` = `finish_product`.`id`
                            AND `customer_stock`.`delivery_man_id` =  ${applicationUser.customerMasterId}
                        GROUP BY `customer_stock`.`finish_product_id`), 0) AS `availableQuantity`
                FROM `retail_order_details`
                INNER JOIN `retail_order`
                        ON (`retail_order`.`id` = `retail_order_details`.`retail_order_id`)
                INNER JOIN `finish_product`
                        ON (`retail_order_details`.`finish_product_id` = `finish_product`.`id`)
                WHERE `retail_order`.`order_no` IN (${retailOrderNumbers})
                GROUP BY `retail_order_details`.`finish_product_id`
                ORDER BY `finish_product`.`id`
            """
            Sql db = new Sql(dataSource)
            return db.rows(sql)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List getProductListForCheckManualQuantity(Object params, Object applicationUser){
        try {
            String priductIds = ""
            params.items.each{
                if(priductIds){
                    priductIds += ","+it.productId
                }else{
                    priductIds = it.productId
                }
            }

            String sql = """
                SELECT
                    `finish_product`.`id`
                    , `finish_product`.`code` AS `productCode`
                    , `finish_product`.`name` AS `productName`
                    , IFNULL(( SELECT SUM(IFNULL(`customer_stock`.`in_quantity`, 0)) - SUM(IFNULL(`customer_stock`.`out_quantity`, 0))
                                FROM `customer_stock`
                                WHERE `customer_stock`.`finish_product_id` = `finish_product`.`id`
                                    AND `customer_stock`.`delivery_man_id` =  ${applicationUser.customerMasterId}
                                GROUP BY `customer_stock`.`finish_product_id`), 0) AS `availableQuantity`
                FROM `finish_product`
                WHERE `finish_product`.`id` IN (${priductIds?priductIds:null})
                ORDER BY `finish_product`.`id`
            """
            Sql db = new Sql(dataSource)
            return db.rows(sql)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Boolean applyCashCollectionForInvoice(Map data, Object user) {
        try{
            ApplicationUser applicationUser = (ApplicationUser) user
            CustomerPayment customerPayment = (CustomerPayment) data.get("customerPayment")
            customerPayment.save()

            List<Invoice> retailInvoiceList = (List<Invoice>) data.get("retailInvoiceList")
            retailInvoiceList.each { retailInvoice->
                retailInvoice.userUpdated = applicationUser
                retailInvoice.save()
            }

            List<CustomerPaymentInvoice> customerPaymentInvoiceList = data.get("customerPaymentInvoiceList")
            if (customerPaymentInvoiceList && customerPaymentInvoiceList.size() > 0) {
                customerPaymentInvoiceList.each {
                    it.save(validate: false, insert: true)
                }
            }

            CustomerAccount customerAccount = (CustomerAccount )data.get("customerAccount")
            customerAccount.save()
            return true
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public List getCustomerStockList(Object params){
        String sql = """
            SELECT -- `customer_stock`.id,
            `finish_product`.`id` AS productId,
            `finish_product`.`code` AS productCode, `finish_product`.`name` AS productName,
            IFNULL(`customer_stock`.`batch_no`,'') AS batchNo,
            (`customer_stock`.`in_quantity` - `customer_stock`.`out_quantity`) AS availableQuantity,
            IFNULL(ROUND((SELECT `product_price_product`.`total_amount` AS priceWithVat
                FROM `customer_master`
                INNER JOIN `pricing_category` ON (`pricing_category`.id = `customer_master`.`pricing_category_id`)
                INNER JOIN `product_price_product` ON (`pricing_category`.`id` = `product_price_product`.`pricing_category_id`)
                INNER JOIN `product_price` ON (`product_price_product`.`product_price_id` = `product_price`.`id`
                       AND `product_price`.`enterprise_configuration_id` = `customer_master`.`enterprise_configuration_id` AND product_price.`is_active` = TRUE)
                INNER JOIN `customer_territory_sub_area` ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`)
                INNER JOIN `territory_sub_area_product_price` ON (`customer_territory_sub_area`.`territory_sub_area_id` = `territory_sub_area_product_price`.`territory_sub_area_id`
                       AND `territory_sub_area_product_price`.`product_price_id` = `product_price`.`id`)
                WHERE `customer_master`.`id`= `customer_stock`.`delivery_man_id`
                  AND `product_price_product`.`finish_product_id` = `finish_product`.`id`
                  AND DATE(NOW()) >= DATE(`product_price`.`date_effective_from`) AND DATE(NOW()) <= DATE(IFNULL(`product_price`.`date_effective_to`, NOW()))
            ),2),0) AS price

            FROM `customer_stock`
            INNER JOIN `finish_product` ON (`customer_stock`.`finish_product_id` = `finish_product`.`id`)
            WHERE `customer_stock`.`in_quantity` > `customer_stock`.`out_quantity`
            AND `customer_stock`.`delivery_man_id` = ${params.deliveryManId}
            AND `customer_stock`.id NOT IN (
                    SELECT (SELECT CONCAT(customer_stock.id)
                    FROM `customer_stock`
                    WHERE `customer_stock`.`finish_product_id` = `finish_product`.`id`
                    AND `customer_stock`.`delivery_man_id` =  ${params.deliveryManId}
                    GROUP BY `customer_stock`.`finish_product_id`
                ) AS stockIds

                FROM `retail_order_details`
                INNER JOIN `retail_order`
                        ON (`retail_order_details`.`retail_order_id` = `retail_order`.`id`)
                INNER JOIN `finish_product`
                        ON (`retail_order_details`.`finish_product_id` = `finish_product`.`id`)
                WHERE `retail_order`.`order_no` = '${params.retailOrderNo}'
                ORDER BY `finish_product`.`id`
            )
        """
        Sql db = new Sql(dataSource)
        return db.rows(sql)
    }
}
