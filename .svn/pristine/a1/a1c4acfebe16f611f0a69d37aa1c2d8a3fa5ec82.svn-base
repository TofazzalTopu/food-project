package com.docu.commons

/**
 * Created by IntelliJ IDEA.
 * User: Rafiq
 * Date: 1/4/11
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */
class LogUtil {

  public static String getParamsAsString(def params){
    StringBuffer paramData = new StringBuffer()
    params.each {
      paramData.append(it.key + CommonConstants.COLON + it.value + CommonConstants.NEW_LINE)
    }
    return paramData.toString()
  }
}
