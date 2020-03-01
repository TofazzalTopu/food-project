package com.bits.bdfp.customer

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class CustomerShippingAddressService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public CustomerShippingAddress create(Object object) {
        CustomerShippingAddress customerShippingAddress = (CustomerShippingAddress) object
        if (customerShippingAddress.save(false)) {
            return customerShippingAddress
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        CustomerShippingAddress customerShippingAddress = (CustomerShippingAddress) object
        if (customerShippingAddress.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        CustomerShippingAddress customerShippingAddress = (CustomerShippingAddress) object
        customerShippingAddress.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Object params) {
        List<CustomerShippingAddress> objList = CustomerShippingAddress.withCriteria {
            if(params.id){
                eq('customerMaster.id', Long.parseLong(params.id))
            }
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = 0
        if(params.id){
            CustomerMaster customerMaster = CustomerMaster.read(Long.parseLong(params.id))
            total = CustomerShippingAddress.countByCustomerMaster(customerMaster)
        }else{
            total = CustomerShippingAddress.count()
        }
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<CustomerShippingAddress> list() {
        return CustomerShippingAddress.list()
    }

    @Transactional(readOnly = true)
    public CustomerShippingAddress read(Long id) {
        return CustomerShippingAddress.read(id)
    }

    @Transactional(readOnly = true)
    public CustomerShippingAddress search(String fieldName, String fieldValue) {
        String query = "from CustomerShippingAddress as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return CustomerShippingAddress.find(query)
    }
}
