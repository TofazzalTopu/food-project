package com.docu.sqlgen.security

import com.docu.commons.sqlgen.AbstractSqlBuilder
import com.docu.security.LoginHistory
import org.hibernate.HibernateException

/**
 * Created by rafiqul.islam on 11/27/2014.
 */
@Singleton
class SecuritySqlBuilder extends AbstractSqlBuilder {
    public static final String INSERT_LOGIN_HISTORY = "INSERT INTO login_history(id,version,loginipaddress,login_time,logoutipaddress,logout_time,office_code,office_type_id,user_name) VALUES"
    @Override
    String getInsertSqlPrefix(String className) {
        String insertSqlPrefix = null
        switch (className) {
            case LoginHistory.class.simpleName:
                insertSqlPrefix = INSERT_LOGIN_HISTORY
                break
            default:
                throw new HibernateException("Sql Prefix is not found for " + className)
                break
        }
        return insertSqlPrefix
    }
}
