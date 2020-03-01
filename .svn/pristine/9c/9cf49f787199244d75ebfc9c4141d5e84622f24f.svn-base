<%@ page import="com.bits.bdfp.finance.CashReceivedFromBranch" %>


<div id="spinnerCashReceivedFromBranch" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>

<form name='gFormCashReceivedFromBranch' id='gFormCashReceivedFromBranch'>
    <g:hiddenField name="id" value=""/>
    <g:hiddenField name="version" value="0"/>
    <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>
    <div id="remote-content-cashReceivedFromBranch"></div>

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
                            <g:textField class="width300" name="enterPriseName" id="enterPriseName" readonly="readonly"
                                         value="${list[0].name}" />
                            <script type="text/javascript">
                                $(document).ready(function () {
                                    $("#enterpriseConfiguration").val("${list[0].id}");
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
                                        $("#enterpriseConfiguration").val($('#enterpriselist_hidden').val());
                                    }
                                });
                                $('#enterpriselist_input').addClass("validate[required]");
                                $('#enterpriselist_input').blur(function () {
                                    if ($('#enterpriselist_input').val() == '') {
                                        $("#enterpriseId").val("");
                                        $("#enterpriseConfiguration").val("");
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
                    <label for="transactionDate" class="txtright bold hight1x width150">
                        Transaction Date
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textField class="validate[required] text-input datepicker width300"
                                 name="transactionDate" id="transactionDate"
                                 value="${currentDate}" onchange="setDefaultCustomer();"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="distributionPoint" class="txtright bold hight1x width150" >
                        Distribution Point
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:select class="width300" name="distributionPoint.id" id="distributionPoint"
                              from="" onchange="setDefaultCustomer();"
                              optionKey="id"
                              value=""/>
                </td>
                %{--from="${dpListByUserId}" onchange="setDefaultCustomer();" optionValue="dpName"--}%
            </tr>
            <tr>
                <td>
                    <label for="defaultCustomer" class="txtright bold hight1x width150">
                        DP Default Customer
                    </label>
                </td>
                <td>
                    <g:textField class="width300" name="defaultCustomer" id="defaultCustomer"
                                 value="" readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="depositCashToDepositPool" class="txtright bold hight1x width150" >
                        Transaction No
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:select class="width300" name="depositCashToDepositPool.id"
                              id="depositCashToDepositPool"
                              from="" onchange="setData();"
                              optionKey="id"
                              value=""/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="totalDeposit" class="txtright bold hight1x width150" >
                        Total Deposit To HO
                    </label>
                </td>
                <td>
                    <g:textField class="width300 alin_right" name="totalDeposit" id="totalDeposit"
                                 value="" readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="depositToBankAccount" class="txtright bold hight1x width150" >
                        Deposit To Bank
                        <span>*</span>
                    </label>
                </td>
                <td>
                    <g:textField class="width300 alin_right" name="depositToBankAccount" id="depositToBankAccount"
                                 value="" readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="depositToHoCash" class="txtright bold hight1x width150" >
                        Deposit By Cash
                        <span>*</span>
                    </label>
                </td>
                <td>
                    <g:textField class="width300 alin_right" name="depositToHoCash" id="depositToHoCash"
                                 value="" readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="salesAmount" class="txtright bold hight1x width150" >
                        Sales Amount
                        <span>*</span>
                    </label>
                </td>
                <td>
                    <g:textField class="width300 alin_right" name="salesAmount" id="salesAmount"
                                 value=""/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="sdAmount" class="txtright bold hight1x width150" >
                        SD Amount
                        <span>*</span>
                    </label>
                </td>
                <td>
                    <g:textField class="width300 alin_right" name="sdAmount" id="sdAmount"
                                 value=""/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="cashPool" class="txtright bold hight1x width150" >
                        Bank Account
                    </label>
                </td>
                <td>
                    <g:select class="width300" name="bankAccount.id" id="bankAccount"
                              from="${com.bits.bdfp.common.BankAccount.list()}"
                              optionKey="id"
                              value=""/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="cashPool" class="txtright bold hight1x width150" >
                        Cash Pool
                    </label>
                </td>
                <td>
                    <g:select class="width300" name="cashPool.id" id="cashPool"
                              from=""
                              optionKey="id"
                              value=""/>
                </td>
            </tr>
        </table>
    </div>

    <div class="clear"></div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="create-button" id="create-button-cashReceivedFromBranch"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxCashReceivedFromBranch();"/></span>
    </div>
</form>
