package com.bits.bdfp.accounts.mushak

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.bits.bdfp.accounts.Mushak
import com.bits.bdfp.accounts.MushakService

@Component("readMushakAction")
class ReadMushakAction extends Action {
    public static final Log log = LogFactory.getLog(ReadMushakAction.class)

    @Autowired
    MushakService mushakService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        Mushak mushak = mushakService.read(Long.parseLong(params.id))
        return mushak
    }

    public Object postCondition(def params, def object) {
        return null
    }
}