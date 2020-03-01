package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointStock
import com.bits.bdfp.inventory.sales.DistributionPointStockTransaction
import com.bits.bdfp.inventory.sales.MarketReturn
import com.bits.bdfp.inventory.sales.MarketReturnDetails
import com.docu.commons.CommonConstants
import com.docu.security.ApplicationUser
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource

class SubInventoryToSubInventoryTransferService {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional(readOnly = true)
    public List getInventoryByUserId(Long id){
        String queryInv = """
            select w.id, w.name
            from warehouse w
            inner join distribution_point_warehouse dpw
                    on w.id = dpw.warehouse_id
            WHERE dpw.distribution_point_id = ${id}
        """
        sql = new Sql(dataSource)
        List inventoryList = sql.rows(queryInv)

        return inventoryList
    }

    @Transactional(readOnly = true)
    public List fetchDistributionPointList(Long id) {

        sql = new Sql(dataSource)
        String query = CommonConstants.EMPTY_STRING;

        List objList=null;

         query = """
            SELECT dp.id, dp.name AS dpName
            FROM application_user_distribution_point AS audp , distribution_point AS dp
            WHERE audp.application_user_id=${id}
            AND dp.id=audp.distribution_point_id
            """

        objList = sql.rows(query)

        return objList;
    }

    @Transactional(readOnly = true)
    public List getSubInventoryListByInventoryId(Long id){

        String querySubInv = """
            SELECT sw.id, sw.name, swt.name AS typeName, CONCAT(sw.name,' [',swt.name,']') AS nameTypeName
            FROM sub_warehouse sw
            INNER JOIN sub_warehouse_type swt
                ON swt.id = sw.sub_warehouse_type_id
            WHERE warehouse_id = ${id}
        """
        sql = new Sql(dataSource)
        List subInventoryList = sql.rows(querySubInv)

        return subInventoryList
    }

    @Transactional(readOnly = true)
    public List getSubInventoryToListByInventoryId(Long id,Long inventoryId, Object params){

        boolean dmgResult=Boolean.parseBoolean(params.dmgRslt)
        boolean mrkResult=Boolean.parseBoolean(params.mrkRslt)

        String dmg="";
        if(dmgResult || mrkResult){
             dmg=
                    """
                        AND swt.name <> 'Salable'
                    """
        }


        String querySubInv = """
            SELECT sw.id, sw.name, CONCAT(sw.name,' [',swt.name,']') AS nameTypeName
            FROM sub_warehouse sw
            INNER JOIN sub_warehouse_type swt
                ON swt.id = sw.sub_warehouse_type_id
            WHERE warehouse_id = ${inventoryId}
            AND sw.id <> ${id}
            ${dmg}
        """

        sql = new Sql(dataSource)
        List subInventoryList = sql.rows(querySubInv)

        return subInventoryList
    }


    @Transactional(readOnly = true)
    public List getAllProductListBySubInventoryId(Long id, Long dpId){
        DistributionPoint distributionPoint = null
        String queryFactory
        String queryNonFactory
        List list = []

        if(dpId){
            distributionPoint = DistributionPoint.get(dpId)
        }

        if(distributionPoint.isFactory){
            queryFactory = """
                SELECT fgs.id AS dpsId, fgs.sub_warehouse_id AS subInventoryId,
                    fp.name AS productName, fp.code AS productCode, fp.id AS productId, fgs.batch_no,
                    ROUND(AVG(fgst.unit_price),2) AS price, SUM(fgst.in_quantity - fgst.out_quantity) AS quantity,
                    ROUND((SUM(fgst.in_quantity - fgst.out_quantity) * AVG(fgst.unit_price)),2) AS totalPrice
                FROM finish_good_stock fgs
                    INNER JOIN finish_good_stock_transaction fgst
                        ON (fgs.id = fgst.finish_good_stock_id)
                    INNER JOIN finish_product fp
                        ON (fp.id = fgs.finish_product_id)
                WHERE fgs.sub_warehouse_id = ${id}
                GROUP BY fgs.id
                HAVING SUM(fgst.in_quantity - fgst.out_quantity) > 0
                ORDER BY fp.id
            """
            sql = new Sql(dataSource)
            list = sql.rows(queryFactory)
        }else{
            queryNonFactory = """
                SELECT dps.id dpsId, dps.sub_warehouse_id AS subInventoryId,
                    fp.name AS productName, fp.code AS productCode,
                    fp.id AS productId, dps.batch_no,
                    ROUND(AVG(dpst.unit_price),2) AS price,
                    SUM(dpst.in_quantity - dpst.out_quantity) AS quantity,
                    ROUND((SUM(dpst.in_quantity - dpst.out_quantity) * AVG(dpst.unit_price)), 2) AS totalPrice
                FROM distribution_point_stock dps
                    INNER JOIN distribution_point_stock_transaction dpst
                        ON (dps.id = dpst.distribution_point_stock_id)
                    INNER JOIN finish_product fp
                        ON (fp.id = dps.finish_product_id)
                WHERE dps.sub_warehouse_id = ${id}
                GROUP BY dps.id
                HAVING SUM(dpst.in_quantity - dpst.out_quantity) > 0
                ORDER BY fp.id
            """
            sql = new Sql(dataSource)
            list = sql.rows(queryNonFactory)
        }

        return list
    }

