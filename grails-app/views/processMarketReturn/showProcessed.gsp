<%@ page import="com.bits.bdfp.inventory.sales.ProcessMarketReturn" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>View Processed Market Return</title>
<div class="main_container">
    <div class="content_container">
        <h1>View Processed Market Return</h1>
        <h3>View Processed Market Return Details</h3>
        <g:render template='view' model="[result: result, list: list]"/>
        <div class="clear height5"></div>
        <g:render template="viewScript" model="[dpList: dpList, dpSize: dpSize]"/>

        <div id="dialog-confirm-processMarketReturn" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?</p>
        </div>

    </div>
</div>
