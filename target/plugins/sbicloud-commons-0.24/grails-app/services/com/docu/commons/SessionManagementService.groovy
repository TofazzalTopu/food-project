/*
 * ***************************************************************
 * Copyright � 2010 Documentatm (TM) Limited. All rights reserved.
 * DOCUMENTA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * This software is the confidential and proprietary information of
 * Documentatm (TM) Limited ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Documentatm (TM) Limited.
 * ****************************************************************
 */

package com.docu.commons

import com.docu.commons.exception.ProcessAlreadyRunningException
import org.springframework.web.context.request.RequestContextHolder
import javax.servlet.http.HttpSession

class SessionManagementService {

    static transactional = false
    static scope = "singleton"
    List<String> busyOfficeCodeList = Collections.synchronizedList(new ArrayList<String>())
    private static final String ID_PREFIX = "IDPREFIX"

    public String getCurrentSessionId() {
        return getSession().id
    }

    public void setUser(Object user) {
        getSession().user = user
    }

    public Object getUser() {
        return getSession().user
    }

    public String setUserRole(String role) {
        return getSession().userRole = role
    }

    public String getUserRole() {
        return getSession().userRole
    }

    public void setMessage(String message) {
        getSession().message = message
    }

    public String getMessage() {
        return getSession().message
    }

    public void removeMessage() {
        getSession().removeAttribute("message")
    }

    public void setTaskStatus(double value) {
        getSession().setAttribute("status", value)
    }

    public double getTaskStatus() {
        return (double)getSession().status
    }

    public void setEmployeeInfo(Object employeeInfo) {
        getSession().setAttribute("EmployeeInfo", employeeInfo)
    }

    public Object getEmployeeInfo() {
        return getSession().getAttribute("EmployeeInfo")
    }

    public void setOffice(Object office) {
        getSession().setAttribute("Office", office)
        StringBuilder idPrefix = new StringBuilder(office.officeCode)
        if (office.isHeadOffice()) {
            for (int i in (office.officeCode.length()..7)) {
                idPrefix.append('0')
            }
        }
        getSession().setAttribute(ID_PREFIX, idPrefix.toString())
    }

    public Object getOffice() {
        return getSession().getAttribute("Office")
    }

    public String getOfficeCode() {
        return (getSession().getAttribute("Office"))?.getOfficeCode()
    }

    public String getOfficeIdPrefix() {
        return ((String) getSession().getAttribute(ID_PREFIX))
    }

    public void setOfficeIds(String officeIds) {
        getSession().setAttribute('mf.officeIds', officeIds)
    }

    public String getOfficeIds() {
        return (String) getSession().getAttribute('mf.officeIds')
    }

    public void setOfficeTypeIds(String officeTypeIds) {
        getSession().setAttribute('mf.officeTypeIds', officeTypeIds)
    }

    public String getOfficeTypeIds() {
        return (String) getSession().getAttribute('mf.officeTypeIds')
    }

    public void setOfficeCodes(String officeCodes) {
        getSession().setAttribute('mf.officeCodes', officeCodes)
    }

    public String getOfficeCodes() {
        return (String) getSession().getAttribute('mf.officeCodes')
    }

    public void setProjectIds(String ids) {
        getSession().setAttribute('mf.projectIds', ids)
    }

    public String getProjectIds() {
        return (String) getSession().getAttribute('mf.projectIds')
    }

    public void setCostCenterIds(String ids) {
        getSession().setAttribute('mf.costCenterIds', ids)
    }

    public String getCostCenterIds() {
        return (String) getSession().getAttribute('mf.costCenterIds')
    }

    public void setAuthorizedProjectList(List list) {
        getSession().setAttribute('mf.projectInfoList', list)
    }

    public List getAuthorizedProjectList() {
        return (List) getSession().getAttribute('mf.projectInfoList')
    }

    public void setCostCenterList(List list) {
        getSession().setAttribute('mf.costCenterList', list)
    }

    public List getCostCenterList() {
        return (List) getSession().getAttribute('mf.costCenterList')
    }

    public void setUserFavouriteLink(List linkList) {
        getSession().setAttribute('userFavouriteLink', linkList)
    }

    public List getUserFavouriteLink() {
        return (List) getSession().getAttribute('userFavouriteLink')
    }

    public void setUserMenuList(List menuList) {
        getSession().setAttribute('menuList', menuList)
    }

