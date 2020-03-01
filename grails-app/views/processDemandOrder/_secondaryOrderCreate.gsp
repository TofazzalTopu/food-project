<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.inventory.sales.DistributionPoint" %>
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
    width: 170px;
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
<script type="text/javascript">
    jQuery(document).ready(function () {
        setDateRangeNoLimit('deliveryDateFrom','deliveryDateTo');
        $('#dateFrom').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        $('#dateTo').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
    });
</script>
<g:form name='gFormFinishGood' id='gFormDistributionPoint'>
    <g:hiddenField name="id" value="${distributionPoint?.id}"/>
    <g:hiddenField name="version" value="${distributionPoint?.version}"/>
    <g:hiddenField name="demandOrderId" id="demandOrderId" value=""/>
    <g:hiddenField name="demandOrderSecondaryId" id="demandOrderSecondaryId" value=""/>
    <div id="remote-content-distributionPoint"></div>
    <fieldset>
        <div style="float:left;">
            <table>
                <tr>
                    <td>
                        <label style="padding-right: 5px;" for='demandOrder' class='customLabel'>
                            Primary Demand Order:
                        </label>
                        <div class='element-input inputContainer'>
                            <g:textField  class="cutomInput"
                                          id="demandOrder"   name="demandOrder" value=""/>
                        </div>
                    </td>
                    <td>
                        <label style="padding-right: 5px;" for='demandOrder' class='customLabel'>
                            Secondary Demand Order:
                        </label>
                        <div class='element-input inputContainer'>
                            <g:textField  class="cutomInput"
                                          id="demandOrderSecondary"   name="demandOrderSecondary" value=""/>
                        </div>
                    </td>
                </tr>

                <tr>
                    <td>
                        <label style="padding-right: 5px;" for='invoiceNo' class='customLabel'>
                            Invoice Number:
                        </label>
                        <div class='element-input inputContainer' >
                            <g:textField  class="cutomInput"
                                          id="invoiceNo"  disabled="disabled" name="invoiceNo"  value=""/>

                        </div>
                    </td>
                    <td>
                        <label style="padding-right: 5px;" for='retriveOrderNumber' class='customLabel'>
                            Order Number:
                        </label>
                        <div class='element-input inputContainer' >
                            <g:textField  class="cutomInput"
                                          id="retriveOrderNumber"  disabled="disabled" name="retriveOrderNumber"   value=""/>
                        </div>

                    </td>
                </tr>
                <tr>
                    <td>
                        <label style="padding-right: 5px;" for='deliveryDateFrom' class='customLabel'>
                            Expected Delivery Date From:
                        </label>
                        <div class='element-input inputContainer' >
                            <g:textField  class="cutomInput"
                                          id="deliveryDateFrom"  name="deliveryDateFrom"   value=""/>

                        </div>
                    </td>
                    <td>
                        <label style="padding-right: 5px;" for='deliveryDateTo' class='customLabel'>
                            To:
                        </label>
                        <div class='element-input inputContainer' >
                            <g:textField  class="cutomInput"
                                          id="deliveryDateTo"  name="deliveryDateTo"   value=""/>

                        </div>
                    </td>
                </tr>
                <g:if test="${warehouseList}">
                    <tr class="element_row_content_container lightColorbg pad_bot0">
                        <td>
                            <label for="warehouse" class="txtright bold hight1x width100"
                                   style="width: 160px;">Inventory:
                                <span class="mendatory_field">*</span>
                            </label>
                        </td>
                        <td>
                            <g:select name="warehouse"
                                      class="validate[required]"
                                      style="width: 350px; height: 20px;" id="warehouseId"
                                      optionKey="id"/>
                        </td>
                    </tr>
                    <script type="text/javascript">
                        $(document).ready(function () {
                            $('#warehouseId').val('');
                            var options = '';
//                                        '<option value="">Please Select</option>';
                            for(var i = 0; i < ${warehouseCount}; i++){
                                options += '<option value="' + ${warehouseList}[i].id + '">' + ${warehouseList}[i].name + '</option>';
                            }
                            $("#warehouseId").html(options);
                        });
                    </script>
                </g:if>
                <tr>
                    <td>
                        <div class="buttons">
                            <span class="button"><input type="button" name="search" id="search-button"
                                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                                        value="Search"
                                                        onclick="orderDetailsInfo();"/></span>
                        </div>
                    </td>
                </tr>
            </table>
            <table>
                <tr>
                    <td>
                        <div id="orderAvailable">
                            <div class="jqgrid-container">
                                <table id="jqgrid-grid-secondaryOrder"></table>

                            </div>
                        </div>
                    </td>
                    <td>
                        <div id="itemAvailable">

                            <table id="jqgrid-grid-item-available"></table>


                        </div>
                    </td>
                </tr>
            </table>

        </div>

        <div style="clear:both;"></div>
        <br/>
    </fieldset>

    <div class="buttons">
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick="checkItemQuantity();" value="Check Quantity"/></span>

        <span class="button"><input type='button' name="process-button" id="process-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Process Order'
                                    onclick="processOrder();"/></span>
    </div>
    <div style="display: none;">
        <a href="#fancy-update-order-display-block" id="fancy-update-order-div"></a>
    </div>

    <div style="display: none;">
        <div id="fancy-update-order-display-block" style="width: 800px;height: 500px;">

        </div>
    </div>


    <div style="display: none;">
        <a href="#fancy-product-batch-display-block" id="fancy-product-batch-div"></a>
    </div>

    <div style="display: none;">
        <div id="fancy-product-batch-display-block" style="width: 800px;height: 500px;">

        </div>
    </div>
</g:form>
