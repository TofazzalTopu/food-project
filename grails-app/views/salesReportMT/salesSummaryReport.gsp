<%--
  Created by IntelliJ IDEA.
  User: sarahafreen.orny
  Date: 5/8/2016
  Time: 2:45 PM
--%>
<%@ page import="com.bits.bdfp.setup.salestarget.SalesReportMTController;com.docu.commons.CommonConstants" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Sales Summary Report</title>

<script>
    $(document).ready(function () {
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
            return false
        }
        else {
            trim_form();
            return true;
        }
    }

    function salesSummaryReport() {
        if (!executePreCondition()) {
            return false;
        }

        var fromDate = $('#fromDate').val();
        var toDate = $('#toDate').val();
        var salesChannelId = $('#salesChannel').val();
        var categoryId = $('#customerCategory').val();
        var salesChannelName = $("#salesChannel option:selected").text();
        var categoryName = $("#customerCategory option:selected").text();

        SubmissionLoader.showTo();
        window.open("${resource(dir:'salesReportMT', file:'rptSalesSummaryReport')}?format=PDF&fromDate=" + fromDate + "&toDate=" + toDate + "&salesChannelId=" + salesChannelId + "&categoryId=" + categoryId + "&salesChannelName=" + salesChannelName + "&categoryName=" + categoryName);
        SubmissionLoader.hideFrom();
    }

</script>

<div class="main_container">
    <div class="content_container">
        <h1>Sales Summary Report</h1>
        <h3>Sales Summary Report</h3>

        <div class="clear height5"></div>

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
                <div class='element-input inputContainer'><g:textField class="validate[required] text-input datepicker"
                                                                       name="toDate" id="toDate"/>
                </div>
                <div class="clear"></div>

            </div>
        </div>

        <div class="clear height5"></div>

        <div class="element_container_big">
            <div class="block-title">

                <div class='element-title'>
                    Customer Category:
                </div>

                <div class='element-title'>
                    Sales Channel:
                </div>
                <div class="clear"></div>
            </div>

            <div class="block-input">

                <div class='element-input inputContainer'>
                    <g:select name="customerCategory" id="customerCategory" class="validate[required]" from="${list}"
                              optionKey="id"
                              optionValue="name"
                              noSelection="['': 'ALL']"/>
                </div>

                <div class='element-input inputContainer'>
                    <g:select name="salesChannel" id="salesChannel" class="validate[required]"
                              from="${salesChannel}" optionKey="id"
                              optionValue="name"
                              noSelection="['': 'ALL']"/>
                </div>

                <div class="clear"></div>

            </div>
        </div>

    </div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="print-button" id="dialog-confirm-workOrder"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Generate Report"
                                    onclick="salesSummaryReport();"/></span>
    </div>
</div>