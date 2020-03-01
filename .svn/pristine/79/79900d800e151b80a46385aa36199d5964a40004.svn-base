package com.bits.bdfp.inventory.demandorder.primarydemandorder

import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetailsService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.common.GridEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 9/16/2015.
 */
@Component("generatePrimaryOrderFromSecondaryOrderAction")
class GeneratePrimaryOrderFromSecondaryOrderAction extends Action {

    @Autowired
    SecondaryDemandOrderDetailsService secondaryDemandOrderDetailsService

    Message message = null

    public Object preCondition(Object params, Object object) {
        return null
    }

    public Object postCondition(Object params, Object object) {
        return getResultForUI(object)
    }

    public Object execute(Object params, Object object) {
        try {
            init(params)
            Map result = [:]
            String[] list = params.ids.split(',')
            List objectList = null

            result = secondaryDemandOrderDetailsService.listSecondaryDetails(list, params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List objList = this.wrapListInGridEntityList(objectList, start)
            result = this.postCondition(null, objList)
            return result
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

//    private List wrapListInGridEntityList(objList, start) {
//        List instants = []
//        int counter = start + 1
//        int size = objList.size()
//
//        int finishProductId = objList[0].finish_product_id
//        String orderId = objList[0].order_no
//        int id = objList[0].id
//        String name = objList[0].name
//        String customerName = objList[0].customer_name
//        String customerId = objList[0].customer_id
//        Float rate = objList[0].rate
//        Float amount = 0
//        Float quantity = 0
//
//        objList.each { instance ->
//            if (finishProductId == instance.finish_product_id && orderId == instance.order_no) {
//                amount = amount + instance.amount
//                quantity = quantity + instance.quantity
//                id = instance.id
//            } else {
//                GridEntity obj = new GridEntity()
//                obj.id = id
//                obj.cell = ["${finishProductId ? finishProductId : ''}",
//                            "${name ? name : ''}",
//                            "${quantity ? quantity : '0'}",
//                            "${rate ? rate : '0'}",
//                            "${Float.parseFloat(quantity) * Float.parseFloat(rate)}"
//                ]
//                instants << obj
//                finishProductId = instance.finish_product_id
//                orderId = instance.order_no
//                name = instance.name
//                id = instance.id
//                rate = instance.rate
//                amount = instance.amount
//                quantity = instance.quantity
//                customerName = instance.customer_name
//                customerId = instance.customer_id
//            }
//            if (counter == size) {
//                GridEntity obj = new GridEntity()
//                obj.id = instance.id
//                obj.cell = ["${finishProductId ? finishProductId : ''}",
//                            "${customerId ? customerId : ''}",
//                            "${orderId ? orderId : ''}",
//                            "${name ? name : ''}",
//                            "${customerName ? customerName : ''}",
//                            "${quantity ? quantity : ''}",
//                            "${rate ? rate : ''}",
//                            "${amount ? amount : ''}"
//                ]
//                instants << obj
//            }
//            counter++
//        };
//        return instants
//    }

    private List wrapListInGridEntityList(objList, start) {
        try{
            List instants = []
            int counter = start + 1;
            objList.each { instance ->
                GridEntity obj = new GridEntity()
                obj.id = instance.id
                obj.cell = [ "${instance.id ? instance.id : ''}",
                        "${instance.finish_product_id ? instance.finish_product_id : ''}",
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
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    Map getResultForUI(List objList) {
        int pageCount = 1
        if (resultPerPage < 0) {
            pageCount = 1
            resultPerPage = total
        } else {
            if (total > resultPerPage) {
                pageCount = Math.ceil(total / resultPerPage)
            }
        }
        Map output = [page: pageNum, total: pageCount, records: total, rows: objList]
        return output;
    }
}
