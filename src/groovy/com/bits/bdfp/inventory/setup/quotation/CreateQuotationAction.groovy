package com.bits.bdfp.inventory.setup.quotation

import com.bits.bdfp.inventory.setup.QuotationDetails
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.common.CodeGenerationUtil
import com.docu.commons.ObjectUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.inventory.setup.Quotation
import com.bits.bdfp.inventory.setup.QuotationService


import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService

import java.text.SimpleDateFormat


@Component("createQuotationAction")
class CreateQuotationAction extends Action{
    public static final Log log = LogFactory.getLog(CreateQuotationAction.class)
    private final String MESSAGE_HEADER = 'New Quotation'
    private final String MESSAGE_SUCCESS = 'Quotation Created Successfully'

    Message message

    @Autowired
    QuotationService quotationService
    
    public Object preCondition(def params, def object) {
        try {
            Map map = (Map) object
            Quotation quotation = map.quotation
            if (!quotation.validate()) {
                message = this.getValidationErrorMessage(quotation)
            }else{
                message = this.getMessage(quotation, Message.SUCCESS, SUCCESS_SAVE)
            }
            return  message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Quotation", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object execute(def params, def object) {
        try {
            Date dateNow = new Date()
            Quotation quotation = new Quotation(params)
            quotation.dateCreated = dateNow
            quotation.userCreated = (ApplicationUser) object
            SimpleDateFormat formatDate = new SimpleDateFormat("DD")
            String currentDay = formatDate.format(dateNow)
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            quotation.quotationNo = CodeGenerationUtil.instance.generateCode(quotation.enterpriseConfiguration, "QUOTATION", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
            quotation.customerId = CodeGenerationUtil.instance.generateCode(quotation.enterpriseConfiguration, "TENTATIVE_CUSTOMER", "", "", "", "", "", currentMonth, '', "", '')

            List<QuotationDetails> quotationDetailsList = ObjectUtil.instantiateObjects(params.items, QuotationDetails.class)
            for (int i = 0; i < quotationDetailsList.size(); i++) {
                quotationDetailsList[i].quotation = quotation
            }
            
            Map map = [:]
            map.put('quotation', quotation)
            map.put('quotationDetailsList', quotationDetailsList)

            message = this.preCondition(params, map)
            if (message.type == 1) {
                int noOfRows = quotationService.create(map)
                if (noOfRows > 0) {
                    message = this.getMessage("Quotation", Message.SUCCESS, 'Quotation, Numbered: ' + quotation.quotationNo + ' Successfully Created For Customer ID: ' + quotation.customerId + '.')
                } else {
                    message = this.getMessage('Quotation', Message.ERROR, 'Quotation Could Not Be Created.')
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, 'Quotation Could Not Be Created.')
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}