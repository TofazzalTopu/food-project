package com.docu.app
/**
 * Created by rafiqul.islam on 11/27/2014.
 */
@Singleton
class MigrationDateUtil {
    List<SysMigrationDate> list = null

    public void resolve() {
        list = SysMigrationDate.list()
    }

    public void destroy() {
        list = null
    }

    SysMigrationDate get(long id) {
        return list.find { it.id == id }
    }

    SysMigrationDate getByOffice(String officeId, long officeTypeId) {
        return list.find { it.officeId == officeId && it.officeTypeId == officeTypeId }
    }

    Date getFirstMigrationDateByCountryId(long countryId) {
        List<SysMigrationDate> migrationDateList = list.findAll { it.countryId == countryId }
        migrationDateList.sort { it.migrationDate }
        return migrationDateList.size() > 0 ? migrationDateList.get(0).migrationDate : null
    }
}
