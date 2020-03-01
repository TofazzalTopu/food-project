<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

       var dpId = document.getElementById("distributionPoint").value
        //alert(dpId)
        //setDefaultCustomer();
      //  loadBankAndCash();


        $("#gFormCashReceivedFromBranch").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormCashReceivedFromBranch").validationEngine('attach');
//        reset_form("#gFormCashReceivedFromBranch");

        setPreviousDatePicker('transactionDate');
        $('#transactionDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        if ('${list.size()}' == '1') {
            loadBankAndCash();
        }
    });

    function loadBankAndCash() {
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'cashReceivedFromBranch', file:'loadBankAndCash')}?entId=" + $("#enterpriseConfiguration").val(),
            success: function (data, textStatus) {
                var option = '<option value="">Please Select</option>';
                var list = data.cashPool;
                for (var i = 0; i < list.length; i++) {
                    option += '<option value="' + list[i].id + '">' + list[i].name + '</option>';
                }
                $("#cashPool").html(option);
                option = '<option value="">Please Select</option>';
                list = data.bankAccount;
                for (i = 0; i < list.length; i++) {
                    option += '<option value="' + list[i].id + '">' + list[i].name + '</option>';
                }
                $("#bankAccount").html(option);
                option = '<option value="">Please Select</option>';
                list = data.dp;
                for (i = 0; i < list.length; i++) {
                    option += '<option value="' + list[i].id + '" name="' + list[i].customer + '">' + list[i].name + '</option>';
                }
                $("#distributionPoint").html(option);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
            },
            dataType: 'json'
        });
    }

    function setDefaultCustomer() {
        if(!$("#distributionPoint").val()){
            return false;
        }
       // alert('hgffg');
        /*var dpId = document.getElementById("distributionPoint").value
        var dp=$("#distributionPoint").val()
        */
        console.log($("#distributionPoint"));
        $("#defaultCustomer").val($("#distributionPoint").find(':selected').attr('name'));
        $("#totalDeposit").val('');
        $("#depositToBankAccount").val('');
        $("#depositToHoCash").val('');
        $("#salesAmount").val('');
        $("#sdAmount").val('');
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'cashReceivedFromBranch', file:'loadTransactionNo')}?dpId=" + $("#distributionPoint").val() + "&date=" + $("#transactionDate").val(),
            success: function (data, textStatus) {
                var option = '<option value="">Please Select</option>';
                for (var i = 0; i < data.length; i++) {
                    option += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                }
                $("#depositCashToDepositPool").html(option);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
            },
            dataType: 'json'
        });
    }

    function setData(){
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'cashReceivedFromBranch', file:'fetchData')}?id=" + $("#depositCashToDepositPool").val(),
            success: function (data, textStatus) {
                $("#totalDeposit").val(data[0].total);
                $("#depositToBankAccount").val(data[0].deposit_to_bank_account);
                $("#depositToHoCash").val(data[0].deposit_to_ho_cash);
                $("#sdAmount").val(data[0].sd_amount);
                $("#salesAmount").val(data[0].sales_amount);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
            },
            dataType: 'json'
        });
    }

    function executePreConditionCashReceivedFromBranch() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormCashReceivedFromBranch").validationEngine('validate')) {
            return false;
        }
        return true;
    }

    function executeAjaxCashReceivedFromBranch() {
        if (!executePreConditionCashReceivedFromBranch()) {
            return false;
        }
        if(!checkVal()){
            return false;
        }

        var actionUrl = "${request.contextPath}/${params.controller}/create";
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormCashReceivedFromBranch").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionCashReceivedFromBranch(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    var ent = $("#enterpriseConfiguration").val();
                    reset_form('#gFormCashReceivedFromBranch');
                    $("#enterpriseConfiguration").val(ent);
                    $("#cashPool").val('');
                    $("#bankAccount").val('');
                    $("#distributionPoint").val('');
                    $("#depositCashToDepositPool").html('');
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom()
            },
            dataType: 'json'
        });
        return false;
    }

    function executePostConditionCashReceivedFromBranch(result) {
        if (result.type == 1) {
            var ent = $("#enterpriseConfiguration").val();
            reset_form('#gFormCashReceivedFromBranch');
            $("#enterpriseConfiguration").val(ent);
            $("#cashPool").val('');
            $("#bankAccount").val('');
            $("#distributionPoint").val('');
            $("#depositCashToDepositPool").html('');
        }
        MessageRenderer.render(result);
    }

    function checkVal(){
        var msg = {
            "class": "com.docu.common.Message",
            "messageBody": ["Mandatory Field"],
            "messageTitle": "Message",
            "type": 2
        };
        var total = parseFloat($("#totalDeposit").val());
        var calcTotal = parseFloat($("#salesAmount").val()) + parseFloat($("#sdAmount").val());
        if(total != calcTotal){
            msg.messageBody = 'Total of Sales and SD amount can not be greater or lesser than Total deposited.';
            MessageRenderer.render(msg);
            return false;
        }

        var depositToBankAccount = Number($("#depositToBankAccount").val());
        var depositToHoCash = Number($("#depositToHoCash").val());
        if(depositToBankAccount > 0){
             if(!$("#bankAccount").val()){
                 msg.messageBody = 'Select Bank Account';
                 MessageRenderer.render(msg);
                 return false;
             }
        }
        if(depositToHoCash > 0){
            if(!$("#cashPool").val()){
                msg.messageBody = 'Select Cash Pool';
                MessageRenderer.render(msg);
                return false;
            }
        }
        return true;
    }

</script>