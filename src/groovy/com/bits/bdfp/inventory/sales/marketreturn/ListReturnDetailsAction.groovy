package com.bits.bdfp.inventory.sales.marketreturn

import com.bits.bdfp.inventory.sales.MarketReturn
import com.bits.bdfp.inventory.sales.MarketReturnService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 1/24/2016.
 */
@Component("listReturnDetailsAction")
class ListReturnDetailsAction extends Action {

    @Autowired
    MarketReturnService marketReturnService

    @Override
    protected Object preCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    protected Object postCondition(Object params, Object object) {
        return getResultForUI(object)
    }

    @Override
    public Object execute(Object params, Object object) {
        Map output = [:]
        try {
            init(params)
            List list = marketReturnService.listReturnDetails(params)
            List objList = this.wrapListInGridEntityList(list, start)
            return objList
        }
        catch (Exception ex) {
            log.error(ex.message)
        }
        return output
    }

    private List wrapListInGridEntityList(List<MarketReturn> marketReturnList, int start) {
        List instants = []
        int counter = start + 1;
        marketReturnList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = [
                        "${instance.id ? instance.id : ''}",
                        "${instance.distribution_point_stock_id ? instance.distribution_point_stock_id : ''}",
                        "${instance.finish_product_id? instance.finish_product_id : ''}",
                        "${instance.invoice_id? instance.invoice_id : ''}",
                        "${instance.name? instance.name : ''}",
                        "${instance.product_code? instance.product_code : ''}",
                        "${instance.invoice_code? instance.invoice_code : ''}",
                        "${instance.batch ? instance.batch : ''}",
                        "${instance.mr_type ? instance.mr_type : ''}",
                        "${instance.quantity ? instance.quantity : ''}",
                        "${instance.reference? instance.reference : ''}",
                        "${instance.remarks ? instance.remarks : ''}",
                        "${instance.price ? instance.price : ''}",
                        '<a  href="javascript:deleteItemFromGrid(' + instance.id + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
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
