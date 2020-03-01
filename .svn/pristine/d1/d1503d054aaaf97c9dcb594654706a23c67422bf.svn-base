<%@ page import="com.bits.bdfp.geolocation.TerritoryConfiguration" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="territoryConfiguration.create.label" default="Create Territory "/></title>
<style>
.nameformError{margin-top: 146px !important;}
</style>
<div class="main_container">
    <div class="content_container">
        <h1><g:message code="territoryConfiguration.create.label" default="Create Territory"/></h1>

        <h3><g:message code="territoryConfiguration.info.label" default="Territory Info"/></h3>

        <div style="width:100%;">
            <g:render template='create'  model="[result:result, list:list]"/>
            <br/>
            <g:render template="script"/>
        </div>

        <div class="jqgrid-container">
            <table id="jqgrid-grid"></table>

            <div id="jqgrid-pager"></div>
        </div>

        <div id="dialog-confirm-territoryConfiguration" title="Delete alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>
    </div>
</div>


