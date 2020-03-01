package com.bits.bdfp.inventory.sales.distributionpointwarehouse

import com.bits.bdfp.inventory.sales.DistributionPointWarehouse
import com.bits.bdfp.inventory.sales.DistributionPointWarehouseService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateDistributionPointWarehouseAction")
class UpdateDistributionPointWarehouseAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    DistributionPointWarehouseService distributionPointWarehouseService

    public Object preCondition(Object params, Object object) {
        DistributionPointWarehouse distributionPointWarehouse = DistributionPointWarehouse.read(Long.parseLong(params?.id?.toString()))
        distributionPointWarehouse.properties = params
        if (!distributionPointWarehouse.validate()) {
            return null
        }
        return distributionPointWarehouse
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return distributionPointWarehouseService.update(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }
}
