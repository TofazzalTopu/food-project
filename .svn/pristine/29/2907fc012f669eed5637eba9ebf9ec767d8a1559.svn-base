<%--
  Created by IntelliJ IDEA.
  User: mdsajedul.islam
  Date: 6/20/2016
  Time: 2:51 PM
--%>

<%@ page import="com.bits.bdfp.reports.AccountStatementReportController" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="journalReport.create.label" default="Account Statement Report"/></title>
<style>
.DateFromformError {
    left: 260px !important;
    margin-top: 60px !important;
}
.DateToformError {
    left: 530px !important;
    margin-top: 60px !important;
}
.ledgerAccCodeformError {
    left: 260px !important;
    margin-top: 92px !important;
}
</style>
<div class="main_container">
    <div class="content_container">
        <h1><g:message code="ledgerReport.create.label" default="Account Statement Report"/></h1>
        <h3><g:message code="ledgerReport.info.label" default="Report Information"/></h3>
        <form name='gFormLedgerSearch' id='gFormLedgerSearch'>
            <div class="element_row_content_container lightColorbg pad_bot0">
                <table>
                    <tr>
                        <td>
                            <label class="txtright bold hight1x width1x" style="width:150px;">
                                <g:message code="ledgerReport.ledgerDate.label"
                                           default="Date From"/>
                            </label>
                        </td>
                        <td>
                            <g:textField name="DateFrom" id="DateFrom" value="" class="width120 validate[required]" />
                        </td>
                        <td>
                            <label class="txtright bold hight1x width1x" style="width:60px">
                                <g:message code="ledgerReport.ledgerDate.label" default="To"/>
                            </label>
                        </td>
                        <td>
                            <g:textField name="DateTo" id="DateTo" value="" class="width120 validate[required]"/>
                        </td>
                        <td>
                            <g:select name="chartOfAccount" id="chartOfAccount" class="validate[required]" from="${list}"
                                      optionKey="id"
                                      optionValue="name"
                                      noSelection="['': '- Select Chart of Account -']"/>
                        </td>
                        <td>
                            <g:select name="customer" id="customer" class="validate[required]" from="${customerList}"
                                      optionKey="id"
                                      optionValue="name"
                                      noSelection="['': '- Select Customer -']"/>
                        </td>
                    </tr>
                </table>

            </div>
            <div class="clear"></div>
            <div class="buttons">
                <span class="button"><input type="button" name="Print Journal" id="print-button"
                                            class="ui-button ui-widget ui-state-default ui-corner-all" value="Print Report"
                                            onclick="executeAjax();"/></span>
            </div>

        </form>
    </div>
</div>

<script>
    $(document).ready(function () {
        $('#print-button').focus();
        $("#DateFrom, #DateTo").datepicker(
                { dateFormat: 'dd-mm-yy',
                    changeMonth:true,
                    changeYear:true
                });
        $('#DateFrom').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#DateTo').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

        $("#gFormLedgerSearch").validationEngine({
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormLedgerSearch").validationEngine('attach');
    });

    function executeAjax() {
        if (!$("#gFormLedgerSearch").validationEngine('validate')) {
            return false;
        }

        var DateFrom = $('#DateFrom').val();
        var DateTo = $('#DateTo').val();
        var customer = $('#customer').val();
        var chartOfAccount = $('#chartOfAccount').val();

        SubmissionLoader.showTo();
        window.open("${resource(dir:'accountStatementReport', file:'printReport')}?format=PDF&dateFrom="+DateFrom+"&dateTo="+DateTo+"&customer="+customer+"&chartOfAccount="+chartOfAccount);
        SubmissionLoader.hideFrom();
    }
</script>