package com.docu.security.urlwrapper


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.GridEntity
import com.docu.security.UrlWrapper
import com.docu.security.UrlWrapperService

@Component("listUrlWrapperAction")
class ListUrlWrapperAction implements IAction {
    public static final Log log = LogFactory.getLog(CreateUrlWrapperAction.class)

    @Autowired
    UrlWrapperService urlWrapperService

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

            Map result                              = urlWrapperService.list(params)
            List<UrlWrapper> urlWrapperList  = (List<UrlWrapper>) result.get('urlWrapperList')
            long urlWrapperCount               = (long) result.get('urlWrapperCount')
            List rows                               = this.wrapListInGridEntityList(urlWrapperList, Integer.parseInt(start))

            int pageCount = 1
            if (urlWrapperCount > Long.parseLong(resultPerPage.toString())) {
                pageCount = Math.ceil(urlWrapperCount / Long.parseLong(resultPerPage.toString()))
            }

            output = [page: pageNum, total: pageCount, records: urlWrapperCount, rows: rows]
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return output
    }

    public Object postCondition(def object) {
        return null
    }

    private List wrapListInGridEntityList(List<UrlWrapper> urlWrapperList, int start) {
        List instants = []
        int counter = start + 1;
        urlWrapperList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${counter}",
                        "${instance.id ? instance.id : ''}",
                            
                        "${instance.url? instance.url : ''}",
                        "${instance.urlWrapperName? instance.urlWrapperName : ''}"
                        ]
                        instants << obj
                        counter++
        }

        return instants
    }
}
