

<%@ page import="com.bits.bdfp.bill.CreateBill" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'createBill.label', default: 'CreateBill')}" />
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
            <g:hasErrors bean="${createBill}">
            <div class="errors">
                <g:renderErrors bean="${createBill}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${createBill?.id}" />
                <g:hiddenField name="version" value="${createBill?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="invoiceNumber"><g:message code="createBill.invoiceNumber.label" default="Invoice Number" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: createBill, field: 'invoiceNumber', 'errors')}">
                                    <g:textField name="invoiceNumber" value="${createBill?.invoiceNumber}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="deliveryDate"><g:message code="createBill.deliveryDate.label" default="Delivery Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: createBill, field: 'deliveryDate', 'errors')}">
                                    <g:textField name="deliveryDate" id="deliveryDate" value="${createBill?.deliveryDate}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="totalAmount"><g:message code="createBill.totalAmount.label" default="Total Amount" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: createBill, field: 'totalAmount', 'errors')}">
                                    <g:textField name="totalAmount" value="${fieldValue(bean: createBill, field: 'totalAmount')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="customerName"><g:message code="createBill.customerName.label" default="Customer Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: createBill, field: 'customerName', 'errors')}">
                                    <g:select name="customerName.id" id="customerName" from="${com.bits.bdfp.customer.CustomerMaster.list()}" optionKey="id" value="${createBill?.customerName?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="territory"><g:message code="createBill.territory.label" default="Territory" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: createBill, field: 'territory', 'errors')}">
                                    <g:select name="territory.id" id="territory" from="${com.bits.bdfp.geolocation.TerritoryConfiguration.list()}" optionKey="id" value="${createBill?.territory?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="territoryGeoLocation"><g:message code="createBill.territoryGeoLocation.label" default="Territory Geo Location" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: createBill, field: 'territoryGeoLocation', 'errors')}">
                                    <g:select name="territoryGeoLocation.id" id="territoryGeoLocation" from="${com.bits.bdfp.geolocation.TerritorySubArea.list()}" optionKey="id" value="${createBill?.territoryGeoLocation?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="customerId"><g:message code="createBill.customerId.label" default="Customer Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: createBill, field: 'customerId', 'errors')}">
                                    <g:textField name="customerId" value="${fieldValue(bean: createBill, field: 'customerId')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="billNumber"><g:message code="createBill.billNumber.label" default="Bill Number" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: createBill, field: 'billNumber', 'errors')}">
                                    <g:textField name="billNumber" value="${createBill?.billNumber}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="billGenerationDate"><g:message code="createBill.billGenerationDate.label" default="Bill Generation Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: createBill, field: 'billGenerationDate', 'errors')}">
                                    <g:textField name="billGenerationDate" id="billGenerationDate" value="${createBill?.billGenerationDate}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="purchaseOrderNumber"><g:message code="createBill.purchaseOrderNumber.label" default="Purchase Order Number" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: createBill, field: 'purchaseOrderNumber', 'errors')}">
                                    <g:textField name="purchaseOrderNumber" value="${createBill?.purchaseOrderNumber}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="purchaseOrderDate"><g:message code="createBill.purchaseOrderDate.label" default="Purchase Order Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: createBill, field: 'purchaseOrderDate', 'errors')}">
                                    <g:textField name="purchaseOrderDate" id="purchaseOrderDate" value="${createBill?.purchaseOrderDate}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="vatChallanNumber"><g:message code="createBill.vatChallanNumber.label" default="Vat Challan Number" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: createBill, field: 'vatChallanNumber', 'errors')}">
                                    <g:textField name="vatChallanNumber" value="${createBill?.vatChallanNumber}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="vatChallanDate"><g:message code="createBill.vatChallanDate.label" default="Vat Challan Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: createBill, field: 'vatChallanDate', 'errors')}">
                                    <g:textField name="vatChallanDate" id="vatChallanDate" value="${createBill?.vatChallanDate}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="customerCategory"><g:message code="createBill.customerCategory.label" default="Customer Category" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: createBill, field: 'customerCategory', 'errors')}">
                                    <g:select name="customerCategory.id" id="customerCategory" from="${com.bits.bdfp.customer.CustomerCategory.list()}" optionKey="id" value="${createBill?.customerCategory?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="receivableAmount"><g:message code="createBill.receivableAmount.label" default="Receivable Amount" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: createBill, field: 'receivableAmount', 'errors')}">
                                    <g:textField name="receivableAmount" value="${fieldValue(bean: createBill, field: 'receivableAmount')}" />
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
