package com.bits.bdfp.settings.measureunitconfiguration

import com.bits.bdfp.inventory.product.PackageTypeService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.bits.bdfp.settings.MeasureUnitConfiguration
import com.bits.bdfp.settings.MeasureUnitConfigurationService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by prianka.adhikary on 9/17/2015.
 */
@Component("listMeasurementUnitByEnterpriseAction")
class ListMeasurementUnitByEnterpriseAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    MeasureUnitConfigurationService measureUnitConfigurationService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService
    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(Long.parseLong(params.id))
            return measureUnitConfigurationService.findAllByEnterpriseConfiguration(enterpriseConfiguration)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }
}
