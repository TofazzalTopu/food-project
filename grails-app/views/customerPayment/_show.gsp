<%@ page import="com.bits.bdfp.finance.CustomerPayment" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Register Payment</title>

<div class="main_container">
    <div class="content_container">
        <h1>Register Payment</h1>

        <h3>Register Payment Details</h3>

        <div style="width:100%;">
            <g:render template='create'  model="[result:result, currentDate: currentDate, list:list,applicationUser:applicationUser,
                                                 isFactory: isFactory]"/>
            <br/>
            <g:render template="script" model="[isFactory: isFactory]"/>
        </div>


        <div id="dialog-confirm-territoryConfiguration" title="Delete alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>
    </div>
</div>
