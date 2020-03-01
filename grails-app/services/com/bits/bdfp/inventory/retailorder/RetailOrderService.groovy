package com.bits.bdfp.inventory.retailorder

import com.bits.bdfp.customer.CustomerStock
import com.bits.bdfp.customer.CustomerStockTransaction
import com.bits.bdfp.finance.CustomerAccount
import com.bits.bdfp.finance.CustomerPayment
import com.bits.bdfp.finance.CustomerPaymentInvoice
import com.bits.bdfp.inventory.demandorder.DemandOrderStatus
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.demandorder.InvoiceDetails
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetails
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder
import com.bits.bdfp.inventory.demandorder.primarydemandorder.CancelSalesInvoiceAction
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Action
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service

import javax.sql.DataSource

class RetailOrderService extends Service {
    static transactional = false
    SpringSecurityService springSecurityService
    DataSource dataSource
    Sql sql

    @Transactional(readOnly = true)
    public Map getListForGrid(Map params) {
        List<RetailOrder> retailOrderList = []
        long retailOrderCount = 0
        try{
            retailOrderList = RetailOrder.withCriteria {
                maxResults(Integer.parseInt(params.resultPerPage.toString()))
                firstResult(Integer.parseInt(params.start.toString()))
                order(params.sortCol.toString(), params.sortOrder.toString())
            }
            retailOrderCount = RetailOrder.count()
        }
        catch(Exception ex){
            log.error(ex.message)
        }

        return [retailOrderList: retailOrderList, retailOrderCount: retailOrderCount]
    }

    @Transactional(readOnly = true)
    public List<RetailOrder> list() {
        return RetailOrder.list()
    }

