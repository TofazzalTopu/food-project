<%@ page import="com.bits.bdfp.inventory.sales.MarketReturn" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Register Market Return</title>

<div class="main_container">
    <div class="content_container">
        <h1>Register Market Return</h1>
        <h3>Register Market Return Details</h3>
        <g:render template='create'/>
        <div class="clear height5"></div>
        <g:render template="script" model="[customer: customer,
                dpList: dpList,
                factoryList: factoryList,
                wareHouseList: wareHouseList,
                subWareHouseList: subWareHouseList,
                dpSize: dpSize,
                factorySize: factorySize,
                wareHouseSize: wareHouseSize,
                subWareHouseSize: subWareHouseSize]"/>


        <div id="dialog-confirm-marketReturn" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>

    </div>
</div>
