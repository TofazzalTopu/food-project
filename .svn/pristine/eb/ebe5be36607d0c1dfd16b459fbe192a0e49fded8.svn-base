package com.docu.accesscontrol

import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import org.codehaus.groovy.grails.context.support.PluginAwareResourceBundleMessageSource
import org.hibernate.exception.ConstraintViolationException
import org.hibernate.exception.DataException
import org.hibernate.exception.JDBCConnectionException
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import org.springframework.transaction.annotation.Transactional

class FeatureControllerMappingService {

    TransactionAwareDataSourceProxy dataSource
    static transactional = false
    PluginAwareResourceBundleMessageSource messageSource

    @Transactional
    public boolean save(Map object) {
        try {
            boolean isInsert = false
            println object
            List newIdList = []
            List<FeatureControllerMapping> featureControllerMappingList = (List<FeatureControllerMapping>) object.get("featureControllerMapping")
            Long featureActionId = Long.parseLong(object.get('featureActionId').toString())
            List existingIdList = FeatureControllerMapping.findAll("from FeatureControllerMapping fcm where fcm.featureAction.id='${featureActionId}' ").collect {
                it.id
            }

            featureControllerMappingList.each {
                FeatureControllerMapping existFeatureControllerMap = FeatureControllerMapping.find("from FeatureControllerMapping fcm where fcm.controllerName='${it.controllerName}' and fcm.controllerAction='${it.controllerAction}' and fcm.featureAction.id='${it.featureAction.id}' ")

                if (existFeatureControllerMap == null) {
                    it.save()
                } else {
                    newIdList.add(existFeatureControllerMap.id)
                    existingIdList.removeAll(newIdList)
                }
            }
            if (existingIdList.size() > 0) {
                existingIdList.each {
                    FeatureControllerMapping featureControllerMapping = FeatureControllerMapping.read(it)
                    featureControllerMapping.delete()
                }

            }

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

    List<GroovyRowResult> getFeatureControllerName(long actionId ) {
        String strQuery = """
                SELECT f.controller_name as "controllerName"
                FROM feature_controller_mapping AS f
                WHERE f.feature_action_id = ${actionId}
                GROUP BY f.controller_name"""
        Sql sql = new Sql(dataSource)
        List<GroovyRowResult> resultList = sql.rows(strQuery)
        return resultList
    }
}
