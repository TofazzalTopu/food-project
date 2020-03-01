package com.bits.bdfp.setup.salestarget.monthlysalestargetbyvolume

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.setup.salestarget.MonthlySalesTargetByVolume
import com.bits.bdfp.setup.salestarget.MonthlySalesTargetByVolumeService

@Component("deleteMonthlySalesTargetByVolumeAction")
class DeleteMonthlySalesTargetByVolumeAction extends Action {
    public static final Log log = LogFactory.getLog(DeleteMonthlySalesTargetByVolumeAction.class)
    private final String MESSAGE_HEADER = 'Delete Monthly Sales Target By Volume'
    private final String MESSAGE_SUCCESS = 'Monthly Sales Target By Volume deleted successfully'
    private final String MESSAGE_FAILURE = 'Monthly Sales Target By Volume delete failed'

    @Autowired
    MonthlySalesTargetByVolumeService monthlySalesTargetByVolumeService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            MonthlySalesTargetByVolume monthlySalesTargetByVolume = monthlySalesTargetByVolumeService.read(Long.parseLong(params.id))
            monthlySalesTargetByVolumeService.delete(monthlySalesTargetByVolume)
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