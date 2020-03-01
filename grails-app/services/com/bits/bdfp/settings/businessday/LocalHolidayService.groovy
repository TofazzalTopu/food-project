package com.bits.bdfp.settings.businessday

import com.bits.bdfp.settings.bussinessday.LocalHoliday
import com.docu.common.Action
import com.docu.common.Service
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource

class LocalHolidayService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public LocalHoliday create(Object object) {
        LocalHoliday localHoliday = (LocalHoliday) object
        localHoliday = localHoliday.save(false)
        return localHoliday
    }

    @Transactional
    public Integer update(Object object) {
        LocalHoliday localHoliday = (LocalHoliday) object
        if (localHoliday.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        LocalHoliday localHoliday = (LocalHoliday) object
        localHoliday.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Object params) {
        sql = new Sql(dataSource)
        String strLIMIT = "";
        String offSet = ""
        String searchCondition = ""
        String territoryId = ''

        if (params?.territoryConfiguration?.id) {
            territoryId = """WHERE local_holiday.territory_configuration_id=${params.territoryConfiguration.id}"""

        }
        if (action.resultPerPage != -1) {
            strLIMIT = """LIMIT ${action.resultPerPage}"""
            offSet = """
                    OFFSET ${action.start}
                   """
        } else {
            action.resultPerPage = -1;
        }

        String strSql = """SELECT local_holiday.id,financial_year.name,territory_configuration.name AS territory,
                            STR_TO_DATE(CONCAT(local_holiday.day,'-',local_holiday.month,'-',local_holiday.year),'%d-%m-%YY') AS holiday_date,
                            local_holiday.note
                            FROM
                            local_holiday
                            INNER JOIN
                            financial_year
                            ON
                            financial_year.id=local_holiday.financial_year_id
                            INNER JOIN
                            territory_configuration
	                        ON
	                        territory_configuration.id=local_holiday.territory_configuration_id
                            ${territoryId}
                            ORDER BY ${action.sortCol} ${action.sortOrder}
                            ${strLIMIT}
                           ${offSet}
                          """
        List objList = sql.rows(strSql.toString())

        strSql = """SELECT COUNT(id) as total_rows FROM local_holiday
                            ${territoryId}
                          """

        List resultCount = sql.rows(strSql)
        return [objList: objList, count: resultCount?.get(0)?.total_rows]
    }

    @Transactional(readOnly = true)
    public List<LocalHoliday> list() {
        return LocalHoliday.list()
    }

    @Transactional(readOnly = true)
    public LocalHoliday read(Long id) {
        return LocalHoliday.read(id)
    }

    @Transactional(readOnly = true)
    public LocalHoliday search(String fieldName, String fieldValue) {
        String query = "from LocalHoliday as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return LocalHoliday.find(query)
    }

    @Transactional(readOnly = true)
    public List getExistingHoliday(Object local_holiday) {
        LocalHoliday localHoliday = (LocalHoliday) local_holiday
        sql = new Sql(dataSource)
        String strSql = """SELECT id,day  FROM local_holiday
                            WHERE
                            day=${localHoliday.day}
                            AND month=${localHoliday.month}
                            AND year=${localHoliday.year}
                            AND territory_configuration_id=${localHoliday.territoryConfiguration.id}
                          """
        List objList = sql.rows(strSql)
        return objList
    }
    @Transactional(readOnly = true)
    public List getExistingHolidayForEdit(Object local_holiday) {
        LocalHoliday localHoliday = (LocalHoliday) local_holiday
        sql = new Sql(dataSource)
        String strSql = """SELECT id,day  FROM local_holiday
                            WHERE
                            day=${localHoliday.day}
                            AND month=${localHoliday.month}
                            AND year=${localHoliday.year}
                            AND territory_configuration_id=${localHoliday.territoryConfiguration.id}
                            AND id!=${localHoliday.id}
                          """
        List objList = sql.rows(strSql)
        return objList
    }
}
