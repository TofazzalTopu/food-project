<%--
  Created by IntelliJ IDEA.
  User: mdtofazzal.hossain
  Date: 5/25/2016
  Time: 1:34 PM
--%>



<%@ page import="com.bits.bdfp.bill.CreateBill" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="createBill.create.label" default="View Bill"/></title>
<div class="main_container">
    <div class="content_container">
        <h1><g:message code="createBill.create.label" default="View Bill"/></h1>
        <h3><g:message code="createBill.Info.label" default="View Bill Details"/></h3>
        <g:render template='viewAndPrintBill'/>
        <div class="clear height5"></div>
        <g:render template="viewScript"/>

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


<div style="display:none;">
    <div style="display:none;">
        <a href="#admission-form-block" id="admission-form-slip" ></a>
    </div>
    <div style="display:none;">
        <div id="admission-form-block" style="width:400px;height:250px;">

        </div>
        <div>
        </div>
    </div>
</div>
