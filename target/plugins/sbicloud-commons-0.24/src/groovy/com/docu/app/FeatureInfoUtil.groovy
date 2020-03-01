package com.docu.app

import com.docu.accesscontrol.FeatureInfo

/**
 * Created by rafiqul.islam on 11/26/2014.
 */
@Singleton
class FeatureInfoUtil {
    List<FeatureInfo> list = null

    public void resolve() {
        list = FeatureInfo.listOrderById()
    }

    public void destroy() {
        list = null
    }

    FeatureInfo get(long id) {
        return list.find { it.id == id }
    }
}
