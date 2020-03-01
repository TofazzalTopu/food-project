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

import grails.converters.JSON
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import groovy.text.GStringTemplateEngine
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import org.springframework.stereotype.Component

/**
 * File: Example.java
 * Purpose: Brief description of this class
 * @version 1.0
 * @author User
 * Date------------Author---------------Changes
 * 01 Sep  2010     Doug Lea            Created
 * 12 Sep 2010      Doug Lea            Added new doc conventions
 */
@Component("jsonUtil")
class JsonUtil {

    @Autowired
    TransactionAwareDataSourceProxy dataSource
    @Autowired
    CommonUtil commonUtil
    @Autowired
    JsonUtilService jsonUtilService

    /**
     *
     * @param value
     * @param quote
     * @return String
     */
    public String enQuote(String value, String quote) {
        return quote + value + quote
    }

    /**
     *
     * @param value
     * @return String
     */
    public String wrapWithSingleQuote(String value) {
        return enQuote(value.replaceAll("'", "\\\\'"), "'")
    }

    /**
     *
     * @param value
     * @return String
     */
    public String wrapWithDoubleQuote(String value) {
        value = value.replaceAll("null", "")
        return enQuote(value.replaceAll('"', '\\\\"'), '"')
    }

    /**
     *
     * @param object Object source of value
     * @param property String contains the property name to be taken
     * @return String in the format of javascript array
     */
    public String getJavascriptArray(List objects, String property) {
        // Wrap value by double for the given property of given objects
        List arrayItems = objects.collect {
            wrapWithDoubleQuote(it.getAt(property).toString())
        }

        // Returns as javascript array
        return "[" + arrayItems.join(",") + "]"
    }

    /**
     *
     * @param object Object source of value
     * @param property String contains the property name to be taken
     * @return String in the format of javascript object, without key
     */
    public String getJavascriptObject(List objects, String property) {
        // Wrap value by double for the given property of given objects
        List arrayItems = objects.collect {
            wrapWithDoubleQuote(it.getAt(property).toString())
        }

        // Returns as javascript object
        return "{" + arrayItems.join(",") + "}"
    }

    /**
     *
     * @param object Object source of value
     * @param properties List contains the property name to be taken
     *                    element in the list may be String or List that
     *                    contains Map having List as its value
     *                    If a given property does not match then that
     *                    property will consider as its value
     *                    Example -
     *                    ['firstName', 'lastName', [address:['street','zipCode', city:['name']]], '<input />']
     * @return String in the format of javascript array of array
     */
    public String getJsonArray(List objects, List properties) {
        // Get string value for each object as javascript array format for given property
        List arrayItems = objects.collect { object ->
            getJsonArrayElements(object, properties)
        }

        // Return collected javascript array as javascript array
        return "[" + arrayItems.join(",") + "]"
    }

    /**
     *
     * @param object Object source of value
     * @param properties List contains the property name to be taken
     *                    element in the list may be String or List that
     *                    contains Map having List as its value
     *                    If a given property does not match then that
     *                    property will consider as its value
     *                    Example -
     *                    ['firstName', 'lastName', [address:['street','zipCode', city:['name']]], '<input />']
     * @return String in the format of javascript array of object
     */
    public String getJsonObject(List objects, List properties) {
        // Get string value for each object as javascript object format for given property
        List arrayItems = objects.collect { object ->
            getJsonObjectElements(object, properties)
        }

        // Return collected javascript object as javascript array
        return "[" + arrayItems.join(",") + "]"
    }

    /**
     *
     * @param object Object source of value
     * @param properties List contains the property name to be taken
     *                    element in the list may be String or List that
     *                    contains Map having List as its value
     *                    If a given property does not match then that
     *                    property will consider as its value
     *                    Example -
     *                    ['firstName', 'lastName', [address:['street','zipCode', city:['name']]], '<input />']
     * @return String in the format of javascript array
     */
    public String getJsonArrayElements(Object object, List properties) {
        List values = getValueList(object, properties)

        // Enclose each value by double quote and
        return values.collect {wrapWithDoubleQuote(it.toString())}.toString()
    }

