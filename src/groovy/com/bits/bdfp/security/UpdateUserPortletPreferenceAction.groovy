package com.bits.bdfp.security

import com.docu.app.UserPortletPreference
import com.docu.commons.IAction
import com.docu.commons.Message
import com.docu.commons.ObjectUtil
import com.docu.commons.UserMessageBuilder
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by IntelliJ IDEA.
 * User: Saiful
 * Date: 12/7/11
 * Time: 6:58 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("updateUserPortletPreferenceAction")
class UpdateUserPortletPreferenceAction implements IAction {
    public static final Log log = LogFactory.getLog(UpdateUserPortletPreferenceAction.class);

    @Autowired
    MyDashboardService myDashboardService

    Object preCondition(def params) {
        List<UserPortletPreference> userPortletPreferenceList = ObjectUtil.instantiateObjects(params.items, UserPortletPreference.class)
        Map mapInstance = [:]
        mapInstance.put(UserPortletPreference.class.simpleName, userPortletPreferenceList)
        return mapInstance
    }

    Object postCondition(Object object) {
        return null
    }

    Object execute(def params, def object) {
        try {
            List<UserPortletPreference> userPortletPreferenceList = (List<UserPortletPreference>) object.get(UserPortletPreference.class.simpleName)
            myDashboardService.updateUserPortletPreference(userPortletPreferenceList)
            return UserMessageBuilder.createMessage(myDashboardService.getUserMessage("paymentVoucher.title", null), Message.SUCCESS,
                    myDashboardService.getUserMessage("voucher.success.method", null))
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(myDashboardService.getUserMessage("paymentVoucher.title", null), Message.ERROR, object, ex)
        }
    }
}
