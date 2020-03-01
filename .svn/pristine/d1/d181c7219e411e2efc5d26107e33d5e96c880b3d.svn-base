package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.CustomerStatus
import com.bits.bdfp.util.ApplicationConstants
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import groovy.sql.Sql
import javax.sql.DataSource
import com.docu.common.Action

class MonthlySalesTargetByAmountService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    SpringSecurityService  springSecurityService

    @Transactional(readOnly = true)
    public MonthlySalesTargetByAmount read(Long id) {
        return MonthlySalesTargetByAmount.read(id)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Map params) {
        try {
            List<MonthlySalesTargetByAmount> monthlySalesTargetByAmountList = MonthlySalesTargetByAmount.withCriteria {
                maxResults(Integer.parseInt(action.resultPerPage.toString()))
                firstResult(Integer.parseInt(action.start.toString()))
                order(action.sortCol.toString(), action.sortOrder.toString())
            }
            long monthlySalesTargetByAmountCount = MonthlySalesTargetByAmount.count()
            return [objList: monthlySalesTargetByAmountList, count: monthlySalesTargetByAmountCount]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List<MonthlySalesTargetByAmount> list() {
        return MonthlySalesTargetByAmount.list()
    }

    @Transactional
    public MonthlySalesTargetByAmount create(Object object) {
        try {
            MonthlySalesTargetByAmount monthlySalesTargetByAmount = (MonthlySalesTargetByAmount) object
            return monthlySalesTargetByAmount.save(vaidate: false, insert: true)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Boolean createNewSalesTarget(Map data){
        try {
            YearlySalesTargetByAmount yearlySalesTargetByAmount = (YearlySalesTargetByAmount) data.get("yearlySalesTargetByAmount")
            yearlySalesTargetByAmount.save()

            List<YearlySalesTargetByAmount> subordinateYearlySalesTargetByAmountList = (List<YearlySalesTargetByAmount>) data.get("subordinateYearlySalesTargetByAmountList")
            subordinateYearlySalesTargetByAmountList.each { subordinateYearlySalesTargetByAmount->
                subordinateYearlySalesTargetByAmount.save()
            }

            List<MonthlySalesTargetByAmount> monthlySalesTargetByAmountList = (List<MonthlySalesTargetByAmount>) data.get("monthlySalesTargetByAmountList")
            monthlySalesTargetByAmountList.each { monthlySalesTargetByAmount->
                monthlySalesTargetByAmount.save()
            }

            List<DailySalesTargetByAmount> dailySalesTargetByAmountList = (List<DailySalesTargetByAmount>) data.get("dailySalesTargetByAmountList")
            dailySalesTargetByAmountList.each { dailySalesTargetByAmount->
                dailySalesTargetByAmount.save()
            }
            return Boolean.TRUE
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Boolean updateSalesTarget(Map data, CustomerMaster currentEmployee){
        try {
            YearlySalesTargetByAmount yearlySalesTargetByAmount = (YearlySalesTargetByAmount) data.get("yearlySalesTargetByAmount")
            yearlySalesTargetByAmount.save()

            // Delete Subordinate Monthly Sales Target
            MonthlySalesTargetByAmount.executeUpdate("delete from MonthlySalesTargetByAmount msta where msta.yearlySalesTargetByAmount =:yearlySalesTarget and msta.employee !=:employee", [yearlySalesTarget: yearlySalesTargetByAmount, employee: currentEmployee])
            // Delete Subordinate Daily Sales Target
            DailySalesTargetByAmount.executeUpdate("delete from DailySalesTargetByAmount dsta where dsta.yearlySalesTargetByAmount = ?", [yearlySalesTargetByAmount])

            List<YearlySalesTargetByAmount> subordinateYearlySalesTargetByAmountList = (List<YearlySalesTargetByAmount>) data.get("subordinateYearlySalesTargetByAmountList")
            subordinateYearlySalesTargetByAmountList.each { subordinateYearlySalesTargetByAmount->
                subordinateYearlySalesTargetByAmount.save()
            }

            List<MonthlySalesTargetByAmount> monthlySalesTargetByAmountList = (List<MonthlySalesTargetByAmount>) data.get("monthlySalesTargetByAmountList")
            monthlySalesTargetByAmountList.each { monthlySalesTargetByAmount->
                monthlySalesTargetByAmount.save()
            }

            List<DailySalesTargetByAmount> dailySalesTargetByAmountList = (List<DailySalesTargetByAmount>) data.get("dailySalesTargetByAmountList")
            dailySalesTargetByAmountList.each { dailySalesTargetByAmount->
                dailySalesTargetByAmount.save()
            }
            return Boolean.TRUE
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object object) {
        try {
            MonthlySalesTargetByAmount monthlySalesTargetByAmount = (MonthlySalesTargetByAmount) object
            if (monthlySalesTargetByAmount.save(vaidate: false)) {
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
    public Integer delete(Object object) {
        try {
            MonthlySalesTargetByAmount monthlySalesTargetByAmount = (MonthlySalesTargetByAmount) object
            monthlySalesTargetByAmount.delete()
            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public MonthlySalesTargetByAmount search(String fieldName, String fieldValue) {
        String query = "from MonthlySalesTargetByAmount as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return MonthlySalesTargetByAmount.find(query)
    }

    @Transactional(readOnly = true)
    public List<MonthlySalesTargetByAmount> monthWiseYearlyTargetList(int targetYear, CustomerMaster employee) {
        try{
            List<MonthlySalesTargetByAmount> monthlySalesTargetByAmountList = new ArrayList<MonthlySalesTargetByAmount>()
            int targetMonth
            for(targetMonth = 1; targetMonth <= 12; targetMonth++){
                MonthlySalesTargetByAmount monthlySalesTargetByAmount = MonthlySalesTargetByAmount.findWhere(employee: employee, targetYear: targetYear, targetMonth: targetMonth)
                if(!monthlySalesTargetByAmount){
                    monthlySalesTargetByAmount = new MonthlySalesTargetByAmount(targetYear: targetYear, targetMonth: targetMonth, employee: employee, targetAmount: 0.00)
                }
                monthlySalesTargetByAmountList.add(monthlySalesTargetByAmount)
            }

            return monthlySalesTargetByAmountList
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public BigDecimal yearlySalesTarget(int targetYear, CustomerMaster employee){
        BigDecimal targetAmount = 0.00
        YearlySalesTargetByAmount yearlySalesTargetByAmount = YearlySalesTargetByAmount.findByTargetYearAndEmployee(targetYear, employee)
        if(yearlySalesTargetByAmount){
           targetAmount = yearlySalesTargetByAmount.targetAmount
        } else{
            SalesHead salesHead = SalesHead.findByTargetYearAndEmployee(targetYear, employee)
            if(salesHead){
                targetAmount = salesHead.targetAmount
            }
        }
        return targetAmount
    }

    @Transactional(readOnly = true)
    public List listSalesManForTargetSetup(Object params) {
        try{
            ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
            sql = new Sql(dataSource)
            String query = """
                SELECT DISTINCT `customer_master`.`id`, `customer_master`.`legacy_id` AS `code`, `customer_master`.`name`, `customer_sales_channel`.`name` AS `salesChannel`, `customer_category`.`name` AS `category`
                FROM
                    `customer_master`
                    INNER JOIN `customer_sales_channel`
                        ON (`customer_master`.`customer_sales_channel_id` = `customer_sales_channel`.`id`)
                    INNER JOIN `customer_category`
                        ON (`customer_master`.`category_id` = `customer_category`.`id`)
                    INNER JOIN `customer_territory_sub_area`
                        ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id` AND `customer_master`.`master_type_id` = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID})
                WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                    AND `customer_master`.`master_type_id` = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID}
                    AND `customer_master`.`category_id` = ${ApplicationConstants.CUSTOMER_CATEGORY_SALES_MAN_ID}
                    AND `customer_territory_sub_area`.`territory_sub_area_id` IN (SELECT `territory_sub_area_id` FROM `customer_territory_sub_area` WHERE `customer_master_id` = ${applicationUser.customerMasterId})
            """
            List resultList = sql.rows(query)
            return resultList
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listSubordinateAndSalesManForTargetSetup(Object params) {
        try{
            ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
            sql = new Sql(dataSource)
            String query = """
                SELECT * FROM (
                    SELECT DISTINCT `customer_master`.`id`, `customer_master`.`code`, `customer_master`.`name`, `designation`.`name` AS `designation`, `department`.`name` AS `department`, 1 AS isEmployee
                    FROM
                        `customer_master`
                        INNER JOIN `designation`
                            ON (`customer_master`.`designation_id` = `designation`.`id`)
                        INNER JOIN `department`
                            ON (`customer_master`.`department_id` = `department`.`id`)
                    WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                        AND `customer_master`.`master_type_id` = ${ApplicationConstants.EMPLOYEE_MASTER_TYPE_ID}
                        AND `customer_master`.`customer_master_id` =  ${applicationUser.customerMasterId}
                    UNION ALL
                    SELECT DISTINCT `customer_master`.`id`, `customer_master`.`legacy_id` AS `code`, `customer_master`.`name`, `customer_sales_channel`.`name` AS `designation`, `customer_category`.`name` AS `department`, 0 AS isEmployee
                    FROM
                        `customer_master`
                        INNER JOIN `customer_sales_channel`
                            ON (`customer_master`.`customer_sales_channel_id` = `customer_sales_channel`.`id`)
                        INNER JOIN `customer_category`
                            ON (`customer_master`.`category_id` = `customer_category`.`id`)
                        INNER JOIN `customer_territory_sub_area`
                            ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id` AND `customer_master`.`master_type_id` = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID})
                    WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                        AND `customer_master`.`master_type_id` = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID}
                        AND `customer_master`.`category_id` = ${ApplicationConstants.CUSTOMER_CATEGORY_SALES_MAN_ID}
                        AND `customer_territory_sub_area`.`territory_sub_area_id` IN (SELECT `territory_sub_area_id` FROM `customer_territory_sub_area` WHERE `customer_master_id` = ${applicationUser.customerMasterId})
                ) AS `tbl`
            """
            List resultList = sql.rows(query)
            return resultList
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listSubordinateMonthLySalesTarget(Object params) {
        try{
            ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
            sql = new Sql(dataSource)
            String query = """
                SELECT id, `code`, `name`, SUM(ROUND(`jan`,2))AS `jan`, SUM(ROUND(`feb`,2)) AS `feb`, SUM(ROUND(`mar`,2)) AS `mar`, SUM(ROUND(`apr`,2)) AS `apr`, SUM(ROUND(`may`,2)) AS `may`,
                    SUM(ROUND(`jun`,2)) AS `jun`, SUM(ROUND(`jul`,2)) AS `jul`, SUM(ROUND(`aug`,2)) AS `aug`, SUM(ROUND(`sep`,2)) AS `sep`, SUM(ROUND(`oct`,2)) AS `oct`, SUM(ROUND(`nov`,2)) AS `nov`, SUM(ROUND(`dec`,2)) AS `dec`
                FROM(
                    SELECT `customer_master`.`id`, `customer_master`.`code`, `customer_master`.`name`, `monthly_sales_target_by_amount`.`target_month`
                        , IF(`monthly_sales_target_by_amount`.`target_month` = 1,`monthly_sales_target_by_amount`.`target_amount`, 0) AS 'jan'
                        , IF(`monthly_sales_target_by_amount`.`target_month` = 2,`monthly_sales_target_by_amount`.`target_amount`, 0) AS 'feb'
                        , IF(`monthly_sales_target_by_amount`.`target_month` = 3,`monthly_sales_target_by_amount`.`target_amount`, 0) AS 'mar'
                        , IF(`monthly_sales_target_by_amount`.`target_month` = 4,`monthly_sales_target_by_amount`.`target_amount`, 0) AS 'apr'
                        , IF(`monthly_sales_target_by_amount`.`target_month` = 5,`monthly_sales_target_by_amount`.`target_amount`, 0) AS 'may'
                        , IF(`monthly_sales_target_by_amount`.`target_month` = 6,`monthly_sales_target_by_amount`.`target_amount`, 0) AS 'jun'
                        , IF(`monthly_sales_target_by_amount`.`target_month` = 7,`monthly_sales_target_by_amount`.`target_amount`, 0) AS 'jul'
                        , IF(`monthly_sales_target_by_amount`.`target_month` = 8,`monthly_sales_target_by_amount`.`target_amount`, 0) AS 'aug'
                        , IF(`monthly_sales_target_by_amount`.`target_month` = 9,`monthly_sales_target_by_amount`.`target_amount`, 0) AS 'sep'
                        , IF(`monthly_sales_target_by_amount`.`target_month` = 10,`monthly_sales_target_by_amount`.`target_amount`, 0) AS 'oct'
                        , IF(`monthly_sales_target_by_amount`.`target_month` = 11,`monthly_sales_target_by_amount`.`target_amount`, 0) AS 'nov'
                        , IF(`monthly_sales_target_by_amount`.`target_month` = 12,`monthly_sales_target_by_amount`.`target_amount`, 0) AS 'dec'
                    FROM `monthly_sales_target_by_amount`
                        INNER JOIN `customer_master` ON (`monthly_sales_target_by_amount`.`employee_id` = `customer_master`.`id`)
                    WHERE `monthly_sales_target_by_amount`.`target_year` =:targetYear
                        AND `monthly_sales_target_by_amount`.`is_active` = TRUE
                        AND `monthly_sales_target_by_amount`.`yearly_sales_target_by_amount_id` = (SELECT `id` FROM `yearly_sales_target_by_amount` WHERE `employee_id` = ${applicationUser.customerMasterId} AND `target_year` =:targetYear)
                        AND `monthly_sales_target_by_amount`.`employee_id` != ${applicationUser.customerMasterId}
                    ) AS `tbl`
                GROUP BY `tbl`.`id`
            """
            List resultList = sql.rows(query, params)
            return resultList
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listCustomerDailySalesTarget(Object params) {
        try{
            String filter = ""
            if(params.targetMonth){
                filter += """
                    AND MONTH(`target_date`) = ${params.targetMonth}
                """
            }
            if(params.targetYear){
                filter += """
                    AND YEAR(`target_date`) = ${params.targetYear}
                """
            }
            String query = """
                SELECT `customer_master`.`code`,`customer_master`.`name`,`target_amount` AS `targetAmount`, '${params.monthName}' AS monthName
                FROM `daily_sales_target_by_amount`
                    INNER JOIN `customer_master` ON (`daily_sales_target_by_amount`.`employee_id` = `customer_master`.`id`)
                WHERE employee_id = ${params.customerId}
                    ${filter}
                ORDER BY `target_date`
            """
            sql = new Sql(dataSource)
            List resultList = sql.rows(query)
            return resultList
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listCustomerYearlyDailySalesTarget(Object params) {
        try{
            List<List> resultList = new ArrayList<List>()
            for(int i = 1; i <= 12; i++){
                String query = """
                    SELECT `customer_master`.`code`,`customer_master`.`name`,`target_amount` AS `targetAmount`
                    FROM `daily_sales_target_by_amount`
                        INNER JOIN `customer_master` ON (`daily_sales_target_by_amount`.`employee_id` = `customer_master`.`id`)
                    WHERE employee_id = ${params.customerId}
                        AND YEAR(`target_date`) = ${params.targetYear}
                        AND MONTH(`target_date`) = ${i}
                    ORDER BY `target_date`
                """
                sql = new Sql(dataSource)
                List result = sql.rows(query)
                resultList.add(result)
            }
            return resultList
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
