package com.bits.bdfp.common

import com.bits.bdfp.customer.customermaster.ListCustomerByApplicationUserEnterpriseAction
import com.bits.bdfp.customer.employee.ListEmployeeByApplicationUserAction
import com.bits.bdfp.security.UserAccountService
import com.bits.bdfp.security.useraccount.AjaxUserAccountAction
import com.bits.bdfp.security.useraccount.CreateUserAccountAction
import com.bits.bdfp.security.useraccount.ListUserAccountAction
import com.bits.bdfp.security.useraccount.ListUserAccountControlPanelAction
import com.bits.bdfp.security.useraccount.ListUserAccountDistributionPointAction
import com.bits.bdfp.security.useraccount.ListUsersAction
import com.bits.bdfp.security.useraccount.LockUserAccountAction
import com.bits.bdfp.security.useraccount.PasswordResetAction
import com.bits.bdfp.security.useraccount.ShowUserAccountAction
import com.bits.bdfp.security.useraccount.UnlockUserAccountAction
import com.bits.bdfp.security.useraccount.UpdateUserAccountAction
import com.bits.bdfp.security.useraccount.UpdateUserAccountAuthorityAction
import com.bits.bdfp.security.useraccount.UpdateUserAccountStatusAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseMappingForApplicationUserAction
import com.docu.commons.CommonConstants
import com.docu.commons.Message
import com.docu.commons.ModuleInfoUtil
import com.docu.commons.SessionManagementService
import com.docu.security.ApplicationUser
import com.docu.security.UserAuthority
import com.docu.security.UserType
import com.docu.security.changepassword.action.ChangePasswordAction
import com.docu.security.resetpassword.action.ResetPasswordAction
import com.docu.security.setupoption.AjaxSetupOptionAction
import grails.converters.JSON
import groovy.sql.GroovyRowResult
import org.springframework.beans.factory.annotation.Autowired

class UserAccountController {

    SessionManagementService sessionManagementService
    @Autowired
    AjaxSetupOptionAction  ajaxSetupOptionAction
    @Autowired
    CreateUserAccountAction createUserAccountAction
    @Autowired
    EnterpriseMappingForApplicationUserAction enterpriseMappingForApplicationUserAction
    @Autowired
    ListEmployeeByApplicationUserAction listEmployeeByApplicationUserAction
    @Autowired
    ListUserAccountAction listUserAccountAction
    @Autowired
    ShowUserAccountAction showUserAccountAction
    @Autowired
    UpdateUserAccountAction updateUserAccountAction
    @Autowired
    ResetPasswordAction resetPasswordAction
    @Autowired
    ChangePasswordAction changePasswordAction
    @Autowired
    ListUserAccountControlPanelAction listUserAccountControlPanelAction
    @Autowired
    UpdateUserAccountStatusAction updateUserAccountStatusAction
    @Autowired
    LockUserAccountAction lockUserAccountAction
    @Autowired
    UnlockUserAccountAction unlockUserAccountAction
    @Autowired
    UpdateUserAccountAuthorityAction updateUserAccountAuthorityAction
    @Autowired
    UserAccountService userAccountService
    @Autowired
    AjaxUserAccountAction ajaxUserAccountAction
    @Autowired
    ListUserAccountDistributionPointAction listUserAccountDistributionPointAction
    @Autowired
    ListCustomerByApplicationUserEnterpriseAction listCustomerByApplicationUserEnterpriseAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListUsersAction listUsersAction
    @Autowired
    PasswordResetAction passwordResetAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"];

    def index = {
        redirect(action: "create", params: params)
    }

    def resetPassword = {
        Map securitySettingObject = ajaxUserAccountAction.getSecuritySettingsObject()
        if(CommonConstants.PASSWORD_PATTERN.length() == 0){
            ajaxSetupOptionAction.setPasswordPatternInfo()
        }
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        String passwordPattern = CommonConstants.PASSWORD_PATTERN
        render(view: "resetPassword", model:[securitySettingObject: securitySettingObject,
                                             passwordPattern: passwordPattern, enterpriseList: list as JSON,
                                             size: list.size()] )
    }

    def changePassword = {
        //have to check precondition here
//        Map securitySettingObject = ajaxUserAccountAction.getSecuritySettingsObject()
        if(CommonConstants.PASSWORD_PATTERN.length() == 0){
            ajaxSetupOptionAction.setPasswordPatternInfo()
        }
        String passwordPattern = CommonConstants.PASSWORD_PATTERN
        render(view: "changePassword", model:[passwordPattern: passwordPattern] )
    }

    def list = {
        String message = sessionManagementService.getMessage()
        flash.message = message
        sessionManagementService.removeMessage()
        List applicationUserList = []
        List uniqueList = []
        applicationUserList = (List) listUserAccountAction.execute(null, null)

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
        applicationUserList = showUserAccountAction.execute(params, null)
        applicationUserAuthorityList = showUserAccountAction.showApplicationUserRoles(params)
        def applicationUserEnterpriseList = showUserAccountAction.showApplicationUserEnterprises(params)
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

        return [applicationUserInstance: applicationUserList, applicationUserAuthorityInstance: applicationUserAuthorityList, applicationUserEnterpriseInstance: applicationUserEnterpriseList]
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
        Map enterpriseMap  = (Map)enterpriseMappingForApplicationUserAction.execute(params, null)

        Map result =[:]
        if (enterpriseMap && enterpriseMap.enterpriseList.size() > 0){
            result = ["results": enterpriseMap.enterpriseList, "total": enterpriseMap.enterpriseList.size()]
        }

        String passwordPattern = CommonConstants.PASSWORD_PATTERN
        return [userTypeList: userTypeList, authorityList: authorityList, selected: ["NoSelection"], moduleInfoList: ModuleInfoUtil.instance.list(), enterpriseFlexData: result as JSON,
                enterpriseList: enterpriseMap.enterpriseList, selectedEnterpriseList: enterpriseMap.selectedEnterpriseList, loginUserRole: sessionManagementService.getUserRole(), passwordPattern: passwordPattern]
    }

