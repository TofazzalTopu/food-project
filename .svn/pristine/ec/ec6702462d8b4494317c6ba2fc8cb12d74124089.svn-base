package com.bits.bdfp.inventory.sales.distributionpoint

import com.bits.bdfp.customer.CustomerMasterService
import com.bits.bdfp.geolocation.TerritorySubAreaService
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointService
import com.bits.bdfp.inventory.sales.DistributionPointTerritorySubArea
import com.bits.bdfp.inventory.sales.DistributionPointWarehouse
import com.bits.bdfp.inventory.warehouse.WarehouseService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createDistributionPointAction")
class CreateDistributionPointAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    DistributionPointService distributionPointService
    @Autowired
    TerritorySubAreaService territorySubAreaService
    @Autowired
    CustomerMasterService customerMasterService
    @Autowired
    WarehouseService warehouseService
    Message message

    public Object preCondition(Object params, Object object) {
        List<DistributionPointTerritorySubArea> distributionPointTerritorySubAreaList = []
        DistributionPointWarehouse distributionPointWarehouse = null
        Boolean isValid = false
        ApplicationUser applicationUser=params.applicationUser
        String msg = ''
        try {
            DistributionPoint distributionPoint = (DistributionPoint) object
            if (distributionPoint.validate()) {
                params.items.each { key, val ->
                    if (val instanceof Map) {
                        DistributionPointTerritorySubArea distributionPointTerritorySubArea = new DistributionPointTerritorySubArea()
                        distributionPointTerritorySubArea.distributionPoint = distributionPoint
                        distributionPointTerritorySubArea.territorySubArea = territorySubAreaService.read(Long.parseLong(val.territorySubAreaId))
                        distributionPointTerritorySubArea.userCreated = applicationUser
                        distributionPointTerritorySubArea.dateCreated = new Date()
                        if (distributionPointTerritorySubArea.validate()) {
                            distributionPointTerritorySubAreaList.add(distributionPointTerritorySubArea)
                        }
                    }
                }
                isValid = true
                if (params.warehouse.id) {
                    distributionPointWarehouse = new DistributionPointWarehouse()
                    distributionPointWarehouse.distributionPoint = distributionPoint
                    distributionPointWarehouse.warehouse = warehouseService.read(Long.parseLong(params.warehouse.id))
                    distributionPointWarehouse.dateCreated = new Date()
                    distributionPointWarehouse.userCreated = applicationUser
                    if (params.inCharge.id) {
                        distributionPointWarehouse.inCharge = customerMasterService.read(Long.parseLong(params.inCharge.id))
                    }
                    if(params.defaultCustomer.id){
                        distributionPointWarehouse.defaultCustomer = customerMasterService.read(Long.parseLong(params.defaultCustomer.id))
                    }
                    if(!distributionPointWarehouse.validate()){
                        isValid = false
                        message =  this.getValidationErrorMessage(distributionPointWarehouse)
                    }
                }
                if(distributionPoint.isFactory){
                    if(DistributionPoint.countByIsFactoryAndEnterpriseConfiguration(true, distributionPoint.enterpriseConfiguration) > 0){
                        isValid = false
                        message =  this.getMessage("Duplicate Factory", Message.ERROR, "Factory already exist")
                    }
                }
            }
            else{
              message = this.getValidationErrorMessage(distributionPoint)
            }
            return [message: message, isValid: isValid, distributionPointTerritorySubAreaList: distributionPointTerritorySubAreaList, distributionPointWarehouse: distributionPointWarehouse]
        } catch (Exception ex) {
            log.error(ex.message)
            return [message: ex.message, isValid: isValid, distributionPointTerritorySubAreaList: distributionPointTerritorySubAreaList, distributionPointWarehouse: distributionPointWarehouse]
        }
    }

    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser=(ApplicationUser) object
            DistributionPoint distributionPoint = new DistributionPoint(params)
            distributionPoint.userCreated = object
            distributionPoint.dateCreated = new Date()
            params.put("applicationUser", applicationUser)
            Map distributionPointMap = this.preCondition(params,distributionPoint)
            if(distributionPointMap?.isValid){
                distributionPointMap.put("distributionPoint",distributionPoint)
                distributionPoint= distributionPointService.create(distributionPointMap)
                if (distributionPoint) {
                    message = this.getMessage(distributionPoint, Message.SUCCESS, this.SUCCESS_SAVE)
                } else {
                    message = this.getMessage(distributionPoint, Message.ERROR, this.FAIL_SAVE)
                }
            }
            else{
                message = distributionPointMap?.message
            }

        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Distribution Point Setup", Message.ERROR, "Exception-${ex.message}")
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}