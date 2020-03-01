<%@ page import="com.bits.bdfp.inventory.sales.ProcessMarketReturn" %>

<form name='gViewProcessedMarketReturn' id='gViewProcessedMarketReturn'>
    <g:hiddenField name="id" value=""/>
    <g:hiddenField name="version" value="0"/>
    <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>
    <div id="remote-content-cashReceivedFromBranch"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x width130">
                        Enterprise
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <g:if test="${result}">
                    <g:if test="${list.size() == 1}">
                        <td>
                            <g:textField class="width180" name="enterPriseName" id="enterPriseName" readonly="readonly"
                                         value="${list[0].name}" />
                            <script type="text/javascript">
                                $(document).ready(function () {
                                    $("#enterpriseConfiguration").val("${list[0].id}");
                                });
                            </script>
                        </td>
                    </g:if>
                    <g:else>
                        <td><div id="enterpriselist" style="width: 180px;"></div></td>
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
            <tr id="customer_type_dev" hidden="hidden">
                <td>
                    <label for="isNonDpCustomer" class="txtright bold hight1x width130" >
                        Non-DP Customer
                    </label>
                </td>
                <td>
                    <g:checkBox name="isNonDpCustomer" id="isNonDpCustomer" value="false"
                                onclick="customerTypeChange();"/>
                </td>
            </tr>
            <tr id="dp_dev">
                <td>
                    <label for="distributionPoint" class="txtright bold hight1x width130">
                        Distribution Point
                        <span class="mendatory_field">*</span>
                    </label>

                </td>
                <td id="single">
                    <g:textField class="width180" name="distributionPoint.name"
                                 id="dpName" value="" readonly="readonly"/>
                    <g:hiddenField name="distributionPoint.id" id="distributionPointId"/>
                </td>
                <td id="multi" hidden="hidden">
                    <g:select class="width185" name="distributionPoint.id" id="distributionPoint"
                              from="" optionKey="id" value=""/>
                </td>
            </tr>
            <tr id="customer_dev">
                <td>
                    <label for="searchKey" class="txtright bold hight1x width130">
                        Customer Name
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <input type="text" id="searchKey" name="searchKey" class="width160"/>
                    <input type="hidden" id="customerMaster" name="customerMaster.id"/>
                    <span id="search-btn-customer-id" title="" role="button"
                          class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                        <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                        <span class="ui-button-text"></span>
                    </span>
                </td>
                <td>
                    <label for="code" class="txtright bold hight1x width130">
                        Customer Code
                    </label>
                </td>
                <td>
                    <g:textField class="width180 alin_right" name="code" id="code"
                                 value="" readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="dateFrom" class="txtright bold hight1x width130">
                        Return Date From
                    </label>
                </td>
                <td>
                    <g:textField class="width180 validate[required] text-input datepicker"
                                 name="dateFrom" id="dateFrom" value=""/>
                </td>
                <td>
                    <label for="dateTo" class="txtright bold hight1x width130">
                        Date To
                    </label>
                </td>
                <td>
                    <g:textField class="width180 validate[required] text-input datepicker"
                                 name="dateTo" id="dateTo" value=""/>
                </td>
            </tr>
        </table>

        <br/>

        <span class="button"><input type="button" name="search-button" id="search-button-view"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Search"
                                    onclick="searchMarketReturn();"/></span>

        <br/><br/>

    </div>

    <div class="clear"></div>
    <div class="jqgrid-container">
        <table id="jqgrid-grid-processed"></table>

        <div id="jqgrid-pager-processed"></div>
    </div>

    <div class="clear"></div>
    <div class="jqgrid-container-2">
        <table id="jqgrid-grid-details"></table>

        <div id="jqgrid-pager-details"></div>
    </div>

</form>
