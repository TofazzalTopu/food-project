package com.docu.commons

import com.docu.commons.gender.CreateGenderAction
import com.docu.commons.gender.DeleteGenderAction
import com.docu.commons.gender.ListGenderAction
import com.docu.commons.gender.UpdateGenderAction
import com.docu.commons.gender.ReadGenderAction
import com.docu.commons.gender.SearchGenderAction

import com.docu.commons.Message
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class GenderController {

    @Autowired
    private CreateGenderAction createGenderAction
    @Autowired
    private UpdateGenderAction updateGenderAction
    @Autowired
    private ListGenderAction listGenderAction
    @Autowired
    private DeleteGenderAction deleteGenderAction
    @Autowired
    private ReadGenderAction readGenderAction
    @Autowired
    private SearchGenderAction searchGenderAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def listJSON = {
        render listGenderAction.execute(params, null) as JSON
    }


    def list = {
        //params.max = Math.min(params.max ? params.int('max') : 10, 100)
        //[genderList: Gender.list(params), genderTotal: Gender.count()]
        render(view: '/commons/gender/list')
    }

    def show = {
        render(view: '/commons/gender/show')
    }

    def create = {
        render(view: '/commons/gender/create')
    }

    def save = {
        Object object = createGenderAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = createGenderAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def edit = {
        Gender gender = (Gender) readGenderAction.execute(params, null)
        render(view: '/commons/gender/list', model: [gender: gender])
    }

    def editJSON = {
        Map data = (Map) readGenderAction.execute(params, null)
        render data as JSON
    }

    def update = {
        Object object = updateGenderAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = updateGenderAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def delete = {
        Object object = deleteGenderAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = deleteGenderAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def search = {
        Gender gender = (Gender) searchGenderAction.execute(params, null)
        if (gender) {
            render gender as JSON
        }
        else {
            render ''
        }
    }

    def showajax = {
        render(view: '/commons/gender/showajax')
    }
}
