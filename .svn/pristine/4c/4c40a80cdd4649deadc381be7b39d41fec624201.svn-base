package com.bits.bdfp.settings.businessday


import com.bits.bdfp.settings.FinancialYearService
import com.bits.bdfp.settings.bussinessday.BusinessDay
import com.docu.commons.IAction
import com.docu.commons.Message
import com.docu.commons.SessionManagementService
import com.docu.commons.UserMessageBuilder
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("updateBusinessDayAction")
class UpdateBusinessDayAction implements IAction {
  public static final Log log = LogFactory.getLog(UpdateBusinessDayAction.class)
  private final String MESSAGE_HEADER = 'financialYear.header'
  private final String MESSAGE_SUCCESS = 'financialYear.save.success'

  @Autowired
  FinancialYearService financialYearService
  @Autowired
  SessionManagementService sessionManagementService

    public Object preCondition(def params) { Map mapInstance = [:]
        BusinessDay businessDay = financialYearService.businessDay(params)
        try {
            boolean isOpen=false
            println(params)

            if( params?.isOpen && !financialYearService.isSelectedBusinessDayOpen(params)){
                isOpen=true
            }
            businessDay.dateLastUpdated=new Date()
            businessDay.userLastUpdated=params?.userCreated
            businessDay.isOpen=isOpen

            if (!businessDay.validate()) {
                return UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, financialYearService.getErrorMessage(businessDay), businessDay)
            }


            mapInstance = (Map) UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, null)
            mapInstance.put(BusinessDay.class.simpleName, businessDay)
            mapInstance.put("businessDay",businessDay)

        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, businessDay, ex)
        }

        return mapInstance
    }

    public Object execute(def params, def object) {
        BusinessDay businessDay = null
        try {
            financialYearService.saveBusinessDay(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, businessDay, ex)
        }

        return UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, financialYearService.getUserMessage(MESSAGE_SUCCESS, null))
    }

  public Object postCondition(def object) {
    return null
  }
}