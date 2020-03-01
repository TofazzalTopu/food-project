package com.bits.bdfp.customer

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class CustomerTerritorySubAreaService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public CustomerTerritorySubArea create(Object object) {
     try{
        CustomerTerritorySubArea customerTerritorySubArea = (CustomerTerritorySubArea) object
        if (customerTerritorySubArea.save(false)) {
            return customerTerritorySubArea
        }
        return null
    } catch (Exception ex) {
        log.error(ex.message)
        throw new RuntimeException(ex.message)
    }
    }

    @Transactional
    public Integer update(Object object) {
        try{
        CustomerTerritorySubArea customerTerritorySubArea = (CustomerTerritorySubArea) object
        if (customerTerritorySubArea.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer delete(Object object) {
     try{
        CustomerTerritorySubArea customerTerritorySubArea = (CustomerTerritorySubArea) object
        customerTerritorySubArea.delete()
        return new Integer(1)
    } catch (Exception ex) {
        log.error(ex.message)
        throw new RuntimeException(ex.message)
    }
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Object params) {
        List<CustomerTerritorySubArea> objList = CustomerTerritorySubArea.withCriteria {
            if(params.id){
                eq('customerMaster.id', Long.parseLong(params.id))
            }
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        int total = 0
        if(params.id){
            CustomerMaster customerMaster = CustomerMaster.read(Long.parseLong(params.id))
            total = CustomerTerritorySubArea.countByCustomerMaster(customerMaster)
        }else{
            total = CustomerTerritorySubArea.count()
        }

        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<CustomerTerritorySubArea> list() {
        return CustomerTerritorySubArea.list()
    }

    @Transactional(readOnly = true)
    public CustomerTerritorySubArea read(Long id) {
        return CustomerTerritorySubArea.read(id)
    }

    @Transactional(readOnly = true)
    public CustomerTerritorySubArea search(String fieldName, String fieldValue) {
        String query = "from CustomerTerritorySubArea as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return CustomerTerritorySubArea.find(query)
    }
}
