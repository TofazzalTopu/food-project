<% import grails.persistence.Event %>
<% import org.codehaus.groovy.grails.orm.hibernate.support.ClosureEventTriggeringInterceptor as Events %>
<%=packageName%>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="${propertyName}.create.label" default="Create ${className}"/></title>
<div class="main_container">
    <div class="content_container">
        <h1><g:message code="${propertyName}.create.label" default="Create ${domainClass.naturalName}"/></h1>
        <h3><g:message code="${propertyName}.Info.label" default="${domainClass.naturalName} Details"/></h3>
        <g:render template='create'/>
        <div class="clear height5"></div>
        <g:render template="script"/>

        <div class="jqgrid-container">
            <table id="jqgrid-grid-${propertyName}"></table>
            <div id="jqgrid-pager-${propertyName}"></div>
        </div>
        <div id="dialog-confirm-${propertyName}" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?</p>
        </div>

    </div>
</div>
