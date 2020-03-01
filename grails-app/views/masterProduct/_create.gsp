<%@ page import="com.bits.bdfp.inventory.product.MasterProduct" %>
<form name='gFormMasterProduct' id='gFormMasterProduct'>
    <g:hiddenField name="id" value="${masterProduct?.id}"/>
    <g:hiddenField name="version" value="${masterProduct?.version}"/>
    <g:hiddenField name="enterpriseConfiguration.id"  id="enterpriseConfiguration" value="" />
    <div id="remote-content-masterProduct"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width200">
            <g:message code='businessUnitConfiguration.code.label' default='Enterprise'/>
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
                                if ($('#enterpriseList_input').val() == '') {
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
        <label class="txtright bold hight1x width200">
            <g:message code="masterProduct.code.label" default="Master Product Code"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField name="code" value="${masterProduct?.code}" class="validate[required]"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width200">
            <g:message code="masterProduct.name.label" default="Master Product Name"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField name="name" value="${masterProduct?.name}" class="validate[required]"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width200">
            <g:message code="masterProduct.note.label" default="Note"/>
        </label>

        <div class='element-input inputContainer'>
            <g:textArea name="note" value="${masterProduct?.note}"/>
        </div>
    </div>

    <div id="divActive" class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='businessUnitConfiguration.code.label' default='Active'/>
        </label>

        <div class='element-input inputContainer'>
            <g:checkBox name="isActive" id="isActive" value="${masterProduct?.isActive}"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width200">
            Master Product Sequence
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField name="sequenceNumber" value="${masterProduct?.sequenceNumber}" class="validate[required]"/>
        </div>
    </div>

    <div class="clear"></div>

    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxMasterProduct();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxMasterProduct();"/></span>
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick=" reset_masterProduct_form('#gFormMasterProduct');" value="Cancel"/></span>
    </div>
</form>
