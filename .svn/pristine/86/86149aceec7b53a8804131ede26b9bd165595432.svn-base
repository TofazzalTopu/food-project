package com.bits.bdfp.setupbonus

import com.bits.bdfp.customer.CustomerCategory
import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.promotionsetup.CreateBonusPromotionAction
import com.bits.bdfp.promotionsetup.GetFactorySalableProductListAction
import com.bits.bdfp.promotionsetup.ListPromotionCustomerByCcAction
import com.bits.bdfp.promotionsetup.ListPromotionGeoAndCustomerByTerritoryAction
import com.bits.bdfp.promotionsetup.ListPromotionPtAndCustomerByGeoAction
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class SetupBonusPromotionController {
    @Autowired
    ListPromotionCustomerByCcAction listPromotionCustomerByCcAction
    @Autowired
    ListPromotionGeoAndCustomerByTerritoryAction listPromotionGeoAndCustomerByTerritoryAction
    @Autowired
    ListPromotionPtAndCustomerByGeoAction listPromotionPtAndCustomerByGeoAction
    @Autowired
    GetFactorySalableProductListAction getFactorySalableProductListAction
    @Autowired
    CreateBonusPromotionAction createBonusPromotionAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def show = {
        List<TerritoryConfiguration> territoryConfigurationList = TerritoryConfiguration.findAllByIsActive(true)
        List<CustomerCategory> customerCategoryList = CustomerCategory.list()

        List productList = getFactorySalableProductListAction.execute(params,null)

        render(view: "/setupBonusPromotion/show", model: [territoryConfigurationListSize: territoryConfigurationList.size(),
                                                          territoryConfigurationList:territoryConfigurationList as JSON,
                                                          customerCategoryListSize:customerCategoryList.size(),
                                                          customerCategoryList:customerCategoryList as JSON,
                                                          productList:productList])
    }

    def getCcWiseCustomers = {
        Map map = listPromotionCustomerByCcAction.execute(params, null)
        render map as JSON
    }

    def getTerritoryWiseGeoAndCustomers = {
        Map map = listPromotionGeoAndCustomerByTerritoryAction.execute(params, null)
        render map as JSON
    }

    def getGeoWiseTpAndCustomers = {
        Map map = listPromotionPtAndCustomerByGeoAction.execute(params, null)
        render map as JSON
    }

    def getPromotionalProductList = {
        List productList = getFactorySalableProductListAction.execute(params,null)
        render productList as JSON
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        render createBonusPromotionAction.execute(params,applicationUser) as JSON
    }
}
