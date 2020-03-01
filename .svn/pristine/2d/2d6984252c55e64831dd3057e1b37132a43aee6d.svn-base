package com.bits.bdfp.settings.businessday

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.settings.bussinessday.BusinessHoliday
import com.docu.common.Action
import com.docu.common.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("deleteBusinessHolidayAction")
class DeleteBusinessHolidayAction extends Action {

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    BusinessHolidayService businessHolidayService
    @Autowired
    ValidationCheckService validationCheckService

    Message message

    public Object preCondition(Object params, Object object) {
        Boolean isError = false
        try{

            BusinessHoliday businessHoliday = businessHolidayService.read(Long.parseLong(params?.id?.toString()))
            String domain = 'business_holiday'
            String id =  businessHoliday.id

            isError = validationCheckService.validationCheck(domain,id)

            if (!businessHoliday.validate()) {
                message = this.getValidationErrorMessage(businessHoliday)
                return message
            }
            else if (isError){
                message = this.getMessage('BusinessHoliday', Message.ERROR, 'This Business Holiday has already been used')
                return message
            }else {
                return businessHoliday;
            }
        } catch (Exception ex) {
            log.error(ex.message)
            throw ex
        }
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Message execute(Object params, Object object) {

        try {
            Object result = this.preCondition(params, object)
            if (result instanceof BusinessHoliday) {
                int noOfRows = (int) businessHolidayService.delete(result)
                if (noOfRows > 0) {
                    message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_DELETE)
                } else {
                    message = this.getMessage(result, Message.ERROR, this.FAIL_DELETE)
                }
            }
            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            message= this.getMessage("BusinessHoliday", Message.ERROR, "Exception-${ex.message}")
            return message;
        }
    }

}