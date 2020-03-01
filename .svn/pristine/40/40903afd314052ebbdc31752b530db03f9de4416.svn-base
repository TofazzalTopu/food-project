package com.docu.security

import com.docu.commons.*
import com.docu.sqlgen.security.LoginHistoryQueryGenerator
import com.docu.sqlgen.security.SecuritySqlBuilder
import grails.converters.JSON
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

class LoginHistoryService extends InternationalizationService {

    static transactional = false
    SessionManagementService sessionManagementService
    TransactionAwareDataSourceProxy dataSource
//    OfficeFacadeService officeFacadeService
//    HrFacadeService hrFacadeService
    SqlExecutionService sqlExecutionService

    @Transactional
    public boolean saveLoginHistory(LoginHistory loginHistory) {
        try {
            String query = SecuritySqlBuilder.instance.buildInsertSql(LoginHistory.class.simpleName, LoginHistoryQueryGenerator.instance.toInsertSql(loginHistory))
            sqlExecutionService.executeInsertQuery(query)
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional
    public boolean updateLoginHistory() {
        LoginHistory loginHistory = null
        try {
            loginHistory = sessionManagementService.getLoginHistory()
            if (loginHistory) {
                loginHistory.logoutTime = DateUtil.getCurrentSystemDateTime()
                loginHistory.logoutIPAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();
                sqlExecutionService.executeUpdateQuery(LoginHistoryQueryGenerator.instance.toUpdateSql(loginHistory))
            }
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        finally {
            loginHistory = null
        }
        return true
    }

    public String getLoginHistory(Map params) {
        if (!params.fromDate) {
            params.fromDate = DateUtil.getDateFormatAsString(DateUtil.getStartDateOfMonth(DateUtil.getCurrentSystemDate()))
        }
        if (!params.toDate) {
            params.toDate = DateUtil.getDateFormatAsString(DateUtil.getCurrentSystemDate())
        }
        String sFromDate = DateUtil.getFlexibleDateFormatAsString(DateUtil.getSimpleDate(params.fromDate), CommonConstants.DATE_FORMAT_Y_M_D) + " 00:00:00"
        String sToDate = DateUtil.getFlexibleDateFormatAsString(DateUtil.getSimpleDate(params.toDate), CommonConstants.DATE_FORMAT_Y_M_D) + " 23:59:59"
        String clause = ""

        String strSql = """
                 SELECT
                        lh.id                    AS id,
                        lh.user_name             AS "userPin",
                        au.full_name             AS "userName",
                        ''                       AS user,
                        au.application_user_type AS "userType",
                        lh.loginipaddress        AS "loginIP",
                        lh.logoutipaddress       AS "logoutIP",
                        lh.login_time            AS "loginTime",
                        lh.logout_time           AS "logoutTime"
                 FROM login_history AS lh
                   LEFT JOIN application_user AS au
                     ON (au.username = lh.user_name
                         AND au.domain_status_id = ${CommonConstants.DOMAIN_STATUS_ACTIVE})
                 WHERE lh.user_name = '${params.user}'
                 AND lh.login_time BETWEEN '${sFromDate}' AND '${sToDate}'
                 order by lh.login_time desc
        """
        Map map = [:]
        Sql sql = new Sql(dataSource)
        List results = sql.rows(strSql)
        results.each {
            it.user = "[ " + it.userPin + " ] " + it.userName
            it.loginTime = DateUtil.getDatabaseDateFormatAsString(it.loginTime, CommonConstants.DATE_FORMAT_H_M_S)
            it.logoutTime = DateUtil.getDatabaseDateFormatAsString(it.logoutTime, CommonConstants.DATE_FORMAT_H_M_S)
        }
        map.put('aaData', results)
        return map as JSON
    }

    public List<GroovyRowResult> getUserList() {
        Sql sql = new Sql(dataSource)
        String strSql = """
                            SELECT
                                au.username as "userName",
                                au.full_name AS "fullName"
                            FROM application_user AS au
                            WHERE au.domain_status_id = ${CommonConstants.DOMAIN_STATUS_ACTIVE}
                        """
        if (sessionManagementService.user.applicationUserType == ApplicationUserType.INTERNAL) {
            strSql += """ AND au.application_user_type = '${ApplicationUserType.INTERNAL}'"""
        }
        List<GroovyRowResult> userList = sql.rows(strSql)
        return userList
    }

//    @Transactional(readOnly = true)
//    public List<LoginHistory> getUserLoginHistory(String userName){
//        List<LoginHistory> loginHistoryList = LoginHistory.withCriteria {
//            eq("userName", userName)
//        }
//        return loginHistoryList
//    }
//    @Transactional(readOnly = true)
//    public long countUserLoginHistory(String userName){
//        return LoginHistory.countByUserName(userName)
//    }

//    public LoginHistory setupLoginHistory(String userPinNumber) {
//        String ipAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();
//        LoginHistory loginHistory = new LoginHistory(userName: userPinNumber,
//                officeCode: OfficeConstants.OFFICE_CODE_BI,
//                officeType: OfficeConstants.OFFICE_TYPE_ID_BI,
//                loginTime: DateUtil.getCurrentSystemDateTime(),
//                loginIPAddress: ipAddress)
//        return loginHistory
//    }

    public LoginHistory createLoginHistory(String userPinNumber, Office office) {
        String ipAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();
        LoginHistory loginHistory = new LoginHistory(userName: userPinNumber,
                officeCode: office.officeCode,
                officeTypeId: office.officeTypeId,
                loginTime: DateUtil.getCurrentSystemDateTime(),
                loginIPAddress: ipAddress)
        return loginHistory
    }
}
