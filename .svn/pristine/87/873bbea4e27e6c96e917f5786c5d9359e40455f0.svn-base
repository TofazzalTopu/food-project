package com.docu.app

import com.docu.accesscontrol.FeatureAction

/**
 * Created by rafiqul.islam on 11/26/2014.
 */
@Singleton
class FeatureActionUtil {
    List<FeatureAction> list = null

    public void resolve() {
        list = FeatureAction.listOrderById()
    }

    public void destroy() {
        list = null
    }

    FeatureAction get(long id) {
        return list.find { it.id == id }
    }
}
