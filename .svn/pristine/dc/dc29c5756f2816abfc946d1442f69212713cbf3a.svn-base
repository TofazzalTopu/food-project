package com.docu.security

import com.docu.security.authority.action.CreateAuthorityAction
import com.docu.security.authority.action.DeleteAuthorityAction
import com.docu.security.authority.action.ListAuthorityAction
import com.docu.security.authority.action.UpdateAuthorityAction
import com.docu.security.authority.action.ReadAuthorityAction
import com.docu.security.authority.action.SearchAuthorityAction

import com.docu.commons.Message
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON
import com.docu.commons.SessionManagementService
import com.docu.commons.ModuleInfoUtil

class UserAuthorityController {

    @Autowired
    private CreateAuthorityAction createAuthorityAction
    @Autowired
    private UpdateAuthorityAction updateAuthorityAction
    @Autowired
    private ListAuthorityAction listAuthorityAction
    @Autowired
    private DeleteAuthorityAction deleteAuthorityAction
    @Autowired
    private ReadAuthorityAction readAuthorityAction
    @Autowired
    private SearchAuthorityAction searchAuthorityAction
    UserAuthorityService userAuthorityService
    SessionManagementService sessionManagementService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
        //render listAuthorityAction.execute(params, null) as JSON
        render userAuthorityService.listWithDashboardMapping(params)
    }

    def show = {
        List moduleInfoIds = sessionManagementService.getAllowedModuleList()
        List moduleInfoList = []
        moduleInfoIds.each {
            moduleInfoList.add(ModuleInfoUtil.instance.get(it))
        }
        render(view: "show", model: [moduleInfoList: moduleInfoList])
    }

    def save = {
        Message msg = null
        def object = createAuthorityAction.preCondition(params)
        msg = object.get("message")
        if (msg.type == Message.SUCCESS) {
            def result = createAuthorityAction.execute(null, object)
            msg = result.get("message")
        }
        render msg.toString()
    }

    def edit = {
        Map data = (Map) userAuthorityService.readAuthorityWithAuthorityDashboardMapping(params)//readAuthorityAction.execute(params, null)
        render data as JSON
    }

    def update = {
        Object object = updateAuthorityAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = updateAuthorityAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def delete = {
        Object object = deleteAuthorityAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = deleteAuthorityAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def search = {
        UserAuthority authority = (UserAuthority) searchAuthorityAction.execute(params, null)
        if (authority) {
            render authority as JSON
        }
        else {
            render ''
        }
    }
}
