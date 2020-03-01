package com.docu.security.authority.action

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.security.UserAuthority
import com.docu.security.UserAuthorityService
import com.docu.security.Requestmap
import com.docu.commons.CommonConstants
import com.docu.app.AuthorityDashboardMapping

@Component("updateAuthorityAction")
class UpdateAuthorityAction implements IAction {
    public static final Log log = LogFactory.getLog(UpdateAuthorityAction.class)
    private final String MESSAGE_HEADER = 'Update Authority'
    private final String MESSAGE_SUCCESS = 'Authority updated successfully'
    private static String officeDashboard = "/officeDashboard/**"
    private static String myDashboard = "/myDashboard/**"

    @Autowired
    UserAuthorityService userAuthorityService

    public Object preCondition(def params) {
        Map mapInstance = [:]
        UserAuthority authority = null
        try {
            authority = userAuthorityService.read(params)
            authority.properties = params

            UserAuthority duplicateAuthority = UserAuthority.findByRole(authority.role)
            if (duplicateAuthority.id != authority.id) {
                return UserMessageBuilder.createMessage(userAuthorityService.getUserMessage("Authority.title", null), Message.ERROR, userAuthorityService.getUserMessage("Authority with this role already exists.", null), authority)
            }

            if (!authority.validate()) {
                return UserMessageBuilder.createMessage(userAuthorityService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, userAuthorityService.getErrorMessage(authority), authority)
            }

            mapInstance = UserMessageBuilder.createMessage(userAuthorityService.getUserMessage("Authority.title", null), Message.SUCCESS, null)
            mapInstance.put(UserAuthority.class.simpleName, authority)

            AuthorityDashboardMapping authorityDashboardMapping = null
            List<AuthorityDashboardMapping> authorityDashboardMappingList = AuthorityDashboardMapping.findAll("from AuthorityDashboardMapping as adm join adm.authority as adma where adma.role = '${authority.role}'")
            if(authorityDashboardMappingList.size() > 0){
                authorityDashboardMapping = (AuthorityDashboardMapping)authorityDashboardMappingList[0][0]
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
        }
        catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(userAuthorityService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, authority, ex)
        }

        return mapInstance
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

        return UserMessageBuilder.createMessage(userAuthorityService.getUserMessage("Authority.title", null), Message.SUCCESS, "Authority  has been updated successfully")
    }

    public Object postCondition(def object) {
        return null
    }
}
