package com.bits.bdfp.bill

import com.bits.bdfp.customer.CustomerStatus
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import groovy.sql.GroovyRowResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import com.docu.commons.CommonConstants
import com.docu.common.Service
import groovy.sql.Sql
import javax.sql.DataSource
import com.docu.common.Action

class CreateBillService {
    static transactional = false
    DataSource dataSource
    Sql sql


    @Autowired
    SpringSecurityService springSecurityService

    @Transactional(readOnly = true)
    public CreateBill read(Long id) {
        return CreateBill.read(id)
    }

    @Transactional(readOnly = true)
    public Map getListByBillForGrid(Action action, Map params) {
        try {
            String qry = """
            from CreateBill where billNumber= ${params.billNumber} """;
            List<CreateBill> createBillList = [];
            createBillList = CreateBill.executeQuery(qry);

            long createBillCount = CreateBill.count()
            return [objList: createBillList, count: createBillCount]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Map getAllListForGrid(Action action, Map params) {
        try {
            List<CreateBill> createBillList = CreateBill.withCriteria {
                maxResults(Integer.parseInt(action.resultPerPage.toString()))
                firstResult(Integer.parseInt(action.start.toString()))
                order(action.sortCol.toString(), action.sortOrder.toString())
            }

            long createBillCount = CreateBill.count()
            return [objList: createBillList, count: createBillCount]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Map params) {
        try {

            def now = new Date();
            /* List<CreateBill> createBillList = CreateBill.withCriteria {
                 maxResults(Integer.parseInt(action.resultPerPage.toString()))
                 firstResult(Integer.parseInt(action.start.toString()))
                 order(action.sortCol.toString(), action.sortOrder.toString())
             }*/
            sql = new Sql(dataSource)

            String createBillList = """
                SELECT  id, bill_number AS billNumber, bill_generation_date AS billGenerationDate, SUM(`receivable_amount`) AS receivableAmount
                FROM create_bill
                WHERE is_active = TRUE
            """

            if (params.billNum) {
                createBillList = createBillList + " AND bill_number= '${params.billNum}' ";
            }
            if (params.cusId) {
                createBillList = createBillList + " AND customer_name_id='${params.cusId}' "
            }
            if (params.frmDate && params.toDate) {
                createBillList = createBillList + " AND bill_generation_date BETWEEN ('${params.frmDate}') AND ('${params.toDate}') "
            }
            createBillList = createBillList + " GROUP BY bill_number "

            List billList = sql.rows(createBillList);

            long createBillCount = CreateBill.count()
            return [objList: billList, count: createBillCount]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List<CreateBill> list() {
        return CreateBill.list()
    }

    @Transactional
    public Integer create(Map map) {
        try {
            List<CreateBill> createBillList = map.createBillList
            Integer count = 0;
            String qry = '';
            createBillList.each {
                if (it.save()) {
                    count++
                }
            }

            createBillList.each {
                qry = """ update Invoice set isBill = 1 where code = '${it.invoiceNumber}' """;
                Invoice.executeUpdate(qry)
            }
            return count
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object object) {
        try {
            CreateBill createBill = (CreateBill) object
            if (createBill.save(vaidate: false)) {
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
            CreateBill createBill = (CreateBill) object
            createBill.delete()
            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public CreateBill search(String fieldName, String fieldValue) {
        String query = "from CreateBill as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return CreateBill.find(query)
    }

    @Transactional(readOnly = true)
    public CreateBill sarchBill() {
        try {
            //sql = new Sql(dataSource)
            String qry = "from CreateBill order by id desc";
            //Integer maxId=  CreateBill.executeQuery(qry);
            return CreateBill.find(qry)
            /*return sql.rows(query)*/


        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listCustomerByCode(Object params) {
        try {
            sql = new Sql(dataSource)
            String query = ""
            if (params.query) {
                query = " AND (`customer_master`.`name` LIKE '%${params.query}%' OR customer_master.code LIKE '%${params.query}%')"
            }
            String strSql = """

                          SELECT DISTINCT
                 `customer_master`.`id`
                , `customer_master`.`code`
                , `customer_master`.`legacy_id`
                , `customer_master`.`name`
                , `customer_status` AS `status`
                , `customer_master`.`present_address` AS `address`
                , `customer_category`.`name` AS `category`
            FROM `customer_territory_sub_area`
                INNER JOIN `customer_master`
                    ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id` AND `customer_master`.`master_type_id` = ${
                ApplicationConstants.CUSTOMER_MASTER_TYPE_ID
            })
                INNER JOIN `customer_category` ON (`customer_master`.`category_id` = `customer_category`.`id`)
            WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                AND `customer_master`.`master_type_id` = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID}
                AND `customer_territory_sub_area`.`territory_sub_area_id` =${params.territorySubAreaId}
              AND `customer_master`.`category_id` = ${params.customerCategory}
        ${query}
        ORDER BY `customer_master`.`name`


            """
            return sql.rows(strSql)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }

    }

    @Transactional(readOnly = true)
    public List searchBillByCriteria(Object params) {
        try {
            sql = new Sql(dataSource)
            String query = ""
            query = " customer_master.name LIKE '%${params.customerName}%' OR customer_master.code LIKE '%${params.customerCode}%'"


            String strSql = """
               SELECT `customer_master`.id, `customer_master`.`code`, `customer_master`.`name`, `enterprise_configuration`.`name` AS `enterprise`,
                     IFNULL(`customer_master`.`present_address`, '') AS `present_address`, `customer_category`.`name` AS `category`
                FROM `customer_master`
                    INNER JOIN `master_type` ON `master_type`.`id` = `customer_master`.`master_type_id`
                    INNER JOIN `customer_category` ON (`customer_master`.`category_id` = `customer_category`.`id`)
                    INNER JOIN `enterprise_configuration`
                         ON (`customer_master`.`enterprise_configuration_id` = `enterprise_configuration`.`id`)
                WHERE ${query}
                ORDER BY `customer_master`.`code`
            """
            return sql.rows(strSql)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List getBillInfo(Object params) {
        sql = new Sql(dataSource)
        String strSql = CommonConstants.EMPTY_STRING;
        // String condition = CommonConstants.EMPTY_STRING;

        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()

        List objList = null;
        strSql = """
        SELECT c.purchase_order_number, c.delivery_date, c.vat_challan_number, c.purchase_order_date, ROUND(c.receivable_amount,2) AS receivable_amount, id.quantity, id.unit_price,
        f.name as "productName",  cm.name AS "CustomerName", cm.present_address, c.bill_number, au.email_address AS userEmail, au.full_name,
        cm.mobile, enc.name AS "enterpriseName",  d.name AS "designationName", enc.address AS en_address, enc.email AS en_email, enc.phone AS en_phone_number, enc.fax AS en_fax, enc.extension
        FROM  create_bill AS c, invoice_details AS id, invoice AS i,  finish_product AS f, customer_master AS cm, application_user AS au,
              enterprise_configuration enc, designation AS d
        WHERE c.bill_number = '${params.billNumber}'
        AND i.is_active = true
        AND i.code = c.invoice_number
        AND id.`invoice_id`=i.`id`
        AND cm.id=c.customer_master_id
        AND f.id= id.finish_product_id
        AND  enc.id=cm.enterprise_configuration_id
        AND au.id= ${applicationUser.id}
        GROUP BY c.id
        """
        objList = sql.rows(strSql)
        //}

// GROUP BY c.`purchase_order_number`
        return objList;
    }
// AND d.id=cm.designation_id
/*
    @Transactional(readOnly = true)
    public List searchCustomerByGeoLocation(Object params) {
        try {
            sql = new Sql(dataSource)
            List objList = null;

            String filter = ""
            *//*if (params.searchKey) {
                filter = " AND (`customer_master`.`name` LIKE '%${params.searchKey}%' OR `customer_master`.`code` LIKE '%${params.searchKey}%' OR `customer_master`.`legacy_id` LIKE '%${params.searchKey}%')"
            }*//*
            if(params.customerCategory){
                filter += " AND `customer_master`.`category_id` = ${params.customerCategory}"
            }
            String query = """
             SELECT
                 `customer_master`.`id`
                , `customer_master`.`code`
                , `customer_master`.`legacy_id`
                , `customer_master`.`name`
                , `customer_status` AS `status`
                , `customer_master`.`present_address` AS `address`
                , `customer_category`.`name` AS `category`
            FROM `customer_territory_sub_area`
                INNER JOIN `customer_master`
                    ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id` AND `customer_master`.`master_type_id` = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID})
                INNER JOIN `customer_category` ON (`customer_master`.`category_id` = `customer_category`.`id`)
            WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                AND `customer_master`.`master_type_id` = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID}
                AND `customer_territory_sub_area`.`territory_sub_area_id` =${params.territorySubAreaId}
                ${filter}
            ORDER BY `customer_master`.`name`
        """
           // objList = new Sql(dataSource).rows(query, params)

            objList = sql.rows(query)
            return objList
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }*/
}
