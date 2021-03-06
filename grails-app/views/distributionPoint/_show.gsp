<%@ page import="com.bits.bdfp.inventory.sales.DistributionPoint" %>
<div style="width:100%;">
    <meta name="layout" content="docuThemeRollerLayout"/>
    <title><g:message code="division.create.label" default="Distribution Point"/></title>

    <div class="main_container">
        <div class="content_container">
            <h3><g:message code="division.Info.label" default="Create Distribution Point"/></h3>

            <g:render template='create' model="[result: result, list: list]"/>
            <br/>
            <g:render template="script"/>
        </div>

        <div class="jqgrid-container">
            <table id="jqgrid-grid"></table>

            <div id="jqgrid-pager"></div>
        </div>

        <div id="dialog-confirm-distributionPoint" title="Delete alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>

        <div id="dialog-geoLocation-selection" title="Geo Location Selection Missing" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>Please select a Geo Location</p>
        </div>
    </div>
</div>