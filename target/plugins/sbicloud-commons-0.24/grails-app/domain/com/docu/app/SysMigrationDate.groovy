package com.docu.app

class SysMigrationDate {

    long countryId
    String officeId
    long officeTypeId
    Date migrationDate

    static constraints = {
        countryId()
        officeId()
        officeTypeId()
        migrationDate()
    }
}
