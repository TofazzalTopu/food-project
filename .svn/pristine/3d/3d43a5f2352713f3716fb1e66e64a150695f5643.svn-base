package com.docu.security.util

import com.docu.security.UserAuthority

/**
 * Created by rafiqul.islam on 11/26/2014.
 */
@Singleton
class AuthorityUtil {
    List<UserAuthority> authorityList = null

    public void resolve() {
        authorityList = UserAuthority.list()
    }

    public void destroy() {
        authorityList = null
    }

    public UserAuthority get(long id) {
        return authorityList.find { it.id == id }
    }

    public UserAuthority getByRole(String role) {
        return authorityList.find { it.role == role }
    }
}
