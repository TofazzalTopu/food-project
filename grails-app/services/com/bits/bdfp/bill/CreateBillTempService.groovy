package com.bits.bdfp.bill

import com.docu.commons.CommonConstants
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import groovy.sql.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource

class CreateBillTempService {

    static transactional = false
    DataSource dataSource
    Sql sql

    @Autowired
    SpringSecurityService springSecurityService

    @Transactional(readOnly = true)
    public List getBillInfo(Object params) {
        sql = new Sql(dataSource)
        String strSql = CommonConstants.EMPTY_STRING;
        // String condition = CommonConstants.EMPTY_STRING;

        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()

        List objList = null;
        //if (params?.po) {
        /*strSql = """
               SELECT c.purchase_order_number, c.delivery_date, c.vat_challan_number, c.purchase_order_date, c.receivable_amount, d.invoice_id, d.quantity, d.unit_price, f.description,  cm.name AS "CustomerName", cm.present_address, c.bill_number
                FROM  create_bill_temp as c, invoice_details as d, invoice as i,  finish_product as f, customer_master as cm
                WHERE i.code = c.invoice_number
                AND cm.id=c.customer_name_id
                AND f.id= d.finish_product_id
                GROUP BY i.`id`
                """
*/
        strSql = """

            SELECT c.purchase_order_number, c.delivery_date, c.vat_challan_number, c.purchase_order_date, ROUND(c.receivable_amount,2) AS receivable_amount, id.quantity, id.unit_price,
        f.name AS "productName",  cm.name AS "CustomerName", cm.present_address, c.bill_number, au.email_address AS userEmail, au.full_name,
        cm.mobile, enc.name AS "enterpriseName",  d.name AS "designationName", enc.address AS en_address, enc.email AS en_email, enc.phone AS en_phone_number, enc.fax AS en_fax, enc.extension
        FROM  create_bill_temp AS c, invoice_details AS id, invoice AS i,  finish_product AS f, customer_master AS cm, application_user AS au,
              enterprise_configuration AS enc, designation AS d
        WHERE i.code = c.invoice_number
        AND i.is_active = true
        AND cm.id=c.customer_master_id
        AND id.`invoice_id`=i.`id`
        AND f.id= id.finish_product_id
        AND  enc.id=cm.enterprise_configuration_id

        AND au.id= ${applicationUser.id}
        GROUP BY i.`id`
        """
        objList = sql.rows(strSql)
        //}


        return objList;



    }

    def serviceMethod() {

    }

/*
        SELECT c.purchase_order_number, c.delivery_date, c.vat_challan_number, c.purchase_order_date, ROUND(c.receivable_amount,4) AS receivable_amount, id.quantity, id.unit_price,
                f.name as "productName",  cm.name AS "CustomerName", cm.present_address, c.bill_number, au.email_address AS userEmail, au.full_name,
        cm.mobile, enc.name AS "enterpriseName",  d.name AS "designationName", enc.en_address, enc.en_email, enc.en_phone_number, enc.en_fax, enc.en_extention
        FROM  create_bill_temp AS c, invoice_details AS id, invoice AS i,  finish_product AS f, customer_master AS cm, application_user AS au,
                                                                                                                                           enterprise_configuration enc, designation AS d
        WHERE i.code = c.invoice_number
        AND cm.id=c.customer_master_id
        AND id.`invoice_id`=i.`id`
        AND f.id= id.finish_product_id
        AND  enc.id=cm.enterprise_configuration_id
        AND d.id=cm.designation_id
        AND au.id= ${applicationUser.id}
        GROUP BY i.`id`
*/


    @Transactional
    public Integer create(Map map) {
        try {
            List<CreateBillTemp> createBillList =map.createBillTempList
            Integer count=0;
            createBillList.each {
                if(it.save()){

                    count++
                }
            }
            return count
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    /*@Transactional
    public Boolean delete() {
        try {
            sql = new Sql(dataSource)
            String strSql = CommonConstants.EMPTY_STRING;

            strSql = """
                   TRUNCATE create_bill_temp
                    """
            Boolean dl= CreateBillTemp.executeQuery(strSql);

            return dl
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }*/
}

