<%@ page import="com.bits.bdfp.inventory.demandorder.Invoice" %>
<form name='gFormUnadjustedInvoice' id='gFormUnadjustedInvoice'>
    <g:hiddenField name="id" id="id" value=""/>
    <g:hiddenField name="version" id="version" value=""/>
    <g:hiddenField name="enterpriseConfiguration.id"  id="enterpriseConfiguration"/>
    <div id="remote-content-unadjusted-invoice"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x width1x">
                        <g:message code="secondaryDemandOrder.enterpriseConfiguration.label"
                                   default="Enterprise "/>
                        <span class="mendatory_field"> * </span>
                    </label>
                </td>

                <g:if test="${result}">
                    <g:if test="${list.size()==1}">
                        <td>
                            <g:textField name="enterPriseName" id="enterPriseName" readonly="readonly" value="${list[0].name}" style="width: 150px;"/>
                            <script type="text/javascript">
                                $(document).ready(function(){
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
            <tr>
                <td>
                    <label for="dateFrom" class="txtright bold hight1x width1x">
                        <g:message code='mushak.invoice.label' default='Date From'/>
                    </label>

                </td>
                <td>
                    <g:textField name="dateFrom" id="dateFrom" value="" style="text-align: center;" class="width150"/>
                </td>
                <td>
                    <label for="dateTo" class="txtright bold hight1x width1x">
                        <g:message code='mushak.invoice.label' default='Date To'/>
                    </label>

                </td>
                <td>
                    <g:textField name="dateTo" id="dateTo" value="" style="text-align: center;" class="width150"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="searchCustomerKey" class="txtright bold hight1x width1x"><g:message
                            code='mushak.invoice.label' default='Customer'/>
                    </label>

                </td>
                <td>
                    <input type="text" id="searchKey" name="searchKey" class="width150"/>
                    <input type="hidden" id="customer" name="customer.id"/>
                    <span id="search-btn-customer-id" title="" role="button"
                          class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                        <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                        <span class="ui-button-text"></span>
                    </span>
                </td>
                <td>
                    <label for="code" class="txtright bold hight1x width1x">
                        <g:message code='mushak.name.label' default='Customer Code'/>
                    </label>

                </td>
                <td>
                    <g:textField name="code"
                                 id="code" class="width150"
                                 value=""/>
                </td>
            </tr>
        </table>

    </div>

    <div class="clear"></div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="search-button" id="search-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Search"
                                    onclick="searchInvoice();"/></span>
    </div>

    <div class="clear"></div>

    <div class="jqgrid-container">
        <table id="jqgrid-grid-unadjusted-invoice"></table>

        <div id="jqgrid-pager-unadjusted-invoice"></div>
    </div>

</form>
