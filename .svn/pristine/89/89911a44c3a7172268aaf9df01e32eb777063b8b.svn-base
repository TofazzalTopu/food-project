package com.docu.commons.relationship.action

import com.docu.commons.*
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 1/4/11
 * Time: 7:22 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("createOrUpdateRelationshipAction")
class CreateOrUpdateRelationshipAction implements IAction {

  public static final Log log = LogFactory.getLog(CreateOrUpdateRelationshipAction.class);

  @Autowired
  RelationshipService relationshipService

  public Object preCondition(def params) {
    boolean isNew = true;
    if (params.id) {
      isNew = false;
    }
    Map result = [:]
    Relationship relationshipInstance = null;
    if (params.isDelete) {
      relationshipInstance = Relationship.get(params.id?.toLong())
    } else {
      if (!isNew) {
        result = this.getRelationshipWithIntegrityCheckForEdit(params)
        if (result.isError) {
          return result
        }
        relationshipInstance = result.entity
      } else {
        relationshipInstance = new Relationship(params)
      }

      if (!relationshipInstance.validate()) {
        Map mapInstance = UserMessageBuilder.createMessage(relationshipService.getUserMessage("relationship.title", null), Message.ERROR,relationshipService.getErrorMessage(relationshipInstance), relationshipInstance)
        log.error(mapInstance.get(Message.MESSAGE).toString())
        return mapInstance
      }
    }
    return relationshipInstance
  }

  public Object postCondition(def object) {
    return null;
  }

  public Object execute(def param, def object) {
    try {
      relationshipService.save(object)
    } catch (Exception ex) {
      log.error(ex.message)
      return UserMessageBuilder.createMessage(relationshipService.getUserMessage("relationship.title", null), Message.ERROR, object, ex)
    }
    String successMessageBody
    String successMessageTitle
    if (param.isDelete) {
      successMessageTitle = relationshipService.getUserMessage("relationship.delete.title", null)
      successMessageBody = relationshipService.getUserMessage("relationship.delete.success.message", null)
    }
    else if(param.id) {
      successMessageTitle = relationshipService.getUserMessage("relationship.update.title", null)
      successMessageBody = relationshipService.getUserMessage("relationship.update.success.message", null)
    }  else {
      successMessageTitle = relationshipService.getUserMessage("relationship.create.title", null)
      successMessageBody = relationshipService.getUserMessage("relationship.create.success.message", null)
    }

    return UserMessageBuilder.createMessage(successMessageTitle, Message.SUCCESS, successMessageBody, object)
  }

  /**
   * Check to see is there any integrity errors while getting an instance of Relationship
   * at the time of editing
   *
   * @param params Request parameters
   * @return Exisitng Relationship instance as an entity if there is no integrity errors with within a data structure
   */
  private Map getRelationshipWithIntegrityCheckForEdit(params) {
    Map result = null
    List errors = []
    Relationship relationshipInstance = Relationship.read(params.id)
    if (relationshipInstance) {
      if (params.version) {
        def version = params.version.toLong()
        if (relationshipInstance.version > version) {
          errors << ["Another user has updated this Relationship while you were editing"]
          result = [isError: true, entity: null, errors: errors]
        } else {
          relationshipInstance.properties = params
          result = [isError: false, entity: relationshipInstance, errors: null]
        }
      }
    }
    else {
      errors << ["No Relationship found with this id or might have been deleted by someone!"];
      result = [isError: true, entity: null, errors: errors];
    }
    return result
  }
}
