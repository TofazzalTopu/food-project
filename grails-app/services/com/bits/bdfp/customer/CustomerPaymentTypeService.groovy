package com.bits.bdfp.customer

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class CustomerPaymentTypeService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public CustomerPaymentType create(Object object) {
        CustomerPaymentType customerPaymentType = (CustomerPaymentType) object
        if (customerPaymentType.save(false)) {
            return customerPaymentType
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        CustomerPaymentType customerPaymentType = (CustomerPaymentType) object
        if (customerPaymentType.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        CustomerPaymentType customerPaymentType = (CustomerPaymentType) object
        customerPaymentType.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<CustomerPaymentType> objList = CustomerPaymentType.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = CustomerPaymentType.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<CustomerPaymentType> list() {
        return CustomerPaymentType.list()
    }

    @Transactional(readOnly = true)
    public CustomerPaymentType read(Long id) {
        return CustomerPaymentType.read(id)
    }

    @Transactional(readOnly = true)
    public CustomerPaymentType search(String fieldName, String fieldValue) {
        String query = "from CustomerPaymentType as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return CustomerPaymentType.find(query)
    }
}
