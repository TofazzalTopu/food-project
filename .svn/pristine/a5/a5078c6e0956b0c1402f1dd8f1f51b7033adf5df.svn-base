<%@ page import="com.bits.bdfp.inventory.sales.DistributionPoint" %>
<style>
</style>
<g:form name='gFormFinishGood' id='gFormDistributionPoint'>
    <g:hiddenField name="id" value=""/>
    <g:hiddenField name="version" value="0"/>
    <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>
    <g:hiddenField name="productId" id="productId" value=""/>
    <div id="remote-content-distributionPoint"></div>

<div>
    <div class="element_container_big">
        <div class="block-title">
            <div class="element-title input_width370">Product <span class="mendatory_field"> * </span></div>

            <div class="clear"></div>
        </div>

        <div class="block-input">
            <div class='element-input-td inputContainer width370'>
                <input type="text" id="product" name="cutomInput" class="width340 validate[required]" />
                <span id="search-btn-customer-register-id" title="" role="button"
                      class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                    <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                    <span class="ui-button-text"></span>
                </span>


            </div>
            <div class="clear"></div>
        </div>
    </div>

    <div class="element_container_big">
        <div class="block-title">
            <div class="element-title ">Quantity <span class="mendatory_field"> * </span></div>
            <div class="element-title ">Per Unit Cost Value <span class="mendatory_field"> * </span></div>
            <div class="element-title ">Confirm Unit Cost Value <span class="mendatory_field"> * </span></div>
            <div class="clear"></div>
        </div>

        <div class="block-input">
            <div class="element-input inputContainer value ">
                <g:textField class="customInput validate[required] number" name="quantity" value=""/>
            </div>
            <div class="element-input inputContainer value ">
               <g:textField contenteditable="false" class="customInput validate[required]" name="cost" value=""/>
            </div>
            <div class="element-input inputContainer value ">
                <g:textField class="customInput validate[required]" name="confirmCost" value=""/>
            </div>
            <div class="clear"></div>
        </div>
    </div>

    <div class="element_container_big">
        <div class="block-title">
            <div class="element-title ">Batched Controlled </div>
            <div class="element-title ">Batch Number</div>
            <div class="element-title ">Batch Date</div>
            <div class="element-title ">Batch Time</div>
            <div class="clear"></div>
        </div>

        <div class="block-input">
            <div class="element-input inputContainer value ">
                <input id="Yes" type="radio" name="isBatchControl" checked="checked"
                       value="true"/>Yes
                <input id="No" type="radio" name="isBatchControl" value="false"/>No
            </div>
            <div class="element-input inputContainer value ">
                <g:textField contenteditable="false" class="cutomInput" name="batchNo" id="batchNo" value=""/>
            </div>
            <div class="element-input inputContainer value ">
                <g:textField class="cutomInput " name="dateTransaction"
                             id="batchDate" value=""/>
                <script type='text/javascript'>$(document).ready(function () {
                    $('#batchDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                });</script>
            </div>
            <div class="element-input inputContainer value ">
                <g:textField class="cutomInput " name="timeTransaction"
                             id="timeTransaction" value=""/>
            </div>

            <div class="clear"></div>
        </div>
    </div>

    <div class="element_container_big">
        <div class="block-title">
            <div class="element-title width355">Destination Inventory <span class="mendatory_field"> * </span></div>
            <div class="element-title width350">Destination Sub Inventory <span class="mendatory_field">*</span></div>
            <div class="clear"></div>
        </div>

        <div class="block-input">
            <div class="element-input inputContainer width355">
                <div id="inventoryList" ></div>
                <g:hiddenField name="warehouse.id" id="warehouse" value=""/>
            </div>
            <div class="element-input inputContainer width360">
                <div id="subInventoryList"></div>
                <g:hiddenField name="subWarehouse.id" id="subWarehouse" value=""/>
            </div>
            <div class="clear"></div>
        </div>
    </div>


    <div class="clear"></div>

    <div class="buttons">
        <span class="button"><input type="button" name="search" id="search-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Add"
                                    onclick="addRowInGrid();"/></span>

    </div>

    <div class="clear"></div>

    <div id="geoLocationInfo">
        <div class="jqgrid-container">
            <table id="jqgrid-grid-finishGoodStock"></table>
        </div>
    </div>

    <g:if test="${inventoryResult}">

        <script type="text/javascript">
            jQuery(document).ready(function () {
                $("#inventoryList").empty();
                $("#inventoryList").flexbox(${inventoryResult}, {
                    watermark: "Select Inventory",
                    width: 340,
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



    <div class="clear"></div>

<div class="buttons">
<span class="button" hidden="hidden"><input type="button" name="create-button" id="create-button"
                        class="ui-button ui-widget ui-state-default ui-corner-all"
                        value="${message(code: 'default.button.create.label', default: 'Create')}"
                        onclick="executeAjaxFinishGoodStock();"/></span>
<span class="button"><input type="button" name="cancel-button" id="cancel-button"
                        class="ui-button ui-widget ui-state-default ui-corner-all"
                        onclick=" reset_form('#gFormFinishGood');" value="Done"/></span>
</div>
</div>


</g:form>
