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
@Component("listAllProductByAction")
class ListAllProductByAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    SalesCommissionService salesCommissionService
    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
//            List objectList = []
//            objectList = salesCommissionService.listProductForUpdateByCustomer(Long.parseLong(params.cId))
//            return objectList;

            List result =  salesCommissionService.fetchDataProductAction()
            return result;

        }
        catch (Exception ex) {
            log.error(ex.message)
            throw  new RuntimeException(ex.message)
        }
    }
    private List wrapListInGridEntityList(objList, start) {
        List instants = []
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = [
                    "${instance.id ? instance.id : ''}",
                    "${instance.pcode ? instance.pcode : ''}",
                    "${instance.pname ? instance.pname : ''}"
            ]
            instants << obj
        };
        return instants
    }



    public Object postCondition(Object params, Object object) {
        return null
    }

}
