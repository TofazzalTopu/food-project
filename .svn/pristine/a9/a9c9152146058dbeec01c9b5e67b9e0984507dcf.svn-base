package ${domainClass.packageName}.${className.toString().toLowerCase()}
<% import grails.persistence.Event %>
<% import org.codehaus.groovy.grails.orm.hibernate.support.ClosureEventTriggeringInterceptor as Events %>
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.common.Message
import ${domainClass.packageName}.${className}
import ${domainClass.packageName}.${className}Service

@Component("list${className}Action")
class List${className}Action extends Action {

    @Autowired
    ${className}Service ${propertyName}Service

    public Object preCondition(def params, def object) {
        //not need to implement
        return null
    }

    public Object execute(def params, def object) {
        try{
            init(params)
            Map result = [:]
            List objectList = null

            result = ${propertyName}Service.getListForGrid(this, params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            return this.wrapListInGridEntityList(objectList, start)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

   Map getResultForUI(List objList) {
        int pageCount = 1
        if (total > resultPerPage) {
            pageCount = Math.ceil(total / resultPerPage)
        }
        Map output = [page: pageNum, total: pageCount, records: total, rows: objList]
        return output;
   }

   private Object wrapListInGridEntityList(objList, start) {
        try{
            List instants = []
            int counter = start + 1;
            objList.each { instance ->
                GridEntity obj = new GridEntity()
                obj.id = instance.id
                obj.cell = ["\${counter}",
                          "\${instance.id ? instance.id : ''}",
                            <%  excludedProps = Event.allEvents.toList() << 'version' << 'id' << 'dateCreated' << 'lastUpdated' << 'userCreated' << 'userUpdated'
                            props = domainClass.properties.findAll { !excludedProps.contains(it.name) }
                            Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
                            props.eachWithIndex  { p, index ->
                                if (!Collection.class.isAssignableFrom(p.type)) {
                                    cp = domainClass.constrainedProperties[p.name]
                                    display = (cp ? cp.display : true)
                                    if (display) { %>
                            "\${instance.${p.name}? instance.${p.name} : ''}"<% if(props.size() -1 > index){ %>,<%} }   }   } %>
                            ]
                instants << obj
                counter++
            }
            return instants
        } catch (Exception ex){
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
   }

   public Object postCondition(def params, def object) {
        return getResultForUI(params)
   }
}