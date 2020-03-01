package com.bits.bdfp.bill.createbill

import groovy.sql.GroovyRowResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.common.Message
import com.bits.bdfp.bill.CreateBill
import com.bits.bdfp.bill.CreateBillService

@Component("listCreateBillAction")
class ListCreateBillAction extends Action {

    @Autowired
    CreateBillService createBillService

    public Object preCondition(def params, def object) {
        //not need to implement
        return null
    }

    public Object execute(def params, def object) {
        try{
            init(params)
            Map result = [:]
            List objectList = null

            result = createBillService.getAllListForGrid(this, params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            return this.wrapListInGridEntityList(objectList, start)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    public Object findByBill(def params, def object) {
        try{
            init(params)
            Map result = [:]
            List objectList = null

            result = createBillService.getListByBillForGrid(this, params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            return this.wrapListInGridEntityList(objectList, start)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    public Object listByCriteria(def params, def object) {
        try{
            init(params)
            Map result = [:]
            List objectList = null

                                result = createBillService.getListForGrid(this, params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List resultList = this.wrapListInGridEntityListByCriteria(objectList, start)
            return resultList
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    public Object executeCustomerByCode(Object params, Object object) {
        try {
            return createBillService.listCustomerByCode(params)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    /*public List searchCustomerByGeoLocation(Object params, Object object) {
        try {
            return createBillService.searchCustomerByGeoLocation(params)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
*/
    public Object searchBillByCriteria(Object params, Object object) {
        try {
            return createBillService.searchBillByCriteria(params)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

   Map getResultForUI(List objList) {
        int pageCount = 1
        if (total > resultPerPage) {
            pageCount = Math.ceil(total / resultPerPage)
        }
        Map output = [page: pageNum, total: pageCount, records: total, rows: objList]
        return output;
   }

   private Object wrapListInGridEntityList(objList, start) {
        try{
            List instants = []
            int counter = start + 1;
            objList.each { instance ->
                GridEntity obj = new GridEntity()
                obj.id = instance.id
                obj.cell = ["${counter}",
                          "${instance.id ? instance.id : ''}",
                            
                            "${instance.invoiceNumber? instance.invoiceNumber : ''}",
                            //"${instance.customerName? instance.customerName : ''}",
                            //"${instance.territory? instance.territory : ''}",
                            //"${instance.territoryGeoLocation? instance.territoryGeoLocation : ''}",
                           // "${instance.customerId? instance.customerId : ''}",
                            "${instance.billNumber? instance.billNumber : ''}",
                            "${instance.billGenerationDate? instance.billGenerationDate : ''}",
                            "${instance.purchaseOrderNumber? instance.purchaseOrderNumber : ''}",
                            "${instance.purchaseOrderDate? instance.purchaseOrderDate : ''}",
                            "${instance.vatChallanNumber? instance.vatChallanNumber : ''}",
                            "${instance.vatChallanDate? instance.vatChallanDate : ''}",
                          "${instance.deliveryDate? instance.deliveryDate : ''}",
                          "${instance.receivableAmount? instance.receivableAmount : ''}"


                            //"${instance.customerCategory? instance.customerCategory : ''}",
                            //"${instance.receivableAmount? instance.receivableAmount : ''}"
                            ]
                instants << obj
                counter++
            }
            return instants
        } catch (Exception ex){
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
   }

    private List wrapListInGridEntityListByCriteria(objList, start) {
        try{
            List instants = []
            int counter = start + 1;
            objList.each { instance ->
                GridEntity obj = new GridEntity()
                obj.id = instance.id
                obj.cell = ["${counter}",
                            "${instance.id ? instance.id : ''}",

                            "${instance.billNumber? instance.billNumber : ''}",
                            "${instance.billGenerationDate? instance.billGenerationDate : ''}",
                            "${instance.receivableAmount? instance.receivableAmount : ''}"
                ]
                instants << obj
                counter++
            }
            return instants
        } catch (Exception ex){
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

   public Object postCondition(Object params, Object object) {
        return getResultForUI(object)
   }
}