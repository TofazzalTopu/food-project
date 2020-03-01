<%--
  Created by IntelliJ IDEA.
  User: mdtofazzal.hossain
  Date: 7/10/2016
  Time: 3:22 PM
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
                    // alert(JSON.stringify(val) )
                    options += '<option value="' + val.id + '">' + val.dpName + '</option>';
//                  alert(options)
                })
                $("#distributionPoint").html(options);
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
        }
        if(monthYr==''){
            MessageRenderer.renderErrorText("Please select Date.");
        }
        SubmissionLoader.showTo();

        if(monthYr){
            window.open("${resource(dir:'branchStockReport', file:'generateBranchStockReport')}?format=PDF&dp="+distributionPoint+ "&monthYr=" + monthYr);
        }

       /* if(fromDate){
            if(toDate) {
                window.open("${resource(dir:'branchStockOutReport', file:'generateBranchStockReport')}?format=PDF&fromDate=" + fromDate + "&toDate=" + toDate);
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
<title><g:message code="stockOutReport.create.label" default="Branch Stock Report"/></title>

<div class="main_container">
    <div class="content_container">
        <h1><g:message code="stockOutReport.create.label" default="Branch Stock Report"/></h1>
        <h3><g:message code="stockOutReport.Info.label" default="Branch Stock Report"/></h3>

        <div class="clear height5"></div>

        <div class="element_container_big" >
            <div class="block-title">
                <div class='element-title'>
                    <g:message code='expenseFromDPCashPool.distributionPoint.label' default='Distribution Point' />
                    <span class="mendatory_field"> * </span>
                </div>
                <div class="block-input">
                    %{--<div class='element-input inputContainer '><g:select name="distributionPoint.id" id="distributionPoint" class="validate[required]"  optionKey="id" value="${expenseFromDPCashPool?.distributionPoint?.id}"  noSelection="[1: 'For All DP']" /></div>--}%
                    <div class='element-input inputContainer '><g:select name="distributionPoint.id" id="distributionPoint" class="validate[required]"  from="${com.bits.bdfp.inventory.sales.DistributionPoint.list()}" optionKey="id" value="${expenseFromDPCashPool?.distributionPoint?.id}"  noSelection="['null': 'For All DP']" /></div>
                    %{--<div class='element-input inputContainer '><g:select name="distributionPoint.id" id="distributionPoint" class="validate[required]"   optionKey="id" value="${expenseFromDPCashPool?.distributionPoint?.id}"  onchange="selectCashPool(this.value);" noSelection="['null': 'Factory/ Non Factory']" /></div>--}%
                    <div class="clear"></div>
                </div>
                <div class="clear"></div>
            </div>
        </div>
        %{--
        <div class="element_container_big" >
            <div class="block-title">
                <div class='element-title'>
                    <g:message code='expenseFromDPCashPool.cashPool.label' default='Cash Pool' />
                    <span class="mendatory_field"> * </span>
                </div>
                <div class="block-input">
                    <div class='element-input inputContainer '><g:select name="cashPool.id" id="cashPool" class="validate[required]"  from="${com.bits.bdfp.common.CashPool.list()}" optionKey="id" value="${expenseFromDPCashPool?.cashPool?.id}" noSelection="['null': 'DP Cash Pool']" /></div>
                    <div class="clear"></div>
                </div>
                <div class="clear"></div>
            </div>
        </div>
    --}%
        <div class="clear height5"></div>

        %{--<div id="dateWise" class="element_container_big">
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
--}%
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