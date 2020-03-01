
<%@ page import="com.bits.bdfp.inventory.sales.DistributionPoint" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'distributionPoint.label', default: 'DistributionPoint')}"/>
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
                                  title="${message(code: 'distributionPoint.id.label', default: 'Id')}"/>
                
                <th><g:message code="distributionPoint.enterpriseConfiguration.label" default="Enterprise Configuration"/></th>
                
                <g:sortableColumn property="name"
                                  title="${message(code: 'distributionPoint.name.label', default: 'Name')}"/>
                
                <g:sortableColumn property="code"
                                  title="${message(code: 'distributionPoint.code.label', default: 'Code')}"/>
                
                <g:sortableColumn property="address"
                                  title="${message(code: 'distributionPoint.address.label', default: 'Address')}"/>
                
                <g:sortableColumn property="email"
                                  title="${message(code: 'distributionPoint.email.label', default: 'Email')}"/>
                
            </tr>
            </thead>
            <tbody>
            <g:each in="${distributionPointList}" status="i" var="distributionPoint">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    
                    <td><g:link action="show"
                                id="${distributionPoint.id}">${fieldValue(bean: distributionPoint, field: "id")}</g:link></td>
                    
                    <td>${fieldValue(bean: distributionPoint, field: "enterpriseConfiguration")}</td>
                    
                    <td>${fieldValue(bean: distributionPoint, field: "name")}</td>
                    
                    <td>${fieldValue(bean: distributionPoint, field: "code")}</td>
                    
                    <td>${fieldValue(bean: distributionPoint, field: "address")}</td>
                    
                    <td>${fieldValue(bean: distributionPoint, field: "email")}</td>
                    
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>

    <div class="paginateButtons">
        <g:paginate total="${distributionPointTotal}"/>
    </div>
</div>
</body>
</html>
