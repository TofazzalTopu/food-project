

<%@ page import="com.bits.bdfp.inventory.setup.DeliveryTruck" %>
<form name='gFormDeliveryTruck' id='gFormDeliveryTruck'>
    <g:hiddenField name="id" value="${deliveryTruck?.id}" />
    <g:hiddenField name="version" value="${deliveryTruck?.version}" />
    <g:hiddenField name="enterpriseConfiguration.id"  id="enterpriseConfiguration" value="" />
    <div id="remote-content-deliveryTruck"></div>



    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='businessUnitConfiguration.code.label' default='Enterprise' />
            <span class="mendatory_field"> * </span>
        </label>
        <g:if test="${list}">
            <g:if test="${list.size()==1}">
                <div  class='element-input inputContainer'>
                    <g:textField name="enterPriseName" readonly="readonly" value="${list[0].name}" />
                    <script type="text/javascript">
                        $(document).ready(function () {
                            $("#enterpriseConfiguration").val("${list[0].id}");
                            loadDistributionPoint("${list[0].id}");
                        })
                    </script>
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
                                    loadDistributionPoint($('#enterpriseList_hidden').val());
                                }
                            });
                            $('#enterpriseList_input').addClass("validate[required]");
                            $('#enterpriseList_input').blur(function() {
                                if($('#enterpriseList_hidden').val() == ''){
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
            <g:message code="cashPool.distributionPoint.label" default="Distribution Point" />
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer'>
            <div id="distributionPointList" style="width: 350px;"></div>
        </div>
        <g:hiddenField name="distributionPoint.id"  id="distributionPoint" value="" />
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="deliveryTruck.name.label" default="Truck Name" />
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField class="validate[required]" name="name" value="${deliveryTruck?.name}" />
        </div>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="deliveryTruck.vehicleNumber.label" default="Vehicle Number" />
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField class="validate[required, funcCall[vehicleNumberValidation]]" name="vehicleNumber" value="${deliveryTruck?.vehicleNumber}" />

        </div>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="deliveryTruck.loadingCapacity.label" default="Loading Capacity" />
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField class="validate[required]" name="loadingCapacity" value="${fieldValue(bean: deliveryTruck, field: 'loadingCapacity')}" />
        </div>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="deliveryTruck.truckSize.label" default="Truck Size" />
        </label>

        <div class='element-input inputContainer'>
            height <g:textField class="" name="truckHeight" value="" style="width: 100px !important; margin-left: 2px !important;" />
        </div>
        <div class='element-input inputContainer'>
            width <g:textField name="truckWidth" value="" style="width: 100px !important; margin-left: 2px !important;" />
        </div>
        <div class='element-input inputContainer'>
            length <g:textField name="truckLength" value="" style="width: 100px !important; margin-left: 2px !important;" />
        </div>

    </div>



    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="${message(code: 'default.button.create.label', default: 'Create')}" onclick="executeAjaxDeliveryTruck();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button" class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete' onclick="deleteAjaxDeliveryTruck();"/></span>
        <span class="button"><input type="button" name="cancel-button" id="cancel-button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick=" reset_deliveryTruck_form('#gFormDeliveryTruck');" value="Cancel"/></span>
    </div>
</form>
