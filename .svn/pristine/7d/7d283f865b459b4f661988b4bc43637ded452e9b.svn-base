<%@ page import="com.bits.bdfp.common.CashPool" %>
<form name='gFormCashPool' id='gFormCashPool'>
    <g:hiddenField name="id" value="${cashPool?.id}"/>
    <g:hiddenField name="version" value="${cashPool?.version}"/>
    <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>
    <div id="remote-content-cashPool"></div>


    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='businessUnitConfiguration.code.label' default='Enterprise'/>
            <span class="mendatory_field">*</span>
        </label>
        <g:if test="${list}">
            <g:if test="${list.size() == 1}">
                <div class='element-input inputContainer width252'>
                    <g:textField name="enterPriseName" class="width252" readonly="readonly" value="${list[0].name}"/>
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
                            var data = ${result};
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
            <g:message code="cashPool.distributionPoint.label" default="Distribution Point"/>
        </label>

        <div class='element-input inputContainer '>
            <div id="distributionPointList" class="width350"></div>
        </div>
        <g:hiddenField name="distributionPoint.id" id="distributionPoint" value=""/>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="cashPool.code.label" default="Cash Pool Code"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField class="validate[required] width260" name="code" value="${cashPool?.code}"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="cashPool.name.label" default="Cash Pool Name"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField class="validate[required] width260" name="name" value="${cashPool?.name}"/>
        </div>

    </div>

    %{--<div class="element_row_content_container lightColorbg pad_bot0">--}%
        %{--<label class="txtright bold hight1x width1x">--}%
            %{--<g:message code="cashPool.name.label" default="Account No"/>--}%
            %{--<span class="mendatory_field">*</span>--}%
        %{--</label>--}%

        %{--<div class='element-input inputContainer'>--}%
            %{--<g:textField class="validate[required]" name="accountNo" value="${cashPool?.accountNo}"/>--}%
        %{--</div>--}%

    %{--</div>--}%

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="cashPool.isActive.label" default="Is Active"/>
        </label>

        <div class='element-input inputContainer'>
            <g:checkBox name="isActive" value="${cashPool?.isActive}" checked="checked"/>
        </div>
    </div>


    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxCashPool();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxCashPool();"/></span>
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick=" reset_cashPool_form('#gFormCashPool');" value="Cancel"/></span>
    </div>
</form>
