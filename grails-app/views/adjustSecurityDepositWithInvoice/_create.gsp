<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder" %>
%{--<form name='gFormCashTroughBank' id='gFormCashTroughBank'>--}%
<form name='gFormAdjustSecurityDeposit' id='gFormAdjustSecurityDeposit'>
    <g:hiddenField name="id" value=""/>
    <g:hiddenField name="version" value=""/>
    <g:hiddenField name="idEnterprise" id="idEnterprise" value=""/>

    <g:hiddenField name="defaultCustomerId" value=""/>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>

            <tr>
                %{--<td>--}%
                    %{--<label class="txtright bold hight1x width1x">--}%
                        %{--<g:message code="secondaryDemandOrder.deliveryDate.label" default="ID"/>--}%
                    %{--</label>--}%
                %{--</td>--}%
                <td>
                    <label for="isDpCustomers" class="txtright bold hight1x width1x">
                        %{--<g:checkBox name="isDpCustomer" id="isDpCustomer" checked="false"/>--}%
                        <input type="checkbox" id="isDpCustomers" name="isDpCustomers" style="text-align: right;" onclick="SelectIsDpCustomers()"/>
                        DP Customer
                    </label>
                </td>
            </tr>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        <g:message code="secondaryDemandOrder.enterpriseConfiguration.label"
                                   default="Enterprise "/>
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

            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0" id="territoryRow" hidden="hidden">
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
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0" id="dpRow" hidden="hidden">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Select DP
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:select name="distributionPoint" id="distributionPoint" class="width2x"
                              optionKey="id"
                              optionValue="name"
                              noSelection="['':' - Select DP - ']"
                              onchange="loadDpDefaultCustomer(this.value);"
                    />
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0" id="customerRow">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Select Customer
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <input type="text" id="searchKey" name="searchKey" class="width170 validate[required]"/>
                    <input type="hidden" id="primaryCustomer" name="primaryCustomer.id"/>
                    <input type="hidden" id="primaryCustomerCategory" name="primaryCustomerCategory"/>
                    <span id="search-btn-primary-customer-id" title="" role="button"
                          class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                        <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                        <span class="ui-button-text"></span>
                    </span>
                </td>
            </tr>
            <tr class="element_row_content_container  lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Default Customer
                    </label>
                </td>
                <td>
                    <g:textField name="defaultCustomer" class="width2x" readonly="readonly"/>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x">
                        ID
                    </label>
                </td>
                <td>
                    <g:textField name="defaultCustomerCode" readonly="readonly"/>
                </td>
            </tr>

            <tr class="element_row_content_container  lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold width1x">
                        Total available SD
                    </label>
                </td>
                <td>
                    <g:textField name="availableDPSecurityDeposit" readonly="readonly" class="width2x"/>
                </td>
                <td style="width: 150px;">

                </td>
                <td>

                </td>
            </tr>
        </table>

        <div class="height10"></div>

        <div class="jqgrid-container" id="gridTP">
            <table id="jqgrid-tp-grid"></table>

            <div id="jqgrid-tp-pager"></div>

            <div class="height10"></div>


            </div>
        <div class="height10"></div>
        <table><tr><td>
        <div class="width515">
            <g:textField name="tpId" class="width2x" readonly="readonly" hidden="true"/>

            <label id="lblAmountToBeAdjusted" class="txtright bold width1x">
                Amount to be Adjusted
            <span class="mendatory_field">*</span>
            </label>
        </div>

            <g:textField name="amountAdjusted" class="width2x validate[required]"/>

        </td></tr></table>

        <div class="height10 clear"></div>
            <div class="pad_left15 width515">
                <span class="button floatR"><input type="button" name="tp-save-button" id="tp-save-button"
                                            class="ui-button ui-widget ui-state-default ui-corner-all"
                                            value="Adjust with Invoice"
                                            onclick="adjustWithInvoice();"/></span>
            </div>

            <div class="height10 clear"></div>
        </div>
        <div class="height10 clear"></div>
    </div>
</form>