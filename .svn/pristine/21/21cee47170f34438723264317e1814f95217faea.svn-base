<%--
  Created by IntelliJ IDEA.
  User: mdalinaser.khan
  Date: 6/29/16
  Time: 10:53 AM
--%>

<%@ page import="com.bits.bdfp.util.ApplicationConstants; com.docu.security.ApplicationUser; com.docu.commons.CommonConstants; com.docu.commons.DateUtil; com.bits.bdfp.reports.StockReportController" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Stock Report</title>

<script type="text/javascript">
    $(document).ready(function () {
        $('#stockDate').datepicker({dateFormat: '${ApplicationConstants.JQUERY_UI_DATE_FORMAT}',
            changeMonth: true,
            changeYear: true,
            defaultDate: new Date(),
            maxDate: new Date()
        });
        $('#stockDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
    });
    function generateStockReport() {
        var stockDate = $("#stockDate").val();
        var batchNo = $("#batchNo").val();
        window.open("${request.contextPath}/stockReport/rptStockReport?"
                + 'batchNo=' + batchNo + "&stockDate=" + stockDate
        );
    }
</script>

<div class="main_container">
    <div class="content_container">
        <h1>Sales Report</h1>

        <h3>Sales Report</h3>

        <div class="element_container_big">
            <div class="block-title">
                <div class='element-title'>
                    Date
                </div>

                <div class='element-title'>
                    Batch No
                </div>

                <div class="clear"></div>
            </div>

            <div class="block-input">
                <div class='element-input inputContainer'><g:textField class="text-input datepicker"
                                                                       name="stockDate" id="stockDate"
                                                                       value="${DateUtil.getCurrentDateFormatAsString()}" /></div>
                <script type='text/javascript'>
                    $(document).ready(function () {
                        $('#stockDate').datepicker({dateFormat: 'dd-mm-yy',
                            changeMonth: true,
                            changeYear: true,
                            defaultDate: new Date(),
                            maxDate: new Date()
                        });
                        $('#stockDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
                    });
                </script>
                <div class='element-input inputContainer '>
                        <g:textField name="batchNo" id="batchNo" value="" maxlength="20"/>
                </div>

                <div class="clear"></div>
            </div>
        </div>


    </div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="print-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Generate Report"
                                    onclick="generateStockReport();"/></span>

    </div>

</div>