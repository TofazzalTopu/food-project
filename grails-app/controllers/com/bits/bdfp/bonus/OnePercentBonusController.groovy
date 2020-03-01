package com.bits.bdfp.bonus

import com.bits.bdfp.bonus.onepercentbonus.AdjustOnePercentBonusAction
import com.bits.bdfp.bonus.onepercentbonus.CreateOnePercentBonusAction
import com.bits.bdfp.bonus.onepercentbonus.DeleteOnePercentBonusAction
import com.bits.bdfp.bonus.onepercentbonus.ListBranchByTerritoryAction
import com.bits.bdfp.bonus.onepercentbonus.ListCategoryAction
import com.bits.bdfp.bonus.onepercentbonus.ListCustomerByGeoAndCategoryAction
import com.bits.bdfp.bonus.onepercentbonus.ListGeoLocationAction
import com.bits.bdfp.bonus.onepercentbonus.ListOnePercentBonusAction
import com.bits.bdfp.bonus.onepercentbonus.ListProductAction
import com.bits.bdfp.bonus.onepercentbonus.ListTerritoryAction
import com.bits.bdfp.bonus.onepercentbonus.ListTotalSalesForSalesmanAction
import com.bits.bdfp.bonus.onepercentbonus.ReadOnePercentBonusAction
import com.bits.bdfp.finance.customerpayment.ListAccountByCustomerAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class OnePercentBonusController {

    @Autowired
    private CreateOnePercentBonusAction createOnePercentBonusAction
    @Autowired
    private ListOnePercentBonusAction listOnePercentBonusAction
    @Autowired
    private DeleteOnePercentBonusAction deleteOnePercentBonusAction
    @Autowired
    private ReadOnePercentBonusAction readOnePercentBonusAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListTerritoryAction listTerritoryAction
    @Autowired
    ListProductAction listProductAction
    @Autowired
    ListCategoryAction listCategoryAction
    @Autowired
    ListGeoLocationAction listGeoLocationAction
    @Autowired
    ListCustomerByGeoAndCategoryAction listCustomerByGeoAndCategoryAction
    @Autowired
    ListAccountByCustomerAction listAccountByCustomerAction
    @Autowired
    ListTotalSalesForSalesmanAction listTotalSalesForSalesmanAction
    @Autowired
    ListBranchByTerritoryAction listBranchByTerritoryAction
    @Autowired
    AdjustOnePercentBonusAction adjustOnePercentBonusAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
        Object objList = listOnePercentBonusAction.execute(params, null)
        render listOnePercentBonusAction.postCondition(objList, null) as JSON
    }

    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        List list1 = BonusPercent.list()
        Map result = ["results": list, "total": list.size()]
        render(view: "show", model: [result: result as JSON, list: list, bonus: list1])
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createOnePercentBonusAction.execute(params, applicationUser)
        render message as JSON
    }

    def edit = {
        Map data = (Map) readOnePercentBonusAction.execute(params, null)
        render data as JSON
    }

    def delete = {
        Message message = deleteOnePercentBonusAction.execute(params, null)
        render message as JSON
    }

    def listTerritory = {
        Object objList = listTerritoryAction.execute(params, null)
        render listTerritoryAction.postCondition(null, objList) as JSON
    }

    def listProduct = {
        Object objList = listProductAction.execute(params, null)
        render listProductAction.postCondition(null, objList) as JSON
    }

    def listCategory = {
        Object objList = listCategoryAction.execute(params, null)
        render listCategoryAction.postCondition(null, objList) as JSON
    }

    def listGeolocation = {
        Object objList = listGeoLocationAction.execute(params, null)
        render listGeoLocationAction.postCondition(null, objList) as JSON
    }

    def listCustomer = {
        Object objList = listCustomerByGeoAndCategoryAction.execute(params, null)
        render listCustomerByGeoAndCategoryAction.postCondition(null, objList) as JSON
    }

    def showAdjust = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        List isFactory = listAccountByCustomerAction.preCondition(null, applicationUser)
        Map result = ["results": list, "total": list.size()]
        render(view: "showAdjust", model: [result: result as JSON, list: list, isFactory: isFactory[0]? isFactory[0].is_factory:false])
    }

    def listGeolocationByDp = {
        ApplicationUser applicationUser = session?.applicationUser
        params.put('userId', applicationUser.id)
        Object objList = listGeoLocationAction.execute(params, null)
        render listGeoLocationAction.postCondition(null, objList) as JSON
    }

    def listSalesForSalesman = {
        Object objList = listTotalSalesForSalesmanAction.execute(params, null)
        render listTotalSalesForSalesmanAction.postCondition(null, objList) as JSON
    }

    def listBranch = {
        Object objList = listBranchByTerritoryAction.execute(params, null)
        render listBranchByTerritoryAction.postCondition(null, objList) as JSON
    }

    def adjustBonus = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = adjustOnePercentBonusAction.execute(params, applicationUser)
        render message as JSON
    }

}
