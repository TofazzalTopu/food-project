package com.docu.commons

import com.docu.plugin.cxfws.CommonHelperWebService
import org.springframework.transaction.annotation.Transactional

class ReligionService extends InternationalizationService {
    static transactional = false
    CommonHelperWebService commonHelperWebService

    @Transactional(readOnly = true)
    public Religion read(Map params) {
        return Religion.read(params.id)
    }

    @Transactional(readOnly = true)
    public Map list(Map params) {
        def religionList
        long religionCount = 0
        int maxRows = Integer.parseInt(params.resultPerPage.toString())
        int rowOffset = Integer.parseInt(params.start.toString())
        String sortIndex = params.sortCol.toString()
        String sortOrder = params.sortOrder.toString()

        try {
            religionList = Religion.createCriteria().list(max: maxRows, offset: rowOffset) {
                /** ***************************
                 ///// help ref: http://grails.org/doc/1.0.x/ref/Domain%20Classes/createCriteria.html
                 if (params.boo)
                 ilike('boo', "%${params.boo}%")
                 if (params.fid)
                 eq('fid', "%${params.fid}%")
                 **************************** */
                order(sortIndex, sortOrder).ignoreCase()
            }
            religionCount = religionList.totalCount
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
        }
        return [religionList: religionList, religionCount: religionCount]
    }

    @Transactional
    public boolean save(Religion religion) {
        boolean isUpdate = true
        try {
            if (religion.id == null){
                isUpdate = false
            }
            religion.save()
            commonHelperWebService.callWebMethod(religion,'',isUpdate)
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional
    public boolean update(Religion religion) {
        try {
            religion.save()
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional
    public delete(Religion religion) {
        try {
            religion.delete()
            commonHelperWebService.callWebMethod(religion,"delete")
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional(readOnly = true)
    public Religion search(String fieldName, String fieldValue) {
        String query = "from Religion as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return Religion.find(query)
    }

    @Transactional(readOnly = true)
    public List religionList() {
        return Religion.list()
    }

    @Transactional
    public initializeDefaultData() {
        if(Religion.count() == 0){
            new Religion(religionName: "Muslim").save()
            new Religion(religionName: "Christian").save()
            new Religion(religionName: "Buddhist").save()
            new Religion(religionName: "Hindu").save()
            new Religion(religionName: "Other").save()
        }
    }
}
