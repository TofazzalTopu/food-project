<%@ page import="com.bits.bdfp.geolocation.TerritoryConfiguration" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Setup Bonus & Promotions</title>
<style>
/*.nameformError{margin-top: 146px !important;}*/
.formError {
    position: static;
}
</style>
<div class="main_container">
    <div class="content_container">
        <h1>Setup Bonus & Promotions</h1>
        <h3>Setup Bonus & Promotions Info</h3>
        <div style="width:100%;">
            <g:render template='create'  model="[productList:productList]"/>
            <br/>
            <g:render template="script" model="[territoryConfigurationList:territoryConfigurationList,
                                                territoryConfigurationListSize:territoryConfigurationListSize,
                                                customerCategoryList:customerCategoryList,
                                                customerCategoryListSize:customerCategoryListSize,]"/>
        </div>

        <div id="dialog-confirm-territoryConfiguration" title="Delete alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>
    </div>
</div>


