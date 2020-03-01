package com.docu.app

import com.docu.commons.CommonConstants
import com.docu.commons.InternationalizationService
import com.docu.commons.SessionManagementService
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import org.springframework.transaction.annotation.Transactional

import javax.servlet.http.HttpSession

class MenuService extends InternationalizationService {

    static transactional = false
    TransactionAwareDataSourceProxy dataSource
    SessionManagementService sessionManagementService

    public List<GroovyRowResult> getMenuItems(Long moduleInfoId, List<String> roles){
        String query = """
        SELECT distinct
            m.id AS "moduleId",
            m.module_name AS "moduleName",
            mi.menu_group_id as "menuGroupId",
            mg.title as "menuGroupName",
            fi.id AS "featureInfoId",
            fi.feature_name AS "featureInfoName",
            fa.id AS "featureActionId",
            fa.action_name AS "featureActionName",
            fa.action_code AS "featureActionCode",
            fa.menu_url AS "actionUrl",
            fa.image_css AS "imageCss"
        FROM
            menu_map AS mm
            LEFT JOIN feature_action AS fa ON (fa.id = mm.feature_action_id)
            LEFT JOIN feature_info AS fi ON (fi.id = fa.feature_info_id)
            LEFT JOIN authority AS a ON (a.authority = mm.role_name)
            LEFT JOIN menu_item AS mi ON (mi.feature_info_id = fi.id)
            LEFT JOIN menu_group AS mg ON (mg.id = mi.menu_group_id)
            LEFT JOIN module_info AS m ON (m.id = mg.module_info_id)
            WHERE
            mm.module_id = ${moduleInfoId} AND
            a.role IN (${roles.collect {"'${it}'"}.join(",")}) AND
            fa.menu_url != 'null'
           -- GROUP BY fa.id
        ORDER BY
            mg.position,
            mi.position,
            fi.position,
            fa.position
      """

        Sql sql = new Sql(dataSource)
        return sql.rows(query)
    }

    public List<UserMenu> getUserMenuByRoleList(List<String> roleList){
        String query = """
            SELECT distinct
              fi.module_info_id AS "moduleId",
              mi.menu_group_id AS "menuGroupId",
              fi.id AS "featureInfoId",
              fa.id AS "featureActionId",
              mg.position,
              mi.position,
              fi.position,
              fa.position
            FROM menu_map AS mm
            INNER JOIN feature_action AS fa ON (fa.id = mm.feature_action_id)
            INNER JOIN feature_info AS fi ON (fi.id = fa.feature_info_id)
            INNER JOIN menu_item AS mi ON (mi.feature_info_id = fi.id)
            INNER JOIN menu_group AS mg ON (mg.id = mi.menu_group_id)
            WHERE mm.role_name IN (${roleList.collect {"'ROLE_${it}'"}.join(",")})
              AND (fa.menu_url != 'null' OR fa.menu_url IS NULL)
            -- GROUP BY fa.id
            ORDER BY fi.module_info_id, mg.position, mi.position, fi.position, fa.position
      """

        Sql sql = new Sql(dataSource)
        List<GroovyRowResult> resultList = sql.rows(query)
        List<UserMenu> menuList = []
        UserMenu userMenu = null
        resultList.each {
            userMenu = new UserMenu(moduleId: it.moduleId, menuGroupId: it.menuGroupId, featureInfoId: it.featureInfoId,
                    featureActionId: it.featureActionId)
            menuList.add(userMenu)
        }
        resultList = null
        return menuList
    }

    @Transactional
    def saveUserFavouriteLink(UserFavoriteLink userFavoriteLink) {
        try {
            userFavoriteLink.save()
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(ex.message))
        }
        return true
    }

    public GroovyRowResult getLinkInformation(String url, HttpSession session) {
        GroovyRowResult result = null
        List<GroovyRowResult> menuGroupList = (List<GroovyRowResult>) session.getAttribute("menuGroovyRowResult")
        for (int i = 0; i < menuGroupList.size(); i++) {
            List<GroovyRowResult> menuItemList = (List<GroovyRowResult>) menuGroupList[i].get("children")
            if (menuItemList.size() > 1) {
                for (int j = 0; j < menuItemList.size(); j++) {
                    List<GroovyRowResult> menuFeatureList = (List<GroovyRowResult>) menuItemList[j].children
                    result = menuFeatureList.find {it.actionUrl == url && it.moduleId == sessionManagementService.getModuleInfoId()}
                    if (result != null) {
                        break
                    }
                }
            }
            else {
                result = (GroovyRowResult) menuItemList.first().children.find {it.actionUrl == url && it.moduleId == sessionManagementService.getModuleInfoId()}
            }
            if (result != null) {
                break
            }
        }
        return result
    }

    public List<UserFavoriteLink> getUserFavouriteLink() {
        List<UserFavoriteLink> userFavoriteLinkList = UserFavoriteLink.findAll("from UserFavoriteLink where userId = '${sessionManagementService.user?.id}' and domainStatusId = ${CommonConstants.DOMAIN_STATUS_ACTIVE}")
        return userFavoriteLinkList
    }

    public UserFavoriteLink getLink(String url) {
        UserFavoriteLink link = UserFavoriteLink.find("from UserFavoriteLink where userId = '${sessionManagementService.user?.id}' and moduleInfoId = ${sessionManagementService.getModuleInfoId()} and domainStatusId = ${CommonConstants.DOMAIN_STATUS_ACTIVE} and url = '${url}'")
        return link
    }
}
