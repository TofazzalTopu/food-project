package com.bits.bdfp.customer.customerterritorysubarea

import com.bits.bdfp.customer.CustomerTerritorySubAreaService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteCustomerTerritorySubAreaAction")
class DeleteCustomerTerritorySubAreaAction extends Action {

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerTerritorySubAreaService customerTerritorySubAreaService

    Message message

    public Object preCondition(Object params, Object object) {
        try {
            return customerTerritorySubAreaService.read(Long.parseLong(params.id.toString()))
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            int noOfRows = customerTerritorySubAreaService.delete(object)
            if (noOfRows > 0) {
                message = this.getMessage('Delete Alert', Message.SUCCESS, 'Customer Territory Deleted Successfully.')
            } else {
                message = this.getMessage('Delete Alert', Message.ERROR, 'Customer Territory Could Not Be Deleted.')
            }
            return message
        }
        catch (Exception ex) {
            log.error(ex.message)
            return new Integer(0)
        }
    }

}