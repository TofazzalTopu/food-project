package com.bits.bdfp.dashboard


import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.reports.SearchReportInfoService
import com.bits.bdfp.dashboard.SearchDashboardInfoService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by sarahafreen.orny on 4/25/2016.
 */
@Component("searchDashboardInfoAction")
class SearchDashboardInfoAction {

        private static final Log log = LogFactory.getLog(this)
        @Autowired
        SearchReportInfoService searchReportInfoService

        @Autowired
        SearchDashboardInfoService searchDashboardInfoService

    public Object preCondition(Object user, Object object) {

            return null
    }



    public Object postCondition(Object params, Object object) {
        return null
    }

    public List salesVsCollection() {

        try {
            return searchDashboardInfoService.salesVsCollection()
        }catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public List salesVsCollectionClaim() {

        try {
            return searchDashboardInfoService.salesVsCollectionclaim()
        }catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public List increaseDecreaseSales() {

        try {
            return searchDashboardInfoService.increasedecreasesales()
        }catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public List projectedSales() {

        try {
            return searchDashboardInfoService.projectedsales()
        }catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public List targetvsAchievement() {

        try {
            return searchDashboardInfoService.targetvsachievement()
        }catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }


}

