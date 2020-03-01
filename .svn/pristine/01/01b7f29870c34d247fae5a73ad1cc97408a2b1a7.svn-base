package com.bits.bdfp.finance.viewandeditsecuritydeposit

import com.bits.bdfp.finance.CustomerPaymentService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 12/1/2015.
 */
@Component("listTerritoryByEnterprisAction")
class ListTerritoryByEnterpriseAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerPaymentService customerPaymentService

    public Object preCondition(Object params, Object object) {
        return null
    }

    public List execute(Object params, Object object) {
        try{
            List objectList = null
            objectList = customerPaymentService.getTerritoryListByEnterprise(Long.parseLong(params.enterpriseId))
            return objectList
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object objList, Object object) {
        return null
    }

    public List getDpListByTerritory(Object params){
        return customerPaymentService.getDpListByTerritory(Long.parseLong(params.territoryId));
    }

    public List getDpListByTerritoryEnterprise(Object params){
        return customerPaymentService.getDpListByTerritoryAndEnterprise(params);
    }

    public Object getDpDefaultCustomer(Object params){
        //return customerPaymentService.getDpDefaultCustomer(Long.parseLong(params.dpId), params.date);
        customerPaymentService.getDpDefaultCustomerWithDepositBalance(Long.parseLong(params.dpId), params.date);
    }
    public Object getDpDefaultCustomerWithDepositBelance(Object params){
        return customerPaymentService.getDpDefaultCustomerWithDepositBalance(Long.parseLong(params.dpId), params.date);
    }

    public Map getDpDefaultCustomerWithSecurityDepositBalance(Object params){
        return customerPaymentService.getDpDefaultCustomerWithSecurityDepositBalance(params.dpId, params.date);
    }

    public Object getOtherCustomersSd(Object params){
        return customerPaymentService.getOtherCustomersSd(Long.parseLong(params.cId), params.date);
    }

    public List getDpDefaultCustomersTp(Object params){
        return customerPaymentService.getDpDefaultCustomersTp(Long.parseLong(params.customerId), params.date);
    }

    public List listSalesManSDBalance(Object params){
        return customerPaymentService.listSalesManSDBalance(params);
    }

    public Object fetchPrimaryCustomerDepositBalance(Object params){
        return customerPaymentService.fetchCustomerDepositBalance(Long.parseLong(params.customerId), params.date);
    }

//    public List getDpDefaultCustomersSdList(Object params){
//        return customerPaymentService.getDpDefaultCustomersSdList(Long.parseLong(params.customerId), params.date);
//    }

    public Object getDpDefaultCustomersSdList(Object params,Object object){
        try{
            init(params)
            Map result = [:]
            List objectList = null

            result = customerPaymentService.getDpDefaultCustomersSdList(this, params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List objList = this.wrapListInGridEntityList(objectList, start)
            result = this.lastCondition(objList, null)
            return result
        }
        catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    private List wrapListInGridEntityList(objList, start) {
        List instants = []
        int counter = start + 1;
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${counter}",
                        "",
                        "${instance.id ? instance.id : ''}",
                        "${instance.customer_master_id ? instance.customer_master_id : ''}",
                        "${instance.name? instance.name : ''}",
                        "${instance.deposited? instance.deposited : 0}",
                        "${instance.withdrawn? instance.withdrawn : 0}",
                        "${instance.date_transaction? instance.date_transaction : ''}"
            ]
            instants << obj
            counter++
        };
        return instants
    }

    public Object lastCondition(Object objList, Object object) {
        int pageCount = 1
        if (resultPerPage < 0) {
            pageCount = 1
            resultPerPage=total
        }
        else {
            if (total > resultPerPage) {
                pageCount = Math.ceil(total / resultPerPage)
            }
        }
        Map output = [page: pageNum, total: pageCount, records: total, rows: objList]
        return output;
    }
}
