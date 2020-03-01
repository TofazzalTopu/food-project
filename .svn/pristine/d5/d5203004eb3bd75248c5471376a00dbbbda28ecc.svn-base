package com.docu.security

import com.docu.commons.InternationalizationService
import com.docu.plugin.cxfws.CommonHelperWebService
import com.docu.security.util.AuthorityDashboardMappingUtil
import com.docu.security.util.AuthorityUtil
import org.springframework.transaction.annotation.Transactional
import groovy.sql.Sql
import com.docu.commons.CommonConstants
import com.docu.commons.SessionManagementService
import com.docu.app.AuthorityDashboardMapping
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import org.springframework.beans.factory.annotation.Autowired
import com.docu.commons.JsonUtil

class UserAuthorityService extends InternationalizationService {
    static transactional = false
    SessionManagementService sessionManagementService
    TransactionAwareDataSourceProxy dataSource
    CommonHelperWebService commonHelperWebService
    @Autowired
    JsonUtil jsonUtil

    @Transactional(readOnly = true)
    public UserAuthority read(Map params) {
        return UserAuthority.read(params.id)
    }

    @Transactional(readOnly = true)
    public Map list(Map params) {
        List<UserAuthority> authorityList = []
        long authorityCount = 0
        try {
            authorityList = UserAuthority.withCriteria {
                maxResults(Integer.parseInt(params.resultPerPage.toString()))
                firstResult(Integer.parseInt(params.start.toString()))
                order(params.sortCol.toString(), params.sortOrder.toString())
            }
            authorityCount = UserAuthority.count()
        }
        catch (Exception ex) {
            log.error(ex.message)
        }
        return [authorityList: authorityList, authorityCount: authorityCount]
    }

    @Transactional
    public boolean save(Map object) {
        try {
            boolean isUpdate = true
            UserAuthority authority = (UserAuthority) object.get(UserAuthority.class.simpleName)
            Requestmap officeDashboard = (Requestmap) object.get('officeDashboard')
            Requestmap myDashboard = (Requestmap) object.get('myDashboard')
            Requestmap appSecuritySettings = (Requestmap) object.get('appSecuritySettings')
            AuthorityDashboardMapping authorityDashboardMapping = (AuthorityDashboardMapping) object.get(AuthorityDashboardMapping.class.simpleName)
            if (authority.id == null || authority.id == "") {
                isUpdate = false
            }
            authority.save()
            commonHelperWebService.callWebMethod(authority,'',isUpdate)
            if (officeDashboard) {
                officeDashboard.save()
            }
            if (appSecuritySettings){
                appSecuritySettings.save()
            }
            if (myDashboard){
                myDashboard.save()
            }

            isUpdate = true
            if (authorityDashboardMapping) {
                isUpdate = false
                authorityDashboardMapping.authority = authority
                authorityDashboardMapping.save()
                commonHelperWebService.callWebMethod(authorityDashboardMapping,'',isUpdate)
            }

            //
            AuthorityUtil.instance.resolve()
            AuthorityDashboardMappingUtil.instance.resolve()

        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional
    public boolean update(UserAuthority authority) {
        try {
            authority.save()
            //
            AuthorityUtil.instance.resolve()
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional
    public delete(UserAuthority authority) {
        try {
            AuthorityDashboardMapping.executeUpdate("delete from AuthorityDashboardMapping adm where adm.authority=?", [authority])
            authority.delete()
            //
            AuthorityUtil.instance.resolve()
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional(readOnly = true)
    public UserAuthority search(String fieldName, String fieldValue) {
        String query = "from UserAuthority as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return UserAuthority.find(query)
    }

    @Transactional(readOnly = true)
    public String listWithDashboardMapping(Map params) {
        String feed = ""
        try {
            String strSql = """
                SELECT
                    aa.id AS id,
                    aa.authority AS authority,
                    aa.role AS role,
                    adm.id AS "authorityDashboardId",
                    adm.module_id AS "moduleId",
                    mi.module_name AS "moduleName",
                    adm.dashboard_url AS "dashboardUrl"
                FROM user_authority AS aa
                    LEFT JOIN authority_dashboard_mapping AS adm
                    LEFT JOIN module_info AS mi ON mi.id = adm.module_id
                        ON adm.authority_id = aa.id
                """
            feed = jsonUtil.getJqGridFeed(strSql, params)
        }
        catch (Exception ex) {
            log.error(ex.message)
        }
       return feed
    }

    @Transactional(readOnly = true)
    public Map readAuthorityWithAuthorityDashboardMapping(Map params) {
        Map map = [:]
        try {
            String strSql = """
                SELECT
                    aa.id AS id,
                    aa.authority AS authority,
                    aa.role AS role,
                    adm.id AS "authorityDashboardId",
                    adm.module_id AS "moduleId",
                    mi.module_name AS "moduleName",
                    adm.dashboard_url AS "dashboardUrl"
                FROM user_authority AS aa
                    LEFT JOIN authority_dashboard_mapping AS adm
                    LEFT JOIN module_info AS mi ON mi.id = adm.module_id
                        ON adm.authority_id = aa.id
                WHERE aa.id = '${params.id}'
                """
            Sql sql = new Sql(dataSource)
            map.put("authority", sql.rows(strSql).first())
        }
        catch (Exception ex) {
            log.error(ex.message)
        }
        return map
    }
}
