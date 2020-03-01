<%@ page import="com.bits.bdfp.accounts.Mushak" %>


<div id="spinnerMushak" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>

<form name='gFormMushak' id='gFormMushak'>
    <g:hiddenField name="id" id="id" value="${mushak?.id}"/>
    <g:hiddenField name="version" id="version" value="${mushak?.version}"/>
    <div id="remote-content-mushak"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr id="create">
                <td>
                    <label for="date" class="txtright bold hight1x width1x"
                           style="width: 150px;">
                        <g:message code='mushak.invoice.label' default='Date'/>
                    </label>

                </td>
                <td>
                    <g:textField name="date" id="date" value="" style="width: 170px; text-align: center;"/>
                </td>
                <td>
                    <label for="distributionPoint" class="txtright bold hight1x width1x"
                           style="width: 170px;">
                        <g:message code='mushak.invoice.label' default='Distribution Point'/>
                        <span class="mendatory_field">*</span>
                    </label>

                </td>
                <td id="single">
                    <g:textField name="distributionPoint.name"
                                 id="dpName"
                                 style="width: 170px;"
                                 value="" readonly="readonly"/>
                    <g:hiddenField name="dpId" id="distributionPointId"/>
                </td>
                <td id="multi" hidden="hidden">
                    <g:select name="distributionPoint.id" id="distributionPoint"
                              from=""
                              style="width: 176px;"
                              optionKey="id"
                              value=""/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="Invoice" class="txtright bold hight1x width1x"
                           style="width: 150px;"><g:message code='mushak.invoice.label' default='Invoice'/>
                        <span class="mendatory_field">*</span>
                    </label>

                </td>
                <td>
                    <input type="text" id="searchInvoiceKey" name="searchInvoiceKey" class="width150"/>
                    <input type="hidden" id="invoice" name="invoice.id"/>
                    <span id="search-btn-customer-invoice-id" title="" role="button"
                          class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                        <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                        <span class="ui-button-text"></span>
                    </span>
                </td>
                <td>
                    <label for="enterpriseConfiguration" class="txtright bold hight1x width1x"
                           style="width: 170px;">
                        <g:message code='mushak.enterpriseConfiguration.label' default='Business Organization Name'/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:select name="enterpriseConfiguration.id"
                              id="enterpriseConfiguration"
                              from="${com.bits.bdfp.settings.EnterpriseConfiguration.list()}"
                              optionKey="id"
                              style="width: 176px;"
                              disabled="true"
                              value="${mushak?.enterpriseConfiguration?.id}"/>
                </td>

            </tr>
            <tr>
                <td>
                    <label for="Name" class="txtright bold hight1x width1x"
                           style="width: 150px;">
                        <g:message code='mushak.name.label' default='Name'/>
                        <span class="mendatory_field">*</span>
                    </label>

                </td>
                <td>
                    <g:textField name="name" class="validate[required]"
                                 id="name"
                                 style="width: 170px;"
                                 value="${mushak?.name}"/>
                </td>
                <td>
                    <label for="enterpriseTin" class="txtright bold hight1x width1x"
                           style="width: 170px;">
                        <g:message code='mushak.enterpriseTin.label' default='Business Organization Tin'/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textField name="enterpriseTin" class="validate[required]"
                                 style="width: 170px;"
                                 value="18101002833" disabled="disabled"/>
                </td>

            </tr>
            <tr>
                <td>
                    <label for="Address" class="txtright bold hight2x width1x"
                           style="width: 150px;">
                        <g:message code='mushak.address.label' default='Address'/>
                        <span class="mendatory_field">*</span>
                    </label>

                </td>
                <td>
                    <g:textArea name="address" class="validate[required]" id="address"
                                value="${mushak?.address}" rows="2" cols="27"/>
                </td>
                <td>
                    <label for="enterpriseAddress" class="txtright bold hight2x width1x"
                           style="width: 170px;">
                        <g:message code='mushak.enterpriseAddress.label' default='Business Address'/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textArea name="enterpriseAddress"
                                class="validate[required]"
                                value="Lakhkhipura, Gazipur Main Road, Joydevpur, Gazipur" rows="2" cols="27"
                                disabled="disabled"/>
                </td>

            </tr>
            <tr>
                <td>
                    <label for="customerTin" class="txtright bold hight1x width1x"
                           style="width: 150px;">
                        <g:message code='mushak.customerTin.label' default='Customer Tin'/>
                    </label>
                </td>
                <td>
                    <g:textField name="customerTin" id="customerTin"
                                 style="width: 170px;"
                                 value="${mushak?.customerTin}"/>
                </td>
                <td>
                    <label for="challanNo" class="txtright bold hight1x width1x"
                           style="width: 170px;">
                        <g:message code='mushak.challanNo.label' default='Challan No'/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textField name="challanNo" class="validate[required]" id="challanNo"
                                 style="width: 170px;"
                                 value="${mushak?.challanNo}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="deliveryDate" class="txtright bold hight1x width1x"
                           style="width: 150px;">
                        <g:message code='mushak.deliveryDate.label' default='Delivery Date'/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textField class="validate[required] text-input datepicker"
                                 name="deliveryDate" id="deliveryDate"
                                 style="width: 170px; text-align: center;"
                                 value="${mushak?.deliveryDate}"/>
                </td>
                <td>
                    <label for="challanHandoverDate" class="txtright bold hight1x width1x"
                           style="width: 170px;">
                        <g:message code='mushak.challanHandoverDate.label' default='Challan Handover Date'/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textField class="validate[required] text-input datepicker"
                                 name="challanHandoverDate"
                                 id="challanHandoverDate"
                                 style="width: 170px; text-align: center;"
                                 value="${mushak?.challanHandoverDate}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="finalDestination" class="txtright bold hight1x width1x"
                           style="width: 150px;">
                        <g:message code='mushak.finalDestination.label' default='Final Destination'/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textField name="finalDestination" id="finalDestination"
                                 class="validate[required]"
                                 style="width: 170px;"
                                 value="${mushak?.finalDestination}"/>
                </td>
                <td>
                    <label for="challanHandovertime" class="txtright bold hight1x width1x"
                           style="width: 170px;">
                        <g:message code='mushak.challanHandoverDate.label' default='Challan Handover Time'/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textField class="validate[required] text-input"
                                 name="challanHandoverTime"
                                 id="challanHandoverTime"
                                 style="width: 170px; text-align: center;"
                                 value="${mushak?.challanHandoverTime}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="vehicleInfo" class="txtright bold hight1x width1x"
                           style="width: 150px;">
                        <g:message code='mushak.vehicleInfo.label' default='Vehicle Info'/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textField name="vehicleInfo" class="validate[required]" id="vehicleInfo"
                                 value="${mushak?.vehicleInfo}"
                                 style="width: 170px;"/>
                </td>
                <td>
                    <label for="totalMushakAmount" class="txtright bold hight1x width1x"
                           style="width: 170px;">
                        <g:message code='mushak.totalMushakAmount.label' default='Total Mushak Amount'/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textField name="totalMushakAmount" id="totalMushakAmount" value="${mushak?.totalMushakAmount}"
                                 readonly="readonly"
                                 style="width: 170px;"/>
                </td>
            </tr>
        </table>

    </div>

    <div class="clear"></div>

    <div class="jqgrid-container">
        <table id="jqgrid-grid-mushak"></table>

        <div id="jqgrid-pager-mushak"></div>
    </div>

    <div class="clear"></div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="create-button" id="create-button-mushak"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxMushak();"/></span>
        <span class="button" id="deleteSpan" hidden="hidden"><input type='button' id="delete-button-mushak"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxMushak();"/></span>
        %{--<span class="button"><input name="clearFormButtonMushak"--}%
        %{--class="ui-button ui-widget ui-state-default ui-corner-all" type="button"--}%
        %{--onclick=" reset_form('#gFormMushak');" value="Cancel"/></span>--}%
    </div>
</form>
