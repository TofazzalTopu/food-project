<%@ page import="com.bits.bdfp.inventory.sales.MarketReturnType; com.bits.bdfp.inventory.sales.MarketReturn" %>

<form name='gFormMarketReturn' id='gFormMarketReturn'>
    <g:hiddenField name="id" value=""/>
    <g:hiddenField name="version" value="0"/>
    <g:hiddenField name="stockId" id="stockId" value=""/>
    <g:hiddenField name="price" id="price" value=""/>
    <g:hiddenField name="isDpCustomer" id="isDpCustomer" value="false"/>
    <div id="remote-content-marketReturn"></div>

    <br>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="isNonDpCustomer" class="txtright bold hight1x width140">
                           Non-DP Customer
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:checkBox name="isNonDpCustomer" id="isNonDpCustomer" class="validate[required]" value="false"
                                onclick="customerTypeChange();"/>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="destinationDistributionPoint" class="txtright bold hight1x width140">
                        Destination DP
                        <span class="mendatory_field">*</span>
                    </label>
                </td>

                <td>
                    <g:select class="width400" name="destinationDistributionPoint.id"
                              id="destinationDistributionPoint" from="" optionKey="id"
                              value="" />
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="sourceDistributionPoint" class="txtright bold hight1x width140">
                        Source DP
                        <span>*</span>
                    </label>
                </td>
                <td>
                    <g:select class="width400" name="sourceDistributionPoint.id"
                              id="sourceDistributionPoint" from="" optionKey="id"
                              disabled="true" value=""
                              noSelection="['': 'Please Select']" onchange="setCustomer(this.value);"/>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="warehouse" class="txtright bold hight1x width140">
                        Warehouse
                        <span>*</span>
                    </label>
                </td>
                <td>
                    <g:select class="width400" name="warehouse.id"
                              id="warehouse" from="" optionKey="id"
                              disabled="true" value=""
                              noSelection="['': 'Please Select']" onchange="listSubWarehouse();"/>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="subWarehouse" class="txtright bold hight1x width140">
                        Sub-Warehouse
                        <span>*</span>
                    </label>
                </td>
                <td>
                    <g:select class="width400" name="subWarehouse.id"
                              id="subWarehouse" from="" optionKey="id"
                              disabled="true" value=""
                              noSelection="['': 'Please Select']" onchange=""/>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="searchKey" class="txtright bold hight1x width140">
                        Primary Customer
                        <span class="mendatory_field">*</span>
                    </label>
                </td>

                <td>
                    <input type="text" id="searchKey" name="searchKey" class="width160 validate[required]"/>
                    <input type="hidden" id="primaryCustomer" name="primaryCustomer.id"/>
                    <input type="hidden" id="primaryCustomerCategory" name="primaryCustomerCategory"/>
                    <span id="search-btn-primary-customer-id" title="" role="button"
                          class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                        <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                        <span class="ui-button-text"></span>
                    </span>
                    %{--<g:select name="primaryCustomer.id" id="primaryCustomer"--}%
                    %{--from="${com.bits.bdfp.customer.CustomerMaster.list()}"--}%
                    %{--optionKey="id" style="width: 260px;"--}%
                    %{--value="${marketReturn?.primaryCustomer?.id}"/>--}%
                </td>
                <td>
                    <label for="primaryCustomerCode" class="txtright bold hight1x width30">
                        Code
                    </label>
                </td>

                <td>
                    <g:textField name="code" id="primaryCustomerCode"
                                 value="" readonly="readonly" size="18"/>
                </td>
                <td>
                    <label for="date" class="txtright bold hight1x width80">
                        Spot Date
                    </label>
                </td>

                <td>
                    <g:textField name="date" id="date"
                                 value="" size="14"/>
                </td>
            </tr>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="searchKeySec" class="txtright bold hight1x width140">
                        Secondary Customer
                    </label>
                </td>

                <td>
                    <input type="text" id="searchKeySec" name="searchKeySec" class="width160 validate[required]"
                           disabled="disabled"/>
                    <input type="hidden" id="secondaryCustomer" name="secondaryCustomer.id"/>
                    <span id="search-btn-secondary-customer-id" title="" role="button"
                          class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                        <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                        <span class="ui-button-text"></span>
                    </span>
                    %{--<g:select name="secondaryCustomer.id"--}%
                    %{--id="secondaryCustomer"--}%
                    %{--from="${com.bits.bdfp.customer.CustomerMaster.list()}"--}%
                    %{--optionKey="id" style="width: 260px;"--}%
                    %{--value="${marketReturn?.secondaryCustomer?.id}"--}%
                    %{--noSelection="['null': '']"/>--}%
                </td>
                <td>
                    <label for="secondaryCustomerCode" class="txtright bold hight1x width30">
                        Code
                    </label>
                </td>

                <td>
                    <g:textField name="code" id="secondaryCustomerCode"
                                 value="" readonly="readonly" size="18"/>
                </td>
                <td>
                    <label for="spotTime" class="txtright bold hight1x width80">
                        Spot Time
                    </label>
                </td>

                <td>
                    <g:textField name="spotTime" id="spotTime"
                                 value="" size="14"/>
                </td>
            </tr>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="searchProductKey" class="txtright bold hight1x width140">
                        Product
                    </label>
                </td>
                <td>
                    <input type="text" id="searchProductKey" name="searchProductKey" class="width160"/>
                    <input type="hidden" id="finishProduct" name="finishProduct.id"/>
                    <span id="search-btn-customer-product-id" title="" role="button"
                          class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                        <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                        <span class="ui-button-text"></span>
                    </span>
                    %{--<g:select name="secondaryCustomer.id"--}%
                    %{--id="secondaryCustomer"--}%
                    %{--from="${com.bits.bdfp.customer.CustomerMaster.list()}"--}%
                    %{--optionKey="id" style="width: 260px;"--}%
                    %{--value="${marketReturn?.secondaryCustomer?.id}"--}%
                    %{--noSelection="['null': '']"/>--}%
                </td>
                <td>
                    <label for="productCode" class="txtright bold hight1x width30">
                        Code
                    </label>
                </td>

                <td>
                    <g:textField name="code" id="productCode"
                                 value="" readonly="readonly" size="18"/>
                </td>
                <td>
                    <label for="temperature" class="txtright bold hight1x width80">
                        Temperature
                    </label>
                </td>

                <td>
                    <g:textField name="temperature"
                                 value="" size="14"/>
                </td>
            </tr>

            <tr class="element_row_content_container lightColorbg pad_bot0" id="invoiceRow" hidden="hidden">
                <td>
                    <label for="searchInvoiceKey" class="txtright bold hight1x width140">
                        Invoice
                    </label>
                </td>
                <td>
                    <input type="text" id="searchInvoiceKey" name="searchInvoiceKey" class="width160" onchange="eraseData();"/>
                    <input type="hidden" id="invoice" name="invoice.id"/>
                    <span id="search-btn-customer-invoice-id" title="" role="button"
                          class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                        <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                        <span class="ui-button-text"></span>
                    </span>
                </td>
                <td>
                    <label for="batch" class="txtright bold hight1x width30">
                        Batch
                    </label>
                </td>

                <td>
                    <g:textField name="batch" id="batch"
                                 value="" readonly="readonly" size="18"/>
                </td>
            </tr>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="invoiceAvailable" class="txtright bold hight1x width140">
                        Show Invoice
                    </label>
                </td>
                <td>
                    <g:checkBox name="invoiceAvailable" id="invoiceAvailable" value="false"
                                onclick="showInvoiceRow();" checked="false"/>
                </td>
                <td style="width: 302px;"></td>
                <td>
                    <label for="loader" class="txtright bold hight1x width110">
                        Loader/Driver
                    </label>
                </td>
                <td>
                    <g:textField name="loader"
                                 value="" size="25"/>
                </td>
            </tr>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="mrType" class="txtright bold hight1x width140">
                        Market Return Type
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    %{--<select name="mrType" id="mrType" onchange="" style="width: 200px;">--}%
                        %{--<option value="">Please Select</option>--}%
                        %{--<option value="Leak Pack">Leak Pack</option>--}%
                        %{--<option value="Short Pack">Short Pack</option>--}%
                        %{--<option value="Market Return">Market Return</option>--}%
                        %{--<option value="Short Supply from Challan">Short Supply from Challan</option>--}%
                        %{--<option value="Damage">Damage</option>--}%
                    %{--</select>--}%
                    <g:select name="mrType" id="mrType" class="width200"
                              from="${MarketReturnType?.values()}" optionKey="displayName"
                              value="" noSelection="['':'Select MR Type']"/>
                    %{--<g:select id="mrType" name="mrType" from="${com.bits.bdfp.inventory.sales.MarketReturnType.values()}"/>--}%
                </td>
                <td style="width: 225px;"></td>
                %{--<td>--}%
                %{--<label for="dateExpectedDeliver" class="txtright bold hight1x width1x"--}%
                %{--style="width: 91px;"><g:message--}%
                %{--code="primaryDemandOrder.dateExpectedDeliver.label"--}%
                %{--default="MR Sub-Type"/>--}%
                %{--</label>--}%
                %{--</td>--}%
                %{--<td>--}%
                %{--<select name="mrType" id="mrType" onchange="loadBankDiv()">--}%
                %{--<option value="">Please Select</option>--}%
                %{--<option value="Damage by crate">Damage by crate</option>--}%
                %{--<option value="Sealing problem">Sealing problem</option>--}%
                %{--<option value="Teeth cutting">Teeth cutting</option>--}%
                %{--<option value="Pinpointed">Pinpointed</option>--}%
                %{--<option value="Damage by pen">Damage by pen</option>--}%
                %{--<option value="Back dated">Back dated</option>--}%
                %{--<option value="Poly film leak">Poly film leak</option>--}%
                %{--<option value="Expire date">Expire date</option>--}%
                %{--<option value="Packet Swollen">Packet Swollen</option>--}%
                %{--<option value="Packet structural damage">Packet structural damage</option>--}%
                %{--<option value="Product Spoiled">Product Spoiled</option>--}%
                %{--<option value="Packet leak">Packet leak</option>--}%
                %{--<option value="Rodent bite">Rodent bite</option>--}%
                %{--<option value="Rough Handling">Rough Handling</option>--}%
                %{--<option value="Structural damage">Structural damage</option>--}%
                %{--<option value="Fungal spoiled">Fungal spoiled</option>--}%
                %{--<option value="Can sealing/seaming problem">Can sealing/seaming problem</option>--}%
                %{--<option value="Can structural damage">Can structural damage</option>--}%
                %{--<option value="Can rusted">Can rusted</option>--}%
                %{--<option value="Others">Others</option>--}%
                %{--</select>--}%
                %{--<g:select name="mrSubType"--}%
                %{--id="mrSubType"--}%
                %{--from=""--}%
                %{--optionKey="id" style="width: 100px;"--}%
                %{--value="${marketReturn?.secondaryCustomer?.id}"--}%
                %{--noSelection="['': 'Please Select']" disabled="true"/>--}%
                %{--</td>--}%
                <td>
                    <label for="returnedBy" class="txtright bold hight1x width110">Returned By
                    </label>
                </td>

                <td>
                    <g:textField name="returnedBy"
                                 value="" size="25"/>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="aQuantity" class="txtright bold hight1x width140">
                        Available for Return
                    </label>
                </td>

                <td>
                    <g:textField name="availableQuantity" id="aQuantity" readonly="readonly"
                                 value="0" size="14"/>
                </td>
                <td style="width: 225px;"></td>
                <td>
                    <label for="receivedBy" class="txtright bold hight1x width110">
                        Received By
                    </label>
                </td>

                <td>
                    <g:textField name="receivedBy"
                                 value="" size="25"/>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="quantity" class="txtright bold hight1x width140">
                        Quantity
                    </label>
                </td>

                <td>
                    <g:textField name="quantity"
                                 value="" size="14"/>
                </td>
                <td style="width: 225px;"></td>

                <td>
                    <label for="authorizedPerson" class="txtright bold hight1x width110">
                        Authorized Person
                    </label>
                </td>

                <td>
                    <g:textField name="authorizedPerson"
                                 value="" size="25"/>
                </td>
            </tr>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="reference" class="txtright bold hight1x width140">
                        Reference
                    </label>
                </td>

                <td>
                    <g:textField name="reference"
                                 value=""/>
                </td>
                <td style="width: 225px;"></td>
                <td>
                    <label for="checkedBy" class="txtright bold hight1x width110">
                        Checked By
                    </label>
                </td>

                <td>
                    <g:textField name="checkedBy"
                                 value="" size="25"/>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="remarks" class="txtright bold hight1x width140">
                        Remarks
                    </label>
                </td>

                <td>
                    <g:textField name="remarks"
                                 value=""/>
                </td>
            </tr>

            <script type='text/javascript'>
                $(document).ready(function () {
                    $('#date').datepicker({
                        dateFormat: 'dd-mm-yy',
                        changeMonth: true,
                        changeYear: true,
                        maxDate: 0
                    });
                    $('#date').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                });
            </script>

        </table>

        <div class="buttons">
            <span class="button"><input type="button" name="add-button" id="addProduct"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="Add"
                                        onclick="addItemToGrid();"/></span>
        </div>
    </div>

    <div class="clear"></div>

    <div class="jqgrid-container blue_grid">
        <table id="jqgrid-grid-marketReturn"></table>

        <div id="jqgrid-pager-marketReturn"></div>
    </div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="create-button" id="create-button-marketReturn"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxMarketReturn();"/></span>
        <span class="button"><input name="clearFormButtonMarketReturn"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" type="button"
                                    onclick=" reset_form('#gFormMarketReturn');" value="Cancel"/></span>
    </div>
</form>
