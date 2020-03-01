<%--
  Created by IntelliJ IDEA.
  User: mdtofazzal.hossain
  Date: 10/31/2016
  Time: 12:31 PM
--%>


<script>

    $(document).ready(function () {
        fetchDistributionPoint();
        /* $('#dateWise').show();
         $('#monthWise').hide();*/

    });

    function fetchDistributionPoint(){
        /*if (id == '') {
         var options = '<option value="">--Select--</option>';
         $("#distributionPoint").html(options);
         return false;
         }
         else {
         //   alert(id)
         var options = '<option value="">Please Select</option>';
         }*/
        //alert('sfd')

        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'branchStockReport', file:'fetchDistributionPoint')}",
            success: function (data) {
                options = '<option value="">Select Distribution Point</option>';
                $.each(data, function (key, val) {
                    options += '<option value="' + val.id + '">' + val.dpName + '</option>';
                })
                $("#distributionPoint").html(options);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                /*if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }*/
            },
            dataType: 'json'
        });
    }


    function generateBranchStockReport() {
        // alert("helloBranch");
        var fromDate = $('#fromDate').val();
        var toDate = $('#toDate').val();
        var monthYr = $('#monthYr').val();
        var distributionPoint = $('#distributionPoint').val();

        //alert(monthYr)

        if(distributionPoint=='null'){
            MessageRenderer.renderErrorText("Please select distributionPoint.");
            return;
        }
        if(monthYr==''){
            MessageRenderer.renderErrorText("Please select Date.");
        }
        SubmissionLoader.showTo();

        if(monthYr){
            window.open("${resource(dir:'uhtSaleAndStockStatement', file:'generateUHTStockStatement')}?format=PDF&dp="+distributionPoint+ "&monthYr=" + monthYr);
            window.open("${resource(dir:'uhtSaleAndStockStatement', file:'generateUhtSaleStockStatementDetails')}?format=PDF&dp="+distributionPoint+ "&monthYr=" + monthYr);
        }

        /* if(fromDate){
         if(toDate) {
         window.open("${resource(dir:'uhtSaleAndStockStatement', file:'generateUHTStockStatement')}?format=PDF&fromDate=" + fromDate + "&toDate=" + toDate);
         }else{
         MessageRenderer.renderErrorText("Please select to Date.");
         }
         }*/

        // window.open("${resource(dir:'branchStockReport', file:'generateBranchStockReport')}");
        //window.open("${resource(dir:'branchStockReport', file:'generateBranchStockReport')}");
        //window.open("${resource(dir:'branchStockOutReport', file:'generateBranchStockReport')}?format=PDF&fromDate="+fromDate+"&toDate="+toDate+"&monthYr="+monthYr);
        SubmissionLoader.hideFrom();
    }
</script>



<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="stockOutReport.create.label" default="UHT Sale And Stock Statement"/></title>

<div class="main_container">
    <div class="content_container">
        <h1><g:message code="stockOutReport.create.label" default="UHT Sale And Stock Statement"/></h1>
        <h3><g:message code="stockOutReport.Info.label" default="UHT Sale And Stock Statement"/></h3>

        <div class="clear height5"></div>

        <div class="element_container_big" >
            <div class="block-title">
                <div class='element-title'>
                    <g:message code='expenseFromDPCashPool.distributionPoint.label' default='Branch' />
                    <span class="mendatory_field"> * </span>
                </div>
                <div class="block-input">
                    %{--<div class='element-input inputContainer '><g:select name="distributionPoint.id" id="distributionPoint" class="validate[required]"  optionKey="id" value="${expenseFromDPCashPool?.distributionPoint?.id}"  noSelection="[1: 'For All DP']" /></div>--}%
                    <div class='element-input inputContainer '><g:select name="distributionPoint.id" id="distributionPoint" class="validate[required]"  from="${com.bits.bdfp.inventory.sales.DistributionPoint.list()}" optionKey="id" value="${expenseFromDPCashPool?.distributionPoint?.id}"  noSelection="['null': 'Please Select Branch']" /></div>
                    %{--<div class='element-input inputContainer '><g:select name="distributionPoint.id" id="distributionPoint" class="validate[required]"   optionKey="id" value="${expenseFromDPCashPool?.distributionPoint?.id}"  onchange="selectCashPool(this.value);" noSelection="['null': 'Factory/ Non Factory']" /></div>--}%
                    <div class="clear"></div>
                </div>
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
                                    onclick="generateBranchStockReport();"/></span>

    </div>

</div>