    @Transactional
    public RetailOrder create(Object object) {
        try {
            RetailOrder retailOrder = (RetailOrder) object
            return  retailOrder.save(vaidate: false, insert: true)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public RetailOrder read(Long id) {
        return RetailOrder.read(id)
    }

    @Transactional
    public Integer update(Object object) {
        try {
            RetailOrder retailOrder = (RetailOrder) object
            if (retailOrder.save(vaidate: false, insert: false)) {
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
            RetailOrder retailOrder = (RetailOrder) object
            RetailOrderDetails.executeUpdate("delete from RetailOrderDetails rod where retailOrder=?",[retailOrder])
            retailOrder.delete()
            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Boolean deleteSecondaryOrder(Object object) {
        try {
            SecondaryDemandOrder secondaryDemandOrder = (SecondaryDemandOrder) object
            SecondaryDemandOrderDetails.executeUpdate("delete from SecondaryDemandOrderDetails sdod where secondaryDemandOrder=?",[secondaryDemandOrder])
            List<RetailOrder> retailOrderList = RetailOrder.findAllBySecondaryDemandOrder(secondaryDemandOrder)
            retailOrderList.each {  retailOrder ->
                 RetailOrderDetails.executeUpdate("delete from RetailOrderDetails rod where rod.retailOrder=?",[retailOrder])
            }
            RetailOrder.executeUpdate("delete from RetailOrder ro where ro.secondaryDemandOrder = ?", [secondaryDemandOrder])
            secondaryDemandOrder.delete()
            return true
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public RetailOrder search(String fieldName, String fieldValue) {
        String query = "from RetailOrder as docu where docu."+ fieldName +" = '" + fieldValue +"'"
        return RetailOrder.find(query)
    }

    @Transactional
    public boolean saveOrderDetails(RetailOrderDetails retailOrderDetails) {
        try {
            retailOrderDetails.save(vaidate: false, insert: true)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }

        return true
    }

    @Transactional
    public boolean updateOrderDetails(RetailOrderDetails retailOrderDetails) {
        try {
            retailOrderDetails.save(vaidate: false, insert: false)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }

        return true
    }

    @Transactional(readOnly = true)
    public Map listRetailOrder(Object params, Action action) {
        ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
        String searchCondition = ""
        if (params.orderNo) {
            searchCondition = " AND `retail_order`.`order_no` = '${params.orderNo}' "
        }
        else if (params.orderDate) {
            searchCondition = " AND DATE(`retail_order`.`order_date`) = STR_TO_DATE('${params.orderDate}', '${ApplicationConstants.DATE_FORMAT_DB}') "
        }
        sql = new Sql(dataSource)
        String strSql = """
            SELECT
                `retail_order`.`id`
                , `retail_order`.`order_no` AS `orderNo`
                , DATE_FORMAT(`retail_order`.`order_date`, '${ApplicationConstants.DATE_FORMAT_DB}') AS `orderDate`
                , `customer_master`.`name` AS `customerName`
                , `customer_master`.`legacy_id` AS `legacyId`
                , `customer_master`.`code`  AS `customerCode`
                , CONCAT('[', `enterprise_configuration`.`code`,'] ', `enterprise_configuration`.`name`) AS `enterprise`
                , DATE_FORMAT(`retail_order`.`delivery_date`, '${ApplicationConstants.DATE_FORMAT_DB}') AS `deliveryDate`
            FROM
                `retail_order`
                INNER JOIN `customer_master`
                    ON (`retail_order`.`order_placed_for_id` = `customer_master`.`id`)
                INNER JOIN `enterprise_configuration`
                    ON (`retail_order`.`enterprise_id` = `enterprise_configuration`.`id`)
            WHERE `retail_order`.`is_order_submitted` = FALSE
                AND (`retail_order`.`user_created_id` = ${applicationUser.id} OR `retail_order`.`delivery_man_id` = ${applicationUser.customerMasterId})
                ${searchCondition}
            ORDER BY ${action.sortCol.toString()} ${action.sortOrder.toString()} LIMIT ${action.resultPerPage} OFFSET ${action.start}
        """

        List resultList = sql.rows(strSql)
        strSql = """
            SELECT COUNT(*) AS recordCount
            FROM `retail_order`
            WHERE `retail_order`.`is_order_submitted` = FALSE
                AND (`retail_order`.`user_created_id` = ${applicationUser.id} OR `retail_order`.`delivery_man_id` = ${applicationUser.customerMasterId})
                ${searchCondition}
        """
        List recordCountList = sql.rows(strSql)

        return [objList: resultList, count: recordCountList.first().recordCount]
    }

    @Transactional(readOnly = true)
    public List retailOrderNoAutoComplete(Object params) {
        ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
        String filter = ""
        if(params.isSubmitted){
            filter = " AND `retail_order`.`is_order_submitted` = TRUE "
        }else{
            filter = " AND `retail_order`.`is_order_submitted` = FALSE "
        }
        if (params.searchKey) {
            filter += " AND `retail_order`.`order_no` LIKE '%${params.searchKey}%' "
        }
        String query = """
             SELECT
                 `retail_order`.`order_no` AS `orderNo`
                , `customer_master`.`name` AS `customerName`
                , `customer_master`.`legacy_id` AS `legacyId`
                , `customer_master`.`code`  AS `customerCode`
            FROM `retail_order`
                INNER JOIN `customer_master`
                    ON (`retail_order`.`order_placed_for_id` = `customer_master`.`id`)
            WHERE 1 = 1
                AND (`retail_order`.`user_created_id` = ${applicationUser.id} OR `retail_order`.`delivery_man_id` = ${applicationUser.customerMasterId})
                ${filter}
        """
        return new Sql(dataSource).rows(query)
    }

    @Transactional(readOnly = true)
    public List secondaryOrderNoAutoComplete(Object params) {
        ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
        String filter = ""
        if (params.searchKey) {
            filter = " AND `secondary_demand_order`.`order_no` LIKE '%${params.searchKey}%' "
        }
        String query = """
             SELECT
                 `secondary_demand_order`.`order_no` AS `orderNo`
                , `customer_master`.`name` AS `customerName`
                , `customer_master`.`legacy_id` AS `legacyId`
                , `customer_master`.`code`  AS `customerCode`
            FROM `secondary_demand_order`
                INNER JOIN `customer_master`
                    ON (`secondary_demand_order`.`customer_master_id` = `customer_master`.`id`)
            WHERE `secondary_demand_order`.`demand_order_status` = '${DemandOrderStatus.NOT_SUBMITTED}'
                AND `secondary_demand_order`.`user_created_id` = ${applicationUser.id}
                ${filter}
        """
        return new Sql(dataSource).rows(query)
    }
    @Transactional(readOnly = true)
    public Map listRetailsOrderDetailsGrid(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
            SELECT
                `finish_product`.`id`
                , `finish_product`.`code` AS `productCode`
                , `finish_product`.`name` AS `productName`
                , `retail_order_details`.`id` AS `retailOrderDetailsId`
                , `retail_order_details`.`rate`
                , `retail_order_details`.`quantity`
                , `retail_order_details`.`amount`
            FROM
                `retail_order_details`
                INNER JOIN `finish_product`
                    ON (`retail_order_details`.`finish_product_id` = `finish_product`.`id`)
            WHERE `retail_order_details`.`retail_order_id` =:retailOrderId
            ORDER BY `retail_order_details`.`id`
        """
        List objList = sql.rows(strSql, params)
        int resultCount = objList.size()
        return [objList: objList, count: resultCount]
    }

    @Transactional
    public boolean deleteRetailOrderDetails(Object params){
        try{
            RetailOrderDetails.executeUpdate("delete from RetailOrderDetails rod where id=?",[Long.parseLong(params.retailOrderDetailsId)])
            return true
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public boolean submitRetailOrders(Object params){
        try {
            ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
            sql = new Sql(dataSource)
            String strSql = """
                UPDATE `retail_order`
                    SET `is_order_submitted` = TRUE, `last_updated` = NOW(), `user_updated_id` = ${applicationUser.id}
                WHERE `retail_order`.`id` IN (${params.retailOrderIds})
            """
            sql.execute(strSql)
            return true
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listRetailOrderForConsolidate(Object params) {
        try{
            ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
            String operator = ""
            if (params.includePendingOrder) {
                operator = " <= "
            }else{
                operator = " = "
            }

            sql = new Sql(dataSource)
            String strSql = """
                SELECT
                    `retail_order`.`id`
                    , `retail_order`.`order_no` AS `orderNo`
                    , DATE_FORMAT(`retail_order`.`order_date`, '${ApplicationConstants.DATE_FORMAT_DB}') AS `orderDate`
                    , `customer_master`.`name` AS `customerName`
                    , `customer_master`.`legacy_id` AS `legacyId`
                    , `customer_master`.`code`  AS `customerCode`
                    , DATE_FORMAT(`retail_order`.`delivery_date`, '${ApplicationConstants.DATE_FORMAT_DB}') AS `deliveryDate`
                    , (SELECT SUM(`retail_order_details`.`quantity`) FROM `retail_order_details` WHERE `retail_order_details`.`retail_order_id` = `retail_order`.`id`) AS `quantity`
                    , (SELECT SUM(`retail_order_details`.`amount`) FROM `retail_order_details` WHERE `retail_order_details`.`retail_order_id` = `retail_order`.`id`) AS `amount`
                FROM
                    `retail_order`
                    INNER JOIN `customer_master`
                        ON (`retail_order`.`order_placed_for_id` = `customer_master`.`id`)
                WHERE `retail_order`.`is_order_submitted` = TRUE
                    AND `retail_order`.`secondary_demand_order_id` IS NULL
                    AND (`retail_order`.`user_created_id` = ${applicationUser.id} OR `retail_order`.`delivery_man_id` = ${applicationUser.customerMasterId})
                    AND DATE(`retail_order`.`delivery_date`) ${operator} STR_TO_DATE('${params.deliveryDate}', '${ApplicationConstants.DATE_FORMAT_DB}')
            """
            return sql.rows(strSql)
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listRetailsOrderDetailsForConsolidate(Object params) {
        String retailOrderIds = params.retailOrderIds
        String[] retailOrderIdList = retailOrderIds.split(',')
        int totalOrderCount = retailOrderIdList.size()
        String retailOrderId = ""
        String orderFilter = ""
        for(int i = 0; i < totalOrderCount; i++){
            retailOrderId = retailOrderIdList[i]
            if(retailOrderId.length() > 0){
                if(orderFilter == ""){
                    orderFilter += " AND ("
                }else{
                    orderFilter += " OR"
                }
                orderFilter += " `retail_order_details`.`retail_order_id` = ${retailOrderId}"
            }
        }
        orderFilter += " )"
        try{
            sql = new Sql(dataSource)
            String strSql = """
                SELECT
                    `finish_product`.`id`
                    , `finish_product`.`code` AS `productCode`
                    , `finish_product`.`name` AS `productName`
                    , AVG(`retail_order_details`.`rate`) AS `rate`
                    , SUM(`retail_order_details`.`quantity`) AS `quantity`
                    , SUM(`retail_order_details`.`amount`) AS `amount`
                    , round((`retail_order_details`.`quantity` * finish_product.qty_in_ltr),1) AS qtyInLtr
                FROM
                    `retail_order_details`
                    INNER JOIN `finish_product`
                        ON (`retail_order_details`.`finish_product_id` = `finish_product`.`id`)
                WHERE 1 = 1 ${orderFilter}
                GROUP BY `retail_order_details`.`finish_product_id`
                ORDER BY `finish_product`.`name`
            """
            return sql.rows(strSql)
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listSecondaryDetailsFromRetailOrders(String [] retailOrderIdList, Object params) {
        String query = """
            SELECT `finish_product`.`id`,
                `retail_order_details`.`quantity` AS quantity,
                (SELECT `product_price_product`.`price`
                    FROM `customer_master`
                        INNER JOIN `pricing_category` ON (`pricing_category`.id = `customer_master`.`pricing_category_id`)
                        INNER JOIN `product_price_product` ON (`pricing_category`.`id` = `product_price_product`.`pricing_category_id`)
                        INNER JOIN `product_price` ON (`product_price_product`.`product_price_id` = `product_price`.`id`
                            AND `product_price`.`enterprise_configuration_id` = `customer_master`.`enterprise_configuration_id`)
                        INNER JOIN `product_pricing_type` ON (`product_pricing_type`.`id` = `product_price`.`product_pricing_type_id`)
                        INNER JOIN `customer_territory_sub_area` ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`)
                        INNER JOIN `territory_sub_area_product_price` ON (`customer_territory_sub_area`.`territory_sub_area_id` = `territory_sub_area_product_price`.`territory_sub_area_id`
                            AND `territory_sub_area_product_price`.`product_price_id` = `product_price`.`id`)
                    WHERE `customer_master`.`id`= ${params.customerId}
                        AND DATE(NOW()) >= DATE(`product_price`.`date_effective_from`) AND DATE(NOW()) <= DATE(IFNULL(`product_price`.`date_effective_to`, NOW()))
                        AND `customer_territory_sub_area`.`territory_sub_area_id`= ${params.territorySubAreaId}
                        AND `product_price_product`.`finish_product_id` = `finish_product`.`id` ORDER BY `date_effective_from` DESC LIMIT 1) AS `rate`,
                `finish_product`.`code` AS `productCode`,
                `finish_product`.`name` AS `productName`
            FROM `retail_order_details`
                INNER JOIN `finish_product` ON (`finish_product`.`id` = `retail_order_details`.`finish_product_id`)
                INNER JOIN `retail_order` ON (`retail_order`.`id` = `retail_order_details`.`retail_order_id`)
            WHERE
            """
        for(int i = 0; i < retailOrderIdList.size(); i++){
            if(retailOrderIdList[i]) {
                query = query + " retail_order_details.retail_order_id = " + retailOrderIdList[i]
                if (i != retailOrderIdList.size() - 1) {
                    query = query + ' OR '
                }
            }
        }
        query = query + """
            GROUP BY `retail_order_details`.`finish_product_id`
            ORDER BY `retail_order_id`, `finish_product_id`
            """

        return new Sql(dataSource).rows(query)
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

            String[] retailOrderIds = map.get('retailOrderIds')
            String condition = ""
            for (int i = 0; i < retailOrderIds.length; i++) {
                if (retailOrderIds[i]) {
                    if (condition != ""){
                        condition += " OR"
                    }else{
                        condition += " AND ("
                    }
                    condition += """ `retail_order`.`id` = ${retailOrderIds[i]} """
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

    @Transactional
    public SecondaryDemandOrder updateSecondaryDemandOrder(Map map) {
        try {
            SecondaryDemandOrder secondaryDemandOrder = (SecondaryDemandOrder) map.get('secondaryDemandOrder')
            secondaryDemandOrder = secondaryDemandOrder.save(validate: false, insert: false)
            List<SecondaryDemandOrderDetails> secondaryDemandOrderDetailList = map.get('secondaryDemandOrderDetails')
            secondaryDemandOrderDetailList.each { secondaryDemandOrderDetails ->
                if(secondaryDemandOrderDetails.id){
                    secondaryDemandOrderDetails.save(validate: false, insert: false)
                }else{
                    secondaryDemandOrderDetails.save(validate: false, insert: true)
                }
            }
            return secondaryDemandOrder
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Boolean deleteMultipleRetailOrder(Object params) {
        try {
            sql = new Sql(dataSource)
            String retailOrderIdList = params.retailOrderIds
            String[] retailOrderIds = retailOrderIdList.split(",")
            String condition = ""
            if(retailOrderIds.length <= 0){
                return false
            }
            for (int i = 0; i < retailOrderIds.length; i++) {
                if (retailOrderIds[i]) {
                    if (condition != ""){
                        condition += " OR"
                    }else{
                        condition += " AND ("
                    }
                    condition += " `retail_order`.`id` = ${retailOrderIds[i]}"
                }
            }
            if(condition != ""){
                condition += " )"
            }

            String query = """
                DELETE `retail_order_details` FROM `retail_order_details`
                    INNER JOIN `retail_order` ON (`retail_order_details`.`retail_order_id` = `retail_order`.`id`)
                WHERE `retail_order`.`secondary_demand_order_id` IS NULL
                    ${condition}
            """
            int updateCount = sql.executeUpdate(query)
            query = """
                DELETE FROM `retail_order`
                WHERE `retail_order`.`secondary_demand_order_id` IS NULL
                    ${condition}
            """
            updateCount = sql.executeUpdate(query)
            return (updateCount > 0) ? true : false
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Map listSecondaryOrder(Object params, Action action) {
        ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
        String searchCondition = ""
        if (params.orderNo) {
            searchCondition = " AND `secondary_demand_order`.`order_no` = '${params.orderNo}' "
        }
        else if (params.orderDate) {
            searchCondition = " AND DATE(`secondary_demand_order`.`date_order`) = STR_TO_DATE('${params.orderDate}', '$ApplicationConstants.DATE_FORMAT_DB') "
        }
        sql = new Sql(dataSource)
        String strSql = """
            SELECT
                `secondary_demand_order`.`id`
                , `secondary_demand_order`.`order_no` AS `orderNo`
                , DATE_FORMAT(`secondary_demand_order`.`date_order`, '${ApplicationConstants.DATE_FORMAT_DB}') AS `orderDate`
                , `customer_master`.`name` AS `customerName`
                , `customer_master`.`legacy_id` AS `legacyId`
                , `customer_master`.`code`  AS `customerCode`
                , CONCAT('[', `enterprise_configuration`.`code`,'] ', `enterprise_configuration`.`name`) AS `enterprise`
                , DATE_FORMAT(`secondary_demand_order`.`date_deliver`, '${ApplicationConstants.DATE_FORMAT_DB}') AS `deliveryDate`
            FROM
                `secondary_demand_order`
                INNER JOIN `customer_master`
                    ON (`secondary_demand_order`.`customer_master_id` = `customer_master`.`id`)
                INNER JOIN `enterprise_configuration`
                    ON (`customer_master`.`enterprise_configuration_id` = `enterprise_configuration`.`id`)
            WHERE `secondary_demand_order`.`demand_order_status` = '${DemandOrderStatus.NOT_SUBMITTED}'
                AND `secondary_demand_order`.`user_created_id` = ${applicationUser.id}
                ${searchCondition}
            ORDER BY ${action.sortCol.toString()} ${action.sortOrder.toString()} LIMIT ${action.resultPerPage} OFFSET ${action.start}
        """

        List resultList = sql.rows(strSql)
        strSql = """
            SELECT COUNT(*) AS recordCount
            FROM `secondary_demand_order`
            WHERE `secondary_demand_order`.`demand_order_status` = '${DemandOrderStatus.NOT_SUBMITTED}'
                AND `secondary_demand_order`.`user_created_id` = ${applicationUser.id}
                ${searchCondition}
        """
        List recordCountList = sql.rows(strSql)

        return [objList: resultList, count: recordCountList.first().recordCount]
    }

    @Transactional
    public boolean submitSecondaryOrders(Object params){
        try {
            ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
            sql = new Sql(dataSource)
            String strSql = """
                UPDATE `secondary_demand_order`
                    SET `demand_order_status` = '${DemandOrderStatus.UNDER_PROCESS}', `last_updated` = NOW(), `user_updated_id` = ${applicationUser.id}
                WHERE `secondary_demand_order`.`id` IN (${params.secondaryOrderIds})
            """
            sql.execute(strSql)
            return true
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Map listSecondaryOrderDetailsGrid(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
            SELECT
                `finish_product`.`id`
                , `finish_product`.`code` AS `productCode`
                , `finish_product`.`name` AS `productName`
                , `secondary_demand_order_details`.`id` AS `secondaryDemandOrderDetailsId`
                , `secondary_demand_order_details`.`rate`
                , `secondary_demand_order_details`.`quantity`
                , `secondary_demand_order_details`.`amount`
            FROM
                `secondary_demand_order_details`
                INNER JOIN `finish_product`
                    ON (`secondary_demand_order_details`.`finish_product_id` = `finish_product`.`id`)
            WHERE `secondary_demand_order_details`.`secondary_demand_order_id` =:secondaryOrderId
            ORDER BY `secondary_demand_order_details`.`id`
        """
        List objList = sql.rows(strSql, params)
        int resultCount = objList.size()
        return [objList: objList, count: resultCount]
    }

    @Transactional
    public boolean deleteSecondaryOrderDetails(Object params){
        try{
            SecondaryDemandOrderDetails.executeUpdate("delete from SecondaryDemandOrderDetails sdod where id=?",[Long.parseLong(params.secondaryOrderDetailsId)])
            return true
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listRetailOrderForProcessing(Object params) {
        try{
            ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
            String filter = ""
            String joinOption = ""

            if(params.secondaryOrderNo){
                joinOption = """
                    INNER JOIN `secondary_demand_order`
                        ON (`retail_order`.`secondary_demand_order_id` = `secondary_demand_order`.`id`)
                """
                filter += """
                    AND `secondary_demand_order`.`order_no` = '${params.secondaryOrderNo}'
                """
            }else{
                if (params.includePendingOrder) {
                    filter += """
                        AND DATE(`retail_order`.`delivery_date`) <= STR_TO_DATE('${params.deliveryDate}', '${ApplicationConstants.DATE_FORMAT_DB}')
                    """
                }else{
                    filter += """
                        AND DATE(`retail_order`.`delivery_date`) = STR_TO_DATE('${params.deliveryDate}', '${ApplicationConstants.DATE_FORMAT_DB}')
                    """
                }
            }

            sql = new Sql(dataSource)
            String strSql = """
                SELECT
                    `retail_order`.`id`
                    , `retail_order`.`order_no` AS `orderNo`
                    , DATE_FORMAT(`retail_order`.`order_date`, '${ApplicationConstants.DATE_FORMAT_DB}') AS `orderDate`
                    , `customer_master`.`name` AS `customerName`
                    , `customer_master`.`legacy_id` AS `legacyId`
                    , `customer_master`.`code`  AS `customerCode`
                    , DATE_FORMAT(`retail_order`.`delivery_date`, '${ApplicationConstants.DATE_FORMAT_DB}') AS `deliveryDate`
                    , ROUND(SUM(rtd.`quantity` * rtd.rate),2) AS orderAmount
                FROM
                    `retail_order`
                    INNER JOIN `customer_master`
                        ON (`retail_order`.`order_placed_for_id` = `customer_master`.`id`)
                    INNER JOIN retail_order_details AS rtd
                        ON rtd.`retail_order_id` = `retail_order`.id
                    ${joinOption}
                WHERE `retail_order`.`is_order_submitted` = TRUE
                    AND `retail_order`.`secondary_demand_order_id` IS NOT NULL
                    AND `retail_order`.`invoice_id` IS NULL
                    AND `retail_order`.`delivery_man_id` = ${applicationUser.customerMasterId}
                    ${filter}
                 GROUP BY retail_order.`id`
            """
            return sql.rows(strSql)
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listRetailsOrderDetailsForProcessing(Object params) {
        try{
            ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
            sql = new Sql(dataSource)
            String strSql = """
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
                FROM
                    `retail_order_details`
                    INNER JOIN `finish_product`
                        ON (`retail_order_details`.`finish_product_id` = `finish_product`.`id`)
                WHERE `retail_order_details`.`retail_order_id` IN (${params.retailOrderIds})
                GROUP BY `retail_order_details`.`finish_product_id`
                ORDER BY `finish_product`.`id`
            """
            return sql.rows(strSql)
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List secondaryOrderNoAutoCompleteForProcessing(Object params) {
        ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
        String filter = ""
        if (params.searchKey) {
            filter = " AND `secondary_demand_order`.`order_no` LIKE '%${params.searchKey}%' "
        }
        String query = """
             SELECT DISTINCT
                 `secondary_demand_order`.`order_no` AS `orderNo`
                , `customer_master`.`name` AS `customerName`
                , `customer_master`.`legacy_id` AS `legacyId`
                , `customer_master`.`code`  AS `customerCode`
            FROM `retail_order`
                INNER JOIN `secondary_demand_order`
                    ON (`retail_order`.`secondary_demand_order_id` = `secondary_demand_order`.`id`)
                INNER JOIN `customer_master`
                    ON (`secondary_demand_order`.`customer_master_id` = `customer_master`.`id`)
            WHERE `secondary_demand_order`.`demand_order_status` != '${DemandOrderStatus.NOT_SUBMITTED}'
                AND `secondary_demand_order`.`user_created_id` = ${applicationUser.id}
                AND `retail_order`.`invoice_id` IS NULL
                ${filter}
        """
        return new Sql(dataSource).rows(query)
    }

    @Transactional(readOnly = true)
    public List listManualProcessOrderDetailsForProcessing(Object params) {
        try{
            List<Map> processedCustomerStockList = new ArrayList<Map>()
            ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
            List<RetailOrderDetails> retailOrderDetailsList = RetailOrderDetails.withCriteria(){
                eq("retailOrder.id", Long.parseLong(params.retailOrderId))
            }

            retailOrderDetailsList.each { retailOrderDetails ->
                double quantity = retailOrderDetails.quantity
                double tempQty = 0
                List<CustomerStock> customerStockList = CustomerStock.withCriteria() {
                    eq("deliveryMan.id", applicationUser.customerMasterId)
                    eq("finishProduct", retailOrderDetails.finishProduct)
                    gtProperty("inQuantity", "outQuantity")
                }
                int totalItems = customerStockList.size()
                for(int i = 0; i <totalItems; i++){
                    Map processedCustomerStock = new LinkedHashMap()
                    processedCustomerStock.put("id", customerStockList[i].id)
                    processedCustomerStock.put("productId", retailOrderDetails.finishProduct.id)
                    processedCustomerStock.put("productCode", retailOrderDetails.finishProduct.code)
                    processedCustomerStock.put("productName", retailOrderDetails.finishProduct.name)
                    processedCustomerStock.put("rate", retailOrderDetails.rate)

                    if(quantity > 0){
                    //    processedCustomerStock.put("batchNo", customerStockList[i].batchNo)
                        tempQty = quantity
                        double availableQty = customerStockList[i].inQuantity - customerStockList[i].outQuantity
                        processedCustomerStock.put("availableQty", availableQty)
                        quantity -= availableQty
                        if(quantity >= 0){
                            processedCustomerStock.put("processQty", customerStockList[i].inQuantity - customerStockList[i].outQuantity)
                            customerStockList[i].outQuantity = customerStockList[i].inQuantity
                            processedCustomerStock.put("orderQty", availableQty)
                            processedCustomerStock.put("amount", availableQty * retailOrderDetails.rate)
                            processedCustomerStockList.add(processedCustomerStock)
                        }else {
                            processedCustomerStock.put("processQty", tempQty)
                            customerStockList[i].outQuantity += tempQty
                            processedCustomerStock.put("orderQty", tempQty)
                            processedCustomerStock.put("amount", tempQty * retailOrderDetails.rate)
                            processedCustomerStockList.add(processedCustomerStock)
                            break
                        }
                    }
                }
            }

            return processedCustomerStockList
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Object manualProcessRetailOrder(Map data) {
        try{
            Invoice retailInvoice = (Invoice) data.get('retailInvoice')
            retailInvoice.save(validate: false)
            List<InvoiceDetails> invoiceDetailsList = (List<InvoiceDetails>) data.get('retailInvoiceDetails')
            invoiceDetailsList.each { invoiceDetails->
                invoiceDetails.save(validate: false)
            }


            List<CustomerStock> customerStockList = (List<CustomerStock>) data.get('customerStockList')
            customerStockList.each { customerStock->
                customerStock.save()
            }
            List<CustomerStockTransaction> customerStockTransactionList = (List<CustomerStockTransaction>) data.get('customerStockTransactionList')
            customerStockTransactionList.each { customerStockTransaction->
                customerStockTransaction.save(validate: false)
            }
            RetailOrder retailOrder = (RetailOrder) data.get('retailOrder')
            retailOrder.invoice = retailInvoice
            retailOrder.actualDeliveryDate = new Date()
            retailOrder.save()

            return retailInvoice
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Map listRetailInvoice(Object params, Action action) {
        ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
        String searchCondition = ""
        if (params.invoiceNo) {
            searchCondition = " AND `invoice`.`code` = '${params.invoiceNo}' "
        }
        else if (params.invoiceDateFrom && params.invoiceDateTo) {
            searchCondition = " AND DATE(`invoice`.`date_created`) >= STR_TO_DATE('${params.invoiceDateFrom}', '${ApplicationConstants.DATE_FORMAT_DB}') AND  DATE(`invoice`.`date_created`) <= STR_TO_DATE('${params.invoiceDateTo}', '${ApplicationConstants.DATE_FORMAT_DB}')"
        }
        sql = new Sql(dataSource)


        String strSql = """
            SELECT
                `invoice`.`id`
                , `invoice`.`code` AS `invoiceNo`
                , `retail_order`.`order_no` AS `orderNo`
                , DATE_FORMAT(`invoice`.`date_created`, '${ApplicationConstants.DATE_FORMAT_DB}') AS `invoiceDate`
                , `customer_master`.`name` AS `customerName`
                , `customer_master`.`legacy_id` AS `legacyId`
                , `customer_master`.`code`  AS `customerCode`
                , DATE_FORMAT(`retail_order`.`order_date`, '${ApplicationConstants.DATE_FORMAT_DB}') AS `orderDate`
                , ROUND(SUM(invd.`quantity` *invd.unit_price),2) AS invoiceAmount
            FROM
                `invoice`
                INNER JOIN `retail_order` ON (`invoice`.`retail_order_id` = `retail_order`.`id`)
                INNER JOIN invoice_details AS invd
                        ON invd.`invoice_id`= invoice.`id`
                INNER JOIN `customer_master` ON (`retail_order`.`order_placed_for_id` = `customer_master`.`id`)
            WHERE `retail_order`.`delivery_man_id` = ${applicationUser.customerMasterId}
                AND `invoice`.`is_active` = TRUE
                ${searchCondition}
            GROUP BY retail_order.`id`
            ORDER BY ${action.sortCol.toString()} ${action.sortOrder.toString()} LIMIT ${action.resultPerPage} OFFSET ${action.start}
        """

        List resultList = sql.rows(strSql)
        strSql = """
            SELECT COUNT(*) AS invoiceCount
            FROM `invoice`
                INNER JOIN `retail_order` ON (`invoice`.`retail_order_id` = `retail_order`.`id`)
            WHERE `retail_order`.`delivery_man_id` = ${applicationUser.customerMasterId}
                AND `invoice`.`is_active` = TRUE
                ${searchCondition}
        """
        List recordCountList = sql.rows(strSql)

        return [objList: resultList, count: recordCountList.first().invoiceCount]
    }

    @Transactional(readOnly = true)
    public List listNonPaidRetailInvoice(Object params) {
        try{
            String filter = ""
            if(params.fromDate && params.toDate){
                filter += " AND DATE(`invoice`.`date_created`) BETWEEN STR_TO_DATE('${params.fromDate}', '${ApplicationConstants.DATE_FORMAT_DB}') AND STR_TO_DATE('${params.toDate}', '${ApplicationConstants.DATE_FORMAT_DB}')"
            }
            String query = """
                SELECT `invoice`.`id`, `invoice`.`code` AS `invoiceNo`, DATE_FORMAT(`invoice`.`date_created`, '${ApplicationConstants.DATE_FORMAT_DB}') AS `invoiceDate`,
                    (ROUND(`invoice`.`invoice_amount`,2) - ROUND(`invoice`.`paid_amount`,2)) AS `invoiceAmount`
                FROM `invoice`
                WHERE `invoice`.`retail_order_id` IS NOT NULL
                   AND (ROUND(`invoice`.`invoice_amount`,2) - ROUND(`invoice`.`paid_amount`,2)) > 0
                    AND `invoice`.`is_active` = TRUE
                    AND `invoice`.`default_customer_id` = ${params.customerId}
                    ${filter}
                ORDER BY `invoice`.`id`
            """
            return new Sql(dataSource).rows(query)
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Boolean applyCashCollectionForInvoice(Map data) {
        try{
            ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
            CustomerPayment customerPayment = (CustomerPayment) data.get("customerPayment")
            customerPayment.save()

            List<Invoice> retailInvoiceList = (List<Invoice>) data.get("retailInvoiceList")
            retailInvoiceList.each { retailInvoice->
//                retailInvoice.paidAmount = retailInvoice.invoiceAmount
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

    @Transactional
    public Boolean cancelRetailInvoice(Invoice invoice){
        try{
            ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
            List<CustomerStockTransaction> customerStockTransactionList = CustomerStockTransaction.findAllByOutInvoice(invoice)
            customerStockTransactionList.each {  customerStockTransaction->
                CustomerStock customerStock = customerStockTransaction.customerStock
                customerStock.outQuantity -= customerStockTransaction.outQuantity
                customerStock.userUpdated = applicationUser
                customerStock.save()

                customerStockTransaction.inQuantity = 0
                customerStockTransaction.outQuantity = 0
                customerStockTransaction.userUpdated = applicationUser
                customerStockTransaction.save()
            }

            // Update Invoice
            invoice.isActive = false
            invoice.userUpdated = applicationUser
            invoice.lastUpdated = new Date()
            invoice.save()

            // Update Retail Order
            RetailOrder retailOrder = invoice.retailOrder
            retailOrder.invoice = null
            retailOrder.save()
        }catch (Exception ex){
            throw new RuntimeException(ex.message)
        }
    }

}
