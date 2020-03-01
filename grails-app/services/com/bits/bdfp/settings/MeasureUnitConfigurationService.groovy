package com.bits.bdfp.settings

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class MeasureUnitConfigurationService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public MeasureUnitConfiguration create(Object object) {
        MeasureUnitConfiguration measureUnitConfiguration = (MeasureUnitConfiguration) object
        if (measureUnitConfiguration.save(false)) {
            return measureUnitConfiguration
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        MeasureUnitConfiguration measureUnitConfiguration = (MeasureUnitConfiguration) object
        if (measureUnitConfiguration.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        MeasureUnitConfiguration measureUnitConfiguration = (MeasureUnitConfiguration) object
        measureUnitConfiguration.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<MeasureUnitConfiguration> objList = MeasureUnitConfiguration.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = MeasureUnitConfiguration.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<MeasureUnitConfiguration> list() {
        return MeasureUnitConfiguration.list()
    }
    @Transactional(readOnly = true)
    public List<MeasureUnitConfiguration> findAllByEnterpriseConfiguration(EnterpriseConfiguration enterpriseConfiguration) {
        return MeasureUnitConfiguration.findAllByEnterpriseConfiguration(enterpriseConfiguration)
    }

    @Transactional(readOnly = true)
    public MeasureUnitConfiguration read(Long id) {
        return MeasureUnitConfiguration.read(id)
    }

    @Transactional(readOnly = true)
    public MeasureUnitConfiguration search(String fieldName, String fieldValue) {
        String query = "from MeasureUnitConfiguration as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return MeasureUnitConfiguration.find(query)
    }
}
