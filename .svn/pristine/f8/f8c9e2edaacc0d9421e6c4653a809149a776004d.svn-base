package com.bits.bdfp.security.useraccount

import com.bits.bdfp.security.UserAccountService
import com.docu.commons.IAction
import com.docu.commons.Message
import com.docu.commons.ObjectUtil
import com.docu.commons.UserMessageBuilder
import com.docu.plugin.cxfws.CommonHelperWebService
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 11/23/15.
 */
@Component("unlockUserAccountAction")
class UnlockUserAccountAction implements IAction {
    @Autowired
    UserAccountService userAccountService
    @Autowired
    CommonHelperWebService commonHelperWebService

    @Override
    Object preCondition(def params) {
        Map mapInstance =[:]
        List<ApplicationUser> applicationUserList = ObjectUtil.instantiateObjects(params.items, ApplicationUser.class)
        applicationUserList.each {
            if (!it.validate()) {
                mapInstance = UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR,it)
                return mapInstance
            }
        }
        mapInstance = UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.SUCCESS, null)
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
            if(userAccountService.unlock((Map)object)){
                errorList = commonHelperWebService.callApplicationUserAndDependencies((Map)object,"")
            }
        }
        catch (Exception ex) {
            return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.ERROR, object, ex)
        }
        String errorMessage = "ApplicationUser Unlock failed for <br/>"
        for (int i = 0;i < errorList.size();i++){
            errorMessage = errorMessage + errorList.get(i).toString() + " <br/>"
        }
        if (errorList.size() > 0){
            return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title",null), Message.ERROR, errorMessage)
        }

        return UserMessageBuilder.createMessage(userAccountService.getUserMessage("ApplicationUser.title", null), Message.SUCCESS, "Application User is unlocked successfully")
    }
}
