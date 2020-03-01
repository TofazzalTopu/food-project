package com.bits.bdfp.setup.salestarget.salesheadbyvolume

import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.setup.salestarget.SalesHeadFinishProduct
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


@Component("updateSalesHeadByVolumeAction")
class UpdateSalesHeadByVolumeAction extends Action {
    public static final Log log = LogFactory.getLog(UpdateSalesHeadByVolumeAction.class)
    private final String MESSAGE_HEADER = 'Update Sales Head By Volume'
    private final String MESSAGE_SUCCESS = 'Sales Head By Volume Updated Successfully'

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
            SalesHeadByVolume newSalesHeadByVolume = new SalesHeadByVolume()
            SalesHeadByVolume currentSalesHeadByVolume = salesHeadByVolumeService.read(Long.parseLong(params.id))
            newSalesHeadByVolume.properties = params
            newSalesHeadByVolume.userCreated = applicationUser
            currentSalesHeadByVolume.userUpdated = applicationUser

//            Float previousTarget = salesHeadByVolumeService.previousMonthTarget(newSalesHeadByVolume.targetYear, newSalesHeadByVolume.employee.id)
//            if(previousTarget > newSalesHead.targetAmount){
//                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Please set a higher target")
//            }

            if(currentSalesHeadByVolume.employee != newSalesHeadByVolume.employee){
                for(int i = 0; i < itemCount; i++){
                    SalesHeadFinishProduct salesHeadFinishProduct = new SalesHeadFinishProduct()
                    salesHeadFinishProduct.finishProduct = FinishProduct.get(Long.parseLong(params["salesHeadFinishProduct[" + i.toString() + "].finishProductId"].toString()))
                    salesHeadFinishProduct.userCreated = applicationUser
                    salesHeadFinishProduct.quantity = Integer.parseInt(params["salesHeadFinishProduct[" + i.toString() + "].quantity"].toString())
                    salesHeadFinishProduct.salesHeadByVolume = newSalesHeadByVolume
                    if(!salesHeadFinishProduct.validate()){
                        return this.getValidationErrorMessage(salesHeadFinishProduct)
                    }
                    salesHeadFinishProductList.add(salesHeadFinishProduct)
                }
                salesHeadByVolumeService.replaceSalesHeadByVolume(currentSalesHeadByVolume, newSalesHeadByVolume, salesHeadFinishProductList)
            }else{
                for(int i = 0; i < itemCount; i++){
                    SalesHeadFinishProduct salesHeadFinishProduct = null
                    String salesHeadFinishProductId = params["salesHeadFinishProduct[" + i.toString() + "].id"].toString()
                    if(salesHeadFinishProductId == ""){
                        salesHeadFinishProduct = new SalesHeadFinishProduct()
                        salesHeadFinishProduct.finishProduct = FinishProduct.get(Long.parseLong(params["salesHeadFinishProduct[" + i.toString() + "].finishProductId"].toString()))
                        salesHeadFinishProduct.salesHeadByVolume = currentSalesHeadByVolume
                        salesHeadFinishProduct.userCreated = applicationUser
                    }else{
                        salesHeadFinishProduct = SalesHeadFinishProduct.get(Long.parseLong(salesHeadFinishProductId))
                        salesHeadFinishProduct.userUpdated = applicationUser
                    }
                    salesHeadFinishProduct.quantity = Integer.parseInt(params["salesHeadFinishProduct[" + i.toString() + "].quantity"].toString())
                    if(!salesHeadFinishProduct.validate()){
                        return this.getValidationErrorMessage(salesHeadFinishProduct)
                    }
                    salesHeadFinishProductList.add(salesHeadFinishProduct)
                }
                salesHeadByVolumeService.updateSalesHeadByVolume(currentSalesHeadByVolume, salesHeadFinishProductList)
            }

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
