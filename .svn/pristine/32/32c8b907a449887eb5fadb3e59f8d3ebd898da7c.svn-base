<%@ page import="com.bits.bdfp.inventory.sales.DistributionPoint" %>
<div style="width:100%;">
    <meta name="layout" content="docuThemeRollerLayout"/>
    <title><g:message code="division.create.label" default="Reverse Finish Good"/></title>

    <div class="main_container" style="width: 1000px;">
        <div class="content_container">
            <h3><g:message code="division.Info.label" default="Reverse Finish Good"/></h3>

            <g:render template='reverseCreate' />
            <br/>
            <g:render template="reverseScript"/>
        </div>

        <div class="jqgrid-container">
            <table id="jqgrid-grid"></table>
            <div id="jqgrid-pager"></div>
        </div>
        <div class="button">
             <span class="button"><input type="button" name="select-button" id="select-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Select All" onclick="checkUnCheck();" /></span>
            <span class="button"><input type="button" name="search-button" id="search-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Reverse" onclick="reverseGood();" /></span>
        </div>
        <div id="dialog-confirm-distributionPoint" title="Delete alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>

        <div id="dialog-geoLocation-selection" title="Product Selection Missing" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>Please select a product</p>
        </div>
    </div>
</div>