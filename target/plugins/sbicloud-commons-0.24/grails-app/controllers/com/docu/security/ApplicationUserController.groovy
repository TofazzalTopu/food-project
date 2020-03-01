package com.docu.security

import com.docu.commons.CommonConstants
import com.docu.commons.Message
import com.docu.commons.ModuleInfoUtil
import com.docu.commons.SessionManagementService
import com.docu.security.applicationuser.action.*
import com.docu.security.changepassword.action.ChangePasswordAction
import com.docu.security.resetpassword.action.ResetPasswordAction
import com.docu.security.setupoption.AjaxSetupOptionAction
import grails.converters.JSON
import groovy.sql.GroovyRowResult
import org.springframework.beans.factory.annotation.Autowired

class ApplicationUserController {
    @Autowired
    CreateApplicationUserAction createApplicationUserAction
    @Autowired
    ListApplicationUserAction listApplicationUserAction
    @Autowired
    ShowApplicationUserAction showApplicationUserAction
    @Autowired
    UpdateApplicationUserAction updateApplicationUserAction
    @Autowired
    ResetPasswordAction resetPasswordAction
    @Autowired
    ChangePasswordAction changePasswordAction
    @Autowired
    ListApplicationUserControlPanelAction listApplicationUserControlPanelAction
    @Autowired
    UpdateApplicationUserStatusAction updateApplicationUserStatusAction
    @Autowired
    LockApplicationUserAction lockApplicationUserAction
    @Autowired
    UnlockApplicationUserAction unlockApplicationUserAction
    @Autowired
    UpdateApplicationUserAuthorityAction updateApplicationUserAuthorityAction

    @Autowired
    AjaxApplicationUserAction ajaxApplicationUserAction
    @Autowired
    AjaxSetupOptionAction  ajaxSetupOptionAction

    ApplicationUserService applicationUserService

    def springSecurityService
    SessionManagementService sessionManagementService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"];

    def index = {
        redirect(action: "create", params: params)
    }

    def resetPassword = {

    }

    def changePassword = {
        //have to check precondition here
        Map securitySettingObject = ajaxApplicationUserAction.getSecuritySettingsObject()
        if(CommonConstants.PASSWORD_PATTERN.length() == 0){
            ajaxSetupOptionAction.setPasswordPatternInfo()
        }
        String passwordPattern = CommonConstants.PASSWORD_PATTERN
        render(view: "changePassword", model:[securitySettingObject: securitySettingObject, passwordPattern: passwordPattern] )
    }

    def list = {
        String message = sessionManagementService.getMessage()
        flash.message = message
        sessionManagementService.removeMessage()
        List applicationUserList = []
        List uniqueList = []
        applicationUserList = (List) listApplicationUserAction.execute(null, null)

        Map dataMap = [:]
        applicationUserList.each { object ->
            if (!dataMap.containsKey(object.id)) {
                dataMap.put(object.id, object)
            }
            else {
                def obj = dataMap.get(object.id)
                if (object.authority) {
                    obj.authority = obj.authority + " , " + object.authority
                }

                dataMap.put(object.id, obj)
            }
        }
        dataMap.each { key, val ->

            uniqueList.add(id: val.id, username: val.username, fullname: val.fullName,
                    enabled: val.enabled,accountlocked: val.accountLocked, accountexpired: val.accountExpired,
                     passwordexpired: val.passwordExpired, datecreated: val.dateCreated, assignedrole: val.authority)

        }

        //String gridFeed = uniqueList as JSON
        //render gridFeed
        render(view: "_list", model: [gridFeed: uniqueList])

        /** this will show default list via Ajax in a Flexigrid view     */
    }

    def show = {
        def applicationUserList
        def applicationUserAuthorityList
//        List uniqueList = []
        applicationUserList = showApplicationUserAction.execute(params, null)
        applicationUserAuthorityList = showApplicationUserAction.showApplicationUserRoles(params)
        /*
        Map dataMap = [:]
        applicationUserList.each { object ->
            if (!dataMap.containsKey(object.id)) {
                dataMap.put(object.id, object)
            }
            else {
                def obj = dataMap.get(object.id)
                if (object.authority) {
                    obj.authority = obj.authority + " , " + object.authority
                }

                dataMap.put(object.id, obj)
            }
        } */

        return [applicationUserInstance: applicationUserList, applicationUserAuthorityInstance: applicationUserAuthorityList]
    }

