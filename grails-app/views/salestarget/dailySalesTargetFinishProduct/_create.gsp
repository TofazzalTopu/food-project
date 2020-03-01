<%@ page import="com.bits.bdfp.setup.salestarget.DailySalesTargetFinishProduct" %>


<div id="spinnerDailySalesTargetFinishProduct" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>

<form name='gFormDailySalesTargetFinishProduct' id='gFormDailySalesTargetFinishProduct'>
    <g:hiddenField name="id" value=""/>
    <g:hiddenField name="version" value=""/>
    <div id="remote-content-dailySalesTargetFinishProduct"></div>

    <div>
        <div class="element_container_big">
            <div class="block-title">
                <div class='element-title'>
                    Select Year
                    <span class="mendatory_field">*</span>
                </div>

                <div class='element-title'>
                    Select Month
                    <span class="mendatory_field">*</span>
                </div>

                <div class='element-title'>
                    Select Employee/SM
                    <span class="mendatory_field">*</span>
                </div>

                <div class="clear"></div>
            </div>

            <div class="block-input">
                <div class='element-input inputContainer '><g:select name="yearlySalesTargetId" id="yearlySalesTargetId" from="${yearlySalesTargetByVolumeList}"
                                                                     optionKey="id" optionValue="targetYear" value="" noSelection="['':'Select Target Year']" onchange="loadSubordinate(this.value); loadMonthData(this.value)"/></div>

                <div class='element-input inputContainer setup-css-numeric-currency'><g:select name="monthId" id="monthId" noSelection="['':'Select Month']"
                                                                                               from="" optionKey="id" value="" onchange="clearData();"/></div>

                <div class='element-input inputContainer '><g:select name="employeeId" id="employeeId" from=""
                                                                     optionKey="id" value="" noSelection="['':'Select Employee/SM']" style="min-width:380px;"/></div>

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
                <div class="element-title width310">Product Name<span class="mendatory_field"> * </span></div>
                <div class="element-title width190">Target Quantity<span class="mendatory_field"> * </span></div>
                <div class="clear"></div>
            </div>

            <div class="block-input">
                <g:hiddenField name="productId" id="productId" value=""/>
                <div class="element-input inputContainer value width190">
                    <g:textField class="width180" name="productCode" id="productCode" value="" readonly="readonly" />
                </div>
                <div class="element-input inputContainer value width310">
                    <g:textField name="productName" class="width300" readonly="readonly" value="" />
                </div>
                <div class="element-input inputContainer value width190">
                    <g:textField class="width180" name="targetQuantity" id="targetQuantity" value="" readonly="readonly" />
                </div>
                <div class="clear"></div>
            </div>
        </div>
        <div class="clear"></div>
    </div>
</form>
<div class="jqgrid-container">
    <table id="jqgrid-grid-dailySalesTargetFinishProduct"></table>
</div>

<div class="clear"></div>

<div class="buttons" style="margin-left:10px;">
    <span class="button"><input type="button" name="update-button" id="update-button-dailySalesTargetFinishProduct"
                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                value="Update"
                                onclick="executeAjaxDailySalesTargetFinishProduct();"/></span>
</div>