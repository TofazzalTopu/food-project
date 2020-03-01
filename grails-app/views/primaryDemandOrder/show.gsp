<%@ page import="com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Create Primary Demand Order</title>

<div class="main_container">
    <div class="content_container">
        <h1>Create Primary Demand Order</h1>

        <h3>Primary Demand Order Details</h3>

        <div style="width:100%;">
            <g:render template='create' model="[enterpriseList:enterpriseList, size:size, distributionPointList: distributionPointList]"/>
            <br/>
            <g:render template="script"/>
        </div>

        <div class="jqgrid-container">
            <table id="jqgrid-grid"></table>

            <div id="jqgrid-pager"></div>
        </div>

        <div id="dialog-confirm-territoryConfiguration" title="Cancellation alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>Selected order(s) and all associated data will be permanently cancelled/deleted and cannot be recovered. Are you sure?
            </p>
        </div>
    </div>
</div>


