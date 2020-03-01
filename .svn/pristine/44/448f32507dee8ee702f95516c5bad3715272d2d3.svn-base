<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder" %>
<form name='gFormSecondaryDemandOrderUpdate' id='gFormSecondaryDemandOrderUpdate'>
<g:hiddenField name="id" value="${id}"/>
<g:hiddenField name="version" value="${secondaryDemandOrder?.version}"/>
<g:hiddenField name="enterpriseConfiguration" id="enterpriseConfiguration"/>

<div id="remote-content-secondaryDemandOrderUpdate"></div>
<br>

<div class="element_row_content_container lightColorbg pad_bot0">
<table>

<tr class="element_row_content_container lightColorbg pad_bot0">
    <td>
        <label class="txtright bold hight1x width105">
            <g:message code="secondaryDemandOrder.enterpriseConfiguration.label"
                       default="Enterprise "/>
            <span class="mendatory_field">*</span>
        </label>

    </td>

    <g:if test="${result}">
        <g:if test="${list.size() == 1}">
            <td>
                <g:textField name="enterPriseName" id="enterPriseName" readonly="readonly" value="${list[0].name}"
                             class="width200"/>
                <g:hiddenField name="enterpriseConfiguration" id="enterpriseConfiguration" value="${list[0].id}"/>
                <script type="text/javascript">
                    jQuery(document).ready(function () {
                        $("#enterpriseConfiguration").val("${list[0].id}");
                        %{--loadCustomer(${list[0].id})--}%
                        %{--loadProduct(${list[0].id})--}%
                    });
                </script>
            </td>
        </g:if>
        <g:else>

            <td><div id="enterpriselist" style="width: 300px;"></div></td>
            <script type="text/javascript">
                %{--alert("${result}");--}%
                jQuery(document).ready(function () {

                    $("#enterpriselist").empty();

                    $("#enterpriselist").flexbox(${result}, {
                        watermark: "Select Enterprise",
                        width: 260,
                        onSelect: function () {
                            $("#enterpriseConfiguration").val($('#enterpriselist_hidden').val());

//                                        loadCustomer();
//                                        loadProduct($('#enterpriselist_hidden').val());
                        }
                    });
                    $('#enterpriselist_input').addClass("validate[required]");


                    $('#enterpriselist_input').blur(function () {
                        if ($('#enterpriselist_input').val() == '') {
                            $("#enterpriseId").val("");
                            $("#enterpriseConfiguration").val("");
                        }
                    });
                });
            </script>
        </g:else>
    </g:if>
    <g:else>
        <td>
            <g:textField name="enterPriseName" readonly="readonly" value=""/>
            <script type="text/javascript">
                jQuery(document).ready(function () {
                    MessageRenderer.showHeaderMessage("You have no assigned enterprise, please assign enterprise first.", 0)
                });
            </script>
        </td>
    </g:else>
    <td>
        <label class="txtright bold hight1x width100">
            <g:message code="secondaryDemandOrder.businessUnitConfiguration.label"
                       default="Customer"/>
            <span class="mendatory_field">*</span>
        </label>
    </td>
    <td><div class='element-input-td inputContainer'>
        <input type="text" id="searchKey" name="searchKey" class="width300 validate[required] " readonly="readonly"/>
        <input type="hidden" id="customerMaster" name="customerMaster.id"/>
        %{--<span id="search-btn-customer-register-id" title="" role="button"--}%
        %{--class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">--}%
        %{--<span class="ui-button-icon-primary ui-icon ui-icon-search"></span>--}%
        %{--<span class="ui-button-text"></span>--}%
        %{--</span>--}%
    </div>
    </td>
</tr>

%{--<tr class="element_row_content_container inputContainer lightColorbg pad_bot0">--}%
%{--<td>--}%
%{--<label class="txtright bold hight1x width1x" style="width: 105px;">--}%
%{--<g:message code="secondaryDemandOrder.countryInfo.label" default="Customer Name"/>--}%
%{--<span class="mendatory_field"> * </span>--}%
%{--</label>--}%

%{--</td>--}%
%{--<td><g:textField  readonly="readonly"  name="name" id="name" style="width:300px;"/></td>--}%
%{--</tr>--}%

<tr class="element_row_content_container inputContainer lightColorbg pad_bot0">

    <td>
        <label class="txtright bold hight1x width105">
            <g:message code="secondaryDemandOrder.division.label" default="Order Placed By"/>
            <span class="mendatory_field">*</span>
        </label>

    </td>
    <td><g:textField name="orderBy" value="${applicationUser?.username}" readonly="readonly" class="width200"/></td>

    <td>
        <label class="txtright bold hight1x width130">
            <g:message code="secondaryDemandOrder.district.label" default="Tentative Delivery Man"/>
        </label>

    </td>
    <td><div class='element-input-td inputContainer'>
        <input class="width250" type="text" id="searchDeliveryKey" name="searchDeliveryKey" readonly="readonly"/>
        <input type="hidden" id="userTentativeDelivery" name="userTentativeDelivery.id"/>
        %{--<span id="search-btn-customer-delivery-id" title="" role="button"--}%
        %{--class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">--}%
        %{--<span class="ui-button-icon-primary ui-icon ui-icon-search"></span>--}%
        %{--<span class="ui-button-text"></span>--}%

        %{--</span>--}%

    </div>

    </td>
</tr>

<tr class="element_row_content_container lightColorbg pad_bot0">
    <script type="text/javascript">
        jQuery(document).ready(function () {
//                        $("#orderDate, #deliveryDate").datepicker(
//                                { dateFormat: 'dd-mm-yy',
//                                    changeMonth:true,
//                                    changeYear:true
//                                });
//                        alert('ok');
            setDateRangeNoLimit('orderDate', 'deliveryDate');
            $('#orderDate').change(function () {
                var fromDate = DocuDateUtil.createDateFromString($(this).val());
                var toDate = DocuDateUtil.createDateFromString($('#deliveryDate').val());

                if ($(this).val() && $('#deliveryDate').val()) {
                    if (toDate < fromDate) {
                        $(this).css('border-color', '#D42525');
                        MessageRenderer.render({
                            "messageBody": "Order date cannot greater than Delivery date.",
                            "messageTitle": "Create Demand Order",
                            "type": "0"
                        });
                    } else {
                        $(this).css('border', '');
                    }
                }
            });

            $('#deliveryDate').change(function () {
                var fromDate = DocuDateUtil.createDateFromString($('#orderDate').val());
                var toDate = DocuDateUtil.createDateFromString($(this).val());

                if ($(this).val()) {
                    if (fromDate > toDate) {
                        $(this).css('border-color', '#D42525');
                        MessageRenderer.render({
                            "messageBody": "Delivery date cannot less than Order date.",
                            "messageTitle": "Create Demand Order",
                            "type": "0"
                        });
                    } else {
                        $(this).css('border', '');
                    }
                }
            });

            $('#orderDate, #deliveryDate').focusout(function () {
                var fromDate = DocuDateUtil.createDateFromString($('#orderDate').val());
                var toDate = DocuDateUtil.createDateFromString($('#deliveryDate').val());

                if (!((fromDate > toDate) && (toDate < fromDate))) {
                    $('#orderDate').css('border', '');
                    $('#deliveryDate').css('border', '');
                }
            });

            $('#orderDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
            $('#deliveryDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        });
    </script>
    <td>
        <label class="txtright bold hight1x width105">
            <g:message code="secondaryDemandOrder.orderDate.label"
                       default="Order Date"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer'><g:textField name="orderDate" id="orderDate"
                                                               class="validate[required]" style="width: 100px"
                                                               value="" readonly="readonly"
                                                               placeholder=""/></div>
    </td>


    <td style="padding-left: 2%">
        <label class="txtright bold hight1x width105">
            <g:message code="secondaryDemandOrder.deliveryDate.label" default="Delivery Date"/>
            <span class="mendatory_field">*</span>
        </label>

    </td>
    <td>
        <div class='element-input inputContainer'><g:textField name="deliveryDate" id="deliveryDate"
                                                               class="validate[required]"
                                                               value=""/></div>

    </td>
</tr>

</table>

<table style="width: 100%;">

    <tr class="element_row_content_container lightColorbg pad_bot0">
        <td>
            <label class="txtright bold hight1x width105">
                <g:message code="secondaryDemandOrder.product.label"
                           default="Product"/>
            </label>

        </td>
        <td><input type="text" id="searchProductKey" name="searchProductKey" class="width500"/>
            <input type="hidden" id="detailsOrder" name="detailsOrder"/>
            <input type="hidden" id="finishProduct" name="finishProduct.id"/>
            <input type="hidden" id="productCode"/>
            <input type="hidden" id="product"/>
            <span id="search-btn-customer-product-id" title="" role="button"
                  class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                <span class="ui-button-text"></span>
            </span></td>
    </tr>

    <tr class="element_row_content_container lightColorbg pad_bot0">
        <td>
            <label class="txtright bold hight1x width105">
                <g:message code="secondaryDemandOrder.rate.label" default="Rate"/>
            </label>

        </td>
        <td><g:textField class="width150" name="rate" id="rate" value="" readonly="readonly"/></td>
        <td>
            <label class="txtright bold hight1x width105">
                <g:message code="secondaryDemandOrder.qty.label" default="Quantity"/>
            </label>
        </td>
        <td><g:textField class="width150" name="qty" value=""/></td>
        <td>
            <div class="buttons">
                <span class="button"><input type="button" name="add-button" id="add-button"
                                            class="ui-button ui-widget ui-state-default ui-corner-all"
                                            value="Add"
                                            onclick="addNewItemToCollectionGrid();"/></span>
                <span class="button"><input type='button' name="delete-button" id="remove-button"
                                            class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                            onclick="deleteProduct();"/></span>
            </div>
        </td>
    </tr>
</table>

<div class="jqgrid-container">
    <table id="jqgrid-grid"></table>

    <div id="jqgrid-pager"></div>
</div>
</div>
<br/>

<div class="buttons">
    <span class="button"><input type="button" name="submit-button" id="submit-button"
                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                value="Submit"
                                onclick="executeUpdateAjaxSecondaryDemandOrder();"/></span>

    <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                onclick=" reset_form('#gFormSecondaryDemandOrderUpdate');" value="Cancel"/></span>
</div>
</form>

