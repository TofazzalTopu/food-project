package com.bits.bdfp.settings.businessday

import com.bits.bdfp.settings.bussinessday.BusinessHoliday
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("createBusinessHolidayAction")
class CreateBusinessHolidayAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    BusinessHolidayService businessHolidayService
    Message message

    protected Message preCondition(Object object, Object params) {
        try {
            BusinessHoliday businessHoliday = (BusinessHoliday) object
            if(businessHoliday.validate()){
                List holidayExistList = businessHolidayService.getExistingHoliday(businessHoliday)
                if(holidayExistList &&  holidayExistList.size()>0){
                    message = this.getMessage(businessHoliday, Message.ERROR, "Holiday already exist on this day")
                }
                else{
                    message = this.getMessage(businessHoliday, Message.SUCCESS, this.SUCCESS_SAVE)
                }
            }
            else{
                message = this.getValidationErrorMessage(businessHoliday)
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
            BusinessHoliday businessHoliday = new BusinessHoliday(params)
            businessHoliday.dateCreated = new Date()
            businessHoliday.userCreated = applicationUser

            message = this.preCondition(businessHoliday, params)
            if (message.type == 1) {
                businessHoliday = businessHolidayService.create(businessHoliday)
                if (businessHoliday) {
                    message = this.getMessage(businessHoliday, Message.SUCCESS, this.SUCCESS_SAVE)
                } else {
                    message = this.getMessage(businessHoliday, Message.ERROR, this.FAIL_SAVE)
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