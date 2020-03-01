package com.bits.bdfp.inventory.sales

import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.geolocation.TerritoryConfigurationService
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class DistributionPointWarehouseService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql
    TerritoryConfigurationService territoryConfigurationService

    @Transactional
    public DistributionPointWarehouse create(Object object) {
        DistributionPointWarehouse distributionPointWarehouse = (DistributionPointWarehouse) object
        if (distributionPointWarehouse.save(false)) {
            return distributionPointWarehouse
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        DistributionPointWarehouse distributionPointWarehouse = (DistributionPointWarehouse) object
        if (distributionPointWarehouse.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        DistributionPointWarehouse distributionPointWarehouse = (DistributionPointWarehouse) object
        distributionPointWarehouse.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<DistributionPointWarehouse> objList = DistributionPointWarehouse.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = DistributionPointWarehouse.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<DistributionPointWarehouse> list() {
        return DistributionPointWarehouse.list()
    }

    @Transactional(readOnly = true)
    public DistributionPointWarehouse read(Long id) {
        return DistributionPointWarehouse.read(id)
    }

    @Transactional(readOnly = true)
    public DistributionPointWarehouse search(String fieldName, String fieldValue) {
        String query = "from DistributionPointWarehouse as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return DistributionPointWarehouse.find(query)
    }
    @Transactional(readOnly = true)
    public DistributionPointWarehouse getWareHouseByDistributionPoint(DistributionPoint distributionPoint) {
        return DistributionPointWarehouse.findByDistributionPoint(distributionPoint)
    }

    @Transactional(readOnly = true)
    public TerritoryConfiguration territoryConfigurationByDistribution(DistributionPoint distributionPoint) {
        TerritoryConfiguration territoryConfiguration=null
        sql=new Sql(dataSource)
        String strSql="""SELECT territory_configuration.id
                            FROM
                            distribution_point_territory_sub_area
                            INNER JOIN
                            territory_sub_area
                            ON
                            territory_sub_area.id=distribution_point_territory_sub_area.territory_sub_area_id
                            INNER JOIN
                            territory_configuration
                            ON
                            territory_configuration.id=territory_sub_area.territory_configuration_id
                            WHERE distribution_point_territory_sub_area.distribution_point_id=${distributionPoint.id}"""
        List list=sql.rows(strSql)
        if(list && list.size()>0){
           territoryConfiguration=territoryConfigurationService.read(list[0].id)
        }
        return territoryConfiguration

    }
}
