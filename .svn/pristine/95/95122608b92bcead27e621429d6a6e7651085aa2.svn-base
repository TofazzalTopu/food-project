package com.bits.bdfp.common

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class DistrictService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public District create(Object object) {
        District district = (District) object
        if (district.save(false)) {
            return district
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        District district = (District) object
        if (district.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        District district = (District) object
        district.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<District> objList = District.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = District.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<District> list() {
        return District.list()
    }

    @Transactional(readOnly = true)
    public District read(Long id) {
        return District.read(id)
    }

    @Transactional(readOnly = true)
    public District search(String fieldName, String fieldValue) {
        String query = "from District as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return District.find(query)
    }
}
