<%--
  Created by IntelliJ IDEA.
  User: mdtofazzal.hossain
  Date: 5/21/2018
  Time: 2:10 PM
--%>

<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.setup.salestarget.ReportListController" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>HO Receivable Report</title>

<script>

    var format = 'pdf';
    $(document).ready(function () {
        initDatePicker();
        $("#pdf").attr('checked', 'checked');
    });


    function generateReport() {
        var fromDate = $("#fromDate").val();
        if (!fromDate) {
            MessageRenderer.renderErrorText("Please Select From Date");
            return
        }
        var toDate = $("#toDate").val();
        if (!toDate) {
            MessageRenderer.renderErrorText("Please Select To Date");
            return
        }

        var salesChannelId = $("#salesChannel").val();
        if (!salesChannelId) {
            MessageRenderer.renderErrorText("Please Select Sales Channel");
            return
        }
        var customerCategoryId = $("#customerCategory").val();
        if (!customerCategoryId) {
            MessageRenderer.renderErrorText("Please Select Customer Category");
            return
        }

//        SubmissionLoader.showTo();
        window.open("${resource(dir:'hoReceivableReport', file:'reportHoReceivable')}?format=" + format
                + "&fromDate=" + fromDate + "&toDate=" + toDate+ "&salesChannelId=" + salesChannelId + "&customerCategoryId=" + customerCategoryId
//                + "&month=" + $.datepicker.formatDate('MM', $("#fromDate").datepicker('getDate'))
        );
        SubmissionLoader.hideFrom();
    }

    function initDatePicker() {
        $("#toDate,#fromDate").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $('#fromDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        $('#toDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
    }

    function changeFormat(val) {
        if (val == 2) {
            format = 'xlsx';
        } else {
            format = 'pdf';
        }
    }

</script>

<div class="main_container">
    <div class="content_container">
        <h1>HO Receivable Report</h1>

        <h3>HO Receivable Report</h3>

        <div class="clear height5"></div>

        <div class="element_row_content_container lightColorbg pad_bot0">

            <table>
                <tr>
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
                </tr>
                <tr>
                    <td>
                        <label for="salesChannel" class="txtright bold hight1x width125">
                            Sales Channel:
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <g:select name="salesChannel" from="${salesChannelList}"
                                  class="width160" id="salesChannel"
                                  optionKey="id" optionValue="name" value="" noSelection="['': 'All Sales Channel']"
                                  onchange=""/>
                    </td>
                    <td>
                        <label for="customerCategory" class="txtleft bold hight1x width125">
                            Customer Category:
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <g:select name="customerCategory" from="${customerCategoryList}" class="width160"
                                  id="customerCategory" optionValue="name"
                                  optionKey="id" value="" noSelection="['': 'Please Select']"
                                  onchange=""/>
                    </td>
                </tr>

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