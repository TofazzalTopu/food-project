package com.bits.bdfp.inventory.sales.marketreturn

import com.bits.bdfp.inventory.sales.MarketReturnService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 1/21/2016.
 */
@Component("distributionPointStockChangeAction")
class DistributionPointStockChangeAction extends Action {

    @Autowired
    MarketReturnService marketReturnService

    @Override
    protected Object preCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    protected Object postCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    public Object execute(Object object, Object params) {
        int noOfRows = marketReturnService.updateQuantity(params)
        if (noOfRows > 0) {
            return this.getMessage("Update Market Return", Message.SUCCESS, 'Product return record deleted successfully.')
        } else {
            return this.getMessage("Update Market Return", Message.ERROR, 'Product return record could not be deleted.')
        }
    }
}
