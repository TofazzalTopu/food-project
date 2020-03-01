<%@ page import="com.bits.bdfp.geolocation.TerritoryConfiguration" %>
<form name='promotionForm' id='promotionForm'>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            %{--<tr><td><h4></h4></td></tr>--}%
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        Effective From
                        %{--<span class="mendatory_field">*</span>--}%
                    </label>
                </td>
                <td><g:textField name="effectiveFrom" id="effectiveFrom" value="" class=""/></td>

                <td>
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        Effective To
                        %{--<span class="mendatory_field">*</span>--}%
                    </label>
                </td>
                <td><g:textField name="effectiveTo" id="effectiveTo" value="" class=""/></td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        Select Promotion
                        %{--<span class="mendatory_field">*</span>--}%
                    </label>
                </td>
                <td>
                    <g:select name="promotion" id="promotion" from="${promotionList}" style="width: 150px;"
                              optionKey="id" optionValue="name"
                              noSelection="['':'Select promotion...']"
                              value="" class=""/>
                </td>

                %{--<td>
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        As of Date
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td><g:textField name="asOfDate" id="asOfDate" value="" readonly="readonly" class="validate[required]"/></td>--}%
            </tr>
        </table>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <div class="buttons">
            <span class="button"><input type="button" name="search-button" id="search-button"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="Search"
                                        onclick="executeAjaxSearch();"/></span>
        </div>
    </div>
</form>

<div class="element_row_content_container lightColorbg pad_bot0">
    <div class="jqgrid-container">
        <table id="jqgrid-grid"></table>

        <div id="jqgrid-pager"></div>
    </div>
</div>

<div class="element_row_content_container lightColorbg pad_bot0">
    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Calculate & Adjust Bonus"
                                    onclick="executeAjaxAdjustBonusPromotion();"/></span>
        %{--<span class="button"><input type='button' name="delete-button" id="delete-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Calculate & Adjust Bonus'
                                    onclick="deleteAjaxPromotionInfo();"/></span>--}%
    </div>
</div>