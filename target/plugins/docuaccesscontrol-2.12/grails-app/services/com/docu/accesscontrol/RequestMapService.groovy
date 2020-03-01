package com.docu.accesscontrol

import grails.plugins.springsecurity.SpringSecurityService
import groovy.sql.Sql
import org.codehaus.groovy.grails.context.support.PluginAwareResourceBundleMessageSource
import org.hibernate.exception.ConstraintViolationException
import org.hibernate.exception.DataException
import org.hibernate.exception.JDBCConnectionException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import org.springframework.transaction.annotation.Transactional
import java.util.regex.Pattern
import java.util.regex.Matcher

class RequestMapService {
    private static final String FIELD_NAME_CONVERSION_PATTERN = "((?<=[^A-Z])(?=[A-Z][^A-Z]))";
    TransactionAwareDataSourceProxy dataSource
    static transactional = false
    PluginAwareResourceBundleMessageSource messageSource
    private static String COMMA = ","

    @Autowired
    PluginClassUtil pluginClassUtil
    SpringSecurityService   springSecurityService

    @Transactional(readOnly = true)
    public List showFeatureAndAction(Object params) {
        List result = []
        String permittedToShowPart = ''
        String appVersion = params.applicationVersion

        if (params.userName == 'sadmin') {
            permittedToShowPart = ' AND 1=1 '
        } else {
            permittedToShowPart = ' AND is_permitted_to_show = 1 '
        }
//        if (appVersion.startsWith('C')) {
//            permittedToShowPart = ' AND is_permitted_to_show = 1 '
//        } else {
//            if (params.userName == 'sadmin') {
//                permittedToShowPart = ' AND 1=1 '
//            } else {
//                permittedToShowPart = ' AND is_permitted_to_show = 1 '
//            }
//        }

        try {
            String sql = """
                  SELECT feature_action.id, feature_name AS "featureName",action_name AS "actionName" FROM feature_info,feature_action
                  WHERE  feature_info.id=feature_action.feature_info_id ${permittedToShowPart} AND feature_info.module_info_id=${params.moduleid}
                 """

            Sql db = new Sql(dataSource)
            result = db.rows(sql)
        } catch (Exception ex) {
            log.error(ex.message)
        }
        return result
    }


    @Transactional(readOnly = true)
    public List showOnlyFeatureName(Object params) {
        List result = []
        try {
            String sql = """
                  SELECT feature_name AS "featureName" FROM feature_info WHERE feature_info.module_info_id=?
                 """

            Sql db = new Sql(dataSource)
            result = db.rows(sql, [params.moduleid])

        } catch (Exception ex) {
            log.error(ex.message)
        }
        return result
    }


    @Transactional(readOnly = true)
    public List getControllerActionUrl(Object params, String featureAction, String featureName) {
        List result = []
        try {
            String sql = """
                  SELECT controller_action As  "controllerAction",feature_controller_mapping.id as "featureControllerMappingId" FROM feature_info,feature_action,feature_controller_mapping
                  WHERE feature_info.id=feature_action.feature_info_id AND feature_action.id=feature_controller_mapping.feature_action_id
                  AND feature_info.module_info_id=${params.moduleList} AND feature_action.action_name='${featureAction}'
                    AND feature_info.feature_name='${featureName}'"""

            Sql db = new Sql(dataSource)
            result = db.rows(sql)

        } catch (Exception ex) {
            log.error(ex.message)
        }
        return result
    }

    @Transactional(readOnly = true)
    public List getFeatureActionId(Object params, String featureAction, String featureName) {
        List result = []
        try {
            String sql = """
                 SELECT fa.id FROM feature_action fa INNER JOIN feature_info fi
                 ON fa.feature_info_id=fi.id
                 INNER JOIN module_info mi ON
                 fi.module_info_id=mi.id
                 WHERE fi.module_info_id=${params.moduleList} AND fa.action_name='${featureAction}' AND fi.feature_name='${featureName}'
                 """

            Sql db = new Sql(dataSource)
            result = db.rows(sql)

        } catch (Exception ex) {
            log.error(ex.message)
        }
        return result
    }

