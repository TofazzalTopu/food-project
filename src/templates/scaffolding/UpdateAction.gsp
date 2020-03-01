package ${domainClass.packageName}.${className.toString().toLowerCase()}
<% import com.docu.security.ApplicationUser %>
<% org.codehaus.groovy.grails.commons.DefaultGrailsDomainClassProperty propertyUpdatedBy = null %>
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import com.docu.commons.CommonConstants
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import ${domainClass.packageName}.${className}
import ${domainClass.packageName}.${className}Service
<% propertyUpdatedBy = domainClass.properties.find{it.name == "userUpdated"} %>
<% if(propertyUpdatedBy != null && propertyUpdatedBy.name == "userUpdated" && propertyUpdatedBy.type == ApplicationUser.class ){ %>
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
<% } %>

@Component("update${className}Action")
class Update${className}Action extends Action {
    public static final Log log = LogFactory.getLog(Update${className}Action.class)
    private final String MESSAGE_HEADER = 'Update ${domainClass.naturalName}'
    private final String MESSAGE_SUCCESS = '${domainClass.naturalName} Updated Successfully'

    @Autowired
    ${className}Service ${propertyName}Service
    <% if(propertyUpdatedBy != null && propertyUpdatedBy.name == "userUpdated" && propertyUpdatedBy.type == ApplicationUser.class ){ %>
    @Autowired
    SpringSecurityService springSecurityService
    <% } %>

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            ${className} ${propertyName} = ${propertyName}Service.read(Long.parseLong(params.id))
            ${propertyName}.properties = params
            <% propertyVersion = domainClass.properties.find{it.name == "version"} %>
            <% if(propertyVersion != null){ %>
            if(Long.parseLong(params.version) > ${propertyName}.version) {
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, CommonConstants.ALREADY_UPDATED)
            }
            <% } %>
            <% if(propertyUpdatedBy != null && propertyUpdatedBy.name == "userUpdated" && propertyUpdatedBy.type == ApplicationUser.class ){ %>
            ${propertyName}.userUpdated = (ApplicationUser) springSecurityService?.getCurrentUser()
            <% } %>
            if (!${propertyName}.validate()) {
                this.getValidationErrorMessage(${propertyName})
            }
            ${propertyName}Service.update(${propertyName})
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
