package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.customermaster.ListSubordinateEmployeeByApplicationUserAction
import com.bits.bdfp.setup.salestarget.monthlysalestargetbyamount.CreateMonthlySalesTargetByAmountAction
import com.bits.bdfp.setup.salestarget.monthlysalestargetbyamount.DeleteMonthlySalesTargetByAmountAction
import com.bits.bdfp.setup.salestarget.monthlysalestargetbyamount.ListCustomerDailySalesTargetAction
import com.bits.bdfp.setup.salestarget.monthlysalestargetbyamount.ListMonthWiseYearlyTargetAction
import com.bits.bdfp.setup.salestarget.monthlysalestargetbyamount.ListMonthlySalesTargetByAmountAction
import com.bits.bdfp.setup.salestarget.monthlysalestargetbyamount.ListSalesManForTargetSetupAction
import com.bits.bdfp.setup.salestarget.monthlysalestargetbyamount.ListSubordinateAndSalesManForTargetSetupAction
import com.bits.bdfp.setup.salestarget.monthlysalestargetbyamount.ListSubordinateMonthLySalesTargetAction
import com.bits.bdfp.setup.salestarget.monthlysalestargetbyamount.UpdateMonthlySalesTargetByAmountAction
import com.bits.bdfp.setup.salestarget.monthlysalestargetbyamount.ReadMonthlySalesTargetByAmountAction
import com.bits.bdfp.setup.salestarget.monthlysalestargetbyamount.SearchMonthlySalesTargetByAmountAction
import com.bits.bdfp.setup.salestarget.monthlysalestargetbyamount.YearlySalesTargetByEmployeeAction
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Message
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class MonthlySalesTargetByAmountController {

    @Autowired
    private CreateMonthlySalesTargetByAmountAction createMonthlySalesTargetByAmountAction
    @Autowired
    private UpdateMonthlySalesTargetByAmountAction updateMonthlySalesTargetByAmountAction
    @Autowired
    private ListMonthlySalesTargetByAmountAction listMonthlySalesTargetByAmountAction
    @Autowired
    private DeleteMonthlySalesTargetByAmountAction deleteMonthlySalesTargetByAmountAction
    @Autowired
    private ReadMonthlySalesTargetByAmountAction readMonthlySalesTargetByAmountAction
    @Autowired
    private SearchMonthlySalesTargetByAmountAction searchMonthlySalesTargetByAmountAction
    @Autowired
    private ListMonthWiseYearlyTargetAction listMonthWiseYearlyTargetAction
    @Autowired
    YearlySalesTargetByEmployeeAction yearlySalesTargetByEmployeeAction
    @Autowired
    ListSubordinateEmployeeByApplicationUserAction listSubordinateEmployeeByApplicationUserAction
    @Autowired
    ListSalesManForTargetSetupAction listSalesManForTargetSetupAction
    @Autowired
    ListSubordinateMonthLySalesTargetAction listSubordinateMonthLySalesTargetAction
    @Autowired
    ListCustomerDailySalesTargetAction listCustomerDailySalesTargetAction
    @Autowired
    ListSubordinateAndSalesManForTargetSetupAction listSubordinateAndSalesManForTargetSetupAction
    SpringSecurityService springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
        Object objList = listMonthlySalesTargetByAmountAction.execute(params, null)
        render listMonthlySalesTargetByAmountAction.postCondition(objList, null) as JSON
    }

    def show = {
        int currentYear = DateUtil.getCurrentSystemYear()
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        if(!applicationUser.customerMasterId){
            render(view: "/salestarget/monthlySalesTargetByAmount/unAuthorized");
            return
        }
        CustomerMaster currentEmployee = CustomerMaster.get(applicationUser.customerMasterId)
        if(currentEmployee.masterType.id != ApplicationConstants.EMPLOYEE_MASTER_TYPE_ID){
            render(view: "/salestarget/monthlySalesTargetByAmount/unAuthorized");
            return
        }
        List<YearlySalesTargetByAmount> yearlySalesTargetByAmountList = new ArrayList<YearlySalesTargetByAmount>()
        List<SalesHead> salesHeadList = SalesHead.findAllByEmployeeAndTargetYearGreaterThanEquals(currentEmployee, currentYear)
        if(salesHeadList.size() == 0){
            yearlySalesTargetByAmountList = YearlySalesTargetByAmount.findAllByEmployeeAndTargetYearGreaterThanEquals(currentEmployee, currentYear)
        }
        render(view: "/salestarget/monthlySalesTargetByAmount/show", model: [yearlySalesTargetByAmountList: yearlySalesTargetByAmountList, salesHeadList: salesHeadList])

    }

    def create = {
        Message message = createMonthlySalesTargetByAmountAction.execute(params, null)
        render message as JSON
    }

    def edit = {
        Map data = (Map) readMonthlySalesTargetByAmountAction.execute(params, null)
        render data as JSON
    }

    def update = {
        Message message = updateMonthlySalesTargetByAmountAction.execute(params, null)
        render message as JSON
    }

    def delete = {
        Message message = deleteMonthlySalesTargetByAmountAction.execute(params, null)
        render message as JSON
    }

    def search = {
        MonthlySalesTargetByAmount monthlySalesTargetByAmount = (MonthlySalesTargetByAmount) searchMonthlySalesTargetByAmountAction.execute(params, null)
        if (monthlySalesTargetByAmount) {
            render monthlySalesTargetByAmount as JSON
        } else {
            render ''
        }
    }

    def showMonthlyTargetByCurrentUser = {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        if(!applicationUser.customerMasterId){
            render("Employee is not assigned to current user");
            return
        }
        List monthWiseYearlyTargetList = (List) listMonthWiseYearlyTargetAction.execute(params, null)
        BigDecimal yearlyTarget = (BigDecimal) yearlySalesTargetByEmployeeAction.execute(params, null)
        String[] monthList = ['Jan','Feb','Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
        int targetYear = Integer.parseInt(params.targetYear)
        int editableMonthFrom = 0
        Date editableDateFrom = new Date()
        if(targetYear == DateUtil.getCurrentSystemYear()){
            editableMonthFrom = editableDateFrom.getMonth() + 1
        }

        GregorianCalendar mycal = new GregorianCalendar(Integer.parseInt(params.targetYear), Calendar.FEBRUARY, 1);
        int daysInFeb = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

        render(template: "/salestarget/monthlySalesTargetByAmount/monthDetails", model: [monthWiseYearlyTargetList: monthWiseYearlyTargetList, monthList: monthList, yearlyTarget: yearlyTarget, editableMonthFrom: editableMonthFrom, editableDateFrom: editableDateFrom, daysInFeb: daysInFeb])
    }

    def listSubordinateEmployees = {
        Map result = (Map) listSubordinateEmployeeByApplicationUserAction.execute(params, null)
        render result as JSON
    }

    def listSalesManForTargetSetup = {
        List result = (List) listSalesManForTargetSetupAction.execute(params, null)
        render result as JSON
    }

    def showUpdate = {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        if(!applicationUser.customerMasterId){
            render(view: "/salestarget/monthlySalesTargetByAmount/unAuthorized");
            return
        }
        CustomerMaster currentEmployee = CustomerMaster.get(applicationUser.customerMasterId)
        if(currentEmployee.masterType.id != ApplicationConstants.EMPLOYEE_MASTER_TYPE_ID){
            render(view: "/salestarget/monthlySalesTargetByAmount/unAuthorized");
            return
        }
        int currentYear = DateUtil.getCurrentSystemYear()
        List<YearlySalesTargetByAmount> yearlySalesTargetByAmountList = YearlySalesTargetByAmount.findAllByEmployeeAndTargetYearGreaterThanEquals(currentEmployee, currentYear)
        render(view: "/salestarget/monthlySalesTargetByAmount/update", model: [yearlySalesTargetByAmountList: yearlySalesTargetByAmountList])
    }

    def showMonthlyTargetByCurrentUserForUpdate = {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        if(!applicationUser.customerMasterId){
            render("Employee is not assigned to current user");
            return
        }
        List monthWiseYearlyTargetList = (List) listMonthWiseYearlyTargetAction.execute(params, null)
        Float yearlyTarget = (Float) yearlySalesTargetByEmployeeAction.execute(params, null)
        String[] monthList = ['Jan','Feb','Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
        int targetYear = Integer.parseInt(params.targetYear)

        int editableMonthFrom = 0
        Date editableDateFrom = new Date()
        if(targetYear == DateUtil.getCurrentSystemYear()){
            editableMonthFrom = editableDateFrom.getMonth() + 1
        }

        GregorianCalendar mycal = new GregorianCalendar(Integer.parseInt(params.targetYear), Calendar.FEBRUARY, 1);
        int daysInFeb = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

        YearlySalesTargetByAmount yearlySalesTargetByAmount = YearlySalesTargetByAmount.findWhere(employee: CustomerMaster.get(applicationUser.customerMasterId), targetYear: Integer.parseInt(params.targetYear), isActive: true)
        render(template: "/salestarget/monthlySalesTargetByAmount/editMonthWiseDetails", model: [monthWiseYearlyTargetList: monthWiseYearlyTargetList, monthList: monthList, yearlyTarget: yearlyTarget, editableMonthFrom: editableMonthFrom, editableDateFrom: editableDateFrom, daysInFeb: daysInFeb, yearlySalesTargetByAmount: yearlySalesTargetByAmount])
    }

    def listSubordinateMonthLySalesTarget = {
        Object objList =listSubordinateMonthLySalesTargetAction.execute(params, null)
        render listSubordinateMonthLySalesTargetAction.postCondition(objList, null) as JSON
    }

    def listCustomerDailySalesTarget = {
        List result = (List) listCustomerDailySalesTargetAction.execute(params, null)
        render result as JSON
    }

    def listSubordinateAndSalesManForTargetSetup = {
        render listSubordinateAndSalesManForTargetSetupAction.execute(params, null) as JSON
    }
}
