package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointStockTransaction
import com.bits.bdfp.inventory.sales.MarketReturn
import com.bits.bdfp.inventory.sales.MarketReturnDetails
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource

class MiscellaneousTransactionsService {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional(readOnly = true)
    public List getDpListByApplicationUser(Long userId) {
        String query = """
            SELECT dp.id, dp.name
            FROM application_user_distribution_point audp
            INNER JOIN distribution_point dp
                    ON dp.id = audp.distribution_point_id
            WHERE application_user_id = ${userId}
        """

        sql = new Sql(dataSource)
        List dpList = sql.rows(query)

        return dpList
    }


    @Transactional(readOnly = true)
    public List getFactoryDpListByApplicationUser(Long userId) {
        String query = """
            SELECT dp.id, dp.name
            FROM application_user_distribution_point audp
            INNER JOIN distribution_point dp
                    ON dp.id = audp.distribution_point_id
            WHERE audp.application_user_id = ${userId}
                AND dp.is_factory = TRUE
        """

        sql = new Sql(dataSource)
        List dpList = sql.rows(query)

        return dpList
    }

    @Transactional(readOnly = true)
    public List getCustomerListBySelectedDp(Long dpId, String key) {
        String query = ""
        String condition = ""
        DistributionPoint distributionPoint = DistributionPoint.findByIdAndIsFactory(dpId,true)
        if (key) {
            condition = """ AND (cm.name LIKE '%${key}%' OR cm.code LIKE '%${key}%') """
        }
        if(distributionPoint){
            /*query = """
                SELECT cm.id, cm.name
                FROM customer_master cm
                INNER JOIN customer_category ct
                    ON ct.id = cm.category_id
                WHERE LOWER(ct.name) NOT IN ('sales man','retail outlet')
                ORDER BY cm.name
            """*/
            query = """
                SELECT cm.id, cm.name, cm.code, mt.name AS status, GROUP_CONCAT(tsa.geo_location) AS geo_location
                FROM customer_master cm
                INNER JOIN master_type mt
                        ON mt.id = cm.master_type_id
                INNER JOIN customer_category ct
                    ON ct.id = cm.category_id
                LEFT OUTER JOIN customer_territory_sub_area ctsa
                             ON cm.id = ctsa.customer_master_id
                LEFT JOIN territory_sub_area tsa
                       ON tsa.id = ctsa.territory_sub_area_id
                WHERE LOWER(ct.name) NOT IN ('sales man','retail outlet')
                ${condition}
                GROUP BY cm.id
                ORDER BY cm.name
            """
        }else{
            /*query = """
                SELECT cm.id, cm.name
                FROM customer_master cm
                INNER JOIN customer_category ct
                        ON ct.id = cm.category_id
                WHERE LOWER(ct.name) IN ('sales man')
                    AND cm.id IN (
                        SELECT DISTINCT ctsa.customer_master_id
                        FROM customer_territory_sub_area ctsa
                        WHERE ctsa.territory_sub_area_id IN (
                            SELECT dptsa.territory_sub_area_id
                            FROM distribution_point_territory_sub_area dptsa
                            WHERE dptsa.distribution_point_id = ${dpId}
                        )
                    )
                ORDER BY cm.name
            """*/
            query = """
                SELECT cm.id, cm.name, cm.code, mt.name AS status, GROUP_CONCAT(tsa.geo_location) AS geo_location
                FROM customer_master cm
                INNER JOIN master_type mt
                    ON mt.id = cm.master_type_id
                INNER JOIN customer_category ct
                        ON ct.id = cm.category_id
                LEFT OUTER JOIN customer_territory_sub_area ctsa
                        ON cm.id = ctsa.customer_master_id
                LEFT JOIN territory_sub_area tsa
                       ON tsa.id = ctsa.territory_sub_area_id
                WHERE LOWER(ct.name) IN ('sales man')
                    AND cm.id IN (
                        SELECT DISTINCT ctsa.customer_master_id
                        FROM customer_territory_sub_area ctsa
                        WHERE ctsa.territory_sub_area_id IN (
                            SELECT dptsa.territory_sub_area_id
                            FROM distribution_point_territory_sub_area dptsa
                            WHERE dptsa.distribution_point_id = ${dpId}
                        )
                    )
                ${condition}
                GROUP BY cm.id
                ORDER BY cm.name
            """
        }

        sql = new Sql(dataSource)
        List customerList = sql.rows(query)

        return customerList
    }

