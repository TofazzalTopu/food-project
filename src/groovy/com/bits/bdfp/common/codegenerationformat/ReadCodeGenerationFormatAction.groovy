package com.bits.bdfp.common.codegenerationformat

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.bits.bdfp.common.CodeGenerationFormat
import com.bits.bdfp.common.CodeGenerationFormatService

@Component("readCodeGenerationFormatAction")
class ReadCodeGenerationFormatAction implements IAction {
  public static final Log log = LogFactory.getLog(ReadCodeGenerationFormatAction.class)

  @Autowired
  CodeGenerationFormatService codeGenerationFormatService

  public Object preCondition(def params) {
    return null
  }

  public Object execute(def params, def object) {
    CodeGenerationFormat codeGenerationFormat = codeGenerationFormatService.read(params)
    Map objectData = ObjectUtil.getPersistenceData(codeGenerationFormat)
    return objectData
  }

  public Object postCondition(def object) {
    return null
  }
}