    /**
     *
     * @param object Object source of value
     * @param properties List contains the property name to be taken
     *                    element in the list may be String or List that
     *                    contains Map having List as its value
     *                    If a given property does not match then that
     *                    property will consider as its value
     *                    Example -
     *                    ['firstName', 'lastName', [address:['street','zipCode', city:['name']]], '<input />']
     * @return String in the format of javascript object
     */
    public String getJsonObjectElements(Object object, List properties) {
        // Get property names and values of given object and properties
        // Enclose key by single quote and value by double quote, join in the key:value format
        List arrayItems = getValueMap(object, properties).collect {
            wrapWithSingleQuote(it.key.toString()) + ":" + wrapWithDoubleQuote(it.value.toString())
        }

        // Return property name and value as javascript object
        return "{" + arrayItems.join(",") + "}"
    }

    public String getJsonObjectElements(Object object, Map properties) {
        // Get property names and values of given object and properties
        // Enclose key by single quote and value by double quote, join in the key:value format
        List arrayItems = getValueMap(object, properties).collect {
            wrapWithDoubleQuote(it.key.toString()) + ":" + wrapWithDoubleQuote(it.value.toString())
        }

        // Return property name and value as javascript object
        return "{" + arrayItems.join(",") + "}"
    }

    /**
     *
     * @param object Object source of value
     * @param properties List contains the property name to be taken
     *                    element in the list may be String or List that
     *                    contains Map having List as its value
     *                    If a given property does not match then that
     *                    property will consider as its value
     *                    Example -
     *                    ['firstName', 'lastName', [address:['street','zipCode', city:['name']]], '<input />']
     * @return List containing property values for given properties
     */
    public List getValueList(Object object, List properties) {
        List list = []

        // Define a closure to parse string/template that are not
        // property of the object
        GStringTemplateEngine engine = new GStringTemplateEngine()
        def parseTemplate = {Map data, String template ->
            engine.createTemplate(template).make(data).toString()
        }

        // Loop the given properties to get values from given object
        properties.each { property ->
            // If a given property is map, then recursive call
            // to extract values from the map
            if (property instanceof java.util.Map) {
                //Loop through the given Map elements
                property.each { mapItem ->
                    // Get child object from current object
                    def child = object.properties.get(mapItem.key)
                    if (child == null) {
                        list.add("")
                    } else {
                        // Recursive call as given property is map
                        getValueList(child, mapItem.value.collect {it}).each {
                            list.add(it.toString())
                        }
                    }
                }
            }
            // If a given property found in the given object then take the value
            else if (object.properties.containsKey(property.toString())) {
                list.add(object.properties.get(property).toString())
            }
            // If given property not found in the given object,
            // then take the given property as value
            else {
                list.add(parseTemplate(object.getProperties(), property.toString()))
            }
        }

        return list
    }

    /**
     *
     * @param object Object source of value
     * @param properties List contains the property name to be taken
     *                    element in the list may be String or List that
     *                    contains Map having List as its value
     *                    If a given property does not match then that
     *                    property will consider as its value
     *                    Example -
     *                    ['firstName', 'lastName', [address:['street','zipCode', city:['name']]], '<input />']
     * @return Map containing property name as key and corresponding property value as value
     */
    public Map getValueMap(Object object, List properties) {
        Map map = [:]

        // Define a closure to parse string/template that are not
        // property of the object
        GStringTemplateEngine engine = new GStringTemplateEngine()
        def parseTemplate = {Map data, String template ->
            engine.createTemplate(template).make(data).toString()
        }

        // Loop the given properties to get values from given object
        properties.each { property ->
            // If a given property is map, then recursive call
            // to extract values from the map
            if (property instanceof java.util.Map) {

                //Loop through the given Map elements
                property.each { mapItem ->
                    // Get child object from current object
                    def child = object.properties.get(mapItem.key)
                    if (child == null) {
                        map.put(mapItem.key.toString(), "")
                    } else {
                        // Recursive call as given property is map
                        getValueMap(child, mapItem.value.collect {it}).each {
                            // Spell new key name for child property
                            StringBuffer key = new StringBuffer()
                            key.append(mapItem.key.toString())
                            key.append(it.key.toString().substring(0, 1).toUpperCase())
                            key.append(it.key.toString().substring(1))

                            map.put(key.toString(), it.value.toString())
                        }
                    }
                }
            }
            // If a given property found in the given object then take the value
            else if (object.properties.containsKey(property.toString())) {
                map.put(property.toString(), object.properties.get(property).toString())
            }
            // If given property not found in the given object,
            // then take the given property as value
            else {
                map.put(map.size(), parseTemplate(object.getProperties(), property.toString()))
            }
        }

        return map
    }

