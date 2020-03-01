package com.docu.commons

import groovy.sql.Sql
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import groovy.sql.GroovyRowResult
import groovy.text.SimpleTemplateEngine

/**
 * Created by IntelliJ IDEA.
 * User: feroz
 * Date: 1/18/11
 * Time: 12:50 PM
 * To change this template use File | Settings | File Templates.
 */
class ObjectUtil {
  public static List instantiateObjects(Map data, Class className) {
    List list = []
    data.each {key, val ->
      if (val instanceof java.util.Map) {
        def object = null
        if (val.containsKey("id") && val.id != '') {
          object = className.newInstance().invokeMethod("read", val.id.toString())
        } else {
          object = className.newInstance()
        }
        if (object) {
          object.properties = val
          list.add(object)
        }
      }
    }

    return list
  }

  public static List getObjectData(Map data) {
    List list = []
    data.each {key, val ->
      if (val instanceof java.util.Map) {
        list.add(val)
      }
    }

    return list
  }

    public static List getObjectId(Map data) {
        List<String> list = []
        data.each {key, val ->
            if (val instanceof java.util.Map) {
                list.add(val.id)
            }
        }

        return list
    }

    public static List instantiateObjects(Sql sqlInstance, String sqlString, Class className) {
    List list = []
    Object object = null
    Map mapInstance = [:]
    sqlInstance.eachRow(sqlString) {
      mapInstance = it.toRowResult()
      object = className.newInstance(mapInstance)
      object.id = mapInstance.id
      object.version = mapInstance.version
      list.add(object)
    }
    return list
  }

  public def executeReadOnlyQuery(Class domainClass, String query, Map properties = [:]) {
    domainClass.withSession { session ->
      def q = session.createQuery(query)
      q.readOnly = true
      q.properties = properties
      List results = q.list()
      return results
    }
  }

  public static Map compareIds(Object paramsIds, List<String> existingIds) {
    List incomingIdList = new ArrayList()
    List newIdList = new ArrayList()
    List orphanIdList = new ArrayList()

    if (paramsIds instanceof String) {
      incomingIdList.add(paramsIds)
    }
    else if (paramsIds instanceof String[]) {
      incomingIdList = (List) paramsIds
    }

    newIdList.addAll(incomingIdList)
    orphanIdList.addAll(existingIds)

    newIdList.removeAll(existingIds)
    orphanIdList.removeAll(incomingIdList)

    return [newIds: newIdList, orphanIds: orphanIdList]
  }

  public static Map getPersistenceData(Object object) {
    Map data = [:]
    data.put("id", object.id.toString())

    GrailsDomainClassProperty[] propertyList = null
    propertyList = new DefaultGrailsDomainClass(object.class).getPersistantProperties()
    Object propertyValue = null
    String propertyName = null
    for (property in propertyList) {
      propertyName = property.name
      propertyValue = object.properties.get(propertyName)
      propertyValue = propertyValue != null ? propertyValue : ""
      //propertyValue = propertyValue ? propertyValue : ""
      if (propertyValue instanceof java.sql.Timestamp) {
        propertyValue = DateUtil.getDateFormatAsString(DateUtil.getSimpleDateYMD(propertyValue.toString()))
      }
      data.put(propertyName, propertyValue)
    }

    return data
  }

    /**
     *  List<Map> groupByMapList = [
     *      [groupByField:"group_name", fields:["group_name", "group_id"]],
     *      [groupByField:"main_sub_group_name", fields:["main_sub_group_name", "main_sub_group_id"]],
     *      [groupByField:"sub_group_name", fields:["sub_group_name", "sub_group_id"]]
     *  ]
     *
     * @param groovyRowResultList
     * @param groupByMapList
     * @param groupByMapIndex
     * @return
     */
    public static List<GroovyRowResult> groupGroovyRowResult(
            List<GroovyRowResult> groovyRowResultList,
            List<Map> groupByMapList,
            Integer groupByMapIndex = 0)
    {
        List<GroovyRowResult> groupedGroovyRowResult = []
        Map groupByMap = groupByMapList.get(groupByMapIndex)
        String groupByField = groupByMap.groupByField
        List<String> fields = groupByMap.fields

        def createGroovyRowResult = {GroovyRowResult row ->
            GroovyRowResult groovyRowResult = [:]
            groovyRowResult.put("name", "")
            for(String field: fields){
                groovyRowResult.put(field, row[field])
            }
            groovyRowResult.put("children", [])
            return groovyRowResult
        }

        Map<String, List<GroovyRowResult>> map = (Map<String, List<GroovyRowResult>>) groovyRowResultList.groupBy {it[groupByField]}
        GroovyRowResult groovyRowResult = null
        List<GroovyRowResult> groupItems = []
        for(String groupName : map.keySet()) {
            groupItems = map.get(groupName)
            groovyRowResult = (GroovyRowResult) createGroovyRowResult((GroovyRowResult) groupItems.get(0))
            groovyRowResult.put('name', groupName)
            if(groupByMapIndex < groupByMapList.size() - 1){
                groovyRowResult.put('children', groupGroovyRowResult(groupItems, groupByMapList, groupByMapIndex + 1))
            }
            else{
                groovyRowResult.put('children', groupItems)
            }
            groupedGroovyRowResult.add(groovyRowResult)
        }
        return groupedGroovyRowResult
    }

    public static GroovyRowResult sumGroovyRowResultList(List<GroovyRowResult> groovyRowResultList, List<String> sumAwareColumnNames){
        GroovyRowResult groovyRowResult = new GroovyRowResult([:])
        groovyRowResult.putAll(groovyRowResultList.first())
        sumAwareColumnNames.each {String columnName ->
            groovyRowResult[columnName] = ((List<Double>) groovyRowResultList."${columnName}").sum()
        }
        return groovyRowResult
    }

    public static String parseString(String str, Map params, SimpleTemplateEngine simpleTemplateEngine = null){
        if(!simpleTemplateEngine){
            simpleTemplateEngine = new SimpleTemplateEngine()
        }
        return simpleTemplateEngine.createTemplate(str).make(params).toString()
    }
}
