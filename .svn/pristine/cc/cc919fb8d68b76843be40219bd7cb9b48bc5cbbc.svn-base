package com.bits.bdfp.inventory.sales.distributionpoint

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointService
import com.bits.bdfp.inventory.sales.DistributionPointTerritorySubArea
import com.bits.bdfp.inventory.sales.DistributionPointWarehouse
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteDistributionPointAction")
class DeleteDistributionPointAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  DistributionPointService distributionPointService
    @Autowired
    ValidationCheckService validationCheckService
  Message message
   public Object preCondition(Object params, Object object) {
       List<DistributionPointTerritorySubArea> distributionPointTerritorySubAreaExistingList=[]
       DistributionPointWarehouse distributionPointWarehouse=null
       DistributionPoint distributionPoint=null
       Boolean isError=false
       Boolean isValid = false
       try{

           distributionPoint = distributionPointService.read(Long.parseLong(params?.id?.toString()))
           String domain = 'distribution_point'
           String id =  distributionPoint.id

           isError = validationCheckService.validationCheck(domain,id)

           if (!distributionPoint.validate()) {
               message = this.getValidationErrorMessage(distributionPoint)
               isError=true
               return [isError:isError,message:message,distributionPoint:distributionPoint,distributionPointWarehouse:distributionPointWarehouse,distributionPointTerritorySubAreaExistingList:distributionPointTerritorySubAreaExistingList]
           }
           else if (isValid){
               isError=true
               message = this.getMessage('DistributionPoint', Message.ERROR, 'This Distribution Point has already been used')
               return [isError:isError,message:message]

           }
           else {
               distributionPointTerritorySubAreaExistingList = distributionPointService.allExistingDistributionSubAreaList(distributionPoint)
               distributionPointWarehouse = distributionPointService.dwByDistributionPoint(distributionPoint)
               return [isError:isError,message:message,distributionPoint:distributionPoint,distributionPointWarehouse:distributionPointWarehouse,distributionPointTerritorySubAreaExistingList:distributionPointTerritorySubAreaExistingList]
           }
       } catch (Exception ex) {
           log.error(ex.message)
           return [isError:isError,message:message,distributionPoint:distributionPoint]
           throw ex
       }
  }
   public Object postCondition(Object params, Object object) {
    //not implement
    return null
  }

   public Object execute(Object params, Object object) {
       try {
           Object result = this.preCondition(params, object)
           if (!result.isError) {
               int noOfRows = (int) distributionPointService.deleteDistributionPoint(result)
               if (noOfRows > 0) {
                   message = this.getMessage(result.distributionPoint, Message.SUCCESS, this.SUCCESS_DELETE)
               } else {
                   message = this.getMessage(result.distributionPoint, Message.ERROR, this.FAIL_DELETE)
               }
           }
           return message;
       } catch (Exception ex) {
           log.error(ex.message)
           message = this.getMessage("Product", Message.ERROR, "Exception-${ex.message}")
           return message;
       }
  }

}