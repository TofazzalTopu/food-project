package com.bits.bdfp.customer.customerAssetLending

import com.bits.bdfp.customer.CustomerAssetLendingAndRecoveryService
import com.bits.bdfp.customer.CustomerCategoryService
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.common.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by depok.chakma on 8/9/2016.
 */

@Component("listCustomerAssetLendingAction")
public class ListCustomerAssetLendingAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerAssetLendingAndRecoveryService customerAssetLendingAndRecoveryService


    @Override
    public Object preCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    public Object postCondition(def params, def object) {
        return getResultForUI(params)
    }

    @Override
    public Object execute( def params, def object) {
        try {
            init(params)
            params.put('appId', object.id)
            List objectList = null

            objectList = customerAssetLendingAndRecoveryService.list(params)
            total = objectList.size()
            return this.wrapListInGridEntityListByCustomerId(objectList, start)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }


    }

    public Object executeCustomerByCode(Object params, Object object) {

        try {
            return customerAssetLendingAndRecoveryService.listCustomerByCode(params)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    private Object wrapListInGridEntityListByCustomerId(objList, start) {
        try {
            List instants = []
            int counter = start + 1;
            objList.each { instance ->
                GridEntity obj = new GridEntity()
                obj.id = instance.id
                obj.cell = ["${instance.id ? '': ''}",
                            "${instance.asset_name? instance.asset_name : ''}",
                            "${instance.lending_date ? instance.lending_date : ''}",
                            "${instance.model_number ? instance.model_number : ''}",
                            "${instance.asset_cost ? instance.asset_cost : '0.0'}"
                ]
                instants << obj
                counter++
            }
            return instants
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
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
    public Object getNetReceivable(Object params){
        return customerAssetLendingAndRecoveryService.getNetReceivable(params)
    }

}
