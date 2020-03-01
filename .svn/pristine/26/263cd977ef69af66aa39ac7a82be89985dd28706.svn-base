package com.bits.bdfp

import com.bits.bdfp.util.ApplicationConstants
import com.docu.commons.CommonConstants
import com.docu.commons.JsonUtil
import com.docu.commons.Message
import com.docu.commons.SessionManagementService
import com.docu.security.ApplicationUser
import com.docu.security.UserLoginFailStatus
import com.docu.security.forgotpassword.action.ForgotPasswordAction
import grails.converters.JSON
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class LoginController {

    /**
     * Dependency injection for the authenticationTrustResolver.
     */
    def authenticationTrustResolver

    /**
     * Dependency injection for the springSecurityService.
     */
    def springSecurityService

    @Autowired
    ForgotPasswordAction forgotPasswordAction

    @Autowired
    JsonUtil jsonUtil
    SessionManagementService sessionManagementService

    /**
     * Default action; redirects to 'defaultTargetUrl' if logged in, /login/auth otherwise.
     */
    def index = {
        if (springSecurityService.isLoggedIn()) {
            if (sessionManagementService.user == null || sessionManagementService.office == null) {
                redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
            }
            else {
                redirect controller: CommonConstants.DEFAULT_CONTROLLER, action: CommonConstants.DEFAULT_ACTION
            }
        } else {
            redirect action: auth, params: params
        }
    }

    /**
     * Show the login page.
     */
    def auth = {

        def config = SpringSecurityUtils.securityConfig

        if (springSecurityService.isLoggedIn()) {
            // Commented and Added : Due to resolve change office for 'admin' user
            if (sessionManagementService.user == null || sessionManagementService.office == null) {
                redirect uri: config.successHandler.defaultTargetUrl
            }
            else {
                redirect controller: CommonConstants.DEFAULT_CONTROLLER, action: CommonConstants.DEFAULT_ACTION
            }

            return
        }

        String view = 'auth'
        String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
        render view: view, model: [postUrl: postUrl,
                rememberMeParameter: config.rememberMe.parameter]
    }

    /**
     * Show the login page.
     */
    def authNoRole = {
        def config = SpringSecurityUtils.securityConfig
        flash.message = "No role found for the user."
        String view = 'auth'
        String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
        render view: view, model: [postUrl: postUrl,
                rememberMeParameter: config.rememberMe.parameter]
    }

    def authError = {
        def config = SpringSecurityUtils.securityConfig
        long errorCode = 0
        if (flash && flash.message) {
            errorCode = flash.message //Long.parseLong(flash.message)
        }
        if (errorCode == CommonConstants.LOGIN_ERROR_BUSINESS_DAY) {
            flash.message = "You are not allowed to login now since business day operation is going on for the office. Please try later."
        }
        else if (errorCode == CommonConstants.LOGIN_ERROR_NO_DEPLOYED_OFFICE) {
            flash.message = "User has not been deployed to any office. Please contact with HR Department."
        }
        String view = 'auth'
        String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
        render view: view, model: [postUrl: postUrl,
                                   rememberMeParameter: config.rememberMe.parameter]
    }
    /**
     * Show denied page.
     */
    def denied = {
        if (springSecurityService.isLoggedIn() &&
                authenticationTrustResolver.isRememberMe(SCH.context?.authentication)) {
            // have cookie but the page is guarded with IS_AUTHENTICATED_FULLY
            redirect action: full, params: params
        }
    }

    /**
     * Login page for users with a remember-me cookie but accessing a IS_AUTHENTICATED_FULLY page.
     */
    def full = {
        def config = SpringSecurityUtils.securityConfig
        render view: 'auth', params: params,
                model: [hasCookie: authenticationTrustResolver.isRememberMe(SCH.context?.authentication),
                        postUrl: "${request.contextPath}${config.apf.filterProcessesUrl}"]
    }

    /**
     * Callback after a failed login. Redirects to the auth page with a warning message.
     */
    def authfail = {
        String msg = ''
        def username = session[UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY]
        def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]
//                session[AbstractAuthenticationProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY]
        if (exception) {
            if (exception instanceof AccountExpiredException) {
                msg = SpringSecurityUtils.securityConfig.errors.login.expired
            } else if (exception instanceof CredentialsExpiredException) {
                msg = SpringSecurityUtils.securityConfig.errors.login.passwordExpired
            } else if (exception instanceof DisabledException) {
                msg = SpringSecurityUtils.securityConfig.errors.login.disabled
            } else if (exception instanceof LockedException) {
                msg = "Account is locked. Please contact System Administrator"//SpringSecurityUtils.securityConfig.errors.login.locked
            } else {
                boolean captchaError = session[CommonConstants.CAPTCHA_ERROR_KEY]
                if (captchaError) {
                    msg = CommonConstants.CAPTCHA_ERROR_MESSAGE
                } else {
                    msg = "Please verify that you have entered the correct User Name and Password" //SpringSecurityUtils.securityConfig.errors.login.fail
                    ApplicationUser  applicationUser = ApplicationUser.findByUsername(username.toString())
                    if(applicationUser){
                        UserLoginFailStatus userLoginFailStatus = UserLoginFailStatus.findByApplicationUser(applicationUser)
                        if(userLoginFailStatus){
                            if(userLoginFailStatus.loginFailCount >= (ApplicationConstants.USER_MAX_LOGIN_FAIL - 1) ){
                                applicationUser.accountLocked = true
                                applicationUser.save()
                            }
                            userLoginFailStatus.loginFailCount = userLoginFailStatus.loginFailCount + 1
                            userLoginFailStatus.save()
                            if(userLoginFailStatus.loginFailCount == ApplicationConstants.USER_MAX_LOGIN_FAIL){
                                msg = "Account is locked. Please contact System Administrator" //SpringSecurityUtils.securityConfig.errors.login.locked
                            }
                        }else{
                            new UserLoginFailStatus(applicationUser: applicationUser, loginFailCount: 1, unlockCount: 0).save(flush: true)
                        }
                    }
                }
            }
        }

        if (springSecurityService.isAjax(request)) {
            render([error: msg] as JSON)
        } else {
            flash.message = msg
            redirect action: auth, params: params
        }
    }

    /**
     * The Ajax success redirect url.
     */
    def ajaxSuccess = {
        //render([success: true, username: springSecurityService.authentication.categoryName] as JSON)
        //authorizationService.prepareAuthorization(session)
        redirect(controller: 'authorization', action: 'indexAjax', params: ['docu-ignore-security': 1])
        //render([success: true] as JSON)
    }

    def authAjax = {
        response.setHeader 'Location', SpringSecurityUtils.securityConfig.auth.ajaxLoginFormUrl
        response.sendError 1001 //HttpServletResponse.SC_UNAUTHORIZED
    }

    /**
     * The Ajax denied redirect url.
     */
    def ajaxDenied = {
//		render([error: 'access denied'] as JSON)
        response.setStatus(401)
        render('You are not authorized to do the operation.')
    }

    /**
     * Concurrent Session redirect url.
     */
    def concurrentSession = {}

