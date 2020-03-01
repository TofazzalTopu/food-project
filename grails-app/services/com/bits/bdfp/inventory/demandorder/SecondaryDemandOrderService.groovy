package com.bits.bdfp.inventory.demandorder

import com.bits.bdfp.history.PrimaryOrderHistory
import com.bits.bdfp.history.SecondaryOrderHistory
import com.bits.bdfp.util.ApplicationConstants
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class SecondaryDemandOrderService extends Service {
    static transactional = false
    SpringSecurityService springSecurityService
    DataSource dataSource
    Sql sql

    @Transactional
    public SecondaryDemandOrder create(Object object) {
        Map map=(Map)object
        SecondaryDemandOrder secondaryDemandOrder = map.secondaryDemandOrder
        secondaryDemandOrder = secondaryDemandOrder.save(false)
        List <SecondaryDemandOrderDetails> secondaryDemandOrderDetailsList = map.secondaryDemandOrderDetailsList
        secondaryDemandOrderDetailsList.each{
//            it.secondaryDemandOrder=secondaryDemandOrder
            it.save(false)
        }
        return secondaryDemandOrder
    }

    @Transactional
    public Integer update(Object object) {
        try{
            SecondaryDemandOrder secondaryDemandOrder = (SecondaryDemandOrder) object
            if (secondaryDemandOrder.save()) {
                return new Integer(1)
            } else {
                return new Integer(0)
            }
        }
       catch (Exception ex){
           log.error(ex.message)
       }
    }

    @Transactional
    public boolean createSecondaryOrderHistory(SecondaryOrderHistory secondaryOrderHistory) {
        try {
            secondaryOrderHistory.save(vaidate: false, insert: false)
            return true
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }

    }
    public Integer createORUpdateSecondaryOrderHistory(List orderHistoryList) {
        List<SecondaryOrderHistory> secondaryOrderHistoryList = orderHistoryList
        secondaryOrderHistoryList.each {
            if (it.validate()) {
                it.save(false)
            }
        }
        return new Integer(1)
    }
    @Transactional
    public Integer delete(Object object) {
        SecondaryDemandOrder secondaryDemandOrder = (SecondaryDemandOrder) object
        secondaryDemandOrder.delete()
        return new Integer(1)
    }

//    @Transactional(readOnly = true)
//    public Map getListForGrid(Action action) {
//        List<SecondaryDemandOrder> objList = SecondaryDemandOrder.withCriteria {
//            if (action.resultPerPage != -1) {
//                maxResults(action.resultPerPage)
//            }
//            firstResult(action.start)
//            order(action.sortCol, action.sortOrder)
//        }
//        long total = SecondaryDemandOrder.count()
//        return [objList: objList, count: total]
//    }
    @Transactional(readOnly = true)
    public Map getListForGrid(Action action,Object params,ApplicationUser applicationUser) {
        sql = new Sql(dataSource)
        String strLIMIT = "";
        String offSet = ""
        String searchCondition = ""
        String orderNo = ""
        String date = ""
        if (params.orderNo) {
            orderNo = """ AND `secondary_demand_order`.`order_no` = '${params.orderNo}'
        """
        }
        if (params.status) {
            searchCondition = """ AND`secondary_demand_order`.`demand_order_status`='${params.status}'
        """
        }
        if (params.dateFrom && params.dateTo) {
            date = """ AND DATE(secondary_demand_order.`date_order`) BETWEEN STR_TO_DATE('${params.dateFrom}','%d-%m-%Y')
              AND STR_TO_DATE('${params.dateTo}','%d-%m-%Y')

        """
        }

        if (action.resultPerPage != -1) {
            strLIMIT = """LIMIT ${action.resultPerPage}"""
            offSet="""
                    OFFSET ${action.start}
                   """
        } else {
            action.resultPerPage = -1;
        }

        String strSql = """SELECT tbl.id,tbl.name,tbl.order_no,tbl.date_order,
                            SUM(tbl.amount) AS amount,tbl.demand_order_status
                            FROM
                            (SELECT secondary_demand_order.id,customer_master.name,
                            secondary_demand_order.`order_no`,
                            DATE_FORMAT(secondary_demand_order.`date_order`,'%d-%m-%Y') as date_order,
                           (CASE WHEN secondary_demand_order_details.id
                           THEN`secondary_demand_order_details`.amount ELSE 0 END) AS amount ,
                           `secondary_demand_order`.`demand_order_status`

                            FROM `secondary_demand_order`
                            LEFT OUTER JOIN `secondary_demand_order_details`
                            ON `secondary_demand_order`.id=`secondary_demand_order_details`.`secondary_demand_order_id`
                            INNER JOIN `customer_master` ON customer_master.id=`secondary_demand_order`.`customer_master_id`
                            WHERE `secondary_demand_order`.`user_order_placed_id`=${applicationUser.id}
                            AND (`secondary_demand_order`.`demand_order_status` = '${DemandOrderStatus.UNDER_PROCESS}'
                            OR `secondary_demand_order`.`demand_order_status` = '${DemandOrderStatus.REJECTED}')
                            ${orderNo}
                            ${searchCondition}
                            ${date}
                            ) AS tbl
                            GROUP BY
                            tbl.id,tbl.name,tbl.order_no,tbl.date_order,tbl.demand_order_status
                            ORDER BY tbl.id
                            ${strLIMIT}
                           ${offSet}
                          """
        List objList = sql.rows(strSql.toString())
        int resultCount=0
            if (objList&& objList.size()>0){
                resultCount = objList.size()
            }

        return [objList: objList, count:resultCount]
    }
    @Transactional(readOnly = true)
    public List<SecondaryDemandOrder> list() {
        return SecondaryDemandOrder.list()
    }

    @Transactional(readOnly = true)
    public SecondaryDemandOrder read(Long id) {
        return SecondaryDemandOrder.read(id)
    }

    @Transactional(readOnly = true)
    public SecondaryDemandOrder search(String fieldName, String fieldValue) {
        String query = "from SecondaryDemandOrder as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return SecondaryDemandOrder.find(query)
    }

    @Transactional(readOnly = true)
    public List listForUpdate(ApplicationUser applicationUser,Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                        SELECT tbl.id,tbl.eid,tbl.name,tbl.cid,tbl.cus_name,CONCAT(customer_master.`code`,'-',
                        customer_master.`name`,'-',master_type.name) AS dname,tbl.date_order,tbl.date_deliver,
                         tbl.ename,tbl.tid
                        FROM

                        (SELECT secondary_demand_order.id,enterprise_configuration.id AS eid,customer_master.`name`,
                        customer_master.id AS cid,secondary_demand_order.user_tentative_delivery_id AS tid,
                        CONCAT(  customer_master.`code`,'-',
                        customer_master.`name`,'-',master_type.name) AS cus_name,
                        enterprise_configuration.`name` AS ename,
                        DATE_FORMAT(secondary_demand_order.`date_order`,'%d-%m-%Y') AS date_order,
                         DATE_FORMAT(secondary_demand_order.`date_deliver`,'%d-%m-%Y') AS date_deliver

                         FROM `secondary_demand_order`
                         INNER JOIN `customer_master`
                         ON `customer_master`.id=`secondary_demand_order`.`customer_master_id`
                        INNER JOIN `enterprise_configuration`
                        ON `enterprise_configuration`.id=`customer_master`.`enterprise_configuration_id`
                        INNER JOIN master_type ON master_type.id=`customer_master`.`master_type_id`

                        WHERE `secondary_demand_order`.id=${params.id}
                        AND secondary_demand_order.`user_order_placed_id`=${applicationUser.id})
                        AS tbl
                        LEFT OUTER JOIN customer_master ON `customer_master`.id=tbl.tid
                        LEFT OUTER JOIN master_type ON master_type.id=`customer_master`.`master_type_id`

                          """

        List resultList = sql.rows(strSql.toString())
        return resultList
    }
    @Transactional(readOnly = true)
    public List listOrderNo(String orderNO,ApplicationUser applicationUser) {
        sql = new Sql(dataSource)
        String  strSql = """SELECT secondary_demand_order.id,secondary_demand_order.`order_no`
                             FROM `secondary_demand_order`
                            WHERE `secondary_demand_order`.`user_order_placed_id`=${applicationUser.id}
                            AND secondary_demand_order.`order_no` LIKE '%${orderNO}%'

                          """

        List resultList = sql.rows(strSql.toString())
        return resultList
    }
    @Transactional(readOnly = true)
    public List listOrderNoForUpdate(String orderNO,ApplicationUser applicationUser) {
        sql = new Sql(dataSource)
        String  strSql = """SELECT secondary_demand_order.id,secondary_demand_order.`order_no`,
                          CONCAT(  customer_master.`code`,'-',
                        customer_master.`name`,'-',master_type.name) AS cus_name,
                        enterprise_configuration.`name` AS ename,
                        enterprise_configuration.id AS eid,
                         customer_master.id AS cid
                             FROM `secondary_demand_order`
                              INNER JOIN `customer_master`
                         ON `customer_master`.id=`secondary_demand_order`.`customer_master_id`
                        INNER JOIN `enterprise_configuration`
                        ON `enterprise_configuration`.id=`customer_master`.`enterprise_configuration_id`
                        INNER JOIN master_type ON master_type.id=`customer_master`.`master_type_id`
                            WHERE `secondary_demand_order`.`user_order_placed_id`=${applicationUser.id}
                            AND secondary_demand_order.`order_no` LIKE '%${orderNO}%'

                          """

        List resultList = sql.rows(strSql.toString())
        return resultList
    }
    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<SecondaryDemandOrder> objList = SecondaryDemandOrder.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = SecondaryDemandOrder.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public Map getListForGridByOrder(Action action,Object params,ApplicationUser applicationUser) {
        try{
            sql = new Sql(dataSource)
            String strLIMIT = "";
            String offSet = ""

            if (action.resultPerPage != -1) {
                strLIMIT = """LIMIT ${action.resultPerPage}"""
                offSet="""
                        OFFSET ${action.start}
                       """
            } else {
                action.resultPerPage = -1;
            }

            String strSql = """
                SELECT DISTINCT secondary_demand_order_details.id, finish_product.id AS pid,
                    finish_product.code, finish_product.name, secondary_demand_order_details.rate,
                    secondary_demand_order_details.`quantity` AS qty, secondary_demand_order_details.amount
                FROM `secondary_demand_order`
                     INNER JOIN `secondary_demand_order_details`
                        ON (`secondary_demand_order`.id = `secondary_demand_order_details`.`secondary_demand_order_id`)
                    INNER JOIN finish_product ON (finish_product.id = secondary_demand_order_details.`finish_product_id`)
                    INNER JOIN `product_price` ON (finish_product.id = `product_price`.`finish_product_id`)
                WHERE `secondary_demand_order`.order_no = '${params.orderNo}'
                    AND secondary_demand_order.`user_order_placed_id` = ${applicationUser.id}
                ${strLIMIT}
                ${offSet}
            """
            List objList = sql.rows(strSql)
            int resultCount=0
            if (objList&& objList.size()>0){
                resultCount = objList.size()
            }

            return [objList: objList, count:resultCount]
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Map listSecondaryOrderForPrimary(Action action,Object params) {
        try{
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
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

        String strSql = """
            SELECT `customer_master`.`name`,`secondary_demand_order`.`order_no`,`secondary_demand_order`.`date_order`,
                `secondary_demand_order`.`date_deliver`,SUM(`secondary_demand_order_details`.`amount`) AS `amount`,
                `secondary_demand_order`.`id`,`customer_master`.`id` AS `customer_id`
            FROM `secondary_demand_order`
                INNER JOIN `customer_master` ON (`customer_master`.`id` = `secondary_demand_order`.`customer_master_id`)
                INNER JOIN `enterprise_configuration` ON (`enterprise_configuration`.`id` = `customer_master`.`enterprise_configuration_id`)
                INNER JOIN `secondary_demand_order_details` ON (`secondary_demand_order_details`.`secondary_demand_order_id` = `secondary_demand_order`.`id`)
            WHERE `enterprise_configuration`.`id` = ${params.entId}
                AND DATE(secondary_demand_order.`date_deliver`) = STR_TO_DATE('${params.date}','${ApplicationConstants.DATE_FORMAT_DB}')
                AND (`secondary_demand_order`.`demand_order_status` = '${DemandOrderStatus.UNDER_REVIEW}'
                    OR `secondary_demand_order`.`demand_order_status` = '${DemandOrderStatus.UNDER_PROCESS}')
                AND `secondary_demand_order`.`territory_sub_area_id` IN (
                    SELECT `distribution_point_territory_sub_area`.`territory_sub_area_id`
                    FROM `distribution_point_territory_sub_area`
                        INNER JOIN application_user_distribution_point ON (application_user_distribution_point.`distribution_point_id` = `distribution_point_territory_sub_area`.`distribution_point_id`)
                    WHERE application_user_distribution_point.`application_user_id` = ${applicationUser.id})
            GROUP BY `secondary_demand_order`.`order_no`
            ${strLIMIT}
            ${offSet}
        """
        List objList = sql.rows(strSql)
        int resultCount=0
        if (objList && objList.size() > 0){
            resultCount = objList.size()
            /****************** Set Demand Order Status to Under Review ***********/
            String sdoIds = ""
            objList.each {
                if(sdoIds == ""){
                    sdoIds += it.id.toString()
                }else{
                    sdoIds += "," + it.id.toString()
                }
            }
            strSql = """
                UPDATE `secondary_demand_order`
                SET `demand_order_status` = '${DemandOrderStatus.UNDER_REVIEW}',
                    user_updated_id = ${applicationUser.id}, last_updated = NOW()
                WHERE `id` IN (${sdoIds})
            """
            sql.execute(strSql)
            /*****************************************************************/
        }

        return [objList: objList, count:resultCount]
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public void cancelOrder(Object params) {
        try{
            ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
            sql = new Sql(dataSource)

            String strSql = """
                UPDATE `secondary_demand_order`
                SET `demand_order_status` = '${DemandOrderStatus.REJECTED}',
                    user_updated_id = ${applicationUser.id}, last_updated = NOW()
                WHERE `id` IN (${params.ids})
            """
            sql.execute(strSql)
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }

    }
}
