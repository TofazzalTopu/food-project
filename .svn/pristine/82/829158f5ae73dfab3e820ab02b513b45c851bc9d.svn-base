package com.bits.bdfp.currency


import com.docu.commons.InternationalizationService
import org.springframework.transaction.annotation.Transactional
import com.docu.commons.CommonConstants


class CurrencyDemonstrationService extends InternationalizationService {
    static transactional = false


    @Transactional(readOnly = true)
    public CurrencyDemonstration read(Map params) {
        return CurrencyDemonstration.read(params.id)
    }

    @Transactional(readOnly = true)
    public Map list(Map params) {
        List<CurrencyDemonstration> currencyDemonstrationList = []
        long currencyDemonstrationCount = 0
        try {
            currencyDemonstrationList = CurrencyDemonstration.withCriteria {
                if (params.localCurrencyId) {
                    eq("localCurrency.id", Long.parseLong(params.localCurrencyId))
                }
                maxResults(Integer.parseInt(params.resultPerPage.toString()))
                firstResult(Integer.parseInt(params.start.toString()))
                order(params.sortCol.toString(), params.sortOrder.toString())
            }
            currencyDemonstrationCount = CurrencyDemonstration.count()
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return [currencyDemonstrationList: currencyDemonstrationList, currencyDemonstrationCount: currencyDemonstrationCount]
    }

    @Transactional
    public boolean save(CurrencyDemonstration currencyDemonstration) {
        try {
            currencyDemonstration.save()
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return true
    }

    @Transactional
    public boolean update(CurrencyDemonstration currencyDemonstration) {
        try {
            currencyDemonstration.save()
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return true
    }

    @Transactional
    public delete(CurrencyDemonstration currencyDemonstration) {
        try {
            currencyDemonstration.delete()
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return true
    }

    @Transactional(readOnly = true)
    public CurrencyDemonstration search(String fieldName, String fieldValue) {
        String query = "from CurrencyDemonstration as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return CurrencyDemonstration.find(query)
    }
}
