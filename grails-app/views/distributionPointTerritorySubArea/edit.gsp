

<%@ page import="com.bits.bdfp.inventory.sales.DistributionPointTerritorySubArea" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'distributionPointTerritorySubArea.label', default: 'DistributionPointTerritorySubArea')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
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
    <h1><g:message code="default.edit.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${distributionPointTerritorySubArea}">
        <div class="errors">
            <g:renderErrors bean="${distributionPointTerritorySubArea}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form method="post" >
    <g:hiddenField name="id" value="${distributionPointTerritorySubArea?.id}"/>
    <g:hiddenField name="version" value="${distributionPointTerritorySubArea?.version}"/>
    <div class="dialog">
        <table>
            <tbody>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="distributionPoint"><g:message code="distributionPointTerritorySubArea.distributionPoint.label"
                                                      default="Distribution Point"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: distributionPointTerritorySubArea, field: 'distributionPoint', 'errors')}">
                    <g:select name="distributionPoint.id" from="${com.bits.bdfp.inventory.sales.DistributionPoint.list()}" optionKey="id" value="${distributionPointTerritorySubArea?.distributionPoint?.id}"  />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="territorySubArea"><g:message code="distributionPointTerritorySubArea.territorySubArea.label"
                                                      default="Territory Sub Area"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: distributionPointTerritorySubArea, field: 'territorySubArea', 'errors')}">
                    <g:select name="territorySubArea.id" from="${com.bits.bdfp.geolocation.TerritorySubArea.list()}" optionKey="id" value="${distributionPointTerritorySubArea?.territorySubArea?.id}"  />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="userCreated"><g:message code="distributionPointTerritorySubArea.userCreated.label"
                                                      default="User Created"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: distributionPointTerritorySubArea, field: 'userCreated', 'errors')}">
                    <g:select name="userCreated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${distributionPointTerritorySubArea?.userCreated?.id}"  />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="userUpdated"><g:message code="distributionPointTerritorySubArea.userUpdated.label"
                                                      default="User Updated"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: distributionPointTerritorySubArea, field: 'userUpdated', 'errors')}">
                    <g:select name="userUpdated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${distributionPointTerritorySubArea?.userUpdated?.id}" noSelection="['null': '']" />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="dateUpdated"><g:message code="distributionPointTerritorySubArea.dateUpdated.label"
                                                      default="Date Updated"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: distributionPointTerritorySubArea, field: 'dateUpdated', 'errors')}">
                    <g:datePicker name="dateUpdated" precision="day" value="${distributionPointTerritorySubArea?.dateUpdated}" default="none" noSelection="['': '']" />
                </td>
            </tr>
            
            </tbody>
        </table>
    </div>

    <div class="buttons">
        <span class="button"><g:actionSubmit class="save" action="update"
                                             value="${message(code: 'default.button.update.label', default: 'Update')}"/></span>
        <span class="button"><g:actionSubmit class="delete" action="delete"
                                             value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                             onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></span>
    </div>
    </g:form>
</div>
</body>
</html>
