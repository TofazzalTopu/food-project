package com.docu.ws.util

import com.docu.commons.CommonConstants
import com.docu.plugin.cxfws.CommonHelperWebService
import com.docu.webservice.WebRouteTable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created with IntelliJ IDEA.
 * User: bipul
 * Date: 3/12/14
 * Time: 1:10 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("webRouteUtil")
class WebRouteUtil {
    @Autowired
    CommonHelperWebService commonHelperWebService

    public List<WebRouteTable> webRouteTableList = []

    public void resolve() {
        webRouteTableList = commonHelperWebService.getWebRouteList()
        CommonConstants.ROUTER_COUNT = webRouteTableList.size()
    }
    public  void destroy(){
        webRouteTableList = null
    }

}
