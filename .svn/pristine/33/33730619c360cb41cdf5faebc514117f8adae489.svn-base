<form name='inventoryToInventoryTransferForm' id='inventoryToInventoryTransferForm'>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr>
                <td>
                    <label class="txtright bold width70">
                        DP Name
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="dpName" value="${map.dpInfo.name}" class="validate[required] " readonly="true"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x">
                        DP ID
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="dpCode" value="${map.dpInfo.code}" class="validate[required]" readonly="true"/>
                        <g:hiddenField name="senderDp.id" id="senderDp" value="${map.dpInfo.id}" class="validate[required]"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x">
                        Transfer Date
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="transferDate" value="" class="validate[required]"/>
                    </div>
                </td>
            </tr>

            <tr>
                <td>
                    <label class="txtright bold width70">
                        DP Address
                        %{--<span class="mendatory_field">*</span>--}%
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="dpAddress" value="${map.dpInfo.address}" readonly="true"/>
                    </div>
                </td>

                <td>
                </td>
                <td>
                </td>

                <td>
                    <label class="txtright bold hight1x">
                        Transfer Challan Number
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="transferChallanNumber" value="" class="validate[required]" readonly="true"/>
                    </div>
                </td>
            </tr>
        </table>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <h4 style="margin-left: 10px;">Transfer From :</h4>
        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x">
                        Sender Inventory
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:select from="${map.inventoryList}"
                                  optionKey="id"
                                  optionValue="name"
                                  noSelection="['':'Select inventory...']"
                                  name="senderInventory.id" value=""
                                  id="senderInventory"
                                  class="validate[required] width160"
                                  onchange="getDataAndSubInventoryList(this.value);"
                        />
                    </div>
                </td>

                <td>
                    <label class="txtright bold hight1x">
                        Sender Inventory ID
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="senderInventoryId" value=""  readonly="true"/>
                    </div>
                </td>
            </tr>


            <tr>
                <td>
                    <label class="txtright bold width110">
                        Sub-Inventory
                        %{--<span class="mendatory_field">*</span>--}%
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:select class="width160" from=""
                                  optionKey="id"
                                  optionValue="name"
                                  noSelection="['':'Select sub-inventory...']"
                                  name="senderSubInventory.id" value=""
                                  id="senderSubInventory"
                                  onchange="getAllProductListBySubInventoryId(this.value);"
                        />
                    </div>
                </td>

                <td>
                    <label class="txtright bold  width125">
                        Sub-Inventory ID
                        %{--<span class="mendatory_field">*</span>--}%
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="subInventoryId"   value="" readonly="true"/>
                    </div>
                </td>

                <td>
                </td>
                <td>
                </td>
            </tr>

            <tr>
                <td>
                    <label class="txtright bold width110">
                        Inventory Address
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="inventoryAddress" class="validate[required] width230" value="" readonly="readonly" />
                    </div>
                </td>
            </tr>
        </table>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <h4 style="margin-left: 10px;">Transfer To :</h4>
        <table>
            <tr>
                <td>
                    <label class="txtright bold width130">
                        Receiver DP Name
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:select from="${receiverDp}"
                                  optionKey="id"
                                  optionValue="name"
                                  noSelection="['':'Select DP...']"
                                  name="receiverDp.id" value=""
                                  id="receiverDp"
                                  class="validate[required] width220"
                                  onchange="getReceiverDpIdAndInventory(this.value);"
                        />
                    </div>
                </td>

                <td>
                    <label class="txtright bold width150">
                        Receiver DP ID
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer width50'>
                        <g:textField name="receiverDpId"  value="" class="validate[required] width80" readonly="true"/>
                    </div>
                </td>

                <td>
                </td>
                <td>
                </td>
            </tr>

            <tr>
                <td>
                    <label class="txtright bold width130">
                        Receiver Inventory
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:select from="${com.bits.bdfp.inventory.warehouse.Warehouse.list()}"
                                  optionKey="id"
                                  optionValue="name"
                                  noSelection="['':'Select inventory...']"
                                  name="receiverInventory.id" value=""
                                  id="receiverInventory"
                                  class="validate[required] width220"
                                  onchange="getReceiverInventoryInfo(this.value);"
                        />
                    </div>
                </td>

                <td>
                    <label class="txtright bold width150">
                        Receiver Inventory ID
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="receiverInventoryId" value="" class="validate[required] width80" readonly="true"/>
                    </div>
                </td>


            </tr>
            <tr>
                <td>
                    <label class="txtright bold width130">
                        Receiver Inventory Address
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="receiverInventoryAddress" class="validate[required] width220" value="" readonly="readonly" />
                    </div>
                </td>
            </tr>
        </table>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <div class="jqgrid-container">
            <h4></h4>
            <table id="jqgrid-product-grid"></table>

            <div id="jqgrid-product-pager"></div>
        </div>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x">
                        Transfer Qty
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="transferQty" value="" class="validate[required,funcCall[checkQuantity]]"/>
                        <g:hiddenField name="transferProduct.id" id="transferProduct" value="" class="validate[required]"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x" >
                        Batch
                        %{--<span class="mendatory_field">*</span>--}%
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="batch" value="" class=""/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x">
                        Unit Price
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="unitPrice" value="" class="validate[required]"/>
                        <g:hiddenField name="dpsId" value="" class="validate[required]"/>
                    </div>
                </td>
                <td>
                    <div class="buttons">
                        <span class="button"><input type="button" name="transfer-button" id="transfer-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Transfer"
                                                    onclick="transferChallan();"/></span>

                    </div>
                </td>
            </tr>
        </table>
    </div>
</form>


