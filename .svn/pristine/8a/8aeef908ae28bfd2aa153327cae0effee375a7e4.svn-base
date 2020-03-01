package com.bits.bdfp.setup.salestarget.monthlysalestargetbyvolume

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.setup.salestarget.DailySalesTargetByVolume
import com.bits.bdfp.setup.salestarget.DailySalesTargetFinishProduct
import com.bits.bdfp.setup.salestarget.MonthlySalesTargetFinishProduct
import com.bits.bdfp.setup.salestarget.YearlySalesTargetByVolume
import com.bits.bdfp.setup.salestarget.YearlySalesTargetFinishProduct
import com.docu.commons.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.setup.salestarget.MonthlySalesTargetByVolume
import com.bits.bdfp.setup.salestarget.MonthlySalesTargetByVolumeService


import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService


@Component("createMonthlySalesTargetByVolumeAction")
class CreateMonthlySalesTargetByVolumeAction extends Action{
    public static final Log log = LogFactory.getLog(CreateMonthlySalesTargetByVolumeAction.class)
    private final String MESSAGE_HEADER = 'New Monthly Sales Target By Volume'
    private final String MESSAGE_SUCCESS = 'Monthly Sales Target By Volume Created Successfully'
    Message message

    @Autowired
    MonthlySalesTargetByVolumeService monthlySalesTargetByVolumeService
    
    @Autowired
    SpringSecurityService springSecurityService
    

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            Map mapInstance = [:]
            List<FinishProduct> finishProductList = new ArrayList<FinishProduct>()
            List<YearlySalesTargetByVolume> subordinateYearlySalesTargetByVolumeList = new ArrayList<YearlySalesTargetByVolume>()
            List<YearlySalesTargetFinishProduct> subordinateYearlySalesTargetFinishProductList = new ArrayList<YearlySalesTargetFinishProduct>()
            List<MonthlySalesTargetByVolume> monthlySalesTargetByVolumeList = new ArrayList<MonthlySalesTargetByVolume>()
            List<MonthlySalesTargetFinishProduct> monthlySalesTargetFinishProductList = new ArrayList<MonthlySalesTargetFinishProduct>()
            List<MonthlySalesTargetByVolume> subordinateMonthlySalesTargetByVolumeList = new ArrayList<MonthlySalesTargetByVolume>()
            List<MonthlySalesTargetFinishProduct> subordinateMonthlySalesTargetFinishProductList = new ArrayList<MonthlySalesTargetFinishProduct>()
            List<DailySalesTargetByVolume> dailySalesTargetByVolumeList = new ArrayList<DailySalesTargetByVolume>()
            List<DailySalesTargetFinishProduct> dailySalesTargetFinishProductList = new ArrayList<DailySalesTargetFinishProduct>()
            ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
            CustomerMaster currentEmployee = CustomerMaster.get(applicationUser.customerMasterId)
            int targetYear   = Integer.parseInt(params.targetYear)

