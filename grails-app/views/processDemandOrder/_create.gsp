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
    <g:hiddenField name="id" value="${distributionPoint?.id}"/>
    <g:hiddenField name="version" value="${distributionPoint?.version}"/>
    <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>
    <g:hiddenField name="customerId" value=""/>
    <div id="remote-content-distributionPoint"></div>
%{--<fieldset>--}%
    <div style="float:left;">
        <table style="width: auto">
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
                        <label for="userOrderPlaced" class="txtright bold hight1x width1x"
                               style="width: 160px;"><g:message
                                code="primaryDemandOrder.userOrderPlaced.label"
                                default="Enterprise"/>
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <g:select name="ent"
                                  class="validate[required]"
                                  style="width: 350px; height: 20px;" id="ent"
                                  optionKey="id" value="" noSelection="['': 'Please Select']"
                                  onchange="setId(this.value);"/>
                    </td>
                </tr>
                <script type="text/javascript">
                    $(document).ready(function () {
                        $('#ent').val('');
                        var options = '<option value="">Please Select</option>';
                        for (var i = 0; i < ${size}; i++) {
                            options += '<option value="' + ${enterpriseList}[i].id + '">' + ${enterpriseList}[i].name + '</option>';
                        }
                        $("#ent").html(options);
                    });
                </script>
            </g:else>
            <tr>
                <td>
                    <table style="width: 100%;">

                        <tr class="element_row_content_container lightColorbg pad_bot0">
                            <td>
                                <label class='txtright bold hight1x width100'>
                                    Please Select:
                                </label>

                                <div class='element-input inputContainer'>
                                    <select id="orderSelect" onchange="showHideInput();">
                                        <option value="no">Select one</option>
                                        <option value="date" selected="selected">Delivery Date</option>
                                        <option value="number">Order Number</option>

                                    </select>
                                </div>

                            </td>
                            <td>
                                <label class='txtright bold hight1x width150'>
                                    Order No/Delivery Date:
                                </label>

                                <div class='element-input inputContainer'>
                                    <g:textField class="cutomInput"
                                                 id="dummy" name="dummy" value=""/>

                                    <g:textField class="cutomInput validate[required] "
                                                 id="deliveryDate" style="display: none;" name="deliveryDate" value=""/>

                                    <g:textField class="cutomInput validate[required]"
                                                 id="orderNo" name="orderNo" style="display: none;" value=""/>

                                </div>

                            </td>
                            <td>
                                <div class='element-input inputContainer'>
                                    <input type="checkbox" id="pendingOrder"> Include Pending Order
                                </div>

                            </td>

                        </tr>

                    </table>
                </td>
            </tr>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <g:if test="${warehouseList}">
                    <td>
                        <label for="warehouse" class="txtright bold hight1x width100"
                               style="width: 160px;">Inventory:
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <g:select name="warehouse"
                                  class="validate[required]"
                                  style="width: 200px; height: 20px;" id="warehouseId"
                                  optionKey="id" value="${warehouseList}"/>
                    </td>
                </g:if>
                <g:if test="${salesChannelList}">
                    <td>
                        <label for="salesChannel" class="txtright bold hight1x width100"
                               style="width: 160px;">Sales Channel:
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <g:select name="salesChannel"
                                  class="validate[required]"
                                  style="width: 200px; height: 20px;" id="salesChannel"
                                  optionKey="id" value="${salesChannelList}"/>
                    </td>
                </g:if>
            </tr>

            <script type="text/javascript">
                $(document).ready(function () {
                    $('#warehouseId').val('');
                    var options = '';
                    for (var i = 0; i < ${size}; i++) {
                        options += '<option value="' + ${warehouseList}[i].id + '">' + ${warehouseList}[i].name + '</option>';
                    }
                    $("#warehouseId").html(options);

                    $('#salesChannel').val('');
                    var options = '<option value="">Please Select</option>';
                    for (var i = 0; i < ${salesChannelSize}; i++) {
                        options += '<option value="' + ${salesChannelList}[i].id + '">' + ${salesChannelList}[i].name + '</option>';
                    }
                    $("#salesChannel").html(options);
                });
            </script>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="calculateBonus" class="txtright bold hight1x width100"
                           style="width: 160px;">Calculate Bonus:
                    </label>
                </td>
                <td>
                    <g:checkBox name="calculateBonus" id="calculateBonus" value="true"
                                onclick="calculateChange();"/>
                </td>
                <td>
                    <label for='customerList' class='txtright bold hight1x width1x width100' style="width: 160px;">
                        Search Customer
                    </label>
                </td>
                <td>
                    <div class="element-input inputContainer width230">
                        <input type="text" id="customerList" name="customerList" class="width200"/>
                    </div>
                </td>
                <td>
                    <label for='legacyId' class='txtright bold hight1x width1x  width100' style="width: 160px;">
                        Legacy ID
                    </label>
                </td>
                <td>
                    <div class="element-input inputContainer width100">
                        <input type="text" id="legacyId" name="legacyId" class="width100"/>
                    </div>
                </td>

            </tr>
            <tr>
                <td>
                    <div class="buttons">
                        <span class="button"> <input type="button" name="search" id="search-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Search"
                                                    onclick="orderDetailsInfo();"/></span>
                    </div>
                </td>
            </tr>
        </table>
        <table>
            <tr>
                <td style="width: 40%">
                    <div id="orderAvailable" class="width600">
                        <div class="jqgrid-container">
                            <table id="jqgrid-grid-primaryOrder"></table>
                            <div id="jqgrid-primaryOrder-pager"></div>

                        </div>
                    </div>
                </td>
                <td style="width: 60%">
                    <div id="itemAvailable">

                        <table id="jqgrid-grid-item-available"></table>

                    </div>
                </td>
            </tr>
        </table>

    </div>

    <div style="clear:both;"></div>
    <br/>
%{--</fieldset>--}%

    <div class="buttons">
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick="checkItemQuantity();" value="Check Quantity"/></span>


        <span class="button"><input type='button' name="process-button" id="udd-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value='Update Delivery Date'
                                    onclick="UpdateDeliveryDate();"/></span>

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
