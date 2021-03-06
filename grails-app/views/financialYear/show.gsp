
<%@ page import="com.bits.bdfp.settings.bussinessday.FinancialYear" %>

<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="financialYear.create.label" default="Open Financial Year"/></title>
<div class="main_container">
    <div class="content_container">
        <h1><g:message code="financialYear.create.label" default="Open Financial Year"/></h1>
        <h3><g:message code="financialYear.Info.label" default="Financial Year Info"/></h3>
        <g:render template='create'/>
        <div class="clear height5"></div>
        <g:render template="script"/>

        <div class="jqgrid-container">
            <table id="jqgrid-grid-financialYear"></table>
            <div id="jqgrid-pager-financialYear"></div>
        </div>
        <div id="dialog-confirm-financialYear" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?</p>
        </div>

    </div>
</div>
