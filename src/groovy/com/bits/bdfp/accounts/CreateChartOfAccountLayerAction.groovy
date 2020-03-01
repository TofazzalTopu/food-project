package com.bits.bdfp.accounts

import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.ObjectUtil
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 2/2/2016.
 */
@Component("createChartOfAccountLayerAction")
class CreateChartOfAccountLayerAction extends Action {

    @Autowired
    ChartOfAccountService chartOfAccountService
    Message message

    @Override
    protected Object preCondition(Object object, Object params) {
        try {
//            if (!marketReturn.validate()) {
            if(!object){
                message = this.getValidationErrorMessage(object)
            }else{
                message = this.getMessage(object, Message.SUCCESS, SUCCESS_SAVE)
            }
            return  message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Chart Of Account", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    @Override
    protected Object postCondition(Object object, Object object2) {
        return null
    }

    @Override
    protected Object execute(Object params, Object object) {
        try {
            List<ChartOfAccountLayer> chartOfAccountLayerList = ObjectUtil.instantiateObjects(params.items, ChartOfAccountLayer.class)
            for (int i = 0; i < chartOfAccountLayerList.size(); i++) {
                chartOfAccountLayerList[i].userCreated = (ApplicationUser) object
                chartOfAccountLayerList[i].dateCreated = new Date()
            }

            message = this.preCondition(chartOfAccountLayerList, params)
            if (message.type == 1) {
                int noOfRows = chartOfAccountService.create(chartOfAccountLayerList)
                if (noOfRows > 0) {
                    message = this.getMessage('Chart Of Account', Message.SUCCESS, 'Chart Of Account Layers Created Successfully')
                } else {
                    message = this.getMessage('Chart Of Account', Message.ERROR, 'Chart Of Account Layers Could Not Be Saved.')
                }
            }
            return message

        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Chart Of Account", Message.ERROR, "Exception-${ex.message}")
        }
    }
}
