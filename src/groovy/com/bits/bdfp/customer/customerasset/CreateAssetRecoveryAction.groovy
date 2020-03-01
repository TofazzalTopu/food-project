package com.bits.bdfp.customer.customerasset

import com.bits.bdfp.customer.AssetLending
import com.bits.bdfp.customer.CustomerAssetLending
import com.bits.bdfp.customer.CustomerAssetLendingAndRecoveryService
import com.bits.bdfp.customer.CustomerAssetRecovery
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
@Component("createAssetRecoveryAction")
class CreateAssetRecoveryAction extends Action{
    public static final Log log = LogFactory.getLog(CustomerAssetRecovery.class)
    private final String MESSAGE_HEADER = 'New Customer Asset Recovery'
    private final String MESSAGE_SUCCESS = 'Customer Asset Recovery Created Successfully'
    private final String MESSAGE_FAIL = 'Customer Asset Recovery Failed'
    @Autowired
    CustomerMasterService customerMasterService

    @Autowired
    CustomerAssetLendingAndRecoveryService customerAssetLendingAndRecoveryService
    public Object preCondition(Object user, Object object) {
        return null
    }

    public AssetLending preConditionAssetLanding(Object params, ApplicationUser applicationUser)
    {
        Map map = [:]
        EnterpriseConfiguration enterpriseConfiguration=EnterpriseConfiguration.read(1)
        CustomerMaster customerMaster=CustomerMaster.read(params.customerMasterId)
        List<AssetLending> assetLendingList=AssetLending.findAllByCustomerMaster(customerMaster)
        AssetLending assetLending=new AssetLending()
        if(assetLendingList.size()==0)
        {
            assetLending.customerMaster=customerMaster
            assetLending.userCreated=applicationUser
            assetLending.dateCreated=new Date()
            assetLending.enterpriseConfiguration=enterpriseConfiguration
            //map.put('assetLending',assetLending)
        }
        else
        {
            assetLending=assetLendingList[0]
        }

        return assetLending
    }

    public Object execute(Object params, Object object) {
        try {
            boolean result = false
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

            List<CustomerAssetRecovery> customerAssetRecoveryArrayList = []
            customerAssetRecoveryArrayList = ObjectUtil.instantiateObjects(params.items, CustomerAssetRecovery.class)
            customerAssetRecoveryArrayList.each {
                it.assetLending=assetLending
                it.userCreated = applicationUser
                it.dateCreated = new Date()
            }
            map.put('customerAssetRecoveryArrayList',customerAssetRecoveryArrayList)
            map.put('assetLending',assetLending)

            result = customerAssetLendingAndRecoveryService.createAssetRecovery(map)
            if (result) {
                message = this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)
            } else {
                message = this.getMessage(MESSAGE_HEADER, Message.ERROR, MESSAGE_FAIL)
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
