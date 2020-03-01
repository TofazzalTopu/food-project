package com.bits.bdfp.security

import com.docu.commons.CommonConstants
import com.docu.commons.IAction
import com.docu.commons.Message
import com.docu.commons.SessionManagementService
import com.docu.commons.UserMessageBuilder
import com.docu.security.ApplicationUser
import com.docu.security.ApplicationUserService
import com.docu.security.SecurityAnswer
import com.docu.security.SecurityQuestion
import com.docu.security.UserFirstLoginPasswordVerification
import com.docu.security.setupoption.AjaxSetupOptionAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by prianka.adhikary on 7/30/2015.
 */
@Component("saveChangedPasswordAction")
class SaveChangedPasswordAction implements IAction{
    public static final Log log = LogFactory.getLog(SaveChangedPasswordAction.class)
    @Autowired
    ApplicationUserService applicationUserService
    @Autowired
    SessionManagementService sessionManagementService

    @Autowired
    AjaxSetupOptionAction  ajaxSetupOptionAction
    public Object preCondition(def params){
        Map mapInstance=[:]
        try{
            String errorMessage=''
            List<SecurityAnswer> securityAnswerList=[]
            List questionList=[]
            Boolean isError=false
            ApplicationUser applicationUser = ApplicationUser.read(Long.parseLong(params.userId.toString()))
            applicationUser.password = applicationUserService.encodePassword(params.password)
            applicationUser.confirmPassword = params.confirmPassword
            applicationUser.dateCreated=new Date()
            applicationUser.lastUpdated=new Date()
            if (!params.password.equals(params.confirmPassword)) {
                mapInstance = (Map) UserMessageBuilder.createMessage("Change Password", Message.ERROR, "Confirm Password does not match with password")

            }
            if(!ajaxSetupOptionAction.isPasswordPatternMatched(params.password)){
                mapInstance = (Map) UserMessageBuilder.createMessage("Change Password", Message.ERROR, CommonConstants.PASSWORD_VALIDATION_MESSAGE)

            }

            UserFirstLoginPasswordVerification userFirstLoginPasswordVerification=UserFirstLoginPasswordVerification.read(Long.parseLong(params.verifyId.toString()))
            userFirstLoginPasswordVerification.applicationUser=applicationUser
            userFirstLoginPasswordVerification.dateVerification=new Date()
            userFirstLoginPasswordVerification.isVerified=true


            if(userFirstLoginPasswordVerification.validate()){
                SecurityAnswer securityAnswer
                params?.securityQuestion?.each { key, val ->

                    SecurityQuestion securityQuestion=SecurityQuestion.read(Long.parseLong(val.toString()))

                    securityAnswer = new SecurityAnswer()
                    securityAnswer.securityQuestion=securityQuestion
                    params?.answer?.each{ ansKey,ansVal->
                        if (key==ansKey){
                            securityAnswer.answer=ansVal
                        }

                    }

                    securityAnswer.applicationUser=applicationUser
                    securityAnswer.createDate=new Date()
                    if(securityAnswer.validate()) {
                        if(!questionList.contains(securityAnswer.securityQuestionId)){
                            questionList.add(securityAnswer.securityQuestionId)
                            securityAnswerList.add(securityAnswer)
                        }
                        else{
                            isError=true
                            mapInstance = (Map) UserMessageBuilder.createMessage('Change Password', Message.ERROR,"Duplicate Question.Please ans for different questions")

                        }
                    }
                    else{
                        mapInstance = (Map) UserMessageBuilder.createMessage('Change Password', Message.ERROR,"Not Saved")
                    }
                }


//                mapInstance.put(UserFirstLoginPasswordVerification.class.simpleName, userFirstLoginPasswordVerification)

            }
            else{
                mapInstance = (Map) UserMessageBuilder.createMessage('Change Password', Message.ERROR,"Not Saved")
            }
            if(userFirstLoginPasswordVerification && securityAnswerList?.size()>0 && !isError){
                mapInstance = (Map) UserMessageBuilder.createMessage('Change Password', Message.SUCCESS,null)
                mapInstance.put("userFirstLoginPasswordVerification",userFirstLoginPasswordVerification)
                mapInstance.put("securityAnswerList",securityAnswerList)
                mapInstance.put("applicationUser",applicationUser)
            }
            return mapInstance
        }
        catch (Exception ex){
            log.error(ex.message)
            return UserMessageBuilder.createMessage('Change Password', Message.ERROR, "Unexpected Error Occured")
        }

    }
    Object postCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }
    public Object execute(Object params, Object object) {
        try {
            applicationUserService.saveChangedPassword(object)
        }
        catch (Exception ex) {
            log.error("SaveChangedPasswordAction::execute::" + ex.message)
            return UserMessageBuilder.createMessage('Change Password', Message.ERROR, "not saved")
        }
        return UserMessageBuilder.createMessage('Change Password', Message.SUCCESS, "Successfully saved")

    }



}
