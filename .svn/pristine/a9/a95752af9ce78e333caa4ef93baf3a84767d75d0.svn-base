package com.bits.bdfp.inventory.product.productprice

import com.bits.bdfp.inventory.product.ProductPriceService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("listProductPriceAction")
class ListProductPriceAction extends Action {
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

            result = productPriceService.getListForGrid(this)
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
        int counter = start + 1;
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${counter}",
                    "${instance.id ? instance.id : ''}",

                    "${instance.enterpriseConfiguration ? instance.enterpriseConfiguration : ''}",
                    "${instance.businessUnitConfiguration ? instance.businessUnitConfiguration : ''}",
                    "${instance.productPricingType ? instance.productPricingType : ''}",
                    "${instance.pricingCategory ? instance.pricingCategory : ''}",
                    "${instance.name ? instance.name : ''}",
                    "${instance.territorySubArea ? instance.territorySubArea : ''}",
                    "${instance.dateEffectiveFrom ? instance.dateEffectiveFrom : ''}",
                    "${instance.dateEffectiveTo ? instance.dateEffectiveTo : ''}",
                    "${instance.price ? instance.price : ''}",
                    "${instance.vatPercentage ? instance.vatPercentage : ''}",
                    "${instance.vatAmount ? instance.vatAmount : ''}",
                    "${instance.totalAmount ? instance.totalAmount : ''}",
                    "${instance.isActive ? instance.isActive : ''}",
                    "${instance.userCreated ? instance.userCreated : ''}",
                    "${instance.userUpdated ? instance.userUpdated : ''}",
                    "${instance.dateCreated ? instance.dateCreated : ''}",
                    "${instance.dateUpdated ? instance.dateUpdated : ''}"
            ]
            instants << obj
            counter++
        };
        return instants
    }

    public Object postCondition(Object params, Object object) {
        return getResultForUI(object)
    }
}
