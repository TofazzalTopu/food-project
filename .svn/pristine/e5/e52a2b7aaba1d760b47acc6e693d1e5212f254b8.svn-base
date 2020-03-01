package ${packageName}

import ${packageName.replaceAll('.entity','.action')}.${className.toLowerCase()}.Create${className}Action
import ${packageName.replaceAll('.entity','.action')}.${className.toLowerCase()}.Delete${className}Action
import ${packageName.replaceAll('.entity','.action')}.${className.toLowerCase()}.List${className}Action
import ${packageName.replaceAll('.entity','.action')}.${className.toLowerCase()}.Update${className}Action
import ${packageName.replaceAll('.entity','.action')}.${className.toLowerCase()}.Read${className}Action
import ${packageName.replaceAll('.entity','.action')}.${className.toLowerCase()}.Search${className}Action

import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class ${className}Controller {

    @Autowired
    private Create${className}Action create${className}Action
    @Autowired
    private Update${className}Action update${className}Action
    @Autowired
    private List${className}Action list${className}Action
    @Autowired
    private Delete${className}Action delete${className}Action
    @Autowired
    private Read${className}Action read${className}Action
    @Autowired
    private Search${className}Action search${className}Action

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
        Object objList = list${className}Action.execute(params, null)
        render list${className}Action.postCondition(objList, null) as JSON
    }

    def show = {
       render(view:"show")
    }

    def create = {
      Message message = create${className}Action.execute(params, null)
      render message as JSON
    }

    def edit = {
      Map data = (Map) read${className}Action.execute(params, null)
      render data as JSON
    }

    def update = {
      Message message = update${className}Action.execute(params, null)
      render message as JSON
    }

    def delete = {
        Message message = delete${className}Action.execute(params, null)
        render message as JSON
    }

    def search = {
        ${className} ${propertyName} = (${className}) search${className}Action.execute(params, null)
        if(${propertyName}){
            render ${propertyName} as JSON
        }
        else{
            render ''
        }
    }
}
