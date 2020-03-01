package com.bits.bdfp.common

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class DesignationService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public Designation create(Object object) {
        Designation designation = (Designation) object
        if (designation.save(false)) {
            return designation
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        Designation designation = (Designation) object
        if (designation.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        Designation designation = (Designation) object
        designation.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<Designation> objList = Designation.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = Designation.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<Designation> list() {
        return Designation.list()
    }

    @Transactional(readOnly = true)
    public Designation read(Long id) {
        return Designation.read(id)
    }

    @Transactional(readOnly = true)
    public Designation search(String fieldName, String fieldValue) {
        String query = "from Designation as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return Designation.find(query)
    }
}
