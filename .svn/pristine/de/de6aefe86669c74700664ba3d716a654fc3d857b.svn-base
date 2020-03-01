package com.bits.bdfp.inventory.demandorder.adjustusingho

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.inventory.demandorder.AdjustUsingHo
import com.bits.bdfp.inventory.demandorder.AdjustUsingHoService

@Component("deleteAdjustUsingHoAction")
class DeleteAdjustUsingHoAction extends Action {
    public static final Log log = LogFactory.getLog(DeleteAdjustUsingHoAction.class)
    private final String MESSAGE_HEADER = 'Delete Adjust Using Ho'
    private final String MESSAGE_SUCCESS = 'Adjust Using Ho deleted successfully'
    private final String MESSAGE_FAILURE = 'Adjust Using Ho delete failed'

    @Autowired
    AdjustUsingHoService adjustUsingHoService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            AdjustUsingHo adjustUsingHo = adjustUsingHoService.read(Long.parseLong(params.id))
            adjustUsingHoService.delete(adjustUsingHo)
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