<%@ page import="com.bits.bdfp.inventory.setup.PosCustomer" %>
<form name='gFormPosCustomer' id='gFormPosCustomer'>
    <g:hiddenField name="id" value="${posCustomer?.id}"/>
    <g:hiddenField name="version" value="${posCustomer?.version}"/>
    <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>
    <div id="remote-content-posCustomer"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
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
                            loadDistributionPoint("${list[0].id}");
                            loadCustomer();
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
                                    loadCustomer()
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
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer'>

            <div id="distributionPointList" style="width: 350px;" class="validate[required]"></div>

        </div>
        <g:hiddenField name="distributionPoint.id" id="distributionPoint" value=""/>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="secondaryDemandOrder.businessUnitConfiguration.label"
                       default="Customer"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer'>
            <input type="text" id="searchKey" name="searchKey" class="width350 validate[required] "/>
            <input type="hidden" id="customerMaster" name="customerMaster.id"/>

        </div>

        <div style="padding-right: 32%;padding-top: 0.6%">
            <span id="search-btn-customer-register-id" title="" role="button"
                  class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                <span class="ui-button-text"></span>
            </span>
        </div>
    </div>


    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxPosCustomer();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxPosCustomer();"/></span>
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick=" reset_PosCustomer_form('#gFormPosCustomer');" value="Cancel"/></span>
    </div>
</form>
