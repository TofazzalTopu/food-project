package com.bits.bdfp.inventory.setup.salescommission

import com.bits.bdfp.inventory.setup.SalesCommissionService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mddelower.hossain on 2/8/2016.
 */
@Component("listCustomerSalesCommissionAction")
class ListCustomerSalesCommissionAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    SalesCommissionService salesCommissionService
    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            List result = salesCommissionService.listCustomerByDP(Long.parseLong(params.dpId))
            List objList = this.wrapListInGridEntityList(result, start)
            Map output = [page: 1, total: result.size(), records: result.size(), rows: objList]
            return output;
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw  new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }

    private List wrapListInGridEntityList(objList, start) {
        List instants = []
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.customer_id
            obj.cell = [
                "${instance.customer_id ? instance.customer_id : ''}",
                "${instance.customer_name ? instance.customer_name : ''}",
                "${instance.code ? instance.code : ''}",
                "${instance.legacy_id ? instance.legacy_id : ''}"
            ]
            instants << obj
        };
        return instants
    }
}
