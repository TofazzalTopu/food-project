package com.bits.bdfp.inventory.warehouse

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class SubWarehouseTypeService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public SubWarehouseType create(Object object) {
        SubWarehouseType subWarehouseType = (SubWarehouseType) object
        if (subWarehouseType.save(false)) {
            return subWarehouseType
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        SubWarehouseType subWarehouseType = (SubWarehouseType) object
        if (subWarehouseType.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        SubWarehouseType subWarehouseType = (SubWarehouseType) object
        subWarehouseType.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<SubWarehouseType> objList = SubWarehouseType.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = SubWarehouseType.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<SubWarehouseType> list() {
        return SubWarehouseType.list()
    }

    @Transactional(readOnly = true)
    public SubWarehouseType read(Long id) {
        return SubWarehouseType.read(id)
    }

    @Transactional(readOnly = true)
    public SubWarehouseType search(String fieldName, String fieldValue) {
        String query = "from SubWarehouseType as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return SubWarehouseType.find(query)
    }
}
