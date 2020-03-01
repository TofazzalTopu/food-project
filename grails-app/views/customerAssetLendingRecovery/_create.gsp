<%@ page import="com.bits.bdfp.customer.CustomerCategory" %>
<form name='gFormCustomerAssetLending' id='gFormCustomerAssetLending'>


    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width160">
            <g:message code='businessUnitConfiguration.code.label' default='Enterprise'/>
            <span class="mendatory_field">*</span>
        </label>
        <g:if test="${list}">
            <g:if test="${list.size() == 1}">
                <div class='element-input inputContainer width250'>

                    <g:textField class="width250" name="enterPriseName" disabled="disabled" value="${list[0].name}"/>
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
        <label class="txtright bold hight1x width1x">
            <g:message code="customerMaster.name.label" default="Customer Name"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer'>
            <g:hiddenField name="hCustomerId"></g:hiddenField>
            <g:textField class="validate[required] width400" id='customerList' name="name"/>
        </div>

    </div>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="customerMaster.name.label" default="Net Receivable"/>
        </label>

        <div class='element-input inputContainer'>
            <g:textField  id='netReceiveAble' name="netReceiveAble" readonly="readonly"/>
        </div>

    </div>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="customerMaster.name.label" default="Asset Name"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer'>

            <g:textField  id='assetName' name="assetName"/>
        </div>

    </div>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="customerMaster.name.label" default="Lending Date"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer'>

            <g:textField id='lendingDate' name="lendingDate"/>
        </div>

    </div>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="customerMaster.name.label" default="Model number"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField  id='modelNumber' name="modelNumber"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="customerMaster.name.label" default="Cost"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField  id='assetCost' name="assetCost"/>
        </div>

    </div>
    <div class="buttons">
        <span class="button"><input type="button" name="button" id="button" value="Add"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick="addToGrid();"/></span>

    </div>
</form>