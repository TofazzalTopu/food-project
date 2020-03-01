package com.docu.security

import com.docu.commons.CommonConstants
import com.docu.commons.InternationalizationService
import com.docu.security.UrlWrapper
import org.springframework.transaction.annotation.Transactional

class UrlWrapperService extends InternationalizationService {
    static transactional = false


    @Transactional(readOnly = true)
    public UrlWrapper read(Map params) {
        return UrlWrapper.read(params.id)
    }

    @Transactional(readOnly = true)
    public Map list(Map params) {
        List<UrlWrapper> urlWrapperList = []
        long urlWrapperCount = 0
        try {
            urlWrapperList = UrlWrapper.withCriteria {


                maxResults(Integer.parseInt(params.resultPerPage.toString()))
                firstResult(Integer.parseInt(params.start.toString()))
                order(params.sortCol.toString(), params.sortOrder.toString())
            }
            urlWrapperCount = UrlWrapper.count()
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return [urlWrapperList: urlWrapperList, urlWrapperCount: urlWrapperCount]
    }

    @Transactional
    public boolean save(UrlWrapper urlWrapper) {
        try {


            urlWrapper.save()
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return true
    }

    @Transactional
    public boolean update(UrlWrapper urlWrapper) {
        try {
            urlWrapper.save()
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return true
    }

    @Transactional
    public delete(UrlWrapper urlWrapper) {
        try {


            urlWrapper.delete()

        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return true
    }
}
