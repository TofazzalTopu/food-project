<%@ page import="com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.inventory.warehouse.SubWarehouse; com.bits.bdfp.inventory.product.FinishProduct; com.bits.bdfp.promotion.Promotion" %>
<style>
table tr td .cbox, table tr th .cbox {
    margin-left: 0 !important;
    margin-top: 3px;
}
</style>

<form name='salesChannelSummaryForm' id='salesChannelSummaryForm'>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <table style="width: 100%;">
            <tr><div class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width125">
                        From Date:
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td><g:textField name="fromDate" id="fromDate" value="" class="validate[required] width160"/></td>
                <td>
                    <label class="txtright bold hight1x width125">
                        To Date:
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td><g:textField name="toDate" id="toDate" value="" class="validate[required] width160"/></td>
            </div>
            </tr>
            <tr>
                <td>
                    <label class="txtright bold hight1x width125">
                        All SKU:
                    </label>
                </td>
                <td>
                    <div class="txtright hight1x width20">
                        <g:checkBox name="isAll" id="isAll" value=""/>
                    </div>
                </td>
            </tr>
        </table>
        <table style="width: 100%;">
            <tr><td>
                <div class="element_row_content_container lightColorbg pad_bot0">
                    <div class="jqgrid-container floatL" style="margin-left: 5px;">
                        <table id="tb-jqgrid-eligible-territory-grid"></table>
                    </div>

                    <div class="jqgrid-container floatL" style="margin-left: 5px;">
                        <table id="tb-jqgrid-eligible-geo-grid"></table>
                    </div>

                    <div class="jqgrid-container floatL" style="margin-left: 5px;">
                        <table id="tb-jqgrid-eligible-customers-grid"></table>
                    </div>

                    <div class="jqgrid-container floatL" style="margin-left: 5px;">
                        <table id="tb-jqgrid-eligible-cc-grid"></table>
                    </div>

                    <div class="clearfix"></div>
                </div>
            </td></tr>

        </table>
        <table style="width: 100%;">
            <tr>
                <td>
                    <label for="pdf" class="txtright bold hight1x width125">
                        Report Format:
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:radio name="pm" id="pdf" value="pdf" checked="checked"
                             onclick="changeFormat(1);"/> PDF Format <br/>
                    <g:radio name="pm" id="xls" value="xls" onclick="changeFormat(2);"/> Excel Format <br/>
                </td>
            </tr>
        </table>
    </div>

    <br/>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="print-button" id="dialog-confirm-workOrder"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Generate Report"
                                    onclick="generateReport();"/></span>

    </div>
</form>
