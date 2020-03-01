package com.bits.bdfp.finance

import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.SubLedger
import com.bits.bdfp.util.ApplicationConstants
import org.springframework.transaction.annotation.Transactional
import com.docu.commons.CommonConstants
import com.docu.common.Service
import groovy.sql.Sql
import javax.sql.DataSource
import com.docu.common.Action

class CashReceivedFromBranchService extends Service {
    static transactional = false

    Sql sql
    DataSource dataSource

    @Transactional(readOnly = true)
    public CashReceivedFromBranch read(Long id) {
        return CashReceivedFromBranch.read(id)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Map params) {
        try {
            List<CashReceivedFromBranch> cashReceivedFromBranchList = CashReceivedFromBranch.withCriteria {
                maxResults(Integer.parseInt(action.resultPerPage.toString()))
                firstResult(Integer.parseInt(action.start.toString()))
                order(action.sortCol.toString(), action.sortOrder.toString())
            }
            long cashReceivedFromBranchCount = CashReceivedFromBranch.count()
            return [objList: cashReceivedFromBranchList, count: cashReceivedFromBranchCount]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List<CashReceivedFromBranch> list() {
        return CashReceivedFromBranch.list()
    }

    @Transactional
    public Integer create(Object object) {
        try{
            Map map = (Map) object
            CashReceivedFromBranch cashReceivedFromBranch = map.cashReceivedFromBranch
            if (cashReceivedFromBranch) {
                cashReceivedFromBranch.save(false)
                DepositCashToDepositPool depositCashToDepositPool = map.depositCashToDepositPool
                depositCashToDepositPool.save()

                /************* COA Entry **************/
                Journal journalHead = (Journal) map.get('journalHead')
                journalHead.save(validate: false, insert: true)

                List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.get('journalDetailsList')
                journalDetailsList.each { journalDetails ->
                    journalDetails.save(validate: false, insert: true)
                }
            }
            return new Integer(1)
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object object) {
        try {
            CashReceivedFromBranch cashReceivedFromBranch = (CashReceivedFromBranch) object
            if (cashReceivedFromBranch.save(vaidate: false)) {
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
            CashReceivedFromBranch cashReceivedFromBranch = (CashReceivedFromBranch) object
            cashReceivedFromBranch.delete()
            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public CashReceivedFromBranch search(String fieldName, String fieldValue) {
        String query = "from CashReceivedFromBranch as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return CashReceivedFromBranch.find(query)
    }

    @Transactional(readOnly = true)
    public Map fetchBankAndCash(Object params) {
        Map map = [:]
        String strSql = """"""
        sql = new Sql(dataSource)
        strSql = """
                            SELECT CONCAT('[',`cash_pool`.`code`,'] ',`cash_pool`.`name`) AS `name`, `cash_pool`.`id`
                            FROM `cash_pool`
                            INNER JOIN `distribution_point` ON `distribution_point`.`id` = `cash_pool`.`distribution_point_id`
                            WHERE `distribution_point`.`is_factory` IS TRUE
                                AND `cash_pool`.`enterprise_configuration_id` = ${params.entId}
                            ORDER BY `id`
                          """
        List objList = sql.rows(strSql)
        map.put('cashPool', objList)
        strSql = """
                            SELECT CONCAT('[',`bank_account`.`account_no`,'] ',`bank`.`name`,'(',`bank_branch`.`name`,')') AS `name`, `bank_account`.`id`
                            FROM `bank_account`
                            INNER JOIN `bank` ON `bank`.`id` = `bank_account`.`bank_id`
                            INNER JOIN `bank_branch` ON `bank_branch`.`id` = `bank_account`.`bank_branch_id`
                            WHERE `bank`.`enterprise_configuration_id` = ${params.entId}
                            ORDER BY `id`
                          """
        objList = sql.rows(strSql)
        map.put('bankAccount', objList)
        strSql = """
                            SELECT CONCAT('[',`distribution_point`.`code`,'] ',`distribution_point`.`name`) AS `name`,
                                `distribution_point`.`id`,CONCAT('[',`customer_master`.`code`,'] ',`customer_master`.`name`) AS `customer`
                            FROM `distribution_point`
                            INNER JOIN `distribution_point_warehouse`
                                ON `distribution_point_warehouse`.`distribution_point_id` = `distribution_point`.`id`
                            INNER JOIN `customer_master`
                                ON `customer_master`.`id` = `distribution_point_warehouse`.`default_customer_id`
                            WHERE `distribution_point`.`is_factory` IS FALSE
                                AND `distribution_point`.`enterprise_configuration_id` = ${params.entId}
                            ORDER BY `id`
                          """
        objList = sql.rows(strSql)
        map.put('dp', objList)

        return map
    }

    @Transactional(readOnly = true)
    public List fetchTransactionNo(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `transaction_no` as `name`,`id`
                            FROM `deposit_cash_to_deposit_pool`
                            WHERE `distribution_point_id` = ${params.dpId}
                                AND DATE_FORMAT(`date_created`,'%d-%m-%Y') = '${params.date}'
                                AND `status` = '${ApplicationConstants.NOT_ACKNOWLEDGED}'
                                AND `ho_deposit` IS TRUE
                            ORDER BY `id`
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List fetchData(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `total`,`deposit_to_bank_account`,`deposit_to_ho_cash`,`sd_amount`,`sales_amount`
                            FROM `deposit_cash_to_deposit_pool`
                            WHERE id = ${params.id}
                            ORDER BY `id`
                          """
        List objList = sql.rows(strSql)
        return objList
    }
}
