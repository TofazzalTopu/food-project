package ${domainClass.packageName}.${className.toString().toLowerCase()}

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import com.docu.common.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import ${domainClass.packageName}.${className}Service

@Component("search${className}Action")
class Search${className}Action extends Action {
    public static final Log log = LogFactory.getLog(Search${className}Action.class)

    @Autowired
    ${className}Service ${propertyName}Service

    public Object preCondition(Object params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            String strFieldName =  params.fieldName.toString()
            String strFieldValue = params.fieldValue.toString()
            if(strFieldName.length() == 0){
                return null
            }
            return ${propertyName}Service.search(strFieldName, strFieldValue)
        }catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    public Object postCondition(Object params, def object) {
        return null
    }
}