package com.bits.bdfp.common

import com.docu.commons.SessionManagementService
import com.docu.commons.annotation.ControllerAnnotationHelper
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.web.mapping.UrlMappingInfo
import org.codehaus.groovy.grails.web.mapping.UrlMappingsHolder
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by feroz on 9/18/2014.
 */
class RequestLoggingFilter extends GenericFilterBean implements ApplicationEventPublisherAware {
    SessionManagementService sessionManagementService
    UrlMappingsHolder grailsUrlMappingsHolder
    ControllerAnnotationHelper controllerAnnotationHelper
    String controllerName = null
    String actionName = null

    void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req
        HttpServletResponse response = (HttpServletResponse) res

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UrlMappingInfo info = grailsUrlMappingsHolder.match((request.requestURI - request.contextPath))
            if (info && info.params) {
                controllerName = info.params.controller
                actionName = info.params.action
                if (controllerName != null && controllerName != "login" && controllerName != "home" && !SpringSecurityUtils.isAjax(request) && request.method.toLowerCase() == "get" && controllerAnnotationHelper.requiresAuthentication(controllerName, actionName)) {
                    sessionManagementService.setRequestedUrl(new String(request.getRequestURI() + (request.getQueryString() ? "?" + request.getQueryString() : "")))
                }
            }
        }

        chain.doFilter(request, response)
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
    }
}
