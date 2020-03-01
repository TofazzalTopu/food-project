package com.bits.bdfp.accounts.mushak

import com.bits.bdfp.accounts.ChartOfAccountService
import com.bits.bdfp.accounts.MushakDetails
import com.bits.bdfp.inventory.product.FinishProduct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.accounts.Mushak
import com.bits.bdfp.accounts.MushakService

@Component("deleteMushakAction")
class DeleteMushakAction extends Action {
    public static final Log log = LogFactory.getLog(DeleteMushakAction.class)
    private final String MESSAGE_HEADER = 'Delete Mushak'
    private final String MESSAGE_SUCCESS = 'Mushak deleted successfully'
    private final String MESSAGE_FAILURE = 'Mushak delete failed'

    @Autowired
    MushakService mushakService
    @Autowired
    ChartOfAccountService chartOfAccountService

    Message message

    public Object preCondition(def params, def object) {
        try {
            MushakDetails mushakDetails = MushakDetails.read(Long.parseLong(params.id))
            Mushak mushak = Mushak.read(params.mushakId)
            String code = mushakDetails.finishProduct.code + chartOfAccountService.getHead("Mushak Value")
            Map map = [:]
            map.put('mushakDetails', mushakDetails)
            map.put('code', code)
            map.put('mushakNo', mushak.mushakNo)
            int noOfRows = mushakService.deleteDetail(map)
            if (noOfRows > 0) {
                message = this.getMessage('Delete Alert', Message.SUCCESS, 'Mushak Detail Deleted Successfully.')
                mushak.totalMushakAmount = Float.parseFloat(params.amount)
                mushakService.update(mushak)
            } else {
                message = this.getMessage('Delete Alert', Message.ERROR, 'Mushak Detail Could Not Be Deleted.')
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage('Delete Alert', Message.ERROR, 'Mushak Detail Could Not Be Deleted.')
        }
    }

    public Object execute(def params, def object) {
        try {
            Mushak mushak = mushakService.read(Long.parseLong(params.id))
            int noOfRows = mushakService.delete(mushak)
            if (noOfRows > 0) {
                message = this.getMessage('Delete Alert', Message.SUCCESS, 'Mushak Deleted Successfully.')
            } else {
                message = this.getMessage('Delete Alert', Message.ERROR, 'Mushak Could Not Be Deleted.')
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage('Delete Alert', Message.ERROR, 'Mushak Could Not Be Deleted.')
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}