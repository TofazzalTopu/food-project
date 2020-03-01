package com.bits.bdfp.security

import com.docu.app.DashboardPortlet
import com.docu.app.UserPortletPreference
import com.docu.app.UserPreference
import com.docu.commons.*
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by IntelliJ IDEA.
 * User: nasrin
 * Date: 9/10/12
 * Time: 10:57 AM
 * To change this template use File | Settings | File Templates.
 */
@Component("updateUserDashboardInfoAction")
class UpdateUserDashboardInfoAction implements IAction {
    public static final Log log = LogFactory.getLog(UpdateUserPortletPreferenceAction.class);

    @Autowired
    MyDashboardService myDashboardService
    @Autowired
    SessionManagementService sessionManagementService

    Object preCondition(def params) {
        List<UserPortletPreference> userPortletPreferenceList = ObjectUtil.instantiateObjects(params.dpp, UserPortletPreference.class)
        Map mapInstance = [:]

        //checking for duplicate dashboard
        List<DashboardPortlet> dashboardPortletList = userPortletPreferenceList.portlet.toList()
        String errorMessage = ""
        for(DashboardPortlet dashboardPortlet : dashboardPortletList){
            int occurrences = Collections.frequency(dashboardPortletList, dashboardPortlet);
            if (occurrences > 1) {
                errorMessage = dashboardPortlet.title + " is selected multiple times"
                break;
            }
        }
        if(errorMessage != ""){
            return UserMessageBuilder.createMessage(myDashboardService.getUserMessage("User Dashboard", null), Message.ERROR,
                    myDashboardService.getUserMessage(errorMessage, null))
        }


        List existingPreferenceList = myDashboardService.getAllDashboardList(sessionManagementService.user.id, Long.parseLong(params.moduleInfoId))
        String portletPreferenceIds = existingPreferenceList.collect {"'${it.userPortletPreferenceId}'"}.join(",").toString().replace("[", "").replace("]", "")

        // Existing Dashboard List by user and module
        List<UserPortletPreference> allDashboardList = UserPortletPreference.findAll("from UserPortletPreference as upp where upp.id in (${portletPreferenceIds})")
        List<UserPortletPreference> changedDashboardList = []
        List<UserPortletPreference> removedDashboardList = []
        List<UserPortletPreference> addedDashboardList = []

        userPortletPreferenceList.each {portletInfo ->
            UserPortletPreference portletPreference = allDashboardList.find {it.portlet.id == portletInfo.portlet.id}
            if (portletPreference) {
                if (portletPreference.domainStatusId == CommonConstants.DOMAIN_STATUS_INACTIVE) {
                    portletPreference.domainStatusId = CommonConstants.DOMAIN_STATUS_ACTIVE
                    portletPreference.rowIndex = portletInfo.rowIndex
                    portletPreference.colIndex = portletInfo.colIndex
                    addedDashboardList.add(portletPreference)
                }

//            UserPortletPreference changedPortletPreference = allDashboardList.find {it.portlet.id == portletInfo.id && it.domainStatusId == CommonConstants.DOMAIN_STATUS_ACTIVE && (it.rowIndex != portletInfo.rowIndex || it.colIndex != portletInfo.colIndex)}
                else {
                    if (portletPreference.rowIndex != portletInfo.rowIndex || portletPreference.colIndex != portletInfo.colIndex) {
                        portletPreference.rowIndex = portletInfo.rowIndex
                        portletPreference.colIndex = portletInfo.colIndex
                        changedDashboardList.add(portletPreference)
                    }
                }
            }
        }
        if (userPortletPreferenceList.size() != allDashboardList.size()) {
            allDashboardList.findAll {it.domainStatusId == CommonConstants.DOMAIN_STATUS_ACTIVE}
            allDashboardList.each {
                if (!userPortletPreferenceList.portlet.contains(it.portlet)) {
                    it.domainStatusId = CommonConstants.DOMAIN_STATUS_INACTIVE
                    removedDashboardList.add(it)
                }
            }
        }

        List<UserPortletPreference> allPortletPreference = []
        allPortletPreference.addAll(addedDashboardList)
        allPortletPreference.addAll(changedDashboardList)
        allPortletPreference.addAll(removedDashboardList)

        mapInstance.put(UserPortletPreference.class.simpleName, allPortletPreference)
        return mapInstance
    }

    Object postCondition(Object object) {
        return null
    }

    Object execute(def params, def object) {
        try {
            List<UserPortletPreference> userPortletPreferenceList = (List<UserPortletPreference>) object.get(UserPortletPreference.class.simpleName)
            myDashboardService.updateUserPortletPreference(userPortletPreferenceList)
            return UserMessageBuilder.createMessage(myDashboardService.getUserMessage("User Dashboard", null), Message.SUCCESS,
                    myDashboardService.getUserMessage("Dashboard Information updated successfully", null))
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(myDashboardService.getUserMessage("User Dashboard", null), Message.ERROR, object, ex)
        }
    }

    public Map saveUserPreferredTheme(String preferredTheme){
        UserPreference userPreference = null
        try {
            String existingTheme = sessionManagementService.getPreferredTheme()
            if (existingTheme != preferredTheme) {
                userPreference = UserPreference.findByUserId(sessionManagementService.user.id)
                if(userPreference){
                    userPreference.colorTheme = preferredTheme
                }
                else{
                    userPreference = new UserPreference()
                    userPreference.colorTheme = preferredTheme
                    userPreference.userId = sessionManagementService.user.id
                }
                myDashboardService.savePreferredTheme(userPreference)
                sessionManagementService.setPreferredTheme(preferredTheme)
                return UserMessageBuilder.createMessage(myDashboardService.getUserMessage("User Theme", null), Message.SUCCESS,
                        myDashboardService.getUserMessage("User Theme has updated successfully", null))

            }else{
                return UserMessageBuilder.createMessage(myDashboardService.getUserMessage("User Theme", null), Message.SUCCESS,
                        myDashboardService.getUserMessage("User Theme is already using", null))
            }
        } catch(Exception ex){
            log.error(ex.message)
            return UserMessageBuilder.createMessage(myDashboardService.getUserMessage("User Theme", null), Message.ERROR, userPreference, ex)
        }
    }
}
