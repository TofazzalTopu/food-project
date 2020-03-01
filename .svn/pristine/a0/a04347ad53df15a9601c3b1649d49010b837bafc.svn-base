package com.bits.bdfp.inventory.setup.vatrate

import com.bits.bdfp.inventory.setup.VatRateService
import com.docu.common.Action

import grails.plugins.springsecurity.SpringSecurityService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("utilVatRateAction")
class UtilVatRateAction extends Action{
    public static final Log log = LogFactory.getLog(UtilVatRateAction.class)
    private final String MESSAGE_HEADER = 'New Vat Rate'
    private final String MESSAGE_SUCCESS = 'Vat Rate Created Successfully'

    @Autowired
    VatRateService vatRateService
    
    @Autowired
    SpringSecurityService springSecurityService
    

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        return null
    }

    public Object postCondition(def params, def object) {
        return null
    }

    public Object getProductById(Long id){
        return vatRateService.getProductById(id)
    }

    public List getProductListByKey(String key){
        return vatRateService.getProductListByKey(key)
    }
}