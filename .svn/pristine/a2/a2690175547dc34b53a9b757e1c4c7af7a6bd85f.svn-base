package com.bits.bdfp.inventory.demandorder

import com.bits.bdfp.util.ApplicationConstants
import com.docu.security.ApplicationUser
import groovy.sql.GroovyRowResult
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class SecondaryDemandOrderDetailsService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public SecondaryDemandOrderDetails create(Object object) {
        SecondaryDemandOrderDetails secondaryDemandOrderDetails = (SecondaryDemandOrderDetails) object
        if (secondaryDemandOrderDetails.save(false)) {
            return secondaryDemandOrderDetails
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        SecondaryDemandOrderDetails secondaryDemandOrderDetails = (SecondaryDemandOrderDetails) object
        if (secondaryDemandOrderDetails.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        SecondaryDemandOrderDetails secondaryDemandOrderDetails = (SecondaryDemandOrderDetails) object
        secondaryDemandOrderDetails.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)

    public Map getListForGrid(Action action, Object params, ApplicationUser applicationUser) {
        sql = new Sql(dataSource)
        String strLIMIT = "";
        String offSet = ""

        if (action.resultPerPage != -1) {
            strLIMIT = """LIMIT ${action.resultPerPage}"""
            offSet = """
                    OFFSET ${action.start}
                   """
        } else {
            action.resultPerPage = -1;
        }

        String strSql = """SELECT DISTINCT secondary_demand_order_details.id,finish_product.id AS pid,
                        finish_product.code,finish_product.name,secondary_demand_order_details.rate,
                        secondary_demand_order_details.`quantity` as qty,secondary_demand_order_details.amount
                         FROM `secondary_demand_order`
                         INNER JOIN `secondary_demand_order_details`
                        ON `secondary_demand_order`.id=`secondary_demand_order_details`.`secondary_demand_order_id`
                        INNER JOIN finish_product ON finish_product.id=secondary_demand_order_details.`finish_product_id`
                        WHERE `secondary_demand_order`.id=${
            params.id
        } AND secondary_demand_order.`user_order_placed_id`=${applicationUser.id}

                            ${strLIMIT}
                           ${offSet}
                          """
        List objList = sql.rows(strSql.toString())
        int resultCount = 0
        if (objList && objList.size() > 0) {
            resultCount = objList.size()
        }

        return [objList: objList, count: resultCount]
    }

    @Transactional(readOnly = true)
    public List<SecondaryDemandOrderDetails> list() {
        return SecondaryDemandOrderDetails.list()
    }

    @Transactional(readOnly = true)
    public SecondaryDemandOrderDetails read(Long id) {
        return SecondaryDemandOrderDetails.read(id)
    }

    @Transactional(readOnly = true)
    public SecondaryDemandOrderDetails search(String fieldName, String fieldValue) {
        String query = "from SecondaryDemandOrderDetails as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return SecondaryDemandOrderDetails.find(query)
    }

    @Transactional(readOnly = true)
    public List<GroovyRowResult> listDetailsById(Object params) {
        String query = """
             SELECT
                  `secondary_demand_order_details`.`id`
                , `finish_product`.`name` AS `productName`
                , `secondary_demand_order_details`.`quantity`
                , ROUND(`secondary_demand_order_details`.`rate`,2) AS rate
                , ROUND(`secondary_demand_order_details`.`amount`,2) AS amount
                , ROUND((`finish_product`.`qty_in_ltr` * `secondary_demand_order_details`.`quantity`),2) AS qtyInLtr
            FROM
                `secondary_demand_order_details`
                INNER JOIN `finish_product`
                    ON (`finish_product`.`id` = `secondary_demand_order_details`.`finish_product_id`)
            WHERE `secondary_demand_order_details`.`secondary_demand_order_id` = ${params.orderId}
        """
        List<GroovyRowResult> groovyRowResultList = new Sql(dataSource).rows(query)
        List<String> list = null
        for (GroovyRowResult row : groovyRowResultList) {
            list = new ArrayList<String>()
            if (row.productName) {
                list.add(row.productName)
            }
            if (row.quantity) {
                list.add(row.quantity)
            }
            if (row.rate) {
                list.add(row.rate)
            }
            if (row.amount) {
                list.add(row.amount)
            }
            if (row.qtyInLtr) {
                list.add(row.qtyInLtr)
            }
        }
        return groovyRowResultList
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, String [] list) {
        String query = """SELECT `secondary_demand_order_details`.`id`,`secondary_demand_order_details`.`finish_product_id`,
            `secondary_demand_order_details`.`quantity`,`secondary_demand_order_details`.`rate`,
            `secondary_demand_order_details`.`amount`,`finish_product`.`name`
            FROM `secondary_demand_order_details`
            INNER JOIN `finish_product` ON `finish_product`.`id` = `secondary_demand_order_details`.`finish_product_id`
            WHERE"""
        for(int i = 0; i < list.size(); i++){
            if(list[i]) {
                query = query + " secondary_demand_order_details.secondary_demand_order_id = " + list[i]
                if (i != list.size() - 1) {
                    query = query + ' OR '
                }
            }
        }
        query = query + " ORDER BY `finish_product_id`"

        List<GroovyRowResult> groovyRowResultList = new Sql(dataSource).rows(query)
        return [objList: groovyRowResultList, count: groovyRowResultList.size()]
    }

    @Transactional(readOnly = true)
    public Map listSecondaryDetails(String [] list,Object params) {
        String query = """
            SELECT `finish_product`.`id`,`secondary_demand_order_details`.`finish_product_id`,
                SUM(`secondary_demand_order_details`.`quantity`) AS quantity,
                (SELECT `product_price_product`.`total_amount` AS price FROM `product_price_product`
                WHERE `product_price_product`.`product_price_id` IN (SELECT `product_price`.`id`
                    FROM `product_price`
                        JOIN `territory_sub_area_product_price`
                            ON (`territory_sub_area_product_price`.`product_price_id` = `product_price`.`id`)
                        JOIN `territory_sub_area`
                            ON (`territory_sub_area_product_price`.`territory_sub_area_id` = `territory_sub_area`.`id`)
                    WHERE `product_price`.`is_active` = TRUE
                        AND `territory_sub_area`.id = (SELECT `customer_territory_sub_area`.`territory_sub_area_id`
                                                       FROM `customer_territory_sub_area`
                                                       WHERE `customer_territory_sub_area`.`customer_master_id` = ${params.customerId}
                                                            AND `customer_territory_sub_area`.`territory_sub_area_id` = ${params.territorySubAreaId} )
                                                       ORDER BY ABS(DATEDIFF(NOW(),`product_price`.`date_effective_from`))
                                                       )
                        AND `product_price_product`.`finish_product_id` = `finish_product`.`id`
                        AND `product_price_product`.`pricing_category_id` = (SELECT `customer_master`.`pricing_category_id` FROM `customer_master` WHERE `customer_master`.`id` = ${params.customerId})
                LIMIT 1) AS rate,
                `finish_product`.`code` AS `productCode`,
                `finish_product`.`name` AS `productName`
            FROM `secondary_demand_order_details`
                INNER JOIN `finish_product` ON `finish_product`.`id` = `secondary_demand_order_details`.`finish_product_id`
                INNER JOIN `secondary_demand_order` ON `secondary_demand_order`.`id` = `secondary_demand_order_details`.`secondary_demand_order_id`
            WHERE
            """
        for(int i = 0; i < list.size(); i++){
            if(list[i]) {
                query = query + " secondary_demand_order_details.secondary_demand_order_id = " + list[i]
                if (i != list.size() - 1) {
                    query = query + ' OR '
                }
            }
        }
        query = query + """
            GROUP BY `secondary_demand_order_details`.`finish_product_id`
            ORDER BY `secondary_demand_order_id`, `finish_product_id`
            """

//            SELECT `secondary_demand_order_details`.`id`,`secondary_demand_order_details`.`finish_product_id`,
//            `secondary_demand_order_details`.`quantity`,`secondary_demand_order_details`.`rate`,
//            `secondary_demand_order_details`.`amount`,`secondary_demand_order_details`.`secondary_demand_order_id`,
//            `finish_product`.`name`,`customer_master`.`name` AS `customer_name`,`customer_master`.`id` AS `customer_id`,
//            `secondary_demand_order`.`order_no`
//            FROM `secondary_demand_order_details`
//            INNER JOIN `finish_product` ON `finish_product`.`id` = `secondary_demand_order_details`.`finish_product_id`
//            INNER JOIN `secondary_demand_order` ON `secondary_demand_order`.`id` = `secondary_demand_order_details`.`secondary_demand_order_id`
//            INNER JOIN `customer_master` ON `customer_master`.`id` = `secondary_demand_order`.`customer_master_id`
//            WHERE"""

        //query = query + " ORDER BY `secondary_demand_order_id`, `finish_product_id`"

        List<GroovyRowResult> groovyRowResultList = new Sql(dataSource).rows(query)
        return [objList: groovyRowResultList, count: groovyRowResultList.size()]
    }
}
