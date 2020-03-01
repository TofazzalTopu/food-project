<%@ page import="com.docu.commons.DateUtil; com.bits.bdfp.util.ApplicationConstants; com.docu.commons.CommonConstants; com.bits.bdfp.customer.CustomerSettlement" %>


<div id="spinnerCustomerSettlement" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>

<form name='gFormCustomerSettlement' id='gFormCustomerSettlement'>
<g:hiddenField name="id" value=""/>
<g:hiddenField name="version" value="0"/>
<g:hiddenField name="customerMaster.id" id="customerMasterId" value=""/>
<g:hiddenField name="territorySubArea.id" id="territorySubAreaId" value=""/>
<g:hiddenField name="enterprise.id" id="enterpriseId" value=""/>
<g:hiddenField name="defaultCustomerId" id="defaultCustomerId" value=""/>
<div id="remote-content-customerSettlement"></div>

<div>

<div class="element_container_big">
    <div class="block-title">
        <div class='element-title'>
            Enterprise
            <span class="mendatory_field">*</span>
        </div>

        <div class='element-title'>
            Settlement Date
            <span class="mendatory_field">*</span>
        </div>

        <div class="clear"></div>
    </div>

    <div class="block-input">
        <div class='element-input inputContainer '>
            <g:if test="${result}">
                <g:if test="${list.size() == 1}">
                    <g:textField name="enterPriseName" id="enterPriseName" disabled="disabled"
                                 value="${list[0].name}"/>
                    <script type="text/javascript">
                        jQuery(document).ready(function () {
                            $('#enterpriseId').val(${list[0].id});
                            getTerritoryByEnterprise($('#enterpriseId').val());
                        });
                    </script>
                </g:if>
                <g:else>
                    <div id="enterpriselist" style="float: left"></div>
                    <script type="text/javascript">
                        jQuery(document).ready(function () {
                            $("#enterpriselist").empty();
                            $("#enterpriselist").flexbox(${result}, {
                                watermark: "Select Enterprise",
                                width: 125,
                                onSelect: function () {
                                    $("#enterpriseId").val($('#enterpriselist_hidden').val());
                                    getTerritoryByEnterprise($('#enterpriselist_hidden').val());
                                }
                            });
                            $('#enterpriselist_input').blur(function () {
                                if ($('#enterpriselist_input').val() == '') {
                                    $("#enterpriseId").val("");
                                    $(".remove-all").click();
                                }
                            });
                        });
                    </script>
                </g:else>
            </g:if>
            <g:else>
                <g:textField name="enterPriseName" readonly="readonly" value=""/>
                <script type="text/javascript">
                    jQuery(document).ready(function () {
                        MessageRenderer.showHeaderMessage("You have no assigned enterprise, please assign enterprise first.", 0)
                    });
                </script>
            </g:else>
        </div>

        <div class='element-input inputContainer'><g:textField class="validate[required] text-input datepicker"
                                                               name="settlementDate" id="settlementDate"
                                                               value="${DateUtil.getCurrentDateFormatAsString()}" /></div>
        <script type='text/javascript'>
            $(document).ready(function () {
                $('#settlementDate').datepicker({dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true,
                    defaultDate: '10-10-2015',
                    maxDate: new Date()
                });
                $('#settlementDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
            });
        </script>

        <div class="clear"></div>
    </div>
</div>

<div class="element_container_big">
    <div class="block-title">
        <div class='element-title'>
            Select Territory
            <span class="mendatory_field">*</span>
        </div>

        <div class='element-title width500'>
            Select Geo Location
            <span class="mendatory_field">*</span>
        </div>

        <div class="clear"></div>
    </div>

    <div class="block-input">
        <div class='element-input inputContainer'><g:select name="territory" from="" optionKey="id" value=""
                                                            onchange="getFlexBoxTerritorySubAreaData(this.value)"/></div>

        <div class='element-input inputContainer width500'><div id="geoLocation" style="float: left"></div></div>

        <div class="clear"></div>
    </div>
</div>

<div class="element_container_big">
    <div class="block-title">
        <div class='element-title'>
            Choose Option
            <span class="mendatory_field">*</span>
        </div>

        <div class="clear"></div>
    </div>

    <div class="block-input">
        <div class='element-input inputContainer width500'>
            %{--<g:radioGroup name="lovesGrails"--}%
            %{--labels="['Branch','Others!']"--}%
            %{--values="[1,2]">--}%
            %{--${it.label} ${it.radio}--}%
            %{--</g:radioGroup>--}%
            <input type="radio" name="customerType" id="branch" value="branch" checked="checked" onclick="handleCustomerTypeClick(this)"> Branch
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="customerType" id="others" onclick="handleCustomerTypeClick(this)"
                                                               value="others"> Others
        </div>

        <div class="clear"></div>
    </div>
</div>

<div class="element_container_big" id="dpSelection">
    <div class="block-title">
        <div class='element-title'>
            Select DP
            <span class="mendatory_field">*</span>
        </div>

        <div class='element-title width500'>
            DP Default Customer
            <span class="mendatory_field">*</span>
        </div>

        <div class="clear"></div>
    </div>

    <div class="block-input">
        <div class='element-input inputContainer'><g:select name="distributionPoint" id="distributionPoint" from="" optionKey="id" value=""
                                                            onchange="loadBranchCustomerForSettlement(this.value); getDefaultCustomerByDp(this.value)"/></div>

        <div class='element-input inputContainer width500'><g:textField class="width450" name="defaultCustomer"
                                                                        id="defaultCustomer" readonly="readonly"/></div>

        <div class="clear"></div>
    </div>
</div>

<div class="element_container_big">
    <div class="block-title">
        <div class='element-title width500'>
            Select Customer
            <span class="mendatory_field">*</span>
        </div>

        <div class="clear"></div>
    </div>

    <div class="block-input">
        <div class='element-input inputContainer width500'>
            <input type="text" id="searchCustomerKey" name="searchCustomerKey" class="width460 validate[required]"/>
            <span id="search-btn-customer-register-id" title="" role="button"
                  class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                <span class="ui-button-text"></span>
            </span>
        </div>


        <div class="clear"></div>
    </div>
</div>

<div class="element_container_big">
    <div class="block-title">
        <div class='element-title width100'>
            Customer Code
        </div>

        <div class='element-title width100'>
            Legacy ID
        </div>

        <div class='element-title width200'>
            Customer Name
        </div>

        <div class='element-title width200'>
            Customer Address
        </div>

        <div class="clear"></div>
    </div>

    <div class="block-input">
        <div class='element-input inputContainer width100'><g:textField class="width100" name="customerNumber" value="" readonly="readonly"/></div>
        <div class='element-input inputContainer width100'><g:textField class="width100" name="legacyId" value="" readonly="readonly"/></div>
        <div class='element-input inputContainer width200'><g:textField class="width200" name="customerName" value="" readonly="readonly"/></div>
        <div class='element-input inputContainer width200'><g:textField class="width280" name="customerAddress" value="" readonly="readonly"/></div>

        <div class="clear"></div>
    </div>
</div>

<div class="element_container_big">
    <div class="block-title">

        <div class='element-title width110'>
            Receivable Amount
        </div>

        <div class='element-title width110'>
            Security Deposit
        </div>
        <div class='element-title width110'>
            Due To Customer
        </div>

        <div class='element-title width200'>
            Due To Company
        </div>
        <div class="clear"></div>
    </div>

    <div class="block-input">

        <div class='element-input inputContainer setup-css-numeric-currency width110'>
            <g:textField class="width110" name="receivableAmount" value="" readonly="readonly"/></div>

        <div class='element-input inputContainer setup-css-numeric-currency width110'>
            <g:textField class="width110" name="securityDeposit" value="" readonly="readonly"/></div>
        <div class='element-input inputContainer setup-css-numeric-currency width110'>
            <g:textField class="width110" name="dueToCustomer" readonly="readonly" value=""/></div>

        <div class='element-input inputContainer setup-css-numeric-currency width300'>
            <g:textField class="width110" name="dueToCompany" readonly="readonly" value=""/>
            <span class="button"><input type="button" name="adjust-button" id="adjust-button-customerSettlement"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="Adjust with Receivable" style="display: none"
                                        onclick="executeAjaxAdjustWithReceivable();"/></span>
        </div>

        <div class="clear"></div>
    </div>
</div>

<div class="element_container_big">
    <div class="block-title">
        <div class='element-title width110'>
            Remaining Balance After Adjustment
        </div>

        <div class='element-title width110'>
            Withdraw Amount
        </div>

        <div class='element-title width110'>
            Paid Through
            <span class="mendatory_field">*</span>
        </div>

        <div class='element-title width280'>
            Reference
        </div>

        <div class="clear"></div>
    </div>

    <div class="block-input">

        <div class='element-input inputContainer setup-css-numeric-currency width110'><g:textField class="width100" name="remainingAfterAdjustAmount"
                                                                                          value="" readonly="readonly"/></div>

        <div class='element-input inputContainer setup-css-numeric-currency width110'><g:textField class="width100" name="withdrawAmount"
                                                                                          value="" maxlength="10"/></div>

        <div class='element-input inputContainer width110'><g:select class="width100" name="paidThrough"
                                                             from="${customerSettlement.constraints.paidThrough.inList}"
                                                             value=""
                                                             valueMessagePrefix="customerSettlement.paidThrough"/></div>

        <div class='element-input inputContainer width320'>
            <g:textField class="width220" name="reference" value="" maxlength="100"/>
            <span class="button"><input type="button" name="withdraw-button" id="withdraw-button-customerSettlement"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="Withdraw" style="display: none"
                                        onclick="executeAjaxWithdraw();"/></span>
        </div>

        <div class="clear"></div>
    </div>
</div>

</div>

<div class="clear"></div>
</form>