    public Map getValueMap(Object object, Map properties) {
        Map map = [:]

        // Define a closure to parse string/template that are not
        // property of the object
        GStringTemplateEngine engine = new GStringTemplateEngine()
        def parseTemplate = {Map data, String template ->
            engine.createTemplate(template).make(data).toString()
        }

        // Loop the given properties to get values from given object
        properties.each { propertyKey, property ->
            // If a given property is map, then recursive call
            // to extract values from the map
            if (property instanceof java.util.Map) {
                /*
                //Loop through the given Map elements
                println "I got a map..." + propertyKey
                property.each { mapItem ->
                  // Get child object from current object
                  def child = object.properties.get(mapItem.value.toString())
                  println child
                  if(child == null){
                    println "child is null...."
                    map.put(mapItem.key.toString(), "")
                  }else{
                    // Recursive call as given property is map
                    getValueMap(child, mapItem.value.collect {it}).each {
                      // Spell new key name for child property
                      StringBuffer key = new StringBuffer()
                      key.append(mapItem.key.toString())
                      key.append(it.key.toString().substring(0, 1).toUpperCase())
                      key.append(it.key.toString().substring(1))

                      map.put(key.toString() , it.value.toString())
                    }
                  }
                }
                */
            }
            // If a given property found in the given object then take the value
            else if (object.properties.containsKey(property.toString())) {
                map.put(propertyKey, object.properties.get(property).toString())
            }
            // If given property not found in the given object,
            // then take the given property as value
            else {
                map.put(propertyKey, parseTemplate(object.getProperties(), property.toString()))
            }
        }

        return map
    }

    /**
     *
     * @param objects
     * @param properties
     * @param pageNo
     * @param totalRecords
     * @return
     */
    public String getJqGridFeed(List objects, List properties, long pageNo, long resultsPerPage, long totalRecords) {

        List jqRows = []
        StringBuffer strBuffer = null;
        double totalPages = Math.ceil(totalRecords / resultsPerPage)
        int rowCounter = 1
        objects.each {row ->
            strBuffer = new StringBuffer()
            strBuffer.append('{"id":')
            strBuffer.append(rowCounter++)
            strBuffer.append(',"cell":')
            strBuffer.append(getJsonArrayElements(row, properties))
            strBuffer.append('}')
            jqRows.add(strBuffer.toString())
        }

        strBuffer = new StringBuffer()
        strBuffer.append('{"page":')
        strBuffer.append(pageNo.toString())
        strBuffer.append(',"total":')
        strBuffer.append(totalPages.toString())
        // strBuffer.append(totalRecords.toString())
        strBuffer.append(',"records":')
        strBuffer.append(totalRecords.toString())
        strBuffer.append(',"rows":[')
        strBuffer.append(jqRows.join(','))
        strBuffer.append(']}')

        //    strBuffer = new StringBuffer()
//    strBuffer.append('{"page":')
//    strBuffer.append(pageIndex)
//    strBuffer.append(',"total":')
//    strBuffer.append(pageCount)
//    strBuffer.append(',"records":"')
//    strBuffer.append(recordCount)
//    strBuffer.append('","rows":[')
//    strBuffer.append(jqRows.join(','))
//    strBuffer.append(']')
//    strBuffer.append(',"userdata":')
//    strBuffer.append(userDataToJson())
//    strBuffer.append('}')
//
//    return strBuffer.toString()

        return strBuffer.toString()
    }
/**
 *
 * @param objects
 * @param properties
 * @param pageNo
 * @param totalRecords
 * @return
 */
    public String getFlexGridFeed(List objects, List properties, String pageNo, String totalRecords) {
        List jqRows = []
        StringBuffer strBuffer = null;
        int rowCounter = 1
        objects.each {row ->
            strBuffer = new StringBuffer()
            strBuffer.append('{"id":')
            strBuffer.append(rowCounter++)
            strBuffer.append(',"cell":')
            strBuffer.append(getJsonArrayElements(row, properties))
            strBuffer.append('}')
            jqRows.add(strBuffer.toString())
        }

        strBuffer = new StringBuffer()
        strBuffer.append('{"page":')
        strBuffer.append(pageNo)
        strBuffer.append(',"total":')
        strBuffer.append(totalRecords)
        strBuffer.append(',"rows":[')
        strBuffer.append(jqRows.join(','))
        strBuffer.append(']}')

        return strBuffer.toString()
    }

