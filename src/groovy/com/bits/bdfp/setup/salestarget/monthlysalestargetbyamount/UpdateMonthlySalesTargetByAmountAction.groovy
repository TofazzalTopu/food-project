package com.bits.bdfp.setup.salestarget.monthlysalestargetbyamount

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.setup.salestarget.DailySalesTargetByAmount
import com.bits.bdfp.setup.salestarget.YearlySalesTargetByAmount
import com.docu.commons.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.setup.salestarget.MonthlySalesTargetByAmount
import com.bits.bdfp.setup.salestarget.MonthlySalesTargetByAmountService
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService


@Component("updateMonthlySalesTargetByAmountAction")
class UpdateMonthlySalesTargetByAmountAction extends Action{
    public static final Log log = LogFactory.getLog(CreateMonthlySalesTargetByAmountAction.class)
    private final String MESSAGE_HEADER = 'Update Sales Target By Amount'
    private final String MESSAGE_SUCCESS = 'Sales Target By Amount Updated Successfully'
    Message message

    @Autowired
    MonthlySalesTargetByAmountService monthlySalesTargetByAmountService

    @Autowired
    SpringSecurityService springSecurityService


    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            Map mapInstance = [:]
            List<YearlySalesTargetByAmount> subordinateYearlySalesTargetByAmountList = new ArrayList<YearlySalesTargetByAmount>()
            List<MonthlySalesTargetByAmount> monthlySalesTargetByAmountList = new ArrayList<MonthlySalesTargetByAmount>()
            List<DailySalesTargetByAmount> dailySalesTargetByAmountList = new ArrayList<DailySalesTargetByAmount>()
            ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
            CustomerMaster currentEmployee = CustomerMaster.get(applicationUser.customerMasterId)
            int targetYear   = Integer.parseInt(params.targetYear)
            Float targetAmount = Float.parseFloat(params.yearlyTarget)
            YearlySalesTargetByAmount yearlySalesTargetByAmount = YearlySalesTargetByAmount.findWhere(targetYear: targetYear, employee: currentEmployee)
            if(!yearlySalesTargetByAmount){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Your Annual Sales Target is not available")
            }else{
                yearlySalesTargetByAmount.userUpdated = applicationUser
            }

            if(params.isSalesManIncluded == "1") {
                yearlySalesTargetByAmount.isSalesManIncluded = true
            }else{
                yearlySalesTargetByAmount.isSalesManIncluded = false
            }
            yearlySalesTargetByAmount.isActive = true

            if (!yearlySalesTargetByAmount.validate()) {
                message = this.getValidationErrorMessage(yearlySalesTargetByAmount)
                throw new RuntimeException(message.messageBody[0].toString())
            }
            // Set Current User Monthly Target
            for(int j= 0; j <12; j++){
                int targetMonth = j+1
                MonthlySalesTargetByAmount selfMonthlySalesTargetByAmount = MonthlySalesTargetByAmount.findWhere(targetYear: targetYear, employee: currentEmployee, targetMonth: targetMonth, isActive: true)
                if(!selfMonthlySalesTargetByAmount){
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Your Monthly Sales Target is not available")
                }else{
                    selfMonthlySalesTargetByAmount.targetAmount = Float.parseFloat(params.get("selfMonthWiseTarget_" + j.toString() + "_targetAmount"))
                    selfMonthlySalesTargetByAmount.userUpdated = applicationUser
                    monthlySalesTargetByAmountList.add(selfMonthlySalesTargetByAmount)
                }
            }

            int employeeCount = Integer.parseInt(params.employeeCount)

            // Set month wise & date wise target for each subordinate employee
            for(int i = 0; i < employeeCount; i++){
                CustomerMaster subordinateEmployee = CustomerMaster.get(Long.parseLong(params["subordinates_" + i + "_employeeId"].toString()))
                BigDecimal subordinateYearlyTarget = 0.00
                // Set month wise target for each subordinate employee
                for(int k = 0; k < 12; k++){
                    int targetMonth = k+1
                    MonthlySalesTargetByAmount monthlySalesTargetByAmount = new MonthlySalesTargetByAmount(targetYear: targetYear, employee: subordinateEmployee, isActive: true)
                    monthlySalesTargetByAmount.targetMonth = targetMonth
                    monthlySalesTargetByAmount.targetAmount = Float.parseFloat(params["subordinates_" + i.toString() + "_month_" + k + "_targetAmount"].toString())
                    monthlySalesTargetByAmount.startDate = DateUtil.firstDayOfMonth(targetYear, targetMonth)
                    monthlySalesTargetByAmount.endDate = DateUtil.lastDayOfMonth(targetYear, targetMonth)
                    monthlySalesTargetByAmount.userCreated = applicationUser
                    monthlySalesTargetByAmount.yearlySalesTargetByAmount = yearlySalesTargetByAmount
                    monthlySalesTargetByAmountList.add(monthlySalesTargetByAmount)

                    subordinateYearlyTarget += monthlySalesTargetByAmount.targetAmount
                    subordinateYearlyTarget = subordinateYearlyTarget.setScale(2, BigDecimal.ROUND_HALF_UP)

                    // Set date wise target for each subordinate employee
                    int maxDayOfMonth = DateUtil.countMaxDaysOfMonth(targetYear, targetMonth)
                    for(int day = 1; day <= maxDayOfMonth; day++){
                        DailySalesTargetByAmount dailySalesTargetByAmount  = new DailySalesTargetByAmount(employee: subordinateEmployee, isActive: true)
                        dailySalesTargetByAmount.targetDate = DateUtil.getSimpleDateWithFormat( day.toString() + "-" + targetMonth.toString() + "-" + targetYear.toString(), 'dd-MM-yyyy')
                        dailySalesTargetByAmount.targetAmount = Float.parseFloat(params["subordinates_" + i + "_month_" + k + "_" + day.toString() + "_targetAmount"].toString())
                        dailySalesTargetByAmount.userCreated = applicationUser
                        dailySalesTargetByAmount.yearlySalesTargetByAmount = yearlySalesTargetByAmount
                        dailySalesTargetByAmountList.add(dailySalesTargetByAmount)
                    }
                }

                YearlySalesTargetByAmount subordinateYearlySalesTargetByAmount = YearlySalesTargetByAmount.findWhere(targetYear: targetYear, employee: subordinateEmployee)
                if(subordinateYearlySalesTargetByAmount){
                    subordinateYearlySalesTargetByAmount.userUpdated  = applicationUser
                }else{
                    subordinateYearlySalesTargetByAmount = new YearlySalesTargetByAmount(targetYear: targetYear, employee: subordinateEmployee, isActive: true, userCreated: applicationUser)
                    subordinateYearlySalesTargetByAmount.yearlySalesTargetByAmount = yearlySalesTargetByAmount
                    subordinateYearlySalesTargetByAmount.startDate = yearlySalesTargetByAmount.startDate
                    subordinateYearlySalesTargetByAmount.endDate = yearlySalesTargetByAmount.endDate
                    subordinateYearlySalesTargetByAmount.isSalesManIncluded = false
                }
            }

            mapInstance.put("yearlySalesTargetByAmount", yearlySalesTargetByAmount)
            mapInstance.put("subordinateYearlySalesTargetByAmountList", subordinateYearlySalesTargetByAmountList)
            mapInstance.put("monthlySalesTargetByAmountList", monthlySalesTargetByAmountList)
            mapInstance.put("dailySalesTargetByAmountList", dailySalesTargetByAmountList)

            monthlySalesTargetByAmountService.updateSalesTarget(mapInstance, currentEmployee)
            return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}