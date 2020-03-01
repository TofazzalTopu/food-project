package com.bits.bdfp.setup.salestarget.dailysalestargetfinishproduct

import com.docu.commons.ObjectUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.setup.salestarget.DailySalesTargetFinishProduct
import com.bits.bdfp.setup.salestarget.DailySalesTargetFinishProductService
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService


@Component("updateDailySalesTargetFinishProductAction")
class UpdateDailySalesTargetFinishProductAction extends Action {
    public static final Log log = LogFactory.getLog(UpdateDailySalesTargetFinishProductAction.class)
    private final String MESSAGE_HEADER = 'Update Daily Sales Target Finish Product'
    private final String MESSAGE_SUCCESS = 'Daily Sales Target Finish Product Updated Successfully'

    @Autowired
    DailySalesTargetFinishProductService dailySalesTargetFinishProductService
    
    @Autowired
    SpringSecurityService springSecurityService
    

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            List<DailySalesTargetFinishProduct> dailySalesTargetFinishProductList = ObjectUtil.instantiateObjects(params.items, DailySalesTargetFinishProduct.class)
            int dayCount = dailySalesTargetFinishProductList.size()
            if(dayCount <= 0){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "No Daily Sales Target Available")
            }
            ApplicationUser applicationUser = (ApplicationUser) springSecurityService?.getCurrentUser()
            dailySalesTargetFinishProductList.each { dailySalesTargetFinishProduct->
                 dailySalesTargetFinishProduct.userUpdated = applicationUser
            }
            
            dailySalesTargetFinishProductService.updateMonthlyTarget(dailySalesTargetFinishProductList)
            return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}
