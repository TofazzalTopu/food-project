

<%@ page import="com.bits.bdfp.bonus.BonusCriteriaSetup" %>

<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="bonusCriteriaSetup.create.label" default="Bonus Criteria Setup "/></title>

<div class="main_container">
  <div class="content_container">
    <h1><g:message code="bonusCriteriaSetup.create.label" default="Bonus Criteria Setup"/></h1>

    <h3><g:message code="bonusCriteriaSetup.info.label" default="Bonus Criteria Info"/></h3>

    <div style="width:100%;">
      <g:render template='create' />
      <br/>
      <g:render template="script"/>
    </div>

    <div class="jqgrid-container">
      <table id="jqgrid-grid"></table>

      <div id="jqgrid-pager"></div>
    </div>

    <div id="dialog-confirm-bonusCriteriaSetup" title="Delete alert" style="display: none">
      <p><span class="ui-icon ui-icon-alert"
               style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
      </p>
    </div>
  </div>
</div>

