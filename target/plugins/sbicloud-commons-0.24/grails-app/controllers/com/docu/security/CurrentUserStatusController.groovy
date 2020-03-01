package com.docu.security

import com.docu.commons.CommonConstants
import com.docu.commons.JsonUtil
import com.docu.commons.Message
import com.docu.commons.SessionManagementService
import com.docu.util.SessionManager
import com.docu.util.UserSessionInformation
import grails.converters.JSON

import java.text.DateFormat
import java.text.SimpleDateFormat
import org.springframework.beans.factory.annotation.Autowired

class CurrentUserStatusController {
    @Autowired
    JsonUtil jsonUtil

    SessionManagementService sessionManagementService

    def index = {redirect(action: listOfCurrentUser) }

    def listOfCurrentUser = {
        List<UserSessionInformation> userSessionInformationList = SessionManager.instance.list(true)
        [feed: getListOfCurrentUserAsJson(userSessionInformationList)]
    }

    def listOfCurrentOnlineUserAtOffice = {}

    def listOfCurrentUserLogOut = {
        Message msg = null
        if(params.flag){
            SessionManager.instance.invalidateUserSession(params.userName)
        }else{
            params.items.each{
                String userName = it.value
                if(userName != sessionManagementService?.user?.username){
                    SessionManager.instance.invalidateUserSession(userName)
                }
            }
        }
        Map map = [:]
        map.put('logInUserName',sessionManagementService?.user?.username)
        map.put('message',msg)
        render map as JSON
    }

    def listOfCurrentUserOfCurrentOffice = {
        render(template: "userStatusGridOfCurrentOffice")
    }
    def currentUserOfCurrentOfficeList = {
        List<UserSessionInformation> userSessionInformationList = SessionManager.instance.getNumberOfLoggedInUserList(sessionManagementService.getOffice()?.officeCode)
        render getListOfCurrentUserAsJson(userSessionInformationList)
    }

    private String getListOfCurrentUserAsJson(List<UserSessionInformation> userSessionInformationList ) {
        String feed = ""
        List<Map> jsonData = []
        Map thisMap = [:]
        ApplicationUserType userType = sessionManagementService.user.applicationUserType
        if(userType == ApplicationUserType.INTERNAL){
            userSessionInformationList = userSessionInformationList.findAll {ApplicationUser.findByUsername(it.username).applicationUserType == ApplicationUserType.INTERNAL}
        }
        if (userSessionInformationList && userSessionInformationList.size() > 0) {
            DateFormat dateFormat = new SimpleDateFormat(CommonConstants.DATE_FORMAT_H_M_S)
            userSessionInformationList.each { userSessionInformation ->
                if (userSessionInformation.username) {
                    thisMap = [:]
                    thisMap.username = userSessionInformation.username
                    thisMap.fullName=userSessionInformation.fullName
                    thisMap.userRole = userSessionInformation.userRole
                    thisMap.officeCode = userSessionInformation.officeCode
                    thisMap.officeName = userSessionInformation.officeName
                    thisMap.office = '[' + thisMap.officeCode + '] - ' + thisMap.officeName
                    thisMap.officeType = userSessionInformation.officeType
                    thisMap.creationTime = dateFormat.format(userSessionInformation.creationTime)
                    thisMap.lastAccessedTime = dateFormat.format(userSessionInformation.lastAccessedTime)
                    thisMap.expireAt = dateFormat.format(userSessionInformation.expireAt)
                    if (userSessionInformation.username == sessionManagementService?.user?.username){
                        thisMap.logOut = ''
                    }else{
                        thisMap.logOut = 'Logout'
                    }

                    jsonData.add(thisMap)
                }
            }
        }
        feed = jsonData as JSON
        return feed
    }
}
