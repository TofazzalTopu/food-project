package com.bits.bdfp.inventory.demandorder.writeoff

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.inventory.demandorder.WriteOff
import com.bits.bdfp.inventory.demandorder.WriteOffService

@Component("deleteWriteOffAction")
class DeleteWriteOffAction extends Action {
    public static final Log log = LogFactory.getLog(DeleteWriteOffAction.class)
    private final String MESSAGE_HEADER = 'Delete Write Off'
    private final String MESSAGE_SUCCESS = 'Write Off deleted successfully'
    private final String MESSAGE_FAILURE = 'Write Off delete failed'

    @Autowired
    WriteOffService writeOffService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            WriteOff writeOff = writeOffService.read(Long.parseLong(params.id))
            writeOffService.delete(writeOff)
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