<%@ page import="com.bits.bdfp.inventory.setup.SalesCommission" %>


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

<form name='gFormAdjustCommission' id='gFormAdjustCommission'>
    <g:hiddenField name="id" value=""/>
    <g:hiddenField name="version" value="0"/>
    <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>
    <div id="remote-content-cashReceivedFromBranch"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x width150">
                        Enterprise
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <g:if test="${result}">
                    <g:if test="${list.size() == 1}">
                        <td>
                            <g:textField name="enterPriseName" id="enterPriseName" readonly="readonly"
                                         value="${list[0].name}" style="width: 200px;"/>
                            <script type="text/javascript">
                                $(document).ready(function () {
                                    $("#enterpriseConfiguration").val("${list[0].id}");
                                });
                            </script>
                        </td>
                    </g:if>
                    <g:else>
                        <td><div id="enterpriselist" style="width: 200px;"></div></td>
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
                        As of Date
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textField class="validate[required] text-input datepicker width200"
                                 name="date" id="date"
                                 value="${currentDate}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="distributionPoint" class="txtright bold hight1x width150">
                        Distribution Point
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:select name="distributionPoint.id" id="distributionPoint" class="width205"
                              from="" onchange="setDefaultCustomer();"
                              optionKey="id"
                              value=""/>
                </td>
                <td>
                    <label for="defaultCustomer" class="txtright bold hight1x width150">
                        DP Default Customer
                    </label>
                </td>
                <td>
                    <g:textField name="defaultCustomer" id="defaultCustomer" class="width200"
                                 value="" readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="searchKey" class="txtright bold hight1x width150">
                        Customer Name
                    </label>
                </td>
                <td>
                    <input type="text" id="searchKey" name="searchKey" style="width: 182px"/>
                    <input type="hidden" id="customerMaster" name="customerMaster.id"/>
                    <span id="search-btn-customer-id" title="" role="button"
                          class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                        <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                        <span class="ui-button-text"></span>
                    </span>
                </td>
                <td>
                    <label for="code" class="txtright bold hight1x width150">
                        Customer Code
                    </label>
                </td>
                <td>
                    <g:textField name="code" id="code"
                                 style="width: 200px; text-align: left;"
                                 value="" readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="availableCommission" class="txtright bold hight1x width150">
                        Available Commission
                    </label>
                </td>
                <td>
                    <g:textField name="availableCommission" id="availableCommission" class="width200 alin_right"
                                 value="" readonly="readonly"/>
                    <g:hiddenField name="commission" id="commission" value=""/>
                </td>
                <td>
                    <label for="totalAdjusted" class="txtright bold hight1x width150">
                        Total Adjusted Amount
                    </label>
                </td>
                <td>
                    <g:textField name="totalAdjusted" id="totalAdjusted" class="width200 alin_right"
                                 value="" readonly="readonly"/>
                </td>
            </tr>
        </table>
    </div>

    <div class="clear"></div>
    <div class="jqgrid-container">
        <table id="jqgrid-grid-unAdjusted"></table>

        <div id="jqgrid-pager-unAdjusted"></div>
    </div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="adjust-button" id="create-button-adjust"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Adjust Against Invoice"
                                    onclick="executeAjaxAdjust();"/></span>
    </div>
</form>
