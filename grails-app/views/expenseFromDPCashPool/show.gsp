

<%@ page import="com.bits.bdfp.finance.ExpenseFromDPCashPool" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Expense From DP Cash Pool</title>
<div class="main_container">
    <div class="content_container">
        <h1>Expense From DP Cash Pool</h1>
        <h3>Expense From DP Cash Pool Details</h3>
        <g:render template='create' />
        <div class="clear height5"></div>
        <g:render template="script" model="[disList: disList, distSize:distSize]"/>

        <div class="jqgrid-container">
            <table id="jqgrid-grid-expenseFromDPCashPool"></table>
            <div id="jqgrid-pager-expenseFromDPCashPool"></div>
        </div>
        <div id="dialog-confirm-expenseFromDPCashPool" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?</p>
        </div>

    </div>
</div>
