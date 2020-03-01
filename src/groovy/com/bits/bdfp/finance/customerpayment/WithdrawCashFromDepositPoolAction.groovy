package com.bits.bdfp.finance.customerpayment

import com.bits.bdfp.finance.CustomerPaymentService
import com.bits.bdfp.finance.WithdrawCashFromDepositPool
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 9/18/2016.
 */
@Component("withdrawCashFromDepositPoolAction")
class WithdrawCashFromDepositPoolAction extends Action {
    @Autowired
    CustomerPaymentService customerPaymentService

    Message message

    @Override
    public Object preCondition(def params, def object) {
        try {
            Map map = (Map) object
            WithdrawCashFromDepositPool withdrawCashFromDepositPool = map.withdrawCashFromDepositPool
            if (!withdrawCashFromDepositPool.validate()) {
                message = this.getValidationErrorMessage(withdrawCashFromDepositPool)
            } else {
                message = this.getMessage(withdrawCashFromDepositPool, Message.SUCCESS, SUCCESS_SAVE)
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Withdraw Cash from Deposit Pool", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    @Override
    public Object postCondition(Object params, Object object) {
        return null
    }

    @Override
    public Object execute(Object params, Object object) {
        try {
            Date dateNow = new Date()
            ApplicationUser applicationUser = (ApplicationUser) object
            WithdrawCashFromDepositPool withdrawCashFromDepositPool = new WithdrawCashFromDepositPool(params)
            withdrawCashFromDepositPool.userCreated = applicationUser

            Map map = [:]
            map.put('withdrawCashFromDepositPool', withdrawCashFromDepositPool)

            if (!withdrawCashFromDepositPool.validate()) {
                return this.getValidationErrorMessage(withdrawCashFromDepositPool)
            }

            int noOfRows = customerPaymentService.createWithdrawCashFromDepositPool(map)
            if (noOfRows > 0) {
                message = this.getMessage("Withdraw Cash from Deposit Pool", Message.SUCCESS, 'Withdraw Successful. Transaction No: ' + withdrawCashFromDepositPool.transactionNo)
            } else {
                message = this.getMessage('Withdraw Cash from Deposit Pool', Message.ERROR, 'Cash Could Not Be Withdrawn.')
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException('Cash Could Not Be Withdrawn.')
        }
    }
}
