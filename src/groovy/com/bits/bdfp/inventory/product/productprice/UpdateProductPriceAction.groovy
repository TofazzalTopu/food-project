package com.bits.bdfp.inventory.product.productprice

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.geolocation.TerritorySubArea
import com.bits.bdfp.inventory.product.CustomerProductPrice
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.product.PricingCategory
import com.bits.bdfp.inventory.product.ProductPrice
import com.bits.bdfp.inventory.product.ProductPriceProduct
import com.bits.bdfp.inventory.product.ProductPriceService
import com.bits.bdfp.inventory.product.ProductPricingType
import com.bits.bdfp.inventory.product.TerritorySubAreaProductPrice
import com.bits.bdfp.settings.BusinessUnitConfiguration
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateProductPriceAction")
class UpdateProductPriceAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    Message message
    @Autowired
    ProductPriceService productPriceService

    public Object preCondition(Object params, Object object) {
        try {
            if(!params.productPricingType){
                return this.getMessage("Product Price", Message.ERROR, "Price List Type is not selected")
            }
            if(!params.enterpriseConfiguration){
                return this.getMessage("Product Price", Message.ERROR, "Enterprise is not selected")
            }
            if(!params.businessUnitConfiguration){
                return this.getMessage("Product Price", Message.ERROR, "Business Unit is not selected")
            }
            if(!params.name){
                return this.getMessage("Product Price", Message.ERROR, "Price List Name is blank")
            }
            if(!params.dateEffectiveFrom){
                return this.getMessage("Product Price", Message.ERROR, "Effective Date From is blank")
            }
            return this.getMessage("Product Price", Message.SUCCESS, this.SUCCESS_SAVE)
        } catch (Exception ex) {
            log.error(ex.message)
            throw ex
        }
    }

    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            ProductPrice productPrice = productPriceService.read(Long.parseLong(params.productPriceId))
            ProductPriceProduct productPriceProduct = null
            Map mapInstance = [:]
            TerritorySubAreaProductPrice territorySubAreaProductPrice = null
            CustomerProductPrice customerProductPrice = null
            message = this.preCondition(params, null)
            if (message.type == 1) {
                Map basePrice = [:]
                Map vatPercent = [:]
                Map vatAmount = [:]
                params.each { param ->
                    if (param.key.toString().contains('basePrice')) {
                        basePrice.put(param.key, param.value)
                    } else if(param.key.toString().contains('vatPercent')){
                        vatPercent.put(param.key, param.value)
                    } else if(param.key.toString().contains('vatAmount')){
                        vatAmount.put(param.key, param.value)
                    }
                }
                if(basePrice.isEmpty()) {
                    return this.getMessage(null, Message.ERROR, "No Product selected")
                }
                productPrice.userUpdated = applicationUser
                List<ProductPriceProduct> productPriceProductList = new ArrayList<ProductPriceProduct>()
                List<TerritorySubAreaProductPrice> territorySubAreaProductPriceList = new ArrayList<TerritorySubAreaProductPrice>()
                List<CustomerProductPrice> customerProductPriceList = new ArrayList<CustomerProductPrice>()
                basePrice.each{
                    String[] idContents = it.key.toString().split("_")
                    if(idContents.length > 2){
                        productPriceProduct = new ProductPriceProduct()
                        productPriceProduct.productPrice = productPrice
                        productPriceProduct.price = Float.parseFloat(it.value.toString())
                        String pricingCategoryId = idContents[1];
                        String finishProductId = idContents[2]
                        productPriceProduct.pricingCategory = PricingCategory.get(Long.parseLong(pricingCategoryId))
                        productPriceProduct.finishProduct = FinishProduct.get(Long.parseLong(finishProductId))
                        String vatPercentValue = vatPercent.get("vatPercent_" + idContents[1] + "_" + idContents[2])
                        String vatAmountValue = vatAmount.get("vatAmount_" + idContents[1] + "_" + idContents[2])
                        productPriceProduct.vatPercentage = Float.parseFloat(vatPercentValue)
                        if(productPriceProduct.vatPercentage == 0.00){
                            productPriceProduct.vatAmount = Float.parseFloat(vatAmountValue)
                        }else{
                            productPriceProduct.vatAmount = 0.00
                        }
                        productPriceProduct.isActive = true
                        productPriceProduct.userCreated = applicationUser
                        productPriceProduct.totalAmount = productPriceProduct.price + (productPriceProduct.price * productPriceProduct.vatPercentage)/100 + productPriceProduct.vatAmount

                        productPriceProductList.add(productPriceProduct)
                    }
                }
                if(params.productPricingType == ApplicationConstants.PRODUCT_PRICING_TYPE_STANDARD_ID.toString()){
                    TerritorySubArea territorySubArea = null
                    String[] geoLocationIds = params.geoLocationIds.split(",")
                    for(int i = 0; i < geoLocationIds.length ; i++){
                        if(geoLocationIds[i] != ''){
                            territorySubAreaProductPrice = new TerritorySubAreaProductPrice()
                            territorySubArea = TerritorySubArea.get(Long.parseLong(geoLocationIds[i]))
                            List<TerritorySubAreaProductPrice> existingTerritorySubAreaProductPriceList = TerritorySubAreaProductPrice.findAllByTerritorySubArea(territorySubArea)
                            for(int j = 0; j < existingTerritorySubAreaProductPriceList.size(); j++){
                                ProductPrice productPriceExisting = existingTerritorySubAreaProductPriceList[j].productPrice
                                if(productPrice.id == productPriceExisting.id){
                                    continue
                                }
                                if(productPriceExisting.enterpriseConfiguration == productPrice.enterpriseConfiguration){
                                    if(productPriceExisting.dateEffectiveTo){
                                        if(productPrice.dateEffectiveFrom >= productPriceExisting.dateEffectiveFrom && productPrice.dateEffectiveFrom <= productPriceExisting.dateEffectiveTo){
                                            throw new RuntimeException(territorySubArea.geoLocation + " conflicts for Price List Name: " + productPriceExisting.name)
                                        }
                                        if(productPrice.dateEffectiveTo){
                                            if(productPrice.dateEffectiveTo >= productPriceExisting.dateEffectiveFrom && productPrice.dateEffectiveTo <= productPriceExisting.dateEffectiveTo){
                                                throw new RuntimeException(territorySubArea.geoLocation + " conflicts for Price List Name: " + productPriceExisting.name)
                                            }
                                        }
                                    } else if(productPrice.dateEffectiveFrom >= productPriceExisting.dateEffectiveFrom){
                                        throw new RuntimeException(territorySubArea.geoLocation + " conflicts for Price List Name: " + productPriceExisting.name)
                                    }

                                    if(!productPrice.dateEffectiveTo && productPrice.dateEffectiveFrom <=  productPriceExisting.dateEffectiveFrom){
                                        throw new RuntimeException(territorySubArea.geoLocation + " conflicts for Price List Name: " + productPriceExisting.name)
                                    }
                                }
                            }
                            territorySubAreaProductPrice.territorySubArea = territorySubArea
                            territorySubAreaProductPrice.productPrice = productPrice
                            territorySubAreaProductPrice.userCreated = applicationUser
                            territorySubAreaProductPriceList.add(territorySubAreaProductPrice)
                        }
                    }
                } else if(params.productPricingType == ApplicationConstants.PRODUCT_PRICING_TYPE_NEGOTIATED_ID.toString()){
                    CustomerMaster customerMaster = null
                    String[] customerIds = params.customerIds.split(",")
                    for(int i = 0; i < customerIds.length ; i++){
                        if(customerIds[i] != ''){
                            customerProductPrice = new CustomerProductPrice()
                            customerMaster = CustomerMaster.get(Long.parseLong(customerIds[i]))
                            List<CustomerProductPrice> existingCustomerProductPriceList = CustomerProductPrice.findAllByCustomerMaster(customerMaster)
                            for(int j = 0; j < existingCustomerProductPriceList.size(); j++){
                                ProductPrice productPriceExisting = existingCustomerProductPriceList[j].productPrice
                                if(productPrice.id == productPriceExisting.id){
                                    continue
                                }
                                if(productPriceExisting.enterpriseConfiguration == productPrice.enterpriseConfiguration){
                                    if(productPriceExisting.dateEffectiveTo){
                                        if(productPrice.dateEffectiveFrom >= productPriceExisting.dateEffectiveFrom && productPrice.dateEffectiveFrom <= productPriceExisting.dateEffectiveTo){
                                            throw new RuntimeException("[" + customerMaster.code + "]" + customerMaster.name + " conflicts for Price List Name: " + productPriceExisting.name)
                                        }
                                        if(productPrice.dateEffectiveTo){
                                            if(productPrice.dateEffectiveTo >= productPriceExisting.dateEffectiveFrom && productPrice.dateEffectiveTo <= productPriceExisting.dateEffectiveTo){
                                                throw new RuntimeException("[" + customerMaster.code + "]" + customerMaster.name + " conflicts for Price List Name: " + productPriceExisting.name)
                                            }
                                        }
                                    } else if(productPrice.dateEffectiveFrom >= productPriceExisting.dateEffectiveFrom){
                                        throw new RuntimeException("[" + customerMaster.code + "]" + customerMaster.name + " conflicts for Price List Name: " + productPriceExisting.name)
                                    }

                                    if(!productPrice.dateEffectiveTo && productPrice.dateEffectiveFrom <=  productPriceExisting.dateEffectiveFrom){
                                        throw new RuntimeException("[" + customerMaster.code + "]" + customerMaster.name + " conflicts for Price List Name: " + productPriceExisting.name)
                                    }
                                }
                            }
                            customerProductPrice.customerMaster = customerMaster
                            customerProductPrice.productPrice = productPrice
                            customerProductPrice.userCreated = applicationUser
                            customerProductPriceList.add(customerProductPrice)
                        }
                    }
                }
                mapInstance.put(ProductPrice.class.simpleName, productPrice)
                mapInstance.put(ProductPriceProduct.class.simpleName, productPriceProductList)
                mapInstance.put("priceName", params.name)
                mapInstance.put("productPriceTypeId", params.productPricingType)
                if(params.productPricingType == ApplicationConstants.PRODUCT_PRICING_TYPE_STANDARD_ID.toString()){
                    if(territorySubAreaProductPriceList.size() <= 0){
                        return this.getMessage("Product Price", Message.ERROR, "Geo Location is not selected")
                    }
                    mapInstance.put(TerritorySubAreaProductPrice.class.simpleName, territorySubAreaProductPriceList)
                    if (productPriceService.updateStandardPrice(mapInstance, params)) {
                        return this.getMessage("Product Price", Message.SUCCESS, this.SUCCESS_UPDATE)
                    } else {
                        return this.getMessage("Product Price", Message.ERROR, this.FAIL_UPDATE)
                    }
                }
                else if(params.productPricingType == ApplicationConstants.PRODUCT_PRICING_TYPE_NEGOTIATED_ID.toString()){
                    if(customerProductPriceList.size() <= 0){
                        return this.getMessage("Product Price", Message.ERROR, "Customer is not selected")
                    }
                    mapInstance.put(CustomerProductPrice.class.simpleName, customerProductPriceList)
                    if (productPriceService.updateNegotiatedPrice(mapInstance, params)) {
                        return this.getMessage("Product Price", Message.SUCCESS, this.SUCCESS_UPDATE)
                    } else {
                        return this.getMessage("Product Price", Message.ERROR, this.FAIL_UPDATE)
                    }
                }
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Product Price", Message.ERROR, "Exception-${ex.message}")
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}