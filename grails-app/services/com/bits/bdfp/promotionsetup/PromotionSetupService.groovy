package com.bits.bdfp.promotionsetup

import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.bonus.OnePercentBonus
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointStock
import com.bits.bdfp.inventory.sales.DistributionPointStockTransaction
import com.bits.bdfp.inventory.warehouse.FinishGoodStock
import com.bits.bdfp.inventory.warehouse.FinishGoodStockTransaction
import com.bits.bdfp.promotion.AdjustBonusPromotionWithInvoice
import com.bits.bdfp.promotion.PromotionPackage
import com.bits.bdfp.promotion.PromotionPackageCustomers
import com.bits.bdfp.promotion.PromotionPackageProducts
import com.bits.bdfp.promotion.PromotionPackagePromotionalProducts
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Action
import com.docu.security.ApplicationUser
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource

class PromotionSetupService {
    static transactional = true
    DataSource dataSource
    Sql sql

    @Transactional(readOnly = true)
    public Map getGeoAndCustomersListByTerritory(Object params) {
        List geoList = []
        if(params.territoryIds){
            String queryGeo = """
                SELECT tsa.territory_configuration_id, tsa.id, tsa.geo_location AS "name"
                FROM territory_configuration tc
                INNER JOIN territory_sub_area tsa
                        ON tsa.territory_configuration_id = tc.id
                WHERE tc.id IN (${params.territoryIds})
                GROUP BY tsa.id
                ORDER BY tsa.id ASC
            """
            sql = new Sql(dataSource)
            geoList = sql.rows(queryGeo)
        }

        List customerList = getCustomersListByCriteria(params)

        Map map = [:]
        map.put("geoList", geoList)
        map.put("customerList", customerList)

        return map
    }

    @Transactional(readOnly = true)
    public Map getCustomersListByCc(Object params) {
        List customerList = getCustomersListByCriteria(params)
        Map map = [:]
        map.put("customerList", customerList)
        return map
    }

    @Transactional(readOnly = true)
    public Map getCustomersListByGeo(Object params) {
        List customerList = getCustomersListByCriteria(params)
        Map map = [:]
        map.put("customerList",customerList)
        return map
    }

    @Transactional(readOnly = true)
    public List getCustomersListByCriteria(Object params) {
        List customerList = []
        sql = new Sql(dataSource)

        String condition = ''
        if(params.territoryIds){
            condition += """ AND tc.id IN (${params.territoryIds}) """
        }
        if(params.geoIds){
            condition += """ AND tsa.id IN (${params.geoIds}) """
        }
        if(params.ccIds){
            condition += """ AND cc.id IN (${params.ccIds}) """
        }

        if(params.territoryIds || params.geoIds || params.ccIds){
            String queryCustomer = """
                SELECT cm.id, cm.name AS "name", cm.code
                FROM territory_configuration tc
                INNER JOIN territory_sub_area tsa
                        ON tsa.territory_configuration_id = tc.id
                INNER JOIN customer_territory_sub_area ctsa
                        ON ctsa.territory_sub_area_id = tsa.id
                INNER JOIN customer_master cm
                        ON cm.id = ctsa.customer_master_id
                INNER JOIN customer_category cc
                        ON cc.id = cm.category_id
                WHERE 1=1

                ${condition}

                GROUP BY cm.id
                ORDER BY cm.id ASC
            """
            customerList = sql.rows(queryCustomer)
        }
        return customerList
    }


    @Transactional(readOnly = true)
    public List getFactorySalableProductList(Object params) {
        List productList = null
        Long stockId = null

        if(params.containsKey('stockId') && params.stockId){
            stockId = Long.parseLong(params.stockId)
        }else{
            stockId = ApplicationConstants.SALABLE_TYPE_INVENTORY_ID
        }


       /* String queryOld = """
            SELECT fp.id, fgs.id AS stockId, fp.`name`, (SUM(fgs.in_quantity) - SUM(fgs.out_quantity)) AS quantity
            FROM `finish_good_stock` fgs
            INNER JOIN finish_product fp
                    ON (fp.`id` = fgs.finish_product_id)
            WHERE `sub_warehouse_id` = ${ApplicationConstants.SALABLE_TYPE_INVENTORY_ID}
            GROUP BY `finish_product_id`
            HAVING (SUM(in_quantity) - SUM(out_quantity))>0
            ORDER BY fgs.finish_product_id ASC
        """*/

        String query = """
            SELECT fp.id, fgs.id AS stockId, fp.`name`, (SUM(fgs.in_quantity) - SUM(fgs.out_quantity)) AS quantity, ROUND(AVG(fgst.`unit_price`),2) AS rate
            FROM `finish_good_stock` fgs
            INNER JOIN finish_product fp
                    ON (fp.`id` = fgs.finish_product_id)
            INNER JOIN `finish_good_stock_transaction` fgst
                    ON (fgst.`finish_good_stock_id` = fgs.id)
            WHERE `sub_warehouse_id` = ${stockId}
            GROUP BY `finish_product_id`
            HAVING (SUM(fgs.in_quantity) - SUM(fgs.out_quantity))>0
            ORDER BY fgs.finish_product_id ASC
        """
        sql = new Sql(dataSource)
        productList = sql.rows(query)
        return productList
    }

