<%@ page import="com.bits.bdfp.inventory.warehouse.SubWarehouse" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="subWarehouse.create.label" default="Sub Inventory"/></title>

<div class="main_container">
    <div class="content_container">
        <h1><g:message code="subWarehouse.create.label" default="Sub Inventory"/></h1>

        <h3><g:message code="subWarehouse.Info.label" default="Sub Inventory Information"/></h3>
        <g:render template='create' model="[list: list, result: result]"/>
        <div class="clear height5"></div>
        <g:render template="script"/>

        <div class="jqgrid-container">
            <table id="jqgrid-grid"></table>

            <div id="jqgrid-pager"></div>
        </div>

        <div id="dialog-confirm-subWarehouse" title="Delete alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>

    </div>
</div>
