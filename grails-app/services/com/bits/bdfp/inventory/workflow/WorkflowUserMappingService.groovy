package com.bits.bdfp.inventory.workflow

import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class WorkflowUserMappingService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public WorkflowUserMapping create(Object object) {
        WorkflowUserMapping workflowUserMapping = (WorkflowUserMapping) object
        if (workflowUserMapping.save(false)) {
            return workflowUserMapping
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        WorkflowUserMapping workflowUserMapping = (WorkflowUserMapping) object
        if (workflowUserMapping.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        WorkflowUserMapping workflowUserMapping = (WorkflowUserMapping) object
        workflowUserMapping.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<WorkflowUserMapping> objList = WorkflowUserMapping.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = WorkflowUserMapping.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<WorkflowUserMapping> list() {
        return WorkflowUserMapping.list()
    }

    @Transactional(readOnly = true)
    public WorkflowUserMapping read(Long id) {
        return WorkflowUserMapping.read(id)
    }

    @Transactional(readOnly = true)
    public WorkflowUserMapping search(String fieldName, String fieldValue) {
        String query = "from WorkflowUserMapping as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return WorkflowUserMapping.find(query)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Object params) {
        List<WorkflowUserMapping> objList = WorkflowUserMapping.withCriteria {
            if(params.id){
                eq("workflow.id", Long.parseLong(params.id))
            }
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = WorkflowUserMapping.count()
        return [objList: objList, count: total]
    }
}
