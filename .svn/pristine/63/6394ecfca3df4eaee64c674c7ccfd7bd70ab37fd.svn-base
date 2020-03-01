<%@ page import="com.bits.bdfp.inventory.setup.Quotation" %>
<div id="spinnerQuotation" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>

<form name='gFormCs' id='gFormCs'>
    <g:hiddenField name="id" value="${quotation?.id}"/>
    <g:hiddenField name="version" value="${quotation?.version}"/>
    <div id="remote-content-quotation"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
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
        </table>

        <table>
            <tr>
                <td>
                    <div class="clear"></div>

                    <div class="jqgrid-container">
                        <table id="jqgrid-grid-quotation"></table>

                        <div id="jqgrid-pager-quotation"></div>
                    </div>
                </td>
                <td>
                    <div class="clear"></div>

                    <div class="jqgrid-container">
                        <table id="jqgrid-grid-product"></table>

                        <div id="jqgrid-pager-product"></div>
                    </div>
                </td>
            </tr>
        </table>

        <div class="clear"></div>

        <div class="jqgrid-container">
            <table id="jqgrid-grid-cs"></table>

            <div id="jqgrid-pager-cs"></div>
        </div>

    </div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="save-button" id="save-button-quotation"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Create CS"
                                    onclick="executeAjaxQuotation();"/></span>

    </div>
</form>