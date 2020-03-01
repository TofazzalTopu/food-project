/*
 * ****************************************************************
 * Copyright � 2010 Documentatm (TM) Limited. All rights reserved.
 * DOCUMENTA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * This software is the confidential and proprietary information of
 * Documentatm (TM) Limited ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Documentatm (TM) Limited.
 * *****************************************************************
 */

package com.docu.commons


import java.text.SimpleDateFormat
import java.text.DateFormat
import java.text.DateFormatSymbols
import java.text.ParseException

/**
 * File: DateUtil.java
 * Purpose: This class is used for date related functionality
 * @version 1.0
 * @author smziaur.rahman
 * Date------------Author---------------Changes
 * 08 Nov 2010     smziaur.rahman       Created
 * 15 Nov 2011     swapon.chandra       Modified
 */

public class DateUtil {
/**
 * Get date as string format ("dd-MM-yyyy")
 * @param date Util date
 * @return String String date
 */
    public static String[] monthLongNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]
    public static String[] monthShortNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
    private static int FIRST_WEEK_FIRST_DAY = 1;
    private static int FIRST_WEEK_LAST_DAY = 7;
    private static int SECOND_WEEK_FIRST_DAY = 8;
    private static int SECOND_WEEK_LAST_DAY = 14;
    private static int THIRD_WEEK_FIRST_DAY = 15;
    private static int THIRD_WEEK_LAST_DAY = 21;
    private static int FOURTH_WEEK_FIRST_DAY = 22;
    private static int FOURTH_WEEK_LAST_DAY = 28;
    private static int FIFTH_WEEK_FIRST_DAY = 29;
    private static String DATE_FORMAT_NOW = "ddMMyyyy_hhmmssa"

    /* To use TimeStamp with report file name */

    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    public static Date getDateAfterMonthAdded(Date pFromDate,int pAddedMonth) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(pFromDate);
        cal.add(Calendar.MONTH, pAddedMonth);
        Date dt = cal.getTime();
        return dt;
    }

    public static String getDateFormatAsString(Date pDate) {
        String stringDate = CommonConstants.EMPTY_STRING;
        DateFormat dateFormat = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
        if (pDate != null) {
            stringDate = dateFormat.format(pDate);
        }
        return stringDate;
    }

    public static String getCurrentDateFormatAsString() {
        DateFormat dateFormat = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
        return dateFormat.format(new Date());
    }

    public static String getNextDateFormatAsString() {
        DateFormat dateFormat = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
        return dateFormat.format(new Date() + 1);
    }

    public static String getFlexibleDateFormatAsString(Date pDate, String pFormat) {
        DateFormat dateFormat = null
        String stringDate = CommonConstants.EMPTY_STRING;
        if (pFormat != null) {
            dateFormat = new SimpleDateFormat(pFormat)
        }
        if (pDate != null) {
            stringDate = dateFormat.format(pDate)
        }
        return stringDate
    }

