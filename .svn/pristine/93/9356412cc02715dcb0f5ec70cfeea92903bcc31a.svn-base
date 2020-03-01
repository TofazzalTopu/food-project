package com.docu.commons.gender


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.GridEntity
import com.docu.commons.Gender
import com.docu.commons.GenderService

@Component("listGenderAction")
class ListGenderAction implements IAction {
    public static final Log log = LogFactory.getLog(CreateGenderAction.class)

    @Autowired
    GenderService genderService

    public Object preCondition(def params) {
        return null
    }

    public Object execute(def params, def object) {
        Map output = [:]
        try {
            String pageNum = Integer.parseInt(params.page) ? Integer.parseInt(params.page).toString() : "1"
            String resultPerPage = params.rows && Integer.parseInt(params.rows) && (1000 >= Integer.parseInt(params.rows)) && (Integer.parseInt(params.rows) > 0) ? Integer.parseInt(params.rows).toString() : "10"
            String start = ((Integer.parseInt(pageNum) - 1) * Integer.parseInt(resultPerPage)).toString()
            String sortCol = params.sidx ? params.sidx : "id"                          //check for xscript (not included)
            String sortOrder = params.sord ? params.sord : "desc"                        //check for xscript (not included)

            params.put("pageNum", pageNum)
            params.put("resultPerPage", resultPerPage)
            params.put("start", start)
            params.put("sortCol", sortCol)
            params.put("sortOrder", sortOrder)

            Map result = genderService.list(params)
            List<Gender> genderList = (List<Gender>) result.get('genderList')
            long genderCount = (long) result.get('genderCount')
            List rows = this.wrapListInGridEntityList(genderList, Integer.parseInt(start))

            int pageCount = 1
            if (genderCount > Long.parseLong(resultPerPage.toString())) {
                pageCount = Math.ceil(genderCount / Long.parseLong(resultPerPage.toString()))
            }

            output = [page: pageNum, total: pageCount, records: genderCount, rows: rows]
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return output
    }

    public Object postCondition(def object) {
        return null
    }

    private List wrapListInGridEntityList(List<Gender> genderList, int start) {
        List instants = []
        int counter = start + 1;
        genderList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${counter}",
                    "${instance.id ? instance.id : ''}",

                    "${instance.genderType ? instance.genderType : ''}",
                    "${instance.description ? instance.description : ''}", "${'view detail'}"
            ]
            instants << obj
            counter++
        }

        return instants
    }
}
