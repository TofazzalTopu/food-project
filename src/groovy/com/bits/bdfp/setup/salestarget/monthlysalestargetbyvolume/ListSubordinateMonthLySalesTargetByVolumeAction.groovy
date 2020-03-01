package com.bits.bdfp.setup.salestarget.monthlysalestargetbyvolume

import com.bits.bdfp.setup.salestarget.MonthlySalesTargetByVolumeService
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 4/4/16.
 */
@Component("listSubordinateMonthLySalesTargetByVolumeAction")
class ListSubordinateMonthLySalesTargetByVolumeAction extends Action {

    @Autowired
    MonthlySalesTargetByVolumeService monthlySalesTargetByVolumeService

    public Object preCondition(def params, def object) {
        //not need to implement
        return null
    }

    public Object execute(def params, def object) {
        try{
            init(params)
            List result = monthlySalesTargetByVolumeService.listSubordinateMonthLySalesTarget(params)
            if (result) { // in case: normal list
                total = result.size()
            }
            List gridData = (List) this.wrapListInGridEntityList(result)
            Map output = [page: 1, total: 1, records: gridData.size(), rows: gridData]
            return output;
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    private Object wrapListInGridEntityList(objList) {
        try{
            List instants = []
            int counter = 1
            long productId = 0
            objList.each { instance ->
                String productCode = instance.productCode
                String productName = instance.productName
                if(instance.productId == productId){
                    productCode = ""
                    productName = ""
                }
                productId = instance.productId
                GridEntity obj = new GridEntity()
                obj.id = counter
                obj.cell = ["${counter}",
                        "${instance.productId? instance.productId : ''}",
                        "${productCode}",
                        "${productName}",
                        "${instance.employeeId? instance.employeeId : ''}",
                        "${instance.employeeCode? instance.employeeCode : ''}",
                        "${instance.employeeName? instance.employeeName : ''}",
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
                counter++
            }
            return instants
        } catch (Exception ex){
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}