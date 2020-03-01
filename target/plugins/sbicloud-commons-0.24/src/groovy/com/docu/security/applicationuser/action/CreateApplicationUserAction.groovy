package com.docu.security.applicationuser.action

import com.docu.accesscontrol.ModuleInfo
import com.docu.app.DashboardPortlet
import com.docu.app.UserPortletPreference
import com.docu.app.UserPreference
import com.docu.commons.*
import com.docu.plugin.cxfws.CommonHelperWebService

//import com.docu.cxfws.HelperWebService
import com.docu.security.ApplicationUser
import com.docu.security.ApplicationUserAuthority
import com.docu.security.ApplicationUserService
import com.docu.security.UserAuthority
import com.docu.security.setupoption.AjaxSetupOptionAction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by IntelliJ IDEA.
 * User: bipul.kumar
 * Date: 6/8/11
 * Time: 6:49 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("createApplicationUserAction")
class CreateApplicationUserAction implements IAction {
    @Autowired
    ApplicationUserService applicationUserService
    @Autowired
    SessionManagementService sessionManagementService
    @Autowired
    CommonHelperWebService commonHelperWebService
    @Autowired
    AjaxSetupOptionAction ajaxSetupOptionAction

    Object preCondition(def params) {
        Map mapInstance = [:]
        ApplicationUser applicationUser = null
        UserPreference userPreference = null
        ApplicationUserAuthority applicationUserAuthority = null
        UserPortletPreference userPortletPreference = null

        List nAuthorityList = []
        List applicationUserAuthorityList = []
        List userPortletPreferenceList = []

        applicationUser = new ApplicationUser()
        if (!params.password.equals(params.confirmPassword)) {
            return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title", null), Message.ERROR, "Confirm Password does not match with password")
        }

        if(!ajaxSetupOptionAction.isPasswordPatternMatched(params.password)){
            return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title", null), Message.ERROR, CommonConstants.PASSWORD_VALIDATION_MESSAGE)
        }

        applicationUser.username = params.username
        applicationUser.password = applicationUserService.encodePassword(params.password)
        applicationUser.confirmPassword = params.confirmPassword
        applicationUser.fullName = params.fullName

        applicationUser.enabled = true
        applicationUser.accountExpired = false
        applicationUser.accountLocked = false
        applicationUser.passwordExpired = false
        //Date lastUpdated
        applicationUser.emailAddress = params.email

        if (params.nAuthority instanceof String) {
            nAuthorityList.add(params.nAuthority)
        } else if (params.nAuthority instanceof String[]) {
            nAuthorityList = (List) params.nAuthority
        }
        if (nAuthorityList.size() <= 0) {
            return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title", null), Message.WARNING,
                    applicationUserService.getUserMessage("applicationUser.authorityMapping.error", null))
        }

        UserAuthority authority = null
        nAuthorityList.each {
            authority = UserAuthority.read(it)
            applicationUserAuthority = new ApplicationUserAuthority()
            applicationUserAuthority.applicationUser = applicationUser
            applicationUserAuthority.userAuthority = authority//new Authority(id: it)

            if (!applicationUserAuthority.validate()) {
                mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title", null), Message.ERROR, applicationUserAuthority)
                return mapInstance
            }
            applicationUserAuthorityList.add(applicationUserAuthority)
        }

        if (!applicationUser.validate()) {
            mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title", null), Message.ERROR, applicationUser)
            return mapInstance
        }

        // for portlet Preference
        userPreference = new UserPreference()
        userPreference.colorTheme = CommonConstants.DEFAULT_CURRENT_THEME

        // for user portlet Preference
        List<ModuleInfo> moduleInfoList = ModuleInfoUtil.instance.list()
        List<DashboardPortlet> dashboardPortletAllList = DashboardPortlet.list()
        moduleInfoList.each { ModuleInfo mi ->
            List<DashboardPortlet> dashboardPortletList = dashboardPortletAllList.findAll {
                it.moduleInfoId == mi.id
            } //DashboardPortlet.findAllByModuleInfoId(it.id)
            int i = 0
            int j = 0
            dashboardPortletList.each {
                userPortletPreference = new UserPortletPreference()
                userPortletPreference.userPreference = userPreference
                userPortletPreference.portlet = it
                userPortletPreference.rowIndex = j
                userPortletPreference.colIndex = i

                i++
                if (i % 3 == 0) {
                    i = 0
                    j++
                }
                if (!userPortletPreference.validate()) {
                    mapInstance = UserMessageBuilder.createMessage("UserPortletPreference.title", Message.ERROR, userPortletPreference)
                    return mapInstance
                }
                userPortletPreferenceList.add(userPortletPreference)
            }
        }

        mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title", null), Message.SUCCESS, null)
        mapInstance.put("applicationUser", applicationUser)
        mapInstance.put("applicationUserAuthority", applicationUserAuthorityList)
        mapInstance.put("userPreference", userPreference)
        mapInstance.put("userPortletPreference", userPortletPreferenceList)
        return mapInstance
    }

    Object postCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    Object execute(Object params, Object object) {
        List errorList = []
        try {
            if (applicationUserService.save((Map) object)) {
                errorList = commonHelperWebService.callApplicationUserAndDependencies(object, "")
            }
        }
        catch (Exception ex) {
            return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title", null), Message.ERROR, object, ex)
        }
        String errorMessage = "Application User creation failed for <br/>"
        for (int i = 0; i < errorList.size(); i++) {
            errorMessage = errorMessage + errorList.get(i).toString() + " <br/>"
        }
        if (errorList.size() > 0) {
            return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title", null), Message.ERROR, errorMessage)
        }

        return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title", null), Message.SUCCESS, "Application User is created Successfully")
    }
}
