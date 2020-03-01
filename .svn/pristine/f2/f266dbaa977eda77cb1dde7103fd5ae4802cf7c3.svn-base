package com.bits.bdfp.inventory.sales

import com.bits.bdfp.inventory.sales.distributionpointwarehouse.CreateDistributionPointWarehouseAction
import com.bits.bdfp.inventory.sales.distributionpointwarehouse.DeleteDistributionPointWarehouseAction
import com.bits.bdfp.inventory.sales.distributionpointwarehouse.ListDistributionPointWarehouseAction
import com.bits.bdfp.inventory.sales.distributionpointwarehouse.UpdateDistributionPointWarehouseAction
import com.bits.bdfp.inventory.sales.distributionpointwarehouse.ReadDistributionPointWarehouseAction
import com.bits.bdfp.inventory.sales.distributionpointwarehouse.SearchDistributionPointWarehouseAction

import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class DistributionPointWarehouseController {

    @Autowired
    private CreateDistributionPointWarehouseAction createDistributionPointWarehouseAction
    @Autowired
    private UpdateDistributionPointWarehouseAction updateDistributionPointWarehouseAction
    @Autowired
    private ListDistributionPointWarehouseAction listDistributionPointWarehouseAction
    @Autowired
    private DeleteDistributionPointWarehouseAction deleteDistributionPointWarehouseAction
    @Autowired
    private ReadDistributionPointWarehouseAction readDistributionPointWarehouseAction
    @Autowired
    private SearchDistributionPointWarehouseAction searchDistributionPointWarehouseAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listDistributionPointWarehouseAction.execute(params, null)
        render listDistributionPointWarehouseAction.postCondition(null, list) as JSON
    }

    def show = {
        DistributionPointWarehouse distributionPointWarehouse = new DistributionPointWarehouse()
        render(template: "show", model: [distributionPointWarehouse: distributionPointWarehouse])
    }

    def create = {
        DistributionPointWarehouse distributionPointWarehouse = new DistributionPointWarehouse(params)
        DistributionPointWarehouse distributionPointWarehouseInstance = createDistributionPointWarehouseAction.preCondition(null, distributionPointWarehouse)
        Message message = null
        if (distributionPointWarehouseInstance == null) {
            message = createDistributionPointWarehouseAction.getValidationErrorMessageForUI(distributionPointWarehouse)
        } else {
            distributionPointWarehouseInstance = createDistributionPointWarehouseAction.execute(null, distributionPointWarehouseInstance)
            if (distributionPointWarehouseInstance) {
                message = createDistributionPointWarehouseAction.getSuccessMessageForUI(distributionPointWarehouseInstance, createDistributionPointWarehouseAction.SUCCESS_SAVE)
            } else {
                message = createDistributionPointWarehouseAction.getErrorMessageForUI(distributionPointWarehouse, createDistributionPointWarehouseAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        render readDistributionPointWarehouseAction.execute(params, null) as JSON
    }

    def update = {
        DistributionPointWarehouse distributionPointWarehouse = new DistributionPointWarehouse(params)
        Object object = updateDistributionPointWarehouseAction.preCondition(params, null)
        Message message = null
        if (object == false) {
            message = updateDistributionPointWarehouseAction.getValidationErrorMessageForUI(distributionPointWarehouse)
        } else {
            int noOfRows = (int) updateDistributionPointWarehouseAction.execute(null, object)
            if (noOfRows > 0) {
                message = updateDistributionPointWarehouseAction.getSuccessMessageForUI(distributionPointWarehouse, updateDistributionPointWarehouseAction.SUCCESS_UPDATE)
            } else {
                message = updateDistributionPointWarehouseAction.getErrorMessageForUI(distributionPointWarehouse, updateDistributionPointWarehouseAction.FAIL_UPDATE)
            }
        }
        render message as JSON
    }

    def delete = {
        DistributionPointWarehouse distributionPointWarehouse = deleteDistributionPointWarehouseAction.execute(params, null);
        Message message = null
        if (distributionPointWarehouse) {
            int rowCount = (int) deleteDistributionPointWarehouseAction.preCondition(null, distributionPointWarehouse);
            if (rowCount > 0) {
                message = deleteDistributionPointWarehouseAction.getSuccessMessageForUI(distributionPointWarehouse, deleteDistributionPointWarehouseAction.SUCCESS_DELETE);
            } else {
                message = deleteDistributionPointWarehouseAction.getErrorMessageForUI(distributionPointWarehouse, deleteDistributionPointWarehouseAction.FAIL_DELETE);
            }
        } else {
            message = deleteDistributionPointWarehouseAction.getErrorMessageForUI(distributionPointWarehouse, deleteDistributionPointWarehouseAction.ALREADY_DELETED);
        }
        render message as JSON;
    }

    def search = {
        DistributionPointWarehouse distributionPointWarehouse = searchDistributionPointWarehouseAction.execute(params.fieldName, params.fieldValue)
        if (distributionPointWarehouse) {
            render distributionPointWarehouse as JSON
        } else {
            render ''
        }

    }
}
