
<%@ page import="com.bits.bdfp.customer.CustomerShippingAddress" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'customerShippingAddress.label', default: 'CustomerShippingAddress')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </span>
    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label"
                                                                               args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <div class="list">
        <table>
            <thead>
            <tr>
                
                <g:sortableColumn property="id"
                                  title="${message(code: 'customerShippingAddress.id.label', default: 'Id')}"/>
                
                <th><g:message code="customerShippingAddress.customerMaster.label" default="Customer Master"/></th>
                
                <g:sortableColumn property="address"
                                  title="${message(code: 'customerShippingAddress.address.label', default: 'Address')}"/>
                
                <g:sortableColumn property="isActive"
                                  title="${message(code: 'customerShippingAddress.isActive.label', default: 'Is Active')}"/>
                
                <th><g:message code="customerShippingAddress.userCreated.label" default="User Created"/></th>
                
                <th><g:message code="customerShippingAddress.userUpdated.label" default="User Updated"/></th>
                
            </tr>
            </thead>
            <tbody>
            <g:each in="${customerShippingAddressList}" status="i" var="customerShippingAddress">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    
                    <td><g:link action="show"
                                id="${customerShippingAddress.id}">${fieldValue(bean: customerShippingAddress, field: "id")}</g:link></td>
                    
                    <td>${fieldValue(bean: customerShippingAddress, field: "customerMaster")}</td>
                    
                    <td>${fieldValue(bean: customerShippingAddress, field: "address")}</td>
                    
                    <td><g:formatBoolean boolean="${customerShippingAddress.isActive}"/></td>
                    
                    <td>${fieldValue(bean: customerShippingAddress, field: "userCreated")}</td>
                    
                    <td>${fieldValue(bean: customerShippingAddress, field: "userUpdated")}</td>
                    
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>

    <div class="paginateButtons">
        <g:paginate total="${customerShippingAddressTotal}"/>
    </div>
</div>
</body>
</html>
