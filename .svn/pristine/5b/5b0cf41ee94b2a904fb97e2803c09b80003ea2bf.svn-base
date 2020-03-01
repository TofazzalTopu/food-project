package com.bits.bdfp.inventory.product

import com.bits.bdfp.settings.EnterpriseConfiguration
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class PackageTypeService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public PackageType create(Object object) {
        PackageType packageType = (PackageType) object
        if (packageType.save(false)) {
            return packageType
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        PackageType packageType = (PackageType) object
        if (packageType.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        PackageType packageType = (PackageType) object
        packageType.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<PackageType> objList = PackageType.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = PackageType.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<PackageType> list() {
        return PackageType.list()
    }
    @Transactional(readOnly = true)
    public List<PackageType> findAllByEnterpriseConfiguration(EnterpriseConfiguration enterpriseConfiguration) {
        return PackageType.findAllByEnterpriseConfiguration(enterpriseConfiguration)
    }

    @Transactional(readOnly = true)
    public PackageType read(Long id) {
        return PackageType.read(id)
    }

    @Transactional(readOnly = true)
    public PackageType search(String fieldName, String fieldValue) {
        String query = "from PackageType as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return PackageType.find(query)
    }
}
