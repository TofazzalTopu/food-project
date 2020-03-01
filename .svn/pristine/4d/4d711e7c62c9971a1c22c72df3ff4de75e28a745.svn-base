

<%@ page import="com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetails" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'secondaryDemandOrderDetails.label', default: 'SecondaryDemandOrderDetails')}"/>
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
    <g:hasErrors bean="${secondaryDemandOrderDetails}">
        <div class="errors">
            <g:renderErrors bean="${secondaryDemandOrderDetails}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form action="save" >
    <div class="dialog">
        <table>
            <tbody>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="secondaryDemandOrder"><g:message code="secondaryDemandOrderDetails.secondaryDemandOrder.label"
                                                      default="Secondary Demand Order"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: secondaryDemandOrderDetails, field: 'secondaryDemandOrder', 'errors')}">
                    <g:select name="secondaryDemandOrder.id" from="${com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder.list()}" optionKey="id" value="${secondaryDemandOrderDetails?.secondaryDemandOrder?.id}"  />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="finishProduct"><g:message code="secondaryDemandOrderDetails.finishProduct.label"
                                                      default="Finish Product"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: secondaryDemandOrderDetails, field: 'finishProduct', 'errors')}">
                    <g:select name="finishProduct.id" from="${com.bits.bdfp.inventory.product.FinishProduct.list()}" optionKey="id" value="${secondaryDemandOrderDetails?.finishProduct?.id}"  />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="rate"><g:message code="secondaryDemandOrderDetails.rate.label"
                                                      default="Rate"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: secondaryDemandOrderDetails, field: 'rate', 'errors')}">
                    <g:textField name="rate" value="${fieldValue(bean: secondaryDemandOrderDetails, field: 'rate')}" />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="quantity"><g:message code="secondaryDemandOrderDetails.quantity.label"
                                                      default="Quantity"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: secondaryDemandOrderDetails, field: 'quantity', 'errors')}">
                    <g:textField name="quantity" value="${fieldValue(bean: secondaryDemandOrderDetails, field: 'quantity')}" />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="amount"><g:message code="secondaryDemandOrderDetails.amount.label"
                                                      default="Amount"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: secondaryDemandOrderDetails, field: 'amount', 'errors')}">
                    <g:textField name="amount" value="${fieldValue(bean: secondaryDemandOrderDetails, field: 'amount')}" />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="userCreated"><g:message code="secondaryDemandOrderDetails.userCreated.label"
                                                      default="User Created"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: secondaryDemandOrderDetails, field: 'userCreated', 'errors')}">
                    <g:select name="userCreated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${secondaryDemandOrderDetails?.userCreated?.id}"  />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="userUpdated"><g:message code="secondaryDemandOrderDetails.userUpdated.label"
                                                      default="User Updated"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: secondaryDemandOrderDetails, field: 'userUpdated', 'errors')}">
                    <g:select name="userUpdated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${secondaryDemandOrderDetails?.userUpdated?.id}" noSelection="['null': '']" />
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
