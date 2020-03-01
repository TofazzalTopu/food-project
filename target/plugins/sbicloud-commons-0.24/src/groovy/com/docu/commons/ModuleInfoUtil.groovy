package com.docu.commons

import com.docu.accesscontrol.ModuleInfo

/**
 * Created by rafiqul.islam on 2/17/2015.
 */
@Singleton
class ModuleInfoUtil {
    private List<ModuleInfo> list = []
    private accountsIntegrated = false

    public void resolve() {
        list = ModuleInfo.list(sort: "id")
        ModuleInfo moduleInfo = list.find {it.id == CommonConstants.MODULE_INFO_ACCOUNTS}
        if (moduleInfo != null) {
            accountsIntegrated = true
        }
        else {
            accountsIntegrated = false
        }
    }

    public void destroy() {
        list = null
    }

    public List<ModuleInfo> list() {
        return list
    }

    public ModuleInfo get(long id) {
        return list.find {it.id == id}
    }

    public boolean isAccountsIntegrated() {
        return accountsIntegrated
    }
}
