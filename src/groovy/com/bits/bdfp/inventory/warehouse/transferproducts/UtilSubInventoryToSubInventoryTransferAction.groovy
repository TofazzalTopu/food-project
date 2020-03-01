package com.bits.bdfp.inventory.warehouse.transferproducts

import com.bits.bdfp.inventory.warehouse.SubInventoryToSubInventoryTransferService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by liton.miah on 4/27/2016.
 */
@Component("utilSubInventoryToSubInventoryTransferAction")
class UtilSubInventoryToSubInventoryTransferAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    SubInventoryToSubInventoryTransferService subInventoryToSubInventoryTransferService

    public Object preCondition(Object params, Object object) {

    }

    public Object execute(Object params, Object object) {

    }

    public Object postCondition(Object params, Object object) {
        return null
    }

    public List getInventoryListByUserId(Long id){
        return subInventoryToSubInventoryTransferService.getInventoryByUserId(id)
    }

    public List getDistributionListByUserId(Long id){
        return subInventoryToSubInventoryTransferService.fetchDistributionPointList(id)
    }

    public List getSubInventoryListByInventoryId(Long id){
        return subInventoryToSubInventoryTransferService.getSubInventoryListByInventoryId(id)
    }

    public List getSubInventoryToListByInventoryId(Long id,Long inventoryId, Object params){
        return subInventoryToSubInventoryTransferService.getSubInventoryToListByInventoryId(id,inventoryId,params)
    }

    public List getAllProductListBySubInventoryId(Long id, Long dpId){
        return subInventoryToSubInventoryTransferService.getAllProductListBySubInventoryId(id, dpId)
    }
}
