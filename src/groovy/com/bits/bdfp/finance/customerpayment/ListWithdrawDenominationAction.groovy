package com.bits.bdfp.finance.customerpayment

import com.bits.bdfp.finance.CustomerPaymentService
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 9/19/2016.
 */
@Component("listWithdrawDenominationAction")
class ListWithdrawDenominationAction extends Action{
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerPaymentService customerPaymentService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    @Override
    public Object postCondition(Object params, Object object) {
        return getResultForUI(object)
    }

    public Object execute(Object params, Object object) {
        try{
            init(params)
            List objectList = customerPaymentService.fetchWithdrawDenomination(params)
            List objList = this.wrapListInGridEntityList(objectList, start)
            return objList
        }
        catch (Exception ex) {
            log.error(ex.message)
            return  null
        }
    }


    private List wrapListInGridEntityList(List objList, int start) {
        List instants = []
        int counter = 1;
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = [
                    "${instance.id ? instance.id : ''}",
                    "${instance.note_name? instance.note_name : ''}",
                    "${instance.quantity? instance.quantity : ''}"
            ]
            instants << obj
            counter++
        };
        return instants
    }

    Map getResultForUI(List objList) {
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
