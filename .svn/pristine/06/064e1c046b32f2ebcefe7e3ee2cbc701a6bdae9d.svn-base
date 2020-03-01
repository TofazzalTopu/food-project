package com.bits.bdfp.inventory.setup.incentive

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.setup.IncentiveSetupService
import com.bits.bdfp.settings.MeasureUnitConfiguration
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by liton.miah on 3/10/2016.
 */
@Component("updateVolumeBasedIncentiveAction")
class UpdateVolumeBasedIncentiveAction extends Action {
    private static final Log log = LogFactory.getLog(this)

    @Autowired
    IncentiveSetupService incentiveSetupService
    Message message

    public Object preCondition(Object params, Object object) {
        try{
            Map map = (Map) object
            boolean isExistingCustomer = false
            String msg = ''
            VolumeBasedIncentive volumeBasedIncentive = map.get("volumeBasedIncentive")
            List<VolumeBasedIncentiveCustomers> volumeBasedIncentiveCustomersList = (List<VolumeBasedIncentiveCustomers>) map.get("volumeBasedIncentiveCustomersList")

            volumeBasedIncentiveCustomersList.each {
                VolumeBasedIncentiveCustomers volumeBasedIncentiveCustomers = VolumeBasedIncentiveCustomers.findByCustomerMasterAndVolumeBasedIncentive(it.customerMaster,volumeBasedIncentive)
                if(volumeBasedIncentiveCustomers){
                    if(msg){
                        msg += ', '+it.customerMaster.name+'['+it.customerMaster.code+']'
                    }else{
                        msg = it.customerMaster.name+'['+it.customerMaster.code+']'
                    }
                    isExistingCustomer = true
                }
            }

            if(incentiveSetupService.checkOverlappingMultipleProgram(volumeBasedIncentive.class.getSimpleName(), volumeBasedIncentive.id, volumeBasedIncentive.effectiveDateFrom, volumeBasedIncentive.effectiveDateTo)){
                message = this.getMessage("Volume Based Incentive", Message.ERROR, "Multiple Incentive program can’t overlap. \nPlease select a valid date range.")
                return message
            }else{
                if(isExistingCustomer){
                    message = this.getMessage("Volume Based Incentive", Message.ERROR, "Customer "+msg+" already exists.")
                }else if (!volumeBasedIncentive.validate()) {
                    message = this.getValidationErrorMessage(volumeBasedIncentive)
                }else{
                    message = this.getMessage("Volume Based Incentive", Message.SUCCESS, SUCCESS_SAVE)
                }
                return message
            }
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object execute(Object params, Object user) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) user

            VolumeBasedIncentive volumeBasedIncentive = VolumeBasedIncentive.get(Long.parseLong(params.id))
            volumeBasedIncentive.properties = params

            volumeBasedIncentive.userUpdated = applicationUser
            volumeBasedIncentive.lastUpdated = new Date()

            List<VolumeBasedIncentiveSlab> volumeBasedIncentiveSlabList = []
            List<VolumeBasedIncentiveCustomers> volumeBasedIncentiveCustomersList = []
            List<VolumeBasedIncentiveProductSet> volumeBasedIncentiveProductSetList = []

            List<VolumeBasedIncentiveSlab> volumeBasedIncentiveSlabDeleteList = []
            List<VolumeBasedIncentiveCustomers> volumeBasedIncentiveCustomersDeleteList = []
            List<VolumeBasedIncentiveProductSet> volumeBasedIncentiveProductSetDeleteList = []

            params.allIncentive.each{key, val ->
                if(val instanceof Map){
                    if(!val.id){
                        VolumeBasedIncentiveSlab volumeBasedIncentiveSlab = new VolumeBasedIncentiveSlab()
                        volumeBasedIncentiveSlab.volumeBasedIncentive = volumeBasedIncentive

                        if(val.productIds){
                            String[] ids = val.productIds.split(",")
                            for(int i = 0; i < ids.length; i++){
                                VolumeBasedIncentiveProductSet volumeBasedIncentiveProductSet = new VolumeBasedIncentiveProductSet()
                                volumeBasedIncentiveProductSet.productSet = volumeBasedIncentiveSlab
                                volumeBasedIncentiveProductSet.finishProduct = FinishProduct.get(ids[i])
                                volumeBasedIncentiveProductSet.userCreated = applicationUser
                                volumeBasedIncentiveProductSet.dateCreated = new Date()

                                volumeBasedIncentiveProductSetList.add(volumeBasedIncentiveProductSet)
                            }
                        }

                        volumeBasedIncentiveSlab.productSetName = val.productSetName
                        volumeBasedIncentiveSlab.volumeFrom = Float.parseFloat(val.volumeFrom)
                        volumeBasedIncentiveSlab.volumeTo = Float.parseFloat(val.volumeTo)
                        volumeBasedIncentiveSlab.perUnitMasterUomCost = Float.parseFloat(val.perUnitMasterUomCost)
                        volumeBasedIncentiveSlab.masterUom = MeasureUnitConfiguration.get(val.masterUomId)
                        volumeBasedIncentiveSlab.incentiveAmountValue = val.incentiveAmountValue?Double.parseDouble(val.incentiveAmountValue):0
                        volumeBasedIncentiveSlab.incentiveAmountPct = val.incentiveAmountPct?Float.parseFloat(val.incentiveAmountPct):0

                        volumeBasedIncentiveSlab.userCreated = applicationUser
                        volumeBasedIncentiveSlab.dateCreated = new Date()

                        volumeBasedIncentiveSlabList.add(volumeBasedIncentiveSlab)
                    }
                }
            }

            params.customerList.each{key, val ->
                if(val instanceof Map){
                    VolumeBasedIncentiveCustomers volumeBasedIncentiveCustomers = new VolumeBasedIncentiveCustomers()
                    volumeBasedIncentiveCustomers.volumeBasedIncentive = volumeBasedIncentive
                    volumeBasedIncentiveCustomers.customerMaster = CustomerMaster.read(val.customerId)

                    volumeBasedIncentiveCustomers.userCreated = applicationUser
                    volumeBasedIncentiveCustomers.dateCreated = new Date()

                    volumeBasedIncentiveCustomersList.add(volumeBasedIncentiveCustomers)
                }
            }

            if(params.containsKey('incentiveSlabDeleteList')){
                params.incentiveSlabDeleteList.each{key, val ->
                    if(val instanceof Map) {
                        VolumeBasedIncentiveSlab volumeBasedIncentiveSlab = VolumeBasedIncentiveSlab.get(Long.parseLong(val.id))

                        List<VolumeBasedIncentiveProductSet> volumeBasedIncentiveProductSets = VolumeBasedIncentiveProductSet.findAllByProductSet(volumeBasedIncentiveSlab)
                        if (volumeBasedIncentiveProductSets.size() > 0) {
                            volumeBasedIncentiveProductSets.each {
                                volumeBasedIncentiveProductSetDeleteList.add(it)
                            }
                        }

                        volumeBasedIncentiveSlabDeleteList.add(volumeBasedIncentiveSlab)
                    }
                }
            }

            if(params.containsKey('incentiveCustomersDeleteList')){
                params.incentiveCustomersDeleteList.each{key, val ->
                    if(val instanceof Map) {
                        VolumeBasedIncentiveCustomers volumeBasedIncentiveCustomers = VolumeBasedIncentiveCustomers.get(Long.parseLong(val.id))
                        volumeBasedIncentiveCustomersDeleteList.add(volumeBasedIncentiveCustomers)
                    }
                }
            }

            Map map = [:]
            map.put("volumeBasedIncentive",volumeBasedIncentive)
            map.put("volumeBasedIncentiveProductSetList",volumeBasedIncentiveProductSetList)
            map.put("volumeBasedIncentiveSlabList",volumeBasedIncentiveSlabList)
            map.put("volumeBasedIncentiveCustomersList",volumeBasedIncentiveCustomersList)

            map.put("volumeBasedIncentiveProductSetDeleteList",volumeBasedIncentiveProductSetDeleteList)
            map.put("volumeBasedIncentiveSlabDeleteList",volumeBasedIncentiveSlabDeleteList)
            map.put("volumeBasedIncentiveCustomersDeleteList",volumeBasedIncentiveCustomersDeleteList)

            message = this.preCondition(params, map)

            if(message.type == 1){
                VolumeBasedIncentive volumeBasedIncentiveRow = incentiveSetupService.createVolumeBasedIncentive(map)
                if (volumeBasedIncentiveRow) {
                    message = this.getMessage("Volume Based Incentive", Message.SUCCESS, "Volume Based Incentive Updated Successfully.")
                } else {
                    message = this.getMessage("Volume Based Incentive", Message.ERROR, FAIL_SAVE)
                }
            }
            return message

        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return  null
    }
}
