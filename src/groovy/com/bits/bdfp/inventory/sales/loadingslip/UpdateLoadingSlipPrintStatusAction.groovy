package com.bits.bdfp.inventory.sales.loadingslip

import com.bits.bdfp.inventory.sales.LoadingSlip
import com.bits.bdfp.inventory.sales.LoadingSlipService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by prianka.adhikary on 9/21/2015.
 */
@Component("updateLoadingSlipPrintStatusAction")
class UpdateLoadingSlipPrintStatusAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    LoadingSlipService loadingSlipService

    Message message

    public Object preCondition(Object params, Object object) {
        try {
            Map map =(Map) object

            boolean isValidate = true
            List <LoadingSlip> loadingSlipList  = map.printLoadingSlipStatusList
            if (loadingSlipList && loadingSlipList.size()>0){
                loadingSlipList.each {
                    if (!it.validate()){
                        isValidate = false
                        message = this.getValidationErrorMessage(it)
                    }
                }
            }
            else{
                isValidate = false
                message = this.getMessage('Print Loading Slip Status', Message.ERROR, "You did not select any Loading Slip .Please select")
            }

            if (isValidate){
                message = this.getMessage('Print Loading Slip Status', Message.SUCCESS, this.SUCCESS_SAVE)
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Print Loading Slip Status", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object execute(Object params, Object object) {
        try {

            ApplicationUser applicationUser = (ApplicationUser) object
            Integer counter = 0
            Map map = [:]
            LoadingSlip loadingSlip = null
            List <LoadingSlip> loadingSlipList = []
            params.items.each { key, val ->
                if (val instanceof Map) {
                    loadingSlip = LoadingSlip.read(Long.parseLong(val.id))
                    if (loadingSlip){
                        loadingSlip.printCount =  loadingSlip.printCount + 1
                        loadingSlip.userUpdated = applicationUser
                        loadingSlip.dateUpdated = new Date()
                        loadingSlipList.add(loadingSlip)
                    }
                }

            }
            map.put("printLoadingSlipStatusList",loadingSlipList)


            message = this.preCondition(null, map)
            if (message.type == 1) {
                int noOfRows = (int) loadingSlipService.updatePrintLoadingSlipStatus(map)
                if (noOfRows>0) {
                    message = this.getMessage("Print Loading Slip Status", Message.SUCCESS, this.SUCCESS_SAVE)
                } else {
                    message = this.getMessage("Print Loading Slip Status", Message.ERROR, this.FAIL_SAVE)
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Print Loading Slip Status", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }

}
