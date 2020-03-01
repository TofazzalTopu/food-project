package com.docu.accesscontrol

import com.docu.accesscontrol.commons.GridEntity
import grails.converters.JSON
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty

import java.text.DateFormat
import java.text.SimpleDateFormat

class ModuleInfoController {



    static allowedMethods = [save: "POST", update: "POST", delete: "POST"];

    def index = {
        redirect(action: "show", params: params)
    }

    def list = { /** this will show default list via Ajax in a Flexigrid view   */}

    def show = {
        ModuleInfo moduleInfoInstance = new ModuleInfo()
        moduleInfoInstance.properties = params
        return [moduleInfoInstance: moduleInfoInstance]
    }

    /**
     * -----------------------------------------
     * for Flexigrid to operate via Ajax
     * -----------------------------------------
     */

    /**
     *
     * This function will return a list of all ModuleInfo via an Ajax call
     * in the form of JSON. A list can be normal list containing all the
     * objects in the back-end, or user can search based on a column,
     * in which case the function will run a case-insensitive LIKE query.
     *
     */
    def getModuleInfoGridJSON = {

        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        // pagination
        int pageNum = params.page ? params.int('page') : 1
        int resultPerPage = params.rows ? params.int('rows') : 10
        long start = ((pageNum - 1) * resultPerPage);
        long total = 0

        // sorting
        def sortCol = params.sidx ? params.sidx : "id"
        def sortOrder = params.sortorder ? params.sortorder : "desc"

        def moduleInfoList = null

        def moduleInfoResult = this.searchModuleInfosFromGrid(params, resultPerPage, start, sortCol, sortOrder)
        if (moduleInfoResult) { // in case: query
            moduleInfoList = moduleInfoResult.moduleInfoList
            total = moduleInfoResult.count
        } else if ((moduleInfoResult = this.getModuleInfoList(params, resultPerPage, start, sortCol, sortOrder))) { // in case: normal list
            moduleInfoList = moduleInfoResult.moduleInfoList
            total = moduleInfoResult.count
        }

        def pageCount = 1
        if (total > resultPerPage) {
            pageCount = Math.ceil(total / resultPerPage)
        }

        moduleInfoList = formatData(moduleInfoList)
        def moduleInfos = this.wrapModuleInfoListInGridEntityList(moduleInfoList, start)
        def output = [page: pageNum, total: pageCount, records: total, rows: moduleInfos] as JSON
        render output;
    }

    /**
     * Wrapping each ModuleInfo entity in GridEntity object (required representation of object
     * for Flexigrid), create a list of GridEntity, and then returns
     *
     * @param moduleInfoList List of all ModuleInfos
     * @param start start offset, required to set counter
     * @return List of GridEntity
     */
    private def wrapModuleInfoListInGridEntityList(moduleInfoList, start) {

        def moduleInfos = []
        def counter = start + 1;
        moduleInfoList.each { moduleInfo ->
            GridEntity obj = new GridEntity()
            obj.id = moduleInfo.id
            obj.cell = ["${counter}", "${moduleInfo.id ? moduleInfo.id : ''}", "${moduleInfo.moduleName ? moduleInfo.moduleName : ''}","${moduleInfo.moduleCode ? moduleInfo.moduleCode : ''}","${moduleInfo.url ? moduleInfo.url : ''}","${moduleInfo.gspFileName ? moduleInfo.gspFileName : ''}", "${moduleInfo.description ? moduleInfo.description : ''}"]
            moduleInfos << obj
            counter++
        };
        return moduleInfos
    }

    /**
     * This function will first check whether the end-user has initiated a query
     * from the Flexigrid against a column; it it is a query, it queries the database
     * table and returns the result. If it's not a query, it returns null
     *
     * CAUTION:
     * 	1. To count result for search, this function uses in-efficient way.
     * 	2. Be careful to search when the column type is something other
     * 	   than string (VARCHAR)
     *
     * @param params Request parameters
     * @param resultPerPage how many result to display per page
     * @param start start offset
     * @param sortCol column to sort
     * @param sortOrder sort order (e.g., ASC or DESC)
     * @return return search result if it is a query, null otherwise
     */
    private def searchModuleInfosFromGrid(params, resultPerPage, start, sortCol, sortOrder) {

        // check to see if it's a search by field (single)
        String query = params.query
        String qtype = params.qtype

//        if (query) {
        List moduleInfoList = ModuleInfo.list()
        long total = moduleInfoList.size()

        return [moduleInfoList: moduleInfoList, count: total]
//        } else {
//            return null
//        }
    }

    /**
     * Returns a list of ModuleInfos from the back-end table, which supports
     * pagination from within Flexigrid
     *
     * @param params Request parameters
     * @param resultPerPage how many result to display per page
     * @param start start offset
     * @param sortCol column to sort
     * @param sortOrder sort order (e.g., ASC or DESC)
     * @return List of ModuleInfos
     */
    private def getModuleInfoList(params, resultPerPage, start, sortCol, sortOrder) {
        List moduleInfoList = ModuleInfo.withCriteria {
            maxResults(resultPerPage)
            firstResult(start)
            order(sortCol, sortOrder)
        } as List
        def total = ModuleInfo.count();
        return [moduleInfoList: moduleInfoList, count: total]
    }

