package com.bits.bdfp.inventory.setup

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.setup.incentive.QuantityBasedIncentive
import com.bits.bdfp.inventory.setup.incentive.QuantityBasedIncentiveCustomers
import com.bits.bdfp.inventory.setup.incentive.QuantityBasedIncentiveSlab
import com.bits.bdfp.inventory.setup.incentive.SalesAmountBasedIncentive
import com.bits.bdfp.inventory.setup.incentive.SalesAmountBasedIncentiveCustomers
import com.bits.bdfp.inventory.setup.incentive.SalesAmountBasedIncentiveSlab
import com.bits.bdfp.inventory.setup.incentive.TargetBasedIncentive
import com.bits.bdfp.inventory.setup.incentive.TargetBasedIncentiveCustomers
import com.bits.bdfp.inventory.setup.incentive.TargetBasedIncentiveSlab
import com.bits.bdfp.inventory.setup.incentive.VolumeBasedIncentive
import com.bits.bdfp.inventory.setup.incentive.VolumeBasedIncentiveCustomers
import com.bits.bdfp.inventory.setup.incentive.VolumeBasedIncentiveProductSet
import com.bits.bdfp.inventory.setup.incentive.VolumeBasedIncentiveSlab
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource

class IncentiveSetupService {
    static transactional = true
    DataSource dataSource
    Sql sql

    @Transactional(readOnly = true)
    public Boolean checkOverlappingMultipleProgram(String className, Long id, Date dateFrom, Date dateTo){
        String effectiveDateFrom = dateFrom.format('dd-MM-yyyy')
        String effectiveDateTo = dateTo.format('dd-MM-yyyy')
        String query = ""
        Boolean isOverlapped = false

        query = """
            SELECT * FROM target_based_incentive
            WHERE (STR_TO_DATE(DATE_FORMAT(effective_date_from,'%d-%m-%Y'),'%d-%m-%Y') BETWEEN STR_TO_DATE('${effectiveDateFrom}','%d-%m-%Y') AND STR_TO_DATE('${effectiveDateTo}','%d-%m-%Y'))
            OR (STR_TO_DATE(DATE_FORMAT(effective_date_to,'%d-%m-%Y'),'%d-%m-%Y') BETWEEN STR_TO_DATE('${effectiveDateFrom}','%d-%m-%Y') AND STR_TO_DATE('${effectiveDateTo}','%d-%m-%Y'));
        """

        sql = new  Sql(dataSource)
        List targetBasedIncentiveList = sql.rows(query)

        List salesAmountBasedIncentiveList = []
        List quantityBasedIncentiveList = []
        List volumeBasedIncentiveList = []

        if(targetBasedIncentiveList.size() == 0){
            query = """
                SELECT * FROM sales_amount_based_incentive
                WHERE (STR_TO_DATE(DATE_FORMAT(effective_date_from,'%d-%m-%Y'),'%d-%m-%Y') BETWEEN STR_TO_DATE('${effectiveDateFrom}','%d-%m-%Y') AND STR_TO_DATE('${effectiveDateTo}','%d-%m-%Y'))
                OR (STR_TO_DATE(DATE_FORMAT(effective_date_to,'%d-%m-%Y'),'%d-%m-%Y') BETWEEN STR_TO_DATE('${effectiveDateFrom}','%d-%m-%Y') AND STR_TO_DATE('${effectiveDateTo}','%d-%m-%Y'));
            """
            salesAmountBasedIncentiveList = sql.rows(query)
        }

        if(salesAmountBasedIncentiveList.size() == 0){
            query = """
                SELECT * FROM quantity_based_incentive
                WHERE (STR_TO_DATE(DATE_FORMAT(effective_date_from,'%d-%m-%Y'),'%d-%m-%Y') BETWEEN STR_TO_DATE('${effectiveDateFrom}','%d-%m-%Y') AND STR_TO_DATE('${effectiveDateTo}','%d-%m-%Y'))
                OR (STR_TO_DATE(DATE_FORMAT(effective_date_to,'%d-%m-%Y'),'%d-%m-%Y') BETWEEN STR_TO_DATE('${effectiveDateFrom}','%d-%m-%Y') AND STR_TO_DATE('${effectiveDateTo}','%d-%m-%Y'));
            """
            quantityBasedIncentiveList = sql.rows(query)
        }

        if(quantityBasedIncentiveList.size() == 0){
            query = """
                SELECT * FROM volume_based_incentive
                WHERE (STR_TO_DATE(DATE_FORMAT(effective_date_from,'%d-%m-%Y'),'%d-%m-%Y') BETWEEN STR_TO_DATE('${effectiveDateFrom}','%d-%m-%Y') AND STR_TO_DATE('${effectiveDateTo}','%d-%m-%Y'))
                OR (STR_TO_DATE(DATE_FORMAT(effective_date_to,'%d-%m-%Y'),'%d-%m-%Y') BETWEEN STR_TO_DATE('${effectiveDateFrom}','%d-%m-%Y') AND STR_TO_DATE('${effectiveDateTo}','%d-%m-%Y'));
            """
            volumeBasedIncentiveList = sql.rows(query)
        }

        if(targetBasedIncentiveList && targetBasedIncentiveList.size()>0){
            targetBasedIncentiveList.each {
                if(className == "TargetBasedIncentive"){
                    if(id != it.id){
                        isOverlapped = true
                    }
                }else{
                    isOverlapped = true
                }
            }
        }else if(salesAmountBasedIncentiveList && salesAmountBasedIncentiveList.size()>0){
            salesAmountBasedIncentiveList.each {
                if(className == "SalesAmountBasedIncentive"){
                    if(id != it.id){
                        isOverlapped = true
                    }
                }else{
                    isOverlapped = true
                }
            }
        }else if(quantityBasedIncentiveList && quantityBasedIncentiveList.size()>0){
            quantityBasedIncentiveList.each {
                if(className == "QuantityBasedIncentive"){
                    if(id != it.id){
                        isOverlapped = true
                    }
                }else{
                    isOverlapped = true
                }
            }
        }else if(volumeBasedIncentiveList && volumeBasedIncentiveList.size()>0){
            volumeBasedIncentiveList.each {
                if(className == "VolumeBasedIncentive"){
                    if(id != it.id){
                        isOverlapped = true
                    }
                }else{
                    isOverlapped = true
                }
            }
        }

        return isOverlapped
    }

    @Transactional(readOnly = true)
    public Map getGeoAndCustomersListByTerritory(Object params) {
        String queryGeo = """
            SELECT tsa.territory_configuration_id, tsa.id, tsa.geo_location AS "name"
            FROM territory_configuration tc
            INNER JOIN territory_sub_area tsa
                    ON tsa.territory_configuration_id = tc.id
            WHERE tc.id IN (${params.territoryIds})
            GROUP BY tsa.id
            ORDER BY tsa.id ASC
        """

        sql = new  Sql(dataSource)
        List geoList = sql.rows(queryGeo)

        List customerList = []

        if(geoList.size()>0){
            String queryCustomer = """
                SELECT cm.id, cm.name AS "name", cm.code
                FROM territory_configuration tc
                INNER JOIN territory_sub_area tsa
                        ON tsa.territory_configuration_id = tc.id
                INNER JOIN customer_territory_sub_area ctsa
                        ON ctsa.territory_sub_area_id = tsa.id
                INNER JOIN customer_master cm
                        ON cm.id = ctsa.customer_master_id
                WHERE tc.id IN (${params.territoryIds})
                GROUP BY cm.id
                ORDER BY cm.id ASC
            """
            customerList = sql.rows(queryCustomer)
        }

        Map map = [:]
        map.put("geoList",geoList)
        map.put("customerList",customerList)

        return map
    }

    @Transactional(readOnly = true)
    public Map getPtAndCustomersListByGeo(Object params) {
        List customerList = []
        List ptList = []

        String condition = ''
        if(params.geoIds){
            condition = """
                AND tsa.id IN (${params.geoIds})
            """

            String queryPt = """
                SELECT pc.id, pc.name
                FROM territory_sub_area tsa
                INNER JOIN customer_territory_sub_area ctsa
                        ON ctsa.territory_sub_area_id = tsa.id
                INNER JOIN customer_master cm
                        ON cm.id = ctsa.customer_master_id
                INNER JOIN pricing_category pc
                        ON pc.id = cm.pricing_category_id
                WHERE tsa.id IN (${params.geoIds})
                GROUP BY pc.id
            """
            sql = new  Sql(dataSource)
            ptList = sql.rows(queryPt)
        }

        if(params.territoryIds){
            String queryCustomer = """
                SELECT cm.id, cm.name AS "name", cm.code
                FROM territory_configuration tc
                INNER JOIN territory_sub_area tsa
                        ON tsa.territory_configuration_id = tc.id
                INNER JOIN customer_territory_sub_area ctsa
                        ON ctsa.territory_sub_area_id = tsa.id
                INNER JOIN customer_master cm
                        ON cm.id = ctsa.customer_master_id
                WHERE tc.id IN (${params.territoryIds})
                  ${condition}
                GROUP BY cm.id
                ORDER BY cm.id ASC
            """
            customerList = sql.rows(queryCustomer)
        }

        Map map = [:]
        map.put("ptList",ptList)
        map.put("customerList",customerList)

        return map
    }

