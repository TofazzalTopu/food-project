package com.bits.bdfp.inventory.sales

import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Service
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional
import com.docu.commons.CommonConstants

import javax.sql.DataSource


class ReceiveProductsFromMarketService extends Service {
    static transactional = false

    DataSource dataSource
    Sql sql

    @Transactional(readOnly = true)
    public Object read(Long id) {
        return ReceiveProductsFromMarket.read(id)
    }

    @Transactional(readOnly = true)
    public List list() {
        List<ReceiveProductsFromMarket> receiveProductsFromMarketList = []
        long receiveProductsFromMarketCount = 0
        try {
            receiveProductsFromMarketList = ReceiveProductsFromMarket.withCriteria {
                maxResults(Integer.parseInt(params.resultPerPage.toString()))
                firstResult(Integer.parseInt(params.start.toString()))
                order(params.sortCol.toString(), params.sortOrder.toString())
            }
            receiveProductsFromMarketCount = ReceiveProductsFromMarket.count()
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return receiveProductsFromMarketList
    }

    @Transactional
    public Object create(Object map) {
        try{
            ReceiveProductsFromMarket receiveProductsFromMarket = map.receiveProductsFromMarket
            List<ReceiveProductsFromMarketDetails> receiveProductsFromMarketDetailsList = map.receiveProductsFromMarketDetailsList
            List<DistributionPointStock> distributionPointStocks = map.distributionPointStocks
            List<DistributionPointStockTransaction> distributionPointStockTransactions = map.distributionPointStockTransactions
            if (receiveProductsFromMarket) {
                receiveProductsFromMarket.save(false)
                if (distributionPointStocks && distributionPointStocks.size() > 0) {
                    distributionPointStocks.each {
                        if(it.id){
                            it.save()
                        }else {
                            it.save(false)
                        }
                    }
                }
                if (receiveProductsFromMarketDetailsList && receiveProductsFromMarketDetailsList.size() > 0) {
                    receiveProductsFromMarketDetailsList.each {
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
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object receiveProductsFromMarket) {
        try {
            receiveProductsFromMarket.save()
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return 1
    }

    @Transactional
    public Integer delete(Object receiveProductsFromMarket) {
        try {
            receiveProductsFromMarket.delete()
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return 1
    }

    @Transactional(readOnly = true)
    public ReceiveProductsFromMarket search(String fieldName, String fieldValue) {
        String query = "from ReceiveProductsFromMarket as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return ReceiveProductsFromMarket.find(query)
    }

    @Transactional(readOnly = true)
    public List listDpForUser(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT distribution_point.`name`,distribution_point.`id`
                            FROM `distribution_point`
                            INNER JOIN `application_user_distribution_point`
                                ON `application_user_distribution_point`.`distribution_point_id` = `distribution_point`.`id`
                            WHERE `enterprise_configuration_id` = ${params.entId}
                                AND `application_user_distribution_point`.`application_user_id` = ${params.userId}
                                AND `is_factory` IS FALSE
                            GROUP BY distribution_point.`id`
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List listWareHouse(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `warehouse`.`name`, `warehouse`.`id`
                            FROM `warehouse`
                            INNER JOIN `distribution_point_warehouse`
                                ON `distribution_point_warehouse`.`warehouse_id` = `warehouse`.`id`
                            WHERE `distribution_point_warehouse`.`distribution_point_id` = ${params.dpId}
                            GROUP BY warehouse.`id`
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List listSubWareHouse(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `sub_warehouse`.`name`,`sub_warehouse`.`id`
                            FROM `sub_warehouse`
                            WHERE `sub_warehouse`.`warehouse_id` = ${params.invId}
                                AND `sub_warehouse_type_id` = ${ApplicationConstants.MARKET_RETURN_TYPE_INVENTORY_ID}
                            GROUP BY sub_warehouse.`id`
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List listProduct(Object params) {
        sql = new Sql(dataSource)
        String strSql = ""
        if (params.invoice) {
            strSql = """
                            SELECT `finish_product`.`name`,`finish_product`.`id`,`finish_product`.`code`,
                                `invoice_details`.`batch_number`,`invoice_details`.`unit_price`,`invoice_details`.`quantity`
                                AS received_quantity
                            FROM `finish_product`
                            INNER JOIN `invoice_details` ON `finish_product`.id = `invoice_details`.`finish_product_id`
                            INNER JOIN `invoice` ON `invoice`.`id` = `invoice_details`.`invoice_id`
                            WHERE invoice.is_active = true
                                AND `invoice`.id = ${params.invoice}
                            GROUP BY `invoice_details`.`batch_number`,`finish_product`.`id`
                            ORDER BY `invoice_details`.`batch_number` DESC
                          """
        } else if (params.batched) {
//            strSql = """
//                            SELECT `finish_product`.`name`,`finish_product`.`code`,
//                                `receive_products_from_market_details`.`finish_product_id` AS id,
//                                'Select Invoice' AS received_quantity
//                            FROM `receive_products_from_market`
//                            INNER JOIN `receive_products_from_market_details`
//                                ON `receive_products_from_market_details`.`receive_products_from_market_id` = `receive_products_from_market`.`id`
//                            INNER JOIN `finish_product`
//                                ON `finish_product`.`id` = `receive_products_from_market_details`.`finish_product_id`
//                            INNER JOIN `distribution_point_stock`
//                                ON `distribution_point_stock`.`id` = `receive_products_from_market_details`.`distribution_point_stock_id`
//                            WHERE `receive_products_from_market`.`receiving_dp_id` = ${params.dpId}
//                                AND `distribution_point_stock`.`batch_no` IS NOT NULL
//                                AND `distribution_point_stock`.`in_quantity` - `distribution_point_stock`.`out_quantity` > 0
//                            GROUP BY `finish_product`.id
//                          """
//        } else if (params.batched == "false") {
            strSql = """
                            SELECT `finish_product`.`name`,`finish_product`.`code`,
                                `receive_products_from_market_details`.`finish_product_id` AS id,
                                `distribution_point_stock`.`id` AS stock_id,`distribution_point_stock`.`batch_no` AS batch_number,
                                (`distribution_point_stock`.`in_quantity` -
                                `distribution_point_stock`.`out_quantity`) AS received_quantity,
                                (SELECT `unit_price`
                                FROM `invoice_details`
                                INNER JOIN `invoice` ON `invoice`.`id` = `invoice_details`.`invoice_id`
                                INNER JOIN `distribution_point_warehouse`
                                    ON `distribution_point_warehouse`.`default_customer_id` = `invoice`.`default_customer_id`
                                WHERE  invoice.is_active = true
                                    AND `invoice_details`.`finish_product_id` = `receive_products_from_market_details`.`finish_product_id`
                                ORDER BY `invoice`.`date_created` DESC
                                LIMIT 1) AS unit_price
                            FROM `receive_products_from_market`
                            INNER JOIN `receive_products_from_market_details`
                                ON `receive_products_from_market_details`.`receive_products_from_market_id` = `receive_products_from_market`.`id`
                            INNER JOIN `finish_product`
                                ON `finish_product`.`id` = `receive_products_from_market_details`.`finish_product_id`
                            INNER JOIN `distribution_point_stock`
                                ON `distribution_point_stock`.`id` = `receive_products_from_market_details`.`distribution_point_stock_id`
                            WHERE `receive_products_from_market`.`receiving_dp_id` = ${params.dpId}
                                AND `distribution_point_stock`.`in_quantity` - `distribution_point_stock`.`out_quantity` > 0
                            GROUP BY stock_id
                            ORDER BY batch_number DESC
                          """
        } else {
            strSql = """
                            SELECT `finish_product`.`name`,`finish_product`.`id`,`finish_product`.`code`
                            FROM `finish_product`
                            WHERE `finish_product`.`enterprise_configuration_id` = ${params.entId}
                            GROUP BY finish_product.`id`
                          """
        }
        List objList = sql.rows(strSql)
        return objList
    }

/*
    strSql = """
                            SELECT `finish_product`.`name`,`finish_product`.`code`,
                                `receive_products_from_market_details`.`finish_product_id` AS id,
                                `distribution_point_stock`.`id` AS stock_id,`distribution_point_stock`.`batch_no` AS batch_number,
                                (`distribution_point_stock`.`in_quantity` -
                                `distribution_point_stock`.`out_quantity`) AS received_quantity,
                                (SELECT `unit_price`
                                FROM `invoice_details`
                                INNER JOIN `invoice` ON `invoice`.`id` = `invoice_details`.`invoice_id`
                                INNER JOIN `distribution_point_warehouse`
                                    ON `distribution_point_warehouse`.`default_customer_id` = `invoice`.`default_customer_id`
                                INNER JOIN `application_user`
                                    ON `application_user`.`customer_master_id` = `distribution_point_warehouse`.`in_charge_id`
                                WHERE  invoice.is_active = true
                                    AND `invoice_details`.`finish_product_id` = `receive_products_from_market_details`.`finish_product_id`
                                    AND `application_user`.`id` = ${params.appId}
                                ORDER BY `invoice`.`date_created` DESC
                                LIMIT 1) AS unit_price
                            FROM `receive_products_from_market`
                            INNER JOIN `receive_products_from_market_details`
                                ON `receive_products_from_market_details`.`receive_products_from_market_id` = `receive_products_from_market`.`id`
                            INNER JOIN `finish_product`
                                ON `finish_product`.`id` = `receive_products_from_market_details`.`finish_product_id`
                            INNER JOIN `distribution_point_stock`
                                ON `distribution_point_stock`.`id` = `receive_products_from_market_details`.`distribution_point_stock_id`
                            WHERE `receive_products_from_market`.`receiving_dp_id` = ${params.dpId}
                                AND `distribution_point_stock`.`in_quantity` - `distribution_point_stock`.`out_quantity` > 0
                            GROUP BY stock_id
                            ORDER BY batch_number DESC
                          """
    */
    @Transactional(readOnly = true)
    public Object listInvoice(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `id`,`code`,`invoice_amount`,`date_created`
                            FROM invoice
                            WHERE invoice.is_active = true
                                AND `default_customer_id` =  ${params.id}
                                AND `primary_demand_order_id` IS NULL
                            GROUP BY `id`
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public Object listBatch(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT DISTINCT finish_good_batch_stock.`batch_no`
                            FROM `finish_good_batch_stock`
                            INNER JOIN `finish_product` ON `finish_product`.id = `finish_good_batch_stock`.`finish_product_id`
                            WHERE `finish_product`.`enterprise_configuration_id` = ${params.entId}
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public Object checkIntegrity(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `invoice_details`.`quantity`
                            FROM `invoice_details`
                            INNER JOIN `invoice` ON `invoice`.id = `invoice_details`.`invoice_id`
                            WHERE `invoice_details`.`batch_number` = '${params.batch}'
                                AND `invoice_details`.`finish_product_id` = ${params.productId}
                                AND `invoice`.`id` = ${params.invoice}
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List listInvoiceAndPrice(Long invoice, Long product, Long customer, String batch) {
        sql = new Sql(dataSource)
        String cond = ""
        String limit = "LIMIT 1"
        if (invoice) {
            cond = """
                    AND `invoice`.`id` = ${invoice}
                    AND `invoice_details`.`batch_number` = '${batch}'
                   """
            limit = ""
        } else {
            cond = """ AND invoice.`default_customer_id` = ${customer}"""
        }
        String strSql = """
                            SELECT `invoice_details`.`unit_price`,`invoice`.`date_created`
                            FROM `invoice_details`
                            INNER JOIN `invoice` ON `invoice`.`id` = `invoice_details`.`invoice_id`
                            WHERE invoice.is_active = true
                                AND `invoice_details`.`finish_product_id` = ${product}
                                ${cond}
                            ORDER BY `invoice`.`date_created` DESC
                            ${limit}
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public DistributionPointStock listDistributionTransaction(Object params, ReceiveProductsFromMarketDetails receiveProductsFromMarketDetails) {
        sql = new Sql(dataSource)
        String cond = ""
        String limit = "LIMIT 1"
        if (receiveProductsFromMarketDetails.batchAvailable) {
            cond = """
                    AND `batch_no` = '${receiveProductsFromMarketDetails.batch}'
                   """
        } else {
            cond = """
                    AND `batch_no` IS NULL
                   """
        }
        String strSql = """
                            SELECT `id`
                            FROM `distribution_point_stock`
                            WHERE `sub_warehouse_id` = ${params.receivingSubInventory.id}
                                AND `finish_product_id` = ${receiveProductsFromMarketDetails.finishProduct.id}
                                ${cond}
                          """
        List objList = sql.rows(strSql)
        if(objList)
            return DistributionPointStock.read(objList[0].id)
        else
            return null
    }

    @Transactional(readOnly = true)
    public Object listWarehouse(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `warehouse`.`id`,`warehouse`.`name`
                            FROM `warehouse`
                            INNER JOIN `distribution_point_warehouse`
                                ON `distribution_point_warehouse`.`warehouse_id` = `warehouse`.`id`
                            WHERE `distribution_point_warehouse`.`distribution_point_id` = ${params.dpId}
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public Object listSubWarehouse(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `id`,`name`
                            FROM `sub_warehouse`
                            WHERE `warehouse_id` = ${params.id}
                                AND `sub_warehouse_type_id` = ${ApplicationConstants.MARKET_RETURN_TYPE_INVENTORY_ID}
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List listSubWareHouse(Long id) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `sub_warehouse`.`name`,`sub_warehouse`.`id`
                            FROM `sub_warehouse`
                            INNER JOIN `distribution_point_warehouse`
                                ON `distribution_point_warehouse`.`warehouse_id` = `sub_warehouse`.`warehouse_id`
                            WHERE `distribution_point_warehouse`.`distribution_point_id` = ${id}
                                AND `sub_warehouse_type_id` = ${ApplicationConstants.MARKET_RETURN_TYPE_INVENTORY_ID}
                            GROUP BY sub_warehouse.`id`
                          """
        List objList = sql.rows(strSql)
        return objList
    }

}
