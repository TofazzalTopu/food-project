package com.bits.bdfp.customer

import org.springframework.transaction.annotation.Transactional
import com.docu.commons.CommonConstants
import com.docu.common.Service
import groovy.sql.Sql
import javax.sql.DataSource
import com.docu.common.Action

class CustomerEligibilityMasterService extends Service {
    static transactional = false

    @Transactional(readOnly = true)
    public CustomerEligibilityMaster read(Long id) {
        return CustomerEligibilityMaster.read(id)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Map params) {
        try {
            List<CustomerEligibilityMaster> customerEligibilityMasterList = CustomerEligibilityMaster.withCriteria {
                maxResults(Integer.parseInt(action.resultPerPage.toString()))
                firstResult(Integer.parseInt(action.start.toString()))
                order(action.sortCol.toString(), action.sortOrder.toString())
            }
            long customerEligibilityMasterCount = CustomerEligibilityMaster.count()
            return [objList: customerEligibilityMasterList, count: customerEligibilityMasterCount]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
    @Transactional(readOnly = true)
    public Map getListForGridCustomer(Action action, Map params) {
        try {
            def customerEligibilityMasterList = []
            CustomerMaster customerMaster = CustomerMaster.get(Long.parseLong(params.customerMasterId))
            customerEligibilityMasterList = CustomerEligibilityMaster.findAllByCustomerMaster(customerMaster)
            if(customerEligibilityMasterList.size() == 0){
                customerEligibilityMasterList = EligibilityTemplate.withCriteria {
                    maxResults(Integer.parseInt(action.resultPerPage.toString()))
                    firstResult(Integer.parseInt(action.start.toString()))
                    order(action.sortCol.toString(), action.sortOrder.toString())
                }
            }

            long customerEligibilityMasterCount = CustomerEligibilityMaster.count()
            return [objList: customerEligibilityMasterList, count: customerEligibilityMasterCount]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List<CustomerEligibilityMaster> list() {
        return CustomerEligibilityMaster.list()
    }

    @Transactional
    public CustomerEligibilityMaster create(Object object) {
        try {
            CustomerEligibilityMaster customerEligibilityMaster = (CustomerEligibilityMaster) object
            return customerEligibilityMaster.save(vaidate: false, insert: true)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object object) {
        try {
            CustomerEligibilityMaster customerEligibilityMaster = (CustomerEligibilityMaster) object
            if (customerEligibilityMaster.save(vaidate: false)) {
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
            CustomerEligibilityMaster customerEligibilityMaster = (CustomerEligibilityMaster) object
            customerEligibilityMaster.delete()
            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public CustomerEligibilityMaster search(String fieldName, String fieldValue) {
        String query = "from CustomerEligibilityMaster as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return CustomerEligibilityMaster.find(query)
    }
}
