package com.docu.security.authority.action

import org.springframework.stereotype.Component
import com.docu.commons.IAction
import org.springframework.beans.factory.annotation.Autowired
import com.docu.security.UserAuthorityService
import com.docu.security.UserAuthority
import com.docu.commons.UserMessageBuilder
import com.docu.commons.Message
import com.docu.security.Requestmap
import com.docu.commons.CommonConstants
import com.docu.app.AuthorityDashboardMapping

/**
 * Created by IntelliJ IDEA.
 * User: bipul.kumar
 * Date: 6/12/11
 * Time: 5:46 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("createAuthorityAction")
class CreateAuthorityAction implements IAction {
    @Autowired
    UserAuthorityService userAuthorityService
    private static String officeDashboard = "/officeDashboard/**"
    private static String myDashboard = "/myDashboard/**"

    Object preCondition(def params) {
        Map mapInstance = [:]
        UserAuthority authority = null

        authority = UserAuthority.findByRole(params.role)

        if (!authority) {
            authority = new UserAuthority()
            authority.role = params.role
            String role = params.role
            role = role.toUpperCase()
            role = role.replaceAll("ROLE","")
            role = role.replaceAll(" ", "_")
            authority.authority = "ROLE_" + role

            if (!authority.validate()) {
                mapInstance = UserMessageBuilder.createMessage(userAuthorityService.getUserMessage("Authority.title", null), Message.ERROR, authority)
                return mapInstance
            }

            if(UserAuthority.findByAuthorityIlike(authority.authority)){
                mapInstance = UserMessageBuilder.createMessage(userAuthorityService.getUserMessage("Authority.title", null), Message.ERROR, "Others Role Name Contains this Role")
                return mapInstance
            }

            List<UserAuthority> userAuthorityList = UserAuthority.list()
            for(int i = 0; i < userAuthorityList.size(); i++){
                if(authority.authority.contains(userAuthorityList[i].authority)){
                    mapInstance = UserMessageBuilder.createMessage(userAuthorityService.getUserMessage("Authority.title", null), Message.ERROR, "Part of Others Role Name Contains this Role")
                    return mapInstance
                }
            }

        }
        else {
            return UserMessageBuilder.createMessage(userAuthorityService.getUserMessage("Authority.title", null), Message.ERROR, userAuthorityService.getUserMessage("Authority with this role already exists.", null), authority)
        }

        mapInstance = UserMessageBuilder.createMessage(userAuthorityService.getUserMessage("Authority.title", null), Message.SUCCESS, null)
        mapInstance.put(UserAuthority.class.simpleName, authority)
        AuthorityDashboardMapping authorityDashboardMapping = null
        List<AuthorityDashboardMapping> authorityDashboardMappingList = AuthorityDashboardMapping.findAll("from AuthorityDashboardMapping as adm join adm.authority as adma where adma.role = '${authority.role}'")
        if (authorityDashboardMappingList.size() > 0) {
            authorityDashboardMapping = (AuthorityDashboardMapping) authorityDashboardMappingList[0][0]
        }
        if (!authorityDashboardMapping) {
            authorityDashboardMapping = new AuthorityDashboardMapping()
            authorityDashboardMapping.moduleId = Long.parseLong(params.moduleId)
            authorityDashboardMapping.dashboardUrl = params.dashboardUrl
            mapInstance.put(AuthorityDashboardMapping.class.simpleName, authorityDashboardMapping)
        }
        else {
            if (authorityDashboardMapping.moduleId != Long.parseLong(params.moduleId) || authorityDashboardMapping.dashboardUrl != params.dashboardUrl) {
                authorityDashboardMapping.moduleId = Long.parseLong(params.moduleId)
                authorityDashboardMapping.dashboardUrl = params.dashboardUrl
                mapInstance.put(AuthorityDashboardMapping.class.simpleName, authorityDashboardMapping)
            }
        }

        return mapInstance
    }

    Object postCondition(Object object) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    Object execute(Object params, Object object) {
        Map mapInstance = (Map) object
        UserAuthority authority = (UserAuthority) mapInstance.get(UserAuthority.class.simpleName)
        try {
            Requestmap map = Requestmap.findByUrl(officeDashboard)
            if (map == null) {
                mapInstance.put("officeDashboard", new Requestmap(url: officeDashboard, configAttribute: authority.authority, featureControllerMappingId: 0, moduleId: 0))
            }
            else {
                map.configAttribute += CommonConstants.COMMA + authority.authority
                mapInstance.put("officeDashboard", map)
            }

            map = Requestmap.findByUrl(myDashboard)
            if (map == null) {
                mapInstance.put("myDashboard", new Requestmap(url: myDashboard, configAttribute: authority.authority, featureControllerMappingId: 0, moduleId: 0))
            }
            else {
                map.configAttribute += CommonConstants.COMMA + authority.authority
                mapInstance.put("myDashboard", map)
            }
            userAuthorityService.save(mapInstance)
        } catch (Exception ex) {
            return UserMessageBuilder.createMessage(userAuthorityService.getUserMessage("Authority.title", null), Message.ERROR, ex)
        }
        return UserMessageBuilder.createMessage(userAuthorityService.getUserMessage("Authority.title", null), Message.SUCCESS, "Authority  has been created successfully")
    }
}
