<%--
  Created by IntelliJ IDEA.
  User: mdtofazzal.hossain
  Date: 6/12/2016
  Time: 3:01 PM
--%>

%{--<%@ page contentType="text/html;charset=UTF-8" %>--}%

%{--<%@ page import="com.bits.bdfp.report.StockOutReportController" %>--}%

<script>

    $(document).ready(function () {
       /* $('#dateWise').show();
        $('#monthWise').hide();*/

    });


    function generateStockOutReport() {
//        alert("hello");
        var fromDate = $('#fromDate').val();
        var toDate = $('#toDate').val();
        var monthYr = $('#monthYr').val();
        var territory = $('#territory').val();
        var territoryArea=$("#territory option:selected").text();
//        alert(territoryArea);
//        var territoryName=$('#territory').selected();
        SubmissionLoader.showTo();
        

        if(fromDate){
            if(toDate) {
                window.open("${resource(dir:'stockOutReport', file:'generateStockOutReport')}?format=PDF&fromDate=" + fromDate + "&toDate=" + toDate);
            }else{
                MessageRenderer.renderErrorText("Please select to Date.");
            }
        }
        if(monthYr){
            window.open("${resource(dir:'stockOutReport', file:'generateStockOutReport')}?format=PDF&monthYr="+monthYr);
        }
       // window.open("${resource(dir:'stockOutReport', file:'generateStockOutReport')}");
        //window.open("${resource(dir:'stockOutReport', file:'generateStockOutReport')}?format=PDF&fromDate="+fromDate+"&toDate="+toDate+"&monthYr="+monthYr);
        SubmissionLoader.hideFrom();
    }
</script>



<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="stockOutReport.create.label" default="Stock Out Report"/></title>

<div class="main_container">
    <div class="content_container">
        <h1><g:message code="stockOutReport.create.label" default="Stock Out Report"/></h1>
        <h3><g:message code="stockOutReport.Info.label" default="Stock Out Report"/></h3>

        <div class="clear height5"></div>

        <div id="dateWise" class="element_container_big">
            <div class="block-title">

                <div class='element-title'>
                    <g:message code='stockOutReport.fromDate.label' default='From Date'/>
                </div>

                <div class='element-title'>
                    <g:message code='stockOutReport.toDate.label' default='To Date'/>
                </div>


                <div class="clear"></div>
            </div>

            <div class="block-input">

                <div class='element-input inputContainer'><g:textField  name="fromDate" id="fromDate"/>
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

                <div class='element-input inputContainer'><g:textField  name="toDate" id="toDate"/>
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

    </div>

        <div id="monthWise" class="element_container_big">
            <div class="block-title">

                <div class='element-title'>
                    <g:message code='stockOutReport.month.label' default='Month'/>
                </div>

                <div class="clear"></div>
            </div>

            <div class="block-input">

                <div class='element-input inputContainer'><g:textField  name="monthYr" id="monthYr"/>
                </div>
                <script type='text/javascript'>
                    $(document).ready(function () {
                        $('#monthYr').datepicker({
                            dateFormat: 'mm-yy',
                            changeMonth: true,
                            changeYear: true
                        });
                        $('#monthYr').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                    });
                </script>

                <div class="clear"></div>


            </div>
        </div>

        <div class="clear height5"></div>



    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="print-button" id="dialog-confirm-workOrder"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Generate Report"
                                    onclick="generateStockOutReport();"/></span>

    </div>

</div>