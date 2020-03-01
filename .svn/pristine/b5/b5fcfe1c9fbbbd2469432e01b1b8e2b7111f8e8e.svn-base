package com.bits.bdfp.customer

import com.bits.bdfp.util.ApplicationConstants
import com.docu.security.ApplicationUser
import groovy.sql.GroovyRowResult
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class CustomerCategoryService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public CustomerCategory create(Object object) {
        CustomerCategory customerCategory = (CustomerCategory) object
        if (customerCategory.save(false)) {
            return customerCategory
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        CustomerCategory customerCategory = (CustomerCategory) object
        if (customerCategory.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        CustomerCategory customerCategory = (CustomerCategory) object
        customerCategory.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<CustomerCategory> objList = CustomerCategory.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = CustomerCategory.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<CustomerCategory> list() {
        return CustomerCategory.list()
    }

    @Transactional(readOnly = true)
    public CustomerCategory read(Long id) {
        return CustomerCategory.read(id)
    }

    @Transactional(readOnly = true)
    public CustomerCategory search(String fieldName, String fieldValue) {
        String query = "from CustomerCategory as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return CustomerCategory.find(query)
    }


    @Transactional(readOnly = true)
    public List<GroovyRowResult> fetchCustomerCategory() {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT id, name
                            FROM customer_category
                            WHERE id <> ${ApplicationConstants.CUSTOMER_CATEGORY_RETAIL_ID}
                            ORDER BY NAME
                          """
        List<GroovyRowResult> resultList = sql.rows(strSql.toString())
        return resultList
    }

    @Transactional(readOnly = true)
    public List searchCustomerCategory() {
        String query = """
                           SELECT DISTINCT
                           `customer_category`.`id`
                           , `customer_category`.`name`
                            FROM
                           `customer_category`
                            INNER JOIN `customer_master`
                            ON (`customer_category`.id = `customer_master`.`category_id`)
                            WHERE `customer_master`.`customer_level`  != 'SECONDARY'
                        """
        Sql sql = new Sql(dataSource)
        return sql.rows(query)
    }
}
