package com.docu.security.setupoption

import com.docu.commons.CommonConstants
import com.docu.security.SetupOptionService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by mdalinaser.khan on 7/28/15.
 */
@Component("ajaxSetupOptionAction")
class AjaxSetupOptionAction {

    public static final Log log = LogFactory.getLog(AjaxSetupOptionAction.class)
    @Autowired
    SetupOptionService  setupOptionService

    private Pattern pattern;
    private Matcher matcher;

    public boolean setPasswordPatternInfo(){
        return setupOptionService.setPasswordPatternInfo()
    }

    public boolean isPasswordPatternMatched(String password){
        if(CommonConstants.PASSWORD_PATTERN.length() == 0){
            setupOptionService.setPasswordPatternInfo()
        }
        String passwordPattern = CommonConstants.PASSWORD_PATTERN
        passwordPattern = passwordPattern.replace("/^","")
        passwordPattern = passwordPattern.replace("\$/","")
        pattern = Pattern.compile(passwordPattern);
        matcher = pattern.matcher(password);
        return matcher.matches()
    }
}

