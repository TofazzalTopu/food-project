package com.bits.bdfp.customer

import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.inventory.demandorder.Invoice
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import groovy.sql.Sql
import javax.sql.DataSource
import com.docu.common.Action

class CustomerSettlementService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional(readOnly = true)
    public CustomerSettlement read(Long id) {
        return CustomerSettlement.read(id)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Map params) {
        try {
            List<CustomerSettlement> customerSettlementList = CustomerSettlement.withCriteria {
                maxResults(Integer.parseInt(action.resultPerPage.toString()))
                firstResult(Integer.parseInt(action.start.toString()))
                order(action.sortCol.toString(), action.sortOrder.toString())
            }
            long customerSettlementCount = CustomerSettlement.count()
            return [objList: customerSettlementList, count: customerSettlementCount]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List<CustomerSettlement> list() {
        return CustomerSettlement.list()
    }

    @Transactional
    public CustomerSettlement create(Object object) {
        try {
            CustomerSettlement customerSettlement = (CustomerSettlement) object
            return customerSettlement.save(vaidate: false, insert: true)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object object) {
        try {
            CustomerSettlement customerSettlement = (CustomerSettlement) object
            if (customerSettlement.save(vaidate: false)) {
                return new Integer(1)
            } else {
                return new Integer(0)
            }
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public CustomerSettlement adjustWithReceivable(Object object) {
        try {
            Map map = (Map) object
            CustomerSettlement customerSettlement = (CustomerSettlement) map.get('customerSettlement')
            customerSettlement.save(vaidate: false, insert: true)

            List<Invoice> invoiceList = (List<Invoice>) map.get('invoiceListToBeUpdated')
            invoiceList.each { invoice->
                invoice.save()
            }

            List<SettledInvoice> settledInvoiceList = (List<SettledInvoice>) map.get('settledInvoiceList')
            settledInvoiceList.each { settledInvoice->
                settledInvoice.save(validate: false, insert: true)
            }

            Journal journal = (Journal) map.get('journal')
            journal.save(validate: false, insert: true)

            List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.get('journalDetailsList')
            journalDetailsList.each { journalDetails->
                journalDetails.save(validate: false, insert: true)
            }
            return customerSettlement
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public CustomerSettlement withdraw(Object object) {
        try {
            Map map = (Map) object
            CustomerSettlement customerSettlement = (CustomerSettlement) map.get('customerSettlement')
            customerSettlement.save(vaidate: false, insert: true)

            Journal journal = (Journal) map.get('journal')
            journal.save(validate: false, insert: true)

            List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.get('journalDetailsList')
            journalDetailsList.each { journalDetails->
                journalDetails.save(validate: false, insert: true)
            }
            return customerSettlement
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        try {
            CustomerSettlement customerSettlement = (CustomerSettlement) object
            customerSettlement.delete()
            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public CustomerSettlement search(String fieldName, String fieldValue) {
        String query = "from CustomerSettlement as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return CustomerSettlement.find(query)
    }

    @Transactional(readOnly = true)
    public List listDueInvoiceByCustomer(long customerId) {
        String query = """ SELECT id FROM invoice
            WHERE is_active = true
                AND `default_customer_id` = ${customerId}
                AND invoice_amount > paid_amount
            ORDER BY date_created
        """
        sql = new Sql(dataSource)
        List resultList = sql.rows(query)
        return resultList
    }
}
