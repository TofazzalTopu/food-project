package com.bits.bdfp.inventory.sales

import com.bits.bdfp.inventory.sales.distributionpointterritorysubarea.CreateDistributionPointTerritorySubAreaAction
import com.bits.bdfp.inventory.sales.distributionpointterritorysubarea.DeleteDistributionPointTerritorySubAreaAction
import com.bits.bdfp.inventory.sales.distributionpointterritorysubarea.ListDistributionPointTerritorySubAreaAction
import com.bits.bdfp.inventory.sales.distributionpointterritorysubarea.UpdateDistributionPointTerritorySubAreaAction
import com.bits.bdfp.inventory.sales.distributionpointterritorysubarea.ReadDistributionPointTerritorySubAreaAction
import com.bits.bdfp.inventory.sales.distributionpointterritorysubarea.SearchDistributionPointTerritorySubAreaAction

import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class DistributionPointTerritorySubAreaController {

    @Autowired
    private CreateDistributionPointTerritorySubAreaAction createDistributionPointTerritorySubAreaAction
    @Autowired
    private UpdateDistributionPointTerritorySubAreaAction updateDistributionPointTerritorySubAreaAction
    @Autowired
    private ListDistributionPointTerritorySubAreaAction listDistributionPointTerritorySubAreaAction
    @Autowired
    private DeleteDistributionPointTerritorySubAreaAction deleteDistributionPointTerritorySubAreaAction
    @Autowired
    private ReadDistributionPointTerritorySubAreaAction readDistributionPointTerritorySubAreaAction
    @Autowired
    private SearchDistributionPointTerritorySubAreaAction searchDistributionPointTerritorySubAreaAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listDistributionPointTerritorySubAreaAction.execute(params, null)
        render listDistributionPointTerritorySubAreaAction.postCondition(null, list) as JSON
    }

    def show = {
        DistributionPointTerritorySubArea distributionPointTerritorySubArea = new DistributionPointTerritorySubArea()
        render(template: "show", model: [distributionPointTerritorySubArea: distributionPointTerritorySubArea])
    }

    def create = {
        DistributionPointTerritorySubArea distributionPointTerritorySubArea = new DistributionPointTerritorySubArea(params)
        DistributionPointTerritorySubArea distributionPointTerritorySubAreaInstance = createDistributionPointTerritorySubAreaAction.preCondition(null, distributionPointTerritorySubArea)
        Message message = null
        if (distributionPointTerritorySubAreaInstance == null) {
            message = createDistributionPointTerritorySubAreaAction.getValidationErrorMessageForUI(distributionPointTerritorySubArea)
        } else {
            distributionPointTerritorySubAreaInstance = createDistributionPointTerritorySubAreaAction.execute(null, distributionPointTerritorySubAreaInstance)
            if (distributionPointTerritorySubAreaInstance) {
                message = createDistributionPointTerritorySubAreaAction.getSuccessMessageForUI(distributionPointTerritorySubAreaInstance, createDistributionPointTerritorySubAreaAction.SUCCESS_SAVE)
            } else {
                message = createDistributionPointTerritorySubAreaAction.getErrorMessageForUI(distributionPointTerritorySubArea, createDistributionPointTerritorySubAreaAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        render readDistributionPointTerritorySubAreaAction.execute(params, null) as JSON
    }

    def update = {
        DistributionPointTerritorySubArea distributionPointTerritorySubArea = new DistributionPointTerritorySubArea(params)
        Object object = updateDistributionPointTerritorySubAreaAction.preCondition(params, null)
        Message message = null
        if (object == false) {
            message = updateDistributionPointTerritorySubAreaAction.getValidationErrorMessageForUI(distributionPointTerritorySubArea)
        } else {
            int noOfRows = (int) updateDistributionPointTerritorySubAreaAction.execute(null, object)
            if (noOfRows > 0) {
                message = updateDistributionPointTerritorySubAreaAction.getSuccessMessageForUI(distributionPointTerritorySubArea, updateDistributionPointTerritorySubAreaAction.SUCCESS_UPDATE)
            } else {
                message = updateDistributionPointTerritorySubAreaAction.getErrorMessageForUI(distributionPointTerritorySubArea, updateDistributionPointTerritorySubAreaAction.FAIL_UPDATE)
            }
        }
        render message as JSON
    }

    def delete = {
        DistributionPointTerritorySubArea distributionPointTerritorySubArea = deleteDistributionPointTerritorySubAreaAction.execute(params, null);
        Message message = null
        if (distributionPointTerritorySubArea) {
            int rowCount = (int) deleteDistributionPointTerritorySubAreaAction.preCondition(null, distributionPointTerritorySubArea);
            if (rowCount > 0) {
                message = deleteDistributionPointTerritorySubAreaAction.getSuccessMessageForUI(distributionPointTerritorySubArea, deleteDistributionPointTerritorySubAreaAction.SUCCESS_DELETE);
            } else {
                message = deleteDistributionPointTerritorySubAreaAction.getErrorMessageForUI(distributionPointTerritorySubArea, deleteDistributionPointTerritorySubAreaAction.FAIL_DELETE);
            }
        } else {
            message = deleteDistributionPointTerritorySubAreaAction.getErrorMessageForUI(distributionPointTerritorySubArea, deleteDistributionPointTerritorySubAreaAction.ALREADY_DELETED);
        }
        render message as JSON;
    }

    def search = {
        DistributionPointTerritorySubArea distributionPointTerritorySubArea = searchDistributionPointTerritorySubAreaAction.execute(params.fieldName, params.fieldValue)
        if (distributionPointTerritorySubArea) {
            render distributionPointTerritorySubArea as JSON
        } else {
            render ''
        }

    }
}
