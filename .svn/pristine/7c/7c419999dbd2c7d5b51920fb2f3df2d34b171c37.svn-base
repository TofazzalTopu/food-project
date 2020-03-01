package com.bits.bdfp.inventory.warehouse.transferproducts

import com.bits.bdfp.inventory.warehouse.InventoryToInventoryTransferService
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by prianka.adhikary on 9/21/2015.
 */
@Component("listReceiveInventoryToInventoryTransferAction")
class ListReceiveInventoryToInventoryTransferAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    InventoryToInventoryTransferService inventoryToInventoryTransferService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try{
            init(params)
            Map result = [:]
            List objectList = null
            ApplicationUser applicationUser = (ApplicationUser) object

            result = inventoryToInventoryTransferService.getListTransferStatusGrid(this, params,applicationUser)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List objList = this.wrapListInGridEntityList(objectList, start)
            result = this.postCondition(objList, null)
            return result
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    private List wrapListInGridEntityList(objList, start) {
        List instants = []
        int counter = start + 1;
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${counter}",
                        "${instance.id ? instance.id : ''}",
                        "${instance.sender_dp_id ? instance.sender_dp_id : ''}",
                        "${instance.sender_dp_name? instance.sender_dp_name : ''}",
                        "${instance.sender_inventory_id? instance.sender_inventory_id : ''}",
                        "${instance.sender_inventory_name? instance.sender_inventory_name : ''}",
                        "${instance.sender_sub_inventory_id? instance.sender_sub_inventory_id : ''}",
                        "${instance.sender_sub_inventory_name? instance.sender_sub_inventory_name : ''}",
                        "${instance.batch? instance.batch : ''}",
                        "${instance.transfer_product_id? instance.transfer_product_id : ''}",
                        "${instance.transfer_product_name? instance.transfer_product_name : ''}",
                        "${instance.transfer_qty? instance.transfer_qty : ''}",
                        "${instance.unit_price? instance.unit_price : ''}",
                        "${instance.transfer_challan_number? instance.transfer_challan_number : ''}",
                        "${instance.transfer_date? instance.transfer_date : ''}",
                        "${instance.receiver_dp_id? instance.receiver_dp_id : ''}",
                        "${instance.receiver_dp_name? instance.receiver_dp_name : ''}",
                        "${instance.receiver_inventory_id? instance.receiver_inventory_id : ''}",
                        "${instance.receiver_inventory_name? instance.receiver_inventory_name : ''}"
            ]
            instants << obj
            counter++
        };
        return instants
    }

    public Object postCondition(Object objList, Object object) {
        int pageCount = 1
        if (resultPerPage < 0) {
            pageCount = 1
            resultPerPage=total
        }
        else {
            if (total > resultPerPage) {
                pageCount = Math.ceil(total / resultPerPage)
            }
        }
        Map output = [page: pageNum, total: pageCount, records: total, rows: objList]
        return output;
    }

}
