package com.bits.bdfp.inventory.setup

import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.sales.AdjustSalesCommission
import com.bits.bdfp.inventory.sales.AdjustSalesCommissionDetails
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Action
import com.docu.commons.CommonConstants
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource

class SalesCommissionService {
    static transactional = false
    DataSource dataSource
    Sql sql
    SpringSecurityService springSecurityService

    @Transactional
    public SalesCommission create(Object object) {
        try {
            Map map = (Map) object
            SalesCommission salesCommission = (SalesCommission) map.get("salesCommission")

            List<CustomerSalesCommission> customerSalesCommissionList = map.get("customerSalesCommissionList")
            List<ProductSalesCommission> productSalesCommissionList = map.get("productSalesCommissionList")

            if (salesCommission.save(false)) {
                customerSalesCommissionList.each {
                    it.save(false)
                }
                productSalesCommissionList.each {
                    it.save(false)
                }
                return salesCommission
            } else {
                return null
            }
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public SalesCommission Update(Object object) {
        SalesCommission salesCommission = (SalesCommission) object
        if (salesCommission.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        SalesCommission salesCommission = (SalesCommission) object
        salesCommission.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<SalesCommission> objList = SalesCommission.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = SalesCommission.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<SalesCommission> list() {
        return SalesCommission.list()
    }

    @Transactional(readOnly = true)
    public SalesCommission read(Long id) {
        return SalesCommission.read(id)
    }

    @Transactional(readOnly = true)
    public SalesCommission search(String fieldName, String fieldValue) {
        String query = "from SalesCommission as sc where sc." + fieldName + " = '" + fieldValue + "'"
        return SalesCommission.find(query)
    }

    @Transactional(readOnly = true)
    public List fetchDataForDistributionPointDropDownAction(String id) {
        String query = """SELECT id,name FROM distribution_point WHERE id IN
                    (SELECT distribution_point_id FROM distribution_point_territory_sub_area
                    WHERE territory_sub_area_id IN
                    (SELECT id FROM territory_sub_area
                    WHERE `territory_configuration_id` =
                    (SELECT id FROM territory_configuration WHERE id=${id})))"""
        Sql sql = new Sql(dataSource)
        return sql.rows(query.toString())
    }

    @Transactional(readOnly = true)
    public List fetchUserTerritoryList(Long id) {

        sql = new Sql(dataSource)
        String query = CommonConstants.EMPTY_STRING;
        def user=(ApplicationUser) springSecurityService?.getCurrentUser()

        List objList=null;

        query = """
                    SELECT DISTINCT tc.id, tc.name
                    FROM territory_sub_area AS tsa
                    INNER JOIN territory_configuration AS tc
                                            ON (tc.`id` = tsa.`territory_configuration_id`)
                    INNER JOIN customer_territory_sub_area AS ctsa
                            ON (ctsa.`territory_sub_area_id` = tsa.id)
                     INNER JOIN customer_master  AS cm
                            ON (cm.`id` = ctsa.`customer_master_id`)
                    INNER JOIN application_user AS apu
                            ON (apu.`customer_master_id` = cm.id)
                    WHERE apu.id = ${user.id}
            """
        objList = sql.rows(query)

        return objList;
    }

    @Transactional(readOnly = true)
    public List fetchDistributionPointList() {
        def user=(ApplicationUser) springSecurityService?.getCurrentUser()
        String query = """
                       SELECT dp.id, dp.name AS name
                        FROM distribution_point AS dp, application_user_distribution_point AS audp
                        WHERE dp.id=audp.distribution_point_id
                        AND audp.application_user_id=${user.id}
                        ORDER BY name
                      """
        Sql sql = new Sql(dataSource)
        return sql.rows(query)
    }

    @Transactional(readOnly = true)
    public List listDefaultCustomerByDP(Long id) {
        String query = '';

       /* query = """
            SELECT dp.id, dp.name AS dpName
            FROM application_user_distribution_point AS audp , distribution_point AS dp
            WHERE audp.application_user_id=${id}
            AND dp.id=audp.distribution_point_id
            """*/
        query = """
            SELECT id, name, code
            FROM customer_master
            WHERE id = (SELECT default_customer_id
                FROM distribution_point_warehouse
                WHERE distribution_point_id=${id})
        """
        Sql sql = new Sql(dataSource)
        return sql.rows(query.toString())
    }

    @Transactional(readOnly = true)
    public List listCustomerByDP(Long id) {

        List customerList = []
        String query = """
            SELECT ctsa.customer_master_id AS customer_id, cm.name AS customer_name, cm.code, cm.legacy_id
            FROM  customer_master AS cm, `customer_territory_sub_area` AS ctsa, `distribution_point_territory_sub_area` AS dpctsa
            WHERE cm.id = ctsa.`customer_master_id`
                AND ctsa.`territory_sub_area_id` = dpctsa.`territory_sub_area_id`
                AND dpctsa.`distribution_point_id` = ${id}
                AND cm.`category_id` = ${ApplicationConstants.CUSTOMER_CATEGORY_SALES_MAN_ID}
            ORDER BY cm.name
        """
        Sql sql = new Sql(dataSource)
        customerList = sql.rows(query.toString())
        return customerList
    }


    @Transactional(readOnly = true)
    public List listProductForUpdateByCustomer(Object params) {

        List productList = []
        String query = """
                 SELECT DISTINCT `product_sales_commission`.`id` as id,`product_sales_commission`.`finish_product_id` AS productid,`finish_product`.`code` AS pcode,`finish_product`.`name` AS pname
                ,`sales_commission`.`branch_commission` AS bcommission,`sales_commission`.`sales_man_commission` AS smcommission
                ,DATE_FORMAT(`customer_sales_commission`.`effective_date_from`,'${ApplicationConstants.DATE_FORMAT_DB}') as effective_date_from
                ,DATE_FORMAT(`customer_sales_commission`.`effective_date_to`,'${ApplicationConstants.DATE_FORMAT_DB}') effective_date_to
                  FROM `product_sales_commission`
            INNER JOIN `customer_sales_commission` ON `customer_sales_commission`.`id` = `product_sales_commission`.`customer_sales_commission_id`
            INNER JOIN `sales_commission` ON `sales_commission`.`id`= `customer_sales_commission`.`sales_commission_id`
            INNER JOIN `finish_product` ON `finish_product`.id = `product_sales_commission`.`finish_product_id`
                 WHERE `customer_sales_commission`.`id`= ${params.cscId}
        """

        Sql sql = new Sql(dataSource)
        productList = sql.rows(query.toString())
        return productList
    }


    @Transactional(readOnly = true)
    public List listSalesCommissionByDP(Long dpId) {

        List salesCommissionList = []
        String query = """
               SELECT id,sales_commission_name,branch_commission,sales_man_commission  FROM sales_commission
                WHERE  distribution_point_id= ${dpId}
        """

        Sql sql = new Sql(dataSource)
        salesCommissionList = sql.rows(query.toString())
        return salesCommissionList
    }


    @Transactional(readOnly = true)
    public List listCustomerForUpdateByDP(Long salesCommissionId) {

        List customerList = []
        String query = """
                SELECT `customer_sales_commission`.`id`,`sales_commission`.`id` AS sid,`customer_sales_commission`.`customer_master_id` AS cid,`customer_master`.`name` AS cname,customer_master.code
                ,DATE_FORMAT(`customer_sales_commission`.`effective_date_from`,'${ApplicationConstants.DATE_FORMAT_DB}') as  effectiveFrom
                ,DATE_FORMAT(`customer_sales_commission`.`effective_date_to`,'${ApplicationConstants.DATE_FORMAT_DB}')  as effectiveTo

                 FROM `customer_sales_commission`

             INNER JOIN `sales_commission` ON `sales_commission`.`id` = `customer_sales_commission`.`sales_commission_id`
             INNER JOIN `customer_master` ON `customer_master`.id = `customer_sales_commission`.`customer_master_id`
             INNER JOIN `customer_territory_sub_area`  ON `customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`
             INNER JOIN `distribution_point_territory_sub_area` ON `distribution_point_territory_sub_area`.`territory_sub_area_id` = `customer_territory_sub_area`.`territory_sub_area_id`

           WHERE  `sales_commission`.`id`  = ${salesCommissionId}
        """
//        SELECT ctsa.customer_master_id AS customer_id, cm.name AS customer_name, cm.code, cm.legacy_id
//        FROM  customer_master AS cm, `customer_territory_sub_area` AS ctsa, `distribution_point_territory_sub_area` AS dpctsa
//        WHERE cm.id = ctsa.`customer_master_id`
//        AND ctsa.`territory_sub_area_id` = dpctsa.`territory_sub_area_id`
//        AND dpctsa.`distribution_point_id` = ${id}
//        AND cm.`category_id` = ${ApplicationConstants.CUSTOMER_CATEGORY_SALES_MAN_ID}
//        ORDER BY cm.name
        Sql sql = new Sql(dataSource)
        customerList = sql.rows(query.toString())
        return customerList
    }
    //One customer named Zakir not found thats why query changed
    /* @Transactional(readOnly = true)
     public List listCustomerByDP(Long id) {
         String query = """ SELECT ctsa.customer_master_id as customer_id, cm.name AS customer_name, cm.code, cm.legacy_id

                            FROM customer_territory_sub_area ctsa
                            INNER JOIN customer_master cm
                                    ON cm.id = ctsa.customer_master_id
                            INNER JOIN customer_category cc
                                    ON cc.id = cm.category_id

                            WHERE ctsa.territory_sub_area_id IN(
                                  SELECT territory_sub_area_id
                                  FROM customer_territory_sub_area
                                  WHERE customer_master_id =(SELECT default_customer_id
                                 FROM distribution_point_warehouse
                                 WHERE distribution_point_id=${id})

                                  AND ctsa.customer_master_id !=(SELECT default_customer_id
                                 FROM distribution_point_warehouse
                                 WHERE distribution_point_id=${id})
                                          AND cc.name = 'Sales Man'
                                      )

                             GROUP BY ctsa.customer_master_id
                             ORDER BY cm.name ASC"""
         Sql sql = new Sql(dataSource)
         return sql.rows(query.toString())
     }*/

    @Transactional(readOnly = true)
    public List listCustomersCommissionByDP(Object params) {
        String query = """
            SELECT cm.id AS customer_id, cm.code, cm.name AS customer_name, cm.legacy_id,
                (ROUND(SUM((((ppp.total_amount - (SELECT total_amount
                        FROM product_price_product pppr
                        WHERE pppr.finish_product_id = id.finish_product_id
                        AND pppr.`product_price_id` = pp.id
                        AND pppr.`pricing_category_id` = 1)) * id.quantity) / 100) * sc.sales_man_commission),2)
                ) AS commissionAmount

            FROM customer_territory_sub_area ctsa
            INNER JOIN customer_master cm
                    ON cm.id = ctsa.customer_master_id
            INNER JOIN customer_category cc
                    ON cc.id = cm.category_id
            INNER JOIN pricing_category pc
                    ON pc.id = cm.pricing_category_id
            INNER JOIN invoice i
                    ON i.default_customer_id = cm.id
            INNER JOIN invoice_details id
                    ON id.invoice_id = i.id
            INNER JOIN customer_sales_commission csc
                    ON csc.customer_master_id = cm.id
            INNER JOIN sales_commission sc
                    ON sc.id = csc.sales_commission_id
            INNER JOIN product_price_product ppp
                    ON ppp.finish_product_id = id.finish_product_id
            INNER JOIN product_price pp
                    ON pp.id = ppp.product_price_id
            INNER JOIN territory_sub_area_product_price tsapp
                    ON tsapp.product_price_id = pp.id
                    AND tsapp.territory_sub_area_id = ctsa.territory_sub_area_id

            WHERE ctsa.territory_sub_area_id IN(
                 SELECT territory_sub_area_id
                 FROM customer_territory_sub_area
                 WHERE customer_master_id =(SELECT default_customer_id
                    FROM distribution_point_warehouse
                    WHERE distribution_point_id=${params.dpId}
                 )
                 AND ctsa.customer_master_id !=(SELECT default_customer_id
                    FROM distribution_point_warehouse
                    WHERE distribution_point_id=${params.dpId}
                 )
            )
            AND cc.name = 'Sales Man'
            AND pc.short_name = 'TP'
            AND id.finish_product_id IN (
                SELECT psc.finish_product_id
                FROM product_sales_commission psc
                WHERE psc.`customer_sales_commission_id` IN (
                    SELECT csc.id
                    FROM customer_sales_commission csc
                    WHERE csc.customer_master_id = cm.id
                )
            )
            AND ppp.pricing_category_id = 2
            AND i.date_created
                BETWEEN pp.date_effective_from
                AND pp.date_effective_to
            AND STR_TO_DATE(DATE_FORMAT(i.date_created,'%d-%m-%Y'),'%d-%m-%Y') <= STR_TO_DATE('${params.asOfDate}','%d-%m-%Y')

            GROUP BY cm.id
            ORDER BY cm.name ASC
        """
        Sql sql = new Sql(dataSource)
        return sql.rows(query.toString())
    }

    @Transactional(readOnly = true)
    public List listBranchCommissionByDP(Object params) {
        String query = """
            SELECT ROUND(SUM((((ppp.total_amount - (SELECT total_amount
                        FROM product_price_product pppr
                        WHERE pppr.finish_product_id = id.finish_product_id
                        AND pppr.`product_price_id` = pp.id
                        AND pppr.`pricing_category_id` = 1)) * id.quantity) / 100) * sc.branch_commission),2) AS commissionAmount

            FROM sales_commission sc
            INNER JOIN distribution_point_warehouse dpw
                    ON dpw.distribution_point_id = sc.distribution_point_id
            INNER JOIN invoice i
                    ON i.default_customer_id = dpw.default_customer_id
            INNER JOIN invoice_details id
                    ON id.invoice_id = i.id
            INNER JOIN product_price_product ppp
                    ON ppp.finish_product_id = id.finish_product_id
            INNER JOIN product_price pp
                    ON pp.id = ppp.product_price_id
            INNER JOIN distribution_point_territory_sub_area dptsa
                    ON dptsa.distribution_point_id = dpw.distribution_point_id
            INNER JOIN territory_sub_area_product_price tsapp
                    ON tsapp.product_price_id = pp.id
                    AND tsapp.territory_sub_area_id = dptsa.territory_sub_area_id

            WHERE sc.distribution_point_id = ${params.dpId}
            AND ppp.pricing_category_id = 2
            AND i.date_created
                BETWEEN pp.date_effective_from
                AND pp.date_effective_to
            AND STR_TO_DATE(DATE_FORMAT(i.date_created,'%d-%m-%Y'),'%d-%m-%Y') <= STR_TO_DATE('${params.asOfDate}','%d-%m-%Y')

            GROUP BY dpw.default_customer_id
        """
        Sql sql = new Sql(dataSource)
        return sql.rows(query.toString())
    }

    @Transactional(readOnly = true)
    public List listCommission(Object params) {
        try{
            /*Old Script*/
            /*String queryOld = """
                SELECT ROUND(SUM(tbl.quantity * tbl.tpPrice - tbl.quantity * tbl.dpPrice), 2) AS commission
                FROM
                    (SELECT DISTINCT `invoice_details`.`finish_product_id` AS pid,
                        (SELECT SUM(quantity)
                        FROM `invoice_details`
                        INNER JOIN `invoice` ON `invoice`.`id` = `invoice_details`.`invoice_id`
                        WHERE `invoice`.`default_customer_id` = ${params.id}
                            AND `invoice`.`is_active` = TRUE
                            AND `finish_product_id` = pid) AS quantity,
                        (SELECT DISTINCT `product_price_product`.`total_amount`
                        FROM `product_price_product`
                        INNER JOIN `product_price` ON (`product_price`.`id` = `product_price_product`.`product_price_id`)
                        INNER JOIN `invoice_details` ON (`invoice_details`.`finish_product_id` = `product_price_product`.`finish_product_id`)
                        INNER JOIN `invoice` ON (`invoice`.`id` = `invoice_details`.`invoice_id`)
                        INNER JOIN `customer_territory_sub_area` ON `customer_territory_sub_area`.`customer_master_id` = `invoice`.`default_customer_id`
                        INNER JOIN `territory_sub_area_product_price` ON `territory_sub_area_product_price`.`product_price_id` = `product_price`.`id`
                            AND `territory_sub_area_product_price`.`territory_sub_area_id` = `customer_territory_sub_area`.`territory_sub_area_id`
                        INNER JOIN `product_sales_commission` ON `product_sales_commission`.`finish_product_id` = `invoice_details`.`finish_product_id`
                        WHERE `product_price_product`.`pricing_category_id` = ${ApplicationConstants.PRICING_CATEGORY_DP_ID}
                            AND `invoice`.`is_active` = TRUE
                            AND `invoice`.`default_customer_id` = ${params.id}
                            AND `product_price_product`.`finish_product_id` = pid
                            AND `invoice`.`date_created`
                                BETWEEN `product_price`.`date_effective_from`
                                AND `product_price`.`date_effective_to`
                        ORDER BY `product_price_product`.`total_amount` DESC
                        LIMIT 1) AS dpPrice,
                        (SELECT DISTINCT `product_price_product`.`total_amount`
                        FROM `product_price_product`
                            INNER JOIN `product_price` ON `product_price`.`id` = `product_price_product`.`product_price_id`
                            INNER JOIN `invoice_details` ON `invoice_details`.`finish_product_id` = `product_price_product`.`finish_product_id`
                            INNER JOIN `invoice` ON `invoice`.`id` = `invoice_details`.`invoice_id`
                            INNER JOIN `customer_territory_sub_area` ON `customer_territory_sub_area`.`customer_master_id` = `invoice`.`default_customer_id`
                            INNER JOIN `territory_sub_area_product_price` ON (`territory_sub_area_product_price`.`product_price_id` = `product_price`.`id`
                                AND `territory_sub_area_product_price`.`territory_sub_area_id` = `customer_territory_sub_area`.`territory_sub_area_id`)
                            INNER JOIN `product_sales_commission` ON `product_sales_commission`.`finish_product_id` = `invoice_details`.`finish_product_id`
                        WHERE `product_price_product`.`pricing_category_id` = ${ApplicationConstants.PRICING_CATEGORY_TP_ID}
                            AND `invoice`.`is_active` = TRUE
                            AND `invoice`.`default_customer_id` = ${params.id}
                            AND `product_price_product`.`finish_product_id` = pid
                            AND `invoice`.`date_created`
                                BETWEEN `product_price`.`date_effective_from`
                                AND `product_price`.`date_effective_to`
                        ORDER BY `product_price_product`.`total_amount` DESC
                        LIMIT 1) AS tpPrice
                    FROM `invoice_details`
                        INNER JOIN `invoice` ON (`invoice`.`id` = `invoice_details`.`invoice_id`)
                    WHERE invoice.is_active = TRUE
                        AND `invoice`.`default_customer_id` = ${params.id}
                        AND DATE(`invoice`.`date_created`) <= STR_TO_DATE('${params.date}','%d-%m-%Y')
                        AND `invoice`.`commission_calculated` IS NOT TRUE
                ) AS tbl
            """*/

            /*New Optimized Script*/
            String query = """
                SELECT ROUND(SUM(id.quantity * (id.unit_price - id.dp_unit_price))*sc.sales_man_commission/100, 2) AS commission
                FROM invoice_details id
                INNER JOIN invoice i
                        ON (i.id = id.invoice_id)
                INNER JOIN customer_sales_commission csc
                        ON (csc.customer_master_id = i.default_customer_id)
                INNER JOIN sales_commission sc
                        ON (sc.id = csc.sales_commission_id)
                WHERE i.is_active = TRUE
                AND i.default_customer_id = ${params.id}
                AND DATE(i.date_created) <= STR_TO_DATE('${params.date}','%d-%m-%Y')
                AND i.commission_calculated IS NOT TRUE
            """
            Sql sql = new Sql(dataSource)
            return sql.rows(query)
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List commissionPercent(Object params) {
        try{
            String query = """
                SELECT `sales_commission`.`sales_man_commission`
                FROM `customer_sales_commission`
                    INNER JOIN `sales_commission` ON (`sales_commission`.`id` = `customer_sales_commission`.`sales_commission_id`)
                WHERE `customer_sales_commission`.`customer_master_id` = ${params.id}
            """
            Sql sql = new Sql(dataSource)
        return sql.rows(query)
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List previousCommission(Object params) {
        try{
            String query = """
                SELECT ROUND(`commission`, 2) AS `commission`
                FROM `adjust_sales_commission`
                WHERE `customer_master_id` = ${params.id}
                ORDER BY id DESC LIMIT 1
            """
            Sql sql = new Sql(dataSource)
            return sql.rows(query)
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public AdjustSalesCommission searchAdjust(String fieldName, String fieldValue) {
        String query = "from AdjustSalesCommission as sc where sc." + fieldName + " = '" + fieldValue + "'"
        return AdjustSalesCommission.find(query)
    }

    @Transactional
    public Integer updateInvoice(Object params) {
        try {
            Sql sql = new Sql(dataSource)
            String query = """
                    UPDATE `invoice`
                    SET `commission_calculated` = TRUE
                    WHERE `default_customer_id` = ${params.customerMaster.id}
                        AND DATE(`date_created`) <= STR_TO_DATE('${params.date}','%d-%m-%Y')
                     """
            sql.execute(query)
            return new Integer(1)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer adjustSalesCommission(Object map) {
        try {
            AdjustSalesCommission adjustSalesCommission = map.adjustSalesCommission
            List<AdjustSalesCommissionDetails> adjustSalesCommissionDetailsList = map.adjustSalesCommissionDetailsList
            List<Invoice> invoices = map.invoices
            if (adjustSalesCommission.id) {
                adjustSalesCommission.save()
            } else {
                adjustSalesCommission.save(false)
            }

            adjustSalesCommissionDetailsList.each {
                it.save()
            }

            invoices.each {
                it.save()
            }
            //***********************************COA Start*******************************//

            Journal journal = (Journal) map.get('journal')
            journal.save(validate: false, insert: true)

            List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.get('journalDetailsList')
            journalDetailsList.each { journalDetails ->
                journalDetails.save(validate: false, insert: true)
            }

            //*********************************COA End*******************************//

            return new Integer(1)

        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Map fetchDataGridProductAction() {
        String query = """SELECT DISTINCT id as productInfoId,CONCAT('[',CODE ,']','-',NAME) AS productInfo FROM finish_product"""
        Sql sql = new Sql(dataSource)
        List objList = sql.rows(query.toString())
        return [objList: objList]
    }

    @Transactional(readOnly = true)
    public List fetchDataProductAction() {
        String query = """SELECT DISTINCT id ,CONCAT('[',CODE ,']','-',NAME) AS productInfo FROM finish_product"""
        Sql sql = new Sql(dataSource)
        List objList = sql.rows(query.toString())
    }

    @Transactional
    public ProductSalesCommission createProduct(Object object) {
        ProductSalesCommission productSalesCommission = (ProductSalesCommission) object
        if (productSalesCommission.save(false)) {
            return productSalesCommission
        }
        return null
    }

    @Transactional
    public ProductSalesCommission deleteProduct(Object object) {
        ProductSalesCommission productSalesCommission = (ProductSalesCommission) object
        productSalesCommission.delete()
        return productSalesCommission
    }

    @Transactional
    public SalesCommission deleteSalesCommision(Object object) {
        SalesCommission salesCommission = (SalesCommission) object
        salesCommission.delete()
        return salesCommission
    }

    @Transactional
    public CustomerSalesCommission deleteCustomerSalesCommission(Object object) {
//        CustomerSalesCommission customerSalesCommission = (CustomerSalesCommission) object
//        customerSalesCommission.delete()
//        return customerSalesCommission

        try {
            Map map = (Map) object
            CustomerSalesCommission customerSalesCommission = (CustomerSalesCommission) map.get("customerSalesCommission")
            List<ProductSalesCommission> productSalesCommissionList = map.get("productSalesCommissionList")

            productSalesCommissionList.each {
                it.delete()
            }

            customerSalesCommission.delete()
            return customerSalesCommission

        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }


    @Transactional
    public CustomerSalesCommission update(Object object) {
        CustomerSalesCommission customerSalesCommission = (CustomerSalesCommission) object
        customerSalesCommission.save()
        return customerSalesCommission
    }
}