    def listCustomer = {
        render listCustomerByApplicationUserEnterpriseAction.execute(params, null) as JSON
    }

    def popupCustomerListPanel={
        render(view: 'popUpCustomerList')
    }

    def jsonCustomerList = {
        Map map = [:]
        List data = (List)listCustomerByApplicationUserEnterpriseAction.execute(params, null)
        map.put("aaData", data)
        render map as JSON
    }

    def listEmployee = {
        render listEmployeeByApplicationUserAction.execute(params, null) as JSON
    }

    def popupEmployeeListPanel={
        render(view: 'popUpEmployeeList')
    }

    def jsonEmployeeList = {
        Map map = [:]
        List data = (List)listEmployeeByApplicationUserAction.execute(params, null)
        map.put("aaData", data)
        render map as JSON
    }
    def edit = {
        List uniqueList = []
        List<UserType> userTypeList = UserType.getAll()
        def applicationUserInstance = showUserAccountAction.execute(params, null)

        List<UserAuthority> authorityList = UserAuthority.getAll()
        List<UserAuthority> objects = (List) showUserAccountAction.showSelectedRules(params)
        if (sessionManagementService.getUserRole() != "SA") {
            authorityList.remove(UserAuthority.findByRole("SA"))
            objects.remove(UserAuthority.findByRole("SA"))
        }

        List<String> selectedAuthorityList = objects.collect {
            it.id
        }

        params.put("applicationUserId", params.id)

        Map enterpriseMap  = (Map)enterpriseMappingForApplicationUserAction.execute(params, null)

        Map result =[:]
        if (enterpriseMap && enterpriseMap.enterpriseList.size() > 0){
            result = ["results": enterpriseMap.enterpriseList, "total": enterpriseMap.enterpriseList.size()]
        }

        return [userTypeList: userTypeList, authorityList: authorityList, selectedAuthorityList: selectedAuthorityList, moduleInfoList: ModuleInfoUtil.instance.list(), enterpriseFlexData: result as JSON,
                enterpriseList: enterpriseMap.enterpriseList, selectedEnterpriseList: enterpriseMap.selectedEnterpriseList, loginUserRole: sessionManagementService.getUserRole(), applicationUserInstance: applicationUserInstance]

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
        applicationUserList = listUserAccountAction.execute(null, null)

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
        render(template: "list", model: [gridFeed: gridFeed])

    }

    def saveApplicationUserRemotely = {
        Message msg = null
        def object = createUserAccountAction.preCondition(params)
        msg = object.get("message")
        if (msg.type == Message.SUCCESS) {
            def result = createUserAccountAction.execute(null, object)
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
        def object = updateUserAccountAuthorityAction.preCondition(params)
        msg = object.get("message")
        if (msg.type == Message.SUCCESS) {
            def result = updateUserAccountAuthorityAction.execute(params, object)
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
        def object = updateUserAccountAction.preCondition(params)
        msg = object.get("message")
        if (msg.type == Message.SUCCESS) {
            def result = updateUserAccountAction.execute(params, object)
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
        boolean isExist = showUserAccountAction.checkAvailability(params)
        if (isExist) {
            render '{"isError":0, "message":"User Name already Exist !! Try with another Name"}'
        } else {
            render '{"isError":1, "message":""}'
        }
    }

    def checkUserName = {
        String userId = showUserAccountAction.checkUserName(params)
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
        Object object = listUserAccountControlPanelAction.execute(params, null)
        List<Map> tableData = (List<Map>) object.get('mapList')
        render(view: "userControlPanel", model: [tableData: tableData])
    }

    def updateApplicationUserStatus = {
        Object object = updateUserAccountStatusAction.preCondition(params)
        Message msg = (Message) object.get(Message.MESSAGE)
        if (msg.type == Message.SUCCESS) {
            def result = updateUserAccountStatusAction.execute(null, object)
            msg = result.get(Message.MESSAGE)
        }

        Map result = [message: msg]
        render msg.toString()
    }

    def lockUser = {
        render (view: "/applicationUser/lockUser")
    }


    def lock = {
        Object object = lockUserAccountAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if(message.type == Message.SUCCESS){
            object = lockUserAccountAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def unlock = {
        Object object = unlockUserAccountAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if(message.type == Message.SUCCESS){
            object = unlockUserAccountAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def jsonInternalUserList = {
        List<GroovyRowResult> list = userAccountService.getInternalApplicationUserList()
        render list as JSON
    }

    def listUserAccountDistributionPoint = {
        List listUserAccountDistributionPoint = listUserAccountDistributionPointAction.execute(params, null)
        render listUserAccountDistributionPointAction.postCondition(null, listUserAccountDistributionPoint) as JSON
    }

    def listUser = {
        List list = listUsersAction.execute(params, null)
        render listUsersAction.postCondition(null, list) as JSON
    }

    def passwordReset = {
        Message msg = null
        def object = passwordResetAction.preCondition(params)
        msg = object.get("message")
        if (msg.type == Message.SUCCESS) {
            def result = passwordResetAction.execute(null, object)
            msg = result.get("message")
        }
        render msg
    }
}