    public String getSqlCalcFoundRowsSql(String sql) {
        sql = sql.toLowerCase()
        if (!sql.contains("sql_calc_found_rows")) {
            // sql = sql.replaceAll("select", "select sql_calc_found_rows")
            sql = sql.replaceFirst("select", "select sql_calc_found_rows")
        }

        return sql
    }


    public int getRecordCount(String sql) {
        def db = new Sql(dataSource)
        sql = getSqlCalcFoundRowsSql(sql)
        db.rows(sql)
        def result = db.rows("SELECT FOUND_ROWS() as recordCount")

        return Integer.parseInt(result.first().get("recordCount").toString())
    }

/*
  public int getRecordCount(String sql) {
    String query = new String(sql)
    query = query.toLowerCase()
    //int fromPos = query.indexOf("from")
    int toPos = query.indexOf("limit")
    query = query.substring(0, toPos)
    Sql db = new Sql(dataSource)
    List<GroovyRowResult> result = db.rows(query)
    return result.size()
  }
*/
//  public List formatSqlRowResultBad(GroovyRowResult sqlRowResult) {
//    List list = sqlRowResult.collect { key, val ->
//      val == null ? "" : val
//    }
//
//    List values = list.collect { val ->
//      if (val instanceof java.sql.Timestamp) {
//        String dateField = val.toString()
//        def matcher = dateField =~ /(.+)-(.+)-(.+) /
//        matcher.each {haystack, y, m, d -> dateField = y + "-" + m + "-" + d }
//        return dateField.toString()
//      }
//      else if(val instanceof java.lang.Double){
//        return CommonUtil.formatCurrency((double) val, ".", "")
//      }
//      else {
//        return val.toString()
//      }
//    }
//
//    return values
//  }

    public void formatSqlRowResult(GroovyRowResult sqlRowResult) {
        String dateField = ""
        def matcher = null
        sqlRowResult.each { key, val ->
            if (val == null) {
                sqlRowResult[key] = ""
            }
            else if (val instanceof java.lang.Double) {
                sqlRowResult[key] = CommonUtil.formatCurrency((double) val, ".", "")
            }
            else if (val instanceof java.sql.Timestamp) {
                dateField = val.toString()
                matcher = dateField =~ /(.+)-(.+)-(.+) /
                matcher.each {haystack, y, m, d -> dateField = y + "-" + m + "-" + d }
                sqlRowResult[key] = dateField
            }
        }
    }

    /*
      Map data = [:]
      data.put('data', [loan_name:'Total'])
      data.put('total', ['approved_loan_amount', 'approved_duration_in_months'])
      params['userdata'] = data
    */

