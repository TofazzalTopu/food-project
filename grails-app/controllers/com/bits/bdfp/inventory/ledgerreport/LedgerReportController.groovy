package com.bits.bdfp.inventory.ledgerreport

import com.bits.bdfp.accounts.ChartOfAccounts
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.ListForChartOfAccountsChildAction
import com.bits.bdfp.accounts.SubLedger
import com.bits.bdfp.accounts.journal.ListPostfixCodeJournalAction
import com.bits.bdfp.accounts.journal.ListPrefixCodeJournalAction
//import com.bits.bdfp.reports.LedgerReportAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.commons.CommonConstants
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.beans.factory.annotation.Autowired

class LedgerReportController {

    @Autowired
    ListPrefixCodeJournalAction listPrefixCodeJournalAction

    @Autowired
    ListPostfixCodeJournalAction listPostfixCodeJournalAction

    @Autowired
    ListForChartOfAccountsChildAction coaOfAccountsChildAction

    /*@Autowired
    LedgerReportAction ledgerReportAction
*/
    def jasperService
    static defaultAction = "show"
    def show = {
        List coaList = coaOfAccountsChildAction.execute(params,null)
        render(view: "show", model: [coaList: coaList as JSON, coaListSize:coaList? coaList.size():0])
        //render(view: "show", model: [coaList: ChartOfAccounts.list(sort: "chartOfAccountName", order: "asc")])
    }
/*

    def generateLedger = {
        Message message = new Message();
        String js;

        List list = ledgerReportAction.getLedgerReport(params, null)
        if (list.size() > 0) {
            Map map = ledgerReportAction.checkPreCondition(params, null)
            chain(controller: 'docuJasper', action: 'index', model: [data: list], params: map.params)
        } else {
            render ("No Data Found")
        }
        js = "<script>parent.MessageRenderer.render(${message as JSON})</script>";
        render js
    }
*/


    def printLedger = {

        try {

            params.COMMON_DIR = servletContext.getRealPath("/") + "reports/template/"
//        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/ledgerReport/"
            params._file = "ledgerReport/rptLedgerReport.jasper"

            params._name = "LedgerReport_" + DateUtil.now()
            ApplicationUser applicationUser = session?.applicationUser
            params.put('username', applicationUser.username)

            ChartOfAccounts chartOfAccounts = ChartOfAccounts.get(Long.parseLong(params.chartOfAccountsId))
            if (chartOfAccounts) {
                params.coaCode = chartOfAccounts.chartOfAccountCodeUser
                params.coaName = chartOfAccounts.chartOfAccountName
            } else {
                params.coaCode = ""
                params.coaName = ""
            }


            params.dateRange = "From:" + params.ledgerDateFrom + " To:" + params.ledgerDateTo

            Date startDate = DateUtil.getSimpleDate(params.ledgerDateFrom)
            params.ledgerDateFrom = DateUtil.getFlexibleDateFormatAsString(startDate, CommonConstants.DATE_FORMAT_Y_M_D);

            Date endDate = DateUtil.getSimpleDate(params.ledgerDateTo);
            params.ledgerDateTo = DateUtil.getFlexibleDateFormatAsString(endDate, CommonConstants.DATE_FORMAT_Y_M_D);


            params.postfixCode = params.postfixCode
            params.prefixCode = params.prefixCode
            def reportDef = new JasperReportDef(name: params._file,
                    parameters: params,
                    fileFormat: JasperExportFormat.PDF_FORMAT)

            ByteArrayOutputStream byteArrayOutputStream = null
            byteArrayOutputStream = jasperService.generateReport(reportDef)
            /* try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception ex) {
            render ex.message
        }*/
            OutputStream os = response.outputStream
            response.contentLength = byteArrayOutputStream.size()
            response.contentType = "application/pdf"   // msword; pdf; richtext; html
            response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");
            // inline; attachment
            os << byteArrayOutputStream.toByteArray()

            os.flush()
            os.close()
        } catch (Exception e) {
            throw RuntimeException(e.message);
            //e.printStackTrace();
        }
    }


    def listPrefixCode=
    {
        render listPrefixCodeJournalAction.execute(params, null) as JSON
    }

    def listPostfixCode=
    {
        render listPostfixCodeJournalAction.execute(params, null) as JSON
    }
}