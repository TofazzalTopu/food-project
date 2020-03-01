package com.bits.bdfp.inventory.product.externalproduct

import com.bits.bdfp.inventory.product.ExternalProductService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdtofazzal.hossain on 1/16/2019.
 */

@Component("readExternalProductAction")
class ReadExternalProductAction extends Action {

    private static final Log log = LogFactory.getLog(this)

    @Autowired
    ExternalProductService externalProductService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService

    @Override
    public Object preCondition(Object params, Object object) {
        return null
    }

    @Override
    public Object execute(Object params, Object object) {
        try {
            ExternalProduct externalProduct = externalProductService.read(Long.parseLong(params.id))
            EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(externalProduct.enterpriseConfiguration.id)
            return [externalProduct: externalProduct, enterpriseConfiguration: enterpriseConfiguration]
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
