package com.bits.bdfp.promotion

import com.docu.common.Action
import grails.plugins.springsecurity.SpringSecurityService
import groovy.sql.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource

class CreatePromotionService {
    static transactional = false
    DataSource dataSource
    Sql sql


    @Autowired
    SpringSecurityService springSecurityService

    @Transactional(readOnly = true)
    public Promotion read(Long id) {
        return Promotion.read(id)
    }


    @Transactional
    public Promotion create(Object object) {
        try {
            Promotion promotion = (Promotion) object
            if (promotion.save(false)) {
                return promotion
            }
            return null
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object object) {
        Promotion promotion = (Promotion) object
        if (promotion.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }
    @Transactional
    public Integer updateInactive(Object object) {
        Promotion promotion = (Promotion) object
        if (promotion.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        Promotion promotion = (Promotion) object
        promotion.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<Promotion> objList = Promotion.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = Promotion.count()
        return [objList: objList, count: total]
    }



}
