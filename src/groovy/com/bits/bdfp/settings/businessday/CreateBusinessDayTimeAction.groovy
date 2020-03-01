package com.bits.bdfp.settings.businessday

import com.bits.bdfp.settings.FinancialYearService
import com.bits.bdfp.settings.bussinessday.BusinessDayTime
import com.docu.commons.IAction
import com.docu.commons.Message
import com.docu.commons.SessionManagementService
import com.docu.commons.UserMessageBuilder
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("CreateBusinessDayTimeAction")
class CreateBusinessDayTimeAction implements IAction {
  public static final Log log = LogFactory.getLog(CreateBusinessDayTimeAction.class)
  private final String MESSAGE_HEADER = 'businessDayTime.header'
  private final String MESSAGE_SUCCESS = 'businessDayTime.save.success'

  @Autowired
  FinancialYearService financialYearService
  @Autowired
  SessionManagementService sessionManagementService

  public Object preCondition(def params) {
    Map mapInstance = [:]
    BusinessDayTime businessDayTime1 = null

    try {
        List  businessDayTimeList=financialYearService.getBusinessDayListFromCurrentDaysToLast(params)
        if(businessDayTimeList && businessDayTimeList.size()>0){
            businessDayTimeList.each {
                it.startTime=params.startTime
                it.endTime=params.endTime
            }
        }
        else{
            mapInstance = (Map) UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR,"No business day time found", businessDayTime1)
        }

        mapInstance = (Map) UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, businessDayTime1)
        mapInstance.put(BusinessDayTime.class.simpleName, businessDayTimeList)

    } catch (Exception ex) {
        log.error(ex.message)
        return UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, businessDayTime1, ex)
    }

    return mapInstance
  }

  public Object execute(def params, def object) {
        BusinessDayTime businessDayTime = null
        try {
           // financialYear = object.get(FinancialYear.class.simpleName)
            financialYearService.saveBusinessDayTime(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, businessDayTime, ex)
        }

        return UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, financialYearService.getUserMessage(MESSAGE_SUCCESS, null))
  }

  public Object postCondition(def object) {
    return null
  }
}