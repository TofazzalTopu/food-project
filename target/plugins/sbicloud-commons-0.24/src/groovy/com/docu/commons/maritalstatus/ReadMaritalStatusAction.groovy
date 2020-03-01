package com.docu.commons.maritalstatus

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.docu.commons.MaritalStatus
import com.docu.commons.MaritalStatusService

@Component("readMaritalStatusAction")
class ReadMaritalStatusAction implements IAction {
    public static final Log log = LogFactory.getLog(ReadMaritalStatusAction.class)
    private final String MESSAGE_HEADER = 'maritalStatus.header'
    private final String MESSAGE_SUCCESS = 'maritalStatus.update.success'

    @Autowired
    MaritalStatusService maritalStatusService

    public Object preCondition(def params) {
        return null
    }

    public Object execute(def params, def object) {
        MaritalStatus maritalStatus = maritalStatusService.read(params)
        Map objectData = ObjectUtil.getPersistenceData(maritalStatus)
        return objectData
    }

    public Object postCondition(def object) {
        return null
    }
}