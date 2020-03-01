

<%@ page import="com.bits.bdfp.finance.CustomerPayment" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Withdraw Security Deposit</title>

<div class="main_container">
    <div class="content_container">
        <h1>Withdraw Security Deposit</h1>

        <h3>Withdraw Security Deposit Info</h3>

        <div style="width:100%;">
            <g:render template='create'  model="[result:result, enterpriseList:enterpriseList,applicationUser:applicationUser]"/>
            <br/>
            <g:render template="script"/>
        </div>


        <div id="dialog-confirm-territoryConfiguration" title="Delete alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>
    </div>
</div>
