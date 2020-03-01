package com.bits.bdfp.settings.businessday

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.settings.bussinessday.LocalHoliday
import com.docu.common.Action
import com.docu.common.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("deleteLocalHolidayAction")
class DeleteLocalHolidayAction extends Action {

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    LocalHolidayService localHolidayService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

    public Object preCondition(Object params, Object object) {
        Boolean isError = false
        try{

            LocalHoliday localHoliday = localHolidayService.read(Long.parseLong(params?.id?.toString()))


            String domain = 'local_holiday'
            String id =  localHoliday.id

            isError = validationCheckService.validationCheck(domain,id)

            if (!localHoliday.validate()) {
                message = this.getValidationErrorMessage(localHoliday)
                return message
            }
            else if (isError){
                message = this.getMessage('LocalHoliday', Message.ERROR, 'This Local Holiday has already been used')
                return message
            }
            else {
                return localHoliday;
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
            if (result instanceof LocalHoliday) {
                int noOfRows = (int) localHolidayService.delete(result)
                if (noOfRows > 0) {
                    message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_DELETE)
                } else {
                    message = this.getMessage(result, Message.ERROR, this.FAIL_DELETE)
                }
            }
            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            message= this.getMessage("LocalHoliday", Message.ERROR, "Exception-${ex.message}")
            return message;
        }
    }

}