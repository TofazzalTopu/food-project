<%@ page import="com.bits.bdfp.inventory.sales.ProcessMarketReturn" %>


<div id="spinnerProcessMarketReturn" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>

<form name='gFormProcessMarketReturn' id='gFormProcessMarketReturn'>
    <g:hiddenField name="id" value="${processMarketReturn?.id}"/>
    <g:hiddenField name="version" value="${processMarketReturn?.version}"/>
    <g:hiddenField name="mrId" id="mrId" value=""/>
    <g:hiddenField name="dpId" id="dpId" value=""/>
    <g:hiddenField name="price" id="price" value=""/>
    <div id="remote-content-processMarketReturn"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>

            <tr>
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
                    <label class="txtright bold hight1x width120">
                        <g:message code="secondaryDemandOrder.product.label"
                                   default="MR No"/>
                    </label>

                </td>
                <td><input type="text" id="searchOrderKey" name="searchOrderKey" class="width120"/>
                    <input type="hidden" id="productId" name="productId" value=""/>
                    <input type="hidden" id="productCode" value=""/>
                    <input type="hidden" id="product" value=""/>
                </td>
                <td>
                    <label class="txtright bold hight1x width60">
                        <g:message code="secondaryDemandOrder.orderDate.label"
                                   default="Date From"/>

                    </label>
                </td>
                <td>
                    <g:textField name="dateFrom" id="dateFrom" value="" class="width120"
                                 onload="loadDataByOrder();"/>
                </td>
                <td>
                    <label class="txtright bold hight1x width60">
                        <g:message code="secondaryDemandOrder.deliveryDate.label" default="Date To"/>

                    </label>

                </td>
                <td>
                    <g:textField name="dateTo" id="dateTo" value="" class="width120"/>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="buttons">
                        <span class="button"><input type="button" name="show-button" id="show-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Search"
                                                    onclick="loadDataByOrder();"/></span>

                    </div>
                </td>
            </tr>
        </table>

        <div class="jqgrid-container">
            <table id="mr-grid"></table>

            <div id="mr-grid-pager"></div>
        </div>
        <br/>
        <div class="jqgrid-container">
            <table id="mr-details-grid"></table>

            <div id="mr-details-grid-pager"></div>
        </div>
        <br/>

        %{--<table>--}%
            %{--<tr>--}%
                %{--<td>--}%
                    %{--<div class="jqgrid-container">--}%
                        %{--<table id="mr-grid"></table>--}%

                        %{--<div id="mr-grid-pager"></div>--}%
                    %{--</div>--}%
                %{--</td>--}%
                %{--<td>--}%
                    %{--<div class="jqgrid-container">--}%
                        %{--<table id="mr-details-grid"></table>--}%

                        %{--<div id="mr-details-grid-pager"></div>--}%
                    %{--</div>--}%
                %{--</td>--}%
            %{--</tr>--}%
        %{--</table>--}%

        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x width120">
                        <g:message code='processMarketReturn.qcPersonName.label'
                                   default='Qc Person Name'/>
                    </label>
                </td>
                <td>
                    <g:textField name="qcPersonName" size="50"
                                 value="${processMarketReturn?.qcPersonName}"/>
                </td>
                <td>
                    <label class="txtright bold hight1x width120">
                        <g:message code='processMarketReturn.qcPersonPin.label'
                                   default='Qc Person Pin'/>
                    </label>
                </td>
                <td>
                    <g:textField name="qcPersonPin"
                                 value="${processMarketReturn?.qcPersonPin}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="txtright bold hight1x width120">
                        <g:message code='processMarketReturn.mrProcessedBy.label'
                                   default='Mr Processed By'/>
                    </label>
                </td>
                <td>
                    <g:textField name="mrProcessedBy" size="50"
                                 value="${processMarketReturn?.mrProcessedBy}"/>
                </td>
                <td>
                    <label class="txtright bold hight1x width120">
                        <g:message code='processMarketReturn.qcPerformingTime.label'
                                   default='Qc Performing Time'/>
                    </label>
                </td>
                <td>
                    <g:textField name="qcPerformingTime"
                                 value=""/>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="txtright bold hight1x width120">
                        <g:message code='processMarketReturn.mrProcessedBy.label'
                                   default='Finish Product'/>
                    </label>
                </td>
                <td>
                    <g:textField name="finishProductName" size="50" readonly="readonly" id="finishProductName"
                                 value="${processMarketReturn?.mrProcessedBy}"/>
                    <g:hiddenField name="finishProduct.id" id="finishProduct"/>
                </td>
                <td>
                    <label class="txtright bold hight1x width120">
                        <g:message code='processMarketReturn.qcPerformingTime.label'
                                   default='Code'/>
                    </label>
                </td>
                <td>
                    <g:textField name="finishProductCode" readonly="readonly" id="finishProductCode"
                                 value="${processMarketReturn?.qcPerformingTime}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="txtright bold hight1x width120">
                        <g:message code='processMarketReturn.mrProcessedBy.label'
                                   default='MR Type'/>
                        <span>*</span>
                    </label>
                </td>
                <td>
                    <select name="mrType" id="mrType" onchange="setAvailable(this.value);" style="width: 275px;">
                        <option value="Leak Pack">Leak Pack</option>
                        <option value="Short Pack">Short Pack</option>
                        <option value="Market Return">Market Return</option>
                        <option value="Short Supply from Challan">Short Supply from Challan</option>
                        <option value="Damage">Damage</option>
                    </select>
                </td>
                <td>
                    <label class="txtright bold hight1x width120">
                        <g:message code='processMarketReturn.qcPerformingTime.label'
                                   default='Claimed quantity'/>
                    </label>
                </td>
                <td>
                    <g:textField name="availableQty" readonly="readonly" id="availableQty" class="alin_right"
                                 value="0"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="txtright bold hight1x width120">
                        <g:message code='processMarketReturn.mrProcessedBy.label'
                                   default='Accepted Quantity'/>
                        <span>*</span>
                    </label>
                </td>
                <td>
                    <g:textField name="acceptedQuantity" class="alin_right"
                                 id="acceptedQuantity" onkeyup="checkValue(this.value);"
                                 value="${processMarketReturn?.qcPerformingTime}"/>
                </td>
                <td>
                    <label class="txtright bold hight1x width120">
                        <g:message code='processMarketReturn.qcPerformingTime.label'
                                   default='Failed Quantity'/>
                    </label>
                </td>
                <td>
                    <g:textField name="failedQuantity" class="alin_right" id="failedQuantity" readonly="readonly"
                                 value="${processMarketReturn?.qcPerformingTime}"/>
                </td>
            </tr>
            <tr id="subType" hidden="hidden">
                <td>
                    <label class="txtright bold hight1x width120">
                        <g:message code='processMarketReturn.mrProcessedBy.label'
                                   default='MR Sub-Type'/>
                    </label>
                </td>
                <td>
                    <select name="mrSubType" id="mrSubType" onchange="" style="width: 275px;">
                        <option value="Damage by crate">Damage by crate</option>
                        <option value="Sealing problem">Sealing problem</option>
                        <option value="Teeth cutting">Teeth cutting</option>
                        <option value="Pinpointed">Pinpointed</option>
                        <option value="Damage by pen">Damage by pen</option>
                        <option value="Back dated">Back dated</option>
                        <option value="Poly film leak">Poly film leak</option>
                        <option value="Expire date">Expire date</option>
                        <option value="Packet Swollen">Packet Swollen</option>
                        <option value="Packet structural damage">Packet structural damage</option>
                        <option value="Product Spoiled">Product Spoiled</option>
                        <option value="Packet leak">Packet leak</option>
                        <option value="Rodent bite">Rodent bite</option>
                        <option value="Rough Handling">Rough Handling</option>
                        <option value="Structural damage">Structural damage</option>
                        <option value="Fungal spoiled">Fungal spoiled</option>
                        <option value="Can sealing/seaming problem">Can sealing/seaming problem</option>
                        <option value="Can structural damage">Can structural damage</option>
                        <option value="Can rusted">Can rusted</option>
                        <option value="Others">Others</option>
                    </select>
                </td>
                <td>
                    <label class="txtright bold hight1x width120">
                        <g:message code='processMarketReturn.qcPerformingTime.label'
                                   default='Quantity'/>
                    </label>
                </td>
                <td>
                    <g:textField name="qcPerformingTime"
                                 value="${processMarketReturn?.qcPerformingTime}"/>
                </td>
            </tr>
        </table>

        <div class="buttons">
            <span class="button"><input type="button" name="add-button" id="addProduct"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="Add"
                                        onclick="addItemToGrid();"/></span>
        </div>
    </div>

    <div class="clear"></div>

    <div class="jqgrid-container blue_grid">
        <table id="jqgrid-grid-processMarketReturn"></table>

        <div id="jqgrid-pager-processMarketReturn"></div>
    </div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="create-button" id="create-button-processMarketReturn"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxProcessMarketReturn();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button-processMarketReturn"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxProcessMarketReturn();"/></span>
        <span class="button"><input name="clearFormButtonProcessMarketReturn"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" type="button"
                                    onclick=" reset_form('#gFormProcessMarketReturn');" value="Cancel"/></span>
    </div>
</form>
