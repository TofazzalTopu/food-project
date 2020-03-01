package com.docu.commons

import com.docu.commons.addresstype.CreateAddressTypeAction
import com.docu.commons.addresstype.DeleteAddressTypeAction
import com.docu.commons.addresstype.ListAddressTypeAction
import com.docu.commons.addresstype.UpdateAddressTypeAction
import com.docu.commons.addresstype.ReadAddressTypeAction
import com.docu.commons.addresstype.SearchAddressTypeAction
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class AddressTypeController {

    @Autowired
    private CreateAddressTypeAction createAddressTypeAction
    @Autowired
    private UpdateAddressTypeAction updateAddressTypeAction
    @Autowired
    private ListAddressTypeAction listAddressTypeAction
    @Autowired
    private DeleteAddressTypeAction deleteAddressTypeAction
    @Autowired
    private ReadAddressTypeAction readAddressTypeAction
    @Autowired
    private SearchAddressTypeAction searchAddressTypeAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def listJSON = {
       render listAddressTypeAction.execute(params, null) as JSON
    }

    def show = {
        render(view: '/commons/addressType/show')
    }

    def save = {
      Object object = createAddressTypeAction.preCondition(params)
      Message message = object.get(Message.MESSAGE)
      if(message.type == Message.SUCCESS){
        object = createAddressTypeAction.execute(params, object)
        message = object.get(Message.MESSAGE)
      }
      render message as JSON
    }

    def edit = {
        AddressType addressType = (AddressType)  readAddressTypeAction.execute(params, null)
        render(view: '/commons/addressType/list', model: [addressType: addressType])
    }

    def editJSON = {
      Map data = (Map) readAddressTypeAction.execute(params, null)
      render data as JSON
    }

    def update = {
      Object object = updateAddressTypeAction.preCondition(params)
      Message message = object.get(Message.MESSAGE)
      if(message.type == Message.SUCCESS){
        object = updateAddressTypeAction.execute(params, object)
        message = object.get(Message.MESSAGE)
      }
      render message as JSON
    }

    def delete = {
      Object object = deleteAddressTypeAction.preCondition(params)
      Message message = object.get(Message.MESSAGE)
      if(message.type == Message.SUCCESS){
        object = deleteAddressTypeAction.execute(params, object)
        message = object.get(Message.MESSAGE)
      }
      render message as JSON
    }

    def search = {
        AddressType addressType = (AddressType) searchAddressTypeAction.execute(params, null)
        if(addressType){
            render addressType as JSON
        }
        else{
            render ''
        }
    }

}
