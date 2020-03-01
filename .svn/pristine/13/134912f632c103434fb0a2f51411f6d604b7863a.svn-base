package com.bits.bdfp.settings

import com.docu.security.ApplicationUser
import groovy.sql.GroovyRowResult
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class BusinessUnitConfigurationService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public BusinessUnitConfiguration create(Object object) {
        BusinessUnitConfiguration businessUnitConfiguration = (BusinessUnitConfiguration) object
        if (businessUnitConfiguration.save(false)) {
            return businessUnitConfiguration
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        BusinessUnitConfiguration businessUnitConfiguration = (BusinessUnitConfiguration) object
        if (businessUnitConfiguration.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        BusinessUnitConfiguration businessUnitConfiguration = (BusinessUnitConfiguration) object
        businessUnitConfiguration.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Object params, ApplicationUser applicationUser) {
        sql = new Sql(dataSource)
        String strLIMIT = "";
        String offSet = ""
        String searchCondition = ""
        if (params.query) {
            searchCondition = """ AND (
         lower( business_unit_configuration.name) like lower('%${params?.query}%')
         OR
         business_unit_configuration.code LIKE '%${params?.query}%'
         OR
         business_unit_configuration.note LIKE '%${params?.query}%'
           OR
         enterprise_configuration.`name` LIKE '%${params?.query}%'
        )
        """
        }
        if (action.resultPerPage != -1) {
            strLIMIT = """LIMIT ${action.resultPerPage}"""
            offSet = """
                    OFFSET ${action.start}
                   """
        } else {
            action.resultPerPage = -1;
        }

        String strSql = """SELECT business_unit_configuration.id,business_unit_configuration.name,business_unit_configuration.code,
                        business_unit_configuration.note,
                        business_unit_configuration.is_active,
                        enterprise_configuration.id AS eid,
                        CONCAT(enterprise_configuration.name,'[',enterprise_configuration.code,']') AS ename
                        FROM `business_unit_configuration`
                        INNER JOIN `enterprise_configuration`
                        ON enterprise_configuration.id=business_unit_configuration.`enterprise_configuration_id`
                        INNER JOIN `application_user_enterprise`
                        ON enterprise_configuration.id=application_user_enterprise.`enterprise_configuration_id`

                        WHERE `application_user_enterprise`.`application_user_id`=${applicationUser.id}
                            ${searchCondition}
                            ORDER BY ${action.sortCol} ${action.sortOrder}
                            ${strLIMIT}
                           ${offSet}
                          """
        List objList = sql.rows(strSql.toString())

        strSql = """SELECT COUNT(business_unit_configuration.id) as total_rows
                         FROM `business_unit_configuration`
                        INNER JOIN `enterprise_configuration`
                        ON enterprise_configuration.id=business_unit_configuration.`enterprise_configuration_id`
                        INNER JOIN `application_user_enterprise`
                        ON enterprise_configuration.id=application_user_enterprise.`enterprise_configuration_id`

                        WHERE `application_user_enterprise`.`application_user_id`=${applicationUser.id}
                            ${searchCondition}
                          """

        List resultCount = sql.rows(strSql.toString())
        return [objList: objList, count: resultCount?.get(0)?.total_rows]
    }

    @Transactional(readOnly = true)
    public List<BusinessUnitConfiguration> list() {
        return BusinessUnitConfiguration.list()
    }

    @Transactional(readOnly = true)
    public BusinessUnitConfiguration read(Long id) {
        return BusinessUnitConfiguration.read(id)
    }

    @Transactional(readOnly = true)
    public BusinessUnitConfiguration search(String fieldName, String fieldValue) {
        String query = "from BusinessUnitConfiguration as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return BusinessUnitConfiguration.find(query)
    }

    @Transactional(readOnly = true)
    public List<GroovyRowResult> enterpriseList(ApplicationUser applicationUser) {
        sql = new Sql(dataSource)
        String strSql = """SELECT enterprise_configuration.id,
                                enterprise_configuration.code,
                                enterprise_configuration.name
                                FROM `application_user_enterprise`
                            INNER JOIN `enterprise_configuration`
                            ON enterprise_configuration.id=application_user_enterprise.`enterprise_configuration_id`
                            WHERE `application_user_enterprise`.`application_user_id`=${applicationUser.id}
                            AND  application_user_enterprise.is_active IS TRUE
                          """

        List<GroovyRowResult> resultList = sql.rows(strSql.toString())
        return resultList
    }

    public List readList(Long id, ApplicationUser applicationUser) {
        sql = new Sql(dataSource)
        String strSql = """SELECT business_unit_configuration.id,business_unit_configuration.version,business_unit_configuration.name,business_unit_configuration.code,business_unit_configuration.is_active,
                    business_unit_configuration.note,
                    enterprise_configuration.id AS eid,CONCAT(enterprise_configuration.name,' [',enterprise_configuration.code,']') AS ecode
                    FROM `business_unit_configuration`
                    INNER JOIN `enterprise_configuration` ON enterprise_configuration.id=business_unit_configuration.`enterprise_configuration_id`
                    INNER JOIN `application_user_enterprise` ON enterprise_configuration.id=application_user_enterprise.`enterprise_configuration_id`

                    WHERE `application_user_enterprise`.`application_user_id`=${applicationUser.id}
                    AND business_unit_configuration.id=${id}
                          """

        List resultList = sql.rows(strSql.toString())
        return resultList
    }

    @Transactional(readOnly = true)
    public List businessList(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
            SELECT business_unit_configuration.id,
                CONCAT(business_unit_configuration.name, ' [',business_unit_configuration.code,']') as name
            FROM `business_unit_configuration`
            WHERE `business_unit_configuration`.`enterprise_configuration_id` = ${params.id}
        """
        return sql.rows(strSql.toString())
    }
}
