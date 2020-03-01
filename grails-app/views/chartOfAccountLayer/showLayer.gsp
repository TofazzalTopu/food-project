

<%@ page import="com.bits.bdfp.accounts.ChartOfAccountLayer" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="marketReturn.create.label" default="Create/Update Chart of Account Layer"/></title>
<div class="main_container">
    <div class="content_container">
        <h1><g:message code="marketReturn.create.label" default="Create/Update Chart of Account Layer"/></h1>
        <h3><g:message code="marketReturn.Info.label" default="Chart of Account Layer Info"/></h3>
        <g:render template='createLayer' model="[result:result, list:list]"/>
        <div class="clear height5"></div>
        <g:render template="scriptLayer" model="[size: size, length: length, editable: editable, layerList: layerList]"/>


        <div id="dialog-confirm-marketReturn" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?</p>
        </div>

    </div>
</div>
