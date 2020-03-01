package com.docu.accesscontrol.requestmap.action

import com.docu.accesscontrol.RequestMapService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.accesscontrol.RequestMapService

/**
 * Created by IntelliJ IDEA.
 * User: bipul.kumar
 * Date: 5/16/11
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("showRequestMapAction")
class ShowRequestMapAction  {
  @Autowired
  RequestMapService requestMapService

  Object preCondition(Object object) {
    return null  //To change body of implemented methods use File | Settings | File Templates.
  }

  Object postCondition(Object object) {
    return null  //To change body of implemented methods use File | Settings | File Templates.
  }

  Object execute(Object params, Object object) {
    List featureAction = requestMapService.showFeatureAndAction(params)
    return featureAction

  }

  Object getCheckAction(Object params) {
    List checkedAction = requestMapService.getCheckAction(params)
    return checkedAction
  }
}

