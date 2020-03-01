package com.docu.accesscontrol.featureinfo.action

/**
 * Created by IntelliJ IDEA.
 * User: bipul.kumar
 * Date: 5/15/11
 * Time: 7:01 PM
 * To change this template use File | Settings | File Templates.
 */
import com.docu.accesscontrol.FeatureInfoService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by IntelliJ IDEA.
 * User: bipul.kumar
 * Date: 5/2/11
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("listFeatureInfoAction")
class ListFeatureInfoAction  {
  @Autowired
  com.docu.accesscontrol.FeatureInfoService featureInfoService

  Object preCondition(Object object) {
    return null  //To change body of implemented methods use File | Settings | File Templates.
  }

  Object postCondition(Object object) {
    return null  //To change body of implemented methods use File | Settings | File Templates.
  }

  Object execute(Object params, Object object) {
   return featureInfoService.getFeatureInfoListJson(params)
  }
}