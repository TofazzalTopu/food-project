package com.docu.commons

import com.docu.commons.util.GenderUtil
import com.docu.plugin.cxfws.CommonHelperWebService
import org.springframework.transaction.annotation.Transactional

class GenderService extends InternationalizationService {
    static transactional = false
    CommonHelperWebService commonHelperWebService

    @Transactional(readOnly = true)
    public Gender read(Map params) {
        return Gender.read(params.id)
    }

    @Transactional(readOnly = true)
    public Map list(Map params) {
        def genderList
        long genderCount = 0
        int maxRows = Integer.parseInt(params.resultPerPage.toString())
        int rowOffset = Integer.parseInt(params.start.toString())
        String sortIndex = params.sortCol.toString()
        String sortOrder = params.sortOrder.toString()

        try {
            genderList = Gender.createCriteria().list(max: maxRows, offset: rowOffset) {
                /** ***************************
                 ///// help ref: http://grails.org/doc/1.0.x/ref/Domain%20Classes/createCriteria.html
                 if (params.boo)
                 ilike('boo', "%${params.boo}%")
                 if (params.fid)
                 eq('fid', "%${params.fid}%")
                 **************************** */
                order(sortIndex, sortOrder).ignoreCase()
            }
            genderCount = genderList.totalCount
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
        }
        return [genderList: genderList, genderCount: genderCount]
    }

    @Transactional
    public boolean save(Gender gender) {
        boolean isUpdate = true
        try {
            if (gender.id == null){
                isUpdate = false
            }
            gender.save()
            GenderUtil.instance.resolve()
            commonHelperWebService.callWebMethod(gender,'',isUpdate)
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional
    public boolean update(Gender gender) {
        try {
            gender.save()
            GenderUtil.instance.resolve()
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional
    public delete(Gender gender) {
        try {
            gender.delete()
            GenderUtil.instance.resolve()
            commonHelperWebService.callWebMethod(gender,"delete")
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional(readOnly = true)
    public Gender search(String fieldName, String fieldValue) {
        String query = "from Gender as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return Gender.find(query)
    }

    @Transactional(readOnly = true)
    public List<Gender> genderList() {
        return Gender.list()
    }

    @Transactional
    public initializeDefaultData() {
        if(Gender.count() == 0){
            new Gender(genderType: "Male").save(validate: false)
            new Gender(genderType: "Female").save(validate: false)
            new Gender(genderType: "Other").save(validate: false)
        }
    }
}
