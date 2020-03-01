package com.bits.bdfp.common.documenttype


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.GridEntity
import com.bits.bdfp.common.DocumentType
import com.bits.bdfp.common.DocumentTypeService

@Component("listDocumentTypeAction")
class ListDocumentTypeAction implements IAction {
    public static final Log log = LogFactory.getLog(CreateDocumentTypeAction.class)

    @Autowired
    DocumentTypeService documentTypeService

    public Object preCondition(def params) {
        return null
    }

    public Object execute(def params, def object) {
        Map output = [:]
        try {
            String pageNum                          = params.page ? params.page : "1"
            String resultPerPage                    = params.rows ? params.rows : "10"
            String start                            = ((Integer.parseInt(pageNum) - 1) * Integer.parseInt(resultPerPage)).toString()
            String sortCol                          = params.sidx ? params.sidx : "id"
            String sortOrder                        = params.sord ? params.sord : "desc"

            params.put("pageNum",                   pageNum)
            params.put("resultPerPage",             resultPerPage)
            params.put("start",                     start)
            params.put("sortCol",                   sortCol)
            params.put("sortOrder",                 sortOrder)

            Map result                              = documentTypeService.list(params)
            List<DocumentType> documentTypeList  = (List<DocumentType>) result.get('documentTypeList')
            long documentTypeCount               = (long) result.get('documentTypeCount')
            List rows                               = this.wrapListInGridEntityList(documentTypeList, Integer.parseInt(start))

            int pageCount = 1
            if (documentTypeCount > Long.parseLong(resultPerPage.toString())) {
                pageCount = Math.ceil(documentTypeCount / Long.parseLong(resultPerPage.toString()))
            }

            output = [page: pageNum, total: pageCount, records: documentTypeCount, rows: rows]
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return output
    }

    public Object postCondition(def object) {
        return null
    }

    private List wrapListInGridEntityList(List<DocumentType> documentTypeList, int start) {
        List instants = []
        int counter = start + 1;
        documentTypeList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${counter}",
                        "${instance.id ? instance.id : ''}",
                            
                        "${instance.name? instance.name : ''}",
                        "${instance.note? instance.note : ''}"
                        ]
                        instants << obj
                        counter++
        }

        return instants
    }
}
