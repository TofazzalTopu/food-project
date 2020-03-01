package com.bits.bdfp.inventory.setup

import org.springframework.transaction.annotation.Transactional
import com.docu.commons.CommonConstants
import com.docu.common.Service
import groovy.sql.Sql
import javax.sql.DataSource
import com.docu.common.Action

class QuotationService extends Service {
    static transactional = false

    Sql sql
    DataSource dataSource

    @Transactional(readOnly = true)
    public Quotation read(Long id) {
        return Quotation.read(id)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Map params) {
        try {
            List<Quotation> quotationList = Quotation.withCriteria {
                maxResults(Integer.parseInt(action.resultPerPage.toString()))
                firstResult(Integer.parseInt(action.start.toString()))
                order(action.sortCol.toString(), action.sortOrder.toString())
            }
            long quotationCount = Quotation.count()
            return [objList: quotationList, count: quotationCount]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List<Quotation> list() {
        return Quotation.list()
    }

    @Transactional
    public Integer create(Object map) {
        Quotation quotation = map.quotation
        List<QuotationDetails> quotationDetailsList = map.quotationDetailsList
        if (quotation) {
            quotation.save(false)
            if (quotationDetailsList && quotationDetailsList.size() > 0) {
                quotationDetailsList.each {
                    it.save(false)
                }
            }
        }
        return new Integer(1)
    }

    @Transactional
    public Integer update(Object map) {
        Quotation quotation = map.quotation
        List<QuotationDetails> quotationDetailsList = map.quotationDetailsList
        if (quotation) {
            quotation.save()
            if (quotationDetailsList && quotationDetailsList.size() > 0) {
                quotationDetailsList.each {
                    if(it.id)
                        it.save()
                    else
                        it.save(false)
                }
            }
        }
        return new Integer(1)
    }

    @Transactional
    public Integer delete(Object object) {
        try {
            QuotationDetails quotationDetails = (QuotationDetails) object
            quotationDetails.delete()
            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Quotation search(String fieldName, String fieldValue) {
        String query = "from Quotation as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return Quotation.find(query)
    }

    @Transactional(readOnly = true)
    public List fetchDetails(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `quotation_details`.`id`,`finish_product`.`name`,`finish_product`.`code`,
                                `quotation_details`.`rate`,`quotation_details`.`quantity`,`quotation_details`.`total`,
                                `finish_product`.`id` AS product_id
                            FROM `quotation_details`
                            INNER JOIN `finish_product` ON `finish_product`.id = `quotation_details`.`id`
                            WHERE `quotation_details`.`quotation_id` = ${params.id}
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List fetchQuotationNo(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT id,`quotation_no`,`customer_name`
                            FROM `quotation`
                            WHERE `enterprise_configuration_id` = ${params.entId}
                            AND `is_active` = TRUE
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List fetchProductList(Object params) {
        sql = new Sql(dataSource)
        String cond = ''
        String [] ids = params.quotations.split(',')
        for(int i = 0; i < ids.length; i++){
            if(i != 0){
                cond = cond + """ OR"""
            }
            cond = cond + """ `quotation_details`.`quotation_id` = ${ids[i]}"""
        }
        String strSql = """
                            SELECT `quotation_details`.`finish_product_id`,`finish_product`.`name`,`finish_product`.`code`
                            FROM `quotation_details`
                            INNER JOIN `finish_product` ON `finish_product`.`id` = `quotation_details`.`finish_product_id`
                            WHERE ${cond}
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List fetchCsDetails(Object params) {
        sql = new Sql(dataSource)
        String cond = """("""
        String [] ids = params.quotations.split(',')
        for(int i = 0; i < ids.length; i++){
            if(i != 0){
                cond = cond + """ OR"""
            }
            cond = cond + """ `quotation_details`.`quotation_id` = ${ids[i]}"""
        }
        ids = params.products.split(',')
        cond = cond + """) AND ("""
        for(int i = 0; i < ids.length; i++){
            if(i != 0){
                cond = cond + """ OR"""
            }
            cond = cond + """ `quotation_details`.`finish_product_id` = ${ids[i]}"""
        }
        cond = cond + """)"""
        String strSql = """
                            SELECT `quotation_details`.`finish_product_id`,`quotation_details`.`rate`,`quotation_details`.`quantity`,
                                `quotation_details`.`total`,`finish_product`.`name`,`finish_product`.`code`,`quotation`.`id`,
                                `quotation`.`customer_name`,`quotation`.`quotation_no`,DATE_FORMAT(`quotation`.`quotation_date`,'%d-%m-%Y') AS quotation_date,
                                DATE_FORMAT(`quotation`.`valid_from`,'%d-%m-%Y') AS valid_from,DATE_FORMAT(`quotation`.`valid_to`,'%d-%m-%Y') AS valid_to
                            FROM `quotation`
                            INNER JOIN `quotation_details` ON `quotation_details`.`quotation_id` = `quotation`.`id`
                            INNER JOIN `finish_product` ON `finish_product`.`id` = `quotation_details`.`finish_product_id`
                            WHERE ${cond}
                          """
        List objList = sql.rows(strSql)
        return objList
    }
}
