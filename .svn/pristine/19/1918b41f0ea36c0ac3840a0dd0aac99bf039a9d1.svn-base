<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder" %>
<form name='gFormCashTroughBank' id='gFormCashTroughBank'>
    <g:hiddenField name="id" value=""/>
    <g:hiddenField name="version" value=""/>
    <g:hiddenField name="idEnterprise" id="idEnterprise" value=""/>
    <g:hiddenField name="isFactory" id="isFactory" value="${isFactory}"/>
    <g:hiddenField name="idBusiness" id="idBusiness" value=""/>
    <g:hiddenField name="distributionPoint.id" id="distributionPoint" value=""/>
    <div id="remote-content-territoryConfiguration"></div>
    <br>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width170">
                        Enterprise:
                        <span class="mendatory_field">*</span>
                    </label>
                    %{--<g:textField name="enterPriseName" readonly="readonly" value="7" />--}%
                </td>

                <g:if test="${result}">
                    <g:if test="${list.size() == 1}">
                        <td>
                            <g:textField class="width120" name="enterPriseName" id="enterPriseName" readonly="readonly"
                                         value="${list[0].name}"/>
                            <g:hiddenField name="enterpriseConfiguration" id="enterpriseConfiguration"
                                           value="${list[0].id}"/>
                            <script type="text/javascript">
                                jQuery(document).ready(function () {
                                    $('#idEnterprise').val(${list[0].id});
                                    loadDistributionPoint(${list[0].id});

                                    //$("#distributionPointList_hidden").val("${list[0].id}");
                                    //$("#distributionPointList").setValue("${list[0].name}");
                                    //console.log("distributionPoint "+"${list[0].name}");
                                   // alert('first');
                                });
                            </script>
                        </td>
                    </g:if>
                    <g:else>
                    %{--<td><g:select name="enterpriseConfiguration.id"--}%
                    %{--from="${list}" optionKey="id"--}%
                    %{--value="${territoryConfiguration?.enterpriseConfiguration?.id}"/></td>--}%
                        <td><div id="enterpriselist" style="width: 240px;"></div></td>
                        <script type="text/javascript">
                            jQuery(document).ready(function () {
                                $("#enterpriselist").empty();
                                //console.log("distributionPointList_input");
                                //$("#distributionPointList").setValue("result[0].name");
                               // $("#distributionPointList_input").setValue("NAME");

                                $("#enterpriselist").flexbox(${result}, {
                                    watermark: "Select Enterprise",
                                    width: 150,
                                    onSelect: function () {
                                        $("#enterpriseConfiguration").val($('#enterpriselist_hidden').val());
                                        loadDistributionPoint($('#enterpriselist_hidden').val());
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
                    <label class="txtright bold hight1x width120">
                        Select DP/Factory:
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div id="distributionPointList" style="width: 280px;"></div>
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width170">
                        Select Customer:
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='element-input-td inputContainer width450'>
                        <input type="text" id="searchKey" name="searchKey" class="width400 validate[required] "/>
                        <input type="hidden" id="customerMaster" name="customerMaster.id" />
                        <span id="search-btn-customer-register-id" title="" role="button"
                              class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                            <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                            <span class="ui-button-text"></span>
                        </span>
                    </div>
                </td>
            </tr>

            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width170">
                        Customer ID:
                    </label>

                </td>
                <td><g:textField name="code" class="width170" id="code" readonly="readonly"/></td>
                <td>
                    <label class="txtright bold hight1x width110">
                        Customer Name:
                    </label>
                </td>
                <td><g:textField class="width300" name="name" id="name" readonly="readonly"/></td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width170">
                        Collected as security deposit:
                    </label>
                </td>
                <td>
                    <g:checkBox name="isSecurityDeposit" id="isSecurityDeposit" value="true" checked=""
                                onchange="disableForm();"/>

                </td>

                <td>
                    <label class="txtright bold hight1x width110">
                        Date:
                        <span class="mendatory_field">*</span>
                    </label>

                    <div class='element-input inputContainer'><g:textField name="dateTransaction" id="dateTransaction"
                                                                           class="validate[required]"
                                                                           style="width: 100px"
                                                                           value="${currentDate}"/>
                    </div>
                </td>
            </tr>

            <tr class="element_row_content_container  lightColorbg pad_bot0" id="payment_method">

                <td>
                    <label class="txtright bold hight1x width170">
                        Payment Mode:
                    </label>

                </td>
                <td>

                    <select name="paymentMode" id="paymentMode" onchange="loadBankDiv()">
                        <option id="bankOption" value="Bank">Bank</option>
                        <option value="Cash">Cash</option>

                    </select>

                </td>

            </tr>

        </table>

        <div id="bank">
            <table style="width: 100%;">

                <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">

                    <td>
                        <label class="txtright bold hight1x width170">
                            Bank A/C:
                            <span class="mendatory_field">*</span>
                        </label>

                    </td>
                    <td class="width300"><div class='element-input-td inputContainer width300'>
                        <g:select name="bankAccount.id" id="bankAccount" noSelection="['': 'Select Bank Account']"
                                  class="validate[required] width300"
                                  from="" optionKey="" value=""/>

                    </div>

                    </td>
                    <td>
                        <label class="txtright bold hight1x width110">
                            Paid Through:
                            <span class="mendatory_field">*</span>
                        </label>

                    </td>
                    <td>
                        <div class='element-input-td inputContainer'>
                            <g:select name="bankPaymentMethod.id" id="bankPaymentMethod"
                                      from="${com.bits.bdfp.common.BankPaymentMethod.list()}" optionKey="id"
                                      class="validate[required]" optionValue="name" value=""/>

                        </div>
                    </td>

                </tr>
                <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                    <td>
                        <label class="txtright bold hight1x width170">
                            Ref No:
                            <span class="mendatory_field">*</span>
                        </label>

                    </td>
                    <td>
                        <div class='element-input-td inputContainer width300'>
                            <g:textField name="refNo" id="refNo" value="" class="validate[required] width290"/>
                        </div>

                    </td>
                    <td>
                        <label class="txtright bold hight1x width110">
                            Remarks:
                        </label>
                    </td>
                    <td>
                        <g:textField name="remark" id="remark" value=""/>
                    </td>

                </tr>
            </table>
        </div>

        <div id="cash" style="display: none">
            <table style="width: 100%;">
                <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">

                    <td>
                        <label class="txtright bold hight1x width170">
                            Cash Pool:
                            <span class="mendatory_field">*</span>
                        </label>

                    </td>
                    <td>
                        <div class='element-input-td inputContainer'>
                            <g:select name="cashPool.id" id="cashPool" class="validate[required]"
                                      optionKey="id" optionValue="name"
                                      value=""/>

                        </div>
                    </td>

                </tr>
                <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                    <td>
                        <label class="txtright bold hight1x width170">
                            Enable Denomination Feature:
                        </label>
                    </td>
                    <td>
                        <g:checkBox name="denomination" id="denomination" class="denomination" value=""
                                    onclick="loadDataGrid()"/>

                    </td>

                </tr>

            </table>
        </div>

        <div id="dataGrid" style="display: none">
            <table>
                <tr class="element_row_content_container lightColorbg pad_bot0">
                    <td>
                        <div class="jqgrid-data-container">
                            <table id="jqgrid-data-grid"></table>

                            <div id="jqgrid-data-pager"></div>
                        </div>

                    </td>
                    <td>
                        <label class="txtright bold hight1x width110">
                            Total:
                        </label>

                    </td>
                    <td>
                        <div class='element-input-td inputContainer'>
                            <g:textField name="totalAmountQty" id="totalAmountQty" value="" readonly="readonly"/>
                        </div>
                    </td>

                </tr>
            </table>
        </div>
        <table>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width170">
                        Amount Received:
                        <span class="mendatory_field">*</span>
                    </label>

                </td>
                <td>
                    <div class='element-input-td inputContainer'>
                        <g:textField name="amount" id="amount" value="" class="validate[required] alin_right"
                                     onkeyup="applyDue(this.value)"/>
                    </div>
                </td>

                <td>
                    <label class="txtright bold hight1x width110">
                        Confirm Amount:
                        <span class="mendatory_field">*</span>
                    </label>

                </td>
                <td>
                    <div class='element-input-td inputContainer'>
                        <g:textField name="confirmAmount" id="confirmAmount" value="" class="validate[required] alin_right"/>
                    </div>
                </td>

            </tr>
        </table>

        <table id="securityDeposit">
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width170">
                        Total Due Amount:
                    </label>

                </td>
                <td>
                    <div class='element-input-td inputContainer'>
                        <g:textField name="totalReceivedAmount" id="totalReceivedAmount" value="0" readonly="readonly"
                                     class="alin_right"/>
                    </div>
                </td>

            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width170">
                        Total Applied Amount:
                    </label>

                </td>
                <td>
                    <div class='element-input-td inputContainer'>
                        <g:textField name="totalAppliedAmount" id="totalAppliedAmount" value="0" readonly="readonly"
                                     class="alin_right"/>
                    </div>
                </td>

            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width170">
                        MR Number:
                    </label>

                </td>
                <td><g:textField name="mrNo" id="mrNo" value="Auto" readonly="readonly"/></td>

            </tr>
        </table>

        <div class="jqgrid-container" id="gridDiv">
            <table id="jqgrid-grid"></table>

            <div id="jqgrid-pager"></div>
        </div>
    </div>
    <br/>

    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Save')}"
                                    onclick="executeAjax();"/></span>

        %{--<span class="button"><input type="button" name="select-button" id="select-button"--}%
                                    %{--class="ui-button ui-widget ui-state-default ui-corner-all" value="Select All"--}%
                                    %{--onclick="selectAllApplicant();"/></span>--}%

        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick="clearAll();" value="Cancel"/></span>
    </div>
</form>

