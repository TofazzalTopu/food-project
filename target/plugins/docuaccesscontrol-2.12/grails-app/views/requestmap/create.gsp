<%@ page import="com.docu.security.UserAuthority" %>
<%@ page import="com.docu.accesscontrol.ModuleInfo ;org.codehaus.groovy.grails.commons.ApplicationHolder as AH" %>

<%--
  Created by IntelliJ IDEA.
  User: bipul.kumar
  Date: 5/10/11
  Time: 1:14 PM
  To change this template use File | Settings | File Templates.
--%>

<title>User Access Control</title>
<g:set var="pluginStyle" value="${AH.application.config.pluginStyle}"/>
<meta name="layout" content='${pluginStyle}'/>
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

    function resetModule(){
        $("#moduleList").val(0);
        showRequestMapHead(0);
    }


    function showRequestMapHead(moduleId) {
        if (moduleId == 'null' || $("#roleList option:selected").val()=='null' ) {
            MessageRenderer.render({"messageBody":"Please select Role and Module Information", "messageTitle":"Request Map", "type":"0"});
            $("#request-map-html").html('');
            return false
        }
        $('#roleName').val($("#roleList option:selected").text());
        AjaxLoader.showTo('request-map-html');
        $.ajax({
            url: "${request.contextPath}/${params.controller}/showRequestMapHead",
            dataType: "html",
            data: {moduleid:moduleId,rolename:$('#roleName').val()},

            success: function(html) {
                AjaxLoader.hideFrom();
                $("#request-map-html").html(html).show();

            }
        });
    }

    function saveRequestMap() {
        PluginSubmissionLoader.showTo();
        $.ajax({
            type:"POST",
            url: "${request.contextPath}/${params.controller}/saveRequestMap",
            dataType: "json",
            data: $("#request-map-form").serialize() ,
            success: function(json) {
                PluginSubmissionLoader.hideFrom();
                MessageRenderer.render(json.message);
                showRequestMapHead(json.moduleid)
            }
        });
    }

    function selectAll(){
        $('.request-map-checkbox').attr("checked", true);
    }

    function deSelectAll(){
        $('.request-map-checkbox').attr("checked", false);
    }

</script>

<h1 style="width:980px;">User Access Control</h1>

<form id="request-map-form">
    <div class="width1000">
        <fieldset>
            <legend>User Access Control Setup</legend>
            <fieldset>
                <div>
                    <div class="block-title">
                        <div class="element-title width400">Authority</div>

                        <div class="element-title width400">Module</div>

                        <div class="clear"></div>
                    </div>

                    <div class="block-input">

                        <div class="element-input width400"><g:select class="width380" name="roleList" id="roleList" from="${authorityList}"
                                                             optionKey="id" optionValue="authority"
                                                             noSelection="['null':'-Select Role-']"
                                                             onChange="resetModule()"/></div>

                        <div class="element-input width400"><g:select class="width380" name="moduleList" id="moduleList"
                                                             from="${moduleInfoList}" optionKey="id"
                                                             noSelection="['null':'-Select Module-']"
                                                             onChange="showRequestMapHead(this.value)"/></div>

                        <div class="element-input"><g:hiddenField name="roleName" id="roleName"/></div>

                        <div class="clear"></div>
                    </div>
                </div>
            </fieldset>
            <div class="clear"></div>
            <br>

            <div id="request-map-html"></div>
        </fieldset>
        <br/>

        <div class="buttons">
            <input type="button"
                   class="ui-button ui-widget ui-state-default ui-corner-all"
                   value="<g:message code="accountsLedgerMapping.saveButton.label" default="Save"/>"
                   onclick="saveRequestMap()"/>
        </div>
    </div>
</form>
