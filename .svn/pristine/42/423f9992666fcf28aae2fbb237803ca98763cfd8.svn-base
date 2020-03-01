<%@ page import="com.docu.commons.DateUtil" %>
<form name='gSearchRetailOrder' id='gSearchRetailOrder'>
    <div id="remote-content-searchRetailOrder"></div>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width100">
                        Delivery Date:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer'>
                        <g:textField name="deliveryDateSearch" id="deliveryDateSearch" class="width100" onchange="clearAllGridData()"/>
                        <span class="button"><input type="button" name="search-button" id="search-button-retailOrder"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Search"
                                                    onclick="executeRetailOrderSearch();"/></span>
                    </div>
                </td>
                <td>
                    <label class='txtright bold hight1x width170'>
                        Include Any Pending Orders:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer '>
                        <g:checkBox name="includePendingOrder" checked="checked" value="${true}" onclick="addRemoveCheckBox(this)"/>
                    </div>
                </td>
            </tr>
        </table>

        <div class="jqgrid-container">
            <table id="jqgrid-grid-retailOrder"></table>
        </div>
        <div class="clear"></div>
        <div class="buttons" style="margin-left:10px;">
            <span class="button"><input type='button' name="delete-button" id="delete-button-retailOrder"
                                        class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                        onclick="deleteAjaxMultipleRetailOrder();"/>
            </span>
        </div>
        <div class="clear"></div>
        <br/>
        <div class="jqgrid-container">
            <table id="jqgrid-grid-finishProduct"></table>
        </div>
    </div>
</form>
<form name='gSecondaryDemandOrder' id='gSecondaryDemandOrder'>
    <g:hiddenField name="customerMaster.id" id="customerId" value="${customerMaster?.id}"/>
    <g:hiddenField name="userTentativeDelivery.id" id="userTentativeDeliveryId" value="${customerMaster?.id}"/>
    <g:hiddenField name="territorySubArea.id" id="territorySubAreaId" value=""/>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width130">
                        Secondary Customer:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer'>
                        <g:textField class="width300" name="secondaryCustomer"
                                  id="secondaryCustomer" value="[${customerMaster?.code}] ${customerMaster?.name}"
                                  readonly="readonly"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width100">
                        Geo Location:
                        <span class="mendatory_field"> * </span>
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer'>
                        <g:if test="${subAreaList}">
                            <g:if test="${suAreaCount == 1}">
                                <g:textField class="width150 validate[required]" name="territorySubAreaName" id="territorySubAreaName" readonly="readonly" value="${subAreaList[0].geo_location}"/>
                                <script type="text/javascript">
                                    $(document).ready(function(){
                                        $("#territorySubAreaId").val("${subAreaList[0].id}");
                                    });
                                </script>
                            </g:if>
                            <g:else>
                                <g:select class="minWidth200 validate[required]" name="geoLocation.id"
                                          id="territorySubArea"
                                          optionKey="id" optionValue="geo_location"
                                          from="${subAreaList}" onchange="changeGeolocation(this.value)"
                                          value="" noSelection="['':'Select Geo Location']"/>
                                %{--<script type="text/javascript">--}%
                                    %{--var subAreaList = ${subAreaList};--}%
                                    %{--jQuery(document).ready(function () {--}%
                                        %{--var optionData = '<option value="">Select Geo Location</option>';--}%
                                        %{--var index = 0;--}%
                                        %{--for(index = 0; index < subAreaList.length; index++){--}%
                                            %{--optionData += '<option value="' + subAreaList[index].id + '">' + subAreaList[index].geo_location + '</option>';--}%
                                        %{--}--}%
                                        %{--$("select#territorySubArea").html(optionData);--}%
                                    %{--});--}%
                                %{--</script>--}%
                            </g:else>
                        </g:if>
                        <g:else>
                            <g:textField name="geoLocationName" readonly="readonly" value=""/>
                            <script type="text/javascript">
                                jQuery(document).ready(function () {
                                    MessageRenderer.showHeaderMessage("You have no assigned Geo Location, please assign Geo Location first.", 0)
                                });
                            </script>
                        </g:else>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width140">
                        Order Date:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer'>
                        <g:textField name="dateOrder" id="dateOrder" class="width100" readonly="readonly" value="${DateUtil.getCurrentDateFormatAsString()}"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width140">
                       Expected Delivery Date:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer'>
                        <g:textField name="dateDeliver" id="dateDeliver" class="width100" readonly="readonly"/>
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <div class="clear"></div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input name="clearFormButtonRetailOrder"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" type="button"
                                    onclick=" generateSecondaryDetailsFromRetailOrder();" value="Generate Secondary Order"/>
        </span>
    </div>
    <div class="jqgrid-container blue_grid">
        <table id="jqgrid-grid-secondaryOrderDetails"></table>
    </div>
    <div class="clear"></div>
    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type='button' name="save-button" id="save-button-secondaryOrder"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Save'
                                    onclick="executeAjaxSecondaryDemandOrder(false);"/>
        </span>
        <span class="button"><input type='button' name="cancel-button" id="cancel-button-secondaryOrder"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Cancel'
                                    onclick="cancelSecondaryOrder();"/>
        </span>
        <span class="button"><input type='button' name="submit-button" id="submit-button-secondaryOrder"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Submit'
                                    onclick="executeAjaxSecondaryDemandOrder(true);"/>
        </span>
    </div>
    <h3>Add Addition Product if Any</h3>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width120">
                        Select Product:
                    </label>
                </td>
                <td>
                    <input type="text" id="searchProductKey" name="searchProductKey" class="width400"/>
                    <input type="hidden" id="productId" name="productId"/>
                    <input type="hidden" id="productCode" />
                    <input type="hidden" id="product" />
                    <span id="search-btn-customer-product-id" title="" role="button"
                          class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                        <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                        <span class="ui-button-text"></span>
                    </span>
                    %{--</div>--}%
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width120">
                        Product Name:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer width260'>
                        <g:hiddenField name="productCode" id="productCode" value=""/>
                        <g:textField class="width240" name="productName" id="productName" value="" readonly="readonly"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width100">
                        Rate:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer width100'>
                        <g:textField class="width80" name="rate" id="rate" value="" readonly="readonly"/>
                    </div>
                </td>
                <td>
                    <div class='element_row_container inputContainer width100'>
                        <g:hiddenField class="width80" name="qtyInLtr" id="qtyInLtr" value="" readonly="readonly"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width100">
                        Quantity:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer width120'>
                        <g:textField class="width100" name="quantity" value=""/>
                    </div>
                </td>
                <td>
                    <div class="buttons">
                        <span class="button"><input type="button" name="add-button" id="add-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Add"
                                                    onclick="addFinishProductToGrid();"/></span>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</form>