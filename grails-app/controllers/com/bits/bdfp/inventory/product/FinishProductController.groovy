package com.bits.bdfp.inventory.product

import com.bits.bdfp.accounts.ListChartOfAccountsGroupAction
import com.bits.bdfp.accounts.ListChartOfAccountsHeadByGroupAction
import com.bits.bdfp.inventory.product.finishproduct.CreateFinishProductAction
import com.bits.bdfp.inventory.product.finishproduct.DeleteFinishProductAction
import com.bits.bdfp.inventory.product.finishproduct.EnterpriseProductCategoryAction
import com.bits.bdfp.inventory.product.finishproduct.ListFinishProductAction
import com.bits.bdfp.inventory.product.finishproduct.ListProductPriceByCustomerAction
import com.bits.bdfp.inventory.product.finishproduct.UpdateFinishProductAction
import com.bits.bdfp.inventory.product.finishproduct.ReadFinishProductAction
import com.bits.bdfp.inventory.product.finishproduct.SearchFinishProductAction
import com.bits.bdfp.settings.businessunitconfiguration.ListBusinessUnitEnterpriseAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class FinishProductController {

    @Autowired
    private CreateFinishProductAction createFinishProductAction
    @Autowired
    private UpdateFinishProductAction updateFinishProductAction
    @Autowired
    private ListFinishProductAction listFinishProductAction
    @Autowired
    private DeleteFinishProductAction deleteFinishProductAction
    @Autowired
    private ReadFinishProductAction readFinishProductAction
    @Autowired
    private SearchFinishProductAction searchFinishProductAction
    @Autowired
    ListBusinessUnitEnterpriseAction listBusinessUnitEnterpriseAction
    @Autowired
    EnterpriseProductCategoryAction enterpriseProductCategoryAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListProductPriceByCustomerAction listProductPriceByCustomerAction
    @Autowired
    ListChartOfAccountsGroupAction listChartOfAccountsGroupAction
    @Autowired
    ListChartOfAccountsHeadByGroupAction listChartOfAccountsHeadByGroupAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listFinishProductAction.execute(params, null)
        render listFinishProductAction.postCondition(null, list) as JSON
    }

    def show = {
        FinishProduct finishProduct = new FinishProduct()
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser,null)
        Map result = ["results": list, "total": list?.size()]
        render(template: "show", model: [finishProduct: finishProduct, result:result as JSON, list: list])
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createFinishProductAction.execute(params, applicationUser)
        render message as JSON
    }

    def edit = {
        render readFinishProductAction.execute(params, null) as JSON
    }

    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateFinishProductAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteFinishProductAction.execute(params, null)
        render message as JSON
    }

    def search = {
        FinishProduct finishProduct = searchFinishProductAction.execute(params.fieldName, params.fieldValue)
        if (finishProduct) {
            render finishProduct as JSON
        } else {
            render ''
        }

    }

    def businessUnitForEnterprise = {
        Map productCategoryMap = (Map)enterpriseProductCategoryAction.execute(params, null)
        render productCategoryMap as JSON
    }

    def popupProductListPanel = {
        render(view: 'popupProductList', model: ['customerId': params.customerId,'territorySubAreaId': params.territorySubAreaId])
    }

    def jsonProductList = {
        Map map = [:]
        List data = (List)listProductPriceByCustomerAction.execute(params, null)
        map.put("aaData", data)
        render map as JSON
    }

    def listProductAutoComplete = {
        render listProductPriceByCustomerAction.execute(params, null) as JSON
    }

    def listCoaGroup = {
        render listChartOfAccountsGroupAction.execute(params, null) as JSON
    }

    def listCoaHeadByGroup = {
        render listChartOfAccountsHeadByGroupAction.execute(params, null) as JSON
    }
}
