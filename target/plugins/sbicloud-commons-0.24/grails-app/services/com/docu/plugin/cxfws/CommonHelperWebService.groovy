package com.docu.plugin.cxfws

import com.docu.commons.CommonConstants

import com.docu.dto.DtoUtil
import com.docu.webservice.WebRouteTable
import com.docu.ws.util.WebRouteUtil
import com.docu.plugin.wsdl.*

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

class CommonHelperWebService {
    public static final Log log = LogFactory.getLog(CommonHelperWebService.class)
    public static final String APP_NAME = "syncclient"//ConfigurationHolder.config.application.name
    static transactional = true

    def grailsApplication

    @Autowired
    DtoUtil dtoUtil
    @Autowired
    WebRouteUtil webRouteUtil

    @Transactional(readOnly = true)
    public List<WebRouteTable> getWebRouteList() {
        List<WebRouteTable> webRouteTableList = WebRouteTable.findAllByDomainStatusId(CommonConstants.DOMAIN_STATUS_ACTIVE)
        return webRouteTableList
    }

    public boolean callWebMethod(Object object, String operationType = "", boolean isInsert = true) {
        boolean isOk = false
        if (CommonConstants.ROUTER_COUNT > 0) {
            String serverIP = ""
            String methodName = ""
            Map errorMap = [:]
            try {
                def domainDto = grailsApplication.classLoader.loadClass('com.docu.plugin.wsdl.' + object.class.simpleName + "DTO").newInstance()
                domainDto = dtoUtil.toDTO(object, domainDto)
                List<WebRouteTable> webRouteTableList = webRouteUtil.webRouteTableList
                webRouteTableList.each {
                    serverIP = it.routeIP
                    URL url = new URL("http://" + it.routeIP + "/services/commonPluginWeb?wsdl")
                    // CommonWebServiceWSDL commonWebService = new CommonWebServiceWSDL(url);
                    CommonPluginWebServiceWSDL commonPluginWebService = new CommonPluginWebServiceWSDL(url);
                    if (operationType == "") {
                        methodName = 'save' + object.class.simpleName
                        isOk = commonPluginWebService.getCommonPluginWebServicePort()."${methodName}"(domainDto)
                    } else if (operationType == 'reset') {
                        methodName = operationType + object.class.simpleName
                        isOk = commonPluginWebService.getCommonPluginWebServicePort()."${methodName}"(domainDto)
                    } else {
                        methodName = operationType + object.class.simpleName
                        isOk = commonPluginWebService.getCommonPluginWebServicePort()."${methodName}"(domainDto)
                    }
                }
            } catch (Exception ex) {
                log.error(serverIP + ' ' + object.class.simpleName + ex.message)
                throw new RuntimeException(ex)
            }
        }
        return isOk
    }

