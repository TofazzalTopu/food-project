package com.docu.commons

import org.springframework.context.i18n.LocaleContextHolder
import org.codehaus.groovy.grails.context.support.PluginAwareResourceBundleMessageSource
import org.springframework.web.context.request.RequestContextHolder
import javax.servlet.http.HttpSession

class InternationalizationService {

  static transactional = false
  PluginAwareResourceBundleMessageSource messageSource

  String getUserMessage(String paramCode, Object[] args) {
    return messageSource.getMessage(paramCode, args, paramCode, LocaleContextHolder.getLocale())
  }

  String getErrorMessage(Object object) {
    StringBuffer message = new StringBuffer()
    if (object?.hasErrors()) {
      message.append("<ul>")
      List fieldErrorList = object.errors.allErrors
      fieldErrorList.each { fieldError ->
        message.append("<li>${messageSource.getMessage(fieldError, LocaleContextHolder.getLocale())}</li>")
      }
      message.append("</ul>")
    }
    return message.toString()
  }
    
  String getExceptionMessage(Exception ex){
      HttpSession session = RequestContextHolder.currentRequestAttributes().getSession()
      StringBuilder stringBuilder = new StringBuilder(CommonConstants.SQUARE_BRACKET_OPEN)
//      Office office = (Office)session.getAttribute(Office.class.simpleName)
      def user = session.getAttribute("user")
//      stringBuilder.append(office.getOfficeCode())
      stringBuilder.append(CommonConstants.COLON)
      stringBuilder.append(user?.id)
      stringBuilder.append(CommonConstants.SQUARE_BRACKET_CLOSE)
      stringBuilder.append(CommonConstants.SPACE)
      stringBuilder.append(ex.message)
      return stringBuilder.toString()
  }

    String getLogMessage(String message){
        HttpSession session = RequestContextHolder.currentRequestAttributes().getSession()
        StringBuilder stringBuilder = new StringBuilder(CommonConstants.SQUARE_BRACKET_OPEN)
//        Office office = (Office)session.getAttribute(Office.class.simpleName)
        def user = session.getAttribute("user")
//        stringBuilder.append(office.getOfficeCode())
        stringBuilder.append(CommonConstants.COLON)
        stringBuilder.append(user?.id)
        stringBuilder.append(CommonConstants.SQUARE_BRACKET_CLOSE)
        stringBuilder.append(CommonConstants.SPACE)
        stringBuilder.append(message)
        return stringBuilder.toString()
    }
}