    public List getUserMenuList() {
        return (List) getSession().getAttribute('menuList')
    }

    public void setDisplayMenuCode(boolean value) {
        getSession().setAttribute('menuCode', value)
    }

    public boolean displayMenuCode() {
        return (boolean) getSession().getAttribute('menuCode')
    }

    public HttpSession getSession() {
        return RequestContextHolder.currentRequestAttributes().getSession()
    }

    public void setAccountingDay(Object accountingDay) {
        getSession().setAttribute('accountingDay', accountingDay)
        if (accountingDay.businessDate) {
            getOffice().businessDate = accountingDay.businessDate
        }
        else {
            getOffice().businessDate = accountingDay.lastClosedBusinessDate
        }
    }

    public Object getAccountingDay() {
        return getSession().getAttribute('accountingDay')
    }





    public void setLoginHistory(Object loginHistory) {
        getSession().setAttribute("LoginHistory", loginHistory)
    }

    public Object getLoginHistory() {
        return getSession().getAttribute("LoginHistory")
    }

    public void setModuleInfoId(long moduleInfoId) {
        getSession().moduleInfoId = moduleInfoId
    }

    public long getModuleInfoId() {
        return getSession().moduleInfoId
    }

    public void setAllowedModuleList(List list) {
        getSession().setAttribute('allowedModuleList', list)
    }

    public List getAllowedModuleList() {
        return (List) getSession().getAttribute('allowedModuleList')
    }

    public void setPreferredTheme(String themeName) {
        getSession().setAttribute('preferredTheme', themeName)
    }

    public String getPreferredTheme() {
        return getSession().getAttribute('preferredTheme')
    }

    public synchronized void makeCurrentOfficeBusy(String officeCode) {
        synchronized (busyOfficeCodeList) {
            String busyOfficeCode = busyOfficeCodeList.find { it == officeCode }
            if (busyOfficeCode == null) {
                busyOfficeCodeList.add(officeCode)
            }
            else {
                throw new Exception("Office Code is already in Busy Office List")
            }
        }
    }

    public synchronized void makeCurrentOfficeIdle(String officeCode) {
        synchronized (busyOfficeCodeList) {
            busyOfficeCodeList.removeAll { it == officeCode }
        }
    }

    public synchronized boolean isOfficeBusy(String officeCode) {
        synchronized (busyOfficeCodeList) {
            if (busyOfficeCodeList.contains(officeCode)) {
                return true
            }
        }
        return false
    }

    public boolean setHasMultipleOfficeMapping(boolean value) {
        getSession().hasMultipleOfficeMapping = value
    }

    public boolean getHasMultipleOfficeMapping() {
        return getSession().hasMultipleOfficeMapping
    }

    public boolean setMultipleOfficeList(List officeList) {
        getSession().multipleOfficeList = officeList
    }

    public List getMultipleOfficeList() {
        return getSession().multipleOfficeList
    }

    public void setRequestedUrl(String requestedUrl) {
        getSession().setAttribute('requestedUrl', requestedUrl)
    }

    public String getRequestedUrl() {
        return getSession().getAttribute('requestedUrl')
    }

    public void removeRequestedUrl() {
        getSession().removeAttribute("requestedUrl")
    }

    public synchronized void startProcess(String officeCode, long moduleId, String processId) {
        synchronized (busyOfficeCodeList) {
            String processKey = officeCode + CommonConstants.CODE_SEPARATOR + moduleId + CommonConstants.CODE_SEPARATOR + processId
            String processCode = busyOfficeCodeList.find { it == processKey }
            if (processCode == null) {
                busyOfficeCodeList.add(processKey)
            }
            else {
                throw new ProcessAlreadyRunningException("Requested process is already running")
            }
        }
    }

    public synchronized void endProcess(String officeCode, long moduleId, String processId) {
        synchronized (busyOfficeCodeList) {
            String processKey = officeCode + CommonConstants.CODE_SEPARATOR + moduleId + CommonConstants.CODE_SEPARATOR + processId
            busyOfficeCodeList.removeAll { it == processKey }
        }
    }

    public synchronized boolean isProcessBusy(String officeCode, long moduleId, String processId) {
        synchronized (busyOfficeCodeList) {
            String processKey = officeCode + CommonConstants.CODE_SEPARATOR + moduleId + CommonConstants.CODE_SEPARATOR + processId
            if (busyOfficeCodeList.contains(processKey)) {
                return true
            }
        }
        return false
    }

}
