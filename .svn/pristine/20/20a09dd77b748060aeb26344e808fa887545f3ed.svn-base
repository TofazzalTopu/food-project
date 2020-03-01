package com.bits.bdfp.inventory.product

import com.bits.bdfp.inventory.product.externalproduct.ExternalProductStock
import com.docu.common.Action
import com.docu.common.Service
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource

class ExternalProductStockService extends Service {

    static transactional = true
    DataSource dataSource
    Sql sql


    @Transactional
    public ExternalProductStock create(Object object) {
        ExternalProductStock externalProductStock = (ExternalProductStock) object
        if (externalProductStock.save(false)) {
            return externalProductStock
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        ExternalProductStock externalProductStock = (ExternalProductStock) object
        if (externalProductStock.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        ExternalProductStock externalProductStock = (ExternalProductStock) object
        externalProductStock.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public ExternalProductStock read(Long id) {
        return ExternalProductStock.read(id)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action){
        List<ExternalProductStock> objList = ExternalProductStock.withCriteria {
            eq('inQuantity', 20)
            if(action.resultPerPage != -1){
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = ExternalProductStock.count()
        return [objList : objList, count : total]
    }

    @Transactional(readOnly = true)
    public List<ExternalProductStock> list() {
        return ExternalProductStock.list()
    }

    @Transactional(readOnly = true)
    public ExternalProductStock search(String fieldName, String fieldValue) {
        String query = "from ExternalProductStock as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return ExternalProductStock.find(query)
    }
}