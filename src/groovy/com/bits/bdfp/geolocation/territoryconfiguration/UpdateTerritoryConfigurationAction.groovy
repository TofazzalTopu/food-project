package com.bits.bdfp.geolocation.territoryconfiguration

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.geolocation.TerritoryConfigurationService
import com.bits.bdfp.geolocation.TerritorySubArea
import com.bits.bdfp.geolocation.TerritorySubAreaService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.ObjectUtil
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateTerritoryConfigurationAction")
class UpdateTerritoryConfigurationAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    TerritoryConfigurationService territoryConfigurationService
    @Autowired
    TerritorySubAreaService territorySubAreaService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

    public Object preCondition(Object params, Object object) {
        Boolean isError = false
        try {

            TerritoryConfiguration territoryConfiguration = (TerritoryConfiguration) object

//            String domain = 'territory_configuration'
//            String childDomain = 'territory_sub_area'
//
//            String id = territoryConfiguration.id

            //isError = validationCheckService.validationCheck(domain,childDomain, id)

            if (!territoryConfiguration.validate()) {
                message = this.getValidationErrorMessage(territoryConfiguration)
            } else if (isError) {
                message = this.getMessage('TerritoryConfiguration', Message.ERROR, 'This Territory has already been used')

            } else {
                message = this.getMessage(territoryConfiguration, Message.SUCCESS, this.SUCCESS_UPDATE)
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("TerritoryConfiguration", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            TerritoryConfiguration territoryConfiguration = TerritoryConfiguration.read(Long.parseLong(params?.id?.toString()))
            territoryConfiguration.properties = params
            territoryConfiguration.lastUpdated = new Date()
            territoryConfiguration.userUpdated = applicationUser
            if (params.isActiveVal == 'true') {
                territoryConfiguration.isActive = true
            } else {
                territoryConfiguration.isActive = false
            }

            List<TerritorySubArea> territorySubAreaList = ObjectUtil.instantiateObjects(params.items, TerritorySubArea.class)
            territorySubAreaList.each { territorySubArea ->
                if(!territorySubArea.id){
                    territorySubArea.userCreated = applicationUser
                    territorySubArea.territoryConfiguration = territoryConfiguration
                }else{
                    territorySubArea.userUpdated = applicationUser
                }
                if (!territorySubArea.validate()) {
                    message = this.getValidationErrorMessage(territorySubArea)
                    throw new RuntimeException(message.messageBody[0])
                }
            }
            Map map = new LinkedHashMap()
            map.put('territoryConfiguration', territoryConfiguration)
            map.put('territorySubArea', territorySubAreaList)
            message = preCondition(null, territoryConfiguration)
            if (message.type == 1) {
                int noOfRows = (int) territoryConfigurationService.update(map)
                if (noOfRows > 0) {
                    message = this.getMessage(territoryConfiguration, Message.SUCCESS, "Territory Updated Successfully")
                } else {
                    message = this.getMessage(territoryConfiguration, Message.ERROR, this.FAIL_UPDATE)
                }
            }
            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Territory Configuration", Message.ERROR, ex.message)
        }
    }
}
