package com.bits.bdfp.inventory.setup.salescommission

import com.bits.bdfp.inventory.setup.SalesCommissionService
import com.docu.common.Action
import com.docu.commons.CommonConstants
import groovy.sql.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * Created by mddelower.hossain on 1/27/2016.
 */

    @Component("fetchDataForDistributionPointDropDownAction")
    class FetchDataForDistributionPointDropDownAction extends Action {

        @Autowired
        SalesCommissionService salesCommissionService

         Object preCondition(def Object object1,def Object object2) {
            return null
        }

         Object execute(Object params, Object object2) {
            try {
                List list
                list = salesCommissionService.fetchDataForDistributionPointDropDownAction(params.id)
                return list
            }catch(Exception ex){
                log.error(ex.message)
                throw new RuntimeException(ex.message)
            }
        }

        Object fetchUserTerritoryList(Object params, Object object2) {
            try {
                List list
                list = salesCommissionService.fetchUserTerritoryList(params.id)
                return list
            }catch(Exception ex){
                log.error(ex.message)
                throw new RuntimeException(ex.message)
            }
        }

        Object fetchDistributionPointList(Object params, Object object2) {
            try {
                List list
                list = salesCommissionService.fetchDistributionPointList()
                return list
            }catch(Exception ex){
                log.error(ex.message)
                throw new RuntimeException(ex.message)
            }
        }
         Object postCondition(def Object object1,def Object object2) {
            return null
        }



}
