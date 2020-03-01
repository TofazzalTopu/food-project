package com.bits.bdfp.common.depositpool

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.common.DepositPool
import com.bits.bdfp.common.DepositPoolService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateDepositPoolAction")
class UpdateDepositPoolAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    DepositPoolService depositPoolService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

    public Object preCondition(Object object, Object params) {
        Boolean isError = false
        try {
            ApplicationUser applicationUser = (ApplicationUser) object

        DepositPool depositPool = DepositPool.read(Long.parseLong(params?.id?.toString()))
        depositPool.properties = params
        depositPool.userUpdated = applicationUser
        depositPool.dateCreated = new Date()

            String domain = 'deposit_pool'
            String id =  depositPool.id

            isError = validationCheckService.validationCheck(domain,id)

            if (!depositPool.validate()) {
                message = this.getValidationErrorMessage(depositPool)
                return message
            }
            else if (isError){
                message = this.getMessage('DepositPool', Message.ERROR, 'This deposit pool has already been used')
                return message
            }
            else{
                return depositPool
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
            if (result instanceof DepositPool) {
                int noOfRows = (int) depositPoolService.update(result)
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
