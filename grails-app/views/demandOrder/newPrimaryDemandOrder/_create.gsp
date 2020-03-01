<%@ page import="com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.inventory.sales.DistributionPoint;" %>
<form name='gFormNewPrimaryDemandOrder' id='gFormNewPrimaryDemandOrder'>
    <g:hiddenField name="id" value=""/>
    <g:hiddenField name="version" value=""/>
    <input type="hidden" id="customerId" name="customerId"/>

    <div id="remote-content-productPrice"></div>
    %{--<fieldset  class="ui-state-default ui-corner-all">--}%
    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Customer:
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:if test="${userType == com.bits.bdfp.util.ApplicationConstants.USER_TYPE_CUSTOMER}">
                        <div class='element-input-td inputContainer'>
                            <input type="text" id="searchKey" name="searchKey" class="width173 validate[required]"
                                   readonly="true"/>
                            <script>
                                $(document).ready(function () {
                                    $("#searchKey").val("[${customerMaster.code}] ${customerMaster.name}");
                                    $("#customerId").val("${customerMaster.id}");
                                    $("#customerNumber").val("${customerMaster.code}");
                                    $("#customerName").val("${customerMaster.name}");
                                    $("#customerAddress").val("${customerMaster.presentAddress}");
                                    loadProduct("${customerMaster.id}");
                                    readCustomerBalanceAndShippingAddress(${customerMaster.id});
                                    loadProduct(${customerMaster.id});
                                    generateGeoSelectList(${customerMaster.id});
                                });
                            </script>
                        </div>
                    </g:if>
                    <g:else>
                        <div class='element-input-td inputContainer'>
                            <input type="text" id="searchKey" name="searchKey" class="width173 validate[required]"/>
                            <span id="search-btn-customer-register-id" title="" role="button"
                                  class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                                <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                                <span class="ui-button-text"></span>
                            </span>
                        </div>
                    </g:else>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x">
                        Delivery Date:
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='element-input-td inputContainer'>
                        <g:textField id="dateProposedDelivery" name="dateProposedDelivery"
                                     class="width80 validate[required]" value=""/>
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
                        <g:textField class="width100" name="customerNumber" value="" readonly="readonly"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x">
                        Customer Name:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="width300" name="customerName" value="" readonly="readonly"/>
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
                        <g:textField class="width300" name="customerAddress" value="" readonly="readonly"/>
                    </div>
                </td>
            </tr>
            <tr id="geoTr" class="element_row_content_container" hidden="hidden">
                <td>
                    <label for="territorySubArea" class="txtright bold hight1x width1x">
                        Geo Location
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:select name="territorySubArea.id"
                              class="validate[required] width165"
                              id="territorySubArea"
                              style="height: 20px;"/>
                </td>
            </tr>
           %{-- <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Select Product:
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='element-input-td inputContainer'>
                        <input type="text" id="searchProductKey" name="searchProductKey" class="width173"/>
                        <input type="hidden" id="productId" name="productId"/>
                        <input type="hidden" id="productCode"/>
                        <input type="hidden" id="product"/>
                        <span id="search-btn-customer-product-id" title="" role="button"
                              class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                            <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                            <span class="ui-button-text"></span>
                        </span>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x">
                        Product Name:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:hiddenField name="productCode" id="productCode" value=""/>
                        <g:textField class="width240" name="productName" id="productName" value="" readonly="readonly"/>
                    </div>
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Rate:
                    </label>
                </td>
                <td><g:textField class="width80" name="rate" id="rate" value="" readonly="readonly"/></td>
                <td>
                    <label class="txtright bold hight1x width1x">
                        Quantity:
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td><g:textField name="quantity" value=""/></td>
                <td>
                    <div class="buttons">
                        <span class="button"><input type="button" name="add-button" id="add-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Add"
                                                    onclick="addFinishProductToGrid();"/></span>
                    </div>
                </td>
            </tr>--}%
        </table>

        <div class="jqgrid-container">
            <table id="jqgrid-grid-finishProduct"></table>
            <div id="jqgrid-product-pager"></div>

        </div>
        <table>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Total Receivable:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="amount alin_right width100" name="receivableAmount" id="receivableAmount"
                                     value="" readonly="readonly"/>
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
                                     readonly="readonly"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x">
                        Eligible to Proceed:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        %{--<g:textField class="width100" name="eligibility" id="eligibility" value="Y"--}%
                                     %{--readonly="readonly"/>--}%
                        <a href="javascript:popupEligibility()"><span class="txtright bold hight1x width1x" id="checkbutton">
                        </span>
                        </a>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <g:hiddenField name="customerPriority" id="customerPriority" value=""/>
                    <g:hiddenField name="customerLimit" id="customerLimit" value=""/>
                    <g:hiddenField name="totalamount" id="totalamount" value=""/>
                    <g:hiddenField name="customerDeposit" id="customerDeposit" value=""/>
                </td>
            </tr>
        </table>
        <table>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Select DP:
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:select name="distributionPoint.id" id="distributionPoint" from="${distributionPointList}"
                                  optionKey="id" optionValue="name" value="" noSelection="['': 'Select DP...']"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width50" style="padding-left: 10px;">
                        Ship To:
                    </label>
                </td>
                <td>
                    <div class='element-input-td inputContainer'>
                        <input type="radio" name="shipTo" id="own" value="Own" checked> Own
                    &nbsp;&nbsp;&nbsp;<input type="radio" name="shipTo" id="ship" value="ShipAddress"> Ship Address
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x">
                        Ship to Address:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:select name="shippingAddress.id" id="shippingAddress" from="" optionKey="id" value=""
                                  noSelection="['': '']"/>
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
                                onclick="executeAjaxNewPrimaryDemandOrder();"/></span>
    <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                onclick=" clean_form('#gFormNewPrimaryDemandOrder');" value="Cancel"/></span>
</div>

