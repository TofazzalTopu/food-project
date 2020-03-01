package com.bits.bdfp.customer.customerasset

import com.bits.bdfp.customer.AssetLending
import com.bits.bdfp.customer.CustomerAssetLending
import com.bits.bdfp.customer.CustomerAssetLendingAndRecoveryService
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.CustomerMasterService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.ObjectUtil
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by abhijit.majumder on 4/24/2016.
 */
@Component("createAssetLendingAction")
class CreateAssetLendingAction extends Action{
    public static final Log log = LogFactory.getLog(CustomerAssetLending.class)
    private final String MESSAGE_HEADER = 'New Customer Asset Lending'
    private final String MESSAGE_SUCCESS = 'Customer Asset Lending Created Successfully'
    private final String MESSAGE_FAILED = 'Customer Asset Lending Create Failed'
    @Autowired
    CustomerMasterService customerMasterService

    @Autowired
    CustomerAssetLendingAndRecoveryService customerAssetLendingAndRecoveryService



    public Object preCondition(Object user, Object object) {
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            Message message
            ApplicationUser applicationUser = (ApplicationUser) object
            Map map = [:]
            if(!params.customerMasterId){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Customer is not selected")
            }

            if(!params.enterpriseConfiguration){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Enterprise is not selected")
            }

            EnterpriseConfiguration enterpriseConfiguration = EnterpriseConfiguration.read(params.enterpriseConfiguration)
            CustomerMaster customerMaster = CustomerMaster.read(params.customerMasterId)
            AssetLending assetLending = AssetLending.findByCustomerMaster(customerMaster)
            if(!assetLending){
                assetLending = new AssetLending()
                assetLending.customerMaster = customerMaster
                assetLending.userCreated = applicationUser
                assetLending.enterpriseConfiguration = enterpriseConfiguration

            }

            if(!assetLending.validate()){
                return this.getValidationErrorMessage(assetLending)
            }

            List<CustomerAssetLending> customerAssetLendingArrayList = ObjectUtil.instantiateObjects(params.items, CustomerAssetLending.class)
            customerAssetLendingArrayList.each { customerAssetLending ->
                customerAssetLending.assetLending = assetLending
                customerAssetLending.userCreated = applicationUser
            }

            map.put('assetLending', assetLending)
            map.put('customerAssetLendingList', customerAssetLendingArrayList)

            boolean result = customerAssetLendingAndRecoveryService.createAssetLending(map)
            if (result) {
                message = this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)
            } else {
                message = this.getMessage(MESSAGE_HEADER, Message.ERROR, MESSAGE_FAILED)
            }
            return message

        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }

    }
    public Object postCondition(Object params, Object object) {
        return null
    }
}
