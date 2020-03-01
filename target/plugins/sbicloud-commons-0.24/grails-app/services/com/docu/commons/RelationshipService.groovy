package com.docu.commons

import com.docu.plugin.cxfws.CommonHelperWebService
import org.springframework.transaction.annotation.Transactional

class RelationshipService extends InternationalizationService {
    static transactional = false
    CommonHelperWebService commonHelperWebService

    @Transactional(readOnly = true)
    public Relationship read(Map params) {
        return Relationship.read(params.id)
    }

    @Transactional(readOnly = true)
    public Map list(Map params) {
        List<Relationship> relationshipList = []
        long relationshipCount = 0
        try {
            relationshipList = Relationship.withCriteria {
                //ne("id", CommonConstants.EMPLOYEE_RELATIONSHIP_TYPE_OTHERS_ID)
                maxResults(Integer.parseInt(params.resultPerPage.toString()))
                firstResult(Integer.parseInt(params.start.toString()))
                order(params.sortCol.toString(), params.sortOrder.toString())
            }
            relationshipCount = Relationship.count()
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
        }

        return [relationshipList: relationshipList, relationshipCount: relationshipCount]
    }

    @Transactional
    public boolean save(Relationship relationship) {
        boolean isUpdate = true

        try {
            relationship.save()
            commonHelperWebService.callWebMethod(relationship,'',isUpdate)
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional
    public boolean update(Relationship relationship) {
        try {
            relationship.save()
        }
        catch (Exception ex) {

            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }

        return true
    }

    @Transactional
    public delete(Relationship relationship) {
        try {
            relationship.delete()
            commonHelperWebService.callWebMethod(relationship,"delete")
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional(readOnly = true)
    public Relationship search(String fieldName, String fieldValue) {
        String query = "from Relationship as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return Relationship.find(query)
    }

    @Transactional
    public initializeDefaultData() {
        if(Relationship.count() == 0){
            new Relationship(name: "Spouse").save()
            new Relationship(name: "Father").save()
            new Relationship(name: "Mother").save()
            new Relationship(name: "Son").save()
            new Relationship(name: "Daughter").save()
            new Relationship(name: "Brother").save()
            new Relationship(name: "Sister").save()
            new Relationship(name: "Father-in-law").save()
            new Relationship(name: "Mother-in-law").save()
            new Relationship(name: "Brother-in-law").save()
            new Relationship(name: "Sister-in-law").save()
            new Relationship(name: "Other").save()
        }
    }
}
