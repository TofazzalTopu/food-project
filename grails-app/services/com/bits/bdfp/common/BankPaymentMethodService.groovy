package com.bits.bdfp.common

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class BankPaymentMethodService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public BankPaymentMethod create(Object object) {
        BankPaymentMethod bankPaymentMethod = (BankPaymentMethod) object
        if (bankPaymentMethod.save(false)) {
            return bankPaymentMethod
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        BankPaymentMethod bankPaymentMethod = (BankPaymentMethod) object
        if (bankPaymentMethod.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        BankPaymentMethod bankPaymentMethod = (BankPaymentMethod) object
        bankPaymentMethod.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<BankPaymentMethod> objList = BankPaymentMethod.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = BankPaymentMethod.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<BankPaymentMethod> list() {
        return BankPaymentMethod.list()
    }

    @Transactional(readOnly = true)
    public BankPaymentMethod read(Long id) {
        return BankPaymentMethod.read(id)
    }

    @Transactional(readOnly = true)
    public BankPaymentMethod search(String fieldName, String fieldValue) {
        String query = "from BankPaymentMethod as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return BankPaymentMethod.find(query)
    }
}
