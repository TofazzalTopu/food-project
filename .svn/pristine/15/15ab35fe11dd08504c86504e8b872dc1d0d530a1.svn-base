
<%@ page import="com.bits.bdfp.inventory.setup.VatRate" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'vatRate.label', default: 'VatRate')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'vatRate.id.label', default: 'Id')}" />
                        
                            <th><g:message code="vatRate.finishProduct.label" default="Finish Product" /></th>
                        
                            <g:sortableColumn property="rate" title="${message(code: 'vatRate.rate.label', default: 'Rate')}" />
                        
                            <g:sortableColumn property="supplementaryDuty" title="${message(code: 'vatRate.supplementaryDuty.label', default: 'Supplementary Duty')}" />
                        
                            <g:sortableColumn property="vat" title="${message(code: 'vatRate.vat.label', default: 'Vat')}" />
                        
                            <g:sortableColumn property="effectiveFromDate" title="${message(code: 'vatRate.effectiveFromDate.label', default: 'Effective From Date')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${vatRateList}" status="i" var="vatRate">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${vatRate.id}">${fieldValue(bean: vatRate, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: vatRate, field: "finishProduct")}</td>
                        
                            <td>${fieldValue(bean: vatRate, field: "rate")}</td>
                        
                            <td>${fieldValue(bean: vatRate, field: "supplementaryDuty")}</td>
                        
                            <td>${fieldValue(bean: vatRate, field: "vat")}</td>
                        
                            <td><g:formatDate date="${vatRate.effectiveFromDate}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${vatRateTotal}" />
            </div>
        </div>
    </body>
</html>
