package com.bits.bdfp.inventory.setup

import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.product.finishproduct.ListFinishProductAction
import com.bits.bdfp.inventory.setup.vatrate.CreateVatRateAction
import com.bits.bdfp.inventory.setup.vatrate.DeleteVatRateAction
import com.bits.bdfp.inventory.setup.vatrate.ListVatRateAction
import com.bits.bdfp.inventory.setup.vatrate.UpdateVatRateAction
import com.bits.bdfp.inventory.setup.vatrate.ReadVatRateAction
import com.bits.bdfp.inventory.setup.vatrate.SearchVatRateAction
import com.bits.bdfp.inventory.setup.vatrate.UtilVatRateAction
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class VatRateController {

    @Autowired
    private CreateVatRateAction createVatRateAction
    @Autowired
    private UpdateVatRateAction updateVatRateAction
    @Autowired
    private ListVatRateAction listVatRateAction
    @Autowired
    private DeleteVatRateAction deleteVatRateAction
    @Autowired
    private ReadVatRateAction readVatRateAction
    @Autowired
    private SearchVatRateAction searchVatRateAction
    @Autowired
    private ListFinishProductAction listFinishProductAction
    @Autowired
    private UtilVatRateAction utilVatRateAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
        Object objList = listVatRateAction.execute(params, null)
        render listVatRateAction.postCondition(objList, null) as JSON
    }

    def show = {
       render(view:"show")
    }

    def create = {
      Message message = createVatRateAction.execute(params, null)
      render message as JSON
    }

    def edit = {
      render readVatRateAction.execute(params, null) as JSON
    }

    def getProduct = {
      render utilVatRateAction.getProductById(Long.parseLong(params.id)) as JSON
    }

    def update = {
      Message message = updateVatRateAction.execute(params, null)
      render message as JSON
    }

    def delete = {
        Message message = deleteVatRateAction.execute(params, null)
        render message as JSON
    }

    def search = {
        VatRate vatRate = (VatRate) searchVatRateAction.execute(params, null)
        if(vatRate){
            render vatRate as JSON
        }
        else{
            render ''
        }
    }

//    def popupVatRateListPanel = {
//        render(view: '../vatRate/popupVatRateList', model: ['id': params.vatRateId])
//    }

    def jsonVatRateList = {
        Map map = [:]
        List data = (List)listFinishProductAction.execute(params, null)
        map.put("aaData", data)
        render map as JSON
    }

    def popupProductListPanel = {
        render(view: '../vatRate/popupVatRateList', model: ['id': params.vatRateId])
    }

    def jsonProductList = {
        Map map = [:]
        List data = (List)listProductPriceByCustomerAction.execute(params, null)
        map.put("aaData", data)
        render map as JSON
    }

    def getProductListByKey = {
        render utilVatRateAction.getProductListByKey(params.key) as JSON
    }

    def getProductList = {
        List productList = FinishProduct.list();
        render(view: 'popUpProductList', model: [aaData: productList as JSON])
    }

}
