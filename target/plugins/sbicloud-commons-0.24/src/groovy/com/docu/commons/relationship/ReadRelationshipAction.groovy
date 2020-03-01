package com.docu.commons.relationship

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.docu.commons.Relationship
import com.docu.commons.RelationshipService

@Component("readRelationshipAction")
class ReadRelationshipAction implements IAction {
    public static final Log log = LogFactory.getLog(ReadRelationshipAction.class)
    private final String MESSAGE_HEADER = 'relationship.header'
    private final String MESSAGE_SUCCESS = 'relationship.update.success'

    @Autowired
    RelationshipService relationshipService

    public Object preCondition(def params) {
        return null
    }

    public Object execute(def params, def object) {
        Relationship relationship = relationshipService.read(params)
        Map objectData = ObjectUtil.getPersistenceData(relationship)
        return objectData
    }

    public Object postCondition(def object) {
        return null
    }
}