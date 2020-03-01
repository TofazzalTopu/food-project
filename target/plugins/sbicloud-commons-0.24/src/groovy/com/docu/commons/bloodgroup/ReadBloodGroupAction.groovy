package com.docu.commons.bloodgroup

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.docu.commons.BloodGroup
import com.docu.commons.BloodGroupService

@Component("readBloodGroupAction")
class ReadBloodGroupAction implements IAction {
  public static final Log log = LogFactory.getLog(ReadBloodGroupAction.class)

  @Autowired
  BloodGroupService bloodGroupService

  public Object preCondition(def params) {
    return null
  }

  public Object execute(def params, def object) {
    BloodGroup bloodGroup = bloodGroupService.read(params)
    Map objectData = ObjectUtil.getPersistenceData(bloodGroup)
    return objectData
  }

  public Object postCondition(def object) {
    return null
  }
}