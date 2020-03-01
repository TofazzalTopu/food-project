

<%@ page import="com.bits.bdfp.inventory.sales.DistributionPointWarehouse" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'distributionPointWarehouse.label', default: 'DistributionPointWarehouse')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label"
                                                                           args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1><g:message code="default.create.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${distributionPointWarehouse}">
        <div class="errors">
            <g:renderErrors bean="${distributionPointWarehouse}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form action="save" >
    <div class="dialog">
        <table>
            <tbody>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="distributionPoint"><g:message code="distributionPointWarehouse.distributionPoint.label"
                                                      default="Distribution Point"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: distributionPointWarehouse, field: 'distributionPoint', 'errors')}">
                    <g:select name="distributionPoint.id" from="${com.bits.bdfp.inventory.sales.DistributionPoint.list()}" optionKey="id" value="${distributionPointWarehouse?.distributionPoint?.id}"  />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="warehouse"><g:message code="distributionPointWarehouse.warehouse.label"
                                                      default="Warehouse"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: distributionPointWarehouse, field: 'warehouse', 'errors')}">
                    <g:select name="warehouse.id" from="${com.bits.bdfp.inventory.warehouse.Warehouse.list()}" optionKey="id" value="${distributionPointWarehouse?.warehouse?.id}"  />
                </td>
            </tr>
            
            </tbody>
        </table>
    </div>

    <div class="buttons">
        <span class="button"><g:submitButton name="create" class="save"
                                             value="${message(code: 'default.button.create.label', default: 'Create')}"/></span>
    </div>
    </g:form>
</div>
</body>
</html>
