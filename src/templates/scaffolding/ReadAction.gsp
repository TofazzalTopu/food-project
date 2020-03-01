package ${domainClass.packageName}.${className.toString().toLowerCase()}

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import ${domainClass.packageName}.${className}
import ${domainClass.packageName}.${className}Service

@Component("read${className}Action")
class Read${className}Action extends Action {
    public static final Log log = LogFactory.getLog(Read${className}Action.class)

    @Autowired
    ${className}Service ${propertyName}Service

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        ${className} ${propertyName} = ${propertyName}Service.read(Long.parseLong(params.id))
        Map objectData = ObjectUtil.getPersistenceData(${propertyName})
        return objectData
    }

    public Object postCondition(def params, def object) {
        return null
    }
}