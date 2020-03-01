<%@ page import="com.bits.bdfp.inventory.setup.Quotation" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="quotation.create.label" default="Create Quotation"/></title>

<div class="main_container">
    <div class="content_container">
        <h1><g:message code="quotation.create.label" default="Create Quotation"/></h1>

        <h3><g:message code="quotation.Info.label" default="Quotation Details"/></h3>

        <div class="element_row_content_container lightColorbg pad_bot0">
            <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>
            <table>
                <tr>
                    <td>
                        <label class="txtright bold hight1x width1x">
                            <g:message code="secondaryDemandOrder.enterpriseConfiguration.label"
                                       default="Enterprise "/>
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>

                    <g:if test="${result}">
                        <g:if test="${list.size() == 1}">
                            <td>
                                <g:textField name="enterPriseName" id="enterPriseName" readonly="readonly"
                                             value="${list[0].name}" class="width193"/>
                                <script type="text/javascript">
                                    $(document).ready(function () {
                                        $("#enterpriseConfiguration").val("${list[0].id}");
                                    });
                                </script>
                            </td>
                        </g:if>
                        <g:else>
                            <td><div id="enterpriselist" class="width193"></div></td>
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
                        <label for="quotationNo" class="txtright bold hight1x width1x">
                            <g:message code='mushak.invoice.label' default='Quotation No'/>
                        </label>
                        %{--<g:message code='quotation.quotationNo.label' default='Quotation No'/>--}%
                    </td>
                    <td>
                        <g:textField name="quotationNo" id="quotationNo" class="width193"
                                     value=""/>
                    </td>
                    <td>
                        <label for="date" class="txtright bold hight1x width1x">
                            <g:message code='mushak.invoice.label' default='Quotation Date'/>
                        </label>
                        %{--<g:message code='quotation.quotationNo.label' default='Quotation No'/>--}%
                    </td>
                    <td>
                        <g:textField name="date" id="date" class="width193" style="text-align: center;"
                                     value="" readonly="readonly"/>
                    </td>
                </tr>
            </table>
            <span class="button"><input type="button" name="search-button" id="search-button-quotation"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="Search"
                                        onclick="renderPage();"/></span>
            <span class="button"><input type="button" name="new-button" id="new-button-quotation"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="New Quotation"
                                        onclick="renderPage(0);"/></span>

        </div>
        <div class="clear"></div>

        <div id="createDiv">
        </div>
        %{--<g:render template='create'/>--}%
        <div class="clear height5"></div>
        <g:render template="script"/>

        <div id="dialog-confirm-quotation" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>This item will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>

    </div>
</div>
