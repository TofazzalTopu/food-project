package com.docu.commons.relationship


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.GridEntity
import com.docu.commons.Relationship
import com.docu.commons.RelationshipService

@Component("listRelationshipAction")
class ListRelationshipAction implements IAction {
    public static final Log log = LogFactory.getLog(CreateRelationshipAction.class)

    @Autowired
    RelationshipService relationshipService

    public Object preCondition(def params) {
        return null
    }

    public Object execute(def params, def object) {
        Map output = [:]
        try {
            String pageNum = params.page ? params.page : "1"
            String resultPerPage = params.rows ? params.rows : "10"
            String start = ((Integer.parseInt(pageNum) - 1) * Integer.parseInt(resultPerPage)).toString()
            String sortCol = params.sidx ? params.sidx : "id"
            String sortOrder = params.sord ? params.sord : "desc"

            params.put("pageNum", pageNum)
            params.put("resultPerPage", resultPerPage)
            params.put("start", start)
            params.put("sortCol", sortCol)
            params.put("sortOrder", sortOrder)

            Map result = relationshipService.list(params)
            List<Relationship> relationshipList = (List<Relationship>) result.get('relationshipList')
            long relationshipCount = (long) result.get('relationshipCount')
            List rows = this.wrapListInGridEntityList(relationshipList, Integer.parseInt(start))

            int pageCount = 1
            if (relationshipCount > Long.parseLong(resultPerPage.toString())) {
                pageCount = Math.ceil(relationshipCount / Long.parseLong(resultPerPage.toString()))
            }

            output = [page: pageNum, total: pageCount, records: relationshipCount, rows: rows]
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return output
    }

    public Object postCondition(def object) {
        return null
    }

    private List wrapListInGridEntityList(List<Relationship> relationshipList, int start) {
        List instants = []
        int counter = start + 1;
        relationshipList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${counter}",
                    "${instance.id ? instance.id : ''}",

                    "${instance.name ? instance.name : ''}",
                    "${instance.description ? instance.description : ''}"
            ]
            instants << obj
            counter++
        }

        return instants
    }
}
