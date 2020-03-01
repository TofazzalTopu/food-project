

<%@ page import="com.bits.bdfp.inventory.setup.VatType" %>

<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="vatType.create.label" default="Vat Type "/></title>

<div class="main_container">
  <div class="content_container">
    <h1><g:message code="vatType.create.label" default="Vat Type"/></h1>

    <h3><g:message code="vatType.info.label" default="Vat Type Info"/></h3>

    <div style="width:100%;">
      <g:render template='create' model="[list:list,result:result]"/>
      <br/>
      <g:render template="script"/>
    </div>

    <div class="jqgrid-container">
      <table id="jqgrid-grid"></table>

      <div id="jqgrid-pager"></div>
    </div>

    <div id="dialog-confirm-vatType" title="Delete alert" style="display: none">
      <p><span class="ui-icon ui-icon-alert"
               style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
      </p>
    </div>
  </div>
</div>

