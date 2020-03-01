package com.bits.bdfp.finance

import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource

class SecurityDepositService {

    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public Object createWithdrawSecurityDeposit(Map map) {

        try{
//            WithdrawSecurityDeposit withdrawSecurityDeposit = (WithdrawSecurityDeposit) object.get('withdrawSecurityDeposit')
//            withdrawSecurityDeposit.save(validate: false, insert: true)



            List<WithdrawSecurityDeposit> withdrawSecurityDepositList = (List<WithdrawSecurityDeposit>) map.get('withdrawSecurityDepositList')
            withdrawSecurityDepositList.each { withdrawSecurityDeposit->
                withdrawSecurityDeposit.save(validate: false, insert: true)
            }

            List<SecurityDeposit> securityDepositList = (List<SecurityDeposit>) map.get('securityDepositList')
            securityDepositList.each {
                it.save(validate: false, insert: true)
            }

            Journal journal = (Journal) map.get('journal')
            journal.save(validate: false, insert: true)

            List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.get('journalDetailsList')
            journalDetailsList.each { journalDetails->
                journalDetails.save(validate: false, insert: true)
            }
            //return null
            return new Integer(1)
        }catch(Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }


}
