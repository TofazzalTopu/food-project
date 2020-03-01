<%@ page import="com.bits.bdfp.inventory.setup.Quotation" %>
<div id="spinnerQuotation" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>

<form name='gFormQuotation' id='gFormQuotation'>
    <g:hiddenField name="id" value="${quotation?.id}"/>
    <g:hiddenField name="version" value="${quotation?.version}"/>
    <div id="remote-content-quotation"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr>
                <td>
                    <label for="quotationDate" class="txtright bold hight1x width1x">
                        <g:message code='mushak.invoice.label' default='Date'/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textField name="quotationDate" id="quotationDate" style="text-align: center;"
                                 class="validate[required] text-input datepicker width193"
                                 value="${quotation?.quotationDate?.format('dd-MM-yyyy')}"/>
                </td>
                <td>
                    <label for="quotationDate" class="txtright bold hight1x width1x">
                        <g:message code='quotation.isActive.label' default='Is Active'/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:checkBox name="isActive" id="isActive"
                                value="${quotation?.isActive}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="quotationDate" class="txtright bold hight1x width1x">
                        <g:message code='quotation.refNo.label' default='Ref No'/>
                    </label>
                </td>
                <td>
                    <g:textField name="refNo" value="${quotation?.refNo}" class="width193"/>
                </td>
                <td>
                    <label for="quotationDate" class="txtright bold hight1x width1x">
                        <g:message code='quotation.refDate.label' default='Ref Date'/>
                    </label>
                </td>
                <td>
                    <g:textField name="refDate" id="refDate" class="width193" style="text-align: center;"
                                 value="${quotation?.refDate?.format('dd-MM-yyyy')}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="quotationDate" class="txtright bold hight1x width1x">
                        <g:message code='quotation.validFrom.label' default='Valid From'/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textField class="validate[required] text-input datepicker width193" style="text-align: center;"
                                 name="validFrom" id="validFrom"
                                 value="${quotation?.validFrom?.format('dd-MM-yyyy')}"/>
                </td>
                <td>
                    <label for="quotationDate" class="txtright bold hight1x width1x">
                        <g:message code='quotation.validTo.label' default='Valid To'/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textField class="validate[required] text-input datepicker width193" style="text-align: center;"
                                 name="validTo" id="validTo"
                                 value="${quotation?.validTo?.format('dd-MM-yyyy')}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="quotationDate" class="txtright bold hight1x width1x">
                        <g:message code='quotation.customerName.label' default='Customer Name'/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textField name="customerName" class="validate[required] width193"
                                 value="${quotation?.customerName}"/>
                </td>
                <td>
                    <label for="quotationDate" class="txtright bold hight1x width1x">
                        <g:message code='quotation.contactNumber.label' default='Contact Number'/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textField name="contactNumber" class="validate[required] width193"
                                 value="${quotation?.contactNumber}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="quotationDate" class="txtright bold hight2x width1x">
                        <g:message code='quotation.address.label' default='Address'/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textArea name="address" class="validate[required]"
                                value="${quotation?.address}" cols="31"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="searchProductKey" class="txtright bold hight1x width1x">
                        <g:message code="primaryDemandOrder.dateExpectedDeliver.label"
                                   default="Product"/>
                    </label>
                </td>
                <td>
                    <input type="text" id="searchProductKey" name="searchProductKey" class="width173"/>
                    <input type="hidden" id="finishProduct" name="finishProduct.id"/>
                    <span id="search-btn-customer-product-id" title="" role="button"
                          class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                        <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                        <span class="ui-button-text"></span>
                    </span>
                </td>
                <td>
                    <label for="productCode" class="txtright bold hight1x width1x">
                        <g:message code="primaryDemandOrder.dateExpectedDeliver.label"
                                   default="Product Code"/>
                    </label>
                </td>
                <td>
                    <g:textField name="productCode" id="productCode" class="width193"
                                 value="" readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="quantity" class="txtright bold hight1x width1x">
                        <g:message code="primaryDemandOrder.dateExpectedDeliver.label"
                                   default="Quantity"/>
                    </label>
                </td>
                <td>
                    <g:textField name="quantity" id="quantity" class="width193"
                                 value=""/>
                </td>
                <td>
                    <label for="rate" class="txtright bold hight1x width1x">
                        <g:message code="primaryDemandOrder.dateExpectedDeliver.label"
                                   default="Unit Price"/>
                    </label>
                </td>
                <td>
                    <g:textField name="rate" id="rate" class="width193" style="text-align: right;"
                                 value=""/>
                </td>
            </tr>
        </table>

        <div class="buttons">
            <span class="button"><input type="button" name="add-button" id="add-button-quotation"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="Add"
                                        onclick="addItem();"/></span>
        </div>

        <div class="clear"></div>

        <div class="jqgrid-container">
            <table id="jqgrid-grid-quotation"></table>

            <div id="jqgrid-pager-quotation"></div>
        </div>

        <table>
            <tr>
                <td>
                    <label for="shipmentCharge" class="txtright bold hight1x width1x">
                        <g:message code='quotation.shipmentCharge.label' default='Shipment Charge'/>
                    </label>
                </td>
                <td>
                    <g:textField name="shipmentCharge" class="width193" style="text-align: right;"
                                 value="${quotation?.shipmentCharge}"/>
                </td>
                <td>
                    <label for="vatCharge" class="txtright bold hight1x width1x">
                        <g:message code='quotation.vatCharge.label' default='Vat Charge'/>
                    </label>
                </td>
                <td>
                    <g:textField name="vatCharge" class="width193" style="text-align: right;"
                                 value="${quotation?.vatCharge}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="handlingCharge" class="txtright bold hight1x width1x">
                        <g:message code='quotation.handlingCharge.label' default='Handling Charge'/>
                    </label>
                </td>
                <td>
                    <g:textField name="handlingCharge" class="width193" style="text-align: right;"
                                 value="${quotation?.handlingCharge}"/>
                </td>
                <td>
                    <label for="otherCharge" class="txtright bold hight1x width1x">
                        <g:message code='quotation.otherCharge.label' default='Other Charge'/>
                    </label>
                </td>
                <td>
                    <g:textField name="otherCharge" class="width193" style="text-align: right;"
                                 value="${quotation?.otherCharge}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="paymentTerm" class="txtright bold hight1x width1x">
                        <g:message code='quotation.paymentTerm.label' default='Payment Term'/>
                    </label>
                </td>
                <td>
                    <g:textField name="paymentTerm" class="width193"
                                 value="${quotation?.paymentTerm}"/>
                </td>
                <td>
                    <label for="remarks" class="txtright bold hight1x width1x">
                        <g:message code='quotation.remarks.label' default='Remarks'/>
                    </label>
                </td>
                <td>
                    <g:textField name="remarks" class="width193"
                                 value="${quotation?.remarks}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="salesTermsAndConditions" class="txtright bold hight2x width1x">
                        <g:message code='quotation.salesTermsAndConditions.label' default='Sales Terms And Conditions'/>
                    </label>
                </td>
                <td>
                    <g:textArea name="salesTermsAndConditions" cols="31"
                                value="${quotation?.salesTermsAndConditions}"/>
                </td>
            </tr>
        </table>

        <script type='text/javascript'>
            $(document).ready(function () {

            });
        </script>

    </div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="save-button" id="save-button-quotation"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Create"
                                    onclick="executeAjaxQuotation();"/></span>

    </div>
</form>
