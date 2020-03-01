package com.docu.accesscontrol

class FeatureControllerMapping {
  FeatureAction featureAction
  String controllerName
  String controllerAction
  boolean isDeleted
  static transients = ['isDeleted']
  static constraints = {
  }

  def String toString() {
    return controllerName + " (" + controllerAction + ")";
  }
}