            int productCount = Integer.parseInt(params.productCount)
            for(int productIndex = 0; productIndex < productCount; productIndex++){
                String productId = params["productId_" + productIndex]
                FinishProduct finishProduct = FinishProduct.get(Long.parseLong(productId))
                finishProductList.add(finishProduct)
            }
            List<YearlySalesTargetFinishProduct> yearlySalesTargetFinishProductList = new ArrayList<YearlySalesTargetFinishProduct>()
            YearlySalesTargetByVolume yearlySalesTargetByVolume = YearlySalesTargetByVolume.findByEmployeeAndTargetYear(currentEmployee, targetYear)
            if(!yearlySalesTargetByVolume){
                yearlySalesTargetByVolume = new YearlySalesTargetByVolume(employee: currentEmployee, targetYear: targetYear)
                yearlySalesTargetByVolume.startDate = DateUtil.getSimpleDateWithFormat("01-01-" + targetYear.toString(), 'dd-MM-yyyy')
                yearlySalesTargetByVolume.endDate = DateUtil.getSimpleDateWithFormat("31-12-" + targetYear.toString(), 'dd-MM-yyyy')
                yearlySalesTargetByVolume.userCreated = applicationUser
                finishProductList.each { finishProduct ->
                    int targetQuantity = Integer.parseInt(params["yearlyTarget_" + finishProduct.id.toString()].toString())
                    YearlySalesTargetFinishProduct yearlySalesTargetFinishProduct = new YearlySalesTargetFinishProduct(yearlySalesTargetByVolume: yearlySalesTargetByVolume, finishProduct: finishProduct, quantity: targetQuantity)
                    yearlySalesTargetFinishProduct.userCreated = applicationUser
                    yearlySalesTargetFinishProductList.add(yearlySalesTargetFinishProduct)
                }
            }else{
                yearlySalesTargetByVolume.userUpdated = applicationUser
                finishProductList.each { finishProduct ->
                    int targetQuantity = Integer.parseInt(params["yearlyTarget_" + finishProduct.id.toString()].toString())
                    YearlySalesTargetFinishProduct yearlySalesTargetFinishProduct = YearlySalesTargetFinishProduct.findWhere(yearlySalesTargetByVolume: yearlySalesTargetByVolume, finishProduct: finishProduct)
                    if(yearlySalesTargetFinishProduct){
                        yearlySalesTargetFinishProduct.quantity =  targetQuantity
                        yearlySalesTargetFinishProduct.userUpdated = applicationUser
                    }else{
                        yearlySalesTargetFinishProduct = new YearlySalesTargetFinishProduct(yearlySalesTargetByVolume: yearlySalesTargetByVolume, finishProduct: finishProduct, quantity: targetQuantity)
                        yearlySalesTargetFinishProduct.userCreated = applicationUser
                    }
                    yearlySalesTargetFinishProductList.add(yearlySalesTargetFinishProduct)
                }
            }

            if(params.isSalesManIncluded == "1") {
                yearlySalesTargetByVolume.isSalesManIncluded = true
            }else{
                yearlySalesTargetByVolume.isSalesManIncluded = false
            }
            yearlySalesTargetByVolume.isActive = true

            if (!yearlySalesTargetByVolume.validate()) {
                message = this.getValidationErrorMessage(yearlySalesTargetByVolume)
                throw new RuntimeException(message.messageBody[0].toString())
            }
            // Set Current User Monthly Target
            for(int j= 0; j <12; j++){
                int targetMonth = j+1
                MonthlySalesTargetByVolume selfMonthlySalesTargetByVolume = MonthlySalesTargetByVolume.findWhere(employee: currentEmployee, targetYear: targetYear, targetMonth: targetMonth)
                if(selfMonthlySalesTargetByVolume){
                    selfMonthlySalesTargetByVolume.userUpdated = applicationUser
                    selfMonthlySalesTargetByVolume.isActive = true
                    finishProductList.each { finishProduct ->
                        int targetQuantity = Integer.parseInt(params["selfMonthWiseTarget_" + finishProduct.id.toString() + "_" + j + "_targetQuantity"].toString())
                        MonthlySalesTargetFinishProduct monthlySalesTargetFinishProduct = MonthlySalesTargetFinishProduct.findWhere(monthlySalesTargetByVolume: selfMonthlySalesTargetByVolume, finishProduct: finishProduct)
                        if(monthlySalesTargetFinishProduct){
                            monthlySalesTargetFinishProduct.quantity =  targetQuantity
                            monthlySalesTargetFinishProduct.userUpdated = applicationUser
                        }else{
                            monthlySalesTargetFinishProduct = new MonthlySalesTargetFinishProduct(monthlySalesTargetByVolume: selfMonthlySalesTargetByVolume, finishProduct: finishProduct, quantity: targetQuantity)
                            monthlySalesTargetFinishProduct.userCreated = applicationUser
                        }
                        monthlySalesTargetFinishProductList.add(monthlySalesTargetFinishProduct)
                    }
                }else{
                    selfMonthlySalesTargetByVolume = new MonthlySalesTargetByVolume(targetYear: targetYear, employee: currentEmployee, targetMonth: targetMonth, isActive: true)
                    selfMonthlySalesTargetByVolume.yearlySalesTargetByVolume = yearlySalesTargetByVolume
                    selfMonthlySalesTargetByVolume.startDate =  DateUtil.firstDayOfMonth(targetYear, targetMonth)
                    selfMonthlySalesTargetByVolume.endDate = DateUtil.lastDayOfMonth(targetYear, targetMonth)
                    selfMonthlySalesTargetByVolume.userCreated = applicationUser
                    finishProductList.each { finishProduct ->
                        int targetQuantity = Integer.parseInt(params["selfMonthWiseTarget_" + finishProduct.id.toString() + "_" + j + "_targetQuantity"].toString())
                        MonthlySalesTargetFinishProduct monthlySalesTargetFinishProduct = new MonthlySalesTargetFinishProduct(monthlySalesTargetByVolume: selfMonthlySalesTargetByVolume, finishProduct: finishProduct, quantity: targetQuantity)
                        monthlySalesTargetFinishProduct.userCreated = applicationUser
                        monthlySalesTargetFinishProductList.add(monthlySalesTargetFinishProduct)
                    }
                }
                if (!selfMonthlySalesTargetByVolume.validate()) {
                    message = this.getValidationErrorMessage(selfMonthlySalesTargetByVolume)
                    throw new RuntimeException(message.messageBody[0].toString())
                }
                monthlySalesTargetByVolumeList.add(selfMonthlySalesTargetByVolume)
            }

