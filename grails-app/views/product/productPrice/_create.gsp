<%@ page import="com.bits.bdfp.inventory.product.ProductPricingType; com.bits.bdfp.inventory.product.ProductPrice" %>
<style>
#jqgh_cb .cbox {
    margin-left: 0 !important;
    margin-right: 3px;
    display: inherit;
}

#jqgrid-grid-geoLocation tr td .cbox {
    margin-left: 0 !important;
    margin-top: 3px;
}

#jqgrid-grid-customer tr td .cbox {
    margin-left: 0 !important;
    margin-top: 3px;
}
</style>
<form name='gFormProductPrice' id='gFormProductPrice'>
    <g:hiddenField name="id" value="" />
    <g:hiddenField name="version" value="" />
    <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>
    <g:hiddenField name="businessUnitConfiguration.id" id="businessUnitConfiguration" value=""/>
    <div id="remote-content-productPrice"></div>
    <div class="element_row_content_container lightColorbg pad_bot0">
    %{--<fieldset  class="ui-state-default ui-corner-all">--}%
        %{--<legend class="ui-jqgrid-titlebar ui-widget-header  ui-helper-clearfix">Product Basic Information</legend>--}%
        <table>
            <tr>
                <td>
                    %{--<label for='productPricingType' class='width110'>Price List Type<span class="mendatory_field"> * </span></label>--}%
                    <label class="txtright bold hight1x width110">
                        Price List Type
                        <span class="mendatory_field">*</span>
                    </label>

                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:select name="productPricingType.id" id="productPricingType"
                                  class="validate[required]"
                                  optionKey="id" from="${productPricingTypeList}"
                                  value="" onchange="changeProductPricingType(this.value)"
                                  noSelection="['': 'Select One']"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width130">
                        Enterprise Name:<span class="mendatory_field"> * </span>
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:if test="${enterpriseList}">
                            <div id="enterpriselist" style="float: left" class="validate[required]"></div>
                            <script type="text/javascript">
                                jQuery(document).ready(function () {
                                    $("#enterpriselist").empty();
                                    $("#enterpriselist").flexbox(${enterpriseList}, {
                                        watermark: "Select Enterprise",
                                        width: 120,
                                        onSelect: function () {
                                            $("#enterpriseConfiguration").val($('#enterpriselist_hidden').val());
                                            loadBusinessUnitByEnterprise($('#enterpriselist_hidden').val());
                                            getTerritoryListByEnterprise($('#enterpriselist_hidden').val());
                                            loadCustomer($('#enterpriselist_hidden').val());
                                            $('#enterpriselist_input').removeAttr("validate[required]");
                                        }
                                    });
                                    $('#enterpriselist_input').blur(function () {
                                        if ($('#enterpriselist_input').val() == '') {
                                            $("#businessUnitList").empty();
                                            $("#businessUnitConfiguration").val("");
                                            $("#enterpriseConfiguration").val("");
                                            $("#div_price_list").html("");
                                            getTerritoryListByEnterprise('');
                                            $("#jqgrid-grid-geoLocation").jqGrid('clearGridData');
                                            $('#enterpriselist_input').addClass("validate[required]");
                                        }
                                    });
                                    $('#enterpriselist_input').addClass("validate[required]");
                                    if (${list.size() == 1}) {
                                        $("#enterpriseConfiguration").val("${list[0].id}");
                                        $('#enterpriselist').setValue("${list[0].name}");
                                        loadBusinessUnitByEnterprise("${list[0].id}");
                                        getTerritoryListByEnterprise("${list[0].id}");
                                        loadCustomer("${list[0].id}");
                                    }
                                });
                            </script>
                        </g:if>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width110">
                        Business Unit:<span class="mendatory_field"> * </span>
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer' id="businessUnitList"></div>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="txtright bold hight1x width110">
                        Price List Name:<span class="mendatory_field"> * </span>
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="validate[required]" name="name" value=""/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width130">
                        Effective Date: From<span class="mendatory_field"> * </span>
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="validate[required] width80" name="dateEffectiveFrom" value=""/>
                    </div>
                </td>
                <td>
                    %{--<div class='element-input inputContainer'>--}%
                        <label class="txtright bold hight1x width110">
                            To:
                        </label>
                    %{--<div style="float: right">--}%
                        %{--<g:textField class="width80" name="dateEffectiveTo" value=""/>--}%
                    %{--</div>--}%
                    %{--</div>--}%
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="width80" name="dateEffectiveTo" value=""/>
                    </div>
                </td>
            </tr>
        </table>
    %{--</fieldset>--}%
    </div>