/**
 * Get date as Util date
 * @param pDate String Date
 * @return returnDate Util Date
 */
    public static Date getSimpleDate(String pDate) {
        if (pDate == null) {
            return null
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CommonConstants.DATE_FORMAT);
        if (pDate) {
            Date returnDate = null;
            try {
                returnDate = (Date) simpleDateFormat.parse(pDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return returnDate;
        }
    }


    /**
     * Get date as Util date
     * @param pDate String Date
     * @return returnDate Util Date
     */
    public static Date getSimpleDateWithFormat(String pDate, String format) {
        if (pDate == null) {
            return null
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        if (pDate) {
            Date returnDate = null;
            try {
                returnDate = (Date) simpleDateFormat.parse(pDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return returnDate;
        }
    }

    /**
     * Get date as Util date
     * @param pDate String Date
     * @return returnDate Util Date
     */
    public static Date getSimpleDateYMD(String pDate) {
        if (pDate == null) {
            return null
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CommonConstants.DATE_FORMAT_Y_M_D);
        if (pDate) {
            Date returnDate = null;
            try {
                returnDate = (Date) simpleDateFormat.parse(pDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return returnDate;
        }
    }
    /*
   * Check given date  with business date
   *
   * */

    public static boolean compareDates(String strDate, Date endDate) {
        Date startDate = getSimpleDate(strDate)
        if (strDate == null || strDate == "") {
            return false
        }
        if (!isValidateDate(strDate, "dd/MM/yyyy")) {
            return false
        }
        //String date = DateUtil.getFlexibleDateFormatAsString(endDate, CommonConstants.DATE_FORMAT)
        if (startDate.compareTo(endDate) <= 0) {
            return true
        }
        return false
    }
/**
 * Calculates the number of days between start and end dates, taking
 * into consideration leap years, year boundaries etc.
 * @since 28 Sep 2010
 * @param pStartDate the start date
 * @param pEndDate the end date, must be later than the start date
 * @return the number of days between the start and end dates
 */
    public static long countDaysBetween(Date pStartDate, Date pEndDate) {
        if (pEndDate.before(pStartDate)) {
            throw new IllegalArgumentException("The end date must be later than the start date");
        }
        //reset all hours mins and secs to zero on start date
        Calendar startCal = GregorianCalendar.getInstance();
        startCal.setTime(pStartDate);
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        long startTime = startCal.getTimeInMillis();

        //reset all hours mins and secs to zero on end date
        Calendar endCal = GregorianCalendar.getInstance();
        endCal.setTime(pEndDate);
        endCal.set(Calendar.HOUR_OF_DAY, 0);
        endCal.set(Calendar.MINUTE, 0);
        endCal.set(Calendar.SECOND, 0);
        long endTime = endCal.getTimeInMillis();

        return (endTime - startTime) / CommonConstants.MILLISECONDS_IN_DAY
    }
    /**
     * Get the day of a given date
     * @param pGivenDate Util Date
     * @return String day of the given date
     */
    public static String getDayofTheDate(Date pGivenDate) {
        String day = null;
        DateFormat format = new SimpleDateFormat("EEEE");
        try {
            day = format.format(pGivenDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }
    /**
     * Get a date by adding no of days
     * @param givenDate
     * @param noOfDays
     * @return Date return the required date
     */
    public static Date addToDate(Date givenDate, int noOfDays) {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.setTime(givenDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, noOfDays);
        return calendar.getTime()
    }
    /**
     * Get a date adding no of given years
     * @param givenDate
     * @param noOfDays
     * @return Date return the required date
     */
    public static Date getDateOfNextYear(Date givenDate, int years) {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.setTime(givenDate);
        calendar.add(Calendar.DATE, -1)
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime()
    }
    public static Date getDateOfPreviousYear(Date givenDate) {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.setTime(givenDate);
        calendar.add(Calendar.DATE, 1)
        calendar.add(Calendar.YEAR, -1);
        return calendar.getTime()
    }

    /**
     * Get a date adding no of given years
     * @param givenDate
     * @param noOfDays
     * @return Date return the required date
     */
    public static Date getDateOfYear(Date givenDate, int years) {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.setTime(givenDate);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime()
    }
    /**
     * Compare a date in between two dates
     * @param firstDate
     * @param lastDate
     * @param givenDate
     * @return
     */
    public static boolean checkInBetweenDates(Date firstDate, Date lastDate, Date givenDate) {
        if (givenDate.compareTo(firstDate) >= 0 && givenDate.compareTo(lastDate) <= 0) {
            return true
        } else {
            return false
        }
    }
    /**
     * Get system date
     * @return date system date
     */
    public static Date getCurrentSystemDate() {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime()
    }

    public static Date getCurrentSystemDateTime() {
        Calendar calendar = GregorianCalendar.getInstance()
        return calendar.getTime()
    }

    /**
     * This method returns the name of current day
     * @return String
     */
    public static String getDayName() {
        String[] dayNames = new DateFormatSymbols().getWeekdays();
        Calendar dateNow = Calendar.getInstance()
        String dayName = dayNames[dateNow.get(Calendar.DAY_OF_WEEK)]
        return dayName
    }

    /**
     * This method returns the name of specified day
     * @param Date
     * @return String
     */
    public static String getDayName(Date date) {
        String[] dayNames = new DateFormatSymbols().getWeekdays();
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(date)
        String dayName = dayNames[calendar.get(Calendar.DAY_OF_WEEK)]
        return dayName
    }

    public static int getWeekNumberOfMonth(Date date){
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(date)
        int weekNumber = calendar.get(Calendar.WEEK_OF_MONTH)
        return weekNumber
    }

    /**
     * Get year of the given date
     * @param date
     * @return int year
     */
    public static int getYear(Date date) {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.setTime(date)
        return calendar.get(Calendar.YEAR)
    }

    public static int getCurrentSystemYear() {
        Calendar calendar = GregorianCalendar.getInstance()
        return calendar.get(Calendar.YEAR)
    }

    /**
     * Get month of the given date
     * @param date
     * @return int year
     */
    public static int getMonth(Date date) {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.setTime(date)
        return (calendar.get(Calendar.MONTH) + 1)
    }

    /**
     * Get day of the given date
     * @param date
     * @return int day of month
     */
    public static int getDayOfMonth(Date date) {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.setTime(date)
        return calendar.get(Calendar.DAY_OF_MONTH)
    }
    /*
   * this method returns the start and end dates of current week as a map
    */

    /**
     * Get day of the given date
     * @param date
     * @return int day of week
     */
    public static int getDayOfWeek(Date date) {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.setTime(date)
        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    /*
   * this method returns the start and end dates of current week as a map
    */

    public static Map<String, String> getCurrentWeekStartDateAndEndDate() {
        Map<String, String> weekStartAndEndDatesMap = new HashMap<String, String>()
        String weekStartDate = ""
        String weekEndDate = ""
        Date startDate = null
        Date endDate = null
        Calendar calendar = null
        calendar = Calendar.getInstance()

        int day = calendar.get(Calendar.DAY_OF_MONTH)
        if (day >= FIRST_WEEK_FIRST_DAY && day <= FIRST_WEEK_LAST_DAY) {
            // calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, (FIRST_WEEK_FIRST_DAY - day))
            startDate = calendar.getTime();
            weekStartDate = DateUtil.getFlexibleDateFormatAsString(startDate, CommonConstants.DATE_FORMAT_Y_M_D);
            calendar = null
            calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, (FIRST_WEEK_LAST_DAY - day))
            endDate = calendar.getTime()
            weekEndDate = DateUtil.getFlexibleDateFormatAsString(endDate, CommonConstants.DATE_FORMAT_Y_M_D);
            println endDate
        }
        else if (day >= SECOND_WEEK_FIRST_DAY && day <= SECOND_WEEK_LAST_DAY) {
            calendar.add(Calendar.DATE, (SECOND_WEEK_FIRST_DAY - day))
            startDate = calendar.getTime();
            weekStartDate = DateUtil.getFlexibleDateFormatAsString(startDate, CommonConstants.DATE_FORMAT_Y_M_D);
            calendar = null
            calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, (SECOND_WEEK_LAST_DAY - day))
            endDate = calendar.getTime()
            weekEndDate = DateUtil.getFlexibleDateFormatAsString(endDate, CommonConstants.DATE_FORMAT_Y_M_D);
            println endDate
        }
        else if (day >= THIRD_WEEK_FIRST_DAY && day <= THIRD_WEEK_LAST_DAY) {
            calendar.add(Calendar.DATE, (THIRD_WEEK_FIRST_DAY - day))
            startDate = calendar.getTime();
            weekStartDate = DateUtil.getFlexibleDateFormatAsString(startDate, CommonConstants.DATE_FORMAT_Y_M_D);
            calendar = null
            calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, (THIRD_WEEK_LAST_DAY - day))
            endDate = calendar.getTime()
            weekEndDate = DateUtil.getFlexibleDateFormatAsString(endDate, CommonConstants.DATE_FORMAT_Y_M_D);
            println endDate

        }
        else if (day >= FOURTH_WEEK_FIRST_DAY && day <= FOURTH_WEEK_LAST_DAY) {
            calendar.add(Calendar.DATE, (FOURTH_WEEK_FIRST_DAY - day))
            startDate = calendar.getTime();
            weekStartDate = DateUtil.getFlexibleDateFormatAsString(startDate, CommonConstants.DATE_FORMAT_Y_M_D);
            calendar = null
            calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, (FOURTH_WEEK_LAST_DAY - day))
            endDate = calendar.getTime()
            weekEndDate = DateUtil.getFlexibleDateFormatAsString(endDate, CommonConstants.DATE_FORMAT_Y_M_D);
            println endDate
        }
        else if (day >= FIFTH_WEEK_FIRST_DAY) {
            calendar.add(Calendar.DATE, (FIFTH_WEEK_FIRST_DAY - day))
            startDate = calendar.getTime();
            weekStartDate = DateUtil.getFlexibleDateFormatAsString(startDate, CommonConstants.DATE_FORMAT_Y_M_D);
            calendar = null
            calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, (6 - (day - FIFTH_WEEK_FIRST_DAY)))
            endDate = calendar.getTime()
            weekEndDate = DateUtil.getFlexibleDateFormatAsString(endDate, CommonConstants.DATE_FORMAT_Y_M_D);
            println endDate
        }
        weekStartAndEndDatesMap.put("weekStartDate", weekStartDate)
        weekStartAndEndDatesMap.put("weekEndDate", weekEndDate)
        return weekStartAndEndDatesMap
    }

    /**
     * Get previous date of the given date
     * @param date
     * @return date
     */
    public static Date getPreviousDate(Date date) {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.setTime(date)
        calendar.add(Calendar.DATE, -1)
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime()
    }

    /**
     * Get next date of the given date
     * @param date
     * @return date
     */
    public static Date getNextDate(Date date) {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.setTime(date)
        calendar.add(Calendar.DATE, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime()
    }

    /**
     * Get calendar with the given date
     * @param date
     * @return calendar
     */
    public static GregorianCalendar getCalendar(Date date) {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.setTime(date)
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return (GregorianCalendar)calendar
    }

    /**
     * Get first date of the given date's month
     * @param date
     * @return date
     */
    public static Date getStartDateOfMonth(Date date) {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.setTime(date)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime()
    }

/**
 * Get last date of the given date's month
 * @param date
 * @param date
 * */
    public static Date getEndDateOfMonth(Date date) {
        Calendar calendar = GregorianCalendar.instance
        calendar.setTime(date)
        int day = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime()
    }

    /* Checks the given date is Last Date of the Month or Not */
    public static boolean isTheDateLastDateOfMonth(Date date){
        if((getEndDateOfMonth(date)).compareTo(date)==0){
            return true;
        }
        return false;
    }

    public static Date getDateAfterMonths(Date date, Integer noOfMonths) {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.setTime(date)
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MONTH, noOfMonths)
        return calendar.getTime()
    }

    public static Date getDateAfterMonths(Date date, float noOfMonths) {
        int noOfMonth = noOfMonths.intValue()
        int noOfDay = (noOfMonths - noOfMonth) * CommonConstants.NUMBER_OF_DAYS_IN_A_MONTH
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.setTime(date)
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MONTH, noOfMonth)
        calendar.add(Calendar.DATE, noOfDay)
        return calendar.getTime()
    }

    public static Date getDateAfterSeconds(Date date, Integer noOfSeconds) {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.setTime(date)
        calendar.add(Calendar.SECOND, noOfSeconds)
        return calendar.getTime()
    }

    public static String getCurrentTimeByTimeZone(TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat formatter =
        (SimpleDateFormat) DateFormat.getDateTimeInstance(DateFormat.LONG,
                DateFormat.LONG, Locale.US);
        formatter.setTimeZone(timeZone);
        formatter.applyPattern("dd-MM-yyyy h:mm:ss a z");
        String result = formatter.format(date);
        return result
    }

    /**
     * pDate takes string as dd-MM-yyyy, MM-dd-YYYY and so on
     * format takes date format any type such as dd/MM/yyyy
     * @return true when it is a date such as 29/02/2004
     * @return false when it is not a date such as 31/02/2004, 05/15/2005
     */
    public static boolean isValidateDate(String pDate, String format) {
        String date = pDate.replace('-', '/')
        boolean isDate = true
        try {
            DateFormat dateFormat = new SimpleDateFormat(format)
            dateFormat.setLenient(false)
            if (dateFormat.parse(date)) {
                isDate = true
            } else {
                isDate = false
            }
        } catch (ParseException e) {
            isDate = false
        } catch (IllegalArgumentException e) {
            isDate = false
        }
        return isDate
    }
    /**
     * for time validation
     * @param time
     * @return boolean
     */
    public static boolean isValidTime(String time) {
        String[] temp = time.split(":")
        String[] temp1 = temp[1].split(" ")

        int h = temp[0].toInteger()
        int m = temp1[0].toInteger()
        if (h > 12) {
            return false
        }
        if (m > 60) {
            return false
        }
        if ((temp1[1].toUpperCase().equals("PM")) || (temp1[1].toUpperCase().equals("AM"))) {
            return true
        } else {
            return false
        }
    }

    /**
     * for Month Name
     * @param String
     * @return String
     *
     * Month name
     */
    public static String getMonthNameFromDateString(String strDate) {
        Date date = getSimpleDateYMD(strDate)
        return getMonthName(date)
    }

    public static String getMonthName(Date date) {
        // Create a instance of calendar
        Calendar calendar = getCalendar(date)
        return monthLongNames[calendar.get(Calendar.MONTH)]
    }

    public static String getMonthNameWithYear(Date date) {
        // Create a instance of calendar
        Calendar calendar = getCalendar(date)
        int year = calendar.get(Calendar.YEAR)
        return monthLongNames[calendar.get(Calendar.MONTH)] + ", " + year
    }
    /**
     * for Month Name
     * @param String
     * @return String
     *
     * Month name
     */
    public static String getMonthShortNameByMonthIndex(int monthIndex) {
        //creates a array of string for months name
//        String[] nameOfMonth = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
        // Return month name
        return monthShortNames[monthIndex]
    }

    public static String getDatabaseDateFormatAsString(Date pDate, String pFormat) {
        DateFormat dateFormat = null
        String stringDate = null
        if (pFormat != null) {
            dateFormat = new SimpleDateFormat(pFormat)
        }
        if (pDate != null) {
            stringDate = dateFormat.format(pDate)
        }
        return stringDate
    }

    public static Date getFirstDate(int monthIndex) {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.set(Calendar.MONTH, monthIndex)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        return calendar.getTime()
    }

    public static Date getLastDate(int monthIndex) {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.set(Calendar.MONTH, monthIndex)
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        return calendar.getTime()
    }

    public static Date getFirstDate(Date date) {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.setTime(date)
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH))
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        return calendar.getTime()
    }

    public static Date getLastDate(Date date) {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.setTime(date)
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH))
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        return calendar.getTime()
    }

    public static String getNextYear() {
        Date currentDate = DateUtil.getCurrentSystemDate()
        int currentYear = DateUtil.getYear(currentDate)
        int nextYear = currentYear + 1
        return nextYear.toString()
    }

    public static isDateWithinSameMonth(Date date1, Date date2) {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.setTime(date1)
        int monthId1 = calendar.get(Calendar.MONTH)
        calendar.setTime(date2)
        int monthId2 = calendar.get(Calendar.MONTH)
        return monthId1 == monthId2
    }

    public static Date getDateOfCurrentMonth(Date currentDate, int dayOfMonth) {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.setTime(currentDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        return calendar.getTime()
    }
    
    public static float countYearsBetween(Date date1, Date date2){
        if (date1.after(date2)){
            Date date = date1
            date1 = date2
            date2 = date
        }
        float months = 0.0
        Calendar calendar1 = getCalendar(date1)
        Calendar calendar2 = getCalendar(date2)
        while (!calendar1.after(calendar2)){
            ++months
            calendar1.add(Calendar.MONTH, 1)
        }
        float years = months / 12.0
        return years
    }

    public static float countMonthBetween(Date date1, Date date2){
        if (date1.after(date2)){
            Date date = date1
            date1 = date2
            date2 = date
        }
        float months = 0.0
        Calendar calendar1 = getCalendar(date1)
        Calendar calendar2 = getCalendar(date2)
        while (!calendar1.after(calendar2)){
            ++months
            calendar1.add(Calendar.MONTH, 1)
        }

       return months
    }

    public static String getMonthName(int monthNo){
        return monthLongNames[monthNo - 1]
    }
    public static Date getFirstDayOfTheCurrentYear() {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.DAY_OF_YEAR,1);
        return c.getTime();
    }

    public static Date getFirstDateOfYear(Date date) {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.set(Calendar.YEAR, Integer.parseInt(new SimpleDateFormat("yyyy").format((Date) date)))
        calendar.set(Calendar.MONTH, 0)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        return calendar.getTime()
    }

    public static Date addMinute(Date givenDate, int minute) {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.setTime(givenDate);
        calendar.add(Calendar.MINUTE , minute)
        return calendar.getTime()
    }

    public static int countWeekInAMonth(Date date)
    {
        int count = 0,day = 0
        Calendar calendar = getCalendar(date)

        int weekDayIndex = calendar.get(Calendar.DAY_OF_WEEK)

        int year = getYear(date), month = getMonth(date)
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for(int i=1;i<=days;i++)
        {
            calendar.set(year,month -1,i)
            day = calendar.get(Calendar.DAY_OF_WEEK)
            if(day==weekDayIndex){
                count++
            }
        }

        return count
    }

    public static int countLeftWeekInAMonth(Date date)
    {
        int count = 0,day = 0,monthDay = 0
        Calendar calendar = getCalendar(date)

        int weekDayIndex = calendar.get(Calendar.DAY_OF_WEEK)
        int monthDayIndex = calendar.get(Calendar.DAY_OF_MONTH)

        int year = getYear(date), month = getMonth(date)
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for(int i=1;i<=days;i++)
        {
            calendar.set(year,month -1,i)
            day = calendar.get(Calendar.DAY_OF_WEEK)
            monthDay = calendar.get(Calendar.DAY_OF_MONTH)
            if(day==weekDayIndex && monthDay>=monthDayIndex){
                count++
            }
        }

        return count
    }

    public static List<String> getWeekDateInAMonth(Date date){
        int count = 0,day = 0
        List<String> dateList = []
        Date newDate = null
        Calendar calendar = getCalendar(date)

        int weekDayIndex = calendar.get(Calendar.DAY_OF_WEEK)

        int year = getYear(date), month = getMonth(date)
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for(int i=1;i<=days;i++)
        {
            calendar.set(year,month -1,i)
            day = calendar.get(Calendar.DAY_OF_WEEK)
            if(day==weekDayIndex){
                newDate = calendar.getTime();
                dateList.add(getFlexibleDateFormatAsString(newDate, CommonConstants.DATE_FORMAT_Y_M_D));
            }
        }

        return dateList
    }

    public static List<String> getLeftWeekDateInAMonth(Date date){
        int count = 0,day = 0,monthDay = 0
        List<String> dateList = []
        Date newDate = null
        Calendar calendar = getCalendar(date)

        int weekDayIndex = calendar.get(Calendar.DAY_OF_WEEK)
        int monthDayIndex = calendar.get(Calendar.DAY_OF_MONTH)

        int year = getYear(date), month = getMonth(date)
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for(int i=1;i<=days;i++)
        {
            calendar.set(year,month -1,i)
            day = calendar.get(Calendar.DAY_OF_WEEK)
            monthDay = calendar.get(Calendar.DAY_OF_MONTH)
            if(day==weekDayIndex && monthDay>=monthDayIndex){
                newDate = calendar.getTime();
                dateList.add(getFlexibleDateFormatAsString(newDate, CommonConstants.DATE_FORMAT_Y_M_D));
            }
        }

        return dateList
    }

    public static boolean isWithInRange(Date testDate,Date startDate,Date endDate){
        return testDate.after(startDate.minus(1)) && testDate.before(endDate.plus(1))
    }

    public static List<Date> getDateListWithinMonth(Date date, int day) {
        List<Date> dateList = []
        Date startDate = getStartDateOfMonth(date)
        Date endDate = getEndDateOfMonth(date)
        int dayOfWeek = getDayOfWeek(startDate)
        int diff = 0
        if (day > dayOfWeek) {
            diff = day - dayOfWeek
        } else if (day < dayOfWeek) {
            diff = day - dayOfWeek + 7
        }
        Calendar calendar = getCalendar(startDate)
        calendar.add(Calendar.DATE, diff)
        startDate = calendar.getTime()

        while (startDate.compareTo(endDate) < 1) {
            dateList.add(startDate)
            calendar.add(Calendar.DATE, 7)
            startDate = calendar.getTime()
        }
        return dateList
    }

    public static boolean isLeapYear(Date givenDate){
        Calendar calendar = getCalendar(givenDate)
        return calendar.isLeapYear(calendar.get(Calendar.YEAR))
    }

    public static int countDaysOfMonth(Date givenDate) {
        int monthMaxDays = 0
        Calendar calendar = Calendar.getInstance()
        int year = getYear(givenDate)
        int month = getMonth(givenDate)
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1)
        monthMaxDays = calendar.getActualMaximum(Calendar.DATE)
        return monthMaxDays
    }

    public static int countMaxDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance()
        GregorianCalendar myCalendar = new GregorianCalendar(year, month-1, 1);
        return myCalendar.getActualMaximum(calendar.DAY_OF_MONTH);
    }

    public static Date lastDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1)
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        return calendar.getTime()
    }

    public static Date firstDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        return calendar.getTime()
    }

    public static List<Date> getBiWeekDateListWithinMonth(Date date) {
        List<Date> dateList = []
        Date startDateTemp = getStartDateOfMonth(date)
        Date endDateTemp = getEndDateOfMonth(date)

        Calendar calendar1 = getCalendar(date)
        calendar1.add(Calendar.DATE, 0)
        Date startDate = calendar1.getTime()

        dateList.add(startDate)

        while (startDate.before(endDateTemp)) {
            calendar1.add(Calendar.DATE, 14)
            startDate = calendar1.getTime()
            if(startDate<=endDateTemp){
                dateList.add(startDate)
            }
        }

        Calendar calendar2 = getCalendar(date)
        calendar2.add(Calendar.DATE, 0)
        Date endDate = calendar2.getTime()

        while (endDate.after(startDateTemp)) {
            calendar2.add(Calendar.DATE, -14)
            endDate = calendar2.getTime()
            if(endDate>=startDateTemp){
                dateList.add(endDate)
            }
        }

        return dateList.sort()
    }

    public static String getMothIndexByName(String monthName) {
        int monthIndex = monthLongNames.findIndexOf {it == monthName}
        return monthIndex+1
    }

    /* Month Diff Between 2 Dates :: Example. beginningDate = 2014/01/01 and endingDate = 2014/12/25 */
    public static Integer getDifferenceInMonths(Date beginningDate, Date endingDate) {
        if (beginningDate == null || endingDate == null) {
            return 0;
        }

        Calendar startCalendar = new GregorianCalendar()
        startCalendar.setTime(beginningDate)
        Calendar endCalendar = new GregorianCalendar()
        endCalendar.setTime(endingDate)

        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR)

        int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH)

        return diffMonth
    }

    public static Date getLastDateOfYear(Date date) {
        Calendar calendar = GregorianCalendar.getInstance()
        calendar.set(Calendar.YEAR, Integer.parseInt(new SimpleDateFormat("yyyy").format((Date) date)))
        calendar.set(Calendar.MONTH, 11)
        calendar.set(Calendar.DAY_OF_MONTH, 31)
        return calendar.getTime()
    }

}