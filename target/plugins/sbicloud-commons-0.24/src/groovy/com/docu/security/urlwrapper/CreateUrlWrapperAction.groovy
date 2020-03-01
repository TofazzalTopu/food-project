package com.docu.security.urlwrapper





import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.security.UrlWrapper
import com.docu.security.UrlWrapperService










@Component("createUrlWrapperAction")
class CreateUrlWrapperAction implements IAction {
  public static final Log log = LogFactory.getLog(CreateUrlWrapperAction.class)
  private final String MESSAGE_HEADER = 'urlWrapper.header'
  private final String MESSAGE_SUCCESS = 'urlWrapper.save.success'

  @Autowired
  UrlWrapperService urlWrapperService
  

  public Object preCondition(def params) {
    Map mapInstance = [:]
    UrlWrapper urlWrapper = null
    try {
        urlWrapper = new UrlWrapper()
        urlWrapper.properties = params
        
        
        
        
        if (!urlWrapper.validate()) {
            return UserMessageBuilder.createMessage(urlWrapperService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, urlWrapperService.getErrorMessage(urlWrapper), urlWrapper)
        }
        mapInstance = (Map) UserMessageBuilder.createMessage(urlWrapperService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, null)
        mapInstance.put(UrlWrapper.class.simpleName, urlWrapper)
    } catch (Exception ex) {
        log.error(ex.message)
        return UserMessageBuilder.createMessage(urlWrapperService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, urlWrapper, ex)
    }

    return mapInstance
  }

  public Object execute(def params, def object) {
        UrlWrapper urlWrapper = null
        try {
            urlWrapper = object.get(UrlWrapper.class.simpleName)
            urlWrapperService.save(urlWrapper)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(urlWrapperService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, urlWrapper, ex)
        }

        return UserMessageBuilder.createMessage(urlWrapperService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, urlWrapperService.getUserMessage(MESSAGE_SUCCESS, null))
  }

  public Object postCondition(def object) {
    return null
  }
}