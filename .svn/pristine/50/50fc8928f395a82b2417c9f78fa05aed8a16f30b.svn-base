package com.bits.bdfp.common.bankaccount

import com.bits.bdfp.common.BankAccountService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by prianka.adhikary on 9/27/2015.
 */
@Component("loadBankAccountAction")
class LoadBankAccountAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    BankAccountService bankAccountService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser)object
            return bankAccountService.listBankAccountByEnterprise(params,applicationUser)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }
}
