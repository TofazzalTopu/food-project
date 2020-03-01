package com.bits.bdfp.common

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class DivisionService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public Division create(Object object) {
        Division division = (Division) object
        if (division.save(false)) {
            return division
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        Division division = (Division) object
        if (division.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        Division division = (Division) object
        division.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<Division> objList = Division.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = Division.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<Division> list() {
        return Division.list()
    }

    @Transactional(readOnly = true)
    public Division read(Long id) {
        return Division.read(id)
    }

    @Transactional(readOnly = true)
    public Division search(String fieldName, String fieldValue) {
        String query = "from Division as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return Division.find(query)
    }
}
