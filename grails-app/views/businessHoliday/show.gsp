<%@ page import="com.bits.bdfp.settings.bussinessday.BusinessHoliday" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="division.create.label" default="Open Business holiday"/></title>
<div class="main_container">
    <div class="content_container">
        <h1><g:message code="division.create.label" default="Business holiday"/></h1>
        <h3><g:message code="division.Info.label" default="Open Business holiday"/></h3>
<g:if test="${financialYearList.size()>0}">
        <g:render template='/businessHoliday/create'/>
        <div class="clear height5"></div>
        <g:render template="/businessHoliday/script"/>

    <div class="jqgrid-container">
            <table id="jqgrid-grid-division"></table>
            <div id="jqgrid-pager-division"></div>
        </div>
        <div id="dialog-confirm-holiday" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?</p>
        </div>
</g:if>
        <g:else><h2>Please open an financial year first</h2></g:else>
    </div>
</div>
