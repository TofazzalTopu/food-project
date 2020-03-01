

<%@ page import="com.bits.bdfp.inventory.sales.MarketReturn" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="marketReturn.create.label" default="Show Unadjusted Invoice"/></title>
<div class="main_container">
    <div class="content_container">
        <h1><g:message code="marketReturn.create.label" default="Show Unadjusted Invoice"/></h1>
        <h3><g:message code="marketReturn.Info.label" default="Unadjusted Invoice Info"/></h3>
        <g:render template='createUnadjusted' model="[result:result, list: list]"/>
        <div class="clear height5"></div>
        <g:render template="scriptUnadjusted"/>


        <div id="dialog-confirm-marketReturn" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?</p>
        </div>

    </div>
</div>
