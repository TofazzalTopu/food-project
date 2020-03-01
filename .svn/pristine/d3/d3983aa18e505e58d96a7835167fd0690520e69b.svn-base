package com.bits.bdfp.settings.businessday

import com.bits.bdfp.settings.bussinessday.LocalHoliday
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("createLocalHolidayAction")
class CreateLocalHolidayAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    LocalHolidayService localHolidayService
    Message message

    protected Message preCondition(Object object, Object params) {
        try {
            LocalHoliday localHoliday = (LocalHoliday) object
            if(localHoliday.validate()){
                List holidayExistList = localHolidayService.getExistingHoliday(localHoliday)
                if(holidayExistList &&  holidayExistList.size()>0){
                    message = this.getMessage(localHoliday, Message.ERROR, "Local Holiday already exist on this day")
                }
                else{
                    message = this.getMessage(localHoliday, Message.SUCCESS, this.SUCCESS_SAVE)
                }
            }
            else{
                message = this.getValidationErrorMessage(localHoliday)
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("BusinessHoliday", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Message execute(Object params, Object object) {
        try {

            ApplicationUser applicationUser = (ApplicationUser) object
            LocalHoliday localHoliday = new LocalHoliday(params)
            localHoliday.dateCreated = new Date()
            localHoliday.userCreated = applicationUser

            message = this.preCondition(localHoliday, params)
            if (message.type == 1) {
                localHoliday = localHolidayService.create(localHoliday)
                if (localHoliday) {
                    message = this.getMessage(localHoliday, Message.SUCCESS, this.SUCCESS_SAVE)
                } else {
                    message = this.getMessage(localHoliday, Message.ERROR, this.FAIL_SAVE)
                }
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            throw ex
        }
    }

    protected Object postCondition(Object params, Object object) {
        return null
    }
}