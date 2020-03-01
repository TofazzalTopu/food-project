package com.bits.bdfp.inventory.setup

import com.bits.bdfp.util.ApplicationConstants
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class PosCustomerService extends Service {
    static transactional = false
    DataSource dataSource
    SpringSecurityService springSecurityService
    Sql sql

    @Transactional
    public PosCustomer create(Object object) {
        PosCustomer posCustomer = (PosCustomer) object
        if (posCustomer.save(false)) {
            return posCustomer
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        PosCustomer posCustomer = (PosCustomer) object
        if (posCustomer.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        PosCustomer posCustomer = (PosCustomer) object
        posCustomer.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<PosCustomer> objList = PosCustomer.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = PosCustomer.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<PosCustomer> list() {
        return PosCustomer.list()
    }

    @Transactional(readOnly = true)
    public PosCustomer read(Long id) {
        return PosCustomer.read(id)
    }

    @Transactional(readOnly = true)
    public PosCustomer search(String fieldName, String fieldValue) {
        String query = "from PosCustomer as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return PosCustomer.find(query)
    }

    @Transactional(readOnly = true)
    public Map posCustomerByApplicationUser() {
        try {
            sql = new Sql(dataSource)
            ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
            String customerId = ""
            String customerCode = ""
            String customerName = ""
            String customerAddress = ""
            String strSql = """
                SELECT
                    `pos_customer`.`customer_master_id`
                    , `customer_master`.`code`
                    , `customer_master`.`name`
                    , `customer_master`.`present_address`
                FROM
                    `pos_customer`
                    INNER JOIN `customer_master`
                        ON (`pos_customer`.`customer_master_id` = `customer_master`.`id`)
                WHERE `pos_customer`.`distribution_point_id` = (SELECT `distribution_point_id` FROM `application_user_distribution_point` WHERE `application_user_id` = ${applicationUser.id} LIMIT 1)
            """
            List objList = sql.rows(strSql)
            if (objList && objList.size() > 0) {
                customerId = objList.first().customer_master_id
                customerCode = objList.first().code
                customerName = objList.first().name
                customerAddress = objList.first().present_address
            }

            return [customerId: customerId, customerCode: customerCode, customerName: customerName, customerAddress: customerAddress]
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

}
