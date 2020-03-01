package com.docu.app.action

import com.docu.app.MenuService
import com.docu.app.UserFavoriteLink
import com.docu.app.UserMenu
import com.docu.commons.*
import groovy.sql.GroovyRowResult
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("ajaxMenuAction")
class AjaxMenuAction implements IAction {

    public static final Log log = LogFactory.getLog(AjaxMenuAction.class)

    @Autowired
    MenuService menuService
    @Autowired
    SessionManagementService sessionManagementService

    public Object preCondition(def params) {
        Map map = [:]
        String url = params.url
        String[] sa = null
        if (params.isDeleted) {
            sa = url.split("[/]", 3);
        }
        else {
            sa = url.split("[/]", 5);
        }
        String menuUrl = sa[sa.length - 1];
        UserFavoriteLink userFavoriteLink = getLink(menuUrl)
        if (params.isDeleted && userFavoriteLink) {
            userFavoriteLink.domainStatusId = CommonConstants.DOMAIN_STATUS_INACTIVE
            map.put(UserFavoriteLink.class.simpleName, userFavoriteLink)
            return map
        }

        GroovyRowResult menuInformation = menuService.getLinkInformation(menuUrl, params.session)
        if (!menuInformation) {
            return UserMessageBuilder.createMessage(menuService.getUserMessage("User Favourite Link", null), Message.ERROR, menuService.getUserMessage("This link can not be added as favourite", null), userFavoriteLink)
        }

        if (userFavoriteLink == null) {
            userFavoriteLink = new UserFavoriteLink()
            userFavoriteLink.url = menuUrl
            userFavoriteLink.userId = sessionManagementService.user?.id
            userFavoriteLink.domainStatusId = CommonConstants.DOMAIN_STATUS_ACTIVE
            long moduleInfoId = sessionManagementService.getModuleInfoId()
            userFavoriteLink.moduleInfoId = moduleInfoId
            String controllerName = menuUrl.split('[/]').first()
            if (controllerName == CommonConstants.DEFAULT_CONTROLLER) {
                userFavoriteLink.quickAccessCode = "DB000"
                userFavoriteLink.linkTitle = "Dash Board"
            }
            else {
                userFavoriteLink.quickAccessCode = menuInformation.featureActionCode
                userFavoriteLink.linkTitle = menuInformation.featureActionName
                userFavoriteLink.imageCss = menuInformation.imageCss
            }
        }
        else if (userFavoriteLink.domainStatusId == CommonConstants.DOMAIN_STATUS_INACTIVE) {
            userFavoriteLink.domainStatusId = CommonConstants.DOMAIN_STATUS_ACTIVE
        }
        else {
            return UserMessageBuilder.createMessage(menuService.getUserMessage("User Favourite Link", null), Message.ERROR, menuService.getUserMessage("This link is already added as favourite.", null), userFavoriteLink)
        }
        map.put(UserFavoriteLink.class.simpleName, userFavoriteLink)
        return map
    }

    public Object postCondition(def object) {
        return null
    }

    public Object execute(def params, def object) {
        UserFavoriteLink userFavoriteLink = (UserFavoriteLink) object
        try {
            menuService.saveUserFavouriteLink(userFavoriteLink)
        }
        catch (Exception ex) {
            log.error(ex.message)
            Map mapInstance = UserMessageBuilder.createMessage(menuService.getUserMessage("User Favourite Link", null), Message.ERROR, userFavoriteLink, ex)
            return mapInstance
        }
        if (params.isDeleted) {
            return UserMessageBuilder.createMessage(menuService.getUserMessage("User Favourite Link", null), Message.SUCCESS,
                    menuService.getUserMessage("Link is deleted from favourite successfully", userFavoriteLink))
        } else {
            return UserMessageBuilder.createMessage(menuService.getUserMessage("User Favourite Link", null), Message.SUCCESS,
                    menuService.getUserMessage("Link is added as favourite successfully", userFavoriteLink))
        }

    }

    public List<GroovyRowResult> getMenuItems(Map params) {
        Long moduleInfoId = sessionManagementService.getModuleInfoId()
        List<String> roles = sessionManagementService.getUserRole().split(",").toList()
        List<GroovyRowResult> groovyRowResult = menuService.getMenuItems(moduleInfoId, roles)
//        groovyRowResult.each {row ->
//            if (row.moduleId == 2 || row.moduleId == 3) {
//                row.actionUrl = "myDashboard#/" + row.actionUrl
//            }
//        }

        List<Map> groupByMapList = [
                [groupByField: "menuGroupName", fields: ["moduleId", "moduleName", "menuGroupId", "menuGroupName"]],
                [groupByField: "featureInfoName", fields: ["featureInfoId", "featureInfoName"]]
        ]
        return ObjectUtil.groupGroovyRowResult(groovyRowResult, groupByMapList)
    }

    public List<UserMenu> getCurrentUserMenuList() {
        List<String> roleList = sessionManagementService.getUserRole().split(",").toList()
        return menuService.getUserMenuByRoleList(roleList)
    }
    public List<Long> getUserModuleIdList() {
        Set<Long> set = sessionManagementService.userMenuList.collectAll { it.moduleId } as Set
        return set.toList().sort { it }
    }


    public List<UserFavoriteLink> getUserFavouriteLink() {
        return menuService.getUserFavouriteLink()
    }

    public UserFavoriteLink getLink(String url) {
        return menuService.getLink(url)
    }

}