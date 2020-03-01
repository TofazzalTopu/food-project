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

import java.text.SimpleDateFormat

@Component("saveBusinessDayAction")
class SaveBusinessDayAction implements IAction {
  public static final Log log = LogFactory.getLog(SaveBusinessDayAction.class)
  private final String MESSAGE_HEADER = 'financialYear.header'
  private final String MESSAGE_SUCCESS = 'financialYear.save.success'

  @Autowired
  FinancialYearService financialYearService
  @Autowired
  SessionManagementService sessionManagementService

    public Object preCondition(def params) {
        Map mapInstance = [:]
        BusinessDay businessDay = null
        try {
            boolean isOpen=true
            if(params?.month && params?.day  && params?.yearVal){
                if(financialYearService.isBusinessDayOpen(params)){
                    return UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, "Office day is already opened,please close it first", businessDay)
                }
                    else if (financialYearService.isBusinessHoliday(params)){
                    return UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, "This is an holiday,you can not open day on  holiday", businessDay)
                }
                else{
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String dateInString = "${params?.yearVal}-${params?.month}-${params?.day}";
                    Date businessDate = formatter.parse(dateInString);
                    businessDay = new BusinessDay()
                    businessDay.businessDate=businessDate
                    businessDay.systemDate=new Date()
                    params.put("id",params?.financialYearId)
                    businessDay.openedFrom=params.openedFrom
                    businessDay.userCreated=params.userCreated
                    businessDay.dateCreated=new Date()
                    businessDay.isOpen=isOpen

                    if (!businessDay.validate()) {
                        return UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, financialYearService.getErrorMessage(businessDay), businessDay)
                    }
                }

            }
            else{
                return UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, "Please select Financial year,Financial Month and Financial day", businessDay)
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