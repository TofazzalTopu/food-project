package com.docu.app

class UserPreference {
    long        userId
    String      colorTheme
    String      securityQuestionOne
    String      answerOne
    String      securityQuestionTwo
    String      answerTwo
    
    static hasMany = [nPortlets: UserPortletPreference]

    static constraints = {
        securityQuestionOne(blank: true, nullable: true)
        securityQuestionTwo(blank: true, nullable: true)
        answerOne(blank: true, nullable: true)
        answerTwo(blank: true, nullable: true)
    }
}
