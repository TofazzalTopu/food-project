package com.bits.bdfp.settings.businessday

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.settings.bussinessday.BusinessHoliday
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("updateBusinessHolidayAction")
class UpdateBusinessHolidayAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    BusinessHolidayService businessHolidayService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

    protected Message preCondition(Object object, Object params) {
        Boolean isError = false
        try{

            BusinessHoliday businessHoliday = (BusinessHoliday) object


            String domain = 'business_holiday'
            String id =  businessHoliday.id

            isError = validationCheckService.validationCheck(domain,id)

            if(businessHoliday.validate()){
                List holidayExistList = businessHolidayService.getExistingHolidayForEdit(businessHoliday)
                if(holidayExistList &&  holidayExistList.size()>0){
                    message = this.getMessage(businessHoliday, Message.ERROR, "Holiday already exist on this day")
                }
                else if (isError){
                    message = this.getMessage('BusinessHoliday', Message.ERROR, 'This Business holiday has already been used')

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
            BusinessHoliday businessHoliday = businessHolidayService.read(Long.parseLong(params?.id?.toString()))
            businessHoliday.properties = params
            businessHoliday.dateLastUpdated = new Date()
            businessHoliday.userLastUpdated = applicationUser
            message = this.preCondition(businessHoliday, params)
            if (message.type == 1) {
                int noOfRows = (int) businessHolidayService.update(businessHoliday)
                if (noOfRows > 0) {
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