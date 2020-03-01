package com.bits.bdfp.setup.salestarget.salesheadbyvolume

import com.bits.bdfp.setup.salestarget.SalesHeadByVolumeService
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 3/29/16.
 */
@Component("listFinishProductBySalesHeadAction")
class ListFinishProductBySalesHeadAction extends Action {

    @Autowired
    SalesHeadByVolumeService salesHeadByVolumeService

    public Object preCondition(def params, def object) {
        //not need to implement
        return null
    }

    public Object execute(def params, def object) {
        try{
            List objectList = null

            Map result = salesHeadByVolumeService.listFinishProductGrid(params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List gridDataList =  (List) wrapListInGridEntityList(objectList, start)
            Map output = [page: 1, total: 1, records: total, rows: gridDataList]
            return output;
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    private Object wrapListInGridEntityList(objList, start) {
        try{
            List instants = []
            objList.each { instance ->
                GridEntity obj = new GridEntity()
                obj.id = instance.id
                obj.cell = [
                        "${instance.id ? instance.id : ''}",
                        "${instance.salesHeadFinishProductId? instance.salesHeadFinishProductId : ''}",
                        "${instance.productCode? instance.productCode : ''}",
                        "${instance.productName? instance.productName : ''}",
                        "${instance.quantity? instance.quantity : ''}"
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
        return null
    }
}