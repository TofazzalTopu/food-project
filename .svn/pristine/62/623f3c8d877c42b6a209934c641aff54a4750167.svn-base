<%@ page import="com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.geolocation.TerritoryConfiguration;" %>
<form name='promotionForm' id='promotionForm'>
    <g:hiddenField name="id" value="${promotion?.id}"/>
    <g:hiddenField name="version" value="${promotion?.version}"/>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            %{--<tr><td><h4></h4></td></tr>--}%
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        Promotion Name
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td><g:textField name="promotionName" id="promotionName" value="" class="validate[required]"/></td>

                <td>
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        As of Date
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td><g:textField name="asOfDate" id="asOfDate" value="${asOfDate}" readonly="readonly"
                                 class="validate[required]"/></td>
            </tr>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        Effective From
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td><g:textField name="effectiveFrom" id="effectiveFrom" value="" class="validate[required]"/></td>

                <td>
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        Effective To
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td><g:textField name="effectiveTo" id="effectiveTo" value="" class="validate[required]"/></td>
            </tr>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        Active
                        %{--<span class="mendatory_field">*</span>--}%
                    </label>
                </td>
                <td style="width: 136px;">
                    <g:checkBox name="isActive" id="isActive" value="true" class=""/>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        Calculation Status
                        %{--<span class="mendatory_field">*</span>--}%
                    </label>
                </td>
                <td>
                    <g:radio name="calculationStatus" id="calculationStatusCurrent" value="${ApplicationConstants.BONUS_CALCULATION_STATUS_CURRENT}" class="validate[required]"/> Current
                    <g:radio name="calculationStatus" id="calculationStatusPost" value="${ApplicationConstants.BONUS_CALCULATION_STATUS_POST}" class="validate[required]"/> Post
                </td>
            </tr>
        </table>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <div class="buttons">
            <span class="button"><input type="button" name="create-button" id="create-button"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="${message(code: 'default.button.create.label', default: 'Create')}"
                                        onclick="executeAjaxPromotion();"/></span>
            <span class="button"><input type='button' name="delete-button" id="delete-button"
                                        class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                        onclick="deleteAjaxPromotionInfo();"/></span>
            <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        onclick="reset_form('#promotionForm');" value="Cancel"/></span>
        </div>
    </div>
</form>

<div class="element_row_content_container lightColorbg pad_bot0">
    <div class="jqgrid-container">
        <table id="jqgrid-grid"></table>

        <div id="jqgrid-pager"></div>
    </div>
</div>