    def create = {
        List<UserAuthority> authorityList = UserAuthority.getAll()
        List<UserType> userTypeList = UserType.getAll()
        if (sessionManagementService.getUserRole() != "SA") {
            authorityList.remove(UserAuthority.findByRole("SA"))
            userTypeList.remove(UserType.findByTypeName("Super Admin"))
        }
        if(CommonConstants.PASSWORD_PATTERN.length() == 0){
            ajaxSetupOptionAction.setPasswordPatternInfo()
        }

        String passwordPattern = CommonConstants.PASSWORD_PATTERN
        return [userTypeList: userTypeList, authorityList: authorityList, selected: ["NoSelection"], moduleInfoList: ModuleInfoUtil.instance.list(),
                loginUserRole: sessionManagementService.getUserRole(), passwordPattern: passwordPattern]
    }

    def edit = {
        List uniqueList = []
        def applicationUserInstance = showApplicationUserAction.execute(params, null)

        Map dataMap = [:]
        /* applicationUserList.each { object ->
            if (!dataMap.containsKey(object.id)) {
                dataMap.put(object.id, object)
            }
            else {
                def obj = dataMap.get(object.id)
                if (object.authority) {
                    obj.authority = obj.authority + " , " + object.authority
                }

                dataMap.put(object.id, obj)
            }
        } */

        List<UserAuthority> authorityList = UserAuthority.getAll()
        List<UserAuthority> objects = (List) showApplicationUserAction.showSelectedRules(params)
        if (sessionManagementService.getUserRole() != "SA") {
            authorityList.remove(UserAuthority.findByRole("SA"))
            objects.remove(UserAuthority.findByRole("SA"))
        }

        List<String> selectedAuthorityList = objects.collect {
            it.id
        }

        return [authorityList: authorityList, selectedAuthorityList: selectedAuthorityList, applicationUserInstance: applicationUserInstance]
    }

    /**
     * -----------------------------------------
     * for Flexigrid to operate via Ajax
     * -----------------------------------------
     */

    /**
     *
     * This function will return a list of all ApplicationUser via an Ajax call
     * in the form of JSON. A list can be normal list containing all the
     * objects in the back-end, or user can search based on a column,
     * in which case the function will run a case-insensitive LIKE query.
     *
     */
    def getApplicationUserGridJSON = {

        List applicationUserList = []
        List uniqueList = []
        applicationUserList = listApplicationUserAction.execute(null, null)

        Map dataMap = [:]
        applicationUserList.each { object ->
            if (!dataMap.containsKey(object.id)) {
                dataMap.put(object.id, object)
            }
            else {
                def obj = dataMap.get(object.id)
                if (object.authority) {
                    obj.authority = obj.authority + " , " + object.authority
                }

                dataMap.put(object.id, obj)
            }
        }

        dataMap.each { key, val ->

            uniqueList.add(id: val.id, username: val.username, fullname: val.full_name, enabled: val.enabled, accountexpired: val.account_expired,
                    accountlocked: val.account_locked, passwordexpired: val.password_expired, datecreated: val.date_created, assignedrole: val.authority)

        }

        String gridFeed = uniqueList as JSON
        //render gridFeed
        render(view: "_list", model: [gridFeed: gridFeed])

    }

    def saveApplicationUserRemotely = {
        Message msg = null
        def object = createApplicationUserAction.preCondition(params)
        msg = object.get("message")
        if (msg.type == Message.SUCCESS) {
            def result = createApplicationUserAction.execute(null, object)
            msg = result.get("message")
        }
        if (msg.type == Message.SUCCESS) {
            //accessControlSessionManagementService.message .message = msg.toString()
//            sessionManagementService.message = msg.toString()
            render '{"isError":0, "message":' + msg.toString() + '}'
        }
        else {
            render '{"isError":1, "message":' + msg.toString() + '}'
        }
    }

