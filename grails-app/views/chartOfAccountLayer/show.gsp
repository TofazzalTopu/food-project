

<%@ page import="com.bits.bdfp.accounts.ChartOfAccountLayer" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="marketReturn.create.label" default="Create/Update Chart of Account"/></title>
<div class="main_container">
    <div class="content_container">
        <h1><g:message code="marketReturn.create.label" default="Create/Update Chart of Account"/></h1>
        <h3><g:message code="marketReturn.Info.label" default="Chart of Account Info"/></h3>
        <g:render template='create' model="[layerList: layerList, result: result, codeLength: codeLength, layer: layer]"/>
        <div class="clear height5"></div>
        <g:render template="script" model="[layerList: layerList, update: update, editable: editable, layers:layers]"/>


        <div id="dialog-confirm-marketReturn" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?</p>
        </div>

    </div>
</div>
