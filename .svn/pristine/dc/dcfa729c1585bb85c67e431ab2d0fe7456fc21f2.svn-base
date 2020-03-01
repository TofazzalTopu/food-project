package com.bits.bdfp.finance

import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.transaction.annotation.Transactional
import groovy.sql.Sql
import javax.sql.DataSource
import com.docu.common.Action

class ExpenseFromDPCashPoolService {
    static transactional = false
    DataSource dataSource
    SpringSecurityService springSecurityService
    Sql sql

    @Transactional(readOnly = true)
    public ExpenseFromDPCashPool read(Long id) {
        return ExpenseFromDPCashPool.read(id)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Map params) {
        try {
            DistributionPoint distributionPoint = DistributionPoint.read(Long.parseLong(params.dpId))
            List<ExpenseFromDPCashPool> objList = ExpenseFromDPCashPool.withCriteria {
                eq("isActive", Boolean.TRUE)
                eq("distributionPoint.id", distributionPoint.id)
                if (action.resultPerPage != -1) {
                    maxResults(action.resultPerPage)
                }
                firstResult(action.start)
                order(action.sortCol, action.sortOrder)
            }
            long total = ExpenseFromDPCashPool.countByIsActiveAndDistributionPoint(true, distributionPoint)
            return [objList: objList, count: total]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
    @Transactional(readOnly = true)
    public Map getListForExpenseRollbackGrid(Action action, Map params) {
        try {
            sql = new Sql(dataSource)
            def now = new Date()
            now.clearTime()
            DistributionPoint distributionPoint = DistributionPoint.read(Long.parseLong(params.dpId))
            List<ExpenseFromDPCashPool> objList = ExpenseFromDPCashPool.withCriteria {
                eq("isActive", Boolean.TRUE)
                eq("distributionPoint.id", distributionPoint.id)
                between("dateCreated", now, now + 1)
                if (action.resultPerPage != -1) {
                    maxResults(action.resultPerPage)
                }
                firstResult(action.start)
                order(action.sortCol, action.sortOrder)
            }

            def criteria = ExpenseFromDPCashPool.createCriteria()

            def expenseFromDPCashPoolCount = criteria.count {
                eq("isActive", Boolean.TRUE)
                eq("distributionPoint.id", distributionPoint.id)
                between("dateCreated", now, now + 1)
            }

            return [objList: objList, count: expenseFromDPCashPoolCount]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List<ExpenseFromDPCashPool> list() {
        return ExpenseFromDPCashPool.list()
    }

    @Transactional
    public boolean createExpense(Object object) {
        try {
            Map map = (Map) object
            ExpenseFromDPCashPool expenseFromDPCashPool = map.expenseFromDPCashPool

            if (expenseFromDPCashPool) {
                expenseFromDPCashPool.save(false)

                Journal journal = (Journal) map.get('journal')
                journal.save(validate: false, insert: true)

                List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.get('journalDetailsList')

                journalDetailsList.each { journalDetails ->
                    journalDetails.save(validate: false, insert: true)
                }
                return true;
            }
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
        return false;

    }

    @Transactional
    public ExpenseFromDPCashPool create(Object object) {
        try {
            ExpenseFromDPCashPool expenseFromDPCashPool = (ExpenseFromDPCashPool) object
            return expenseFromDPCashPool.save(vaidate: false, insert: true)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object object) {
        try {
            ExpenseFromDPCashPool expenseFromDPCashPool = (ExpenseFromDPCashPool) object
            if (expenseFromDPCashPool.save(vaidate: false)) {
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
    public Integer delete(Object object) {
        try {
            ExpenseFromDPCashPool expenseFromDPCashPool = (ExpenseFromDPCashPool) object
            expenseFromDPCashPool.delete()
            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public ExpenseFromDPCashPool search(String fieldName, String fieldValue) {
        String query = "from ExpenseFromDPCashPool as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return ExpenseFromDPCashPool.find(query)
    }

    @Transactional(readOnly = true)
    public List fetchExpenditureHeads() {
        String query = """
            SELECT id, CONCAT(chart_of_account_name, "-", chart_of_account_code_user) AS chartOfAccountName
            FROM chart_of_accounts
            WHERE parent_code = '01'
            ORDER BY chartOfAccountName ASC
        """
        Sql sql = new Sql(dataSource)
        return sql.rows(query)
    }

    @Transactional(readOnly = true)
    public List fetchDistributionPointList() {
        def user=(ApplicationUser) springSecurityService?.getCurrentUser()
        String query = """
            SELECT dp.id, dp.name AS DPName
            FROM distribution_point AS dp, application_user_distribution_point AS audp
            WHERE dp.id=audp.distribution_point_id
                AND audp.application_user_id = ${user.id}
            ORDER BY DPName
        """
        Sql sql = new Sql(dataSource)
        return sql.rows(query)
    }

    @Transactional(readOnly = true)
    public List fetchCashPoolList(Object params) {
        String query = """
            SELECT id, name
            FROM cash_pool
            WHERE distribution_point_id = ${params.id}
            ORDER BY Name
        """
        Sql sql = new Sql(dataSource)
        return sql.rows(query)
    }

    @Transactional
    public Integer cancelExpense(Map map) {
        try {
            ExpenseFromDPCashPool expenseFromDPCashPool = (ExpenseFromDPCashPool) map.expenseFromDPCashPool
            List<Journal> journals = (List<Journal>) map.journals
            List<JournalDetails> journalDetails = (List<JournalDetails>) map.journalDetails
            int count = 0

            if(journalDetails && journalDetails.size() > 0){
                journalDetails.each {
                    if(it.save()){
                        count++
                    }
                }
            }

            if(journals && journals.size() > 0){
                journals.each {
                    if(it.save()){
                        count++
                    }
                }
            }

            if(expenseFromDPCashPool){
                expenseFromDPCashPool.save()
                count++
            }
            return count

        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

}
