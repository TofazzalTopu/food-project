package com.docu.app

import com.docu.commons.CommonConstants
import com.docu.commons.InternationalizationService
import com.docu.plugin.cxfws.CommonHelperWebService
import org.springframework.transaction.annotation.Transactional

class DashboardPortletService extends InternationalizationService {
    static transactional = false
    CommonHelperWebService commonHelperWebService

    @Transactional(readOnly = true)
    public DashboardPortlet read(Map params) {
        return DashboardPortlet.read(params.id)
    }

    @Transactional(readOnly = true)
    public Map list(Map params) {
        List<DashboardPortlet> dashboardPortletList = []
        long dashboardPortletCount = 0
        try {
            dashboardPortletList = DashboardPortlet.withCriteria {
                maxResults(Integer.parseInt(params.resultPerPage.toString()))
                firstResult(Integer.parseInt(params.start.toString()))
                order(params.sortCol.toString(), params.sortOrder.toString())
            }
            dashboardPortletCount = DashboardPortlet.count()
        }
        catch (Exception ex) {
            log.error(ex.message)
        }
        return [dashboardPortletList: dashboardPortletList, dashboardPortletCount: dashboardPortletCount]
    }

    @Transactional
    public boolean save(DashboardPortlet dashboardPortlet) {
        boolean isUpdate = true
        try {
            if (dashboardPortlet.id == null){

                isUpdate = false
            }
            dashboardPortlet.save()
            commonHelperWebService.callWebMethod(dashboardPortlet,'',isUpdate)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional
    public boolean update(DashboardPortlet dashboardPortlet) {
        try {
            dashboardPortlet.save()
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional
    public delete(DashboardPortlet dashboardPortlet) {
        try {
            dashboardPortlet.delete()
            commonHelperWebService.callWebMethod(dashboardPortlet,"delete")
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional(readOnly = true)
    public DashboardPortlet search(String fieldName, String fieldValue) {
        String query = "from DashboardPortlet as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return DashboardPortlet.find(query)
    }
}
