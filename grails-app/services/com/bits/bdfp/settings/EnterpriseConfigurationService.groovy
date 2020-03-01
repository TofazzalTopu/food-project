package com.bits.bdfp.settings

import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class EnterpriseConfigurationService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql
    SpringSecurityService   springSecurityService

    @Transactional
    public EnterpriseConfiguration create(Object object) {
        EnterpriseConfiguration enterpriseConfiguration = (EnterpriseConfiguration) object
        enterpriseConfiguration = enterpriseConfiguration.save(false)
        return enterpriseConfiguration
    }

    @Transactional
    public Integer update(Object object) {
        EnterpriseConfiguration enterpriseConfiguration = (EnterpriseConfiguration) object
        if (enterpriseConfiguration.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        EnterpriseConfiguration enterpriseConfiguration = (EnterpriseConfiguration) object
        enterpriseConfiguration.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action,Object params) {
        sql = new Sql(dataSource)
        String strLIMIT = "";
        String offSet = ""
        String searchCondition = ""
        if (params.query) {
            searchCondition = """ WHERE (
         lower(name) like lower('%${params?.query}%')
         OR
         code LIKE '%${params?.query}%'
         OR
         note LIKE '%${params?.query}%'

        )
        """
        }
        if (action.resultPerPage != -1) {
            strLIMIT = """LIMIT ${action.resultPerPage}"""
            offSet="""
                    OFFSET ${action.start}
                   """
        } else {
            action.resultPerPage = -1;
        }

        String strSql = """SELECT id,code,name,note FROM enterprise_configuration
                            ${searchCondition}
                            ORDER BY ${action.sortCol} ${action.sortOrder}
                            ${strLIMIT}
                           ${offSet}
                          """
        List objList = sql.rows(strSql.toString())

        strSql = """SELECT COUNT(id) as total_rows FROM enterprise_configuration
                            ${searchCondition}
                          """

        List resultCount = sql.rows(strSql.toString())
        return [objList: objList, count: resultCount?.get(0)?.total_rows]
    }
    @Transactional(readOnly = true)
    public List<EnterpriseConfiguration> list() {
        return EnterpriseConfiguration.list()
    }

    @Transactional(readOnly = true)
    public EnterpriseConfiguration read(Long id) {
        return EnterpriseConfiguration.read(id)
    }

    @Transactional(readOnly = true)
    public EnterpriseConfiguration search(String fieldName, String fieldValue) {
        String query = "from EnterpriseConfiguration as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return EnterpriseConfiguration.find(query)
    }

    @Transactional(readOnly = true)
    public Map enterpriseMappingByApplicationUser(Object params) {
        ApplicationUser applicationUser = (ApplicationUser)springSecurityService.getCurrentUser()
        String query = """
            SELECT `enterprise_configuration`.`id`, CONCAT('[', `enterprise_configuration`.`code`, '] ', `enterprise_configuration`.`name`) AS `name`
                FROM `application_user_enterprise`
                    INNER JOIN `enterprise_configuration` ON (`application_user_enterprise`.`enterprise_configuration_id` = `enterprise_configuration`.`id`)
            WHERE `application_user_enterprise`.`application_user_id` = ${applicationUser.id}
        """
        Sql sql = new Sql(dataSource)
        List enterpriseList = sql.rows(query)
        List selectedEnterpriseList = new ArrayList()
        if(params.applicationUserId){
            query = """
                SELECT `enterprise_configuration`.`id`, CONCAT('[', `enterprise_configuration`.`code`, '] ', `enterprise_configuration`.`name`) AS `name`
                    FROM `application_user_enterprise`
                        INNER JOIN `enterprise_configuration` ON (`application_user_enterprise`.`enterprise_configuration_id` = `enterprise_configuration`.`id`)
                WHERE `application_user_enterprise`.`application_user_id` = ${params.applicationUserId}
            """
            selectedEnterpriseList = sql.rows(query)
        }
        return [enterpriseList: enterpriseList, selectedEnterpriseList: selectedEnterpriseList]
    }
}
