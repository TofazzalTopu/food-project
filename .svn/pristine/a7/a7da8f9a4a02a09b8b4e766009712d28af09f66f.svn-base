package com.docu.commons

import org.springframework.beans.factory.annotation.Autowired
import org.codehaus.groovy.grails.context.support.PluginAwareResourceBundleMessageSource
import org.springframework.context.i18n.LocaleContextHolder

/**
 * Created by IntelliJ IDEA.
 * User: Rafiq
 * Date: 1/5/11
 * Time: 1:55 PM
 * To change this template use File | Settings | File Templates.
 */
class UserMessageBuilder {
  // Validation error message
  public static Map createMessage(String title, int messageType, def object){
    Map map = [:]
    Message message = MessageFactory.getMessage(messageType)
    message.messageTitle = title
    if (object?.hasErrors()){
      message.messageBody = object.errors.allErrors
    }

    map.put(Message.MESSAGE, message)
    map.put(Message.OBJECT, object)
    return map
  }

  // any exception
  public static Map createMessage(String title, int messageType, def object, Exception ex){
    Map map = [:]
    Message message = MessageFactory.getMessage(messageType)
    message.messageTitle = title
    message.messageBody.add(ex.message)
    map.put(Message.MESSAGE, message)
    map.put(Message.OBJECT, object)
    return map
  }

  //success
  public static Map createMessage(String title, int messageType, String messageDesc){
    Map map = [:]
    Message message = MessageFactory.getMessage(messageType)
    message.messageTitle = title
    message.messageBody.add(messageDesc)
    map.put(Message.MESSAGE, message)
    return map
  }

  //success
  public static Map createMessage(String title, int messageType, String messageDesc, def object){
    Map map = [:]
    Message message = MessageFactory.getMessage(messageType)
    message.messageTitle = title
    message.messageBody.add(messageDesc)
    map.put(Message.MESSAGE, message)
    map.put(Message.OBJECT, object)
    return map
  }
}
