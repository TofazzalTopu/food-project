package com.bits.bdfp.finance.customerpayment

import com.bits.bdfp.finance.CustomerPaymentService
import com.bits.bdfp.inventory.demandorder.ProcessOrderService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by shazadur.rahman on 9/15/2015.
 */
@Component("listDepositCashPaymentAction")
class ListDepositCashPaymentAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerPaymentService customerPaymentService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try{
            init(params)
            Map result = [:]
            List objectList = null

            result = customerPaymentService.getListForCashDepositPayments(this,params)
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
            return  null
        }
    }


    private List wrapListInGridEntityList(objList, start) {
        List instants = []
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = [
                        "${instance.id ? instance.id : ''}",
                        "${instance.date_transaction ? instance.date_transaction : ''}",
                        "${instance.name? instance.name : ''}",
                        "${instance.mr_no? instance.mr_no : ''}",
                        "${instance.amount? instance.amount : 0}",
                        "${instance."1000"? instance."1000" : 0}",
                        "${instance."500"? instance."500" : 0}",
                        "${instance."100"? instance."100" : 0}",
                        "${instance."50"? instance."50" : 0}",
                        "${instance."20"? instance."20" : 0}",
                        "${instance."10"? instance."10" : 0}",
                        "${instance."5"? instance."5" : 0}",
                        "${instance."2"? instance."2" : 0}",
                        "${instance."1"? instance."1" : 0}",
                        "${instance."0.5"? instance."0.5" : 0}",
            ]
            instants << obj
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

    public Object getListForCashDepositPaymentsTotal(Object params, Object object){
        try{
            init(params)
            Map result = [:]
            List objectList = null

            result = customerPaymentService.getListForCashDepositPaymentsTotal(this,params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List objList = this.wrapListInGridEntityDepositList(objectList, start)
            result = this.postConditionDeposit(objList, null)
            return result
        }
        catch (Exception ex) {
            log.error(ex.message)
            return  null
        }
    }

    public Object postConditionDeposit(Object objList, Object object) {
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

    private List wrapListInGridEntityDepositList(objList, start) {
        List instants = []
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = [
                    "${instance.id ? instance.id : ''}",
                    "${instance.date_transaction ? instance.date_transaction : ''}",
                    "${instance.name? instance.name : ''}",
                    "${instance.mr_no? instance.mr_no : ''}",
                    "${instance.amount? instance.amount : 0}"
            ]
            instants << obj
        };
        return instants
    }
}
