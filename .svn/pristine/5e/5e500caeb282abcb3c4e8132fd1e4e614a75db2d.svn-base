package com.bits.bdfp.inventory.sales

import com.bits.bdfp.customer.CustomerLevel
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.geolocation.TerritorySubArea
import com.bits.bdfp.inventory.warehouse.Warehouse
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class DistributionPointService extends Service {
    static transactional = false
    SpringSecurityService springSecurityService
    DataSource dataSource
    Sql sql
    DistributionPointWarehouseService distributionPointWarehouseService

    @Transactional
    public DistributionPoint create(Object object) {
        DistributionPoint distributionPoint = (DistributionPoint) object.distributionPoint
        if (distributionPoint.save(false)) {
            List distributionTerritorySubAreaList = object.distributionPointTerritorySubAreaList
            DistributionPointWarehouse distributionPointWarehouse = object.distributionPointWarehouse
            distributionTerritorySubAreaList.each {
                it.save()
            }
            if(distributionPointWarehouse){
                distributionPointWarehouse.save()
            }

            return distributionPoint

        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        DistributionPoint distributionPoint = (DistributionPoint) object.distributionPoint
        if (distributionPoint.save()) {
            List distributionTerritorySubAreaList = object.distributionPointTerritorySubAreaList
            List distributionPointTerritorySubAreaDeleteList = object.distributionPointTerritorySubAreaDeleteList
            DistributionPointWarehouse distributionPointWarehouse = object.distributionPointWarehouse
            distributionPointTerritorySubAreaDeleteList.each {
                it.delete()
            }
            distributionTerritorySubAreaList.each {
                it.save()
            }
            if(distributionPointWarehouse){
                distributionPointWarehouse.save()
            }
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        DistributionPoint distributionPoint = (DistributionPoint) object
        distributionPoint.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<DistributionPoint> objList = DistributionPoint.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = DistributionPoint.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<DistributionPoint> list() {
        return DistributionPoint.list()
    }

    @Transactional(readOnly = true)
    public DistributionPoint read(Long id) {
        return DistributionPoint.get(id)
    }

    @Transactional(readOnly = true)
    public DistributionPoint search(String fieldName, String fieldValue) {
        String query = "from DistributionPoint as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return DistributionPoint.find(query)
    }

    @Transactional(readOnly = true)
    public Map territoryAndInventoryMap(Object params) {
        String strSql = ''
        sql = new Sql(dataSource)
        List territoryList = []
        List inventoryList = []
        List inventoryInchargeList = []
        strSql = """SELECT territory_configuration.id,territory_configuration.name
                    FROM territory_configuration
                    WHERE territory_configuration.enterprise_configuration_id=${params.id}"""
        territoryList = sql.rows(strSql)

        strSql = """SELECT warehouse.id,CONCAT(warehouse.name,'[',warehouse.code,']') as name
                    FROM warehouse
                    WHERE warehouse.enterprise_configuration_id=${params.id}"""
        inventoryList = sql.rows(strSql)

        strSql = """SELECT customer_master.id,CONCAT(customer_master.name,'[',customer_master.code,']') as name
                    FROM customer_master
                    WHERE customer_master.enterprise_configuration_id=${params.id}
                    AND customer_master.master_type_id=(SELECT id FROM master_type WHERE name='Employee')"""
        inventoryInchargeList = sql.rows(strSql)
        return [inventoryList: inventoryList, territoryList: territoryList,inventoryInchargeList: inventoryInchargeList]
//
    }

    @Transactional(readOnly = true)
    public Map getGeoLocationInfobyCustomer(Object params) {
        String byDp = ''
        if(params.dpId){
            byDp = """ AND distribution_point_territory_sub_area.distribution_point_id = ${params.dpId}"""
        }

        String strSql = ''
        sql = new Sql(dataSource)
        List geoLocationList = []
        strSql = """
                        SELECT  territory_sub_area.id
                                \t ,territory_sub_area.geo_location
                                \t,country_info.name AS country_name
                                \t,division.name AS division_name
                                \t ,district.name AS district_name
                                \t ,thana_upazila_pouroshova.name AS thana_name
                                \t ,union_info.name AS union_name
                                \t ,territory_sub_area.para_or_locality
                                \t ,territory_sub_area.road
                                \t ,distribution_point_territory_sub_area.id AS addedd_geo_location_id

                           FROM territory_sub_area

                                    INNER JOIN territory_configuration ON territory_configuration.id=territory_sub_area.territory_configuration_id
                                    INNER JOIN  country_info  ON   country_info.id=territory_sub_area.country_info_id
                                    INNER JOIN division   ON    division.id=territory_sub_area.division_id
                                    INNER JOIN  district ON  district.id=territory_sub_area.district_id
                                    LEFT OUTER JOIN  thana_upazila_pouroshova ON   thana_upazila_pouroshova.id=territory_sub_area.thana_upazila_pouroshova_id
                                    LEFT OUTER JOIN    union_info ON    union_info.id=territory_sub_area.union_info_id
                                    LEFT JOIN   distribution_point_territory_sub_area  ON distribution_point_territory_sub_area.territory_sub_area_id=territory_sub_area.id
                                    INNER JOIN `customer_territory_sub_area` ON `customer_territory_sub_area`.`territory_sub_area_id`=  `territory_sub_area`.id

                            WHERE customer_territory_sub_area.`customer_master_id` = ${params.id}
                             GROUP BY territory_sub_area.id

                    """
//   WHERE territory_sub_area.territory_configuration_id=${params.id}
//        SELECT territory_sub_area.id,territory_sub_area.geo_location,country_info.name AS country_name,
//        division.name AS division_name,district.name AS district_name,thana_upazila_pouroshova.name AS thana_name,
//        union_info.name AS union_name,territory_sub_area.para_or_locality,territory_sub_area.road,
//        distribution_point_territory_sub_area.id AS addedd_geo_location_id
//        -- (CASE WHEN ${params.edit=='true'} THEN distribution_point_territory_sub_area.id  ELSE NULL  END) AS addedd_geo_location_id
//        FROM
//        territory_sub_area
//        INNER JOIN territory_configuration ON territory_configuration.id=territory_sub_area.territory_configuration_id
//        INNER JOIN
//        country_info ON  country_info.id=territory_sub_area.country_info_id
//        INNER JOIN division ON division.id=territory_sub_area.division_id
//        INNER JOIN district ON district.id=territory_sub_area.district_id
//        LEFT OUTER JOINhana_upazila_pouroshova  ON  thana_upazila_pouroshova.id=territory_sub_area.thana_upazila_pouroshova_id
//        LEFT OUTER JOIN union_info ON union_info.id=territory_sub_area.union_info_id
//        LEFT JOIN distribution_point_territory_sub_area ON (distribution_point_territory_sub_area.territory_sub_area_id=territory_sub_area.id ${byDp})
//        INNER JOIN `distribution_point_warehouse` ON `distribution_point_warehouse`.`distribution_point_id`=  distribution_point_territory_sub_area.`distribution_point_id`
//
//        WHERE distribution_point_warehouse.`default_customer_id` = ${params.id}
//        GROUP BY territory_sub_area.id

        geoLocationList = sql.rows(strSql)
        return [objList: geoLocationList, count: geoLocationList?.size()]


    }

    @Transactional(readOnly = true)
    public Map getGeoLocationInfo(Object params) {
        String byDp = ''
        if(params.dpId){
            byDp = """ AND distribution_point_territory_sub_area.distribution_point_id = ${params.dpId}"""
        }

        String strSql = ''
        sql = new Sql(dataSource)
        List geoLocationList = []
        strSql = """SELECT territory_sub_area.id,territory_sub_area.geo_location,country_info.name AS country_name,
                    division.name AS division_name,district.name AS district_name,thana_upazila_pouroshova.name AS thana_name,
                    union_info.name AS union_name,territory_sub_area.para_or_locality,territory_sub_area.road,
                    distribution_point_territory_sub_area.id AS addedd_geo_location_id
                    -- (CASE WHEN ${params.edit=='true'} THEN distribution_point_territory_sub_area.id  ELSE NULL  END) AS addedd_geo_location_id
                    FROM
                    territory_sub_area
                    INNER JOIN
                    territory_configuration
                    ON
                    territory_configuration.id=territory_sub_area.territory_configuration_id
                    INNER JOIN
                    country_info
                    ON
                    country_info.id=territory_sub_area.country_info_id
                    INNER JOIN
                    division
                    ON
                    division.id=territory_sub_area.division_id
                    INNER JOIN
                    district
                    ON
                    district.id=territory_sub_area.district_id
                    LEFT OUTER JOIN
                    thana_upazila_pouroshova
                    ON
                    thana_upazila_pouroshova.id=territory_sub_area.thana_upazila_pouroshova_id
                    LEFT OUTER JOIN
                    union_info
                    ON
                    union_info.id=territory_sub_area.union_info_id
                    LEFT JOIN
                    distribution_point_territory_sub_area
                    ON
                    (distribution_point_territory_sub_area.territory_sub_area_id=territory_sub_area.id ${byDp})
                    WHERE territory_sub_area.territory_configuration_id=${params.id}
                    GROUP BY territory_sub_area.id
                    """

        geoLocationList = sql.rows(strSql)
        return [objList: geoLocationList, count: geoLocationList?.size()]


    }

    @Transactional(readOnly = true)
    public List distributionPointDefaultCustomerList(Object params) {
        sql = new Sql(dataSource)
        String query = ""
        if (params.query) {
            query = """AND (cm.name LIKE '%${params.query}%' OR cm.code LIKE '%${params.query}%')
                """
        }
        String strSql = """
            SELECT DISTINCT cm.id,cm.code,cm.name,cc.`name` AS category
                ,GROUP_CONCAT(`tsa`.`geo_location`) AS geo_location
            FROM `territory_configuration` tc

                INNER JOIN `territory_sub_area` tsa ON (tsa.`territory_configuration_id` = tc.`id`)
                INNER JOIN `customer_territory_sub_area` ctsa ON (ctsa.`territory_sub_area_id`= tsa.`id`)
                INNER JOIN `customer_master` cm ON (cm.`id` = ctsa.`customer_master_id`)
                INNER JOIN `customer_category` cc ON (cc.`id` = cm.`category_id`)

            WHERE tc.id = ${params.id}
                AND (cm.`category_id` = ${ApplicationConstants.CUSTOMER_CATEGORY_BRANCH_ID} || cm.`category_id` = ${ApplicationConstants.CUSTOMER_CATEGORY_DIRECT_ID})
                AND cm.`customer_level`= '${CustomerLevel.PRIMARY}'
                AND cm.master_type_id = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID}
                ${query}
            GROUP BY `cm`.id,`cm`.`code`,`cm`.`name`
        """

//        SELECT customer_master.id,customer_master.code,customer_master.name,
//        master_type.name AS status,GROUP_CONCAT(`territory_sub_area`.`geo_location`) AS geo_location
//        FROM customer_master
//        INNER JOIN master_type ON master_type.id=customer_master.master_type_id
//        LEFT OUTER JOIN customer_territory_sub_area ON customer_master.id=customer_territory_sub_area.customer_master_id
//        LEFT JOIN territory_sub_area ON territory_sub_area.id=customer_territory_sub_area.territory_sub_area_id
//        INNER JOIN enterprise_configuration ON enterprise_configuration.id=customer_master.enterprise_configuration_id
//        WHERE enterprise_configuration.id=${params.id}
//        AND customer_master.master_type_id=(SELECT id FROM master_type WHERE name='Customer')
//        ${query}
//        GROUP BY `customer_master`.id,`customer_master`.`code`,`customer_master`.`name`, master_type.`name`

        List resultList = sql.rows(strSql.toString())
        return resultList
    }

    @Transactional(readOnly = true)
    public Map getDistributionPointForEdit(Long id) {
        Map result = [:]
        DistributionPointWarehouse distributionPointWarehouse=null
        Warehouse warehouse=null
        CustomerMaster inCharge=null
        CustomerMaster defaultCustomer =null
        DistributionPoint distributionPoint = DistributionPoint.read(id)
        EnterpriseConfiguration enterpriseConfiguration = distributionPoint.enterpriseConfiguration
        distributionPointWarehouse = distributionPointWarehouseService.getWareHouseByDistributionPoint(distributionPoint)
        if(distributionPointWarehouse){
             warehouse = distributionPointWarehouse.warehouse
             inCharge = distributionPointWarehouse.inCharge
             defaultCustomer = distributionPointWarehouse.defaultCustomer
        }

        TerritoryConfiguration territoryConfiguration = distributionPointWarehouseService.territoryConfigurationByDistribution(distributionPoint)

//        result.put("finishProduct",finishProduct)
//        result.put("enterpriseConfiguration",enterpriseConfiguration)
//        result.put("businessUnitConfiguration",businessUnitConfiguration)
//        result.put("productCategory",productCategory)
//        result.put("masterProduct",masterProduct)
//        result.put("mainProduct",mainProduct)
//        result.put("productType",productType)
//        result.put("measureUnitConfiguration",measureUnitConfiguration)
        return [distributionPoint: distributionPoint, distributionPointWarehouse: distributionPointWarehouse, warehouse: warehouse, inCharge: inCharge, defaultCustomer: defaultCustomer, territoryConfiguration: territoryConfiguration, enterpriseConfiguration: enterpriseConfiguration]
    }

    @Transactional(readOnly = true)
    public DistributionPointTerritorySubArea existingDistributionSubArea(TerritorySubArea territorySubArea, DistributionPoint distributionPoint) {
        sql = new Sql(dataSource)
        DistributionPointTerritorySubArea distributionPointTerritorySubArea = DistributionPointTerritorySubArea.findByDistributionPointAndTerritorySubArea(distributionPoint, territorySubArea)
        return distributionPointTerritorySubArea
    }

    @Transactional(readOnly = true)
    public List<DistributionPointTerritorySubArea> allExistingDistributionSubAreaList(DistributionPoint distributionPoint) {
        List<DistributionPointTerritorySubArea> list = DistributionPointTerritorySubArea.findAllByDistributionPoint(distributionPoint)
        return list

    }

    @Transactional(readOnly = true)
    public DistributionPointWarehouse dwByDistributionPoint(DistributionPoint distributionPoint) {
        DistributionPointWarehouse distributionPointWarehouse = DistributionPointWarehouse.findByDistributionPoint(distributionPoint)
        return distributionPointWarehouse

    }

    @Transactional
    public Integer deleteDistributionPoint(Object object) {
        DistributionPoint distributionPoint = (DistributionPoint) object.distributionPoint
        if (distributionPoint) {
            List distributionPointTerritorySubAreaDeleteList = object.distributionPointTerritorySubAreaExistingList
            DistributionPointWarehouse distributionPointWarehouse = object.distributionPointWarehouse
            distributionPointTerritorySubAreaDeleteList.each {
                it.delete()
            }
            if(distributionPointWarehouse){
                distributionPointWarehouse.delete()
            }
            distributionPoint.delete()
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional(readOnly = true)
    public List listDistributionPoint(Object params,ApplicationUser applicationUser) {
        sql = new Sql(dataSource)
        String  strSql = """SELECT distribution_point.id,distribution_point.`name`
                            FROM distribution_point
                                INNER JOIN `enterprise_configuration`
                                    ON `enterprise_configuration`.id = `distribution_point`.`enterprise_configuration_id`
                                INNER JOIN `application_user_enterprise`
                                    ON `enterprise_configuration`.id = `application_user_enterprise`.`enterprise_configuration_id`
                            WHERE `enterprise_configuration`.id = ${params.id}
                                AND `application_user_enterprise`.`application_user_id` = ${applicationUser.id}
                                                      """

        List list = sql.rows(strSql.toString())
        return list
    }

    @Transactional(readOnly = true)
    public List listDistributionPointByEnterprise(Object params) {
        sql = new Sql(dataSource)
        String  strSql = """
            SELECT `id`, `name`, `address`
            FROM `distribution_point`
            WHERE `enterprise_configuration_id`=:enterpriseId
        """
        return sql.rows(strSql, params)
    }

    @Transactional(readOnly = true)
    public List listDistributionPointByApplicationUser(Object params) {
        try{
            ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
            sql = new Sql(dataSource)
            String  strSql = """
                            SELECT `distribution_point`.`id`, `distribution_point`.`name`
                            FROM `application_user_distribution_point`
                                INNER JOIN `distribution_point` ON (`application_user_distribution_point`.`distribution_point_id` = `distribution_point`.`id`)
                            WHERE `application_user_distribution_point`.`application_user_id` = ${applicationUser.id}
                                AND `distribution_point`.`enterprise_configuration_id` IN (
                                        SELECT `enterprise_configuration_id`
                                        FROM `application_user_enterprise`
                                        WHERE `application_user_id` = ${applicationUser.id})
                                """
            List list = sql.rows(strSql)
            return list
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listDpByEnterpriseAndAppUser(Object params) {
        try{
            sql = new Sql(dataSource)
            String strSql = """
                SELECT `distribution_point`.`id`,`distribution_point`.`name`
                FROM `application_user_distribution_point`
                    INNER JOIN `distribution_point`
                        ON `application_user_distribution_point`.`distribution_point_id` = `distribution_point`.`id`
                WHERE `application_user_distribution_point`.`application_user_id` = ${params.applicationUserId}
                    AND `distribution_point`.`enterprise_configuration_id` = ${params.enterpriseId}
                ORDER BY `distribution_point`.`name`
            """
            List objList = sql.rows(strSql)
            return objList
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listDistributionPointByDpWarehouseDefaultCustomer() {
        try{
            ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
            sql = new Sql(dataSource)
            String  strSql = """
                SELECT dp.id, dp.name
                  FROM distribution_point_warehouse dpw
                INNER JOIN distribution_point dp
                        ON (dp.id = dpw.distribution_point_id)
                WHERE default_customer_id = ${applicationUser.customerMasterId}
            """
            List list = sql.rows(strSql)
            return list
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
    @Transactional(readOnly = true)
    public List getGeolocationByCustomer(Object params){
        try{
            sql = new Sql(dataSource)
            String  strSql = """
                SELECT `customer_territory_sub_area`.`territory_sub_area_id` AS id,`territory_sub_area`.`geo_location` AS geoLocation
                    FROM `customer_territory_sub_area`
                JOIN `territory_sub_area`
                        ON `territory_sub_area`.id = `customer_territory_sub_area`.`territory_sub_area_id`
                WHERE `customer_territory_sub_area`.`customer_master_id` = ${params.customerMasterId}
            """
            List list = sql.rows(strSql)
            return list
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listDistributionPointBySubArea(Object params) {
        try{
            sql = new Sql(dataSource)
            String  strSql = """
                SELECT `distribution_point`.`id`, `distribution_point`.`name`
                FROM `distribution_point`
                INNER JOIN `distribution_point_territory_sub_area` dptsa ON (dptsa.`distribution_point_id` = `distribution_point`.`id`)
                WHERE dptsa.`territory_sub_area_id` =:territorySubAreaId
            """
            return sql.rows(strSql, params)
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.mesmessage)
        }
    }
}
