<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.inventory.sales.LoadingSlip" %>
<g:javascript src="jquery/watermark/jquery.watermark.js"/>
<style>
.formError {
    margin-top: 65px !important;
}
</style>

<form name='gFormLoadingSlipSearch' id='gFormLoadingSlipSearch'>
    <g:hiddenField name="id" value="${loadingSlip?.id}"/>
    <g:hiddenField name="version" value="${loadingSlip?.version}"/>
    <g:hiddenField name="idEnterprise" id="idEnterprise" value=""/>
    <g:hiddenField name="idBusiness" id="idBusiness" value=""/>
    <g:hiddenField name="customerId" value=""/>
    <br>

    <div class="element_row_content_container lightColorbg pad_bot0 width5x">
        <table style="width: 60%;">
            <g:if test="${enterpriseList.size() == 1}">
                <script type="text/javascript">
                    $('#idEnterprise').val(${enterpriseList[0].id});
                </script>
            </g:if>
            <g:else>
                <tr class="element_row_content_container lightColorbg pad_bot0">
                    <td>
                        <label for="userOrderPlaced" class="txtright bold hight1x width1x"
                               style="width: 160px;"><g:message
                                code="primaryDemandOrder.userOrderPlaced.label"
                                default="Enterprise"/></label>
                    </td>
                    <td>
                        <g:select class="validate[required]" name="enterpriseConfiguration.id" from="${enterpriseList}"
                                  style="width: 350px; height: 20px;" id="enterpriseConfiguration"
                                  optionKey="id" optionValue="name" value="" noSelection="['': 'Please Select']"
                                  onchange="setId(this.value);"/>

                    </td>
                </tr>
                <script type="text/javascript">
                    $(document).ready(function () {
                        $('#enterpriseConfiguration').val(null);
                    });
                    function setId(id) {
                        $('#idEnterprise').val(id);
                        jQuery("#invoice-grid").jqGrid().setGridParam(
                                {url: '${resource(dir:'loadingSlip', file:'listInvoices')}?entId=' + $('#idEnterprise').val()}).trigger("reloadGrid");
                        jQuery("#vehicle-grid").jqGrid().setGridParam(
                                {url: '${resource(dir:'deliveryTruck', file:'listTruck')}?entId=' + $('#idEnterprise').val()}).trigger("reloadGrid");
                        resetAll();
                    }
                </script>
            </g:else>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <script type="text/javascript">
                    jQuery(document).ready(function () {
                        $("#dateFrom, #dateTo").datepicker(
                                {
                                    dateFormat: 'dd-mm-yy',
                                    changeMonth: true,
                                    changeYear: true
                                });
                        $('#dateFrom').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                        $('#dateTo').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                    });
                </script>
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 80px;">
                        <g:message code="secondaryDemandOrder.product.label"
                                   default="Invoice No"/>
                    </label>

                </td>
                <td><input type="text" id="searchOrderKey" name="searchOrderKey" class="width120"/>
                    <input type="hidden" id="productId" name="productId"/>
                    <input type="hidden" id="productCode"/>
                    <input type="hidden" id="product"/>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 80px;">
                        <g:message code="secondaryDemandOrder.orderDate.label"
                                   default="Issue Date:"/>

                    </label>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 50px;">
                        <g:message code="secondaryDemandOrder.orderDate.label"
                                   default="From"/>

                    </label>
                </td>
                <td>
                    <g:textField name="dateFrom" id="dateFrom" value="" class="width120"
                                 onload="loadDataByOrder();"/>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 50px">
                        <g:message code="secondaryDemandOrder.deliveryDate.label" default="To"/>

                    </label>

                </td>
                <td>
                    <g:textField name="dateTo" id="dateTo" value="" class="width120"/>
                </td>
            </tr>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="calculateBonus" class="txtright bold hight1x width100"
                           style="width: 160px;">Calculate Bonus:
                    </label>
                </td>
                <td>
                    <g:checkBox name="calculateBonus" id="calculateBonus" value="true"
                                onclick="calculateChange();"/>
                </td>
                %{--<g:if test="${salesChannelList}">--}%
                <td>
                    <label for="salesChannel" class="txtright bold hight1x width100"
                           style="width: 160px;">Sales Channel:
                    </label>
                </td>
                <td>
                    <g:select id="salesChannel" name="salesChannel" from="${salesChannelList}"
                              class="" noSelection="['': 'All Sales Channel']"
                              style="width: 200px; height: 20px;"
                              optionKey="id" optionValue="name" value=""/>
                </td>
                %{--</g:if>--}%
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for='customerList' class='txtright bold hight1x width1x width100' style="width: 160px;">
                        Search Customer
                    </label>
                </td>
                <td>
                    <div class="element-input inputContainer width230">
                        <input type="text" id="customerList" name="customerList" class="width200"/>
                    </div>
                </td>
                <td>
                    <label for='customerList' class='txtright bold hight1x width1x  width100' style="width: 160px;">
                        Legacy ID
                    </label>
                </td>
                <td>
                    <div class="element-input inputContainer width100">
                        <input type="text" id="legacyId" name="legacyId" class="width100"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="buttons">
                        <span class="button"><input type="button" name="show-button" id="show-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Show"
                                                    onclick="loadDataByOrder();"/></span>

                    </div>
                </td>
            </tr>
        </table>

        <div class="jqgrid-container ">
            <table>
                <tr>
                    <td class="element_row_content_container" style="width: 120%;">
                        <table id="invoice-grid"></table>

                        <div id="invoice-grid-pager"></div>
                    </td>
                    <td style="width: 100%;">
                        <table id="vehicle-grid"></table>

                        <div id="vehicle-grid-pager"></div>
                    </td>
                </tr>
            </table>
        </div>
    </div>

    <div class="buttons" style="padding-top: 400px;">
        <span class="button"><input type="button" name="suggest-button" id="suggest-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Suggest Vehicle"
                                    onclick="suggestVehicle();"/></span>

        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Create Loading Slip"
                                    onclick="createLoadingSlip();"/></span>
    </div>

</form>

