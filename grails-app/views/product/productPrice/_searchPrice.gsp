%{--<div class="main_container">--}%
    %{--<div class="content_container">--}%
<form name='gFormSearchProductPrice' id='gFormSearchProductPrice'>
    <div id="remote-content-productPrice"></div>
    <div class="element_row_content_container lightColorbg pad_bot0">
    %{--<fieldset  class="ui-state-default ui-corner-all">--}%
        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x width110">
                        Price List Type:
                        <span class="mendatory_field">*</span>
                    </label>
                </td>

                <td>
                    <div class='element-input inputContainer'>
                        <g:select name="productPricingType" id="productPricingType"
                                  class="validate[required]"
                                  optionKey="id" from="${productPricingTypeList}"
                                  value="" onchange="loadPriceNameByPriceType(this.value)"
                                  noSelection="['': 'Select One']"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width150">
                        Select Status:
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:select name="status" id="status"
                                  optionKey="id" from=""
                                  value=""
                                  noSelection="['': 'Select Status']"/>
                    </div>
                </td>

            </tr>
            <tr>
                <td>
                    <label class="txtright bold hight1x width110">
                        Price Name List:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        %{--<g:hiddenField name="priceName" id="priceName" value=""/>--}%
                        %{--<div id ="priceNameList"></div>--}%
                        <g:select name="priceName" id="priceName"
                                  optionKey="id" from=""
                                  value=""
                                  noSelection="['': 'Select Price Name']"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width150">
                        Effective Date: From
                    </label>
                </td>
                <td colspan="2">
                    <div class='element-input inputContainer width300'>
                        <g:textField class="width80" name="searchDateEffectiveFrom" value=""/>
                        <label class="txtright bold hight1x width30">To:</label>
                        <g:textField class="width80" name="searchDateEffectiveTo" value=""/>
                    </div>
                </td>
                %{--<td class="width130">--}%
                    %{--<div class='element-input inputContainer'>--}%
                        %{--<div style="float: left">To:</div><div style="float: right"><g:textField class="width80" name="searchDateEffectiveTo" value=""/></div>--}%
                    %{--</div>--}%
                %{--</td>--}%
            </tr>
        </table>
    %{--</fieldset>--}%
        </div>
</form>
<br/>
<br/>
<div class="buttons">
    <span class="button"><input type="button" name="search-button" id="search-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Search" onclick="searchProductPrice()"/></span>
</div>
%{--<fieldset  class="ui-state-default ui-corner-all">--}%
    <div class="jqgrid-container">
        <table id="jqgrid-grid-priceList"></table>
        <div id="jqgrid-pager-priceList"></div>
    </div>
    <div class="buttons">
        <span class="button"><input type="button" name="activate-button" id="activate-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Activate" onclick="activateSelected()"/></span>
        <span class="button"><input type="button" name="inactivate-button" id="inactivate-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Inactivate" onclick="inactivateSelected()"/></span>
    </div>
%{--</fieldset>--}%
<div style="display:none;">
    <a href="#priceDetails" id="priceDetailsFancy"></a>
</div>

<div style="display:none">
    <div id="priceDetails"></div>
</div>
%{--</div>--}%
%{--</div>--}%