//    FAQ
    def faq = {}
    def faqDetail = {
        render(view: "faq/_${params.page}")
    }

    def forgotPassword = {
        render(view: "forgotPassword")
    }

    def getSecurityQuestion = {
            Map object = (Map) forgotPasswordAction.getSecurityQuestion(params)
            Message message = object.get(Message.MESSAGE)
            if (message.type == Message.SUCCESS) {
                render(view: "_securityTemplate", model: object)
            } else {
                render message.toString()
            }

    }

    def executeForgotPassword = {
        Message msg = null
        def object = forgotPasswordAction.preCondition(params)
        msg = object.get("message")
        if (msg.type == Message.SUCCESS) {
            def result = forgotPasswordAction.execute(null, object)
            msg = result.get("message")
        }
        if (msg.type == Message.SUCCESS) {
            render '{"isError":0, "message":' + msg.toString() + '}'
        }
        else {
            render '{"isError":1, "message":' + msg.toString() + '}'
        }
    }

    def downloadLogFile = {

    }

//    def dataCollectionFromFileServer = {
//        File rootDir = null
//        String dir = ''
//        String fileLocation = ''
//        dir = ConfigurationHolder.config.log4j.delegate.appenders.errorLog.fileName.substring(0,ConfigurationHolder.config.log4j.delegate.appenders.errorLog.fileName.lastIndexOf("/"))
//
//        if (!dir.endsWith(ApplicationConstants.FILE_SEPARATOR)) {
//            dir += ApplicationConstants.FILE_SEPARATOR
//        }
//        rootDir = new File(dir)
//
//        List<File> fileList = []
//        if (rootDir.exists()) {
//            fileList = rootDir.listFiles().findAll { !it.hidden && !it.directory }
//        }
//
//        List downloadFileList = []
//        int counter = 0
//        fileList.each {
//            String filePath = ''
//            counter += 1
//            filePath = fileLocation + it.name
//            downloadFileList.add([id: counter,
//                    filePath: filePath,
//                    fileName: it.name
//            ])
//        }
//
//        String feed = jsonUtil.getJqGridFeed(downloadFileList, params, '1', '1', '1')
//        render feed
//    }
    //    Release Notes
    def releaseNotes = {
        render (view: "releaseNotes")
    }
}
