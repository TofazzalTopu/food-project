package com.bits.bdfp.common

import com.bits.bdfp.common.unioninfo.CreateUnionInfoAction
import com.bits.bdfp.common.unioninfo.DeleteUnionInfoAction
import com.bits.bdfp.common.unioninfo.ListUnionInfoAction
import com.bits.bdfp.common.unioninfo.UpdateUnionInfoAction
import com.bits.bdfp.common.unioninfo.ReadUnionInfoAction
import com.bits.bdfp.common.unioninfo.SearchUnionInfoAction

import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class UnionInfoController {

    @Autowired
    private CreateUnionInfoAction createUnionInfoAction
    @Autowired
    private UpdateUnionInfoAction updateUnionInfoAction
    @Autowired
    private ListUnionInfoAction listUnionInfoAction
    @Autowired
    private DeleteUnionInfoAction deleteUnionInfoAction
    @Autowired
    private ReadUnionInfoAction readUnionInfoAction
    @Autowired
    private SearchUnionInfoAction searchUnionInfoAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listUnionInfoAction.execute(params, null)
        render listUnionInfoAction.postCondition(null, list) as JSON
    }

    def show = {
        UnionInfo unionInfo = new UnionInfo()
        render(template: "show", model: [unionInfo: unionInfo])
    }

    def create = {
        ApplicationUser applicationUser=session?.applicationUser
        UnionInfo unionInfo = new UnionInfo(params)
        UnionInfo unionInfoInstance = createUnionInfoAction.preCondition(applicationUser, unionInfo)
        Message message = null
        if (unionInfoInstance == null) {
            message = createUnionInfoAction.getValidationErrorMessage(unionInfo)
        } else {
            unionInfoInstance = createUnionInfoAction.execute(null, unionInfoInstance)
            if (unionInfoInstance) {
                message = createUnionInfoAction.getMessage(unionInfoInstance,Message.SUCCESS, "Union Created Successfully")
            } else {
                message = createUnionInfoAction.getMessage(unionInfo,Message.ERROR, createUnionInfoAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        render readUnionInfoAction.execute(params, null) as JSON
    }

    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        UnionInfo unionInfo = new UnionInfo(params)
        Object object = updateUnionInfoAction.preCondition(params, applicationUser)
        Message message = null
        if (object == false) {
            message = updateUnionInfoAction.getValidationErrorMessage(unionInfo)
        } else {
            int noOfRows = (int) updateUnionInfoAction.execute(null, object)
            if (noOfRows > 0) {
                message = updateUnionInfoAction.getMessage(unionInfo,Message.SUCCESS, "Union Updated Successfully")
            } else {
                message = updateUnionInfoAction.getMessage(unionInfo,Message.ERROR, updateUnionInfoAction.FAIL_UPDATE)
            }
        }
        render message as JSON
    }

    def delete = {
        UnionInfo unionInfo = deleteUnionInfoAction.preCondition(params, null);
        Message message = null
        if (unionInfo) {
            int rowCount = (int) deleteUnionInfoAction.execute(null, unionInfo);
            if (rowCount > 0) {
                message = deleteUnionInfoAction.getMessage(unionInfo,Message.SUCCESS, deleteUnionInfoAction.SUCCESS_DELETE);
            } else {
                message = deleteUnionInfoAction.getMessage(unionInfo,Message.ERROR, deleteUnionInfoAction.FAIL_DELETE);
            }
        } else {
            message = deleteUnionInfoAction.getMessage(unionInfo,Message.ERROR, deleteUnionInfoAction.ALREADY_DELETED);
        }
        render message as JSON;
    }

    def search = {
        UnionInfo unionInfo = searchUnionInfoAction.execute(params.fieldName, params.fieldValue)
        if (unionInfo) {
            render unionInfo as JSON
        } else {
            render ''
        }

    }
}
