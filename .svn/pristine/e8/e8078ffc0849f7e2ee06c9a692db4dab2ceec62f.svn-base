package com.bits.bdfp.customer.customermaster

import com.bits.bdfp.customer.CustomerMasterService
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.commons.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 3/9/16.
 */
@Component("listSubordinateEmployeeByApplicationUserAction")
class ListSubordinateEmployeeByApplicationUserAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerMasterService customerMasterService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            init(params)

            List result = customerMasterService.listSubordinateEmployeeByApplicationUser(this)
            if (result) { // in case: normal list
                total = result.size()
            }
            List objList = this.wrapListInGridEntityList(result, start)
            Map output = [page: 1, total: 1, records: total, rows: objList]
            return output
        }
        catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage('Message', Message.ERROR, ex.message)
        }
    }

    private List wrapListInGridEntityList(objList, start) {
        List instants = []
        int counter = start + 1;
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${instance.id ? instance.id : ''}",
                    "${instance.code ? instance.code : ''}",
                    "${instance.name ? instance.name : ''}",
                    "${instance.department ? instance.department : ''}",
                    "${instance.designation ? instance.designation : ''}",
                    "${instance.isEmployee}"
            ]
            instants << obj
            counter++
        };
        return instants
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}
