<%@ page import="com.bits.bdfp.geolocation.TerritoryConfiguration" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Sales Channel Wise Demand</title>
<style>
/*.nameformError{margin-top: 146px !important;}*/
.formError {
    position: static;
}
</style>

<div class="main_container">
    <div class="content_container">
        <h1>Sales Channel Wise Demand</h1>

        <h3>Sales Channel Wise Demand</h3>

        <div style="width:100%;">
            <g:render template='salesChannelWiseDemandReport/create'
                      model="[productList: productList, productListSize: productListSize]"/>
            <br/>
            <g:render template="salesChannelWiseDemandReport/script"
                      model="[customerSalesChannelList    : customerSalesChannelList,
                              customerSalesChannelListSize: customerSalesChannelListSize,
                              customerCategoryList        : customerCategoryList,
                              customerCategoryListSize    : customerCategoryListSize,
                              finishProductList           : finishProductList,
                              finishProductListSize       : finishProductListSize,
                              productList                 : productList,
                              productListSize             : productListSize]"/>
        </div>

        <div id="dialog-confirm-territoryConfiguration" title="Delete alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>
    </div>
</div>
