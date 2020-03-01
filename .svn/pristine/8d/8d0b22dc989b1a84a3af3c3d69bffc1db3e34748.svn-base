<%--
  Created by IntelliJ IDEA.
  User: mdsajedul.islam
  Date: 6/5/2016
  Time: 12:46 PM
--%>




<%@ page import="com.bits.bdfp.inventory.ledgerreport.LedgerReportController;" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="journalReport.create.label" default="Summary Daily Delivery Report"/></title>
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
        <h1><g:message code="ledgerReport.create.label" default="Summary Daily Delivery Report"/></h1>
        <h3><g:message code="ledgerReport.info.label" default="Report Information"/></h3>
        <form name='gFormLedgerSearch' id='gFormLedgerSearch'>
            <div class="element_row_content_container lightColorbg pad_bot0">
                <table>

                    <tr>
                        <td>
                            <label class="txtright bold hight1x width1x" style="width:150px">
                                <g:message code="customerCategory" default="Customer Category"/>
                            </label>
                        </td>
                        <td>
                            <div class='inputContainer '>
                                <g:select from="${list}" name="customerCategory" id="customerCategory"
                                          class="validate[required] width180"
                                          optionKey="id" optionValue="name"

                                />
                            </div>
                        </td>
                    </tr>


                    <tr>
                        <td>
                            <label class="txtright bold hight1x width1x" style="width:150px;">
                                <g:message code="ledgerReport.ledgerDate.label"
                                           default="Date From"/>
                            </label>
                        </td>
                        <td>
                            <g:textField name="ledgerDateFrom" value="" class="width170 validate[required]" />
                        </td>
                        <td>
                            <label class="txtright bold hight1x width1x" style="width:60px">
                                <g:message code="ledgerReport.ledgerDate.label" default="To"/>
                            </label>
                        </td>
                        <td>
                            <g:textField name="ledgerDateTo" value="" class="width170 validate[required]"/>
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

            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>

                </td>

                <td>


                </td>
            </tr>
        </form>

    </div>
</div>

<script>
    $(document).ready(function () {
        //selectCustomerCategory();

        $('#print-button').focus();
        $("#ledgerDateFrom, #ledgerDateTo").datepicker(
                { dateFormat: 'dd-mm-yy',
                    changeMonth:true,
                    changeYear:true
                });
        $('#ledgerDateFrom').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#ledgerDateTo').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

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
        var ledgerDateTo = $('#ledgerDateTo').val();
        var categoryId = $('#customerCategory').val();
      //  alert(customerCategory)
        SubmissionLoader.showTo();

        window.open("${resource(dir:'summaryDailyDeliveryReport', file:'generateSummaryDailyDeliveryReport')}?format=PDF&ledgerDateFrom="+ledgerDateFrom+"&ledgerDateTo="+ledgerDateTo+"&categoryId="+categoryId);
        //window.open("${resource(dir:'summaryDailyDeliveryReport', file:'printReport')}?format=PDF&reportDate="+ledgerDateFrom+"&ledgerDateTo="+ledgerDateTo);
        SubmissionLoader.hideFrom();
    }


    /*function selectCustomerCategory(){
//        alert("fghgh");
                //console.log("listSize "+${listSize} )
                console.log("listSize "+${list})
        var size;
        var options = '';
        if ('${listSize}' != '') {
            size = ${listSize};
            alert(size)
            if (size == 1) {
                options = '<option value="' + ${list}[0].id + '">' + ${list}[0].name + '</option>';
            } else {
                options = '<option value="">Please Select</option>';
                for (var i = 0; i < size; i++) {
                    options += '<option value="' + ${list}[i].id + '">' + ${list}[i].name     + '</option>';
                }
            }
            $("#customerCategory").html(options);
        }
    }*/
</script>


