<%--
  Created by IntelliJ IDEA.
  User: mdtofazzal.hossain
  Date: 5/29/2016
  Time: 5:11 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="com.bits.bdfp.bill.CreateBill" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="createBill.create.label" default="Edit Bill"/></title>
<div class="main_container">
    <div class="content_container">
        <h1><g:message code="createBill.create.label" default="Edit Bill"/></h1>
        <h3><g:message code="createBill.Info.label" default="View Bill Details"/></h3>
        <g:render template='UpdateBill'/>
        <div class="clear height5"></div>
        <g:render template="scriptUpdate"/>

        <div class="jqgrid-container">
            <table id="jqgrid-grid-createBill"></table>
            <div id="jqgrid-pager-createBill"></div>
        </div>
        <div id="dialog-confirm-createBill" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?</p>
        </div>

    </div>
</div>


<div style="display:none">
    <div id="admission-fee-slip-block1" style="width:400px;height:350px;">

    </div>
    <div style="display:none;">
        <a href="#admission-form-block1" id="admission-form-slip" ></a>
    </div>
    <div style="display:none">
        <div id="admission-form-block1" style="width:400px;height:250px;">

        </div>
        <div>
        </div>
    </div>
</div>

