package com.bits.bdfp.inventory.sales

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class DistributionPointTerritorySubAreaService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public DistributionPointTerritorySubArea create(Object object) {
        DistributionPointTerritorySubArea distributionPointTerritorySubArea = (DistributionPointTerritorySubArea) object
        if (distributionPointTerritorySubArea.save(false)) {
            return distributionPointTerritorySubArea
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        DistributionPointTerritorySubArea distributionPointTerritorySubArea = (DistributionPointTerritorySubArea) object
        if (distributionPointTerritorySubArea.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        DistributionPointTerritorySubArea distributionPointTerritorySubArea = (DistributionPointTerritorySubArea) object
        distributionPointTerritorySubArea.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<DistributionPointTerritorySubArea> objList = DistributionPointTerritorySubArea.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = DistributionPointTerritorySubArea.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<DistributionPointTerritorySubArea> list() {
        return DistributionPointTerritorySubArea.list()
    }

    @Transactional(readOnly = true)
    public DistributionPointTerritorySubArea read(Long id) {
        return DistributionPointTerritorySubArea.read(id)
    }

    @Transactional(readOnly = true)
    public DistributionPointTerritorySubArea search(String fieldName, String fieldValue) {
        String query = "from DistributionPointTerritorySubArea as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return DistributionPointTerritorySubArea.find(query)
    }
}
