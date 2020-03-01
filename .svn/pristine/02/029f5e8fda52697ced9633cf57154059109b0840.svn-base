package com.docu.security.applicationuser.action

import com.docu.accesscontrol.ModuleInfo
import com.docu.app.DashboardPortlet
import com.docu.app.UserPortletPreference
import com.docu.app.UserPreference
import com.docu.commons.*
import com.docu.plugin.cxfws.CommonHelperWebService
import com.docu.security.ApplicationUser
import com.docu.security.ApplicationUserAuthority
import com.docu.security.ApplicationUserService
import com.docu.security.UserAuthority
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by IntelliJ IDEA.
 * User: bipul.kumar
 * Date: 6/11/11
 * Time: 6:03 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("updateApplicationUserAction")
class UpdateApplicationUserAction implements IAction {
    @Autowired
    SessionManagementService sessionManagementService
    @Autowired
    ApplicationUserService applicationUserService
    @Autowired
    CommonHelperWebService commonHelperWebService

    Object preCondition(def params) {
        Map mapInstance = [:]
        ApplicationUserAuthority applicationUserAuthority = null
        List nAuthorityList = []
        List applicationUserAuthorityList = []

        UserPreference userPreference = null
        UserPortletPreference userPortletPreference = null
        List userPortletPreferenceList = []
        List deleteApplicationUserAuthorityList = []
        List<UserPortletPreference> deleteUserPortletPreferenceList = null
        try{
            ApplicationUser applicationUser = applicationUserService.read(params)
            applicationUser.properties = params
            applicationUser.clearErrors()
            applicationUser.username = params.username
            applicationUser.fullName = params.fullName


            applicationUser.id = Long.parseLong(params.id)
            applicationUser.version = Long.parseLong(params.version)

            if (params.enabled == null || params.enabled == "") {
                applicationUser.enabled = false
            } else {
                applicationUser.enabled = params.enabled
            }
            if (params.accountExpired == null || params.accountExpired == "") {
                applicationUser.accountExpired = false
            } else {
                applicationUser.accountExpired = params.accountExpired
            }
            if (params.accountLocked == null || params.accountLocked == "") {
                applicationUser.accountLocked = false
            } else {
                applicationUser.accountLocked = params.accountLocked
            }

            if (params.passwordExpired == "" || params.passwordExpired == null) {
                applicationUser.passwordExpired = false
            } else {
                applicationUser.passwordExpired = params.passwordExpired
            }
            applicationUser.emailAddress = params.email

            if (!applicationUser.validate()) {
                mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title", null), Message.ERROR, applicationUser)
                return mapInstance
            }

            if (params.nAuthority instanceof String) {
                nAuthorityList.add(params.nAuthority)
            } else if (params.nAuthority instanceof String[]) {
                nAuthorityList = (List) params.nAuthority
            }
            if(nAuthorityList.size()<=0){
                return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title", null), Message.WARNING,
                        applicationUserService.getUserMessage("applicationUser.authorityMapping.error", null) )
            }

            nAuthorityList.each {
                UserAuthority authority = UserAuthority.read(it)
                applicationUserAuthority = new ApplicationUserAuthority()
                applicationUserAuthority.applicationUser = applicationUser
                applicationUserAuthority.userAuthority = authority//new Authority(id: it)

                if (!applicationUserAuthority.validate()) {
                    mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title", null), Message.ERROR, applicationUserAuthority)
                    return mapInstance
                }
                applicationUserAuthorityList.add(applicationUserAuthority)
            }
            deleteApplicationUserAuthorityList = applicationUserService.getUserAuthorityByApplicationUser(applicationUser)

            // UserPreference
            userPreference = applicationUserService.getPreferenceByUserId(applicationUser.id)
            if (!userPreference) {
                userPreference = new UserPreference()
                userPreference.userId = applicationUser.id
                userPreference.colorTheme = CommonConstants.DEFAULT_CURRENT_THEME
            } else {
                // UserPortletPreference
                deleteUserPortletPreferenceList = applicationUserService.getAllUserPortletPreference(userPreference)
            }
            if (!userPreference.validate()) {
                mapInstance = UserMessageBuilder.createMessage("UserPreference.title", Message.ERROR, userPreference)
                return mapInstance
            }

            List<ModuleInfo> moduleInfoList = ModuleInfoUtil.instance.list()
            List<DashboardPortlet> dashboardPortletAllList = DashboardPortlet.list()
            moduleInfoList.each {ModuleInfo mi ->
                List<DashboardPortlet> dashboardPortletList = dashboardPortletAllList.findAll {it.moduleInfoId == mi.id }
                int i = 0
                int j = 0
                dashboardPortletList.each {
                    userPortletPreference = UserPortletPreference.findByPortletAndUserPreference(it, userPreference)
                    if (!userPortletPreference) {
                        userPortletPreference = new UserPortletPreference()
                        userPortletPreference.userPreference = userPreference
                        userPortletPreference.portlet = it
                    }
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
            mapInstance.put("deleteApplicationUserAuthority", deleteApplicationUserAuthorityList)
            mapInstance.put("userPreference", userPreference)
            mapInstance.put("deleteUserPortletPreferenceList", deleteUserPortletPreferenceList)
            mapInstance.put("userPortletPreference", userPortletPreferenceList)

            return mapInstance
        } catch (Exception ex) {
            return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title", null), Message.ERROR, params, ex)
        }

    }

    Object postCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    Object execute(Object params, Object object) {
        List errorList = []
        try {
            if (applicationUserService.update((Map)object)){
                errorList = commonHelperWebService.callApplicationUserAndDependencies(object , "")
            }

        } catch (Exception ex) {
            return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title", null), Message.ERROR, ex)
        }

        String errorMessage = "Application User update failed for <br/>"
        for (int i = 0;i < errorList.size();i++){
            errorMessage = errorMessage + errorList.get(i).toString() + " <br/>"
        }
        if (errorList.size() > 0){
            return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title", null), Message.ERROR, errorMessage)
        }
    }
}
