package com.bits.bdfp.inventory.retailorder.consolidateretailorder

import com.bits.bdfp.inventory.retailorder.RetailOrderService
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 1/18/16.
 */
@Component("listSecondaryOrderDetailsFromRetailOrderAction")
class ListSecondaryOrderDetailsFromRetailOrderAction extends Action {

    @Autowired
    RetailOrderService retailOrderService

    Message message = null

    public Object preCondition(Object params, Object object) {
        return null
    }

    public Object postCondition(Object params, Object object) {
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            init(params)
            String[] retailOrderIdList = params.retailOrderIds.split(',')
            List objectList = null

            List result = retailOrderService.listSecondaryDetailsFromRetailOrders(retailOrderIdList, params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List objList = this.wrapListInGridEntityList(result, 0)
            return [page: 1, total: 1, records: objList.size(), rows: objList]
        }
        catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("PrimaryDemandOrder", Message.ERROR, "Exception-${ex.message}")
        }
    }

    private List wrapListInGridEntityList(objList, start) {
        List instants = []
        int counter = start + 1;
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = [ "${instance.id ? instance.id : ''}",
                    "${instance.productCode ? instance.productCode : ''}",
                    "${instance.productName ? instance.productName : ''}",
                    "${instance.quantity ? instance.quantity : '0'}",
                    "${instance.rate ? instance.rate : '0'}",
                    "${instance.quantity * instance.rate}"
            ]
            instants << obj
            counter++
        };
        return instants
    }

}
