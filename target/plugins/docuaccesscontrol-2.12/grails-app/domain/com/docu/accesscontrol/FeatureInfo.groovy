package com.docu.accesscontrol

class FeatureInfo {
  String featureName
  String description
  ModuleInfo moduleInfo
  Long position
  static hasMany = [nFeatureActions: FeatureAction]

  static constraints = {
    featureName blank: false
    position(blank:true,nullable: true)
  }

  def String toString() {
    return featureName + " (" + description + ")";
  }
}