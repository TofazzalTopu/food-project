<form name='adjustMiscellaneousFeesWithReceivableForm' id='adjustMiscellaneousFeesWithReceivableForm'>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x width1x" style="margin-left: 5px;">
                        Customer Name
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="customerName" value="" class="validate[required]" style="width: 270px;"/>
                        <div style="display: inline-block; margin-bottom: -4px;">
                            <span id="search-btn-customer-id" title="" role="button"
                                  class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatL">
                                <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                                <span class="ui-button-text"></span>
                            </span>
                        </div>
                        <g:hiddenField name="customerId" value="" class="validate[required]"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x" style="margin-left: 5px;">
                        ID
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="customerCode" value="" class="validate[required]" readonly="true"/>
                    </div>
                </td>
                <td>
                    <div class="buttons">
                        <span class="button"><input type="button" name="search-button" id="search-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Search"
                                                    onclick="searchCustomer();"/></span>

                    </div>
                </td>
            </tr>

            <tr>
                <td>
                    <label class="txtright bold hight1x width1x" style="margin-left: 5px;">
                        Address
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="customerAddress" value="" class="validate[required]" style="width: 200px;"/>
                    </div>
                </td>
            </tr>

            <tr>
                <td>
                    <label class="txtright bold hight1x width1x" style="margin-left: 5px;">
                        Expense Type
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:select name="expenseType" from="[[name:'Gondola/ Shelve Charges', id:'ACCOMMODATION_RENT'],[name:'Other Charge', id:'GENERAL_EXPENSE']]"
                                  optionKey="id" optionValue="name"
                                  noSelection="['':'Select Expense Type...']"
                                  value="" class="validate[required]"/>
                    </div>
                </td>

                <td>
                    <label class="txtright bold hight1x width1x" style="margin-left: 5px;">
                        Enter the Amount
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="amount" value="" class="validate[required]"/>
                    </div>
                </td>
            </tr>
        </table>
    </div>


    <div class="element_row_content_container lightColorbg pad_bot0">
        <div class="jqgrid-container" style="margin-left: 5px;">
            <h4></h4>
            <table id="jqgrid-customer-grid"></table>

            <div id="jqgrid-customer-pager"></div>
        </div>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <div class="buttons floatR" style="margin-right: 100px !important;">
            <span class="button"><input type="button" name="adjustAgainstReceivable-button" id="adjustAgainstReceivable-button"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="Adjust Against Receivable"
                                        onclick="adjustAgainstReceivable();"/></span>

            <span class="button" id="btn-withdraw"><input type="button" name="withdraw-button" id="withdraw-button"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="withdraw"
                                        onclick="withdraw();"/></span>

        </div>
    </div>
</form>

<form name='adjustMiscellaneousFeesWithdrawForm' id='adjustMiscellaneousFeesWithdrawForm'>
    <div class="element_row_content_container lightColorbg pad_bot0" id="div-withdraw">
        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x width1x" style="margin-left: 5px;">
                        Withdrawal Amount
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="withdrawalAmount" value="" class="validate[required]"/>
                        <g:hiddenField name="amfwrId" value="" class="validate[required]"/>
                    </div>
                </td>
            </tr>

            <tr>
                <td>
                    <label class="txtright bold hight1x width1x" style="margin-left: 5px;">
                        <g:radio name="paymentType" value="cash" style="margin-left: 0px !important;"/>
                        Paid Through Cash
                    </label>
                </td>
            </tr>

            <tr>
                <td>
                    <label class="txtright bold hight1x width1x" style="margin-left: 5px;">
                        <g:radio name="paymentType" value="bank" style="margin-left: 0px !important;" />
                        Paid Through Bank
                    </label>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x" style="margin-left: 5px;">
                        Ref
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="ref" value=""/>
                    </div>
                </td>
            </tr>


        </table>

        <div class="buttons floatR" style="margin-right: 100px !important;">
            <span class="button"><input type="button" name="submit-button" id="submit-button"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="Submit"
                                        onclick="submitWithdraw();"/></span>

        </div>
    </div>
</form>


