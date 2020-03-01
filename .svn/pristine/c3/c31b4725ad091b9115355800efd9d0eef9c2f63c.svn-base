<%@ page import="com.bits.bdfp.inventory.sales.DistributionPoint" %>
<div style="width:100%;">
    <meta name="layout" content="docuThemeRollerLayout"/>
    <title>Process Primary Order</title>

    <div class="main_container">
        <div class="content_container">
            <h3>Process Primary Order</h3>

            <g:render template='create' model="[warehouseList: warehouseList, enterpriseList:enterpriseList, size:size, salesChannelList:salesChannelList, salesChannelSize:salesChannelSize]"/>
            <br/>
            <g:render template="script"/>
        </div>
       %{--<div>--}%
        %{--<div class="jqgrid-container">--}%
            %{--<table id="jqgrid-grid"></table>--}%

            %{--<div id="jqgrid-pager"></div>--}%
        %{--</div>--}%

       %{--</div>--}%
        <div id="dialog-confirm-distributionPoint" title="Delete alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>

        <div id="dialog-primaryOrder-selection" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>Please Select a Primary Order</p>
        </div>



    </div>
</div>