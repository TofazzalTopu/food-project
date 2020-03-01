package com.bits.bdfp.inventory.setup

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class DiscountTypeService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public DiscountType create(Object object) {
        DiscountType discountType = (DiscountType) object
        if (discountType.save(false)) {
            return discountType
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        DiscountType discountType = (DiscountType) object
        if (discountType.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        DiscountType discountType = (DiscountType) object
        discountType.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<DiscountType> objList = DiscountType.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = DiscountType.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<DiscountType> list() {
        return DiscountType.list()
    }

    @Transactional(readOnly = true)
    public DiscountType read(Long id) {
        return DiscountType.read(id)
    }

    @Transactional(readOnly = true)
    public DiscountType search(String fieldName, String fieldValue) {
        String query = "from DiscountType as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return DiscountType.find(query)
    }
}
