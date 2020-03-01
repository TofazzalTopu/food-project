<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder" %>
<form name='gFormViewSecurityDeposit' id='gFormViewSecurityDeposit'>
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
                                        $("#enterpriseConfiguration").val($('#enterpriselist_hidden').val());
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
                        As Of Date
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
                              noSelection="['':' - Select Territory - ']"
                              onchange="loadDP(this.value);"
                    />
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
                              noSelection="['':' - Select DP - ']"
                              onchange="loadDpDefaultCustomer(this.value);"
                    />
                </td>
            </tr>

            <tr class="element_row_content_container  lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Customer Name
                    </label>
                </td>
                <td>
                    <g:textField name="defaultCustomer" class="width2x"/>

                    <div style="display: inline-block; margin-bottom: -4px;">
                        <span id="search-btn-customer-id" title="" role="button"
                              class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatL">
                            <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                            <span class="ui-button-text"></span>
                        </span>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x">
                        ID
                    </label>
                </td>
                <td>
                    <g:textField name="defaultCustomerCode" readonly="readonly"/>
                    <g:hiddenField name="defaultCustomerId"/>
                </td>
            </tr>

            <tr class="element_row_content_container  lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold width1x">
                        Available Distribution Partner Security Deposit
                    </label>
                </td>
                <td>
                    <g:textField name="availableDPSecurityDeposit" readonly="readonly" class="width2x"/>
                </td>
                <td style="width: 150px;">

                </td>
                <td>
                    <div id="defaultArea">
                        %{--<span class="button"><input type="button" name="edit-button" id="edit-button"--}%
                                                    %{--class="ui-button ui-widget ui-state-default ui-corner-all"--}%
                                                    %{--value="${message(code: 'default.button.edit.label', default: 'Edit')}"--}%
                                                    %{--onclick="updateDefaultCustomer();"/></span>--}%
                        %{--<span class="button"><input type="button" name="cancel-button" id="cancel-button"--}%
                                                    %{--class="ui-button ui-widget ui-state-default ui-corner-all"--}%
                                                    %{--value="${message(code: 'default.button.cancel.label', default: 'Cancel')}"--}%
                                                    %{--onclick="clearAll();"/></span>--}%
                    </div>
                </td>
            </tr>
        </table>

        <div class="height10"></div>

        <div class="jqgrid-container" id="gridTP">
            <table id="jqgrid-tp-grid"></table>

            <div id="jqgrid-tp-pager"></div>

            <div class="height10"></div>

            <div class="width515 floatL">
                <div class="floatR">
                    <label class="txtright bold width2x">
                        Total Available SD
                    </label>
                    <g:textField name="totalAvailableSD" class="width2x" readonly="true" style="text-align: right;"/>
                    <g:hiddenField name="tpId" class="width2x" readonly="true"/>
                </div>
            </div>
            <div class="floatL pad_left15">
                %{--<span class="button"><input type="button" name="tp-edit-button" id="tp-edit-button"--}%
                                            %{--class="ui-button ui-widget ui-state-default ui-corner-all"--}%
                                            %{--value="${message(code: 'default.button.edit.label', default: 'Edit')}"--}%
                                            %{--onclick="updateTpCustomer();"/></span>--}%
                %{--<span class="button"><input type="button" name="tp-cancel-button" id="tp-cancel-button"--}%
                                            %{--class="ui-button ui-widget ui-state-default ui-corner-all"--}%
                                            %{--value="${message(code: 'default.button.cancel.label', default: 'Cancel')}"--}%
                                            %{--onclick="clearTp();"/></span>--}%
            </div>

            <div class="height10 clear"></div>
        </div>
        <div class="height10 clear"></div>
    </div>
</form>