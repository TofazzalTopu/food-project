package com.bits.bdfp.common

import com.bits.bdfp.settings.EnterpriseConfiguration

class CodeGenerationFormat {

    String                  keyCode
    String                  format
    String                  currentNo
    String                  note
    Long                    questionLimit
    EnterpriseConfiguration enterpriseConfiguration
    String                  productYear
    String                  prefix
    Date                    dateUpdated

    static constraints = {
        keyCode (blank: false, nullable: false)
        format (blank: true, nullable: true)
        currentNo(blank: false, nullable: false)
        note(blank: true, nullable: true)
        questionLimit(blank: true, nullable: true)
        enterpriseConfiguration(blank:true, nullable: true)
        productYear(blank: true, nullable: true)
        prefix(blank: true, nullable: true)
        dateUpdated(blank: true, nullable: true)
    }
}
