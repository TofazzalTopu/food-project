package com.bits.bdfp.inventory.product

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class ProductPackageService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public ProductPackage create(Object object) {
        ProductPackage productPackage = (ProductPackage) object
        if (productPackage.save(false)) {
            return productPackage
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        ProductPackage productPackage = (ProductPackage) object
        if (productPackage.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        ProductPackage productPackage = (ProductPackage) object
        productPackage.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<ProductPackage> objList = ProductPackage.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = ProductPackage.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<ProductPackage> list() {
        return ProductPackage.list()
    }

    @Transactional(readOnly = true)
    public ProductPackage read(Long id) {
        return ProductPackage.read(id)
    }

    @Transactional(readOnly = true)
    public ProductPackage search(String fieldName, String fieldValue) {
        String query = "from ProductPackage as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return ProductPackage.find(query)
    }
}
