package com.bits.bdfp.settings.businessday

import com.bits.bdfp.settings.FinancialYearService
import com.bits.bdfp.settings.bussinessday.BusinessDayTime
import com.bits.bdfp.settings.bussinessday.FinancialYear
import com.docu.common.Action
import com.docu.commons.IAction
import com.docu.commons.Message
import com.docu.commons.SessionManagementService
import com.docu.commons.UserMessageBuilder
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

@Component("createFinancialYearAction")
class CreateFinancialYearAction extends Action {
  public static final Log log = LogFactory.getLog(CreateFinancialYearAction.class)
  private final String MESSAGE_HEADER = 'financialYear.header'
  private final String MESSAGE_SUCCESS = 'financialYear.save.success'

  @Autowired
  FinancialYearService financialYearService
  @Autowired
  SessionManagementService sessionManagementService
  Message message
  public Object preCondition(Object params,Object object) {
    Map mapInstance = [:]
    FinancialYear financialYear = null
    List<BusinessDayTime>  businessDayTimeList=[]
    try {
        Calendar calendar = Calendar.getInstance();
        boolean  dateRangeExist=financialYearService.financialYearDateBetween(params)
        if(dateRangeExist){
            mapInstance = (Map) UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR,"Financial year already opened in this date range", financialYear)
        }
        else{
            ApplicationUser applicationUser = (ApplicationUser) object

            if(financialYearService.isFinancialYearOpen()){
                return UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, "Financial year already opened,close it first", financialYear)
            }
            else{
                financialYear = new FinancialYear()
                financialYear.properties = params
                financialYear.dateCreated=new Date()
                financialYear.userCreated=applicationUser
                financialYear.isOpened=true
                List<Date> dateList =financialYearService.searchBetweenDates(financialYear.dateStart,financialYear.dateEnd)
                dateList.each {
                    int datePart=it[calendar.DATE]
                    int monthPart=it[calendar.MONTH]+1
                    int yearPart=it[calendar.YEAR]
                    String  monthString=  new SimpleDateFormat("MMMM").format(it);
                    if(datePart && monthPart && yearPart){
                        BusinessDayTime businessDayTime = new BusinessDayTime()
                        businessDayTime.financialYear=financialYear
                        businessDayTime.date=datePart
                        businessDayTime.month=monthPart
                        businessDayTime.year=yearPart
                        businessDayTime.monthString=monthString
                        businessDayTime.dateCreated= new Date()
                        businessDayTime.userCreated=applicationUser
                        businessDayTime.businessDate=it
                        if(businessDayTime.validate()){
                            businessDayTimeList.add(businessDayTime)
                        }
                    }

                }

                if (!financialYear.validate()) {
                    return UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, financialYearService.getErrorMessage(financialYear), financialYear)
                }
                mapInstance = (Map) UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_SUCCESS, null), Message.SUCCESS,"Financial year save successful", financialYear)
                mapInstance.put(FinancialYear.class.simpleName, financialYear)
                mapInstance.put("businessDayTimeList",businessDayTimeList)
            }

        }



    } catch (Exception ex) {
        log.error(ex.message)
        return UserMessageBuilder.createMessage(financialYearService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, financialYear, ex)
    }

    return mapInstance
  }

  public Object execute(Object params, Object object) {
      try {
          FinancialYear financialYear=null
          Boolean isSave=false
          Map financialYearMap = this.preCondition(params, object)
          if(financialYearMap){
              if (financialYearMap?.message.type == Message.SUCCESS) {
                  isSave=financialYearService.saveFinancialYearAndBusinessDayTime(financialYearMap)
                  if(isSave){
                      message = financialYearMap.get(Message.MESSAGE)
                  }
                  else{
                      message = this.getMessage(financialYear, com.docu.common.Message.ERROR, "Financial Year save not Successful")
                  }
              }
              else{
                  message = financialYearMap.get(Message.MESSAGE)
              }


          }
          else{
              message = this.getMessage(financialYear, com.docu.common.Message.ERROR, "Financial Year Build Was Not Successful")
          }
          return message;
      } catch (Exception ex) {
          log.error(ex.message)
          message = this.getMessage("Financial Year", Message.ERROR, "Exception-${ex.message}")
          return message;
      }
  }

  public Object postCondition(Object params,Object object) {
    return null
  }
}