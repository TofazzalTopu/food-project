package com.bits.bdfp.common

import com.docu.security.ApplicationUser
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class BankAccountService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public BankAccount create(Object object) {
        BankAccount bankAccount = (BankAccount) object
        if (bankAccount.save(false)) {
            return bankAccount
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        BankAccount bankAccount = (BankAccount) object
        if (bankAccount.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        BankAccount bankAccount = (BankAccount) object
        bankAccount.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<BankAccount> objList = BankAccount.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = BankAccount.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<BankAccount> list() {
        return BankAccount.list()
    }

    @Transactional(readOnly = true)
    public BankAccount read(Long id) {
        return BankAccount.read(id)
    }

    @Transactional(readOnly = true)
    public BankAccount search(String fieldName, String fieldValue) {
        String query = "from BankAccount as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return BankAccount.find(query)
    }

    @Transactional(readOnly = true)
    public List listBankAccountByEnterprise(Object params,ApplicationUser applicationUser) {
        sql = new Sql(dataSource)
        String  strSql = """SELECT bank_account.id,
                            CONCAT(bank.`name`,',',bank_account.`account_no`) AS name
                            FROM
                            bank_account
                            INNER JOIN bank ON bank.id = bank_account.`bank_id`
                            INNER JOIN `enterprise_configuration`
                            ON `enterprise_configuration`.id=`bank`.`enterprise_configuration_id`
                            INNER JOIN `application_user_enterprise`
                            ON `enterprise_configuration`.id=`application_user_enterprise`.`enterprise_configuration_id`
                            WHERE `enterprise_configuration`.id=${params.id}
                            AND `application_user_enterprise`.`application_user_id`=${applicationUser.id}                                                     """

        List list = sql.rows(strSql.toString())
        return list
    }
}
