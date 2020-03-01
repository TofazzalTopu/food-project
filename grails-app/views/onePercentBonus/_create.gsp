<%@ page import="com.bits.bdfp.bonus.OnePercentBonus" %>
<form name='gFormOnePercentBonus' id='gFormOnePercentBonus'>
    <g:hiddenField name="id" value="${onePercentBonus?.id}"/>
    <g:hiddenField name="version" value="${onePercentBonus?.version}"/>
    <g:hiddenField name="enterpriseConfiguration" id="enterpriseConfiguration" value=""/>
    <div id="remote-content-onePercentBonus"></div>

    <div>
        <table>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 105px;">
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
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                <label class="txtright bold hight1x width1x" style="width: 105px;">
                    Bonus Percent
                    <span class="mendatory_field">*</span>
                </label>
                </td>
                <td>
                    <g:select class="validate[required]" name="bonusPercent.id" optionKey="id"
                              noSelection="['': 'Select Bonus']" style="width: 300px;" optionValue="percentage"
                              id="bonusPercent" onchange="listProduct(this.value)"
                              value="" from="${bonus}"/>

                </td>
            </tr>
        </table>
        <table>
            <tr>
                <td>
                    <div class="jqgrid-container">
                        <table id="jqgrid-grid-territory"></table>

                        <div id="jqgrid-pager-territory"></div>
                    </div>
                </td>
                <td>
                    <div class="jqgrid-container">
                        <table id="jqgrid-grid-geolocation"></table>

                        <div id="jqgrid-pager-geolocation"></div>
                    </div>
                </td>
                <td>
                    <div class="jqgrid-container">
                        <table id="jqgrid-grid-category"></table>

                        <div id="jqgrid-pager-category"></div>
                    </div>
                </td>
            </tr>
        </table>
        <table>
            <tr>
                <td>
                    <div class="jqgrid-container">
                        <table id="jqgrid-grid-customer"></table>

                        <div id="jqgrid-pager-customer"></div>
                    </div>
                </td>
                <td>
                    <div class="jqgrid-container">
                        <table id="jqgrid-grid-product"></table>

                        <div id="jqgrid-pager-product"></div>
                    </div>
                </td>
            </tr>
        </table>
    </div>

    <div class="clear"></div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="create-button" id="create-button-onePercentBonus"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxOnePercentBonus();"/></span>
        <span class="button"><input name="clearFormButtonOnePercentBonus"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" type="button"
                                    onclick=" reset_form('#gFormOnePercentBonus');" value="Cancel"/></span>
    </div>
</form>
