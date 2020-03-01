package com.bits.bdfp.common

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class ThanaUpazilaPouroshovaService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public ThanaUpazilaPouroshova create(Object object) {
        ThanaUpazilaPouroshova thanaUpazilaPouroshova = (ThanaUpazilaPouroshova) object
        if (thanaUpazilaPouroshova.save(false)) {
            return thanaUpazilaPouroshova
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        ThanaUpazilaPouroshova thanaUpazilaPouroshova = (ThanaUpazilaPouroshova) object
        if (thanaUpazilaPouroshova.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        ThanaUpazilaPouroshova thanaUpazilaPouroshova = (ThanaUpazilaPouroshova) object
        thanaUpazilaPouroshova.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<ThanaUpazilaPouroshova> objList = ThanaUpazilaPouroshova.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = ThanaUpazilaPouroshova.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<ThanaUpazilaPouroshova> list() {
        return ThanaUpazilaPouroshova.list()
    }

    @Transactional(readOnly = true)
    public ThanaUpazilaPouroshova read(Long id) {
        return ThanaUpazilaPouroshova.read(id)
    }

    @Transactional(readOnly = true)
    public ThanaUpazilaPouroshova search(String fieldName, String fieldValue) {
        String query = "from ThanaUpazilaPouroshova as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return ThanaUpazilaPouroshova.find(query)
    }
}
