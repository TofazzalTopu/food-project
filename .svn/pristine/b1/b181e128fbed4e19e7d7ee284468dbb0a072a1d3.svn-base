package com.bits.bdfp.common.codegenerationformat




import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.common.CodeGenerationFormat
import com.bits.bdfp.common.CodeGenerationFormatService







@Component("deleteCodeGenerationFormatAction")
class DeleteCodeGenerationFormatAction implements IAction {
  public static final Log log = LogFactory.getLog(DeleteCodeGenerationFormatAction.class)
  private final String MESSAGE_HEADER = 'codeGenerationFormat.header'
  private final String MESSAGE_SUCCESS = 'codeGenerationFormat.delete.success'
  private final String MESSAGE_FAILURE = 'codeGenerationFormat.failure.success'

  @Autowired
  CodeGenerationFormatService codeGenerationFormatService
  

  public Object preCondition(def params) {
    Map mapInstance = [:]
    CodeGenerationFormat codeGenerationFormat = null
    try {
        codeGenerationFormat = codeGenerationFormatService.read(params)
        if (!codeGenerationFormat) {
            return UserMessageBuilder.createMessage(codeGenerationFormatService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, codeGenerationFormatService.getUserMessage(MESSAGE_FAILURE, null))
        }
        
        mapInstance = (Map) UserMessageBuilder.createMessage(codeGenerationFormatService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, null)
        mapInstance.put(CodeGenerationFormat.class.simpleName, codeGenerationFormat)
    } catch (Exception ex) {
        log.error(ex.message)
        return UserMessageBuilder.createMessage(codeGenerationFormatService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, codeGenerationFormat, ex)
    }

    return mapInstance
  }

  public Object execute(def params, def object) {
        CodeGenerationFormat codeGenerationFormat = null
        try {
            codeGenerationFormat = object.get(CodeGenerationFormat.class.simpleName)
            
            
            
            codeGenerationFormatService.delete(codeGenerationFormat)
            
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(codeGenerationFormatService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, codeGenerationFormat, ex)
        }

        return UserMessageBuilder.createMessage(codeGenerationFormatService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, codeGenerationFormatService.getUserMessage(MESSAGE_SUCCESS, null))
  }

  public Object postCondition(def object) {
    return null
  }
}