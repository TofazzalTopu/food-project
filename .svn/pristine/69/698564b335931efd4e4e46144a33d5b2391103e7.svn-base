package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.customer.employee.ListSalesEmployeeByApplicationUserAction
import com.bits.bdfp.inventory.product.finishproduct.ListProductWithoutPriceAction
import com.bits.bdfp.settings.ApplicationUserEnterprise
import com.bits.bdfp.setup.salestarget.salesheadbyvolume.CreateSalesHeadByVolumeAction
import com.bits.bdfp.setup.salestarget.salesheadbyvolume.DeleteSalesHeadByVolumeAction
import com.bits.bdfp.setup.salestarget.salesheadbyvolume.ListFinishProductBySalesHeadAction
import com.bits.bdfp.setup.salestarget.salesheadbyvolume.ListSalesHeadByVolumeAction
import com.bits.bdfp.setup.salestarget.salesheadbyvolume.UpdateSalesHeadByVolumeAction
import com.bits.bdfp.setup.salestarget.salesheadbyvolume.ReadSalesHeadByVolumeAction
import com.bits.bdfp.setup.salestarget.salesheadbyvolume.SearchSalesHeadByVolumeAction

import com.docu.common.Message
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class SalesHeadByVolumeController {

    @Autowired
    private CreateSalesHeadByVolumeAction createSalesHeadByVolumeAction
    @Autowired
    private UpdateSalesHeadByVolumeAction updateSalesHeadByVolumeAction
    @Autowired
    private ListSalesHeadByVolumeAction listSalesHeadByVolumeAction
    @Autowired
    private DeleteSalesHeadByVolumeAction deleteSalesHeadByVolumeAction
    @Autowired
    private ReadSalesHeadByVolumeAction readSalesHeadByVolumeAction
    @Autowired
    private SearchSalesHeadByVolumeAction searchSalesHeadByVolumeAction
    @Autowired
    ListSalesEmployeeByApplicationUserAction listSalesEmployeeByApplicationUserAction
    @Autowired
    ListProductWithoutPriceAction listProductWithoutPriceAction
    @Autowired
    ListFinishProductBySalesHeadAction listFinishProductBySalesHeadAction
    @Autowired
    SpringSecurityService springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
        Object objList = listSalesHeadByVolumeAction.execute(params, null)
        render listSalesHeadByVolumeAction.postCondition(objList, null) as JSON
    }

    def show = {
        int currentYear = DateUtil.getCurrentSystemYear()
        render(view: "/salestarget/salesHeadByVolume/show", model: [currentYear: currentYear])
    }

    def create = {
        Message message = createSalesHeadByVolumeAction.execute(params, null)
        render message as JSON
    }

    def edit = {
        int currentYear = DateUtil.getCurrentSystemYear()
        List<SalesHeadByVolume> salesHeadByVolumeList = SalesHeadByVolume.findAllByTargetYearGreaterThanEqualsAndIsActive(currentYear, true)
        render(view: '/salestarget/salesHeadByVolume/update', model: [salesHeadByVolumeList: salesHeadByVolumeList])
    }

    def update = {
        Message message = updateSalesHeadByVolumeAction.execute(params, null)
        render message as JSON
    }

    def delete = {
        Message message = deleteSalesHeadByVolumeAction.execute(params, null)
        render message as JSON
    }

    def search = {
        SalesHeadByVolume salesHeadByVolume = (SalesHeadByVolume) searchSalesHeadByVolumeAction.execute(params, null)
        if (salesHeadByVolume) {
            render salesHeadByVolume as JSON
        } else {
            render ''
        }
    }

    def listEmployee = {
        render listSalesEmployeeByApplicationUserAction.execute(params, null) as JSON
    }

    def popupEmployeeListPanel={
        render(view: '/salestarget/salesHeadByVolume/popUpEmployeeList')
    }

    def jsonEmployeeList = {
        Map map = [:]
        List data = (List)listSalesEmployeeByApplicationUserAction.execute(params, null)
        map.put("aaData", data)
        render map as JSON
    }

    def listProduct = {
        render listProductWithoutPriceAction.execute(params, null) as JSON
    }

    def popupProductListPanel = {
        render(view: '/salestarget/salesHeadByVolume/popUpProductList')
    }

    def jsonProductList = {
        Map map = [:]
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        params.enterpriseId = ApplicationUserEnterprise.findAllByApplicationUser(applicationUser).first().enterpriseConfiguration.id
        List data = (List)listProductWithoutPriceAction.execute(params, null)
        map.put("aaData", data)
        render map as JSON
    }

    def saleHeadByVolumeDetails = {
        SalesHeadByVolume salesHeadByVolume = (SalesHeadByVolume) readSalesHeadByVolumeAction.execute(params, null)
        render(template: "/salestarget/salesHeadByVolume/salesHeadInfo", model: [salesHeadByVolume: salesHeadByVolume])
    }

    def listFinishProductBySalesHead = {
        render listFinishProductBySalesHeadAction.execute(params, null) as JSON
    }
}
