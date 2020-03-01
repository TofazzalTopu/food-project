package com.docu.accesscontrol.menuGroup.action

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import com.docu.accesscontrol.MenuGroupService
import com.docu.accesscontrol.FeatureInfoService
import com.docu.accesscontrol.ModuleInfo
import com.docu.accesscontrol.FeatureInfo
import groovy.sql.GroovyRowResult

/**
 * Created by IntelliJ IDEA.
 * User: bipul
 * Date: 2/29/12
 * Time: 6:28 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("ajaxMenuGroupAction")
class AjaxMenuGroupAction {
    @Autowired
    MenuGroupService menuGroupService
    @Autowired
    FeatureInfoService featureInfoService

    Object getModuleInfo(Map params) {
        return menuGroupService.getModuleInfo(params)
    }

    Object getFeatureInfo(ModuleInfo moduleInfo){
      return featureInfoService.getFeatureInfoByModule(moduleInfo)
    }

    Object getSelectedFeatureInfo(Map params){
       return featureInfoService.getSelectedFeatureInfoList(params)
    }

    List<GroovyRowResult> getMenuItemListByMenuGroupId(Long menuGroupId){
      return menuGroupService.getMenuItemListByMenuGroupId(menuGroupId)
    }
}
