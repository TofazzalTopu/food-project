


<%@ page import="com.bits.bdfp.finance.ExpenseFromDPCashPool" %>


<div id="spinnerExpenseFromDPCashPool" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>
<form name='gFormExpenseFromDPCashPool' id='gFormExpenseFromDPCashPool'>
  <g:hiddenField name="id" value="" />
  <g:hiddenField name="version" value="0" />
    <div id="remote-content-expenseFromDPCashPool"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x width200 alin_right" style="margin-left: 5px;">
                        Distribution Point
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer '>
                        <g:select from="" name="distributionPoint.id" id="distributionPoint"
                         class="validate[required] width350"
                         optionKey="id"
                         onchange="selectCashPool(this.value);"/>
                    </div>
                </td>
            </tr>

            <tr>
                <td>
                    <label class="txtright bold hight1x width200 alin_right" style="margin-left: 5px;">
                        Cash Pool
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>

                    <div class='inputContainer '>
                            <g:select name="cashPool.id" id="cashPool" class="validate[required] width350" from=""
                            onchange="fetchAvailableCash(this.value)" optionKey="id" value="" noSelection="['null': 'Select DP Cash Pool']" />
                    </div>
                </td>

            </tr>
            <tr>
                <td>
                    <label class="txtright bold hight1x width200 alin_right" style="margin-left: 5px;">
                        Available Cash In Hand
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField  name="availableCash" class="validate[required] width345" id="availableCash" readonly="true" />
                    </div>
                </td>
            </tr>

            <tr>
                <td>
                    <label class="txtright bold hight1x width200 alin_right" style="margin-left: 5px;">
                        Expenditure Heads
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:select name="expenditureHeads.id" id="expenditureHeads" class="validate[required] width350"
                              from="${com.bits.bdfp.accounts.ChartOfAccounts.list()}"
                            optionKey="id" value="${expenseFromDPCashPool?.expenditureHeads?.id}" noSelection="['null': 'Select Expenditure Heads']" />
                    </div>
                </td>
            </tr>

            <tr>
                <td>
                    <label class="txtright bold hight1x width200 alin_right" style="margin-left: 5px;">
                        Enter Expense Amount
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField  name="expenseAmount" class="validate[required] width345" id="expenseAmount"
                           value="${fieldValue(bean: expenseFromDPCashPool, field: 'expenseAmount')}" />
                    </div>
                </td>
            </tr>

            <tr>
                <td>
                    <label class="txtright bold hight1x width200 alin_right" style="margin-left: 5px">
                        remarks
                    </label>
                </td>
                <td>
                    <div class='inputContainer '>
                        <div class='element-input inputContainer '><g:textArea name="remarks" rows="2" cols="61" value="${expenseFromDPCashPool?.remarks}" maxlength="80" /></div>
                    </div>
                </td>
            </tr>
        </table>
    </div>


  <div class="clear"></div>
  <div class="buttons" style="margin-left:10px;">
    <span class="button"><input type="button" name="create-button" id="create-button-expenseFromDPCashPool" class="ui-button ui-widget ui-state-default ui-corner-all" value="${message(code: 'default.button.create.label', default: 'Create')}" onclick="executeAjaxExpenseFromDPCashPool();"/></span>
    %{--<span class="button"><input type='button' name="delete-button" id="delete-button-expenseFromDPCashPool" class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete' onclick="deleteAjaxExpenseFromDPCashPool();"/></span>--}%
    <span class="button"><input name="clearFormButtonExpenseFromDPCashPool" class="ui-button ui-widget ui-state-default ui-corner-all" type="button" onclick=" reset_form('#gFormExpenseFromDPCashPool');" value="Cancel"/></span>
  </div>
</form>
