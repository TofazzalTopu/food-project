package com.docu.accesscontrol

import com.docu.accesscontrol.commons.PluginMessage
import com.docu.accesscontrol.featureinfo.action.FeatureInfoAction
import com.docu.accesscontrol.featureinfo.action.ListFeatureInfoAction
import com.docu.accesscontrol.featureinfo.action.ShowFeatureInfoAction
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.context.request.RequestContextHolder
import com.docu.accesscontrol.commons.PluginGrailsClassUtil
import com.docu.accesscontrol.featureinfo.action.ArrangeFeatureInfoAction

class FeatureInfoController {
    @Autowired
    FeatureInfoAction featureInfoAction
    @Autowired
    ListFeatureInfoAction listFeatureInfoAction
    @Autowired
    ShowFeatureInfoAction showFeatureInfoAction

    @Autowired
    PluginClassUtil pluginClassUtil
    @Autowired
    PluginGrailsClassUtil pluginGrailsClassUtil
    @Autowired
    ArrangeFeatureInfoAction arrangeFeatureInfoAction
    def index = {
        redirect(action: "create", params: params)
    }

    def create = {

    }
    def edit = {
        log.info("InfoController::edit::Enter")
        def object = showFeatureInfoAction.execute(params, null)
        if (!object) {
            flash.message = "Edit message for FeatureInfo"
            redirect(action: "list")
        }
        else {
            [featureInstance: object]
        }
    }

    def show = {
        log.info("InfoController::show()::Enter")
        def object = showFeatureInfoAction.execute(params, null)
        if (object instanceof FeatureInfo) {
            log.info("InfoController::show()::Exit")
            [featureInfoInstance: object]
        }
        else {
            render(view: "list", model: [message: object.get(PluginMessage.MESSAGE)])
        }
    }

    def list = {
        //redirect(action: "getFeatureInfoGridJSON")
        String message = RequestContextHolder.currentRequestAttributes().getSession().message
        flash.message = message
        RequestContextHolder.currentRequestAttributes().getSession().removeAttribute("message")
    }

    def getFeatureInfoGridJSON = {
        String resultFeed=""
        if (params.moduleInfoId){
            resultFeed = listFeatureInfoAction.execute(params, null)
        }
        render resultFeed;
    }

    def ajaxFeatureHead = {
        Integer actionSize = 0
        List urlList = []
        pluginGrailsClassUtil.resolve()
        List<String> urls = pluginGrailsClassUtil.getApplicationUrlList()

        if (params.id != "") {
            FeatureInfo featureInfo = (FeatureInfo)showFeatureInfoAction.execute(params, null)
            actionSize = featureInfo.nFeatureActions.size()// FeatureAction.countByFeatureInfo(featureInfo)
            render(view: "featureInfo", model: ['featureInstance': featureInfo, 'actionsize': actionSize, controllerClassList: urls])
        } else {
            render(view: "featureInfo", model: ['actionsize': actionSize, controllerClassList: urls])
        }
    }

    def saveFeatureActionMapping = {
        PluginMessage msg = null
        Map object = (Map) featureInfoAction.preCondition(params)
        if (object.containsKey("featureInfo")) {
            Map result = (Map)featureInfoAction.execute(null, object)
            msg = result.get("message")
        }
        else {
            msg = object.get("message")
        }
        if (msg.type == PluginMessage.SUCCESS) {
            //accessControlSessionManagementService.message .message = msg.toString()
            RequestContextHolder.currentRequestAttributes().getSession().message = msg.toString()

            render '{"isError":0, "message":' + msg.toString() + ',"moduleid":' + object.featureInfo.moduleInfo.id + '}'
        } else {
            render '{"isError":1, "message":' + msg.toString() + ' }'
        }
    }

    def arrange = {
        render(view: "/featureInfo/arrange/arrange", model: [])
    }

    def ajaxFeatureInfoData = {
        PluginMessage msg = null
        if (params.id != "") {
            Map object = (Map) arrangeFeatureInfoAction.getFeatureInfoList(params, null)

            if (object.containsKey("featureInfo")) {
                List featureList = (List) object.get("featureInfo")
                render(view: "/featureInfo/arrange/arrangeFeatures", model: ['featureInstance': featureList])
            }
            else {
                msg = object.get("message")
                render('"isError":0, "message":' + '"No Data Found For This Module"')
            }

        } else {
            render('"isError":0, "message":' + '"No Data Found For This Module"')
        }


    }

    def arrangeFeatureInfo = {
        PluginMessage msg = null
        Map object = (Map) arrangeFeatureInfoAction.preCondition(params)
        if (object.containsKey("featureInfo")) {
            def result = arrangeFeatureInfoAction.execute(null, object)
            msg = object.get("message")
        }
        else {
            msg = object.get("message")
        }
        if (msg.type == PluginMessage.SUCCESS) {
            //accessControlSessionManagementService.message .message = msg.toString()
            RequestContextHolder.currentRequestAttributes().getSession().message = msg.toString()

            render '{"isError":0, "message":' + msg.toString() + ',"moduleid":' + object.featureInfo.moduleInfo.id + '}'
        } else {
            render '{"isError":1, "message":' + msg.toString() + ' }'
        }
    }

    def getMenuUrls = {
        List jsonUrls = []
        List urlList = []
        pluginGrailsClassUtil.resolve()
        urlList = pluginGrailsClassUtil.getActionViewNamesList()
        int controller = 0
        int actions = 0
        urlList.each {controllers ->
            controller = controller + 1
            controllers.each {
                String str = it.value.toString()
                String replaceVal = str.replaceFirst('/', '')
                actions = actions + 1
                jsonUrls.add([id:controller + actions ,name:replaceVal])
                // foundInvItemInformationList.add([id: it.id, name: it.itemName + '(' + it.itemCategory + ')', label: it.itemName + '(' + it.itemCategory + ')', categoryId: it.itemCategory.id])
            }

        }

        render jsonUrls as JSON
    }
}
