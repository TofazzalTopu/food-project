package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.inventory.warehouse.subwarehouse.CreateSubWarehouseAction
import com.bits.bdfp.inventory.warehouse.subwarehouse.DeleteSubWarehouseAction
import com.bits.bdfp.inventory.warehouse.subwarehouse.ListSubWarehouseAction
import com.bits.bdfp.inventory.warehouse.subwarehouse.ListSubWarehouseByWarehouseAction
import com.bits.bdfp.inventory.warehouse.subwarehouse.UpdateSubWarehouseAction
import com.bits.bdfp.inventory.warehouse.subwarehouse.ReadSubWarehouseAction
import com.bits.bdfp.inventory.warehouse.subwarehouse.SearchSubWarehouseAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import groovy.sql.Sql
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

import javax.sql.DataSource

class SubWarehouseController {

    @Autowired
    private CreateSubWarehouseAction createSubWarehouseAction
    @Autowired
    private UpdateSubWarehouseAction updateSubWarehouseAction
    @Autowired
    private ListSubWarehouseAction listSubWarehouseAction
    @Autowired
    private DeleteSubWarehouseAction deleteSubWarehouseAction
    @Autowired
    private ReadSubWarehouseAction readSubWarehouseAction
    @Autowired
    private SearchSubWarehouseAction searchSubWarehouseAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListSubWarehouseByWarehouseAction listSubWarehouseByWarehouseAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listSubWarehouseAction.execute(params, null)
        render listSubWarehouseAction.postCondition(null, list) as JSON
    }

    def show = {
        SubWarehouse subWarehouse = new SubWarehouse()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }

        render(template: "show", model: [subWarehouse: subWarehouse, list: enterpriseList,result:result as JSON])
    }

    def create = {
        ApplicationUser applicationUser=session?.applicationUser
        SubWarehouse subWarehouse = new SubWarehouse(params)
        SubWarehouse subWarehouseInstance = createSubWarehouseAction.preCondition(applicationUser, subWarehouse)
        Message message = null
        if (subWarehouseInstance == null) {
            message = createSubWarehouseAction.getValidationErrorMessage(subWarehouse)
        } else {
            subWarehouseInstance = createSubWarehouseAction.execute(null, subWarehouseInstance)
            if (subWarehouseInstance) {
                message = createSubWarehouseAction.getMessage(subWarehouseInstance,Message.SUCCESS, createSubWarehouseAction.SUCCESS_SAVE)
            } else {
                message = createSubWarehouseAction.getMessage(subWarehouse,Message.ERROR, createSubWarehouseAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result = readSubWarehouseAction.execute(params, null)
        render result as JSON
    }


    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updateSubWarehouseAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteSubWarehouseAction.execute(params, null)
        render message as JSON
    }



    def search = {
        SubWarehouse subWarehouse = searchSubWarehouseAction.execute(params.fieldName, params.fieldValue)
        if (subWarehouse) {
            render subWarehouse as JSON
        } else {
            render ''
        }

    }

    def listSubWarehouse = {
        List list = (List) listSubWarehouseByWarehouseAction.execute(params, null)
        Map result = ["results": list, "total": list.size()]
        render result as JSON
    }

}
