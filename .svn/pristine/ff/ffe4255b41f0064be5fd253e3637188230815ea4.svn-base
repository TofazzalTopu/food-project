package com.bits.bdfp.customer.customershippingaddress

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.CustomerShippingAddressService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("listCustomerShippingAddressAction")
class ListCustomerShippingAddressAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerShippingAddressService customerShippingAddressService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            init(params)
            Map result = [:]
            List objectList = null

            result = customerShippingAddressService.getListForGrid(this, params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List objList = this.wrapListInGridEntityList(objectList, start)
            return objList
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

    private List wrapListInGridEntityList(objList, start) {
        List instants = []
        int counter = start + 1;
        try {
            objList.each { instance ->
                CustomerMaster customerMaster = CustomerMaster.read(instance.customerMaster.id)
                GridEntity obj = new GridEntity()
                obj.id = instance.id
                obj.cell = ["${instance.id ? instance.id : ''}",
                            "${instance.customerMaster ? customerMaster.name : ''}",
                            "${instance.address ? instance.address : ''}"
//                                "${counter}"
//                  "${instance.isActive? instance.isActive : ''}",
                ]
                instants << obj
                counter++
            }
            return instants
        }
        catch (Exception e) {
            log.error(e.message)
            throw new RuntimeException(e.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return getResultForUI(object)
    }
}
