<%@ page import="com.bits.bdfp.inventory.sales.MarketReturn" %>
<form name='gFormMarketReturnEdit' id='gFormMarketReturnEdit'>
    <g:hiddenField name="id" value="${marketReturn?.id}"/>
    <g:hiddenField name="version" value="${marketReturn?.version}"/>
    <g:hiddenField name="stockId" id="stockId" value=""/>
    <g:hiddenField name="price" id="price" value=""/>
    <g:hiddenField name="isDpCustomer" id="isDpCustomer" value="false"/>
    <div id="remote-content-marketReturn"></div>

    <br>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr class="element_row_content_container lightColorbg pad_bot0" hidden="hidden">
                <td>
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 140px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
                            default="Non-DP Customer"/>
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
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 140px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
                            default="Destination Dp"/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>

                <td>
                    <g:select name="destinationDistributionPoint.id"
                              id="destinationDistributionPoint"
                              from=""
                              optionKey="id" style="width: 400px;"
                              value="${marketReturn?.destinationDistributionPoint?.id}"
                              noSelection="['': 'Please Select']"
                              onchange=""/>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="dateProposedDelivery" class="txtright bold hight1x width1x"
                           style="width: 140px;"><g:message
                            code="primaryDemandOrder.dateProposedDelivery.label"
                            default="Source DP"/>
                        <span>*</span>
                    </label>
                </td>
                <td>
                    <g:select name="sourceDistributionPoint.id"
                              id="sourceDistributionPoint"
                              from=""
                              optionKey="id" style="width: 400px;"
                              disabled="true"
                              value="${marketReturn?.sourceDistributionPoint?.id}"
                              noSelection="['': 'Please Select']" onchange="setCustomer(this.value);"/>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="dateProposedDelivery" class="txtright bold hight1x width1x"
                           style="width: 140px;"><g:message
                            code="primaryDemandOrder.dateProposedDelivery.label"
                            default="Warehouse"/>
                        <span>*</span>
                    </label>
                </td>
                <td>
                    <g:select name="warehouse.id"
                              id="warehouse"
                              from=""
                              optionKey="id" style="width: 400px;"
                              disabled="true"
                              value="${marketReturn?.warehouse?.id}"
                              noSelection="['': 'Please Select']" onchange=""/>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="dateProposedDelivery" class="txtright bold hight1x width1x"
                           style="width: 140px;"><g:message
                            code="primaryDemandOrder.dateProposedDelivery.label"
                            default="Sub-Warehouse"/>
                        <span>*</span>
                    </label>
                </td>
                <td>
                    <g:select name="subWarehouse.id"
                              id="subWarehouse"
                              from=""
                              optionKey="id" style="width: 400px;"
                              disabled="true"
                              value="${marketReturn?.subWarehouse?.id}"
                              noSelection="['': 'Please Select']" onchange=""/>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 140px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
                            default="Primary Customer"/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>

                <td>
                    <input type="text" id="searchKey" name="searchKey" class="width173 validate[required]"
                           value="${marketReturn?.primaryCustomer?.name}"/>
                    <input type="hidden" id="primaryCustomer" name="primaryCustomer.id"
                           value="${marketReturn?.primaryCustomer?.id}"/>
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
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 30px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
                            default="Code"/>
                    </label>
                </td>

                <td>
                    <g:textField name="code" id="primaryCustomerCode"
                                 value="${marketReturn?.primaryCustomer?.code}" readonly="readonly" size="18"/>
                </td>
                <td>
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 80px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
                            default="Spot Date"/>
                    </label>
                </td>

                <td>
                    <g:textField name="date" id="date"
                                 value="${marketReturn?.date}" size="14"/>
                </td>
            </tr>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 140px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
                            default="Secondary Customer"/>
                    </label>
                </td>

                <td>
                    <input type="text" id="searchKeySec" name="searchKeySec" class="width173 validate[required]"
                           disabled="disabled" value="${marketReturn?.secondaryCustomer?.name}"/>
                    <input type="hidden" id="secondaryCustomer" name="secondaryCustomer.id"
                           value="${marketReturn?.secondaryCustomer?.id}"/>
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
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 30px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
                            default="Code"/>
                    </label>
                </td>

                <td>
                    <g:textField name="code" id="secondaryCustomerCode"
                                 value="${marketReturn?.secondaryCustomer?.code}" readonly="readonly" size="18"/>
                </td>
                <td>
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 80px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
                            default="Spot Time"/>
                    </label>
                </td>

                <td>
                    <g:textField name="spotTime" id="spotTime"
                                 value="${marketReturn?.spotTime}" size="14"/>
                </td>
            </tr>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 140px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
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
                    %{--<g:select name="secondaryCustomer.id"--}%
                    %{--id="secondaryCustomer"--}%
                    %{--from="${com.bits.bdfp.customer.CustomerMaster.list()}"--}%
                    %{--optionKey="id" style="width: 260px;"--}%
                    %{--value="${marketReturn?.secondaryCustomer?.id}"--}%
                    %{--noSelection="['null': '']"/>--}%
                </td>
                <td>
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 30px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
                            default="Code"/>
                    </label>
                </td>

                <td>
                    <g:textField name="code" id="productCodeEdit"
                                 value="" readonly="readonly" size="18"/>
                </td>
                <td>
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 80px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
                            default="Temperature"/>
                    </label>
                </td>

                <td>
                    <g:textField name="temperature"
                                 value="${marketReturn?.temperature}" size="14"/>
                </td>
            </tr>

            <tr class="element_row_content_container lightColorbg pad_bot0" id="invoiceRow" hidden="hidden">
                <td>
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 140px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
                            default="Invoice"/>
                    </label>
                </td>
                <td>
                    <input type="text" id="searchInvoiceKey" name="searchInvoiceKey" class="width173" onchange="eraseData();"/>
                    <input type="hidden" id="invoice" name="invoice.id"/>
                    <span id="search-btn-customer-invoice-id" title="" role="button"
                          class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                        <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                        <span class="ui-button-text"></span>
                    </span>
                </td>
                <td>
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 30px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
                            default="Batch"/>
                    </label>
                </td>

                <td>
                    <g:textField name="batch" id="batch"
                                 value="" readonly="readonly" size="18"/>
                </td>
            </tr>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 140px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
                            default="Show Invoice"/>
                    </label>
                </td>
                <td>
                    <g:checkBox name="invoiceAvailable" id="invoiceAvailable" value="false"
                                onclick="showInvoiceRow();" checked="false"/>
                </td>
                <td style="width: 302px;"></td>
                <td>
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 110px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
                            default="Loader/Driver"/>
                    </label>
                </td>

                <td>
                    <g:textField name="loader"
                                 value="${marketReturn?.loader}" size="25"/>
                </td>
            </tr>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 140px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
                            default="Market Return Type"/>
                    </label>
                </td>
                <td>
                    <select name="mrType" id="mrType" onchange="" style="width: 200px;">
                        <option value="">Please Select</option>
                        <option value="Leak Pack">Leak Pack</option>
                        <option value="Short Pack">Short Pack</option>
                        <option value="Market Return">Market Return</option>
                        <option value="Short Supply from Challan">Short Supply from Challan</option>
                        <option value="Damage">Damage</option>
                    </select>
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
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 110px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
                            default="Returned By"/>
                    </label>
                </td>

                <td>
                    <g:textField name="returnedBy"
                                 value="${marketReturn?.returnedBy}" size="25"/>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 140px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
                            default="Available for Return"/>
                    </label>
                </td>

                <td>
                    <g:textField name="availableQuantity" id="aQuantity" readonly="readonly"
                                 value="0" size="14"/>
                </td>
                <td style="width: 225px;"></td>

                <td>
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 110px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
                            default="Received By"/>
                    </label>
                </td>

                <td>
                    <g:textField name="receivedBy"
                                 value="${marketReturn?.receivedBy}" size="25"/>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 140px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
                            default="Quantity"/>
                    </label>
                </td>

                <td>
                    <g:textField name="quantity"
                                 value="" size="14"/>
                </td>
                <td style="width: 225px;"></td>

                <td>
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 110px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
                            default="Authorized Person"/>
                    </label>
                </td>

                <td>
                    <g:textField name="authorizedPerson"
                                 value="${marketReturn?.authorizedPerson}" size="25"/>
                </td>
            </tr>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 140px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
                            default="Reference"/>
                    </label>
                </td>

                <td>
                    <g:textField name="reference"
                                 value=""/>
                </td>
                <td style="width: 225px;"></td>
                <td>
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 110px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
                            default="Checked By"/>
                    </label>
                </td>

                <td>
                    <g:textField name="checkedBy"
                                 value="${marketReturn?.checkedBy}" size="25"/>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 140px;"><g:message
                            code="primaryDemandOrder.dateExpectedDeliver.label"
                            default="Remarks"/>
                    </label>
                </td>

                <td>
                    <g:textField name="remarks"
                                 value=""/>
                </td>
            </tr>

            <script type='text/javascript'>
                $(document).ready(function () {
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
                                    value="Update"
                                    onclick="executeAjaxMarketReturn();"/></span>
        %{--<span class="button"><input type='button' name="delete-button" id="delete-button-marketReturn"--}%
                                    %{--class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'--}%
                                    %{--onclick="deleteAjaxMarketReturn();"/></span>--}%
        %{--<span class="button"><input name="clearFormButtonMarketReturn"--}%
                                    %{--class="ui-button ui-widget ui-state-default ui-corner-all" type="button"--}%
                                    %{--onclick=" reset_form('#gFormMarketReturn');" value="Cancel"/></span>--}%
    </div>
</form>
