package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.util.ApplicationConstants
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import groovy.sql.Sql
import javax.sql.DataSource
import com.docu.common.Action

class SalesHeadService extends Service {
    static transactional = false
    DataSource dataSource

    @Transactional(readOnly = true)
    public SalesHead read(Long id) {
        return SalesHead.read(id)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Map params) {
        try {
            List<SalesHead> salesHeadList = SalesHead.withCriteria {
                maxResults(Integer.parseInt(action.resultPerPage.toString()))
                firstResult(Integer.parseInt(action.start.toString()))
                order(action.sortCol.toString(), action.sortOrder.toString())
            }
            long salesHeadCount = SalesHead.count()
            return [objList: salesHeadList, count: salesHeadCount]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List<SalesHead> list() {
        return SalesHead.list()
    }

    @Transactional
    public SalesHead create(Object object) {
        try {
            SalesHead salesHead = (SalesHead) object
            salesHead.save(vaidate: false, insert: true)
            return salesHead
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object object) {
        try {
            SalesHead salesHead = (SalesHead) object
            if (salesHead.save(vaidate: false)) {
                return new Integer(1)
            } else {
                return new Integer(0)
            }
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public SalesHead updateSalesHead(SalesHead salesHead) {
        try {
            salesHead.save(vaidate: false)
            return salesHead
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public SalesHead replaceSalesHead(SalesHead currentSalesHead, SalesHead newSalesHead) {
        try {
            int currentYear = DateUtil.getCurrentSystemYear()
            Date currentDate = new Date()
            if (currentYear == currentSalesHead.targetYear) {
                currentSalesHead.endDate = currentDate
            }
            currentSalesHead.isActive = false
            currentSalesHead.targetAmount = previousMonthTarget(currentSalesHead.targetYear, currentSalesHead.employee.id)
            currentSalesHead.save()

            if (currentYear == newSalesHead.targetYear) {
                newSalesHead.startDate = currentDate
            } else {
                newSalesHead.startDate = DateUtil.getSimpleDateWithFormat('01-01-' + newSalesHead.targetYear.toString(), ApplicationConstants.DATE_FORMAT)
            }
            newSalesHead.isActive = true
            newSalesHead.endDate = DateUtil.getSimpleDateWithFormat('31-12-' + newSalesHead.targetYear.toString(), ApplicationConstants.DATE_FORMAT)
            newSalesHead.save(validate: false, insert: true)

            // Replace Supper visor
            List<CustomerMaster> employeeList = CustomerMaster.findAllByCustomerMaster(currentSalesHead.employee)
            employeeList.each { employee ->
                employee.customerMaster = newSalesHead.employee
                employee.save(validate: false)
            }
            return newSalesHead
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }


    @Transactional
    public Integer delete(Object object) {
        try {
            SalesHead salesHead = (SalesHead) object
            salesHead.delete()
            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public SalesHead search(String fieldName, String fieldValue) {
        String query = "from SalesHead as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return SalesHead.find(query)
    }

    @Transactional(readOnly = true)
    public Float previousMonthTarget(int year, long employeeId) {
        Float previousTarget = 0
        Sql sql = new Sql(dataSource)
        String query = """
            SELECT SUM(`target_amount`) AS `target_amount`
            FROM
                `monthly_sales_target_by_amount`
            WHERE `target_year` = ${year}
                AND `target_month` <= MONTH(NOW())
                AND `employee_id` = ${employeeId}
            GROUP BY `target_year`, `target_month`
        """
        List result = sql.rows(query)
        if (result && result.size() > 0) {
            previousTarget = result.first().target_amount
        }
        return previousTarget
    }

    @Transactional
    public boolean createVisitPlan(Object object) {
        try {
            List<VisitPlan> visitPlanList = object.get("visitPlanList")
            visitPlanList.each {
                it.save(false)
            }
            return true
        }
        catch (Exception e) {
            log.error(e.message)
            throw new RuntimeException(e.message)
        }
    }

    @Transactional(readOnly = true)
    public Map getListForVisitPlanGrid(Object object) {
        try {
            String dateFrom = ""
            String dateTo = ""
            if (object.dateFrom != "") {
                dateFrom = """ AND DATE(visit_plan.date_from) >= STR_TO_DATE('${object.dateFrom}', '${ApplicationConstants.DATE_FORMAT_DB}') """
            }
            if (object.dateTo != "") {
                dateTo = """ AND DATE(visit_plan.date_to) <= STR_TO_DATE('${object.dateTo}','${ApplicationConstants.DATE_FORMAT_DB}') """
            }
            Sql sql = new Sql(dataSource)
            String strSql = """
                SELECT * FROM visit_plan
                WHERE visit_plan.customer_master_id = ${object.employeeId}
                ${dateFrom}
                ${dateTo}
            """
            List objList = sql.rows(strSql)
            long total = objList.size()
            return [objList: objList, count: total]
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }


    @Transactional(readOnly = true)
    public List employeeNameList(Object params, ApplicationUser applicationUser) {
        List employeeName = []
        Sql sql = new Sql(dataSource)
        String query = """ SELECT DISTINCT `customer_master`.id ,`customer_master`.code, `customer_master`.name as employee
,enterprise_configuration.`name` as enterprise,department.`name` as department,designation.`name` as designation FROM  `customer_master`

INNER JOIN  `application_user` ON application_user.`customer_master_id`= `customer_master`.`user_created_id`
INNER JOIN `user_type` ON `user_type`.`id` = `application_user`.`user_type_id`
  INNER JOIN `enterprise_configuration`
                     ON (`customer_master`.`enterprise_configuration_id` = `enterprise_configuration`.`id`)
                INNER JOIN `department`
                    ON (`customer_master`.`department_id` = `department`.`id`)
                 INNER JOIN `designation`
                    ON (`customer_master`.`designation_id` = `designation`.`id`)
 WHERE `application_user`.`user_type_id`= 4 AND `customer_master`.name LIKE "${params.emp}%" OR `customer_master`.`code` LIKE  "${params.emp}%"
  AND `customer_master`.`customer_status` = 'ACTIVE' """
        employeeName = sql.rows(query)
        return employeeName
    }

    @Transactional(readOnly = true)
    public List popupemployeeNameList(Object params, ApplicationUser applicationUser) {
        List employeeName = []
        Sql sql = new Sql(dataSource)
        String query = """SELECT DISTINCT `customer_master`.id ,`customer_master`.code, `customer_master`.name as employee
,enterprise_configuration.`name` as enterprise,department.`name` as department,designation.`name` as designation FROM  `customer_master`

INNER JOIN  `application_user` ON application_user.`customer_master_id`= `customer_master`.`user_created_id`
INNER JOIN `user_type` ON `user_type`.`id` = `application_user`.`user_type_id`
  INNER JOIN `enterprise_configuration`
                     ON (`customer_master`.`enterprise_configuration_id` = `enterprise_configuration`.`id`)
                INNER JOIN `department`
                    ON (`customer_master`.`department_id` = `department`.`id`)
                 INNER JOIN `designation`
                    ON (`customer_master`.`designation_id` = `designation`.`id`)
 WHERE `application_user`.`user_type_id`= 4 AND `customer_master`.`customer_status` = 'ACTIVE'  """
        employeeName = sql.rows(query)
        return employeeName
    }
}
