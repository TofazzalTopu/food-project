package com.bits.bdfp.common

import com.bits.bdfp.common.nationality.CreateNationalityAction
import com.bits.bdfp.common.nationality.DeleteNationalityAction
import com.bits.bdfp.common.nationality.ListNationalityAction
import com.bits.bdfp.common.nationality.UpdateNationalityAction
import com.bits.bdfp.common.nationality.ReadNationalityAction
import com.bits.bdfp.common.nationality.SearchNationalityAction

import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class NationalityController {

    @Autowired
    private CreateNationalityAction createNationalityAction
    @Autowired
    private UpdateNationalityAction updateNationalityAction
    @Autowired
    private ListNationalityAction listNationalityAction
    @Autowired
    private DeleteNationalityAction deleteNationalityAction
    @Autowired
    private ReadNationalityAction readNationalityAction
    @Autowired
    private SearchNationalityAction searchNationalityAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listNationalityAction.execute(params, null)
        render listNationalityAction.postCondition(null, list) as JSON
    }

    def show = {
        Nationality nationality = new Nationality()
        render(template: "show", model: [nationality: nationality])
    }

    def create = {
        Nationality nationality = new Nationality(params)
        Nationality nationalityInstance = createNationalityAction.preCondition(null, nationality)
        Message message = null
        if (nationalityInstance == null) {
            message = createNationalityAction.getValidationErrorMessage(nationality)
        } else {
            nationalityInstance = createNationalityAction.execute(null, nationalityInstance)
            if (nationalityInstance) {
                message = createNationalityAction.getMessage(nationalityInstance,Message.SUCCESS, createNationalityAction.SUCCESS_SAVE)
            } else {
                message = createNationalityAction.getMessage(nationality,Message.ERROR, createNationalityAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        render readNationalityAction.execute(params, null) as JSON
    }

    def update = {
        Nationality nationality = new Nationality(params)
        Object object = updateNationalityAction.preCondition(params, null)
        Message message = null
        if (object == false) {
            message = updateNationalityAction.getValidationErrorMessage(nationality)
        } else {
            int noOfRows = (int) updateNationalityAction.execute(null, object)
            if (noOfRows > 0) {
                message = updateNationalityAction.getMessage(nationality,Message.SUCCESS, updateNationalityAction.SUCCESS_UPDATE)
            } else {
                message = updateNationalityAction.getMessage(nationality,Message.ERROR, updateNationalityAction.FAIL_UPDATE)
            }
        }
        render message as JSON
    }

    def delete = {
        Nationality nationality = deleteNationalityAction.preCondition(params, null);
        Message message = null
        if (nationality) {
            int rowCount = (int) deleteNationalityAction.execute(null, nationality);
            if (rowCount > 0) {
                message = deleteNationalityAction.getMessage(nationality,Message.SUCCESS, deleteNationalityAction.SUCCESS_DELETE);
            } else {
                message = deleteNationalityAction.getMessage(nationality,Message.ERROR, deleteNationalityAction.FAIL_DELETE);
            }
        } else {
            message = deleteNationalityAction.getMessage(nationality,Message.ERROR, deleteNationalityAction.ALREADY_DELETED);
        }
        render message as JSON;
    }

    def search = {
        Nationality nationality = searchNationalityAction.execute(params.fieldName, params.fieldValue)
        if (nationality) {
            render nationality as JSON
        } else {
            render ''
        }

    }
}
