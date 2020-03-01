package com.bits.bdfp.inventory.product.externalproductstock

import com.bits.bdfp.inventory.product.ExternalProductStockService
import com.bits.bdfp.inventory.product.externalproduct.ExternalProductStock
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdtofazzal.hossain on 1/16/2019.
 */
@Component('readExternalProductStockAction')
class ReadExternalProductStockAction extends Action {

    private static final Log log = LogFactory.getLog(this)

    @Autowired
    ExternalProductStockService externalProductStockService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService

    @Override
    public Object preCondition(Object params, Object object) {
        return null
    }

    @Override
    public Object execute(Object params, Object object) {
        try {
            ExternalProductStock externalProductStock = externalProductStockService.read(Long.parseLong(params.id))
            return [externalProductStock: externalProductStock]
        } catch (Exception e) {
            log.error(e.message)
            return null
        }
    }

    @Override
    public Object postCondition(Object params, Object object) {
        return null
    }
}
