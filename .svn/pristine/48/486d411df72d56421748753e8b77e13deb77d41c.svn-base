package com.bits.bdfp.inventory.demandorder.approveprimarydemandorder

import com.bits.bdfp.inventory.demandorder.DemandOrderStatus
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderApprovalStatus
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderService
import com.bits.bdfp.inventory.workflow.Workflow
import com.bits.bdfp.inventory.workflow.WorkflowService
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by maimuna.akter on 9/15/2015.
 */
@Component("rejectPrimaryDemandOrderAction")
class RejectPrimaryDemandOrderAction extends Action{

    static final String REJECTED = "Primary Demand Order Rejected successfully";

    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService
    @Autowired
    WorkflowService workflowService
    Message message;

    public Object preCondition(Object params, Object object) {

        Boolean isValid = false
        ApplicationUser applicationUser = params.applicationUser
        String msg = ''
        List primaryDemandOrderApprovalStatusList = [];
        String rejectionCause = params.rejectionCause;
        try {
            params.items.each { key, val ->
                if (val instanceof Map) {
                    PrimaryDemandOrderApprovalStatus primaryDemandOrderApprovalStatus = new PrimaryDemandOrderApprovalStatus()
                    primaryDemandOrderApprovalStatus.demandOrderStatus = DemandOrderStatus.REJECTED;
                    primaryDemandOrderApprovalStatus.isReject = true;
                    primaryDemandOrderApprovalStatus.isApproved = false
                    primaryDemandOrderApprovalStatus.rejectionCause = rejectionCause;
                    primaryDemandOrderApprovalStatus.remarks = rejectionCause
                    primaryDemandOrderApprovalStatus.primaryDemandOrder = primaryDemandOrderService.read(Long.parseLong(val.id));
                    primaryDemandOrderApprovalStatus.workflow = Workflow.findByMenuName(ApplicationConstants.PRIMARY_DEMAND_ORDER_APPROVAL_FORM)
                    primaryDemandOrderApprovalStatus.userApproved = applicationUser
                    primaryDemandOrderApprovalStatus.dateCreated = new Date()
                    primaryDemandOrderApprovalStatus
                    if (primaryDemandOrderApprovalStatus.validate()) {
                        primaryDemandOrderApprovalStatusList.add(primaryDemandOrderApprovalStatus)
                        isValid = true
                    }
                    else
                    {
                        isValid = false;
                        message = this.getValidationErrorMessage(primaryDemandOrderApprovalStatus)
                    }
                }
            }
            return [message: message, isValid: isValid, primaryDemandOrderApprovalStatusList: primaryDemandOrderApprovalStatusList]
        } catch (Exception ex) {
            log.error(ex.message)
            return [message: ex.message, isValid: isValid, primaryDemandOrderApprovalStatusList: primaryDemandOrderApprovalStatusList]
        }
    }

    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            params.put("applicationUser", applicationUser)

            Map primaryDemandOrderApprovalStatusMap = this.preCondition(params, null)
            if(primaryDemandOrderApprovalStatusMap?.isValid){
                List primaryDemandOrderApprovalStatusList = primaryDemandOrderApprovalStatusMap.primaryDemandOrderApprovalStatusList;
                int rowCount = primaryDemandOrderService.rejectPrimaryDemandOrderStatus(primaryDemandOrderApprovalStatusList)
                if (rowCount>0) {
                    message = this.getMessage("Primary Demand Order Approval", Message.SUCCESS, this.REJECTED)
                } else {
                    message = this.getMessage("Primary Demand Order Approval", Message.ERROR, this.FAIL_SAVE)
                }
            }
            else{
                message = primaryDemandOrderApprovalStatusMap?.message
            }

        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Distribution Point Setup", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}
