package com.docu.app.dashboardportlet

import com.docu.app.DashboardPortlet
import com.docu.app.DashboardPortletService
import com.docu.commons.GridEntity
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("listDashboardPortletAction")
class ListDashboardPortletAction implements IAction {
    public static final Log log = LogFactory.getLog(CreateDashboardPortletAction.class)

    @Autowired
    DashboardPortletService dashboardPortletService

    public Object preCondition(def params) {
        return null
    }

    public Object execute(def params, def object) {
        Map output = [:]
        try {
            String pageNum                          = params.page ? params.page : "1"
            String resultPerPage                    = params.rows ? params.rows : "10"
            String start                            = ((Integer.parseInt(pageNum) - 1) * Integer.parseInt(resultPerPage)).toString()
            String sortCol                          = params.sidx ? params.sidx : "id"
            String sortOrder                        = params.sord ? params.sord : "desc"

            params.put("pageNum",                   pageNum)
            params.put("resultPerPage",             resultPerPage)
            params.put("start",                     start)
            params.put("sortCol",                   sortCol)
            params.put("sortOrder",                 sortOrder)

            Map result                              = dashboardPortletService.list(params)
            List<DashboardPortlet> dashboardPortletList  = (List<DashboardPortlet>) result.get('dashboardPortletList')
            long dashboardPortletCount               = (long) result.get('dashboardPortletCount')
            List rows                               = this.wrapListInGridEntityList(dashboardPortletList, Integer.parseInt(start))

            int pageCount = 1
            if (dashboardPortletCount > Long.parseLong(resultPerPage.toString())) {
                pageCount = Math.ceil(dashboardPortletCount / Long.parseLong(resultPerPage.toString()))
            }

            output = [page: pageNum, total: pageCount, records: dashboardPortletCount, rows: rows]
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return output
    }

    public Object postCondition(def object) {
        return null
    }

    private List wrapListInGridEntityList(List<DashboardPortlet> dashboardPortletList, int start) {
        List instants = []
        int counter = start + 1;
        dashboardPortletList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${counter}",
                        "${instance.id ? instance.id : ''}",
                            
                        "${instance.auto? instance.auto : ''}",
                        "${instance.moduleInfoId? instance.moduleInfoId : ''}",
                        "${instance.title? instance.title : ''}",
                        "${instance.url? instance.url : ''}"
                        ]
                        instants << obj
                        counter++
        }

        return instants
    }
}