    /**
     * Saves a ModuleInfo via Ajax call
     * If the ModuleInfo is brand-new entity, it inserts, updates otherwise.
     * Before committing, it tries to validate the ModuleInfo object, if
     * validation fails, it returns the error(s) back to the caller in the form
     * of JSON error obejct array.
     * If validation passes, it saves the entity and return the entity in the form
     * of JSON
     */

    def saveModuleInfoRemotely = {

        def result = [isError: false, entity: null];

        def moduleInfoId = params.id;

        def isNew = true;
        if (moduleInfoId) {
            isNew = false;
        }

        def moduleInfoInstance = null;
        if (!isNew) {
            Map integrityResult = this.getModuleInfoWithIntegrityCheckForEdit(params)
//            if (integrityResult.isError) {
//                render integrityResult as JSON
//                return
//            }
            moduleInfoInstance = integrityResult.entity
        } else {
            moduleInfoInstance = new ModuleInfo(params)
        }


        moduleInfoInstance.validate();
        if (moduleInfoInstance.hasErrors()) {
            def errors = []
            moduleInfoInstance.errors.allErrors.each {
                errors << [it.field, g.message(error: it)]
            }
            result = [isError: true, entity: null, errors: errors];
        } else if (moduleInfoInstance.save(flush: true)) {
//            ModuleInfoUtil.instance.resolve()
            GridEntity obj = new GridEntity();
            obj.id = moduleInfoInstance.id;
            obj.cell = ["${moduleInfoInstance.id}", "${moduleInfoInstance.moduleName}", "${moduleInfoInstance.description}"];
            result = [isError: false, entity: obj, version: moduleInfoInstance.version];
        }
        String output = result as JSON
        render output
    }

    /**
     * Check to see is there any integrity errors while getting an instance of ModuleInfo
     * at the time of editing
     *
     * @param params Request parameters
     * @return Exisitng ModuleInfo instance as an entity if there is no integrity errors with within a data structure
     */
    private def getModuleInfoWithIntegrityCheckForEdit(params) {

        Map result = [:]
        String errors = []
        ModuleInfo moduleInfoInstance = ModuleInfo.get(params.id)
        if (moduleInfoInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (moduleInfoInstance.version > version) {
                    errors << ["Another user has updated this ModuleInfo while you were editing"]
                    result = [isError: true, entity: null, errors: errors]
                } else {
                    moduleInfoInstance.properties = params
                    result = [isError: false, entity: moduleInfoInstance, errors: null]
                }
            }
        }
        else {
            errors << ["No ModuleInfo found with this id or might have been deleted by someone!"];
            result = [isError: true, entity: null, errors: errors];
        }
        return result

    }

    /**
     *
     * Retrieves a ModuleInfo via Ajax call against the id attribute (default primary key)
     * If it finds an object against the provided id, it returns the ModuleInfo in the form
     * of JSON, null otherwise.
     * CAUTION:
     * 	1. 	It returns the version property of the entity, so make sure that version is
     * 		not false in the domain class definition
     *
     */
    def getModuleInfoJSON = {

        String result = [entity: null];
        Map data = [:]
        ModuleInfo moduleInfoInstance = ModuleInfo.read(params.id)
        if (moduleInfoInstance) {
            data.put("id", moduleInfoInstance.id.toString())
            data.put("version", moduleInfoInstance.version.toString())

            GrailsDomainClassProperty[] propertyList = null
            propertyList = new DefaultGrailsDomainClass(moduleInfoInstance.class).getPersistantProperties()
            Object propertyValue = null
            String propertyName = null
            for (property in propertyList) {
                propertyName = property.name
                propertyValue = moduleInfoInstance.properties.get(propertyName)
                propertyValue = propertyValue != null ? propertyValue : ""
                data.put(propertyName, propertyValue)
            }
        }
        render data as JSON;
    }

    /**
     * Deletes a ModuleInfo entity from the back-end,which is
     * retrieved against the requested id (params.id). Upon
     * successful deletion, it returns TRUE, FALSE otherwise
     */

    def deleteModuleInfoAjax = {

        String ids = params.id.split('_');

        long count = ids.length;
        long delCount = 0;
        ids.each {
            try {
                ModuleInfo moduleInfoInstance = ModuleInfo.get(it as String)
                if (moduleInfoInstance) {
                    try {
                        moduleInfoInstance.delete(flush: true)
//                        ModuleInfoUtil.instance.resolve()
                        delCount++;
                    }
                    catch (org.springframework.dao.DataIntegrityViolationException e) {
                    }
                } else {
                    // @todo: log error here
                }
            } catch (Exception e) {
                // @todo: log error here
            }
        }

        String result = [deleted: false];
        if (delCount == count) {
            result = [deleted: true];
        }

        render result as JSON;

    }

    private def formatData(moduleInfoList) {
        List moduleInfos = []
        moduleInfoList.each { moduleInfo ->
            Map properties = [:]
            moduleInfo.properties.each { key, val ->
                String fieldValue = val.toString()
                def matcher = fieldValue =~ /(.+)-(.+)-(.+) /
                //if(matcher.matches()){
                matcher.each {haystack, y, m, d -> fieldValue = d + "-" + m + "-" + y }
                properties.put(key, fieldValue)
                //}
            }

            moduleInfos.add(properties)
        }

        return moduleInfos
    }

    /**
     * -----------------------------------------
     * for Flexigrid to operate via Ajax
     * -----------------------------------------
     */
}
