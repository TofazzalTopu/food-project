package com.bits.bdfp.accounts

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.ObjectUtil
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 2/4/2016.
 */
@Component("createChartOfAccountsAction")
class CreateChartOfAccountsAction extends Action {

    @Autowired
    ChartOfAccountService chartOfAccountService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService
    Message message

    private static final Log log = LogFactory.getLog(this)

    public Object preCondition(Object params, Object object) {
        try {
            ChartOfAccounts chartOfAccounts = (ChartOfAccounts) object
            if (!chartOfAccounts.validate()) {
                message = this.getValidationErrorMessage(chartOfAccounts)
            } else {
                message = this.getMessage(chartOfAccounts, Message.SUCCESS, SUCCESS_SAVE)
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Chart Of Account", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object execute(Object params, Object object) {
        try {
            Map ids = [:]
            int length = Integer.parseInt(params.length)
            ChartOfAccounts[] chartOfAccounts = new ChartOfAccounts[length]
            EnterpriseConfiguration enterpriseConfiguration = EnterpriseConfiguration.read(Long.parseLong(params.enterpriseConfiguration.id))
            for (int i = 0; i < length; i++) {
                params.items.each { key, val ->
                    if (key == "chartOfAccounts[" + i + "]") {
                        chartOfAccounts[i] = new ChartOfAccounts()
                        chartOfAccounts[i].enterpriseConfiguration = enterpriseConfiguration
                        chartOfAccounts[i].chartOfAccountLayer = ChartOfAccountLayer.read(Long.parseLong(val.chartOfAccountLayer.id))
                        chartOfAccounts[i].chartOfAccountCodeAuto = val.chartOfAccountCodeAuto
                        chartOfAccounts[i].chartOfAccountCodeUser = val.chartOfAccountCodeUser
                        chartOfAccounts[i].chartOfAccountName = val.chartOfAccountName
                        chartOfAccounts[i].parentId = val.parentId ? ids.get(val.parentId) : 0
                        chartOfAccounts[i].parentCode = val.parentCode
                        chartOfAccounts[i].userCreated = (ApplicationUser) object
                        chartOfAccounts[i].dateCreated = new Date()
                        chartOfAccounts[i].isActive = true
                        message = this.preCondition(null, chartOfAccounts[i])
                        if (message.type == 1) {
                            int noOfRows = chartOfAccountService.createChartOfAccounts(chartOfAccounts[i])
                            ids.put(val.gridId, chartOfAccounts[i].id)
                            if (noOfRows <= 0) {
                                message = this.getMessage('Chart Of Account', Message.ERROR, 'Chart Of Accounts Could Not Be Saved.')
                                for(int j = 0; j < chartOfAccounts.length; j++){
                                    if(chartOfAccounts[j] && chartOfAccounts[j].id){
                                        chartOfAccountService.delete(chartOfAccounts[i])
                                    }
                                }
                                return message
                            }
                        }
                    }
                }
            }
            enterpriseConfiguration.layerUsed = true
            enterpriseConfigurationService.update(enterpriseConfiguration)
            message = this.getMessage('Chart Of Account', Message.SUCCESS, 'Chart Of Accounts Created Successfully')
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Chart Of Account", Message.ERROR, "Exception-${ex.message}")
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}
