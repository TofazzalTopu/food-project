package com.bits.bdfp.inventory.product

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class ProductCategoryService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public ProductCategory create(Object object) {
        ProductCategory productCategory = (ProductCategory) object
        if (productCategory.save(false)) {
            return productCategory
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        ProductCategory productCategory = (ProductCategory) object
        if (productCategory.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        ProductCategory productCategory = (ProductCategory) object
        productCategory.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<ProductCategory> objList = ProductCategory.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = ProductCategory.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<ProductCategory> list() {
        return ProductCategory.list()
    }

    @Transactional(readOnly = true)
    public ProductCategory read(Long id) {
        return ProductCategory.read(id)
    }

    @Transactional(readOnly = true)
    public ProductCategory search(String fieldName, String fieldValue) {
        String query = "from ProductCategory as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return ProductCategory.find(query)
    }




}
