package com.docu.commons

import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import groovy.sql.GroovyRowResult

class JsonUtilService {

    static transactional = false
    TransactionAwareDataSourceProxy dataSource

    @Transactional
    public int getRecordCount(String sql) {
        Map map = [:]
        Sql db = new Sql(dataSource)
        String query = """
            SELECT COUNT(1) AS cnt
            FROM (${sql}) tbl
        """
        Object object = db.firstRow(query)
        return (Integer) object.cnt
    }

    @Transactional
    public List<GroovyRowResult> getRecordList(String sql) {
        Map map = [:]
        return new Sql(dataSource).rows(sql)
    }

    public String getSqlCalcFoundRowsSql(String sql) {
        sql = sql.toLowerCase()
        if (!sql.contains("sql_calc_found_rows")) {
            sql = sql.replaceFirst("select", "select sql_calc_found_rows")
        }
        return sql
    }

    public String getSqlForRowCount(String sql){
        String countQuery = "SELECT COUNT(1) as \"recordCount\" ";
        int i = sql.indexOf('FROM');
        int j = sql.indexOf('ORDER');
        int k = sql.indexOf('LIMIT')
        if(j != -1){
            countQuery = countQuery + sql.substring(i,j)
        }
        if(j == -1 && k != -1){
            countQuery = countQuery + sql.substring(i,j)
        }
        return  countQuery;
    }
}
