<%@ page import="com.bits.bdfp.inventory.warehouse.SubWarehouse" %>
<form name='gFormSubWarehouse' id='gFormSubWarehouse'>

    <g:hiddenField name="id" value="${subWarehouse?.id}"/>
    <g:hiddenField name="version" value="${subWarehouse?.version}"/>
    <g:hiddenField name="enterpriseConfiguration.id"  id="enterpriseConfiguration" value="" />
    <div id="remote-content-subWarehouse"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='businessUnitConfiguration.code.label' default='Enterprise'/>
            <span class="mendatory_field">*</span>
        </label>
        <g:if test="${list}">
            <g:if test="${list.size() == 1}">
                <div class='element-input2 inputContainer'>
                    <g:textField name="enterPriseName" disabled="disabled" value="${list[0].name}" style="width: 260px"/>
                    <script type="text/javascript">
                        $(document).ready(function () {
                            $("#enterpriseConfiguration").val("${list[0].id}");
                            loadWarehouse("${list[0].id}");
                        })
                    </script>
                </div>
            </g:if>
            <g:else>
                <div class='element-input inputContainer'>
                    <div id="enterpriseSubWareList" style="width: 350px;"></div>
                    <script type="text/javascript">

                        jQuery(document).ready(function () {
                            var data = ${result}
                            $("#enterpriseSubWareList").empty();
                            $("#enterpriseSubWareList").flexbox(data, {
                                watermark: "Select Enterprise",
                                width: 260,
                                onSelect: function () {
                                    $("#enterpriseConfiguration").val($('#enterpriseSubWareList_hidden').val());
                                    loadWarehouse($('#enterpriseSubWareList_hidden').val());
                                }
                            });
                            $('#enterpriseSubWareList_input').blur(function () {
                                if ($('#enterpriseSubWareList_input').val() == '') {
                                    $("#enterpriseSubWareList").val("");
                                    $("#enterpriseConfiguration").val("");
                                }
                            });
                        });
                    </script>
                </div>
            </g:else>

        </g:if>
        <g:else>
            <div class='element-input2 inputContainer' >
                <g:textField name="enterPriseName" readonly="readonly" value="" style="width: 264px"/>
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
            <g:message code="subWarehouse.warehouse.label" default="Inventory"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer'>
            <div id="warehouseList" style="width: 350px;"></div>

        </div>
        <g:hiddenField name="warehouse.id" id="warehouse" value=""/>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="subWarehouse.subWarehouseType.label" default="Sub Inventory Type"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input2 inputContainer' style="width: 267px;" >
            <g:select class="validate[required]" name="subWarehouseType.id" id="subWarehouseType" from="${com.bits.bdfp.inventory.warehouse.SubWarehouseType.list()}"
                      optionKey="id" optionValue="name" value="${subWarehouse?.subWarehouseType?.id}" style="width: 267px;"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="subWarehouse.name.label" default="Sub Inventory Name"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input2 inputContainer' style="width: 267px";>
            <g:textField class="validate[required]" name="name" value="${subWarehouse?.name}" maxlength="50" style="width: 264px;"/>
        </div>

    </div>

    <div class="clear"></div>


    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxSubWarehouse();"/></span>
        %{--<span class="button"><input type='button' name="delete-button" id="delete-button"--}%
                                    %{--class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'--}%
                                    %{--onclick="deleteAjaxSubWarehouse();"/></span>--}%
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick=" reset_SubWarehouse_form('#gFormSubWarehouse');" value="Cancel"/></span>
    </div>
</form>
