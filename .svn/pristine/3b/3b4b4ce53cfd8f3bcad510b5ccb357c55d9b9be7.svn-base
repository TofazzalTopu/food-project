package com.docu.security

import com.docu.security.urlwrapper.CreateUrlWrapperAction
import com.docu.security.urlwrapper.DeleteUrlWrapperAction
import com.docu.security.urlwrapper.ListUrlWrapperAction
import com.docu.security.urlwrapper.UpdateUrlWrapperAction
import com.docu.security.urlwrapper.ReadUrlWrapperAction

import com.docu.commons.Message
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class UrlWrapperController {

    @Autowired
    private CreateUrlWrapperAction createUrlWrapperAction
    @Autowired
    private UpdateUrlWrapperAction updateUrlWrapperAction
    @Autowired
    private ListUrlWrapperAction listUrlWrapperAction
    @Autowired
    private DeleteUrlWrapperAction deleteUrlWrapperAction
    @Autowired
    private ReadUrlWrapperAction readUrlWrapperAction


    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
        render listUrlWrapperAction.execute(params, null) as JSON
    }

    def show = {

    }

    def save = {
        Object object = createUrlWrapperAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = createUrlWrapperAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def edit = {
        Map data = (Map) readUrlWrapperAction.execute(params, null)
        render data as JSON
    }

    def update = {
        Object object = updateUrlWrapperAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = updateUrlWrapperAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def delete = {
        Object object = deleteUrlWrapperAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = deleteUrlWrapperAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

}
