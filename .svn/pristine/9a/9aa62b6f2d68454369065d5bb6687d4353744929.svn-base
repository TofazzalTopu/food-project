package com.bits.bdfp.common

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.commons.InternationalizationService
import org.springframework.transaction.annotation.Transactional
import com.docu.commons.CommonConstants



class CodeGenerationFormatService extends InternationalizationService {
    static transactional = false
    
    

    @Transactional(readOnly = true)
    public CodeGenerationFormat read(Map params) {
        return CodeGenerationFormat.read(params.id)
    }

    @Transactional(readOnly = true)
    public Map list(Map params) {
        List<CodeGenerationFormat> codeGenerationFormatList = []
        long codeGenerationFormatCount = 0
        try{
            codeGenerationFormatList = CodeGenerationFormat.withCriteria {
                
                
                maxResults(Integer.parseInt(params.resultPerPage.toString()))
                firstResult(Integer.parseInt(params.start.toString()))
                order(params.sortCol.toString(), params.sortOrder.toString())
            }
            codeGenerationFormatCount = CodeGenerationFormat.count()
        }
        catch(Exception ex){
            log.error(ex.message)
        }

        return [codeGenerationFormatList: codeGenerationFormatList, codeGenerationFormatCount: codeGenerationFormatCount]
    }

//    @Transactional
//    public boolean save(CodeGenerationFormat codeGenerationFormat) {
//        try {
//
//
//            codeGenerationFormat.save()
//        }
//        catch (Exception ex) {
//            log.error(ex.message)
//            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
//        }
//
//        return true
//    }

    @Transactional
    public boolean update(CodeGenerationFormat codeGenerationFormat) {
        try {
            codeGenerationFormat.save()
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return true
    }

    @Transactional
    public delete(CodeGenerationFormat codeGenerationFormat) {
        try {
            
            
            codeGenerationFormat.delete()
            
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return true
    }

    @Transactional(readOnly = true)
    public CodeGenerationFormat search(String fieldName, String fieldValue) {
        String query = "from CodeGenerationFormat as docu where docu."+ fieldName +" = '" + fieldValue +"'"
        return CodeGenerationFormat.find(query)
    }

    @Transactional(readOnly = true)
    public CodeGenerationFormat findByKey(String key) {
        CodeGenerationFormat codeGenerationFormat=  CodeGenerationFormat.findByKeyCode(key)
        return codeGenerationFormat
    }

    @Transactional
    public CodeGenerationFormat save(CodeGenerationFormat codeGenerationFormat) {
        try{
            codeGenerationFormat.save()
            return codeGenerationFormat
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public CodeGenerationFormat findByKeyAndEnterprise(String key,EnterpriseConfiguration enterpriseConfiguration) {
        CodeGenerationFormat codeGenerationFormat=  CodeGenerationFormat.findByKeyCodeAndEnterpriseConfiguration(key,enterpriseConfiguration)
        return codeGenerationFormat
    }

    @Transactional(readOnly = true)
    public CodeGenerationFormat findByEnterpriseAndYear(EnterpriseConfiguration enterpriseConfiguration,String year) {
        CodeGenerationFormat codeGenerationFormat=  CodeGenerationFormat.findByEnterpriseConfigurationAndProductYear(enterpriseConfiguration,year)
        return codeGenerationFormat
    }
}
