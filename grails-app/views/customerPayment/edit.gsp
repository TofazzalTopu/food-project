

<%@ page import="com.bits.bdfp.finance.CustomerPayment" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'customerPayment.label', default: 'CustomerPayment')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${customerPayment}">
            <div class="errors">
                <g:renderErrors bean="${customerPayment}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${customerPayment?.id}" />
                <g:hiddenField name="version" value="${customerPayment?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="customerMaster"><g:message code="customerPayment.customerMaster.label" default="Customer Master" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customerPayment, field: 'customerMaster', 'errors')}">
                                    <g:select name="customerMaster.id" from="${com.bits.bdfp.customer.CustomerMaster.list()}" optionKey="id" value="${customerPayment?.customerMaster?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="transNO"><g:message code="customerPayment.transNO.label" default="Trans NO" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customerPayment, field: 'transNO', 'errors')}">
                                    <g:textField name="transNO" value="${customerPayment?.transNO}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="paymentMode"><g:message code="customerPayment.paymentMode.label" default="Payment Mode" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customerPayment, field: 'paymentMode', 'errors')}">
                                    <g:textField name="paymentMode" value="${customerPayment?.paymentMode}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="bankPaymentMethod"><g:message code="customerPayment.bankPaymentMethod.label" default="Bank Payment Method" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customerPayment, field: 'bankPaymentMethod', 'errors')}">
                                    <g:select name="bankPaymentMethod.id" from="${com.bits.bdfp.common.BankPaymentMethod.list()}" optionKey="id" value="${customerPayment?.bankPaymentMethod?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="bankAccount"><g:message code="customerPayment.bankAccount.label" default="Bank Account" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customerPayment, field: 'bankAccount', 'errors')}">
                                    <g:select name="bankAccount.id" from="${com.bits.bdfp.common.BankAccount.list()}" optionKey="id" value="${customerPayment?.bankAccount?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="refNo"><g:message code="customerPayment.refNo.label" default="Ref No" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customerPayment, field: 'refNo', 'errors')}">
                                    <g:textField name="refNo" value="${customerPayment?.refNo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="remark"><g:message code="customerPayment.remark.label" default="Remark" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customerPayment, field: 'remark', 'errors')}">
                                    <g:textField name="remark" value="${customerPayment?.remark}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="isSecurityDeposit"><g:message code="customerPayment.isSecurityDeposit.label" default="Is Security Deposit" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customerPayment, field: 'isSecurityDeposit', 'errors')}">
                                    <g:checkBox name="isSecurityDeposit" value="${customerPayment?.isSecurityDeposit}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="amount"><g:message code="customerPayment.amount.label" default="Amount" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customerPayment, field: 'amount', 'errors')}">
                                    <g:textField name="amount" value="${fieldValue(bean: customerPayment, field: 'amount')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="confirmAmount"><g:message code="customerPayment.confirmAmount.label" default="Confirm Amount" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customerPayment, field: 'confirmAmount', 'errors')}">
                                    <g:textField name="confirmAmount" value="${fieldValue(bean: customerPayment, field: 'confirmAmount')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dateTransaction"><g:message code="customerPayment.dateTransaction.label" default="Date Transaction" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customerPayment, field: 'dateTransaction', 'errors')}">
                                    <g:datePicker name="dateTransaction" precision="day" value="${customerPayment?.dateTransaction}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="mrNo"><g:message code="customerPayment.mrNo.label" default="Mr No" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customerPayment, field: 'mrNo', 'errors')}">
                                    <g:textField name="mrNo" value="${customerPayment?.mrNo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="userCreated"><g:message code="customerPayment.userCreated.label" default="User Created" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customerPayment, field: 'userCreated', 'errors')}">
                                    <g:select name="userCreated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${customerPayment?.userCreated?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="userUpdated"><g:message code="customerPayment.userUpdated.label" default="User Updated" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customerPayment, field: 'userUpdated', 'errors')}">
                                    <g:select name="userUpdated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${customerPayment?.userUpdated?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dateUpdated"><g:message code="customerPayment.dateUpdated.label" default="Date Updated" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: customerPayment, field: 'dateUpdated', 'errors')}">
                                    <g:datePicker name="dateUpdated" precision="day" value="${customerPayment?.dateUpdated}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
