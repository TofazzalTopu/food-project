package com.bits.bdfp.common

import com.bits.bdfp.common.thanaupazilapouroshova.CreateThanaUpazilaPouroshovaAction
import com.bits.bdfp.common.thanaupazilapouroshova.DeleteThanaUpazilaPouroshovaAction
import com.bits.bdfp.common.thanaupazilapouroshova.ListThanaUpazilaPouroshovaAction
import com.bits.bdfp.common.thanaupazilapouroshova.UpdateThanaUpazilaPouroshovaAction
import com.bits.bdfp.common.thanaupazilapouroshova.ReadThanaUpazilaPouroshovaAction
import com.bits.bdfp.common.thanaupazilapouroshova.SearchThanaUpazilaPouroshovaAction

import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class ThanaUpazilaPouroshovaController {

    @Autowired
    private CreateThanaUpazilaPouroshovaAction createThanaUpazilaPouroshovaAction
    @Autowired
    private UpdateThanaUpazilaPouroshovaAction updateThanaUpazilaPouroshovaAction
    @Autowired
    private ListThanaUpazilaPouroshovaAction listThanaUpazilaPouroshovaAction
    @Autowired
    private DeleteThanaUpazilaPouroshovaAction deleteThanaUpazilaPouroshovaAction
    @Autowired
    private ReadThanaUpazilaPouroshovaAction readThanaUpazilaPouroshovaAction
    @Autowired
    private SearchThanaUpazilaPouroshovaAction searchThanaUpazilaPouroshovaAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listThanaUpazilaPouroshovaAction.execute(params, null)
        render listThanaUpazilaPouroshovaAction.postCondition(null, list) as JSON
    }

    def show = {
        ThanaUpazilaPouroshova thanaUpazilaPouroshova = new ThanaUpazilaPouroshova()
        render(template: "show", model: [thanaUpazilaPouroshova: thanaUpazilaPouroshova])
    }

    def create = {
        ApplicationUser applicationUser=session?.applicationUser
        ThanaUpazilaPouroshova thanaUpazilaPouroshova = new ThanaUpazilaPouroshova(params)
        ThanaUpazilaPouroshova thanaUpazilaPouroshovaInstance = createThanaUpazilaPouroshovaAction.preCondition(applicationUser, thanaUpazilaPouroshova)
        Message message = null
        if (thanaUpazilaPouroshovaInstance == null) {
            message = createThanaUpazilaPouroshovaAction.getValidationErrorMessage(thanaUpazilaPouroshova)
        } else {
            thanaUpazilaPouroshovaInstance = createThanaUpazilaPouroshovaAction.execute(null, thanaUpazilaPouroshovaInstance)
            if (thanaUpazilaPouroshovaInstance) {
                message = createThanaUpazilaPouroshovaAction.getMessage(thanaUpazilaPouroshovaInstance,Message.SUCCESS, "Thana/Upazila Created Successfully")
            } else {
                message = createThanaUpazilaPouroshovaAction.getMessage(thanaUpazilaPouroshova,Message.ERROR, createThanaUpazilaPouroshovaAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        render readThanaUpazilaPouroshovaAction.execute(params, null) as JSON
    }

    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        ThanaUpazilaPouroshova thanaUpazilaPouroshova = new ThanaUpazilaPouroshova(params)
        Object object = updateThanaUpazilaPouroshovaAction.preCondition(params, applicationUser)
        Message message = null
        if (object == false) {
            message = updateThanaUpazilaPouroshovaAction.getValidationErrorMessage(thanaUpazilaPouroshova)
        } else {
            int noOfRows = (int) updateThanaUpazilaPouroshovaAction.execute(null, object)
            if (noOfRows > 0) {
                message = updateThanaUpazilaPouroshovaAction.getMessage(thanaUpazilaPouroshova,Message.SUCCESS, "Thana/Upazila Updated Successfully")
            } else {
                message = updateThanaUpazilaPouroshovaAction.getMessage(thanaUpazilaPouroshova,Message.ERROR, updateThanaUpazilaPouroshovaAction.FAIL_UPDATE)
            }
        }
        render message as JSON
    }

    def delete = {
        ThanaUpazilaPouroshova thanaUpazilaPouroshova = deleteThanaUpazilaPouroshovaAction.preCondition(params, null);
        Message message = null
        if (thanaUpazilaPouroshova) {
            int rowCount = (int) deleteThanaUpazilaPouroshovaAction.execute(null, thanaUpazilaPouroshova);
            if (rowCount > 0) {
                message = deleteThanaUpazilaPouroshovaAction.getMessage(thanaUpazilaPouroshova,Message.SUCCESS, deleteThanaUpazilaPouroshovaAction.SUCCESS_DELETE);
            } else {
                message = deleteThanaUpazilaPouroshovaAction.getMessage(thanaUpazilaPouroshova,Message.ERROR, deleteThanaUpazilaPouroshovaAction.FAIL_DELETE);
            }
        } else {
            message = deleteThanaUpazilaPouroshovaAction.getMessage(thanaUpazilaPouroshova,Message.ERROR, deleteThanaUpazilaPouroshovaAction.ALREADY_DELETED);
        }
        render message as JSON;
    }

    def search = {
        ThanaUpazilaPouroshova thanaUpazilaPouroshova = searchThanaUpazilaPouroshovaAction.execute(params.fieldName, params.fieldValue)
        if (thanaUpazilaPouroshova) {
            render thanaUpazilaPouroshova as JSON
        } else {
            render ''
        }

    }
}
