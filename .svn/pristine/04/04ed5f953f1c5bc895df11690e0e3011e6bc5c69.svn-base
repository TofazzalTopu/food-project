<%@ page import="com.bits.bdfp.settings.MeasureUnitConfiguration" %>
<form name='gFormMeasureUnitConfiguration' id='gFormMeasureUnitConfiguration'>
    <g:hiddenField name="id" value="${measureUnitConfiguration?.id}"/>
    <g:hiddenField name="version" value="${measureUnitConfiguration?.version}"/>
    <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>
    <div id="remote-content-measureUnitConfiguration"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='depositPool.enterprise.label' default='Enterprise'/>
            <span class="mendatory_field">*</span>
        </label>
        <g:if test="${list}">
            <g:if test="${list.size() == 1}">
                <div class='element-input inputContainer'>

                    <g:textField name="enterPriseName" readonly="readonly" value="${list[0].name}"/>
                    <script type="text/javascript">
                        $(document).ready(function () {
                            $("#enterpriseConfiguration").val("${list[0].id}");
                        })
                    </script>
                </div>
            </g:if>
            <g:else>
                <div class='element-input inputContainer'>
                    <div id="enterpriseList" style="width: 350px;"></div>
                    <script type="text/javascript">
                        jQuery(document).ready(function () {
                            var data = ${result}
                            $("#enterpriseList").empty();
                            $("#enterpriseList").flexbox(data, {
                                watermark: "Select Enterprise",
                                width: 260,
                                onSelect: function () {
                                    $("#enterpriseConfiguration").val($('#enterpriseList_hidden').val());
                                }
                            });
                            $('#enterpriseList_input').blur(function () {
                                if ($('#enterpriseList_hidden').val() == '') {
                                    $("#enterpriseList").val("");
                                    $("#enterpriseConfiguration").val("");
                                }
                            });
                        });
                    </script>
                </div>
            </g:else>
        </g:if>
        <g:else>
            <div class='element-input inputContainer'>
                <g:textField name="enterPriseName" readonly="readonly" value=""/>
                <script type="text/javascript">
                    jQuery(document).ready(function () {
                        MessageRenderer.showHeaderMessage("You have no assigned enterprise, please assign enterprise first.", 0)
                    });
                </script>
            </div>
        </g:else>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="measureUnitConfiguration.name.label" default="UoM Name"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField class="validate[required]" name="name" value="${measureUnitConfiguration?.name}" maxlength="50"/>

        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="measureUnitConfiguration.note.label" default="Note"/>

        </label>

        <div class='element-input inputContainer'>
            <g:textArea name="note" value="${measureUnitConfiguration?.note}" maxlength="512"/>

        </div>

    </div>

    %{--<div class="element_row_content_container lightColorbg pad_bot0">--}%
        %{--<label class="txtright bold hight1x width1x">--}%
            %{--<g:message code="measureUnitConfiguration.isFree.label" default="Is Free"/>--}%

        %{--</label>--}%

        %{--<div class='element-input inputContainer'>--}%
            %{--<g:checkBox name="isFree" value="${measureUnitConfiguration?.isFree}"/>--}%

        %{--</div>--}%

    %{--</div>--}%

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="measureUnitConfiguration.isFree.label" default="Is Master UOM"/>

        </label>

        <div class='element-input inputContainer'>
            <g:checkBox name="isMasterUom" value="${measureUnitConfiguration?.isMasterUom}"/>

        </div>

    </div>

    <div class="buttons" style="clear: left;">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxMeasureUnitConfiguration();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxMeasureUnitConfiguration();"/></span>
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick=" reset_measureUnit_form('#gFormMeasureUnitConfiguration');" value="Cancel"/></span>
    </div>
</form>
