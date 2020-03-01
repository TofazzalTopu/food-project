package com.docu.commons.sqlgen

import com.docu.commons.CommonConstants

/**
 * Created by A.B.M. Mahbubor Rahman on 11/17/2014.
 */
abstract class AbstractSqlBuilder {
    protected static final int SYNC_STATUS = 0
    protected static final int EXPORT_STATUS = 0

    protected static final int ACTION_INSERT = 1
    protected static final int ACTION_UPDATE = 2

    protected static final int NO_OF_OBJECTS_PER_SQL = 20

    abstract String getInsertSqlPrefix(String className)

    public String buildInsertSql(String className, String sql){
        String insertSqlPrefix = getInsertSqlPrefix(className)
        StringBuilder insertSqlBuffer = new StringBuilder(insertSqlPrefix)
        insertSqlBuffer.append(sql)
        return insertSqlBuffer.toString()
    }

    public List buildInsertSql(String className, List<String> sqlList){
        List<String> queryList = []
        if (sqlList.isEmpty()) {
            return queryList
        }
        String insertSqlPrefix = getInsertSqlPrefix(className)

        StringBuilder insertSqlBuffer = new StringBuilder()
        insertSqlBuffer.append(insertSqlPrefix)

        int count = 1
        sqlList.each {
            insertSqlBuffer.append(it)
            insertSqlBuffer.append(CommonConstants.COMMA)

            if (count % NO_OF_OBJECTS_PER_SQL == 0) {
                insertSqlBuffer.deleteCharAt(insertSqlBuffer.length() - 1)
                queryList.add(insertSqlBuffer.toString())

                insertSqlBuffer.length = 0
                insertSqlBuffer.append(insertSqlPrefix)
            }
            ++count
        }

        if (insertSqlBuffer.length() > 0 && (count % NO_OF_OBJECTS_PER_SQL != 1)) {
            insertSqlBuffer.deleteCharAt(insertSqlBuffer.length() - 1)
            queryList.add(insertSqlBuffer.toString())
        }
        return queryList
    }
}
