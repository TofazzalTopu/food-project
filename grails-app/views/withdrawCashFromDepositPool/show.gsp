<%@ page import="com.bits.bdfp.finance.WithdrawCashFromDepositPool" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Withdraw Cash From Deposit Pool</title>
<div class="main_container">
    <div class="content_container">
        <h1>Withdraw Cash From Deposit Pool</h1>
        <h3>Withdraw Cash From Deposit Pool Details</h3>

        <div style="width:100%;">
            <g:render template='/withdrawCashFromDepositPool/create' model="[result: result, list: list, recentDate:recentDate]"/>
            <br/>
            <g:render template="/withdrawCashFromDepositPool/script" model="[list: list]"/>
        </div>

        <div class="jqgrid-container">
            <table id="jqgrid-grid-withdrawCashFromDepositPool"></table>
            <div id="jqgrid-pager-withdrawCashFromDepositPool"></div>
        </div>
        <div id="dialog-confirm-withdrawCashFromDepositPool" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?</p>
        </div>

    </div>
</div>
