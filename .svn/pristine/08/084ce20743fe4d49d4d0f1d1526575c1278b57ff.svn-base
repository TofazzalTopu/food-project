<%--
<%--
  Created by IntelliJ IDEA.
  User: bipul.kumar
  Date: 5/3/11
  Time: 8:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.docu.accesscontrol.ModuleInfo ; org.codehaus.groovy.grails.commons.ApplicationHolder as AH" %>
<%--
  Created by IntelliJ IDEA.
  User: bipul.kumar
  Date: 4/28/11
  Time: 10:55 AM
  To change this template use File | Settings | File Templates.
--%>

<title>Feature Controller Mapping</title>
<g:set var="pluginStyle" value="${AH.application.config.pluginStyle}"/>
<meta name="layout" content='${pluginStyle}'/>
%{--<meta name="layout" content="pluginStyle"/>--}%
<script type="text/javascript">
    function addActionTemplate(table, row, index) {
        var tr = $.templateRenderer(row.text(), {i:index});
        table.append(tr)
    }

    PLUGIN_SUBMISSION_LOADER = new Image();
    PLUGIN_SUBMISSION_LOADER.src = "${resource(dir: 'images', file: 'plugin_submission_loader.gif')}";
    var AjaxLoader = {
        showTo: function(divId) {
            $('#' + divId).html('<div style="border:0px #4169e1 solid; text-align:center;"><img src="${resource(dir: 'images', file: 'pluginajax-loader.gif')}" /></div>');
        },

        hideFrom: function(divId) {
            $('#' + divId).html('');
        }
    };

    var PluginSubmissionLoader = {
        showTo: function() {
            $.blockUI({
                        overlayCSS: {
                            backgroundColor: '#F0F0F0', //'#D7DFE7' //'#F0F4F7' '#E8F3FB' '#D7DFE7'
                            opacity: .3
                        },
//            message: '<img src="' + SUBMISSION_LOADER + '" />',
                        message: PLUGIN_SUBMISSION_LOADER ,
                        css: {
                            width: '50px',
                            height: '40px',
                            border:'none',
                            borderColor: 'none',
                            backgroundColor: 'none',
                            top:  ($(window).height()) / 2 + 'px',
                            left: ($(window).width()) / 2 + 'px'
                        }
                    });
        },
        hideFrom: function() {
            $.unblockUI()
        },
        ajaxHide: function() {
            $(document).ajaxStop($.unblockUI);
        }
    };
</script>
<script type="text/javascript">
    $(document).ready(function() {
//    $("#moduleList").change()
        $('#featureList').append('<option value="null">-Select Feature-</option>')
    });

    function loadControllerBlock(actionId) {
        if (actionId == 'null') {
            $("#feature-info-html").html('');
            return false
        }
        AjaxLoader.showTo('feature-info-html');
        $.ajax({
                    url: "${request.contextPath}/${params.controller}/ajaxControllerHead?actionId=" + actionId,
                    dataType: "html",
                    success: function(html) {
                        $("#feature-info-html").html(html).show();
                    }
                });
    }


    function loadFeatureInfo() {
        $('#feature-info-html').html('');
        $('#featureList').children().remove().end();

        $.ajax({
            url: "${request.contextPath}/${params.controller}/getFeatureListByModule",
            dataType: "json",
            data: {moduleId:$('#moduleList').val()},
            success: function(json) {
                $('#featureList').append('<option value="null">-Select Feature-</option>')
                for (key in json) {
                    $('#featureList').append('<option value="' + json[key].id + '">' + json[key].featureName + '</option>')
                }
            }
        });
    }

    function loadActionInfo() {
        $('#feature-info-html').html('');
        $('#actionList').children().remove().end();

        $.ajax({
            url: "${request.contextPath}/${params.controller}/getActionListByFeature",
            dataType: "json",
            data: {featureId:$('#featureList').val()},
            success: function(json) {
                $('#actionList').append('<option value="null">-Select Action-</option>')
                for (key in json) {
                    $('#actionList').append('<option value="' + json[key].id + '">' + json[key].actionName + '</option>')
                }
            }
        });
    }

    function saveFeatureActionMapping() {
        PluginSubmissionLoader.showTo();
        $.ajax({
            url: "${request.contextPath}/${params.controller}/saveFeatureControllerMapping",
            dataType: "json",
            data: $("#feature-info-form").serialize() ,
            type: 'POST',
            success: function(json) {
                PluginSubmissionLoader.hideFrom();
                MessageRenderer.render(json.message);
                //Have to call to show the saved value to edit.
                //loadControllerBlock(5)
                loadControllerBlock(json.actionId);
            }
        });
    }
</script>
<script type="text/javascript">
    function loadControllerAction(urlVal, index) {
        $.ajax({
                    url: "${request.contextPath}/${params.controller}/loadControllerAction",
                    dataType: "html",
                    data: {url:urlVal, index:index,actionid:$("#actionList").val()},
                    success: function(response) {
                        $('#closer-list-' + index).html(response);
                        $(".controllerList").multiselect();
                    }
                });
    }
</script>

<h1>Feature Controller Mapping Details</h1>

<div style="width:800px;">
    <form id="feature-info-form">
        <fieldset>
            <legend>Feature And Action Details</legend>

            <div>
                <div class="block-title">
                    <div class="element-title width200">Module</div>

                    <div class="element-title width200">Feature</div>

                    <div class="element-title width200">Action</div>

                    <div class="clear"></div>
                </div>

                <div class="block-input">
                    <div class="element-input width200"><g:select name="moduleList" id="moduleList" from="${ModuleInfo.list()}"
                                                         optionKey="id" noSelection="['null':'-Select Module-']"
                                                         onChange="loadFeatureInfo(this.value)"
                                                         class="width180" style="padding-left:0;"/></div>

                    <div class="element-input width200"><g:select name="featureList" id="featureList" optionKey="id"
                                                         noSelection="['null':'-Select Feature-']"
                                                         onChange="loadActionInfo(this.value)"
                                                         class="width180"/></div>

                    <div class="element-input width200"><g:select name="actionList" id="actionList" optionKey="id"
                                                         noSelection="['null':'-Select Action-']"
                                                         onChange="loadControllerBlock(this.value)"
                                                         class="width180"/></div>

                    <div class="clear"></div>
                </div>
            </div>

            <div id="feature-info-html"></div>
        </fieldset>
        <br/>

        <div class="buttons">
            <input type="button"
                   class="ui-button ui-widget ui-state-default ui-corner-all"
                   value="Save"
                   onclick="saveFeatureActionMapping()"/>
        </div>
    </form>
</div>