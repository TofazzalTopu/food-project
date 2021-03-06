<%@ page import="com.bits.bdfp.customer.CustomerSalesChannel; com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder" %>
<form name='gFormPrimaryDemandOrder' id='gFormPrimaryDemandOrder'>
    <g:hiddenField name="id" value=""/>
    <g:hiddenField name="version" value=""/>

    <div id="remote-content-territoryConfiguration"></div>
    <br>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>

            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width100">
                        Customer Name
                    </label>

                </td>
                <td><g:textField class="validate[required] width290" name="defaultCustomer" id="defaultCustomer" maxlength="200"/></td>
                <input type="hidden" name="defaultCustomer.id" id="defaultCustomerId" value="">

                <td>
                    <label class="txtright bold hight1x width80">
                        Customer ID
                    </label>

                </td>
                <td><g:textField class="validate[required] width130" name="customerId" id="customerId" maxlength="20"/></td>

                <td>
                    <label class="txtright bold hight1x width120">
                        Enter Order Number
                    </label>

                </td>
                <td><g:textField class="validate[required] width130" name="orderNumber" id="orderNumber" maxlength="20"/></td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width105" >
                        Select Order Date
                    </label>
                </td>

                <td>
                    <label class="txtright bold hight1x width30" >
                        From
                    </label>
                </td>
                %{--<td>--}%
                    %{--<div class='element-input inputContainer'><g:textField name="orderDateFrom" id="orderDateFrom"--}%
                                                                           %{--class="validate[required]"--}%
                                                                           %{--style="width: 40px"--}%
                                                                           %{--value="${com.bits.bdfp.util.ApplicationConstants.DATE_FORMAT_CURRENT}"--}%
                                                                           %{--placeholder="${com.bits.bdfp.util.ApplicationConstants.DATE_FORMAT_CURRENT}"/></div>--}%
                %{--</td>--}%
                <td>
                <script type='text/javascript'>$(document).ready(function(){$('#orderDateFrom').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}',{});});</script>
                <div >
                    <g:textField class="width80" name="orderDateFrom" id="orderDateFrom" value="" /></div>

                </td>

                <td>
                    <label class="txtright bold hight1x width20">
                        To
                    </label>
                </td>
                <td>
                    <script type='text/javascript'>$(document).ready(function(){
                        setDateRangeNoLimit('orderDateFrom','orderDateTo');
                        setDateRangeNoLimit('deliveryDateFrom','deliveryDateTo');
                        $('#orderDateTo').mask('${CommonConstants.DATE_MASK_FORMAT}',{});
                    });</script>
                    <div ><g:textField name="orderDateTo" id="orderDateTo"
                                                                           class="validate[required]"
                                                                           style="width: 80px"
                                                                           value="${fieldValue(bean: primaryDemandOrder, field: 'dateCreated')}" /></div>
                </td>

                <td>
                    <label class="txtright bold hight1x width120">
                        Select Delivery Date
                    </label>
                </td>
                <td>
                    <label class="txtright bold hight1x width30">
                        From
                    </label>
                </td>
                <td>
                    <script type='text/javascript'>$(document).ready(function(){$('#deliveryDateFrom').mask('${CommonConstants.DATE_MASK_FORMAT}',{});});</script>
                    <div ><g:textField name="deliveryDateFrom" id="deliveryDateFrom"
                                                                           class="validate[required] width80"
                                                                           value=""/></div>
                </td>
                <td>
                    <label class="txtright bold hight1x width20">
                        To
                    </label>
                </td>
                <td>
                    <script type='text/javascript'>$(document).ready(function(){$('#deliveryDateTo').mask('${CommonConstants.DATE_MASK_FORMAT}',{});});</script>
                    <div ><g:textField name="deliveryDateTo" id="deliveryDateTo"
                                                                           class="validate[required] width80"
                                                                           value=""/></div>
                </td>

            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width120">
                        Customer Legacy ID
                    </label>

                </td>

                <td><g:textField class="validate[required] width130" name="legacyId" id="legacyId" maxlength="20" /></td>
                <td>
                    <label class="txtright bold hight1x width140">
                        Customer Sales Channel
                    </label>

                </td>

                <td>
                    <g:select name="customerSalesChannel.id" id="customerSalesChannel"
                              from="${CustomerSalesChannel.list()}" optionKey="id"
                              value="" class="width300"
                              noSelection="['': 'Select Sales Channel']"/>
                </td>


                <td>
                    <div class="buttons">
                        <span class="button"><input type="button" name="add-button" id="search-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Search"
                                                    onclick="getPrimaryDemandOrderDetails();"/></span>
                    </div>
                </td>
            </tr>

        </table>


        <div class="jqgrid-container">
            <table id="jqgrid-grid"></table>

            <div id="jqgrid-pager"></div>
        </div>
    </div>
    <table>
        <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
            <td style="column-span: 2"></td>
            <td>
                <b>Remarks/Note  :</b>
            </td>
            <td><g:textArea class="width320" name="remarks" id="remarks"/></td>
            <td style="column-span: 2"></td>
        </tr>
    </table>
    <div class="buttons">
        <span class="button"><input type="button" name="approve-button" id="approve-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Approve"
                                    onclick="approvePrimaryDemandOrder();"/></span>
        <span class="button"><input type='button' name="reject-button" id="reject-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Reject'
                                    onclick="rejectPrimaryDemandOrder();"/></span>
    </div>
</form>
<g:render template='/approvePrimaryDemandOrder/productQtyRateDetails'/>

