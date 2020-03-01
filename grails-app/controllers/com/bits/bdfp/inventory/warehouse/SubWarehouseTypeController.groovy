package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.inventory.warehouse.subwarehousetype.CreateSubWarehouseTypeAction
import com.bits.bdfp.inventory.warehouse.subwarehousetype.DeleteSubWarehouseTypeAction
import com.bits.bdfp.inventory.warehouse.subwarehousetype.ListSubWarehouseTypeAction
import com.bits.bdfp.inventory.warehouse.subwarehousetype.UpdateSubWarehouseTypeAction
import com.bits.bdfp.inventory.warehouse.subwarehousetype.ReadSubWarehouseTypeAction
import com.bits.bdfp.inventory.warehouse.subwarehousetype.SearchSubWarehouseTypeAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class SubWarehouseTypeController {

    @Autowired
    private CreateSubWarehouseTypeAction createSubWarehouseTypeAction
    @Autowired
    private UpdateSubWarehouseTypeAction updateSubWarehouseTypeAction
    @Autowired
    private ListSubWarehouseTypeAction listSubWarehouseTypeAction
    @Autowired
    private DeleteSubWarehouseTypeAction deleteSubWarehouseTypeAction
    @Autowired
    private ReadSubWarehouseTypeAction readSubWarehouseTypeAction
    @Autowired
    private SearchSubWarehouseTypeAction searchSubWarehouseTypeAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listSubWarehouseTypeAction.execute(params, null)
        render listSubWarehouseTypeAction.postCondition(null, list) as JSON
    }

    def show = {
        SubWarehouseType subWarehouseType = new SubWarehouseType()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [subWarehouseType: subWarehouseType, list: enterpriseList,result:result as JSON])
    }

    def create = {
        ApplicationUser applicationUser=session?.applicationUser
        SubWarehouseType subWarehouseType = new SubWarehouseType(params)
        SubWarehouseType subWarehouseTypeInstance = createSubWarehouseTypeAction.preCondition(applicationUser, subWarehouseType)
        Message message = null
        if (subWarehouseTypeInstance == null) {
            message = createSubWarehouseTypeAction.getValidationErrorMessage(subWarehouseType)
        } else {
            subWarehouseTypeInstance = createSubWarehouseTypeAction.execute(null, subWarehouseTypeInstance)
            if (subWarehouseTypeInstance) {
                message = createSubWarehouseTypeAction.getMessage(subWarehouseTypeInstance,Message.SUCCESS, createSubWarehouseTypeAction.SUCCESS_SAVE)
            } else {
                message = createSubWarehouseTypeAction.getMessage(subWarehouseType,Message.ERROR, createSubWarehouseTypeAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result = readSubWarehouseTypeAction.execute(params, null)
        render result as JSON
    }

    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updateSubWarehouseTypeAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteSubWarehouseTypeAction.execute(params, null)
        render message as JSON
    }


    def search = {
        SubWarehouseType subWarehouseType = searchSubWarehouseTypeAction.execute(params.fieldName, params.fieldValue)
        if (subWarehouseType) {
            render subWarehouseType as JSON
        } else {
            render ''
        }

    }
}
