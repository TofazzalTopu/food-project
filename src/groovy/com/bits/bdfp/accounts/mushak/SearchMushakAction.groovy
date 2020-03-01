package com.bits.bdfp.accounts.mushak

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import com.docu.common.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.accounts.MushakService

@Component("searchMushakAction")
class SearchMushakAction extends Action {
    public static final Log log = LogFactory.getLog(SearchMushakAction.class)

    @Autowired
    MushakService mushakService

    public Object preCondition(Object params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            return mushakService.searchMushak(params)
        }catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    public Object postCondition(Object params, def object) {
        return null
    }
}