    @Transactional(readOnly = true)
    public Map getScAndCustomersListByPt(Object params) {
        List customerList = []
        List scList = []

        String condition = ''
        if(params.ptIds && params.geoIds){
            condition = """
                AND pc.id IN (${params.ptIds})
            """

            String querySc = """
                SELECT csc.id, csc.name
                FROM territory_sub_area tsa
                INNER JOIN customer_territory_sub_area ctsa
                        ON ctsa.territory_sub_area_id = tsa.id
                INNER JOIN customer_master cm
                        ON cm.id = ctsa.customer_master_id
                INNER JOIN pricing_category pc
                        ON pc.id = cm.pricing_category_id
                INNER JOIN customer_sales_channel csc
                        ON csc.id = cm.customer_sales_channel_id
                WHERE tsa.id IN (${params.geoIds})
                  AND pc.id In (${params.ptIds})
                GROUP BY csc.id
            """
            sql = new  Sql(dataSource)
            scList = sql.rows(querySc)
        }

        if(params.territoryIds && params.geoIds){
            String queryCustomer = """
                SELECT cm.id, cm.name AS "name", cm.code
                FROM territory_configuration tc
                INNER JOIN territory_sub_area tsa
                        ON tsa.territory_configuration_id = tc.id
                INNER JOIN customer_territory_sub_area ctsa
                        ON ctsa.territory_sub_area_id = tsa.id
                INNER JOIN customer_master cm
                        ON cm.id = ctsa.customer_master_id
                LEFT JOIN pricing_category pc
                        ON pc.id = cm.pricing_category_id
                WHERE tc.id IN (${params.territoryIds})
                  AND tsa.id IN (${params.geoIds})
                  ${condition}
                GROUP BY cm.id
                ORDER BY cm.id ASC
            """
            customerList = sql.rows(queryCustomer)
        }

        Map map = [:]
        map.put("scList",scList)
        map.put("customerList",customerList)

        return map
    }

    @Transactional(readOnly = true)
    public Map getCcAndCustomersListBySc(Object params) {
        List customerList = []
        List ccList = []

        String condition = ''
        if(params.scIds && params.ptIds && params.geoIds){
            condition = """
                AND csc.id IN (${params.scIds})
            """

            String queryCc = """
                SELECT cc.id, cc.name
                FROM territory_sub_area tsa
                INNER JOIN customer_territory_sub_area ctsa
                        ON ctsa.territory_sub_area_id = tsa.id
                INNER JOIN customer_master cm
                        ON cm.id = ctsa.customer_master_id
                INNER JOIN pricing_category pc
                        ON pc.id = cm.pricing_category_id
                INNER JOIN customer_sales_channel csc
                        ON csc.id = cm.customer_sales_channel_id
                INNER JOIN customer_category cc
                        ON cc.id = cm.category_id
                WHERE tsa.id IN (${params.geoIds})
                  AND pc.id In (${params.ptIds})
                  AND csc.id In (${params.scIds})
                GROUP BY cc.id
            """
            sql = new  Sql(dataSource)
            ccList = sql.rows(queryCc)
        }

        if(params.territoryIds && params.geoIds && params.ptIds){
            String queryCustomer = """
                SELECT cm.id, cm.name AS "name", cm.code
                FROM territory_configuration tc
                INNER JOIN territory_sub_area tsa
                        ON tsa.territory_configuration_id = tc.id
                INNER JOIN customer_territory_sub_area ctsa
                        ON ctsa.territory_sub_area_id = tsa.id
                INNER JOIN customer_master cm
                        ON cm.id = ctsa.customer_master_id
                INNER JOIN pricing_category pc
                        ON pc.id = cm.pricing_category_id
                INNER JOIN customer_sales_channel csc
                        ON csc.id = cm.customer_sales_channel_id
                WHERE tc.id IN (${params.territoryIds})
                  AND tsa.id IN (${params.geoIds})
                  AND pc.id IN (${params.ptIds})
                  ${condition}
                GROUP BY cm.id
                ORDER BY cm.id ASC
            """
            customerList = sql.rows(queryCustomer)
        }

        Map map = [:]
        map.put("ccList",ccList)
        map.put("customerList",customerList)

        return map
    }

    @Transactional(readOnly = true)
    public Map getCustomersListByCc(Object params) {
        List customerList = []

        String condition = ''
        if(params.ccIds && params.scIds && params.ptIds && params.geoIds){
            condition = """
                AND cc.id IN (${params.ccIds})
            """
        }

        if(params.territoryIds && params.geoIds && params.ptIds && params.scIds){
            String queryCustomer = """
                SELECT cm.id, cm.name AS "name", cm.code
                FROM territory_configuration tc
                INNER JOIN territory_sub_area tsa
                        ON tsa.territory_configuration_id = tc.id
                INNER JOIN customer_territory_sub_area ctsa
                        ON ctsa.territory_sub_area_id = tsa.id
                INNER JOIN customer_master cm
                        ON cm.id = ctsa.customer_master_id
                INNER JOIN pricing_category pc
                        ON pc.id = cm.pricing_category_id
                INNER JOIN customer_sales_channel csc
                        ON csc.id = cm.customer_sales_channel_id
                INNER JOIN customer_category cc
                        ON cc.id = cm.category_id
                WHERE tc.id IN (${params.territoryIds})
                  AND tsa.id IN (${params.geoIds})
                  AND pc.id IN (${params.ptIds})
                  AND csc.id IN (${params.scIds})
                  ${condition}
                GROUP BY cm.id
                ORDER BY cm.id ASC
            """
            customerList = sql.rows(queryCustomer)
        }

        Map map = [:]
        map.put("customerList",customerList)

        return map
    }

    @Transactional(readOnly = true)
    public Object getQbUomByProduct(Object params){
        String query = """
            SELECT muc.id, muc.name
            FROM measure_unit_configuration muc
            INNER JOIN finish_product fp
                    ON muc.id = fp.measure_unit_configuration_id
            WHERE fp.id = ${params.id}
            LIMIT 1
        """

        sql = new  Sql(dataSource)
        Object object = sql.firstRow(query)

        return object
    }

    @Transactional(readOnly = true)
    public List getVbPrimaryUomByProduct(Object params){
        String query = """
            SELECT muc.id, muc.name
            FROM measure_unit_configuration muc
            INNER JOIN finish_product fp
                    ON muc.id = fp.measure_unit_configuration_id
            WHERE fp.id IN(${params.productIds})
            GROUP BY muc.id
        """

        sql = new  Sql(dataSource)
        List list = sql.rows(query)

        return list
    }

    @Transactional(readOnly = true)
    public List getAllProductListByMasterAndMainProduct(Object params){
        String condition = ''
        if (params.masterProductId){
            condition = """ AND master_product_id = ${params.masterProductId} """
        }

        if(params.mainProductId){
            condition += """ AND main_product_id = ${params.mainProductId} """
        }

        String query = """
            SELECT id, name
            FROM finish_product
            WHERE 1=1
            ${condition}
        """

        sql = new  Sql(dataSource)
        List list = sql.rows(query)

        return list
    }

