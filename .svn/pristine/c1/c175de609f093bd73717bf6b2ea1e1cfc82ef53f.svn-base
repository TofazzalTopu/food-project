<%@ page import="com.bits.bdfp.inventory.warehouse.Warehouse" %>
<form name='gFormWarehouse' id='gFormWarehouse'>
    <g:hiddenField name="id" value="${warehouse?.id}"/>
    <g:hiddenField name="version" value="${warehouse?.version}"/>
    <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>
    <div id="remote-content-warehouse"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='businessUnitConfiguration.code.label' default='Enterprise'/>
            <span class="mendatory_field">*</span>
        </label>
        <g:if test="${list}">
            <g:if test="${list.size() == 1}">
                <div class='element-input inputContainer width240'>
                    <g:textField name="enterPriseName" class="width200" disabled="disabled" value="${list[0].name}"/>
                    <script type="text/javascript">
                        $(document).ready(function () {
                            $("#enterpriseConfiguration").val("${list[0].id}");
                            loadBusinessUnit("${list[0].id}");
                        })
                    </script>
                </div>
            </g:if>
            <g:else>
                <div class='element-input inputContainer width240'>
                    <div id="enterpriseWarList" class="width200"></div>
                    <script type="text/javascript">
                        jQuery(document).ready(function () {
                            var data = ${result}
                            $("#enterpriseWarList").empty();
                            $("#enterpriseWarList").flexbox(data, {
                                watermark: "Select Enterprise",
                                width: 200,
                                onSelect: function () {
                                    $("#enterpriseConfiguration").val($('#enterpriseWarList_hidden').val());
//                                    loadBusinessUnit($('#enterpriseWarList_hidden').val());
                                }
                            });
                            $('#enterpriseWarList_input').blur(function () {
                                if ($('#enterpriseWarList_input').val() == '') {
                                    $("#enterpriseWarList").val("");
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



        <label class="txtright bold hight1x width1x">
            <g:message code="warehouse.businessUnitConfiguration.label" default="Business Unit "/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer width240' >
            <div id="businessUnitList" class="width240 validate[required]"></div>
        </div>
        <g:hiddenField name="businessUnitConfiguration.id" id="businessUnitConfiguration" value=""/>
    </div>



    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="warehouse.name.label" default="Inventory Name"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer width240'>
            <g:textField class="validate[required] width200" name="name" value="${warehouse?.name}" maxlength="50"/>
        </div>


        <label class="txtright bold hight1x width1x">
            <g:message code="warehouse.code.label" default="Inventory legacy ID"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer width240'>
            <g:textField class="validate[required] width200" name="code" value="${warehouse?.code}" maxlength="30"/>
        </div>

    </div>


    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="warehouse.address.label" default="Address"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer width240'>
            %{--<g:textField class="validate[required] width200" name="address" value="${warehouse?.address}" maxlength="512"/>--}%
            <g:textArea class="validate[required] width200" name="address" value="${warehouse?.address}" maxlength="512"/>

        </div>


        <label class="txtright bold hight1x width1x">
            <g:message code="warehouse.note.label" default="Note"/>
        </label>

        <div class='element-input inputContainer width240'>
            <g:textArea name="note" value="${warehouse?.note}" class="width200"/>
        </div>

    </div>



    <div class="clear"></div>

    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxWarehouse();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxWarehouse();"/></span>
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick=" reset_Warehouse_form('#gFormWarehouse');" value="Cancel"/></span>
    </div>
</form>
