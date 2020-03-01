package com.bits.bdfp.inventory.warehouse

import com.docu.security.ApplicationUser
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class SubWarehouseService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public SubWarehouse create(Object object) {
        try {
            SubWarehouse subWarehouse = (SubWarehouse) object
            if (subWarehouse.save(false)) {
                return subWarehouse
            }
            return null

        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }

    }

    @Transactional
    public Integer update(Object object) {
        try{
            SubWarehouse subWarehouse = (SubWarehouse) object
            if (subWarehouse.save()) {
                return new Integer(1)
            } else {
                return new Integer(0)
            }
            }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        SubWarehouse subWarehouse = (SubWarehouse) object
        subWarehouse.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<SubWarehouse> objList = SubWarehouse.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = SubWarehouse.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<SubWarehouse> list() {
        return SubWarehouse.list()
    }

    @Transactional(readOnly = true)
    public SubWarehouse read(Long id) {
        return SubWarehouse.read(id)
    }

    @Transactional(readOnly = true)
    public SubWarehouse search(String fieldName, String fieldValue) {
        String query = "from SubWarehouse as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return SubWarehouse.find(query)
    }

    @Transactional(readOnly = true)
    public List listSubWarehouse(Object params,ApplicationUser applicationUser) {
        sql = new Sql(dataSource)
        String  strSql = """SELECT sub_warehouse.id,sub_warehouse.`name`
                                FROM warehouse
                                INNER JOIN `sub_warehouse` ON warehouse.id=`sub_warehouse`.`warehouse_id`

                                INNER JOIN `enterprise_configuration`
                                ON `enterprise_configuration`.id=`warehouse`.`enterprise_configuration_id`

                                INNER JOIN `application_user_enterprise`
                                ON `enterprise_configuration`.id=`application_user_enterprise`.`enterprise_configuration_id`
                                WHERE warehouse.id = ${params.id}
                                AND application_user_enterprise.`application_user_id`= ${applicationUser.id}
                          """

        List list = sql.rows(strSql)
        return list
    }
    @Transactional(readOnly = true)
    public List listSubWarehouseByWarehouse(Object params){
        try{
            sql = new Sql(dataSource)
            String query = """
                SELECT
                    `sub_warehouse`.`id`
                    , CONCAT(`sub_warehouse`.`name`, ' (' , `sub_warehouse_type`.`name`, ')') AS `name`
                FROM
                    `sub_warehouse`
                    INNER JOIN `sub_warehouse_type`
                        ON (`sub_warehouse`.`sub_warehouse_type_id` = `sub_warehouse_type`.`id`)
                WHERE `sub_warehouse`.`warehouse_id` = ${params.id}
                ORDER BY `sub_warehouse`.`id`
            """
            return sql.rows(query)
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Map getSalesmanStocList(Object params){
        try{
            sql = new Sql(dataSource)
            String query = """
                SELECT `customer_stock`.id, `finish_product`.`id` AS productId,
                    `finish_product`.`code` AS productCode, `finish_product`.`name` AS productName,
                    SUM(`customer_stock`.`in_quantity` - `customer_stock`.`out_quantity`) AS availableQuantity
                FROM `customer_stock`
                    INNER JOIN `finish_product` ON (`customer_stock`.`finish_product_id` = `finish_product`.`id`)
                WHERE `customer_stock`.`in_quantity` > `customer_stock`.`out_quantity`
                    AND `customer_stock`.`delivery_man_id` = ${params.salesmanId}
                GROUP BY `finish_product`.`id`
            """
            List list = sql.rows(query)
            return [objList: list, count: list.size()]
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List getSalesmanListByBranch(Object params){
        try{
            sql = new Sql(dataSource)
            String query = """
                SELECT DISTINCT CONCAT(cm.name, ' (Code:', cm.code, ')') AS name, cm.id
                FROM customer_master cm
                    INNER JOIN customer_category cc
                        ON (cc.id = cm.category_id)
                    INNER JOIN customer_territory_sub_area ctsa
                        ON (ctsa.customer_master_id = cm.id)
                    INNER JOIN territory_sub_area ttsa
                        ON (ttsa.id = ctsa.territory_sub_area_id)
                    INNER JOIN distribution_point_territory_sub_area dptsa
                        ON (dptsa.territory_sub_area_id = ttsa.id)
                    INNER JOIN application_user_distribution_point audp
                        ON (audp.distribution_point_id = dptsa.distribution_point_id)
                WHERE cc.name = 'Sales Man'
                    AND audp.application_user_id = ${params.id}
            """
            return sql.rows(query)
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
