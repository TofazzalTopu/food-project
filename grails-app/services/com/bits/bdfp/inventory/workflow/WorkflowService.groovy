package com.bits.bdfp.inventory.workflow

import com.bits.bdfp.customer.CustomerStatus
import com.bits.bdfp.finance.CustomerAccount
import com.bits.bdfp.finance.CustomerPayment
import com.bits.bdfp.finance.CustomerPaymentCurrencyDenomination
import com.bits.bdfp.inventory.demandorder.CustomerDemandOrderPayment
import com.bits.bdfp.util.ApplicationConstants
import org.apache.jasper.tagplugins.jstl.core.Catch
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class WorkflowService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public Workflow create(Object object) {
        Workflow workflow = (Workflow) object
        if (workflow.save(false)) {
            return workflow
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        Workflow workflow = (Workflow) object
        if (workflow.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        Workflow workflow = (Workflow) object
        workflow.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Object params) {
        List<Workflow> objList = Workflow.withCriteria {
            if(params.enterpriseId){
                eq("enterpriseConfiguration.id", Long.parseLong(params.enterpriseId))
            }
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = Workflow.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<Workflow> list() {
        return Workflow.list()
    }

    @Transactional(readOnly = true)
    public Workflow read(Long id) {
        return Workflow.read(id)
    }

    @Transactional(readOnly = true)
    public Workflow search(String fieldName, String fieldValue) {
        String query = "from Workflow as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return Workflow.find(query)
    }

    @Transactional(readOnly = true)
    public Map getPrioritySequenceListForGrid(Action action, Object params) {

        sql = new Sql(dataSource)
        String strLIMIT = "";
        String offSet = ""

        if (action.resultPerPage != -1) {
            strLIMIT = """LIMIT ${action.resultPerPage}"""
            offSet = """
                    OFFSET ${action.start}
                   """
        } else {
            action.resultPerPage = -1;
        }

        String strSql = """SELECT workflow.id,`workflow`.`name`,`workflow`.`menu_name`,`workflow`.`priority_sequence`
                        FROM workflow
                        WHERE workflow.`enterprise_configuration_id` = ${params.id}
                         ${strLIMIT}
                        ${offSet}
                          """
        List objList = sql.rows(strSql.toString())
        int resultCount = 0
        if (objList && objList.size() > 0) {
            resultCount = objList.size()
        }

        return [objList: objList, count: resultCount]
    }

    @Transactional
    public Integer savePrioritySequnce(Object object) {
        Map map = (Map) object
        List<Workflow> workflowList = map.workflowList

        if (workflowList && workflowList.size() > 0) {
            workflowList.each {
                it.save(false)
            }
            return new Integer(1)
        } else {
            return new Integer(0)
        }

    }

    @Transactional
    public Workflow createWorkflow(Map map) {
        try{
            Workflow workflow = (Workflow) map.get('workflow')
            List<WorkflowUserMapping> workflowUserMappingList = (List<WorkflowUserMapping>) map.get('workflowUserMapping')
            List<WorkflowCustomerMapping> workflowCustomerMappingList = (List<WorkflowCustomerMapping>) map.get('workflowCustomerMapping')
            if (workflow.save(false)) {
                workflowUserMappingList.each { workflowUserMapping ->
                    workflowUserMapping.save(validate: false, insert: true)
                }
                workflowCustomerMappingList.each { workflowCustomerMapping ->
                    workflowCustomerMapping.save(validate: false, insert: true)
                }
                return workflow
            }
            return null
        } catch(Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer updateWorkflow(Map map) {
        try{
        Workflow workflow = (Workflow) map.get('workflow')
        List<WorkflowUserMapping> workflowUserMappingList = (List<WorkflowUserMapping>) map.get('workflowUserMapping')
        List<WorkflowCustomerMapping> workflowCustomerMappingList = (List<WorkflowCustomerMapping>) map.get('workflowCustomerMapping')
        if (workflow.save()) {
            workflowUserMappingList.each {  workflowUserMapping ->
                if(workflowUserMapping.id)
                    workflowUserMapping.save(validate: false, insert: false)
                else
                    workflowUserMapping.save(validate: false, insert: true)
            }
            workflowCustomerMappingList.each { workflowCustomerMapping ->
                if(workflowCustomerMapping.id)
                    workflowCustomerMapping.save(validate: false, insert: false)
                else
                    workflowCustomerMapping.save(validate: false, insert: true)
            }
            return new Integer(1)
        } else {
            return new Integer(0)
        }
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listCustomerByEnterprise(Object params) {
        try{
            sql = new Sql(dataSource)
            String query = ""
            if (params.query) {
                query = " AND (customer_master.name LIKE '%${params.query}%' OR customer_master.code LIKE '%${params.query}%')"
            }
            String strSql = """
                SELECT `customer_master`.id, `customer_master`.`code`, `customer_master`.`name`, `enterprise_configuration`.`name` AS `enterprise`,
                    master_type.`name` AS status, '' AS geo_location, IFNULL(`customer_master`.`present_address`, '') AS `present_address`, `customer_category`.`name` AS `category`
                FROM `customer_master`
                    INNER JOIN `master_type` ON `master_type`.`id` = `customer_master`.`master_type_id`
                    INNER JOIN `customer_category` ON (`customer_master`.`category_id` = `customer_category`.`id`)
                    INNER JOIN `enterprise_configuration`
                         ON (`customer_master`.`enterprise_configuration_id` = `enterprise_configuration`.`id`)
                WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                    AND `customer_master`.`enterprise_configuration_id` =:enterpriseId
                    AND `customer_master`.`master_type_id` = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID}
                    AND `customer_master`.`id` NOT IN ( SELECT customer_master_id FROM workflow_customer_mapping )
                    ${query}
                ORDER BY `customer_master`.`code`
            """
            return sql.rows(strSql, params)
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

}
