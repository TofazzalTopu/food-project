<%--
  Created by IntelliJ IDEA.
  User: NZ
  Date: 6/13/2016
  Time: 10:46 AM
--%>

<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.setup.salestarget.ReportListController" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Daily Region Wise Demand</title>

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
        var regionId = $("#district").val();
        if(!regionId){
            alert("Please Select Region");
            return
        }
        SubmissionLoader.showTo();
        window.open("${resource(dir:'reportList', file:'rptDailyRegionWiseDemand')}?format=" + format + "&re="+3
            + "&date=" + date + "&regionId=" + regionId + "&regionName=" + $( "#district option:selected" ).text()
            + "&masterProductId=" + $("#masterProduct").val() + "&masterProductName=" + $( "#masterProduct option:selected" ).text());
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
//        $("#calculatedTo").datepicker("setDate", "-1d");
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
        <h1>Daily Region Wise Demand</h1>

        <h3>Daily Region Wise Demand</h3>

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
                        <label for="district" class="txtright bold hight1x width105">
                            District:
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <g:select name="district.id" class="width160" from="${com.bits.bdfp.common.District.list()}"
                                  id="district"
                                  optionKey="id" value="" noSelection="['': 'Please Select']"/>
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
                                  style="width: 160px;" id="masterProduct"
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