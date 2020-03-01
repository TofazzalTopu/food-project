<%@ page import="com.bits.bdfp.inventory.demandorder.AdjustUsingHo" %>


<div id="spinnerAdjustUsingHo" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>

<form name='gFormAdjustUsingHo' id='gFormAdjustUsingHo'>
    <g:hiddenField name="id" value="${adjustUsingHo?.id}"/>
    <g:hiddenField name="version" value="${adjustUsingHo?.version}"/>
    <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>
    <div id="remote-content-adjustUsingHo"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 150px;">
                        <g:message code="secondaryDemandOrder.enterpriseConfiguration.label"
                                   default="Enterprise "/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>

                <g:if test="${result}">
                    <g:if test="${list.size() == 1}">
                        <td>
                            <g:textField name="enterPriseName" id="enterPriseName" readonly="readonly"
                                         value="${list[0].name}" style="width: 300px;"/>
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
                    <label for="territoryConfiguration" class="txtright bold hight1x width1x"
                           style="width: 150px;">
                        <g:message code='mushak.invoice.label' default='Territory Configuration'/>
                    </label>
                </td>
                <td>
                    <g:select name="territoryConfiguration.id" id="territoryConfiguration"
                              from="" onchange="loadGeo(this.value);"
                              style="width: 206px;"
                              optionKey="id"
                              value=""/>
                </td>
                <td>
                    <label for="territorySubArea" class="txtright bold hight1x width1x"
                           style="width: 150px;">
                        <g:message code='mushak.invoice.label' default='Geo Location'/>
                    </label>
                </td>
                <td>
                    <g:select name="territorySubArea.id" id="territorySubArea"
                              from="" onchange="clearData();"
                              style="width: 206px;"
                              optionKey="id"
                              value=""/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="customerMaster" class="txtright bold hight1x width1x"
                           style="width: 150px;">
                        <g:message code='mushak.invoice.label' default='Customer Name'/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <input type="text" id="searchKey" name="searchKey" class="width180"/>
                    <input type="hidden" id="customerMaster" name="customerMaster.id"/>
                    <span id="search-btn-customer-id" title="" role="button"
                          class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                        <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                        <span class="ui-button-text"></span>
                    </span>
                </td>
                <td>
                    <label for="customerMasterCode" class="txtright bold hight1x width1x"
                           style="width: 150px;">
                        <g:message code='mushak.invoice.label' default='Customer Code'/>
                    </label>
                </td>
                <td>
                    <g:textField name="customerMaster.code" id="customerMasterCode"
                                 style="width: 200px;"
                                 value="" readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="totalAmount" class="txtright bold hight1x width1x"
                           style="width: 150px;">
                        <g:message code='writeOff.writeOffAmount.label' default='Total Received Amount'/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textField name="totalAmount" id="totalAmount"
                                 style="width: 200px; text-align: right"
                                 value="" onkeyup="applyDue(this.value)"/>
                </td>
                <td>
                    <label for="totalAdjustedAmount" class="txtright bold hight1x width1x"
                           style="width: 150px;">
                        <g:message code='writeOff.writeOffAmount.label' default='Total Adjust Amount'/>
                    </label>
                </td>
                <td>
                    <g:textField name="totalAdjustedAmount" id="totalAdjustedAmount"
                                 style="width: 200px; text-align: right"
                                 value="" readonly="readonly"/>
                </td>
            </tr>
        </table>
    </div>

    <div class="clear"></div>

    <div class="jqgrid-container">
        <table id="jqgrid-grid-adjustUsingHo"></table>
        <div id="jqgrid-pager-adjustUsingHo"></div>
    </div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="create-button" id="create-button-adjustUsingHo"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxAdjustUsingHo();"/></span>
    </div>
</form>
