package com.bits.bdfp.inventory.product.productprice

import com.bits.bdfp.inventory.product.ProductPriceService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 9/14/15.
 */
@Component("searchProductPriceListAction")
class SearchProductPriceListAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    ProductPriceService productPriceService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            init(params)
            Map result = [:]
            List objectList = null

            result = productPriceService.searchPriceNameList(params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List objList = this.wrapListInGridEntityList(objectList, start)
            return objList
        }
        catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    Map getResultForUI(List objList) {
        int pageCount = 1
        if (resultPerPage < 0) {
            pageCount = 1
            resultPerPage = total
        } else {
            if (total > resultPerPage) {
                pageCount = Math.ceil(total / resultPerPage)
            }
        }
        Map output = [page: pageNum, total: pageCount, records: total, rows: objList]
        return output;
    }

    private List wrapListInGridEntityList(objList, start) {
        List instants = []
//        int counter = start + 1;
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${instance.id ? instance.id : ''}",
                    "${instance.pricingTypeId ? instance.pricingTypeId : ''}",
                    "${instance.name ? instance.name : ''}",
                    "${instance.dateEffectiveFrom ? instance.dateEffectiveFrom : ''}",
                    "${instance.dateEffectiveTo ? instance.dateEffectiveTo : ''}",
                    "${instance.status ? instance.status : ''}",
                    "${instance.enterprise ? instance.enterprise : ''}",
                    "${instance.businessUnit ? instance.businessUnit : ''}",
                    "${instance.territory ? instance.territory : ''}",
                    "${instance.geoLocation ? instance.geoLocation : ''}",
                    "${instance.road ? instance.road : ''}",
                    "${instance.paraOrLocality ? instance.paraOrLocality : ''}",
                    "${instance.customerName ? instance.customerName : ''}",
                    "${instance.customerNumber ? instance.customerNumber : ''}",
                    "<a href='#' onclick='displayProductNameDetails(${instance.id}, ${instance.pricingTypeId});' class='ui-icon ui-icon-pencil' title='Edit'></a>"
            ]
            instants << obj
//            counter++
        };
        return instants
    }

    public Object postCondition(Object params, Object object) {
        return getResultForUI(object)
    }
}
