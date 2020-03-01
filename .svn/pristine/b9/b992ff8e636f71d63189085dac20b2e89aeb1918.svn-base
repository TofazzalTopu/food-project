package com.bits.bdfp.common

import com.bits.bdfp.audit.NavigationHistory
import com.bits.bdfp.security.MyDashboardService
import com.docu.accesscontrol.FeatureAction
import com.docu.commons.CommonConstants
import com.docu.commons.IAction
import com.docu.commons.Message
import com.docu.commons.SessionManagementService
import com.docu.commons.UserMessageBuilder
import com.docu.security.ApplicationUser
import com.docu.security.ApplicationUserService
import com.docu.security.SecurityAnswer
import com.docu.security.SecurityQuestion
import com.docu.security.UserFirstLoginPasswordVerification
import com.docu.security.setupoption.AjaxSetupOptionAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by prianka.adhikary on 8/9/2015.
 */
@Component("navigationHistoryAction")
class NavigationHistoryAction implements IAction{
    public static final Log log = LogFactory.getLog(NavigationHistoryAction.class)
    @Autowired
    MyDashboardService myDashboardService
    public Object preCondition(def params){
       return null
    }
    Object postCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }
    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            String menuUrl=''
            String controllerName = params.controllerName
            String actionName = params.actionName
            if (controllerName && actionName){
                menuUrl = controllerName+"/"+actionName
                FeatureAction featureAction = FeatureAction.findByMenuUrl(menuUrl)
                NavigationHistory navigationHistory = new NavigationHistory()
                navigationHistory.featureAction = featureAction
                navigationHistory.applicationUser = applicationUser
                navigationHistory.dateCreated = new Date()
                myDashboardService.saveNaqvigation(navigationHistory)
            }

        }
        catch (Exception ex) {
            log.error("NavigationHistoryAction::execute::" + ex.message)
            return UserMessageBuilder.createMessage('Change Password', Message.ERROR, "not saved")
        }
        return UserMessageBuilder.createMessage('Change Password', Message.SUCCESS, "Successfully saved")

    }


}
