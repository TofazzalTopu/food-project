<%@ page import="com.bits.bdfp.inventory.sales.DistributionPoint" %>
<div style="width:100%;">
    <meta name="layout" content="docuThemeRollerLayout"/>
    <title>Finish Good</title>

    <div class="main_container">
        <div class="content_container">

            <h1>Create Factory Stock</h1>
            <h3>Factory Stock</h3>
            <div style="width:100%;">
                <g:render template='createNew' model="[inventoryResult: inventoryResult, inventoryList: inventoryList]" />
                <br/>
                <g:render template="script"/>
            </div>
        %{--</div>--}%

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
</div>