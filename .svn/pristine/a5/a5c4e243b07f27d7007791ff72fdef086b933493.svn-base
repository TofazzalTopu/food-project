package com.bits.bdfp

import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource

/**
 * Created by prianka.adhikary on 10/26/2015.
 */
class ValidationCheckService {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional(readOnly = true)
    public Boolean validationCheck(String domain, String id) {
        sql = new Sql(dataSource)
        Boolean isNotValidate = false
        List validationList = []
        String strSql = """SELECT DISTINCT table_name,column_name
                           FROM `information_schema`.KEY_COLUMN_USAGE
                           WHERE
                           referenced_table_name = '${domain}'
                          """
        List objList = sql.rows(strSql.toString())

        if (objList && objList.size() > 0) {
            objList.each {
                String strSql1 = """SELECT *
                           FROM ${it.table_name} as tbl
                           WHERE
                           tbl.${it.column_name} = ${id}
                          """
                List objList1 = sql.rows(strSql1.toString())
                if (objList1 && objList1.size() > 0) {
                    validationList.add(it)

                }

            }
            if (validationList && validationList.size() > 0) {
                isNotValidate = true
            } else {
                isNotValidate = false
            }
        } else {
            isNotValidate = false
        }

        return isNotValidate
    }

    @Transactional(readOnly = true)
    public Boolean validationCheck(String domain, String childDomain, String id) {
        sql = new Sql(dataSource)
        Boolean isNotValidate = false
        List validationList = []
        String strSql = """SELECT DISTINCT table_name,column_name
                           FROM `information_schema`.KEY_COLUMN_USAGE
                           WHERE
                           referenced_table_name = '${domain}'
                          """
        List objList = sql.rows(strSql.toString())

        if (objList && objList.size() > 0) {
            objList.each {
                if (it.table_name != childDomain) {
                    String strSql1 = """SELECT *
                           FROM ${it.table_name} as tbl
                           WHERE
                           tbl.${it.column_name} = ${id}
                          """
                    List objList1 = sql.rows(strSql1.toString())
                    if (objList1 && objList1.size() > 0) {
                        validationList.add(it)
                    }
                }
            }
            if (validationList && validationList.size() > 0) {
                isNotValidate = true
            } else {
                isNotValidate = false
            }
        } else {
            isNotValidate = false
        }

        return isNotValidate
    }
}
