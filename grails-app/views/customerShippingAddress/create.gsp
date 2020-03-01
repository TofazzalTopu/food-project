

<%@ page import="com.bits.bdfp.customer.CustomerShippingAddress" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'customerShippingAddress.label', default: 'CustomerShippingAddress')}"/>
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
    <g:hasErrors bean="${customerShippingAddress}">
        <div class="errors">
            <g:renderErrors bean="${customerShippingAddress}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form action="save" >
    <div class="dialog">
        <table>
            <tbody>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="customerMaster"><g:message code="customerShippingAddress.customerMaster.label"
                                                      default="Customer Master"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: customerShippingAddress, field: 'customerMaster', 'errors')}">
                    <g:select name="customerMaster.id" from="${com.bits.bdfp.customer.CustomerMaster.list()}" optionKey="id" value="${customerShippingAddress?.customerMaster?.id}"  />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="address"><g:message code="customerShippingAddress.address.label"
                                                      default="Address"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: customerShippingAddress, field: 'address', 'errors')}">
                    <g:textField name="address" value="${customerShippingAddress?.address}" />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="isActive"><g:message code="customerShippingAddress.isActive.label"
                                                      default="Is Active"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: customerShippingAddress, field: 'isActive', 'errors')}">
                    <g:checkBox name="isActive" value="${customerShippingAddress?.isActive}" />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="userCreated"><g:message code="customerShippingAddress.userCreated.label"
                                                      default="User Created"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: customerShippingAddress, field: 'userCreated', 'errors')}">
                    <g:select name="userCreated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${customerShippingAddress?.userCreated?.id}"  />
                </td>
            </tr>
            
            <tr class="prop">
                <td valign="top" class="name">
                    <label for="userUpdated"><g:message code="customerShippingAddress.userUpdated.label"
                                                      default="User Updated"/></label>
                </td>
                <td valign="top" class="value ${hasErrors(bean: customerShippingAddress, field: 'userUpdated', 'errors')}">
                    <g:select name="userUpdated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${customerShippingAddress?.userUpdated?.id}" noSelection="['null': '']" />
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
