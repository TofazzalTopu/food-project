
<%@ page import="com.bits.bdfp.customer.CustomerTerritorySubArea" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'customerTerritorySubArea.label', default: 'CustomerTerritorySubArea')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label"
                                                                           args="[entityName]"/></g:link></span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label"
                                                                               args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="dialog">
        <table>
            <tbody>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="customerTerritorySubArea.id.label"
                                                         default="Id"/></td>
                
                <td valign="top" class="value">${fieldValue(bean: customerTerritorySubArea, field: "id")}</td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="customerTerritorySubArea.customerMaster.label"
                                                         default="Customer Master"/></td>
                
                <td valign="top" class="value"><g:link controller="customerMaster"
                                                       action="show"
                                                       id="${customerTerritorySubArea?.customerMaster?.id}">${customerTerritorySubArea?.customerMaster?.encodeAsHTML()}</g:link></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="customerTerritorySubArea.territorySubArea.label"
                                                         default="Territory Sub Area"/></td>
                
                <td valign="top" class="value"><g:link controller="territorySubArea"
                                                       action="show"
                                                       id="${customerTerritorySubArea?.territorySubArea?.id}">${customerTerritorySubArea?.territorySubArea?.encodeAsHTML()}</g:link></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="customerTerritorySubArea.userCreated.label"
                                                         default="User Created"/></td>
                
                <td valign="top" class="value"><g:link controller="applicationUser"
                                                       action="show"
                                                       id="${customerTerritorySubArea?.userCreated?.id}">${customerTerritorySubArea?.userCreated?.encodeAsHTML()}</g:link></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="customerTerritorySubArea.userUpdated.label"
                                                         default="User Updated"/></td>
                
                <td valign="top" class="value"><g:link controller="applicationUser"
                                                       action="show"
                                                       id="${customerTerritorySubArea?.userUpdated?.id}">${customerTerritorySubArea?.userUpdated?.encodeAsHTML()}</g:link></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="customerTerritorySubArea.dateCreated.label"
                                                         default="Date Created"/></td>
                
                <td valign="top" class="value"><g:formatDate date="${customerTerritorySubArea?.dateCreated}"/></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="customerTerritorySubArea.lastUpdated.label"
                                                         default="Last Updated"/></td>
                
                <td valign="top" class="value"><g:formatDate date="${customerTerritorySubArea?.lastUpdated}"/></td>
                
            </tr>
            
            </tbody>
        </table>
    </div>

    <div class="buttons">
        <g:form>
            <g:hiddenField name="id" value="${customerTerritorySubArea?.id}"/>
            <span class="button"><g:actionSubmit class="edit" action="edit"
                                                 value="${message(code: 'default.button.edit.label', default: 'Edit')}"/></span>
            <span class="button"><g:actionSubmit class="delete" action="delete"
                                                 value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                                 onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></span>
        </g:form>
    </div>
</div>
</body>
</html>
