package com.bits.bdfp.bonus.onepercentbonus

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.bonus.OnePercentBonus
import com.bits.bdfp.bonus.OnePercentBonusService

@Component("deleteOnePercentBonusAction")
class DeleteOnePercentBonusAction extends Action {
    public static final Log log = LogFactory.getLog(DeleteOnePercentBonusAction.class)
    private final String MESSAGE_HEADER = 'Delete One Percent Bonus'
    private final String MESSAGE_SUCCESS = 'One Percent Bonus deleted successfully'
    private final String MESSAGE_FAILURE = 'One Percent Bonus delete failed'

    @Autowired
    OnePercentBonusService onePercentBonusService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            OnePercentBonus onePercentBonus = onePercentBonusService.read(Long.parseLong(params.id))
            onePercentBonusService.delete(onePercentBonus)
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