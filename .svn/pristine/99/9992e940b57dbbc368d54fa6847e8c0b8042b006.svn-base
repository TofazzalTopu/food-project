package com.bits.bdfp.inventory.warehouse.miscellaneoustransactions

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.sales.MarketReturn
import com.bits.bdfp.inventory.warehouse.MiscellaneousTransactionsService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by liton.miah on 4/27/2016.
 */
@Component("utilMiscellaneousTransactionsAction")
class UtilMiscellaneousTransactionsAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    MiscellaneousTransactionsService miscellaneousTransactionsService

    public Object preCondition(Object params, Object object) {

    }

    public Object execute(Object params, Object object) {

    }

    public Object postCondition(Object params, Object object) {
        return null
    }

    public List getDpListByApplicationUser(Long id){
        return miscellaneousTransactionsService.getDpListByApplicationUser(id)
    }

    public List getFactoryDpListByApplicationUser(Long id){
        return miscellaneousTransactionsService.getFactoryDpListByApplicationUser(id)
    }

    public List getCustomerListBySelectedDp(Long id, String key){
        return miscellaneousTransactionsService.getCustomerListBySelectedDp(id, key)
    }

    public List getAllCustomerListByKey(String key){
        return miscellaneousTransactionsService.getAllCustomerListByKey(key)
    }

    public CustomerMaster getCustomerCodeById(Long customerId){
        return miscellaneousTransactionsService.getCustomerCodeById(customerId)
    }

    public List<MarketReturn> getMrListByCustomerId(Long customerId){
        return miscellaneousTransactionsService.getMrListByCustomerId(customerId)
    }

    public List getMarketReturnSummaryByMrId(Long mrId){
        return miscellaneousTransactionsService.getMarketReturnSummaryByMrId(mrId)
    }

    public List getInventoryListByDp(Long dpId){
        return miscellaneousTransactionsService.getInventoryListByDp(dpId)
    }

    public List getSubInventoryListByInventory(Long inventoryId){
        return miscellaneousTransactionsService.getSubInventoryListByInventory(inventoryId)
    }

    public List getProductListBySubInventory(Long dpId, Long subInventoryId, String key){
        return miscellaneousTransactionsService.getProductListBySubInventory(dpId,subInventoryId, key)
    }
}
