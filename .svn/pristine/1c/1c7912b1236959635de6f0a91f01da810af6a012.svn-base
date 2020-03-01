package ${domainClass.packageName}
<% org.codehaus.groovy.grails.commons.DefaultGrailsDomainClassProperty property = null %>
import org.springframework.transaction.annotation.Transactional
import com.docu.commons.CommonConstants
import com.docu.common.Service
import groovy.sql.Sql
import javax.sql.DataSource
import com.docu.common.Action

class ${className}Service extends Service {
    static transactional = false

    @Transactional(readOnly = true)
    public ${className} read(Long id) {
        return ${className}.read(id)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Map params) {
        try {
            List<${className}> ${propertyName}List = ${className}.withCriteria {
                maxResults(Integer.parseInt(action.resultPerPage.toString()))
                firstResult(Integer.parseInt(action.start.toString()))
                order(action.sortCol.toString(), action.sortOrder.toString())
            }
            long ${propertyName}Count = ${className}.count()
            return [objList: ${propertyName}List, count: ${propertyName}Count]
        }
        catch(Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List<${className}> list() {
        return ${className}.list()
    }

    @Transactional
    public ${className} create(Object object) {
        try {
            ${className} ${propertyName} = (${className}) object
            return ${propertyName}.save(vaidate: false, insert: true)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object object) {
        try {
            ${className} ${propertyName} = (${className}) object
            if(${propertyName}.save(vaidate: false)){
                return new Integer(1)
            } else {
                return new Integer(0)
            }
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        try {
            ${className} ${propertyName} = (${className}) object
            ${propertyName}.delete()
            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public ${className} search(String fieldName, String fieldValue) {
        String query = "from ${className} as docu where docu."+ fieldName +" = '" + fieldValue +"'"
        return ${className}.find(query)
    }
}
