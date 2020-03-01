package com.bits.bdfp.finance

import com.bits.bdfp.accounts.ChartOfAccounts
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.util.ApplicationConstants
import com.docu.security.ApplicationUser
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource

class AdjustSecurityDepositService {

    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public Object createAdjustSecurityDepositInvoice(Object object) {

        try {
            AdjustSecurityDepositWithInvoice adjustSecurityDepositWithInvoice = (AdjustSecurityDepositWithInvoice) object.get('adjustSecurityDepositWithInvoice')
            adjustSecurityDepositWithInvoice.save(validate: false, insert: true)

            Journal journal = (Journal) object.get('journalHead')
            journal.save(validate: false, insert: true)
            List<JournalDetails> journalDetailsList = (List<JournalDetails>) object.get('journalDetailsList')
            journalDetailsList.each { journalDetails ->
                journalDetails.save(validate: false, insert: true)
            }
            return new Integer(1)
        } catch(Exception ex) {
            log.error(ex.message)
            throw new RuntimeException()
        }
        catch(Exception Ex)
        {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
//            return new Integer(0)
        }
    }








}
