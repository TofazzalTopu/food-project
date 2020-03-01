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
                    <g:select name="rtpDistributionPoint"
                              from=""
                              optionKey="id"

                              class="validate[required] width180"
                              onchange="getRtpInventoryListByDp(this.value)"/>
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
                    <g:select name="rtpInventory"
                              from="[]"
                              noSelection="['': 'Select Inventory...']"
                              class="validate[required] width180"
                              onchange="getRtpSubInventoryLisByInventory(this.value);"/>
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
                    <g:select name="rtpSubInventory"
                              from="[]"
                              noSelection="['': 'Select Sub Inventory...']"
                              class="validate[required] width155"
                              onchange="getRtpProductListBySubInventory(this.value);"/>
                </div>
            </td>
        </tr>

        <tr>
            <td colspan="2">
                <div class="jqgrid-container width350">
                    <table id="jqgrid-grid-rtpInvProducts"></table>

                    <div id="jqgrid-pager-rtpInvProducts"></div>
                </div>
            </td>
            <td>
                <label class="txtright bold hight1x width1x" style="margin-left: 10px;">
                    Remarks
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="rtpRemarks" value=""/>
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
                    <g:textField name="selectRtpProduct" class="validate[required] width152"/>
                    <div style="display: inline-block; margin-bottom: -4px;">
                        <span id="search-btn-rtp-product-id" title="" role="button"
                              class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatL">
                            <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                            <span class="ui-button-text"></span>
                        </span>
                    </div>
                    <g:hiddenField name="rtpProduct" class="validate[required]"/>
                </div>
            </td>
            <td>
                <label class="txtright bold hight1x width1x" style="margin-left: 10px;">
                    Qty
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="rtpQty"
                                 value=""
                                 class="validate[required]"/>
                </div>
            </td>
            <td>
                <div class="buttons">
                    <span class="button"><input type="button" name="rtp-add" id="rtp-add"
                                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                                value="Add"
                                                onclick="addRtpProductIntoGrid();"/></span>
                </div>
            </td>
        </tr>
    </table>

    <div class="jqgrid-container width350">
        <table id="jqgrid-grid-rtpProducts"></table>

        <div id="jqgrid-pager-rtpProducts"></div>
    </div>

    <div class="buttons width100p" style="margin-top: -32px;">
        <span class="button floatR"><input type="button" name="save-rtp" id="save-rtp"
                                           class="ui-button ui-widget ui-state-default ui-corner-all"
                                           value="Save"
                                           onclick="executeAjaxRtpMiscellaneousTransactions();"/></span>
    </div>
</div>