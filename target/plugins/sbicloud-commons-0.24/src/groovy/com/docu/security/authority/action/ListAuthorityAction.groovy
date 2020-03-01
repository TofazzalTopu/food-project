package com.docu.security.authority.action

import com.docu.security.UserAuthority
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.GridEntity
import com.docu.security.UserAuthorityService

@Component("listAuthorityAction")
class ListAuthorityAction implements IAction {
    public static final Log log = LogFactory.getLog(CreateAuthorityAction.class)

    @Autowired
    UserAuthorityService userAuthorityService

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

            Map result = userAuthorityService.listWithDashboardMapping(params)
            List<UserAuthority> authorityList = (List<UserAuthority>) result.get('authorityList')
            long authorityCount = (long) result.get('authorityCount')
//            List rows = this.wrapListInGridEntityList(authorityList, Integer.parseInt(start))

            int pageCount = 1
            if (authorityCount > Long.parseLong(resultPerPage.toString())) {
                pageCount = Math.ceil(authorityCount / Long.parseLong(resultPerPage.toString()))
            }

            output = [page: pageNum, total: pageCount, records: authorityCount, rows: authorityList]
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return output
    }

    public Object postCondition(def object) {
        return null
    }

    private List wrapListInGridEntityList(List<UserAuthority> authorityList, int start) {
        List instants = []
        int counter = start + 1;
        authorityList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${counter}",
                    "${instance.id ? instance.id : ''}",

                    "${instance.authority ? instance.authority : ''}",
                    "${instance.role ? instance.role : ''}"
            ]
            instants << obj
            counter++
        }

        return instants
    }
}
