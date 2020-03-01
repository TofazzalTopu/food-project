
<%@ page import="com.bits.bdfp.finance.CustomerPayment" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'customerPayment.label', default: 'CustomerPayment')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'customerPayment.id.label', default: 'Id')}" />
                        
                            <th><g:message code="customerPayment.customerMaster.label" default="Customer Master" /></th>
                        
                            <g:sortableColumn property="transNO" title="${message(code: 'customerPayment.transNO.label', default: 'Trans NO')}" />
                        
                            <g:sortableColumn property="paymentMode" title="${message(code: 'customerPayment.paymentMode.label', default: 'Payment Mode')}" />
                        
                            <th><g:message code="customerPayment.bankPaymentMethod.label" default="Bank Payment Method" /></th>
                        
                            <th><g:message code="customerPayment.bankAccount.label" default="Bank Account" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${customerPaymentList}" status="i" var="customerPayment">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${customerPayment.id}">${fieldValue(bean: customerPayment, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: customerPayment, field: "customerMaster")}</td>
                        
                            <td>${fieldValue(bean: customerPayment, field: "transNO")}</td>
                        
                            <td>${fieldValue(bean: customerPayment, field: "paymentMode")}</td>
                        
                            <td>${fieldValue(bean: customerPayment, field: "bankPaymentMethod")}</td>
                        
                            <td>${fieldValue(bean: customerPayment, field: "bankAccount")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${customerPaymentTotal}" />
            </div>
        </div>
    </body>
</html>
