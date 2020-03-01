package com.bits.bdfp.settings

import com.bits.bdfp.settings.bussinessday.BusinessDay
import com.bits.bdfp.settings.bussinessday.BusinessDayTime
import com.bits.bdfp.settings.bussinessday.BusinessMonth
import com.bits.bdfp.settings.bussinessday.FinancialYear
import com.docu.commons.CommonConstants
import com.docu.commons.InternationalizationService
import groovy.sql.Sql
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import org.springframework.transaction.annotation.Transactional

class FinancialYearService extends InternationalizationService {
    static transactional = false

    TransactionAwareDataSourceProxy dataSource

    @Transactional(readOnly = true)
    public FinancialYear read(Map params) {
        return FinancialYear.read(params.id)
    }

    @Transactional(readOnly = true)
    public Map list(Map params) {
        List<FinancialYear> financialYearList = []
        long financialYearCount = 0
        try {
            def db = new Sql(dataSource)
            String strSql = ""
            strSql = """SELECT financial_year.*,application_user.username,a.username AS user_updated FROM
                         financial_year
                         INNER JOIN
                         application_user
                         ON
                         financial_year.user_created_id=application_user.id
                         LEFT OUTER JOIN
                         application_user a
                         ON
			             a.id=financial_year.user_last_updated_id
			             ORDER BY ${params.sortCol.toString()} ${params.sortOrder.toString()}
			             LIMIT ${Integer.parseInt(params.start.toString())},${
                Integer.parseInt(params.resultPerPage.toString())
            }"""
            financialYearList = db.rows(strSql)
            strSql = """ SELECT financial_year.*,application_user.username,a.username AS user_updated FROM
                         financial_year
                         INNER JOIN
                         application_user
                         ON
                         financial_year.user_created_id=application_user.id
                         LEFT OUTER JOIN
                         application_user a
                         ON
			             a.id=financial_year.user_last_updated_id
			             ORDER BY ${params.sortCol.toString()} ${params.sortOrder.toString()}
			             """
            financialYearCount = db.rows(strSql)?.size()

//            financialYearList = FinancialYear.withCriteria {
//
//
//                maxResults(Integer.parseInt(params.resultPerPage.toString()))
//                firstResult(Integer.parseInt(params.start.toString()))
//                order(params.sortCol.toString(), params.sortOrder.toString())
//            }
//            financialYearCount = FinancialYear.count()
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return [financialYearList: financialYearList, financialYearCount: financialYearCount]
    }

    @Transactional
    public boolean save(FinancialYear financialYear) {
        try {
            financialYear.save()
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return true
    }

    @Transactional
    public boolean update(FinancialYear financialYear) {
        try {
            financialYear.save()
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return true
    }

    @Transactional
    public delete(FinancialYear financialYear) {
        try {


            financialYear.delete()

        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return true
    }

    @Transactional(readOnly = true)
    public FinancialYear search(String fieldName, String fieldValue) {
        String query = "from FinancialYear as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return FinancialYear.find(query)
    }

    @Transactional(readOnly = true)
    public boolean financialYearDateBetween(Object params) {
        def db = new Sql(dataSource)
        boolean dateRangeExist = false
        String strSql = """
                          SELECT financial_year.date_start,financial_year.date_end FROM  financial_year
                          WHERE
                          (SELECT DATE_FORMAT(MIN(date_start),'%Y-%m-%d') FROM financial_year) BETWEEN (SELECT STR_TO_DATE('${
            params.dateStart
        }','%d-%m-%Y'))
                          AND  (SELECT STR_TO_DATE('${params.dateEnd}','%d-%m-%Y'))
                          UNION
                          SELECT financial_year.date_start,financial_year.date_end FROM  financial_year
                          WHERE
                          (SELECT DATE_FORMAT(MAX(date_end),'%Y-%m-%d') FROM financial_year) BETWEEN (SELECT STR_TO_DATE('${
            params.dateStart
        }','%d-%m-%Y'))
                          AND  (SELECT STR_TO_DATE('${params.dateEnd}','%d-%m-%Y'))
                          UNION
                          SELECT financial_year.date_start,financial_year.date_end FROM  financial_year
                          WHERE
                            (SELECT STR_TO_DATE('${params.dateStart}','%d-%m-%Y')) BETWEEN (SELECT DATE_FORMAT(MIN(date_start),'%Y-%m-%d') FROM financial_year)
                          AND  (SELECT DATE_FORMAT(MIN(date_end),'%Y-%m-%d') FROM financial_year)
                          UNION
                          SELECT financial_year.date_start,financial_year.date_end FROM  financial_year
                          WHERE
                          (SELECT STR_TO_DATE('${params.dateEnd}','%d-%m-%Y')) BETWEEN
                          (SELECT DATE_FORMAT(MAX(date_start),'%Y-%m-%d') FROM financial_year)
                          AND  (SELECT DATE_FORMAT(MAX(date_end),'%Y-%m-%d') FROM financial_year)
                        """
        List list = db.rows(strSql)
        if (list && list.size() > 0) {
            dateRangeExist = true
        }
        return dateRangeExist
    }

    @Transactional(readOnly = true)
    public boolean isFinancialYearOpen(Object params) {
        def db = new Sql(dataSource)
        boolean financialYearOpen = false
        String strSql = """SELECT * FROM financial_year
                         WHERE is_opened IS TRUE"""
        List list = db.rows(strSql)
        if (list && list.size() > 0) {
            financialYearOpen = true
        }
        return financialYearOpen
    }

    @Transactional(readOnly = true)
    public List financialYearOpen() {
        def db = new Sql(dataSource)
        boolean financialYearOpen = false
        String strSql = """SELECT * FROM financial_year
                         WHERE is_opened IS TRUE"""
        List list = db.rows(strSql)
        return list
    }

    public List searchBetweenDates(Date startDate, Date endDate) {
        Date begin = new Date(startDate.getTime());
        List<Date> dates = new ArrayList<Date>();
        dates.add(new Date(begin.getTime()));
        while (begin.compareTo(endDate) < 0) {
            begin = new Date(begin.getTime() + 86400000);
            dates.add(new Date(begin.getTime()));
        }
        return dates;
    }

    @Transactional
    public boolean saveFinancialYearAndBusinessDayTime(Object object) {

        FinancialYear financialYearObj = object.get(FinancialYear.class.simpleName)
        List businessDayTimeList = object.get("businessDayTimeList")
        if (financialYearObj.save()) {
            businessDayTimeList.each {
                it.save()
            }
            return true
        } else {
            return false
        }

    }

    @Transactional
    public List getBusinessDayListFromCurrentDaysToLast(params) {
        List<BusinessDayTime> businessDayTimeList = []
        params.put("id", params?.financialYearId)

        Calendar cal = Calendar.getInstance()
        cal.setTime(new Date())
        int month = cal.get(Calendar.MONTH)
        int date = cal.get(Calendar.DATE)
        Date today = new Date()
        today = today - 1
        businessDayTimeList = BusinessDayTime.withCriteria {
            and {
                eq("financialYear", read(params))
                ge("businessDate", today)
            }
        }
        return businessDayTimeList

    }

    @Transactional
    public boolean saveBusinessDayTime(Object object) {
        try {
            List<BusinessDayTime> businessDayTimeList = object.get(BusinessDayTime.class.simpleName)
            businessDayTimeList.each {
                it.save()
            }
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return true
    }

    @Transactional
    public boolean updateFinancialYearAndBusinessDayTime(Object object) {
        try {
            FinancialYear financialYearObj = object.get(FinancialYear.class.simpleName)
            def db = new Sql(dataSource)
            String strSql = """DELETE  FROM business_day_time WHERE financial_year_id=${financialYearObj.id}"""
            db.executeUpdate(strSql)
            List businessDayTimeList = object.get("businessDayTimeList")
            financialYearObj.save()
            businessDayTimeList.each {
                it.save()
            }

        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return true
    }

    @Transactional(readOnly = true)
    public boolean financialYearDateBetweenForUpdate(Object params) {
        def db = new Sql(dataSource)
        boolean dateRangeExist = false
        String strSql = """
                          SELECT financial_year.date_start,financial_year.date_end FROM  financial_year
                          WHERE
                          (SELECT DATE_FORMAT(MIN(date_start),'%Y-%m-%d') FROM financial_year  WHERE  financial_year.id!=${
            params.id
        }) BETWEEN (SELECT STR_TO_DATE('${params.dateStart}','%d-%m-%Y'))
                          AND  (SELECT STR_TO_DATE('${params.dateEnd}','%d-%m-%Y'))
                          AND financial_year.id!=${params.id}
                          UNION
                          SELECT financial_year.date_start,financial_year.date_end FROM  financial_year
                          WHERE
                          (SELECT DATE_FORMAT(MAX(date_end),'%Y-%m-%d') FROM financial_year  WHERE  financial_year.id!=${
            params.id
        }) BETWEEN (SELECT STR_TO_DATE('${params.dateStart}','%d-%m-%Y'))
                          AND  (SELECT STR_TO_DATE('${params.dateEnd}','%d-%m-%Y'))
                          AND financial_year.id!=${params.id}
                        """
        List list = db.rows(strSql)
        if (list && list.size() > 0) {
            dateRangeExist = true
        }
        return dateRangeExist
    }


    @Transactional(readOnly = true)
    public Map getBusinessDayList(Map params) {
        def db = new Sql(dataSource)
        List<FinancialYear> businessDayList = []
        long businessDayCount = 0
        String strSql = """SELECT business_day.id,business_day.business_date,business_day.opened_from,business_day.is_open,
                         application_user.username,a.username AS user_updated,business_day.date_created,business_day.date_last_updated
                         FROM
                         business_day
                         INNER JOIN
                         application_user
                         ON
                         business_day.user_created_id=application_user.id
                         LEFT OUTER JOIN
                         application_user a
                         ON
			             a.id=business_day.user_last_updated_id
			             ORDER BY ${params.sortCol.toString()} ${params.sortOrder.toString()}
			             LIMIT ${Integer.parseInt(params.start.toString())},${
            Integer.parseInt(params.resultPerPage.toString())
        } """
        businessDayList = db.rows(strSql)
        strSql = """SELECT business_day.id,business_day.business_date,business_day.opened_from,business_day.is_open,
                         application_user.username,a.username AS user_updated,business_day.date_created,business_day.date_last_updated
                         FROM
                         business_day
                         INNER JOIN
                         application_user
                         ON
                         business_day.user_created_id=application_user.id
                         LEFT OUTER JOIN
                         application_user a
                         ON
			             a.id=business_day.user_last_updated_id
			             ORDER BY ${params.sortCol.toString()} ${params.sortOrder.toString()}"""

        businessDayCount = db.rows(strSql)?.size()
        return [businessDayList: businessDayList, businessDayCount: businessDayCount]
    }


    @Transactional(readOnly = true)
    public Map getBusinessDayTimeList(Map params) {
        def db = new Sql(dataSource)
        List<BusinessDayTime> businessDayTimeList = []
        long businessDayTimeCount = 0
        String strSql = """SELECT business_day_time.id,DATE_FORMAT(business_day_time.business_date,'%d-%m-%Y') AS business_date,business_day_time.start_time,business_day_time.end_time,
                           business_day_time.month_string,financial_year.name
                           FROM
                           business_day_time
                           INNER JOIN
                           financial_year
                           ON
                           financial_year.id=business_day_time.financial_year_id
			               ORDER BY ${params.sortCol.toString()} ${params.sortOrder.toString()}
			               LIMIT ${Integer.parseInt(params.start.toString())},${
            Integer.parseInt(params.resultPerPage.toString())
        } """
        businessDayTimeList = db.rows(strSql)
        strSql = """SELECT business_day_time.id,business_day_time.business_date,business_day_time.start_time,business_day_time.end_time,
                           business_day_time.month_string,financial_year.name
                           FROM
                           business_day_time
                           INNER JOIN
                           financial_year
                           ON
                           financial_year.id=business_day_time.financial_year_id  """

        businessDayTimeCount = db.rows(strSql)?.size()
        return [businessDayTimeList: businessDayTimeList, businessDayTimeCount: businessDayTimeCount]
    }

    @Transactional(readOnly = true)
    public List getBusinessMonthDetails(Object params) {
        def db = new Sql(dataSource)
        String strSql = """SELECT DISTINCT business_day_time.month AS month_val,
                           CONCAT(business_day_time.month_string,'-',business_day_time.year) AS month_string ,
                           business_day_time.year
                           FROM business_day_time
                           WHERE financial_year_id=${params.id}"""
        List list = db.rows(strSql)
        return list
    }

    @Transactional(readOnly = true)
    public List getOpenedBusinessMonthDetails(Object params) {
        def db = new Sql(dataSource)
        String strSql = """SELECT DISTINCT business_day_time.month AS month_val,
                           CONCAT(business_day_time.month_string,'-',business_day_time.year) AS month_string ,
                           business_day_time.year
                           FROM business_day_time
                           INNER JOIN
                           business_month
                           ON
                           business_month.financial_year_id=business_day_time.financial_year_id
                           AND business_month.month=business_day_time.month
                           WHERE business_month.financial_year_id=${params.id}
                           AND business_month.is_open IS TRUE """
        List list = db.rows(strSql)
        return list
    }

    @Transactional(readOnly = true)
    public List getBusinessDayDetails(Object params) {
        def db = new Sql(dataSource)
        String strSql = """SELECT business_day_time.date,business_day_time.business_date
                            FROM
                            business_day_time
                            WHERE financial_year_id=${params.yearId}
                            AND month=${params.monthId}
                            AND year=${params.yearVal}"""
        List list = db.rows(strSql)
        return list
    }

    @Transactional(readOnly = true)
    public List getOpenedBusinessDay(Object params) {
        def db = new Sql(dataSource)
        String strSql = """SELECT business_day.id,business_day_time.start_time,business_day_time.end_time
                            FROM business_day
                            INNER JOIN
                            business_day_time ON (business_day_time.business_date=business_day.business_date)
                            WHERE business_day.is_open IS TRUE
                            AND CURRENT_TIME >= business_day_time.start_time
                            AND CURRENT_TIME <= business_day_time.end_time"""
        List list = db.rows(strSql)
        return list
    }

    @Transactional
    public boolean saveBusinessDay(Object object) {
        try {
            BusinessDay businessDay = object.get(BusinessDay.class.simpleName)
            businessDay.save(false)
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return true
    }

    @Transactional(readOnly = true)
    public boolean isBusinessDayOpen(Object params) {
        def db = new Sql(dataSource)
        boolean businessDayOpen = false
        String strSql = """SELECT * FROM business_day
                            WHERE
                            is_open IS TRUE
                            AND YEAR(business_date)=${params?.yearVal}
                            AND MONTH(business_date)=${params?.month}"""
        List list = db.rows(strSql)
        if (list && list.size() > 0) {
            businessDayOpen = true
        }
        return businessDayOpen
    }
    @Transactional(readOnly = true)
    public boolean isBusinessHoliday(Object params) {
        def db = new Sql(dataSource)
        boolean isBusinessHoliday = false
        String strSql = """SELECT * FROM business_holiday
                            WHERE
                            year=${params?.yearVal}
                            AND month=${params?.month}
                            AND day=${params?.day}"""
        List list = db.rows(strSql)
        if (list && list.size() > 0) {
            isBusinessHoliday = true
        }
        return isBusinessHoliday
    }

    @Transactional(readOnly = true)
    public List getEditBusinessDayDetails(Object params) {
        def db = new Sql(dataSource)
        String strSql = """SELECT
                            id,
                            version,
                            MONTH(business_date) AS month,
                            DAY(business_date) AS day,is_open
                            FROM business_day WHERE id=${params.id}"""
        List list = db.rows(strSql)
        return list
    }

    @Transactional(readOnly = true)
    public List getEditBusinessMonthDetails(Object params) {
        def db = new Sql(dataSource)
        String strSql = """SELECT
                            id,
                            version,
                            month,
                            is_open
                            FROM business_month WHERE id=${params.id}"""
        List list = db.rows(strSql)
        return list
    }

    @Transactional(readOnly = true)
    public boolean isSelectedBusinessDayOpen(Object params) {
        def db = new Sql(dataSource)
        boolean businessDayOpen = false
        String strSql = """SELECT * FROM business_day
                            WHERE
                            is_open IS TRUE
                            AND YEAR(business_date)=${params?.yearVal}
                            AND MONTH(business_date)=${params?.month}
                            AND id!=${params.id}"""
        List list = db.rows(strSql)
        if (list && list.size() > 0) {
            businessDayOpen = true
        }
        return businessDayOpen
    }

    @Transactional(readOnly = true)
    public boolean isSelectedBusinessMonthOpen(Object params) {
        def db = new Sql(dataSource)
        boolean businessMonthOpen = false
        String strSql = """SELECT * FROM business_month
                            WHERE
                            is_open IS TRUE
                            AND financial_year_id=${params?.financialYear.id}
                            AND id!=${params.id}"""
        List list = db.rows(strSql)
        if (list && list.size() > 0) {
            businessMonthOpen = true
        }
        return businessMonthOpen
    }

    @Transactional(readOnly = true)
    public BusinessDay businessDay(Object params) {
        BusinessDay.read(params.id)
    }

    @Transactional(readOnly = true)
    public BusinessMonth businessMonth(Object params) {
        BusinessMonth.read(params.id)
    }

    @Transactional(readOnly = true)
    public List<FinancialYear> openedFinancialYearList() {
        List financialYearList = FinancialYear.createCriteria().list {
            and {
                eq("isOpened", true)
            }
        }
        return financialYearList
    }

    @Transactional(readOnly = true)
    public boolean isBusinessMonthOpen(Object params) {
        def db = new Sql(dataSource)
        boolean businessMonthOpen = false
        String strSql = """SELECT * FROM business_month
                            WHERE
                            is_open IS TRUE
                            /*AND financial_year_id=${params?.financialYear.id}*/
                        """
        List list = db.rows(strSql)
        if (list && list.size() > 0) {
            businessMonthOpen = true
        }
        return businessMonthOpen
    }

    @Transactional(readOnly = true)
    public List getBusinessMonthStartAndEndDate(Object params) {
        def db = new Sql(dataSource)
        String strSql = """SELECT MIN(business_date) AS start_date,MAX(business_date) AS end_date,month_string
                            FROM
                            business_day_time
                            WHERE
                            month=${params.month}
                            AND financial_year_id=${params?.financialYear.id}"""
        List list = db.rows(strSql)
        return list
    }

    @Transactional
    public boolean saveBusinessMonth(Object object) {
        try {
            BusinessMonth businessMonth = object.get(BusinessMonth.class.simpleName)
            businessMonth.save(false)
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return true
    }

    @Transactional(readOnly = true)
    public Map getBusinessMonthList(Map params) {
        def db = new Sql(dataSource)
        List<FinancialYear> businessMonthList = []
        long businessMonthCount = 0
        String strSql = """SELECT business_month.id,business_month.start_date,business_month.end_date,business_month.open_date,
                            business_month.close_date,business_month.is_open,business_month.month_name,financial_year.name,
                            application_user.username,a.username AS user_updated,business_month.date_created,business_month.date_last_updated
                            FROM
                            business_month
                            INNER JOIN
                            financial_year
                            ON
                            financial_year.id=business_month.financial_year_id
                            INNER JOIN
                            application_user
                            ON
                            business_month.user_created_id=application_user.id
                            LEFT OUTER JOIN
                            application_user a
                            ON
                            a.id=business_month.user_last_updated_id
                            ORDER BY ${params.sortCol.toString()} ${params.sortOrder.toString()}
                            LIMIT ${Integer.parseInt(params.start.toString())},${
            Integer.parseInt(params.resultPerPage.toString())
        } """
        businessMonthList = db.rows(strSql)
        strSql = """SELECT business_month.id
                            FROM
                            business_month
                            INNER JOIN
                            financial_year
                            ON
                            financial_year.id=business_month.financial_year_id
                            INNER JOIN
                            application_user
                            ON
                            business_month.user_created_id=application_user.id
                            LEFT OUTER JOIN
                            application_user a
                            ON
                            a.id=business_month.user_last_updated_id
                            ORDER BY ${params.sortCol.toString()} ${params.sortOrder.toString()}"""

        businessMonthCount = db.rows(strSql)?.size()
        return [businessMonthList: businessMonthList, businessMonthCount: businessMonthCount]
    }

    @Transactional(readOnly = true)
    public List getBusinessMonthDetailsForHoliday() {
        def db = new Sql(dataSource)
        String strSql = """SELECT DISTINCT CONCAT(business_day_time.month,'-',business_day_time.year) AS month_val,
                           CONCAT(business_day_time.month_string,'-',business_day_time.year) AS month_string ,
                           business_day_time.year
                           FROM business_day_time
                           INNER JOIN
                           financial_year
			               ON
			               financial_year.id=business_day_time.financial_year_id
                           WHERE financial_year.is_opened IS TRUE"""
        List list = db.rows(strSql)
        return list
    }
}
