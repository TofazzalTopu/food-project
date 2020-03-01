package com.docu.accesscontrol

import com.docu.accesscontrol.commons.PluginGrailsClassUtil


import com.docu.accesscontrol.commons.PluginJsonUtil
import com.docu.app.FeatureActionUtil
import com.docu.app.FeatureInfoUtil
import org.codehaus.groovy.grails.context.support.PluginAwareResourceBundleMessageSource
import org.hibernate.exception.ConstraintViolationException
import org.hibernate.exception.DataException
import org.hibernate.exception.JDBCConnectionException
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.transaction.annotation.Transactional
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import java.text.DecimalFormat

class FeatureInfoService {

    static transactional = false
    PluginJsonUtil pluginJsonUtil
    PluginAwareResourceBundleMessageSource messageSource
    @Autowired
    TransactionAwareDataSourceProxy dataSource
    @Autowired
    PluginGrailsClassUtil pluginGrailsClassUtil

    static DecimalFormat formatter = new DecimalFormat()

    void initialize() {
        pluginGrailsClassUtil.resolve()
    }

    void uninitialize() {
        pluginGrailsClassUtil.destroy()
    }


    @Transactional
    public boolean save(Map object) {
        try {
            List<FeatureAction> featureActionList = (List<FeatureAction>)object.get("featureAction")
            FeatureInfo featureInfo = (FeatureInfo)object.get("featureInfo")
            featureInfo.save()
            //featureActionList.addAll(featureInfo)
            featureActionList.each {
                if (it.isDeleted == false) {
                    if(it.actionCode=='AUTO' && it.menuUrl !='null' ){
                        it.actionCode = generateActionCode(featureInfo)
                    }
                    it.save()
                } else {
                    it.delete()
                }
            }

            // Re initialize Feature
            FeatureInfoUtil.instance.resolve()
            // Re initialize Feature Action
            FeatureActionUtil.instance.resolve()
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

    @Transactional
    public boolean arrangeFeatureInfo(Map object) {
        try {
            List<FeatureInfo> featureInfoList = (List<FeatureInfo>)object.get("featureInfo")
            featureInfoList.each {
                it.save()
            }
            // Re initialize Feature
            FeatureInfoUtil.instance.resolve()
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


    @Transactional(readOnly = true)
    public String getFeatureInfoListJson(Map params) {
        String sql = """
       select fi.id, mf.module_name,fi.feature_name As "featureName",
       fa.action_name as "actionName"
      from feature_info fi, feature_action fa,module_info mf
      where fi.id=fa.feature_info_id and fi.module_info_id=mf.id
      and fi.module_info_id=${params.moduleInfoId}
      order by fi.id
      """
        // log.info("SQL = " + sql)
        String feed = pluginJsonUtil.getJqGridFeed(sql, params)// .getJqGridFeed(sql, params, true)
        return feed
    }

    @Transactional(readOnly = true)
    public Object getFeatureInfoByModule(ModuleInfo moduleInfo) {
        return FeatureInfo.findAllByModuleInfo(moduleInfo)
    }

    public List<GroovyRowResult> getSelectedFeatureInfoList(Map params) {
        //List selectedFeatureInfoList=[]
        //return FeatureInfo.findAll("from FeatureInfo as fi, MenuItem as mi  where fi.id=mi.featureInfoId and mi.menuGroupId ='${params.id}'").collect {it.featureName}
        String query = """
            select menu_item.feature_info_id from feature_info, menu_item where feature_info.id=menu_item.feature_info_id
            and menu_item.menu_group_id='${params.id}'
        """

        Sql sql = new Sql(dataSource)
        return sql.rows(query)

    }

    private String generateActionCode(FeatureInfo featureInfo) {
        String generatedValue = ''
        List featureActionList = []
        String codePart = ''
        Long actionCount
        int mCode
        formatter.applyPattern('000')
        mCode = featureInfo.moduleInfo.moduleCode.length()
        String sqlQuery = """
            SELECT  fa.action_code as "aCode"
                    FROM feature_info fi
                    INNER JOIN feature_action fa
                    ON fa.feature_info_id=fi.id
                WHERE
                    fa.menu_url !='null'
                    AND fi.module_info_id= ${featureInfo.moduleInfo.id}
                ORDER BY fa.action_code DESC LIMIT 1
        """
        Sql sql = new Sql(dataSource)
        List featureActionLastRow = sql.rows(sqlQuery)
        if (featureActionLastRow.size() > 0){
            codePart = featureActionLastRow.get(0).aCode
            actionCount = Long.parseLong(codePart.substring(mCode)) + 1
        } else {
            actionCount = 1
        }
        generatedValue = featureInfo.moduleInfo.moduleCode + formatter.format(actionCount)
        return generatedValue

    }
}
