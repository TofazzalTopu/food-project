package com.bits.bdfp.inventory.product

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class ProductPricingTypeService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public ProductPricingType create(Object object) {
        ProductPricingType productPricingType = (ProductPricingType) object
        if (productPricingType.save(false)) {
            return productPricingType
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        ProductPricingType productPricingType = (ProductPricingType) object
        if (productPricingType.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        ProductPricingType productPricingType = (ProductPricingType) object
        productPricingType.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<ProductPricingType> objList = ProductPricingType.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = ProductPricingType.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<ProductPricingType> list() {
        return ProductPricingType.list()
    }

    @Transactional(readOnly = true)
    public ProductPricingType read(Long id) {
        return ProductPricingType.read(id)
    }

    @Transactional(readOnly = true)
    public ProductPricingType search(String fieldName, String fieldValue) {
        String query = "from ProductPricingType as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return ProductPricingType.find(query)
    }
}
