package com.docu.commons

import com.docu.commons.maritalstatus.CreateMaritalStatusAction
import com.docu.commons.maritalstatus.DeleteMaritalStatusAction
import com.docu.commons.maritalstatus.ListMaritalStatusAction
import com.docu.commons.maritalstatus.UpdateMaritalStatusAction
import com.docu.commons.maritalstatus.ReadMaritalStatusAction
import com.docu.commons.maritalstatus.SearchMaritalStatusAction

import com.docu.commons.Message
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON
import com.docu.commons.util.MaritalStatusUtil
import com.docu.commons.annotation.RequiresAuthentication

@RequiresAuthentication
class MaritalStatusController {

  @Autowired
  private CreateMaritalStatusAction createMaritalStatusAction
  @Autowired
  private UpdateMaritalStatusAction updateMaritalStatusAction
  @Autowired
  private ListMaritalStatusAction listMaritalStatusAction
  @Autowired
  private DeleteMaritalStatusAction deleteMaritalStatusAction
  @Autowired
  private ReadMaritalStatusAction readMaritalStatusAction
  @Autowired
  private SearchMaritalStatusAction searchMaritalStatusAction

  static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
  static defaultAction = "show"

  def list = {
    render listMaritalStatusAction.execute(params, null) as JSON
  }

  def show = {

  }

  def save = {
    Object object = createMaritalStatusAction.preCondition(params)
    Message message = object.get(Message.MESSAGE)
    if (message.type == Message.SUCCESS) {
      object = createMaritalStatusAction.execute(params, object)
      message = object.get(Message.MESSAGE)
    }
    render message as JSON
  }

  def edit = {
    Map data = (Map) readMaritalStatusAction.execute(params, null)
    render data as JSON
  }

  def update = {
    Object object = updateMaritalStatusAction.preCondition(params)
    Message message = object.get(Message.MESSAGE)
    if (message.type == Message.SUCCESS) {
      object = updateMaritalStatusAction.execute(params, object)
      message = object.get(Message.MESSAGE)
    }
    render message as JSON
  }

  def delete = {
    Object object = deleteMaritalStatusAction.preCondition(params)
    Message message = object.get(Message.MESSAGE)
    if (message.type == Message.SUCCESS) {
      object = deleteMaritalStatusAction.execute(params, object)
      message = object.get(Message.MESSAGE)
    }
    render message as JSON
  }

  def search = {
    MaritalStatus maritalStatus = (MaritalStatus) searchMaritalStatusAction.execute(params, null)
    if (maritalStatus) {
      render maritalStatus as JSON
    }
    else {
      render ''
    }
  }

  def checkUniqueData = {
    boolean uniqueMaritalStatus = false
    if (!params.id) {
      params.id = "0"
    }
    uniqueMaritalStatus = MaritalStatusUtil.instance.isExists(params.name, Integer.parseInt(params.id))

    Map output = [:];
    if (uniqueMaritalStatus) {
      output = [isExists: true]
    } else {
      output = [isExists: false]
    }
    render output as JSON
  }
}
