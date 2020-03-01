package com.bits.bdfp.setup.salestarget.dailysalestargetfinishproduct

import com.bits.bdfp.setup.salestarget.DailySalesTargetFinishProduct
import com.bits.bdfp.setup.salestarget.DailySalesTargetFinishProductService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 4/10/16.
 */
@Component("listProductForDailySalesTargetAction")
class ListProductForDailySalesTargetAction extends Action {

    @Autowired
    DailySalesTargetFinishProductService dailySalesTargetFinishProductService

    public Object preCondition(def params, def object) {
        //not need to implement
        return null
    }

    public Object execute(def params, def object) {
        try{
            return dailySalesTargetFinishProductService.listProductForDailySalesTarget(params)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}