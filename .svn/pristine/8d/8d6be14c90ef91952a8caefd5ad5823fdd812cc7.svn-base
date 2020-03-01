package com.docu.accesscontrol

class UserRequestMap {

  String url
  String configAttribute
  long featureControllerMappingId
  long moduleId
  static mapping = {
    cache true
  }

  static constraints = {
    url blank: false // unique: true
    configAttribute blank: false
    featureControllerMappingId blank: false
  }

  def String toString() {
    return url;
  }
}