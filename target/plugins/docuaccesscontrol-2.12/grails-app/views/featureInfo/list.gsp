<%@ page import="com.docu.accesscontrol.ModuleInfo ; org.codehaus.groovy.grails.commons.ApplicationHolder as AH" %>
<%--
  Created by IntelliJ IDEA.
  User: bipul.kumar
  Date: 5/2/11
  Time: 3:38 PM
  To change this template use File | Settings | File Templates.
--%>

<script type="text/javascript">


    $(document).ready(function () {
        allData = {};
        count = 0;
        $("#listModuleList").change(function () {
            jQuery("#feature-List-Grid").jqGrid('setGridParam', {url:"getFeatureInfoGridJSON?moduleInfoId=" + $("#listModuleList").val(), page:1, datatype:'json'}).trigger("reloadGrid");
        });

    });

</script>

<title>Feature List</title>

<h1>Feature Information List</h1>
<g:render plugin="docuaccesscontrol" template='/accesscontrol/common/pluginmessage'/>
<g:set var="pluginStyle" value="${AH.application.config.pluginStyle}"/>
<meta name="layout" content='${pluginStyle}'/>
%{--<meta name="layout" content="pluginStyle"/>--}%
<div style="width:800px;">
    <form id="feature-info-list-form">
        <fieldset>
            <legend>Feature Information List</legend>

            <div>
                <div class="block-title">

                    <div class="element-title" for="moduleList">Module</div>

                    <div class="clear"></div>
                </div>

                <div class="block-input">
                    <div class="element-input"><g:select name="listModuleList" id="listModuleList"
                                                         from="${ModuleInfo.list()}" value='${params.moduleid}'
                                                         optionKey="id" noSelection="['null':'-Select Module-']"/></div>

                    <div class="clear"></div>
                </div>
            </div>

            <div id="feature-info-html"></div>

            <span id="successMessage">&nbsp;</span>
        </fieldset>

        <div class="clear" style="height:5px;"></div>
        <br/>
        <g:render plugin="docuaccesscontrol" template='/featureInfo/list'/>
    </form>
</div>

<script type="text/javascript">
    //  $(document).ready(function() {
    //    $("#listModuleList").change();
    //  });

</script>