package com.bits.bdfp.inventory.product

import com.bits.bdfp.inventory.product.externalproduct.ExternalProduct
import com.docu.common.Action
import com.docu.common.Service
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource

class ExternalProductService extends Service {

    static transactional = true
    DataSource dataSource
    Sql sql


    @Transactional
    public ExternalProduct create(Object object) {
        ExternalProduct externalProduct = (ExternalProduct) object
        if (externalProduct.save(false)) {
            return externalProduct
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        ExternalProduct externalProduct = (ExternalProduct) object
        if (externalProduct.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        ExternalProduct externalProduct = (ExternalProduct) object
        externalProduct.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public ExternalProduct read(Long id) {
        return ExternalProduct.read(id)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action){
        List<ExternalProduct> objList = ExternalProduct.withCriteria {
            if(action.resultPerPage != -1){
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = ExternalProduct.count()
        return [objList : objList, count : total]
    }

    @Transactional(readOnly = true)
    public List<ExternalProduct> list() {
        return ExternalProduct.list()
    }

    @Transactional(readOnly = true)
    public ExternalProduct search(String fieldName, String fieldValue) {
        String query = "from ExternalProduct as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return ExternalProduct.find(query)
    }
}
