package com.bits.bdfp.settings.measureunitconfiguration

import com.bits.bdfp.settings.MeasureUnitConfiguration
import com.bits.bdfp.settings.MeasureUnitConfigurationService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createMeasureUnitConfigurationAction")
class CreateMeasureUnitConfigurationAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    MeasureUnitConfigurationService measureUnitConfigurationService

    public Object preCondition(Object user, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) user
            MeasureUnitConfiguration measureUnitConfiguration = (MeasureUnitConfiguration) object
            measureUnitConfiguration.userCreated = applicationUser
            measureUnitConfiguration.dateCreated = new Date()
            if (!measureUnitConfiguration.validate()) {
                return null
            }
            return measureUnitConfiguration
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object execute(Object params, Object object) {
        try {
            return measureUnitConfigurationService.create(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}