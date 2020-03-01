

<%@ page import="com.bits.bdfp.customer.CustomerEligibilityMaster" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'customerEligibilityMaster.label', default: 'CustomerEligibilityMaster')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${customerEligibilityMaster}">
            <div class="errors">
                <g:renderErrors bean="${customerEligibilityMaster}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="eligibilityTemplate"><g:message code="customerEligibilityMaster.eligibilityTemplate.label" default="Eligibility Template" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customerEligibilityMaster, field: 'eligibilityTemplate', 'errors')}">
                                    <g:select name="eligibilityTemplate.id" id="eligibilityTemplate" from="${com.bits.bdfp.customer.EligibilityTemplate.list()}" optionKey="id" value="${customerEligibilityMaster?.eligibilityTemplate?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="amount"><g:message code="customerEligibilityMaster.amount.label" default="Amount" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customerEligibilityMaster, field: 'amount', 'errors')}">
                                    <g:textField name="amount" value="${fieldValue(bean: customerEligibilityMaster, field: 'amount')}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
