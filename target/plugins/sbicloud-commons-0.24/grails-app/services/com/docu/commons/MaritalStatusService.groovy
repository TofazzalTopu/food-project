package com.docu.commons

import com.docu.commons.util.MaritalStatusUtil
import com.docu.plugin.cxfws.CommonHelperWebService
import org.springframework.transaction.annotation.Transactional

class MaritalStatusService extends InternationalizationService {
    static transactional = false
    CommonHelperWebService commonHelperWebService

    @Transactional(readOnly = true)
    public MaritalStatus read(Map params) {
        return MaritalStatus.read(params.id)
    }

    @Transactional(readOnly = true)
    public Map list(Map params) {
        List<MaritalStatus> maritalStatusList = []
        long maritalStatusCount = 0
        try {
            maritalStatusList = MaritalStatus.withCriteria {
                maxResults(Integer.parseInt(params.resultPerPage.toString()))
                firstResult(Integer.parseInt(params.start.toString()))
                order(params.sortCol.toString(), params.sortOrder.toString())
            }
            maritalStatusCount = MaritalStatus.count()
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
        }
        return [maritalStatusList: maritalStatusList, maritalStatusCount: maritalStatusCount]
    }

    @Transactional
    public boolean save(MaritalStatus maritalStatus) {
        boolean isUpdate = true
        try {
            if (maritalStatus.id == null){

                isUpdate = false
            }
            maritalStatus.save()
            MaritalStatusUtil.instance.resolve()
            commonHelperWebService.callWebMethod(maritalStatus,'',isUpdate)
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional
    public boolean update(MaritalStatus maritalStatus) {
        try {
            maritalStatus.save()
            MaritalStatusUtil.instance.resolve()
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional
    public delete(MaritalStatus maritalStatus) {
        try {
            maritalStatus.delete()
            commonHelperWebService.callWebMethod(maritalStatus,"delete")
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional(readOnly = true)
    public MaritalStatus search(String fieldName, String fieldValue) {
        String query = "from MaritalStatus as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return MaritalStatus.find(query)
    }

    @Transactional(readOnly = true)
    public List maritalStatusList() {
        return MaritalStatus.list()
    }

    @Transactional
    public initializeDefaultData() {
        if(MaritalStatus.count() == 0){
            new MaritalStatus(name: "Single").save()
            new MaritalStatus(name: "Married").save()
            new MaritalStatus(name: "Separated").save()
            new MaritalStatus(name: "Divorced").save()
            new MaritalStatus(name: "Widowed").save()
            new MaritalStatus(name: "Other").save()
        }
    }
}
