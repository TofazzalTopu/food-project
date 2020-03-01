package com.bits.bdfp

import com.bits.bdfp.audit.NavigationHistory
import com.docu.accesscontrol.ModuleInfo
import com.docu.app.DashboardPortlet
import com.docu.app.UserPortletPreference
import com.docu.app.UserPreference
import com.docu.commons.CommonConstants
import com.docu.commons.InternationalizationService
import com.docu.security.ApplicationUser
import com.docu.security.SecurityAnswer
import com.docu.security.UserFirstLoginPasswordVerification
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import org.springframework.transaction.annotation.Transactional

class MyDashboardService extends InternationalizationService {
    TransactionAwareDataSourceProxy dataSource
    static transactional = false

    public List getUserPortletList(String userId, long moduleInfoId) {
        List result = []
        String sql = """
                SELECT
                     dp.id AS id,
                     dp.auto AS isAuto,
                     dp.title AS title,
                     dp.url AS url,
                     upp.id AS userPortletPreferenceId,
                     upp.row_index AS rowIndex,
                     upp.col_index AS colIndex,
                     upp.domain_status_id AS statusId
                FROM dashboard_portlet AS dp
                INNER JOIN user_portlet_preference AS upp ON (upp.portlet_id = dp.id)
                INNER JOIN user_preference AS up ON(up.id = upp.user_preference_id)
                WHERE
                    up.user_id = '${userId}'
                    AND dp.module_info_id = ${moduleInfoId}
                    AND upp.domain_status_id = ${CommonConstants.DOMAIN_STATUS_ACTIVE}
              """
        def db = new Sql(dataSource)
        result = db.rows(sql)
        return result
    }

    @Transactional
    public boolean updateUserPortletPreference(List<UserPortletPreference> userPortletPreferenceList) {
        try {
            for (UserPortletPreference userPortletPreference: userPortletPreferenceList) {
                if (userPortletPreference.id == null) {
                    userPortletPreference.id = idGenerationService.generateId(userPortletPreference)
                }
                userPortletPreference.save()
            }
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(ex)
        }
        return true
    }

    public boolean populateUserPortletPreference(String userId) {
        UserPreference userPreference = null
        UserPortletPreference userPortletPreference = null
        try {
            userPreference = UserPreference.findByUserId(userId)
            if (!userPreference) {
                userPreference = new UserPreference()
                userPreference.userId = userId
                userPreference.colorTheme = CommonConstants.DEFAULT_CURRENT_THEME
                userPreference.id = idGenerationService.generateId(userPreference)
                userPreference.save()
            }

            List<ModuleInfo> moduleInfoList = ModuleInfo.list()
            moduleInfoList.each {
                List<DashboardPortlet> dashboardPortletList = DashboardPortlet.findAllByModuleInfoId(it.id)
                int i = 0
                int j = 0
                dashboardPortletList.each {
                    userPortletPreference = UserPortletPreference.findByPortletAndUserPreference(it, userPreference)
                    if (!userPortletPreference) {
                        userPortletPreference = new UserPortletPreference()
                        userPortletPreference.userPreference = userPreference
                        userPortletPreference.portlet = it
                        userPortletPreference.domainStatusId = CommonConstants.DOMAIN_STATUS_INACTIVE
                        userPortletPreference.id = idGenerationService.generateId(userPortletPreference)
                    }
                    userPortletPreference.rowIndex = j
                    userPortletPreference.colIndex = i
                    userPortletPreference.save()
                    i++
                    if (i % 3 == 0) {
                        i = 0
                        j++
                    }
                }
            }
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(ex)
        }
        return true
    }

    public GroovyRowResult getUserPortlet(String userId, long moduleInfoId, long dashboardPortletId) {
        String sql = """
                SELECT
                     dp.id AS id,
                     dp.auto AS isAuto,
                     dp.title AS title,
                     dp.url AS url,
                     upp.id AS userPortletPreferenceId,
                     upp.row_index AS rowIndex,
                     upp.col_index AS colIndex
                FROM dashboard_portlet AS dp
                INNER JOIN user_portlet_preference AS upp ON (upp.portlet_id = dp.id)
                INNER JOIN user_preference AS up ON(up.id = upp.user_preference_id)
                WHERE
                    up.user_id = '${userId}'
                    AND dp.module_info_id = ${moduleInfoId}
                    AND dp.id = ${dashboardPortletId}
              """
        def db = new Sql(dataSource)
        List result = db.rows(sql)
        return result[0]
    }

    public List getAllDashboardList(String userId, long moduleInfoId) {
        List result = []

        String sql = """
                SELECT
                     dp.id AS id,
                     dp.auto AS isAuto,
                     dp.title AS title,
                     dp.url AS url,
                     upp.id AS userPortletPreferenceId,
                     upp.row_index AS rowIndex,
                     upp.col_index AS colIndex,
                     upp.domain_status_id AS statusId
                FROM dashboard_portlet AS dp
                INNER JOIN user_portlet_preference AS upp ON (upp.portlet_id = dp.id)
                INNER JOIN user_preference AS up ON(up.id = upp.user_preference_id)
                WHERE
                    up.user_id = '${userId}'
                    AND dp.module_info_id = ${moduleInfoId}
              """
        def db = new Sql(dataSource)
        result = db.rows(sql)
        return result
    }

    @Transactional
    public boolean savePreferredTheme(UserPreference userPreference) {
        try {
            if (userPreference) {
                if (userPreference.id == null) {
                    userPreference.id = idGenerationService.generateId(userPreference)
                }
                userPreference.save()
            }
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(ex)
        }
        return true
    }

    @Transactional(readOnly = true)
    public UserPreference getPreferenceByUserId(String userId){
       return UserPreference.findByUserId(userId)
    }

    @Transactional(readOnly = true)
    public List<UserPortletPreference> getAllUserPortletPreference(UserPreference userPreference){
        return UserPortletPreference.withCriteria {eq('userPreference',userPreference)}
    }
    @Transactional(readOnly = true)
    public List getMostVisitedMenuList() {
        List result = []
        String sql = """
                SELECT COUNT(navigation_history.id) AS log_history,feature_action.id,feature_action.`action_name` FROM `navigation_history`
INNER JOIN `feature_action` ON navigation_history.`feature_action_id`=feature_action.`id`
GROUP BY feature_action.`action_name`
ORDER BY log_history DESC
LIMIT 5
              """
        def db = new Sql(dataSource)
        result = db.rows(sql)
        return result
    }

    @Transactional
    public boolean saveNaqvigation(Object object){
        try {
            NavigationHistory  navigationHistory=(NavigationHistory) object
            navigationHistory.save()

        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }
}
