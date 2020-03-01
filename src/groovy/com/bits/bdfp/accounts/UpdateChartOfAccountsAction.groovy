package com.bits.bdfp.accounts

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 2/7/2016.
 */
@Component("updateChartOfAccountsAction")
class UpdateChartOfAccountsAction extends Action {

    @Autowired
    ChartOfAccountService chartOfAccountService

    Message message

    @Override
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

    @Override
    public Object postCondition(Object object1,Object object2) {
        return null
    }

    @Override
    public Object execute(Object params, Object object) {
        try {
            Map ids = [:]
            int length = Integer.parseInt(params.length)
            ChartOfAccounts[] chartOfAccounts = new ChartOfAccounts[length]
            EnterpriseConfiguration enterpriseConfiguration = EnterpriseConfiguration.read(Long.parseLong(params.enterpriseConfiguration.id))
            for (int i = 0; i < length; i++) {
                params.items.each { key, val ->
                    if (key == "chartOfAccounts[" + i + "]") {
                        if(val.id) {
                            chartOfAccounts[i] = ChartOfAccounts.read(Long.parseLong(val.id))
                            chartOfAccounts[i].userUpdated = (ApplicationUser) object
                            chartOfAccounts[i].lastUpdated = new Date()
                        }else{
                            chartOfAccounts[i] = new ChartOfAccounts()
                            chartOfAccounts[i].enterpriseConfiguration = enterpriseConfiguration
                            chartOfAccounts[i].chartOfAccountLayer = ChartOfAccountLayer.read(Long.parseLong(val.chartOfAccountLayer.id))
                            chartOfAccounts[i].userCreated = (ApplicationUser) object
                            chartOfAccounts[i].dateCreated = new Date()
                            chartOfAccounts[i].isActive = true
                        }
                        chartOfAccounts[i].parentId = val.parentId ? ids.get(val.parentId) : 0
                        chartOfAccounts[i].chartOfAccountCodeAuto = val.chartOfAccountCodeAuto
                        chartOfAccounts[i].chartOfAccountCodeUser = val.chartOfAccountCodeUser
                        chartOfAccounts[i].chartOfAccountName = val.chartOfAccountName
                        chartOfAccounts[i].accountType = val.accountType
                        chartOfAccounts[i].parentCode = val.parentCode
                        message = this.preCondition(null, chartOfAccounts[i])
                        if (message.type == 1) {
                            int noOfRows = 0
                            if(params.length == params.lengthOld) {
                                noOfRows = chartOfAccountService.updateChartOfAccounts(chartOfAccounts[i])
                            }else{
//                                if(chartOfAccounts[i].id) {
//                                    noOfRows = chartOfAccountService.deleteChartOfAccounts(chartOfAccounts[i])
//                                    chartOfAccounts[i].id = null
//                                }
                                noOfRows = chartOfAccountService.createChartOfAccounts(chartOfAccounts[i])
                            }
                            ids.put(val.gridId, chartOfAccounts[i].id)
                            if (noOfRows <= 0) {
                                message = this.getMessage('Chart Of Account', Message.ERROR, 'Chart Of Accounts Could Not Be Updated.')
                                return message
                            }
                        }
                    }
                }
            }
            message = this.getMessage('Chart Of Account', Message.SUCCESS, 'Chart Of Accounts Updated Successfully')
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Chart Of Account", Message.ERROR, "Exception-${ex.message}")
        }
    }
}
