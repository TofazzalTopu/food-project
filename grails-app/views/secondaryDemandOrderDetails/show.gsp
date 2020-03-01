
<%@ page import="com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetails" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'secondaryDemandOrderDetails.label', default: 'SecondaryDemandOrderDetails')}"/>
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
                <td valign="top" class="name"><g:message code="secondaryDemandOrderDetails.id.label"
                                                         default="Id"/></td>
                
                <td valign="top" class="value">${fieldValue(bean: secondaryDemandOrderDetails, field: "id")}</td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="secondaryDemandOrderDetails.secondaryDemandOrder.label"
                                                         default="Secondary Demand Order"/></td>
                
                <td valign="top" class="value"><g:link controller="secondaryDemandOrder"
                                                       action="show"
                                                       id="${secondaryDemandOrderDetails?.secondaryDemandOrder?.id}">${secondaryDemandOrderDetails?.secondaryDemandOrder?.encodeAsHTML()}</g:link></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="secondaryDemandOrderDetails.finishProduct.label"
                                                         default="Finish Product"/></td>
                
                <td valign="top" class="value"><g:link controller="finishProduct"
                                                       action="show"
                                                       id="${secondaryDemandOrderDetails?.finishProduct?.id}">${secondaryDemandOrderDetails?.finishProduct?.encodeAsHTML()}</g:link></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="secondaryDemandOrderDetails.rate.label"
                                                         default="Rate"/></td>
                
                <td valign="top" class="value">${fieldValue(bean: secondaryDemandOrderDetails, field: "rate")}</td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="secondaryDemandOrderDetails.quantity.label"
                                                         default="Quantity"/></td>
                
                <td valign="top" class="value">${fieldValue(bean: secondaryDemandOrderDetails, field: "quantity")}</td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="secondaryDemandOrderDetails.amount.label"
                                                         default="Amount"/></td>
                
                <td valign="top" class="value">${fieldValue(bean: secondaryDemandOrderDetails, field: "amount")}</td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="secondaryDemandOrderDetails.userCreated.label"
                                                         default="User Created"/></td>
                
                <td valign="top" class="value"><g:link controller="applicationUser"
                                                       action="show"
                                                       id="${secondaryDemandOrderDetails?.userCreated?.id}">${secondaryDemandOrderDetails?.userCreated?.encodeAsHTML()}</g:link></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="secondaryDemandOrderDetails.userUpdated.label"
                                                         default="User Updated"/></td>
                
                <td valign="top" class="value"><g:link controller="applicationUser"
                                                       action="show"
                                                       id="${secondaryDemandOrderDetails?.userUpdated?.id}">${secondaryDemandOrderDetails?.userUpdated?.encodeAsHTML()}</g:link></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="secondaryDemandOrderDetails.dateCreated.label"
                                                         default="Date Created"/></td>
                
                <td valign="top" class="value"><g:formatDate date="${secondaryDemandOrderDetails?.dateCreated}"/></td>
                
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name"><g:message code="secondaryDemandOrderDetails.lastUpdated.label"
                                                         default="Last Updated"/></td>
                
                <td valign="top" class="value"><g:formatDate date="${secondaryDemandOrderDetails?.lastUpdated}"/></td>
                
            </tr>
            
            </tbody>
        </table>
    </div>

    <div class="buttons">
        <g:form>
            <g:hiddenField name="id" value="${secondaryDemandOrderDetails?.id}"/>
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
