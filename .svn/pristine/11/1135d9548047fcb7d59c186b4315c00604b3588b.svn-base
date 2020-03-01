package com.docu.commons.relationship


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.CommonConstants
import com.docu.commons.Relationship
import com.docu.commons.RelationshipService

@Component("deleteRelationshipAction")
class DeleteRelationshipAction implements IAction {
    public static final Log log = LogFactory.getLog(DeleteRelationshipAction.class)
    private final String MESSAGE_HEADER = 'relationship.header'
    private final String MESSAGE_SUCCESS = 'relationship.delete.success'
    private final String MESSAGE_FAILURE = 'relationship.failure.success'
    private final String MESSAGE_ERROR = 'relationship.error.delete'


    @Autowired
    RelationshipService relationshipService


    public Object preCondition(def params) {
        Map mapInstance = [:]
        Relationship relationship = null
        try {
            relationship = relationshipService.read(params)
            if (!relationship) {
                return UserMessageBuilder.createMessage(relationshipService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR, relationshipService.getUserMessage(MESSAGE_FAILURE, null))
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


            relationshipService.delete(relationship)
        } catch (Exception ex) {
            log.error(ex.message)
            return UserMessageBuilder.createMessage(relationshipService.getUserMessage(MESSAGE_HEADER, null), Message.ERROR,relationshipService.getUserMessage(MESSAGE_ERROR, null))
        }

        return UserMessageBuilder.createMessage(relationshipService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, relationshipService.getUserMessage(MESSAGE_SUCCESS, null))
    }

    public Object postCondition(def object) {
        return null
    }
}