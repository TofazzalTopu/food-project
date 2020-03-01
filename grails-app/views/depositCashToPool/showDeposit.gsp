<%@ page import="com.bits.bdfp.common.DepositPool" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Deposit Cash To Deposit Pool</title>

<div class="main_container">
    <div class="content_container">
        <h1>Deposit Cash To Deposit Pool</h1>

        <h3>Deposit Cash To Deposit Pool</h3>

        <div style="width:100%;">
            <g:render template='/depositCashToPool/createDeposit' model="[result: result, list: list,recentDate:recentDate]"/>
            <br/>
            <g:render template="/depositCashToPool/scriptDeposit" model="[list: list]"/>
        </div>

        <div id="dialog-confirm-depositPool" title="Delete alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>
    </div>
</div>






