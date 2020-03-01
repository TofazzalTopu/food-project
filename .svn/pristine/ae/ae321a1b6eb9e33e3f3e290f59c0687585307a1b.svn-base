package com.bits.bdfp.inventory.sales.distributionpoint

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.customer.CustomerMasterService
import com.bits.bdfp.geolocation.TerritorySubArea
import com.bits.bdfp.geolocation.TerritorySubAreaService
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointService
import com.bits.bdfp.inventory.sales.DistributionPointTerritorySubArea
import com.bits.bdfp.inventory.sales.DistributionPointWarehouse
import com.bits.bdfp.inventory.warehouse.Warehouse
import com.bits.bdfp.inventory.warehouse.WarehouseService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateDistributionPointAction")
class UpdateDistributionPointAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    DistributionPointService distributionPointService
    @Autowired
    TerritorySubAreaService territorySubAreaService
    @Autowired
    CustomerMasterService customerMasterService
    @Autowired
    WarehouseService warehouseService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

    public Object preCondition(Object params, Object object) {
        List<DistributionPointTerritorySubArea> distributionPointTerritorySubAreaList = []
        List<DistributionPointTerritorySubArea> distributionPointTerritorySubAreaDeleteList = []
        DistributionPointWarehouse distributionPointWarehouse = null
        Boolean isValid = false
        ApplicationUser applicationUser = params.applicationUser
        String msg = ''
        Boolean isError = false
        try{

            DistributionPoint distributionPoint = (DistributionPoint) object

//            String domain = 'distribution_point'
//            String id =  distributionPoint.id
//
//            isError = validationCheckService.validationCheck(domain,id)

            if (distributionPoint.validate()) {
                params.items.each { key, val ->
                    if (val instanceof Map) {
                        TerritorySubArea territorySubArea = territorySubAreaService.read(Long.parseLong(val.territorySubAreaId))
                        DistributionPointTerritorySubArea distributionPointTerritorySubArea = null
                        distributionPointTerritorySubArea = distributionPointService.existingDistributionSubArea(territorySubArea, distributionPoint)
                        if (!distributionPointTerritorySubArea) {
                            distributionPointTerritorySubArea = new DistributionPointTerritorySubArea()
                            distributionPointTerritorySubArea.distributionPoint = distributionPoint
                            distributionPointTerritorySubArea.territorySubArea = territorySubAreaService.read(Long.parseLong(val.territorySubAreaId))
                            distributionPointTerritorySubArea.userCreated = applicationUser
                            distributionPointTerritorySubArea.dateCreated = new Date()
                        } else {
                            distributionPointTerritorySubArea.userUpdated = applicationUser
                            distributionPointTerritorySubArea.dateUpdated = new Date()
                        }

                        if (distributionPointTerritorySubArea.validate()) {
                            distributionPointTerritorySubAreaList.add(distributionPointTerritorySubArea)
                        }


                    }
                }
                List<DistributionPointTerritorySubArea> distributionPointTerritorySubAreaExistingList = distributionPointService.allExistingDistributionSubAreaList(distributionPoint)
                distributionPointTerritorySubAreaExistingList.each {
                    if (!(distributionPointTerritorySubAreaList.contains(it))) {
                        distributionPointTerritorySubAreaDeleteList.add(it)
                    }
                }
                if (params.warehouse.id) {
                    Warehouse warehouse = warehouseService.read(Long.parseLong(params.warehouse.id))
                    distributionPointWarehouse = distributionPointService.dwByDistributionPoint(distributionPoint)
                    if (distributionPointWarehouse) {
                        distributionPointWarehouse.warehouse = warehouse
                        distributionPointWarehouse.userUpdated = applicationUser
                        distributionPointWarehouse.dateUpdated = new Date()
                    }
                    else{
                        distributionPointWarehouse = new DistributionPointWarehouse()
                        distributionPointWarehouse.distributionPoint = distributionPoint
                        distributionPointWarehouse.warehouse = warehouse
                        distributionPointWarehouse.dateCreated = new Date()
                        distributionPointWarehouse.userCreated = applicationUser
                    }

                    if (params.inCharge.id) {
                        distributionPointWarehouse.inCharge = customerMasterService.read(Long.parseLong(params.inCharge.id))
                    }
                    if(params.defaultCustomer.id){
                        distributionPointWarehouse.defaultCustomer = customerMasterService.read(Long.parseLong(params.defaultCustomer.id))
                    }


                }
                 if (isError){
                    message = this.getMessage('DistributionPoint', Message.ERROR, 'This Distribution Point has already been used')

                }
                else{
                     isValid = true
                 }

            }
            else{
                message = this.getValidationErrorMessage(distributionPoint)
            }
            return [message: message, isValid: isValid,distributionPointTerritorySubAreaDeleteList:distributionPointTerritorySubAreaDeleteList, distributionPointTerritorySubAreaList: distributionPointTerritorySubAreaList, distributionPointWarehouse: distributionPointWarehouse]
        } catch (Exception ex) {
            log.error(ex.message)
            return [message: ex.message, isValid: isValid,distributionPointTerritorySubAreaDeleteList:distributionPointTerritorySubAreaDeleteList, distributionPointTerritorySubAreaList: distributionPointTerritorySubAreaList, distributionPointWarehouse: distributionPointWarehouse]
        }
    }

    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            DistributionPoint distributionPoint = distributionPointService.read(Long.parseLong(params?.id?.toString()))
            distributionPoint.properties=params
            distributionPoint.userUpdated = applicationUser
            distributionPoint.dateUpdated = new Date()
            params.put("applicationUser", applicationUser)
            Map distributionPointMap = this.preCondition(params, distributionPoint)
            if (distributionPointMap?.isValid) {
                distributionPointMap.put("distributionPoint", distributionPoint)
                int noOfRows = (int)distributionPointService.update(distributionPointMap)
                if (noOfRows > 0) {
                    message = this.getMessage(distributionPoint, Message.SUCCESS, this.SUCCESS_UPDATE)
                } else {
                    message = this.getMessage(distributionPoint, Message.ERROR, this.FAIL_SAVE)
                }
            } else {
                message = distributionPointMap?.message

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
