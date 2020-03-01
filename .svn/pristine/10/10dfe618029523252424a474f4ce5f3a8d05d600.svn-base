package com.bits.bdfp.promotionsetup

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.warehouse.FinishGoodStock
import com.bits.bdfp.inventory.warehouse.SubWarehouse
import com.bits.bdfp.promotion.Promotion
import com.bits.bdfp.promotion.PromotionPackage
import com.bits.bdfp.promotion.PromotionPackageCustomers
import com.bits.bdfp.promotion.PromotionPackageProducts
import com.bits.bdfp.promotion.PromotionPackagePromotionalProducts
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.web.json.JSONArray
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("createBonusPromotionAction")
class CreateBonusPromotionAction extends Action {
    public static final Log log = LogFactory.getLog(CreateBonusPromotionAction.class)
    private final String MESSAGE_HEADER = 'Setup Bonus Promotion'
    private final String MESSAGE_SUCCESS = 'Bonus Promotion Created Successfully'
    private final String MESSAGE_ERROR = 'Bonus Promotion Does not Created.'

    Message message

    @Autowired
    PromotionSetupService promotionSetupService

    @Autowired
    SpringSecurityService springSecurityService


    public Object preCondition(def params, def object) {
        try {
            PromotionPackage promotionPackage = object.promotionPackage
            List<PromotionPackageCustomers> packageCustomersList = object.packageCustomersList
            List<PromotionPackageProducts> packageProductsList = object.packageProductsList
            List<PromotionPackagePromotionalProducts> packagePromotionalProductsList = object.packagePromotionalProductsList

            if(!promotionPackage.validate()){
                message = this.getValidationErrorMessage(promotionPackage)
                return message
            }

            if(packageCustomersList.size()>0){
                packageCustomersList.each {
                    if(!it.validate()){
                        message = this.getValidationErrorMessage(it)
                        return message
                    }
                }
            }

            if(packageProductsList.size()>0){
                packageProductsList.each {
                    if(!it.validate()){
                        message = this.getValidationErrorMessage(it)
                        return message
                    }
                }
            }

            if(packagePromotionalProductsList.size()>0){
                packagePromotionalProductsList.each {
                    if(!it.validate()){
                        message = this.getValidationErrorMessage(it)
                        return message
                    }
                }
            }

            if(!message){
                message = this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)
                return message
            }

            return message
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, ex.message)
        }
    }

    public Object execute(def params, def object) {
        try {
            String customerIds = params.customerIds
            Promotion promotion = Promotion.read(Long.parseLong(params.promotionId))
            List<PromotionPackageCustomers> packageCustomersList = []
            List<PromotionPackageProducts> packageProductsList = []
            List<PromotionPackagePromotionalProducts> packagePromotionalProductsList = []
            ApplicationUser applicationUser = (ApplicationUser) object

            PromotionPackage promotionPackage = new PromotionPackage()
            promotionPackage.promotion = promotion
            promotionPackage.packageName = params.packageName
            promotionPackage.discountAmount = params.discountAmount?Double.parseDouble(params.discountAmount):0
            promotionPackage.remarks = params.remarks
            promotionPackage.isActive= true
            promotionPackage.userCreated = applicationUser
            promotionPackage.dateCreated = new Date()

            String[] ids = null
            if(customerIds){
                ids = customerIds.split(",")
            }

            if(ids.length>0){
                ids.each{
                    PromotionPackageCustomers promotionPackageCustomers = new PromotionPackageCustomers()
                    promotionPackageCustomers.customer = CustomerMaster.read(Long.parseLong(it))
                    promotionPackageCustomers.promotionPackage = promotionPackage
                    promotionPackageCustomers.isActive = true
                    promotionPackageCustomers.userCreated = applicationUser
                    promotionPackageCustomers.dateCreated = new Date()
                    packageCustomersList.add(promotionPackageCustomers)
                }
            }

            JSONArray productDetailsList = new JSONArray(params.productDetails)
            JSONArray promotionalProductDetailsList = new JSONArray(params.promotionalProductDetails)

            if(productDetailsList.size()>0){
                productDetailsList.each {
                    PromotionPackageProducts promotionPackageProducts = new PromotionPackageProducts()
                    promotionPackageProducts.promotionPackage = promotionPackage
                    promotionPackageProducts.product = FinishProduct.read(Long.parseLong(it.productId))
                    promotionPackageProducts.stockId = FinishGoodStock.read(Long.parseLong(it.stockId))
                    promotionPackageProducts.purchaseQuantity = Integer.parseInt(it.purchaseQuantity)
                    promotionPackageProducts.quantityLimit = Integer.parseInt(it.quantityLimit)
                    promotionPackageProducts.productRate = Float.parseFloat(it.productRate)
                    promotionPackageProducts.isActive = true
                    promotionPackageProducts.userCreated = applicationUser
                    promotionPackageProducts.dateCreated = new Date()
                    packageProductsList.add(promotionPackageProducts)
                }
            }

            if(promotionalProductDetailsList.size()>0){
                promotionalProductDetailsList.each {
                    PromotionPackagePromotionalProducts promotionPackagePromotionalProducts = new PromotionPackagePromotionalProducts()
                    promotionPackagePromotionalProducts.promotionPackage = promotionPackage
                    promotionPackagePromotionalProducts.product = FinishProduct.read(Long.parseLong(it.productId))
                    promotionPackagePromotionalProducts.stockId = FinishGoodStock.read(Long.parseLong(it.stockId))
                    promotionPackagePromotionalProducts.subWarehouse = SubWarehouse.read(Long.parseLong(it.inventoryId))
                    promotionPackagePromotionalProducts.bonusQuantity = Integer.parseInt(it.bonusQuantity)
                    promotionPackagePromotionalProducts.productRate = Float.parseFloat(it.productRate)
                    promotionPackagePromotionalProducts.isActive = true
                    promotionPackagePromotionalProducts.userCreated = applicationUser
                    promotionPackagePromotionalProducts.dateCreated = new Date()
                    packagePromotionalProductsList.add(promotionPackagePromotionalProducts)
                }
            }

            Map map = [:]
            map.put('promotionPackage', promotionPackage)
            map.put('packageCustomersList', packageCustomersList)
            map.put('packageProductsList', packageProductsList)
            map.put('packagePromotionalProductsList', packagePromotionalProductsList)

            message = this.preCondition(params, map)
            if (message.type == 1) {
                int noOfRows = promotionSetupService.create(map)
                if (noOfRows > 0) {
                    message = this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)
                } else {
                    message = this.getMessage(MESSAGE_HEADER, Message.ERROR, MESSAGE_ERROR)
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}