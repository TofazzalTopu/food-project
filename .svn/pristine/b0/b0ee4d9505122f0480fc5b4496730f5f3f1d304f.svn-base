package com.bits.bdfp

import com.docu.commons.SessionManagementService
import com.docu.security.LoginHistoryService
import com.docu.util.SessionManager
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class LogoutController {

    SessionManagementService sessionManagementService
    LoginHistoryService loginHistoryService
	/**
	 * Index action. Redirects to the Spring security logout uri.
	 */
	def index = {
        loginHistoryService.updateLoginHistory()
        if (sessionManagementService.user) {
            SessionManager.instance.removeSession(sessionManagementService.user.username)
        }
		redirect uri: SpringSecurityUtils.securityConfig.logout.filterProcessesUrl // '/j_spring_security_logout'
	}
}
