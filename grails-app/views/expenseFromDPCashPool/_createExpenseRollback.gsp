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
        </table>
    </div>
  <div class="clear"></div>

</form>
