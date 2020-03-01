package com.bits.bdfp.bonus

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.SubLedger
import com.bits.bdfp.customer.CustomerLevel
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.util.ApplicationConstants
import com.docu.commons.CommonConstants
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import groovy.sql.Sql
import javax.sql.DataSource
import com.docu.common.Action

import java.text.SimpleDateFormat

class OnePercentBonusService extends Service {
    static transactional = false

    Sql sql
    DataSource dataSource

    @Transactional(readOnly = true)
    public OnePercentBonus read(Long id) {
        return OnePercentBonus.read(id)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Map params) {
        try {
            List<OnePercentBonus> onePercentBonusList = OnePercentBonus.withCriteria {
                maxResults(Integer.parseInt(action.resultPerPage.toString()))
                firstResult(Integer.parseInt(action.start.toString()))
                order(action.sortCol.toString(), action.sortOrder.toString())
            }
            long onePercentBonusCount = OnePercentBonus.count()
            return [objList: onePercentBonusList, count: onePercentBonusCount]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List<OnePercentBonus> list() {
        return OnePercentBonus.list()
    }

    @Transactional
    public Object create(Object object) {
        try {
            List<OnePercentBonus> onePercentBonusList = object.onePercentBonusList
            List<OnePercentBonus> onePercentBonusUpdateList = object.onePercentBonusUpdateList

            onePercentBonusList.each {
                it.save(false)
            }

            onePercentBonusUpdateList.each {
                it.save()
            }

            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object object) {
        try {
            OnePercentBonus onePercentBonus = (OnePercentBonus) object
            if (onePercentBonus.save(vaidate: false)) {
                return new Integer(1)
            } else {
                return new Integer(0)
            }
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        try {
            OnePercentBonus onePercentBonus = (OnePercentBonus) object
            onePercentBonus.delete()
            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public OnePercentBonus search(String fieldName, String fieldValue) {
        String query = "from OnePercentBonus as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return OnePercentBonus.find(query)
    }

    @Transactional(readOnly = true)
    public List listTerritory(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                SELECT id,`name`
                FROM `territory_configuration`
                WHERE `enterprise_configuration_id` = ${params.entId}
                ORDER BY `name`
              """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List listProduct(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                SELECT `finish_product`.`name`,`finish_product`.`id`,
                    (CASE WHEN(`one_percent_bonus`.`bonus_percent_id` = ${params.bonus})
                    THEN 'true'
                    ELSE '' END) AS checked
                FROM `finish_product`
                LEFT JOIN `one_percent_bonus` ON `one_percent_bonus`.`finish_product_id` = `finish_product`.`id`
                    AND `one_percent_bonus`.`bonus_percent_id` = ${params.bonus}
                WHERE `finish_product`.`enterprise_configuration_id` = ${params.entId}
                GROUP BY `finish_product`.`id`
                ORDER BY `id`
              """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List listCategory(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                SELECT id,`name`
                FROM `customer_category`
                WHERE `enterprise_configuration_id` = ${params.entId}
                ORDER BY `id`
              """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List listGeoLocation(Object params) {
        sql = new Sql(dataSource)
        String[] ids = params.territory.split(',')
        String cond = """ territory_configuration_id = ${ids[0]}"""
        for (int i = 1; i < ids.length; i++) {
            if (ids[i]) {
                cond = cond + """ OR territory_configuration_id = ${ids[i]}"""
            }
        }
        String strSql = """
                SELECT id,`geo_location`
                FROM `territory_sub_area`
                WHERE ${cond}
                ORDER BY `geo_location`
              """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List listCustomer(Object params) {
        sql = new Sql(dataSource)
        String[] cat = params.cat.split(',')
        String[] geo = params.geo.split(',')
        String cond = """"""
        String cond2 = """"""
        if (cat[0] != '') {
            cond = """ customer_master.`category_id` = ${cat[0]}"""
            for (int i = 1; i < cat.length; i++) {
                if (cat[i]) {
                    cond = cond + """ OR customer_master.`category_id` = ${cat[i]}"""
                }
            }
        } else {
            cond = """ customer_master.`category_id` = 0"""
        }
        if (geo[0] != '') {
            cond2 = """ `customer_territory_sub_area`.`territory_sub_area_id` = ${geo[0]}"""
            for (int i = 1; i < geo.length; i++) {
                if (geo[i]) {
                    cond2 = cond2 + """ OR `customer_territory_sub_area`.`territory_sub_area_id` = ${geo[i]}"""
                }
            }
        } else {
            cond2 = """ `customer_territory_sub_area`.`territory_sub_area_id` = 0"""
        }
        String strSql = """
                SELECT DISTINCT customer_master.id AS cid,customer_master.`name`,
                    (CASE WHEN(`one_percent_bonus`.`bonus_percent_id` = ${params.bonus})
                    THEN 'true'
                    ELSE '' END) AS checked,
                    customer_category.`name` AS category,(
                    SELECT GROUP_CONCAT(' ',`territory_sub_area`.`geo_location`)
                    FROM `territory_sub_area`
                    INNER JOIN `customer_territory_sub_area`
                        ON `customer_territory_sub_area`.`territory_sub_area_id` = `territory_sub_area`.`id`
                    WHERE `customer_territory_sub_area`.`customer_master_id` = cid) AS geo_location
                FROM `customer_master`
                INNER JOIN `customer_territory_sub_area`
                    ON `customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`
                INNER JOIN `territory_sub_area`
                    ON `territory_sub_area`.id = `customer_territory_sub_area`.`territory_sub_area_id`
                INNER JOIN `customer_category`
                    ON `customer_master`.`category_id` = `customer_category`.`id`
                LEFT JOIN `one_percent_bonus`
                    ON `one_percent_bonus`.`customer_master_id` = `customer_master`.`id`
                    AND `one_percent_bonus`.`bonus_percent_id` = ${params.bonus}
                WHERE (${cond})
                    AND(${cond2})
                GROUP BY `customer_master`.`id`
              """
//        SELECT DISTINCT customer_master.id,customer_master.`name`,
//        customer_category.`name` AS category,GROUP_CONCAT(' ',`territory_sub_area`.`geo_location`) AS geo_location
//        FROM `customer_master`
//        INNER JOIN `customer_territory_sub_area`
//        ON `customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`
//        INNER JOIN `territory_sub_area`
//        ON `territory_sub_area`.id = `customer_territory_sub_area`.`territory_sub_area_id`
//        INNER JOIN `customer_category`
//        ON `customer_master`.`category_id` = `customer_category`.`id`

        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List fetchBonus(String customer, String product) {
        sql = new Sql(dataSource)
        String strSql = """
                SELECT id
                FROM `one_percent_bonus`
                WHERE `customer_master_id` = ${customer}
                    AND `finish_product_id` = ${product}
              """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List listGeoLocationByDp(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                SELECT `territory_sub_area`.id,`territory_sub_area`.`geo_location`
                FROM `territory_sub_area`
                INNER JOIN `distribution_point_territory_sub_area`
                    ON `distribution_point_territory_sub_area`.`territory_sub_area_id` = `territory_sub_area`.`id`
                INNER JOIN `application_user_distribution_point`
                    ON `application_user_distribution_point`.`distribution_point_id` = `distribution_point_territory_sub_area`.`distribution_point_id`
                WHERE `application_user_distribution_point`.`application_user_id` = ${params.userId}
                ORDER BY `territory_sub_area`.`geo_location`
              """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List listTotalSaleForSalesman(Object params) {
        try{
            sql = new Sql(dataSource)
            String[] geo = params.geo.split(',')
            String cond = ""
            String cond2 = ""
            if (geo[0] != '') {
                cond = """ AND (${geo[0]} IN (SELECT `territory_sub_area_id`
                            FROM `customer_territory_sub_area` WHERE `customer_master_id` = `customer_master`.`id`)"""
                for (int i = 1; i < geo.length; i++) {
                    if (geo[i]) {
                        cond = cond + """ OR ${geo[i]} IN (SELECT `territory_sub_area_id`
                                FROM `customer_territory_sub_area` WHERE `customer_master_id` = `customer_master`.`id`)"""
                    }
                }
                cond = cond + """)"""
            }
            if (params.cat) {
                cond2 = """ AND `customer_master`.`category_id` = ${params.cat}"""
            } else {
                cond2 = """ AND `customer_master`.`category_id` = ${ApplicationConstants.CUSTOMER_CATEGORY_SALES_MAN_ID}"""
            }

    //        String strSql = """
    //                            SELECT `customer_master`.`id`,`customer_master`.`name`,`customer_master`.`code`,
    //                                ROUND (SUM(`invoice_details`.`quantity`*`invoice_details`.`unit_price`),4) AS total_sales,
    //                                (SELECT ROUND (SUM(`distribution_point_stock_transaction`.`unit_price`*`market_return_details`.`quantity`),4)
    //                                FROM `market_return_details`
    //                                INNER JOIN `market_return`
    //                                    ON `market_return`.`id` = `market_return_details`.`market_return_id`
    //                                INNER JOIN `one_percent_bonus`
    //                                    ON `one_percent_bonus`.`finish_product_id` = `market_return_details`.`finish_product_id`
    //                                    AND `one_percent_bonus`.`customer_master_id` = `market_return`.`secondary_customer_id`
    //                                INNER JOIN `distribution_point_stock`
    //                                    ON `distribution_point_stock`.`id` = `market_return_details`.`distribution_point_stock_id`
    //                                INNER JOIN `distribution_point_stock_transaction`
    //                                    ON `distribution_point_stock_transaction`.`distribution_point_stock_id` = `distribution_point_stock`.`id`
    //                                WHERE DATE(`market_return`.`date_created`) BETWEEN DATE(`one_percent_bonus`.`last_calculated`) AND STR_TO_DATE('${
    //            params.date
    //        }','%d-%m-%Y')
    //                                AND `market_return`.`secondary_customer_id` = `customer_master`.`id`
    //                            ) AS total_return
    //                            FROM `invoice_details`
    //                            INNER JOIN `invoice`
    //                                ON `invoice_details`.`invoice_id` = `invoice`.`id`
    //                            INNER JOIN `customer_master`
    //                                ON invoice.`default_customer_id` = `customer_master`.`id`
    //                            INNER JOIN `one_percent_bonus`
    //                                ON `one_percent_bonus`.`finish_product_id` = `invoice_details`.`finish_product_id`
    //                                AND `one_percent_bonus`.`customer_master_id` = `invoice`.`default_customer_id`
    //                            ${join}
    //
    //                                ${cond}
    //                                ${cond2}
    //                            GROUP BY `customer_master`.`id`
    //                            ORDER BY `customer_master`.`id`
    //                          """

            //************************************Original and accurate, comments for test purpose, have to remove comment**************************////////
    //        String strSql = """
    //             SELECT `customer_master`.`id` AS cid,`customer_master`.`name`,`customer_master`.`code` AS cmCode,
    //            `invoice_details`.`finish_product_id` AS pid,`invoice`.`date_created` AS dc,`bonus_percent`.`percentage`,
    //            ROUND (SUM(`invoice_details`.`quantity`*(
    //            SELECT `product_price_product`.`total_amount`
    //            FROM `product_price_product`
    //            INNER JOIN `product_price`
    //                ON `product_price`.`id` = `product_price_product`.`product_price_id`
    //            INNER JOIN `territory_sub_area_product_price`
    //                ON `territory_sub_area_product_price`.`product_price_id` = `product_price_product`.`product_price_id`
    //            INNER JOIN `customer_territory_sub_area`
    //                ON `customer_territory_sub_area`.`territory_sub_area_id` = `territory_sub_area_product_price`.`territory_sub_area_id`
    //            WHERE `customer_territory_sub_area`.`customer_master_id` = cid
    //                AND `product_price_product`.`pricing_category_id` = ${ApplicationConstants.PRICING_CATEGORY_DP_ID}
    //                AND `product_price_product`.`finish_product_id` = pid
    //                AND DATE(dc)
    //                BETWEEN DATE(`product_price`.`date_effective_from`)
    //                AND DATE(`product_price`.`date_effective_to`)
    //                AND `product_price_product`.`is_active` IS TRUE
    //            ORDER BY `product_price_product`.`date_created` DESC
    //            LIMIT 1
    //            )),2) AS total_sales,
    //            (SELECT ROUND (SUM(jd.debit_amount) - SUM(jd.credit_amount),2)
    //            FROM chart_of_accounts_mapping coam
    //            INNER JOIN chart_of_accounts coa
    //                ON coa.id = coam.chart_of_accounts_id
    //            INNER JOIN journal_details jd
    //                ON coa.id = jd.chart_of_accounts_id
    //            WHERE coam.coa_type = 'ACCOUNTS_RECEIVABLE'
    //                 AND jd.prefix_code = cmCode) AS total_receivable
    //            FROM `invoice_details`
    //            INNER JOIN `invoice`
    //                ON `invoice_details`.`invoice_id` = `invoice`.`id`
    //            INNER JOIN `customer_master`
    //                ON invoice.`default_customer_id` = `customer_master`.`id`
    //            INNER JOIN `one_percent_bonus`
    //                ON `one_percent_bonus`.`finish_product_id` = `invoice_details`.`finish_product_id`
    //                AND `one_percent_bonus`.`customer_master_id` = `invoice`.`default_customer_id`
    //            INNER JOIN `bonus_percent`
    //                ON `bonus_percent`.`id` = `one_percent_bonus`.`bonus_percent_id`
    //            ${join}
    //            WHERE invoice.is_active = true
    //                AND DATE(`invoice`.`date_created`)
    //                BETWEEN (
    //                    CASE WHEN(`one_percent_bonus`.`version` = 0)
    //                    THEN DATE(`one_percent_bonus`.`last_calculated`)
    //                    ELSE DATE(DATE_ADD(`one_percent_bonus`.`last_calculated`, INTERVAL 1 DAY)) END)
    //                AND STR_TO_DATE('${params.date}','%d-%m-%Y')
    //                ${cond}
    //                ${cond2}
    //            GROUP BY cid
    //            ORDER BY cid

            /*String strSqlOld = """
                SELECT cid, `name`, `cmCode`, `percentage`,
                (
                    SELECT ROUND (SUM(jd.debit_amount) - SUM(jd.credit_amount),2)
                    FROM chart_of_accounts_mapping coam
                        INNER JOIN chart_of_accounts coa
                            ON (coa.id = coam.chart_of_accounts_id)
                        INNER JOIN journal_details jd
                            ON (coa.id = jd.chart_of_accounts_id)
                        WHERE coam.coa_type = '${COAType.ACCOUNTS_RECEIVABLE}'
                            AND jd.prefix_code = cmCode
                            AND jd.is_active = TRUE
                            AND DATE(jd.date_created) <= STR_TO_DATE('${params.date}','%d-%m-%Y')
                    ) AS total_receivable,
                    ((
                    SELECT IFNULL( ROUND (SUM(`invoice_details`.`quantity` * (
                        SELECT `product_price_product`.`total_amount`
                        FROM `product_price_product`
                            INNER JOIN `product_price`
                                ON (`product_price`.`id` = `product_price_product`.`product_price_id`)
                            INNER JOIN `territory_sub_area_product_price`
                                ON (`territory_sub_area_product_price`.`product_price_id` = `product_price_product`.`product_price_id`)
                            INNER JOIN `customer_territory_sub_area`
                                ON (`customer_territory_sub_area`.`territory_sub_area_id` = `territory_sub_area_product_price`.`territory_sub_area_id`)
                        WHERE `customer_territory_sub_area`.`customer_master_id` = cid
                            AND `product_price_product`.`pricing_category_id` = ${ApplicationConstants.PRICING_CATEGORY_DP_ID}
                            AND `product_price_product`.`finish_product_id` = `invoice_details`.`finish_product_id`
                            AND DATE(`invoice`.`date_created`)
                                BETWEEN DATE(`product_price`.`date_effective_from`)
                                    AND DATE(`product_price`.`date_effective_to`)
                            AND `product_price_product`.`is_active` IS TRUE
                        ORDER BY `product_price_product`.`date_created` DESC
                        LIMIT 1
                        )),2), 0)
                    FROM `invoice_details`
                        INNER JOIN `invoice`
                            ON (`invoice_details`.`invoice_id` = `invoice`.`id`)
                        INNER JOIN `one_percent_bonus`
                            ON (`one_percent_bonus`.`finish_product_id` = `invoice_details`.`finish_product_id`
                            AND `one_percent_bonus`.`customer_master_id` = `invoice`.`default_customer_id`)
                    WHERE `invoice`.`is_active` = TRUE
                        AND `invoice`.`default_customer_id` = cid
                        AND DATE(`invoice`.`date_created`)
                        BETWEEN (
                            CASE WHEN(`one_percent_bonus`.`version` = 0)
                            THEN DATE(`one_percent_bonus`.`last_calculated`)
                            ELSE DATE(DATE_ADD(`one_percent_bonus`.`last_calculated`, INTERVAL 1 DAY)) END)
                        AND STR_TO_DATE('${params.date}','%d-%m-%Y')
                    ) - (
                    SELECT IFNULL(ROUND (SUM(`process_market_return_details`.`rate`*`process_market_return_details`.`qc_quantity`),2), 0)
                    FROM `process_market_return_details`
                        INNER JOIN `process_market_return`
                            ON (`process_market_return`.`id` = `process_market_return_details`.`process_market_return_id`)
                        INNER JOIN `market_return`
                            ON (`market_return`.`mr_no` = `process_market_return`.`mr_no`)
                        INNER JOIN `one_percent_bonus`
                            ON (`one_percent_bonus`.`finish_product_id` = `process_market_return_details`.`finish_product_id`
                            AND `one_percent_bonus`.`customer_master_id` = `market_return`.`secondary_customer_id`)
                    WHERE DATE(`process_market_return`.`date_created`)
                        BETWEEN (
                            CASE WHEN(`one_percent_bonus`.`version` = 0)
                            THEN DATE(`one_percent_bonus`.`last_calculated`)
                            ELSE DATE(DATE_ADD(`one_percent_bonus`.`last_calculated`, INTERVAL 1 DAY)) END)
                        AND STR_TO_DATE('${params.date}','%d-%m-%Y')
                        AND `one_percent_bonus`.`is_active` = TRUE
                        AND `market_return`.`secondary_customer_id` = cid
                    )) AS total_sales
                FROM (
                    SELECT `customer_master`.`id` AS cid,`customer_master`.`name`,
                        `customer_master`.`code` AS cmCode,
                        (SELECT `bonus_percent`.`percentage` FROM one_percent_bonus
                        INNER JOIN `bonus_percent`
                            ON (`bonus_percent`.`id` = `one_percent_bonus`.`bonus_percent_id`)
                        WHERE `one_percent_bonus`.`is_active` = TRUE
                           AND `one_percent_bonus`.`customer_master_id` = cid
                        LIMIT 1) AS `percentage`
                    FROM `invoice`
                        INNER JOIN `customer_master`
                            ON (invoice.`default_customer_id` = `customer_master`.`id` AND `invoice`.`is_active` = TRUE)
                    WHERE  invoice.is_active = TRUE
                        ${cond2}
                        ${cond}
                    GROUP BY cid
                    ORDER BY cid
                ) AS  tbl
                WHERE percentage IS NOT NULL
            """*/

            /*New Optimized Script*/
            String strSql = """
                SELECT cid, `name`, `cmCode`, `percentage`,
                (
                    SELECT ROUND (SUM(jd.debit_amount) - SUM(jd.credit_amount),2)
                    FROM chart_of_accounts_mapping coam
                        INNER JOIN chart_of_accounts coa
                            ON (coa.id = coam.chart_of_accounts_id)
                        INNER JOIN journal_details jd
                            ON (coa.id = jd.chart_of_accounts_id)
                        WHERE coam.coa_type = '${COAType.ACCOUNTS_RECEIVABLE}'
                            AND jd.prefix_code = cmCode
                            AND jd.is_active = TRUE
                            AND DATE(jd.date_created) <= STR_TO_DATE('${params.date}','%d-%m-%Y')
                    ) AS total_receivable,
                    ((
                    SELECT IFNULL( ROUND (SUM(`invoice_details`.`quantity` * `invoice_details`.`dp_unit_price`),2), 0)
                    FROM `invoice_details`
                        INNER JOIN `invoice`
                            ON (`invoice_details`.`invoice_id` = `invoice`.`id`)
                        INNER JOIN `one_percent_bonus`
                            ON (`one_percent_bonus`.`finish_product_id` = `invoice_details`.`finish_product_id`
                            AND `one_percent_bonus`.`customer_master_id` = `invoice`.`default_customer_id`)
                    WHERE `invoice`.`is_active` = TRUE
                        AND `invoice`.`default_customer_id` = cid
                        AND DATE(`invoice`.`date_created`)
                        BETWEEN (
                            CASE WHEN(`one_percent_bonus`.`version` = 0)
                            THEN DATE(`one_percent_bonus`.`last_calculated`)
                            ELSE DATE(DATE_ADD(`one_percent_bonus`.`last_calculated`, INTERVAL 1 DAY)) END)
                        AND STR_TO_DATE('${params.date}','%d-%m-%Y')
                    ) - (
                    SELECT IFNULL(ROUND (SUM(`process_market_return_details`.`rate`*`process_market_return_details`.`qc_quantity`),2), 0)
                    FROM `process_market_return_details`
                        INNER JOIN `process_market_return`
                            ON (`process_market_return`.`id` = `process_market_return_details`.`process_market_return_id`)
                        INNER JOIN `market_return`
                            ON (`market_return`.`mr_no` = `process_market_return`.`mr_no`)
                        INNER JOIN `one_percent_bonus`
                            ON (`one_percent_bonus`.`finish_product_id` = `process_market_return_details`.`finish_product_id`
                            AND `one_percent_bonus`.`customer_master_id` = `market_return`.`secondary_customer_id`)
                    WHERE DATE(`process_market_return`.`date_created`)
                        BETWEEN (
                            CASE WHEN(`one_percent_bonus`.`version` = 0)
                            THEN DATE(`one_percent_bonus`.`last_calculated`)
                            ELSE DATE(DATE_ADD(`one_percent_bonus`.`last_calculated`, INTERVAL 1 DAY)) END)
                        AND STR_TO_DATE('${params.date}','%d-%m-%Y')
                        AND `one_percent_bonus`.`is_active` = TRUE
                        AND `market_return`.`secondary_customer_id` = cid
                    )) AS total_sales
                FROM (
                    SELECT `customer_master`.`id` AS cid,`customer_master`.`name`,
                        `customer_master`.`code` AS cmCode,
                        (SELECT `bonus_percent`.`percentage` FROM one_percent_bonus
                        INNER JOIN `bonus_percent`
                            ON (`bonus_percent`.`id` = `one_percent_bonus`.`bonus_percent_id`)
                        WHERE `one_percent_bonus`.`is_active` = TRUE
                           AND `one_percent_bonus`.`customer_master_id` = cid
                        LIMIT 1) AS `percentage`
                    FROM `invoice`
                        INNER JOIN `customer_master`
                            ON (invoice.`default_customer_id` = `customer_master`.`id` AND `invoice`.`is_active` = TRUE)
                    WHERE  invoice.is_active = TRUE
                        ${cond2}
                        ${cond}
                    GROUP BY cid
                    ORDER BY cid
                ) AS  tbl
                WHERE percentage IS NOT NULL
            """
            List objList = sql.rows(strSql)
            return objList
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listBranch(Object params) {
        sql = new Sql(dataSource)
        String[] ids = params.territory.split(',')
        String cond = """ (territory_configuration_id = ${ids[0]}"""
        for (int i = 1; i < ids.length; i++) {
            if (ids[i]) {
                cond = cond + """ OR territory_configuration_id = ${ids[i]}"""
            }
        }
        cond = cond + """)"""
        String strSql = """
                SELECT `customer_master`.`name`,`customer_master`.`id`
                FROM `customer_master`
                INNER JOIN `customer_territory_sub_area`
                    ON `customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`
                INNER JOIN `territory_sub_area`
                    ON `territory_sub_area`.`id` = `customer_territory_sub_area`.`territory_sub_area_id`
                INNER JOIN `territory_configuration`
                    ON `territory_configuration`.id = `territory_sub_area`.`territory_configuration_id`
                WHERE `customer_master`.`category_id` = 1
                    AND ${cond}
                GROUP BY `customer_master`.`id`
              """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List listSalesmanByBranch(Object params) {
        sql = new Sql(dataSource)
        String[] ids = params.territory.split(',')
        String cond = """ (`territory_sub_area`.`territory_configuration_id` = ${ids[0]}"""
        for (int i = 1; i < ids.length; i++) {
            if (ids[i]) {
                cond = cond + """ OR `territory_sub_area`.`territory_configuration_id` = ${ids[i]}"""
            }
        }
        cond = cond + """)"""
        String strSql = """
                 SELECT `customer_master`.`id`,`customer_master`.`name`,`customer_master`.`code`,
                (SELECT GROUP_CONCAT(DISTINCT `sales_man`.`id`)
                FROM `customer_master` `sales_man`
                INNER JOIN `customer_territory_sub_area`
                    ON (`customer_territory_sub_area`.`customer_master_id` = `sales_man`.`id`)
                WHERE `sales_man`.`category_id` = ${ApplicationConstants.CUSTOMER_CATEGORY_SALES_MAN_ID}
                    AND `customer_territory_sub_area`.`territory_sub_area_id` IN
                    (SELECT `customer_territory_sub_area`.`territory_sub_area_id`
                    FROM `customer_territory_sub_area`
                    WHERE `customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`)
                    LIMIT 1) AS sales_man_sale
                FROM `customer_master`
                INNER JOIN `customer_territory_sub_area`
                    ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`)
                INNER JOIN `territory_sub_area`
                    ON (`territory_sub_area`.`id` = `customer_territory_sub_area`.`territory_sub_area_id`)
                WHERE `customer_master`.`category_id` = ${ApplicationConstants.CUSTOMER_CATEGORY_BRANCH_ID}
                    AND ${cond}
                GROUP BY `customer_master`.`id`
              """
        List objList = sql.rows(strSql)
        for (int i = 0; i < objList.size(); i++) {
            params.put('salesman', objList[i].sales_man_sale ? objList[i].sales_man_sale : '')
            List list = listTotalSaleForPrimary(params)
            if (list[0].total_sale) {
                objList[i].sales_man_sale = list[0].total_sale
            } else {
                objList.remove(i)
                i--
            }
        }
        return objList
    }

    @Transactional(readOnly = true)
    public List listTotalSaleForPrimary(Object params) {
        try{
            sql = new Sql(dataSource)
            String[] ids = params.territory.split(',')
            String cond = """"""
            if (ids[0] != '') {
                cond = " AND (`territory_configuration_id` = ${ids[0]}"
                for (int i = 1; i < ids.length; i++) {
                    if (ids[i]) {
                        cond = cond + " OR `territory_configuration_id` = ${ids[i]}"
                    }
                }
                cond = cond + ")"
            }
            String strSql = """
                    SELECT cid, `name`, `cmCode`, `percentage`,
                        (
                            SELECT ROUND (SUM(jd.debit_amount) - SUM(jd.credit_amount),2)
                            FROM chart_of_accounts_mapping coam
                                INNER JOIN chart_of_accounts coa
                                    ON (coa.id = coam.chart_of_accounts_id)
                                INNER JOIN journal_details jd
                                    ON (coa.id = jd.chart_of_accounts_id)
                                WHERE coam.coa_type = '${COAType.ACCOUNTS_RECEIVABLE}'
                                    AND jd.prefix_code = cmCode
                                    AND jd.is_active = TRUE
                                    AND DATE(jd.date_created) <= STR_TO_DATE('${params.date}','%d-%m-%Y')
                            ) AS total_receivable,
                            ((
                            SELECT IFNULL(ROUND (SUM(`invoice_details`.`quantity`*`invoice_details`.`unit_price`),2),0)
                            FROM `invoice_details`
                                INNER JOIN `invoice`
                                    ON (`invoice_details`.`invoice_id` = `invoice`.`id`)
                                INNER JOIN `one_percent_bonus`
                                    ON (`one_percent_bonus`.`finish_product_id` = `invoice_details`.`finish_product_id`
                                    AND `one_percent_bonus`.`customer_master_id` = `invoice`.`default_customer_id`)
                            WHERE `invoice`.`is_active` = TRUE
                                AND `invoice`.`default_customer_id` = cid
                                AND DATE(`invoice`.`date_created`)
                                BETWEEN (
                                    CASE WHEN(`one_percent_bonus`.`version` = 0)
                                    THEN DATE(`one_percent_bonus`.`last_calculated`)
                                    ELSE DATE(DATE_ADD(`one_percent_bonus`.`last_calculated`, INTERVAL 1 DAY)) END)
                                AND STR_TO_DATE('${params.date}','%d-%m-%Y')
                            ) - (
                            SELECT IFNULL(ROUND (SUM(`process_market_return_details`.`rate`*`process_market_return_details`.`qc_quantity`),2), 0)
                            FROM `process_market_return_details`
                                INNER JOIN `process_market_return`
                                    ON `process_market_return`.`id` = `process_market_return_details`.`process_market_return_id`
                                INNER JOIN `market_return`
                                    ON `market_return`.`mr_no` = `process_market_return`.`mr_no`
                                INNER JOIN `one_percent_bonus`
                                    ON (`one_percent_bonus`.`finish_product_id` = `process_market_return_details`.`finish_product_id`
                                    AND `one_percent_bonus`.`customer_master_id` = `market_return`.`primary_customer_id`)
                            WHERE DATE(`process_market_return`.`date_created`)
                                BETWEEN (
                                    CASE WHEN(`one_percent_bonus`.`version` = 0)
                                    THEN DATE(`one_percent_bonus`.`last_calculated`)
                                    ELSE DATE(DATE_ADD(`one_percent_bonus`.`last_calculated`, INTERVAL 1 DAY)) END)
                                AND STR_TO_DATE('${params.date}','%d-%m-%Y')
                                AND `one_percent_bonus`.`is_active` = TRUE
                                AND `market_return`.`primary_customer_id` = cid
                            )) AS total_sale
                    FROM (
                        SELECT `customer_master`.`id` AS cid,`customer_master`.`name`,
                            `customer_master`.`code` AS cmCode,
                            (SELECT `bonus_percent`.`percentage` FROM one_percent_bonus
                            INNER JOIN `bonus_percent`
                                    ON (`bonus_percent`.`id` = `one_percent_bonus`.`bonus_percent_id`)
                            WHERE `one_percent_bonus`.`is_active` = TRUE
                               AND `one_percent_bonus`.`customer_master_id` = cid
                            LIMIT 1) AS `percentage`,
                            (SELECT `territory_configuration_id` FROM `customer_territory_sub_area`
                                INNER JOIN `territory_sub_area`
                                    ON (`territory_sub_area`.`id` = `customer_territory_sub_area`.`territory_sub_area_id`)
                            WHERE `customer_territory_sub_area`.`customer_master_id` = cid
                            LIMIT 1) AS territory_configuration_id
                        FROM `invoice`
                            INNER JOIN `customer_master`
                                ON (invoice.`default_customer_id` = `customer_master`.`id` AND `invoice`.`is_active` = TRUE)
                        WHERE  invoice.is_active = TRUE
                            AND `customer_master`.`customer_level` = '${CustomerLevel.PRIMARY}'
                        GROUP BY cid
                        ORDER BY cid
                    ) AS  tbl
                    WHERE percentage IS NOT NULL
                    ${cond}
            """
            List objList = sql.rows(strSql)
            return objList
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List getLastCalculated(Long id) {
        sql = new Sql(dataSource)
        String strSql = """
            SELECT DISTINCT `one_percent_bonus`.`last_calculated`
            FROM `one_percent_bonus`
            WHERE `one_percent_bonus`.`customer_master_id` = ${id}
        """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List getLastCalculatedBranch(Long id) {
        sql = new Sql(dataSource)
        String strSql = """
                SELECT DISTINCT `one_percent_bonus`.`last_calculated`
                FROM `one_percent_bonus`
                WHERE `one_percent_bonus`.`customer_master_id` IN(SELECT
                (SELECT GROUP_CONCAT(DISTINCT `sales_man`.`id`)
                FROM `customer_master` `sales_man`
                INNER JOIN `customer_territory_sub_area`
                    ON (`customer_territory_sub_area`.`customer_master_id` = `sales_man`.`id`)
                WHERE `sales_man`.`category_id` = 3
                    AND `customer_territory_sub_area`.`territory_sub_area_id` IN
                    (SELECT `customer_territory_sub_area`.`territory_sub_area_id`
                    FROM `customer_territory_sub_area`
                    WHERE `customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`)
                    LIMIT 1) AS sales_man_sale
                FROM `customer_master`
                INNER JOIN `customer_territory_sub_area`
                    ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`)
                INNER JOIN `territory_sub_area`
                    ON (`territory_sub_area`.`id` = `customer_territory_sub_area`.`territory_sub_area_id`)
                WHERE `customer_master`.`category_id` = 1
                AND `customer_master`.`id` = ${id}
                GROUP BY `customer_master`.`id`)
             """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional
    public Object adjust(Object map) {
        try {
            List<AdjustOnePercentBonus> adjustOnePercentBonusList = map.adjustOnePercentBonusList
            List<Invoice> unadjustedInvoiceList = map.unadjustedInvoiceList
            List<AdjustOnePercentBonusDetails> adjustOnePercentBonusDetailsList = map.adjustOnePercentBonusDetailsList
            adjustOnePercentBonusList.each {
                it.save(false)
            }

            for (int i = 0; i < adjustOnePercentBonusList.size(); i++) {
                setLastCalcDateSale(adjustOnePercentBonusList[i].customerMaster.id, new SimpleDateFormat("dd-MM-yyyy").format(adjustOnePercentBonusList[i].calculatedTo))
            }

//            Adjust with invoices
            unadjustedInvoiceList.each {
                it.save()
            }

            adjustOnePercentBonusDetailsList.each {
                it.save()
            }

            //***********************************COA Start*******************************//
            Journal journal = (Journal) map.get('journal')
            journal.save(validate: false, insert: true)

            List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.get('journalDetailsList')
            journalDetailsList.each { journalDetails ->
                journalDetails.save(validate: false, insert: true)
            }
            //*********************************COA Start*******************************//
            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public void setLastCalcDateFac(Long id, String date) {
        sql = new Sql(dataSource)
        String strSql = """
                SELECT (SELECT GROUP_CONCAT(DISTINCT `sales_man`.`id`)
                FROM `customer_master` `sales_man`
                INNER JOIN `customer_territory_sub_area`
                    ON (`customer_territory_sub_area`.`customer_master_id` = `sales_man`.`id`)
                WHERE `sales_man`.`category_id` = 3
                    AND `customer_territory_sub_area`.`territory_sub_area_id` IN
                    (SELECT `customer_territory_sub_area`.`territory_sub_area_id`
                    FROM `customer_territory_sub_area`
                    WHERE `customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`)
                    LIMIT 1) AS sales_man_sale
                FROM `customer_master`
                INNER JOIN `customer_territory_sub_area`
                    ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`)
                INNER JOIN `territory_sub_area`
                    ON (`territory_sub_area`.`id` = `customer_territory_sub_area`.`territory_sub_area_id`)
                WHERE `customer_master`.`category_id` = 1
                AND `customer_master`.`id` = ${id}
                GROUP BY `customer_master`.`id`
                """
        List objList = sql.rows(strSql)
        String[] ids = objList[0].sales_man_sale.split(',')
        String cond = """`one_percent_bonus`.`customer_master_id` = ${ids[0]}"""
        for (int i = 1; i < ids.length; i++) {
            cond = cond + """ OR `one_percent_bonus`.`customer_master_id` = ${ids[i]}"""
        }
        strSql = """
                UPDATE `one_percent_bonus`
                SET `one_percent_bonus`.`last_calculated_factory` = STR_TO_DATE('${date}','%d-%m-%Y')
                WHERE ${cond}
              """
        sql.execute(strSql)
    }

    @Transactional
    public void setLastCalcDateSale(Long id, String date) {
        sql = new Sql(dataSource)
        String strSql = """
            UPDATE `one_percent_bonus`
            SET `one_percent_bonus`.`last_calculated` = STR_TO_DATE('${date}','%d-%m-%Y'),
                `one_percent_bonus`.`version` = `one_percent_bonus`.`version` + 1
            WHERE `one_percent_bonus`.`customer_master_id` = ${id}
          """
        sql.execute(strSql)
    }

    @Transactional
    public Object subLedgerEntry(Object map) {
        try {
            List<SubLedger> subLedgers = map.subLedgers
            subLedgers.each {
                it.save(false)
            }
            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listAllBranch() {
        sql = new Sql(dataSource)
        String strSql = """
            SELECT `customer_master`.`id`,`customer_master`.`name`
            FROM `customer_master`
            WHERE `customer_master`.`category_id` = 1
          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List fetchDefaultBranch(Long userId) {
        sql = new Sql(dataSource)
        List objList = []
        String strLst = '';
        String strSql = """
            SELECT `distribution_point`.`id` ,`distribution_point`.`name`, `is_factory` AS isFactory
            FROM `application_user_distribution_point`
                INNER JOIN `distribution_point`
                    ON (`application_user_distribution_point`.`distribution_point_id` = `distribution_point`.`id`)
             WHERE application_user_distribution_point.`application_user_id` = ${userId}
          """
        List obj = sql.rows(strSql)
        if (obj.size() > 0) {
            if (obj[0].isFactory) {
                strLst = """
                    SELECT `customer_master`.`id`,`customer_master`.`name`
                    FROM `customer_master`
                    WHERE `customer_master`.`category_id` = ${ApplicationConstants.CUSTOMER_CATEGORY_BRANCH_ID}
                """
            } else {
                strLst = """
                    SELECT `customer_master`.`id`, `customer_master`.`name`
                    FROM `application_user_distribution_point`
                        INNER JOIN `application_user`
                            ON (`application_user_distribution_point`.`application_user_id` = `application_user`.`id`)
                        INNER JOIN `distribution_point`
                            ON (`application_user_distribution_point`.`distribution_point_id` = `distribution_point`.`id`)
                        INNER JOIN `distribution_point_warehouse`
                            ON (`distribution_point_warehouse`.`distribution_point_id` = `distribution_point`.`id`)
                        INNER JOIN `customer_master`
                            ON (`distribution_point_warehouse`.`default_customer_id` = `customer_master`.`id`)
                    WHERE application_user.`id` = ${userId}
                """
            }
            objList = sql.rows(strLst)
        }
        return objList
    }
}
