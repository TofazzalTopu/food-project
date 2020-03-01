package com.bits.bdfp.inventory.warehouse

import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class WarehouseService extends Service {
    static transactional = false
    SpringSecurityService springSecurityService
    DataSource dataSource
    Sql sql

    @Transactional
    public Warehouse create(Object object) {
        try{

            Warehouse warehouse = (Warehouse) object.get('wareHouse')
            if(warehouse.save(false)){
                List<SubWarehouse> subWarehouseList = (List<SubWarehouse>) object.get('subWareHouse')
                subWarehouseList.each { subWarehouse->
                    subWarehouse.save(validate: false, insert: true)
                }
            }
            return warehouse
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object object) {
        try{
            Warehouse warehouse = (Warehouse) object
            if (warehouse.save()) {
                return new Integer(1)
            } else {
                return new Integer(0)
            }
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        Warehouse warehouse = (Warehouse) object
        warehouse.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<Warehouse> objList = Warehouse.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = Warehouse.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<Warehouse> list() {
        return Warehouse.list()
    }

    @Transactional(readOnly = true)
    public Warehouse read(Long id) {
        return Warehouse.get(id)
    }

    @Transactional(readOnly = true)
    public Warehouse search(String fieldName, String fieldValue) {
        String query = "from Warehouse as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return Warehouse.find(query)
    }

    @Transactional(readOnly = true)
    public List listWarehouseByApplicationUser(){
        ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
        sql = new Sql(dataSource)
        String query = """
            SELECT
                `warehouse`.`name`
                , `warehouse`.`id`
            FROM
                `distribution_point_warehouse`
                INNER JOIN `warehouse`
                    ON (`distribution_point_warehouse`.`warehouse_id` = `warehouse`.`id`)
                INNER JOIN `application_user_distribution_point`
                    ON (`application_user_distribution_point`.`distribution_point_id` = `distribution_point_warehouse`.`distribution_point_id`)
            WHERE `application_user_distribution_point`.`application_user_id` = ${applicationUser.id}
        """
        return sql.rows(query)
    }

    @Transactional(readOnly = true)
    public List listFactoryWarehouseByApplicationUser(){
        try{
            ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
            sql = new Sql(dataSource)
            String query = """
                SELECT
                    `warehouse`.`name`
                    , `warehouse`.`id`
                FROM
                    `distribution_point_warehouse`
                    INNER JOIN `warehouse`
                        ON (`distribution_point_warehouse`.`warehouse_id` = `warehouse`.`id`)
                    INNER JOIN `distribution_point`
                        ON (`distribution_point_warehouse`.`distribution_point_id` = `distribution_point`.`id`)
                WHERE `distribution_point`.`is_factory` = TRUE
                    AND `warehouse`.`enterprise_configuration_id` IN (SELECT `enterprise_configuration_id`
                                                                      FROM `application_user_enterprise`
                                                                      WHERE `application_user_id` = ${applicationUser.id})
            """
            return sql.rows(query)
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listNonFactoryWarehouseByApplicationUser(){
        try{
            ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
            sql = new Sql(dataSource)
            String query = """
                SELECT
                    `warehouse`.`name`
                    , `warehouse`.`id`
                FROM
                    `distribution_point_warehouse`
                    INNER JOIN `warehouse`
                        ON (`distribution_point_warehouse`.`warehouse_id` = `warehouse`.`id`)
                    INNER JOIN `distribution_point`
                        ON (`distribution_point_warehouse`.`distribution_point_id` = `distribution_point`.`id`)
                    INNER JOIN `application_user_distribution_point`
                        ON (`application_user_distribution_point`.`distribution_point_id` = `distribution_point_warehouse`.`distribution_point_id`)
                WHERE `distribution_point`.`is_factory` = FALSE
                    AND `warehouse`.`enterprise_configuration_id` IN (SELECT `enterprise_configuration_id`
                                                                      FROM `application_user_enterprise`
                                                                      WHERE `application_user_id` = ${applicationUser.id})
                    AND `application_user_distribution_point`.`application_user_id` = ${applicationUser.id}
            """
            return sql.rows(query)
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
