package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.inventory.warehouse.warehouse.CreateWarehouseAction
import com.bits.bdfp.inventory.warehouse.warehouse.DeleteWarehouseAction
import com.bits.bdfp.inventory.warehouse.warehouse.ListWarehouseAction
import com.bits.bdfp.inventory.warehouse.warehouse.UpdateWarehouseAction
import com.bits.bdfp.inventory.warehouse.warehouse.ReadWarehouseAction
import com.bits.bdfp.inventory.warehouse.warehouse.SearchWarehouseAction
import com.bits.bdfp.settings.businessunitconfiguration.ListBusinessUnitEnterpriseAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import groovy.sql.Sql
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

import javax.sql.DataSource

class WarehouseController {

    @Autowired
    private CreateWarehouseAction createWarehouseAction
    @Autowired
    private UpdateWarehouseAction updateWarehouseAction
    @Autowired
    private ListWarehouseAction listWarehouseAction
    @Autowired
    private DeleteWarehouseAction deleteWarehouseAction
    @Autowired
    private ReadWarehouseAction readWarehouseAction
    @Autowired
    private SearchWarehouseAction searchWarehouseAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListBusinessUnitEnterpriseAction businessUnitEnterpriseAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listWarehouseAction.execute(params, null)
        render listWarehouseAction.postCondition(null, list) as JSON
    }

    def show = {
        Warehouse warehouse = new Warehouse()
        Map result =[:]
        ApplicationUser applicationUser = session?.applicationUser
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [warehouse: warehouse, list: enterpriseList,result:result as JSON])
    }

    def create = {
        ApplicationUser applicationUser=session?.applicationUser
        Warehouse warehouse = new Warehouse(params)
        Warehouse warehouseInstance = createWarehouseAction.preCondition(applicationUser, warehouse)
        Message message = null
        if (warehouseInstance == null) {
            message = createWarehouseAction.getValidationErrorMessage(warehouse)
        } else {
            warehouseInstance = createWarehouseAction.execute(null, warehouseInstance)
            if (warehouseInstance) {
                message = createWarehouseAction.getMessage(warehouseInstance, Message.SUCCESS, createWarehouseAction.SUCCESS_SAVE)
            } else {
                message = createWarehouseAction.getMessage(warehouse, Message.ERROR, createWarehouseAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result = readWarehouseAction.execute(params, null)
        render result as JSON
    }
    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updateWarehouseAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteWarehouseAction.execute(params, null)
        render message as JSON

    }



    def search = {
        Warehouse warehouse = searchWarehouseAction.execute(params.fieldName, params.fieldValue)
        if (warehouse) {
            render warehouse as JSON
        } else {
            render ''
        }

    }

    def listWarehouse = {
        ApplicationUser applicationUser = session?.applicationUser
        List list= listWarehouseByEnterprise(params, applicationUser)
        Map result = ["results": list, "total": list.size()]
        render result as JSON
    }
    DataSource dataSource
    Sql sql
    public List listWarehouseByEnterprise(Object params,ApplicationUser applicationUser) {
        sql = new Sql(dataSource)
        String  strSql = """SELECT warehouse.id,CONCAT(warehouse.name,' [',warehouse.code,']') AS name
                            FROM `warehouse`
                            INNER JOIN `enterprise_configuration` ON warehouse.`enterprise_configuration_id`=enterprise_configuration.`id`
                            INNER JOIN `application_user_enterprise` ON `enterprise_configuration`.id=`application_user_enterprise`.`enterprise_configuration_id`
                            WHERE warehouse.`enterprise_configuration_id`=${params.id} AND application_user_enterprise.`application_user_id`=${applicationUser.id}
                            AND application_user_enterprise.`is_active` IS TRUE
                          """

        List resultList = sql.rows(strSql.toString())
        return resultList
    }



}
