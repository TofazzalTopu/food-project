<%--
  Created by IntelliJ IDEA.
  User: liton.miah
  Date: 12/27/2015
  Time: 3:08 PM
--%>

<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.inventory.ledgerreport.LedgerReportController;com.bits.bdfp.accounts.SubLedger" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Ledger Report</title>
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
        <h1>Ledger Report</h1>
        <h3>Ledger Report Information</h3>
        <g:render template="script"/>
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
                            <g:textField name="ledgerDateFrom" value="" class="width160 validate[required] text-input datepicker" />
                        </td>
                        <td>
                            <label class="txtright bold hight1x width100">
                                To:<span class="mendatory_field">*</span>
                            </label>
                        </td>
                        <td>
                            <g:textField name="ledgerDateTo" value="" class="width160 validate[required] text-input datepicker"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class="txtright bold hight1x width100">
                                Accounts Head:<span class="mendatory_field">*</span>
                            </label>
                        </td>
                        <td>
                            <g:select name="chartOfAccountsId" id="chartOfAccountsId" class="validate[required] width220" from="${coaList}"

                                      noSelection="['':'Select Accounts Head']"
                            />
                        </td>
                    </tr>
                    %{--optionKey="id" optionValue="${{it.chartOfAccountName+' - Code['+it.chartOfAccountCodeUser+']'}}"--}%
                    %{--<g:select name="chartOfAccountsId" id="chartOfAccountsId" class="validate[required]" from="${coaList}"
                              optionKey="id" optionValue="${{it.chartOfAccountName+' - Code['+it.chartOfAccountCodeUser+']'}}"
                              noSelection="['':'Select Accounts Head']"
                    />
                    --}%
                    <tr>
                        <td>
                            <label class="txtright bold hight1x width100">
                                Prefix Code:
                            </label>
                        </td>
                        <td>
                            <g:textField name="prefixCode" id="prefixCode" value="" class="width160" />
                        </td>
                        <td>
                            <label class="txtright bold hight1x width100">
                                Postfix Code:
                            </label>
                        </td>
                        <td>
                            <g:textField name="postfixCode" id="postfixCode" value="" class="width160"/>
                        </td>
                    </tr>
                </table>

            </div>
            <div class="clear"></div>
            <div class="buttons">
                <span class="button"><input type="button" name="Print Invoice" id="print-button"
                                            class="ui-button ui-widget ui-state-default ui-corner-all" value="Print Ledger"
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
        var chartOfAccountsId = $('#chartOfAccountsId').val();
        var prefixCode = $('#prefixCode').val();
        var postfixCode = $('#postfixCode').val();

        SubmissionLoader.showTo();
        window.open("${resource(dir:'ledgerReport', file:'printLedger')}?format=PDF&ledgerDateFrom=" + ledgerDateFrom + "&ledgerDateTo=" + ledgerDateTo + "&chartOfAccountsId=" + chartOfAccountsId + "&prefixCode=" + prefixCode + "&postfixCode=" + postfixCode);
        SubmissionLoader.hideFrom();


    }
</script>


