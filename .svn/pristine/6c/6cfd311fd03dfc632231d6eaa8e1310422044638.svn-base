package com.bits.bdfp.common

import com.bits.bdfp.common.designation.CreateDesignationAction
import com.bits.bdfp.common.designation.DeleteDesignationAction
import com.bits.bdfp.common.designation.ListDesignationAction
import com.bits.bdfp.common.designation.UpdateDesignationAction
import com.bits.bdfp.common.designation.ReadDesignationAction
import com.bits.bdfp.common.designation.SearchDesignationAction

import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class DesignationController {

    @Autowired
    private CreateDesignationAction createDesignationAction
    @Autowired
    private UpdateDesignationAction updateDesignationAction
    @Autowired
    private ListDesignationAction listDesignationAction
    @Autowired
    private DeleteDesignationAction deleteDesignationAction
    @Autowired
    private ReadDesignationAction readDesignationAction
    @Autowired
    private SearchDesignationAction searchDesignationAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listDesignationAction.execute(params, null)
        render listDesignationAction.postCondition(null, list) as JSON
    }

    def show = {
        Designation designation = new Designation()
        render(template: "show", model: [designation: designation])
    }

    def create = {
        ApplicationUser applicationUser=session?.applicationUser
        Designation designation = new Designation(params)
        Designation designationInstance = createDesignationAction.preCondition(applicationUser, designation)
        Message message = null
        if (designationInstance == null) {
            message = createDesignationAction.getValidationErrorMessage(designation)
        } else {
            designationInstance = createDesignationAction.execute(null, designationInstance)
            if (designationInstance) {
                message = createDesignationAction.getMessage(designationInstance,Message.SUCCESS, createDesignationAction.SUCCESS_SAVE)
            } else {
                message = createDesignationAction.getMessage(designation,Message.ERROR, createDesignationAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        render readDesignationAction.execute(params, null) as JSON
    }

    def update = {
        Designation designation = new Designation(params)
        Object object = updateDesignationAction.preCondition(params, null)
        Message message = null
        if (object == false) {
            message = updateDesignationAction.getValidationErrorMessage(designation)
        } else {
            int noOfRows = (int) updateDesignationAction.execute(null, object)
            if (noOfRows > 0) {
                message = updateDesignationAction.getMessage(designation,Message.SUCCESS, updateDesignationAction.SUCCESS_UPDATE)
            } else {
                message = updateDesignationAction.getMessage(designation,Message.ERROR, updateDesignationAction.FAIL_UPDATE)
            }
        }
        render message as JSON
    }

    def delete = {
        Designation designation = deleteDesignationAction.preCondition(params, null);
        Message message = null
        if (designation) {
            int rowCount = (int) deleteDesignationAction.execute(null, designation);
            if (rowCount > 0) {
                message = deleteDesignationAction.getMessage(designation,Message.SUCCESS, deleteDesignationAction.SUCCESS_DELETE);
            } else {
                message = deleteDesignationAction.getMessage(designation,Message.ERROR, deleteDesignationAction.FAIL_DELETE);
            }
        } else {
            message = deleteDesignationAction.getMessage(designation,Message.ERROR, deleteDesignationAction.ALREADY_DELETED);
        }
        render message as JSON;
    }

    def search = {
        Designation designation = searchDesignationAction.execute(params.fieldName, params.fieldValue)
        if (designation) {
            render designation as JSON
        } else {
            render ''
        }

    }
}
