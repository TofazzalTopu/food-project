<%--
  Created by IntelliJ IDEA.
  User: mdsajedul.islam
  Date: 7/19/2016
  Time: 11:34 AM
--%>



<%@ page import="com.bits.bdfp.reports.SalesManSecurityDuesController;" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="journalReport.create.label" default="Salesman Security & Dues Report"/></title>
<style>
.ledgerDateFromformError {
    left: 260px !important;
    margin-top: 60px !important;
}

</style>
<div class="main_container">
    <div class="content_container">
        <h1><g:message code="ledgerReport.create.label" default="Salesman Security & Dues Report"/></h1>
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
                            <g:textField name="ledgerDateFrom" value="" class="width120 validate[required]" />
                        </td>
                        <td>
                            <g:select name="distributionPoint" id="distributionPoint"  from="${list}"
                                      optionKey="Id"
                                      optionValue="NAME"
                                      noSelection="['': '- Select Distribution Point -']"/>
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
        $("#ledgerDateFrom").datepicker(
                { dateFormat: 'dd-mm-yy',
                    changeMonth:true,
                    changeYear:true
                });
        $('#ledgerDateFrom').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});


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

        var ledgerDateFrom = $('#ledgerDateFrom').val();
        var distributionPoint =  $('#distributionPoint').val();
        SubmissionLoader.showTo();
        window.open("${resource(dir:'salesManSecurityDues', file:'printReport')}?format=PDF&givenDate="+ledgerDateFrom+"&dpoint="+distributionPoint);
        SubmissionLoader.hideFrom();
    }
</script>