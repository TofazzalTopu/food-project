package ${domainClass.packageName}.${className.toString().toLowerCase()}
<% import com.docu.security.ApplicationUser %>
<% org.codehaus.groovy.grails.commons.DefaultGrailsDomainClassProperty propertyCreatedBy = null %>
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import ${domainClass.packageName}.${className}
import ${domainClass.packageName}.${className}Service
<% propertyCreatedBy = domainClass.properties.find{it.name == "userCreated"} %>
<% if(propertyCreatedBy != null && propertyCreatedBy.name == "userCreated" && propertyCreatedBy.type == ApplicationUser.class ){ %>
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
<% } %>

@Component("create${className}Action")
class Create${className}Action extends Action{
    public static final Log log = LogFactory.getLog(Create${className}Action.class)
    private final String MESSAGE_HEADER = 'New ${domainClass.naturalName}'
    private final String MESSAGE_SUCCESS = '${domainClass.naturalName} Created Successfully'

    @Autowired
    ${className}Service ${propertyName}Service
    <% if(propertyCreatedBy != null && propertyCreatedBy.name == "userCreated" && propertyCreatedBy.type == ApplicationUser.class ){ %>
    @Autowired
    SpringSecurityService springSecurityService
    <% } %>

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            ${className} ${propertyName} = new ${className}()
            ${propertyName}.properties = params
            <% if(propertyCreatedBy != null && propertyCreatedBy.name == "userCreated" && propertyCreatedBy.type == ApplicationUser.class ){ %>
            ${propertyName}.userCreated = (ApplicationUser) springSecurityService?.getCurrentUser()
            <% } %>
            if (!${propertyName}.validate()) {
                this.getValidationErrorMessage(${propertyName})
            }
            ${propertyName}Service.create(${propertyName})
            return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}