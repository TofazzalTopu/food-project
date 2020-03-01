package com.bits.bdfp.accounts.journal

import com.bits.bdfp.accounts.JournalDetailsService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by depok.chakma on 9/1/2016.
 */
@Component("listPostfixJournal")
public class ListPostfixCodeJournalAction extends Action{
    @Autowired
    JournalDetailsService journalDetailsService
    @Override
    protected Object preCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    protected Object postCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    public Object execute(Object params, Object object) {

        try {
            return journalDetailsService.listPostfixCode(params)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

}