</form>
<br/>
<fieldset  class="ui-state-default ui-corner-all">
    %{--<legend class="ui-jqgrid-titlebar ui-widget-header  ui-helper-clearfix">Product Price Details</legend>--}%
    <div id="div_price_list"></div>
</fieldset>
<br/>
<div id="div_product_territory">
    <div class="element_row_content_container lightColorbg pad_bot0">
%{--<fieldset  class="ui-state-default ui-corner-all">--}%
    %{--<legend class="ui-jqgrid-titlebar ui-widget-header  ui-helper-clearfix">Product Territory</legend>--}%
    <table>
        <tr>
            <td colspan="2">
                <label class="txtright bold hight1x">Enter the Following Information:</label>
            </td>
        </tr>
        <tr>
            <td>
                <label class="txtright bold hight1x width130">Select Territory:</label>
            </td>
            <td>
                <div class='element-input inputContainer'>
                    <g:select name="territoryConfiguration" id="territoryConfiguration" optionKey="id"
                              value="" onchange="loadGeographicLocationList(this.value)"/>
                </div>
            </td>
        </tr>
    </table>
    <div class="jqgrid-container">
        <table id="jqgrid-grid-geoLocation"></table>
        <div id="jqgrid-pager-geoLocation"></div>
    </div>
</div>
%{--</fieldset>--}%
</div>
<div id="div_customer_selection">
<div class="element_row_content_container lightColorbg pad_bot0">
%{--<fieldset class="ui-state-default ui-corner-all">--}%
    %{--<legend class="ui-jqgrid-titlebar ui-widget-header  ui-helper-clearfix">Customer Selection</legend>--}%
    <table style="width: 100%;">
        <tr class="element_row_content_container lightColorbg pad_bot0">
            <td>
                <label class="txtright bold hight1x width120">
                    Customer
                    <span class="mendatory_field"> * </span>
                </label>
            </td>
            <td>
                %{--<div class='element-input-td inputContainer'>--}%
                    <input type="text" id="searchKey" name="searchKey" class="width500 validate[required] "/>
                    <input type="hidden" id="customerId" name="customerId"/>
                    <span id="search-btn-customer-register-id" title="" role="button"
                          class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                        <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                        <span class="ui-button-text"></span>
                    </span>
                %{--</div>--}%
            </td>
        </tr>
        <tr class="element_row_content_container lightColorbg pad_bot0">
            <td>
                <label class="txtright bold hight1x width120">Customer Number:</label>
            </td>
            <td>
                %{--<div class='element-input inputContainer'>--}%
                    <g:textField class="validate[required]" name="customerNumber" value="" readonly="readonly"/>
                %{--</div>--}%
            </td>
            <td>
                <label class="txtright bold hight1x width110">Customer Name:</label>
            </td>
            <td>
                %{--<div class='element-input inputContainer'>--}%
                    <g:textField class="validate[required] width300" name="customerName" value="" readonly="readonly"/>
                %{--</div>--}%
            </td>
        </tr>
        <tr class="element_row_content_container lightColorbg pad_bot0">
            <td>
                <label class="txtright bold hight1x width120">Customer Address:</label>
            </td>
            <td>
                %{--<div class='element-input inputContainer'>--}%
                <g:textField class="validate[required] width500" name="customerAddress" value="" readonly="readonly"/>
                %{--</div>--}%
            </td>
            <td colspan="3">
                <span class="button"><input type="button" name="addCustomer-button" id="addCustomer-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Add Customer" onclick="addCustomerToGrid();"/></span>
            </td>
        </tr>
    </table>
    <div class="jqgrid-container">
        <table id="jqgrid-grid-customer"></table>
        %{--<div id="jqgrid-pager-customer"></div>--}%
    </div>
</div>
%{--</fieldset>--}%
</div>
<br/>
<div class="buttons">
    <span class="button"><input type="button" name="create-button" id="create-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="${message(code: 'default.button.create.label', default: 'Create')}" onclick="executeAjaxProductPrice();"/></span>
    <span class="button"><input type="button" name="cancel-button" id="cancel-button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick=" clean_form('#gFormProductPrice');" value="Cancel"/></span>
</div>

