package com.docu.security.util

import com.docu.app.AuthorityDashboardMapping

/**
 * Created by rafiqul.islam on 11/26/2014.
 */
@Singleton
class AuthorityDashboardMappingUtil {
    List<AuthorityDashboardMapping> list = null

    public void resolve() {
        list = AuthorityDashboardMapping.listOrderById()
    }

    public void destroy() {
        list = null
    }

    AuthorityDashboardMapping get(long id) {
        return list.find { it.id == id }
    }

    AuthorityDashboardMapping getByAuthorityId(String authorityId) {
        return list.find { it.authorityId == authorityId }
    }
}