    @Transactional
    public boolean save(Object params, Map object) {
        try {
            //Requestmap requestmap
            Class clazz = pluginClassUtil.getRequestmapFromConfig()
            String tableName = getTableSpecificName(clazz.simpleName)
            UserRequestMap userRequestMap = null
            List newRequestmapList = (List) object.get("actionUrl")
            String roleName = params.roleName
            long moduleId = Long.parseLong(params.moduleList)
            removeAllAppliedPermissionByRoleName(roleName, moduleId, clazz)
            backupSave(params, object)
            menuMapSave(params, object)
            String url = ""
            String featureControllerMappingId = ""
            def newRequestmap = clazz.newInstance()
            def existingRequestMap = null
            newRequestmapList.each {
                url = it.split(/,/).first()
                featureControllerMappingId = it.split(/,/).last()
                existingRequestMap = clazz.findByUrl(url)
                if (existingRequestMap == null) {
                    newRequestmap = clazz.newInstance()
                    newRequestmap.url = url
                    newRequestmap.configAttribute = roleName
                    newRequestmap.featureControllerMappingId = Long.parseLong(featureControllerMappingId)
                    newRequestmap.moduleId = moduleId
                    newRequestmap.save()
                } else {
                    if (existingRequestMap.configAttribute.indexOf(roleName) == -1) {
                        existingRequestMap.configAttribute += COMMA + roleName
                        existingRequestMap.save()
                    }
                }
            }

            springSecurityService.clearCachedRequestmaps()

        } catch (ConstraintViolationException ex) {
            log.error(ex.message)
            throw new RuntimeException(messageSource.getMessage(ex.message, [], ex.message, LocaleContextHolder.getLocale()))
        }
        catch (DataException ex) {
            log.error(ex.message)
            throw new RuntimeException(messageSource.getMessage(ex.message, [], ex.message, LocaleContextHolder.getLocale()))
        }
        catch (JDBCConnectionException ex) {
            log.error(ex.message)
            throw new RuntimeException(messageSource.getMessage(ex.message, [], ex.message, LocaleContextHolder.getLocale()))
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(messageSource.getMessage(ex.message, [], ex.message, LocaleContextHolder.getLocale()))
        }
        return true
    }

    private void backupSave(Object params, Map object) throws Exception {
        UserRequestMap userRequestMap = null
        List newRequestmapList = (List) object.get("actionUrl")
        String roleName = params.roleName
        long moduleId = Long.parseLong(params.moduleList)
        //first delete existing data
        deleteRequestMap(roleName, moduleId, "user_request_map")
        String url = ""
        String featureControllerMappingId = ""
        newRequestmapList.each {
            url = it.split(/,/).first()
            featureControllerMappingId = it.split(/,/).last()
            //for backupRequestMap
            userRequestMap = new UserRequestMap()
            userRequestMap.configAttribute = roleName
            userRequestMap.url = url
            userRequestMap.featureControllerMappingId = Long.parseLong(featureControllerMappingId)
            userRequestMap.moduleId = moduleId
            userRequestMap.save()
        }
        springSecurityService.clearCachedRequestmaps()
    }

    @Transactional
    private void menuMapSave(Object params, Map object) throws Exception {
        MenuMap menuMap = null
        List newfeatureActionList = (List) object.get("actionIds")
        String roleName = params.roleName
        long moduleId = Long.parseLong(params.moduleList)
        //first delete existing data
        deleteMenuMap(roleName, moduleId, "menu_map")
        String url = ""
        String featureControllerMappingId = ""
        newfeatureActionList.each {
            //for menuMap
            menuMap = new MenuMap()
            menuMap.roleName = roleName
            menuMap.featureActionId = it.value
            menuMap.moduleId = moduleId
            menuMap.save()
        }
        springSecurityService.clearCachedRequestmaps()
    }


