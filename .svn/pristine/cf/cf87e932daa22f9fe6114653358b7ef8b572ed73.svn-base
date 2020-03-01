package com.docu.security

import com.docu.commons.CommonConstants
import org.springframework.transaction.annotation.Transactional

class SetupOptionService {

    static transactional = true

    @Transactional(readOnly = true)
    public boolean setPasswordPatternInfo() {
        SetupOption setupOption = SetupOption.findByKeyName(CommonConstants.PASSWORD_PATTERN_KEY)
        if(setupOption) {
            CommonConstants.PASSWORD_PATTERN = setupOption.keyValue
            CommonConstants.PASSWORD_VALIDATION_MESSAGE = setupOption.comments
        }
        return true
    }

    @Transactional
    public initializeDefaultData() {
        if(SetupOption.count() == 0){
            new SetupOption(keyName: CommonConstants.PASSWORD_PATTERN_KEY, keyValue: "/^(?=.*[0-9])(?=.*[a-z])(?=.*[@#\$%!&*^]).{6,20}\$/", comments: "Password should be 6 to 20 character long. \\n* Password should have at least one alphabet. \\n* one numeric value. \\n* And one special characters.").save()
        }
    }
}
