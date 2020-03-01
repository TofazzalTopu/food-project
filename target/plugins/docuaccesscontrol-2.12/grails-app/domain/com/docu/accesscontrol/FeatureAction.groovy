package com.docu.accesscontrol

class FeatureAction {
    String actionCode
    String actionName
    String description
    FeatureInfo featureInfo
    String menuUrl
    String imageCss
    Long position
    boolean isDeleted
    int isPermittedToShow = 1 // default 1 means permitted to show for all and 0 is only for admin and sa role
    static transients = ['isDeleted']

    static constraints = {
        menuUrl(blank: true, nullable: true)
        imageCss(blank: true, nullable: true)
        position(blank: true, nullable: true)
        actionCode(nullable: true)
    }


    def String toString() {
        return actionName + " (" + description + ")";
    }
}
