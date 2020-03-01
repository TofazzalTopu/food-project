package com.bits.bdfp.geolocation.territorysubarea

import com.bits.bdfp.geolocation.TerritorySubAreaService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("searchTerritorySubAreaAction")
class SearchTerritorySubAreaAction extends Action {

  @Autowired
TerritorySubAreaService territorySubAreaService

  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object fieldName, Object fieldValue) {
    try {
       String strFieldName =  fieldName.toString()
       String strFieldValue = fieldValue.toString()
       if(strFieldName.length() == 0){
          return null
       }
       return territorySubAreaService.search(strFieldName, strFieldValue)
     } catch (Exception ex) {
        log.error(ex.message)
        return null
    }
  }

   public Object postCondition(Object params, Object object) {
    //Not implement
    return null
  }

  // Convert first letter to uppercase
  // Input: source String
  // Output: sourch with first letter to upper case
  // Author Ali Naser Date: 2011-05-31 04:12 PM
  private String convertToSearchable(String source){
    if(source.length() == 0){
        return ''
    }
    StringBuilder searchable = new StringBuilder(source);
    searchable = searchable.replace(0, 1, searchable.substring(0, 1).toUpperCase());
    return searchable.toString()
  }


    protected Object executeSubArea(Object params, Object object2) {
        try {
            List list

            list = territorySubAreaService.searchTerritorySubAreaByTerritory(params.id)


            return list
        }catch(Exception ex){
            log.error(ex.message)
            return null
        }
    }

    protected Object executeCustomer(Object params, Object object2) {
        try {
            List list= territorySubAreaService.searchCustomerCategoryByTerritorySubArea(params.id)
            return list
        }catch(Exception ex){
            log.error(ex.message)
            return null
        }
    }
}