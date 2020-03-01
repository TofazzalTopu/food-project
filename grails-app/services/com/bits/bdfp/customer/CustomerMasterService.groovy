package com.bits.bdfp.customer

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.JournalDetailsService
import com.bits.bdfp.util.ApplicationConstants
import com.docu.commons.CommonConstants
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import groovy.sql.GroovyRowResult
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

import java.text.DecimalFormat
import java.text.NumberFormat

class CustomerMasterService extends Service {

    CustomerTerritorySubAreaService customerTerritorySubAreaService
    CustomerShippingAddressService customerShippingAddressService
    SpringSecurityService  springSecurityService
    JournalDetailsService journalDetailsService

    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public CustomerMaster create(Object object) {
        try{
            CustomerMaster customerMaster = object.get('customerMaster')
            if (customerMaster.save(false)) {
                CustomerShippingAddress[] customerShippingAddresses = object.get('customerShippingAddresses')
                for (int i = 0; i < customerShippingAddresses.length; i++) {
                    customerShippingAddressService.create(customerShippingAddresses[i])
                }
                List<CustomerTerritorySubArea> customerTerritorySubAreaList = object.get('customerTerritorySubAreaList')
                customerTerritorySubAreaList.each {  customerTerritorySubArea->
                    customerTerritorySubAreaService.create(customerTerritorySubArea)
                }
            }
            return customerMaster
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public CustomerMaster createEmployee(Map data) {
        try{
            CustomerMaster customerMaster = (CustomerMaster) data.get(CustomerMaster.class.simpleName)
            customerMaster.save(false)

            List<CustomerTerritorySubArea> customerTerritorySubAreaList = (List) data.get(CustomerTerritorySubArea.class.simpleName)
            customerTerritorySubAreaList.each { customerTerritorySubArea ->
                customerTerritorySubArea.save(false)
            }
            return customerMaster
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public CustomerMaster updateEmployee(Map data) {
        try{
            CustomerMaster customerMaster = (CustomerMaster) data.get(CustomerMaster.class.simpleName)
            customerMaster.save(false)

            CustomerTerritorySubArea.executeUpdate("delete CustomerTerritorySubArea ctsa where ctsa.customerMaster=?", [customerMaster])

            List<CustomerTerritorySubArea> customerTerritorySubAreaList = (List) data.get(CustomerTerritorySubArea.class.simpleName)
            customerTerritorySubAreaList.each { customerTerritorySubArea ->
                customerTerritorySubArea.save(false)
            }
            return customerMaster
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object object) {
        try{
            CustomerMaster customerMaster = object.get('customerMaster')
            if (customerMaster.save()) {
                CustomerShippingAddress[] customerShippingAddresses = object.get('customerShippingAddresses')
                for (int i = 0; i < customerShippingAddresses.length; i++) {
                    customerShippingAddressService.create(customerShippingAddresses[i])
                }
                CustomerTerritorySubArea[] customerTerritorySubAreas = object.get('customerTerritorySubAreas')
                println(customerTerritorySubAreas.length);
                for (int i = 0; i < customerTerritorySubAreas.length; i++) {
                    customerTerritorySubAreaService.create(customerTerritorySubAreas[i])
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

    @Transactional
    public Integer delete(Object object) {
        CustomerMaster customerMaster = (CustomerMaster) object
        customerMaster.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<CustomerMaster> objList = CustomerMaster.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = CustomerMaster.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<CustomerMaster> list() {
        return CustomerMaster.list()
    }

    @Transactional(readOnly = true)
    public CustomerMaster read(Long id) {
        return CustomerMaster.get(id)
    }

    @Transactional(readOnly = true)
    public CustomerMaster search(String fieldName, String fieldValue) {
        String query = "from CustomerMaster as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return CustomerMaster.find(query)
    }

    @Transactional(readOnly = true)
    public List getFlexBoxSupervisorList(Object params) {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        String filter = ""
        if(!params.editEmployee){
            filter = " AND `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}' "
        }
        String query = """
            SELECT `id`, CONCAT('[', `code`, '] ', `name`) AS `name`
            FROM `customer_master`
            WHERE `master_type_id` = ${ApplicationConstants.EMPLOYEE_MASTER_TYPE_ID}
                AND `customer_master`.`enterprise_configuration_id`
                    IN ( SELECT `enterprise_configuration_id`
                        FROM `application_user_enterprise`
                        WHERE `is_active` = TRUE AND `application_user_id` = ${applicationUser.id} )
                ${filter}

        """
        if (params.customerId) {
            // Exclude self during employee update
            query += " AND `customer_master`.`id` != ${params.customerId}"
        }
        Sql sql = new Sql(dataSource)
        return sql.rows(query)
    }

    /** Load all active/ inactive employee **/
    @Transactional(readOnly = true)
    public List<GroovyRowResult> getEmployeeList() {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        String query = """
             SELECT
                 `customer_master`.`id`
                , `customer_master`.`code`
                , `customer_master`.`name`
                , `designation`.`name` AS `designation`
                , `department`.`name` AS `department`
                , `enterprise_configuration`.`name` AS `enterprise`
                , `customer_status` AS `status`
            FROM
                `customer_master`
                INNER JOIN `department`
                    ON (`department_id` = `department`.`id`)
                INNER JOIN `designation`
                    ON (`designation_id` = `designation`.`id`)
                INNER JOIN `enterprise_configuration`
                    ON (`customer_master`.`enterprise_configuration_id` = `enterprise_configuration`.`id`)
            WHERE `customer_master`.`master_type_id` = ${ApplicationConstants.EMPLOYEE_MASTER_TYPE_ID}
                AND `customer_master`.`enterprise_configuration_id`
                    IN ( SELECT `enterprise_configuration_id`
                        FROM `application_user_enterprise`
                        WHERE `is_active` = TRUE AND `application_user_id` = ${applicationUser.id} )
        """
        List<GroovyRowResult> groovyRowResultList = new Sql(dataSource).rows(query)
        return groovyRowResultList
    }

    /** Load all active/ inactive customer **/
    @Transactional(readOnly = true)
    public List<GroovyRowResult> getCustomerList() {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        String query = """
             SELECT
                 `customer_master`.`id`
                , `customer_master`.`legacy_id`
                , `customer_master`.`code`
                , `customer_master`.`name`
                , `enterprise_configuration`.`name` AS `enterprise`
                , `customer_status` AS `status`
                , `customer_master`.`present_address` AS `address`
                , `customer_category`.`name` AS `category`
            FROM
                `customer_master`
                INNER JOIN `enterprise_configuration`
                    ON (`customer_master`.`enterprise_configuration_id` = `enterprise_configuration`.`id`)
                INNER JOIN `customer_category` ON (`customer_master`.`category_id` = `customer_category`.`id`)
            WHERE `customer_master`.`master_type_id` = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID}
                AND `customer_master`.`enterprise_configuration_id`
                    IN ( SELECT `enterprise_configuration_id`
                        FROM `application_user_enterprise`
                        WHERE `is_active` = TRUE AND `application_user_id` = ${applicationUser.id} )
        """
        List<GroovyRowResult> groovyRowResultList = new Sql(dataSource).rows(query)
        return groovyRowResultList
    }
    @Transactional(readOnly = true)
    public Map getCustomerEligibilityMasterList(){
        String query = """
                        select id,title from customer_eligibility_master
                        """
        List<GroovyRowResult> groovyRowResultList = new Sql(dataSource).rows(query)
        long total = groovyRowResultList.count()
        return [objList: groovyRowResultList, count: total]
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
                    AND `customer_master`.`enterprise_configuration_id` = ${params.id}
                    AND `customer_master`.`master_type_id` = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID}
                    ${query}
                ORDER BY `customer_master`.`code`
            """
            return sql.rows(strSql)
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listNegotiatedCustomerByEnterprise(Object params) {
        try{
            sql = new Sql(dataSource)
            String query = ""
            if (params.query) {
                query = " AND (customer_master.name LIKE '%${params.query}%' OR customer_master.code LIKE '%${params.query}%')"
            }
            String strSql = """
                SELECT customer_master.id, customer_master.code, customer_master.legacy_id, customer_master.name, customer_master.present_address,
                    pricing_category.short_name, pricing_category.name AS "partnerType"
                FROM customer_master
                    INNER JOIN pricing_category ON (pricing_category.id = customer_master.pricing_category_id)
                WHERE customer_master.customer_status = '${CustomerStatus.ACTIVE}'
                    AND customer_master.enterprise_configuration_id = ${params.enterpriseId}
                    AND customer_master.master_type_id = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID}
                    AND pricing_category.id = ${ApplicationConstants.PRICING_CATEGORY_NEGOTIATED_ID}
                    ${query}
            """
            List resultList = sql.rows(strSql)
            return resultList
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List getFlexBoxCustomerList(Object params) {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        String query = """
            SELECT `id`, legacy_id, `code`,`present_address` , `name`
            FROM `customer_master`
            WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                AND `master_type_id` = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID}
                AND `customer_master`.`enterprise_configuration_id`
                    IN ( SELECT `enterprise_configuration_id`
                        FROM `application_user_enterprise`
                        WHERE `is_active` = TRUE AND `application_user_id` = ${applicationUser.id} )
                AND (`name` LIKE '%${params.searchKey}%'
                OR `code` LIKE '%${params.searchKey}%'
                OR `legacy_id` LIKE '%${params.searchKey}%')
        """
        if (params.customerId) {
            // Exclude self during employee update
            query += " AND `customer_master`.`id` != ${params.customerId}"
        }
        Sql sql = new Sql(dataSource)
        return sql.rows(query)
    }

    @Transactional(readOnly = true)
    public List listCustomerAutoCompleteByEnterprise(Object params ) {
        sql = new Sql(dataSource)
        String  strSql = """
            SELECT `customer_master`.id,`customer_master`.`code`,`customer_master`.`name`,
                master_type.`name` AS status,`territory_sub_area`.`geo_location`
            FROM `customer_master`
                INNER JOIN master_type ON master_type.id =`customer_master`.`master_type_id`
                INNER JOIN `customer_territory_sub_area` ON `customer_master`.id=`customer_territory_sub_area`.`customer_master_id`
                INNER JOIN `territory_sub_area` ON `territory_sub_area`.id=`customer_territory_sub_area`.`territory_sub_area_id`
                INNER JOIN `enterprise_configuration` ON `enterprise_configuration`.id=`customer_master`.`enterprise_configuration_id`
            WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                AND `enterprise_configuration`.`id` = ${params.enterpriseId}
                AND ( `customer_master`.`code` LIKE('%${params.searchKey}%') OR  `customer_master`.`name` LIKE('%${params.searchKey}%'))
        """
        List resultList = sql.rows(strSql.toString())
        return resultList
    }

    @Transactional(readOnly = true)
    public List listCustomerAutoCompleteForPrimaryDemandOrder(Object params ) {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        sql = new Sql(dataSource)
        String  strSql = """
            SELECT `customer_master`.`id`, `customer_master`.`code`,`customer_master`.`name`,
                `master_type`.`name` AS `status`, `customer_master`.`present_address`
            FROM `customer_master`
                INNER JOIN `master_type` ON `master_type`.`id` = `customer_master`.`master_type_id`
            WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                AND `customer_master`.`enterprise_configuration_id`
                    IN ( SELECT `enterprise_configuration_id`
                        FROM `application_user_enterprise`
                        WHERE `is_active` = TRUE AND `application_user_id` = ${applicationUser.id} )
                AND (`customer_master`.`code` LIKE('%${params.searchKey}%') OR `customer_master`.`name` LIKE('%${params.searchKey}%'))
        """

        List resultList = sql.rows(strSql.toString())
        return resultList
    }

    @Transactional(readOnly = true)
    public List listCustomerAutoCompleteByApplicationUser(Object params ) {
        sql = new Sql(dataSource)
        String  strSql = """
            SELECT `customer_master`.`id`, `customer_master`.`code`,`customer_master`.`name`,
                `master_type`.`name` AS `status`, `customer_master`.`present_address`
            FROM `customer_master`
                INNER JOIN `master_type` ON `master_type`.`id` = `customer_master`.`master_type_id`
            WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                AND `customer_master`.`enterprise_configuration_id` IN (SELECT `enterprise_configuration_id` FROM `application_user_enterprise`
                    WHERE `is_active` = TRUE AND `application_user_id` =:applicationUserId)
                AND (`customer_master`.`code` LIKE('%${params.searchKey}%') OR `customer_master`.`name` LIKE('%${params.searchKey}%'))
        """

        List resultList = sql.rows(strSql, params)
        return resultList
    }

    @Transactional(readOnly = true)
    public List listPosCustomerAutoCompleteByApplicationUser(Object params ) {
        sql = new Sql(dataSource)
        String  strSql = """
            SELECT `customer_master`.`id`, `customer_master`.`code`,`customer_master`.`name`,
                `master_type`.`name` AS `status`, `customer_master`.`present_address`
            FROM `pos_customer`
                INNER JOIN `customer_master` ON (`pos_customer`.`customer_master_id` = `customer_master`.`id`
                    AND `customer_master`.`enterprise_configuration_id` = `pos_customer`.`enterprise_configuration_id`)
                INNER JOIN `master_type` ON `master_type`.`id` = `customer_master`.`master_type_id`
            WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                AND `customer_master`.`enterprise_configuration_id` IN (SELECT `enterprise_configuration_id` FROM `application_user_enterprise`
                    WHERE `is_active` = TRUE AND `application_user_id` =:applicationUserId)
                AND (`customer_master`.`code` LIKE('%${params.searchKey}%') OR `customer_master`.`name` LIKE('%${params.searchKey}%'))
        """

        List resultList = sql.rows(strSql, params)
        return resultList
    }

    @Transactional(readOnly = true)
    public List listSalesEmployeeByApplicationUser(Object params) {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        sql = new Sql(dataSource)
        String filter = ""
        if (params.query) {
            filter = " AND (`customer_master`.`name` LIKE '%${params.query}%' OR `customer_master`.`code` LIKE '%${params.query}%')"
        }
        String query = """
            SELECT `customer_master`.id, `customer_master`.`code`,`customer_master`.`name`, `enterprise_configuration`.`name` AS `enterprise`,
                IFNULL(`customer_master`.`present_address`, '') AS `present_address`, `designation`.`name` AS `designation`, `department`.`name` AS `department`
            FROM `customer_master`
                INNER JOIN `enterprise_configuration`
                    ON (`customer_master`.`enterprise_configuration_id` = `enterprise_configuration`.`id`)
                INNER JOIN `department`
                    ON (`customer_master`.`department_id` = `department`.`id`)
                INNER JOIN `designation`
                    ON (`customer_master`.`designation_id` = `designation`.`id`)
            WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                AND `master_type_id` = ${ApplicationConstants.EMPLOYEE_MASTER_TYPE_ID}
                AND `customer_master`.`department_id` = ${ApplicationConstants.SALES_DEPARTMENT_ID}
                AND `customer_master`.`enterprise_configuration_id` IN (SELECT `enterprise_configuration_id` FROM `application_user_enterprise`
                    WHERE `is_active` = TRUE AND `application_user_id` = ${applicationUser.id})
                ${filter}
            ORDER BY `customer_master`.`code`
        """
        return sql.rows(query)
    }

    @Transactional(readOnly = true)
    public List listEmployeeByApplicationUser(Object params) {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        sql = new Sql(dataSource)
        String filter = ""
        if (params.query) {
            filter = " AND (`customer_master`.`name` LIKE '%${params.query}%' OR `customer_master`.`code` LIKE '%${params.query}%')"
        }
        String query = """
            SELECT `customer_master`.id, `customer_master`.`code`,`customer_master`.`name`, `enterprise_configuration`.`name` AS `enterprise`,
                IFNULL(`customer_master`.`present_address`, '') AS `present_address`, `designation`.`name` AS `designation`, `department`.`name` AS `department`
            FROM `customer_master`
                INNER JOIN `enterprise_configuration`
                    ON (`customer_master`.`enterprise_configuration_id` = `enterprise_configuration`.`id`)
                INNER JOIN `department`
                    ON (`customer_master`.`department_id` = `department`.`id`)
                INNER JOIN `designation`
                    ON (`customer_master`.`designation_id` = `designation`.`id`)
            WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                AND `master_type_id` = ${ApplicationConstants.EMPLOYEE_MASTER_TYPE_ID}
                AND `customer_master`.`enterprise_configuration_id` IN (SELECT `enterprise_configuration_id` FROM `application_user_enterprise`
                    WHERE `is_active` = TRUE AND `application_user_id` = ${applicationUser.id})
                ${filter}
            ORDER BY `customer_master`.`code`
        """
        return sql.rows(query)
    }

    /******** Employee List By Enterprise for Workflow **********/
    @Transactional(readOnly = true)
    public List listEmployeeByEnterpriseForWorkflow(Object params) {
        sql = new Sql(dataSource)
        String filter = ""
        if (params.query) {
            filter = " AND (`customer_master`.`name` LIKE '%${params.query}%' OR `customer_master`.`code` LIKE '%${params.query}%')"
        }
        String query = """
            SELECT `application_user`.id, `customer_master`.`code`,`customer_master`.`name`, `enterprise_configuration`.`name` AS `enterprise`,
                IFNULL(`customer_master`.`present_address`, '') AS `present_address`, `designation`.`name` AS `designation`, `application_user`.`username`
            FROM `application_user`
                INNER JOIN `customer_master`
                    ON (`application_user`.`customer_master_id` = `customer_master`.`id`)
                INNER JOIN `enterprise_configuration`
                    ON (`customer_master`.`enterprise_configuration_id` = `enterprise_configuration`.`id`)
                INNER JOIN `designation`
                    ON (`customer_master`.`designation_id` = `designation`.`id`)
            WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                AND `master_type_id` = ${ApplicationConstants.EMPLOYEE_MASTER_TYPE_ID}
                AND `customer_master`.`enterprise_configuration_id`=:enterpriseId
                ${filter}
            ORDER BY `customer_master`.`code`
        """
        return sql.rows(query, params)
    }

    @Transactional(readOnly = true)
    public List listCustomerByApplicationUserEnterprise(Object params) {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        sql = new Sql(dataSource)
        String filter = ""
        if (params.query) {
            filter = " AND (`customer_master`.`name` LIKE '%${params.query}%' OR `customer_master`.`code` LIKE '%${params.query}%')"
        }
        String query = """
            SELECT `customer_master`.id, `customer_master`.`code`,`customer_master`.`name`, `enterprise_configuration`.`name` AS `enterprise`,
                `master_type`.`name` AS `status`, IFNULL(`customer_master`.`present_address`, '') AS `present_address`, `customer_category`.`name` AS `category`
            FROM `customer_master`
                INNER JOIN  `master_type`  ON (`master_type`.`id` = `customer_master`.`master_type_id`)
                INNER JOIN `customer_category` ON (`customer_master`.`category_id` = `customer_category`.`id`)
                INNER JOIN `enterprise_configuration`
                                    ON (`customer_master`.`enterprise_configuration_id` = `enterprise_configuration`.`id`)
            WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                AND `master_type_id` = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID}
                AND `customer_master`.`enterprise_configuration_id` IN (SELECT `enterprise_configuration_id` FROM `application_user_enterprise`
                    WHERE `is_active` = TRUE AND `application_user_id` = ${applicationUser.id})
                ${filter}
            ORDER BY `customer_master`.`code`
        """
        return sql.rows(query)
    }

    @Transactional(readOnly = true)
    public List listCustomerForPrintPrimaryInvoice(Object params) {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        sql = new Sql(dataSource)
        String filter = ""
        if (params.query) {
            filter = " AND (`customer_master`.`name` LIKE '%${params.query}%' OR `customer_master`.`code` LIKE '%${params.query}%' OR `customer_master`.`legacy_id` LIKE '%${params.query}%')"
        }
        String query = """
            SELECT `customer_master`.id, `customer_master`.`code`,`customer_master`.`name`, `enterprise_configuration`.`name` AS `enterprise`,
                `master_type`.`name` AS `status`, IFNULL(`customer_master`.`present_address`, '') AS `present_address`, `customer_category`.`name` AS `category`
            FROM `customer_master`
                INNER JOIN  `master_type`  ON (`master_type`.`id` = `customer_master`.`master_type_id`)
                INNER JOIN `customer_category` ON (`customer_master`.`category_id` = `customer_category`.`id`)
                INNER JOIN `enterprise_configuration`
                                    ON (`customer_master`.`enterprise_configuration_id` = `enterprise_configuration`.`id`)
            WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                AND `master_type_id` = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID}
                AND `customer_master`.`enterprise_configuration_id` IN (SELECT `enterprise_configuration_id` FROM `application_user_enterprise`
                    WHERE `is_active` = TRUE AND `application_user_id` = ${applicationUser.id})
                AND `customer_master`.`category_id` != ${ApplicationConstants.CUSTOMER_CATEGORY_SALES_MAN_ID}
                ${filter}
            ORDER BY `customer_master`.`code`
        """
        return sql.rows(query)
    }

    @Transactional(readOnly = true)
    public List listCustomerByApplicationUser(Object params) {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        sql = new Sql(dataSource)
        String filter = ""
        if (params.query) {
            filter = " AND (`customer_master`.`name` LIKE '%${params.query}%' OR `customer_master`.`code` LIKE '%${params.query}%' OR  `customer_master`.`legacy_id` LIKE '%${params.query}%')"
        }

        if(params.categoryId){
            filter += " AND `customer_master`.`category_id` = ${params.categoryId} "
        }

        String query = """
            SELECT DISTINCT `customer_master`.id,
                `customer_master`.`code`,
                `customer_master`.`legacy_id`,
                `customer_master`.`name`,
                `enterprise_configuration`.`name` AS `enterprise`,
                `master_type`.`name` AS `status`,
                IFNULL(`customer_master`.`present_address`, '') AS `present_address`,
                `customer_category`.`name` AS `category`
            FROM `customer_master`
                INNER JOIN `customer_territory_sub_area`
                    ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`
                        AND customer_master.`master_type_id` = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID})
                INNER JOIN  `master_type`  ON (`master_type`.`id` = `customer_master`.`master_type_id`)
                INNER JOIN `customer_category` ON (`customer_master`.`category_id` = `customer_category`.`id`)
                INNER JOIN `enterprise_configuration`
                    ON (`customer_master`.`enterprise_configuration_id` = `enterprise_configuration`.`id`)
            WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                AND `customer_master`.`master_type_id` = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID}
                AND `customer_master`.`enterprise_configuration_id`
                    IN (SELECT `enterprise_configuration_id` FROM `application_user_enterprise`
                        WHERE `is_active` = TRUE
                        AND `application_user_id` = ${applicationUser.id})
                AND `customer_territory_sub_area`.`territory_sub_area_id`
                    IN (SELECT `customer_territory_sub_area`.`territory_sub_area_id`
                         FROM `customer_territory_sub_area`
                         WHERE `customer_territory_sub_area`.`customer_master_id` = ${params.customerId} )
            ${filter}
            ORDER BY `customer_master`.`code`
        """
        return sql.rows(query)
    }
 @Transactional(readOnly = true)
    public List listCustomerByApplicationUserForNewPrimaryDemandOrder(Object params) {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        sql = new Sql(dataSource)
        String filter = ""
        if (params.searchKey) {
            filter = " AND (`customer_master`.`name` LIKE '%${params.searchKey}%' OR `customer_master`.`code` LIKE '%${params.searchKey}%' OR  `customer_master`.`legacy_id` LIKE '%${params.searchKey}%')"
        }

        if(params.categoryId){
            filter += " AND `customer_master`.`category_id` = ${params.categoryId} "
        }

        String query = """
            SELECT DISTINCT `customer_master`.id,
                `customer_master`.`code`,
                `customer_master`.`legacy_id`,
                `customer_master`.`name`,
                `enterprise_configuration`.`name` AS `enterprise`,
                `master_type`.`name` AS `status`,
                IFNULL(`customer_master`.`present_address`, '') AS `present_address`,
                `customer_category`.`name` AS `category`
            FROM `customer_master`
                INNER JOIN `customer_territory_sub_area`
                    ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`
                        AND customer_master.`master_type_id` = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID})
                INNER JOIN  `master_type`  ON (`master_type`.`id` = `customer_master`.`master_type_id`)
                INNER JOIN `customer_category` ON (`customer_master`.`category_id` = `customer_category`.`id`)
                INNER JOIN `enterprise_configuration`
                    ON (`customer_master`.`enterprise_configuration_id` = `enterprise_configuration`.`id`)
            WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                AND `customer_master`.`master_type_id` = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID}
                AND `customer_master`.`enterprise_configuration_id`
                    IN (SELECT `enterprise_configuration_id` FROM `application_user_enterprise`
                        WHERE `is_active` = TRUE
                        AND `application_user_id` = ${applicationUser.id})
                AND `customer_territory_sub_area`.`territory_sub_area_id`
                    IN (SELECT `customer_territory_sub_area`.`territory_sub_area_id`
                         FROM `customer_territory_sub_area`
                         WHERE `customer_territory_sub_area`.`customer_master_id` = ${params.customerId} )
            ${filter}
            ORDER BY `customer_master`.`code`
        """
        return sql.rows(query)
    }

//    1429
    @Transactional(readOnly = true)
    public List listDpCustomerByApplicationUser(Object params) {
        sql = new Sql(dataSource)
        String filter = ""
        if (params.searchKey) {
            filter = " AND (cm.name LIKE '%${params.searchKey}%' OR cm.code LIKE '%${params.searchKey}%' OR  cm.legacy_id LIKE '%${params.searchKey}%')"
        }

        String query = """
            SELECT cm.id, cm.code, cm.legacy_id, cm.name, cm.present_address, cc.name AS category
            FROM customer_territory_sub_area ctsa
            INNER JOIN customer_master cm
                    ON cm.id = ctsa.customer_master_id
            INNER JOIN customer_category cc
                    ON cc.id = cm.category_id
            WHERE ctsa.territory_sub_area_id IN (
                    SELECT territory_sub_area_id
                    FROM distribution_point_territory_sub_area
                    WHERE distribution_point_id IN (
                        SELECT distribution_point_id
                        FROM application_user_distribution_point
                        WHERE application_user_id = ${params.customerId}
                    )
            )
            AND cm.customer_level IN ('SECONDARY','RETAIL')
            AND cm.customer_status = '${CustomerStatus.ACTIVE}'
            ${filter}
            GROUP BY cm.id
            ORDER BY cm.code ASC
        """
        return sql.rows(query)
    }

    @Transactional(readOnly = true)
    public List listPosCustomerByApplicationUser(Object params) {
        sql = new Sql(dataSource)
        String query = """
            SELECT `customer_master`.`id`, `customer_master`.`code`,`customer_master`.`name`,
                `master_type`.`name` AS `status`, IFNULL(`customer_master`.`present_address`, '') AS `present_address`
            FROM `pos_customer`
                INNER JOIN `customer_master` ON (`pos_customer`.`customer_master_id` = `customer_master`.`id`
                    AND `customer_master`.`enterprise_configuration_id` = `pos_customer`.`enterprise_configuration_id`)
                INNER JOIN  `master_type`  ON (`master_type`.`id` = `customer_master`.`master_type_id`)
            WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                AND `pos_customer`.`enterprise_configuration_id` IN (SELECT `enterprise_configuration_id` FROM `application_user_enterprise`
                WHERE `is_active` = TRUE AND `application_user_id` =:applicationUserId)
            ORDER BY `customer_master`.`code`
        """
        List resultList = sql.rows(query, params)
        return resultList
    }

    @Transactional(readOnly = true)
    public Map readCustomerBalanceAndShippingAddress(Object params) {
        CustomerMaster customerMaster = CustomerMaster.read(params.customerId)
        double balance = 0.0
        NumberFormat formatter = new DecimalFormat("#0.0000");
        String isOwn = 'true'
        sql = new Sql(dataSource)
        String query = """
            SELECT `id`, `address` FROM `customer_shipping_address`
            WHERE `customer_master_id` =:customerId
        """
        List addressList = sql.rows(query, params)
        query = """
            SELECT IFNULL(SUM(credit)-SUM(debit),0) AS advance FROM sub_ledger
                        WHERE acc_code = '${customerMaster.code+customerMaster.customerType.advanceCode}' AND is_active = true
        """
        List balanceList = sql.rows(query)
        if(balanceList && balanceList.size() > 0){
            balance = balanceList.first().advance
            formatter.format(balance)
        }
//        query = """
//            SELECT IFNULL(application_user_id, -1) AS applicationUserId FROM `customer_master`
//            WHERE `id` =:customerId LIMIT 1
//        """
//        List applicationIds = sql.rows(query, params)
//        if(applicationIds.size() > 0){
//            if(applicationIds.first().applicationUserId.toString() != params.applicationUserId){
//                isOwn = 'false'
//            }
//        }
        return [addressList: addressList, balance: balance, isOwn: isOwn]
    }

    @Transactional(readOnly = true)
    public Map getCustomerTerritoryIds(Object params) {
        String territoryIds = ""
        sql = new Sql(dataSource)
        String query = """
            SELECT  `territory_configuration`.`enterprise_configuration_id`,
                GROUP_CONCAT(DISTINCT CAST(`territory_configuration`.`id` AS CHAR)) AS `territoryIds`
            FROM
                `customer_territory_sub_area`
                INNER JOIN `customer_master`
                    ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`)
                INNER JOIN `enterprise_configuration`
                    ON (`customer_master`.`enterprise_configuration_id` = `enterprise_configuration`.`id`)
                INNER JOIN `territory_sub_area`
                    ON (`customer_territory_sub_area`.`territory_sub_area_id` = `territory_sub_area`.`id`)
                INNER JOIN `territory_configuration`
                    ON (`territory_sub_area`.`territory_configuration_id` = `territory_configuration`.`id`)
            WHERE `customer_master`.`id`=:employeeId
            GROUP BY `territory_configuration`.`enterprise_configuration_id`
        """
        List resultList = sql.rows(query, params)
        if(resultList.size() > 0){
            territoryIds = resultList.first().territoryIds
        }
        return [territoryIds: territoryIds]
    }

    @Transactional(readOnly = true)
    public List listDeliveryManByApplicationUserGeoLocation(Object params) {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        sql = new Sql(dataSource)
        String query = """
            SELECT `customer_master`.`id`, CONCAT("[",`customer_master`.`code`, "] ", `customer_master`.`name`) AS `name`
            FROM customer_master
                INNER JOIN `customer_territory_sub_area` ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id` AND customer_master.`master_type_id` = 2)
                    WHERE `customer_territory_sub_area`.`territory_sub_area_id` IN (SELECT `customer_territory_sub_area`.`territory_sub_area_id` FROM `customer_territory_sub_area`
            WHERE `customer_territory_sub_area`.`customer_master_id` =:customerId )
                AND `customer_master`.`category_id` = ${ApplicationConstants.CUSTOMER_CATEGORY_SALES_MAN_ID}
                AND `customer_master`.`enterprise_configuration_id` IN (SELECT `enterprise_configuration_id` FROM `application_user_enterprise`
                WHERE `is_active` = TRUE AND `application_user_id` = ${applicationUser.id})
            GROUP BY `customer_master`.`id`
        """
        List resultList = sql.rows(query, params)
        return resultList
    }

    @Transactional(readOnly = true)
    public List listDeliveryManByGeoLocation(Object params) {
        sql = new Sql(dataSource)
        String query = """
            SELECT DISTINCT `customer_master`.`id`, CONCAT("[",`customer_master`.`code`, "] ", `customer_master`.`name`) AS `name`
            FROM
                `customer_territory_sub_area`
                INNER JOIN `customer_master`
                    ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id` AND `customer_master`.`master_type_id` = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID})
            WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                AND `customer_master`.`category_id` = ${ApplicationConstants.CUSTOMER_CATEGORY_SALES_MAN_ID}
                AND `customer_territory_sub_area`.`territory_sub_area_id` =:territorySubAreaId
        """
        List resultList = sql.rows(query, params)
        return resultList
    }

    @Transactional(readOnly = true)
    public List<GroovyRowResult> getCustomerListByGeoLocation(Object params) {
        String filter = ""
        if (params.searchKey) {
            filter = " AND (`customer_master`.`name` LIKE '%${params.searchKey}%' OR `customer_master`.`code` LIKE '%${params.searchKey}%' OR `customer_master`.`legacy_id` LIKE '%${params.searchKey}%')"
        }
        if(params.categoryId){
            filter += " AND `customer_master`.`category_id` = ${params.categoryId}"
        }
        String query = """
             SELECT
                 `customer_master`.`id`
                , `customer_master`.`code`
                , `customer_master`.`legacy_id`
                , `customer_master`.`name`
                , `customer_status` AS `status`
                , `customer_master`.`present_address` AS `address`
                , `customer_category`.`name` AS `category`
                , `customer_territory_sub_area`.`territory_sub_area_id` AS territorySubAreaId
            FROM `customer_territory_sub_area`
                INNER JOIN `customer_master`
                    ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id` AND `customer_master`.`master_type_id` = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID})
                INNER JOIN `customer_category` ON (`customer_master`.`category_id` = `customer_category`.`id`)
            WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                AND `customer_master`.`master_type_id` = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID}
                AND `customer_territory_sub_area`.`territory_sub_area_id` =:territorySubAreaId
                ${filter}
        """
        List<GroovyRowResult> groovyRowResultList = new Sql(dataSource).rows(query, params)
        return groovyRowResultList
    }

    @Transactional(readOnly = true)
    public List listSubordinateEmployeeByApplicationUser(Object params) {
        try{
            ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
            sql = new Sql(dataSource)
            String query = """
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
            """
            List resultList = sql.rows(query)
            return resultList
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List searchCustomerByCategory(String categoryId) {
        String query = """ SELECT  cm.id , cm.`name`
                            FROM  customer_master cm
                            WHERE cm.category_id = ${categoryId}
                        """
        Sql sql = new Sql(dataSource)
        return sql.rows(query)
    }


    @Transactional(readOnly = true)
    public List listDeliveryManByDistributionPoint(Object params) {
        try{
            String filter = ""
            if (params.searchKey) {
                filter = " AND (`customer_master`.`name` LIKE '%${params.searchKey}%' OR `customer_master`.`code` LIKE '%${params.searchKey}%' OR `customer_master`.`legacy_id` LIKE '%${params.searchKey}%')"
            }

            String query = """
                SELECT DISTINCT `customer_master`.`id`, `customer_master`.`code`, `customer_master`.`name`, `customer_master`.`legacy_id`, `customer_master`.`present_address` AS address
                FROM
                    `customer_territory_sub_area`
                    INNER JOIN `customer_master`
                        ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id` AND `customer_master`.`master_type_id` = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID})
                WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                    AND `customer_master`.`category_id` = ${ApplicationConstants.CUSTOMER_CATEGORY_SALES_MAN_ID}
                    AND `customer_territory_sub_area`.`territory_sub_area_id` IN (SELECT dptsa.`territory_sub_area_id` FROM `distribution_point_territory_sub_area` dptsa WHERE dptsa.`distribution_point_id` =:distributionPointId)
                    ${filter}
            """
            sql = new Sql(dataSource)
            List resultList = sql.rows(query, params)
            return resultList
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listExternalCustomerByGeoLocation(Object params) {
        try{
            String filter = ""
            if (params.searchKey) {
                filter = " AND (`customer_master`.`name` LIKE '%${params.searchKey}%' OR `customer_master`.`code` LIKE '%${params.searchKey}%' OR `customer_master`.`legacy_id` LIKE '%${params.searchKey}%')"
            }
            String query = """
                SELECT DISTINCT `customer_master`.`id`, `customer_master`.`code`, `customer_master`.`name`, `customer_master`.`legacy_id`,`customer_master`.`present_address` AS address
                FROM
                    `customer_territory_sub_area`
                    INNER JOIN `customer_master`
                        ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id` AND `customer_master`.`master_type_id` = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID})
                WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                    AND `customer_master`.`category_id` != ${ApplicationConstants.CUSTOMER_CATEGORY_SALES_MAN_ID}
                    AND `customer_master`.`category_id` != ${ApplicationConstants.CUSTOMER_CATEGORY_BRANCH_ID}
                    AND `customer_territory_sub_area`.`territory_sub_area_id` =:territorySubAreaId
                    ${filter}
            """
            sql = new Sql(dataSource)
            List resultList = sql.rows(query, params)
            return resultList
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Map readCustomerSettlementData(Object params) {
        try{
            CustomerMaster customerMaster = CustomerMaster.get(Long.parseLong(params.customerMasterId))
            ChartOfAccountsMapping chartOfAccountsMapping = ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_RECEIVABLE)
            Float receivableAmount = journalDetailsService.getDebitAccountsBalance(chartOfAccountsMapping.chartOfAccounts, customerMaster.code)

            chartOfAccountsMapping = ChartOfAccountsMapping.findByCoaType(COAType.SECURITY_DEPOSIT)
            Float securityDeposit = journalDetailsService.getCreditAccountsBalance(chartOfAccountsMapping.chartOfAccounts, customerMaster.code)
            return [receivableAmount: receivableAmount, securityDeposit: securityDeposit]
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Map getDefaultCustomerByDP(Object params) {
        try{
        String query = """
            SELECT customer_master.id, customer_master.`code`, customer_master.`name`, customer_master.`legacy_id`
            FROM distribution_point_warehouse dpw
            INNER JOIN customer_master ON (dpw.`default_customer_id` = customer_master.`id`)
            WHERE dpw.`distribution_point_id` = ${params.distributionPointId}
            LIMIT 1
        """
        Sql sql = new Sql(dataSource)
        List result = sql.rows(query)
        if(result && result.size() > 0){
            return [customerId: result.first().id, customerCode: result.first().code, customerName: result.first().name, customerLegacyId: result.first().legacy_id]
        }else{
            return [customerId: '', customerCode: '', customerName: '', customerLegacyId: '']
        }
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }


}
