package com.bits.bdfp.common

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class DepositPoolService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public DepositPool create(Object object) {
        DepositPool depositPool = (DepositPool) object
        if (depositPool.save(false)) {
            return depositPool
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        DepositPool depositPool = (DepositPool) object
        if (depositPool.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        DepositPool depositPool = (DepositPool) object
        depositPool.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<DepositPool> objList = DepositPool.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = DepositPool.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<DepositPool> list() {
        return DepositPool.list()
    }

    @Transactional(readOnly = true)
    public DepositPool read(Long id) {
        return DepositPool.read(id)
    }

    @Transactional(readOnly = true)
    public DepositPool search(String fieldName, String fieldValue) {
        String query = "from DepositPool as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return DepositPool.find(query)
    }
}
