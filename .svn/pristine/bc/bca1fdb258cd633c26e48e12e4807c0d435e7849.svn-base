package com.bits.bdfp.inventory.product

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class MasterProductService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public MasterProduct create(Object object) {
        MasterProduct masterProduct = (MasterProduct) object
        if (masterProduct.save(false)) {
            return masterProduct
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        MasterProduct masterProduct = (MasterProduct) object
        if (masterProduct.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        MasterProduct masterProduct = (MasterProduct) object
        masterProduct.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<MasterProduct> objList = MasterProduct.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = MasterProduct.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<MasterProduct> list() {
        return MasterProduct.list()
    }

    @Transactional(readOnly = true)
    public MasterProduct read(Long id) {
        return MasterProduct.read(id)
    }

    @Transactional(readOnly = true)
    public MasterProduct search(String fieldName, String fieldValue) {
        String query = "from MasterProduct as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return MasterProduct.find(query)
    }
}
