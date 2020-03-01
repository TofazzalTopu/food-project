<%--
  Created by IntelliJ IDEA.
  User: sarahafreen.orny
  Date: 4/20/2016
  Time: 10:13 AM
--%>

<%@ page import="com.bits.bdfp.setup.salestarget.SalesReportMTController;com.docu.commons.CommonConstants" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Sales Report MT</title>

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
         else {
             trim_form();
             return true;
         }

    }
    function salesReportMTgenerate() {
        if (!executePreCondition()) {
            return false;
        }
        var fromDate = $('#fromDate').val();
        var toDate = $('#toDate').val();
        var territoryId = $('#territory').val();
        var territoryName = $("#territory option:selected").text();

        SubmissionLoader.showTo();
        window.open("${resource(dir:'salesReportMT', file:'rptSalesReportMT')}?format=PDF&fromDate=" + fromDate + "&toDate=" + toDate + "&territoryId=" + territoryId + "&territoryName=" + territoryName);
        SubmissionLoader.hideFrom();
    }

</script>

<div class="main_container">
    <div class="content_container">
        <h1>Sales Report MT</h1>
        <h3>Sales Report MT</h3>

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
                <script type='text/javascript'>
                    $(document).ready(function () {
                        $('#fromDate').datepicker({
                            dateFormat: 'dd-mm-yy',
                            changeMonth: true,
                            changeYear: true
                        });
                        $('#fromDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
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
                        $('#toDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
                    });
                </script>


                <div class="clear"></div>

                <table>
                    <tr>
                        <td>
                            <label class="txtright bold hight1x width1x" style="width: 80px;">
                                Territory:
                            </label>
                        </td>
                        <td>
                            <g:select name="territory" id="territory" class="validate[required]" from="${list}" optionKey="id"
                                      optionValue="name"
                                      noSelection="['':'ALL']"
                            />
                        </td>
                    </tr>
                </table>
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
                                    onclick="salesReportMTgenerate();"/></span>

    </div>

</div>