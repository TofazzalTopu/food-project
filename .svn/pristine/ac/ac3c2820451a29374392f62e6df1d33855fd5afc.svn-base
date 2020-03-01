package com.bits.bdfp.settings.businessday
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("listLocalHolidayAction")
class ListLocalHolidayAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    LocalHolidayService localHolidayService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            init(params)
            Map result = [:]
            List objectList = null

            result = localHolidayService.getListForGrid(this,params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List objList = this.wrapListInGridEntityList(objectList, start)
            result = this.postCondition(objList, null)
            return result
        }
        catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }


    private List wrapListInGridEntityList(objList, start) {
        List instants = []
        int counter = start + 1;
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${counter}",
                        "${instance.id ? instance.id : ''}",

                        "${instance.name ? instance.name : ''}",
                        "${instance.territory ? instance.territory : ''}",
                        "${instance.holiday_date ? instance.holiday_date : ''}",
                        "${instance.note ? instance.note : ''}"

            ]
            instants << obj
            counter++
        };
        return instants
    }

    public Object postCondition(Object objList, Object object) {
        int pageCount = 1
        if (resultPerPage < 0) {
            pageCount = 1
            resultPerPage = total
        } else {
            if (total > resultPerPage) {
                pageCount = Math.ceil(total / resultPerPage)
            }
        }
        Map output = [page: pageNum, total: pageCount, records: total, rows: objList]
        return output;
    }
}
