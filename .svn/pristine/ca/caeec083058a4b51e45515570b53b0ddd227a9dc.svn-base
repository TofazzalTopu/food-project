

<%@ page import="com.bits.bdfp.inventory.setup.VatRate" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'vatRate.label', default: 'VatRate')}" />
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
            <g:hasErrors bean="${vatRate}">
            <div class="errors">
                <g:renderErrors bean="${vatRate}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="finishProduct"><g:message code="vatRate.finishProduct.label" default="Finish Product" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: vatRate, field: 'finishProduct', 'errors')}">
                                    <g:select name="finishProduct.id" id="finishProduct" from="${com.bits.bdfp.inventory.product.FinishProduct.list()}" optionKey="id" value="${vatRate?.finishProduct?.id}"  />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="rate"><g:message code="vatRate.rate.label" default="Rate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: vatRate, field: 'rate', 'errors')}">
                                    <g:textField name="rate" maxlength="20" size="20" value="${fieldValue(bean: vatRate, field: 'rate')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="supplementaryDuty"><g:message code="vatRate.supplementaryDuty.label" default="Supplementary Duty" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: vatRate, field: 'supplementaryDuty', 'errors')}">
                                    <g:textField name="supplementaryDuty" maxlength="20" size="20" value="${fieldValue(bean: vatRate, field: 'supplementaryDuty')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="vat"><g:message code="vatRate.vat.label" default="Vat" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: vatRate, field: 'vat', 'errors')}">
                                    <g:textField  name="vat" maxlength="20" size="20" value="${fieldValue(bean: vatRate, field: 'vat')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="effectiveFromDate"><g:message code="vatRate.effectiveFromDate.label" default="Effective From Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: vatRate, field: 'effectiveFromDate', 'errors')}">
                                    <g:textField class="validate[required] text-input datepicker"  name="effectiveFromDate" id="effectiveFromDate" value="${vatRate?.effectiveFromDate}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="effectiveToDate"><g:message code="vatRate.effectiveToDate.label" default="Effective To Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: vatRate, field: 'effectiveToDate', 'errors')}">
                                    <g:textField class="validate[required] text-input datepicker"  name="effectiveToDate" id="effectiveToDate" value="${vatRate?.effectiveToDate}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="isActive"><g:message code="vatRate.isActive.label" default="Is Active" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: vatRate, field: 'isActive', 'errors')}">
                                    <g:checkBox name="isActive" value="${vatRate?.isActive}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="userCreated"><g:message code="vatRate.userCreated.label" default="User Created" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: vatRate, field: 'userCreated', 'errors')}">
                                    <g:select name="userCreated.id" id="userCreated" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${vatRate?.userCreated?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="userUpdated"><g:message code="vatRate.userUpdated.label" default="User Updated" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: vatRate, field: 'userUpdated', 'errors')}">
                                    <g:select name="userUpdated.id" id="userUpdated" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${vatRate?.userUpdated?.id}" noSelection="['null': '']" />
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
