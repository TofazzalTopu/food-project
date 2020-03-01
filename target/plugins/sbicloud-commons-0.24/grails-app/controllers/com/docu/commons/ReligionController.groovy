package com.docu.commons

import com.docu.commons.religion.CreateReligionAction
import com.docu.commons.religion.DeleteReligionAction
import com.docu.commons.religion.ListReligionAction
import com.docu.commons.religion.UpdateReligionAction
import com.docu.commons.religion.ReadReligionAction
import com.docu.commons.religion.SearchReligionAction

import com.docu.commons.Message
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class ReligionController {

    @Autowired
    private CreateReligionAction createReligionAction
    @Autowired
    private UpdateReligionAction updateReligionAction
    @Autowired
    private ListReligionAction listReligionAction
    @Autowired
    private DeleteReligionAction deleteReligionAction
    @Autowired
    private ReadReligionAction readReligionAction
    @Autowired
    private SearchReligionAction searchReligionAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def listJSON = {
        render listReligionAction.execute(params, null) as JSON
    }


    def list = {
        //params.max = Math.min(params.max ? params.int('max') : 10, 100)
        //[religionList: Religion.list(params), religionTotal: Religion.count()]
        render(view: '/commons/religion/list')
    }

    def show = {
        render(view: '/commons/religion/show')
    }

    def create = {
        render(view: '/commons/religion/create')
    }

    def save = {
        Object object = createReligionAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = createReligionAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def edit = {
        Religion religion = (Religion) readReligionAction.execute(params, null)
        render(view: '/commons/religion/list', model: [religion: religion])
    }

    def editJSON = {
        Map data = (Map) readReligionAction.execute(params, null)
        render data as JSON
    }

    def update = {
        Object object = updateReligionAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = updateReligionAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def delete = {
        Object object = deleteReligionAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = deleteReligionAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def search = {
        Religion religion = (Religion) searchReligionAction.execute(params, null)
        if (religion) {
            render religion as JSON
        }
        else {
            render ''
        }
    }

    def showajax = {
        render(view: '/commons/religion/showajax')
    }
}
