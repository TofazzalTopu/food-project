package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.customermaster.ListSubordinateEmployeeByApplicationUserAction
import com.bits.bdfp.setup.salestarget.monthlysalestargetbyamount.ListSalesManForTargetSetupAction
import com.bits.bdfp.setup.salestarget.monthlysalestargetbyamount.ListSubordinateAndSalesManForTargetSetupAction
import com.bits.bdfp.setup.salestarget.monthlysalestargetbyvolume.CreateMonthlySalesTargetByVolumeAction
import com.bits.bdfp.setup.salestarget.monthlysalestargetbyvolume.DeleteMonthlySalesTargetByVolumeAction
import com.bits.bdfp.setup.salestarget.monthlysalestargetbyvolume.ListMonthlySalesTargetByVolumeAction
import com.bits.bdfp.setup.salestarget.monthlysalestargetbyvolume.ListSubordinateMonthLySalesTargetByVolumeAction
import com.bits.bdfp.setup.salestarget.monthlysalestargetbyvolume.UpdateMonthlySalesTargetByVolumeAction
import com.bits.bdfp.setup.salestarget.monthlysalestargetbyvolume.ReadMonthlySalesTargetByVolumeAction
import com.bits.bdfp.setup.salestarget.monthlysalestargetbyvolume.SearchMonthlySalesTargetByVolumeAction
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Message
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class MonthlySalesTargetByVolumeController {

    @Autowired
    private CreateMonthlySalesTargetByVolumeAction createMonthlySalesTargetByVolumeAction
    @Autowired
    private UpdateMonthlySalesTargetByVolumeAction updateMonthlySalesTargetByVolumeAction
    @Autowired
    private ListMonthlySalesTargetByVolumeAction listMonthlySalesTargetByVolumeAction
    @Autowired
    private DeleteMonthlySalesTargetByVolumeAction deleteMonthlySalesTargetByVolumeAction
    @Autowired
    private ReadMonthlySalesTargetByVolumeAction readMonthlySalesTargetByVolumeAction
    @Autowired
    private SearchMonthlySalesTargetByVolumeAction searchMonthlySalesTargetByVolumeAction
    @Autowired
    ListSubordinateEmployeeByApplicationUserAction listSubordinateEmployeeByApplicationUserAction
    @Autowired
    ListSalesManForTargetSetupAction listSalesManForTargetSetupAction
    @Autowired
    ListSubordinateAndSalesManForTargetSetupAction listSubordinateAndSalesManForTargetSetupAction
    @Autowired
    ListSubordinateMonthLySalesTargetByVolumeAction listSubordinateMonthLySalesTargetByVolumeAction
    @Autowired
    SpringSecurityService springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def show = {
        int currentYear = DateUtil.getCurrentSystemYear()
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        if(!applicationUser.customerMasterId){
            render(view: "/salestarget/monthlySalesTargetByVolume/unAuthorized");
            return
        }
        CustomerMaster currentEmployee = CustomerMaster.get(applicationUser.customerMasterId)
        if(currentEmployee.masterType.id != ApplicationConstants.EMPLOYEE_MASTER_TYPE_ID){
            render(view: "/salestarget/monthlySalesTargetByVolume/unAuthorized");
            return
        }
        List<YearlySalesTargetByVolume> yearlySalesTargetByVolumeList = new ArrayList<YearlySalesTargetByVolume>()
        List<SalesHeadByVolume> salesHeadByVolumeList = SalesHeadByVolume.findAllByEmployeeAndTargetYearGreaterThanEquals(currentEmployee, currentYear)
        if(salesHeadByVolumeList.size() == 0){
            yearlySalesTargetByVolumeList = YearlySalesTargetByVolume.findAllByEmployeeAndTargetYearGreaterThanEquals(currentEmployee, currentYear)
        }
        render(view: "/salestarget/monthlySalesTargetByVolume/show", model: [yearlySalesTargetByVolumeList: yearlySalesTargetByVolumeList, salesHeadByVolumeList: salesHeadByVolumeList])
    }

    def create = {
        Message message = createMonthlySalesTargetByVolumeAction.execute(params, null)
        render message as JSON
    }

    def edit = {
        Map data = (Map) readMonthlySalesTargetByVolumeAction.execute(params, null)
        render data as JSON
    }

    def update = {
        Message message = updateMonthlySalesTargetByVolumeAction.execute(params, null)
        render message as JSON
    }

    def delete = {
        Message message = deleteMonthlySalesTargetByVolumeAction.execute(params, null)
        render message as JSON
    }

    def search = {
        MonthlySalesTargetByVolume monthlySalesTargetByVolume = (MonthlySalesTargetByVolume) searchMonthlySalesTargetByVolumeAction.execute(params, null)
        if (monthlySalesTargetByVolume) {
            render monthlySalesTargetByVolume as JSON
        } else {
            render ''
        }
    }

    def showUpdate = {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        if(!applicationUser.customerMasterId){
            render(view: "/salestarget/monthlySalesTargetByVolume/unAuthorized");
            return
        }
        CustomerMaster currentEmployee = CustomerMaster.get(applicationUser.customerMasterId)
        if(currentEmployee.masterType.id != ApplicationConstants.EMPLOYEE_MASTER_TYPE_ID){
            render(view: "/salestarget/monthlySalesTargetByVolume/unAuthorized");
            return
        }
        int currentYear = DateUtil.getCurrentSystemYear()
        List<YearlySalesTargetByVolume> yearlySalesTargetByVolumeList = YearlySalesTargetByVolume.findAllByEmployeeAndTargetYearGreaterThanEquals(currentEmployee, currentYear)
        render(view: "/salestarget/monthlySalesTargetByVolume/update", model: [yearlySalesTargetByVolumeList: yearlySalesTargetByVolumeList])
    }

    def showMonthlyTargetByCurrentUser = {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        if(!applicationUser.customerMasterId){
            render("Employee is not assigned to current user");
            return
        }
        Map productWiseMonthlyTargetList = (Map) listMonthlySalesTargetByVolumeAction.execute(params, null)

        String[] monthList = ['Jan','Feb','Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
        int targetYear = Integer.parseInt(params.targetYear)
        int editableMonthFrom = 0
        Date editableDateFrom = new Date()
        if(targetYear == DateUtil.getCurrentSystemYear()){
            editableMonthFrom = editableDateFrom.getMonth() + 1
        }

        GregorianCalendar mycal = new GregorianCalendar(Integer.parseInt(params.targetYear), Calendar.FEBRUARY, 1);
        int daysInFeb = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

        render(template: "/salestarget/monthlySalesTargetByVolume/monthDetails", model: [productWiseMonthlyTargetList: productWiseMonthlyTargetList.monthWiseDataList, yearlyTargetQuantityList: productWiseMonthlyTargetList.yearlyTargetQuantityList, monthList: monthList, editableMonthFrom: editableMonthFrom, editableDateFrom: editableDateFrom, daysInFeb: daysInFeb])
    }

    def listSubordinateEmployees = {
        Map result = (Map) listSubordinateEmployeeByApplicationUserAction.execute(params, null)
        render result as JSON
    }

    def listSalesManForTargetSetup = {
        List result = (List) listSalesManForTargetSetupAction.execute(params, null)
        render result as JSON
    }

    def showMonthlyTargetByCurrentUserForUpdate = {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        if(!applicationUser.customerMasterId){
            render("Employee is not assigned to current user");
            return
        }
        Map productWiseMonthlyTargetList = (Map) listMonthlySalesTargetByVolumeAction.execute(params, null)
        String[] monthList = ['Jan','Feb','Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
        int targetYear = Integer.parseInt(params.targetYear)

        int editableMonthFrom = 0
        Date editableDateFrom = new Date()
        if(targetYear == DateUtil.getCurrentSystemYear()){
            editableMonthFrom = editableDateFrom.getMonth() + 1
        }

        GregorianCalendar mycal = new GregorianCalendar(Integer.parseInt(params.targetYear), Calendar.FEBRUARY, 1);
        int daysInFeb = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

        YearlySalesTargetByVolume yearlySalesTargetByVolume = YearlySalesTargetByVolume.findWhere(employee: CustomerMaster.get(applicationUser.customerMasterId), targetYear: Integer.parseInt(params.targetYear), isActive: true)
        render(template: "/salestarget/monthlySalesTargetByVolume/editMonthWiseDetails", model: [productWiseMonthlyTargetList: productWiseMonthlyTargetList.monthWiseDataList, yearlyTargetQuantityList: productWiseMonthlyTargetList.yearlyTargetQuantityList, monthList: monthList, editableMonthFrom: editableMonthFrom, editableDateFrom: editableDateFrom, daysInFeb: daysInFeb, yearlySalesTargetByVolume: yearlySalesTargetByVolume])
    }

    def listSubordinateAndSalesManForTargetSetup = {
        render listSubordinateAndSalesManForTargetSetupAction.execute(params, null) as JSON
    }

    def listSubordinateMonthLySalesTarget = {
        render listSubordinateMonthLySalesTargetByVolumeAction.execute(params, null) as JSON
    }

}
