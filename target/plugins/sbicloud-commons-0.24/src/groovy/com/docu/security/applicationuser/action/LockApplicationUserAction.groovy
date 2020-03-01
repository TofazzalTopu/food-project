package com.docu.security.applicationuser.action

import com.docu.commons.IAction
import com.docu.commons.Message
import com.docu.commons.ObjectUtil
import com.docu.commons.UserMessageBuilder
import com.docu.plugin.cxfws.CommonHelperWebService
import com.docu.security.ApplicationUser
import com.docu.security.ApplicationUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created with IntelliJ IDEA.
 * User: Bipul
 * Date: 7/3/13
 * Time: 10:20 AM
 * To change this template use File | Settings | File Templates.
 */
@Component('lockApplicationUserAction')
class LockApplicationUserAction implements IAction {
    @Autowired
    ApplicationUserService applicationUserService
    @Autowired
    CommonHelperWebService commonHelperWebService

    @Override
    Object preCondition(def params) {
        Map mapInstance =[:]
        List<ApplicationUser> applicationUserList = ObjectUtil.instantiateObjects(params.items, ApplicationUser.class)
        applicationUserList.each {
            if (!it.validate()) {
                mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title", null), Message.ERROR,it)
                return mapInstance
            }
        }

        mapInstance = UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title", null), Message.SUCCESS, null)
        mapInstance.put(ApplicationUser.class.simpleName, applicationUserList)

        return mapInstance
    }

    @Override
    Object postCondition(object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    Object execute(params, object) {
        List errorList = []
        try {
            if(applicationUserService.lock((Map)object)){
            errorList = commonHelperWebService.callApplicationUserAndDependencies((Map)object,"")
            }
        }
        catch (Exception ex) {
            return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title", null), Message.ERROR, object, ex)
        }

        String errorMessage = "ApplicationUser Lock failed for <br/>"
        for (int i = 0;i < errorList.size();i++){
            errorMessage = errorMessage + errorList.get(i).toString() + " <br/>"
        }
        if (errorList.size() > 0){
            return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title",null), Message.ERROR, errorMessage)
        }
        return UserMessageBuilder.createMessage(applicationUserService.getUserMessage("ApplicationUser.title", null), Message.SUCCESS, "Application User is locked successfully")
    }


}
