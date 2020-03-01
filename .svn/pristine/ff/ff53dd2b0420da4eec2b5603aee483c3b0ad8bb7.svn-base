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
@Component("listProductByCustomerSalesCommissionForUpdateAction")
class ListProductByCustomerSalesCommissionForUpdateAction extends Action {
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

            List result =  salesCommissionService.listProductForUpdateByCustomer(params)
            List objList = this.wrapListInGridEntityList(result)
            Double scommission = result[0].smcommission
            Double bcommission = result[0].bcommission
            String datefrom = result[0].effective_date_from
            String dateTo =  result[0].effective_date_to
            Map output = [ rows: objList,scommission:scommission,bcommission:bcommission,dateFrom:datefrom,dateTo:dateTo]
            return output;

        }
        catch (Exception ex) {
            log.error(ex.message)
            throw  new RuntimeException(ex.message)
        }
    }
    private List wrapListInGridEntityList(objList) {
        List instants = []
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = [
                    "${instance.id ? instance.id : ''}",
                    "${instance.productid ? instance.productid : ''}",
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
