<%@ page import="com.docu.accesscontrol.ModuleInfo ; org.codehaus.groovy.grails.commons.ApplicationHolder as AH" %>
<%--
  Created by IntelliJ IDEA.
  User: bipul.kumar
  Date: 2/29/12
  Time: 3:06 PM
  To change this template use File | Settings | File Templates.
--%>

<title><g:message code="menuGroup.title" default="Menu Group"/></title>
<g:set var="pluginStyle" value="${AH.application.config.pluginStyle}"/>
<meta name="layout" content='${pluginStyle}'/>
<g:render plugin="docuaccesscontrol" template='/accesscontrol/common/pluginmessage'/>

<script type="text/javascript">
    $(document).ready(function() {
//        $("#moduleList").val(moduleInfoId);
        $("#moduleList").change();
    });
    var MenuGroup = {
        onModuleInfoChange: function(moduleInfoId) {

            if (moduleInfoId == 'null') {
                $('#feature-info-list').html('');
                return false;
            }
            MenuGroup.listFeatureInfo(moduleInfoId);
        },

        listFeatureInfo: function(moduleInfoId) {
            var menugroupId = ''
            menugroupId = $('#menugroupid').val()

            var url = "${request.contextPath}/${params.controller}/ajaxFeatureInfoList";
            var data = {};
            data['moduleInfoId'] = moduleInfoId
            if (menugroupId != "") {
                data['id'] = menugroupId
            }
            MenuGroup.ajax(url, data, function(html) {

                $('#feature-info-list').html(html);
            });
        },

        save: function() {
            var url = "${request.contextPath}/${params.controller}/save";
            var data = $('#menu-group-form').serialize();
            MenuGroup.json(url, data, function(html) {
                MenuGroup.listFeatureInfo($('#moduleList').val());
                if(html.message.type == 1){
                    window.location = "${request.contextPath}/${params.controller}/sort";
                    //MessageRenderer.render(html.message);
                }else{
                    MessageRenderer.render(html.message);
                }
            });
        },

        ajax: function(url, data, callback) {
            callback = typeof callback == "function" ? callback : function () {
            };
            $.ajax({
                        url: url,
                        data:data,
                        dataType: "html",
                        type:"post",
                        success: function(response) {
                            callback(response);
                        }
                    });
        },

        json: function(url, data, callback) {
            callback = typeof callback == "function" ? callback : function () {
            };
            $.ajax({
                        url: url,
                        data:data,
                        dataType: "json",
                        type:"post",
                        success: function(response) {
                            callback(response);
                        }
                    });
        }
    }
</script>

<h1><g:message code="menuGroup.title" default="Menu Group"/></h1>

<div style="width:800px;">
    <form id="menu-group-form">
        <g:hiddenField name="menugroupid" id="menugroupid" value="${menugroupid}"/>
        <g:hiddenField name="id" id="id" value="${menugroupid}"/>
        <fieldset>
            <legend>Menu Group</legend>
            <fieldset>
                <div>
                    <div class="block-title">
                        <div class="element-title">Module</div>

                        <div class="element-title">Menu Title</div>

                        <div class="clear"></div>
                    </div>

                    <div class="block-input">
                        <g:if test="${menugroupid}">
                            <div class="element-input"><g:select name="moduleInfoId" id="moduleList"
                                                                 from="${ModuleInfo.list()}" value='${moduleId}'
                                                                 optionKey="id" noSelection="['null':'-Select Module-']"
                                                                 onchange="MenuGroup.onModuleInfoChange(this.value)"
                                                                 disabled="true"/></div>
                            <input type="text" name="title" value="${title}" />

                            <span style=" padding-left: 10px;">
                                <a href="javascript:void(0)"
                                   onclick="location.href = '${request.contextPath}/${params.controller}/sort/${moduleId}'" style="font-weight: bold;">Back</a>
                            </span>

                        </g:if>
                        <g:else>
                            <div class="element-input"><g:select name="moduleInfoId" id="moduleList"
                                                                 from="${ModuleInfo.list()}" value='${moduleId}'
                                                                 optionKey="id" noSelection="['null':'-Select Module-']"
                                                                 onchange="MenuGroup.onModuleInfoChange(this.value)"/></div>
                            <input type="text" name="title" value="${title}"/>
                        </g:else>
                        <div class="clear"></div>
                    </div>
                </div>
            </fieldset>

            <div id="feature-info-list"></div>

        </fieldset>
        <br/>

        <div class="buttons">
            <g:if test="${menugroupid}">
                <input type="button"
                       class="ui-button ui-widget ui-state-default ui-corner-all"
                       value="<g:message code="menuGroup.saveButton.label" default="Update"/>"
                       onclick="MenuGroup.save()"/>
            </g:if>
            <g:else>
                <input type="button"
                       class="ui-button ui-widget ui-state-default ui-corner-all"
                       value="<g:message code="menuGroup.saveButton.label" default="Save"/>"
                       onclick="MenuGroup.save()"/>
            </g:else>
        </div>

    </form>
</div>