<%@ page import="com.bits.bdfp.finance.WithdrawCashFromDepositPool" %>
<form name='gFormWithdrawCashFromDepositPool' id='gFormWithdrawCashFromDepositPool'>
    <g:hiddenField name="id" value=""/>
    <g:hiddenField name="version" value="0"/>
    <g:hiddenField name="enterprise.id" id="enterprise" value=""/>
    <div id="remote-content-withdrawCashFromDepositPool"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width150">
                        Enterprise
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <g:if test="${result}">
                    <g:if test="${list.size() == 1}">
                        <td>
                            <g:textField class="width200" name="enterPriseName" id="enterPriseName"
                                         readonly="readonly"
                                         value="${list[0].name}"/>
                            <script type="text/javascript">
                                $(document).ready(function () {
                                    $("#enterprise").val("${list[0].id}");
                                });
                            </script>
                        </td>
                    </g:if>
                    <g:else>
                        <td><div id="enterpriselist" style="width: 300px;"></div></td>
                        <script type="text/javascript">
                            %{--alert("${result}");--}%
                            jQuery(document).ready(function () {

                                $("#enterpriselist").empty();

                                $("#enterpriselist").flexbox(${result}, {
                                    watermark: "Select Enterprise",
                                    width: 260,
                                    onSelect: function () {
                                        $("#enterprise").val($('#enterpriselist_hidden').val());
                                    }
                                });
                                $('#enterpriselist_input').addClass("validate[required]");
                                $('#enterpriselist_input').blur(function () {
                                    if ($('#enterpriselist_input').val() == '') {
                                        $("#enterprise").val("");
                                    }
                                });
                            });
                        </script>
                    </g:else>
                </g:if>
                <g:else>
                    <td>
                        <g:textField name="enterPriseName" readonly="readonly" value=""/>
                        <script type="text/javascript">
                            jQuery(document).ready(function () {
                                MessageRenderer.showHeaderMessage("You have no assigned enterprise, please assign enterprise first.", 0)
                            });
                        </script>
                    </td>
                </g:else>
            </tr>
        </table>

        <table>
            <tr>
                <td>
                    <label for="date" class="txtright bold hight1x width150">
                        Date
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="date" id="date"
                                     class="validate[required] text-input datepicker width200"
                                     value="${recentDate}" onchange="loadTransactionNo();"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="distributionPoint" class="txtright bold hight1x width150">
                        Distribution Point
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td class="width200">
                    <div class='inputContainer'>
                        <g:select class="validate[required]" name="distributionPoint.id" id="distributionPoint"
                                  from="" onchange="setDefaultCustomer();"
                                  style="width: 206px;"
                                  optionKey="id"
                                  value=""/>
                    </div>
                </td>
                <td>
                    <label for="defaultCustomer" class="txtright bold hight1x width150">
                        DP Default Customer
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField class="width200 validate[required]" name="defaultCustomer" id="defaultCustomer"
                                     value="" readonly="readonly"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="cashPool" class="txtright bold hight1x width150">
                        Cash Pool
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:select class="validate[required]" name="cashPool.id" id="cashPool"
                                  from="" onchange="loadCashPoolBalance(this.value);loadTransactionNo();"
                                  style="width: 206px;"
                                  optionKey="id"
                                  value=""/>
                    </div>
                </td>
                <td>
                    <label for="depositPool" class="txtright bold hight1x width150">
                        Non Bank DP Vault
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:select name="depositPool.id" id="depositPool"
                              from="" onchange="loadTransactionNo();"
                              style="width: 206px;"
                              optionKey="id"
                              value=""/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="transactionNo" class="txtright bold hight1x width150">
                        Transaction No
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:select class="validate[required]" name="transactionNo" id="transactionNo"
                              from="" onchange="loadTransactionAmount();" style="width: 206px;"
                              optionKey="id"
                              value=""/>
                </td>
                <td>
                    <label for="withdrawAmount" class="txtright bold hight1x width150">
                        Withdraw Amount
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField class="validate[required] width200" name="withdrawAmount"
                                     id="withdrawAmount" value="" readonly="readonly"/>
                    </div>
                </td>
            </tr>
            <tr hidden="hidden">
                <td>
                    <label for="availableCash" class="txtright bold hight1x width150">
                        Available Cash
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField class="width200 validate[required]" name="availableCash" id="availableCash"
                                     value="" readonly="readonly"/>
                    </div>
                </td>
            </tr>
        </table>

        <div class="jqgrid-data-container" id="dataGrid">
            <table id="jqgrid-data-grid"></table>

            <div id="jqgrid-data-pager"></div>
        </div>
    </div>

    <div class="clear"></div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="create-button" id="create-button-withdrawCashFromDepositPool"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Withdraw"
                                    onclick="executeAjaxWithdrawCashFromDepositPool();"/></span>
    </div>
</form>
