package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.settings.ApplicationUserEnterprise
import com.bits.bdfp.setup.salestarget.dailysalestargetfinishproduct.ListDailySalesTargetFinishProductAction
import com.bits.bdfp.setup.salestarget.dailysalestargetfinishproduct.ListProductForDailySalesTargetAction
import com.bits.bdfp.setup.salestarget.dailysalestargetfinishproduct.ListSubordinateForDailyTargetAction
import com.bits.bdfp.setup.salestarget.dailysalestargetfinishproduct.UpdateDailySalesTargetFinishProductAction
import com.bits.bdfp.setup.salestarget.dailysalestargetfinishproduct.ReadDailySalesTargetFinishProductAction
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Message
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class DailySalesTargetFinishProductController {

    @Autowired
    private UpdateDailySalesTargetFinishProductAction updateDailySalesTargetFinishProductAction
    @Autowired
    private ListDailySalesTargetFinishProductAction listDailySalesTargetFinishProductAction
    @Autowired
    private ReadDailySalesTargetFinishProductAction readDailySalesTargetFinishProductAction
    @Autowired
    ListSubordinateForDailyTargetAction listSubordinateForDailyTargetAction
    @Autowired
    ListProductForDailySalesTargetAction listProductForDailySalesTargetAction
    @Autowired
    SpringSecurityService springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
        render listDailySalesTargetFinishProductAction.execute(params, null) as JSON
    }

    def show = {
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
        render(view: "/salestarget/dailySalesTargetFinishProduct/show", model: [yearlySalesTargetByVolumeList: yearlySalesTargetByVolumeList])
    }

    def update = {
        Message message = updateDailySalesTargetFinishProductAction.execute(params, null)
        render message as JSON
    }


    def listSubordinate = {
        render listSubordinateForDailyTargetAction.execute(params, null) as JSON
    }

    def listProduct = {
        render listProductForDailySalesTargetAction.execute(params, null) as JSON
    }

    def popupProductListPanel = {
        render(view: '/salestarget/dailySalesTargetFinishProduct/popUpProductList', model: [yearlySalesTargetByVolumeId: params.yearlySalesTargetByVolumeId, targetMonth: params.targetMonth, employeeId: params.employeeId])
    }

    def jsonProductList = {
        Map map = [:]
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        params.enterpriseId = ApplicationUserEnterprise.findAllByApplicationUser(applicationUser).first().enterpriseConfiguration.id
        List data = (List)listProductForDailySalesTargetAction.execute(params, null)
        map.put("aaData", data)
        render map as JSON
    }

    def getEditableMonthData = {
        YearlySalesTargetByVolume yearlySalesTargetByVolume = YearlySalesTargetByVolume.read(Long.parseLong(params.yearlySalesTargetByVolumeId))
        int editableMonthFrom = 0
        Date editableDateFrom = new Date()
        if(yearlySalesTargetByVolume.targetYear == DateUtil.getCurrentSystemYear()){
            editableMonthFrom = editableDateFrom.getMonth() + 1
        }
        Map result = [editableMonthFrom: editableMonthFrom]
        render result as JSON
    }
}