    @Transactional(readOnly = true)
    public List getAllCustomerListByKey(String key) {
        String query = ""
        String condition = ""

        if (key) {
            condition = """ AND (cm.name LIKE '%${key}%' OR cm.code LIKE '%${key}%') """
        }

        query = """
            SELECT cm.id, cm.name, cm.code, cm.present_address, mt.name AS status, GROUP_CONCAT(tsa.geo_location) AS geo_location, cm.legacy_id
            FROM customer_master cm
            INNER JOIN master_type mt
                    ON mt.id = cm.master_type_id
            INNER JOIN customer_category ct
                ON ct.id = cm.category_id
            LEFT OUTER JOIN customer_territory_sub_area ctsa
                         ON cm.id = ctsa.customer_master_id
            LEFT JOIN territory_sub_area tsa
                   ON tsa.id = ctsa.territory_sub_area_id
            WHERE LOWER(ct.name) NOT IN ('branch','sales man','retail outlet')
            ${condition}
            GROUP BY cm.id
            ORDER BY cm.name
        """

        sql = new Sql(dataSource)
        List customerList = sql.rows(query)

        return customerList
    }

    @Transactional(readOnly = true)
    public CustomerMaster getCustomerCodeById(Long customerId){
        CustomerMaster customerMaster = CustomerMaster.read(customerId)
        return customerMaster
    }

    @Transactional(readOnly = true)
    public List<MarketReturn> getMrListByCustomerId(Long customerId){
        List<MarketReturn> marketReturnListSorted = []
        List<MarketReturn> marketReturnList = []
        marketReturnList = MarketReturn.findAllBySecondaryCustomerAndMrStatus(CustomerMaster.get(customerId),'PROCESSED')

        if(marketReturnList.size() == 0) {
            marketReturnList = MarketReturn.findAllByPrimaryCustomerAndMrStatus(CustomerMaster.get(customerId), 'PROCESSED')
        }
        marketReturnList.each {
            MarketReturnDetails marketReturnDetails = MarketReturnDetails.findByMarketReturnAndMrSattelementStatus(it,null)
            if(marketReturnDetails){
                marketReturnListSorted.add(it)
            }
        }
        return marketReturnListSorted
    }

    @Transactional(readOnly = true)
    public List getMarketReturnSummaryByMrId(Long mrId){
        String query = """
            SELECT fp.id, mr.id AS mrId, GROUP_CONCAT(mrd.id) AS mrdId, mr.mr_no AS mrNo, SUM(mrd.quantity) AS claimedQty, fp.id AS productId, fp.name AS productName,

                IFNULL((SELECT SUM(pmrd.qc_quantity) AS acceptedQty
                FROM process_market_return pmr
                INNER JOIN process_market_return_details pmrd
                    ON pmr.id = pmrd.process_market_return_id
                WHERE pmr.mr_no = mr.mr_no
                    AND pmrd.finish_product_id = fp.id
                ),0) AS acceptedQty

            FROM market_return mr
            INNER JOIN market_return_details mrd
                ON mr.id = mrd.market_return_id
            INNER JOIN finish_product fp
                ON fp.id = mrd.finish_product_id
            WHERE mr.id = ${mrId}
                AND (mrd.mr_sattelement_status != "SETTLED" OR mrd.mr_sattelement_status IS NULL)
            GROUP BY fp.id ;
        """

        sql = new Sql(dataSource)
        List list = sql.rows(query)

        return list
    }

