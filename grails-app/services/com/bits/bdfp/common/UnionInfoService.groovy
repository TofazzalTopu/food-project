package com.bits.bdfp.common

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class UnionInfoService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public UnionInfo create(Object object) {
        UnionInfo unionInfo = (UnionInfo) object
        if (unionInfo.save(false)) {
            return unionInfo
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        UnionInfo unionInfo = (UnionInfo) object
        if (unionInfo.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        UnionInfo unionInfo = (UnionInfo) object
        unionInfo.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<UnionInfo> objList = UnionInfo.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = UnionInfo.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<UnionInfo> list() {
        return UnionInfo.list()
    }

    @Transactional(readOnly = true)
    public UnionInfo read(Long id) {
        return UnionInfo.read(id)
    }

    @Transactional(readOnly = true)
    public UnionInfo search(String fieldName, String fieldValue) {
        String query = "from UnionInfo as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return UnionInfo.find(query)
    }
}
