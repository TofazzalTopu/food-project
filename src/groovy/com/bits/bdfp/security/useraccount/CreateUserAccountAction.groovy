package com.bits.bdfp.security.useraccount

import com.bits.bdfp.security.UserAccountService
import com.bits.bdfp.settings.ApplicationUserDistributionPoint
import com.bits.bdfp.settings.ApplicationUserEnterprise
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.docu.accesscontrol.ModuleInfo
import com.docu.app.DashboardPortlet
import com.docu.app.UserPortletPreference
import com.docu.app.UserPreference
import com.docu.commons.CommonConstants
import com.docu.commons.IAction
import com.docu.commons.Message
import com.docu.commons.ModuleInfoUtil
import com.docu.commons.ObjectUtil
import com.docu.commons.SessionManagementService
import com.docu.commons.UserMessageBuilder
import com.docu.plugin.cxfws.CommonHelperWebService
import com.docu.security.ApplicationUser
import com.docu.security.ApplicationUserAuthority
import com.docu.security.UserAuthority
import com.docu.security.UserType
import com.docu.security.setupoption.AjaxSetupOptionAction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 11/23/15.
 */
@Component("createUserAccountAction")
class CreateUserAccountAction implements IAction {
    @Autowired
    UserAccountService userAccountService
    @Autowired
    SessionManagementService sessionManagementService
    @Autowired
    CommonHelperWebService commonHelperWebService
    @Autowired
    AjaxSetupOptionAction ajaxSetupOptionAction
    def springSecurityService

    Object preCondition(def params) {
        Map mapInstance = [:]
        ApplicationUser applicationUser = null
        UserPreference userPreference = null
        ApplicationUserAuthority applicationUserAuthority = null
        ApplicationUserEnterprise applicationUserEnterprise = null
        UserPortletPreference userPortletPreference = null

        List nAuthorityList = []
        List nEnterpriseList = []
        List applicationUserAuthorityList = []
        List applicationUserEnterpriseList = []
        List userPortletPreferenceList = []
        List<ApplicationUserDistributionPoint> applicationUserDistributionPointList = new ArrayList<ApplicationUserDistributionPoint>()

        applicationUser = new ApplicationUser()
        if (!params.password.equals(params.confirmPassword)) {
            return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, "Confirm Password does not match with password")
        }

