<%@ page import="com.bits.bdfp.inventory.warehouse.ReplacementMiscellaneousTransactions" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Create MiscellaneousTransactions</title>

<div class="main_container">
    <div class="content_container">
        <h1>Create Miscellaneous Transactions</h1>

        <h3>Miscellaneous Transactions Details</h3>
        <g:render template='create' model="[dpList: dpList , dpSize: dpSize, factoryDpList: factoryDpList, factoryDpSize:factoryDpSize, customers: customers]"/>
        <div class="clear height5"></div>
        <g:render template="script"/>

        <div id="dialog-confirm-miscellaneousTransactions" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>

    </div>
</div>
