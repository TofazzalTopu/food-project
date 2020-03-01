<%@ page import="com.docu.accesscontrol.ModuleInfo ; org.codehaus.groovy.grails.commons.ApplicationHolder as AH" %>
<%--
  Created by IntelliJ IDEA.
  User: bipul.kumar
  Date: 4/28/11
  Time: 10:55 AM
  To change this template use File | Settings | File Templates.
--%>

<title>Feature Info</title>
<g:set var="pluginStyle" value="${AH.application.config.pluginStyle}"/>
<meta name="layout" content='${pluginStyle}'/>
%{--<meta name="layout" content="pluginStyle"/>--}%
<script type="text/javascript">
    PLUGIN_SUBMISSION_LOADER = new Image();
    PLUGIN_SUBMISSION_LOADER.src = "${request.contextPath}/images/plugin_submission_loader.gif";
    var urlJsonData = '';

    $(document).ready(function () {
        $("#feature-info-form").validationEngine({
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#feature-info-form").validationEngine('attach');
        $("#moduleList").change();

    });

    function getDataReady(){
        url = "${request.contextPath}/${params.controller}/getMenuUrls";
        $.ajax({
            'async': false,
            'global': false,
            'url': url,
            'dataType': "json",
            'success': function (data) {
                urlJsonData = data;
            }
        });
    }

    function getArrData(tid) {
        var newArr = [];
        $.each(urlJsonData, function (k, v) {
            if (tid == v.id) {
                newArr.id = v.id;
                newArr.action = v.name;
            }
        });
        return newArr;
    }
    function showMenuUrls(index, selectedVal) {
        $('#showMenuUrls_' + index + '_item').flexbox({"results": urlJsonData}, {
            //showArrow: false,
            watermark: '-- Select an Url --',
            resultTemplate: '<div class="width200 floatL name">{name}</div>',
            onSelect: function () {
                var tid = $('input[name=showMenuUrls_' + index + '_item]').val();
                var urlValue = getArrData(tid);
                $('#items_featureAction' + index + '_menuUrl').val(urlValue.action);
                $('#showMenuUrls_' + index + '_item').setValue(urlValue.action);

            },
            // width: 100,
            maxCacheBytes: 32768,
            paging: {
                pageSize: 6
            }
        });

        $('#items.featureAction' + index + '.menuUrl').val(selectedVal);
        $('#showMenuUrls_' + index + '_item_input').addClass('flexbox_input width270');
        $('#showMenuUrls_' + index + '_item_ctr').addClass('width290 mar_left6');
        $('#showMenuUrls_' + index + '_item_ctr.content').css('overflow', 'hidden');
        $('#showMenuUrls_' + index + '_item').setValue(selectedVal);
    }

    var AjaxLoader = {
        showTo: function (divId) {
            $('#' + divId).html('<div style="border:0px #4169e1 solid; text-align:center;"><img src="../images/pluginajax-loader.gif" /></div>');
        },

        hideFrom: function (divId) {
            $('#' + divId).html('');
        }
    }

    var PluginSubmissionLoader = {
        showTo: function () {
            $.blockUI({
                overlayCSS: {
                    backgroundColor: '#F0F0F0', //'#D7DFE7' //'#F0F4F7' '#E8F3FB' '#D7DFE7'
                    opacity: .3
                },
//            message: '<img src="' + SUBMISSION_LOADER + '" />',
                message: PLUGIN_SUBMISSION_LOADER,
                css: {
                    width: '50px',
                    height: '40px',
                    border: 'none',
                    borderColor: 'none',
                    backgroundColor: 'none',
                    top: ($(window).height()) / 2 + 'px',
                    left: ($(window).width()) / 2 + 'px'
                }
            });
        },
        hideFrom: function () {
            $.unblockUI()
        },
        ajaxHide: function () {
            $(document).ajaxStop($.unblockUI);
        }
    };
    function featureInstance(featureId) {
        var htmlViewData = (function () {
            var htmlView = null;
            $.ajax({
                'url'   : "${request.contextPath}/${params.controller}/ajaxFeatureHead?id=" + '${featureInstance?.id}',
                'dataType': "html",
                success: function (html) {
                    $("#feature-info-html").html(html).show();
                    PluginSubmissionLoader.hideFrom();
                    //Declare contents of following id as sortable
                    $("#feature-action-table").sortable({
                        handle: ".ui-icon-arrowthick-2-n-s",
                        stop: function (event, ui) {
                            reIndexPosition();
                        }
                    });

                }
            });
            return htmlView;
        })();
    }
    function loadFeatureBlock(featureId) {
        if (featureId == 'null') {
            $("#feature-info-html").html('');
            return false
        }
        getDataReady();
        featureInstance(featureId);
    }

    function saveFeatureActionMapping() {
        if (!$("#feature-info-form").validationEngine('validate')) {
            return false;
        }
        trimForm()
        PluginSubmissionLoader.showTo()
        $.ajax({
            url: "${request.contextPath}/${params.controller}/saveFeatureActionMapping",
            dataType: "json",
            data: $("#feature-info-form").serialize(),
            type: 'POST',
            success: function (json) {
                PluginSubmissionLoader.hideFrom()
                if (json.message.type == 1) {

                    var modid = json.moduleid
                    location.href = "${request.contextPath}/${params.controller}/list?moduleid=" + modid;
                } else {
                    MessageRenderer.render(json.message)
                }
            }
        });
    }
    function trimForm() {
        $(":input").not(":button, :submit, :reset").each(function () {
            this.value = $.trim(this.value);
        });
    }
</script>

<h1>Feature Information</h1>

<form id="feature-info-form">
    <div style="width:800px;">
        <fieldset>
            <legend>Feature And Action Information</legend>
            <fieldset>
                <div>
                    <div class="block-title">
                        <div class="element-title">Module</div>

                        <div class="clear"></div>
                    </div>

                    <div class="block-input">
                        <div class="element-input inputContainer">
                            <g:select name="moduleList" id="moduleList" from="${ModuleInfo.list()}" optionKey="id"
                                      value='${featureInstance?.moduleInfo?.id}'
                                      noSelection="['null': '-Select Module-']" onChange="loadFeatureBlock(this.value)"
                                      class="required validate[funcCall[isDropdownSelected]]"/></div>

                        <div class="clear"></div>
                    </div>
                </div>
            </fieldset>

            <div id="feature-info-html"></div>

        </fieldset>
        <br/>

        <div class="buttons">
            <input type="button"
                   class="ui-button ui-widget ui-state-default ui-corner-all"
                   value="Save"
                   onclick="saveFeatureActionMapping()"/>
        </div>
    </div>
    <g:render plugin="docuaccesscontrol" template='/accesscontrol/common/pluginmessage'/>

</form>
