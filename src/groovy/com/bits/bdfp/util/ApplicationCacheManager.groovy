package com.bits.bdfp.util

import com.docu.app.*
import com.docu.commons.ModuleInfoUtil
import com.docu.commons.util.GenderUtil
import com.docu.commons.util.MaritalStatusUtil
import com.docu.security.util.AuthorityDashboardMappingUtil
import com.docu.security.util.AuthorityUtil
import com.docu.security.util.SecurityQuestionUtil
import com.docu.ws.util.WebRouteUtil
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by IntelliJ IDEA.
 * User: Saiful
 * Date: 1/9/12
 * Time: 10:47 AM
 * To change this template use File | Settings | File Templates.
 */
@Component("applicationCacheManager")
class ApplicationCacheManager {
    public static final Log log = LogFactory.getLog(ApplicationCacheManager.class)

    @Autowired
    WebRouteUtil webRouteUtil

    public void resolveAll() {
        SecurityQuestionUtil.instance.resolve()
        MigrationDateUtil.instance.resolve()
        ModuleInfoUtil.instance.resolve()
        MenuGroupUtil.instance.resolve()
        FeatureInfoUtil.instance.resolve()
        FeatureActionUtil.instance.resolve()
        AuthorityUtil.instance.resolve()
        AuthorityDashboardMappingUtil.instance.resolve()

        ApplicationConfigUtil.instance.resolve()
//        MaritalStatusUtil.instance.resolve()
        webRouteUtil.resolve()
        GenderUtil.instance.resolve()
        updateOfficeWithBusinessDate()
    }

    public void resolveSelectedCache(Map params){
        try {
            if (params.ApplicationConfigUtil) {
                ApplicationConfigUtil.instance.destroy()
                ApplicationConfigUtil.instance.resolve()
            }

            if (params.MaritalStatusUtil) {
                MaritalStatusUtil.instance.destroy()
                MaritalStatusUtil.instance.resolve()
            }

            if (params.ModuleInfoUtil) {
                ModuleInfoUtil.instance.destroy()
                ModuleInfoUtil.instance.resolve()
            }

            if (params.webRouteUtil){
                webRouteUtil.destroy()
                webRouteUtil.resolve()
            }
        } catch (Exception ex) {
            log.error(ex.message)
        }
    }

    public void destroyAll() {
        GenderUtil.instance.destroy()
        ApplicationConfigUtil.instance.destroy()
        webRouteUtil.destroy()

        MaritalStatusUtil.instance.destroy()
        FeatureActionUtil.instance.destroy()
        FeatureInfoUtil.instance.destroy()
        MenuGroupUtil.instance.destroy()
        ModuleInfoUtil.instance.destroy()
        AuthorityDashboardMappingUtil.instance.destroy()
        AuthorityUtil.instance.destroy()
        MigrationDateUtil.instance.destroy()
        SecurityQuestionUtil.instance.destroy()
    }

    void updateOfficeWithBusinessDate() {
    }
}
