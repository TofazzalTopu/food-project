package com.bits.bdfp

import com.bits.bdfp.common.MenuGenerator
//import com.bits.bdfp.common.NavigationHistoryAction
import com.bits.bdfp.security.UpdateUserDashboardInfoAction
import com.docu.app.UserFavoriteLink
import com.docu.app.action.AjaxMenuAction
import com.docu.commons.Message
import com.docu.commons.SessionManagementService
import com.docu.security.ApplicationUser
import grails.converters.JSON
import grails.plugins.springsecurity.SpringSecurityService
import groovy.sql.GroovyRowResult
import org.springframework.beans.factory.annotation.Autowired

public class MenuController {
    SessionManagementService sessionManagementService
    @Autowired
    AjaxMenuAction ajaxMenuAction
    @Autowired
    MenuGenerator menuGenerator
    SpringSecurityService   springSecurityService
//    @Autowired
//    NavigationHistoryAction navigationHistoryAction
    @Autowired
    UpdateUserDashboardInfoAction updateUserDashboardInfoAction

    def setLeftMenu = {
        Long moduleInfoId = Long.parseLong(params.moduleInfoId)
        sessionManagementService.setModuleInfoId(moduleInfoId)

        if (false) {
            List<GroovyRowResult> groovyRowResult = ajaxMenuAction.getMenuItems(params)
            session.setAttribute('menuGroovyRowResult', groovyRowResult)
            session.setAttribute('userFavouriteLink', ajaxMenuAction.getUserFavouriteLink())
        }
        else {
            sessionManagementService.setUserMenuList(ajaxMenuAction.getCurrentUserMenuList())
            sessionManagementService.setUserFavouriteLink(ajaxMenuAction.getUserFavouriteLink())
        }

        Map json = [isError: 0]
        render json as JSON
    }

    def setTheme = {
        Map map = updateUserDashboardInfoAction.saveUserPreferredTheme(params.currentTheme.toString())
        Message msg = map.get("message")
        render msg.toString()
    }

    def quickSearch = {
        String actionUrl = ""
        String imageCss = ""
        Map map = [:]
        GroovyRowResult result = null
        List<GroovyRowResult> menuGroupList = (List<GroovyRowResult>) session.getAttribute("menuGroovyRowResult")
        for (int i = 0; i < menuGroupList.size(); i++) {
            List<GroovyRowResult> menuItemList = (List<GroovyRowResult>) menuGroupList[i].get("children")
            if (menuItemList.size() > 1) {
                for (int j = 0; j < menuItemList.size(); j++) {
                    List<GroovyRowResult> menuFeatureList = (List<GroovyRowResult>) menuItemList[j].children
                    result = menuFeatureList.find {it.featureActionCode == params.quick_search && it.moduleId == sessionManagementService.getModuleInfoId()}
                    if (result != null) {
                        actionUrl = result.actionUrl
                        break
                    }
                }
            }
            else {
                result = (GroovyRowResult) menuItemList.first().children.find {it.featureActionCode == params.quick_search && it.moduleId == sessionManagementService.getModuleInfoId()}

            }
            if (result != null) {
                actionUrl = result.actionUrl
                break
            }
        }
        map.put("actionUrl", actionUrl)
        map.put("imageCss", imageCss)
        render map as JSON
    }

    def loadUserFavouriteLink = {
        List<UserFavoriteLink> userFavoriteLinkList = ajaxMenuAction.getUserFavouriteLink()
        render(template: "/layouts/favouriteLink", model: [urlMap: userFavoriteLinkList])
    }

    def addUserFavouriteLink = {
        params.session = session
        Map result = (Map) ajaxMenuAction.preCondition(params)
        if (result.containsKey(UserFavoriteLink.class.simpleName)) {
            UserFavoriteLink userFavoriteLink = result.get(UserFavoriteLink.class.simpleName)
            result = (Map) ajaxMenuAction.execute(params, userFavoriteLink)
        }
        Message message = result.get("message")
        if (message.type == Message.SUCCESS) {
            session.setAttribute('userFavouriteLink', ajaxMenuAction.getUserFavouriteLink())
            render(template: "/layouts/favouriteLink")
        } else {
            render(template: "/layouts/favouriteLink", model: [message: message])
        }

    }

    def deleteFavouriteLink = {
        params.session = session
        Map result = (Map) ajaxMenuAction.preCondition(params)
        if (result.containsKey(UserFavoriteLink.class.simpleName)) {
            UserFavoriteLink userFavoriteLink = result.get(UserFavoriteLink.class.simpleName)
            result = (Map) ajaxMenuAction.execute(params, userFavoriteLink)
        }
        Message message = result.get("message")
        if (message.type == Message.SUCCESS) {
            session.setAttribute('userFavouriteLink', ajaxMenuAction.getUserFavouriteLink())
            render(template: "/layouts/favouriteLink")
        } else {
            render(template: "/layouts/favouriteLink", model: [message: message])
        }
    }

    def navigation = {
        render(template: "/layouts/navigation", model: [menuGenerator: menuGenerator])
    }

    def leftMenu = {
        if (!springSecurityService.isLoggedIn()){
            redirect(controller: "login", action: "auth", params: params)
            return
        }
        def auth = springSecurityService.principal
        Set authorities = auth.authorities
        ApplicationUser applicationUser = ApplicationUser.read(auth.id)
        session.applicationUser = applicationUser

//        def result=navigationHistoryAction.execute(params,applicationUser)


        render(template: "/layouts/dynamicLeftMenu", model: [menuGenerator: menuGenerator, controllerName: params.controllerName, actionName: params.actionName])
    }

}