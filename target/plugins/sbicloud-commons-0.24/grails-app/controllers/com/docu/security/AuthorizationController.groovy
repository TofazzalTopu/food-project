/*
 * ****************************************************************
 * Copyright � 2010 Documentatm (TM) Limited. All rights reserved.
 * DOCUMENTA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * This software is the confidential and proprietary information of
 * Documentatm (TM) Limited ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Documentatm (TM) Limited.
 * *****************************************************************
 */

package com.docu.security

import com.docu.commons.SessionManagementService
import com.docu.commons.CommonConstants
import grails.converters.JSON

class AuthorizationController {
    def springSecurityService
    SessionManagementService sessionManagementService

    def index = {
        def auth = springSecurityService.principal
        if (isValidApplicationUserWithAuthorization(auth)) {
            redirect(controller: 'officeDashboard')
        }
        else {
            redirect(controller: "login", action: "authNoRole")
        }
    }

    def indexAjax = {
        def auth = springSecurityService.principal
        if (isValidApplicationUserWithAuthorization(auth)) {
            redirect(controller: 'officeDashboard', action: 'indexAjax')
        }
        else {
            Map map = [hasError:true, message:"Unauthorised user"]
            render map as JSON
        }
    }

    private boolean isValidApplicationUserWithAuthorization(def auth) {
        Set authorities = auth.authorities // a Collection of GrantedAuthority
        Object noAuthority = authorities.find { it.role == "ROLE_NO_ROLES"}
        if (noAuthority == null) {
            ApplicationUser user = ApplicationUser.findByUsername(auth.username)
            if (user) {
                sessionManagementService.setUser(user)
                sessionManagementService.setUserRole(authorities.collectAll { it.role.substring(5) }.join(","))
            }
            return true
        }
        return false
    }
}