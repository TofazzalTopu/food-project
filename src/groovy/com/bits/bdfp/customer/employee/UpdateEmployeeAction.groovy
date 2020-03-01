package com.bits.bdfp.customer.employee

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.CustomerMasterService
import com.bits.bdfp.customer.CustomerTerritorySubArea
import com.bits.bdfp.geolocation.TerritorySubArea
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 9/3/15.
 */
@Component("updateEmployeeAction")
class UpdateEmployeeAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerMasterService customerMasterService
    Message message

    protected Message preCondition(Object object, Object params) {
        try {
            CustomerMaster customerMaster = (CustomerMaster) object
            if (!customerMaster.validate()) {
                return this.getValidationErrorMessage(customerMaster)
            }
            else {
                /******** Manual validation *******/
                if(!customerMaster.department){
                    return this.getMessage(customerMaster, Message.ERROR, "Employee Department is not selected")
                }
                if(!customerMaster.enterpriseConfiguration){
                    return this.getMessage(customerMaster, Message.ERROR, "Employee Enterprise is not selected")
                }

                if(!customerMaster.mobile){
                    return this.getMessage(customerMaster, Message.ERROR, "Employee Mobile can not be blank")
                }

                if(!customerMaster.presentAddress){
                    return this.getMessage(customerMaster, Message.ERROR, "Employee Present Address can not be blank")
                }
                if(!customerMaster.permanentAddress){
                    return this.getMessage(customerMaster, Message.ERROR, "Employee Permanent Address can not be blank")
                }

                if(!customerMaster.designation){
                    return this.getMessage(customerMaster, Message.ERROR, "Employee Designation is not selected")
                }

                if(customerMaster.department.id == ApplicationConstants.SALES_DEPARTMENT_ID){
                    if(!params.territorySubAreaIdList) {
                        return this.getMessage(customerMaster, Message.ERROR, "No Geographic Location is selected")
                    }
                }
            }
            return this.getMessage(customerMaster, Message.SUCCESS, this.SUCCESS_SAVE)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("CustomerMaster", Message.ERROR, "Exception-${ex.message}")
        }
    }

    public Message execute(Object params, Object object) {
        try {
            Map mapInstance = [:]
            CustomerTerritorySubArea customerTerritorySubArea = null
            List<CustomerTerritorySubArea> customerTerritorySubAreaList = new ArrayList<CustomerTerritorySubArea>()
            ApplicationUser applicationUser = (ApplicationUser) object
            CustomerMaster customerMaster = customerMasterService.read(Long.parseLong(params.id))
            customerMaster.properties = params
            customerMaster.userUpdated = applicationUser

            if(!customerMaster.firstName){
                return this.getMessage(customerMaster, Message.ERROR, "Employee First Name can not be blank")
            }

            /************* Generate Full Name  ***********/
            String fullName = customerMaster.firstName

            if(params.middleName){
                fullName += " " + params.middleName
            }
            if(params.lastName){
                fullName += " " + params.lastName
            }

            customerMaster.name = fullName
            /*****************************************/

            message = this.preCondition(customerMaster, params)
            if (message.type == 1) {
                if(params.territorySubAreaIdList){
                    String[] territorySubAreaIds = params.territorySubAreaIdList.split(",")
                    territorySubAreaIds.each {  territorySubAreaId ->
                        customerTerritorySubArea = new CustomerTerritorySubArea()
                        customerTerritorySubArea.customerMaster = customerMaster
                        customerTerritorySubArea.territorySubArea = TerritorySubArea.get(Long.parseLong(territorySubAreaId))
                        customerTerritorySubArea.userCreated = applicationUser
                        customerTerritorySubAreaList.add(customerTerritorySubArea)
                    }
                }
                mapInstance.put(CustomerMaster.class.simpleName, customerMaster)
                mapInstance.put(CustomerTerritorySubArea.class.simpleName, customerTerritorySubAreaList)
                customerMaster = customerMasterService.updateEmployee(mapInstance)
                if (customerMaster) {
                    message = this.getMessage(customerMaster, Message.SUCCESS, "Employee Updated Successfuly")
                } else {
                    message = this.getMessage(customerMaster, Message.ERROR, this.FAIL_SAVE)
                }
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("CustomerMaster", Message.ERROR, "Exception-${ex.message}")
        }
    }

    protected Object postCondition(Object params, Object object) {
        return null
    }
}
