package com.bits.bdfp.accounts

import org.springframework.transaction.annotation.Transactional
import com.docu.commons.CommonConstants
import com.docu.common.Service
import groovy.sql.Sql
import sun.misc.REException

import javax.sql.DataSource
import com.docu.common.Action

class MushakService extends Service {
    static transactional = false

    DataSource dataSource
    Sql sql

    @Transactional(readOnly = true)
    public Mushak read(Long id) {
        return Mushak.read(id)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Map params) {
        try {
            List<Mushak> mushakList = Mushak.withCriteria {
                maxResults(Integer.parseInt(action.resultPerPage.toString()))
                firstResult(Integer.parseInt(action.start.toString()))
                order(action.sortCol.toString(), action.sortOrder.toString())
            }
            long mushakCount = Mushak.count()
            return [objList: mushakList, count: mushakCount]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List<Mushak> list() {
        return Mushak.list()
    }

    @Transactional
    public Object create(Object map) {
        try{
            Mushak mushak = map.mushak
            List<MushakDetails> mushakDetailsList = map.mushakDetailsList
            if (mushak) {
                mushak.save(false)
                if (mushakDetailsList && mushakDetailsList.size() > 0) {
                    mushakDetailsList.each {
                        it.save(false)
                    }
                }

                Journal journal = (Journal) map.get('journal')
                journal.save(validate: false, insert: true)

                List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.get('journalDetailsList')
                journalDetailsList.each { journalDetails->
                    journalDetails.save(validate: false, insert: true)
                }

            }
            return new Integer(1)
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object object) {
        try {
            Mushak mushak = object.mushak
            List<MushakDetails> mushakDetailsList = object.mushakDetailsList
            if (mushak) {
                mushak.save()
                if (mushakDetailsList && mushakDetailsList.size() > 0) {
                    mushakDetailsList.each {
                        it.save()
                    }
                }
            }

            Journal journal =object.journalHead
            journal.save(validate: false, insert: true)

            List<JournalDetails> journalDetailsList = object.journalDetailsList
            journalDetailsList.each { journalDetails->
                journalDetails.save(validate: false, insert: true)
            }

            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        try {
            Mushak mushak = (Mushak) object
            String strSql = """
                            DELETE FROM `mushak_details`
                            WHERE `mushak_id` = ${mushak.id}
                          """
            sql.execute(strSql)
            strSql = """
                            DELETE FROM `sub_ledger`
                            WHERE `transaction_no` = ${mushak.mushakNo}
                          """
            sql.execute(strSql)
            mushak.delete()
            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Mushak search(String fieldName, String fieldValue) {
        String query = "from Mushak as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return Mushak.find(query)
    }

    @Transactional(readOnly = true)
    public List fetchInvoice(Object params) {
        sql = new Sql(dataSource)
        String cond = """"""
        if(params.date){
            cond = """AND DATE(`invoice`.`date_created`) = STR_TO_DATE('${params.date}','%d-%m-%Y')"""
        }
        String strSql = """
                            SELECT `invoice`.`id`,`invoice`.`code`,DATE_FORMAT(`invoice`.`date_created`,'%d-%m-%Y') AS date_created,
                                `customer_master`.`name`,`customer_master`.`present_address`
                            FROM `invoice`
                            INNER JOIN `customer_master` ON `invoice`.`default_customer_id` = `customer_master`.`id`
                            WHERE invoice.is_active = true
                                AND `invoice`.`distribution_point_id` = ${params.id}
                                AND `invoice`.`id` NOT IN (SELECT `invoice_id` FROM `mushak`)
                                AND `customer_master`.`customer_level` = 'PRIMARY'
                                ${cond}
                            GROUP BY `invoice`.`id`
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List fetchInvoiceDetails(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `invoice_details`.`finish_product_id`,`invoice_details`.`quantity`,`finish_product`.`name`,
                                ROUND((`invoice_details`.`quantity`*`vat_rate`.`rate`),2) AS sd_impose,`vat_rate`.`rate`,
                                IFNULL(`vat_rate`.`supplementary_duty`,0) AS supplementary_duty,`vat_rate`.`vat`,
                                ROUND((`invoice_details`.`quantity`*`vat_rate`.`rate`)*
                                IFNULL(`vat_rate`.`supplementary_duty`,0)/100,2) AS sd_amount,
                                ROUND(((`invoice_details`.`quantity`*`vat_rate`.`rate`)+
                                (`invoice_details`.`quantity`*`vat_rate`.`rate`)*
                                IFNULL(`vat_rate`.`supplementary_duty`,0)/100)*`vat_rate`.`vat`/100,2) AS vat_amount
                            FROM `invoice_details`
                            INNER JOIN `invoice` ON `invoice`.`id` = `invoice_details`.`invoice_id`
                            INNER JOIN `finish_product` ON `finish_product`.`id` = `invoice_details`.`finish_product_id`
                            INNER JOIN `vat_rate` ON `vat_rate`.`finish_product_id` = `invoice_details`.`finish_product_id`
                            WHERE `invoice`.`id` = ${params.id}
                                AND DATE(`invoice`.`date_created`)
                                BETWEEN DATE(`vat_rate`.`effective_from_date`)
                                AND DATE(`vat_rate`.`effective_to_date`)
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List fetchDp(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `distribution_point`.`id`,`distribution_point`.`name`,`distribution_point`.`is_factory`
                            FROM `distribution_point`
                            INNER JOIN `distribution_point_territory_sub_area`
                                ON `distribution_point_territory_sub_area`.`distribution_point_id` = `distribution_point`.`id`
                            INNER JOIN `customer_territory_sub_area`
                                ON `customer_territory_sub_area`.`territory_sub_area_id` = `distribution_point_territory_sub_area`.`territory_sub_area_id`
                            INNER JOIN `application_user`
                                ON `application_user`.`customer_master_id` = `customer_territory_sub_area`.`customer_master_id`
                            WHERE `application_user`.`id` = ${params.id}
                            GROUP BY `distribution_point`.`id`
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List listMushak(Object params) {
        sql = new Sql(dataSource)
        String cond = ''
        if(params.id){
            cond = """WHERE `distribution_point_id` = ${params.id}"""
        }
        if(params.mushakId){
            cond = """WHERE `id` = ${params.mushakId}"""
        }
        String strSql = """
                            SELECT id,`mushak_no`,`challan_no`,`name`,
                                DATE_FORMAT(`challan_handover_date`,'%d-%m-%Y') AS challan_handover_date
                            FROM mushak
                            ${cond}
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List searchMushak(Object params) {
        sql = new Sql(dataSource)
        String cond = ''
        if(params.challan){
            cond = """WHERE `challan_no` = '${params.challan}'"""
        }
        if(params.mushak){
            cond = """WHERE `mushak_no` = '${params.mushak}'"""
        }
        String strSql = """
                            SELECT id,`name`,`challan_no`,`mushak_no`,
                                DATE_FORMAT(`challan_handover_date`,'%d-%m-%Y') AS challan_handover_date
                            FROM mushak
                            ${cond}
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List listMushakDetails(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `mushak_details`.`id`,`mushak_details`.`finish_product_id`,`mushak_details`.`quantity`,
                                `mushak_details`.`rate`,`mushak_details`.`sd_amount`,`mushak_details`.`total_amount`,
                                `mushak_details`.`vat_amount`,`mushak_details`.`sd_percent`,`mushak_details`.`vat_percent`,
                                `finish_product`.`name`,ROUND((`mushak_details`.`rate`*`mushak_details`.`quantity`),4) AS sd_impose,
                                ROUND((`mushak_details`.`rate`*`mushak_details`.`quantity`)+`mushak_details`.`sd_amount`,4) AS vat_impose
                            FROM `mushak_details`
                            INNER JOIN `finish_product` ON `finish_product`.`id` = `mushak_details`.`finish_product_id`
                            WHERE `mushak_details`.`mushak_id` = ${params.id}
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional
    public Integer deleteDetail(Object object) {
        try {
            MushakDetails mushakDetails = object.mushakDetails
            mushakDetails.delete()
            String code = object.code
            String mushakNo = object.mushakNo
            sql = new Sql(dataSource)
            String strSql = """
                            SELECT id
                            FROM `sub_ledger`
                            WHERE acc_code = ${code}
                                AND `transaction_no` = ${mushakNo}
                          """
            List objList = sql.rows(strSql)
            String cond = """id = ${objList[0].id} OR id = ${objList[0].id + 1}"""
            strSql = """
                            DELETE FROM `sub_ledger`
                            WHERE ${cond}
                          """
            sql.execute(strSql)
            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