    @Transactional
    public Integer createSubInventoryToSubInventoryTransfer(Map map){
        try {
            List<SubInventoryToSubInventoryTransfer> subInventoryToSubInventoryTransferList = map.subInventoryToSubInventoryTransferList

            List<DistributionPointStock> distributionPointStockList = map.distributionPointStockList
            List<DistributionPointStock> distributionPointStockInList = map.distributionPointStockInList
            List<DistributionPointStockTransaction> distributionPointStockTransactionList = map.distributionPointStockTransactionList
            List<DistributionPointStockTransaction> distributionPointStockTransactionInList = map.distributionPointStockTransactionInList

            List<FinishGoodStock> finishGoodStockList = map.finishGoodStockList
            List<FinishGoodStock> finishGoodStockInList = map.finishGoodStockInList
            List<FinishGoodStockTransaction> finishGoodStockTransactionList = map.finishGoodStockTransactionList
            List<FinishGoodStockTransaction> finishGoodStockTransactionInList = map.finishGoodStockTransactionInList

            //GET MR
            MarketReturn marketReturn = map.marketReturn
            List<MarketReturnDetails> marketReturnDetailsList = map.marketReturnDetailsList
            //END

            Integer count = 0

            subInventoryToSubInventoryTransferList.each {
                if(it.save()) {
                    count++
                }
            }

            if(count > 0){
                if(distributionPointStockList && distributionPointStockList.size() > 0){
                    distributionPointStockList.each {
                        it.save()
                    }
                }
                if(distributionPointStockInList && distributionPointStockInList.size() > 0){
                    distributionPointStockInList.each {
                        it.save()
                    }
                }
                if(distributionPointStockTransactionList && distributionPointStockTransactionList.size() > 0){
                    distributionPointStockTransactionList.each {
                        it.save()
                    }
                }
                if(distributionPointStockTransactionInList && distributionPointStockTransactionInList.size() > 0){
                    distributionPointStockTransactionInList.each {
                        it.save()
                    }
                }

                if(finishGoodStockList && finishGoodStockList.size() > 0){
                    finishGoodStockList.each {
                        it.save()
                    }
                }
                if(finishGoodStockInList && finishGoodStockInList.size() > 0){
                    finishGoodStockInList.each {
                        it.save()
                    }
                }
                if(finishGoodStockTransactionList && finishGoodStockTransactionList.size() > 0){
                    finishGoodStockTransactionList.each {
                        it.save()
                    }
                }
                if(finishGoodStockTransactionInList && finishGoodStockTransactionInList.size() > 0){
                    finishGoodStockTransactionInList.each {
                        it.save()
                    }
                }

                //MARKET RETURN SAVE
                if(marketReturn){
                    marketReturn.save()
                }

                if(marketReturnDetailsList && marketReturnDetailsList.size() > 0){
                    marketReturnDetailsList.each {
                        it.save()
                    }
                }
                //END
            }

            return count
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
