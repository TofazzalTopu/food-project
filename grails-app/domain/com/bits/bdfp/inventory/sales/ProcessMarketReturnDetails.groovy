package com.bits.bdfp.inventory.sales

import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.warehouse.FinishGoodStock
import com.docu.security.ApplicationUser

class ProcessMarketReturnDetails {

    ProcessMarketReturn processMarketReturn
    FinishGoodStock     finishGoodStock

    FinishProduct   finishProduct
    String          mrType
    String          mrSubType
    Float           subTypeQuantity
    Float           qcQuantity
    Float           qcFailedQuantity
    Float           rate

    static mapping = {
        subTypeQuantity defaultValue: '0'
    }

    static constraints = {
        processMarketReturn(blank: false, nullable: false)
        finishGoodStock(blank: false, nullable: false)
        finishProduct(blank: false, nullable: false)
        mrType(blank: false, nullable: false)
        mrSubType(blank: true, nullable: true)
        subTypeQuantity(blank: true, nullable: true)
        qcQuantity(blank: false, nullable: false)
        qcFailedQuantity(blank: false, nullable: false)
        rate(blank: false, nullable: false)
    }
}
