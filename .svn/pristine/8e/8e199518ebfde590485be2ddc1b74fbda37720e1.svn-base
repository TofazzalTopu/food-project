package com.bits.bdfp.inventory.workflow

import com.docu.common.Action
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service

import javax.sql.DataSource

class WorkflowCustomerMappingService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public WorkflowCustomerMapping create(Object object) {
        WorkflowCustomerMapping workflowCustomerMapping = (WorkflowCustomerMapping) object
        if (workflowCustomerMapping.save(false)) {
            return workflowCustomerMapping
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        WorkflowCustomerMapping workflowCustomerMapping = (WorkflowCustomerMapping) object
        if (workflowCustomerMapping.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        WorkflowCustomerMapping workflowCustomerMapping = (WorkflowCustomerMapping) object
        workflowCustomerMapping.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<WorkflowCustomerMapping> objList = WorkflowCustomerMapping.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = WorkflowCustomerMapping.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<WorkflowCustomerMapping> list() {
        return WorkflowCustomerMapping.list()
    }

    @Transactional(readOnly = true)
    public WorkflowCustomerMapping read(Long id) {
        return WorkflowCustomerMapping.read(id)
    }

    @Transactional(readOnly = true)
    public WorkflowCustomerMapping search(String fieldName, String fieldValue) {
        String query = "from WorkflowCustomerMapping as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return WorkflowCustomerMapping.find(query)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Object params) {
        List<WorkflowCustomerMapping> objList = WorkflowCustomerMapping.withCriteria {
            if(params.id){
                eq("workflow.id", Long.parseLong(params.id))
            }
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = WorkflowCustomerMapping.count()
        return [objList: objList, count: total]
    }
}