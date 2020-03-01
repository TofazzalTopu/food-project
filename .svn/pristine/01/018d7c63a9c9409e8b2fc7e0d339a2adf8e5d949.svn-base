package com.docu.commons.relationship

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.Relationship
import com.docu.commons.RelationshipService

@Component("updateRelationshipAction")
class UpdateRelationshipAction implements IAction {
    public static final Log log = LogFactory.getLog(UpdateRelationshipAction.class)
    private final String MESSAGE_HEADER = 'relationship.header'
    private final String MESSAGE_SUCCESS = 'relationship.update.success'

    @Autowired
    RelationshipService relationshipService

    public Object preCondition(def params) {
        Map mapInstance = [:]
        Relationship relationship = null
        try {
            relationship = relationshipService.read(params)
            relationship.properties = params
            if (!relationship.validate()) {
                return UserMessageBuilder.createMessage(relationshipService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, relationshipService.getErrorMessage(relationship), relationship)
            }
            mapInstance = (Map) UserMessageBuilder.createMessage(relationshipService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, null)
            mapInstance.put(Relationship.class.simpleName, relationship)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(relationshipService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, relationship, ex)
        }

        return mapInstance
    }

    public Object execute(def params, def object) {
        Relationship relationship = null
        try {
            relationship = object.get(Relationship.class.simpleName)
            relationshipService.save(relationship)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(relationshipService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, relationship, ex)
        }

        return UserMessageBuilder.createMessage(relationshipService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, relationshipService.getUserMessage(MESSAGE_SUCCESS, null))
    }

    public Object postCondition(def object) {
        return null
    }
}
