package com.bits.bdfp.common

import com.bits.bdfp.common.division.CreateDivisionAction
import com.bits.bdfp.common.division.DeleteDivisionAction
import com.bits.bdfp.common.division.ListDivisionAction
import com.bits.bdfp.common.division.UpdateDivisionAction
import com.bits.bdfp.common.division.ReadDivisionAction
import com.bits.bdfp.common.division.SearchDivisionAction

import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class DivisionController {

    @Autowired
    private CreateDivisionAction createDivisionAction
    @Autowired
    private UpdateDivisionAction updateDivisionAction
    @Autowired
    private ListDivisionAction listDivisionAction
    @Autowired
    private DeleteDivisionAction deleteDivisionAction
    @Autowired
    private ReadDivisionAction readDivisionAction
    @Autowired
    private SearchDivisionAction searchDivisionAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listDivisionAction.execute(params, null)
        render listDivisionAction.postCondition(null, list) as JSON
    }

    def show = {
        Division division = new Division()
        render(template: "show", model: [division: division])
    }

    def create = {
        ApplicationUser applicationUser=session?.applicationUser
        Division division = new Division(params)
        Division divisionInstance = createDivisionAction.preCondition(applicationUser, division)
        Message message = null
        if (divisionInstance == null) {
            message = createDivisionAction.getValidationErrorMessage(division)
        } else {
            divisionInstance = createDivisionAction.execute(null, divisionInstance)
            if (divisionInstance) {
                message = createDivisionAction.getMessage(divisionInstance,Message.SUCCESS, "Division Created Successfully")
            } else {
                message = createDivisionAction.getMessage(division,Message.ERROR, createDivisionAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        render readDivisionAction.execute(params, null) as JSON
    }

    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Division division = new Division(params)
        Object object = updateDivisionAction.preCondition(params, applicationUser)
        Message message = null
        if (object == false) {
            message = updateDivisionAction.getValidationErrorMessage(division)
        } else {
            int noOfRows = (int) updateDivisionAction.execute(null, object)
            if (noOfRows > 0) {
                message = updateDivisionAction.getMessage(division,Message.SUCCESS, "Division Updated Successfully")
            } else {
                message = updateDivisionAction.getMessage(division,Message.ERROR, updateDivisionAction.FAIL_UPDATE)
            }
        }
        render message as JSON
    }

    def delete = {
        Division division = deleteDivisionAction.preCondition(params, null);
        Message message = null
        if (division) {
            int rowCount = (int) deleteDivisionAction.execute(null, division);
            if (rowCount > 0) {
                message = deleteDivisionAction.getMessage(division,Message.SUCCESS, deleteDivisionAction.SUCCESS_DELETE);
            } else {
                message = deleteDivisionAction.getMessage(division,Message.ERROR, deleteDivisionAction.FAIL_DELETE);
            }
        } else {
            message = deleteDivisionAction.getMessage(division,Message.ERROR, deleteDivisionAction.ALREADY_DELETED);
        }
        render message as JSON;
    }

    def search = {
        Division division = searchDivisionAction.execute(params.fieldName, params.fieldValue)
        if (division) {
            render division as JSON
        } else {
            render ''
        }

    }
}