    public String getJqGridFeed(List sqlResultSet, Map params, String pageIndex, String pageCount, String recordCount) {
        List jqRows = []
        Map userData = [:]
        if (params.containsKey('userdata') && params.userdata.containsKey('total')) {
            params.userdata.total.each { field ->
                userData.put(field, 0)
            }
        }

        StringBuffer strBuffer = null;
        int rowCounter = 1
        sqlResultSet.each {row ->

            userData.each { key, val ->
                if (row.containsKey(key) && row[key].toString().isNumber()) {
                    userData[key] = commonUtil.formatCurrency(Double.parseDouble(val.toString()) + Double.parseDouble(row[key].toString()), '.', '')
                }
            }

            formatSqlRowResult(row as GroovyRowResult)
            String data = new JSON(row.values()).toString()
            strBuffer = new StringBuffer()

            strBuffer.append('{"id":')
            //strBuffer.append(rowCounter++)
            strBuffer.append('"' + row.get('id') + '"')
            strBuffer.append(',"cell":')
            strBuffer.append(data)
            strBuffer.append('}')

            jqRows.add(strBuffer.toString())
        }

        if (params.containsKey('userdata') && params.userdata.containsKey('data')) {
            params.userdata.data.each { field, key ->
                userData.put(field, key)
            }
        }

        def userDataToJson = {
            List items = []
            userData.each {key, val ->
                if (val instanceof Map) {
                    items.add(wrapWithDoubleQuote(key.toString()) + ":" + new JSON(val).toString())
                } else if (val instanceof List) {
                    items.add(wrapWithDoubleQuote(key.toString()) + ":" + new JSON(val).toString())
                } else {
                    items.add(wrapWithDoubleQuote(key.toString()) + ":" + wrapWithDoubleQuote(val.toString()))
                }
            }

            return "{" + items.join(',') + "}"
        }

        strBuffer = new StringBuffer()
        strBuffer.append('{"page":')
        strBuffer.append(pageIndex)
        strBuffer.append(',"total":')
        strBuffer.append(pageCount)
        strBuffer.append(',"records":"')
        strBuffer.append(recordCount)
        strBuffer.append('","rows":[')
        strBuffer.append(jqRows.join(','))
        strBuffer.append(']')
        strBuffer.append(',"userdata":')
        strBuffer.append(userDataToJson())
        strBuffer.append('}')

        return strBuffer.toString()
    }

    public String getJqGridFeed(String sql, Map params, String resultCol, String col1, String col2) {
        String feed = ""
        try {
            int recordCount = jsonUtilService.getRecordCount(sql)
            String sortBy = params.sidx != null ? params.sidx.toString() : ''
            String sortOrder = params.sord != null ? params.sord.toString() : 'ASC'
            String pageSize = params.rows != null ? params.rows.toString() : (String) CommonConstants.SQL_PAGE_SIZE
            String pageIndex = params.page != null ? params.page.toString() : '1'
            String offset = (Integer.parseInt(pageIndex) * Integer.parseInt(pageSize)) - Integer.parseInt(pageSize)

            sql += createWhereClause(params)

            if (sortBy != '') {
                sql += " ORDER BY " + sortBy + " " + sortOrder
            }
            sql += " LIMIT " + pageSize + " offset " + offset
            //println sql



            List<GroovyRowResult> result = jsonUtilService.getRecordList(sql)
            result.each {
                it[resultCol.toLowerCase()] = it[col1.toLowerCase()] + '(' + it[col2.toLowerCase()] + ')'
            }
//            int recordCount = jsonUtilService.getRecordCount(sql)//getRecordCount(sql)
            String pageCount = Math.ceil(recordCount / Integer.parseInt(pageSize)) as String
            feed = getJqGridFeed(result, params, pageIndex, pageCount, recordCount.toString())
        }
        catch (Exception ex) {
            ex.printStackTrace()
        }
        return feed
    }

    public String getJqGridFeed(String sql, Map params) {
        return getJqGridFeed(sql, params, false)
    }

