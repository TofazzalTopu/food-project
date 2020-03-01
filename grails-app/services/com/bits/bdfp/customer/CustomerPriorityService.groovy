package com.bits.bdfp.customer

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class CustomerPriorityService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public CustomerPriority create(Object object) {
        CustomerPriority customerPriority = (CustomerPriority) object
        if (customerPriority.save(false)) {
            return customerPriority
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        CustomerPriority customerPriority = (CustomerPriority) object
        if (customerPriority.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        CustomerPriority customerPriority = (CustomerPriority) object
        customerPriority.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<CustomerPriority> objList = CustomerPriority.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = CustomerPriority.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<CustomerPriority> list() {
        return CustomerPriority.list()
    }

    @Transactional(readOnly = true)
    public CustomerPriority read(Long id) {
        return CustomerPriority.read(id)
    }

    @Transactional(readOnly = true)
    public CustomerPriority search(String fieldName, String fieldValue) {
        String query = "from CustomerPriority as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return CustomerPriority.find(query)
    }
}
