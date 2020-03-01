package com.bits.bdfp.inventory.warehouse.transferproducts

import com.bits.bdfp.inventory.warehouse.InventoryToInventoryTransferService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by liton.miah on 4/27/2016.
 */
@Component("utilInventoryToInventoryTransferAction")
class UtilInventoryToInventoryTransferAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    InventoryToInventoryTransferService inventoryToInventoryTransferService

    public Object preCondition(Object params, Object object) {

    }

    public Object execute(Object params, Object object) {

    }

    public Object postCondition(Object params, Object object) {
        return null
    }

    public Map getDpInfoByApplicationUser(Long id){
        return inventoryToInventoryTransferService.getDpInfoByApplicationUser(id)
    }

    public List getReceiverDpList(Long id){
        return inventoryToInventoryTransferService.getReceiverDpList(id)
    }

    public Map getDataAndSubInventoryListByInventoryId(Long id){
        return inventoryToInventoryTransferService.getDataAndSubInventoryListByInventoryId(id)
    }

    public List getAllProductListBySubInventoryId(Long id, Long dpId){
        return inventoryToInventoryTransferService.getAllProductListBySubInventoryId(id, dpId)
    }

    public Object getReceiverDpInfo(Long id){
        return inventoryToInventoryTransferService.getReceiverDpInfo(id)
    }

    public Object getReceiverInventoryInfo(Long id){
        return inventoryToInventoryTransferService.getReceiverInventoryInfo(id)
    }
}
