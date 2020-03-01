package com.bits.bdfp.bonus.customerbonusfinishgood

import com.bits.bdfp.bonus.CustomerBonusFinishGoodService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by prianka.adhikary on 10/14/2015.
 */
@Component("checkBonusEligibilityAction")
class CheckBonusEligibilityAction extends Action{
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerBonusFinishGoodService customerBonusFinishGoodService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Map execute(Object params, Object object) {
        try {
            boolean isEligible=false
            Map map= [:]
            List eligibilityList = customerBonusFinishGoodService.checkBonusEligibility(params)
            if (eligibilityList && eligibilityList.size()>0){
                eligibilityList.each{
                    if (it.eligibility == 'Y'){
                        isEligible=true
                        map.put("isEligible",isEligible)

                    }
                }
            }
            map.put("eligibilityList",eligibilityList)
            return map
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }


}
