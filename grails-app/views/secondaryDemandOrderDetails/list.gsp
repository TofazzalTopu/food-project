
<%@ page import="com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetails" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'secondaryDemandOrderDetails.label', default: 'SecondaryDemandOrderDetails')}"/>
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
                                  title="${message(code: 'secondaryDemandOrderDetails.id.label', default: 'Id')}"/>
                
                <th><g:message code="secondaryDemandOrderDetails.secondaryDemandOrder.label" default="Secondary Demand Order"/></th>
                
                <th><g:message code="secondaryDemandOrderDetails.finishProduct.label" default="Finish Product"/></th>
                
                <g:sortableColumn property="rate"
                                  title="${message(code: 'secondaryDemandOrderDetails.rate.label', default: 'Rate')}"/>
                
                <g:sortableColumn property="quantity"
                                  title="${message(code: 'secondaryDemandOrderDetails.quantity.label', default: 'Quantity')}"/>
                
                <g:sortableColumn property="amount"
                                  title="${message(code: 'secondaryDemandOrderDetails.amount.label', default: 'Amount')}"/>
                
            </tr>
            </thead>
            <tbody>
            <g:each in="${secondaryDemandOrderDetailsList}" status="i" var="secondaryDemandOrderDetails">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    
                    <td><g:link action="show"
                                id="${secondaryDemandOrderDetails.id}">${fieldValue(bean: secondaryDemandOrderDetails, field: "id")}</g:link></td>
                    
                    <td>${fieldValue(bean: secondaryDemandOrderDetails, field: "secondaryDemandOrder")}</td>
                    
                    <td>${fieldValue(bean: secondaryDemandOrderDetails, field: "finishProduct")}</td>
                    
                    <td>${fieldValue(bean: secondaryDemandOrderDetails, field: "rate")}</td>
                    
                    <td>${fieldValue(bean: secondaryDemandOrderDetails, field: "quantity")}</td>
                    
                    <td>${fieldValue(bean: secondaryDemandOrderDetails, field: "amount")}</td>
                    
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>

    <div class="paginateButtons">
        <g:paginate total="${secondaryDemandOrderDetailsTotal}"/>
    </div>
</div>
</body>
</html>
