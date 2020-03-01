<%--
  Created by IntelliJ IDEA.
  User: NZ
  Date: 2/1/2016
  Time: 11:36 AM
--%>
<%@ page import="com.bits.bdfp.accounts.ChartOfAccountLayer" %>
<form name='gFormChartOfAccountLayer' id='gFormChartOfAccountLayer'>
    <g:hiddenField name="id" value="${division?.id}"/>
    <g:hiddenField name="version" value="${division?.version}"/>
    <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>
    <div id="remote-content-division"></div>

    <table>
        <tr class="element_row_content_container lightColorbg pad_bot0">
            <td>
                <label class="txtright bold hight1x width1x">
                    <g:message code="territoryConfiguration.enterpriseConfiguration.label"
                               default="Enterprise"/>
                    <span class="mendatory_field">*</span>
                </label>
            </td>

            <g:if test="${result}">
                <g:if test="${list.size() == 1}">
                    <td>
                        <g:textField name="enterPriseName" id="enterPriseName" disabled="disabled"
                                     value="${list[0].name}"/>
                        <script type="text/javascript">
                            $(document).ready(function () {
                                $("#enterpriseConfiguration").val("${list[0].id}");
                            })
                        </script>
                    </td>
                </g:if>
                <g:else>
                %{--<td><g:select name="enterpriseConfiguration.id"--}%
                %{--from="${list}" optionKey="id"--}%
                %{--value="${territoryConfiguration?.enterpriseConfiguration?.id}"/></td>--}%
                    <td><div id="enterpriselist" class="validate[required]" style="width: 350px;"></div></td>
                    <script type="text/javascript">
                        %{--alert("${result}");--}%
                        jQuery(document).ready(function () {
                            $("#enterpriselist").empty();
                            $("#enterpriselist").flexbox(${result}, {
                                watermark: "Select Enterprise",
                                width: 317,
                                onSelect: function () {
                                    $("#enterpriseConfiguration").val($('#enterpriselist_hidden').val());
                                }
                            });
                            $('#enterpriselist_input').blur(function () {
                                if ($('#enterpriselist_input').val() == '') {
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
            <td>
                <label class="txtright bold hight1x width1x">
                    <g:message code="division.geoCode.label" default="Max. Code Length"/>
                </label>
            </td>
            <td>
                <g:textField name="maxLength" id="maxLength" maxlength="30"
                             value="" readonly="readonly"/>
            </td>
        </tr>
    </table>

    <table id="popEmpDetails">
    </table>

    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="createLayers();"/></span>
        %{--<span class="button"><input type='button' name="delete-button" id="delete-button"--}%
        %{--class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'--}%
        %{--onclick="deleteAjaxDivision();"/></span>--}%
        %{--<span class="button"><input type="button" name="cancel-button" id="cancel-button"--}%
                                    %{--class="ui-button ui-widget ui-state-default ui-corner-all"--}%
                                    %{--onclick=" reset_form('#gFormDivision');" value="Cancel"/></span>--}%
    </div>
</form>
