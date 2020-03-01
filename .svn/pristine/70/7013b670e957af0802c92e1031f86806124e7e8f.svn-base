<%@ page import="com.docu.accesscontrol.ModuleInfo ; org.codehaus.groovy.grails.commons.ApplicationHolder as AH" %>
<%--
  Created by IntelliJ IDEA.
  User: bipul
  Date: 2/13/12
  Time: 3:14 PM
  To change this template use File | Settings | File Templates.
--%>

<script type="text/javascript">
    PLUGIN_SUBMISSION_LOADER = new Image();
    PLUGIN_SUBMISSION_LOADER.src = "${resource(dir: 'images', file: 'plugin_submission_loader.gif')}";
    var AjaxLoader = {
        showTo: function(divId) {
            $('#' + divId).html('<div style="border:0px #4169e1 solid; text-align:center;"><img src="${resource(dir: 'images', file: 'pluginajax-loader.gif')}" /></div>');
        },

        hideFrom: function(divId) {
            $('#' + divId).html('');
        }
    }

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

    function loadFeatureData(moduleId) {
        if (moduleId == 'null') {
            $("#arrange-feature-info-html").html('');
            return false
        }
        AjaxLoader.showTo('arrange-feature-info-html');
        var json = null;
        $.ajax({
                    url: "${request.contextPath}/${params.controller}/ajaxFeatureInfoData?id=" + moduleId,
                    dataType: "html",
                    success: function(html) {
                        try {
                            json = JSON.parse("{" + html + "}");
                            if (typeof json == "object") {
                                MessageRenderer.renderErrorText(json.message, "Arrange Feature Info");
                                AjaxLoader.hideFrom('arrange-feature-info-html');
                            }
                        }
                        catch(ex) {
                            json = null;
                        }

                        if (!json) {
                            $("#arrange-feature-info-html").html(html).show();
                            //Declare contents of following id as sortable
                            $("#feature_info-table").sortable({
                                        stop: function(event, ui) {
                                            reIndexPosition()
                                        }
                                    });
                        }
                    }

                });
    }


    function reIndexPosition() {
        $('.items-position ').each(function(index, element) {
            $(element).val(index + 1);
        })
    }

    function arrangeFeatureInfo() {
        PluginSubmissionLoader.showTo();
        $.ajax({
                    url: "${request.contextPath}/${params.controller}/arrangeFeatureInfo",
                    dataType: "json",
                    data: $("#feature-info-arrange-form").serialize() ,
                    type: 'POST',
                    success: function(json) {
                        PluginSubmissionLoader.hideFrom();
                        if (json.message.type == 1) {
                            MessageRenderer.render(json.message)
                        } else {
                            MessageRenderer.render(json.message)
                        }
                    }
                });
    }

</script>

<title>Arrange Feature</title>

<h1>Arrange Feature Information</h1>
<g:render plugin="docuaccesscontrol" template='/accesscontrol/common/pluginmessage'/>
<g:set var="pluginStyle" value="${AH.application.config.pluginStyle}"/>
<meta name="layout" content='${pluginStyle}'/>
%{--<meta name="layout" content="pluginStyle"/>--}%
<div style="width:800px;">
    <form id="feature-info-arrange-form">
        <fieldset>
            <legend>Arrange Feature Information</legend>

            <div>
                <div class="block-title">

                    <div class="element-title" for="moduleList">Module</div>

                    <div class="clear"></div>
                </div>

                <div class="block-input">
                    <div class="element-input"><g:select name="listModuleList" id="listModuleList"
                                                         from="${ModuleInfo.list()}" value='${params.moduleid}'
                                                         optionKey="id" onChange="loadFeatureData(this.value)"
                                                         noSelection="['null':'-Select Module-']"/></div>

                    <div class="clear"></div>
                </div>
            </div>

            %{--<div id="arrange-feature-info-html"></div>--}%

            <span id="successMessage">&nbsp;</span>

            <br/>
            %{--<div><g:render template="/featureInfo/arrange/tabs/arrangeFeatureInfos" model="[ ]"/></div>--}%


            <div id="arrange-feature-info-html"></div>
            %{--<g:if test="${featureInstance !=null}">--}%

            %{--<g:each var="featureInstance" in="${featureInstance}" status="i">--}%
            %{--${featureInstance?.featureName}--}%
            %{--<g:render template="/featureInfo/arrange/tabs/arrangeFeatureInfo" model="['featureInstance':featureInstance[i],'i':i]"/>--}%

            %{--</g:each>--}%
            %{--</g:if>--}%
            %{--<g:else>--}%
            %{--<g:render template="tabs/featureAction" model="['i':i]"/>--}%
            %{--</g:else>--}%

        </fieldset>
        %{--<g:render template='/featureInfo/list'/>--}%
    </form>
</div>

