package com.bits.bdfp.accounts

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by abhijit.majumder on 1/24/2016.
 */
class ChartOfAccountLayerController {

    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListChartOfAccountLayerAction listChartOfAccountLayerAction
    @Autowired
    CreateChartOfAccountLayerAction createChartOfAccountLayerAction
    @Autowired
    CreateChartOfAccountsAction createChartOfAccountsAction
    @Autowired
    UpdateChartOfAccountsAction updateChartOfAccountsAction
    @Autowired
    UpdateChartOfAccountLayerAction updateChartOfAccountLayerAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]
    def list = {
        render listChartOfAccountLayerAction.execute(params, null) as JSON
    }

    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser,null)
        EnterpriseConfiguration enterpriseConfiguration = null
        int x = 1
        Map layerList = null
        if(list.size() == 1){
            enterpriseConfiguration = EnterpriseConfiguration.read(list[0].id)
            if(enterpriseConfiguration.layerUsed){
                x = 0
            }
            layerList = (Map) listChartOfAccountLayerAction.execute(params, enterpriseConfiguration)
        }
        Map result = ["results": list, "total": list.size()]
        render(view: "showLayer", model: [result:result as JSON, list: list, layerList: layerList? layerList as JSON : '',
                                          size: enterpriseConfiguration? enterpriseConfiguration.noOfLayers : 0,
                                          length: enterpriseConfiguration? enterpriseConfiguration.codeLength : 0,
                                          editable: x])
    }

    def save = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createChartOfAccountLayerAction.execute(params, applicationUser)
        render message as JSON
    }

    def showChartOfAccount = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser,null)
        EnterpriseConfiguration enterpriseConfiguration = null
        int x = 1
        if(list.size() == 1){
            enterpriseConfiguration = EnterpriseConfiguration.read(list[0].id)
            if(enterpriseConfiguration.coaUsed > 0){
                x = 0
            }
        }
        Map result = ["results": list, "total": list.size()]
        if(enterpriseConfiguration.layerUsed){
            params.put('update', '1')
            Map map = listChartOfAccountLayerAction.execute(params, list[0])
            render(view: "show", model: [layerList: map.coa as JSON, result: result as JSON, list: list,
                                         update: 1, editable: x, codeLength: enterpriseConfiguration.codeLength,
                                         layer: enterpriseConfiguration.noOfLayers, layers: map.layer as JSON])
        }else {
            Map map = listChartOfAccountLayerAction.execute(params, list[0])
            render(view: "show", model: [layerList: map.layer as JSON, result: result as JSON, list: list,
                                         update: 0, editable: x, codeLength: enterpriseConfiguration.codeLength,
                                         layer: enterpriseConfiguration.noOfLayers, layers: map.layer as JSON])
        }
    }

    def saveChartOfAccount = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createChartOfAccountsAction.execute(params, applicationUser)
        render message as JSON
    }

    def updateChartOfAccount = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateChartOfAccountsAction.execute(params, applicationUser)
        render message as JSON
    }

    def listChartOfAccount = {
        params.put('update', '1')
        EnterpriseConfiguration enterpriseConfiguration = EnterpriseConfiguration.read(Long.parseLong(params.entId))
        render listChartOfAccountLayerAction.execute(params, enterpriseConfiguration) as JSON
    }

    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateChartOfAccountLayerAction.execute(params, applicationUser)
        render message as JSON
    }
}
