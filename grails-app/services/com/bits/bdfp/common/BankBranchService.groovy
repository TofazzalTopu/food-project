package com.bits.bdfp.common

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class BankBranchService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public BankBranch create(Object object) {
        BankBranch bankBranch = (BankBranch) object
        if (bankBranch.save(false)) {
            return bankBranch
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        BankBranch bankBranch = (BankBranch) object
        if (bankBranch.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        BankBranch bankBranch = (BankBranch) object
        bankBranch.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<BankBranch> objList = BankBranch.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = BankBranch.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<BankBranch> list() {
        return BankBranch.list()
    }

    @Transactional(readOnly = true)
    public BankBranch read(Long id) {
        return BankBranch.read(id)
    }

    @Transactional(readOnly = true)
    public BankBranch search(String fieldName, String fieldValue) {
        String query = "from BankBranch as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return BankBranch.find(query)
    }
}
