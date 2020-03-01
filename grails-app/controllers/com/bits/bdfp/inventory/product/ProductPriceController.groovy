package com.bits.bdfp.inventory.product

import com.bits.bdfp.customer.customermaster.ListCustomerAutoCompleteByEnterpriseAction
import com.bits.bdfp.customer.customermaster.ListCustomerByEnterpriseAction
import com.bits.bdfp.customer.customermaster.ListNegotiatedCustomerByEnterpriseAction
import com.bits.bdfp.geolocation.territoryconfiguration.SearchTerritoryByEnterpriseAction
import com.bits.bdfp.inventory.product.productprice.ActivateProductPriceAction
import com.bits.bdfp.inventory.product.productprice.CreateProductPriceAction
import com.bits.bdfp.inventory.product.productprice.DeleteProductPriceAction
import com.bits.bdfp.inventory.product.productprice.FlexListPriceNameByPricingTypeAction
import com.bits.bdfp.inventory.product.productprice.InactivateProductPriceAction
import com.bits.bdfp.inventory.product.productprice.ListCustomerForUpdateAction
import com.bits.bdfp.inventory.product.productprice.ListFinishProductWithCategoryAction
import com.bits.bdfp.inventory.product.productprice.ListProductPriceAction
import com.bits.bdfp.inventory.product.productprice.ListProductPriceForUpdateAction
import com.bits.bdfp.inventory.product.productprice.ReadProductPriceCommonDataAction
import com.bits.bdfp.inventory.product.productprice.SearchProductPriceListAction
import com.bits.bdfp.inventory.product.productprice.UpdateProductPriceAction
import com.bits.bdfp.inventory.product.productprice.ReadProductPriceAction
import com.bits.bdfp.inventory.product.productprice.SearchProductPriceAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.commons.CommonConstants
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class ProductPriceController {

    @Autowired
    private CreateProductPriceAction createProductPriceAction
    @Autowired
    private UpdateProductPriceAction updateProductPriceAction
    @Autowired
    private ListProductPriceAction listProductPriceAction
    @Autowired
    private DeleteProductPriceAction deleteProductPriceAction
    @Autowired
    private ReadProductPriceAction readProductPriceAction
    @Autowired
    private SearchProductPriceAction searchProductPriceAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListFinishProductWithCategoryAction listFinishProductWithCategoryAction
    @Autowired
    ListCustomerByEnterpriseAction listCustomerByEnterpriseAction
    @Autowired
    ListCustomerAutoCompleteByEnterpriseAction listCustomerAutoCompleteByEnterpriseAction
    @Autowired
    FlexListPriceNameByPricingTypeAction flexListPriceNameByPricingTypeAction
    @Autowired
    SearchProductPriceListAction searchProductPriceListAction
    @Autowired
    ActivateProductPriceAction  activateProductPriceAction
    @Autowired
    InactivateProductPriceAction inactivateProductPriceAction
    @Autowired
    ReadProductPriceCommonDataAction readProductPriceCommonDataAction
    @Autowired
    ListProductPriceForUpdateAction listProductPriceForUpdateAction
    @Autowired
    SearchTerritoryByEnterpriseAction searchTerritoryByEnterpriseAction
    @Autowired
    ListCustomerForUpdateAction listCustomerForUpdateAction
    @Autowired
    ListNegotiatedCustomerByEnterpriseAction listNegotiatedCustomerByEnterpriseAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listProductPriceAction.execute(params, null)
        render listProductPriceAction.postCondition(null, list) as JSON
    }

    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        Map enterpriseList = ["results": list, "total": list.size()]
        render(template: "/product/productPrice/show", model: [productPricingTypeList: ProductPricingType.findAllByIsActive(true), list: list, enterpriseList: enterpriseList as JSON])
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = (Message) createProductPriceAction.execute(params, applicationUser)
        render message as JSON
    }

    def edit = {
//        render readProductPriceAction.execute(params, null) as JSON
        ApplicationUser applicationUser = session?.applicationUser
        render(view: "/product/productPrice/editPrice", model: [productPricingTypeList: ProductPricingType.findAllByIsActive(true)])
    }

    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = (Message) updateProductPriceAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        ProductPrice productPrice = deleteProductPriceAction.execute(params, null);
        Message message = null
        if (productPrice) {
            int rowCount = (int) deleteProductPriceAction.preCondition(null, productPrice);
            if (rowCount > 0) {
                message = deleteProductPriceAction.getSuccessMessageForUI(productPrice, deleteProductPriceAction.SUCCESS_DELETE);
            } else {
                message = deleteProductPriceAction.getErrorMessageForUI(productPrice, deleteProductPriceAction.FAIL_DELETE);
            }
        } else {
            message = deleteProductPriceAction.getErrorMessageForUI(productPrice, deleteProductPriceAction.ALREADY_DELETED);
        }
        render message as JSON;
    }

    def search = {
        ProductPrice productPrice = searchProductPriceAction.execute(params.fieldName, params.fieldValue)
        if (productPrice) {
            render productPrice as JSON
        } else {
            render ''
        }
    }

    def finishProductListForPricing = {
        Map result = listFinishProductWithCategoryAction.execute(params, null)
        render(template: "/product/productPrice/priceSetup", model: [productList: result.productList, categoryList: result.categoryList])
    }

    def customerAutoCompleteByEnterPrise = {
        render listNegotiatedCustomerByEnterpriseAction.execute(params,null) as JSON
    }

    def popupCustomerListPanel = {
        List list = listNegotiatedCustomerByEnterpriseAction.execute(params,null)
        render(view: '/product/productPrice/popupCustomerList', model: [aaData: list as JSON])
    }

    def flexListPriceNameByPriceType = {
        List result = (List) flexListPriceNameByPricingTypeAction.execute(params, null)
//        Map priceNameList = ["results": result, "total": result.size()]
        render result as JSON
    }

    def searchProductPriceName = {
        if(params.dateEffectiveFrom){
            Date startDate = DateUtil.getSimpleDate(params.dateEffectiveFrom)
            params.dateEffectiveFrom = DateUtil.getFlexibleDateFormatAsString(startDate, CommonConstants.DATE_FORMAT_Y_M_D);
        }

        if(params.dateEffectiveTo){
            Date endDate = DateUtil.getSimpleDate(params.dateEffectiveTo);
            params.dateEffectiveTo = DateUtil.getFlexibleDateFormatAsString(endDate, CommonConstants.DATE_FORMAT_Y_M_D);
        }
        List list = searchProductPriceListAction.execute(params, null)
        render searchProductPriceListAction.postCondition(null, list) as JSON
    }

    def productPriceDetails = {
        Map commonData = (Map) readProductPriceCommonDataAction.execute(params, null)
        List savedPriceList = (List) listProductPriceForUpdateAction.execute(params, null)
        params.put("businessUnitId", commonData.businessUnitId)
        params.put("enterpriseId", commonData.enterpriseId)
        Map result = (Map) listFinishProductWithCategoryAction.execute(params, null)
        List territoryList = (List) searchTerritoryByEnterpriseAction.execute(params, null)
        render(view: "/product/productPrice/priceDetails", model: [commonData: commonData, savedPriceList: savedPriceList,
                productList: result.productList, categoryList: result.categoryList, territoryList: territoryList as JSON])
    }

    def activateProductNameList = {
        ApplicationUser applicationUser = session?.applicationUser
        params.put("userUpdatedId", applicationUser.id)
        Message message = activateProductPriceAction.execute(params, null)
        render message as JSON
    }

    def inactivateProductNameList = {
        ApplicationUser applicationUser = session?.applicationUser
        params.put("userUpdatedId", applicationUser.id)
        Message message = inactivateProductPriceAction.execute(params, null)
        render message as JSON
    }

    def listCustomer = {
        List list = listCustomerForUpdateAction.execute(params, null)
        render listCustomerForUpdateAction.postCondition(null, list) as JSON
    }
}
