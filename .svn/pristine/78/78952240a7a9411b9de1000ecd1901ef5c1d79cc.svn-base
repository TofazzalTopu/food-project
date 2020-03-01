package com.bits.bdfp.common.bank

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.common.Bank
import com.bits.bdfp.common.BankService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateBankAction")
class UpdateBankAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    BankService bankService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

    public Object preCondition(Object object, Object params) {
        Boolean isError = false
        try {
            ApplicationUser applicationUser = (ApplicationUser) object

        Bank bank = Bank.read(Long.parseLong(params?.id?.toString()))
        bank.properties = params
        bank.userUpdated = applicationUser
        bank.lastUpdated = new Date()
            String domain = 'bank'
            String id =  bank.id

            isError = validationCheckService.validationCheck(domain,id)

            if (!bank.validate()) {
                message = this.getValidationErrorMessage(bank)
                return message
            }
            else if (isError){
                message = this.getMessage('Bank', Message.ERROR, 'This Bank has already been used')
                return message
            }
            else{
                return bank
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

    public Object execute(Object params, Object object) {

        try {
            ApplicationUser applicationUser = (ApplicationUser) object

            Object result = this.preCondition(applicationUser, params)
            if (result instanceof Bank) {
                int noOfRows = (int) bankService.update(result)
                if (noOfRows > 0) {
                    message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_UPDATE)
                } else {
                    message = this.getMessage(result, Message.ERROR, this.FAIL_UPDATE)
                }
            }

            return message;

        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }


    }
}