    public List callApplicationUserAndDependencies(Map originalMap, String operationType = "") {
        boolean isOk = false
        def domainDto = null
        List preferenceList = []
        List deletePreferenceList = []
        List userAuthorityList = []
        List deleteUserAuthorityList = []
        List applicationUserList = []
        List<ApplicationUserAuthorityDTO> applicationUserAuthorityDTOList = []
        List<ApplicationUserAuthorityDTO> deleteUserAuthorityDTOList = []
        List<UserPortletPreferenceDTO> userPortletPreferenceDTOList = []
        List<UserPortletPreferenceDTO> deletePortletPreferenceDTOList = []
        List<ApplicationUserDTO> applicationUserDTOList = []
        List errorList = []

        if (CommonConstants.ROUTER_COUNT > 0) {
            String serverIP = ""
            String methodName = ""
            Map errorMap = [:]
            try {


                def dependencyDto = grailsApplication.classLoader.loadClass('com.docu.plugin.wsdl.ApplicationUserAndDependenciesDTO').newInstance()

                if (originalMap.containsKey('applicationUser')) {
                    if (originalMap.get('applicationUser')) {
                        domainDto = grailsApplication.classLoader.loadClass('com.docu.plugin.wsdl.' + originalMap.get('applicationUser').class.simpleName + "DTO").newInstance()
                        domainDto = dtoUtil.toDTO(originalMap.get('applicationUser'), domainDto)
                        dependencyDto.applicationUserDTO = domainDto
                    }
                }

                if (originalMap.containsKey('applicationUserList')){
                    if (originalMap.get('applicationUserList')){
                        applicationUserList = (List) originalMap.get('applicationUserList');
                        applicationUserList.each {
                            domainDto = grailsApplication.classLoader.loadClass('com.docu.plugin.wsdl.' + it.class.simpleName + "DTO").newInstance()
                            domainDto = dtoUtil.toDTO(it,domainDto)
                            applicationUserDTOList.add(domainDto)
                        }
                    }
                }

                if (originalMap.containsKey('userPreference')) {
                    if (originalMap.get('userPreference')) {
                        domainDto = grailsApplication.classLoader.loadClass('com.docu.plugin.wsdl.' + originalMap.get('userPreference').class.simpleName + "DTO").newInstance()
                        domainDto = dtoUtil.toDTO(originalMap.get('userPreference'), domainDto)
                        dependencyDto.userPreferenceDTO = domainDto
                    }
                }

                if (originalMap.containsKey('deleteApplicationUserAuthority')){
                    if (originalMap.get('deleteApplicationUserAuthority')){
                        deleteUserAuthorityList = (List) originalMap.get('deleteApplicationUserAuthority');
                        deleteUserAuthorityList.each {
                            domainDto = grailsApplication.classLoader.loadClass('com.docu.plugin.wsdl.' + it.class.simpleName + "DTO").newInstance()
                            domainDto = dtoUtil.toDTO(it, domainDto)
                            deleteUserAuthorityDTOList.add(domainDto)
                        }
                    }
                }

                if (originalMap.containsKey('applicationUserAuthority')){
                    if (originalMap.get('applicationUserAuthority')){
                        userAuthorityList = (List) originalMap.get('applicationUserAuthority');
                        userAuthorityList.each {
                            domainDto = grailsApplication.classLoader.loadClass('com.docu.plugin.wsdl.' + it.class.simpleName + "DTO").newInstance()
                            domainDto = dtoUtil.toDTO(it, domainDto)
                            applicationUserAuthorityDTOList.add(domainDto)
                        }
                    }
                }

                if (originalMap.containsKey('deleteUserPortletPreferenceList')){
                    if (originalMap.get('deleteUserPortletPreferenceList')){
                        deletePreferenceList = (List) originalMap.get('deleteUserPortletPreferenceList');
                        deletePreferenceList.each {
                            domainDto = grailsApplication.classLoader.loadClass('com.docu.plugin.wsdl.' + it.class.simpleName + "DTO").newInstance()
                            domainDto = dtoUtil.toDTO(it, domainDto)
                            deletePortletPreferenceDTOList.add(domainDto)
                        }
                    }
                }

                if (originalMap.containsKey('userPortletPreference')){
                    if (originalMap.get('userPortletPreference')){
                        preferenceList = (List) originalMap.get('userPortletPreference');
                        preferenceList.each {
                            domainDto = grailsApplication.classLoader.loadClass('com.docu.plugin.wsdl.' + it.class.simpleName + "DTO").newInstance()
                            domainDto = dtoUtil.toDTO(it, domainDto)
                            userPortletPreferenceDTOList.add(domainDto)
                        }
                    }
                }

                dependencyDto.userPortletPreferenceDTOList = userPortletPreferenceDTOList
                dependencyDto.applicationUserAuthorityDTOList = applicationUserAuthorityDTOList
                dependencyDto.deleteUserAuthorityDTOList = deleteUserAuthorityDTOList
                dependencyDto.deletePortletPreferenceDTOList = deletePortletPreferenceDTOList
                dependencyDto.applicationUserDTOList = applicationUserDTOList

                List<WebRouteTable> webRouteTableList = webRouteUtil.webRouteTableList
                webRouteTableList.each {
                    serverIP = it.routeIP

                    try {
                        //  URL url = new URL("http://" + it.routeIP + "/services/bulkTransactionWeb?wsdl");
                        URL url = new URL("http://" + it.routeIP + "/services/commonPluginWeb?wsdl");
                        // BulkTransactionWebServiceWSDL bulkTransactionServicesWSDL = new BulkTransactionWebServiceWSDL(url)
                        CommonPluginWebServiceWSDL commonPluginWebService = new CommonPluginWebServiceWSDL(url)

                        if (operationType == "") {
                            methodName = 'saveApplicationUserAndDependencies'
                            //   isOk = bulkTransactionServicesWSDL.getBulkTransactionWebServicePort()."${methodName}"(dependencyDto)
                            isOk = commonPluginWebService.getCommonPluginWebServicePort()."${methodName}"(dependencyDto)
                        } else {
                            methodName = 'deleteApplicationUserAndDependencies'
                            isOk = commonPluginWebService.getCommonPluginWebServicePort()."${methodName}"(dependencyDto)
                        }
                    }catch(Exception ex){
                        log.error(serverIP + ' ' + ex.message)

                    }
                }

            } catch (Exception ex) {
                log.error(serverIP + ' ' + ex.message)
                throw new RuntimeException(ex)
            }
        }
        return errorList
    }


}