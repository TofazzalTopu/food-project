<%@ page import="com.bits.bdfp.inventory.product.ProductPricingType" %>
<form name='gFormProductPricingType' id='gFormProductPricingType'>
    <g:hiddenField name="id" value="${productPricingType?.id}" />
    <g:hiddenField name="version" value="${productPricingType?.version}" />
    <g:hiddenField name="enterpriseConfiguration.id"  id="enterpriseConfiguration" value="" />
    <div id="remote-content-productPricingType"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='businessUnitConfiguration.code.label' default='Enterprise' />
            <span class="mendatory_field"> * </span>
        </label>
        <g:if test="${list}">
            <g:if test="${list.size()==1}">
                <div  class='element-input inputContainer'>

                    <g:textField name="enterPriseName" readonly="readonly" value="${list[0].name}" />
                    <g:hiddenField name="enterpriseConfiguration.id"  id="enterpriseConfiguration" value="${list[0].id}" />
                </div>
            </g:if>
            <g:else>
                <div  class='element-input inputContainer'>
                    <div id="enterpriseList" style="width: 350px;"></div>
                    <script type="text/javascript">

                        jQuery(document).ready(function () {
                            var data=${result}

                                    $("#enterpriseList").empty();
                            $("#enterpriseList").flexbox(data , {
                                watermark: "Select Enterprise",
                                width: 260,
                                onSelect: function() {
                                    $("#enterpriseConfiguration").val($('#enterpriseList_hidden').val());
                                }
                            });
                            $('#enterpriseList_input').addClass("validate[required]");
                            $('#enterpriseList_input').blur(function() {
                                if($('#enterpriseList_input').val() == ''){
                                    $("#enterpriseList").val("");
                                    $("#enterpriseId").val("");
                                    $("#enterpriseConfiguration").val("");
                                }
                            });
                        });
                    </script>

                    <g:hiddenField name="enterpriseConfiguration.id"  id="enterpriseConfiguration" value="" />

                </div>
            </g:else>

        </g:if>
        <g:else>
            <div  class='element-input inputContainer'>
                <g:textField name="enterPriseName" readonly="readonly" value="" />
                <script type="text/javascript">
                    jQuery(document).ready(function () {
                        MessageRenderer.showHeaderMessage("You have no assigned enterprise, please assign enterprise first.",0)
                    });
                </script>
            </div>
        </g:else>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="productPricingType.name.label" default="Name" />
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField class="validate[required]" name="name" value="${productPricingType?.name}" />
        </div>

    </div>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="productPricingType.priority.label" default="Priority" />
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField class="validate[required]" name="priority" value="${fieldValue(bean: productPricingType, field: 'priority')}" />
        </div>

    </div>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="productPricingType.note.label" default="Note" />
        </label>

        <div class='element-input inputContainer'>
            <g:textArea name="note" value="${productPricingType?.note}" />
        </div>

    </div>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="productPricingType.isActive.label" default="Is Active" />
        </label>

        <div class='element-input inputContainer'>
            <g:checkBox name="isActive" value="${productPricingType?.isActive}" />
        </div>

    </div>

    <div class="clear"></div>
    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="${message(code: 'default.button.create.label', default: 'Create')}" onclick="executeAjaxProductPricingType();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button" class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete' onclick="deleteAjaxProductPricingType();"/></span>
        <span class="button"><input type="button" name="cancel-button" id="cancel-button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick=" reset_form('#gFormProductPricingType');" value="Cancel"/></span>
    </div>
</form>
