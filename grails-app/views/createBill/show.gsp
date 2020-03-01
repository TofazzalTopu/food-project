
<%@ page import="com.bits.bdfp.bill.CreateBill" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Create Bill</title>
<div class="main_container">
    <div class="content_container">
        <h1>Create Bill</h1>
        <h3>Bill Details</h3>
        <g:render template='create'/>
        <div class="clear height5"></div>
        <g:render template="script"/>

        <div class="jqgrid-container">
            <table id="jqgrid-grid-createBill"></table>
            <div id="jqgrid-pager-createBill"></div>
        </div>
        <div id="dialog-confirm-createBill" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?</p>
        </div>

       %{-- <div class="jqgrid-container">
            <table id="jqgrid-grid-unadjusted-invoice"></table>

            <div id="jqgrid-pager-unadjusted-invoice"></div>
        </div>--}%

    </div>
</div>
