package com.bits.bdfp.inventory.setup

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class VatTypeService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public VatType create(Object object) {
        VatType vatType = (VatType) object
        if (vatType.save(false)) {
            return vatType
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        VatType vatType = (VatType) object
        if (vatType.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        VatType vatType = (VatType) object
        vatType.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<VatType> objList = VatType.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = VatType.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<VatType> list() {
        return VatType.list()
    }

    @Transactional(readOnly = true)
    public VatType read(Long id) {
        return VatType.read(id)
    }

    @Transactional(readOnly = true)
    public VatType search(String fieldName, String fieldValue) {
        String query = "from VatType as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return VatType.find(query)
    }
}
