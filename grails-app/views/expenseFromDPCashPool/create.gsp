

<%@ page import="com.bits.bdfp.finance.ExpenseFromDPCashPool" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'expenseFromDPCashPool.label', default: 'ExpenseFromDPCashPool')}" />
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
            <g:hasErrors bean="${expenseFromDPCashPool}">
            <div class="errors">
                <g:renderErrors bean="${expenseFromDPCashPool}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="distributionPoint"><g:message code="expenseFromDPCashPool.distributionPoint.label" default="Distribution Point" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: expenseFromDPCashPool, field: 'distributionPoint', 'errors')}">
                                    <g:select name="distributionPoint.id" id="distributionPoint" from="${com.bits.bdfp.inventory.sales.DistributionPoint.list()}" optionKey="id" value="${expenseFromDPCashPool?.distributionPoint?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="cashPool"><g:message code="expenseFromDPCashPool.cashPool.label" default="Cash Pool" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: expenseFromDPCashPool, field: 'cashPool', 'errors')}">
                                    <g:select name="cashPool.id" id="cashPool" from="${com.bits.bdfp.common.CashPool.list()}" optionKey="id" value="${expenseFromDPCashPool?.cashPool?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="expenseAmount"><g:message code="expenseFromDPCashPool.expenseAmount.label" default="Expense Amount" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: expenseFromDPCashPool, field: 'expenseAmount', 'errors')}">
                                    <g:textField name="expenseAmount" value="${fieldValue(bean: expenseFromDPCashPool, field: 'expenseAmount')}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="remarks"><g:message code="expenseFromDPCashPool.remarks.label" default="Remarks" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: expenseFromDPCashPool, field: 'remarks', 'errors')}">
                                    <g:textField name="remarks" value="${expenseFromDPCashPool?.remarks}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="isActive"><g:message code="expenseFromDPCashPool.isActive.label" default="Is Active" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: expenseFromDPCashPool, field: 'isActive', 'errors')}">
                                    <g:checkBox name="isActive" value="${expenseFromDPCashPool?.isActive}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="userCreated"><g:message code="expenseFromDPCashPool.userCreated.label" default="User Created" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: expenseFromDPCashPool, field: 'userCreated', 'errors')}">
                                    <g:select name="userCreated.id" id="userCreated" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${expenseFromDPCashPool?.userCreated?.id}"  />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="userUpdated"><g:message code="expenseFromDPCashPool.userUpdated.label" default="User Updated" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: expenseFromDPCashPool, field: 'userUpdated', 'errors')}">
                                    <g:select name="userUpdated.id" id="userUpdated" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${expenseFromDPCashPool?.userUpdated?.id}" noSelection="['null': '']" />
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