    def updateApplicationUserAuthority = {

        Message msg = null
        def object = updateApplicationUserAuthorityAction.preCondition(params)
        msg = object.get("message")
        if (msg.type == Message.SUCCESS) {
            def result = updateApplicationUserAuthorityAction.execute(params, object)
            msg = result.get("message")
        }
        if (msg.type == Message.SUCCESS) {
            sessionManagementService.message = msg.toString()
            render '{"isError":0,"message":' + msg.toString() + '}'
        }
        else {
            render '{"isError":1, "message":' + msg.toString() + '}'
        }
    }

    def updateApplicationUserRemotely = {

        Message msg = null
        def object = updateApplicationUserAction.preCondition(params)
        msg = object.get("message")
        if (msg.type == Message.SUCCESS) {
            def result = updateApplicationUserAction.execute(params, object)
            msg = result.get("message")
        }
        if (msg.type == Message.SUCCESS) {
            sessionManagementService.message = msg.toString()
            render '{"isError":0,"message":' + msg.toString() + '}'
        }
        else {
            render '{"isError":1, "message":' + msg.toString() + '}'
        }
    }

    def checkAvailability = {
        boolean isExist = showApplicationUserAction.checkAvailability(params)
        if (isExist) {
            render '{"isError":0, "message":"User Name already Exist !! Try with another Name"}'
        } else {
            render '{"isError":1, "message":""}'
        }
    }

    def checkUserName = {
        String userId = showApplicationUserAction.checkUserName(params)
        if (userId != "") {
            //render(view: "resetPassword", model: ['userId': userId])
            render '{"isError":0,"userId":"' + userId + '"}'
            //return ['userId': userId]

        } else {
            render '{"isError":1, "message":"User Name does not exist !! Try with another name"}'
        }
    }

    def checkOldPassword = {
        boolean matchPassword = changePasswordAction.checkOldPassword(params.oldPassword)
        if (matchPassword) {
            render '{"isError":0}'
        } else {
            render '{"isError":1, "message":"Password does not match !! Try again"}'
        }
    }

    def executeResetPassword = {
        Message msg = null
        def object = resetPasswordAction.preCondition(params)
        msg = object.get("message")
        if (msg.type == Message.SUCCESS) {
            def result = resetPasswordAction.execute(null, object)
            msg = result.get("message")
        }
        if (msg.type == Message.SUCCESS) {
            render '{"isError":0, "message":' + msg.toString() + '}'
        }
        else {
            render '{"isError":1, "message":' + msg.toString() + '}'
        }
    }

    def executeChangePassword = {
        Message msg = null
        Map object = (Map) changePasswordAction.preCondition(params)
        msg = object.get(Message.MESSAGE)
        if (msg.type == Message.SUCCESS) {
            Map result = (Map) changePasswordAction.execute(params, object)
            msg = result.get(Message.MESSAGE)
        }
        render msg.toString()
    }

    def userControlPanel = {
        Object object = listApplicationUserControlPanelAction.execute(params, null)
        List<Map> tableData = (List<Map>) object.get('mapList')
        render(view: "userControlPanel", model: [tableData: tableData])
    }

    def updateApplicationUserStatus = {
        Object object = updateApplicationUserStatusAction.preCondition(params)
        Message msg = (Message) object.get(Message.MESSAGE)
        if (msg.type == Message.SUCCESS) {
            def result = updateApplicationUserStatusAction.execute(null, object)
            msg = result.get(Message.MESSAGE)
        }

        Map result = [message: msg]
        render msg.toString()
    }

    def lockUser = {
        render (view: "/applicationUser/lockUser")
    }


    def lock = {
        Object object = lockApplicationUserAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if(message.type == Message.SUCCESS){
            object = lockApplicationUserAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def unlock = {
        Object object = unlockApplicationUserAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if(message.type == Message.SUCCESS){
            object = unlockApplicationUserAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def jsonInternalUserList = {
        List<GroovyRowResult> list = applicationUserService.getInternalApplicationUserList()
        render list as JSON
    }
}
