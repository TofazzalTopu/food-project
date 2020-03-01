<%--
  Created by IntelliJ IDEA.
  User: mdsajedul.islam
  Date: 5/17/2016
  Time: 1:02 PM
--%>
<%@ page import="com.bits.bdfp.inventory.ledgerreport.LedgerReportController;" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Daily Collection Report</title>

<div class="main_container">
    <div class="content_container">
        <h1>Daily Collection Report</h1>
        <h3>Report Information</h3>
        <form name='gFormLedgerSearch' id='gFormLedgerSearch'>
            <div class="element_row_content_container lightColorbg pad_bot0">
                <table>
                    <tr>
                        <td>
                            <label class="txtright bold hight1x width150">
                                Date From:
                            </label>
                        </td>
                        <td>
                            <g:textField name="dateFrom" value="" class="width120 validate[required]" />
                        </td>
                        <td>
                            <label class="txtright bold hight1x width60">
                                To:
                            </label>
                        </td>
                        <td>
                            <g:textField name="dateTo" value="" class="width120 validate[required]"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class="txtright bold hight1x width150">
                                Distribution Point:
                            </label>
                        </td>
                        <td>
                            <g:select name="distributionPoint" id="distributionPoint"  from="${list}"
                                      class="validate[required]"
                                      optionKey="Id"
                                      optionValue="NAME"
                                      noSelection="['': '- Select Distribution Point -']"
                                      onchange="getSalesMan(this.value);"/> <!-- //  added by liton  22-02-2017 -->
                        </td>
                        <td>
                            <label class="txtright bold hight1x width60">
                                Salesman:
                            </label>
                        </td>
                        <td>
                            <g:select name="salesMan" id="salesMan"  from=""
                                      optionKey="ID"
                                      optionValue="Customer"
                                      class="validate[required]"
                                      noSelection="['': '- Select Salesman -']" /> <!-- //  added by liton  22-02-2017 -->
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
        $("#dateFrom, #dateTo").datepicker(
                { dateFormat: 'dd-mm-yy',
                    changeMonth:true,
                    changeYear:true
                });
        $('#dateFrom').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#dateTo').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

        $("#gFormLedgerSearch").validationEngine({
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormLedgerSearch").validationEngine('attach');
    });

//  added by liton  22-02-2017
    function getSalesMan(dpId){
        if(!dpId){
            $("#salesMan").html('<option value="">- Select Salesman -</option>');
            return false;
        }
        SubmissionLoader.showTo();
        $.ajax({
            url: "${application.contextPath}/${params.controller}/getSalesmanListByDp",
            type: 'post',
            data: {dpId:dpId},
            textType: 'json',
            success: function(result){
                var options = '';
                $.each(result, function(key, val){
                    options += '<option value="'+val.ID+'">'+val.Customer+'</option>'
                });

                $("#salesMan").html(options);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
            },
            complete: function(){
                SubmissionLoader.hideFrom();
            }
        });
    }
//    end

    function executeAjax() {
        if (!$("#gFormLedgerSearch").validationEngine('validate')) {
            return false;
        }

        var dateFrom = $('#dateFrom').val();
        var dateTo = $('#dateTo').val();
        var distributionPoint =  $('#distributionPoint').val();
        var salesMan =  $('#salesMan').val();
        SubmissionLoader.showTo();
        window.open("${resource(dir:'dailyCollectionReport', file:'printJournal')}?format=PDF&dateFrom=" + dateFrom + "&dateTo=" + dateTo + "&dpPoint=" + distributionPoint + "&salesMan=" + salesMan);
        SubmissionLoader.hideFrom();
    }
</script>


