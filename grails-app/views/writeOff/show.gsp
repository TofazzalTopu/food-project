

<%@ page import="com.bits.bdfp.inventory.demandorder.WriteOff" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="writeOff.create.label" default="Apply WriteOff"/></title>
<div class="main_container">
    <div class="content_container">
        <h1><g:message code="writeOff.create.label" default="Apply Write Off"/></h1>
        <h3><g:message code="writeOff.Info.label" default="Write Off Details"/></h3>
        <g:render template='create'/>
        <div class="clear height5"></div>
        <g:render template="script"/>


        <div id="dialog-confirm-writeOff" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?</p>
        </div>

    </div>
</div>
