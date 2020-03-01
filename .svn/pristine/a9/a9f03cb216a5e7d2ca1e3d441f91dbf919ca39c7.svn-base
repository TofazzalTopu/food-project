package com.bits.bdfp.common

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class CountryInfoService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public CountryInfo create(Object object) {
        CountryInfo countryInfo = (CountryInfo) object
        if (countryInfo.save(false)) {
            return countryInfo
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        CountryInfo countryInfo = (CountryInfo) object
        if (countryInfo.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        CountryInfo countryInfo = (CountryInfo) object
        countryInfo.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<CountryInfo> objList = CountryInfo.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = CountryInfo.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<CountryInfo> list() {
        return CountryInfo.list()
    }

    @Transactional(readOnly = true)
    public CountryInfo read(Long id) {
        return CountryInfo.read(id)
    }

    @Transactional(readOnly = true)
    public CountryInfo search(String fieldName, String fieldValue) {
        String query = "from CountryInfo as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return CountryInfo.find(query)
    }
}
