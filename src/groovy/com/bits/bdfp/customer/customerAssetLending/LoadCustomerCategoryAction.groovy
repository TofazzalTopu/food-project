package com.bits.bdfp.customer.customerAssetLending

import com.bits.bdfp.customer.CustomerAssetLendingAndRecoveryService
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

@Component("loadCustomerCategoryAction")
public class LoadCustomerCategoryAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerAssetLendingAndRecoveryService customerAssetLendingAndRecoveryService


    @Override
    public Object preCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    public Object postCondition(Object params, Object object) {
    }

    @Override
    public Object execute( Object params,Object object) {
        try {
           return customerAssetLendingAndRecoveryService.loadCustomerCategoryList(params)

        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }


    }



}
