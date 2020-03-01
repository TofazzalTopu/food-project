<%@ page import="com.bits.bdfp.finance.CustomerPayment" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Print Money Receipt</title>
<div class="main_container width1000">
    <div class="content_container">
        <h1 class="width980">Print Money Receipt</h1>
        <h3>Print Money Receipt Info</h3>

        <div style="width:100%;">
            <g:render template='createReceipt'  model="[enterpriseList: list]"/>
            <br/>
            <g:render template="scriptReceipt"/>
        </div>


        <div id="dialog-confirm-cancelPayment" title="Delete alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>
                <span id="cancelPaymentMsg">These item(s) will be permanently deleted and cannot be recovered. Are you sure?</span>
            </p>
        </div>
    </div>
</div>
