package com.bits.bdfp.setup.salestarget.salesheadbyvolume

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.setup.salestarget.SalesHeadByVolume
import com.bits.bdfp.setup.salestarget.SalesHeadByVolumeService

@Component("deleteSalesHeadByVolumeAction")
class DeleteSalesHeadByVolumeAction extends Action {
    public static final Log log = LogFactory.getLog(DeleteSalesHeadByVolumeAction.class)
    private final String MESSAGE_HEADER = 'Delete Sales Head By Volume'
    private final String MESSAGE_SUCCESS = 'Sales Head By Volume deleted successfully'
    private final String MESSAGE_FAILURE = 'Sales Head By Volume delete failed'

    @Autowired
    SalesHeadByVolumeService salesHeadByVolumeService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            SalesHeadByVolume salesHeadByVolume = salesHeadByVolumeService.read(Long.parseLong(params.id))
            salesHeadByVolumeService.delete(salesHeadByVolume)
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