package com.bits.bdfp.common

import com.docu.security.ApplicationUser
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class BankService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public Bank create(Object object) {
        Bank bank = (Bank) object
        if (bank.save(false)) {
            return bank
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        Bank bank = (Bank) object
        if (bank.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        Bank bank = (Bank) object
        bank.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<Bank> objList = Bank.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = Bank.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<Bank> list() {
        return Bank.list()
    }

    @Transactional(readOnly = true)
    public Bank read(Long id) {
        return Bank.read(id)
    }

    @Transactional(readOnly = true)
    public Bank search(String fieldName, String fieldValue) {
        String query = "from Bank as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return Bank.find(query)
    }
    @Transactional(readOnly = true)
    public List listBank(Object params,ApplicationUser applicationUser) {
        sql = new Sql(dataSource)
        String  strSql = """SELECT bank.id,bank.`name`
                            FROM bank
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
