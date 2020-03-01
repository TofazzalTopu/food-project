<script type="text/javascript">
    $(document).ready(function () {
        $("#jqgrid-container-details").hide();
        initializeGrid();
        loadBankAccount();
    });
    function bankOnclick() {
        $("#tdBank").show();
        $("#tdCash").hide();
    }
    function cashOnclick() {
        $("#tdBank").hide();
        $("#tdCash").show();
    }
    function loadBankAccount() {
        jQuery.ajax({
            type: "POST",
            url: '${resource(dir:'customerPayment', file:'listAccount')}',
            dataType: 'json',
            success: function (data, textStatus) {

                var options = '<option value="">Select Bank Account</option>';
                $.each(data, function (key, val) {
                    options += '<option value="' + val.id + '">' + val.account_no + '</option>';

                })
                $("#bankAccount").html(options);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {

            }
        });
    }
    function initializeGrid() {
        $("#jqgrid-grid-amount").jqGrid({
            datatype: "json",
            colNames: [
                '',
                'Date',
                'Name',
                'MR No',
                'Total Amount',
                'Show',
                ''
            ],
            colModel: [
                {name: 'id', index: 'id', width: 20, align: 'left', hidden: true},
                {name: 'date_transaction', index: 'date_transaction', width: 100, align: 'left', hidden: true},
                {name: 'name', index: 'name', width: 200, align: 'left'},
                {name: 'mr_no', index: 'mr_no', width: 200, align: 'left',hidden:true},
                {name: 'amount', index: 'amount', width: 120, align: 'right',
                    formatter:'currency',
                    formatoptions:{defaulValue:0,thousandsSeparator:',',decimalPlaces:2}
                },
                {name: 'details', index: 'details', width: 60, align: 'center', hidden: false},
                {name: 'edit', index: 'edit', width: 20, align: 'left', hidden: false}
            ],
            rowNum: 1,
            rowList: [1, -1],
            pager: '#jqgrid-amount-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Deposit Amount",
            autowidth: false,
            height: 40,
            scrollOffset: 0,
            loadComplete: function () {
                var ids = jQuery("#jqgrid-grid-amount").jqGrid('getDataIDs');
                for (key in ids) {
                    var id = $("#jqgrid-grid-amount").getCell(ids[key], 'id');
                    var amount = $("#jqgrid-grid-amount").getCell(ids[key], 'amount');
                    $("#amountToDepositHidden").val(amount);
                    $("#jqgrid-grid-amount").jqGrid('setRowData', ids[key], {edit: '<input type="checkbox" id="pool-checkbox' + id + '" class="pool-checkbox" value="' + id + '" onclick="loadDepositAmount('+id+');" />'});
                    $("#jqgrid-grid-amount").jqGrid('setRowData', ids[key], {details: '<a href="#" id="pool-details' + id + '" onclick="showDepositDetails(' + id + ');">Details</a>'});

                }
            },
            onSelectRow: function (rowid, status) {

            }
        });
        $("#jqgrid-grid-amount").jqGrid('navGrid', '#jqgrid-amount-pager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });

        $("#jqgrid-grid").jqGrid({
            datatype: "json",
            colNames: [
                '',
                'Date',
                'Name',
                'MR No',
                'Total',
                '1000',
                '500',
                '100',
                '50',
                '20',
                '10',
                '5',
                '2',
                '1',
                '0.5',
                ''
            ],
            colModel: [

                {name: 'id', index: 'id', width: 20, align: 'left', hidden: true},
                {name: 'date_transaction', index: 'date_transaction', width: 100, align: 'center', hidden: false},
                {name: 'name', index: 'name', width: 200, align: 'left'},
                {name: 'mr_no', index: 'mr_no', width: 100, align: 'center'},
                {name: 'amount', index: 'amount', width: 100, align: 'right'},
                {name: '1000', index: '1000', width: 50, align: 'right'},
                {name: '500', index: '500', width: 50, align: 'right'},
                {name: '100', index: '100', width: 50, align: 'right'},
                {name: '50', index: '50', width: 50, align: 'right'},
                {name: '20', index: '20', width: 50, align: 'right'},
                {name: '10', index: '10', width: 50, align: 'right'},
                {name: '5', index: '5', width: 25, align: 'right'},
                {name: '2', index: '2', width: 25, align: 'right'},
                {name: '1', index: '1', width: 25, align: 'right'},
                {name: '0.5', index: '0.5', width: 50, align: 'right'},
                {name: 'edit', index: 'edit', width: 20, align: 'center', hidden: false}
            ],
            rowNum: 10,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Deposit List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
                var ids = jQuery("#jqgrid-grid").jqGrid('getDataIDs');

                for (key in ids) {
                    var id = $("#jqgrid-grid").getCell(ids[key], 'id');
                    $("#jqgrid-grid").jqGrid('setRowData', ids[key], {edit: '<input type="checkbox" id="pool-details-checkbox' + id + '" class="pool-details-checkbox" value="' + id + '" />'});

                }
            },
            onSelectRow: function (rowid, status) {

            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });
    }
    function loadDepositAmount(id){
        if($('#pool-checkbox'+id).is(':checked')){
            $("#amountToDeposit").val($("#amountToDepositHidden").val());
        }else{
            $("#amountToDeposit").val('');
        }
    }
    function executeAjax() {
//        debugger;
        var data = {};
        var i = 0;
        var c = 0;
        var id = "";
        var paymentMode ="";
        var gridCollection = jQuery("#qgrid-grid-amount").jqGrid('getRowData');
        $('.pool-checkbox').each(function () {
            if (this.checked) {
                c++;
            }
            i++;
        });

        if(c<1){
//            alert("Please select at least one transfer")
            MessageRenderer.render({
                "messageBody": "Please select cash pool to deposit from list.",
                "messageTitle": "Deposit Cash To Deposit Pool",
                "type": "0"
            });
            return false;
        }

//        if(c>1){
//            alert("Please select one transfer at a time")
//            return false;
//        }

        if($('#amountToDeposit').val()==''){
            MessageRenderer.render({
                "messageBody": "Please enter amount to deposit.",
                "messageTitle": "Deposit Cash To Deposit Pool",
                "type": "0"
            });
            return false;
        }

        if($('#rdBank').is(':checked')){
            if(!$("#bankAccount").val()){
//                alert("Please select bank account")
                MessageRenderer.render({
                    "messageBody": "Please select bank account.",
                    "messageTitle": "Deposit Cash To Deposit Pool",
                    "type": "0"
                });
                return false;
            }
            paymentMode = "Bank";
        }
        if($('#rdCash').is(':checked')){
            if(!$("#depositPoolNameDep").val()){
//                alert("Please select pool account")
                MessageRenderer.render({
                    "messageBody": "Please select pool account.",
                    "messageTitle": "Deposit Cash To Deposit Pool",
                    "type": "0"
                });
                return false;
            }
            paymentMode = "Cash";
        }
        if(!checkDepositAmount()){
            return false;
        }

        actionUrl = '${resource(dir:'customerPayment', file:'createDepositCashToPool')}?amountToDeposit=' + $("#amountToDeposit").val() + '&cashPoolId=' + $("#depositPoolNameDep").val() + '&bankAcId=' + $("#bankAccount").val() +'&paymentMode=' + paymentMode + '&dipositorCashPool=' + $("#depositPoolName").val();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionSecondaryDemandOrder(data);
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
        return false;
    }
    function executePostConditionSecondaryDemandOrder(result) {
        if (result.type == 1) {
//            $("#jqgrid-grid").clearGridData();
            $("#jqgrid-grid-amount").trigger("reloadGrid");
        }
        MessageRenderer.render(result);
    }
    function paymentDetailsInfo() {
        if (($('#depositPoolDate').val() || $('#depositPoolName').val())) {
            $("#jqgrid-container-details").hide();
            $("#depositPoolDateHidden").val($('#depositPoolDate').val());
            $("#depositPoolNameHidden").val($("#depositPoolName option:selected").text());
            $("#amountToDepositHidden").val('');

            $("#jqgrid-grid-amount").setGridParam({
                url: '${resource(dir:'customerPayment', file:'listCashDepositAmount')}?transactionDate=' + $('#depositPoolDate').val() + '&name=' + $("#depositPoolName option:selected").text() + '&securityDeposit=' + $('#securityDeposit').is(':checked')
            });
            $("#jqgrid-grid-amount").trigger("reloadGrid");
        }
        else {
            alert("Please select search type and input value")
        }
    }

    function showDepositDetails(id){
        $("#jqgrid-container-details").show();
        $("#jqgrid-grid").setGridParam({
            url: '${resource(dir:'customerPayment', file:'listCashDepositInfo')}?transactionDate=' + $('#depositPoolDateHidden').val() + '&name=' + $('#depositPoolNameHidden').val()
        });
        $("#jqgrid-grid").trigger("reloadGrid");
    }

    function checkDepositAmount(){
        var amountToDeposit = parseFloat($("#amountToDeposit").val());
        var amountToDepositHidden = parseFloat($("#amountToDepositHidden").val());
        if(amountToDeposit > amountToDepositHidden){
            MessageRenderer.render({
                "messageBody": "Deposit amount cannot greater than cash pool amount.",
                "messageTitle": "Deposit Cash To Deposit Pool",
                "type": "0"
            });
            return false;
        }else if($("#amountToDepositHidden").val()==''){
            MessageRenderer.render({
                "messageBody": "Cash pool amount is not available.",
                "messageTitle": "Deposit Cash To Deposit Pool",
                "type": "0"
            });
            return false;
        }else{
            return true;
        }
    }

    function securityDepositCheck(val){
//        alert($('#'+val).is(':checked') ? true : false);
        if($('#'+val).is(':checked')){
//            alert(val);
            this.value(true);
        }else{
//            alert('no val');
            this.value('');
        }
    }
</script>