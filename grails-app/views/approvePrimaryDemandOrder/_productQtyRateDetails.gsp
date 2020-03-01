<form id="searchbox">
    <table>
        <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
            <td>
                <label class="txtright bold hight1x width100">
                    Customer Name:
                </label>

            </td>
            <td><g:textField class="width220" name="customerNameForProductSearch" id="customerNameForProductSearch"
                             readonly="readonly"/></td>
            <td>
                <label class="txtright bold hight1x width130">
                    Primary Order Number:
                </label>

            </td>
            <td><g:textField class="width130" name="orderNumberForProductSearch"
                             id="orderNumberForProductSearch" readonly="readonly"/>
            </td>
        </tr>

    </table>
</form>

<div id="editProduct" class="jqgrid-container">
    <table>

        <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
            <td>
                <label class="txtright bold hight1x width100"> Select Product </label>
            </td>
            <td>
                    <g:select name="productDropDownList" style="width: 400px; height: 20px;"
                              id="productDropDownList"
                              from=""
                              optionKey="id"
                              onchange="setPriceValue();"
                              noSelection="['': 'Select Product']" />
            </td>
        </tr>
        <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
            <td>
                <label class="txtright bold hight1x width100">
                    Product Name:
                </label>
            </td>
            <td>
                <div class='element_row_container inputContainer width260'>
                    <g:hiddenField name="customerStockId" id="customerStockId" value=""/>
                    <g:hiddenField name="productId" id="productId" value=""/>
                    <g:textField class="width240" name="productName" id="productName" value="" readonly="readonly"/>
                    <g:hiddenField class="width130" name="primaryOrderId" id="primaryOrderId" value=""/>
                </div>
            </td>
            <td>
                <label class="txtright bold hight1x width70">
                   Rate:
                </label>
            </td>
            <td>
                <div class='element_row_container inputContainer width100'>
                    <g:textField class="width80" name="rate" id="rate" value="" readonly="readonly"/>
                </div>
            </td>
            <td>
                <label class="txtright bold hight1x width90">
                    Shipment Qty:
                </label>
            </td>
            <td>
                <div class='element_row_container inputContainer width80'>
                    <g:textField class="width60" name="quantity" value=""/>
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

    <table id="jqgrid-grid-editProduct"></table>
    <div id="jqgrid-pager-editProduct"></div>

    <div class="buttons">
        <span class="button"><input type='button' name="update-button" id="update-button"
                                class="ui-button ui-widget ui-state-default ui-corner-all" value='Update'
                                onclick="executeAjaxUpdatePrimaryDemandOrder();"/></span>

    <span class="button"><input type='button' name="close-button" id="close-button"
                                class="ui-button ui-widget ui-state-default ui-corner-all" value='Close'
                                onclick="closeProductDetailsGrid();"/></span>
</div>

</div>