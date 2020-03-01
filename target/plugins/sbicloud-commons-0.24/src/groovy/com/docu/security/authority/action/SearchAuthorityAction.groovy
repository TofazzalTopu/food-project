package com.docu.security.authority.action

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

import com.docu.security.UserAuthorityService

@Component("searchAuthorityAction")
class SearchAuthorityAction implements IAction {
    public static final Log log = LogFactory.getLog(SearchAuthorityAction.class)

    @Autowired
    UserAuthorityService userAuthorityService

    public Object preCondition(Object params) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            String strFieldName = params.fieldName.toString()
            String strFieldValue = params.fieldValue.toString()
            if (strFieldName.length() == 0) {
                return null
            }
            return userAuthorityService.search(strFieldName, strFieldValue)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params) {
        return null
    }

    // Convert first letter to uppercase
    // Input: source String
    // Output: sourch with first letter to upper case
    // Author Ali Naser Date: 2011-05-31 04:12 PM
    private String convertToSearchable(String source) {
        if (source.length() == 0) {
            return ''
        }
        StringBuilder searchable = new StringBuilder(source);
        searchable = searchable.replace(0, 1, searchable.substring(0, 1).toUpperCase());
        return searchable.toString()
    }
}