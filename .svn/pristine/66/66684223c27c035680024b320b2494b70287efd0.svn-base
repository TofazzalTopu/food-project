package com.bits.bdfp.inventory.sales

import com.bits.bdfp.customer.CustomerMaster
import com.docu.common.Service
import com.docu.commons.InternationalizationService
import com.docu.security.ApplicationUser
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional
import com.docu.commons.CommonConstants

import javax.sql.DataSource


class MarketReturnService extends Service {
    static transactional = false

    DataSource dataSource
    Sql sql

    @Transactional
    public Object read(Long id) {
        MarketReturn marketReturn = MarketReturn.read(id)
        if (marketReturn.mrStatus == "MR_IN_TRANSIT" || marketReturn.mrStatus == "REJECTED") {
            marketReturn.mrStatus = "INITIATED"
            marketReturn.save()
            return marketReturn
        } else {
            return marketReturn
        }
    }

    @Transactional(readOnly = true)
    public List list() {
        List<MarketReturn> marketReturnList = []
        long marketReturnCount = 0
        try {
            marketReturnList = MarketReturn.withCriteria {
                maxResults(Integer.parseInt(params.resultPerPage.toString()))
                firstResult(Integer.parseInt(params.start.toString()))
                order(params.sortCol.toString(), params.sortOrder.toString())
            }
            marketReturnCount = MarketReturn.count()
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return list
    }

    @Transactional
    public Object create(Object map) {
        try{
            MarketReturn marketReturn = map.marketReturn
            List<MarketReturnDetails> marketReturnDetailsList = map.marketReturnDetailsList
            List<DistributionPointStock> distributionPointStocks = map.distributionPointStocks
            List<DistributionPointStockTransaction> distributionPointStockTransactions = map.distributionPointStockTransactions
            if (marketReturn) {
                marketReturn.save(false)
                if (distributionPointStocks && distributionPointStocks.size() > 0) {
                    distributionPointStocks.each {
                        it.save()
                    }
                }
                if (marketReturnDetailsList && marketReturnDetailsList.size() > 0) {
                    marketReturnDetailsList.each {
                        it.save(false)
                    }
                }
                if (distributionPointStockTransactions && distributionPointStockTransactions.size() > 0) {
                    distributionPointStockTransactions.each {
                        it.save(false)
                    }
                }
            }
            return new Integer(1)
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object map) {
        try{
            MarketReturn marketReturn = map.get('marketReturn')
            List<MarketReturnDetails> marketReturnDetailsList = map.get('marketReturnDetailsList')
            List<DistributionPointStock> distributionPointStocks = map.distributionPointStocks
            List<DistributionPointStockTransaction> distributionPointStockTransactions = map.distributionPointStockTransactions
            if (marketReturn) {
                marketReturn.save()
                if (distributionPointStocks && distributionPointStocks.size() > 0) {
                    distributionPointStocks.each {
                        it.save()
                    }
                }
                if (marketReturnDetailsList && marketReturnDetailsList.size() > 0) {
                    marketReturnDetailsList.each {
                        if (it.id) {
                            it.save()
                        } else {
                            it.save(false)
                        }
                    }
                }
                if (distributionPointStockTransactions && distributionPointStockTransactions.size() > 0) {
                    distributionPointStockTransactions.each {
                        it.save(false)
                    }
                }
            }
            return new Integer(1)
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer delete(Object marketReturn) {
        try {
            marketReturn.delete()
            return 1
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public MarketReturn search(String fieldName, String fieldValue) {
        String query = "from MarketReturn as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return MarketReturn.find(query)
    }

    @Transactional(readOnly = true)
    public List listTerritory(Object params) {
        sql = new Sql(dataSource)
        String cond = """"""
        if (params.id) {
            cond = """ AND `customer_territory_sub_area`.`customer_master_id` = ${params.id}"""
        }
        String strSql = """
                            SELECT `territory_configuration`.`name`,`territory_configuration`.`id`
                            FROM `customer_territory_sub_area`
                            INNER JOIN `territory_sub_area`
                                ON `territory_sub_area`.id = `customer_territory_sub_area`.`territory_sub_area_id`
                            INNER JOIN `territory_configuration`
                                ON `territory_configuration`.id = `territory_sub_area`.`territory_configuration_id`
                            WHERE `territory_configuration`.`enterprise_configuration_id` = ${params.entId}
                                ${cond}
                            GROUP BY `territory_configuration`.`id`
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List listGeoLocation(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT`territory_sub_area`.`id`,CONCAT(`territory_sub_area`.`geo_location`,', Locality: ',
                                `territory_sub_area`.`para_or_locality`,', Road: ',
                                `territory_sub_area`.`road`) AS geo_location
                            FROM `customer_territory_sub_area`
                            INNER JOIN `territory_sub_area`
                                ON `territory_sub_area`.id = `customer_territory_sub_area`.`territory_sub_area_id`
                            WHERE `customer_territory_sub_area`.`customer_master_id` = ${params.id}
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List listDp(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `name`,`id`,`code`
                            FROM `distribution_point`
                            WHERE `enterprise_configuration_id` = ${params.entId}
                                AND `is_factory` IS TRUE
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List fetchCustomerByGeo(Object params) {
        sql = new Sql(dataSource)
        String join = ""
        String cond = ""
        String cat = ""
        if (params.searchKey) {
            cond = """ AND (`customer_master`.`name` LIKE '%${params.searchKey}%' OR `customer_master`.`code` LIKE '%${
                params.searchKey
            }%')"""
        }
        if (params.cat == '3') {
            join = """
                    INNER JOIN `distribution_point_territory_sub_area`
                        ON `customer_territory_sub_area`.`territory_sub_area_id` = `distribution_point_territory_sub_area`.`territory_sub_area_id`
                   """
            cond = cond + """WHERE `customer_master`.`category_id` = ${params.cat}
                                AND `distribution_point_territory_sub_area`.`distribution_point_id` = ${params.id}"""
        }else{
            cond = cond + """WHERE `customer_master`.`category_id` != 1
                                AND `customer_master`.`category_id` != 3
                                AND `customer_master`.`category_id` != 8
                                AND `customer_master`.`id` != ${params.appCustomer}
                                AND `customer_territory_sub_area`.`territory_sub_area_id` IN (
                                    SELECT `territory_sub_area_id`
                                    FROM `customer_territory_sub_area`
                                    INNER JOIN `application_user`
                                        ON `application_user`.`customer_master_id` = `customer_territory_sub_area`.`customer_master_id`
                                    WHERE `application_user`.id = ${params.appUser}
                                )
                                """
        }
        String strSql = """
                            SELECT `customer_master`.`name`,`customer_master`.id,`customer_master`.code,
                                `customer_category`.`name` AS category
                            FROM `customer_master`
                            INNER JOIN `customer_category`
                                ON `customer_category`.id = `customer_master`.`category_id`
                            INNER JOIN `customer_territory_sub_area`
                                ON `customer_master`.id = `customer_territory_sub_area`.`customer_master_id`
                            ${join}
                            ${cond}
                            GROUP BY `customer_master`.`id`
                            ORDER BY `customer_master`.`id`
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public CustomerMaster fetchDefaultCustomer(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `default_customer_id`
                            FROM `distribution_point_warehouse`
                            WHERE `distribution_point_id` = ${params.dpId}
                          """
        List objList = sql.rows(strSql)
        return CustomerMaster.read(objList[0].default_customer_id)
    }

    @Transactional(readOnly = true)
    public List fetchInvoiceFromStock(Object params) {
        sql = new Sql(dataSource)
        String strSql = ''
        if (params.proId) {
            if(params.batchNo){
                strSql = """
                            SELECT `invoice`.`id`,`invoice`.`code`,`invoice`.`invoice_amount`,`invoice`.`date_created`,
                                (`distribution_point_stock`.`in_quantity` -
                                `distribution_point_stock`.`out_quantity`) AS received_quantity,
                                `distribution_point_stock`.`batch_no`,
                                `distribution_point_stock`.`id` AS stock_id
                            FROM `distribution_point_stock`
                            INNER JOIN `distribution_point_stock_transaction`
                                ON `distribution_point_stock_transaction`.`distribution_point_stock_id` = `distribution_point_stock`.`id`
                            INNER JOIN `invoice_details`
                                ON `invoice_details`.`batch_number` = `distribution_point_stock`.`batch_no`
                                AND `invoice_details`.`finish_product_id` = `distribution_point_stock`.`finish_product_id`
                            INNER JOIN `invoice` ON `invoice`.`id` = `invoice_details`.`invoice_id`
                            WHERE `distribution_point_stock`.`sub_warehouse_id` = ${params.subId}
                                AND `distribution_point_stock`.`finish_product_id` = ${params.proId}
                                AND `invoice`.`primary_demand_order_id` IS NOT NULL
                                AND `invoice_details`.`batch_number` = '${params.batchNo}'
                                AND `invoice`.`is_active` IS TRUE
                            GROUP BY `invoice`.`id`
                          """
            }else{
                strSql = """
                            SELECT `invoice`.`id`,`invoice`.`code`,`invoice`.`invoice_amount`,`invoice`.`date_created`,
                                (`distribution_point_stock`.`in_quantity` -
                                `distribution_point_stock`.`out_quantity`) AS received_quantity,
                                `invoice_details`.`batch_number` AS batch_no,
                                `distribution_point_stock`.`id` AS stock_id
                            FROM `distribution_point_stock`
                            INNER JOIN `distribution_point_stock_transaction`
                                ON `distribution_point_stock_transaction`.`distribution_point_stock_id` = `distribution_point_stock`.`id`
                            INNER JOIN `invoice_details`
                                ON `invoice_details`.`finish_product_id` = `distribution_point_stock`.`finish_product_id`
                                AND `invoice_details`.`finish_product_id` = `distribution_point_stock`.`finish_product_id`
                            INNER JOIN `invoice` ON `invoice`.`id` = `invoice_details`.`invoice_id`
                            WHERE `distribution_point_stock`.`sub_warehouse_id` = ${params.subId}
                                AND `distribution_point_stock`.`finish_product_id` = ${params.proId}
                                AND `invoice`.`primary_demand_order_id` IS NOT NULL
                                AND `distribution_point_stock`.`batch_no` IS NULL
                                AND `invoice`.`is_active` IS TRUE
                            GROUP BY `invoice`.`id`
                            ORDER BY invoice.`date_created`
                            LIMIT 100
                          """
            }
        } else {
            strSql = """
                            SELECT `invoice`.`id`,`invoice`.`code`,`invoice`.`invoice_amount`,`invoice`.`date_created`
                            FROM `invoice`
                            WHERE invoice.is_active = true
                                AND `default_customer_id` = ${params.id}
                          """
        }
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional
    public int updateQuantity(Object params) {
        sql = new Sql(dataSource)
        MarketReturnDetails marketReturnDetails = MarketReturnDetails.read(Long.parseLong(params.retId))
        MarketReturn marketReturn = MarketReturn.read(marketReturnDetails.marketReturn.id)
        String cond = """"""
        if(marketReturnDetails.invoice){
            cond = """AND `out_invoice_id` = ${marketReturnDetails.invoice.id}"""
        }
        String strSql = """
                    DELETE FROM `distribution_point_stock_transaction`
                    WHERE `distribution_point_stock_id` = ${params.id}
                        AND `transaction_date` = '${marketReturn.dateCreated}'
                        AND `user_created_id` = ${marketReturn.userCreated.id}
                        ${cond}
                          """
        sql.execute(strSql)
        strSql = """
                            UPDATE `distribution_point_stock`
                            SET `out_quantity` = `out_quantity` - ${params.quantity}
                            WHERE id = ${params.id}
                          """
        sql.execute(strSql)
        marketReturnDetails.delete()
        return 1
    }

    @Transactional
    public List listMarketReturn(Object params) {
        changeStatus(params)
        String mr = ''
        String date = ''
        if (params.orderNo) {
            mr = """ AND `market_return`.`mr_no` LIKE '%${params.orderNo}%'"""
        }
        if (params.dateFrom) {
            date = """ AND DATE(market_return.`date_created`)
             BETWEEN STR_TO_DATE('${params.dateFrom}','%d-%m-%Y') AND STR_TO_DATE('${params.dateTo}','%d-%m-%Y')"""
        }
        String strSql = """
                            SELECT `market_return`.id,`customer_master`.name AS customer,des.`name` AS destination,
                                `sub_warehouse`.`name` AS warehouse,src.`name` AS source,
                                `market_return`.`mr_no`,`market_return`.`mr_status`
                            FROM `market_return`
                            INNER JOIN `customer_master` ON `customer_master`.id = `market_return`.`primary_customer_id`
                            LEFT JOIN `distribution_point` des ON des.id = `market_return`.`destination_distribution_point_id`
                            LEFT JOIN `distribution_point` src ON src.id = `market_return`.`source_distribution_point_id`
                            LEFT JOIN `sub_warehouse` ON `sub_warehouse`.id = `market_return`.`sub_warehouse_id`
                            WHERE `market_return`.`user_created_id` = ${params.appUser}
                                AND (`market_return`.`mr_status` = 'MR_IN_TRANSIT'
                                OR `market_return`.`mr_status` = 'REJECTED')
                                ${mr}
                                ${date}
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional
    public void changeStatus(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            UPDATE `market_return`
                            SET `mr_status` = 'MR_IN_TRANSIT'
                            WHERE `user_created_id` = ${params.appUser}
                                AND `mr_status` = 'INITIATED'
                          """
        sql.execute(strSql)
    }

    @Transactional(readOnly = true)
    public List listReturnDetails(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `market_return_details`.`id`,`market_return_details`.`distribution_point_stock_id`,
                                `market_return_details`.`batch`,`market_return_details`.`mr_type`,`market_return_details`.`quantity`,
                                `market_return_details`.`reference`,`market_return_details`.`remarks`,`invoice`.`code` AS invoice_code,
                                `finish_product`.`name`,`finish_product`.`code` AS product_code,`market_return_details`.`invoice_id`,
                                `market_return_details`.`finish_product_id`,`market_return_details`.`price`
                            FROM `market_return_details`
                            INNER JOIN `finish_product` ON `finish_product`.id = `market_return_details`.`finish_product_id`
                            LEFT JOIN `invoice` ON `invoice`.`id` = `market_return_details`.`invoice_id`
                            WHERE `market_return_details`.`market_return_id` = ${params.id}
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List listMrNo(String orderNO, ApplicationUser applicationUser) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `market_return`.`mr_no`,`customer_master`.`name` AS customer_name,
                                src.name AS source_dp,des.name AS destination_dp
                            FROM `market_return`
                            INNER JOIN `customer_master`
                                ON `customer_master`.id = `market_return`.`primary_customer_id`
                            INNER JOIN `distribution_point` src
                                ON src.`id` = `market_return`.`source_distribution_point_id`
                            INNER JOIN `distribution_point` des
                                ON des.`id` = `market_return`.`destination_distribution_point_id`
                            WHERE `market_return`.`mr_no` LIKE '%${orderNO}%'
                                AND `market_return`.`user_created_id` = ${applicationUser.id}
                          """

        List resultList = sql.rows(strSql.toString())
        return resultList
    }

}
