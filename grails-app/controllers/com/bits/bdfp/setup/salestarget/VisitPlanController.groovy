package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.retailorder.RetailOrderDetails
import com.bits.bdfp.inventory.sales.visitplan.CreateVisitPlanAction
import com.bits.bdfp.inventory.sales.visitplan.SearchVisitPlanAction
import com.docu.common.Message
import com.docu.commons.DateUtil
import com.docu.commons.ObjectUtil
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by abhijit.majumder on 2/23/2016.
 */
class VisitPlanController {
    def jasperService
    static defaultAction = "show"
    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]
    @Autowired
    CreateVisitPlanAction createVisitPlanAction
    @Autowired
    SearchVisitPlanAction searchVisitPlanAction

    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        String recentDate = DateUtil.getCurrentDateFormatAsString()
        render(template: "show", model: [applicationUser: applicationUser,recentDate: recentDate])
    }
    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createVisitPlanAction.execute(params, applicationUser)
        render message as JSON
    }
    def searchVisitPlan = {
        List list = searchVisitPlanAction.execute(params, null)
        render searchVisitPlanAction.postCondition(null, list) as JSON
//        render(template: "searchVisitPlan")
    }
//    def getListForGrid = {
//        List list = searchVisitPlanAction.execute(params, null)
//        render searchVisitPlanAction.postCondition(null, list) as JSON
//    }

    def showViewVisitPlan = {
        ApplicationUser applicationUser = session?.applicationUser
        String recentDate = DateUtil.getCurrentDateFormatAsString()
        render(template: '/visitPlan/showViewVisitPlan', model: [applicationUser: applicationUser,recentDate: recentDate])
    }
    def popUpEmployeeNameList = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = searchVisitPlanAction.searchList(params, applicationUser)
        render list as JSON
    }
    def popupEmployeeListPanel = {
        render(view: 'popUpEmployeeList')
    }
    def jsonEmployeeList = {
        ApplicationUser applicationUser = session?.applicationUser
        Map map = [:]
        List data = (List)searchVisitPlanAction.popupsearchList(params, applicationUser)
        map.put("aaData", data)
        render map as JSON
    }

    def rptVisitPlan = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._file = "listReport/visitPlan.jasper"
        ApplicationUser applicationUser=session?.applicationUser
        String username= applicationUser.username

        params.put('username',username)
        params.put('fromDate', params.fromDate)
        params.put('toDate', params.toDate)
        params.put('pin', params.pin)
        params.put('ename', params.ename)

        params._name = "VisitPlan" + "_" + DateUtil.now()

        def reportDefTemp = new JasperReportDef(name: params._file,
                parameters: params,
                fileFormat: JasperExportFormat.PDF_FORMAT)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDefTemp)
        } catch (Exception ex) {
            render ex.message
        }
        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        response.contentType = "application/pdf"   // msword; pdf; richtext; html
        response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");  // inline; attachment
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }


}
