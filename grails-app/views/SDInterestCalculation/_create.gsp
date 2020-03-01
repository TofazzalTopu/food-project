<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder" %>
<form name='gFormSDInterestCalculation' id='gFormSDInterestCalculation'>
    <g:hiddenField name="id" value="${secondaryDemandOrder?.id}"/>
    <g:hiddenField name="version" value="${secondaryDemandOrder?.version}"/>
    <g:hiddenField name="idEnterprise" id="idEnterprise" value=""/>
    <g:hiddenField name="defaultCustomerId" id="defaultCustomerId" value=""/>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width2x">
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
                    %{--<label class="txtright bold hight1x width1x">--}%
                        %{--<g:message code="secondaryDemandOrder.businessUnitConfiguration.label"--}%
                                   %{--default="As Of Date"/>--}%
                        %{--<span class="mendatory_field">*</span>--}%
                    %{--</label>--}%
                </td>
                <td>
                    %{--<g:textField name="asOfDate" id="asOfDate"/>--}%
                </td>
            </tr>

            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width2x">
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
                    <label class="txtright bold hight1x width2x">
                        Select DP
                    </label>
                </td>
                <td>
                    <g:select name="distributionPoint" class="width2x" style="margin-right: 5px !important;"
                              optionKey="id"
                              optionValue="name"
                              noSelection="['':' - Select DP - ']"
                              onchange="loadDpDefaultCustomer(this.value);"
                    />
                </td>
                <td>
                    <label class="txtright bold hight1x width1x">
                        DP ID
                    </label>
                </td>
                <td>
                    <g:textField name="dpId" readonly="true"/>
                </td>
            </tr>

            <tr class="element_row_content_container  lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width2x">
                        Default Customer Name
                    </label>
                </td>
                <td>
                    <g:textField name="defaultCustomer" class="width2x" readonly="readonly"/>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x">
                        Default Customer ID
                    </label>
                </td>
                <td>
                    <g:textField name="defaultCustomerCode" readonly="true"/>
                    <g:hiddenField name="defaultCustomerId" readonly="true"/>
                </td>
            </tr>
            <tr class="element_row_content_container  lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width2x">
                        Interest % Per Year
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textField name="interest" class="width1x"/>
                </td>
                <td>
                    <label class="txtright bold hight1x width2x">
                        Select Quarter to Calculate
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:select from="[['id':1, 'name':'Q-1'],['id':2, 'name':'Q-2'],['id':3, 'name':'Q-3'],['id':4, 'name':'Q-4']]"
                              optionKey="id" optionValue="name" noSelection="['':'Select..']" name="quarter" disabled="true" onchange="checkInterest(this.value,'q');"/>
                </td>
            </tr>

            <tr class="element_row_content_container  lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold height1x width2x">
                    </label>
                </td>
                <td>
                    <div id="defaultArea">
                        <span class="button"><input type="button" name="ci-button" id="ci-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Calculate Interest"
                                                    onclick="calculateSdInterest();"/></span>
                        <g:hiddenField name="isAlreadyCreated"/>
                        <g:hiddenField name="isLasQuarterCalculated"/>
                    </div>
                </td>
            </tr>

            <tr class="lightColorbg pad_bot0">
                <td>
                    <h3>Default Customer Security Deposit:</h3>
                </td>
            </tr>

            <tr class="element_row_content_container  lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width2x">
                        Last Quarter Closing Balance
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textField name="lastQob" class="width1x" readonly="readonly"/>
                </td>
                <td>
                    <label class="txtright bold hight1x width2x">
                        Next Quarter Opening Balance
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textField name="nextQob" class="width1x" readonly="readonly"/>
                </td>
            </tr>
        </table>

        <div class="height10"></div>

        <div class="jqgrid-container" id="gridTP">
            <table id="jqgrid-tp-grid"></table>

            <div id="jqgrid-tp-pager"></div>

            <div class="height10"></div>

            <div class="">
                <span class="button floatR pad_right60"><input type="button" name="save-button" id="save-button"
                                            class="ui-button ui-widget ui-state-default ui-corner-all"
                                            value="Save"
                                            onclick="saveSdInterest();"/></span>
            </div>

            <div class="height10 clear"></div>
        </div>
        <div class="height10 clear"></div>
    </div>
</form>