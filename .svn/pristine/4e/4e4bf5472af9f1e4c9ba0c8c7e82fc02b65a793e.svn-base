package com.bits.bdfp.accounts

import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by abhijit.majumder on 1/24/2016.
 */
@Component("listChartOfAccountLayerAction")
class ListChartOfAccountLayerAction extends Action{

    @Autowired
    ChartOfAccountService chartOfAccountService

    private static final Log log = LogFactory.getLog(this)

    public Object preCondition(Object params,Object object){
        return null
    }
    public Object execute(Object params,Object object){
        try{
            List list
            Map map = [:]
            if(params.update && params.update == '1'){
                list = chartOfAccountService.listCoa(object.id)
                map.put('coa', list)
                list = chartOfAccountService.listLayers(object.id)
                map.put('layer', list)
            }else {
                list = chartOfAccountService.listLayers(object.id)
                map.put('layer', list)
            }
            return map
        }
        catch(Exception e){
            log.error(e.message)
            throw new RuntimeException(e.message)
        }
    }
    public Object postCondition(Object params,Object object){
        return null
    }
}
