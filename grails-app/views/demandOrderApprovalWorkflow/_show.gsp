

<%@ page import="com.bits.bdfp.inventory.demandorder.DemandOrderApprovalWorkflow" %>
<div style="width:100%;">
<g:render template='create'/>
<br/>
<g:render template="script"/>
</div>
<div class="jqgrid-container">
  <table id="jqgrid-grid"></table>
  <div id="jqgrid-pager"></div>
</div>
<div id="dialog-confirm-demandOrderApprovalWorkflow" title="Delete alert" style="display: none">
  <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?</p>
</div>
