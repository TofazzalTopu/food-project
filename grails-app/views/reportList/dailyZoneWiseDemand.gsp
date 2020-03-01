<%--
  Created by IntelliJ IDEA.
  User: NZ
  Date: 6/13/2016
  Time: 10:46 AM
--%>

<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.setup.salestarget.ReportListController" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Daily Zone Wise Demand</title>

<script>

    var format = 'pdf';

    $(document).ready(function () {
        initDatePicker();
        $("#pdf").attr('checked','checked');
    });


    function generateReport() {
        var date = $("#reportDate").val();
        if(!date){
            alert("Please Select Date");
            return
        }
        var zoneId = $("#thanaUpazilaPouroshova").val();
        if(!zoneId){
            alert("Please Select Zone");
            return
        }
        SubmissionLoader.showTo();
        window.open("${resource(dir:'reportList', file:'rptDailyZoneWiseDemand')}?format=" + format
            + "&date=" + date + "&zoneId=" + zoneId + "&masterProductId="
            + $("#masterProduct").val() + "&zoneName=" + $( "#thanaUpazilaPouroshova option:selected" ).text()
            + "&masterProductName=" + $( "#masterProduct option:selected" ).text());
        SubmissionLoader.hideFrom();
    }

    function initDatePicker() {
        $('#reportDate').datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true,
                    maxDate: -1
                });
        $('#reportDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
    }

    function changeFormat(val){
        if(val == 2){
            format = 'xlsx';
        }else{
            format = 'pdf';
        }
    }

</script>

<div class="main_container">
    <div class="content_container">
        <h1>Daily Zone Wise Demand</h1>

        <h3>Daily Zone Wise Demand</h3>

        <div class="clear height5"></div>

        <div class="element_row_content_container lightColorbg pad_bot0">

            <table>
                <tr>
                    <td>
                        <label for="reportDate" class="txtright bold hight1x width105">
                            Date:
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <g:textField name="reportDate" id="reportDate" class="validate[required] width160"
                                     value=""/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="thanaUpazilaPouroshova" class="txtright bold hight1x width105">
                            Zone:
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <g:select name="thanaUpazilaPouroshova.id" from="${com.bits.bdfp.common.ThanaUpazilaPouroshova.list()}"
                                  id="thanaUpazilaPouroshova" class="width160"
                                  optionKey="id" value="" noSelection="['': 'Please Select']"
                                  onchange=""/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="masterProduct" class="txtright bold hight1x width105">
                            Master Product:
                        </label>
                    </td>
                    <td>
                        <g:select name="masterProduct.id" from="${com.bits.bdfp.inventory.product.MasterProduct.list()}"
                                  id="masterProduct" class="width160"
                                  optionKey="id" value="" noSelection="['': 'All Master Product']"
                                  onchange=""/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="pdf" class="txtright bold hight1x width105">
                            Report Format:
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <g:radio name="pm" id="pdf" value="pdf" checked="checked" onclick="changeFormat(1);"/> PDF Format <br/>
                        <g:radio name="pm" id="xls" value="xls" onclick="changeFormat(2);"/> Excel Format <br/>
                    </td>
                </tr>
            </table>
        </div>

        <div class="clear height5"></div>

        <div id="dialog-confirm-salessummary" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span> Report will be printed. Want to continue?</p>
        </div>

    </div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="print-button" id="dialog-confirm-workOrder"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Generate Report"
                                    onclick="generateReport();"/></span>

    </div>

</div>