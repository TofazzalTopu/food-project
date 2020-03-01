package com.bits.bdfp.setup.salestarget.salesheadbyvolume

import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.setup.salestarget.SalesHeadFinishProduct
import com.bits.bdfp.setup.salestarget.YearlySalesTargetByVolume
import com.bits.bdfp.util.ApplicationConstants
import com.docu.commons.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.setup.salestarget.SalesHeadByVolume
import com.bits.bdfp.setup.salestarget.SalesHeadByVolumeService
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService


@Component("createSalesHeadByVolumeAction")
class CreateSalesHeadByVolumeAction extends Action{
    public static final Log log = LogFactory.getLog(CreateSalesHeadByVolumeAction.class)
    private final String MESSAGE_HEADER = 'New Sales Head By Volume'
    private final String MESSAGE_SUCCESS = 'Sales Head By Volume Created Successfully'

    @Autowired
    SalesHeadByVolumeService salesHeadByVolumeService
    
    @Autowired
    SpringSecurityService springSecurityService
    

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            Map mapInstance = [:]
            List<SalesHeadFinishProduct> salesHeadFinishProductList = new ArrayList<SalesHeadFinishProduct>()
            ApplicationUser applicationUser = (ApplicationUser) springSecurityService?.getCurrentUser()
            int itemCount = Integer.parseInt(params.itemCount)
            if(itemCount <= 0){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "No Product Item Selected")
            }
            SalesHeadByVolume salesHeadByVolume = new SalesHeadByVolume()

            salesHeadByVolume.properties = params
            
            salesHeadByVolume.userCreated = applicationUser
            salesHeadByVolume.isActive = true
            if(salesHeadByVolume.targetYear){
                salesHeadByVolume.startDate = DateUtil.getSimpleDateWithFormat('01-01-' + salesHeadByVolume.targetYear.toString(), ApplicationConstants.DATE_FORMAT)
                salesHeadByVolume.endDate = DateUtil.getSimpleDateWithFormat('31-12-' + salesHeadByVolume.targetYear.toString(), ApplicationConstants.DATE_FORMAT)
            }
            if (!salesHeadByVolume.validate()) {
                return this.getValidationErrorMessage(salesHeadByVolume)
            }

            if(salesHeadByVolume.targetYear < DateUtil.getCurrentSystemYear()){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "You can't set previous year target")
            }
            if(SalesHeadByVolume.findByTargetYear(salesHeadByVolume.targetYear)){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "This year is already defined. Please Choose a Valid Value")
            }
            if(YearlySalesTargetByVolume.findByEmployeeAndTargetYear(salesHeadByVolume.employee, salesHeadByVolume.targetYear)){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "The Employee already has a target for the selected year. Please select a valid employee")
            }

            for(int i = 0; i < itemCount; i++){
                SalesHeadFinishProduct salesHeadFinishProduct = new SalesHeadFinishProduct()
                salesHeadFinishProduct.finishProduct = FinishProduct.get(Long.parseLong(params["salesHeadFinishProduct[" + i.toString() + "].finishProductId"].toString()))
                salesHeadFinishProduct.quantity = Integer.parseInt(params["salesHeadFinishProduct[" + i.toString() + "].quantity"].toString())
                salesHeadFinishProduct.salesHeadByVolume = salesHeadByVolume
                salesHeadFinishProduct.userCreated = applicationUser
                if(!salesHeadFinishProduct.validate()){
                    return this.getValidationErrorMessage(salesHeadFinishProduct)
                }
                salesHeadFinishProductList.add(salesHeadFinishProduct)
            }
            mapInstance.put("salesHeadByVolume", salesHeadByVolume)
            mapInstance.put("salesHeadFinishProductList", salesHeadFinishProductList)
            salesHeadByVolumeService.createSalesHead(mapInstance)
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