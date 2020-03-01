package com.docu.common

import com.docu.common.aop.LogUtility
import org.codehaus.groovy.grails.context.support.PluginAwareResourceBundleMessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.beans.factory.annotation.Autowired
import org.apache.commons.logging.LogFactory
import org.apache.commons.logging.Log


public abstract class Action {
    protected int pageNum = 1
    protected int resultPerPage = 10
    protected int start = ((pageNum - 1) * resultPerPage);
    protected String sortCol = "id"
    protected String sortOrder = "desc"
    protected String page = 'page'
    protected String row = 'rows'
    protected int total = 0

    public String SUCCESS_SAVE = "message.save.success"
    public String FAIL_SAVE = "message.save.fail"
    public String SUCCESS_UPDATE = "message.update.success"
    public String FAIL_UPDATE = "message.update.fail"
    public String SUCCESS_DELETE = "message.delete.success"
    public String FAIL_DELETE = "message.delete.fail"
    public String ALREADY_DELETED = "message.already.deleted"
    protected static final Log log = LogFactory.getLog(LogUtility.class)
    protected static String UI = "</ul>"
    protected static String UI_START = "<ul>"
    protected static String MESSAGE_TITLE = "message.title"
    @Autowired
    PluginAwareResourceBundleMessageSource messageSource

    /**
     *  function for pagination if not call then default
     * @param params
     */
    protected void init(def params) {
        pageNum = params.page ? params.int(page) : 1
        resultPerPage = params.rows ? params.int(row) : 10
        start = ((pageNum - 1) * resultPerPage);
        sortCol = params.sidx ? params.sidx : sortCol
        sortOrder = params.sord ? params.sord : sortOrder
        total = 0
    }

    protected getUserMessage(String paramCode, Object[] args) {
        return messageSource.getMessage(paramCode, args, paramCode, LocaleContextHolder.getLocale())
    }

    protected Message getValidationErrorMessage(Object object) {
        StringBuffer buffer = new StringBuffer()
        if (object.hasErrors()) {
            buffer.append(UI_START)
            List fieldErrorList = object.errors.allErrors
            fieldErrorList.each { fieldError ->
                buffer.append("<li>${messageSource.getMessage(fieldError, LocaleContextHolder.getLocale())}</li>")
            }
            buffer.append(UI)
        }
        Message message = new Message(messageTitle: getUserMessage(MESSAGE_TITLE, null), type: Message.ERROR)
        message.messageBody[0] = buffer.toString()
        return message
    }


    protected Message getMessage(Object object, int messageType, String actionMessage) {
        Message message = new Message(messageTitle: getUserMessage(MESSAGE_TITLE, null), type: messageType)
        message.messageBody[0] = getUserMessage(actionMessage, object.class.simpleName)
        return message
    }

    protected Message getMessage(String title, int messageType, String actionMessage) {
        Message message = new Message(messageTitle: getUserMessage(title, null), type: messageType)
        message.messageBody[0] = getUserMessage(actionMessage, title)
        return message
    }


//
//  protected Message getValidationErrorMessageForUI(Object object) {
//    StringBuffer buffer = new StringBuffer()
//    if (object.hasErrors()) {
//      buffer.append(UI)
//      List fieldErrorList = object.errors.allErrors
//      fieldErrorList.each { fieldError ->
//        buffer.append("<li>${messageSource.getMessage(fieldError, LocaleContextHolder.getLocale())}</li>")
//      }
//      buffer.append(UI)
//    }
//    Message message = new Message(messageTitle: getUserMessage(MESSAGE_TITLE, null), type: Message.ERROR)
//    message.messageBody[0] = buffer.toString()
//    return message
//  }
//
//  protected Message getSuccessMessageForUI(Object object, String successMessage) {
//    Message message = new Message(messageTitle: getUserMessage(MESSAGE_TITLE, null), type: Message.SUCCESS)
//    message.messageBody[0] = getUserMessage(successMessage, object.class.simpleName)
//    return message
//  }
//
//  protected Message getErrorMessageForUI(Object object, String errorMessage) {
//    Message message = new Message(messageTitle: getUserMessage(MESSAGE_TITLE, null), type: Message.ERROR)
//    message.messageBody[0] = getUserMessage(errorMessage, object.class.simpleName)
//    return message
//  }
//
//  protected Message getMessageForUI(Object object, String messageKey, int messageType) {
//    Message message = new Message(messageTitle: getUserMessage(MESSAGE_TITLE, null), type: messageType)
//    message.messageBody[0] = getUserMessage(messageKey, object.class.simpleName)
//    return message
//  }

    protected abstract Object preCondition(def object1, def object2)

    protected abstract Object postCondition(def object1, def object2)

    protected abstract Object execute(def object1, def object2)

}