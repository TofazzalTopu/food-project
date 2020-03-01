package com.bits.bdfp.settings.businessday

import com.bits.bdfp.settings.FinancialYearService
import com.docu.common.Action
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("getBusinessMonthForHolidayAction")
class GetBusinessMonthForHolidayAction extends Action {
    public static final Log log = LogFactory.getLog(GetBusinessMonthForHolidayAction.class)

    @Autowired
    FinancialYearService financialYearService

    public Object preCondition(Object params, Object object) {
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            List list = financialYearService.getBusinessMonthDetailsForHoliday()
            return list
        }
        catch (Exception ex) {
            log.error(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}