            int employeeCount = Integer.parseInt(params.employeeCount)

            // Set month wise & date wise target for each subordinate employee
            for(int i = 0; i < employeeCount; i++){
                String subordinateId = params["employeeId_" + i.toString()].toString()
                CustomerMaster subordinateEmployee = CustomerMaster.get(Long.parseLong(subordinateId))
                BigDecimal subordinateYearlyTarget = 0.00
                // Set month wise target for each subordinate employee
                for(int k = 0; k < 12; k++){
                    int targetMonth = k+1
                    MonthlySalesTargetByVolume monthlySalesTargetByVolume = new MonthlySalesTargetByVolume(targetYear: targetYear, employee: subordinateEmployee, isActive: true)
                    monthlySalesTargetByVolume.targetMonth = targetMonth
                    monthlySalesTargetByVolume.startDate = DateUtil.firstDayOfMonth(targetYear, targetMonth)
                    monthlySalesTargetByVolume.endDate = DateUtil.lastDayOfMonth(targetYear, targetMonth)
                    monthlySalesTargetByVolume.userCreated = applicationUser
                    monthlySalesTargetByVolume.yearlySalesTargetByVolume = yearlySalesTargetByVolume
                    if (!monthlySalesTargetByVolume.validate()) {
                        message = this.getValidationErrorMessage(monthlySalesTargetByVolume)
                        throw new RuntimeException(message.messageBody[0].toString())
                    }
                    subordinateMonthlySalesTargetByVolumeList.add(monthlySalesTargetByVolume)

                    finishProductList.each { finishProduct ->
                        int targetQuantity = Integer.parseInt(params["subordinates_" + finishProduct.id.toString() + "_" + subordinateId + "_month_" + k.toString() + "_targetQuantity"].toString())
                        MonthlySalesTargetFinishProduct monthlySalesTargetFinishProduct = new MonthlySalesTargetFinishProduct(monthlySalesTargetByVolume: monthlySalesTargetByVolume, finishProduct: finishProduct, quantity: targetQuantity)
                        monthlySalesTargetFinishProduct.userCreated = applicationUser
                        subordinateMonthlySalesTargetFinishProductList.add(monthlySalesTargetFinishProduct)
                    }

                    /*** Calculate Date wise target and Set date wise target for each subordinate employee  ************/
                    int maxDayOfMonth = DateUtil.countMaxDaysOfMonth(targetYear, targetMonth)
                    for(int day = 1; day < maxDayOfMonth; day++){
                        DailySalesTargetByVolume dailySalesTargetByVolume  = new DailySalesTargetByVolume(employee: subordinateEmployee, isActive: true)
                        dailySalesTargetByVolume.targetDate = DateUtil.getSimpleDateWithFormat( day.toString() + "-" + targetMonth.toString() + "-" + targetYear.toString(), 'dd-MM-yyyy')
                        dailySalesTargetByVolume.userCreated = applicationUser
                        dailySalesTargetByVolume.yearlySalesTargetByVolume = yearlySalesTargetByVolume

                        if (!dailySalesTargetByVolume.validate()) {
                            message = this.getValidationErrorMessage(dailySalesTargetByVolume)
                            throw new RuntimeException(message.messageBody[0].toString())
                        }

                        finishProductList.each { finishProduct ->
                            int targetQuantity = Integer.parseInt(params["subordinates_" + finishProduct.id.toString() + "_" + subordinateId + "_month_" + k.toString() + "_targetQuantity"].toString())
                            int perDayTarget = Math.floor((targetQuantity/maxDayOfMonth) + 0.5)
                            DailySalesTargetFinishProduct dailySalesTargetFinishProduct = new DailySalesTargetFinishProduct(dailySalesTargetByVolume: dailySalesTargetByVolume, finishProduct: finishProduct, quantity: perDayTarget)
                            dailySalesTargetFinishProduct.userCreated = applicationUser
                            dailySalesTargetFinishProductList.add(dailySalesTargetFinishProduct)
                        }

                        dailySalesTargetByVolumeList.add(dailySalesTargetByVolume)
                    }
                    /**********************************************************/
                    /*********** Set Last day Target Data **************/
                    DailySalesTargetByVolume dailySalesTargetByVolume  = new DailySalesTargetByVolume(employee: subordinateEmployee, isActive: true)
                    dailySalesTargetByVolume.targetDate = DateUtil.getSimpleDateWithFormat( maxDayOfMonth.toString() + "-" + targetMonth.toString() + "-" + targetYear.toString(), 'dd-MM-yyyy')
                    dailySalesTargetByVolume.userCreated = applicationUser
                    dailySalesTargetByVolume.yearlySalesTargetByVolume = yearlySalesTargetByVolume

                    if (!dailySalesTargetByVolume.validate()) {
                        message = this.getValidationErrorMessage(dailySalesTargetByVolume)
                        throw new RuntimeException(message.messageBody[0].toString())
                    }

                    finishProductList.each { finishProduct ->
                        int targetQuantity = Integer.parseInt(params["subordinates_" + finishProduct.id.toString() + "_" + subordinateId + "_month_" + k.toString() + "_targetQuantity"].toString())
                        int perDayTarget = Math.floor((targetQuantity/maxDayOfMonth) + 0.5)
                        int lastDayTarget = targetQuantity - (perDayTarget*(maxDayOfMonth -1))
                        DailySalesTargetFinishProduct dailySalesTargetFinishProduct = new DailySalesTargetFinishProduct(dailySalesTargetByVolume: dailySalesTargetByVolume, finishProduct: finishProduct, quantity: lastDayTarget)
                        dailySalesTargetFinishProduct.userCreated = applicationUser
                        dailySalesTargetFinishProductList.add(dailySalesTargetFinishProduct)
                    }

                    dailySalesTargetByVolumeList.add(dailySalesTargetByVolume)
                    /********************************************************/
                }

                YearlySalesTargetByVolume subordinateYearlySalesTargetByVolume = YearlySalesTargetByVolume.findWhere(targetYear: targetYear, employee: subordinateEmployee)
                if(subordinateYearlySalesTargetByVolume){
                    subordinateYearlySalesTargetByVolume.userUpdated = applicationUser
                    finishProductList.each { finishProduct ->
                        int yearlyTargetQuantity = 0
                        for(int k = 0; k < 12; k++){
                            yearlyTargetQuantity += Integer.parseInt(params["subordinates_" + finishProduct.id.toString() + "_" + subordinateId + "_month_" + k.toString() + "_targetQuantity"].toString())
                        }
                        YearlySalesTargetFinishProduct subordinateYearlySalesTargetFinishProduct = YearlySalesTargetFinishProduct.findWhere(yearlySalesTargetByVolume: subordinateYearlySalesTargetByVolume, finishProduct: finishProduct)
                        if(subordinateYearlySalesTargetFinishProduct){
                            subordinateYearlySalesTargetFinishProduct.userUpdated = applicationUser
                        }else {
                            subordinateYearlySalesTargetFinishProduct = new YearlySalesTargetFinishProduct(yearlySalesTargetByVolume: subordinateYearlySalesTargetByVolume, finishProduct: finishProduct, userCreated: applicationUser)
                        }
                        subordinateYearlySalesTargetFinishProduct.quantity = yearlyTargetQuantity
                        subordinateYearlySalesTargetFinishProductList.add(subordinateYearlySalesTargetFinishProduct)
                    }
                }else {
                    subordinateYearlySalesTargetByVolume = new YearlySalesTargetByVolume(targetYear: targetYear, employee: subordinateEmployee, isActive: true, userCreated: applicationUser)
                    subordinateYearlySalesTargetByVolume.yearlySalesTargetByVolume = yearlySalesTargetByVolume
                    subordinateYearlySalesTargetByVolume.startDate = yearlySalesTargetByVolume.startDate
                    subordinateYearlySalesTargetByVolume.endDate = yearlySalesTargetByVolume.endDate
                    subordinateYearlySalesTargetByVolume.isSalesManIncluded = false
                    finishProductList.each { finishProduct ->
                        int yearlyTargetQuantity = 0
                        for(int k = 0; k < 12; k++){
                            yearlyTargetQuantity += Integer.parseInt(params["subordinates_" + finishProduct.id.toString() + "_" + subordinateId + "_month_" + k.toString() + "_targetQuantity"].toString())
                        }
                        YearlySalesTargetFinishProduct subordinateYearlySalesTargetFinishProduct = new YearlySalesTargetFinishProduct(yearlySalesTargetByVolume: subordinateYearlySalesTargetByVolume, finishProduct: finishProduct, userCreated: applicationUser)
                        subordinateYearlySalesTargetFinishProduct.quantity = yearlyTargetQuantity
                        subordinateYearlySalesTargetFinishProductList.add(subordinateYearlySalesTargetFinishProduct)
                    }
                }

                if (!subordinateYearlySalesTargetByVolume.validate()) {
                    message = this.getValidationErrorMessage(subordinateYearlySalesTargetByVolume)
                    throw new RuntimeException(message.messageBody[0].toString())
                }

                subordinateYearlySalesTargetByVolumeList.add(subordinateYearlySalesTargetByVolume)
            }

            mapInstance.put("yearlySalesTargetByVolume", yearlySalesTargetByVolume)
            mapInstance.put("yearlySalesTargetFinishProductList", yearlySalesTargetFinishProductList)
            mapInstance.put("subordinateYearlySalesTargetByVolumeList", subordinateYearlySalesTargetByVolumeList)
            mapInstance.put("subordinateYearlySalesTargetFinishProductList", subordinateYearlySalesTargetFinishProductList)
            mapInstance.put("monthlySalesTargetByVolumeList", monthlySalesTargetByVolumeList)
            mapInstance.put("monthlySalesTargetFinishProductList", monthlySalesTargetFinishProductList)
            mapInstance.put("subordinateMonthlySalesTargetByVolumeList", subordinateMonthlySalesTargetByVolumeList)
            mapInstance.put("subordinateMonthlySalesTargetFinishProductList", subordinateMonthlySalesTargetFinishProductList)
            mapInstance.put("dailySalesTargetByVolumeList", dailySalesTargetByVolumeList)
            mapInstance.put("dailySalesTargetFinishProductList", dailySalesTargetFinishProductList)

            monthlySalesTargetByVolumeService.createNewSalesTarget(mapInstance)
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