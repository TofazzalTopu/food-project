package com.bits.bdfp.inventory.demandorder

import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import groovy.sql.Sql
import javax.sql.DataSource
import com.docu.common.Action

class AdjustUsingHoService extends Service {
    static transactional = false

    DataSource dataSource
    Sql sql

    @Transactional(readOnly = true)
    public AdjustUsingHo read(Long id) {
        return AdjustUsingHo.read(id)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Map params) {
        try {
            List<AdjustUsingHo> adjustUsingHoList = AdjustUsingHo.withCriteria {
                maxResults(Integer.parseInt(action.resultPerPage.toString()))
                firstResult(Integer.parseInt(action.start.toString()))
                order(action.sortCol.toString(), action.sortOrder.toString())
            }
            long adjustUsingHoCount = AdjustUsingHo.count()
            return [objList: adjustUsingHoList, count: adjustUsingHoCount]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List<AdjustUsingHo> list() {
        return AdjustUsingHo.list()
    }

    @Transactional
    public Integer create(Object map) {
        try {
            AdjustUsingHo adjustUsingHo = map.adjustUsingHo
            List<AdjustUsingHoDetails> adjustUsingHoDetailsList = map.adjustUsingHoDetailsList
            List<Invoice> invoices = map.invoices
            if (adjustUsingHo) {
                adjustUsingHo.save(false)
                if (adjustUsingHoDetailsList && adjustUsingHoDetailsList.size() > 0) {
                    adjustUsingHoDetailsList.each {
                        it.save(false)
                    }
                }
                invoices.each {
                    it.save()
                }
                Journal journal = (Journal) map.get('journal')
                journal.save(validate: false, insert: true)

                List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.get('journalDetailsList')
                journalDetailsList.each { journalDetails ->
                    journalDetails.save(validate: false, insert: true)
                }
            }
            return new Integer(1)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object object) {
        try {
            AdjustUsingHo adjustUsingHo = (AdjustUsingHo) object
            if (adjustUsingHo.save(vaidate: false)) {
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
            AdjustUsingHo adjustUsingHo = (AdjustUsingHo) object
            adjustUsingHo.delete()
            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public AdjustUsingHo search(String fieldName, String fieldValue) {
        String query = "from AdjustUsingHo as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return AdjustUsingHo.find(query)
    }

    @Transactional(readOnly = true)
    public List fetchCustomer(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT DISTINCT `customer_master`.`id`,`customer_master`.`name`,`customer_master`.`code`,
                                `customer_category`.`name` AS category
                            FROM `customer_master`
                            INNER JOIN `customer_category` ON `customer_category`.`id` = `customer_master`.`category_id`
                            INNER JOIN `customer_territory_sub_area`
                                ON `customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`
                            WHERE `customer_territory_sub_area`.`territory_sub_area_id` = ${params.geoId}
                                AND `customer_master`.`customer_level` = 'PRIMARY'
                          """
        List objList = sql.rows(strSql)
        return objList
    }
}
