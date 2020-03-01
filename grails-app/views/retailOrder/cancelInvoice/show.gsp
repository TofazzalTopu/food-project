<%--
  Created by IntelliJ IDEA.
  User: mdalinaser.khan
  Date: 2/7/16
  Time: 4:53 PM
--%>

<%@ page import="com.bits.bdfp.inventory.retailorder.RetailOrder" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Cancel Retail Invoice</title>

<div class="main_container">
    <div class="content_container">
        <h1>Cancel Retail Invoice</h1>
        <h3>Cancel Retail Invoice</h3>
        <g:render template='/retailOrder/cancelInvoice/create'/>
        <div class="clear height5"></div>
        <g:render template="/retailOrder/cancelInvoice/script"/>

        <div id="dialog-confirm-retailInvoice" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>Selected Invoice(s) will be permanently canceled and cannot be recovered. Are you sure?
            </p>
        </div>
    </div>
</div>