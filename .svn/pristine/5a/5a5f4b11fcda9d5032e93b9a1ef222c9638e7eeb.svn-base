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
@Component("rptPrintLoadingSlipAction")
class RptPrintLoadingSlipAction extends Action{

    private static final Log log = LogFactory.getLog(this)
//    @Autowired

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {

    }

    public Object postCondition(Object object, Object user)
    {
        try
        {
            Map params=object;

            String headerTitle = "Loading Slip"

            params.put("headerTitle",headerTitle)

            List list = new ArrayList();

            String reportFormat = params.format
            params.put("inputLoadingSlipNumber", object.slipNo)
            params.put("SUBREPORT_DIR", object.SUB_REPORT_DIR)
            params.put("_format", reportFormat)
            params.put("_name", "Print Loading Slip")
            params.put("_file", "loadingSlip/print_loading_slip.jasper")

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