        if(!ajaxSetupOptionAction.isPasswordPatternMatched(params.password)){
            return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, CommonConstants.PASSWORD_VALIDATION_MESSAGE)
        }

        if(!params.userType){
            return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, "User Type is not selected")
        }

        applicationUser.username = params.username
        applicationUser.password = userAccountService.encodePassword(params.password)
        applicationUser.confirmPassword = params.confirmPassword
        applicationUser.fullName = params.fullName

        applicationUser.enabled = true
        applicationUser.accountExpired = false
        applicationUser.accountLocked = false
        applicationUser.passwordExpired = false
        //Date lastUpdated
        applicationUser.emailAddress = params.email
        long userTypeId = Long.parseLong(params.userType)
        applicationUser.userType = UserType.read(userTypeId)

        def applicationUserCount = ApplicationUser.countByUsername(params.username)
        if(applicationUserCount > 0){
            return UserMessageBuilder.createMessage(userAccountService.getUserMessage("New User Account", null), Message.ERROR, "User Name: " + params.username + " already exist")
        }

        /************* Application User to Authority Mapping  ***********/
        if (params.nAuthority instanceof String) {
            nAuthorityList.add(params.nAuthority)
        } else if (params.nAuthority instanceof String[]) {
            nAuthorityList = (List) params.nAuthority
        }
        if (nAuthorityList.size() <= 0) {
            return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.WARNING,
                    userAccountService.getUserMessage("applicationUser.authorityMapping.error", null))
        }

        UserAuthority authority = null
        nAuthorityList.each {
            authority = UserAuthority.read(it)
            applicationUserAuthority = new ApplicationUserAuthority()
            applicationUserAuthority.applicationUser = applicationUser
            applicationUserAuthority.userAuthority = authority//new Authority(id: it)

            if (!applicationUserAuthority.validate()) {
                mapInstance = UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, applicationUserAuthority)
                return mapInstance
            }
            applicationUserAuthorityList.add(applicationUserAuthority)
        }
        /*****************************************************************/

        /************* Application User to Enterprise Mapping  ***********/
        if (params.nEnterprise instanceof String) {
            nEnterpriseList.add(params.nEnterprise)
        } else if (params.nEnterprise instanceof String[]) {
            nEnterpriseList = (List) params.nEnterprise
        }
        if (nEnterpriseList.size() <= 0) {
            return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.WARNING,
                    "No Enterprise selected")
        }

        EnterpriseConfiguration enterpriseConfiguration = null
        nEnterpriseList.each {
            enterpriseConfiguration = EnterpriseConfiguration.read(it)
            applicationUserEnterprise = new ApplicationUserEnterprise()
            applicationUserEnterprise.applicationUser = applicationUser
            applicationUserEnterprise.enterpriseConfiguration = enterpriseConfiguration
            applicationUserEnterprise.userCreated =  (ApplicationUser) sessionManagementService.user
            applicationUserEnterprise.dateCreated = new Date()
            applicationUserEnterprise.isActive = true
            if (!applicationUserEnterprise.validate()) {
                mapInstance = UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, applicationUserEnterprise)
                return mapInstance
            }
            applicationUserEnterpriseList.add(applicationUserEnterprise)
        }
        /*****************************************************************/

        if (!applicationUser.validate()) {
            mapInstance = UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, applicationUser)
            return mapInstance
        }

        if(userTypeId == ApplicationConstants.USER_TYPE_OTHER){
            if(!params.employeeId){
                return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, "Employee is not selected")
            }
            applicationUser.customerMasterId = Long.parseLong(params.employeeId)
            applicationUserDistributionPointList = ObjectUtil.instantiateObjects(params.items, ApplicationUserDistributionPoint.class)
            applicationUserDistributionPointList.each { applicationUserDistributionPoint ->
                applicationUserDistributionPoint.applicationUser = applicationUser
                applicationUserDistributionPoint.isActive = true
                applicationUserDistributionPoint.userCreated =  (ApplicationUser) sessionManagementService.user
                applicationUserDistributionPoint.dateCreated = new Date()
            }

        } else if(userTypeId == ApplicationConstants.USER_TYPE_CUSTOMER){
            if(!params.customerId){
                return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, "Customer is not selected")
            }
            applicationUser.customerMasterId = Long.parseLong(params.customerId)
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

        mapInstance = UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.SUCCESS, null)
        mapInstance.put("applicationUser", applicationUser)
        mapInstance.put("applicationUserAuthority", applicationUserAuthorityList)
        mapInstance.put("userPreference", userPreference)
        mapInstance.put("userPortletPreference", userPortletPreferenceList)
        mapInstance.put("applicationUserEnterprise", applicationUserEnterpriseList)
        mapInstance.put("applicationUserDistributionPoint", applicationUserDistributionPointList)
        return mapInstance
    }

    Object postCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    Object execute(Object params, Object object) {
        List errorList = []
        try {
            if (userAccountService.save((Map) object)) {
                errorList = commonHelperWebService.callApplicationUserAndDependencies(object, "")
            }
        }
        catch (Exception ex) {
            return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, object, ex)
        }
        String errorMessage = "Application User creation failed for <br/>"
        for (int i = 0; i < errorList.size(); i++) {
            errorMessage = errorMessage + errorList.get(i).toString() + " <br/>"
        }
        if (errorList.size() > 0) {
            return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, errorMessage)
        }

        return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.SUCCESS, "Application User is created Successfully")
    }
}
