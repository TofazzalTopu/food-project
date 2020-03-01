package com.bits.bdfp.geolocation

import com.bits.bdfp.customer.CustomerLevel
import com.bits.bdfp.util.ApplicationConstants
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class TerritorySubAreaService extends Service {
    static transactional = false
    DataSource dataSource
    SpringSecurityService   springSecurityService
    Sql sql

    @Transactional
    public TerritorySubArea create(Object object) {
        TerritorySubArea territorySubArea = (TerritorySubArea) object
        if (territorySubArea.save(false)) {
            return territorySubArea
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        TerritorySubArea territorySubArea = (TerritorySubArea) object
        if (territorySubArea.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        TerritorySubArea territorySubArea = (TerritorySubArea) object
        territorySubArea.delete()
        return new Integer(1)
    }



    @Transactional(readOnly = true)
    public Map getListForGrid(Action action,Long tid) {
        List<TerritorySubArea> objList = TerritorySubArea.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
//            if( eq('territoryConfiguration.id',tid )){
//
//            }
            eq('territoryConfiguration.id',tid )
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = TerritorySubArea.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<TerritorySubArea> list() {
        return TerritorySubArea.list()
    }

    @Transactional(readOnly = true)
    public TerritorySubArea read(Long id) {
        return TerritorySubArea.get(id)
    }

    @Transactional(readOnly = true)
    public TerritorySubArea search(String fieldName, String fieldValue) {
        String query = "from TerritorySubArea as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return TerritorySubArea.find(query)
    }

    @Transactional(readOnly = true)
    public List searchTerritorySubAreaByTerritory(String territoryId) {
        String query = """SELECT `id`, `geo_location` AS `name`
                          FROM `territory_sub_area`
                          WHERE `territory_configuration_id` IN ('${territoryId}')
                          ORDER BY `name`"""
        Sql sql = new Sql(dataSource)
        return sql.rows(query)
    }
    @Transactional(readOnly = true)
    public List searchCustomerCategoryByTerritorySubArea(String territoryId) {
        String customer_level=CustomerLevel.PRIMARY;
        Long branch=ApplicationConstants.CUSTOMER_CATEGORY_BRANCH_ID;
        Long dealer=ApplicationConstants.CUSTOMER_CATEGORY_DEALER_ID;
        Long pos=ApplicationConstants.CUSTOMER_CATEGORY_DIRECT_ID;
        String query = """

                          SELECT DISTINCT
                                `customer_category`.`id`
                                , `customer_category`.`name`
                            FROM
                                `customer_territory_sub_area`
                                INNER JOIN `customer_master`
                                    ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`)
                                INNER JOIN `customer_category`
                                    ON (`customer_master`.`category_id` = `customer_category`.`id`)
                            WHERE `customer_territory_sub_area`.`territory_sub_area_id` = ${territoryId}
                            AND `customer_master`.`customer_level` = '${CustomerLevel.PRIMARY}'

                            AND `customer_master`.`category_id` != ${ApplicationConstants.CUSTOMER_CATEGORY_BRANCH_ID}
                            AND `customer_master`.`category_id` != ${ApplicationConstants.CUSTOMER_CATEGORY_DEALER_ID}
                            AND `customer_master`.`category_id` != ${ApplicationConstants.CUSTOMER_CATEGORY_DIRECT_ID}



                          """
        Sql sql = new Sql(dataSource)
        return sql.rows(query)
// AND `customer_master`.`category_id` != ${ApplicationConstants.CUSTOMER_CATEGORY_BRANCH_ID}
/*SELECT cc.id AS id, cc.name AS name
                            FROM `customer_territory_sub_area` AS ctsa, customer_master AS cm, customer_category AS cc
                            WHERE ctsa.customer_master_id=cm.id
                            AND ctsa.territory_sub_area_id=${territoryId}
                            AND cm.category_id=cc.id
                            AND cm.customer_level='${customer_level}'
                            AND cm.category_id NOT IN (${branch},${dealer},${pos})
                            GROUP BY cc.name*/
    }



    @Transactional(readOnly = true)
    public Map searchMappingTerritorySubAreaByCustomer(Object params) {
        String query = """
            SELECT
                `territory_sub_area`.id
                , CONCAT(`territory_configuration`.`name`, '-', `territory_sub_area`.`geo_location`, '-', `territory_sub_area`.`para_or_locality`, '-', `territory_sub_area`.`road`) AS `name`
            FROM
                `territory_sub_area`
                INNER JOIN `territory_configuration`
                    ON (`territory_sub_area`.`territory_configuration_id` = `territory_configuration`.`id`)
            WHERE `territory_configuration`.`enterprise_configuration_id` = ${params.enterpriseId}
        """
        Sql sql = new Sql(dataSource)
        List result = sql.rows(query)
        List selectedTerritorySubArea = new ArrayList()
        if (params.employeeId) {
            query = """
                SELECT
                    `territory_sub_area`.id
                    , CONCAT(`territory_configuration`.`name`, '-', `territory_sub_area`.`geo_location`, '-', `territory_sub_area`.`para_or_locality`, '-', `territory_sub_area`.`road`) AS `name`
                FROM `customer_territory_sub_area`
                    INNER JOIN `territory_sub_area` ON ( `customer_territory_sub_area`.`territory_sub_area_id` = `territory_sub_area`.`id`)
                    INNER JOIN `territory_configuration`
                        ON (`territory_sub_area`.`territory_configuration_id` = `territory_configuration`.`id`)
                WHERE `territory_configuration`.`enterprise_configuration_id` = ${params.enterpriseId}
                    AND `customer_territory_sub_area`.`customer_master_id` = ${params.employeeId}
            """
            selectedTerritorySubArea = sql.rows(query)
        }
        return [availableTerritorySubArea: result, selectedTerritorySubArea: selectedTerritorySubArea]
    }

    @Transactional(readOnly = true)
    public Map searchGeoLocationMappingByTerritory(Object params) {
        String query = """
            SELECT
                `territory_sub_area`.id
                , CONCAT(`territory_sub_area`.`geo_location`, '-', `territory_sub_area`.`para_or_locality`, '-', `territory_sub_area`.`road`) AS `name`
            FROM
                `territory_sub_area`
            WHERE `territory_sub_area`.`territory_configuration_id` IN(${params.territoryIds})
        """
        Sql sql = new Sql(dataSource)
        List result = sql.rows(query)
        List selectedTerritorySubArea = new ArrayList()
        if (params.employeeId) {
            query = """
                SELECT
                    `territory_sub_area`.id
                    , CONCAT(`territory_sub_area`.`geo_location`, '-', `territory_sub_area`.`para_or_locality`, '-', `territory_sub_area`.`road`) AS `name`
                FROM `customer_territory_sub_area`
                    INNER JOIN `territory_sub_area` ON ( `customer_territory_sub_area`.`territory_sub_area_id` = `territory_sub_area`.`id`)
                WHERE `territory_sub_area`.`territory_configuration_id` IN(${params.territoryIds})
                    AND `customer_territory_sub_area`.`customer_master_id` = ${params.employeeId}
            """
            selectedTerritorySubArea = sql.rows(query)
        }
        return [availableTerritorySubArea: result, selectedTerritorySubArea: selectedTerritorySubArea]
    }

    @Transactional(readOnly = true)
    public List listTerritorySubAreaByTerritory(Object params) {
        String query = """
            SELECT
                `territory_sub_area`.`id`
                , `territory_sub_area`.`geo_location` AS `geoLocation`
                , IFNULL(`country_info`.`name`, '') AS `country`
                , IFNULL(`division`.`name`, '') AS `division`
                , IFNULL(`district`.`name`, '') AS  `district`
                , IFNULL(`thana_upazila_pouroshova`.`name`, '') AS `thana`
                , IFNULL(`union_info`.`name`, '') AS `union`
                , IFNULL(`territory_sub_area`.`para_or_locality`, '') AS `paraOrLocality`
                , IFNULL(`territory_sub_area`.`road`, '') AS `road`
            FROM
                `territory_sub_area`
                INNER JOIN `territory_configuration`
                    ON (`territory_sub_area`.`territory_configuration_id` = `territory_configuration`.`id`)
                LEFT JOIN `country_info`
                    ON (`territory_sub_area`.`country_info_id` = `country_info`.`id`)
                LEFT JOIN `division`
                    ON (`territory_sub_area`.`division_id` = `division`.`id`)
                LEFT JOIN `district`
                    ON (`territory_sub_area`.`district_id` = `district`.`id`)
                LEFT JOIN `thana_upazila_pouroshova`
                    ON (`territory_sub_area`.`thana_upazila_pouroshova_id` = `thana_upazila_pouroshova`.`id`)
                LEFT JOIN `union_info`
                    ON (`territory_sub_area`.`union_info_id` = `union_info`.`id`)
            WHERE `territory_configuration_id` = ${params.territoryId}
            ORDER BY `territory_sub_area`.`geo_location`
       """
        Sql sql = new Sql(dataSource)
        return sql.rows(query)
    }

    @Transactional(readOnly = true)
    public List fetchTerritorySubArea(String territoryId) {
        String condition = ''
        if (territoryId) {
            condition = """ AND territory_sub_area.`territory_configuration_id` = ${territoryId}"""
        } else {
            condition = """ AND territory_sub_area.`territory_configuration_id` = 0"""
        }
        String query = """SELECT territory_sub_area.`id`,territory_sub_area.`geo_location`,
                            territory_sub_area.`para_or_locality`,territory_sub_area.`road`,
                            territory_configuration.name
                        FROM `territory_sub_area`
                        INNER JOIN territory_configuration ON
                            territory_configuration.id = territory_sub_area.territory_configuration_id
                        WHERE territory_sub_area.`is_active` = TRUE
                        ${condition}
                        """
        Sql sql = new Sql(dataSource)
        return sql.rows(query)

        /*Fetching Thana Union
        SELECT territory_sub_area.`id`,territory_sub_area.`geo_location`, `thana_upazila_pouroshova`.`name` AS TName, `union_info`.`name` AS uName,
    territory_sub_area.`para_or_locality`,territory_sub_area.`road`,
    territory_configuration.name
FROM `territory_sub_area`
INNER JOIN territory_configuration ON
    territory_configuration.id = territory_sub_area.territory_configuration_id
INNER JOIN `thana_upazila_pouroshova`
ON `thana_upazila_pouroshova`.id = territory_sub_area.`thana_upazila_pouroshova_id`
INNER JOIN `union_info`
ON union_info.id = territory_sub_area.`union_info_id`
WHERE territory_sub_area.`is_active` = TRUE
 AND territory_sub_area.`territory_configuration_id` = 1

         */
    }

    @Transactional(readOnly = true)
    public List listTerritorySubAreaByApplicationUser() {
        ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
        try {
            String sql = """
                SELECT DISTINCT
                    `territory_sub_area`.`id`
                    , `territory_sub_area`.`geo_location`
                    , `territory_sub_area`.`para_or_locality`
                    , `territory_sub_area`.`road`
                FROM
                    `customer_territory_sub_area`
                    INNER JOIN `territory_sub_area`
                        ON (`customer_territory_sub_area`.`territory_sub_area_id` = `territory_sub_area`.`id`)
                    INNER JOIN `customer_master`
                        ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`)
                    INNER JOIN `application_user`
                        ON (`application_user`.`customer_master_id` = `customer_master`.`id`)
                WHERE `application_user`.`id` = ${applicationUser.id}
            """
            Sql db = new Sql(dataSource)
            return db.rows(sql)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Map listFlexBoxTerritorySubAreaByTerritory(Object params) {
        String query = """
            SELECT
                `territory_sub_area`.`id`
                ,CONCAT(`territory_sub_area`.`geo_location`,' (',IFNULL(`country_info`.`name`, ''), '->', IFNULL(`division`.`name`, '')
                , '->', IFNULL(`district`.`name`, '')
                , '->', IFNULL(`thana_upazila_pouroshova`.`name`, ''),')') AS name
            FROM
                `territory_sub_area`
                INNER JOIN `territory_configuration`
                    ON (`territory_sub_area`.`territory_configuration_id` = `territory_configuration`.`id`)
                LEFT JOIN `country_info`
                    ON (`territory_sub_area`.`country_info_id` = `country_info`.`id`)
                LEFT JOIN `division`
                    ON (`territory_sub_area`.`division_id` = `division`.`id`)
                LEFT JOIN `district`
                    ON (`territory_sub_area`.`district_id` = `district`.`id`)
                LEFT JOIN `thana_upazila_pouroshova`
                    ON (`territory_sub_area`.`thana_upazila_pouroshova_id` = `thana_upazila_pouroshova`.`id`)
            WHERE `territory_configuration_id` = ${params.territoryId}
            ORDER BY `territory_sub_area`.`geo_location`
       """
        Sql sql = new Sql(dataSource)
        List result = sql.rows(query)
        return [results: result, total: result.size()]
    }

    @Transactional(readOnly = true)
    public List listZoneByApplicationUser() {    // Zone=Thana/Upazila/Pourasava
        ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
        try {
            String sql = """
                SELECT DISTINCT
                    `thana_upazila_pouroshova`.`id`
                    , `thana_upazila_pouroshova`.`name`
                FROM
                    `customer_territory_sub_area`
                    INNER JOIN `territory_sub_area`
                        ON (`customer_territory_sub_area`.`territory_sub_area_id` = `territory_sub_area`.`id`)
                    INNER JOIN `customer_master`
                        ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`)
                    INNER JOIN `thana_upazila_pouroshova`
                        ON (`territory_sub_area`.`thana_upazila_pouroshova_id` = `thana_upazila_pouroshova`.`id`)
                    INNER JOIN `application_user`
                        ON (`customer_master`.`id` = `application_user`.`customer_master_id`)
                WHERE `application_user`.`id` = ${applicationUser.id}
                    AND `customer_master`.`master_type_id` = ${ApplicationConstants.EMPLOYEE_MASTER_TYPE_ID}
            """
            Sql db = new Sql(dataSource)
            return db.rows(sql)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listRegionByApplicationUser() {    // Region=District
        ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
        try {
            String sql = """
                SELECT DISTINCT
                    `district`.`id`
                    , `district`.`name`
                FROM
                    `customer_territory_sub_area`
                    INNER JOIN `territory_sub_area`
                        ON (`customer_territory_sub_area`.`territory_sub_area_id` = `territory_sub_area`.`id`)
                    INNER JOIN `customer_master`
                        ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`)
                    INNER JOIN `district`
                        ON (`territory_sub_area`.`district_id` = `district`.`id`)
                    INNER JOIN `application_user`
                        ON (`customer_master`.`id` = `application_user`.`customer_master_id`)
                WHERE `application_user`.`id` = ${applicationUser.id}
                    AND `customer_master`.`master_type_id` = ${ApplicationConstants.EMPLOYEE_MASTER_TYPE_ID}
            """
            Sql db = new Sql(dataSource)
            return db.rows(sql)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listCSMByApplicationUser() {    // CSM=Division
        ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
        try {
            String sql = """
                SELECT DISTINCT
                    `division`.`id`
                    , `division`.`name`
                FROM
                    `customer_territory_sub_area`
                    INNER JOIN `territory_sub_area`
                        ON (`customer_territory_sub_area`.`territory_sub_area_id` = `territory_sub_area`.`id`)
                    INNER JOIN `customer_master`
                        ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`)
                    INNER JOIN `division`
                        ON (`territory_sub_area`.`division_id` = `division`.`id`)
                    INNER JOIN `application_user`
                        ON (`customer_master`.`id` = `application_user`.`customer_master_id`)
                WHERE `application_user`.`id` = ${applicationUser.id}
                    AND `customer_master`.`master_type_id` = ${ApplicationConstants.EMPLOYEE_MASTER_TYPE_ID}
            """
            Sql db = new Sql(dataSource)
            return db.rows(sql)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
