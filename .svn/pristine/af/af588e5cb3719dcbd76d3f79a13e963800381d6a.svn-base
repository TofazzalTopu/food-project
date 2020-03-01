package com.bits.bdfp.inventory.setup.vatrate

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.inventory.setup.VatRate
import com.bits.bdfp.inventory.setup.VatRateService

@Component("deleteVatRateAction")
class DeleteVatRateAction extends Action {
    public static final Log log = LogFactory.getLog(DeleteVatRateAction.class)
    private final String MESSAGE_HEADER = 'Delete Vat Rate'
    private final String MESSAGE_SUCCESS = 'Vat Rate deleted successfully'
    private final String MESSAGE_FAILURE = 'Vat Rate delete failed'

    @Autowired
    VatRateService vatRateService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            VatRate vatRate = vatRateService.read(Long.parseLong(params.id))
            vatRateService.delete(vatRate)
            return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, MESSAGE_FAILURE)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}