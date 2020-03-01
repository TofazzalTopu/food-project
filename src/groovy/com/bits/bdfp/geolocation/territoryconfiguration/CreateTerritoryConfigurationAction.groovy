package com.bits.bdfp.geolocation.territoryconfiguration

import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.geolocation.TerritoryConfigurationService
import com.bits.bdfp.geolocation.TerritorySubArea
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.ObjectUtil
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createTerritoryConfigurationAction")
class CreateTerritoryConfigurationAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    TerritoryConfigurationService territoryConfigurationService
    Message message

    public Object preCondition(Object params, Object object) {
        try {
            TerritoryConfiguration territoryConfiguration = (TerritoryConfiguration) object.get('territoryConfiguration')
            if (!territoryConfiguration.validate()) {
                message = this.getValidationErrorMessage(territoryConfiguration)
            } else {
                message = this.getMessage(territoryConfiguration, Message.SUCCESS, this.SUCCESS_SAVE)
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("TerritoryConfiguration", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object execute(Object params, Object object) {
        try {
            TerritoryConfiguration territoryConfiguration = new TerritoryConfiguration()
            territoryConfiguration.properties = params
            ApplicationUser applicationUser = (ApplicationUser) object
            territoryConfiguration.isActive = true
            territoryConfiguration.dateCreated = new Date()
            territoryConfiguration.userCreated = applicationUser
            List<TerritorySubArea> territorySubAreaList = ObjectUtil.instantiateObjects(params.items, TerritorySubArea.class)
            territorySubAreaList.each { territorySubArea ->
                territorySubArea.userCreated = applicationUser
                territorySubArea.territoryConfiguration = territoryConfiguration
                if (!territorySubArea.validate()) {
                    message = this.getValidationErrorMessage(territorySubArea)
                    throw new RuntimeException(message.messageBody[0])
                }
            }
            Map map = new LinkedHashMap()
            map.put('territoryConfiguration', territoryConfiguration)
            map.put('territorySubArea', territorySubAreaList)

            message = this.preCondition(null, map)
            if (message.type == 1) {
                territoryConfiguration = territoryConfigurationService.create(map)
                if (territoryConfiguration) {
                    message = this.getMessage(territoryConfiguration, Message.SUCCESS, "Territory Created Successfully")
                } else {
                    message = this.getMessage(territoryConfiguration, Message.ERROR, this.FAIL_SAVE)
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("TerritoryConfiguration", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}