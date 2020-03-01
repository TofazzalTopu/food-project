//

package com.docu.security



import grails.converters.JSON;
import com.docu.commons.GridEntity;

class ApplicationUserAuthorityController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"];

    def index = {
        redirect(action: "show", params: params)
    }

    def list = { /** this will show default list via Ajax in a Flexigrid view */}

    def show = {
        def applicationUserAuthorityInstance = new ApplicationUserAuthority()
        applicationUserAuthorityInstance.properties = params
        return [applicationUserAuthorityInstance: applicationUserAuthorityInstance]
    }
   
     /**
      * -----------------------------------------
      * for Flexigrid to operate via Ajax
      * -----------------------------------------
      */

     /**
      *
      * This function will return a list of all ApplicationUserAuthority via an Ajax call
      * in the form of JSON. A list can be normal list containing all the
      * objects in the back-end, or user can search based on a column,
      * in which case the function will run a case-insensitive LIKE query.
      *
      */
     def getApplicationUserAuthorityGridJSON = {

    	params.max = Math.min(params.max ? params.int('max') : 10, 100)

    	// pagination
    	def pageNum = params.page ? params.int('page') : 1
    	def resultPerPage = params.rows ? params.int('rows') : 10
        def start = ((pageNum - 1) * resultPerPage);
        def total = 0

        // sorting
    	def sortCol = params.sidx ? params.sidx : "id"
    	def sortOrder = params.sortorder ? params.sortorder : "desc"

    	def applicationUserAuthorityList = null

        def applicationUserAuthorityResult = this.searchApplicationUserAuthoritysFromGrid(params, resultPerPage, start, sortCol, sortOrder)
        if (applicationUserAuthorityResult) { // in case: query
          applicationUserAuthorityList = applicationUserAuthorityResult.applicationUserAuthorityList
          total = applicationUserAuthorityResult.count
        } else if ( (applicationUserAuthorityResult = this.getApplicationUserAuthorityList(params, resultPerPage, start, sortCol, sortOrder)) ) { // in case: normal list
          applicationUserAuthorityList = applicationUserAuthorityResult.applicationUserAuthorityList
          total = applicationUserAuthorityResult.count
        }

        def pageCount = 1
        if(total > resultPerPage){
          pageCount  = Math.ceil(total/resultPerPage)
        }

        applicationUserAuthorityList = formatData(applicationUserAuthorityList)
        def applicationUserAuthoritys = this.wrapApplicationUserAuthorityListInGridEntityList(applicationUserAuthorityList, start)
        def output = [page: pageNum, total: pageCount, records: total, rows: applicationUserAuthoritys] as JSON
        render output;
     }


    /**
     * Wrapping each ApplicationUserAuthority entity in GridEntity object (required representation of object
     * for Flexigrid), create a list of GridEntity, and then returns
     *
     * @param applicationUserAuthorityList List of all ApplicationUserAuthoritys
     * @param start start offset, required to set counter
     * @return List of GridEntity
     */
    private def wrapApplicationUserAuthorityListInGridEntityList(applicationUserAuthorityList, start) {

      def applicationUserAuthoritys = []
      def counter = start + 1;
      applicationUserAuthorityList.each { applicationUserAuthority ->
          GridEntity obj = new GridEntity()
          obj.id = applicationUserAuthority.id
          obj.cell = ["${counter}","${applicationUserAuthority.id ? applicationUserAuthority.id : ''}","${applicationUserAuthority.applicationUser ? applicationUserAuthority.applicationUser : ''}","${applicationUserAuthority.authority ? applicationUserAuthority.authority : ''}"]
          applicationUserAuthoritys << obj
          counter++
      };
      return applicationUserAuthoritys
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
    private def searchApplicationUserAuthoritysFromGrid(params, resultPerPage, start, sortCol, sortOrder) {

      // check to see if it's a search by field (single)
      def query = params.query
      def qtype = params.qtype

      if (query) {
        def applicationUserAuthorityList = ApplicationUserAuthority.withCriteria {
          ilike(qtype, '%' + query + '%')
          maxResults(resultPerPage)
          firstResult(start)
          order(sortCol, sortOrder)
        } as List

        def total = (ApplicationUserAuthority.withCriteria {
          ilike(qtype, '%' + query + '%')
        } as List).size()

        return [applicationUserAuthorityList: applicationUserAuthorityList, count: total]
      } else {
        return null
      }
    }

    /**
     * Returns a list of ApplicationUserAuthoritys from the back-end table, which supports
     * pagination from within Flexigrid
     *
     * @param params Request parameters
     * @param resultPerPage how many result to display per page
     * @param start start offset
     * @param sortCol column to sort
     * @param sortOrder sort order (e.g., ASC or DESC)
     * @return List of ApplicationUserAuthoritys
     */
    private def getApplicationUserAuthorityList(params, resultPerPage, start, sortCol, sortOrder) {
        def applicationUserAuthorityList = ApplicationUserAuthority.withCriteria {
          maxResults(resultPerPage)
          firstResult(start)
          order(sortCol, sortOrder)
        } as List
        def total = ApplicationUserAuthority.count();
        return [applicationUserAuthorityList: applicationUserAuthorityList, count: total]
    }

     /**
      * Saves a ApplicationUserAuthority via Ajax call
      * If the ApplicationUserAuthority is brand-new entity, it inserts, updates otherwise.
      * Before committing, it tries to validate the ApplicationUserAuthority object, if
      * validation fails, it returns the error(s) back to the caller in the form
      * of JSON error obejct array.
      * If validation passes, it saves the entity and return the entity in the form
      * of JSON
      */
     def saveApplicationUserAuthorityRemotely = {

    	def result = [isError:false,entity:null];

    	def applicationUserAuthorityId = params.id;

    	def isNew = true;
    	if (applicationUserAuthorityId) {
    		isNew = false;
    	}

        def applicationUserAuthorityInstance = null;
        if (!isNew) {
          def integrityResult = this.getApplicationUserAuthorityWithIntegrityCheckForEdit(params)
          if (integrityResult.isError) {
            render integrityResult as JSON
            return
          }
          applicationUserAuthorityInstance = integrityResult.entity
        } else {
          applicationUserAuthorityInstance = new ApplicationUserAuthority(params)
        }


        applicationUserAuthorityInstance.validate();
        if (applicationUserAuthorityInstance.hasErrors()) {
            def errors = []
        	applicationUserAuthorityInstance.errors.allErrors.each {
        		errors << [it.field, g.message(error:it)]
        	}
        	result = [isError:true,entity:null,errors:errors];
        } else if (applicationUserAuthorityInstance.save(flush: true)) {
        	GridEntity obj = new GridEntity();
    		obj.id = applicationUserAuthorityInstance.id;
    		obj.cell = ["${applicationUserAuthorityInstance.id}","${applicationUserAuthorityInstance.applicationUser}","${applicationUserAuthorityInstance.authority}"];
        	result = [isError:false,entity:obj,version:applicationUserAuthorityInstance.version];
        }        
       String output = result as JSON
       render output
     }


      /**
       * Check to see is there any integrity errors while getting an instance of ApplicationUserAuthority
       * at the time of editing
       *
       * @param params Request parameters
       * @return Exisitng ApplicationUserAuthority instance as an entity if there is no integrity errors with within a data structure
       */
      private def getApplicationUserAuthorityWithIntegrityCheckForEdit(params) {

        def result = null
        def errors = []
        def applicationUserAuthorityInstance = ApplicationUserAuthority.get(params.id)
        if (applicationUserAuthorityInstance) {
          if (params.version) {
            def version = params.version.toLong()
            if (applicationUserAuthorityInstance.version > version) {
              errors << ["Another user has updated this ApplicationUserAuthority while you were editing"]
              result = [isError: true, entity: null, errors: errors]
            } else {
              applicationUserAuthorityInstance.properties = params
              result = [isError: false, entity: applicationUserAuthorityInstance, errors: null]
            }
          }
        }
        else {
          errors << ["No ApplicationUserAuthority found with this id or might have been deleted by someone!"];
          result = [isError: true, entity: null, errors: errors];
        }
        return result

      }

     /**
      *
      * Retrieves a ApplicationUserAuthority via Ajax call against the id attribute (default primary key)
      * If it finds an object against the provided id, it returns the ApplicationUserAuthority in the form
      * of JSON, null otherwise.
      * CAUTION:
      * 	1. 	It returns the version property of the entity, so make sure that version is
      * 		not false in the domain class definition
      *
      */
     def getApplicationUserAuthorityJSON = {

    	def result = [entity:null];
    	def applicationUserAuthorityInstance = ApplicationUserAuthority.get(params.id)
        if (applicationUserAuthorityInstance) {
        	applicationUserAuthorityInstance.version = applicationUserAuthorityInstance.version.toLong();

            Map properties = [:]
            applicationUserAuthorityInstance.properties.each { key, val ->
              String fieldValue = val.toString()
              def matcher = fieldValue =~ /(.+)-(.+)-(.+) /
              //if(matcher.matches()){
                matcher.each{haystack, y, m, d -> fieldValue = d+"-"+m+"-"+y }
                properties.put(key, fieldValue)
              //}
            }
            applicationUserAuthorityInstance = properties

        	result = [entity:applicationUserAuthorityInstance, version:applicationUserAuthorityInstance.version];
        }
    	render result as JSON;
     }


     /**
      * Deletes a ApplicationUserAuthority entity from the back-end,which is
      * retrieved against the requested id (params.id). Upon
      * successful deletion, it returns TRUE, FALSE otherwise
      */
     def deleteApplicationUserAuthorityAjax = {

    	def ids = params.id.split('_');

    	def count = ids.length;
    	def delCount = 0;
    	ids.each {
    		try {
	    		def applicationUserAuthorityInstance = ApplicationUserAuthority.get(it as String)
	            if (applicationUserAuthorityInstance) {
	                try {
	                	applicationUserAuthorityInstance.delete(flush: true)
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

    	def result = [deleted:false];
    	if (delCount == count) {
    		result = [deleted:true];
    	}

    	render result as JSON;

     }

     private def formatData(applicationUserAuthorityList) {
       List applicationUserAuthoritys = []
       applicationUserAuthorityList.each{ applicationUserAuthority ->
          Map properties = [:]
          applicationUserAuthority.properties.each { key, val ->
            String fieldValue = val.toString()
            def matcher = fieldValue =~ /(.+)-(.+)-(.+) /
            //if(matcher.matches()){
              matcher.each{haystack, y, m, d -> fieldValue = d+"-"+m+"-"+y }
              properties.put(key, fieldValue)
            //}
          }

          applicationUserAuthoritys.add(properties)
       }

       return applicationUserAuthoritys
     }

	 /**
	  * -----------------------------------------
	  * for Flexigrid to operate via Ajax
	  * -----------------------------------------
	  */

}
