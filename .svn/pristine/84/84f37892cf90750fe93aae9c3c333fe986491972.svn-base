<%@ page import="com.bits.bdfp.inventory.retailorder.RetailOrder" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Consolidate Retail Order</title>

<div class="main_container" style="width: 900px;">
    <div class="content_container width900">
        <h1>Consolidate Retail Order</h1>
        <h3>Consolidate Retail Order & Generate Secondary Demand Order</h3>
        <g:render template='/retailOrder/consolidateRetailOrder/create' model="[customerMaster: customerMaster, suAreaCount: suAreaCount, subAreaList: subAreaList]"/>
        <div class="clear height5"></div>
        <g:render template="/retailOrder/consolidateRetailOrder/script"/>

        <div id="dialog-confirm-retailOrder" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert"
                 style="float:left; margin:0 7px 20px 0;"></span>Selected Retail Order(s) will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>
    </div>
</div>