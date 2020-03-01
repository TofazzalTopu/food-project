package com.bits.bdfp.inventory.sales.marketreturn

import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.bits.bdfp.inventory.sales.MarketReturn
import com.bits.bdfp.inventory.sales.MarketReturnService

@Component("readMarketReturnAction")
class ReadMarketReturnAction extends Action {
    public static final Log log = LogFactory.getLog(ReadMarketReturnAction.class)

    @Autowired
    MarketReturnService marketReturnService
    Message message

    @Override
    protected Object preCondition(Object object1, Object object2) {
        return null
    }

    @Override
    protected Object postCondition( Object object1, Object object2) {
        return null
    }

    public Object execute(Object params, Object object) {
        MarketReturn marketReturn = marketReturnService.read(Long.parseLong(params.id))
        if(marketReturn){
            message = null
        }else{
            message = this.getMessage(marketReturn, Message.ERROR, this.FAIL_UPDATE)
        }
        Map map = [:]
        map.put('marketReturn', marketReturn)
        map.put('message', message)
        return map
    }

}