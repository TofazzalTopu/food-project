package com.bits.bdfp.geolocation.territoryconfiguration

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.geolocation.TerritoryConfigurationService
import com.bits.bdfp.geolocation.TerritorySubArea
import com.bits.bdfp.geolocation.TerritorySubAreaService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteTerritoryConfigurationAction")
class DeleteTerritoryConfigurationAction extends Action {

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
        try{

            TerritoryConfiguration territoryConfiguration = territoryConfigurationService.read(Long.parseLong(params.id.toString()))
            String domain = 'territory_configuration'
            String id =  territoryConfiguration.id

            isError = validationCheckService.validationCheck(domain,id)

            if (!territoryConfiguration.validate()) {
               return this.getValidationErrorMessage(territoryConfiguration)
            }
            else if (isError){
                return this.getMessage('TerritoryConfiguration', Message.ERROR, 'This Territory has already been used')
            }
            else {
                return territoryConfiguration
            }

        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("TerritoryConfiguration", Message.ERROR, ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            Object result = this.preCondition(params, object)
            List list = territorySubAreaService.searchTerritorySubAreaByTerritory(params.id)
            for (int i = 0; i < list.size(); i++) {
                TerritorySubArea territorySubArea = TerritorySubArea.read(list[i].id)
                territorySubAreaService.delete(territorySubArea)
            }
            if (result instanceof TerritoryConfiguration) {
                int noOfRows = (int) territoryConfigurationService.delete(result)
                if (noOfRows > 0) {
                    message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_DELETE)
                } else {
                    message = this.getMessage(result, Message.ERROR, this.FAIL_DELETE)
                }
            }
            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("TerritoryConfiguration", Message.ERROR, ex.message)
        }
    }
}