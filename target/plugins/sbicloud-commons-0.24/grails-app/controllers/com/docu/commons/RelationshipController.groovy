package com.docu.commons

import com.docu.commons.relationship.CreateRelationshipAction
import com.docu.commons.relationship.DeleteRelationshipAction
import com.docu.commons.relationship.ListRelationshipAction
import com.docu.commons.relationship.UpdateRelationshipAction
import com.docu.commons.relationship.ReadRelationshipAction
import com.docu.commons.relationship.SearchRelationshipAction

import com.docu.commons.Message
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON
import com.docu.commons.annotation.RequiresAuthentication

@RequiresAuthentication
class RelationshipController {

    @Autowired
    private CreateRelationshipAction createRelationshipAction
    @Autowired
    private UpdateRelationshipAction updateRelationshipAction
    @Autowired
    private ListRelationshipAction listRelationshipAction
    @Autowired
    private DeleteRelationshipAction deleteRelationshipAction
    @Autowired
    private ReadRelationshipAction readRelationshipAction
    @Autowired
    private SearchRelationshipAction searchRelationshipAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
       render listRelationshipAction.execute(params, null) as JSON
    }

    def show = {

    }

    def save = {
      Object object = createRelationshipAction.preCondition(params)
      Message message = object.get(Message.MESSAGE)
      if(message.type == Message.SUCCESS){
        object = createRelationshipAction.execute(params, object)
        message = object.get(Message.MESSAGE)
      }
      render message as JSON
    }

    def edit = {
      Map data = (Map) readRelationshipAction.execute(params, null)
      render data as JSON
    }

    def update = {
      Object object = updateRelationshipAction.preCondition(params)
      Message message = object.get(Message.MESSAGE)
      if(message.type == Message.SUCCESS){
        object = updateRelationshipAction.execute(params, object)
        message = object.get(Message.MESSAGE)
      }
      render message as JSON
    }

    def delete = {
      Object object = deleteRelationshipAction.preCondition(params)
      Message message = object.get(Message.MESSAGE)
      if(message.type == Message.SUCCESS){
        object = deleteRelationshipAction.execute(params, object)
        message = object.get(Message.MESSAGE)
      }
      render message as JSON
    }

    def search = {
        Relationship relationship = (Relationship) searchRelationshipAction.execute(params, null)
        if(relationship){
            render relationship as JSON
        }
        else{
            render ''
        }
    }
}