    @Transactional(readOnly = true)
    public List getInventoryListByDp(Long dpId){
        String query = """
            SELECT w.id, w.name
            FROM distribution_point_warehouse dpw
            INNER JOIN warehouse w
                    ON w.id = dpw.warehouse_id
            WHERE dpw.distribution_point_id = ${dpId}
            ORDER BY w.name ;
        """

        sql = new Sql(dataSource)
        List list = sql.rows(query)

        return list
    }

    @Transactional(readOnly = true)
    public List getSubInventoryListByInventory(Long invId){
        String query = """
            SELECT sw.id, sw.name, swt.name as type
            FROM warehouse w
            INNER JOIN sub_warehouse sw
                    ON w.id = sw.warehouse_id
            INNER JOIN sub_warehouse_type swt
                    ON swt.id = sw.sub_warehouse_type_id
            WHERE sw.warehouse_id = ${invId}
            ORDER BY sw.name ;
        """

        sql = new Sql(dataSource)
        List list = sql.rows(query)

        return list
    }

    @Transactional(readOnly = true)
    public List getProductListBySubInventory(Long dpId, Long subInvId, String key){
        String condition = ''
        String query = ''

        if (key) {
            condition = """ AND (fp.name LIKE '%${key}%' OR fp.code LIKE '%${key}%') """
        }

        DistributionPoint distributionPoint = DistributionPoint.get(dpId)

        if(distributionPoint.isFactory == true){
            query = """
                SELECT fp.id, fp.name, fp.code, SUM(fgst.`in_quantity`) - SUM(fgst.`out_quantity`) AS quantity
                FROM `finish_good_stock` fgs
                INNER JOIN `finish_good_stock_transaction` fgst
                        ON fgs.id = fgst.`finish_good_stock_id`
                INNER JOIN finish_product fp
                        ON fp.id = fgs.finish_product_id
                WHERE fgs.sub_warehouse_id = ${subInvId}
                ${condition}
                GROUP BY fp.id
                HAVING SUM(fgst.`in_quantity`) - SUM(fgst.`out_quantity`) > 0
                ORDER BY fp.name ;
            """
        }else{
            query = """
                SELECT fp.id, fp.name, fp.code, SUM(dpst.`in_quantity`) - SUM(dpst.`out_quantity`) AS quantity
                FROM `distribution_point_stock` dps
                INNER JOIN `distribution_point_stock_transaction` dpst
                        ON dps.id = dpst.`distribution_point_stock_id`
                INNER JOIN finish_product fp
                        ON fp.id = dps.finish_product_id
                WHERE dps.sub_warehouse_id = ${subInvId}
                ${condition}
                GROUP BY fp.id
                HAVING SUM(dpst.`in_quantity`) - SUM(dpst.`out_quantity`) > 0
                ORDER BY fp.name ;
            """
        }

        sql = new Sql(dataSource)
        List list = sql.rows(query)

        return list
    }

