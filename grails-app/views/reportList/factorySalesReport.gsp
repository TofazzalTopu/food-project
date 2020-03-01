<%--
  Created by IntelliJ IDEA.
  User: NZ
  Date: 6/13/2016
  Time: 10:47 AM
--%>

<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.setup.salestarget.ReportListController" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="ttumFileExport.create.label" default="Factory Sales"/></title>

<script>

    var format = 'pdf';

    $(document).ready(function () {
        initDatePicker();
        $("#pdf").attr('checked','checked');
    });


    function productList() {

//        alert('hi');
        SubmissionLoader.showTo();
        window.open("${resource(dir:'reportList', file:'rptFactorySales')}?format=" + format + "&re="+3
        + "&date=" + $("#reportDate").val());
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
        <h1><g:message code="salesReportMT.create.label" default="Factory Sales"/></h1>

        <h3><g:message code="salesReportMT.Info.label" default="Factory Sales"/></h3>

        <div class="clear height5"></div>

        <div class="element_row_content_container lightColorbg pad_bot0">

            <table>
                <tr>
                    <td>
                        <label for="date" class="txtright bold hight1x width1x" style="width: 105px;"><g:message
                                code="primaryDemandOrder.dateProposedDelivery.label"
                                default="Date"/>
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <g:textField name="reportDate" id="reportDate" class="validate[required]"
                                     value="" style="width: 160px;text-align: center;"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="date" class="txtright bold hight1x width1x" style="width: 105px;"><g:message
                                code="primaryDemandOrder.dateProposedDelivery.label"
                                default="Report Format"/>
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
                                    onclick="productList();"/></span>

    </div>

</div>