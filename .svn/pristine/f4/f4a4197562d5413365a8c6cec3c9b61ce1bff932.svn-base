<%@ page import="com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder" %>
<form name='gFormPrimaryDemandOrder' id='gFormPrimaryDemandOrder'>
    <g:hiddenField name="id" id="territoryId" value=""/>
    <g:hiddenField name="version" value=""/>
    <g:hiddenField name="idEnterprise" id="idEnterprise" value=""/>
    <g:hiddenField name="idBusiness" id="idBusiness" value=""/>
    %{--new--}%
    <g:hiddenField name="orderRowid" id="orderRowid" value=""/>


    <div id="remote-content-territoryConfiguration"></div>

    <br>

    <div class="element_row_content_container lightColorbg pad_bot0">

        <table>
            <g:if test="${size == 1}">
                <script type="text/javascript">
                    $(document).ready(function () {
                        setId(${enterpriseList}[0].id);
                        %{--$('#idEnterprise').val(${enterpriseList[0].id});--}%
                    });
                </script>
            </g:if>
            <g:else>
                <tr class="element_row_content_container lightColorbg pad_bot0">
                    <td>
                        <label for="enterpriseConfiguration" class="txtright bold hight1x width1x"
                               style="width: 160px;">Enterprise:
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <g:select name="enterpriseConfiguration.id"
                                  class="validate[required]"
                                  style="width: 350px; height: 20px;" id="enterpriseConfiguration"
                                  optionKey="id" value="" noSelection="['': 'Please Select']"
                                  onchange="setId(this.value);"/>
                    </td>
                </tr>
                <script type="text/javascript">
                    $(document).ready(function () {
                        $('#enterpriseConfiguration').val('');
                        var options = '<option value="">Please Select</option>';
                        for (var i = 0; i < ${size}; i++) {
                            options += '<option value="' + ${enterpriseList}[i].id + '">' + ${enterpriseList}[i].name + '</option>';
                        }
                        $("#enterpriseConfiguration").html(options);
                    });
                </script>
            </g:else>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="dateProposedDelivery" class="txtright bold hight1x width1x"
                           style="width: 160px;">Date Proposed Delivery:
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textField name="dateProposedDelivery" id="dateProposedDelivery" class="validate[required]"
                                 value="" style="width: 160px;"/>
                </td>
                <td>
                    <div class="buttons">
                        <span id="search-span" class="button"><input type='button' name="search-button"
                                                                     id="search-button"
                                                                     class="ui-button ui-widget ui-state-default ui-corner-all"
                                                                     value='Search'
                                                                     onclick="populateCustomerOrderGrid();"/></span>
                    </div>
                </td>
                <td>
                    <label for="dateExpectedDeliver" class="txtright bold hight1x width1x"
                           style="width: 160px;">Date Expected Deliver:
                        <span class="mendatory_field">*</span>
                    </label>
                </td>

                <td>
                    <g:textField class="validate[required]" name="dateExpectedDeliver" id="dateExpectedDeliver"
                                 value="" style="width: 160px;"/>
                </td>

            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="distributionPoint" class="txtright bold hight1x width1x"
                           style="width: 160px;">Distribution Point:
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:select name="distributionPoint.id"
                              class="validate[required]"
                              from="${distributionPointList}"
                              id="distributionPoint"
                              style="width: 165px; height: 20px;"
                              optionKey="id" optionValue="name" value=""
                              onchange="fetchCustomer(this.value);"/>
                </td>
                <td>
                    <label for="customerMaster" class="txtright bold hight1x width150">
                        Customer Name:
                    </label>
                </td>
                <td>
                    <g:textField class="width150" name="customerMaster.name"
                                 id="customerMasterName"
                                 value="" disabled="disabled" />
                    <g:hiddenField name="customerOrderFor.id" id="customerMaster"/>
                </td>
            </tr>
            <tr id="geoTr" class="element_row_content_container lightColorbg pad_bot0" hidden="hidden">
                <td>
                    <label for="distributionPoint" class="txtright bold hight1x width1x"
                           style="width: 160px;">Geo Location:
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:select name="territorySubArea.id"
                              class="validate[required]"
                              id="territorySubArea"
                              style="width: 165px; height: 20px;"/>
                </td>

                <td>
                    <label for="customerMasterCode" class="txtright bold hight1x width150">
                        Customer Code:
                    </label>
                </td>
                <td>
                    <g:textField class="width150" name="customerMaster.code" id="customerMasterCode" value=""
                                 disabled="disabled"/>
                </td>
            </tr>
        </table>
        <br/>
        <table>
            <tr>
                <td>
                    <table id="customer-order-grid"></table>

                    <div id="customer-order-grid-pager"></div>
                </td>
                <td style="padding-left: 10px;">
                    <table cellpadding="0" cellspacing="0" border="0" id="detailsTable"
                           style="width: 330px;">
                        <thead>
                        <tr>
                            <th>Product Name</th>
                            <th>Quantity</th>
                            <th>Rate</th>
                            <th>Amount</th>
                            <th>QtyInLtr</th>
                        </tr>
                        </thead>
                        <tbody></tbody>


                    </table>
                    <table>
                        <tr>
                            <td>
                                <label class="txtright bold hight1x width130">
                                    Total Amount :
                                </label>
                            </td>
                            <td>
                                <div class='element-input inputContainer'>
                                    <g:textField class="amount alin_right width100" name="totaleAmount" id="totaleAmount" value=""
                                                 readonly="readonly" disabled="disabled"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>

        </table>
        %{--<table>--}%
        %{--<tr class="element_row_content_container inputContainer lightColorbg pad_bot0">--}%
        %{--<td>--}%
        %{--<label class="txtright bold hight1x width1x">--}%
        %{--Total Receivable:--}%
        %{--</label>--}%
        %{--</td>--}%
        %{--<td>--}%
        %{--<div class='element-input inputContainer'>--}%
        %{--<g:textField class="amount alin_right width100" name="receivableAmount" id="receivableAmount" value="" readonly="readonly" disabled="disabled"/>--}%
        %{--</div>--}%
        %{--</td>--}%
        %{--<td>--}%
        %{--<label class="txtright bold hight1x width130">--}%
        %{--Advance Available:--}%
        %{--</label>--}%
        %{--</td>--}%
        %{--<td>--}%
        %{--<div class='element-input inputContainer'>--}%
        %{--<g:textField class="amount alin_right width100" name="advanceAmount" id="advanceAmount" value="" readonly="readonly" disabled="disabled"/>--}%
        %{--</div>--}%
        %{--</td>--}%
        %{--<td>--}%
        %{--<label class="txtright bold hight1x width1x">--}%
        %{--Eligible to Proceed:--}%
        %{--</label>--}%
        %{--</td>--}%
        %{--<td>--}%
        %{--<div class='element-input inputContainer'>--}%
        %{--<g:textField class="width100" name="eligibility" id="eligibility" value="Y" readonly="readonly" disabled="disabled"/>--}%
        %{--</div>--}%
        %{--</td>--}%
        %{--</tr>--}%
        %{--</table>--}%

        <div class="buttons">
            <span class="button" id="merge-span"><input type="button" name="merge-button" id="merge-order-button"
                                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                                        value="Merge Orders"
                                                        onclick="mergeSelectedOrders();"/></span>
            <span class="button" id="order-cancel-span"><input type="button" name="cancel-button"
                                                               id="cancel-order-button"
                                                               class="ui-button ui-widget ui-state-default ui-corner-all"
                                                               onclick="cancelSelectedOrder();"
                                                               value="Cancel Order(s)"/></span>
            <span class="button"><input type="button" name="generate-button" id="generate-order-button"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        onclick="generateDemandOrder();"
                                        value="Create Order List"/></span>
            <span class="button" id="cancel-span" hidden="hidden"><input type="button" name="cancel-button"
                                                                         id="cancel-button"
                                                                         class="ui-button ui-widget ui-state-default ui-corner-all"
                                                                         onclick="resetAll();" value="Cancel"/></span>
        </div>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0" id="primary-demand-div" hidden="hidden">
        %{--<table>--}%
            %{--<tr class="element_row_content_container lightColorbg pad_bot0">--}%
                %{--<td>--}%
                %{--<label for="distributionPoint" class="txtright bold hight1x width1x"--}%
                %{--style="width: 160px;"><g:message--}%
                %{--code="primaryDemandOrder.distributionPoint.label"--}%
                %{--default="Primary Order No"/></label>--}%
                %{--</td>--}%
                %{--<td>--}%
                %{--<g:textField name="orderNo" readonly="readonly"--}%
                %{--value="AUTO GENERATED" style="width: 160px;"/>--}%
                %{--</td>--}%
                %{--<td>--}%
                    %{--<label for="userOrderPlaced" class="txtright bold hight1x width150"><g:message--}%
                            %{--code="primaryDemandOrder.userOrderPlaced.label"--}%
                            %{--default="Total Order Value"/></label>--}%
                %{--</td>--}%
                %{--<td style="width: 100% !important;">--}%
                    %{--<label for="userOrderPlaced" class="txtright bold hight1x width150">--}%
                        %{--<g:message--}%
                            %{--code="primaryDemandOrder.userOrderPlaced.label"--}%
                            %{--default="Total Order Value"/>--}%
                    %{--</label>--}%
                    %{--<g:textField class="width150" name="totalOrderValue" value="" readonly="readonly" id="totalOrderValue"/>--}%
                %{--</td>--}%

            %{--</tr>--}%
            %{--<tr class="element_row_content_container lightColorbg pad_bot0">--}%
            %{--<td>--}%
            %{--<label for="userOrderPlaced" class="txtright bold hight1x width1x" style="width: 160px;"><g:message--}%
            %{--code="primaryDemandOrder.userOrderPlaced.label"--}%
            %{--default="Order Date"/>--}%
            %{--<span class="mendatory_field">*</span>--}%
            %{--</label>--}%
            %{--</td>--}%
            %{--<td>--}%
            %{--<g:textField class="validate[required]" name="orderDate" id="orderDate" value=""--}%
            %{--style="width: 160px;"/>--}%
            %{--</td>--}%

            %{--</tr>--}%
        %{--</table>--}%



        <div id="productAdder" hidden="hidden">
            <table>
                %{--
                <tr class="element_row_content_container lightColorbg pad_bot0">
                    <td>
                        <label for="userOrderPlaced" class="txtright bold hight1x width150">
                            <g:message code="primaryDemandOrder.userOrderPlaced.label" default="Customer Name"/></label>
                    </td>
                    <td>
                        <g:textField class="width150" name="customerMaster.name"
                                     id="customerMasterName"
                                     value="" disabled="disabled"/>
                        <g:hiddenField name="customerOrderFor.id" id="customerMaster"/>
                    </td>
                    <td>
                        <label for="userOrderPlaced" class="txtright bold hight1x width150">
                            <g:message code="primaryDemandOrder.userOrderPlaced.label" default="Customer Code"/></label>
                    </td>
                    <td>
                        <g:textField class="width150" name="customerMaster.code" id="customerMasterCode" value=""
                                     disabled="disabled"/>
                    </td>
                    --}%%{--<td>--}%%{--
                    --}%%{--<label for="userOrderPlaced" class="txtright bold hight1x width1x"--}%%{--
                    --}%%{--style="width: 160px;"><g:message--}%%{--
                    --}%%{--code="primaryDemandOrder.userOrderPlaced.label"--}%%{--
                    --}%%{--default="Customer Name"/></label>--}%%{--
                    --}%%{--</td>--}%%{--
                    --}%%{--<td>--}%%{--
                    --}%%{--<g:select name="customerMaster.id"--}%%{--
                    --}%%{--style="width: 165px; height: 20px;" id="customerMaster"--}%%{--
                    --}%%{--optionKey="id" value="" noSelection="['null': 'Please Select']"/>--}%%{--
                    --}%%{--</td>--}%%{--
                </tr>
                --}%
                <tr class="element_row_content_container lightColorbg pad_bot0">
                    <td>
                        <label class="txtright bold hight1x width150">
                            Product:
                        </label>

                    </td>
                    <td><input type="text" id="searchProductKey" name="searchProductKey" class="width500"/>
                        <input type="hidden" id="productId" name="finishProduct.id"/>
                        <input type="hidden" id="productCode" value=""/>
                        <input type="hidden" id="product" value=""/>
                        <span id="search-btn-customer-product-id" title="" role="button"
                              class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                            <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                            <span class="ui-button-text"></span>
                        </span>
                    </td>
                </tr>
                <tr class="element_row_content_container lightColorbg pad_bot0">
                    %{--<td>--}%
                    %{--<label for="userOrderPlaced" class="txtright bold hight1x width1x"--}%
                    %{--style="width: 160px;"><g:message--}%
                    %{--code="primaryDemandOrder.userOrderPlaced.label"--}%
                    %{--default="Product"/></label>--}%
                    %{--</td>--}%
                    %{--<td>--}%
                    %{--<g:select name="finishProduct.id"--}%
                    %{--style="width: 165px; height: 20px;" id="finishProduct"--}%
                    %{--optionKey="id" value="" noSelection="['null': 'Please Select']"--}%
                    %{--onchange="storeSelectedId();"/>--}%
                    %{--</td>--}%
                    <td>
                        <label class="txtright bold hight1x width150">
                            Rate:
                        </label>

                    </td>
                    <td>
                        <g:textField class="width150" name="rate" id="rate" value="" disabled="disabled"/>
                    </td>
                    <td>
                        <label for="distributionPoint" class="txtright bold hight1x width150">
                            Quantity:
                        </label>
                    </td>
                    <td>
                        <g:textField name="quantity" class="width150 amount" value=""/>
                    </td>
                    <td>
                        <span class="button"><input type="button" name="add-button" id="add-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Add"
                                                    onclick="addNewItemToCollectionGrid();"/></span>
                        <span class="button"><input type="button" name="cancel-button" id="refresh-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    onclick="resetAdderDiv();" value="Cancel"/></span>
                    </td>
                </tr>
            </table>

            <div class="buttons">
                <span id="remove-span" class="button" hidden="hidden"><input type='button' name="remove-button"
                                                                             id="remove-button"
                                                                             class="ui-button ui-widget ui-state-default ui-corner-all"
                                                                             value='Delete'
                                                                             onclick="deleteItemFromGrid();"/></span>
            </div>
        </div>

        <div class="buttons" id="enablerButton" style="float: left;">
            <span class="button"><input type="button" name="enabler-button" id="enabler-button"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="Add Products"
                                        onclick="showAddProductDiv();"/></span>
        </div>

        <div style="float: right; margin-right: 11%;">
            <label for="totalOrderValue" class="txtright bold hight1x width150">
                Total Primary Order Value:
            </label>
            <g:textField class="width150" name="totalOrderValue" value="" readonly="readonly" id="totalOrderValue"/>
        </div>

        <br/>
        <br/>
        <br/>

        <table id="primary-order-grid"></table>

        <div id="primary-order-grid-pager"></div>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Total Receivable (<span id="dpCusName"> </span>) :
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="amount alin_right width100" name="receivableAmount" id="receivableAmount"
                                     value="" readonly="readonly" disabled="disabled"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width130">
                        Advance Available:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="amount alin_right width100" name="advanceAmount" id="advanceAmount" value=""
                                     readonly="readonly" disabled="disabled"/>
                    </div>
                </td>

                <td>
                    <label class="txtright bold hight1x width1x">
                        Eligible to Proceed:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        %{--<g:textField class="width100" name="eligibility" id="eligibility" value="Y" readonly="readonly"--}%
                        %{--disabled="disabled"/>--}%

                        <a href="javascript:checkEligibility()"><span class="txtright bold hight1x width1x" id="button">

                        </span></a>
                            %{--<a href="javascript:checkEligibility()">Y</a>--}%
                    </div>
                </td>
                <g:hiddenField name="customerPriority" id="customerPriority" value=""/>
                <g:hiddenField name="customerLimit" id="customerLimit" value=""/>
                <g:hiddenField name="totalReceivableAmount" id="totalReceivableAmount" value=""/>
                <g:hiddenField name="customerDeposit" id="customerDeposit" value=""/>
            </tr>
        </table>
    </div>

    <div class="buttons" id="orderCreateDiv" hidden="hidden" style="padding-top: 785px;">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="createPrimaryDemandOrder();"/></span>
    </div>
</form>