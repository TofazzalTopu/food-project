<%@ page import="com.bits.bdfp.customer.CustomerCategory" %>
<form name='gFormCustomerAssetLending' id='gFormCustomerAssetLending'>
    <g:hiddenField name="enterpriseConfiguration" id="enterpriseConfiguration" value=""/>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x ">
            <g:message code='businessUnitConfiguration.code.label' default='Enterprise'/>
            <span class="mendatory_field">*</span>
        </label>
        <g:if test="${list}">
            <g:if test="${list.size() == 1}">
                <div class='element-input inputContainer width200'>

                    <g:textField class="width155" name="enterPriseName" disabled="disabled" value="${list[0].name}"/>
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
            <g:message code="customerMaster.name.label" default="Territory"/>

        </label>

        <div class='element-input inputContainer width200'>
            <g:select name="territoryConfiguration.id" id="territoryConfiguration" class="width160"
              from="${com.bits.bdfp.geolocation.TerritoryConfiguration.list()}" optionKey="id" value=""
                      noSelection="['null': 'Select Territory']" onchange="loadCustomerCategory(this.value)" />
        </div>

        <label class="txtright bold hight1x width1x">
            <g:message code="customerMaster.name.label" default="Customer Category"/>

        </label>

        <div class='element-input inputContainer'>
            <g:select name="customerCategory.id" id="customerCategory" class="width160"
                       optionKey="id" optionValue="name" noSelection="['null': 'Select Customer Category']" />
            %{--from="${com.bits.bdfp.customer.CustomerCategory.list()}  value=""--}%
        </div>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="customerMaster.name.label" default="Customer Name"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer width380'>
            <g:hiddenField name="customerMaster.id" id="hCustomerId" value=""/>
            <g:textField class="validate[required] width335" id='customerName' name="name"/>
            <span id="search-btn-customer-register-id" title="" role="button"
                  class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                <span class="ui-button-text"></span>
            </span>
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


    <label for="rdoAssetLending" class="width200"><input type="radio" name="asset" id="rdoAssetLending" value="assetLending" onclick="displayAssetDiv('lending')">Asset Lending</label>
    <label for="rdoAssetRecovery"><input type="radio" name="asset" id="rdoAssetRecovery" value="assetRecovery" onclick="displayAssetDiv('recovery')">Asset Recovery</label>

<div id="divAssetLending">
    <h3>Asset Lending</h3>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="customerMaster.name.label" default="Asset Name"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer width200'>
            <g:textField  id='assetName' name="assetName"/>
        </div>

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

        <div class='element-input inputContainer width200'>
            <g:textField  id='modelNumber' name="modelNumber"/>
        </div>


    <label class="txtright bold hight1x width1x">
        <g:message code="customerMaster.name.label" default="Cost"/>
        <span class="mendatory_field">*</span>
    </label>

    <div class='element-input inputContainer'>
        <g:textField  id='assetCost' name="assetCost"/>
    </div>


    </div>
    <div class="clear height5"></div>
    <div class="buttons">
        <span class="button"><input type="button" name="button" id="addLendingbutton" value="Add"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick="addToGrid();"/></span>

    </div>
    <div class="clear height5"></div>
    <div class="jqgrid-container">
        <table id="jqgrid-grid-lending"></table>
        <div id="jqgrid-pager-lending"></div>
    </div>
    <div class="clear height5"></div>
    <div class="buttons">
        <span class="button"><input type="button" name="button" id="button" value="Create"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick="saveAjax();"/></span>

    </div>
</div>

    <div class="clear height5"></div>

<div id="divAssetRecovery">

    <h3><g:message code="customerCategory.Info.label" default="Asset Recovery"/></h3>

    <div class="element_row_content_container lightColorbg pad_bot0">

    <label class="txtright bold hight1x width1x">
        <g:message code="customerMaster.name.label" default="Recovery Amount"/>
        <span class="mendatory_field">*</span>
    </label>

    <div class='element-input inputContainer width200'>
        <g:textField  id='amount' name="amount"/>
    </div>

        <label class="txtright bold hight1x width1x">
            <g:message code="customerMaster.name.label" default="Recovery Date"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField id='recoveryDate' name="recoveryDate"/>
        </div>



    </div>

    <div class="clear height5"></div>
    <div class="buttons">
        <span class="button"><input type="button" name="button" id="recoveryButton" value="Add"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick="addToGridRecovery();"/></span>
    </div>

    <div class="clear height5"></div>
    <div class="jqgrid-container">
        <table id="jqgrid-grid-recovery"></table>
        <div id="jqgrid-pager-recovery"></div>
    </div>
    <div class="clear height5"></div>
    <div class="buttons">
        <span class="button"><input type="button" name="button" id="btnSaveRecovery" value="Create"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick="saveRecoveryAjax();"/></span>

    </div>


</div>

</form>
