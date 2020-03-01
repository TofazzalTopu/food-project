<div class="element_row_content_container lightColorbg pad_bot0">
    <div class="floatL">
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
                        <g:select name="distributionPoint"
                                  from=""
                                  optionKey="id"
                                  class="validate[required] width180"
                                  onchange=""/>

                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="txtright bold hight1x width145">
                        Customer Name
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <input type="text" id="defaultCustomer" class="validate[required] width152"/>

                        <div style="display: inline-block; margin-bottom: -4px;">
                            <span id="search-btn-customer-register-id" title="" role="button"
                                  class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatL">
                                <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                                <span class="ui-button-text"></span>
                            </span>
                        </div>

                        <g:hiddenField name="customer.id" id="customer" value="" class="validate[required]"/>

                    </div>
                </td>
            </tr>

            <tr>
                <td>
                    <label class="txtright bold hight1x width145">
                        Customer ID
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="customerId"
                                     value=""
                                     class="validate[required] width180"/>

                    </div>
                </td>
            </tr>

            <tr>
                <td>
                    <label class="txtright bold hight1x width145">
                        Market Return Number
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:select name="marketReturnNumber"
                                  from="[]"
                                  optionKey=""
                                  noSelection="['': 'Select Market Return Number...']"
                                  class="validate[required] width180"/>

                    </div>
                </td>
            </tr>

            <tr>
                <td>

                </td>
                <td>
                    <div class="buttons floatR">
                        <span class="button"><input type="button" name="view-summary" id="view-summary"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="View Summary"
                                                    onclick="viewMarketReturnSummary();"/></span>
                    </div>
                </td>
            </tr>
        </table>
    </div>

    <div class="jqgrid-container floatL" style="width: 390px; margin-left: 10px;">
        <table id="jqgrid-grid-marketReturnSummary"></table>

        <div id="jqgrid-pager-marketReturnSummary"></div>
    </div>

    <div class="clearfix"></div>
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
                    <g:select name="inventory"
                              from="[]"
                              noSelection="['': 'Select Inventory...']"
                              class="validate[required] width180"
                              onchange="getSubInventoryLisByInventory(this.value);"/>
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
                    <g:select name="subInventory"
                              from="[]"
                              noSelection="['': 'Select Sub Inventory...']"
                              class="validate[required] width155"
                              onchange=""/>
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
                    <g:textField name="selectProduct" class="validate[required] width152"/>
                    <div style="display: inline-block; margin-bottom: -4px;">
                        <span id="search-btn-product-id" title="" role="button"
                              class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatL">
                            <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                            <span class="ui-button-text"></span>
                        </span>
                    </div>
                    <g:hiddenField name="product" class="validate[required]"/>
                </div>
            </td>
            <td>
                <label class="txtright bold hight1x width1x" style="margin-left: 10px;">
                    Replacement Qty
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="replacementQty"
                                 value=""
                                 class="validate[required]"/>
                </div>
            </td>

            <td>
                <div class="buttons">
                    <span class="button"><input type="button" name="add" id="add"
                                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                                value="Add"
                                                onclick="addProductIntoGrid();"/></span>
                </div>
            </td>
        </tr>

        <tr>
            <td>
                <label class="txtright bold hight1x width1x" style="margin-left: 10px;">
                    Available Qty
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="availableQty" readonly="true" value=""  />
                </div>
            </td>
        </tr>
    </table>

    <div class="jqgrid-container width350">
        <table id="jqgrid-grid-marketReturnReplacement"></table>

        <div id="jqgrid-pager-marketReturnReplacement"></div>
    </div>

    <div class="buttons width100p" style="margin-top: -32px;">
        <span class="button floatR"><input type="button" name="replace-products" id="replace-products"
                                           class="ui-button ui-widget ui-state-default ui-corner-all"
                                           value="Replace Products"
                                           onclick="executeAjaxMiscellaneousTransactions();"/></span>
    </div>
</div>