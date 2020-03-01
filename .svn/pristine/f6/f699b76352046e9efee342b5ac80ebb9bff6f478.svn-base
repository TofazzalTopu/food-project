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
                    <g:select name="sampleDistributionPoint"
                              from=""
                              optionKey="id"

                              class="validate[required] width180"
                              onchange="getSampleInventoryListByDp(this.value)"/>

                </div>
            </td>
        </tr>
        <tr>
            <td>
                <label class="txtright bold hight1x width145">
                    Select Customer Type
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:radio name="customerType" value="enlisted"/>
                    <span>Enlisted Customer</span>
                    <br style="margin-bottom:8px"/>
                    <g:radio name="customerType" value="others"/>
                    <span>Others</span>
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
                    <div id="sampleCustomer">
                        <input type="text" id="sampleDefaultCustomer" class="validate[required] width152"/>

                        <div style="display: inline-block; margin-bottom: -4px;">
                            <span id="search-btn-sample-register-id" title="" role="button"
                                  class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatL">
                                <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                                <span class="ui-button-text"></span>
                            </span>
                        </div>
                    </div>
                    <g:hiddenField name="sampleCustomer.id" id="sampleCustomerIdHidden" value=""
                                   class="validate[required]"/>
                    <g:textField name="sampleCustomerName" value=""/>

                </div>
            </td>

            <td>
                <label class="txtright bold hight1x width1x" style="margin-left: 10px;">
                    Remarks
                </label>
            </td>
            <td rowspan="3">
                <div class='inputContainer'>
                    <g:textArea name="sampleRemarks" value="" cols="30" rows="5"/>

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
                    <g:textField name="sampleCustomerId" value=""/>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <label class="txtright bold hight1x width145">
                    Address
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="sampleCustomerAddress" value=""/>
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
                    <g:select name="sampleInventory"
                              from="[]"
                              noSelection="['': 'Select Inventory...']"
                              class="validate[required] width180"
                              onchange="getSampleSubInventoryListByInventory(this.value);"/>
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
                    <g:select name="sampleSubInventory"
                              from="[]"
                              noSelection="['': 'Select Sub Inventory...']"
                              class="validate[required] width155"
                              onchange="getSampleProductListBySubInventory(this.value);"/>
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
                    <g:textField name="selectSampleProduct" class="validate[required] width152"/>
                    <div style="display: inline-block; margin-bottom: -4px;">
                        <span id="search-btn-sample-product-id" title="" role="button"
                              class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatL">
                            <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                            <span class="ui-button-text"></span>
                        </span>
                    </div>
                    <g:hiddenField name="sampleProduct" class="validate[required]"/>
                </div>
            </td>
            <td>
                <label class="txtright bold hight1x width1x" style="margin-left: 10px;">
                    Sample Qty
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="sampleQty"
                                 value=""
                                 class="validate[required]"/>

                </div>
            </td>
            <td>
                <div class="buttons">
                    <span class="button"><input type="button" name="sample-add" id="sample-add"
                                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                                value="Add"
                                                onclick="addSampleProductIntoGrid();"/></span>
                </div>
            </td>
        </tr>
    </table>

    <div class="jqgrid-container width350">
        <table id="jqgrid-grid-sampleProducts"></table>

        <div id="jqgrid-pager-sampleProducts"></div>
    </div>

    <div class="buttons width100p" style="margin-top: -32px;">
        <span class="button floatR"><input type="button" name="proceed" id="proceed"
                                           class="ui-button ui-widget ui-state-default ui-corner-all"
                                           value="Proceed"
                                           onclick="executeAjaxSampleMiscellaneousTransactions();"/></span>
    </div>
</div>