package com.bits.bdfp.inventory.setup

import com.bits.bdfp.inventory.product.FinishProduct
import org.springframework.transaction.annotation.Transactional
import com.docu.commons.CommonConstants
import com.docu.common.Service
import groovy.sql.Sql
import javax.sql.DataSource
import com.docu.common.Action

class VatRateService extends Service {
    static transactional = false
    DataSource dataSource

    @Transactional(readOnly = true)
    public VatRate read(Long id) {
        return VatRate.read(id)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Map params) {
        try {
            List<VatRate> vatRateList = VatRate.withCriteria {
                maxResults(Integer.parseInt(action.resultPerPage.toString()))
                firstResult(Integer.parseInt(action.start.toString()))
                order(action.sortCol.toString(), action.sortOrder.toString())
            }
            long vatRateCount = VatRate.count()
            return [objList: vatRateList, count: vatRateCount]
        }
        catch(Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List<VatRate> list() {
        return VatRate.list()
    }

    @Transactional
    public VatRate create(Object object) {
        try {
            VatRate vatRate = (VatRate) object
            return vatRate.save(vaidate: false, insert: true)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object object) {
        try {
            VatRate vatRate = (VatRate) object
            if(vatRate.save(vaidate: false)){
                return new Integer(1)
            } else {
                return new Integer(0)
            }
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        try {
            VatRate vatRate = (VatRate) object
            vatRate.delete()
            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public VatRate search(String fieldName, String fieldValue) {
        String query = "from VatRate as docu where docu."+ fieldName +" = '" + fieldValue +"'"
        return VatRate.find(query)
    }

    @Transactional(readOnly = true)
    public FinishProduct getProductById(Long id) {
        return FinishProduct.read(id)
    }

    @Transactional(readOnly = true)
    public List getProductListByKey(String key){
        String query = """
            SELECT * FROM finish_product
            WHERE NAME LIKE '%${key}%' OR CODE LIKE '%${key}%'
        """

        Sql sql = new Sql(dataSource)
        List list = sql.rows(query)

        return list
    }
}
