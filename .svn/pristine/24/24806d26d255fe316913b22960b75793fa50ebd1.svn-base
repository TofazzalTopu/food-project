package com.docu.commons.sqlgen

import com.docu.commons.CommonConstants
import com.docu.commons.DateUtil

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by rafiqul.islam on 11/16/2014.
 */
abstract class AbstractQueryGenerator {
    private static final String UNDER_SCORE = "_"
    private static final String UPDATE = "UPDATE "
    private static final String DELETE = "DELETE "
    private static final String FROM = "FROM "
    private static final String VERSION = "version"
    private static final String VERSION_VERSION_PLUS_1 = "version=version+1,"
    private static final String SET = "SET "
    private static final String AND = "AND "
    public static final String WHERE = "WHERE "
    public static final String ID = "id="
    public static final String LAST_UPDATED = "lastUpdated"
    public static final String ESCAPE_SINGLE_QUOTE = "\'";
    public static final String ESCAPE_SINGLE_QUOTE_FOR_JAVA = "\'";

    private static final String FIELD_NAME_CONVERSION_PATTERN = "((?<=[^A-Z])(?=[A-Z][^A-Z]))";
    public static final String SPACE = " ";
    public static final String DATE_FORMAT_Y_M_D = "yyyy-MM-dd"
    public static final String DATE_FORMAT_Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss"

    abstract String toInsertSql(Object object);
    String toUpdateSql(Object object, boolean versionCheck = true) {
        return ""
    }
    String toUpdateSql(Object object, Map map, boolean versionCheck = true) {
        StringBuilder query = new StringBuilder(UPDATE)
        query.append(getTableSpecificName(object.class.simpleName))
        query.append(CommonConstants.SPACE)
        query.append(SET)
        query.append(VERSION_VERSION_PLUS_1)
        Object childObject = null
        map.each { String key, String value ->
            query.append(value)
            query.append(CommonConstants.EQUAL)
            query.append(CommonConstants.SINGLE_QUOTE)
            childObject = object.properties.get(key)
            if (childObject instanceof Date) {
                if (key == LAST_UPDATED) {
                    query.append(DateUtil.getFlexibleDateFormatAsString(new Date(), CommonConstants.DATE_FORMAT_Y_M_D_H_M_S))
                } else {
                    query.append(DateUtil.getFlexibleDateFormatAsString(childObject, CommonConstants.DATE_FORMAT_Y_M_D))
                }
            }
            else {
                query.append(childObject)
            }
            query.append(CommonConstants.SINGLE_QUOTE)
            query.append(CommonConstants.COMMA)
        }
        query.deleteCharAt(query.length() - 1)
        query.append(CommonConstants.SPACE)
        query.append(WHERE)
        query.append(ID)
        query.append(CommonConstants.SINGLE_QUOTE)
        query.append(object.id)
        query.append(CommonConstants.SINGLE_QUOTE)
        if (versionCheck) {
            query.append(AND)
            query.append(VERSION)
            query.append(CommonConstants.EQUAL)
            query.append(object.properties.get(VERSION))
        }
        return query.toString()
    }

    String toDeleteSql(Object object) {
        StringBuilder query = new StringBuilder(DELETE)
        query.append(FROM)
        query.append(getTableSpecificName(object.class.simpleName))
        query.append(CommonConstants.SPACE)
        query.append(WHERE)
        query.append(ID)
        query.append(CommonConstants.SINGLE_QUOTE)
        query.append(object.id)
        query.append(CommonConstants.SINGLE_QUOTE)
        return query.toString()
    }

    private String getTableSpecificName(String word) {
        Pattern p = Pattern.compile(FIELD_NAME_CONVERSION_PATTERN)
        Matcher m = p.matcher(word)
        StringBuffer sb = new StringBuffer()
        while (m.find()) {
            m.appendReplacement(sb, UNDER_SCORE)
        }
        m.appendTail(sb)
        return sb.toString().toLowerCase()
    }
}
