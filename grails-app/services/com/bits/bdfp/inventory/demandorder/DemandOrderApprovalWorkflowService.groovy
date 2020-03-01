package com.bits.bdfp.inventory.demandorder

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class DemandOrderApprovalWorkflowService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public DemandOrderApprovalWorkflow create(Object object) {
        DemandOrderApprovalWorkflow demandOrderApprovalWorkflow = (DemandOrderApprovalWorkflow) object
        if (demandOrderApprovalWorkflow.save(false)) {
            return demandOrderApprovalWorkflow
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        DemandOrderApprovalWorkflow demandOrderApprovalWorkflow = (DemandOrderApprovalWorkflow) object
        if (demandOrderApprovalWorkflow.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        DemandOrderApprovalWorkflow demandOrderApprovalWorkflow = (DemandOrderApprovalWorkflow) object
        demandOrderApprovalWorkflow.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<DemandOrderApprovalWorkflow> objList = DemandOrderApprovalWorkflow.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = DemandOrderApprovalWorkflow.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<DemandOrderApprovalWorkflow> list() {
        return DemandOrderApprovalWorkflow.list()
    }

    @Transactional(readOnly = true)
    public DemandOrderApprovalWorkflow read(Long id) {
        return DemandOrderApprovalWorkflow.read(id)
    }

    @Transactional(readOnly = true)
    public DemandOrderApprovalWorkflow search(String fieldName, String fieldValue) {
        String query = "from DemandOrderApprovalWorkflow as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return DemandOrderApprovalWorkflow.find(query)
    }
}
