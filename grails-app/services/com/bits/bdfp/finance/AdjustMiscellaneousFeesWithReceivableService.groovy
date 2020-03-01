package com.bits.bdfp.finance

import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.inventory.demandorder.Invoice
import com.docu.common.Message
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource


class AdjustMiscellaneousFeesWithReceivableService {
    static transactional = false
    Sql sql
    DataSource dataSource

    @Transactional(readOnly = true)
    public  List getCustomerInfoByCustomerId(Long customerId){
        String query = """
            SELECT cm.id AS customerId, cm.name AS customerName, cm.code AS customerCode,
                IFNULL((SELECT  (SUM(jd.debit_amount) - SUM(jd.credit_amount)) receivable
                    FROM chart_of_accounts_mapping coam
                    INNER JOIN chart_of_accounts coa
                        ON coa.id = coam.chart_of_accounts_id
                    INNER JOIN journal_details jd
                        ON coa.id = jd.chart_of_accounts_id
                    WHERE coam.coa_type = 'ACCOUNTS_RECEIVABLE'
                        AND jd.prefix_code = cm.code),0) receivableAmount

            FROM customer_master cm
            INNER JOIN customer_category ct
                    ON ct.id = cm.category_id
            WHERE cm.id = ${customerId}
                AND  LOWER(ct.name) NOT IN ('branch','sales man','retail outlet')
        """

        sql = new Sql(dataSource)

        return sql.rows(query)
    }

    @Transactional(readOnly = true)
    public  List getInvoiceListByCustomerId(Long customerId){
        String query = """
            SELECT i.id, i.default_customer_id, i.invoice_amount, paid_amount
            FROM invoice i
            WHERE i.is_active = true
                AND i.invoice_amount - i.paid_amount > 0
                AND i.default_customer_id = ${customerId}
        """

        sql = new Sql(dataSource)

        return sql.rows(query)
    }

    @Transactional
    public Integer adjust(Map map){
        try {
            Integer count = 0
            AdjustMiscellaneousFeesWithReceivable adjustMiscellaneousFeesWithReceivable = (AdjustMiscellaneousFeesWithReceivable) map.adjustMiscellaneousFeesWithReceivable
            List<Invoice> invoiceUpdateList =  map.invoiceUpdateList
            if(adjustMiscellaneousFeesWithReceivable.save()){

                if(invoiceUpdateList.size()>0){
                    invoiceUpdateList.each {
                        it.save()
                    }
                }

                Journal journal = (Journal) map.get('journal')
                journal.save(validate: false, insert: true)

                List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.get('journalDetailsList')
                journalDetailsList.each { journalDetails->
                    journalDetails.save(validate: false, insert: true)
                }

                count++
            }

            if(count>0){
                return  (Integer) adjustMiscellaneousFeesWithReceivable.id
            }else{
                return count
            }

        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer withdraw(Map map){
        try{
            Integer count = 0
            AdjustMiscellaneousFeesWithReceivable adjustMiscellaneousFeesWithReceivable = (AdjustMiscellaneousFeesWithReceivable) map.adjustMiscellaneousFeesWithReceivable

            if(adjustMiscellaneousFeesWithReceivable.save()){

                Journal journal = (Journal) map.get('journal')
                journal.save(validate: false, insert: true)

                List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.get('journalDetailsList')
                journalDetailsList.each { journalDetails->
                    journalDetails.save(validate: false, insert: true)
                }

                count++
            }
            return count

        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
