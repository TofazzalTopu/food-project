package com.docu.accesscontrol

import com.docu.security.ApplicationUser
import com.docu.security.ApplicationUserAuthority
import com.docu.security.Requestmap
import com.docu.security.UserAuthority
import com.docu.security.UserType
import grails.plugins.springsecurity.SpringSecurityService

class DefaultDataService {

    static transactional = true
    SpringSecurityService springSecurityService

    def initializeDefaultData() {

        //
        ModuleInfo bracDairyModule = null
        if(ModuleInfo.count() == 0){
            bracDairyModule = new ModuleInfo(moduleCode: "BDFE", moduleName: "BRAC DAIRY & FOOD", description: "BRAC DAIRY & FOOD Enterprise", moduleShortName: "bdfe", fileName: "bdfe", url: "bdfe").save(validate: false)
        } else{
            bracDairyModule = ModuleInfo.list().first()
        }
        if(FeatureInfo.count() == 0) {
            /************ Create Feature for Feature Setup  *****************/
            FeatureInfo feature = new FeatureInfo(featureName: "Feature", description: "Feature", moduleInfo: bracDairyModule, position: 1).save(validate: false)

            // Create Feature Actions for Feature Setup
            // New Feature
            FeatureAction featureActionNew = new FeatureAction(featureInfo: feature, position: 1, actionCode: "BDFE001", actionName: "New Feature",
                    description: "New Feature", imageCss: "featureInfo_create", menuUrl: "featureInfo/create", isPermittedToShow: 1, isDeleted: false).save(validate: false)

            // Feature List
            FeatureAction featureActionList = new FeatureAction(featureInfo: feature, position: 2, actionCode: "BDFE002", actionName: "Feature List",
                    description: "Feature List", imageCss: "featureInfo_list", menuUrl: "featureInfo/list", isPermittedToShow: 1, isDeleted: false).save(validate: false)

            // Feature Sorting
            FeatureAction featureActionSorting = new FeatureAction(featureInfo: feature, position: 3, actionCode: "BDFE003", actionName: "Feature Sorting",
                    description: "Feature Sorting", imageCss: "featureInfo_arrange", menuUrl: "featureInfo/arrange", isPermittedToShow: 1, isDeleted: false).save(validate: false)

            // Feature Controller Mapping
            FeatureAction featureActionController = new FeatureAction(featureInfo: feature, position: 4, actionCode: "BDFE004", actionName: "Feature Controller Mapping",
                    description: "Feature Controller Mapping", imageCss: "featureControllerMapping_create", menuUrl: "featureControllerMapping/create", isPermittedToShow: 1, isDeleted: false).save(validate: false)
            /***************************************************************/

            /******************** Create Feature for Menu Setup ************/
            FeatureInfo featureMenu = new FeatureInfo(featureName: "Menu", description: "Menu", moduleInfo: bracDairyModule, position: 2).save(validate: false)
            // Create Feature Actions for Menu Setup
            // New Menu
            FeatureAction newMenu = new FeatureAction(featureInfo: featureMenu, position: 1, actionCode: "BDFE005", actionName: "New Menu", menuUrl: "menuGroup/create", imageCss: "menuGroup_create",
                    description: "New Menu", isDeleted: false, isPermittedToShow: 1).save(validate: false)

            // Short Menu Items
            FeatureAction sortMenu = new FeatureAction(featureInfo: featureMenu, position: 2, actionCode: "BDFE006", actionName: "Sort Menu Items", menuUrl: "menuGroup/sort", imageCss: "menuGroup_sort",
                    description: "Sort Menu Items", isDeleted: false, isPermittedToShow: 1).save(validate: false)


            /*****************************************************************/

            /************ Create Feature for User Account Setup  *****************/
            FeatureInfo featureUser = new FeatureInfo(featureName: "User Account", description: "User Account", moduleInfo: bracDairyModule, position: 3).save()

            // Create Feature Actions for Feature Setup
            // User Authority
            FeatureAction userAuthorityAction = new FeatureAction(featureInfo: featureUser, position: 1, actionCode: "BDFE011", actionName: "Authority", menuUrl: "userAuthority/show", imageCss: "userAuthority_show",
                    description: "Authority", isDeleted: false, isPermittedToShow: 1).save(validate: false)
            // New User
            FeatureAction featureActionNewUser = new FeatureAction(featureInfo: featureUser, position: 2, actionCode: "BDFE007", actionName: "New User",
                    description: "New User", imageCss: "applicationUser_create", menuUrl: "userAccount/create", isPermittedToShow: 1, isDeleted: false).save(validate: false)

            // User List
            FeatureAction featureActionUserList = new FeatureAction(featureInfo: featureUser, position: 3, actionCode: "BDFE008", actionName: "User List",
                    description: "User List", imageCss: "applicationUser_list", menuUrl: "userAccount/list", isPermittedToShow: 1, isDeleted: false).save(validate: false)

            // User Control Panel
            FeatureAction featureActionUserPanel = new FeatureAction(featureInfo: featureUser, position: 4, actionCode: "BDFE009", actionName: "User Control Panel",
                    description: "User Control Panel", imageCss: "applicationUser_userControlPanel", menuUrl: "userAccount/userControlPanel", isPermittedToShow: 1, isDeleted: false).save(validate: false)

            // Access Control
            FeatureAction accessControlAction = new FeatureAction(featureInfo: featureUser, position: 5, actionCode: "BDFE010", actionName: "Access Control", menuUrl: "requestmap/create", imageCss: "requestmap_create",
                    description: "Access Control", isDeleted: false, isPermittedToShow: 1).save(validate: false)


            /***************************************************************/

            //  Feature Controller Mapping
            if(FeatureControllerMapping.count() == 0){
                // New Feature
                new FeatureControllerMapping(featureAction: featureActionNew, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/index", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNew, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/index/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNew, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/ajaxFeatureHead", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNew, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/ajaxFeatureHead/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNew, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/arrangeFeatureInfo", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNew, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/arrangeFeatureInfo/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNew, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/create", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNew, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/create/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNew, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/saveFeatureActionMapping", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNew, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/saveFeatureActionMapping/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNew, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/getFeatureInfoGridJSON", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNew, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/getFeatureInfoGridJSON/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNew, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/show", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNew, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/show/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNew, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/ajaxFeatureInfoData", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNew, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/ajaxFeatureInfoData/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNew, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/arrange", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNew, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/arrange/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNew, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/list", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNew, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/list/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNew, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/edit", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNew, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/edit/**", isDeleted: false).save(validate: false)

                // Feature List
                new FeatureControllerMapping(featureAction: featureActionList, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/index", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionList, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/index/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionList, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/ajaxFeatureHead", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionList, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/ajaxFeatureHead/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionList, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/arrangeFeatureInfo", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionList, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/arrangeFeatureInfo/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionList, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/create", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionList, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/create/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionList, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/saveFeatureActionMapping", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionList, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/saveFeatureActionMapping/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionList, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/getFeatureInfoGridJSON", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionList, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/getFeatureInfoGridJSON/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionList, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/show", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionList, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/show/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionList, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/ajaxFeatureInfoData", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionList, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/ajaxFeatureInfoData/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionList, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/arrange", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionList, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/arrange/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionList, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/list", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionList, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/list/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionList, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/edit", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionList, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/edit/**", isDeleted: false).save(validate: false)

                // Feature Sorting
                new FeatureControllerMapping(featureAction: featureActionSorting, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/index", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionSorting, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/index/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionSorting, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/ajaxFeatureHead", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionSorting, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/ajaxFeatureHead/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionSorting, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/arrangeFeatureInfo", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionSorting, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/arrangeFeatureInfo/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionSorting, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/create", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionSorting, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/create/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionSorting, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/saveFeatureActionMapping", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionSorting, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/saveFeatureActionMapping/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionSorting, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/getFeatureInfoGridJSON", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionSorting, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/getFeatureInfoGridJSON/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionSorting, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/show", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionSorting, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/show/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionSorting, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/ajaxFeatureInfoData", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionSorting, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/ajaxFeatureInfoData/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionSorting, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/arrange", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionSorting, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/arrange/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionSorting, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/list", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionSorting, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/list/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionSorting, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/edit", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionSorting, controllerName: "com.docu.accesscontrol.FeatureInfoController", controllerAction: "/featureInfo/edit/**", isDeleted: false).save(validate: false)

                // Feature Controller Mapping
                new FeatureControllerMapping(featureAction: featureActionController, controllerName: "com.docu.accesscontrol.FeatureControllerMappingController", controllerAction: "/featureControllerMapping", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionController, controllerName: "com.docu.accesscontrol.FeatureControllerMappingController", controllerAction: "/featureControllerMapping/", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionController, controllerName: "com.docu.accesscontrol.FeatureControllerMappingController", controllerAction: "/featureControllerMapping/getFeatureListByModule/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionController, controllerName: "com.docu.accesscontrol.FeatureControllerMappingController", controllerAction: "/featureControllerMapping/create", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionController, controllerName: "com.docu.accesscontrol.FeatureControllerMappingController", controllerAction: "/featureControllerMapping/create/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionController, controllerName: "com.docu.accesscontrol.FeatureControllerMappingController", controllerAction: "/featureControllerMapping/loadControllerAction/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionController, controllerName: "com.docu.accesscontrol.FeatureControllerMappingController", controllerAction: "/featureControllerMapping/saveFeatureControllerMapping", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionController, controllerName: "com.docu.accesscontrol.FeatureControllerMappingController", controllerAction: "/featureControllerMapping/saveFeatureControllerMapping/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionController, controllerName: "com.docu.accesscontrol.FeatureControllerMappingController", controllerAction: "/featureControllerMapping/ajaxControllerHead", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionController, controllerName: "com.docu.accesscontrol.FeatureControllerMappingController", controllerAction: "/featureControllerMapping/ajaxControllerHead/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionController, controllerName: "com.docu.accesscontrol.FeatureControllerMappingController", controllerAction: "/featureControllerMapping/getActionListByFeature", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionController, controllerName: "com.docu.accesscontrol.FeatureControllerMappingController", controllerAction: "/featureControllerMapping/loadControllerAction", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionController, controllerName: "com.docu.accesscontrol.FeatureControllerMappingController", controllerAction: "/featureControllerMapping/getActionListByFeature/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionController, controllerName: "com.docu.accesscontrol.FeatureControllerMappingController", controllerAction: "/featureControllerMapping/getFeatureListByModule", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionController, controllerName: "com.docu.accesscontrol.FeatureControllerMappingController", controllerAction: "/featureControllerMapping/index", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionController, controllerName: "com.docu.accesscontrol.FeatureControllerMappingController", controllerAction: "/featureControllerMapping/index/**", isDeleted: false).save(validate: false)

                // New Menu
                new FeatureControllerMapping(featureAction: newMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/index", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: newMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/index/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: newMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/save", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: newMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/save/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: newMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/sort", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: newMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/sort/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: newMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/create", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: newMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/create/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: newMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/saveMenuGroupSorting", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: newMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/saveMenuGroupSorting/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: newMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/saveMenuItemSorting", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: newMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/saveMenuItemSorting/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: newMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/ajaxMenuItemListByMenuGroup", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: newMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/ajaxMenuItemListByMenuGroup/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: newMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/ajaxMenuGroupListByModuleInfo", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: newMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/ajaxMenuGroupListByModuleInfo/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: newMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/ajaxFeatureInfoList", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: newMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/ajaxFeatureInfoList/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: newMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/edit", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: newMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/edit/**", isDeleted: false).save(validate: false)

                // Sort Menu Items
                new FeatureControllerMapping(featureAction: sortMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: sortMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: sortMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/index", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: sortMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/index/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: sortMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/save", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: sortMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/save/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: sortMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/sort", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: sortMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/sort/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: sortMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/create", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: sortMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/create/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: sortMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/saveMenuGroupSorting", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: sortMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/saveMenuGroupSorting/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: sortMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/saveMenuItemSorting", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: sortMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/saveMenuItemSorting/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: sortMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/ajaxMenuItemListByMenuGroup", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: sortMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/ajaxMenuItemListByMenuGroup/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: sortMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/ajaxMenuGroupListByModuleInfo", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: sortMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/ajaxMenuGroupListByModuleInfo/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: sortMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/ajaxFeatureInfoList", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: sortMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/ajaxFeatureInfoList/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: sortMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/edit", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: sortMenu, controllerName: "com.docu.accesscontrol.MenuGroupController", controllerAction: "/menuGroup/edit/**", isDeleted: false).save(validate: false)

                // User Accounts

                // New User Authority
                new FeatureControllerMapping(featureAction: userAuthorityAction, controllerName: "com.docu.security.UserAuthorityController", controllerAction: "/userAuthority/show", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: userAuthorityAction, controllerName: "com.docu.security.UserAuthorityController", controllerAction: "/userAuthority/show/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: userAuthorityAction, controllerName: "com.docu.security.UserAuthorityController", controllerAction: "/userAuthority/edit", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: userAuthorityAction, controllerName: "com.docu.security.UserAuthorityController", controllerAction: "/userAuthority/edit/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: userAuthorityAction, controllerName: "com.docu.security.UserAuthorityController", controllerAction: "/userAuthority/save", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: userAuthorityAction, controllerName: "com.docu.security.UserAuthorityController", controllerAction: "/userAuthority/save/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: userAuthorityAction, controllerName: "com.docu.security.UserAuthorityController", controllerAction: "/userAuthority/update", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: userAuthorityAction, controllerName: "com.docu.security.UserAuthorityController", controllerAction: "/userAuthority/update/**", isDeleted: false).save(validate: false)

                // New User
                new FeatureControllerMapping(featureAction: featureActionNewUser, controllerName: "com.docu.security.ApplicationUserController", controllerAction: "/userAccount/checkAvailability", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNewUser, controllerName: "com.docu.security.ApplicationUserController", controllerAction: "/userAccount/checkAvailability/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNewUser, controllerName: "com.docu.security.ApplicationUserController", controllerAction: "/userAccount/index", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNewUser, controllerName: "com.docu.security.ApplicationUserController", controllerAction: "/userAccount/index/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNewUser, controllerName: "com.docu.security.ApplicationUserController", controllerAction: "/userAccount/save", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNewUser, controllerName: "com.docu.security.ApplicationUserController", controllerAction: "/userAccount/save/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNewUser, controllerName: "com.docu.security.ApplicationUserController", controllerAction: "/userAccount/create", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNewUser, controllerName: "com.docu.security.ApplicationUserController", controllerAction: "/userAccount/create/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNewUser, controllerName: "com.docu.security.ApplicationUserController", controllerAction: "/userAccount/listCustomer", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNewUser, controllerName: "com.docu.security.ApplicationUserController", controllerAction: "/userAccount/popupCustomerListPanel/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNewUser, controllerName: "com.docu.security.ApplicationUserController", controllerAction: "/userAccount/jsonCustomerList/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNewUser, controllerName: "com.docu.security.ApplicationUserController", controllerAction: "/userAccount/listEmployee", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNewUser, controllerName: "com.docu.security.ApplicationUserController", controllerAction: "/userAccount/popupEmployeeListPanel/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNewUser, controllerName: "com.docu.security.ApplicationUserController", controllerAction: "/userAccount/jsonEmployeeList/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNewUser, controllerName: "com.docu.security.ApplicationUserController", controllerAction: "/userAccount/saveApplicationUserRemotely", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNewUser, controllerName: "com.docu.security.ApplicationUserController", controllerAction: "/userAccount/saveApplicationUserRemotely/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionNewUser, controllerName: "com.docu.security.ApplicationUserController", controllerAction: "/userAccount/updateApplicationUserRemotely/**", isDeleted: false).save(validate: false)

                // User List
                new FeatureControllerMapping(featureAction: featureActionUserList, controllerName: "com.docu.security.ApplicationUserController", controllerAction: "/userAccount/list", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionUserList, controllerName: "com.docu.security.ApplicationUserController", controllerAction: "/userAccount/list/**", isDeleted: false).save(validate: false)

                // User Control Panel
                new FeatureControllerMapping(featureAction: featureActionUserPanel, controllerName: "com.docu.security.ApplicationUserController", controllerAction: "/userAccount/userControlPanel", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionUserPanel, controllerName: "com.docu.security.ApplicationUserController", controllerAction: "/userAccount/userControlPanel/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionUserPanel, controllerName: "com.docu.security.ApplicationUserController", controllerAction: "/userAccount/updateApplicationUserStatus", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: featureActionUserPanel, controllerName: "com.docu.security.ApplicationUserController", controllerAction: "/userAccount/updateApplicationUserStatus/**", isDeleted: false).save(validate: false)

                //
                // Access Control
                new FeatureControllerMapping(featureAction: accessControlAction, controllerName: "com.docu.accesscontrol.RequestmapController", controllerAction: "/requestmap", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: accessControlAction, controllerName: "com.docu.accesscontrol.RequestmapController", controllerAction: "/requestmap/", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: accessControlAction, controllerName: "com.docu.accesscontrol.RequestmapController", controllerAction: "/requestmap/index", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: accessControlAction, controllerName: "com.docu.accesscontrol.RequestmapController", controllerAction: "/requestmap/index/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: accessControlAction, controllerName: "com.docu.accesscontrol.RequestmapController", controllerAction: "/requestmap/saveRequestMap", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: accessControlAction, controllerName: "com.docu.accesscontrol.RequestmapController", controllerAction: "/requestmap/saveRequestMap/**", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: accessControlAction, controllerName: "com.docu.accesscontrol.RequestmapController", controllerAction: "/requestmap/showRequestMapHead", isDeleted: false).save(validate: false)
                new FeatureControllerMapping(featureAction: accessControlAction, controllerName: "com.docu.accesscontrol.RequestmapController", controllerAction: "/requestmap/showRequestMapHead/**", isDeleted: false).save(validate: false)

            }

            // Menu Setup
            if(MenuGroup.count() == 0){
                MenuGroup featureSetupMenu = new MenuGroup(moduleInfoId: bracDairyModule.id, title: "Feature Setup", position: 1).save(validate: false)
                MenuGroup accessControlMenu = new MenuGroup(moduleInfoId: bracDairyModule.id, title: "User Access", position: 2).save(validate: false)

                // Menu and Feature mapping
                if(MenuItem.count() == 0){
                    new MenuItem(menuGroupId: featureSetupMenu.id, featureInfoId: feature.id, position: 1).save(validate: false)
                    new MenuItem(menuGroupId: featureSetupMenu.id, featureInfoId: featureMenu.id, position: 1).save(validate: false)
                    new MenuItem(menuGroupId: accessControlMenu.id, featureInfoId: featureUser.id, position: 2).save(validate: false)
                }
            }
            UserType superAdminType = null
            if(UserType.count() == 0){
                superAdminType = new UserType(typeName: "Super Admin").save(validate: false)
                new UserType(typeName: "Admin").save(validate: false)
                new UserType(typeName: "Customer").save(validate: false)
                new UserType(typeName: "Other").save(validate: false)
            }
            if(!superAdminType){
                superAdminType = UserType.findByTypeName("Super Admin")
            }

            if(UserAuthority.count() == 0){
                UserAuthority saAuthority = new UserAuthority(authority: "ROLE_SA", role: "SADMIN").save(validate: false)
                UserAuthority adminAuthority = new UserAuthority(authority: "ROLE_ADMIN", role: "ADMIN").save(validate: false)
                UserAuthority agentAuthority = new UserAuthority(authority: "ROLE_CUSTOMER", role: "CUSTOMER").save(validate: false)
                UserAuthority subAgentAuthority = new UserAuthority(authority: "ROLE_OTHER", role: "OTHER").save(validate: false)

                if(ApplicationUser.count() == 0){
                    // Create SAdmin User
                    ApplicationUser saUser = new ApplicationUser(username: "sadmin", fullName: "System Administrator", accountExpired: false, accountLocked: false, emailAddress: "sadmin@agentbanking.com", enabled: true, passwordExpired: false)
                    saUser.password = springSecurityService.encodePassword("abc123\$")
                    saUser.userType = superAdminType
                    saUser.save(validate: false)

                    if(ApplicationUserAuthority.count() == 0){
                        new ApplicationUserAuthority(userAuthority: saAuthority, applicationUser: saUser).save(validate: false)
                    }
                }
            }

            if(MenuMap.count() == 0){
                // Feature
                new MenuMap(moduleId: bracDairyModule.id, roleName: "ROLE_SA", featureActionId: featureActionNew.id).save(validate: false)
                new MenuMap(moduleId: bracDairyModule.id, roleName: "ROLE_SA", featureActionId: featureActionList.id).save(validate: false)
                new MenuMap(moduleId: bracDairyModule.id, roleName: "ROLE_SA", featureActionId: featureActionSorting.id).save(validate: false)
                new MenuMap(moduleId: bracDairyModule.id, roleName: "ROLE_SA", featureActionId: featureActionController.id).save(validate: false)
                // Menu
                new MenuMap(moduleId: bracDairyModule.id, roleName: "ROLE_SA", featureActionId: newMenu.id).save(validate: false)
                new MenuMap(moduleId: bracDairyModule.id, roleName: "ROLE_SA", featureActionId: sortMenu.id).save(validate: false)
                // User Access
                new MenuMap(moduleId: bracDairyModule.id, roleName: "ROLE_SA", featureActionId: userAuthorityAction.id).save(validate: false)
                new MenuMap(moduleId: bracDairyModule.id, roleName: "ROLE_SA", featureActionId: featureActionNewUser.id).save(validate: false)
                new MenuMap(moduleId: bracDairyModule.id, roleName: "ROLE_SA", featureActionId: featureActionUserList.id).save(validate: false)
                new MenuMap(moduleId: bracDairyModule.id, roleName: "ROLE_SA", featureActionId: featureActionUserPanel.id).save(validate: false)
                new MenuMap(moduleId: bracDairyModule.id, roleName: "ROLE_SA", featureActionId: accessControlAction.id).save(validate: false)
            }

            if(Requestmap.count() == 0){
                new Requestmap(moduleId: 0, featureControllerMappingId: 0, configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY", url: "/*").save(validate: false)
                new Requestmap(moduleId: 0, featureControllerMappingId: 0, configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY", url: "/js/**").save(validate: false)
                new Requestmap(moduleId: 0, featureControllerMappingId: 0, configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY", url: "/css/**").save(validate: false)
                new Requestmap(moduleId: 0, featureControllerMappingId: 0, configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY", url: "/images/**").save(validate: false)
                new Requestmap(moduleId: 0, featureControllerMappingId: 0, configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY", url: "/login/**").save(validate: false)
                new Requestmap(moduleId: 0, featureControllerMappingId: 0, configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY", url: "/logout/**").save(validate: false)
                new Requestmap(moduleId: 0, featureControllerMappingId: 0, configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY", url: "/authorization/**").save(validate: false)
                new Requestmap(moduleId: 0, featureControllerMappingId: 0, configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY", url: "/plugins/**").save(validate: false)
                new Requestmap(moduleId: 0, featureControllerMappingId: 0, configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY", url: "/themes/**").save(validate: false)
                new Requestmap(moduleId: 0, featureControllerMappingId: 0, configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY", url: "/menu/**").save(validate: false)
                new Requestmap(moduleId: 0, featureControllerMappingId: 0, configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY", url: "/ajaxUploadify/**").save(validate: false)
                new Requestmap(moduleId: 0, featureControllerMappingId: 0, configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY", url: "/tmp/**").save(validate: false)
                new Requestmap(moduleId: 0, featureControllerMappingId: 0, configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY", url: "/fileStream/**").save(validate: false)
                new Requestmap(moduleId: 0, featureControllerMappingId: 0, configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY", url: "/ajaxFeed/**").save(validate: false)
            }
        }
        // Initialize common setup
    }
}
