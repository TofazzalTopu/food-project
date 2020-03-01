package com.bits.bdfp.customer

import com.docu.common.Action
import groovy.sql.Sql
import com.docu.common.Service
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource

class CustomerStockService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql
    @Transactional
    public CustomerStock create(Object object) {
        CustomerStock customerStock = (CustomerStock) object
        if (customerStock.save(false)) {
            return customerStock
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        CustomerStock customerStock = (CustomerStock) object
        if (customerStock.save()) {
            return new Integer(1)
        }
        else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        CustomerStock customerStock = (CustomerStock) object
        customerStock.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<CustomerStock> objList = CustomerStock.withCriteria {
            if(action.resultPerPage !=-1){
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = CustomerStock.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<CustomerStock> list() {
        return CustomerStock.list()
    }

    @Transactional(readOnly = true)
    public CustomerStock read(Long id) {
        return CustomerStock.read(id)
    }

    @Transactional(readOnly = true)
    public CustomerStock search(String fieldName, String fieldValue) {
        String query = "from CustomerStock as docu where docu."+ fieldName +" = '" + fieldValue +"'"
        return CustomerStock.find(query)
    }

    @Transactional(readOnly = true)
    public List customerAvailableStock(Object params){
        try{
            String query = ""
            sql = new Sql(dataSource)
            if (params.query) {
                query = """
                    AND ( finish_product.code LIKE '%${params.query}%' OR finish_product.name LIKE '%${params.query}%')
                """
            }
            String strSql = """
                SELECT `customer_stock`.id, `finish_product`.`id` AS productId, `finish_product`.`code` AS productCode, `finish_product`.`name` AS productName, IFNULL(`customer_stock`.`batch_no`,'') AS batchNo,
                    (`customer_stock`.`in_quantity` - `customer_stock`.`out_quantity`) AS availableQuantity
                FROM `customer_stock`
                    INNER JOIN `finish_product` ON (`customer_stock`.`finish_product_id` = `finish_product`.`id`)
                    INNER JOIN `main_product` ON (`finish_product`.`main_product_id` = `main_product`.`id`)
                    INNER JOIN `master_product` ON (`finish_product`.`master_product_id` = `master_product`.`id`)
                WHERE `customer_stock`.`in_quantity` > `customer_stock`.`out_quantity`
                    AND `customer_stock`.`delivery_man_id` = ${params.customerId}
                    AND `customer_stock`.id NOT IN (${params.customerStockIds})
                    ${query}
                ORDER BY `master_product`.`sequence_number`, `main_product`.`sequence_number`, `finish_product`.`sequence_number`, `finish_product`.`name`
            """
            return sql.rows(strSql)
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
