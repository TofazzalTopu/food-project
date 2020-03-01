package com.docu.commons

import com.docu.plugin.cxfws.CommonHelperWebService
import org.springframework.transaction.annotation.Transactional

class AddressTypeService extends InternationalizationService {

    static transactional = false

    CommonHelperWebService commonHelperWebService

    @Transactional(readOnly = true)
    public AddressType read(Map params) {
        return AddressType.read(params.id)
    }

    @Transactional(readOnly = true)
    public Map list(Map params) {
        def addressTypeList
        long addressTypeCount = 0
        int maxRows = Integer.parseInt(params.resultPerPage.toString())
        int rowOffset = Integer.parseInt(params.start.toString())
        String sortIndex = params.sortCol.toString()
        String sortOrder = params.sortOrder.toString()

        try{
            addressTypeList = AddressType.createCriteria().list(max: maxRows, offset: rowOffset) {
                /*****************************
                ///// help ref: http://grails.org/doc/1.0.x/ref/Domain%20Classes/createCriteria.html
                if (params.boo)
                    ilike('boo', "%${params.boo}%")
                if (params.fid)
                    eq('fid', "%${params.fid}%")
                *****************************/
                order(sortIndex, sortOrder).ignoreCase()
            }
            addressTypeCount = addressTypeList.totalCount
        }
        catch(Exception ex){
            log.error(getExceptionMessage(ex))
        }
        return [addressTypeList: addressTypeList, addressTypeCount: addressTypeCount]
    }

    @Transactional
    public boolean save(AddressType addressType) {
        boolean isUpdate = true
        try {
            if (addressType.id == null){
                isUpdate = false
            }
            addressType.save()
            commonHelperWebService.callWebMethod(addressType,'',isUpdate)
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional
    public boolean update(AddressType addressType) {
        try {
            addressType.save()
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional
    public delete(AddressType addressType) {
        try {
            addressType.delete()
            commonHelperWebService.callWebMethod(addressType,"delete")
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional(readOnly = true)
    public AddressType search(String fieldName, String fieldValue) {
        String query = "from AddressType as docu where docu."+ fieldName +" = '" + fieldValue +"'"
        return AddressType.find(query)
    }

    @Transactional(readOnly = true)
    public List<AddressType> getAddressTypeList(){
        return AddressType.list()
    }

    @Transactional
    public initializeDefaultData() {
        if(AddressType.count() == 0){
            new AddressType(addressTypeName: "Present").save()
            new AddressType(addressTypeName: "Permanent").save()
            new AddressType(addressTypeName: "Emergency").save()
            new AddressType(addressTypeName: "Work").save()
        }
    }
}
