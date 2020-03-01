<%@ page import="com.bits.bdfp.inventory.setup.VatRate;com.bits.bdfp.inventory.product.FinishProduct" %>


<div id="spinnerVatRate" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>

<form name='gFormVatRate' id='gFormVatRate'>
    <g:hiddenField name="id" value="${vatRate?.id}"/>
    <g:hiddenField name="version" value="${vatRate?.version}"/>
    <div id="remote-content-vatRate"></div>

    <div>

        <div class="element_container_big">
            <div class="block-title">
                <div class='element-title'>
                    Finish Product
                    <span class="mendatory_field">*</span>
                </div>

                <div class="clear"></div>
            </div>

            <div class="block-input">
                <div class='element-input inputContainer width400'>
                    <g:textField name="selectProduct" class="validate[required] width300"/>
                    <div style="display: inline-block; margin-bottom: -4px;">
                        <span id="search-btn-product-id" title="" role="button"
                              class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatL">
                            <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                            <span class="ui-button-text"></span>
                        </span>
                    </div>
                    <g:hiddenField name="finishProduct.id" id="finishProduct" class="validate[required]"/>

                </div>
                <div class="clear"></div>
            </div>
        </div>


        <div class="element_container_big">
            <div class="block-title">
                <div class='element-title'>
                    Rate
                    <span class="mendatory_field">*</span>
                </div>

                <div class='element-title'>
                    Supplementary Duty %
                    %{--<span class="mendatory_field">*</span>--}%
                </div>

                <div class='element-title'>
                    Vat %
                    <span class="mendatory_field">*</span>
                </div>

                <div class="clear"></div>
            </div>

            <div class="block-input">
                <div class='element-input inputContainer setup-css-numeric-currency'>
                    <g:textField name="rate" class="rate validate[required]" maxlength="20"
                                 value="${fieldValue(bean: vatRate, field: 'rate')}"/></div>

                <div class='element-input inputContainer setup-css-numeric-currency'>
                    <g:textField
                            name="supplementaryDuty" class="supplementaryDuty" maxlength="20"
                            value="${fieldValue(bean: vatRate, field: 'supplementaryDuty')}"/></div>

                <div class='element-input inputContainer setup-css-numeric-currency'>
                    <g:textField name="vat" class="vat validate[required]" maxlength="20"
                                 value="${fieldValue(bean: vatRate, field: 'vat')}"/></div>

                <div class="clear"></div>
            </div>
        </div>

        <div class="element_container_big">
            <div class="block-title">
                <div class='element-title'>
                    Effective From Date
                    <span class="mendatory_field">*</span>
                </div>

                <div class='element-title'>
                    Effective To Date
                    <span class="mendatory_field">*</span>
                </div>

                <div class='element-title'>
                    Is Active
                </div>

                <div class="clear"></div>
            </div>

            <div class="block-input">
                <div class='element-input inputContainer'>
                    <g:textField class="validate[required] text-input datepicker"
                                 name="effectiveFromDate" id="effectiveFromDate"
                                 value="${vatRate?.effectiveFromDate}"/></div>
                <script type='text/javascript'>
                    $(document).ready(function () {
                        $('#effectiveFromDate').datepicker({
                            dateFormat: 'dd-mm-yy',
                            changeMonth: true,
                            changeYear: true
                        });
                        $('#effectiveFromDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

                        setNumericFieldByClass('rate')
                        setNumericFieldByClass('supplementaryDuty');
                        setNumericFieldByClass('vat');
                    });
                </script>

                <div class='element-input inputContainer'>
                    <g:textField class="validate[required] text-input datepicker"
                                 name="effectiveToDate" id="effectiveToDate"
                                 value="${vatRate?.effectiveToDate}"/></div>
                <script type='text/javascript'>
                    $(document).ready(function () {
                        $('#effectiveToDate').datepicker({
                            dateFormat: 'dd-mm-yy',
                            changeMonth: true,
                            changeYear: true
                        });
                        $('#effectiveToDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                    });
                </script>

                <div class='element-input inputContainer '>
                    <g:checkBox name="isActive"
                                value="${vatRate?.isActive}"/></div>

                <div class="clear"></div>
            </div>
        </div>

    </div>

    <div class="clear"></div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="create-button" id="create-button-vatRate"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxVatRate();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button-vatRate"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxVatRate();"/></span>
        <span class="button"><input name="clearFormButtonVatRate"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" type="button"
                                    onclick=" reset_form('#gFormVatRate');" value="Cancel"/></span>
    </div>
</form>
