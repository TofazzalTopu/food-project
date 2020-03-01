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
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 11/23/15.
 */
@Component("updateUserAccountAction")
class UpdateUserAccountAction implements IAction {
    @Autowired
    SessionManagementService sessionManagementService
    @Autowired
    UserAccountService userAccountService
    @Autowired
    CommonHelperWebService commonHelperWebService

    Object preCondition(def params) {
        Map mapInstance = [:]
        ApplicationUserAuthority applicationUserAuthority = null
        ApplicationUserEnterprise applicationUserEnterprise = null
        List nAuthorityList = []
        List nEnterpriseList = []
        List applicationUserAuthorityList = []
        List applicationUserEnterpriseList = []

        UserPreference userPreference = null
        UserPortletPreference userPortletPreference = null
        List userPortletPreferenceList = []
        List deleteApplicationUserAuthorityList = []
        List deleteApplicationUserEnterpriseList = []
        List deleteApplicationUserDPList = []
        List<UserPortletPreference> deleteUserPortletPreferenceList = null
        List<ApplicationUserDistributionPoint> applicationUserDistributionPointList = new ArrayList<ApplicationUserDistributionPoint>()
        try{
            ApplicationUser applicationUser = userAccountService.read(params)
            applicationUser.properties = params
            if(!applicationUser.userType){
                return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, "User Type is not selected")
            }
            long userTypeId = applicationUser.userType.id

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
                mapInstance = UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, applicationUser)
                return mapInstance
            }

            if (params.nAuthority instanceof String) {
                nAuthorityList.add(params.nAuthority)
            } else if (params.nAuthority instanceof String[]) {
                nAuthorityList = (List) params.nAuthority
            }
            if(nAuthorityList.size()<=0){
                return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.WARNING,
                        userAccountService.getUserMessage("No Role Selected", null) )
            }

            nAuthorityList.each {
                UserAuthority authority = UserAuthority.read(it)
                applicationUserAuthority = new ApplicationUserAuthority()
                applicationUserAuthority.applicationUser = applicationUser
                applicationUserAuthority.userAuthority = authority//new Authority(id: it)

                if (!applicationUserAuthority.validate()) {
                    mapInstance = UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, applicationUserAuthority)
                    return mapInstance
                }
                applicationUserAuthorityList.add(applicationUserAuthority)
            }
            deleteApplicationUserAuthorityList = userAccountService.getUserAuthorityByApplicationUser(applicationUser)


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
                applicationUserEnterpriseList.add(applicationUserEnterprise)
            }
            deleteApplicationUserEnterpriseList = userAccountService.getUserEnterpriseByApplicationUser(applicationUser)
            /*****************************************************************/

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
                deleteApplicationUserDPList = userAccountService.getUserDistributionPointByApplicationUser(applicationUser)

            } else if(userTypeId == ApplicationConstants.USER_TYPE_CUSTOMER){
                if(!params.customerId){
                    return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, "Customer is not selected")
                }
                applicationUser.customerMasterId = Long.parseLong(params.customerId)
            }

            // UserPreference
            userPreference = userAccountService.getPreferenceByUserId(applicationUser.id)
            if (!userPreference) {
                userPreference = new UserPreference()
                userPreference.userId = applicationUser.id
                userPreference.colorTheme = CommonConstants.DEFAULT_CURRENT_THEME
            } else {
                // UserPortletPreference
                deleteUserPortletPreferenceList = userAccountService.getAllUserPortletPreference(userPreference)
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

            mapInstance = UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.SUCCESS, null)
            mapInstance.put("applicationUser", applicationUser)
            mapInstance.put("applicationUserAuthority", applicationUserAuthorityList)
            mapInstance.put("deleteApplicationUserAuthority", deleteApplicationUserAuthorityList)
            mapInstance.put("applicationUserEnterprise", applicationUserEnterpriseList)
            mapInstance.put("deleteApplicationUserEnterprise", deleteApplicationUserEnterpriseList)
            mapInstance.put("applicationUserDistributionPoint", applicationUserDistributionPointList)
            mapInstance.put("deleteApplicationUserDistributionPoint", deleteApplicationUserDPList)
            mapInstance.put("userPreference", userPreference)
            mapInstance.put("deleteUserPortletPreferenceList", deleteUserPortletPreferenceList)
            mapInstance.put("userPortletPreference", userPortletPreferenceList)

            return mapInstance
        } catch (Exception ex) {
            return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, params, ex)
        }

    }

    Object postCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    Object execute(Object params, Object object) {
        List errorList = []
        try {
            if (userAccountService.update((Map)object)){
                errorList = commonHelperWebService.callApplicationUserAndDependencies(object , "")
            }

        } catch (Exception ex) {
            return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, ex)
        }

        String errorMessage = "Application User update failed for <br/>"
        for (int i = 0;i < errorList.size();i++){
            errorMessage = errorMessage + errorList.get(i).toString() + " <br/>"
        }
        if (errorList.size() > 0){
            return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, errorMessage)
        }
        return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.SUCCESS, "Application User updated Successfully")
    }
}
