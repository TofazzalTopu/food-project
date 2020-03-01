package com.bits.bdfp.currency


import com.docu.commons.InternationalizationService
import org.springframework.transaction.annotation.Transactional
import com.docu.commons.CommonConstants



class LocalCurrencyService extends InternationalizationService {
    static transactional = false
    
    

    @Transactional(readOnly = true)
    public LocalCurrency read(Map params) {
        return LocalCurrency.read(params.id)
    }

    @Transactional(readOnly = true)
    public Map list(Map params) {
        List<LocalCurrency> localCurrencyList = []
        long localCurrencyCount = 0
        try{
            localCurrencyList = LocalCurrency.withCriteria {
                
                
                maxResults(Integer.parseInt(params.resultPerPage.toString()))
                firstResult(Integer.parseInt(params.start.toString()))
                order(params.sortCol.toString(), params.sortOrder.toString())
            }
            localCurrencyCount = LocalCurrency.count()
        }
        catch(Exception ex){
            log.error(ex.message)
        }

        return [localCurrencyList: localCurrencyList, localCurrencyCount: localCurrencyCount]
    }

    @Transactional
    public boolean save(LocalCurrency localCurrency) {
        try {
            
            
            localCurrency.save()
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return true
    }

    @Transactional
    public boolean update(LocalCurrency localCurrency) {
        try {
            localCurrency.save()
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return true
    }

    @Transactional
    public delete(LocalCurrency localCurrency) {
        try {
            
            
            localCurrency.delete()
            
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return true
    }

    @Transactional(readOnly = true)
    public LocalCurrency search(String fieldName, String fieldValue) {
        String query = "from LocalCurrency as docu where docu."+ fieldName +" = '" + fieldValue +"'"
        return LocalCurrency.find(query)
    }
}
