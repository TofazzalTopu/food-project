package com.bits.bdfp.inventory.demandorder.primarydemandorder

import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by prianka.adhikary on 9/22/2015.
 */
@Component("rptPrintInvoiceAction")
class RptPrintInvoiceAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            init(params)
            return  primaryDemandOrderService.listPrintInvoiceForReport(null,params)
        }
        catch (Exception ex) {
            log.error(ex.message);
            return null;
        }
    }

    public Object postCondition(Object object, Object user)
    {
        try
        {
            Map params=object;

            String headerTitle = "Sales Invoice"

            params.put("headerTitle",headerTitle)

            String reportFormat = params.format
            params.put("invoiceNo", object.invoiceNo)
            params.put("SUBREPORT_DIR", object.SUB_REPORT_DIR)
            params.put("_format", reportFormat)
            params.put("_name", "Print Invoice")
            params.put("_file", "invoice/print_invoice.jasper")

            Map map = [params:params]
            return map;
        }
        catch (Exception ex)
        {
            log.error(ex.message)
            return null
        }
    }

}
