package com.bits.bdfp.settings.businessday

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.bussinessday.BusinessHoliday
import com.docu.common.Action
import com.docu.common.Service
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource

class BusinessHolidayService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public BusinessHoliday create(Object object) {
        BusinessHoliday businessHoliday = (BusinessHoliday) object
        businessHoliday = businessHoliday.save(false)
        return businessHoliday
    }

    @Transactional
    public Integer update(Object object) {
        BusinessHoliday BusinessHoliday = (BusinessHoliday) object
        if (BusinessHoliday.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        BusinessHoliday businessHoliday = (BusinessHoliday) object
        businessHoliday.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Object params) {
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
            offSet = """
                    OFFSET ${action.start}
                   """
        } else {
            action.resultPerPage = -1;
        }

        String strSql = """SELECT business_holiday.id,financial_year.name,
                            STR_TO_DATE(CONCAT(business_holiday.day,'-',business_holiday.month,'-',business_holiday.year),'%d-%m-%YY') AS holiday_date,
                            business_holiday.note
                            FROM
                            business_holiday
                            INNER JOIN
                            financial_year
                            ON
                            financial_year.id=business_holiday.financial_year_id
                            ${searchCondition}
                            ORDER BY ${action.sortCol} ${action.sortOrder}
                            ${strLIMIT}
                           ${offSet}
                          """
        List objList = sql.rows(strSql.toString())

        strSql = """SELECT COUNT(id) as total_rows FROM business_holiday
                            ${searchCondition}
                          """

        List resultCount = sql.rows(strSql)
        return [objList: objList, count: resultCount?.get(0)?.total_rows]
    }

    @Transactional(readOnly = true)
    public List<BusinessHoliday> list() {
        return BusinessHoliday.list()
    }

    @Transactional(readOnly = true)
    public BusinessHoliday read(Long id) {
        return BusinessHoliday.read(id)
    }

    @Transactional(readOnly = true)
    public BusinessHoliday search(String fieldName, String fieldValue) {
        String query = "from BusinessHoliday as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return BusinessHoliday.find(query)
    }

    @Transactional(readOnly = true)
    public List getExistingHoliday(Object businessHoliday) {
        sql = new Sql(dataSource)
        String strSql = """SELECT id,day  FROM business_holiday
                            WHERE
                            day=${businessHoliday.day}
                            AND month=${businessHoliday.month}
                            AND year=${businessHoliday.year}
                          """
        List objList = sql.rows(strSql)
        return objList
    }
    @Transactional(readOnly = true)
    public List getExistingHolidayForEdit(Object bHoliday) {
        BusinessHoliday businessHoliday =(BusinessHoliday) bHoliday
        sql = new Sql(dataSource)
        String strSql = """SELECT id,day  FROM business_holiday
                            WHERE
                            day=${businessHoliday.day}
                            AND month=${businessHoliday.month}
                            AND year=${businessHoliday.year}
                            AND id!=${businessHoliday.id}
                          """
        List objList = sql.rows(strSql)
        return objList
    }
}
