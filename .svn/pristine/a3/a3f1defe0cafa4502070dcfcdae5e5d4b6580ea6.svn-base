package ${domainClass.packageName}.${className.toString().toLowerCase()}
<% org.codehaus.groovy.grails.commons.DefaultGrailsDomainClassProperty propertyUpdatedBy = null %>
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import ${domainClass.packageName}.${className}
import ${domainClass.packageName}.${className}Service

@Component("delete${className}Action")
class Delete${className}Action extends Action {
    public static final Log log = LogFactory.getLog(Delete${className}Action.class)
    private final String MESSAGE_HEADER = 'Delete ${domainClass.naturalName}'
    private final String MESSAGE_SUCCESS = '${domainClass.naturalName} deleted successfully'
    private final String MESSAGE_FAILURE = '${domainClass.naturalName} delete failed'

    @Autowired
    ${className}Service ${propertyName}Service

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            ${className} ${propertyName} = ${propertyName}Service.read(Long.parseLong(params.id))
            ${propertyName}Service.delete(${propertyName})
            return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, MESSAGE_FAILURE)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}