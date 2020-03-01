package com.bits.bdfp.common


import com.docu.commons.InternationalizationService
import org.springframework.transaction.annotation.Transactional
import com.docu.commons.CommonConstants



class DocumentTypeService extends InternationalizationService {
    static transactional = false
    
    

    @Transactional(readOnly = true)
    public DocumentType read(Map params) {
        return DocumentType.read(params.id)
    }

    @Transactional(readOnly = true)
    public Map list(Map params) {
        List<DocumentType> documentTypeList = []
        long documentTypeCount = 0
        try{
            documentTypeList = DocumentType.withCriteria {
                
                
                maxResults(Integer.parseInt(params.resultPerPage.toString()))
                firstResult(Integer.parseInt(params.start.toString()))
                order(params.sortCol.toString(), params.sortOrder.toString())
            }
            documentTypeCount = DocumentType.count()
        }
        catch(Exception ex){
            log.error(ex.message)
        }

        return [documentTypeList: documentTypeList, documentTypeCount: documentTypeCount]
    }

    @Transactional
    public boolean save(DocumentType documentType) {
        try {
            
            
            documentType.save()
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return true
    }

    @Transactional
    public boolean update(DocumentType documentType) {
        try {
            documentType.save()
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return true
    }

    @Transactional
    public delete(DocumentType documentType) {
        try {
            
            
            documentType.delete()
            
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return true
    }

    @Transactional(readOnly = true)
    public DocumentType search(String fieldName, String fieldValue) {
        String query = "from DocumentType as docu where docu."+ fieldName +" = '" + fieldValue +"'"
        return DocumentType.find(query)
    }
}