    @Transactional
    public TargetBasedIncentive createTargetBasedIncentive(Object object){
        try {
            Map map = (Map) object

            TargetBasedIncentive targetBasedIncentive = map.get("targetBasedIncentive")
            List<TargetBasedIncentiveSlab> targetBasedIncentiveSlabList = map.get("targetBasedIncentiveSlabList")
            List<TargetBasedIncentiveCustomers> targetBasedIncentiveCustomersList = map.get("targetBasedIncentiveCustomersList")

            List<TargetBasedIncentiveSlab> targetBasedIncentiveSlabDeleteList = []
            List<TargetBasedIncentiveCustomers> targetBasedIncentiveCustomersDeleteList = []

            if (targetBasedIncentive.id) {
                targetBasedIncentiveSlabDeleteList = map.get("targetBasedIncentiveSlabDeleteList")
                targetBasedIncentiveCustomersDeleteList = map.get("targetBasedIncentiveCustomersDeleteList")

                targetBasedIncentive = targetBasedIncentive.merge()
            }

            if (targetBasedIncentive.save(false)) {
                if (targetBasedIncentiveSlabDeleteList.size() > 0) {
                    targetBasedIncentiveSlabDeleteList.each {
                        it.delete()
                    }
                }
                if (targetBasedIncentiveCustomersDeleteList.size() > 0) {
                    targetBasedIncentiveCustomersDeleteList.each {
                        it.delete()
                    }
                }

                targetBasedIncentiveSlabList.each {
                    it.save(false)
                }
                targetBasedIncentiveCustomersList.each {
                    it.save(false)
                }
                return targetBasedIncentive
            } else {
                return null
            }
        }catch(Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public SalesAmountBasedIncentive createSalesAmountBasedIncentive(Object object){
        try {
            Map map = (Map) object

            SalesAmountBasedIncentive salesAmountBasedIncentive = map.get("salesAmountBasedIncentive")
            List<SalesAmountBasedIncentiveSlab> salesAmountBasedIncentiveSlabList = map.get("salesAmountBasedIncentiveSlabList")
            List<SalesAmountBasedIncentiveCustomers> salesAmountBasedIncentiveCustomersList = map.get("salesAmountBasedIncentiveCustomersList")

            List<SalesAmountBasedIncentiveSlab> salesAmountBasedIncentiveSlabDeleteList = []
            List<SalesAmountBasedIncentiveCustomers> salesAmountBasedIncentiveCustomersDeleteList = []

            if (salesAmountBasedIncentive.id) {
                salesAmountBasedIncentiveSlabDeleteList = map.get("salesAmountBasedIncentiveSlabDeleteList")
                salesAmountBasedIncentiveCustomersDeleteList = map.get("salesAmountBasedIncentiveCustomersDeleteList")

                salesAmountBasedIncentive = salesAmountBasedIncentive.merge()
            }

            if (salesAmountBasedIncentive.save(false)) {
                if (salesAmountBasedIncentiveSlabDeleteList.size() > 0) {
                    salesAmountBasedIncentiveSlabDeleteList.each {
                        it.delete()
                    }
                }
                if (salesAmountBasedIncentiveCustomersDeleteList.size() > 0) {
                    salesAmountBasedIncentiveCustomersDeleteList.each {
                        it.delete()
                    }
                }

                salesAmountBasedIncentiveSlabList.each {
                    it.save(false)
                }
                salesAmountBasedIncentiveCustomersList.each {
                    it.save(false)
                }
                return salesAmountBasedIncentive
            } else {
                return null
            }
        }catch(Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public QuantityBasedIncentive createQuantityBasedIncentive(Object object){
        try {
            Map map = (Map) object

            QuantityBasedIncentive quantityBasedIncentive = map.get("quantityBasedIncentive")
            List<QuantityBasedIncentiveSlab> quantityBasedIncentiveSlabList = map.get("quantityBasedIncentiveSlabList")
            List<QuantityBasedIncentiveCustomers> quantityBasedIncentiveCustomersList = map.get("quantityBasedIncentiveCustomersList")

            List<QuantityBasedIncentiveSlab> quantityBasedIncentiveSlabDeleteList = []
            List<QuantityBasedIncentiveCustomers> quantityBasedIncentiveCustomersDeleteList = []

            if (quantityBasedIncentive.id) {
                quantityBasedIncentiveSlabDeleteList = map.get("quantityBasedIncentiveSlabDeleteList")
                quantityBasedIncentiveCustomersDeleteList = map.get("quantityBasedIncentiveCustomersDeleteList")

                quantityBasedIncentive = quantityBasedIncentive.merge()
            }

            if (quantityBasedIncentive.save(false)) {
                if (quantityBasedIncentiveSlabDeleteList.size() > 0) {
                    quantityBasedIncentiveSlabDeleteList.each {
                        it.delete()
                    }
                }
                if (quantityBasedIncentiveCustomersDeleteList.size() > 0) {
                    quantityBasedIncentiveCustomersDeleteList.each {
                        it.delete()
                    }
                }

                quantityBasedIncentiveSlabList.each {
                    it.save(false)
                }
                quantityBasedIncentiveCustomersList.each {
                    it.save(false)
                }
                return quantityBasedIncentive
            } else {
                return null
            }
        }catch(Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public VolumeBasedIncentive createVolumeBasedIncentive(Object object){
        try {
            Map map = (Map) object

            VolumeBasedIncentive volumeBasedIncentive = map.get("volumeBasedIncentive")
            List<VolumeBasedIncentiveProductSet> volumeBasedIncentiveProductSetList = map.get("volumeBasedIncentiveProductSetList")
            List<VolumeBasedIncentiveSlab> volumeBasedIncentiveSlabList = map.get("volumeBasedIncentiveSlabList")
            List<VolumeBasedIncentiveCustomers> volumeBasedIncentiveCustomersList = map.get("volumeBasedIncentiveCustomersList")

            List<VolumeBasedIncentiveProductSet> volumeBasedIncentiveProductSetDeleteList = []
            List<VolumeBasedIncentiveSlab> volumeBasedIncentiveSlabDeleteList = []
            List<VolumeBasedIncentiveCustomers> volumeBasedIncentiveCustomersDeleteList = []

            if (volumeBasedIncentive.id) {
                volumeBasedIncentiveProductSetDeleteList = map.get("volumeBasedIncentiveProductSetDeleteList")
                volumeBasedIncentiveSlabDeleteList = map.get("volumeBasedIncentiveSlabDeleteList")
                volumeBasedIncentiveCustomersDeleteList = map.get("volumeBasedIncentiveCustomersDeleteList")

                volumeBasedIncentive = volumeBasedIncentive.merge()
            }

            if (volumeBasedIncentive.save(false)) {
                if (volumeBasedIncentiveProductSetDeleteList.size() > 0) {
                    volumeBasedIncentiveProductSetDeleteList.each {
                        it.delete()
                    }
                }
                if (volumeBasedIncentiveSlabDeleteList.size() > 0) {
                    volumeBasedIncentiveSlabDeleteList.each {
                        it.delete()
                    }
                }
                if (volumeBasedIncentiveCustomersDeleteList.size() > 0) {
                    volumeBasedIncentiveCustomersDeleteList.each {
                        it.delete()
                    }
                }

                if (volumeBasedIncentiveSlabList.size() > 0) {
                    volumeBasedIncentiveSlabList.each {
                        it.save(false)
                    }
                }
                if (volumeBasedIncentiveProductSetList.size() > 0) {
                    volumeBasedIncentiveProductSetList.each {
                        it.save(false)
                    }
                }
                if (volumeBasedIncentiveCustomersList.size() > 0) {
                    volumeBasedIncentiveCustomersList.each {
                        it.save(false)
                    }
                }
                return volumeBasedIncentive
            } else {
                return null
            }
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

//    Update Incentive
    @Transactional(readOnly = true)
    public List getIncentiveProgramListByType(Object params){
        List list = []
        if(params.id == 'tbi'){
            list = TargetBasedIncentive.list()
        }else if(params.id == 'sabi'){
            list = SalesAmountBasedIncentive.list()
        }else if(params.id == 'qbi'){
            list = QuantityBasedIncentive.list()
        }else if(params.id == 'vbi'){
            list = VolumeBasedIncentive.list()
        }

        return list
    }

    @Transactional(readOnly = true)
    public List getIncentiveCustomersByProgram(Object params){
        List list = []
        String query = ""

        if(params.programType == 'tbi'){
            query = """
                SELECT tbic.customer_master_id AS customerId, cm.name, cm.code
                FROM target_based_incentive_customers tbic
                INNER JOIN customer_master cm
                        ON cm.id = tbic.customer_master_id
                WHERE tbic.target_based_incentive_id = ${params.programName}
                    AND tbic.sattlement_status IS NULL
             """
        }else if(params.programType == 'sabi'){
            query = """
                SELECT sabic.customer_master_id AS customerId, cm.name, cm.code
                FROM sales_amount_based_incentive_customers sabic
                INNER JOIN customer_master cm
                        ON cm.id = sabic.customer_master_id
                WHERE sabic.sales_amount_based_incentive_id = ${params.programName}
                    AND sabic.sattlement_status IS NULL
            """
        }else if(params.programType == 'qbi'){
            query = """
                SELECT qbic.customer_master_id AS customerId, cm.name, cm.code
                FROM quantity_based_incentive_customers qbic
                INNER JOIN customer_master cm
                        ON cm.id = qbic.customer_master_id
                WHERE qbic.quantity_based_incentive_id = ${params.programName}
                    AND qbic.sattlement_status IS NULL
            """
        }else if(params.programType == 'vbi'){
            query = """
                SELECT vbic.customer_master_id AS customerId, cm.name, cm.code
                FROM volume_based_incentive_customers vbic
                INNER JOIN customer_master cm
                        ON cm.id = vbic.customer_master_id
                WHERE vbic.volume_based_incentive_id = ${params.programName}
                    AND vbic.sattlement_status IS NULL
            """
        }

        sql = new  Sql(dataSource)
        list = sql.rows(query)
        return list
    }

    @Transactional(readOnly = true)
    public List getAllSearchIncentiveProgramListByCriteria(Object params){
        List list = []
        String table_name = ""
        String condition = ""

        if(params.programName){
            condition = """ AND id = ${params.programName} """
        }

        if(params.programType == 'tbi'){
            table_name = "target_based_incentive"
        }else if(params.programType == 'sabi'){
            table_name = "sales_amount_based_incentive"
        }else if(params.programType == 'qbi'){
            table_name = "quantity_based_incentive"
        }else if(params.programType == 'vbi'){
            table_name = "volume_based_incentive"
        }

        String query = """
            SELECT id,
                program_name AS programName,
                date_format(date_created,'%d-%m-%Y') AS dateCreated,
                date_format(effective_date_from,'%d-%m-%Y') AS effectiveDateFrom,
                date_format(effective_date_to,'%d-%m-%Y') AS effectiveDateTo

            FROM ${table_name}
            WHERE STR_TO_DATE(date_format(effective_date_from,'%d-%m-%Y'),'%d-%m-%Y') >= str_to_date('${params.effectiveDateFrom}','%d-%m-%Y')
            AND STR_TO_DATE(date_format(effective_date_to,'%d-%m-%Y'),'%d-%m-%Y') <= str_to_date('${params.effectiveDateTo}','%d-%m-%Y')
            ${condition}
        """

        sql = new  Sql(dataSource)
        list = sql.rows(query)
        return list
    }


    @Transactional(readOnly = true)
    public List getTargetBasedIncentiveCurrentSlabList(Object params){
        String query = """
            SELECT tbis.id AS tbisId,
                   tbis.achievement_from AS achievementFrom,
                   tbis.achievement_to AS achievementTo,
                   tbis.incentive_amount AS incentiveAmount

            FROM target_based_incentive_slab tbis
            WHERE tbis.target_based_incentive_id = ${params.id}
        """

        sql = new  Sql(dataSource)
        List list = sql.rows(query)
        return list
    }

    @Transactional(readOnly = true)
    public List getTargetBasedCurrentCustomersList(Object params){
        String query = """
            SELECT tbic.id AS tbicId, cm.name, cm.id, cm.code,
                (SELECT GROUP_CONCAT(DISTINCT tc.name SEPARATOR ', ') FROM territory_sub_area tsa
                INNER JOIN territory_configuration tc
                        ON tc.id = tsa.territory_configuration_id
                WHERE tsa.id IN (
                    SELECT territory_sub_area_id FROM customer_territory_sub_area WHERE customer_master_id = cm.id
                )) AS territory,

                (SELECT GROUP_CONCAT(tsa.geo_location SEPARATOR ', ') AS geoLocation FROM territory_sub_area tsa
                WHERE tsa.id IN (
                    SELECT territory_sub_area_id FROM customer_territory_sub_area WHERE customer_master_id = cm.id
                )) AS geo,

                (SELECT GROUP_CONCAT(pc.name) AS pt FROM customer_master
                INNER JOIN pricing_category pc
                        ON pc.id = customer_master.pricing_category_id
                WHERE customer_master.id = cm.id) AS partnerType,

                (SELECT csc.name FROM customer_master
                INNER JOIN customer_sales_channel csc
                        ON csc.id = customer_master.customer_sales_channel_id
                WHERE customer_master.id = cm.id) AS salesChannel,

                (SELECT cc.name FROM customer_master
                INNER JOIN customer_category cc
                        ON cc.id = customer_master.category_id
                WHERE customer_master.id = cm.id) AS customerCategory

            FROM target_based_incentive tbi
            INNER JOIN target_based_incentive_customers tbic
                ON tbi.id = tbic.target_based_incentive_id
            INNER JOIN customer_master cm
                ON cm.id = tbic.customer_master_id
            WHERE tbi.id = ${params.id}

            ORDER BY tbic.id DESC
        """

        sql = new  Sql(dataSource)
        List list = sql.rows(query)
        return list
    }

    @Transactional(readOnly = true)
    public List getSalesAmountBasedIncentiveCurrentSlabList(Object params){
        String query = """
            SELECT sabis.id AS sabisId,
                   sabis.sales_value_from AS salesValueFrom,
                   sabis.sales_value_to AS salesValueTo,
                   sabis.incentive_amount AS incentiveAmount,
                   sabis.incentive_in_pct AS incentiveInPct

            FROM sales_amount_based_incentive_slab sabis
            WHERE sabis.sales_amount_based_incentive_id = ${params.id}
        """

        sql = new  Sql(dataSource)
        List list = sql.rows(query)
        return list
    }

    @Transactional(readOnly = true)
    public List getSalesAmountBasedCurrentCustomersList(Object params){
        String query = """
            SELECT sabic.id AS sabicId, cm.name, cm.id, cm.code,

                (SELECT GROUP_CONCAT(DISTINCT tc.name SEPARATOR ', ') FROM territory_sub_area tsa
                INNER JOIN territory_configuration tc
                        ON tc.id = tsa.territory_configuration_id
                WHERE tsa.id IN (
                    SELECT territory_sub_area_id FROM customer_territory_sub_area WHERE customer_master_id = cm.id
                )) AS territory,

                (SELECT GROUP_CONCAT(tsa.geo_location SEPARATOR ', ') AS geoLocation FROM territory_sub_area tsa
                WHERE tsa.id IN (
                    SELECT territory_sub_area_id FROM customer_territory_sub_area WHERE customer_master_id = cm.id
                )) AS geo,

                (SELECT GROUP_CONCAT(pc.name) AS pt FROM customer_master
                INNER JOIN pricing_category pc
                        ON pc.id = customer_master.pricing_category_id
                WHERE customer_master.id = cm.id) AS partnerType,

                (SELECT csc.name FROM customer_master
                INNER JOIN customer_sales_channel csc
                        ON csc.id = customer_master.customer_sales_channel_id
                WHERE customer_master.id = cm.id) AS salesChannel,

                (SELECT cc.name FROM customer_master
                INNER JOIN customer_category cc
                        ON cc.id = customer_master.category_id
                WHERE customer_master.id = cm.id) AS customerCategory

            FROM sales_amount_based_incentive sabi
            INNER JOIN sales_amount_based_incentive_customers sabic
                ON sabi.id = sabic.sales_amount_based_incentive_id
            INNER JOIN customer_master cm
                ON cm.id = sabic.customer_master_id
            WHERE sabi.id = ${params.id}
        """

        sql = new  Sql(dataSource)
        List list = sql.rows(query)
        return list
    }

    @Transactional(readOnly = true)
    public List getQuantityBasedIncentiveCurrentSlabList(Object params){
        String query = """
            SELECT qbis.id AS qbisId,
                   fp.id AS productId,
                   fp.name AS productName,
                   qbis.quantity_from AS quantityFrom,
                   qbis.quantity_to AS quantityTo,
                   qbis.incentive_amount AS incentiveAmount

            FROM quantity_based_incentive_slab qbis
            INNER JOIN finish_product fp
                ON fp.id = qbis.finish_product_id
            WHERE qbis.quantity_based_incentive_id = ${params.id}
        """

        sql = new  Sql(dataSource)
        List list = sql.rows(query)
        return list
    }

    @Transactional(readOnly = true)
    public List getQuantityBasedCurrentCustomersList(Object params){
        String query = """
            SELECT qbic.id AS qbicId, cm.name, cm.id, cm.code,

            (SELECT GROUP_CONCAT(DISTINCT tc.name SEPARATOR ', ') FROM territory_sub_area tsa
                INNER JOIN territory_configuration tc
                        ON tc.id = tsa.territory_configuration_id
                WHERE tsa.id IN (
                    SELECT territory_sub_area_id FROM customer_territory_sub_area WHERE customer_master_id = cm.id
                )) AS territory,

                (SELECT GROUP_CONCAT(tsa.geo_location SEPARATOR ', ') AS geoLocation FROM territory_sub_area tsa
                WHERE tsa.id IN (
                    SELECT territory_sub_area_id FROM customer_territory_sub_area WHERE customer_master_id = cm.id
                )) AS geo,

                (SELECT GROUP_CONCAT(pc.name) AS pt FROM customer_master
                INNER JOIN pricing_category pc
                        ON pc.id = customer_master.pricing_category_id
                WHERE customer_master.id = cm.id) AS partnerType,

                (SELECT csc.name FROM customer_master
                INNER JOIN customer_sales_channel csc
                        ON csc.id = customer_master.customer_sales_channel_id
                WHERE customer_master.id = cm.id) AS salesChannel,

                (SELECT cc.name FROM customer_master
                INNER JOIN customer_category cc
                        ON cc.id = customer_master.category_id
                WHERE customer_master.id = cm.id) AS customerCategory

            FROM quantity_based_incentive qbi
            INNER JOIN quantity_based_incentive_customers qbic
                ON qbi.id = qbic.quantity_based_incentive_id
            INNER JOIN customer_master cm
                ON cm.id = qbic.customer_master_id
            WHERE qbi.id = ${params.id}
        """

        sql = new  Sql(dataSource)
        List list = sql.rows(query)
        return list
    }

    @Transactional(readOnly = true)
    public List getVolumeBasedIncentiveCurrentSlabList(Object params){
        String query = """
            SELECT vbis.id AS vbisId,
                   vbis.version,
                   vbis.product_set_name AS productSetName,
                   vbis.volume_from AS volumeFrom,
                   vbis.volume_to AS volumeTo,
                   vbis.incentive_amount_value AS incentiveAmountValue,
                   vbis.incentive_amount_pct AS incentiveAmountPct,
                   vbis.per_unit_master_uom_cost AS perUnitMasterUomCost,
                   vbis.master_uom_id AS masterUomId

            FROM volume_based_incentive_slab vbis
            WHERE vbis.volume_based_incentive_id = ${params.id}
        """

        sql = new  Sql(dataSource)
        List list = sql.rows(query)
        return list
    }

    @Transactional(readOnly = true)
    public List getVolumeBasedCurrentCustomersList(Object params){
        String query = """
            SELECT vbic.id AS vbicId, cm.name, cm.id, cm.code,

            (SELECT GROUP_CONCAT(DISTINCT tc.name SEPARATOR ', ') FROM territory_sub_area tsa
                INNER JOIN territory_configuration tc
                        ON tc.id = tsa.territory_configuration_id
                WHERE tsa.id IN (
                    SELECT territory_sub_area_id FROM customer_territory_sub_area WHERE customer_master_id = cm.id
                )) AS territory,

                (SELECT GROUP_CONCAT(tsa.geo_location SEPARATOR ', ') AS geoLocation FROM territory_sub_area tsa
                WHERE tsa.id IN (
                    SELECT territory_sub_area_id FROM customer_territory_sub_area WHERE customer_master_id = cm.id
                )) AS geo,

                (SELECT GROUP_CONCAT(pc.name) AS pt FROM customer_master
                INNER JOIN pricing_category pc
                        ON pc.id = customer_master.pricing_category_id
                WHERE customer_master.id = cm.id) AS partnerType,

                (SELECT csc.name FROM customer_master
                INNER JOIN customer_sales_channel csc
                        ON csc.id = customer_master.customer_sales_channel_id
                WHERE customer_master.id = cm.id) AS salesChannel,

                (SELECT cc.name FROM customer_master
                INNER JOIN customer_category cc
                        ON cc.id = customer_master.category_id
                WHERE customer_master.id = cm.id) AS customerCategory

            FROM volume_based_incentive vbi
            INNER JOIN volume_based_incentive_customers vbic
                ON vbi.id = vbic.volume_based_incentive_id
            INNER JOIN customer_master cm
                ON cm.id = vbic.customer_master_id
            WHERE vbi.id = ${params.id}
        """

        sql = new  Sql(dataSource)
        List list = sql.rows(query)
        return list
    }

    @Transactional(readOnly = true)
    public Map getNetSalesAmountByCustomer(Object params){
        String effectiveDateFrom = ''
        String effectiveDateTo = ''

        Double salesReturn = 0
        Double netSales = 0
        Double incentiveAmount = 0

        String queryIncentiveAmount = ''

        if(params.programType == 'tbi'){
            TargetBasedIncentive targetBasedIncentive = TargetBasedIncentive.read(Long.parseLong(params.programId))
            effectiveDateFrom = targetBasedIncentive.effectiveDateFrom.format('dd-MM-yyyy')
            effectiveDateTo = targetBasedIncentive.effectiveDateTo.format('dd-MM-yyyy')
        }else if(params.programType == 'sabi'){
            SalesAmountBasedIncentive salesAmountBasedIncentive = SalesAmountBasedIncentive.read(Long.parseLong(params.programId))
            effectiveDateFrom = salesAmountBasedIncentive.effectiveDateFrom.format('dd-MM-yyyy')
            effectiveDateTo = salesAmountBasedIncentive.effectiveDateTo.format('dd-MM-yyyy')
        }else if(params.programType == 'qbi'){
            QuantityBasedIncentive quantityBasedIncentive = QuantityBasedIncentive.read(Long.parseLong(params.programId))
            effectiveDateFrom = quantityBasedIncentive.effectiveDateFrom.format('dd-MM-yyyy')
            effectiveDateTo = quantityBasedIncentive.effectiveDateTo.format('dd-MM-yyyy')
        }else if(params.programType == 'vbi'){
            VolumeBasedIncentive volumeBasedIncentive = VolumeBasedIncentive.read(Long.parseLong(params.programId))
            effectiveDateFrom = volumeBasedIncentive.effectiveDateFrom.format('dd-MM-yyyy')
            effectiveDateTo = volumeBasedIncentive.effectiveDateTo.format('dd-MM-yyyy')
        }

        String invoiceIds = ''
        if(params.programType == 'sabi' || params.programType == 'tbi'){
            invoiceIds = ''
            String queryNetSales = """
            SELECT SUM(invoice_amount) as netSales, GROUP_CONCAT(id) AS invoiceIds
            FROM invoice
            WHERE invoice.is_active = true
                AND default_customer_id = ${params.customerId}
                AND is_incentive_calculated != TRUE
                AND STR_TO_DATE(DATE_FORMAT(date_created,'%d-%m-%Y'),'%d-%m-%Y')
                    BETWEEN STR_TO_DATE('${effectiveDateFrom}','%d-%m-%Y')
                    AND STR_TO_DATE('${effectiveDateTo}','%d-%m-%Y')
        """

            String querySalesReturn = """
            SELECT SUM(credit_amount) - SUM(debit_amount) AS amount
            FROM journal_details
            WHERE chart_of_accounts_id = (
                SELECT id FROM chart_of_accounts WHERE chart_of_account_code_user = '3201439'
            ) AND prefix_code = '${CustomerMaster.get(Long.parseLong(params.customerId)).code}'
        """

            sql = new  Sql(dataSource)

            List listSalesReturn = sql.rows(querySalesReturn)
            if(listSalesReturn && listSalesReturn.size()>0){
                if(listSalesReturn[0].amount){
                    salesReturn = listSalesReturn[0].amount
                }
            }

            List listNetSales = sql.rows(queryNetSales)
            if(listNetSales && listNetSales.size()>0){
                if(listNetSales[0].netSales){
                    netSales = listNetSales[0].netSales - salesReturn
                }
                if(listNetSales[0].invoiceIds){
                    invoiceIds = listNetSales[0].invoiceIds
                }
            }
        }

        if(params.programType == 'sabi'){
            /*
            queryIncentiveAmount = """
                SELECT id, sales_amount_based_incentive_id, sales_value_from, sales_value_to, incentive_amount AS incentiveAmount, incentive_in_pct AS incentiveInPct, date_created
                FROM sales_amount_based_incentive_slab
                WHERE sales_amount_based_incentive_id = ${params.programId}
                AND ${netSales} BETWEEN sales_value_from AND sales_value_to
            """ */

            queryIncentiveAmount = """
                SELECT tbl1.rowCount,
                CASE tbl1.rowCount WHEN 0 THEN
                    CASE WHEN ${netSales} > (SELECT MAX(sales_value_to) FROM sales_amount_based_incentive_slab WHERE sales_amount_based_incentive_id = ${params.programId})
                    THEN tbl2.incentiveAmount
                    ELSE 0 END
                ELSE tbl1.incentiveAmount END AS incentiveAmount,

                CASE tbl1.rowCount WHEN 0 THEN
                    CASE WHEN ${netSales} > (SELECT MAX(sales_value_to) FROM sales_amount_based_incentive_slab WHERE sales_amount_based_incentive_id = ${params.programId})
                    THEN tbl2.incentiveInPct
                    ELSE 0 END
                ELSE tbl1.incentiveInPct END AS incentiveInPct

                FROM

                (SELECT COUNT(id) AS rowCount, id, sales_amount_based_incentive_id, sales_value_from, sales_value_to, incentive_amount AS incentiveAmount, incentive_in_pct AS incentiveInPct, date_created
                FROM sales_amount_based_incentive_slab
                WHERE sales_amount_based_incentive_id = ${params.programId}
                AND ${netSales} BETWEEN sales_value_from AND sales_value_to
                ) AS tbl1

                JOIN

                (SELECT id, sales_amount_based_incentive_id, sales_value_from, sales_value_to, incentive_amount AS incentiveAmount, incentive_in_pct AS incentiveInPct, date_created
                FROM sales_amount_based_incentive_slab
                WHERE sales_amount_based_incentive_id = ${params.programId}
                AND sales_value_to = (SELECT MAX(sales_value_to) FROM sales_amount_based_incentive_slab WHERE sales_amount_based_incentive_id = ${params.programId})
                ) AS tbl2

            """

            sql = new  Sql(dataSource)
            List listIncentive = sql.rows(queryIncentiveAmount)
            if(listIncentive.size()>0){
                if(listIncentive[0].incentiveAmount && listIncentive[0].incentiveAmount>0){
                    incentiveAmount = listIncentive[0].incentiveAmount
                }else if(listIncentive[0].incentiveInPct && listIncentive[0].incentiveInPct>0){
                    incentiveAmount = (netSales * listIncentive[0].incentiveInPct) / 100
                }
            }
        }

        if(params.programType == 'tbi'){
            /*
            queryIncentiveAmount = """
                SELECT id, target_based_incentive_id, achievement_from, achievement_to, incentive_amount AS incentiveAmount, date_created
                FROM target_based_incentive_slab
                WHERE target_based_incentive_id = ${params.programId}
                AND (${netSales} / (
                    SELECT SUM(target_amount) AS targetAmount
                    FROM daily_sales_target_by_amount
                    WHERE employee_id = ${params.customerId}
                    AND is_active = TRUE
                    AND STR_TO_DATE(DATE_FORMAT(target_date,'%d-%m-%Y'),'%d-%m-%Y')
                    BETWEEN STR_TO_DATE('${effectiveDateFrom}','%d-%m-%Y')
                      AND STR_TO_DATE('${effectiveDateTo}','%d-%m-%Y')
                )) * 100

                BETWEEN achievement_from AND achievement_to
            """ */
            queryIncentiveAmount = """
                SELECT tabl1.rowCount,
                   CASE tabl1.rowCount WHEN 0 THEN
                        CASE WHEN tabl2.achievement_to < (
                            (${netSales} / (
                                CASE WHEN (
                                    SELECT IFNULL(SUM(IFNULL(target_amount,0)),0) AS targetAmount
                                    FROM daily_sales_target_by_amount
                                    WHERE employee_id = ${params.customerId}
                                    AND is_active = TRUE
                                    AND STR_TO_DATE(DATE_FORMAT(target_date,'%d-%m-%Y'),'%d-%m-%Y')
                                    BETWEEN STR_TO_DATE('${effectiveDateFrom}','%d-%m-%Y')
                                      AND STR_TO_DATE('${effectiveDateTo}','%d-%m-%Y')
                                ) = 0 THEN 1
                                ELSE(
                                    SELECT IFNULL(SUM(IFNULL(target_amount,0)),0) AS targetAmount
                                    FROM daily_sales_target_by_amount
                                    WHERE employee_id = ${params.customerId}
                                    AND is_active = TRUE
                                    AND STR_TO_DATE(DATE_FORMAT(target_date,'%d-%m-%Y'),'%d-%m-%Y')
                                    BETWEEN STR_TO_DATE('${effectiveDateFrom}','%d-%m-%Y')
                                    AND STR_TO_DATE('${effectiveDateTo}','%d-%m-%Y')
                                )
                                END
                            )) * 100
                        ) THEN
                        tabl2.incentiveAmount
                        ELSE 0 END
                    ELSE tabl1.incentiveAmount
                    END AS incentiveAmount

                    FROM

                    (SELECT COUNT(id) AS rowCount,id, target_based_incentive_id, achievement_from, achievement_to, incentive_amount AS incentiveAmount, date_created
                    FROM target_based_incentive_slab
                    WHERE target_based_incentive_id = ${params.programId}
                    AND (
                    (${netSales} / (
                        CASE WHEN (
                            SELECT IFNULL(SUM(IFNULL(target_amount,0)),0) AS targetAmount
                            FROM daily_sales_target_by_amount
                            WHERE employee_id = ${params.customerId}
                            AND is_active = TRUE
                            AND STR_TO_DATE(DATE_FORMAT(target_date,'%d-%m-%Y'),'%d-%m-%Y')
                            BETWEEN STR_TO_DATE('${effectiveDateFrom}','%d-%m-%Y')
                              AND STR_TO_DATE('${effectiveDateTo}','%d-%m-%Y')
                        ) = 0 THEN 1
                        ELSE(
                            SELECT IFNULL(SUM(IFNULL(target_amount,0)),0) AS targetAmount
                            FROM daily_sales_target_by_amount
                            WHERE employee_id = ${params.customerId}
                            AND is_active = TRUE
                            AND STR_TO_DATE(DATE_FORMAT(target_date,'%d-%m-%Y'),'%d-%m-%Y')
                            BETWEEN STR_TO_DATE('${effectiveDateFrom}','%d-%m-%Y')
                              AND STR_TO_DATE('${effectiveDateTo}','%d-%m-%Y')
                        )
                        END
                    )) * 100
                    BETWEEN achievement_from AND achievement_to)
                    ) AS tabl1

                        JOIN

                    (SELECT id, target_based_incentive_id, achievement_from, achievement_to, incentive_amount AS incentiveAmount, date_created
                    FROM target_based_incentive_slab
                    WHERE achievement_to=(SELECT MAX(achievement_to) FROM target_based_incentive_slab WHERE target_based_incentive_id = ${params.programId})
                    AND  target_based_incentive_id = ${params.programId}) AS tabl2
            """

            sql = new  Sql(dataSource)
            List listIncentive = sql.rows(queryIncentiveAmount)
            if(listIncentive.size()>0){
                if(listIncentive[0].incentiveAmount && listIncentive[0].incentiveAmount>0){
                    incentiveAmount = listIncentive[0].incentiveAmount
                }
            }
        }

        Map map = [:]
        map.put("netSales",netSales.round(2))
        map.put("incentiveAmount",incentiveAmount.round(2))
        map.put("cIds",invoiceIds)


        if(params.programType == 'qbi'){
            String invoiceDetailsId = ''
            String queryNetSales = """
                SELECT i.id AS invoiceId, i.invoice_amount AS netSales, GROUP_CONCAT(id.id) invoiceDetailsId,
                    id.finish_product_id AS productId, fp.name AS productName, SUM(id.quantity) AS quantity

                FROM invoice i
                INNER JOIN invoice_details id
                        ON (i.id = id.invoice_id)
                INNER JOIN finish_product fp
                        ON (fp.id = id.finish_product_id)
                INNER JOIN measure_unit_configuration muc
                        ON (muc.id = fp.measure_unit_configuration_id)
                WHERE i.is_active = true
                AND i.default_customer_id = ${params.customerId}
                AND id.is_incentive_calculated != TRUE
                AND STR_TO_DATE(DATE_FORMAT(i.date_created,'%d-%m-%Y'),'%d-%m-%Y')
                    BETWEEN STR_TO_DATE('${effectiveDateFrom}','%d-%m-%Y') AND STR_TO_DATE('${effectiveDateTo}','%d-%m-%Y')

                GROUP BY id.finish_product_id;
            """

            sql = new  Sql(dataSource)
            List listNetSales = sql.rows(queryNetSales)
            if(listNetSales && listNetSales.size()>0){
                listNetSales.each {
                    /* queryIncentiveAmount = """
                        SELECT qbis.id, qbis.finish_product_id, qbis.incentive_amount
                        FROM quantity_based_incentive_slab qbis
                        WHERE qbis.quantity_based_incentive_id = ${params.programId}
                            AND qbis.finish_product_id = ${it.productId}
                            AND ${it.quantity} BETWEEN qbis.quantity_from AND qbis.quantity_to;
                    """  */

                    queryIncentiveAmount = """
                        SELECT tbl1.rowCount,
                            CASE tbl1.rowCount WHEN 0 THEN
                                CASE WHEN ${it.quantity} > (SELECT MAX(quantity_to) FROM quantity_based_incentive_slab WHERE quantity_based_incentive_id = ${params.programId} AND finish_product_id = ${it.productId})
                                THEN tbl2.incentive_amount
                                ELSE 0 END
                            ELSE tbl1.incentive_amount END AS incentiveAmount

                            FROM

                            (SELECT COUNT(id) AS rowCount, qbis.id, qbis.finish_product_id, qbis.incentive_amount
                            FROM quantity_based_incentive_slab qbis
                            WHERE qbis.quantity_based_incentive_id = ${params.programId}
                                AND qbis.finish_product_id = ${it.productId}
                                AND ${it.quantity} BETWEEN qbis.quantity_from AND qbis.quantity_to
                             ) AS tbl1

                            JOIN

                            (SELECT COUNT(id) AS rowCount, qbis.id, qbis.finish_product_id, qbis.incentive_amount, quantity_to
                            FROM quantity_based_incentive_slab qbis
                            WHERE qbis.quantity_based_incentive_id = ${params.programId}
                                AND qbis.finish_product_id = ${it.productId}
                                AND quantity_to = (SELECT MAX(quantity_to) FROM quantity_based_incentive_slab WHERE quantity_based_incentive_id = ${params.programId}
                                    AND finish_product_id = ${it.productId})
                            ) AS tbl2

                    """

                    sql = new  Sql(dataSource)
                    List listIncentive = sql.rows(queryIncentiveAmount)
                    if(listIncentive.size()>0){
                        if(listIncentive[0].incentiveAmount && listIncentive[0].incentiveAmount>0){
                            incentiveAmount += listIncentive[0].incentiveAmount
                            if(invoiceDetailsId){
                                invoiceDetailsId += ','+it.invoiceDetailsId
                            }else{
                                invoiceDetailsId += it.invoiceDetailsId
                            }
                        }
                    }
                }
            }

            map.put("netSales","")
            map.put("incentiveAmount",incentiveAmount.round(2))
            map.put("cIds",invoiceDetailsId)
        }

        if(params.programType == 'vbi'){
            String invoiceDetailsId = ''
            String queryNetSales = """
                SELECT i.id AS invoiceId, i.invoice_amount AS netSales, GROUP_CONCAT(id.id) invoiceDetailsId, id.finish_product_id, fp.name,
                    LOWER(REPLACE(muc.name,' ','')) AS muc, SUM(fp.pack_size * id.quantity) AS totalPackSize, id.quantity,

                    IFNULL(CASE LOWER(REPLACE(muc.name,' ',''))
                         WHEN 'ml'
                    THEN (SUM(fp.pack_size * id.quantity))/1000
                         WHEN 'gm'
                    THEN (SUM(fp.pack_size * id.quantity))/1000
                         WHEN 'kg'
                    THEN (SUM(fp.pack_size * id.quantity))/1000
                    END,0) AS totalVolume

                FROM invoice i
                INNER JOIN invoice_details id
                        ON (i.id = id.invoice_id)
                INNER JOIN finish_product fp
                        ON (fp.id = id.finish_product_id)
                INNER JOIN measure_unit_configuration muc
                        ON (muc.id = fp.measure_unit_configuration_id)
                WHERE i.is_active = true
                AND i.default_customer_id = ${params.customerId}
                AND id.is_incentive_calculated != TRUE
                AND STR_TO_DATE(DATE_FORMAT(i.date_created,'%d-%m-%Y'),'%d-%m-%Y')
                    BETWEEN STR_TO_DATE('${effectiveDateFrom}','%d-%m-%Y') AND STR_TO_DATE('${effectiveDateTo}','%d-%m-%Y')

                GROUP BY muc.name
            """

            sql = new  Sql(dataSource)
            List listNetSales = sql.rows(queryNetSales)
            if(listNetSales && listNetSales.size()>0){
                listNetSales.each {
                    /* queryIncentiveAmount = """
                        SELECT vbis.id AS vbisId, vbis.incentive_amount_pct AS incentiveAmountPct, vbis.incentive_amount_value AS incentiveAmountValue,
                                vbis.per_unit_master_uom_cost AS perUnitMasterUomCost, vbis.master_uom_id,
                                vbis.product_set_name, LOWER(muc.name) AS masterUom, vbis.volume_from, vbis.volume_to,
                                (SELECT LOWER(REPLACE(muc.name,' ',''))
                                    FROM volume_based_incentive_product_set vbips
                                    INNER JOIN finish_product fp
                                            ON (fp.id = vbips.finish_product_id)
                                    INNER JOIN measure_unit_configuration muc
                                            ON (muc.id = fp.measure_unit_configuration_id)
                                    WHERE vbips.product_set_id = vbis.id
                                ) AS pUom,

                                IFNULL(CASE LOWER(muc.name)
                                    WHEN 'liter'
                                    THEN (
                                        SELECT SUM(fp.pack_size) totalPackSize
                                        FROM volume_based_incentive_product_set vbips
                                        INNER JOIN finish_product fp
                                                ON (fp.id = vbips.finish_product_id)
                                        INNER JOIN measure_unit_configuration muc
                                        ON (muc.id = fp.measure_unit_configuration_id)
                                        WHERE vbips.product_set_id = vbis.id
                                    ) / 1000
                                    WHEN 'kg'
                                    THEN (
                                        SELECT SUM(fp.pack_size) totalPackSize
                                        FROM volume_based_incentive_product_set vbips
                                        INNER JOIN finish_product fp
                                                ON (fp.id = vbips.finish_product_id)
                                        INNER JOIN measure_unit_configuration muc
                                                ON (muc.id = fp.measure_unit_configuration_id)
                                        WHERE vbips.product_set_id = vbis.id
                                    ) / 1000
                                    WHEN 'mettric ton'
                                    THEN (
                                        SELECT SUM(fp.pack_size) totalPackSize
                                        FROM volume_based_incentive_product_set vbips
                                        INNER JOIN finish_product fp
                                                ON (fp.id = vbips.finish_product_id)
                                        INNER JOIN measure_unit_configuration muc
                                                ON (muc.id = fp.measure_unit_configuration_id)
                                        WHERE vbips.product_set_id = vbis.id
                                    ) / 1000
                                END,0) AS totalVolume

                                FROM volume_based_incentive_slab vbis
                                INNER JOIN measure_unit_configuration muc
                                        ON (muc.id = vbis.master_uom_id)

                                WHERE vbis.volume_based_incentive_id = ${params.programId}

                                    AND (SELECT LOWER(REPLACE(muc.name,' ',''))
                                            FROM volume_based_incentive_product_set vbips
                                            INNER JOIN finish_product fp
                                                    ON (fp.id = vbips.finish_product_id)
                                            INNER JOIN measure_unit_configuration muc
                                                    ON (muc.id = fp.measure_unit_configuration_id)
                                            WHERE vbips.product_set_id = vbis.id
                                        ) = '${it.muc}'

                                    AND ${it.totalVolume} BETWEEN vbis.volume_from AND vbis.volume_to ;

                    """ */
                    queryIncentiveAmount = """
                        SELECT tbl1.rowCount,
                            CASE tbl1.rowCount WHEN 0 THEN
                                CASE WHEN ${it.totalVolume} > (SELECT MAX(volume_based_incentive_slab.volume_to) FROM volume_based_incentive_slab
                                    INNER JOIN volume_based_incentive_product_set
                                            ON volume_based_incentive_product_set.product_set_id = volume_based_incentive_slab.id
                                    WHERE volume_based_incentive_id =  ${params.programId}
                                    )
                                THEN tbl2.incentiveAmountValue
                                ELSE 0 END
                            ELSE tbl1.incentiveAmountValue END AS incentiveAmountValue,

                            CASE tbl1.rowCount WHEN 0 THEN
                                CASE WHEN ${it.totalVolume} > (SELECT MAX(volume_based_incentive_slab.volume_to) FROM volume_based_incentive_slab
                                    INNER JOIN volume_based_incentive_product_set
                                            ON volume_based_incentive_product_set.product_set_id = volume_based_incentive_slab.id
                                    WHERE volume_based_incentive_id =  ${params.programId}
                                    )
                                THEN tbl2.incentiveAmountPct
                                ELSE 0 END
                            ELSE tbl1.incentiveAmountPct END AS incentiveAmountPct,

                            CASE tbl1.rowCount WHEN 0 THEN
                                CASE WHEN ${it.totalVolume} > (SELECT MAX(volume_based_incentive_slab.volume_to) FROM volume_based_incentive_slab
                                    INNER JOIN volume_based_incentive_product_set
                                            ON volume_based_incentive_product_set.product_set_id = volume_based_incentive_slab.id
                                    WHERE volume_based_incentive_id =  ${params.programId}
                                    )
                                THEN tbl2.perUnitMasterUomCost
                                ELSE 0 END
                            ELSE tbl1.perUnitMasterUomCost END AS perUnitMasterUomCost

                            FROM

                            (SELECT COUNT(vbis.id) AS rowCount, vbis.id AS vbisId, vbis.incentive_amount_pct AS incentiveAmountPct, vbis.incentive_amount_value AS incentiveAmountValue,
                                    vbis.per_unit_master_uom_cost AS perUnitMasterUomCost, vbis.master_uom_id,
                                    vbis.product_set_name, LOWER(muc.name) AS masterUom, vbis.volume_from, vbis.volume_to,
                                    (SELECT LOWER(REPLACE(muc.name,' ',''))
                                        FROM volume_based_incentive_product_set vbips
                                        INNER JOIN finish_product fp
                                                ON (fp.id = vbips.finish_product_id)
                                        INNER JOIN measure_unit_configuration muc
                                                ON (muc.id = fp.measure_unit_configuration_id)
                                        WHERE vbips.product_set_id = vbis.id
                                        GROUP BY muc.id
                                    ) AS pUom,

                                    IFNULL(CASE LOWER(muc.name)
                                        WHEN 'liter'
                                        THEN (
                                            SELECT SUM(fp.pack_size) totalPackSize
                                            FROM volume_based_incentive_product_set vbips
                                            INNER JOIN finish_product fp
                                                    ON (fp.id = vbips.finish_product_id)
                                            INNER JOIN measure_unit_configuration muc
                                            ON (muc.id = fp.measure_unit_configuration_id)
                                            WHERE vbips.product_set_id = vbis.id
                                        ) / 1000
                                        WHEN 'kg'
                                        THEN (
                                            SELECT SUM(fp.pack_size) totalPackSize
                                            FROM volume_based_incentive_product_set vbips
                                            INNER JOIN finish_product fp
                                                    ON (fp.id = vbips.finish_product_id)
                                            INNER JOIN measure_unit_configuration muc
                                                    ON (muc.id = fp.measure_unit_configuration_id)
                                            WHERE vbips.product_set_id = vbis.id
                                        ) / 1000
                                        WHEN 'mettric ton'
                                        THEN (
                                            SELECT SUM(fp.pack_size) totalPackSize
                                            FROM volume_based_incentive_product_set vbips
                                            INNER JOIN finish_product fp
                                                    ON (fp.id = vbips.finish_product_id)
                                            INNER JOIN measure_unit_configuration muc
                                                    ON (muc.id = fp.measure_unit_configuration_id)
                                            WHERE vbips.product_set_id = vbis.id
                                        ) / 1000
                                    END,0) AS totalVolume

                                    FROM volume_based_incentive_slab vbis
                                    INNER JOIN measure_unit_configuration muc
                                            ON (muc.id = vbis.master_uom_id)

                                    WHERE vbis.volume_based_incentive_id = ${params.programId}

                                        AND (SELECT LOWER(REPLACE(muc.name,' ',''))
                                                FROM volume_based_incentive_product_set vbips
                                                INNER JOIN finish_product fp
                                                        ON (fp.id = vbips.finish_product_id)
                                                INNER JOIN measure_unit_configuration muc
                                                        ON (muc.id = fp.measure_unit_configuration_id)
                                                WHERE vbips.product_set_id = vbis.id
                                                GROUP BY muc.id
                                            ) = '${it.muc}'

                                        AND ${it.totalVolume} BETWEEN vbis.volume_from AND vbis.volume_to

                                    ) AS tbl1

                                    JOIN

                                    (SELECT vbis.id AS vbisId, vbis.incentive_amount_pct AS incentiveAmountPct, vbis.incentive_amount_value AS incentiveAmountValue,
                                    vbis.per_unit_master_uom_cost AS perUnitMasterUomCost, vbis.master_uom_id,
                                    vbis.product_set_name, LOWER(muc.name) AS masterUom, vbis.volume_from, vbis.volume_to,
                                    (SELECT LOWER(REPLACE(muc.name,' ',''))
                                        FROM volume_based_incentive_product_set vbips
                                        INNER JOIN finish_product fp
                                                ON (fp.id = vbips.finish_product_id)
                                        INNER JOIN measure_unit_configuration muc
                                                ON (muc.id = fp.measure_unit_configuration_id)
                                        WHERE vbips.product_set_id = vbis.id
                                        GROUP BY muc.id
                                    ) AS pUom,

                                    IFNULL(CASE LOWER(muc.name)
                                        WHEN 'liter'
                                        THEN (
                                            SELECT SUM(fp.pack_size) totalPackSize
                                            FROM volume_based_incentive_product_set vbips
                                            INNER JOIN finish_product fp
                                                    ON (fp.id = vbips.finish_product_id)
                                            INNER JOIN measure_unit_configuration muc
                                                    ON (muc.id = fp.measure_unit_configuration_id)
                                            WHERE vbips.product_set_id = vbis.id
                                        ) / 1000
                                        WHEN 'kg'
                                        THEN (
                                            SELECT SUM(fp.pack_size) totalPackSize
                                            FROM volume_based_incentive_product_set vbips
                                            INNER JOIN finish_product fp
                                                    ON (fp.id = vbips.finish_product_id)
                                            INNER JOIN measure_unit_configuration muc
                                                    ON (muc.id = fp.measure_unit_configuration_id)
                                            WHERE vbips.product_set_id = vbis.id
                                        ) / 1000
                                        WHEN 'mettric ton'
                                        THEN (
                                            SELECT SUM(fp.pack_size) totalPackSize
                                            FROM volume_based_incentive_product_set vbips
                                            INNER JOIN finish_product fp
                                                    ON (fp.id = vbips.finish_product_id)
                                            INNER JOIN measure_unit_configuration muc
                                                    ON (muc.id = fp.measure_unit_configuration_id)
                                            WHERE vbips.product_set_id = vbis.id
                                        ) / 1000
                                    END,0) AS totalVolume

                                    FROM volume_based_incentive_slab vbis
                                    INNER JOIN measure_unit_configuration muc
                                            ON (muc.id = vbis.master_uom_id)

                                    WHERE vbis.volume_based_incentive_id = ${params.programId}

                                        AND CASE WHEN (SELECT LOWER(REPLACE(muc.name,' ',''))
                                            FROM volume_based_incentive_product_set vbips
                                            INNER JOIN finish_product fp
                                                    ON (fp.id = vbips.finish_product_id)
                                            INNER JOIN measure_unit_configuration muc
                                                    ON (muc.id = fp.measure_unit_configuration_id)
                                            WHERE vbips.product_set_id = vbis.id
                                            GROUP BY muc.id
                                        ) = '${it.muc}' THEN TRUE
                                                        ELSE 1=1 END

                                        AND vbis.volume_to = (SELECT MAX(volume_based_incentive_slab.volume_to) FROM volume_based_incentive_slab
                                        INNER JOIN volume_based_incentive_product_set
                                                ON volume_based_incentive_product_set.product_set_id = volume_based_incentive_slab.id
                                        WHERE volume_based_incentive_id = ${params.programId}
                                        )) AS tbl2
                    """

                    sql = new  Sql(dataSource)
                    List listIncentive = sql.rows(queryIncentiveAmount)
                    if(listIncentive.size()>0){
                        if(listIncentive[0].incentiveAmountValue && listIncentive[0].incentiveAmountValue>0){
                            incentiveAmount += listIncentive[0].incentiveAmountValue
                            if(invoiceDetailsId){
                                invoiceDetailsId += ','+it.invoiceDetailsId
                            }else{
                                invoiceDetailsId += it.invoiceDetailsId
                            }
                        }else if(listIncentive[0].incentiveAmountPct && listIncentive[0].incentiveAmountPct>0){
                            incentiveAmount += ((it.totalVolume * listIncentive[0].incentiveAmountPct) / 100 ) * listIncentive[0].perUnitMasterUomCost
                            if(invoiceDetailsId){
                                invoiceDetailsId += ','+it.invoiceDetailsId
                            }else{
                                invoiceDetailsId += it.invoiceDetailsId
                            }
                        }
                    }
                }
            }

            map.put("netSales","")
            map.put("incentiveAmount",incentiveAmount.round(2))
            map.put("cIds",invoiceDetailsId)
        }

        return map
    }
}

