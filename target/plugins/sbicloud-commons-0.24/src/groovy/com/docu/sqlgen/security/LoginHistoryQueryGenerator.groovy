package com.docu.sqlgen.security

import com.docu.commons.CommonConstants
import com.docu.commons.DateUtil
import com.docu.commons.sqlgen.AbstractQueryGenerator
import com.docu.security.LoginHistory

/**
 * Created by rafiqul.islam on 11/27/2014.
 */
@Singleton
class LoginHistoryQueryGenerator extends AbstractQueryGenerator {

    // (id,version,loginipaddress,login_time,logoutipaddress,logout_time,office_code,office_type,user_name)
    @Override
    String toInsertSql(Object object) {
        LoginHistory loginHistory = (LoginHistory) object
        String toInsertSqlValues = SPACE
//        String strLogoutIPAddress = loginHistory.logoutIPAddress ? ESCAPE_SINGLE_QUOTE + loginHistory.logoutIPAddress + ESCAPE_SINGLE_QUOTE : null
//        String strLogoutTime = loginHistory.logoutTime ? ESCAPE_SINGLE_QUOTE + DateUtil.getFlexibleDateFormatAsString(loginHistory.logoutTime, CommonConstants.DATE_FORMAT_Y_M_D_H_M_S) + ESCAPE_SINGLE_QUOTE : null
        toInsertSqlValues = """('${loginHistory.id}', 0, '${loginHistory.loginIPAddress}',
                    '${DateUtil.getFlexibleDateFormatAsString(loginHistory.loginTime, CommonConstants.DATE_FORMAT_Y_M_D_H_M_S)}',
                    NULL,NULL,'${loginHistory.officeCode}','${loginHistory.officeTypeId}','${loginHistory.userName}')"""

        return toInsertSqlValues
    }

    @Override
    String toUpdateSql(Object object, boolean versionCheck = true) {
        LoginHistory loginHistory = (LoginHistory) object
        String toUpdateSqlValues = """UPDATE login_history SET
                    logoutipaddress = '${loginHistory.logoutIPAddress}',
                    logout_time = '${DateUtil.getFlexibleDateFormatAsString(loginHistory.logoutTime, CommonConstants.DATE_FORMAT_Y_M_D_H_M_S)}'
                where id='${loginHistory.id}'"""
        return toUpdateSqlValues
    }
}
