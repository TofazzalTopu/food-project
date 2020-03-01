package com.bits.bdfp.report

import com.bits.bdfp.reports.SearchReportInfoAction
import com.docu.common.Message
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class BranchStockReportController {

    def jasperService
    static defaultAction = "show"

    @Autowired
    private SearchReportInfoAction searchReportInfoAction

    def show = {

        render(view:"show")

      /*  Calendar cal = Calendar.getInstance();

        cal.setTime(time);
        int dt=cal.get(Calendar.DATE);
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int minutes = cal.get(Calendar.MINUTE);

        int totalTimeInMinutes = 60 * hours + minutes;

        cal.set(Calendar.MONTH,6);
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));

        Date lastDayOfMonth = cal.getTime();

      //  Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, -1);
        //cal.set(Calendar.YEAR, ANY_YEAR);
        cal.set(Calendar.DAY_OF_MONTH, 1);// This is necessary to get proper results
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        cal.getTime();
*/
        //Integer currentMonthMaxDay = new Date().lengthOfMonth();
    }

    def fetchDistributionPoint = {
        //List list = searchReportInfoAction.executeDistributionPoint(params, null)
        List list = searchReportInfoAction.executeDistributionPoint()
        render list as JSON
    }


    def generateBranchStockReport = {
        Calendar cal = Calendar.getInstance();
        String mnthParam = params.monthYr
        Date oldMnth = Date.parse('MM-yyyy', mnthParam)
        String newMonth = oldMnth.format('yyyy-MM')

        cal.setTime(oldMnth);
        cal.add(cal.MONTH, -1);
        int dt=cal.get(cal.MONTH);

        //cal.set(dt, cal.getActualMaximum(dt));

        int d=cal.getActualMaximum(dt);
        cal.getTime();
       

        Message message = new Message();
        String js;

        List list = searchReportInfoAction.executeBranchStockReport(params, null)
        if (list.size() > 0) {
            Map map = searchReportInfoAction.checkCondition1(params, null)
            chain(controller: 'docuJasper', action: 'index', model: [data: list], params: map.params)
        } else {
            render ("No Data Found")
        }
        js = "<script>parent.MessageRenderer.render(${message as JSON})</script>";
        render js
    }
}
