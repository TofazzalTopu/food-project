

<%@ page import="com.bits.bdfp.inventory.setup.SalesCommission" %>

<meta name="layout" content="docuThemeRollerLayout"/>
<title>Sales Commission</title>

<div class="main_container">
  <div class="content_container">
    <h1>Setup Sales Commission for Branch/Sales Man</h1>

    <h3>Sales Commission Info</h3>

    <div style="width:100%;">
      <g:render template='create'/>
      <g:render template="script"/>
    </div>

    <div id="dialog-confirm-vatType" title="Delete alert" style="display: none">
      <p><span class="ui-icon ui-icon-alert"
               style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
      </p>
    </div>
  </div>
</div>

