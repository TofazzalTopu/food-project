package com.docu.security.authority.action

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.docu.security.UserAuthority
import com.docu.security.UserAuthorityService

@Component("readAuthorityAction")
class ReadAuthorityAction implements IAction {
    public static final Log log = LogFactory.getLog(ReadAuthorityAction.class)
    private final String MESSAGE_HEADER = 'authority.header'
    private final String MESSAGE_SUCCESS = 'authority.update.success'

    @Autowired
    UserAuthorityService userAuthorityService

    public Object preCondition(def params) {
        return null
    }

    public Object execute(def params, def object) {
        UserAuthority authority = userAuthorityService.read(params)
        Map objectData = ObjectUtil.getPersistenceData(authority)
        return objectData
    }

    public Object postCondition(def object) {
        return null
    }
}