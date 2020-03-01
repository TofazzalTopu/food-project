package com.bits.bdfp.inventory.sales.receiveproductsfrommarket


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.GridEntity
import com.bits.bdfp.inventory.sales.ReceiveProductsFromMarket
import com.bits.bdfp.inventory.sales.ReceiveProductsFromMarketService

@Component("listReceiveProductsFromMarketAction")
class ListReceiveProductsFromMarketAction implements IAction {
    public static final Log log = LogFactory.getLog(CreateReceiveProductsFromMarketAction.class)

    @Autowired
    ReceiveProductsFromMarketService receiveProductsFromMarketService

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

            Map result                              = receiveProductsFromMarketService.list(params)
            List<ReceiveProductsFromMarket> receiveProductsFromMarketList  = (List<ReceiveProductsFromMarket>) result.get('receiveProductsFromMarketList')
            long receiveProductsFromMarketCount               = (long) result.get('receiveProductsFromMarketCount')
            List rows                               = this.wrapListInGridEntityList(receiveProductsFromMarketList, Integer.parseInt(start))

            int pageCount = 1
            if (receiveProductsFromMarketCount > Long.parseLong(resultPerPage.toString())) {
                pageCount = Math.ceil(receiveProductsFromMarketCount / Long.parseLong(resultPerPage.toString()))
            }

            output = [page: pageNum, total: pageCount, records: receiveProductsFromMarketCount, rows: rows]
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return output
    }

    public Object postCondition(def object) {
        return null
    }

    private List wrapListInGridEntityList(List<ReceiveProductsFromMarket> receiveProductsFromMarketList, int start) {
        List instants = []
        int counter = start + 1;
        receiveProductsFromMarketList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${counter}",
                        "${instance.id ? instance.id : ''}",
                            
                        "${instance.receivingDp? instance.receivingDp : ''}",
                        "${instance.receivingInventory? instance.receivingInventory : ''}",
                        "${instance.receivingSubInventory? instance.receivingSubInventory : ''}",
                        "${instance.customerMaster? instance.customerMaster : ''}",
                        "${instance.userCreated? instance.userCreated : ''}",
                        "${instance.userUpdated? instance.userUpdated : ''}",
                        "${instance.dateCreated? instance.dateCreated : ''}",
                        "${instance.dateUpdated? instance.dateUpdated : ''}"
                        ]
                        instants << obj
                        counter++
        }

        return instants
    }
}
