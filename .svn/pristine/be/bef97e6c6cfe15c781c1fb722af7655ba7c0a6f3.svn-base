package com.docu.util

import com.docu.commons.DateUtil
import com.docu.commons.Office
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

import javax.servlet.http.HttpSession

/**
 * Created by IntelliJ IDEA.
 * User: Rafiq
 * Date: 12/5/11
 * Time: 10:30 AM
 * To change this template use File | Settings | File Templates.
 */

@Singleton
class SessionManager {
    private static final Log log = LogFactory.getLog(SessionManager.class)
    private List<UserSessionInformation> userSessionInformationList = []
    private final boolean singleActiveSessionPerUser = true

    public synchronized void refresh() {
        UserSessionInformation sessionInformation = null
        updateList(userSessionInformationList)
    }

    public synchronized List<UserSessionInformation> list(boolean refreshSession = false) {
        if (refreshSession) {
            refresh()
        }
        return userSessionInformationList
    }

    public synchronized void addSession(HttpSession session) {
        UserSessionInformation sessionInformation = new UserSessionInformation(session: session,
                creationTime: new Date(session.creationTime), lastAccessedTime: new Date(session.lastAccessedTime),
                maxInactiveInterval: session.maxInactiveInterval)
        userSessionInformationList.add(sessionInformation)
    }

    public synchronized void removeSession(HttpSession session) {
        userSessionInformationList.removeAll { it.session.id == session.id }
    }

    public synchronized void removeSession(String username, HttpSession session) {
        userSessionInformationList.removeAll { it.username == username && session.id != it.session.id }
    }

    public synchronized void removeSession(String username) {
        userSessionInformationList.removeAll { it.username == username }
    }

    public synchronized void invalidateUserSession(String username) {
        UserSessionInformation sessionInformation = userSessionInformationList.find { it.username == username }
        if (sessionInformation) {
            sessionInformation.session.invalidate()
            userSessionInformationList.removeAll { it.username == username }
        }
    }

    public synchronized void updateSession(HttpSession session) {
//        log.error("params::session == null " + (session == null))
        String username = session.getAttribute("user")?.username
        if (singleActiveSessionPerUser && username != "") {
            removeSession(username, session)
        }
        UserSessionInformation sessionInformation = userSessionInformationList.find { it.session.id == session.id }
        if (sessionInformation) {
            sessionInformation.lastAccessedTime = new Date(session.lastAccessedTime)
            sessionInformation.expireAt = DateUtil.getDateAfterSeconds(sessionInformation.lastAccessedTime, sessionInformation.maxInactiveInterval)
            sessionInformation.username = username
            sessionInformation.userRole = session.getAttribute("userRole")
            sessionInformation.fullName = session.getAttribute("user")?.fullName
            Office office = (Office) session.getAttribute(Office.class.simpleName)
            if (office) {
                sessionInformation.officeCode = office.officeCode
                sessionInformation.officeName = office.officeName
                sessionInformation.officeType = office.officeTypeName
            }
        }
        else {
            log.error("sessionInformation is not found")
        }
    }

    public synchronized List<UserSessionInformation> getNumberOfLoggedInUserList(String officeCode) {
        List<UserSessionInformation> entryList = userSessionInformationList.findAll { it.officeCode == officeCode }
        if (entryList) {
            updateList(entryList)
            return entryList
        }
        return []
    }

    public synchronized long getNumberOfLoggedInUser(String officeCode) {
        List entryList = userSessionInformationList.findAll { it.officeCode == officeCode }
        return entryList == null ? 0 : entryList.size()
    }

    public synchronized long getNumberOfLoggedInUser() {
        List entryList = userSessionInformationList.findAll { it.username != null && !it.username.isEmpty() && it.userRole != "SA" && it.userRole != "ADMIN" }
        return entryList == null ? 0 : entryList.size()
    }

    private synchronized void updateList(List<UserSessionInformation> list) {
        list.each {
            it.lastAccessedTime = new Date(it.session.lastAccessedTime)
            it.expireAt = DateUtil.getDateAfterSeconds(it.lastAccessedTime, it.maxInactiveInterval)
            it.username = it.session.getAttribute("user")?.username
            it.fullName = it.session.getAttribute("user")?.fullName
            it.userRole = it.session.getAttribute("userRole")
            Office office = (Office) it.session.getAttribute(Office.class.simpleName)
            if (office) {
                it.officeCode = office.officeCode
                it.officeName = office.officeName
                it.officeType = office.officeTypeName
            }
        }
    }
}
