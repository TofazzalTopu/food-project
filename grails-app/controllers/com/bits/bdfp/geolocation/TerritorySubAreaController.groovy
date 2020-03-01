package com.bits.bdfp.geolocation

import com.bits.bdfp.geolocation.territorysubarea.CreateTerritorySubAreaAction
import com.bits.bdfp.geolocation.territorysubarea.DeleteTerritorySubAreaAction
import com.bits.bdfp.geolocation.territorysubarea.FlexboxTerritorySubAreaByTerritoryAction
import com.bits.bdfp.geolocation.territorysubarea.ListTerritorySubAreaAction
import com.bits.bdfp.geolocation.territorysubarea.ListTerritorySubAreaByTerritoryAction
import com.bits.bdfp.geolocation.territorysubarea.SearchTerritorySubAreaByTerritoryAction
import com.bits.bdfp.geolocation.territorysubarea.SearchTerritorySubAreaMappingByCustomerAction
import com.bits.bdfp.geolocation.territorysubarea.UpdateTerritorySubAreaAction
import com.bits.bdfp.geolocation.territorysubarea.ReadTerritorySubAreaAction
import com.bits.bdfp.geolocation.territorysubarea.SearchTerritorySubAreaAction

import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class TerritorySubAreaController {

    @Autowired
    private CreateTerritorySubAreaAction createTerritorySubAreaAction
    @Autowired
    private UpdateTerritorySubAreaAction updateTerritorySubAreaAction
    @Autowired
    private ListTerritorySubAreaAction listTerritorySubAreaAction
    @Autowired
    private ListTerritorySubAreaByTerritoryAction listTerritorySubAreaByTerritoryAction
    @Autowired
    private DeleteTerritorySubAreaAction deleteTerritorySubAreaAction
    @Autowired
    private ReadTerritorySubAreaAction readTerritorySubAreaAction
    @Autowired
    private SearchTerritorySubAreaAction searchTerritorySubAreaAction
    @Autowired
    private SearchTerritorySubAreaByTerritoryAction searchTerritorySubAreaByTerritoryAction
    @Autowired
    private SearchTerritorySubAreaMappingByCustomerAction searchTerritorySubAreaMappingByCustomerAction
    @Autowired
    private FlexboxTerritorySubAreaByTerritoryAction flexboxTerritorySubAreaByTerritoryAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listTerritorySubAreaAction.execute(params, null)
        render listTerritorySubAreaAction.postCondition(null, list) as JSON
    }

    def show = {
        TerritorySubArea territorySubArea = new TerritorySubArea()
        render(template: "show", model: [territorySubArea: territorySubArea])
    }

    def create = {
        TerritorySubArea territorySubArea = new TerritorySubArea(params)
        TerritorySubArea territorySubAreaInstance = createTerritorySubAreaAction.preCondition(null, territorySubArea)
        Message message = null
        if (territorySubAreaInstance == null) {
            message = createTerritorySubAreaAction.getValidationErrorMessageForUI(territorySubArea)
        } else {
            territorySubAreaInstance = createTerritorySubAreaAction.execute(null, territorySubAreaInstance)
            if (territorySubAreaInstance) {
                message = createTerritorySubAreaAction.getSuccessMessageForUI(territorySubAreaInstance, createTerritorySubAreaAction.SUCCESS_SAVE)
            } else {
                message = createTerritorySubAreaAction.getErrorMessageForUI(territorySubArea, createTerritorySubAreaAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        render readTerritorySubAreaAction.execute(params, null) as JSON
    }

    def update = {
        TerritorySubArea territorySubArea = new TerritorySubArea(params)
        Object object = updateTerritorySubAreaAction.preCondition(params, null)
        Message message = null
        if (object == false) {
            message = updateTerritorySubAreaAction.getValidationErrorMessageForUI(territorySubArea)
        } else {
            int noOfRows = (int) updateTerritorySubAreaAction.execute(null, object)
            if (noOfRows > 0) {
                message = updateTerritorySubAreaAction.getSuccessMessageForUI(territorySubArea, updateTerritorySubAreaAction.SUCCESS_UPDATE)
            } else {
                message = updateTerritorySubAreaAction.getErrorMessageForUI(territorySubArea, updateTerritorySubAreaAction.FAIL_UPDATE)
            }
        }
        render message as JSON
    }

    def delete = {
        TerritorySubArea territorySubArea = deleteTerritorySubAreaAction.execute(params, null);
        Message message = null
        if (territorySubArea) {
            int rowCount = (int) deleteTerritorySubAreaAction.preCondition(null, territorySubArea);
            if (rowCount > 0) {
                message = deleteTerritorySubAreaAction.getSuccessMessageForUI(territorySubArea, deleteTerritorySubAreaAction.SUCCESS_DELETE);
            } else {
                message = deleteTerritorySubAreaAction.getErrorMessageForUI(territorySubArea, deleteTerritorySubAreaAction.FAIL_DELETE);
            }
        } else {
            message = deleteTerritorySubAreaAction.getErrorMessageForUI(territorySubArea, deleteTerritorySubAreaAction.ALREADY_DELETED);
        }
        render message as JSON;
    }

    def search = {
        TerritorySubArea territorySubArea = searchTerritorySubAreaAction.execute(params.fieldName, params.fieldValue)
        if (territorySubArea) {
            render territorySubArea as JSON
        } else {
            render ''
        }
    }

    def searchTerritorySubAreaByTerritory = {
        Map result = (Map) searchTerritorySubAreaByTerritoryAction.execute(params, null)
        render(template: "/customer/customerMaster/employee/territorySubArea", model: [availableTerritorySubArea: result.availableTerritorySubArea, selectedTerritorySubArea: result.selectedTerritorySubArea])
//        render result as JSON
    }

    def searchTerritorySubAreaMappingByCustomer = {
        Map result = (Map) searchTerritorySubAreaMappingByCustomerAction.execute(params, null)
        render(template: "/customer/customerMaster/employee/territorySubArea", model: [availableTerritorySubArea: result.availableTerritorySubArea, selectedTerritorySubArea: result.selectedTerritorySubArea])
    }

    def listTerritorySubAreaByTerritory = {
        Map result = (Map) listTerritorySubAreaByTerritoryAction.execute(params, null)
        render result as JSON
    }

    def fetchTerritorySubAreaList = {
        List list = searchTerritorySubAreaAction.executeSubArea(params, null)
        render list as JSON
    }
    def fetchCustomerCategoryByTerritorySubArea = {
        List list = searchTerritorySubAreaAction.executeCustomer(params, null)
        render list as JSON
    }


    def getFlexBoxTerritorySubAeaData = {
        Map result = (Map) flexboxTerritorySubAreaByTerritoryAction.execute(params, null)
        render result as JSON
    }

    def territorysubAreaDelete = {
//        TerritorySubArea territorySubArea = deleteTerritorySubAreaAction.deleteTerritorysubarea(params);
        TerritorySubArea territorySubArea
        Message message = null

            Object object= deleteTerritorySubAreaAction.deleteTerritorysubarea(params);
            message = deleteTerritorySubAreaAction.getMessage("Territory Setup",Message.SUCCESS,object.toString())
          render message as JSON;


    }
}

