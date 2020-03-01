package com.docu.commons.gender

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.docu.commons.Gender
import com.docu.commons.GenderService

@Component("readGenderAction")
class ReadGenderAction implements IAction {
    public static final Log log = LogFactory.getLog(ReadGenderAction.class)
    private final String MESSAGE_HEADER = 'gender.header'
    private final String MESSAGE_SUCCESS = 'gender.update.success'

    @Autowired
    GenderService genderService

    public Object preCondition(def params) {

        return null
    }

    public Object execute(def params, def object) {
        Gender gender = genderService.read(params)
        Map objectData = ObjectUtil.getPersistenceData(gender)
        return objectData
    }

    public Object postCondition(def object) {
        return null
    }
}