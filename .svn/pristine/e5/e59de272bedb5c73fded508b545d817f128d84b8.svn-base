package com.bits.bdfp.inventory.workflow.workflow

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.workflow.WorkflowCustomerMappingService
import com.bits.bdfp.inventory.workflow.WorkflowUserMappingService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 12/27/15.
 */
@Component("listWorkflowMappedCustomerAction")
class ListWorkflowMappedCustomerAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    WorkflowCustomerMappingService workflowCustomerMappingService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            init(params)
            Map result = [:]
            List objectList = null

            result = workflowCustomerMappingService.getListForGrid(this, params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List objList = this.wrapListInGridEntityList(objectList, start)
            return objList
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    Map getResultForUI(List objList) {
        int pageCount = 1
        if (resultPerPage < 0) {
            pageCount = 1
            resultPerPage = total
        } else {
            if (total > resultPerPage) {
                pageCount = Math.ceil(total / resultPerPage)
            }
        }
        Map output = [page: pageNum, total: pageCount, records: total, rows: objList]
        return output;
    }

    private List wrapListInGridEntityList(objList, start) {
        List instants = []
        int counter = start + 1;
        objList.each { instance ->
            CustomerMaster customerMaster = instance.customerMaster
            GridEntity obj = new GridEntity()
            obj.id = customerMaster.id
            obj.cell = [
                    "${instance.customerMaster ? instance.customerMaster.id : ''}",
                    "${instance.id ? instance.id : ''}",
                    "${customerMaster ? customerMaster.code : ''}",
                    "${customerMaster.name ? customerMaster.name : ''}",
                    "${customerMaster.presentAddress ? customerMaster.presentAddress : ''}",
                    ''
            ]
            instants << obj
            counter++
        };
        return instants
    }

    public Object postCondition(Object params, Object object) {
        return getResultForUI(object)
    }
}