package com.bits.bdfp.inventory.demandorder

import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import groovy.sql.Sql
import javax.sql.DataSource
import com.docu.common.Action

class WriteOffService extends Service {
    static transactional = false

    Sql sql
    DataSource dataSource

    @Transactional(readOnly = true)
    public WriteOff read(Long id) {
        return WriteOff.read(id)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Map params) {
        try {
            List<WriteOff> writeOffList = WriteOff.withCriteria {
                maxResults(Integer.parseInt(action.resultPerPage.toString()))
                firstResult(Integer.parseInt(action.start.toString()))
                order(action.sortCol.toString(), action.sortOrder.toString())
            }
            long writeOffCount = WriteOff.count()
            return [objList: writeOffList, count: writeOffCount]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List<WriteOff> list() {
        return WriteOff.list()
    }

    @Transactional
    public Integer create(Object map) {
        try{
            List<WriteOff> writeOffs = map.writeOffs
            List<Invoice> invoices = map.invoices
            if (writeOffs && writeOffs.size() > 0) {
                writeOffs.each {
                    it.save(false)
                }
            }
            invoices.each {
                it.save()
            }
            if(map.branchInvoices){
                List<Invoice> branchInvoices = map.branchInvoices
                branchInvoices.each {
                    it.save()
                }
            }

            //***********************************COA Start*******************************//

                Journal journal = (Journal) map.get('journal')
                journal.save(validate: false, insert: true)

                List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.get('journalDetailsList')
                journalDetailsList.each { journalDetails->
                    journalDetails.save(validate: false, insert: true)
                }

            //*********************************COA Start*******************************//

            return new Integer(1)
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object object) {
        try {
            WriteOff writeOff = (WriteOff) object
            if (writeOff.save(vaidate: false)) {
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
            WriteOff writeOff = (WriteOff) object
            writeOff.delete()
            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public WriteOff search(String fieldName, String fieldValue) {
        String query = "from WriteOff as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return WriteOff.find(query)
    }

    @Transactional(readOnly = true)
    public List fetchTerritory(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `id`,`name`
                            FROM `territory_configuration`
                            WHERE `enterprise_configuration_id` = ${params.entId}
                            ORDER BY `id`
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List fetchGeo(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `id`,CONCAT(`geo_location`, ', ', `para_or_locality`, ', ', `road`) AS `name`
                            FROM `territory_sub_area`
                            WHERE `territory_configuration_id` = ${params.territoryId}
                            ORDER BY `id`
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List fetchDp(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `distribution_point`.`id`,`distribution_point`.`name`,`customer_master`.`name` AS customer,
                                `customer_master`.`code` AS customer_code
                            FROM `distribution_point`
                            INNER JOIN `distribution_point_territory_sub_area`
                                ON `distribution_point_territory_sub_area`.`distribution_point_id` = `distribution_point`.`id`
                            INNER JOIN `distribution_point_warehouse`
                                ON `distribution_point_warehouse`.`distribution_point_id` = `distribution_point`.`id`
                            INNER JOIN `customer_master`
                                ON `customer_master`.id = `distribution_point_warehouse`.`default_customer_id`
                            WHERE `distribution_point_territory_sub_area`.`territory_sub_area_id` = ${params.geoId}
                            ORDER BY `id`
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List fetchCustomer(Object params) {
        sql = new Sql(dataSource)
        String cond = """"""
        if(params.cat){
            cond = """
                    INNER JOIN `distribution_point_territory_sub_area`
                        ON `distribution_point_territory_sub_area`.`territory_sub_area_id` = `customer_territory_sub_area`.`territory_sub_area_id`
                    WHERE customer_master.customer_level = 'SECONDARY'
				        AND `distribution_point_territory_sub_area`.`distribution_point_id` = ${params.dpId}
                   """
        }else{
            cond = """
                    WHERE `customer_territory_sub_area`.`territory_sub_area_id` = ${params.geoId}
                        AND `customer_master`.`customer_level` = 'PRIMARY'
                        AND `customer_master`.`category_id` != 1
                   """
        }
        String strSql = """
                            SELECT DISTINCT `customer_master`.`id`,`customer_master`.`name`,`customer_master`.`code`,
                                `customer_category`.`name` AS category
                            FROM `customer_master`
                            INNER JOIN `customer_category` ON `customer_category`.`id` = `customer_master`.`category_id`
                            INNER JOIN `customer_territory_sub_area`
                                ON `customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`
                            ${cond}
                            GROUP BY `customer_master`.`id`
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List fetchInvoice(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
            SELECT invoice.`id`,DATE_FORMAT(`invoice`.`date_created`,'%d-%m-%Y') AS date_created,
                `invoice`.`code`,ROUND(`invoice`.`invoice_amount`,2) AS invoice_amount,
                ROUND(`invoice`.`invoice_amount`-`invoice`.`paid_amount`,2) AS due_amount
            FROM `invoice`
                INNER JOIN `customer_master` ON (`customer_master`.`id` = `invoice`.`default_customer_id`)
            WHERE `invoice`.`is_active` = true
                AND `customer_master`.id = ${params.customerId}
                AND `invoice`.`invoice_amount`-`invoice`.`paid_amount` > 0
        """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List findInvoicesForBranch(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
            SELECT invoice.`id`,`invoice`.`date_created`,
                (`invoice`.`invoice_amount`-`invoice`.`paid_amount`) AS due_amount
            FROM `invoice`
                INNER JOIN `customer_master` ON (`customer_master`.`id` = `invoice`.`default_customer_id`)
            WHERE `invoice`.`is_active` = true
                AND `customer_master`.code = ${params.defaultCustomerCode}
                AND `invoice`.`invoice_amount`-`invoice`.`paid_amount` > 0
            ORDER BY `invoice`.`date_created`
        """
        List objList = sql.rows(strSql)
        return objList
    }
}
