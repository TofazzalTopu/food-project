<%@ page import="com.bits.bdfp.accounts.ChartOfAccountsMapping" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="chartOfAccountsMapping.create.label" default="Create ChartOfAccountsMapping"/></title>

<div class="main_container" style="width: 1000px;">
    <div class="content_container" style="width: 1000px;">
        <h1><g:message code="chartOfAccountsMapping.create.label" default="Create Chart Of Accounts Mapping"/></h1>

        <h3><g:message code="chartOfAccountsMapping.Info.label" default="Chart Of Accounts Mapping Details"/></h3>
        <g:render template='create'/>
        <div class="clear height5"></div>
        <g:render template="script"/>

        <div id="dialog-confirm-chartOfAccountsMapping" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>

    </div>
</div>
