<div class="element_row_content_container lightColorbg pad_bot0">
    <table>
        <tr>
            <td>
                <label class="txtright bold hight1x width145">
                    Select DP
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:select name="damageDistributionPoint"
                              from=""
                              optionKey="id"

                              class="validate[required] width180"
                              onchange="getDamageInventoryListByDp(this.value)"/>

                </div>
            </td>
        </tr>
    </table>
</div>

<div class="element_row_content_container lightColorbg pad_bot0">
    <table>
        <tr>
            <td>
                <label class="txtright bold hight1x width145">
                    Select Inventory
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:select name="damageInventory"
                              from="[]"
                              noSelection="['': 'Select Inventory...']"
                              class="validate[required] width180"
                              onchange="getDamageSubInventoryLisByInventory(this.value);"/>

                </div>
            </td>
            <td>
                <label class="txtright bold hight1x width1x" style="margin-left: 10px;">
                    Select Sub Inventory
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:select name="damageSubInventory"
                              from="[]"
                              noSelection="['': 'Select Sub Inventory...']"
                              class="validate[required] width155"
                              onchange="getDamageProductListBySubInventory(this.value);"/>

                </div>
            </td>
        </tr>

        <tr>
            <td colspan="2">
                <div class="jqgrid-container width350">
                    <table id="jqgrid-grid-damageInvProducts"></table>

                    <div id="jqgrid-pager-damageInvProducts"></div>
                </div>
            </td>
            <td>
                <label class="txtright bold hight1x width1x" style="margin-left: 10px;">
                    Remarks
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="damageRemarks" value=""/>
                </div>
            </td>
        </tr>

        <tr>
            <td>
                <label class="txtright bold hight1x width145">
                    Product Names
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="selectDamageProduct" class="validate[required] width152"/>
                    <div style="display: inline-block; margin-bottom: -4px;">
                        <span id="search-btn-damage-product-id" title="" role="button"
                              class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatL">
                            <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                            <span class="ui-button-text"></span>
                        </span>
                    </div>
                    <g:hiddenField name="damageProduct" class="validate[required]"/>
                </div>
            </td>
            <td>
                <label class="txtright bold hight1x width1x" style="margin-left: 10px;">
                    Damage Qty
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="damageQty"
                                 value=""
                                 class="validate[required]"/>
                </div>
            </td>
            <td>
                <div class="buttons">
                    <span class="button"><input type="button" name="entertainment-add" id="entertainment-add"
                                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                                value="Add"
                                                onclick="addDamageProductIntoGrid();"/></span>
                </div>
            </td>
        </tr>
    </table>

    <div class="jqgrid-container width350">
        <table id="jqgrid-grid-damageProducts"></table>

        <div id="jqgrid-pager-damageProducts"></div>
    </div>

    <div class="buttons width100p" style="margin-top: -32px;">
        <span class="button floatR"><input type="button" name="proceedDamage" id="proceedDamage"
                                           class="ui-button ui-widget ui-state-default ui-corner-all"
                                           value="Proceed"
                                           onclick="executeAjaxDamageMiscellaneousTransactions();"/></span>
    </div>
</div>