

<%@ page import="com.bits.bdfp.inventory.product.ProductCategory" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="productCategory.create.label" default="Product Category"/></title>
<div class="main_container">
  <div class="content_container">
    <h1><g:message code="productCategory.create.label" default="Product Category"/></h1>
    <h3><g:message code="productCategory.Info.label" default="Product Category Information"/></h3>
    <g:render template='create'  model="[list:list,result:result]"/>
    <div class="clear height5"></div>
    <g:render template="script"/>

    <div class="jqgrid-container">
      <table id="jqgrid-grid"></table>

      <div id="jqgrid-pager"></div>
    </div>

    <div id="dialog-confirm-productCategory" title="Delete alert" style="display: none">
      <p><span class="ui-icon ui-icon-alert"
               style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
      </p>
    </div>

  </div>
</div>
