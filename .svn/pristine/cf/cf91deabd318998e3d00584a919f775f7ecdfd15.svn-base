package com.bits.bdfp.finance.customerpayment

import com.bits.bdfp.finance.CustomerPaymentService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by shazadur.rahman on 9/15/2015.
 */
@Component("listSecurityDepositPaymentAction")
class ListSecurityDepositPaymentAction extends Action{

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

            result = customerPaymentService.getListForSecurityDepositPayments(this,params)
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
            obj.id = instance.customerId
            obj.cell = [
                    "${instance.customerId ? instance.customerId : ''}",
                    "${instance.customerName ? instance.customerName : ''}",
                    "${instance.deposited? instance.deposited :0}",
                    "${instance.withdrawn? instance.withdrawn : 0}",
                    "${instance.dateTransaction? instance.dateTransaction : ''}"
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
}
