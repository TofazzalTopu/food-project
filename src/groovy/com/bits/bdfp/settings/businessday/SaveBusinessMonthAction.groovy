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

@Component("saveBusinessMonthAction")
class SaveBusinessMonthAction implements IAction {
  public static final Log log = LogFactory.getLog(SaveBusinessMonthAction.class)
  private final String MESSAGE_HEADER = 'businessMonth.header'
  private final String MESSAGE_SUCCESS = 'businessMonth.save.success'

  @Autowired
  FinancialYearService financialYearService
  @Autowired
  SessionManagementService sessionManagementService

    public Object preCondition(def params) {
        Map mapInstance = [:]
        BusinessMonth businessMonth = null
        Date dateOpen=null
        try {
            boolean isOpen=true
            if(params?.month  && params?.financialYear.id){
                if(financialYearService.isBusinessMonthOpen(params)){
                    return UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, "Business month already open,please close it first", businessMonth)
                }
                else{
                    List businessMonthStartEndDateList =financialYearService.getBusinessMonthStartAndEndDate(params)
                    if(businessMonthStartEndDateList &&  businessMonthStartEndDateList.size()>0){
                        businessMonth = new BusinessMonth(params)
                        businessMonth.startDate=businessMonthStartEndDateList[0].start_date
                        businessMonth.endDate=businessMonthStartEndDateList[0].end_date
                        businessMonth.dateCreated=new Date()
                        businessMonth.isOpen=isOpen
                        businessMonth.openDate=new Date()
                        businessMonth.monthName=businessMonthStartEndDateList[0].month_string

                    }
                    else{
                        return UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, "No business day time set for this month", businessMonth)
                    }


                    if (!businessMonth.validate()) {
                        return UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, financialYearService.getErrorMessage(businessMonth), businessMonth)
                    }
                }

            }
            else{
                return UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, "Please select Financial year,Financial Month", businessMonth)
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