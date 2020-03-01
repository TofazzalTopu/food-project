package com.docu.commons.religion

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.docu.commons.Religion
import com.docu.commons.ReligionService

@Component("readReligionAction")
class ReadReligionAction implements IAction {
    public static final Log log = LogFactory.getLog(ReadReligionAction.class)
    private final String MESSAGE_HEADER = 'religion.header'
    private final String MESSAGE_SUCCESS = 'religion.update.success'

    @Autowired
    ReligionService religionService

    public Object preCondition(def params) {

        return null
    }

    public Object execute(def params, def object) {
        Religion religion = religionService.read(params)
        Map objectData = ObjectUtil.getPersistenceData(religion)
        return objectData
    }

    public Object postCondition(def object) {
        return null
    }
}