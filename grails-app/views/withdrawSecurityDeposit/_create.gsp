<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder" %>
<form name='gFormCashTroughBank' id='gFormCashTroughBank'>
    <g:hiddenField name="id" value=""/>
    <g:hiddenField name="version" value=""/>
    <g:hiddenField name="idEnterprise" id="idEnterprise" value=""/>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Enterprise
                        <span class="mendatory_field">*</span>
                    </label>
                </td>

                <g:if test="${result}">
                    <g:if test="${enterpriseList.size() == 1}">
                        <td>
                            <g:textField name="enterPriseName" id="enterPriseName" readonly="readonly"
                                         value="${enterpriseList[0].name}" class="width2x"/>
                            <g:hiddenField name="enterpriseConfiguration" id="enterpriseConfiguration"
                                           value="${enterpriseList[0].id}"/>
                            <script type="text/javascript">
                                jQuery(document).ready(function () {
                                    $('#idEnterprise').val(${enterpriseList[0].id});
                                    loadTerritory(${enterpriseList[0].id})
                                });
                            </script>
                        </td>
                    </g:if>
                    <g:else>
                        <td><div id="enterpriselist" class="width2x"></div></td>
                        <script type="text/javascript">
                            jQuery(document).ready(function () {
                                $("#enterpriselist").empty();
                                $("#enterpriselist").flexbox(${result}, {
                                    watermark: "Select Enterprise",
                                    width: 260,
                                    onSelect: function () {
                                        //$("#enterpriseConfiguration").val($('#enterpriselist_hidden').val());
                                        $('#idEnterprise').val($('#enterpriselist_hidden').val());
//                                        loadCustomer($('#enterpriselist_hidden').val());
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
                    <g:hiddenField name="enterpriseConfiguration" id="enterpriseConfiguration" value=""/>
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
                <td>
                    <label class="txtright bold hight1x width1x">
                        Date
                        %{--<span class="mendatory_field">*</span>--}%
                    </label>
                </td>
                <td>
                    <g:textField name="asOfDate" id="asOfDate"/>
                </td>
            </tr>

            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Select Territory
                    </label>
                </td>
                <td>
                    <g:select name="territory" from="" class="width2x"
                              optionKey="id"
                              optionValue="name"
                              noSelection="['': ' - Select Territory - ']"
                              onchange="loadDP(this.value);"/>
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Select DP
                    </label>
                </td>
                <td>
                    <g:select name="distributionPoint" class="width2x"
                              optionKey="id"
                              optionValue="name"
                              noSelection="['': ' - Select DP - ']"
                              onchange="loadDpDefaultCustomer(this.value);"/>
                </td>
            </tr>

            <tr class="element_row_content_container  lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Default Customer
                    </label>
                </td>
                <td>
                    <g:textField name="defaultCustomer" class="width2x"/>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x">
                        ID
                    </label>
                </td>
                <td>
                    <g:textField name="defaultCustomerCode" readonly="true"/>
                    <g:hiddenField name="defaultCustomerId" readonly="true"/>
                </td>
            </tr>

            <tr class="element_row_content_container  lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold width1x">
                        Total available SD
                    </label>
                </td>
                <td>
                    <g:textField name="availableDPSecurityDeposit" readonly="true" class="width2x"/>
                </td>
                <td style="width: 150px;">

                </td>
                <td>

                </td>
            </tr>
        </table>

        <div class="height10"></div>

    <div id="divTradePartner" hidden="true">

                <input type="text" id="customerMaster" name="customerMaster">
                <input type="text" id="securityDepositBalance" name="securityDepositBalance">
                <input type="text" id="receivableAmount" name="receivableAmount">

    </div>


        <div class="jqgrid-container" id="gridTP">
            <table id="jqgrid-tp-grid"></table>

            <div id="jqgrid-tp-pager"></div>

            <div class="height10"></div>

            <div class="width515">
                <div class="floatR">
                    <label class="txtright bold width2x">
                        Total available SD for Trade Partners
                    </label>
                    <g:textField name="totalAvailableSD" class="width2x" readonly="true" style="text-align: right;"/>

                    <div class="height10 clear"></div>
                    <label class="txtright bold width2x">
                        Withdrawal Amount
                    </label>
                    <g:textField name="withdrawalAmount" class="width2x" style="text-align: right;"/>


                    <div class="height10 clear"></div>
                    %{--<label class="txtright bold width2x">--}%
                    %{--<g:message code="secondaryDemandOrder.deliveryDate.label" default="Withdrawal Amount"/>--}%
                    %{--</label>--}%
                    <label for="isCashPayment" class="txtright bold width2x"><input type="radio" id="isCashPayment" name="paymentType" value="cash" style="text-align: right;" onclick="SelectIsCashPayment()" checked="checked"/> Paid Through Cash</label>

                    <label for="isBankPayment" class="txtright bold width2x"><input type="radio" id="isBankPayment" name="paymentType" value="bank" style="text-align: right;" onclick="SelectIsBankPayment()"/>Paid Through Bank</label>
                <div id="divPaymentBank">
                    <div class="height10 clear"></div>

                    <label id="lblBankRef" class="txtright bold width2x" hidden="true">
                        Ref
                    </label>
                    <g:textField name="bankRef" class="width2x" style="text-align: right;" hidden="true"/>

                </div>

                    <div class="height10 clear"></div>
                    <label class="txtright bold width2x" style="color: #828282">
                        <g:checkBox name="forceWithdrawalAmountCheck"/>
                        Force Withdrawal Amount
                    </label>
                    <g:textField name="forceWithdrawalAmount" class="width2x" style="text-align: right;"/>
                    <g:hiddenField name="tpId" class="width2x" readonly="true"/>


                </div>
            </div>
        </div>



    <div class="height10 clear"></div>

        <div class="pad_left15 width515">
            <span class="button floatR"><input type="button" name="tp-save-button" id="tp-save-button"
                                               class="ui-button ui-widget ui-state-default ui-corner-all"
                                               value="Save"
                                               onclick="withdrawSecurityDeposit();"/></span>
        </div>
        <div class="height10 clear"></div>
    </div>
</form>