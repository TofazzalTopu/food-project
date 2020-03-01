package com.bits.bdfp.inventory.product.productprice

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.geolocation.TerritorySubArea
import com.bits.bdfp.inventory.product.CustomerProductPrice
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.product.PricingCategory
import com.bits.bdfp.inventory.product.ProductPrice
import com.bits.bdfp.inventory.product.ProductPriceProduct
import com.bits.bdfp.inventory.product.ProductPriceService
import com.bits.bdfp.inventory.product.TerritorySubAreaProductPrice
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createProductPriceAction")
class CreateProductPriceAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    Message message
    @Autowired
    ProductPriceService productPriceService

    public Object preCondition(Object params, Object object) {
        try {
            ProductPrice productPrice = (ProductPrice) object
            if(!productPrice.validate()){
                return this.getValidationErrorMessage(productPrice)
            }
            if(productPrice.dateEffectiveTo && (productPrice.dateEffectiveFrom > productPrice.dateEffectiveTo)){
                return this.getMessage("Product Price", Message.SUCCESS, "'Effective Date To' can not be greater than 'Effective Date From'")
            }
            return this.getMessage("Product Price", Message.SUCCESS, this.SUCCESS_SAVE)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            ProductPrice productPrice = new ProductPrice()
            productPrice.properties = params.properties
            productPrice.userCreated = applicationUser
            productPrice.isActive = true
            ProductPriceProduct productPriceProduct = null
            Map mapInstance = [:]
            TerritorySubAreaProductPrice territorySubAreaProductPrice = null
            CustomerProductPrice customerProductPrice = null
            message = this.preCondition(params, productPrice)
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
                        productPriceProduct.totalAmount = productPriceProduct.price + (productPriceProduct.price * productPriceProduct.vatPercentage)/100 + productPriceProduct.vatAmount
                        productPriceProduct.isActive = true
                        productPriceProduct.userCreated = applicationUser
                        if (!productPriceProduct.validate()) {
                            message = this.getValidationErrorMessage(productPriceProduct)
                            throw new RuntimeException(message.messageBody[0].toString())
                        }
                        productPriceProductList.add(productPriceProduct)


                    }
                }

                if(productPrice.productPricingType.id == ApplicationConstants.PRODUCT_PRICING_TYPE_STANDARD_ID){
                    TerritorySubArea territorySubArea = null
                    String[] geoLocationIds = params.geoLocationIds.split(",")
                    for(int i = 0; i < geoLocationIds.length ; i++){
                        if(geoLocationIds[i] != ''){
                            territorySubAreaProductPrice = new TerritorySubAreaProductPrice()
                            territorySubArea = TerritorySubArea.get(Long.parseLong(geoLocationIds[i]))
                            List<TerritorySubAreaProductPrice> existingTerritorySubAreaProductPriceList = TerritorySubAreaProductPrice.findAllByTerritorySubArea(territorySubArea)
                            for(int j = 0; j < existingTerritorySubAreaProductPriceList.size(); j++){
                                ProductPrice productPriceExisting = existingTerritorySubAreaProductPriceList[j].productPrice
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
                            if (!territorySubAreaProductPrice.validate()) {
                                message = this.getValidationErrorMessage(territorySubAreaProductPrice)
                                throw new RuntimeException(message.messageBody[0].toString())
                            }
                            territorySubAreaProductPriceList.add(territorySubAreaProductPrice)
                        }
                    }
                } else if(productPrice.productPricingType.id == ApplicationConstants.PRODUCT_PRICING_TYPE_NEGOTIATED_ID){
                    String[] customerIds = params.customerIds.split(",")
                    CustomerMaster customerMaster = null
                    for(int i = 0; i < customerIds.length ; i++){
                        if(customerIds[i] != ''){
                            customerProductPrice = new CustomerProductPrice()
                            customerMaster = CustomerMaster.get(Long.parseLong(customerIds[i]))
                            List<CustomerProductPrice> existingCustomerProductPriceList = CustomerProductPrice.findAllByCustomerMaster(customerMaster)
                            for(int j = 0; j < existingCustomerProductPriceList.size(); j++){
                                ProductPrice productPriceExisting = existingCustomerProductPriceList[j].productPrice
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
                            if (!customerProductPrice.validate()) {
                                message = this.getValidationErrorMessage(customerProductPrice)
                                throw new RuntimeException(message.messageBody[0].toString())
                            }
                            customerProductPriceList.add(customerProductPrice)
                        }
                    }
                }
                mapInstance.put(ProductPrice.class.simpleName, productPrice)
                mapInstance.put(ProductPriceProduct.class.simpleName, productPriceProductList)

                if(productPrice.productPricingType.id == ApplicationConstants.PRODUCT_PRICING_TYPE_STANDARD_ID){
                    if(territorySubAreaProductPriceList.size() <= 0){
                        return this.getMessage("Product Price", Message.ERROR, "Geo Location is not selected")
                    }
                    mapInstance.put(TerritorySubAreaProductPrice.class.simpleName, territorySubAreaProductPriceList)
                    if (productPriceService.createStandardPrice(mapInstance)) {
                        return this.getMessage("Product Price", Message.SUCCESS, "Standard Product Price Created Successfully")
                    } else {
                        return this.getMessage("Product Price", Message.ERROR, this.FAIL_SAVE)
                    }
                }

                else if(productPrice.productPricingType.id == ApplicationConstants.PRODUCT_PRICING_TYPE_NEGOTIATED_ID){
                    if(customerProductPriceList.size() <= 0){
                        return this.getMessage("Product Price", Message.ERROR, "Customer is not selected")
                    }
                    mapInstance.put(CustomerProductPrice.class.simpleName, customerProductPriceList)
                    if (productPriceService.createNegotiatedPrice(mapInstance)) {
                        return this.getMessage("Product Price", Message.SUCCESS, "Negotiated Product Price Created Successfully")
                    } else {
                        return this.getMessage("Product Price", Message.ERROR, this.FAIL_SAVE)
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