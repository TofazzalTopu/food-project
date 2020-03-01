<%@ page import="com.bits.bdfp.inventory.sales.DistributionPoint" %>
<style>
.customLabel {
    background-color: #d7dfe7;
    color: #000000;
    display: block;
    float: left;
    font-size: 11px;
    font-weight: bold;
    height: 17px;
    margin: 0;
    padding: 5px 10px 0;
    width: 160px;
}

.customLabel4 {
    color: #000000;
    display: block;
    float: left;
    font-size: 11px;
    font-weight: bold;
    height: 17px;
    margin: 0;
    padding: 5px 10px 0;
    width: 160px;
}

.customLabel1 {
    background-color: #d7dfe7;
    color: #000000;
    display: block;
    float: left;
    font-size: 11px;
    font-weight: bold;
    height: 17px;
    margin-left: 4px;
    padding: 5px 10px 0;
    width: 160px;
}

.cutomInput {
    height: 18px;
    width: 160px;
}
</style>
<g:form name='gFormFinishGood' id='gFormDistributionPoint'>
    <g:hiddenField name="id" value="${finishGoodWarehouse?.id}"/>
    <g:hiddenField name="version" value="${finishGoodWarehouse?.version}"/>
    <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>
    <g:hiddenField name="productId" id="productId" value=""/>
    <div id="remote-content-distributionPoint"></div>
    <fieldset>
        <div style="float:left;">
            <table>
                <tr>
                    <td>
                        <table id="itemSpecification">

                            <tr>

                                <td>
                                    <label style="padding-right: 5px;" for='countryInfo' class='customLabel'><g:message
                                            code='division.countryInfo.label' default='Product'/></label>

                                    <div class='element-input inputContainer'>
                                        <div class="element-input inputContainer">
                                            <input type="text"  id="product" class="cutomInput" style="width: 123px !important;"/>

                                            <span id="search-btn-customer-register-id" title="" role="button"
                                                  class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                                                <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                                                <span class="ui-button-text"></span>
                                            </span>
                                        </div>

                                        <g:if test="${inventoryResult}">

                                            <script type="text/javascript">
                                                jQuery(document).ready(function () {
                                                    $("#inventoryList").empty();
                                                    $("#inventoryList").flexbox(${inventoryResult}, {
                                                        watermark: "Select Inventory",
                                                        width: 140,
                                                        onSelect: function () {
                                                            $("#warehouse").val($('#inventoryList_hidden').val());
                                                            selectSubInventory($('#inventoryList_hidden').val());
                                                        }

                                                    });
                                                    $('#inventoryList_input').val("");
                                                    $('#inventoryList_input').addClass("validate[required]");

                                                    $('#inventoryList_input').blur(function () {
                                                        if ($('#inventoryList_input').val() == '') {
                                                            $("#warehouse").val("");
                                                        }
                                                    });

                                                });
                                            </script>

                                        </g:if>

                                    </div>

                                </td>

                                <td>
                                    <label style="padding-right: 5px;" for='countryInfo' class='customLabel'><g:message
                                            code='division.countryInfo.label' default='Quantity'/></label>

                                    <div class='element-input inputContainer'>
                                        <g:textField class="cutomInput validate[required] number" name="quantity" value=""/>
                                    </div>

                                </td>
                                <td>

                                    <label style="padding-right: 5px;" for='countryInfo' class='customLabel'><g:message
                                            code='division.countryInfo.label' default='Per Unit Cost Value*'/></label>

                                    <div class='element-input inputContainer'>
                                        <g:textField contenteditable="false" class="cutomInput validate[required]"
                                                     name="cost" value=""/>
                                    </div>

                                </td>
                                <td>

                                    <label style="padding-right: 5px;" for='countryInfo' class='customLabel'><g:message
                                            code='division.countryInfo.label'
                                            default='Confirm Unit Cost Value*'/></label>

                                    <div class='element-input inputContainer'>
                                        <g:textField class="cutomInput validate[required]" name="confirmCost" value=""/>
                                    </div>

                                </td>
                            </tr>

                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table>

                            <tr>
                                <td>

                                    <label style="padding-right: 5px;" for='countryInfo' class='customLabel'><g:message
                                            code='division.countryInfo.label'
                                            default='Transaction Reference No'/></label>

                                    <div class='element-input inputContainer'>
                                        <g:textField class="cutomInput" placeholder="Auto Generate"
                                                     disabled="disabled" name="transactionNo" value=""/>
                                    </div>

                                </td>
                                <td>

                                    <label style="padding-right: 5px;" for='countryInfo' class='customLabel'><g:message
                                            code='division.countryInfo.label' default='Batch Number'/></label>

                                    <div class='element-input inputContainer'>
                                        <g:textField contenteditable="false" class="cutomInput"
                                                     name="batchNo" value=""/>
                                    </div>

                                </td>
                                <td>
                                    <label style="padding-right: 5px;" for='countryInfo' class='customLabel'><g:message
                                            code='division.countryInfo.label' default='Batch Controlled'/></label>

                                    <div class='element-input inputContainer'>
                                        <input id="Yes" type="radio" name="isBatchControl" checked="checked"
                                               value="true"/>Yes
                                        <input id="No" type="radio" name="isBatchControl" value="false"/>No
                                    </div>

                                </td>

                            </tr>
                            <tr>

                                <td>

                                    <label style="padding-right: 5px;" for='countryInfo' class='customLabel'><g:message
                                            code='division.countryInfo.label' default='Batch Date'/></label>

                                    <div class='element-input inputContainer'>
                                        <g:textField class="cutomInput " name="dateTransaction"
                                                     id="batchDate" value=""/>
                                        <script type='text/javascript'>$(document).ready(function () {
                                            $('#batchDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                                        });</script>
                                    </div>

                                </td>
                                <td>

                                    <label style="padding-right: 5px;" for='countryInfo' class='customLabel'><g:message
                                            code='division.countryInfo.label' default='Batch Time'/></label>

                                    <div class='element-input inputContainer'>
                                        <g:textField class="cutomInput " name="timeTransaction"
                                                     id="timeTransaction" value=""/>
                                    </div>

                                </td>
                                <td>

                                    <label style="padding-right: 5px;" for='countryInfo' class='customLabel'><g:message
                                            code='division.countryInfo.label'
                                            default='Product Reference Number'/></label>

                                    <div class='element-input inputContainer'>
                                        <g:textField class="cutomInput" placeholder="Auto Generate"
                                                     disabled="disabled" name="mobile" value=""/>
                                    </div>

                                </td>

                            </tr>
                            <tr>

                                <td>

                                    <label style="padding-right: 5px;" for='countryInfo' class='customLabel'><g:message
                                            code='division.countryInfo.label' default='Destination Inventory*'/></label>

                                    <div class='element-input inputContainer'>
                                        <div id="inventoryList"></div>
                                        <g:hiddenField name="warehouse.id" id="warehouse" value=""/>
                                    </div>

                                </td>
                                <td>

                                    <label style="padding-right: 5px;" for='countryInfo' class='customLabel'><g:message
                                            code='division.countryInfo.label'
                                            default='Destination Sub-Inventory'/></label>

                                    <div class='element-input inputContainer'>
                                        <div id="subInventoryList"></div>
                                        <g:hiddenField name="subWarehouse.id" id="subWarehouse" value=""/>
                                    </div>

                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>

                %{--end item info here --}%

                <tr>
                    <td>
                        <div class="buttons">
                            <span class="button"><input type="button" name="search" id="search-button"
                                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                                        value="Add"
                                                        onclick="addRowInGrid();"/></span>

                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div id="geoLocationInfo">
                            <div class="jqgrid-container">
                                <table id="jqgrid-grid-finishGoodStock"></table>
                            </div>
                        </div>
                    </td>
                </tr>

            </table>

        </div>

        <div style="clear:both;"></div>
        <br/>
    </fieldset>

    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxFinishGoodStock();"/></span>
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick=" reset_form('#gFormFinishGood');" value="Cancel"/></span>
    </div>

</g:form>
