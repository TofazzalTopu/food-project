<%@ page import="com.bits.bdfp.common.DepositPool" %>
<form name='frmDepositCashToDepositPool' id="frmDepositCashToDepositPool">
    <g:hiddenField name="id" value=""/>
    <g:hiddenField name="version" value=""/>
    <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>
    <div id="remote-content-depositCashToDepositPool"></div>

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
                    <label for="date" class="txtright bold hight1x width150">
                        Date
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="date" id="date" readonly="readonly"
                                     class="validate[required] text-input datepicker width200"
                                     value="${recentDate}"/>
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
                        <g:select class="validate[required] width200" name="distributionPoint.id" id="distributionPoint"
                                  from="" onchange="setDefaultCustomer();"
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
                        <g:select class="validate[required] width200" name="cashPool.id" id="cashPool"
                                  from="" onchange="loadCashPoolBalance(this.value);"
                                  optionKey="id"
                                  value=""/>
                    </div>
                </td>
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
            <tr>
                <td>
                    <label for="pm" class="txtright bold hight1x width150">
                        Deposit Type
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:radio name="pm" id="ho" value="ho" checked="checked" onclick="showHo(1);"/> Deposit To HO <br/>
                    <g:radio name="pm" id="vault" value="vault" onclick="showHo(0);"/> Deposit to non Bank Vault <br/>
                </td>
                <td>
                    <label for="total" class="txtright bold hight1x width150">
                        Deposit Amount
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField class="validate[required] width200" name="total" id="total" value=""/>
                    </div>
                </td>
            </tr>
        </table>

        <div id="hoDiv">
            <table>
                <tr>
                    <td>
                        <label for="depositToBankAccount" class="txtright bold hight1x width150">
                            Deposit To Bank
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <div class='inputContainer'>
                            <g:textField class="width200 validate[required]" name="depositToBankAccount"
                                         id="depositToBankAccount" value=""/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="depositToHoCash" class="txtright bold hight1x width150">
                            Deposit To HO Cash
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <div class='inputContainer'>
                            <g:textField class="width200 validate[required]" name="depositToHoCash" id="depositToHoCash"
                                         value=""/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="salesAmount" class="txtright bold hight1x width150">
                            Collection Amount
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <div class='inputContainer'>
                            <g:textField class="width200 validate[required]" name="salesAmount" id="salesAmount"
                                         value=""/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="sdAmount" class="txtright bold hight1x width150">
                            SD Amount
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <div class='inputContainer'>
                            <g:textField class="width200 validate[required] " name="sdAmount" id="sdAmount" value=""/>
                        </div>
                    </td>

                </tr>
            </table>
        </div>

        <div id="vaultDiv" style="display: none">
            <table>
                <tr>
                    <td>
                        <label for="depositPool" class="txtright bold hight1x width150">
                            Non Bank DP Vault
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <g:select name="depositPool.id" id="depositPool"
                                  from="" onchange=""
                                  style="width: 206px;"
                                  optionKey="id"
                                  value=""/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label class="txtright bold hight1x width150">
                            Enable Denomination
                        </label>
                    </td>
                    <td>
                        <g:checkBox name="denomination" id="denomination" class="denomination" value=""
                                    onclick="loadDataGrid()"/>

                    </td>
                </tr>
            </table>

            <div class="jqgrid-data-container" id="dataGrid" style="display: none">
                <table id="jqgrid-data-grid"></table>

                <div id="jqgrid-data-pager"></div>
            </div>
        </div>
    </div>


    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjax();"/></span>
    </div>
</form>