package com.bits.bdfp.settings

import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.bits.bdfp.settings.measureunitconfiguration.CreateMeasureUnitConfigurationAction
import com.bits.bdfp.settings.measureunitconfiguration.DeleteMeasureUnitConfigurationAction
import com.bits.bdfp.settings.measureunitconfiguration.ListMeasureUnitConfigurationAction
import com.bits.bdfp.settings.measureunitconfiguration.UpdateMeasureUnitConfigurationAction
import com.bits.bdfp.settings.measureunitconfiguration.ReadMeasureUnitConfigurationAction
import com.bits.bdfp.settings.measureunitconfiguration.SearchMeasureUnitConfigurationAction

import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class MeasureUnitConfigurationController {

    @Autowired
    private CreateMeasureUnitConfigurationAction createMeasureUnitConfigurationAction
    @Autowired
    private UpdateMeasureUnitConfigurationAction updateMeasureUnitConfigurationAction
    @Autowired
    private ListMeasureUnitConfigurationAction listMeasureUnitConfigurationAction
    @Autowired
    private DeleteMeasureUnitConfigurationAction deleteMeasureUnitConfigurationAction
    @Autowired
    private ReadMeasureUnitConfigurationAction readMeasureUnitConfigurationAction
    @Autowired
    private SearchMeasureUnitConfigurationAction searchMeasureUnitConfigurationAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listMeasureUnitConfigurationAction.execute(params, null)
        render listMeasureUnitConfigurationAction.postCondition(null, list) as JSON
    }

    def show = {
        MeasureUnitConfiguration measureUnitConfiguration = new MeasureUnitConfiguration()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [measureUnitConfiguration: measureUnitConfiguration,list: enterpriseList,result:result as JSON])
    }

    def create = {
        ApplicationUser applicationUser=session?.applicationUser
        MeasureUnitConfiguration measureUnitConfiguration = new MeasureUnitConfiguration(params)
        MeasureUnitConfiguration measureUnitConfigurationInstance = createMeasureUnitConfigurationAction.preCondition(applicationUser, measureUnitConfiguration)
        Message message = null
        if (measureUnitConfigurationInstance == null) {
            message = createMeasureUnitConfigurationAction.getValidationErrorMessage(measureUnitConfiguration)
        } else {
            measureUnitConfigurationInstance = createMeasureUnitConfigurationAction.execute(applicationUser, measureUnitConfigurationInstance)
            if (measureUnitConfigurationInstance) {
                message = createMeasureUnitConfigurationAction.getMessage(measureUnitConfigurationInstance,Message.SUCCESS, createMeasureUnitConfigurationAction.SUCCESS_SAVE)
            } else {
                message = createMeasureUnitConfigurationAction.getMessage(measureUnitConfiguration,Message.ERROR, createMeasureUnitConfigurationAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result = readMeasureUnitConfigurationAction.execute(params, null)
        render result as JSON
    }

    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updateMeasureUnitConfigurationAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteMeasureUnitConfigurationAction.execute(params, null)
        render message as JSON
    }



    def search = {
        MeasureUnitConfiguration measureUnitConfiguration = searchMeasureUnitConfigurationAction.execute(params.fieldName, params.fieldValue)
        if (measureUnitConfiguration) {
            render measureUnitConfiguration as JSON
        } else {
            render ''
        }

    }
}
