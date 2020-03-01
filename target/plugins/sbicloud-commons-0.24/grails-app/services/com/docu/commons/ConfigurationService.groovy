package com.docu.commons

class ConfigurationService {

    static transactional = false

    SessionManagementService sessionManagementService


    boolean shouldConsiderHolidayInLoanScheduleGeneration() {
        return false
    }

    boolean skipHolidayInLoanScheduleGeneration() {
        return true
    }

    boolean isOnlineOffice() {
        return true
    }
}
