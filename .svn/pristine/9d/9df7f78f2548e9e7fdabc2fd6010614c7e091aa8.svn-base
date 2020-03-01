package com.bits.bdfp.inventory.product

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class PricingCategoryService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public PricingCategory create(Object object) {
        PricingCategory pricingCategory = (PricingCategory) object
        if (pricingCategory.save(false)) {
            return pricingCategory
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        PricingCategory pricingCategory = (PricingCategory) object
        if (pricingCategory.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        PricingCategory pricingCategory = (PricingCategory) object
        pricingCategory.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<PricingCategory> objList = PricingCategory.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = PricingCategory.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<PricingCategory> list() {
        return PricingCategory.list()
    }

    @Transactional(readOnly = true)
    public PricingCategory read(Long id) {
        return PricingCategory.read(id)
    }

    @Transactional(readOnly = true)
    public PricingCategory search(String fieldName, String fieldValue) {
        String query = "from PricingCategory as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return PricingCategory.find(query)
    }
}
