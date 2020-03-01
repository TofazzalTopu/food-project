package com.bits.bdfp.setup.salestarget.monthlysalestargetbyamount

import com.bits.bdfp.setup.salestarget.MonthlySalesTargetByAmountService
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 3/21/16.
 */
@Component("listSubordinateMonthLySalesTargetAction")
class ListSubordinateMonthLySalesTargetAction extends Action {

    @Autowired
    MonthlySalesTargetByAmountService monthlySalesTargetByAmountService

    public Object preCondition(def params, def object) {
        //not need to implement
        return null
    }

    public Object execute(def params, def object) {
        try{
            init(params)
            List result = monthlySalesTargetByAmountService.listSubordinateMonthLySalesTarget(params)
            if (result) { // in case: normal list
                total = result.size()
            }
            return this.wrapListInGridEntityList(result)
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

    private Object wrapListInGridEntityList(objList) {
        try{
            List instants = []
            objList.each { instance ->
                GridEntity obj = new GridEntity()
                obj.id = instance.id
                obj.cell = ["${instance.id ? instance.id : ''}",

                        "${instance.code? instance.code : ''}",
                        "${instance.name? instance.name : ''}",
                        "${instance.jan? instance.jan : '0'}",
                        "${instance.feb? instance.feb : '0'}",
                        "${instance.mar? instance.mar : '0'}",
                        "${instance.apr? instance.apr : '0'}",
                        "${instance.may? instance.may : '0'}",
                        "${instance.jun? instance.jun : '0'}",
                        "${instance.jul? instance.jul : '0'}",
                        "${instance.aug? instance.aug : '0'}",
                        "${instance.sep? instance.sep : '0'}",
                        "${instance.oct? instance.oct : '0'}",
                        "${instance.nov? instance.nov : '0'}",
                        "${instance.dec? instance.dec : '0'}",
                        "${instance.jan + instance.feb + instance.mar + instance.apr + instance.may + instance.jun + instance.jul + instance.aug + instance.sep + instance.oct + instance.nov + instance.dec}"
                ]
                instants << obj
            }
            return instants
        } catch (Exception ex){
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return getResultForUI(params)
    }
}