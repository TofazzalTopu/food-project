

<%@ page import="com.bits.bdfp.common.DepositPool" %>
<form name='gFormDepositPool' id='gFormDepositPool'>
  <g:hiddenField name="id" value="${depositPool?.id}" />
  <g:hiddenField name="version" value="${depositPool?.version}" />
  <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>
  <g:hiddenField name="distributionPoint.id"  id="distributionPoint" value="" />
  <div id="remote-content-depositPool"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='depositPool.enterprise.label' default='Enterprise' />
            <span class="mendatory_field"> * </span>
        </label>
        <g:if test="${list}">
            <g:if test="${list.size() == 1}">
                <div class='element-input inputContainer width252'>
                    <g:textField name="enterPriseName" class="width252" readonly="true" value="${list[0].name}"/>
                    <script type="text/javascript">
                        $(document).ready(function(){
                            $("#enterpriseConfiguration").val("${list[0].id}");
                            loadDistributionPoint(${list[0].id});
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
                                    loadDistributionPoint($('#enterpriseList_hidden').val());
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
            <g:message code="depositPool.distributionPoint.label" default="Distribution Point" />
        </label>

        <div class='element-input inputContainer'>
            <div id="distributionPointList" style="width: 350px;"></div>
        </div>
    </div>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="depositPool.code.label" default="Deposit Pool Code" />
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer width260'>
            <g:textField class="validate[required] width260" name="code" value="${depositPool?.code}" />

        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="depositPool.name.label" default="Deposit Pool Name" />
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer width260'>
            <g:textField class="validate[required] width260" name="name" value="${depositPool?.name}" />
        </div>

    </div>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="depositPool.name.label" default="Account No" />
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer width260'>
            <g:textField class="validate[required] width260" name="accountNo" value="${depositPool?.accountNo}" />
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="depositPool.isActive.label" default="Is Active" />
        </label>

        <div class='element-input inputContainer'>
            <g:checkBox name="isActive" value="${depositPool?.isActive}" checked="checked" />
        </div>

    </div>


  <div class="buttons">
    <span class="button"><input type="button" name="create-button" id="create-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="${message(code: 'default.button.create.label', default: 'Create')}" onclick="executeAjaxDepositPool();"/></span>
    <span class="button"><input type='button' name="delete-button" id="delete-button" class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete' onclick="deleteAjaxDepositPool();"/></span>
    <span class="button"><input type="button" name="cancel-button" id="cancel-button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick=" reset_depositPool_form('#gFormDepositPool');" value="Cancel"/></span>
  </div>
</form>