    public String getJqGridFeed(String sql, Map params, boolean debug) {
        String feed = ""
        try {
            int recordCount = jsonUtilService.getRecordCount(sql)

            String sortBy = params.sidx != null ? params.sidx.toString() : ''
            String sortOrder = params.sord != null ? params.sord.toString() : 'ASC'
            String pageSize = params.rows != null ? params.rows.toString() : (String) CommonConstants.SQL_PAGE_SIZE
            String pageIndex = params.page != null ? params.page.toString() : '1'
            String offset = (Integer.parseInt(pageIndex) * Integer.parseInt(pageSize)) - Integer.parseInt(pageSize)

            sql += createWhereClause(params)

            if (sortBy != '') {
                sql += " ORDER BY " + sortBy + " " + sortOrder
            }
            sql += " LIMIT " + pageSize + " offset " + offset
            //println sql

            if (debug) {
                println sql
            }

            List<GroovyRowResult> result = jsonUtilService.getRecordList(sql)
            String pageCount = Math.ceil(recordCount / Integer.parseInt(pageSize)) as String
            feed = getJqGridFeed(result, params, pageIndex, pageCount, recordCount.toString())

        } catch (Exception ex) {
            ex.printStackTrace()
        }

        return feed
    }

    // jqgrid feed method with address objects
    /**
     *
     * @param sql
     * @param params
     * @return
     */
    //change sohel
    public String getJqGridFeedWithAddresses(String sql, Map params) {
        return getJqGridFeedWithAddressesFieldOffice(sql, params, false)
    }
    /**
     *
     * @param sql
     * @param params
     * @param debug
     * @return
     */
    //added sohel
    public String getJqGridFeedWithAddressesFieldOffice(String sql, Map params, boolean debug) {

        String feed = ""
        try {
            int recordCount = jsonUtilService.getRecordCount(sql)
            String sortBy = params.sidx != null ? params.sidx.toString() : ''
            String sortOrder = params.sord != null ? params.sord.toString() : 'ASC'
            String pageSize = params.rows != null ? params.rows.toString() : (String) CommonConstants.SQL_PAGE_SIZE
            String pageIndex = params.page != null ? params.page.toString() : '1'
            String offset = (Integer.parseInt(pageIndex) * Integer.parseInt(pageSize)) - Integer.parseInt(pageSize)

            sql += createWhereClause(params)

            sql += " LIMIT " + pageSize + " offset " + offset

            if (debug) {
                println sql
            }

            List<GroovyRowResult> result = jsonUtilService.getRecordList(sql)
            String pageCount = Math.ceil(recordCount / Integer.parseInt(pageSize)) as String
            // processing for address objects
            for (GroovyRowResult row: result) {
                if ((row.containsKey("registeredaddressline1")) && (row.containsKey("businessadressline1"))) {
                    String registeredAddress = ""
                    String businessAddress = ""
                    String registeredAddressLine1 = row.get("registeredaddressline1").toString()
                    if (!(registeredAddressLine1.equals("null")) && (registeredAddressLine1 != "")) {
                        String registeredAddressLine2 = row.get("registeredaddressline2").toString()
                        String registeredCity = row.get("registeredcity").toString()
                        registeredAddress = concatOfficeAddress(registeredAddressLine1, registeredAddressLine2, registeredCity)
                    }
                    row.remove("registeredaddressline1")
                    row.remove("registeredaddressline2")
                    row.remove("registeredcity")
                    row.put("registeredAddress", registeredAddress)
                    String businessAddressLine1 = row.get("businessadressline1").toString()
                    if (!(businessAddressLine1.equals("null")) && (businessAddressLine1 != "")) {
                        String businessAddressLine2 = row.get("businessaddressline2").toString()
                        String businessCity = row.get("businesscity").toString()
                        businessAddress = concatOfficeAddress(businessAddressLine1, businessAddressLine2, businessCity)
                    }
                    row.remove("businessadressline1")
                    row.remove("businessaddressline2")
                    row.remove("businesscity")
                    row.put("businessAddress", businessAddress)

                }
            }
            feed = getJqGridFeed(result, params, pageIndex, pageCount, recordCount.toString())

        } catch (Exception ex) {
            ex.printStackTrace()
        }

        return feed
    }

