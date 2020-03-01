package com.bits.bdfp.inventory.setup.quotation

import com.bits.bdfp.inventory.setup.QuotationDetails
import com.docu.commons.ObjectUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import com.docu.commons.CommonConstants
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.inventory.setup.Quotation
import com.bits.bdfp.inventory.setup.QuotationService


import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService


@Component("updateQuotationAction")
class UpdateQuotationAction extends Action {
    public static final Log log = LogFactory.getLog(UpdateQuotationAction.class)
    private final String MESSAGE_HEADER = 'Update Quotation'
    private final String MESSAGE_SUCCESS = 'Quotation Updated Successfully'

    @Autowired
    QuotationService quotationService

    Message message


    public Object preCondition(def params, def object) {
        try {
            Map map = (Map) object
            Quotation quotation = map.quotation
            if (!quotation.validate()) {
                message = this.getValidationErrorMessage(quotation)
            } else {
                message = this.getMessage(quotation, Message.SUCCESS, SUCCESS_SAVE)
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Quotation", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object execute(def params, def object) {
        try {
            Quotation quotation = Quotation.read(Long.parseLong(params.id))
            quotation.properties = params
            quotation.lastUpdated = new Date()
            quotation.userUpdated = (ApplicationUser) object
            List<QuotationDetails> quotationDetailsList = ObjectUtil.instantiateObjects(params.items, QuotationDetails.class)
            for (int i = 0; i < quotationDetailsList.size(); i++) {
                quotationDetailsList[i].quotation = quotation
            }

            Map map = [:]
            map.put('quotation', quotation)
            map.put('quotationDetailsList', quotationDetailsList)
            message = this.preCondition(params, map)
            if (message.type == 1) {
                int noOfRows = quotationService.update(map)
                if (noOfRows > 0) {
                    message = this.getMessage("Quotation", Message.SUCCESS, 'Quotation, Numbered: ' + quotation.quotationNo + ' Successfully Updated.')
                } else {
                    message = this.getMessage('Quotation', Message.ERROR, 'Quotation Could Not Be Updated.')
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, 'Quotation Could Not Be Updated.')
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}
