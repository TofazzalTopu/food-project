<%@ page import="com.docu.commons.Relationship" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="relationship.create.label" default="Create Relationship"/></title>

<div class="main_container">
    <div class="content_container">
        <h1><g:message code="relationship.create.label" default="Create Relationship"/></h1>

        <h3><g:message code="relationship.domainInfo.label" default="Relationship"/></h3>
        <g:render template='create'/>
        <div class="clear" style="height:2px; overflow:hidden; font-size:0pt;"></div>
        <g:render template="script"/>

        <div class="jqgrid-container blue_grid">
            <table id="jqgrid-grid-relationship"></table>

            <div id="jqgrid-pager-relationship"></div>
        </div>

        <div id="dialog-confirm-relationship" title="Delete alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>

    </div>
</div>