    public String getJqGridFeedWithAddresses(String sql, Map params, boolean debug) {

        String feed = ""
        try {
            int recordCount = jsonUtilService.getRecordCount(sql)
            String sortBy = params.sidx != null ? params.sidx.toString() : ''
            String sortOrder = params.sord != null ? params.sord.toString() : 'ASC'
            String pageSize = params.rows != null ? params.rows.toString() : (String) CommonConstants.SQL_PAGE_SIZE
            String pageIndex = params.page != null ? params.page.toString() : '1'
            String offset = (Integer.parseInt(pageIndex) * Integer.parseInt(pageSize)) - Integer.parseInt(pageSize)

            sql += createWhereClause(params)

            sql += " LIMIT " + pageSize + " offset " + offset

            if (debug) {
                println sql
            }

            List<GroovyRowResult> result = jsonUtilService.getRecordList(sql)
            String pageCount = Math.ceil(recordCount / Integer.parseInt(pageSize)) as String
            // processing for address objects
            for (GroovyRowResult row: result) {
                if ((row.containsKey("registeredAddressLine1")) && (row.containsKey("businessAdressLine1"))) {
                    String registeredAddress = ""
                    String businessAddress = ""
                    String registeredAddressLine1 = row.get("registeredAddressLine1").toString()
                    if (!(registeredAddressLine1.equals("null")) && (registeredAddressLine1 != "")) {
                        String registeredAddressLine2 = row.get("registeredAddressLine2").toString()
                        String registeredCity = row.get("registeredCity").toString()
                        registeredAddress = concatOfficeAddress(registeredAddressLine1, registeredAddressLine2, registeredCity)
                    }
                    row.remove("registeredAddressLine1")
                    row.remove("registeredAddressLine2")
                    row.remove("registeredCity")
                    row.put("registeredAddress", registeredAddress)
                    String businessAddressLine1 = row.get("businessAdressLine1").toString()
                    if (!(businessAddressLine1.equals("null")) && (businessAddressLine1 != "")) {
                        String businessAddressLine2 = row.get("businessAddressLine2").toString()
                        String businessCity = row.get("businessCity").toString()
                        businessAddress = concatOfficeAddress(businessAddressLine1, businessAddressLine2, businessCity)
                    }
                    row.remove("businessAdressLine1")
                    row.remove("businessAddressLine2")
                    row.remove("businessCity")
                    row.put("businessAddress", businessAddress)

                }
            }
            feed = getJqGridFeed(result, params, pageIndex, pageCount, recordCount.toString())

        } catch (Exception ex) {
            ex.printStackTrace()
        }

        return feed
    }
    /**
     *
     * @param addressLine1
     * @param addressLine2
     * @param addressCity
     * @return
     */
    /*Modified By Sohel*/
    private String concatOfficeAddress(String addressLine1, String addressLine2, String addressCity) {
        String concatAddress = addressLine1
        if(addressLine2 != "null" && addressLine2 != ""){
            concatAddress +=  "," + addressLine2
        }
        if (addressCity != "null" && addressCity != ""){
            concatAddress += "," + addressCity
        }
        concatAddress = concatAddress.replaceAll("null", "")
        return concatAddress
    }


    public String getAutoCompleteFeed(List sqlResultSet) {
        List feedItems = []
        sqlResultSet.collect { row ->
            List list = []
            row.each { key, val ->
                list.add(
                        wrapWithDoubleQuote(key.toString()) + ":" + wrapWithDoubleQuote(val.toString()
                        )
                )
            }
            feedItems.add("{" + list.join(",") + "}")
        }

        return feedItems.toString()
    }


    public String getFeed(List listOfMaps) {
        List feedItems = []
        listOfMaps.collect { row ->
            List list = []
            row.each { key, val ->
                list.add(
                        wrapWithDoubleQuote(key.toString()) + ":" + wrapWithDoubleQuote(val.toString()
                        )
                )
            }
            feedItems.add("{" + list.join(",") + "}")
        }

        return feedItems.toString()
    }


    public String createWhereClause(Map params) {
        List list = []

        params.whereLike.each { key, val ->
            if (val != '' && !(val instanceof Map)) {
                list.add(key + " LIKE '%" + val + "%'")
            }
        }

        params.whereBetween.each { key, val ->
            if (val != '' && !(val instanceof Map)) {
                list.add(val)
            }
        }

        String clauses = ""
        if (list.size()) {
            clauses = " AND " + list.join(' AND ')
        }

        return clauses
    }
}
