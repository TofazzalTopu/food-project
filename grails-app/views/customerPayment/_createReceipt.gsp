<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder" %>
<form name='gFormSalesReceipt' id='gFormSalesReceipt'>
    <g:hiddenField name="id" value=""/>
    <g:hiddenField name="version" value=""/>
    <g:hiddenField name="idEnterprise" id="idEnterprise" value=""/>
    <g:hiddenField name="idBusiness" id="idBusiness" value=""/>
    <br>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <g:if test="${enterpriseList.size() == 1}">
                <tr class="element_row_content_container lightColorbg pad_bot0">
                    <td>
                        <label for="enterPriseName" class="txtright bold hight1x width100">Enterprise:</label>
                    </td>
                    <td>
                        <g:textField name="enterPriseName" id="enterPriseName" readonly="readonly" class="width300"
                                     value="${enterpriseList[0].name}"/>
                        <g:hiddenField name="enterpriseConfiguration" id="enterpriseConfiguration"
                                       value="${enterpriseList[0].id}"/>
                    </td>
                </tr>
                <script type="text/javascript">
                    $('#idEnterprise').val(${enterpriseList[0].id});
                </script>
            </g:if>
            <g:else>
                <tr class="element_row_content_container lightColorbg pad_bot0">
                    <td>
                        <label for="enterpriseConfiguration" class="txtright bold hight1x width100">EnterPrise:</label>
                    </td>
                    <td>
                        <g:select name="enterpriseConfiguration.id" from="${enterpriseList}"
                                  style="width: 350px; height: 20px;" id="enterpriseConfiguration"
                                  optionKey="id" value="" noSelection="['null': 'Please Select']"
                                  onchange="setId(this.value);"/>
                    </td>
                </tr>
                <script type="text/javascript">
                    $(document).ready(function () {
                        $('#enterpriseConfiguration').val(null);
                    });
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
                    <label class="txtright bold hight1x width100">
                        MR No:
                    </label>
                </td>
                <td><input type="text" id="searchOrderKey" name="searchOrderKey" class="width120"/>
                    <input type="hidden" id="productId" name="productId"/>
                    <input type="hidden" id="productCode"/>
                    <input type="hidden" id="product"/>
                </td>
                <td>
                    <label class="txtright bold hight1x width80">
                        Issue Date:
                    </label>
                </td>
                <td>
                    <label class="txtright bold hight1x width50">
                        From:
                    </label>
                </td>
                <td>
                    <g:textField name="dateFrom" id="dateFrom" value="" class="width100"
                                 onload="loadDataByOrder();"/>
                </td>
                <td>
                    <label class="txtright bold hight1x width50">
                        To:
                    </label>
                </td>
                <td>
                    <g:textField name="dateTo" id="dateTo" value="" class="width100"/>
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
    </div>
    <div class="jqgrid-container" style="width: 1000px;">
        <table>
            <tr>
                <td>
                    <table id="invoice-grid"></table>

                    <div id="invoice-grid-pager"></div>
                </td>
            </tr>
        </table>
    </div>
    <div class="buttons" style="padding-top: 400px;">
        <span class="button"><input type="button" name="create" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Print Money Receipt"
                                    onclick="printMR();"/></span>
    </div>
</form>

