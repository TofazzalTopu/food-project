package com.bits.bdfp.finance.cashreceivedfrombranch


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.common.Message
import com.bits.bdfp.finance.CashReceivedFromBranch
import com.bits.bdfp.finance.CashReceivedFromBranchService

@Component("listCashReceivedFromBranchAction")
class ListCashReceivedFromBranchAction extends Action {

    @Autowired
    CashReceivedFromBranchService cashReceivedFromBranchService

    public Object preCondition(def params, def object) {
        //not need to implement
        return null
    }

    public Object execute(def params, def object) {
        try{
            init(params)
            Map result = [:]
            List objectList = null

            result = cashReceivedFromBranchService.getListForGrid(this, params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            return this.wrapListInGridEntityList(objectList, start)
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

   private Object wrapListInGridEntityList(objList, start) {
        try{
            List instants = []
            int counter = start + 1;
            objList.each { instance ->
                GridEntity obj = new GridEntity()
                obj.id = instance.id
                obj.cell = ["${counter}",
                          "${instance.id ? instance.id : ''}",
                            
                            "${instance.transactionDate? instance.transactionDate : ''}",
                            "${instance.distributionPoint? instance.distributionPoint : ''}",
                            "${instance.depositCashToDepositPool? instance.depositCashToDepositPool : ''}",
                            "${instance.bankAccount? instance.bankAccount : ''}",
                            "${instance.cashPool? instance.cashPool : ''}",
                            "${instance.dateUpdated? instance.dateUpdated : ''}"
                            ]
                instants << obj
                counter++
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