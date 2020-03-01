package com.bits.bdfp.common

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class NationalityService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public Nationality create(Object object) {
        Nationality nationality = (Nationality) object
        if (nationality.save(false)) {
            return nationality
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        Nationality nationality = (Nationality) object
        if (nationality.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        Nationality nationality = (Nationality) object
        nationality.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<Nationality> objList = Nationality.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = Nationality.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<Nationality> list() {
        return Nationality.list()
    }

    @Transactional(readOnly = true)
    public Nationality read(Long id) {
        return Nationality.read(id)
    }

    @Transactional(readOnly = true)
    public Nationality search(String fieldName, String fieldValue) {
        String query = "from Nationality as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return Nationality.find(query)
    }
}
