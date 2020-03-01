package com.bits.bdfp.settings.businessday

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.settings.bussinessday.LocalHoliday
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("updateLocalHolidayAction")
class UpdateLocalHolidayAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    LocalHolidayService localHolidayService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

    protected Message preCondition(Object object, Object params) {
        Boolean isError = false
        try{

            LocalHoliday localHoliday = (LocalHoliday) object

            String domain = 'local_holiday'
            String id =  localHoliday.id

            isError = validationCheckService.validationCheck(domain,id)

            if(localHoliday.validate()){
                List holidayExistList = localHolidayService.getExistingHolidayForEdit(localHoliday)
                if(holidayExistList &&  holidayExistList.size()>0){
                    message = this.getMessage(localHoliday, Message.ERROR, "Local Holiday already exist on this day")
                }
                else if (isError){
                    message = this.getMessage('LocalHoliday', Message.ERROR, 'This Local holiday has already been used')

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
            message = this.getMessage("LocalHoliday", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Message execute(Object params, Object object) {
        try {

            ApplicationUser applicationUser = (ApplicationUser) object
            LocalHoliday localHoliday = localHolidayService.read(Long.parseLong(params?.id?.toString()))
            localHoliday.properties = params
            localHoliday.dateLastUpdated = new Date()
            localHoliday.userLastUpdated = applicationUser
            message = this.preCondition(localHoliday, params)
            if (message.type == 1) {
                int noOfRows = (int) localHolidayService.update(localHoliday)
                if (noOfRows>0) {
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