<form name='gSecondaryDemandOrder' id='gSecondaryDemandOrder'>
    <g:hiddenField name="id" value="${secondaryDemandOrder?.id}"/>
    <g:hiddenField name="version" value="${secondaryDemandOrder?.version}"/>
    <g:hiddenField name="customerMaster.id" id="customerId" value="${customerMaster?.id}"/>
    <g:hiddenField name="userTentativeDelivery.id" id="userTentativeDeliveryId" value="${userTentativeDelivery?.id}"/>
    <g:hiddenField name="territorySubArea.id" id="territorySubAreaId" value="${territorySubArea?.id}"/>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width130">
                        Secondary Order No:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer'>
                        <g:textField class="width100" name="orderNoTemp"
                                     id="orderNo" value="${secondaryDemandOrder?.orderNo}"
                                     readonly="readonly"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width130">
                        Secondary Order Date:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer'>
                        <g:textField class="width80" name="orderDate" id="orderDate" readonly="readonly" value="${orderDate}"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width140">
                        Expected Delivery Date:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer'>
                        <g:textField class="width100" name="dateDeliver" id="dateDeliver" value="${deliveryDate}"/>
                    </div>
                </td>
            </tr>
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
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer'>
                        <g:textField class="width250 validate[required]" name="territorySubAreaName" id="territorySubAreaName" readonly="readonly" value="${territorySubArea?.geoLocation}"/>
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <div class="clear"></div>

    <div class="jqgrid-container blue_grid">
        <table id="jqgrid-grid-finishProduct"></table>
    </div>
    <div class="clear"></div>
    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="update-button" id="update-button-retailOrder"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.update.label', default: 'Update')}"
                                    onclick="executeAjaxSecondaryOrder(false);"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button-retailOrder"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxSecondaryOrder();"/></span>
        <span class="button"><input name="submitRetailOrder"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" type="button"
                                    onclick="executeAjaxSecondaryOrder(true);" value="Submit"/>
        </span>
        <span class="button"><input name="clearFormButtonRetailOrder"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" type="button"
                                    onclick=" reset_formSecondaryOrder();" value="Cancel"/>
        </span>
    </div>
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