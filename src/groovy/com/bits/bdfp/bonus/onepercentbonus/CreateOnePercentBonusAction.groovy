package com.bits.bdfp.bonus.onepercentbonus

import com.bits.bdfp.bonus.BonusPercent
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.settings.EnterpriseConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.bonus.OnePercentBonus
import com.bits.bdfp.bonus.OnePercentBonusService


import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService


@Component("createOnePercentBonusAction")
class CreateOnePercentBonusAction extends Action {
    public static final Log log = LogFactory.getLog(CreateOnePercentBonusAction.class)
    private final String MESSAGE_HEADER = '1% Bonus'
    private final String MESSAGE_SUCCESS = '1% Bonus Created Successfully'

    Message message

    @Autowired
    OnePercentBonusService onePercentBonusService

    @Autowired
    SpringSecurityService springSecurityService


    public Object preCondition(def params, def object) {
        try {
            if (!object && !message) {
                message = this.getMessage(MESSAGE_HEADER, Message.INFORMATION, "Please select customer before assigning bonus.")
            } else if (object) {
                message = this.getMessage(MESSAGE_HEADER, Message.SUCCESS, SUCCESS_SAVE)
                object.each {
                    if (!it.validate()) {
                        message = this.getValidationErrorMessage(it)
                        return message
                    }
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, ex.message)
        }
    }

    public Object execute(def params, def object) {
        try {
            String ids = '('
            List<OnePercentBonus> onePercentBonusList = []
            List<OnePercentBonus> onePercentBonusUpdateList = []
            ApplicationUser applicationUser = (ApplicationUser) object
            OnePercentBonus onePercentBonus
            BonusPercent bonusPercent = BonusPercent.read(Long.parseLong(params.bonusPercent.id))
            String[] customer = params.customer.split(',')
            String[] product = params.product.split(',')

            List<OnePercentBonus> onePercentBonusDeleteList = OnePercentBonus.findAll("FROM OnePercentBonus AS opb " +
                    "WHERE opb.bonusPercent.id = ${params.bonusPercent.id ? params.bonusPercent.id : 0}" +
                    "AND (opb.customerMaster.id IN (${params.customerRejected ? params.customerRejected : 0}) " +
                    "OR opb.finishProduct.id IN (${params.productRejected ? params.productRejected : 0}))")
            if (onePercentBonusDeleteList) {
                message = this.getMessage(MESSAGE_HEADER, Message.INFORMATION, "Product removed from bonus list.")
                onePercentBonusDeleteList.each {
                    it.delete()
                }
            }

            for (int i = 0; i < customer.length; i++) {
                if (customer[i]) {
                    for (int j = 0; j < product.length; j++) {
                        List temp = onePercentBonusService.fetchBonus(customer[i], product[j] ? product[j] : '0')
                        if (!temp) {
                            onePercentBonus = new OnePercentBonus()
                            onePercentBonus.enterpriseConfiguration = EnterpriseConfiguration.read((Long.parseLong(params.enterpriseConfiguration)))
                            onePercentBonus.customerMaster = CustomerMaster.read(Long.parseLong(customer[i]))
                            onePercentBonus.finishProduct = FinishProduct.read(Long.parseLong(product[j]))
                            onePercentBonus.bonusPercent = bonusPercent
                            onePercentBonus.lastCalculated = new Date()
                            onePercentBonus.lastCalculatedFactory = new Date()
                            onePercentBonus.dateCreated = new Date()
                            onePercentBonus.userCreated = applicationUser
                            onePercentBonus.isActive = true
                            onePercentBonusList.add(onePercentBonus)
                        } else {
                            onePercentBonus = OnePercentBonus.read(temp[0].id)
                            if (onePercentBonus.bonusPercent != bonusPercent) {
                                if (onePercentBonus.version != 0) {
                                    onePercentBonus.bonusPercent = bonusPercent
                                    onePercentBonus.lastUpdated = new Date()
                                    onePercentBonus.userUpdated = applicationUser
                                    onePercentBonusUpdateList.add(onePercentBonus)
                                    if(ids == '(') {
                                        ids = ids + onePercentBonus.id
                                    }else{
                                        ids = ids + ',' + onePercentBonus.id
                                    }
                                } else {
                                    onePercentBonus = new OnePercentBonus()
                                    onePercentBonus.enterpriseConfiguration = EnterpriseConfiguration.read((Long.parseLong(params.enterpriseConfiguration)))
                                    onePercentBonus.customerMaster = CustomerMaster.read(Long.parseLong(customer[i]))
                                    onePercentBonus.finishProduct = FinishProduct.read(Long.parseLong(product[j]))
                                    onePercentBonus.bonusPercent = bonusPercent
                                    onePercentBonus.lastCalculated = new Date()
                                    onePercentBonus.lastCalculatedFactory = new Date()
                                    onePercentBonus.dateCreated = new Date()
                                    onePercentBonus.userCreated = applicationUser
                                    onePercentBonus.isActive = true
                                    onePercentBonusList.add(onePercentBonus)
                                }
                            }
                        }
                    }
                }
            }

            ids = ids + ')'
            onePercentBonusDeleteList = OnePercentBonus.findAll("FROM OnePercentBonus AS opb " +
                    "WHERE opb.customerMaster.id IN (${params.customer ? params.customer : 0}) " +
                    "AND opb.bonusPercent.id != ${bonusPercent.id} " +
                    "${ids == '()'? '' : 'AND opb.id NOT IN ' + ids}")
            if (onePercentBonusDeleteList) {
                onePercentBonusDeleteList.each {
                    it.delete()
                }
            }

            Map map = [:]
            map.put('onePercentBonusList', onePercentBonusList)
            map.put('onePercentBonusUpdateList', onePercentBonusUpdateList)

            message = this.preCondition(params, onePercentBonusList)
            if (message.type == 1) {
                int noOfRows = onePercentBonusService.create(map)
                if (noOfRows > 0) {
                    message = this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)
                } else {
                    message = this.getMessage(MESSAGE_HEADER, Message.ERROR, FAIL_SAVE)
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}