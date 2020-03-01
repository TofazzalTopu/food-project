<%@ page import="com.bits.bdfp.setup.salestarget.SalesHeadByVolume" %>

<div id="spinnerSalesHeadByVolume" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>

<form name='gFormSalesHeadByVolume' id='gFormSalesHeadByVolume'>
    <g:hiddenField name="id" value=""/>
    <g:hiddenField name="version" value=""/>
    <g:hiddenField name="employee.id" id="employeeId" value=""/>
    <div id="remote-content-salesHead"></div>

    <div>

        <div class="element_container_big">
            <div class="block-title">
                <div class='element-title'>
                    New Target Year
                    <span class="mendatory_field">*</span>
                </div>

                <div class="clear"></div>
            </div>

            <div class="block-input">
                <div class='element-input inputContainer'><g:textField class="validate[required,custom[integer],min[${currentYear}]] alin_right" name="targetYear" value="" maxlength="4" min="2016"/></div>

                <div class="clear"></div>
            </div>
        </div>
        <div class="element_container_big">
            <div class="block-title">
                <div class="element-title input_width420">Sales Head<span class="mendatory_field"> * </span></div>

                <div class="clear"></div>
            </div>

            <div class="block-input">
                <div class='element-input-td inputContainer width420'>
                    <input type="text" id="employeeSearchKey" name="customerSearchKey" class="width390"/>
                    <span id="search-btn-employee-id" title="" role="button"
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
                <div class="element-title width190">Employee PIN<span class="mendatory_field"> * </span></div>
                <div class="element-title input_width320">Employee Name<span class="mendatory_field"> * </span></div>
                <div class="element-title input_width200">Designation<span class="mendatory_field"> * </span></div>
                <div class="clear"></div>
            </div>

            <div class="block-input">
                <div class="element-input inputContainer value width190">
                    <g:textField class="width100 validate[required]" name="employeePin" id="employeePin" value="" readonly="readonly" />
                </div>
                <div class="element-input inputContainer value input_width320">
                    <g:textField name="employeeName" class="width300" readonly="readonly" value="" />
                </div>
                <div class="element-input inputContainer value input_width200">
                    <g:textField name="employeeDesignation" class="width200" readonly="readonly" value="" />
                </div>
                <div class="clear"></div>
            </div>
        </div>
        <div class="element_container_big">
            <div class="block-title">
                <div class="element-title input_width420">Select Product<span class="mendatory_field"> * </span></div>
                <div class="clear"></div>
            </div>

            <div class="block-input">
                <div class='element-input-td inputContainer width420'>
                    <input type="text" id="productSearchKey" name="productSearchKey" class="width390"/>
                    <span id="search-btn-product-id" title="" role="button"
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
                <div class="element-title width190">Product Code<span class="mendatory_field"> * </span></div>
                <div class="element-title input_width320">Product Name<span class="mendatory_field"> * </span></div>
                <div class="element-title input_width200">Quantity <span class="mendatory_field"> * </span></div>
                <div class="clear"></div>
            </div>

            <div class="block-input">
                <g:hiddenField name="productId" id="productId" value=""/>
                <div class="element-input inputContainer value width190">
                    <g:textField class="width180" name="productCode" id="productCode" value="" readonly="readonly" />
                </div>
                <div class="element-input inputContainer value input_width320">
                    <g:textField name="productName" class="width300" readonly="readonly" value="" />
                </div>
                <div class="element-input inputContainer value input_width200">
                    <g:textField name="quantity" class="width90" value="" />
                    <span class="button"><input type="button" name="add-button" id="add-button-salesHeadByVolume"
                                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                                value="Add"
                                                onclick="addFinishProductToGrid();"/></span>
                </div>
                <div class="clear"></div>
            </div>
        </div>
    </div>
    <div class="clear"></div>

    <div class="jqgrid-container">
        <table id="jqgrid-grid-finishProduct"></table>
    </div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="create-button" id="create-button-salesHeadByVolume"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxSalesHeadByVolume();"/></span>
        <span class="button"><input name="clearFormButtonSalesHeadByVolume"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" type="button"
                                    onclick=" reset_form('#salesHeadByVolume');" value="Cancel"/></span>
    </div>
</form>
