package com.bits.bdfp.geolocation

import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class TerritoryConfigurationService extends Service {

    @Autowired
    TerritorySubAreaService territorySubAreaService

    @Autowired
    SpringSecurityService springSecurityService

    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public TerritoryConfiguration create(Object object) {
        try{
            TerritoryConfiguration territoryConfiguration = (TerritoryConfiguration) object.get('territoryConfiguration')
            territoryConfiguration = territoryConfiguration.save(false)
            TerritorySubArea[] territorySubArea = object.get('territorySubArea')
            for (int i = 0; i < territorySubArea.length; i++){
                territorySubAreaService.create(territorySubArea[i])
            }
            return territoryConfiguration
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object object) {
        try{
            Boolean isActive = false
            TerritoryConfiguration territoryConfiguration = (TerritoryConfiguration) object.get('territoryConfiguration')

            TerritorySubArea[] territorySubArea = object.get('territorySubArea')
            for (int i = 0; i < territorySubArea.length; i++){
                if(territorySubArea[i].id){
                    territorySubAreaService.update(territorySubArea[i])
                    if(territorySubArea[i].isActive){
                        isActive = true
                    }
                }else{
                    territorySubAreaService.create(territorySubArea[i])
                    if(territorySubArea[i].isActive){
                        isActive = true
                    }
                }
            }

            TerritorySubArea territorySubAreaCheck = TerritorySubArea.findByTerritoryConfigurationAndIsActive(territoryConfiguration,true)
            if(territorySubAreaCheck){
                isActive = true
            }
            territoryConfiguration.isActive = isActive
            territoryConfiguration.save(false)

            return 1
        }catch (Exception ex){
            log.error(ex.message)
            throw  new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        TerritoryConfiguration territoryConfiguration = (TerritoryConfiguration) object
        territoryConfiguration.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<TerritoryConfiguration> objList = TerritoryConfiguration.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = TerritoryConfiguration.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<TerritoryConfiguration> list() {
        return TerritoryConfiguration.list()
    }

    @Transactional(readOnly = true)
    public TerritoryConfiguration read(Long id) {
        TerritoryConfiguration territoryConfiguration = TerritoryConfiguration.read(id)
        return territoryConfiguration
    }

    @Transactional(readOnly = true)
    public TerritoryConfiguration search(String fieldName, String fieldValue) {
        String query = "from TerritoryConfiguration as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return TerritoryConfiguration.find(query)
    }

    @Transactional(readOnly = true)
    public List searchTerritoryByEnterprise(long enterpriseId) {
        String query = """SELECT `id`, `name`
                          FROM `territory_configuration`
                          WHERE `enterprise_configuration_id` = ${enterpriseId}
                          ORDER BY `name`"""
        Sql sql = new Sql(dataSource)
        return sql.rows(query)
    }
    @Transactional(readOnly = true)
    public List fetchTerritoryByUserId() {

        def user=(ApplicationUser) springSecurityService?.getCurrentUser()

        ApplicationUser applicationUser = (ApplicationUser) user
        String query = """
                        SELECT DISTINCT t.id as id, t.name as name
                        FROM territory_configuration AS t, `customer_territory_sub_area` AS ctsa, territory_sub_area AS tsa
                        WHERE ctsa.customer_master_id=${applicationUser.id}
                        AND tsa.id=ctsa.territory_sub_area_id
                        AND t.id=tsa.territory_configuration_id
                        """
        Sql sql = new Sql(dataSource)
        return sql.rows(query)

/*        SELECT DISTINCT  t.id,t.name AS NAME
        FROM application_user AS au, customer_master AS cm, territory_configuration AS t, `customer_territory_sub_area` AS ctsa, territory_sub_area AS tsa
        WHERE au.id=3
        AND cm.customer_master_id=au.id
        AND ctsa.customer_master_id=au.id
        AND cm.customer_master_id=ctsa.customer_master_id
        AND tsa.id=ctsa.territory_sub_area_id
        AND t.id=tsa.territory_configuration_id*/
    }


    @Transactional(readOnly = true)
    public List fetchDivisionList(String id) {
        String query = """SELECT `id`, `name`
                        FROM `division`
                        WHERE `country_info_id` = ${id}"""
        Sql sql = new Sql(dataSource)
        return sql.rows(query.toString())
    }

    @Transactional(readOnly = true)
    public List fetchDistrictList(String id) {
        String query = """SELECT `id`, `name`
                        FROM `district`
                        WHERE `division_id` = ${id}"""
        Sql sql = new Sql(dataSource)
        return sql.rows(query)
    }

    @Transactional(readOnly = true)
    public List fetchThanaList(String id) {
        String query = """SELECT `id`, `name`
                        FROM `thana_upazila_pouroshova`
                        WHERE `district_id` = ${id}"""
        Sql sql = new Sql(dataSource)
        return sql.rows(query)
    }


    @Transactional(readOnly = true)
    public List fetchUnionList(String id) {
        String query = """SELECT `id`, `name`
                        FROM `union_info`
                        WHERE `thana_upazila_pouroshova_id` = ${id}"""
        Sql sql = new Sql(dataSource)
        return sql.rows(query)
    }
}
