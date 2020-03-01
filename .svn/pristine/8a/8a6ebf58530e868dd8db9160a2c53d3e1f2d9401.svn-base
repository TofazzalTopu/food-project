package com.docu.commons

import com.docu.security.SetupOptionService
import org.springframework.transaction.annotation.Transactional

class CommonService extends InternationalizationService {

    static transactional = false
//    AddressTypeService      addressTypeService
//    BloodGroupService       bloodGroupService
    GenderService           genderService
//    MaritalStatusService    maritalStatusService
//    RelationshipService     relationshipService
//    ReligionService         religionService
    SetupOptionService      setupOptionService

    @Transactional(readOnly = true)
    List getList(Object object, String hql, Map map) {
        List list = []
        if (map) {
            list = object.findAll(hql, map)
        } else {
            list = object.findAll(hql)
        }
        return list
    }

    public void initializeDefaultData() {
//        addressTypeService.initializeDefaultData()
//        bloodGroupService.initializeDefaultData()
        genderService.initializeDefaultData()
//        maritalStatusService.initializeDefaultData()
//        relationshipService.initializeDefaultData()
//        religionService.initializeDefaultData()
        setupOptionService.initializeDefaultData()
    }
}
