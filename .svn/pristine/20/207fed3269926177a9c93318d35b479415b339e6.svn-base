<%@ page import="com.bits.bdfp.geolocation.TerritoryConfiguration" %>
<form name='gFormTerritoryConfiguration' id='gFormTerritoryConfiguration'>
    <g:hiddenField name="id" id="territoryId" value="${territoryConfiguration?.id}"/>
    <g:hiddenField name="version" value="${territoryConfiguration?.version}"/>
    <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>
    <g:hiddenField name="businessUnitConfiguration.id" id="businessUnitConfiguration" value=""/>
    <g:hiddenField name="isActiveVal" id="isActiveVal" value="true"/>
    <div id="remote-content-territoryConfiguration"></div>
    <br>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
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
                                $(document).ready(function(){
                                    $("#enterpriseConfiguration").val("${list[0].id}");
                                    loadBusinessUnit(${list[0].id});
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
                                        loadBusinessUnit($('#enterpriselist_hidden').val());
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
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        <g:message code="territoryConfiguration.businessUnitConfiguration.label"
                                   default="Business Unit"/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div id="businessUnitList" class="validate[required]" style="width: 350px;"></div>
                    %{--<g:select name="businessUnitConfiguration.id"--}%
                    %{--optionKey="id"--}%
                    %{--style="width: 322px; height: 20px;"--}%
                    %{--id="businessUnitConfiguration"--}%
                    %{--value=""--}%
                    %{--noSelection="['null': '']"/>--}%
                </td>
            </tr>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        <g:message code="territoryConfiguration.name.label" default="Territory Name "/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td><g:textField class="validate[required]" name="name" value="${territoryConfiguration?.name}"
                                 style="width: 317px;"
                                 maxlength="100"/></td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        <g:message code="territoryConfiguration.unionInfo.label" default="Activate/Deactivate"/>
                    </label>
                </td>

                <td><g:checkBox name="isTerActive" id="isActive" value="true"
                                onchange="statusChange();"/></td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width80" style="width: 165px;">
                        <g:message code="territoryConfiguration.countryInfo.label" default="Country"/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td><g:select name="countryInfo.id"
                              from="${com.bits.bdfp.common.CountryInfo.list()}" optionKey="id"
                              id="countryInfo"
                              onchange="selectDivision(this.value, 0);"
                              noSelection="['': 'Select Country']"
                              style="width: 138px; height: 20px;"
                              value="${territoryConfiguration?.countryInfo?.id}"/></td>
                %{--</tr>--}%

                %{--<tr class="element_row_content_container lightColorbg pad_bot0">--}%
                <td>
                    <label class="txtright bold hight1x width80">
                        <g:message code="territoryConfiguration.division.label" default="Division"/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td><g:select name="division.id"
                              optionKey="id"
                              id="division"
                              onchange="selectDistrict(this.value, 0);"
                              noSelection="['': 'Select Division']"
                              style="width: 138px; height: 20px;"
                              value="${territoryConfiguration?.division?.id}"/></td>
                %{--</tr>--}%

                %{--<tr class="element_row_content_container lightColorbg pad_bot0">--}%
                <td>
                    <label class="txtright bold hight1x width80">
                        <g:message code="territoryConfiguration.district.label" default="District"/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td><g:select name="district.id"
                              optionKey="id"
                              id="district"
                              onchange="selectThana(this.value, 0);"
                              noSelection="['': 'Select District']"
                              style="width: 138px; height: 20px;"
                              value="${territoryConfiguration?.district?.id}"/></td>
            </tr>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width80" style="width: 165px;">
                        <g:message code="territoryConfiguration.thanaUpazilaPouroshova.label"
                                   default="Thana"/>
                    </label>
                </td>
                <td><g:select name="thanaUpazilaPouroshova.id"
                              optionKey="id"
                              id="thanaUpazilaPouroshova"
                              onchange="selectUnion(this.value, 0);"
                              noSelection="['': 'Select Thana']"
                              value="${territoryConfiguration?.thanaUpazilaPouroshova?.id}"
                              style="width: 138px; height: 20px;"/></td>
                %{--</tr>--}%

                %{--<tr class="element_row_content_container lightColorbg pad_bot0">--}%
                <td>
                    <label class="txtright bold hight1x width80">
                        <g:message code="territoryConfiguration.unionInfo.label" default="Union"/>
                    </label>
                </td>
                <td><g:select name="unionInfo.id"
                              optionKey="id"
                              id="unionInfo"
                              noSelection="['': 'Select Union']"
                              style="width: 138px; height: 20px;"
                              value="${territoryConfiguration?.unionInfo?.id}"/></td>

            </tr>

        </table>

        <table style="width: 100%;">
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width100">
                        <g:message code="territorySubArea.paraOrLocality.label" default="Geo Location"/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>

                <td>
                    <g:textField name="geoLocation" value="${territorySubArea?.geoLocation}"
                                 maxlength="100"/>
                </td>
                %{--</tr>--}%
                %{--<tr class="element_row_content_container lightColorbg pad_bot0">--}%
                <td>
                    <label class="txtright bold hight1x width120">
                        <g:message code="territorySubArea.paraOrLocality.label" default="Para Or Locality"/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textField name="paraOrLocality" value="${territorySubArea?.paraOrLocality}"/>
                </td>
                %{--</tr>--}%
                %{--<tr class="element_row_content_container lightColorbg pad_bot0">--}%
                <td>
                    <label class="txtright bold hight1x width80">
                        <g:message code="territorySubArea.road.label" default="Road"/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textField name="road" value="${territorySubArea?.road}"/>
                </td>

            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td id="labelSubCol" hidden="hidden">
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        <g:message code="territoryConfiguration.unionInfo.label" default="Activate/Deactivate"/>
                    </label>
                </td>
                <td id="fieldSubCol" hidden="hidden"><g:checkBox name="isSubActive" id="isSubActive" value=""/></td>
            </tr>
        </table>

        <div class="buttons">
            <span class="button"><input type="button" name="add-button" id="add-button"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="Add"
                                        onclick="addNewItemToCollectionGrid();"/></span>
            %{--<span class="button"><input type='button' name="delete-button" id="remove-button"--}%
            %{--class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'--}%
            %{--onclick="deleteTerritorySubAreaConfiguration();"/></span>--}%
        </div>
        <table id="territory-detail-grid"></table>

        <div id="territory-detail-grid-pager"></div>
    </div>
    <br/>

    <div class="buttons" style="padding-top: 480px;">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxTerritoryConfiguration();"/></span>
        %{--<span class="button"><input type='button' name="delete-button" id="delete-button"--}%
                                    %{--class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'--}%
                                    %{--onclick="deleteAjaxTerritoryConfiguration();"/></span>--}%
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick="resetAll();" value="Cancel"/></span>
    </div>
</form>
