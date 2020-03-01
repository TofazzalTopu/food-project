package com.bits.bdfp.common.bankaccount

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.common.BankAccount
import com.bits.bdfp.common.BankAccountService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateBankAccountAction")
class UpdateBankAccountAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    BankAccountService bankAccountService
    @Autowired
   ValidationCheckService validationCheckService
    Message message


    public Object preCondition(Object object,Object params) {
        Boolean isError = false
        try {
            ApplicationUser applicationUser = (ApplicationUser) object

            BankAccount bankAccount = BankAccount.read(Long.parseLong(params?.id?.toString()))
            bankAccount.properties = params
            bankAccount.userUpdated = applicationUser
            bankAccount.lastUpdated = new Date()


            String domain = 'bank_account'
            String id =  bankAccount.id

//            isError = validationCheckService.validationCheck(domain,id)

            if (!bankAccount.validate()) {
                message = this.getValidationErrorMessage(bankAccount)
                return message
            }
                else if (isError){
                message = this.getMessage('Bank Account', Message.ERROR, 'This bank account has already been used')
                return message
            }
            else{
                return bankAccount
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
            if (result instanceof BankAccount) {
                int noOfRows = (int) bankAccountService.update(result)
                if (noOfRows > 0) {
                    message = this.getMessage("Bank Account", Message.SUCCESS, this.SUCCESS_UPDATE)
                } else {
                    message = this.getMessage("Bank Account", Message.ERROR, this.FAIL_UPDATE)
                }
            }

            return message;

        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }
}
