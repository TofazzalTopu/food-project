<%@ page import="com.bits.bdfp.inventory.setup.SalesCommission" %>

<meta name="layout" content="docuThemeRollerLayout"/>
<title>View Commission for Branch & Sales Man</title>

<div class="main_container">
  <div class="content_container">
    <h1>View Commission for Branch & Sales Man</h1>

    <h3>View Commission for Branch & Sales Man Details</h3>

    <div style="width:100%;">
      <g:render template='viewCommission'/>
      <g:render template="scriptCommission" model="[territoryList: territoryList, territoryListSize:territoryListSize, dpList:dpList, dpSize:dpSize]"/>
    </div>

    <div id="dialog-confirm-vatType" title="Delete alert" style="display: none">
      <p><span class="ui-icon ui-icon-alert"
               style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
      </p>
    </div>
  </div>
</div>

