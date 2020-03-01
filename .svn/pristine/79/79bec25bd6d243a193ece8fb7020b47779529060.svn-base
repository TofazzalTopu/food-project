package com.bits.bdfp.settings.businessday
import com.bits.bdfp.settings.FinancialYearService
import com.bits.bdfp.settings.bussinessday.BusinessMonth
import com.docu.commons.IAction
import com.docu.commons.Message
import com.docu.commons.SessionManagementService
import com.docu.commons.UserMessageBuilder
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("updateBusinessMonthAction")
class UpdateBusinessMonthAction implements IAction {
  public static final Log log = LogFactory.getLog(UpdateBusinessMonthAction.class)
  private final String MESSAGE_HEADER = 'financialYear.header'
  private final String MESSAGE_SUCCESS = 'financialYear.save.success'

  @Autowired
  FinancialYearService financialYearService
  @Autowired
  SessionManagementService sessionManagementService

    public Object preCondition(def params) { Map mapInstance = [:]
        BusinessMonth businessMonth = financialYearService.businessMonth(params)
        try {
            boolean isOpen=false
            println(params)

            if( params?.isOpen && !financialYearService.isSelectedBusinessMonthOpen(params)){
                isOpen=true
            }
            businessMonth.dateLastUpdated=new Date()
            businessMonth.userLastUpdated=params?.userCreated
            businessMonth.isOpen=isOpen

            if (!businessMonth.validate()) {
                return UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, financialYearService.getErrorMessage(businessMonth), businessMonth)
            }


            mapInstance = (Map) UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, null)
            mapInstance.put(BusinessMonth.class.simpleName, businessMonth)


        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, businessMonth, ex)
        }

        return mapInstance
    }

    public Object execute(def params, def object) {
        BusinessMonth businessMonth = null
        try {
            financialYearService.saveBusinessMonth(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, businessMonth, ex)
        }

        return UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, financialYearService.getUserMessage(MESSAGE_SUCCESS, null))
    }

  public Object postCondition(def object) {
    return null
  }
}