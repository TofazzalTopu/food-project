package com.docu.accesscontrol

import groovy.sql.Sql
import groovy.sql.GroovyRowResult
import com.docu.accesscontrol.menu.UserMenu
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy

class UserMenuService {

    static transactional = false
    TransactionAwareDataSourceProxy dataSource


    public List<UserMenu> getUserMenuByRoles(Long moduleInfoId, List<String> roles){
        String query = """
        SELECT
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
            mg.id IS NOT NULL
            GROUP BY fa.id
        ORDER BY
            mg.position,
            mi.position,
            fi.position,
            fa.position
      """

        Sql sql = new Sql(dataSource)
        List<GroovyRowResult> resultList = sql.rows(query)
        List<UserMenu> menuList = []
        UserMenu userMenu = null
        resultList.each {
            userMenu = new UserMenu(moduleId: it.moduleId, menuGroupId: it.menuGroupId, menuGroupName: it.menuGroupName,
                    featureInfoId: it.featureInfoId, featureInfoName: it.featureInfoName, featureActionId: it.featureActionId,
                    qCode: it.qCode, featureActionName: it.featureActionName, imageCss: it.imageCss)
            menuList.add(userMenu)
        }
        resultList = []
        return menuList
    }

    public List<Long> getUserModuleIdList(List<String> roles){
        List<Long> moduleIdList = []
        String query = """
        SELECT
            DISTINCT m.id AS "moduleId"
        FROM
            menu_map AS mm
            LEFT JOIN feature_action AS fa ON (fa.id = mm.feature_action_id)
            LEFT JOIN feature_info AS fi ON (fi.id = fa.feature_info_id)
            LEFT JOIN authority AS a ON (a.authority = mm.role_name)
            LEFT JOIN menu_item AS mi ON (mi.feature_info_id = fi.id)
            LEFT JOIN menu_group AS mg ON (mg.id = mi.menu_group_id)
            LEFT JOIN module_info AS m ON (m.id = mg.module_info_id)
            WHERE
            a.role IN (${roles.collect {"'${it}'"}.join(",")}) AND
            mg.id IS NOT NULL
            ORDER BY m.id
      """

        Sql sql = new Sql(dataSource)
        List<GroovyRowResult> resultList = sql.rows(query)
        List<UserMenu> menuList = []
        UserMenu userMenu = null
        resultList.each {
            moduleIdList.add(it.moduleId)
        }
        resultList = []
        return moduleIdList
    }
}
