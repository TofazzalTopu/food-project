package com.docu.commons


import org.springframework.transaction.annotation.Transactional

class BloodGroupService extends InternationalizationService {
    static transactional = false



    @Transactional(readOnly = true)
    public BloodGroup read(Map params) {
        return BloodGroup.read(params.id)
    }

    @Transactional(readOnly = true)
    public Map list(Map params) {
        List<BloodGroup> bloodGroupList = []
        long bloodGroupCount = 0
        try {
            bloodGroupList = BloodGroup.withCriteria {


                maxResults(Integer.parseInt(params.resultPerPage.toString()))
                firstResult(Integer.parseInt(params.start.toString()))
                order(params.sortCol.toString(), params.sortOrder.toString())
            }
            bloodGroupCount = BloodGroup.count()
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return [bloodGroupList: bloodGroupList, bloodGroupCount: bloodGroupCount]
    }

    @Transactional
    public boolean save(BloodGroup bloodGroup) {
        try {
            bloodGroup.save()
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return true
    }

    @Transactional
    public boolean update(BloodGroup bloodGroup) {
        try {
            bloodGroup.save()
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return true
    }

    @Transactional
    public delete(BloodGroup bloodGroup) {
        try {
            bloodGroup.delete()
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return true
    }

    @Transactional
    public initializeDefaultData() {
        if(BloodGroup.count() == 0){
            new BloodGroup(groupName: "A-").save()
            new BloodGroup(groupName: "A+").save()
            new BloodGroup(groupName: "B-").save()
            new BloodGroup(groupName: "B+").save()
            new BloodGroup(groupName: "AB-").save()
            new BloodGroup(groupName: "AB+").save()
            new BloodGroup(groupName: "O-").save()
            new BloodGroup(groupName: "O+").save()
        }
    }
}
