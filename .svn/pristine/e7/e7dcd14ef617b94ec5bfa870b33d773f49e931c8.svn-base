package com.docu.commons.bloodgroup


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.GridEntity
import com.docu.commons.BloodGroup
import com.docu.commons.BloodGroupService

@Component("listBloodGroupAction")
class ListBloodGroupAction implements IAction {
    public static final Log log = LogFactory.getLog(CreateBloodGroupAction.class)

    @Autowired
    BloodGroupService bloodGroupService

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

            Map result                              = bloodGroupService.list(params)
            List<BloodGroup> bloodGroupList  = (List<BloodGroup>) result.get('bloodGroupList')
            long bloodGroupCount               = (long) result.get('bloodGroupCount')
            List rows                               = this.wrapListInGridEntityList(bloodGroupList, Integer.parseInt(start))

            int pageCount = 1
            if (bloodGroupCount > Long.parseLong(resultPerPage.toString())) {
                pageCount = Math.ceil(bloodGroupCount / Long.parseLong(resultPerPage.toString()))
            }

            output = [page: pageNum, total: pageCount, records: bloodGroupCount, rows: rows]
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return output
    }

    public Object postCondition(def object) {
        return null
    }

    private List wrapListInGridEntityList(List<BloodGroup> bloodGroupList, int start) {
        List instants = []
        int counter = start + 1;
        bloodGroupList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${counter}",
                        "${instance.id ? instance.id : ''}",
                            
                        "${instance.groupName? instance.groupName : ''}",
                        "${instance.description? instance.description : ''}"
                        ]
                        instants << obj
                        counter++
        }

        return instants
    }
}
