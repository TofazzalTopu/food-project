package com.bits.bdfp.common.codegenerationformat


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.GridEntity
import com.bits.bdfp.common.CodeGenerationFormat
import com.bits.bdfp.common.CodeGenerationFormatService

@Component("listCodeGenerationFormatAction")
class ListCodeGenerationFormatAction implements IAction {
    public static final Log log = LogFactory.getLog(CreateCodeGenerationFormatAction.class)

    @Autowired
    CodeGenerationFormatService codeGenerationFormatService

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

            Map result                              = codeGenerationFormatService.list(params)
            List<CodeGenerationFormat> codeGenerationFormatList  = (List<CodeGenerationFormat>) result.get('codeGenerationFormatList')
            long codeGenerationFormatCount               = (long) result.get('codeGenerationFormatCount')
            List rows                               = this.wrapListInGridEntityList(codeGenerationFormatList, Integer.parseInt(start))

            int pageCount = 1
            if (codeGenerationFormatCount > Long.parseLong(resultPerPage.toString())) {
                pageCount = Math.ceil(codeGenerationFormatCount / Long.parseLong(resultPerPage.toString()))
            }

            output = [page: pageNum, total: pageCount, records: codeGenerationFormatCount, rows: rows]
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return output
    }

    public Object postCondition(def object) {
        return null
    }

    private List wrapListInGridEntityList(List<CodeGenerationFormat> codeGenerationFormatList, int start) {
        List instants = []
        int counter = start + 1;
        codeGenerationFormatList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${counter}",
                        "${instance.id ? instance.id : ''}",
                            
                        "${instance.keyCode? instance.keyCode : ''}",
                        "${instance.format? instance.format : ''}",
                        "${instance.currentNo? instance.currentNo : ''}",
                        "${instance.note? instance.note : ''}"
                        ]
                        instants << obj
                        counter++
        }

        return instants
    }
}
