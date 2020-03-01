package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.accounts.ChartOfAccounts
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointStock
import com.bits.bdfp.inventory.sales.DistributionPointStockTransaction
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Action
import com.docu.security.ApplicationUser
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource

class InventoryToInventoryTransferService {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional(readOnly = true)
    public Map getDpInfoByApplicationUser(Long id){
        try{
            String queryDp = """
                SELECT dp.id, dp.address, dp.name, dp.code
                FROM application_user_distribution_point audp
                    INNER JOIN distribution_point dp ON(audp.`distribution_point_id` = dp.`id`)
                WHERE audp.`application_user_id` = ${id}
            """

            String queryInv = """
                SELECT w.id, w.name
                FROM application_user_distribution_point audp
                    INNER JOIN distribution_point dp ON(audp.`distribution_point_id` = dp.`id`)
                    INNER JOIN distribution_point_warehouse dpw ON (dpw.`distribution_point_id` = dp.`id`)
                    INNER JOIN warehouse w ON(w.`id` = dpw.`warehouse_id`)
                WHERE audp.`application_user_id` = ${id}
            """
            sql = new Sql(dataSource)
            Object dpInfo = sql.firstRow(queryDp)
            List inventoryList = sql.rows(queryInv)

            Map map = [:]
            map.put("dpInfo", dpInfo)
            map.put("inventoryList", inventoryList)

            return map
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public List getReceiverDpList(Long id){
        try{
            String queryDp = """
                SELECT dp.id, dp.address, dp.name, dp.code
                FROM distribution_point dp
                WHERE dp.id NOT IN (${id})
                    AND dp.is_factory = FALSE
                ORDER BY dp.name;
            """

            sql = new Sql(dataSource)
            List dpList = sql.rows(queryDp)

            return dpList
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Map getDataAndSubInventoryListByInventoryId(Long id){
        String queryInv = """
            SELECT id, code, address, name
            FROM warehouse
            WHERE id = ${id}
        """

        String querySubInv = """
            SELECT sw.id, sw.name, swt.name AS typeName
            FROM sub_warehouse sw
            INNER JOIN sub_warehouse_type swt
                ON swt.id = sw.sub_warehouse_type_id
            WHERE warehouse_id = ${id}
        """

        sql = new Sql(dataSource)
        Object inventoryInfo = sql.firstRow(queryInv)
        List subInventoryList = sql.rows(querySubInv)

        Map map = [:]
        map.put("inventoryInfo",inventoryInfo)
        map.put("subInventoryList",subInventoryList)

        return map
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
                SELECT fgs.id AS dpsId,
                    fgs.sub_warehouse_id AS subInventoryId,
                    fp.name AS productName,
                    fp.code AS productCode,
                    fp.id AS productId,
                    fgs.batch_no,
                    ROUND(AVG(fgst.unit_price),2) AS price,
                    (fgs.in_quantity - fgs.out_quantity) AS quantity,
                    ROUND((SUM(fgst.in_quantity - fgst.out_quantity) * AVG(fgst.unit_price)),2) AS totalPrice

                    FROM finish_good_stock fgs
                    INNER JOIN finish_good_stock_transaction fgst
                    ON fgs.id = fgst.finish_good_stock_id
                    INNER JOIN finish_product fp
                    ON fp.id = fgs.finish_product_id

                    WHERE fgs.sub_warehouse_id = ${id}
                        AND (fgs.in_quantity - fgs.out_quantity) > 0
                        GROUP BY fgs.id
            """

            sql = new Sql(dataSource)
            list = sql.rows(queryFactory)
        }else{
            queryNonFactory = """
                SELECT dps.id dpsId,
                    dps.sub_warehouse_id AS subInventoryId,
                    fp.name AS productName,
                    fp.code AS productCode,
                    fp.id AS productId,
                    dps.batch_no,
                    ROUND(AVG(dpst.unit_price),2) AS price,
                    (dps.in_quantity - dps.out_quantity) AS quantity,
                    ROUND((SUM(dpst.in_quantity - dpst.out_quantity) * AVG(dpst.unit_price)),2) AS totalPrice

                FROM distribution_point_stock dps
                INNER JOIN distribution_point_stock_transaction dpst
                        ON dps.id = dpst.distribution_point_stock_id
                INNER JOIN finish_product fp
                        ON fp.id = dps.finish_product_id

                WHERE dps.sub_warehouse_id = ${id}
                    AND (dps.in_quantity - dps.out_quantity) > 0
                GROUP BY fp.`id`, dps.id
            """

            sql = new Sql(dataSource)
            list = sql.rows(queryNonFactory)
        }

        return list
        /*
        String query = """
            SELECT dps.id dpsId,
            dps.sub_warehouse_id AS subInventoryId,
            fp.name AS productName,
            fp.code AS productCode,
            fp.id AS productId,
            dps.batch_no,
            ROUND(AVG(dpst.unit_price),2) AS price,
            (dps.in_quantity - dps.out_quantity) AS quantity,
            ROUND(((dps.in_quantity - dps.out_quantity) * AVG(dpst.unit_price)),2) AS totalPrice


            FROM distribution_point_stock dps
            INNER JOIN distribution_point_stock_transaction dpst
                ON dps.id = dpst.distribution_point_stock_id
            INNER JOIN finish_product fp
                ON fp.id = dps.finish_product_id
            WHERE dps.sub_warehouse_id = ${id}
            AND (dps.in_quantity - dps.out_quantity) > 0
            GROUP BY dps.id

        """

        sql = new Sql(dataSource)
        List list = sql.rows(query)

        return list */
    }

    @Transactional(readOnly = true)
    public Object getReceiverDpInfo(Long id){
        Object object = DistributionPoint.read(id)

        String queryInv = """
            select w.id, w.name
            from warehouse w
            inner join distribution_point_warehouse dpw
                    on w.id = dpw.warehouse_id
            WHERE dpw.distribution_point_id = ${object.id}
        """

        sql = new Sql(dataSource)
        List inventoryList = sql.rows(queryInv)

        Map map = [:]
        map.put("dp",object)
        map.put("inventoryList",inventoryList)

        return map
    }

    @Transactional(readOnly = true)
    public Object getReceiverInventoryInfo(Long id){
        Object object = Warehouse.read(id)

        return object
    }

    @Transactional
    public InventoryToInventoryTransfer createInventoryToInventoryTransfer(Map map){
        try {
            InventoryToInventoryTransfer inventoryToInventoryTransfer = (InventoryToInventoryTransfer) map.inventoryToInventoryTransfer
            FinishGoodStock finishGoodStock = (FinishGoodStock) map.finishGoodStock
            FinishGoodStockTransaction finishGoodStockTransaction = (FinishGoodStockTransaction) map.finishGoodStockTransaction
            DistributionPointStock distributionPointStock = (DistributionPointStock) map.distributionPointStock
            DistributionPointStockTransaction distributionPointStockTransaction = (DistributionPointStockTransaction) map.distributionPointStockTransaction
            if (inventoryToInventoryTransfer.save()) {
                if(finishGoodStock){
                    finishGoodStock.save()
                }
                if(finishGoodStockTransaction){
                    finishGoodStockTransaction.save()
                }
                if(distributionPointStock){
                    distributionPointStock.save()
                }
                if(distributionPointStockTransaction){
                    distributionPointStockTransaction.save()
                }

                /************* Save COA Data  ************/
                Journal journalHead = (Journal) map.journalHead
                journalHead.save(validate: false, insert: true)

                List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.journalDetailsList
                journalDetailsList.each { journalDetails ->
                    journalDetails.save(validate: false, insert: true)
                }
                /** **************************************/

            }
            return inventoryToInventoryTransfer
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }


    @Transactional(readOnly = true)
    public Map getListTransferStatusGrid(Action action, Object params,ApplicationUser applicationUser) {
        sql = new Sql(dataSource)
        String strLIMIT = "";
        String offSet = ""

        if (action.resultPerPage != -1) {
            strLIMIT = """LIMIT ${action.resultPerPage}"""
            offSet = """
                    OFFSET ${action.start}
                   """
        } else {
            action.resultPerPage = -1;
        }

        String strSql = """
            SELECT itit.id,
                itit.sender_dp_id,
                sdp.name AS sender_dp_name,
                itit.sender_inventory_id,
                sw.name AS sender_inventory_name,
                itit.sender_sub_inventory_id,
                ssw.name AS sender_sub_inventory_name,
                itit.batch,
                itit.transfer_product_id,
                fp.name AS transfer_product_name,
                itit.transfer_qty,
                itit.unit_price,
                itit.transfer_challan_number,
                itit.transfer_date,
                itit.receiver_dp_id,
                rdp.name AS receiver_dp_name,
                itit.receiver_inventory_id,
                rw.name AS receiver_inventory_name

                FROM inventory_to_inventory_transfer itit
                INNER JOIN distribution_point sdp
                        ON sdp.id = itit.sender_dp_id
                INNER JOIN warehouse sw
                        ON sw.id = itit.sender_inventory_id
                INNER JOIN sub_warehouse ssw
                        ON ssw.id = itit.sender_sub_inventory_id
                INNER JOIN finish_product fp
                        ON fp.id = itit.transfer_product_id
                INNER JOIN distribution_point rdp
                        ON rdp.id = itit.receiver_dp_id
                INNER JOIN warehouse rw
                        ON rw.id = itit.receiver_inventory_id
                WHERE itit.transfer_challan_status = '${InventoryToInventoryTransferStatus.TRANSFER_IN_TRANSIT}'
                AND itit.receiver_dp_id =
                ( SELECT `distribution_point`.`id` FROM `application_user_distribution_point`
                    INNER JOIN `distribution_point`
                      ON `application_user_distribution_point`.`distribution_point_id` = `distribution_point`.`id`
                    WHERE `application_user_distribution_point`.`application_user_id` = ${applicationUser.id}
                    AND `distribution_point`.`enterprise_configuration_id` IN (
                    SELECT `enterprise_configuration_id` FROM `application_user_enterprise`
                    WHERE `application_user_id` = ${applicationUser.id}))
                GROUP BY itit.id
                ORDER BY itit.id ASC
                ${strLIMIT}
                ${offSet}
        """
        List objList = sql.rows(strSql)
        int resultCount = 0
        if (objList && objList.size() > 0) {
            resultCount = objList.size()
        }

        return [objList: objList, count: resultCount]
    }

    @Transactional
    public Integer receiveTransfer(Map map){
        try {
            InventoryToInventoryTransfer inventoryToInventoryTransfer = (InventoryToInventoryTransfer) map.inventoryToInventoryTransfer

            FinishGoodStock finishGoodStock = (FinishGoodStock) map.finishGoodStock
            FinishGoodStockTransaction finishGoodStockTransaction = (FinishGoodStockTransaction) map.finishGoodStockTransaction

            DistributionPointStock distributionPointStock = (DistributionPointStock) map.distributionPointStock
            DistributionPointStockTransaction distributionPointStockTransaction = (DistributionPointStockTransaction) map.distributionPointStockTransaction
            Integer count = 0

            if (inventoryToInventoryTransfer.save()) {
                if(finishGoodStock){
                    finishGoodStock.save()
                }
                if(finishGoodStockTransaction){
                    finishGoodStockTransaction.save()
                }
                if(distributionPointStock){
                    distributionPointStock.save()
                }
                if(distributionPointStockTransaction){
                    distributionPointStockTransaction.save()
                }
                count++
            }

            return count
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public List<JournalDetails> getJournalDetailsList(Float transferAmount, Journal journalHead, ApplicationUser applicationUser, ChartOfAccounts chartOfAccountsCurrentAccountWithHo, ChartOfAccounts chartOfAccountsPurchase, DistributionPoint senderDP, DistributionPoint receiverDP)
    {
        try{
            DistributionPoint factoryDp = DistributionPoint.findByEnterpriseConfigurationAndIsFactory(senderDP.enterpriseConfiguration, true)
            List<JournalDetails> journalDetailsList = new ArrayList<JournalDetails>()
            JournalDetails journalDetails = null
                journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsCurrentAccountWithHo
                journalDetails.prefixCode = factoryDp.code.toString()
                journalDetails.postfixCode = senderDP.code.toString()
                journalDetails.debitAmount = transferAmount
                journalDetails.creditAmount = 0.00
                journalDetails.particular = ApplicationConstants.MODULE_INVENTORY_TO_INVENTORY_TRANSFER + " from Distribution Point: [" + senderDP.code + "] " + senderDP.name
                journalDetailsList.add(journalDetails)

                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsPurchase
                journalDetails.prefixCode = ''
                journalDetails.postfixCode = senderDP.code.toString()
                journalDetails.debitAmount = 0.00
                journalDetails.creditAmount = transferAmount
            journalDetails.particular = ApplicationConstants.MODULE_INVENTORY_TO_INVENTORY_TRANSFER + " from Distribution Point: [" + receiverDP.code + "] " + receiverDP.name
                journalDetailsList.add(journalDetails)


            return journalDetailsList
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Object processInventoryToInventoryTransfer(Map data) {


        /************* Save COA Data  ************/
        Journal journalHead = (Journal) data.get('journalHead')
        journalHead.save(validate: false, insert: true)

        List<JournalDetails> journalDetailsList = (List<JournalDetails>) data.get('journalDetailsList')
        journalDetailsList.each { journalDetails->
            journalDetails.save(validate: false, insert: true)
        }
        /****************************************/
    }
}
