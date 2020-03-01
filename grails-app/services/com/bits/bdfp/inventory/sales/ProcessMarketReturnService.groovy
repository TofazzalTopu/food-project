package com.bits.bdfp.inventory.sales

import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.inventory.warehouse.FinishGoodStock
import com.bits.bdfp.inventory.warehouse.FinishGoodStockTransaction
import com.docu.common.Service
import com.docu.commons.CommonConstants
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource


class ProcessMarketReturnService extends Service {

    static transactional = false

    DataSource dataSource
    Sql sql


    @Transactional(readOnly = true)
    public Object read(Long params) {
        return ProcessMarketReturn.read(params.id)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Object params) {
        List<ProcessMarketReturn> processMarketReturnList = []
        long processMarketReturnCount = 0
        try {
            processMarketReturnList = ProcessMarketReturn.withCriteria {
                maxResults(Integer.parseInt(params.resultPerPage.toString()))
                firstResult(Integer.parseInt(params.start.toString()))
                order(params.sortCol.toString(), params.sortOrder.toString())
            }
            processMarketReturnCount = ProcessMarketReturn.count()
            return [processMarketReturnList: processMarketReturnList, processMarketReturnCount: processMarketReturnCount]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List<ProcessMarketReturn> list() {
        return ProcessMarketReturn.list()
    }

    @Transactional
    public Object create(Object map) {
        try {
            ProcessMarketReturn processMarketReturn = map.get('processMarketReturn')
            List<ProcessMarketReturnDetails> processMarketReturnDetailsList = map.get('processMarketReturnDetailsList')
            MarketReturn marketReturn = map.get('marketReturn')
            List<FinishGoodStock> finishGoodStocks = map.get('finishGoodStocks')
            List<FinishGoodStockTransaction> finishGoodStockTransactions = map.get('finishGoodStockTransactions')
            List<DistributionPointStock> distributionPointStocks = map.get('distributionPointStocks')
            List<DistributionPointStockTransaction> distributionPointStockTransactions = map.get('distributionPointStockTransactions')
            if (processMarketReturn) {
                processMarketReturn.save(false)
                marketReturn.save()
                if (finishGoodStocks && finishGoodStocks.size() > 0) {
                    finishGoodStocks.each {
                        if (it.id) {
                            it.save()
                        } else {
                            it.save(false)
                        }
                    }
                }
                if (distributionPointStocks && distributionPointStocks.size() > 0) {
                    distributionPointStocks.each {
                        it.save()
                    }
                }
                if (distributionPointStockTransactions && distributionPointStockTransactions.size() > 0) {
                    distributionPointStockTransactions.each {
                        it.save(false)
                    }
                }
                if (processMarketReturnDetailsList && processMarketReturnDetailsList.size() > 0) {
                    processMarketReturnDetailsList.each {
                        it.save(false)
                    }
                }
                if (finishGoodStockTransactions && finishGoodStockTransactions.size() > 0) {
                    finishGoodStockTransactions.each {
                        it.save(false)
                    }
                }


                Journal journal = map.get('journalHead')
                if (journal) {
                    journal.save(validate: false, insert: true)
                }

                List<JournalDetails> journalDetailsList = map.get('journalDetailsList')
                journalDetailsList.each { journalDetails ->
                    journalDetails.save(validate: false, insert: true)
                }

                return new Integer(1)
            }
            return new Integer(0)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object processMarketReturn) {
        try {
            processMarketReturn.save()
            return 1
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }

    }

    @Transactional
    public Integer delete(Object processMarketReturn) {
        try {
            processMarketReturn.delete()
            return 1
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public ProcessMarketReturn search(String fieldName, String fieldValue) {
        String query = "from ProcessMarketReturn as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return ProcessMarketReturn.find(query)
    }

    @Transactional
    public List listMarketReturn(Object params) {
        try {
            sql = new Sql(dataSource)
            String strSql = """
                UPDATE `market_return`
                SET `mr_status` = 'MR_IN_TRANSIT'
                WHERE `mr_status` = 'MR_UNDER_REVIEW'
            """
            sql.execute(strSql)

            String mr = ''
            String date = ''
            if (params.orderNo) {
                mr = """ AND `market_return`.`mr_no` LIKE '%${params.orderNo}%'"""
            }
            if (params.dateFrom) {
                mr = """ AND DATE(market_return.`date_created`)
                 BETWEEN STR_TO_DATE('${params.dateFrom}','%d-%m-%Y') AND STR_TO_DATE('${params.dateTo}','%d-%m-%Y')"""
            }
            strSql = """
                SELECT `market_return`.id,`customer_master`.name AS customer,`market_return`.`mr_no`,
                    `market_return`.`mr_status`,DATE_FORMAT(market_return.`date_created`,'%d-%m-%Y') as date_created
                FROM `market_return`
                INNER JOIN `customer_master` ON `customer_master`.id = `market_return`.`primary_customer_id`
                WHERE `market_return`.`mr_status` = 'MR_IN_TRANSIT'
                    AND `market_return`.`destination_distribution_point_id` = ${params.dpId}
                    ${mr}
                    ${date}
            """
            return sql.rows(strSql)
        } catch (Exception ex) {
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public void changeStatus(Object params) {
        try {
            sql = new Sql(dataSource)
            String strSql = """"""
            strSql = """
                UPDATE `market_return`
                SET `mr_status` = 'MR_IN_TRANSIT'
                WHERE `mr_status` = 'MR_UNDER_REVIEW'
            """
            sql.execute(strSql)
            strSql = """
                UPDATE `market_return`
                SET `mr_status` = 'MR_UNDER_REVIEW'
                WHERE `id` = ${params.id}
            """
            sql.execute(strSql)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer rejectMr(MarketReturn marketReturn) {
        marketReturn.save()
        return new Integer(1)
    }

    @Transactional
    public List listMarketReturnDetails(Object params) {
        try {
            changeStatus(params)
            String strSql = """"""
            sql = new Sql(dataSource)
//            if(params.nonDp){
            strSql = """
                    SELECT tab.`name`,GROUP_CONCAT(tab.`mr_type`) AS mr_type,
                        GROUP_CONCAT(tab.`quantity`) AS quantity,tab.`id`,
                        tab.`code`,tab.`unit_price`
                    FROM(
                        SELECT DISTINCT `market_return_details`.`id` AS detail_id,`finish_product`.`name`,
                            `market_return_details`.`mr_type`,`market_return_details`.`quantity` AS quantity,
                            `finish_product`.`id`,`finish_product`.`code`,`market_return_details`.`price` AS unit_price
                        FROM `market_return_details`
                        INNER JOIN `finish_product` ON `market_return_details`.`finish_product_id` = `finish_product`.`id`
                        WHERE `market_return_details`.`market_return_id` = ${params.id}
                    ) AS tab
                    GROUP BY tab.`id`,tab.`unit_price`
                """
//            }else {
//                strSql = """
//                    SELECT tab.`name`,GROUP_CONCAT(tab.`mr_type`) AS mr_type,
//                        GROUP_CONCAT(tab.`quantity`) AS quantity,tab.`id`,
//                        tab.`code`,tab.`unit_price`
//                    FROM(
//                        SELECT DISTINCT `market_return_details`.`id` AS detail_id,`finish_product`.`name`,
//                            `market_return_details`.`mr_type`,`market_return_details`.`quantity` AS quantity,
//                            `finish_product`.`id`,`finish_product`.`code`,`distribution_point_stock_transaction`.`unit_price`
//                        FROM `market_return_details`
//                        INNER JOIN `finish_product` ON `market_return_details`.`finish_product_id` = `finish_product`.`id`
//                        INNER JOIN `distribution_point_stock` ON `distribution_point_stock`.`id` = `market_return_details`.`distribution_point_stock_id`
//                        INNER JOIN `distribution_point_stock_transaction`
//                            ON `distribution_point_stock_transaction`.`distribution_point_stock_id` = `distribution_point_stock`.id
//                        WHERE `market_return_details`.`market_return_id` = ${params.id}
//                    ) AS tab
//                    GROUP BY tab.`id`,tab.`unit_price`
//                """
//            }
            return sql.rows(strSql)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listDpByUser(Object params) {
        try {
            sql = new Sql(dataSource)
            String strSql = """
                SELECT distribution_point.`name`,distribution_point.`id`
                FROM `distribution_point`
                INNER JOIN `application_user_distribution_point`
                    ON `application_user_distribution_point`.`distribution_point_id` = `distribution_point`.`id`
                WHERE `enterprise_configuration_id` = ${params.entId}
                    AND `application_user_distribution_point`.`application_user_id` = ${params.userId}
                    AND `is_factory` IS TRUE
                GROUP BY distribution_point.`id`
            """
            return sql.rows(strSql)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listCustomer(Object params) {
        try {
            sql = new Sql(dataSource)
            String strSql = """
                SELECT `customer_master`.`id`,`customer_master`.`name`,`customer_master`.`code`,`customer_category`.`name` AS category
                FROM `customer_master`
                INNER JOIN `customer_category`
                    ON `customer_category`.`id` = `customer_master`.`category_id`
                INNER JOIN `customer_territory_sub_area`
                    ON `customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`
                INNER JOIN `distribution_point_territory_sub_area`
                    ON `distribution_point_territory_sub_area`.`territory_sub_area_id` = `customer_territory_sub_area`.`territory_sub_area_id`
                WHERE `distribution_point_territory_sub_area`.`distribution_point_id` = ${params.dpId}
            """
            return sql.rows(strSql)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listProcessedReturn(Object params) {
        try {
            sql = new Sql(dataSource)
            String cond = ""
            if (params.dpId) {
                cond = """ `market_return`.`source_distribution_point_id` = ${params.dpId}"""
            } else {
                cond = """ `market_return`.`primary_customer_id` = ${params.customerId}"""
            }
            if (params.dateFrom && params.dateTo) {
                cond = cond + """ AND DATE(process_market_return.`date_created`)
                 BETWEEN STR_TO_DATE('${params.dateFrom}','%d-%m-%Y') AND STR_TO_DATE('${params.dateTo}','%d-%m-%Y')"""
            }
            String strSql = """
                SELECT `process_market_return`.`id`,`customer_master`.`name`,`customer_master`.`code`,
                    `process_market_return`.`mr_no`,DATE_FORMAT(`process_market_return`.`date_created`,'%d-%m-%Y') AS process_date,
                    `process_market_return`.`qc_performing_time`,`process_market_return`.`qc_person_name`,`process_market_return`.`qc_person_pin`,
                    `market_return`.`mr_status`
                FROM `process_market_return`
                INNER JOIN `market_return` ON `market_return`.`mr_no` = `process_market_return`.`mr_no`
                INNER JOIN `customer_master` ON `customer_master`.`id` = `market_return`.`primary_customer_id`
                WHERE ${cond}
            """
            return sql.rows(strSql)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listProcessedReturnDetails(Object params) {
        try {
            sql = new Sql(dataSource)
            String strSql = """
                SELECT `process_market_return_details`.`finish_product_id` AS pid,`finish_product`.`name`,
                    `finish_product`.`code`,SUM(`process_market_return_details`.`qc_quantity`) AS total,
                    `process_market_return_details`.`rate`,
                    (SELECT CONCAT((`qc_quantity` + `qc_failed_quantity`),',',`qc_quantity`)
                        FROM `process_market_return_details`
                        WHERE `finish_product_id` = pid
                        AND `mr_type` = 'Leak Pack'
                        AND `process_market_return_id` = ${params.id}) AS leak,
                    (SELECT CONCAT((`qc_quantity` + `qc_failed_quantity`),',',`qc_quantity`)
                        FROM `process_market_return_details`
                        WHERE `finish_product_id` = pid
                        AND `mr_type` = 'Market Return'
                        AND `process_market_return_id` = ${params.id}) AS mr,
                    (SELECT CONCAT((`qc_quantity` + `qc_failed_quantity`),',',`qc_quantity`)
                        FROM `process_market_return_details`
                        WHERE `finish_product_id` = pid
                        AND `mr_type` = 'Damage'
                        AND `process_market_return_id` = ${params.id}) AS damage,
                    (SELECT CONCAT((`qc_quantity` + `qc_failed_quantity`),',',`qc_quantity`)
                        FROM `process_market_return_details`
                        WHERE `finish_product_id` = pid
                        AND `mr_type` = 'Short Pack'
                        AND `process_market_return_id` = ${params.id}) AS short,
                    (SELECT CONCAT((`qc_quantity` + `qc_failed_quantity`),',',`qc_quantity`)
                        FROM `process_market_return_details`
                        WHERE `finish_product_id` = pid
                        AND `mr_type` = 'Short Supply from Challan'
                        AND `process_market_return_id` = ${params.id}) AS short_challan
                FROM `process_market_return_details`
                    INNER JOIN `finish_product` ON `finish_product`.id = `process_market_return_details`.`finish_product_id`
                WHERE `process_market_return_details`.`process_market_return_id` = ${params.id}
                GROUP BY pid
            """
            return sql.rows(strSql)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listSalesMan(Object params) {
        try {
            sql = new Sql(dataSource)
            String strSql = """
                SELECT `customer_master`.`id`,`customer_master`.`name`,`customer_master`.`code`,`customer_category`.`name` AS category
                FROM `customer_master`
                INNER JOIN `customer_category`
                    ON `customer_category`.`id` = `customer_master`.`category_id`
                INNER JOIN `customer_territory_sub_area`
                    ON `customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`
                INNER JOIN `distribution_point_territory_sub_area`
                    ON `distribution_point_territory_sub_area`.`territory_sub_area_id` = `customer_territory_sub_area`.`territory_sub_area_id`
                WHERE `distribution_point_territory_sub_area`.`distribution_point_id` = ${params.dpId}
                    AND `customer_master`.`category_id` = 3
                GROUP BY `customer_master`.`id`
            """
            return sql.rows(strSql)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public FinishGoodStock listFinishGoodTransaction(Object params, Long id) {
        try {
            sql = new Sql(dataSource)
            String strSql = """
                SELECT `finish_good_stock`.`id`
                FROM `finish_good_stock`
                WHERE `sub_warehouse_id` = ${CommonConstants.FACTORY_MARKET_RETURN_SUB_WAREHOUSE_ID}
                    AND `finish_product_id` = ${id}
                    AND `batch_no` IS NULL
            """
            List objList = sql.rows(strSql)
            if (objList)
                return FinishGoodStock.read(objList[0].id)
            else
                return null
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List fetchDpByUser(Object params) {
        try {
            sql = new Sql(dataSource)
            String strSql = """
                SELECT `distribution_point`.`id`,`distribution_point`.`name`,`distribution_point`.`is_factory`
                FROM `distribution_point`
                INNER JOIN `application_user_distribution_point`
                    ON `application_user_distribution_point`.`distribution_point_id` = `distribution_point`.`id`
                WHERE `application_user_distribution_point`.`application_user_id` = ${params.id}
            """
            return sql.rows(strSql)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List fetchNonFactoryDp() {
        try {
            sql = new Sql(dataSource)
            String strSql = """
                SELECT `distribution_point`.`id`,`distribution_point`.`name`
                FROM `distribution_point`
                WHERE `distribution_point`.`is_factory` IS FALSE
            """
            return sql.rows(strSql)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listCustomerByGeoOfUser(Object params) {
        try {
            sql = new Sql(dataSource)
            String strSql = """
                SELECT `customer_master`.`id`,`customer_master`.`name`,`customer_master`.`code`,`customer_category`.`name` AS category
                FROM `customer_master`
                INNER JOIN `customer_category`
                    ON `customer_category`.`id` = `customer_master`.`category_id`
                INNER JOIN `customer_territory_sub_area`
                    ON `customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`
                WHERE `customer_territory_sub_area`.`territory_sub_area_id` IN (
                    SELECT `customer_territory_sub_area`.`territory_sub_area_id`
                    FROM `customer_territory_sub_area`
                    INNER JOIN `application_user`
                        ON `application_user`.`customer_master_id` = `customer_territory_sub_area`.`customer_master_id`
                    WHERE `application_user`.`id` = ${params.appId}
                    )
                    AND `customer_master`.`customer_level` = 'PRIMARY'
            """
            return sql.rows(strSql)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listCustomerByUser() {
        try {
            sql = new Sql(dataSource)
            String strSql = """
                SELECT `customer_master`.`id`,`customer_master`.`name`,`customer_master`.`code`,`customer_category`.`name` AS category
                FROM `customer_master`
                INNER JOIN `customer_category`
                    ON `customer_category`.`id` = `customer_master`.`category_id`
                WHERE `customer_master`.`customer_level` = 'PRIMARY'
            """
            return sql.rows(strSql)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listProcessedAndRejectedReturn(Object params) {
        sql = new Sql(dataSource)
        String cond = ""
        if (params.dateFrom && params.dateTo) {
            cond = cond + """ AND DATE(market_return.`date_created`)
             BETWEEN STR_TO_DATE('${params.dateFrom}','%d-%m-%Y') AND STR_TO_DATE('${params.dateTo}','%d-%m-%Y')"""
        }
        String strSql = """
                            SELECT (CASE WHEN (`process_market_return`.`id`) THEN `process_market_return`.`id`
                                ELSE `market_return`.`id` END) AS id,`customer_master`.`name`,`customer_master`.`code`,
                                `market_return`.`mr_no`,(CASE WHEN (`market_return`.`mr_status` = 'REJECTED') THEN
                                (DATE_FORMAT(`market_return`.`date_updated`,'%d-%m-%Y'))
                                ELSE (DATE_FORMAT(`process_market_return`.`date_created`,'%d-%m-%Y')) END) AS process_date,
                                `process_market_return`.`qc_performing_time`,`process_market_return`.`qc_person_name`,
                                `process_market_return`.`qc_person_pin`,`market_return`.`mr_status`
                            FROM `market_return`
                            INNER JOIN `customer_master` ON `customer_master`.`id` = `market_return`.`primary_customer_id`
                            LEFT JOIN `process_market_return` ON `process_market_return`.`mr_no` = `market_return`.`mr_no`
                            WHERE (`market_return`.`mr_status` = 'PROCESSED' OR `market_return`.`mr_status` = 'REJECTED')
                                AND `market_return`.`source_distribution_point_id` = ${params.dpId}
                                ${cond}
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List listRejectedReturnDetails(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `market_return_details`.`finish_product_id` AS pid,`finish_product`.`name`,
                                `finish_product`.`code`,(0) AS total,
                                `market_return_details`.`price` AS rate,
                                (SELECT CONCAT(`quantity`,',0')
                                FROM `market_return_details`
                                WHERE `finish_product_id` = pid
                                AND `mr_type` = 'Leak Pack'
                                AND `market_return_id` = ${params.rejectId}) AS leak,
                                (SELECT CONCAT(`quantity`,',0')
                                FROM `market_return_details`
                                WHERE `finish_product_id` = pid
                                AND `mr_type` = 'Market Return'
                                AND `market_return_id` = ${params.rejectId}) AS mr,
                                (SELECT CONCAT(`quantity`,',0')
                                FROM `market_return_details`
                                WHERE `finish_product_id` = pid
                                AND `mr_type` = 'Damage'
                                AND `market_return_id` = ${params.rejectId}) AS damage,
                                (SELECT CONCAT(`quantity`,',0')
                                FROM `market_return_details`
                                WHERE `finish_product_id` = pid
                                AND `mr_type` = 'Short Pack'
                                AND `market_return_id` = ${params.rejectId}) AS short,
                                (SELECT CONCAT(`quantity`,',0')
                                FROM `market_return_details`
                                WHERE `finish_product_id` = pid
                                AND `mr_type` = 'Short Supply from Challan'
                                AND `market_return_id` = ${params.rejectId}) AS short_challan
                            FROM `market_return_details`
                            INNER JOIN `finish_product` ON `finish_product`.id = `market_return_details`.`finish_product_id`
                            WHERE `market_return_details`.`market_return_id` = ${params.rejectId}
                            GROUP BY pid
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List dpMrStock(Object params, Long id) {
        try {
            sql = new Sql(dataSource)
            String strSql = """
                SELECT `distribution_point_stock`.`id`
                FROM `distribution_point_stock`
                WHERE `sub_warehouse_id` = ${params.dpInvId}
                    AND `finish_product_id` = ${id}
            """
//            AND `batch_no` IS NULL
            List objList = sql.rows(strSql)
            return objList
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
