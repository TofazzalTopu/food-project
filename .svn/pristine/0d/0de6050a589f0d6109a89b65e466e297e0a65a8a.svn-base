<%@ page import="com.bits.bdfp.common.BankPaymentMethod; com.bits.bdfp.inventory.setup.VatType; com.bits.bdfp.inventory.setup.DiscountType; com.bits.bdfp.inventory.setup.ChargeType; com.bits.bdfp.inventory.sales.DistributionPoint" %>
<form name='gFormInvoice' id='gFormInvoice'>
    <g:hiddenField name="id" value=""/>
    <g:hiddenField name="version" value=""/>
    <g:hiddenField name="invoiceNo" id="invoiceNo" value=""/>
    <input type="hidden" id="customerId" name="customerId" value="${customer.customerId}"/>

    <div id="remote-content-productPrice"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        POS Customer:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer width210'>
                        <input type="checkbox" name="isPosCustomer" id="isPosCustomer" value="true" checked="true"
                               onclick="addRemoveCheckBoxForPos(this)"/>
                    </div>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Select Customer:
                    </label>
                </td>
                <td>
                    <div class='element-input-td inputContainer width500'>
                        %{--<g:checkBox name="isPosCustomer" value="${true}" />--}%
                        <input type="text" id="searchKey" name="searchKey" class="width450" maxlength="50"/>

                        <span id="search-btn-customer-register-id" title="" role="button"
                              class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                            <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                            <span class="ui-button-text"></span>
                        </span>
                    </div>
                </td>

            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Customer Number:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="width100" name="customerNumber" value="${customer.customerCode}"
                                     readonly="readonly"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x">
                        Customer Name:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="width300" name="customerName" value="${customer.customerName}"
                                     readonly="readonly"/>
                    </div>
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Customer Address:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="width300" name="customerAddress" value="${customer.customerAddress}"
                                     readonly="readonly"/>
                    </div>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0 externalInfo">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Name:
                    </label>
                </td>
                <td>
                    <g:textField class="width200" name="externalCustomerName" id="externalCustomerName" value=""/>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x">
                        Mobile No:
                    </label>
                </td>
                <td>
                    <g:textField class="width200" name="externalCustomerMobile" id="externalCustomerMobile" value=""/>
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0 externalInfo">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Address:
                    </label>
                </td>
                <td>
                    <g:textField class="width400" name="externalCustomerAddress" id="externalCustomerAddress" value=""/>
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Pick Automatically:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer width210'>
                        <input type="checkbox" id="isPickAutomatically" value="true" checked="true"
                               onclick="changeProductSelectionMethod(this)">
                    </div>
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Select Product:
                    </label>
                </td>
                <td>
                    <div class='element-input-td inputContainer width500'>
                        <input type="text" id="searchProductKey" name="searchProductKey" class="width450"
                               maxlength="50"/>
                        <input type="hidden" id="productId" name="productId"/>
                        <input type="hidden" id="product"/>
                        <span id="search-btn-customer-product-id" title="" role="button"
                              class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                            <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                            <span class="ui-button-text"></span>
                        </span>
                    </div>
                </td>

            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width100">
                        Product Code:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer width270'>
                        <g:hiddenField name="rate" id="rate" value=""/>
                        <g:textField class="width250" name="productCode" id="productCode" value="" readonly="readonly"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width100">
                        Product Name:
                    </label>
                </td>
                <td class="width250">
                    <div class='element-input inputContainer width200'>
                        <g:textField class="width200" name="productName" id="productName" value="" readonly="readonly"/>
                    </div>
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width100">
                        Quantity:
                    </label>
                </td>
                <td><g:textField class="amount" name="quantity" value="" maxlength="8"/></td>
                <td>
                    <div class="buttons">
                        <span class="button"><input type="button" name="add-button" id="add-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Add"
                                                    onclick="addFinishProductToGrid(0);"/></span>
                    </div>
                </td>
            </tr>
        </table>
        <table>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <div class="jqgrid-container width400">
                        <table id="jqgrid-grid-product-batch"></table>
                    </div>
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <div class="buttons">
                        <span class="button"><input type="button" name="add-pick-quantity" id="add-pick-quantity"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Add" onclick="addPickQuantity()" disabled="disabled"/></span>
                    </div>
                </td>
            </tr>
        </table>
        <table>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x width140">
                        Other Charges In Value:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer width100'>
                        <g:textField class="width80 amount alin_right" name="otherChargeValue" value="" maxlength="8"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x width30">
                        %:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer width100'>
                        <g:textField class="width80 amount alin_right" name="otherChargePercentage" value=""
                                     maxlength="5"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width100">
                        Charge Type:
                        %{--<span class="mendatory_field">*</span>--}%
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:select class="minWidth150" name="chargeType.id" id="chargeType" from="${ChargeType.list()}"
                                  value="" noSelection="['': '']"/>
                    </div>
                </td>
                <td>
                    <div class="buttons">
                        <span class="button"><input type="button" name="add-charge-button" id="add-charge-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Add" onclick="addOtherCharges()"/></span>
                    </div>
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x width140">
                        Discount In Value:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer width100'>
                        <g:textField class="width80 amount alin_right" name="discountValue" value="" maxlength="8"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x width30">
                        %:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer width100'>
                        <g:textField class="width80 amount alin_right" name="discountPercentage" value=""
                                     maxlength="5"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width100">
                        Discount Type:
                        %{--<span class="mendatory_field">*</span>--}%
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:select class="minWidth150" name="discountType.id" id="discountType"
                                  from="${DiscountType.list()}" value="" noSelection="['': '']"/>
                    </div>
                </td>
                <td>
                    <div class="buttons">
                        <span class="button"><input type="button" name="add-discount-button" id="add-discount-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Add" onclick="addDiscount()"/></span>
                    </div>
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x width140">
                        VAT In Value:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer width100'>
                        <g:textField class="width80 amount alin_right" name="vatValue" value="" maxlength="8"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x width30">
                        %:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer width100'>
                        <g:textField class="width80 amount alin_right" name="vatPercentage" value="" maxlength="5"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width100">
                        VAT Type:
                        %{--<span class="mendatory_field">*</span>--}%
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:select class="minWidth150" name="vatType.id" id="vatType" from="${VatType.list()}" value=""
                                  noSelection="['': '']"/>
                    </div>
                </td>
                <td>
                    <div class="buttons">
                        <span class="button"><input type="button" name="add-vat-button" id="add-vat-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Add" onclick="addVatTax()"/></span>
                    </div>
                </td>
            </tr>
        </table>

        <div class="jqgrid-container width700">
            <table id="jqgrid-grid-finishProduct"></table>
        </div>
        <br/>

        <h3>Charges/Discount/TAX</h3>
        <table id="othersAmount">
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width515" id="actualOtherChargeLabel"></label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="width100 amount alin_right" name="actualOtherChargeValue"
                                     id="actualOtherChargeValue" value="" onblur="calculateNetAmount()"/>
                    </div>
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width515" id="actualDiscountLabel"></label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="width100 amount alin_right" name="actualDiscountValue"
                                     id="actualDiscountValue" value="" onblur="calculateNetAmount()"/>
                    </div>
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width515" id="actualVatLabel"></label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="width100 amount alin_right" name="actualVatValue" id="actualVatValue"
                                     value="" onblur="calculateNetAmount()"/>
                    </div>
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width515" id="netTotalData">
                        Net Total:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="width100 amount alin_right" name="netTotalValue" id="netTotalValue" value=""
                                     readonly="readonly"/>
                    </div>
                </td>
            </tr>
        </table>
        <table>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Payment Method:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <select class="width100" name="paymentMode" id="paymentMode">
                            <option value="Bank">Bank</option>
                            <option value="Cash">Cash</option>
                        </select>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x">
                        Payment Received:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="validate[required] width100 amount alin_right" name="paymentReceived"
                                     value="" maxlength="9"/>
                    </div>
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Transaction Date:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="width150 alin_center" name="transactionDate" id="transactionDate"
                                     value="" maxlength="9"/>
                    </div>
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Transaction No:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer width300'>
                        <g:textField class="width300" name="transactionNo" id="transactionNo" value="" maxlength="250"/>
                    </div>
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Reference:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer width300'>
                        <g:textField class="width300" name="reference" id="reference" value="" maxlength="250"/>
                    </div>
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Remarks if Any:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer width300'>
                        <g:textField class="width300" name="remarks" id="remarks" value="" maxlength="250"/>
                    </div>
                </td>
            </tr>
        </table>
    </div>

</form>
<br/>

<div class="buttons">
    <span class="button"><input type="button" name="create-button" id="create-button"
                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                value="${message(code: 'default.button.create.label', default: 'Create')}"
                                onclick="executeAjaxInvoice()"/></span>
    <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                onclick=" cleanForm();" value="Clear Form"/></span>
    <span class="button"><input type="button" name="printInvoice-button" id="printInvoice-button"
                                class="ui-button ui-widget ui-state-default ui-corner-all" onclick=" printInvoice();"
                                value="Print Invoice"/></span>
    %{--<span class="button"><input type="button" name="printMR-button" id="printMR-button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick=" printMR();" value="Print MR"/></span>--}%
</div>

