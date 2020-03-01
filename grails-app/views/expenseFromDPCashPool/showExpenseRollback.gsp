<%@ page import="com.bits.bdfp.finance.ExpenseFromDPCashPool" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title> Expense Rollback </title>
<div class="main_container">
    <div class="content_container width900">
        <h1> Expense Rollback </h1>
        <h3> Expense Rollback Details</h3>
        <g:render template='createExpenseRollback' />
        <div class="clear height5"></div>
        <g:render template="scriptExpenseRollback" model="[disList: disList, distSize:distSize]"/>

        <div class="jqgrid-container">
            <table id="jqgrid-grid-expenseFromDPCashPool"></table>
            <div id="jqgrid-pager-expenseFromDPCashPool"></div>
        </div>

        <div id="dialog-confirm-expenseFromDPCashPool" title="Delete alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>
                <span id="cancelExpenseMsg">These item(s) will be permanently deleted and cannot be recovered. Are you sure?</span>
            </p>
        </div>

    </div>
</div>
