package com.bits.bdfp.customer

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class CustomerContactTypeService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public CustomerContactType create(Object object) {
        CustomerContactType customerContactType = (CustomerContactType) object
        if (customerContactType.save(false)) {
            return customerContactType
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        CustomerContactType customerContactType = (CustomerContactType) object
        if (customerContactType.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        CustomerContactType customerContactType = (CustomerContactType) object
        customerContactType.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<CustomerContactType> objList = CustomerContactType.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = CustomerContactType.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<CustomerContactType> list() {
        return CustomerContactType.list()
    }

    @Transactional(readOnly = true)
    public CustomerContactType read(Long id) {
        return CustomerContactType.read(id)
    }

    @Transactional(readOnly = true)
    public CustomerContactType search(String fieldName, String fieldValue) {
        String query = "from CustomerContactType as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return CustomerContactType.find(query)
    }
}
