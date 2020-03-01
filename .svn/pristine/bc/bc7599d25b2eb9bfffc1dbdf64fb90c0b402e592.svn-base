package com.docu.commons

import grails.util.Environment
import groovy.sql.Sql
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import org.hibernate.HibernateException

class SqlExecutionService {

    static transactional = false

    TransactionAwareDataSourceProxy dataSource

    public boolean executeBatchQuery(List<String> queryList) {
        try {
            Sql db = new Sql(dataSource)
            queryList.each {
                if (Environment.current == Environment.DEVELOPMENT) {
                    println "Executing INSERT SQL  -> " + it
                }
                db.execute(it)
            }
        }
        catch (Exception ex) {
            log.error(ex.stackTrace)
            throw new HibernateException(ex)
        }
        finally {
            queryList = null
        }
        return true
    }

    public boolean executeBatchInsertQuery(List<String> queryList) {
        try {
            Sql db = new Sql(dataSource)
            queryList.each {
                if (Environment.current == Environment.DEVELOPMENT) {
                    println "Executing INSERT SQL -> " + it
                }
                db.execute(it)
            }
        }
        catch (Exception ex) {
            log.error(ex.stackTrace)
            throw new HibernateException(ex)
        }
        finally {
            queryList = null
        }
        return true
    }

    public boolean executeInsertQuery(String query) {
        try {
            Sql db = new Sql(dataSource)
            if (Environment.current == Environment.DEVELOPMENT) {
                println "Executing INSERT SQL -> " + query
            }
            db.execute(query)
        }
        catch (Exception ex) {
            log.error(ex.stackTrace)
            throw new HibernateException(ex)
        }
        return true
    }

    public boolean executeUpdateQuery(List<String> queryList) {
        try {
            Sql db = new Sql(dataSource)
            queryList.each {
                if (Environment.current == Environment.DEVELOPMENT) {
                    println "Executing UPDATE SQL -> " + it
                }
                if (db.executeUpdate(it) == 0) {
                    throw new HibernateException("Data is not found to update")
                }
            }
        }
        catch (Exception ex) {
            log.error(ex.stackTrace)
            throw new HibernateException(ex)
        }
        finally {
            queryList = null
        }
        return true
    }

    public boolean executeUpdateQuery(String query) {
        try {
            Sql db = new Sql(dataSource)
            if (Environment.current == Environment.DEVELOPMENT) {
                println "Executing UPDATE SQL -> " + query
            }
            if (db.executeUpdate(query) == 0) {
                throw new HibernateException("Data is not found to update")
            }
        }
        catch (Exception ex) {
            log.error(ex.stackTrace)
            throw new HibernateException(ex)
        }
        return true
    }

    public boolean executeUpdateQueryIgnoreZeroAffectedRows(String query) {
        try {
            Sql db = new Sql(dataSource)
            if (Environment.current == Environment.DEVELOPMENT) {
                println "Executing UPDATE SQL -> " + query
            }
            db.executeUpdate(query)
        }
        catch (Exception ex) {
            log.error(ex.stackTrace)
            throw new HibernateException(ex)
        }
        return true
    }

    public List executeSelectQuery(String sqlString) {
        Sql sqlInstance = new Sql(dataSource)
        return sqlInstance.rows(sqlString).toList()
    }

    public boolean executeDeleteQuery(List<String> queryList) {
        try {
            Sql db = new Sql(dataSource)
            queryList.each {
                if (Environment.current == Environment.DEVELOPMENT) {
                    println "Executing DELETE SQL -> " + it
                }
                db.execute(it)
            }
        }
        catch (Exception ex) {
            log.error(ex.stackTrace)
            throw new HibernateException(ex)
        }
        finally {
            queryList = null
        }
        return true
    }
}
