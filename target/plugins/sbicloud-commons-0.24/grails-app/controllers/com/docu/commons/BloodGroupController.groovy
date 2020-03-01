package com.docu.commons

import com.docu.commons.bloodgroup.CreateBloodGroupAction
import com.docu.commons.bloodgroup.DeleteBloodGroupAction
import com.docu.commons.bloodgroup.ListBloodGroupAction
import com.docu.commons.bloodgroup.UpdateBloodGroupAction
import com.docu.commons.bloodgroup.ReadBloodGroupAction

import com.docu.commons.Message
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class BloodGroupController {

    @Autowired
    private CreateBloodGroupAction createBloodGroupAction
    @Autowired
    private UpdateBloodGroupAction updateBloodGroupAction
    @Autowired
    private ListBloodGroupAction listBloodGroupAction
    @Autowired
    private DeleteBloodGroupAction deleteBloodGroupAction
    @Autowired
    private ReadBloodGroupAction readBloodGroupAction


    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
        render listBloodGroupAction.execute(params, null) as JSON
    }

    def show = {
        render (view: "/commons/bloodGroup/show")
    }

    def save = {
        Object object = createBloodGroupAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = createBloodGroupAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def edit = {
        Map data = (Map) readBloodGroupAction.execute(params, null)
        render data as JSON
    }

    def update = {
        Object object = updateBloodGroupAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = updateBloodGroupAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def delete = {
        Object object = deleteBloodGroupAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = deleteBloodGroupAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

}