    @Transactional
    public Object create(Object object) {
        try {
            PromotionPackage promotionPackage = object.promotionPackage
            List<PromotionPackageCustomers> packageCustomersList = object.packageCustomersList
            List<PromotionPackageProducts> packageProductsList = object.packageProductsList
            List<PromotionPackagePromotionalProducts> packagePromotionalProductsList = object.packagePromotionalProductsList

            if(promotionPackage) {
                promotionPackage.save()
            }else{
                return new Integer(0)
            }

            packageCustomersList.each {
                it.save()
            }

            packageProductsList.each {
                it.save()
            }

            packagePromotionalProductsList.each {
                it.save()
            }

            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Map getPromotionAndPackageListForGrid(Action action, Object params){
        List dataList = []
        String condition = ""
        if(params?.promotionId){
            condition = " AND p.`id` = ${params.promotionId} "
        }

        String query = """
            SELECT pp.id, p.id AS promotion_id, pp.id AS package_id, p.`name` AS promotion_name, pp.`package_name`,
            DATE(p.`effective_from`) AS effective_from, DATE(p.`effective_to`) AS effective_to,
            pp.`discount_amount`
            FROM `promotion_package` pp
            INNER JOIN promotion p
                    ON (p.`id` = pp.`promotion_id`)
            WHERE p.is_active = TRUE
            AND pp.`is_active` = TRUE
            AND p.calculation_status = 'post'
            AND DATE(p.`effective_from`) >= STR_TO_DATE('${params.effectiveFrom}','%d-%m-%Y')
            AND DATE(p.`effective_to`) <= STR_TO_DATE('${params.effectiveTo}','%d-%m-%Y')
            ${condition}
        """

        sql = new Sql(dataSource)
        dataList = sql.rows(query)
        return [objList: dataList, count: dataList.size()]
    }

    public Boolean adjustBonusPromotion(Map data){
        try{
            List<FinishGoodStock> finishGoodStockBonusPromotionList = data.get("finishGoodStockBonusPromotionList")
            List<FinishGoodStockTransaction> finishGoodStockTransactionBonusPromotionList = data.get("finishGoodStockTransactionBonusPromotionList")

            List<DistributionPointStock> distributionPointStockBonusPromotionList = data.get("distributionPointStockBonusPromotionList")
            List<DistributionPointStockTransaction> distributionPointStockTransactionBonusPromotionList = data.get("distributionPointStockTransactionBonusPromotionList")

            List<AdjustBonusPromotionWithInvoice> adjustBonusPromotionWithInvoiceList = data.get("adjustBonusPromotionWithInvoiceList")
            List<Journal> journalHeadList = data.get("journalHeadList")
            List<JournalDetails> journalDetailsListForBonus = data.get("journalDetailsListForBonus")

            finishGoodStockBonusPromotionList.each {
                it.save(validate: false, insert: true)
            }

            finishGoodStockTransactionBonusPromotionList.each {
                it.save(validate: false, insert: true)
            }

            distributionPointStockBonusPromotionList.each {
                it.save(validate: false, insert: true)
            }

            distributionPointStockTransactionBonusPromotionList.each {
                it.save(validate: false, insert: true)
            }

            adjustBonusPromotionWithInvoiceList.each {
                it.save(validate: false, insert: true)
            }

            journalHeadList.each { journal->
                journal.save(validate: false, insert: true)
            }

            journalDetailsListForBonus.each { journalDetails->
                journalDetails.save(validate: false, insert: true)
            }
        }catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }

        return true
    }
}

