package com.bits.bdfp.common

import com.bits.bdfp.inventory.workflow.Workflow
import com.bits.bdfp.security.MyDashboardService
import com.docu.accesscontrol.FeatureAction
import com.docu.accesscontrol.FeatureInfo
import com.docu.accesscontrol.MenuGroup
import com.docu.app.FeatureActionUtil
import com.docu.app.FeatureInfoUtil
import com.docu.app.MenuGroupUtil
import com.docu.app.UserMenu
import com.docu.commons.CommonConstants
import com.docu.commons.SessionManagementService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by rafiqul.islam on 11/26/2014.
 */
@Component("menuGenerator")
class MenuGenerator {
    public static final Log log = LogFactory.getLog(MenuGenerator.class)
    @Autowired
    SessionManagementService sessionManagementService
    @Autowired
    MyDashboardService myDashboardService

    List<Map> getLevelOneMenu(long moduleId) {
        List<Map> mapList = []
        List<UserMenu> menuList = sessionManagementService.userMenuList.findAll { it.moduleId == moduleId }
        Map menuGroupMap = null
        MenuGroup menuGroup = null
        menuList.each {
            menuGroupMap = [:]
            menuGroup = MenuGroupUtil.instance.get(it.menuGroupId)
            menuGroupMap.id = it.menuGroupId
            menuGroupMap.name = menuGroup.title
            menuGroupMap.position = menuGroup.position
            mapList.add(menuGroupMap)
        }
        return mapList
    }

    List<Map> getLevelTwoMenu(long menuGroupId) {
        List<Map> mapList = []
        List<UserMenu> menuList = sessionManagementService.userMenuList.findAll { it.menuGroupId == menuGroupId }
        Map featureInfoMap = null
        FeatureInfo featureInfo = null
        menuList.each {
            featureInfoMap = [:]
            featureInfo = FeatureInfoUtil.instance.get(it.featureInfoId)
            featureInfoMap.id = it.featureInfoId
            featureInfoMap.name = featureInfo.featureName
            featureInfoMap.position = featureInfo.position
            mapList.add(featureInfoMap)
        }
        return mapList
    }

    List<Map> getLevelThreeMenu(long featureInfoId) {
        try {
            boolean displayCode = sessionManagementService.displayMenuCode()
            List<Map> mapList = []
            List<UserMenu> menuList = sessionManagementService.userMenuList.findAll {
                it.featureInfoId == featureInfoId
            }
            Map featureActionMap = null
            FeatureAction featureAction = null
            menuList.each {
                featureActionMap = [:]
                featureAction = FeatureActionUtil.instance.get(it.featureActionId)
                featureActionMap.id = it.featureActionId
                featureActionMap.name = displayCode ? featureAction.actionCode + CommonConstants.SPACE + featureAction.actionName : featureAction.actionName
                featureActionMap.position = featureAction.position
                featureActionMap.menuUrl = featureAction.menuUrl
                featureActionMap.imageCss = featureAction.imageCss
                mapList.add(featureActionMap)
            }
            return mapList
        }
        catch (Exception ex){
            log.error(ex.message)
        }
    }
    List<Map> getLevelMostVisitedMenu() {
        try{
            boolean displayCode = sessionManagementService.displayMenuCode()
            List<Map> mapList = []
            List menuList = myDashboardService.getMostVisitedMenuList()
            Map featureActionMap = null
            FeatureAction featureAction = null
            menuList.each {
                featureActionMap = [:]
                featureAction = FeatureActionUtil.instance.get(it.id)
                featureActionMap.id = it.id
                featureActionMap.name = displayCode ? featureAction.actionCode + CommonConstants.SPACE + featureAction.actionName : featureAction.actionName
                featureActionMap.position = featureAction.position
                featureActionMap.menuUrl = featureAction.menuUrl
                featureActionMap.imageCss = featureAction.imageCss
                mapList.add(featureActionMap)
            }
            return mapList
        }
        catch (Exception ex){
            log.error(ex.message)
        }
    }
    List<Map> getLevelWorkflowMenu() {
        try{
            boolean displayCode = sessionManagementService.displayMenuCode()
            List<Map> mapList = []
            List menuList = myDashboardService.getWorkflowMenuList()
            Map featureActionMap = null
            Workflow workflow = null
            menuList.each {
                featureActionMap = [:]
                workflow = Workflow.read(it.id)
                featureActionMap.id = it.id
                featureActionMap.name = workflow.name
                featureActionMap.position = workflow.prioritySequence
                featureActionMap.menuUrl = workflow.menuName

                mapList.add(featureActionMap)
            }
            return mapList
        }
        catch (Exception ex){
            log.error(ex.message)
        }
    }
}
