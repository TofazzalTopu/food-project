package com.docu.app

import com.docu.accesscontrol.MenuGroup

/**
 * Created by rafiqul.islam on 11/26/2014.
 */
@Singleton
class MenuGroupUtil {
    List<MenuGroup> menuGroupList = []

    public void resolve() {
        menuGroupList = MenuGroup.listOrderByModuleInfoId()
    }

    public void destroy() {
        menuGroupList = null
    }

    MenuGroup get(long id) {
        return menuGroupList.find { it.id == id }
    }
}
