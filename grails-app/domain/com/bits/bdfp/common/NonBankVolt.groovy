package com.bits.bdfp.common

import com.bits.bdfp.settings.EnterpriseConfiguration

class NonBankVolt {

    EnterpriseConfiguration enterprise
    String                  code
    String                  name


    static constraints = {
        enterprise(blank: false, nullable: false)
        code(blank: false, nullable: false, unique: 'enterprise', maxSize: 10)
        name(blank: false, nullable: false, unique: 'enterprise', maxSize: 50)
    }

    @Override
    String toString() {
        return "[" + code + "]" + name
    }
}
