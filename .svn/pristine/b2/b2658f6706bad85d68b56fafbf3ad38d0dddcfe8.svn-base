<%@ page import="com.bits.bdfp.inventory.sales.MarketReturnType; com.bits.bdfp.inventory.sales.ReceiveProductsFromMarket" %>

<form name='gFormReceiveProductsFromMarket' id='gFormReceiveProductsFromMarket'>
    <g:hiddenField name="id" value=""/>
    <g:hiddenField name="version" value="0"/>
    <div id="remote-content-receiveProductsFromMarket"></div>

    <br>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="receivingDp" class="txtright bold hight1x width160">
                        Receiving DP
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:select class="width450" name="receivingDp.id" id="receivingDp"
                              from=""
                              optionKey="id"
                              value="" onchange="listWarehouse();"/>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="receivingInventory" class="txtright bold hight1x width160">
                        Receiving Inventory
                        <span class="mendatory_field">*</span>
                    </label>
                </td>

                <td>
                    <g:select class="width450" name="receivingInventory.id"
                              id="receivingInventory"
                              from=""
                              optionKey="id"
                              value="" onchange="listSubWarehouse();"/>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="receivingSubInventory" class="txtright bold hight1x width160">
                        Receiving Sub Inventory
                        <span class="mendatory_field">*</span>
                    </label>
                </td>

                <td>
                    <g:select class="width450" name="receivingSubInventory.id"
                              id="receivingSubInventory"
                              from=""
                              optionKey="id"
                              value=""/>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="searchKeySec" class="txtright bold hight1x width160">
                        Secondary Customer
                        <span class="mendatory_field">*</span>
                    </label>
                </td>

                <td>
                    <input type="text" id="searchKeySec" name="searchKeySec" class="validate[required]" style="width: 183px"/>
                    <input type="hidden" id="secondaryCustomer" name="secondaryCustomer.id"/>
                    <input type="hidden" id="customerMaster" name="customerMaster.id"/>
                    <span id="search-btn-secondary-customer-id" title="" role="button"
                          class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                        <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                        <span class="ui-button-text"></span>
                    </span>
                </td>
                <td>
                    <label for="secondaryCustomerCode" class="txtright bold hight1x width60">
                        Code
                    </label>
                </td>

                <td>
                    <g:textField name="code" id="secondaryCustomerCode"
                                 value="" readonly="readonly" size="20"/>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="batchAvailable" class="txtright bold hight1x width160">
                        Batch Available
                    </label>
                </td>
                <td>
                    <g:checkBox name="batchAvailable" id="batchAvailable" class="validate[required]" value="false" checked="false"
                                onclick="showInvoiceRow();"/>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0" id="invoiceRow" hidden="hidden">
                <td>
                    <label for="searchInvoiceKey" class="txtright bold hight1x width160">
                        Invoice
                    </label>
                </td>
                <td>
                    <input type="text" id="searchInvoiceKey" name="searchInvoiceKey" style="width: 183px" onchange="eraseData();"/>
                    <input type="hidden" id="invoice" name="invoice.id"/>
                    <span id="search-btn-customer-invoice-id" title="" role="button"
                          class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                        <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                        <span class="ui-button-text"></span>
                    </span>
                </td>
                <td>
                    <label for="batch" class="txtright bold hight1x width60">
                        Batch
                    </label>
                </td>

                <td>
                    <g:textField name="batch" id="batch"
                                 value="" readonly="readonly" size="20"/>
                    %{--<g:select name="batch"--}%
                              %{--id="batch"--}%
                              %{--from=""--}%
                              %{--optionKey="id" style="width: 140px;"--}%
                              %{--noSelection="['': 'Please Select']"/>--}%

                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="searchProductKey" class="txtright bold hight1x width160">
                        Product
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <input type="text" id="searchProductKey" name="searchProductKey" style="width: 183px"/>
                    <input type="hidden" id="finishProduct" name="finishProduct.id"/>
                    <span id="search-btn-customer-product-id" title="" role="button"
                          class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                        <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                        <span class="ui-button-text"></span>
                    </span>
                </td>
                <td>
                    <label for="productCode" class="txtright bold hight1x width60">
                        Code
                    </label>
                </td>

                <td>
                    <g:textField name="code" id="productCode"
                                 value="" readonly="readonly" size="20"/>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="mrType" class="txtright bold hight1x width160">
                        Market Return Type
                        <span class="mendatory_field">*</span>
                    </label>
                </td>

                <td>
                    %{--<select name="mrType" id="mrType" style="width: 210px;">--}%
                        %{--<option value="Leak Pack">Leak Pack</option>--}%
                        %{--<option value="Short Pack">Short Pack</option>--}%
                        %{--<option value="Market Return">Market Return</option>--}%
                        %{--<option value="Short Supply from Challan">Short Supply from Challan</option>--}%
                        %{--<option value="Damage">Damage</option>--}%
                    %{--</select>--}%
                    <g:select name="mrType" id="mrType" class="width210"
                              from="${MarketReturnType?.values()}" optionKey="displayName"
                              value="" noSelection="['':'Select MR Type']"/>
                </td>
                <td>
                    <label for="quantity" class="txtright bold hight1x width60">Quantity
                        <span class="mendatory_field">*</span>
                    </label>
                </td>

                <td>
                    <g:textField name="quantity" id="quantity"
                                 value="" size="20"/>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="reference" class="txtright bold hight1x width160">
                        Reference
                    </label>
                </td>

                <td>
                    <g:textField name="reference" id="reference"
                                 value="" size="32"/>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="remarks" class="txtright bold hight1x width160">
                        Remarks
                    </label>
                </td>

                <td>
                    <g:textField name="remarks" id="remarks"
                                 value="" size="32"/>
                </td>
            </tr>
        </table>
    </div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="addButton" id="addButton"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Add Product"
                                    onclick="addItemToGrid();"/></span>
    </div>

    <div class="clear"></div>

    <div class="jqgrid-container blue_grid">
        <table id="jqgrid-grid-receiveProductsFromMarket"></table>

        <div id="jqgrid-pager-receiveProductsFromMarket"></div>
    </div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="create-button" id="create-button-receiveProductsFromMarket"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxReceiveProductsFromMarket();"/></span>
        <span class="button"><input name="clearFormButtonReceiveProductsFromMarket"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" type="button"
                                    onclick=" reset_form('#gFormReceiveProductsFromMarket');" value="Cancel"/></span>
    </div>
</form>
