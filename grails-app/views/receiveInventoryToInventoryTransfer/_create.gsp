<form name='receiveInventoryToInventoryTransferForm' id='receiveInventoryToInventoryTransferForm'>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <div class="jqgrid-container" style="margin-left: 5px;">
            <h4></h4>
            <table id="jqgrid-product-grid"></table>

            <div id="jqgrid-product-pager"></div>
        </div>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x" style="margin-left: 5px;">
                        Receiver Sub-Inventory
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:select name="receiverSubInventory" from=""
                                  optionKey="id" optionValue="name"
                                  noSelection="['':'Select Sub-Inventory...']"
                                  class="validate[required]" />
                        %{--<g:textField name="transferQty" value="" class="validate[required,funcCall[checkQuantity]]"/>--}%
                        <g:hiddenField name="transferId" value="" class="validate[required]"/>
                    </div>
                </td>
                <td style="width: 200px;"></td>
                <td>
                    <div class="buttons">
                        <span class="button"><input type="button" name="transfer-button" id="transfer-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Receive"
                                                    onclick="transferReceive();"/></span>

                    </div>
                </td>
            </tr>
        </table>
    </div>
</form>


