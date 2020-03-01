
<%@ page import="com.bits.bdfp.setup.salestarget.SalesReportMTController" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="CustomerPaymentAndSalesReport.create.label" default="Customer Payment And Sales Report"/></title>

<script>

    function executePreConditionReportCustomerPaymentAndSales() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormCustomerPaymentAndSales").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function salesReportHEADgenerate() {
if(!executePreConditionReportCustomerPaymentAndSales())
{
    return false

}
        var fromDate = $('#fromDate').val();
        SubmissionLoader.showTo();
        %{--window.open("${resource(dir:'reportCustomerPaymentAndSale', file:'rptCustomerPaymentAndSalesReport')}?format=PDF&fromDate="+fromDate+"&toDate="+toDate+"&customerID="+$('#hCustomerId').val());--}%
        window.open("${resource(dir:'reportCustomerPaymentAndSale', file:'rptCustomerPaymentAndSalesReport')}?format=PDF&fromDate="+fromDate+"&cusID="+$('#hCustomerId').val());
        SubmissionLoader.hideFrom();
    }



    //loadGrid();
   // })

</script>
<form id="gFormCustomerPaymentAndSales">
<div class="main_container">
    <div class="content_container">
        <h1><g:message code="salesReportInstitutional.create.label" default="Customer Payment and Sales Report"/></h1>

<h3><g:message code="salesReportInstitutional.Info.label" default="Customer Payment and Sales Report"/></h3>
        <g:render template="script"/>

<div class="clear height5"></div>



        <div class="element_row_content_container lightColorbg pad_bot0">
            <label class="txtright bold hight1x width1x">
                <g:message code="CustomerPaymentAndSalesReport.date.label" default="Date"/>
                <span class="mendatory_field">*</span>
            </label>


                <div class='element-input inputContainer'>
                    <g:textField class="validate[required] text-input datepicker"
                                                                       name="fromDate" id="fromDate" readonly="true"/>
                </div>
                <script type='text/javascript'>
                    $(document).ready(function () {

                        $('#fromDate').datepicker({
                            dateFormat: 'dd-mm-yy',
                            changeMonth: true,
                            changeYear: true,
                            showButtonPanel: true,

                            onClose: function(dateText, inst) {
                                var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                                var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                                $(this).val($.datepicker.formatDate('mm-yy', new Date(year, month, 1)));
                            }
                        });

                        $("#fromDate").focus(function () {
                            $(".ui-datepicker-calendar").hide();
                            $("#ui-datepicker-div").position({
                                my: "center top",
                                at: "center bottom",
                                of: $(this)
                            });
                        });
                        $('#fromDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                    });
                </script>


        </div>


 <div class="element_row_content_container lightColorbg pad_bot0">
     <label class="txtright bold hight1x width1x">
         <g:message code="CustomerPaymentAndSalesReport.name.label" default="Customer Name"/>
         <span class="mendatory_field">*</span>
     </label>

     <div class='element-input inputContainer'>
         <g:hiddenField name="hCustomerId"></g:hiddenField>
         <g:textField class="validate[required] width400" id='customerList' name="name"/>
     </div>

 </div>



<div class="buttons" style="margin-left:10px;">
    <span class="button"><input type="button" name="print-button" id="dialog-confirm-workOrder"
                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                value="Generate Report"
                                onclick="salesReportHEADgenerate();"/></span>

</div>

</div>
</div>
</form>