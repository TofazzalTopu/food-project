package com.bits.bdfp.inventory.setup.vatrate

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import com.docu.common.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.inventory.setup.VatRateService

@Component("searchVatRateAction")
class SearchVatRateAction extends Action {
    public static final Log log = LogFactory.getLog(SearchVatRateAction.class)

    @Autowired
    VatRateService vatRateService

    public Object preCondition(Object params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            String strFieldName =  params.fieldName.toString()
            String strFieldValue = params.fieldValue.toString()
            if(strFieldName.length() == 0){
                return null
            }
            return vatRateService.search(strFieldName, strFieldValue)
        }catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    public Object postCondition(Object params, def object) {
        return null
    }
}