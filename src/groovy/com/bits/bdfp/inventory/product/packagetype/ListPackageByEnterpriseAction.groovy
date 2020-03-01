package com.bits.bdfp.inventory.product.packagetype

import com.bits.bdfp.common.BankService
import com.bits.bdfp.inventory.product.PackageTypeService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by prianka.adhikary on 9/17/2015.
 */
@Component("listPackageByEnterpriseAction")
class ListPackageByEnterpriseAction extends Action{
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    PackageTypeService packageTypeService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService
    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(Long.parseLong(params.id))
            return packageTypeService.findAllByEnterpriseConfiguration(enterpriseConfiguration)
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
