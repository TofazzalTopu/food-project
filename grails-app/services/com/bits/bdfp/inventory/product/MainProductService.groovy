package com.bits.bdfp.inventory.product

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class MainProductService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public MainProduct create(Object object) {
        MainProduct mainProduct = (MainProduct) object
        if (mainProduct.save(false)) {
            return mainProduct
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        MainProduct mainProduct = (MainProduct) object
        if (mainProduct.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        MainProduct mainProduct = (MainProduct) object
        mainProduct.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<MainProduct> objList = MainProduct.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = MainProduct.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<MainProduct> list() {
        return MainProduct.list()
    }

    @Transactional(readOnly = true)
    public MainProduct read(Long id) {
        return MainProduct.read(id)
    }

    @Transactional(readOnly = true)
    public MainProduct search(String fieldName, String fieldValue) {
        String query = "from MainProduct as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return MainProduct.find(query)
    }
}
