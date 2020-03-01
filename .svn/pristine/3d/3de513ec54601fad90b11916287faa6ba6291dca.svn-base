<%--
  Created by IntelliJ IDEA.
  User: sarahafreen.orny
  Date: 4/27/2016
  Time: 12:57 PM
--%>
<%@ page import="com.bits.bdfp.customer.CustomerSalesChannel; com.bits.bdfp.setup.salestarget.SalesReportMTController;com.docu.commons.CommonConstants" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Head of Sales Report</title>

<script>
    $(document).ready(function () {
        $('#territory').focus();

        setDateRange('fromDate','toDate');
        $('#fromDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        $('#toDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});

    });
    function executePreCondition() {

        var fromDate = $('#fromDate').val();
        var toDate = $('#toDate').val();

        if (fromDate == "" || toDate == "") {
            var message = {
                'messageTitle': "Supplier Report",
                'type': 2,
                'messageBody': "Date can't be blank. Please, Select Date."
            };
            MessageRenderer.render(message);
        }
//       else if (!isPastDate(toDate, fromDate)) {
//            var message = {
//                'messageTitle': "Supplier Report",
//                'type': 2,
//                'messageBody': "From Date can't be greater than To Date"
//            }
//            MessageRenderer.render(message);
//        }
        else {
            trim_form();
            return true;
        }

    }

    function salesReportHEADgenerate() {
//        alert("hello");
        if (!executePreCondition()) {
            return false;
        }

        var fromDate = $('#fromDate').val();
        var toDate = $('#toDate').val();
        var salesChannelId = $('#customerSalesChannel').val();
        var salesChannelName = $("#customerSalesChannel option:selected").text();

        SubmissionLoader.showTo();
        window.open("${resource(dir:'salesReportMT', file:'rptSalesReportHEAD')}?format=PDF&fromDate=" + fromDate + "&toDate=" + toDate
            + "&salesChannelId=" + salesChannelId + "&salesChannelName=" + salesChannelName);
        SubmissionLoader.hideFrom();
    }
</script>

<div class="main_container">
    <div class="content_container">
        <h1>Head of Sales Report</h1>

        <h3>Head of Sales Report</h3>


        <div class="clear height5"></div>
        <div class="element_container_big">
            <div class="block-title">

                <div class='element-title'>
                    Customer Sales Channel:
                </div>

                <div class="clear"></div>
            </div>

            <div class="block-input">

                <div class='element-input inputContainer'>
                    <g:select name="customerSalesChannel.id" id="customerSalesChannel"
                              from="${CustomerSalesChannel.list()}" optionKey="id"
                              value="" class="width300"
                              noSelection="['': 'All']"/>
                </div>

                <div class="clear"></div>

            </div>
        </div>

        <div class="element_container_big">
            <div class="block-title">

                <div class='element-title'>
                    From Date:
                    <span class="mendatory_field">*</span>
                </div>

                <div class='element-title'>
                    To Date:
                    <span class="mendatory_field">*</span>
                </div>


                <div class="clear"></div>
            </div>

            <div class="block-input">

                <div class='element-input inputContainer'><g:textField class="validate[required] text-input datepicker"
                                                                       name="fromDate" id="fromDate"/>
                </div>
                <script type='text/javascript'>
                    $(document).ready(function () {
                        $('#fromDate').datepicker({
                            dateFormat: 'dd-mm-yy',
                            changeMonth: true,
                            changeYear: true
                        });
                        $('#fromDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                    });
                </script>

                <div class='element-input inputContainer'><g:textField class="validate[required] text-input datepicker"
                                                                       name="toDate" id="toDate"/>
                </div>
                <script type='text/javascript'>
                    $(document).ready(function () {
                        $('#toDate').datepicker({
                            dateFormat: 'dd-mm-yy',
                            changeMonth: true,
                            changeYear: true
                        });
                        $('#toDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                    });
                </script>


                <div class="clear"></div>

            </div>
        </div>

        <div class="clear height5"></div>
        <div id="dialog-confirm-salesMT" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span> Report will be printed. Want to continue?</p>
        </div>

    </div>
    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="print-button" id="dialog-confirm-workOrder"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Generate Report"
                                    onclick="salesReportHEADgenerate();"/></span>

    </div>

</div>