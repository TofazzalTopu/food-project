<%--
  Created by IntelliJ IDEA.
  User: NZ
  Date: 2/1/2016
  Time: 11:36 AM
--%>
<%@ page import="com.bits.bdfp.accounts.ChartOfAccounts" %>
<form name='gFormChartOfAccount' id='gFormChartOfAccount'>
    <g:hiddenField name="id" value="${division?.id}"/>
    <g:hiddenField name="version" value="${division?.version}"/>
    <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>
    <g:hiddenField name="enterpriseConfiguration.code" id="entCode" value=""/>
    <g:hiddenField name="enterpriseConfiguration.codeLength" id="entCodeLength" value="${codeLength}"/>
    <g:hiddenField name="enterpriseConfiguration.noOfLayers" id="entLayers" value="${layer}"/>
    <div id="remote-content-division"></div>

    <div style="padding-bottom: 50px;">
        <div style="width: 30%;float: left;">
            <table class="element_row_content_container lightColorbg pad_bot0">
                <tr class="element_row_content_container lightColorbg pad_bot0">
                    <td>
                        <label class="txtright bold hight1x width90">
                            <g:message code="territoryConfiguration.enterpriseConfiguration.label"
                                       default="Enterprise"/>
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>

                    <g:if test="${result}">
                        <g:if test="${list.size() == 1}">
                            <td>
                                <g:textField class="width250" name="enterPriseName" id="enterPriseName" disabled="disabled"
                                             value="${list[0].name}"/>
                                <script type="text/javascript">
                                    $(document).ready(function () {
                                        $("#enterpriseConfiguration").val("${list[0].id}");
                                        $("#entCode").val("${list[0].code}");
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
                </tr>
                <tr class="element_row_content_container lightColorbg pad_bot0">
                    <td>
                        <label class="txtright bold hight1x width90">
                            <g:message code="division.geoCode.label" default="Complete Code"/>
                        </label>
                    </td>
                    <td>
                        <g:textField class="width250" name="completeCode" id="completeCode" readonly="readonly"
                                     value=""/>
                    </td>
                </tr>
                <tr class="element_row_content_container lightColorbg pad_bot0">
                    <td>
                        <label class="txtright bold hight1x width90">
                            <g:message code="division.geoCode.label" default="Parent Name"/>
                        </label>
                    </td>
                    <td>
                        <g:textField class="width250" name="parentName" id="parentName"
                                     value="" readonly="readonly"/>
                    </td>
                </tr>
                <tr class="element_row_content_container lightColorbg pad_bot0">
                    <td>
                        <label class="txtright bold hight1x width90">
                            <g:message code="division.geoCode.label" default="Parent Code"/>
                        </label>
                    </td>
                    <td>
                        <g:textField class="width250" name="parentCode" id="parentCode"
                                     value="" readonly="readonly"/>
                    </td>
                </tr>
                <tr class="element_row_content_container lightColorbg pad_bot0">
                    <td>
                        <label class="txtright bold hight1x width90">
                            <g:message code="division.geoCode.label" default="Name"/>
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <g:textField class="width250" name="name" id="name"
                                     value=""/>
                    </td>
                </tr>
                <tr class="element_row_content_container lightColorbg pad_bot0">
                    <td>
                        <label class="txtright bold hight1x width90">
                            <g:message code="division.geoCode.label" default="Code"/>
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <g:textField class="width250" name="code" id="code"
                                     value=""/>
                    </td>
                </tr>
                <tr class="element_row_content_container lightColorbg pad_bot0">
                    <td>
                        <span class="button" id="layerAdder"><input type="button"
                                                                                    name="add-layer-button"
                                                                                    id="add-layer-button"
                                                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                                                    value="Add Layer"
                                                                                    onclick="addChild();"/></span>

                    </td>
                    <td>
                        <span class="button"><input type="button" name="add-button" id="add-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Save Data"
                                                    onclick="setData();"/></span>
                    </td>
                    <td>
                        <span class="button"><input type="button" name="create-button" id="create-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Create"
                                                    onclick="createOrUpdate();"/></span>

                    </td>
                </tr>
            </table>
        </div>

        <div style="margin-left: 50%;">
            <div id="tree"></div>
        </div>
    </div>

    %{--<div class="jqgrid-container">--}%
        %{--<table id="tree-grid"></table>--}%

        %{--<div id="tree-grid-pager"></div>--}%
    %{--</div>--}%

    <div class="buttons">

    </div>
</form>
