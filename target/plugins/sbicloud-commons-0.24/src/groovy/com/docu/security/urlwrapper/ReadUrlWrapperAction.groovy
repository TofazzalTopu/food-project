package com.docu.security.urlwrapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.docu.security.UrlWrapper
import com.docu.security.UrlWrapperService

@Component("readUrlWrapperAction")
class ReadUrlWrapperAction implements IAction {
  public static final Log log = LogFactory.getLog(ReadUrlWrapperAction.class)

  @Autowired
  UrlWrapperService urlWrapperService

  public Object preCondition(def params) {
    return null
  }

  public Object execute(def params, def object) {
    UrlWrapper urlWrapper = urlWrapperService.read(params)
    Map objectData = ObjectUtil.getPersistenceData(urlWrapper)
    return objectData
  }

  public Object postCondition(def object) {
    return null
  }
}