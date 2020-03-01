package com.bits.bdfp.inventory.workflow.workflow

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.workflow.WorkflowUserMappingService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 11/23/2015.
 */
@Component("listWorkflowMappedUserAction")
class ListWorkflowMappedUserAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    WorkflowUserMappingService workflowUserMappingService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            init(params)
            Map result = [:]
            List objectList = null

            result = workflowUserMappingService.getListForGrid(this, params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List objList = this.wrapListInGridEntityList(objectList, start)
            return objList
        }
        catch (Exception ex) {
            log.error(ex.message)
            return null
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
            CustomerMaster customerMaster = CustomerMaster.read(instance.applicationUser.customerMasterId)
            GridEntity obj = new GridEntity()
            obj.id = instance.applicationUser.id
            obj.cell = [
                        "${instance.applicationUser ? instance.applicationUser.id : ''}",
                        "${instance.id ? instance.id : ''}",
                        "${instance.applicationUser ? instance.applicationUser.username : ''}",
                        "${customerMaster.designation ? customerMaster.designation : ''}",
                        "${instance.prioritySequence ? instance.prioritySequence : ''}",
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
