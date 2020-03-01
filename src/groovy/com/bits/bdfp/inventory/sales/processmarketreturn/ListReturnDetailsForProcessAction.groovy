package com.bits.bdfp.inventory.sales.processmarketreturn

import com.bits.bdfp.inventory.sales.MarketReturn
import com.bits.bdfp.inventory.sales.ProcessMarketReturnService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 1/25/2016.
 */
@Component("listReturnDetailsForProcessAction")
class ListReturnDetailsForProcessAction extends Action {

    @Autowired
    ProcessMarketReturnService processMarketReturnService

    @Override
    public Object preCondition(Object params, Object object) {
        return null
    }

    @Override
    public Object postCondition(Object params, Object object) {
        return getResultForUI(object)
    }

    @Override
    public Object execute(Object params, Object object) {
        Map output = [:]
        try {
            init(params)
            MarketReturn marketReturn = MarketReturn.read(Long.parseLong(params.id))
            if(!marketReturn.isDpCustomer){
                params.put('nonDp','1')
            }
            List list = processMarketReturnService.listMarketReturnDetails(params)
            List objList = this.wrapListInGridEntityList(list, start)
            return objList
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return output
    }

    private List wrapListInGridEntityList(List marketReturnList, int start) {
        List instants = []
        int counter = start + 1;
        marketReturnList.each { instance ->
            GridEntity obj = new GridEntity()
            String leak = '0'
            String shrt = '0'
            String mr = '0'
            String challan = '0'
            String damage = '0'
            String [] mrType = instance.mr_type.split(',')
            String [] qty = instance.quantity.split(',')
            for(int i = 0; i < mrType.length; i++){
                if(mrType[i] == 'Leak Pack'){
                    leak = qty[i]
                }else if(mrType[i] == 'Short Pack'){
                    shrt = qty[i]
                }else if(mrType[i] == 'Market Return'){
                    mr = qty[i]
                }else if(mrType[i] == 'Short Supply from Challan'){
                    challan = qty[i]
                }else if(mrType[i] == 'Damage'){
                    damage = qty[i]
                }
            }
            obj.id = counter
            obj.cell = ["${instance.id ? instance.id : ''}",
                        "${instance.name? instance.name : ''}",
                        "${instance.code? instance.code : ''}",
                        "${leak}",
                        "${shrt}",
                        "${mr}",
                        "${challan}",
                        "${damage}",
                        "${instance.unit_price? instance.unit_price : ''}",
                        "0"
            ]
            instants << obj
            counter++
        }

        return instants
    }

    Map getResultForUI(List objList) {
        int pageCount = 1
        if (resultPerPage < 0) {
            pageCount = 1
            resultPerPage=total
        }
        else {
            if (total > resultPerPage) {
                pageCount = Math.ceil(total / resultPerPage)
            }
        }
        Map output = [page: pageNum, total: pageCount, records: total, rows: objList]
        return output;
    }
}
