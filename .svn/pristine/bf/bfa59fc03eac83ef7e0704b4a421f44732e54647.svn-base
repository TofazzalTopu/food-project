<%--
  Created by IntelliJ IDEA.
  User: liton.miah
  Date: 12/27/2015
  Time: 3:08 PM
--%>

<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.inventory.ledgerreport.LedgerReportController;" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="ledgerReport.create.label" default="Journal Report"/></title>
<style>
.ledgerDateFromformError {
        left: 260px !important;
        margin-top: 60px !important;
    }
.ledgerDateToformError {
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
        <h1><g:message code="ledgerReport.create.label" default="Journal Report"/></h1>
        <h3><g:message code="ledgerReport.info.label" default="Journal Report Information"/></h3>
        <form name='gFormLedgerSearch' id='gFormLedgerSearch'>
            <div class="element_row_content_container lightColorbg pad_bot0">
                <table>
                    <tr>
                        <td>
                            <label class="txtright bold hight1x width100">
                                From Date:<span class="mendatory_field">*</span>
                            </label>
                        </td>
                        <td>
                            <g:textField name="ledgerDateFrom" value="" class="width120 validate[required] text-input datepicker" />
                        </td>
                        <td>
                            <label class="txtright bold hight1x width60">
                                To:<span class="mendatory_field">*</span>
                            </label>
                        </td>
                        <td>
                            <g:textField name="ledgerDateTo" value="" class="width120 validate[required] text-input datepicker"/>
                        </td>
                    </tr>
                </table>

            </div>
            <div class="clear"></div>
            <div class="buttons">
                <span class="button"><input type="button" name="Print Journal" id="print-button"
                                            class="ui-button ui-widget ui-state-default ui-corner-all" value="Print Journal"
                                            onclick="executeAjax();"/></span>
            </div>
        </form>
    </div>
</div>

<script>
    $(document).ready(function () {
        $("#gFormLedgerSearch").validationEngine({
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormLedgerSearch").validationEngine('attach');
        $('#print-button').focus();
        setDateRange('ledgerDateFrom','ledgerDateTo');
        $('#ledgerDateFrom').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        $('#ledgerDateTo').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
    });

    function executeAjax() {
        if (!$("#gFormLedgerSearch").validationEngine('validate')) {
            return false;
        }

        var ledgerDateFrom = $('#ledgerDateFrom').val();
        var ledgerDateTo = $('#ledgerDateTo').val();
        SubmissionLoader.showTo();
        window.open("${resource(dir:'journalReport', file:'printJournal')}?format=PDF&ledgerDateFrom="+ledgerDateFrom+"&ledgerDateTo="+ledgerDateTo);
        SubmissionLoader.hideFrom();
    }
</script>