    @Transactional
    public Integer createReplacement(Map map){
        try {
            List<ReplacementMiscellaneousTransactions> replacementMiscellaneousTransactionsList = (List<ReplacementMiscellaneousTransactions>) map.replacementMiscellaneousTransactionsList
            List<FinishGoodStockTransaction> finishGoodStockTransactionList = (List<FinishGoodStockTransaction>) map.finishGoodStockTransactionList
            List<DistributionPointStockTransaction> distributionPointStockTransactionList = (List<DistributionPointStockTransaction>) map.distributionPointStockTransactionList
            List<MarketReturnDetails> marketReturnDetailsList = (List<MarketReturnDetails>) map.marketReturnDetailsList
            Integer count = 0
            replacementMiscellaneousTransactionsList.each {
                if(it.save()){
                    count++
                }
            }

            if(count>0){
                distributionPointStockTransactionList.each {
                    it.save()
                }

                finishGoodStockTransactionList.each {
                    it.save()
                }

                marketReturnDetailsList.each {
                    it.save()
                }
            }

            return count

        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }


    @Transactional
    public Integer createEntertainment(Map map){
        try {
            List<EntertainmentMiscellaneousTransactions> entertainmentMiscellaneousTransactionsList  = (List<EntertainmentMiscellaneousTransactions>) map.entertainmentMiscellaneousTransactionsList
            List<FinishGoodStockTransaction> finishGoodStockTransactionList = (List<FinishGoodStockTransaction>) map.finishGoodStockTransactionList
            List<DistributionPointStockTransaction> distributionPointStockTransactionList = (List<DistributionPointStockTransaction>) map.distributionPointStockTransactionList
            Journal journalHead = (Journal) map.journalHead
            List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.journalDetailsList

            Integer count = 0
            entertainmentMiscellaneousTransactionsList.each {
                if(it.save()){
                    count++
                }
            }

            if(count>0){
                distributionPointStockTransactionList.each {
                    it.save()
                }

                finishGoodStockTransactionList.each {
                    it.save()
                }

                if(journalHead){
                    journalHead.save()
                }

                if(journalDetailsList && journalDetailsList.size()>0){
                    journalDetailsList.each {
                        it.save()
                    }
                }
            }

            return count

        }catch(Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer createSample(Map map){
        try {
            List<SampleMiscellaneousTransactions> sampleMiscellaneousTransactionsList = ( List<SampleMiscellaneousTransactions>) map.sampleMiscellaneousTransactionsList
            List<FinishGoodStockTransaction> finishGoodStockTransactionList = (List<FinishGoodStockTransaction>) map.finishGoodStockTransactionList
            List<DistributionPointStockTransaction> distributionPointStockTransactionList = (List<DistributionPointStockTransaction>) map.distributionPointStockTransactionList
            Journal journalHead = (Journal) map.journalHead
            List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.journalDetailsList

            Integer count = 0
            sampleMiscellaneousTransactionsList.each {
                if(it.save()){
                    count++
                }
            }

            if(count>0){
                distributionPointStockTransactionList.each {
                    it.save()
                }

                finishGoodStockTransactionList.each {
                    it.save()
                }

                if(journalHead){
                    journalHead.save()
                }

                if(journalDetailsList && journalDetailsList.size()>0){
                    journalDetailsList.each {
                        it.save()
                    }
                }
            }

            return count

        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer createDamage(Map map){
        try {
            List<DamageMiscellaneousTransactions> damageMiscellaneousTransactionsList = ( List<DamageMiscellaneousTransactions>) map.damageMiscellaneousTransactionsList
            List<FinishGoodStockTransaction> finishGoodStockTransactionList = (List<FinishGoodStockTransaction>) map.finishGoodStockTransactionList
            List<DistributionPointStockTransaction> distributionPointStockTransactionList = (List<DistributionPointStockTransaction>) map.distributionPointStockTransactionList
            Journal journalHead = (Journal) map.journalHead
            List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.journalDetailsList

            Integer count = 0
            damageMiscellaneousTransactionsList.each {
                if(it.save()){
                    count++
                }
            }

            if(count>0){
                distributionPointStockTransactionList.each {
                    it.save()
                }

                finishGoodStockTransactionList.each {
                    it.save()
                }

                if(journalHead){
                    journalHead.save()
                }

                if(journalDetailsList && journalDetailsList.size()>0){
                    journalDetailsList.each {
                        it.save()
                    }
                }
            }

            return count

        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer createRtp(Map map){
        try {
            List<RtpMiscellaneousTransactions> rtpMiscellaneousTransactionsList = ( List<RtpMiscellaneousTransactions>) map.rtpMiscellaneousTransactionsList
            List<FinishGoodStockTransaction> finishGoodStockTransactionList = (List<FinishGoodStockTransaction>) map.finishGoodStockTransactionList
            List<DistributionPointStockTransaction> distributionPointStockTransactionList = (List<DistributionPointStockTransaction>) map.distributionPointStockTransactionList

            Integer count = 0
            rtpMiscellaneousTransactionsList.each {
                if(it.save()){
                    count++
                }
            }

            if(count>0){
                finishGoodStockTransactionList.each {
                    it.save()
                }

                distributionPointStockTransactionList.each {
                    it.save()
                }
            }

            return count

        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}