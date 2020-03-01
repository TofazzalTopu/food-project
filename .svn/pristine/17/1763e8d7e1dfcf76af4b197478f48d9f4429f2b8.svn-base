package com.bits.bdfp.common

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class CashPoolService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public CashPool create(Object object) {
        CashPool cashPool = (CashPool) object
        if (cashPool.save(false)) {
            return cashPool
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        CashPool cashPool = (CashPool) object
        if (cashPool.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        CashPool cashPool = (CashPool) object
        cashPool.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<CashPool> objList = CashPool.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = CashPool.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<CashPool> list() {
        return CashPool.list()
    }

    @Transactional(readOnly = true)
    public CashPool read(Long id) {
        return CashPool.read(id)
    }

    @Transactional(readOnly = true)
    public CashPool search(String fieldName, String fieldValue) {
        String query = "from CashPool as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return CashPool.find(query)
    }
}