    @Transactional(readOnly = true)
    public List getCheckAction(Object params) {
        Class clazz = pluginClassUtil.getRequestmapFromConfig()
        List result = []
//        String tableName = getTableSpecificName(clazz.simpleName)
        String tableName = 'user_request_map'
        String permittedToShowPart = ''
//        if (params.rolename == 'ROLE_SA' || params.rolename == 'ROLE_ADMIN') {
//            permittedToShowPart = ' AND 1=1 '
//        } else {
//            permittedToShowPart = ' AND is_permitted_to_show = 1 '
//        }
        if (params.userName == 'sadmin') {
            permittedToShowPart = ' AND 1=1 '
        } else {
            permittedToShowPart = ' AND is_permitted_to_show = 1 '
        }
        try {
            String sql = """
             SELECT
                feature_action.id,feature_name AS "featureName",action_name AS "actionName"
             FROM feature_info,feature_action,feature_controller_mapping,${tableName}
             WHERE feature_info.id=feature_action.feature_info_id AND feature_action.id=feature_controller_mapping.feature_action_id
                AND feature_controller_mapping.id=${tableName}.feature_controller_mapping_id AND  feature_info.module_info_id=${params.moduleid}
                ${permittedToShowPart} AND config_attribute like '%${params.rolename}%'
             GROUP BY feature_action.id, feature_name, action_name
             """

            Sql db = new Sql(dataSource)
            result = db.rows(sql)
        }
        catch (Exception ex) {
            log.error(ex.message)
        }
        return result
    }

    public List removeAllAppliedPermissionByRoleName(String roleName, long moduleId, Class clazz) {
        List result = []
        try {
            result = clazz.findAll("from ${clazz.simpleName} as m where m.moduleId=${moduleId} and m.configAttribute like '%${roleName}%'")
            if (result) {
                int index = -1
                String str = ""
                result.each {
                    str = it.configAttribute
                    index = str.indexOf(roleName)
                    if (index == 0) {
                        if (str.indexOf(",", 0) == -1) {
                            it.delete()
                        } else {
                            str = str.substring(roleName.length() + 1)
                            it.configAttribute = str
                            it.save()
                        }
                    } else if (index > 0) {
                        str = str.replaceAll(COMMA + roleName, "")
                        it.configAttribute = str
                        it.save()
                    }
                }
            }
            springSecurityService.clearCachedRequestmaps()
        }
        catch (Exception ex) {
            log.error(ex.message)
        }
        return result
    }

    @Transactional
    public void deleteRequestMap(String roleName, long moduleId, String tableName) {
        try {
            String sql = """Delete From ${tableName} where config_attribute='${roleName}' and module_id= '${moduleId}'"""

            Sql db = new Sql(dataSource)
            db.execute(sql)
            springSecurityService.clearCachedRequestmaps()
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(messageSource.getMessage(ex.message, [], ex.message, LocaleContextHolder.getLocale()))
        }

    }

    @Transactional
    public void deleteMenuMap(String roleName, long moduleId, String tableName) {
        try {
            String sql = """Delete From ${tableName} where role_name='${roleName}' and module_id= '${moduleId}'"""

            Sql db = new Sql(dataSource)
            db.execute(sql)
            springSecurityService.clearCachedRequestmaps()
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(messageSource.getMessage(ex.message, [], ex.message, LocaleContextHolder.getLocale()))
        }
    }

    static String getTableSpecificName(String word) {
        Pattern p = Pattern.compile(FIELD_NAME_CONVERSION_PATTERN)
        Matcher m = p.matcher(word)
        StringBuffer sb = new StringBuffer()
        while (m.find()) {
            m.appendReplacement(sb, "_")
        }
        m.appendTail(sb)
        return sb.toString().toLowerCase()
    }
}

