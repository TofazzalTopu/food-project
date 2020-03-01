package com.docu.security

import com.docu.commons.SessionManagementService
import grails.converters.JSON
import groovy.sql.GroovyRowResult


class LoginHistoryController {
    SessionManagementService sessionManagementService
    LoginHistoryService loginHistoryService
//    @Autowired
//    AjaxOfficeInfoAction ajaxOfficeInfoAction

    def loginHistory = {
//        [currentOffice:sessionManagementService.office,
//                configuredCountry: sessionManagementService.configuredCountry, systemDate : DateUtil.getDateFormatAsString(DateUtil.currentSystemDate)]
    }

    def getLoginInformation = {
        render loginHistoryService.getLoginHistory(params)
    }

//    def getOffice = {
//        long countryId = sessionManagementService.configuredCountry.id
//        if(params.countryId != 'null'){
//            countryId = Long.parseLong(params.countryId)
//        }
//        render ajaxOfficeInfoAction.getAllOfficeList(countryId, params.term.toString())
//    }

    def getUserList = {
        List<GroovyRowResult> userList = loginHistoryService.getUserList()
        List<Map> mapList = userList.collect { GroovyRowResult userInfo ->
            [
                    id      : userInfo.userName,
                    value   : userInfo.userName,
                    userInfo: "[${userInfo.userName}] ${userInfo.fullName}"
            ]
        }
        render mapList as JSON
    }
}
