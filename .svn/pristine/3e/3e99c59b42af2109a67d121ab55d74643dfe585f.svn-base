
<%@ page import="com.bits.bdfp.bill.CreateBill" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'createBill.label', default: 'CreateBill')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'createBill.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="invoiceNumber" title="${message(code: 'createBill.invoiceNumber.label', default: 'Invoice Number')}" />
                        
                            <g:sortableColumn property="deliveryDate" title="${message(code: 'createBill.deliveryDate.label', default: 'Delivery Date')}" />
                        
                            <g:sortableColumn property="totalAmount" title="${message(code: 'createBill.totalAmount.label', default: 'Total Amount')}" />
                        
                            <th><g:message code="createBill.customerName.label" default="Customer Name" /></th>
                        
                            <th><g:message code="createBill.territory.label" default="Territory" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${createBillList}" status="i" var="createBill">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${createBill.id}">${fieldValue(bean: createBill, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: createBill, field: "invoiceNumber")}</td>
                        
                            <td><g:formatDate date="${createBill.deliveryDate}" /></td>
                        
                            <td>${fieldValue(bean: createBill, field: "totalAmount")}</td>
                        
                            <td>${fieldValue(bean: createBill, field: "customerName")}</td>
                        
                            <td>${fieldValue(bean: createBill, field: "territory")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${createBillTotal}" />
            </div>
        </div>
    </body>
</html>
