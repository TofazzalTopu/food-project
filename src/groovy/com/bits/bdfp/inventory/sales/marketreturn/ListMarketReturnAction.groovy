package com.bits.bdfp.inventory.sales.marketreturn

import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.common.GridEntity
import com.bits.bdfp.inventory.sales.MarketReturn
import com.bits.bdfp.inventory.sales.MarketReturnService

@Component("listMarketReturnAction")
class ListMarketReturnAction extends Action {
    public static final Log log = LogFactory.getLog(CreateMarketReturnAction.class)

    @Autowired
    MarketReturnService marketReturnService

    @Override
    public Object preCondition(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            String orderNO=''
            if (params?.searchKey) {
                orderNO = params?.searchKey
            }
            return marketReturnService.listMrNo(orderNO,applicationUser)

        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    @Override
    public Object postCondition(Object params, Object object) {
        return getResultForUI(object)
    }

    public Object execute(Object params, Object object) {
        Map output = [:]
        try {
            init(params)
            params.put('appUser', object.id)
            List list = marketReturnService.listMarketReturn(params)
            List objList = this.wrapListInGridEntityList(list, start)
            return objList
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return output
    }

    private List wrapListInGridEntityList(List<MarketReturn> marketReturnList, int start) {
        List instants = []
        int counter = start + 1;
        marketReturnList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${counter}",
                        "${instance.id ? instance.id : ''}",
                        "${instance.mr_no? instance.mr_no : ''}",
                        "${instance.customer? instance.customer : ''}",
                        "${instance.destination? instance.destination : ''}",
                        "${instance.source? instance.source : ''}",
                        "${instance.warehouse? instance.warehouse : ''}",
                        "${instance.mr_status? instance.mr_status : ''}"
                        ]
                        instants << obj
                        counter++
        }

        return instants
    }

    Map getResultForUI(List objList) {
        int pageCount = 1
        if (resultPerPage < 0) {
            pageCount = 1
            resultPerPage=total
        }
        else {
            if (total > resultPerPage) {
                pageCount = Math.ceil(total / resultPerPage)
            }
        }
        Map output = [page: pageNum, total: pageCount, records: total, rows: objList]
        return output;
    }
}
