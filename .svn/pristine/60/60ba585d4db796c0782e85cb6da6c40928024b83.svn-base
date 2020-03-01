package com.bits.bdfp.customer

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class CustomerSalesChannelService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public CustomerSalesChannel create(Object object) {
        CustomerSalesChannel customerSalesChannel = (CustomerSalesChannel) object
        if (customerSalesChannel.save(false)) {
            return customerSalesChannel
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        CustomerSalesChannel customerSalesChannel = (CustomerSalesChannel) object
        if (customerSalesChannel.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        CustomerSalesChannel customerSalesChannel = (CustomerSalesChannel) object
        customerSalesChannel.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<CustomerSalesChannel> objList = CustomerSalesChannel.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = CustomerSalesChannel.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<CustomerSalesChannel> list() {
        return CustomerSalesChannel.list()
    }

    @Transactional(readOnly = true)
    public CustomerSalesChannel read(Long id) {
        return CustomerSalesChannel.read(id)
    }

    @Transactional(readOnly = true)
    public CustomerSalesChannel search(String fieldName, String fieldValue) {
        String query = "from CustomerSalesChannel as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return CustomerSalesChannel.find(query)
    }
}
