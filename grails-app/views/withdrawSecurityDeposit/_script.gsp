<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $("#asOfDate").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $('#asOfDate').val($.datepicker.formatDate('dd-mm-yy', new Date()));
        $('#asOfDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

        $('#territory').focus();
        $('#gridTP').hide();
        $('#defaultArea').hide();
    });

    function clearData() {
        $('#defaultCustomer').val('');
        $('#defaultCustomerId').val('');
        $('#availableDPSecurityDeposit').val('');
        $('#gridTP').hide();
        $('#defaultArea').hide();
    }

    function clearAll() {
        $('#territory').val('');
        $('#distributionPoint').val('');
        clearData();
    }

    function clearTp() {
        $("#jqgrid-tp-grid").trigger("reloadGrid");
        $('#tpId').val('');
    }

    function loadTerritory(id) {
        var actionUrl = "${request.contextPath}/${params.controller}/getTerritoryListByEnterprise";
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {enterpriseId: id},
            url: actionUrl,
            success: function (data, textStatus) {
                clearData();
                $('#territory option').remove();
                $('#territory').append('<option value="">' + " - Select Territory - " + '</option>');
                for (key in data) {
                    $('#territory').append('<option value="' + data[key].id + '">' + data[key].name + '</option>');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText)
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    function loadDP(id) {
        if (!id) {
            MessageRenderer.render({
                "messageBody": "Select Territory!",
                "messageTitle": "Withdraw Security Deposit",
                "type": "2"
            });
            return false;
        }
        else {
            var actionUrl = "${request.contextPath}/${params.controller}/getDpListByTerritory";
            SubmissionLoader.showTo();
            jQuery.ajax({
                type: 'post',
                data: {territoryId: id, enterpriseConfiguration: $('#idEnterprise').val()},
                url: actionUrl,
                success: function (data, textStatus) {
                    clearData();
                    $('#distributionPoint option').remove();
                    $('#distributionPoint').append('<option value="">' + " - Select DP - " + '</option>');
                    for (key in data) {
                        $('#distributionPoint').append('<option value="' + data[key].id + '">' + data[key].name + '</option>');
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText)
                },
                complete: function () {
                    SubmissionLoader.hideFrom();
                },
                dataType: 'json'
            });
        }
    }

    function loadDpDefaultCustomer(id) {
        if (id == '') {
            clearData();
            clearTp();
            $('#gridTP').hide();
            return false
        }
        var actionUrl = "${request.contextPath}/${params.controller}/getDpDefaultCustomer";
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {dpId: id, date: $('#asOfDate').val()},
            url: actionUrl,
            success: function (data, textStatus) {
                clearData();
                if (data == 0) {
                    MessageRenderer.render({
                        "messageBody": "No default customer is found!",
                        "messageTitle": "Security Deposit",
                        "type": "0"
                    });
                    return false;
                } else {
                    $('#defaultCustomer').val(data.customerName);
                    $('#defaultCustomerId').val(data.default_customer_id);
                    $('#defaultCustomerCode').val(data.code);

                    //getOtherCustomersSd(data.default_customer_id);
                    $('#defaultArea').show();
                    if ($('#defaultCustomerId').val()) {
                        loadDpDefaultCustomersTp(data.default_customer_id)
                    }
                    $('#availableDPSecurityDeposit').val(data.totalAvailableSd);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText)
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    function loadDpDefaultCustomersTp(id) {
        var dpId = $("#distributionPoint").val();
        var actionUrl = "${request.contextPath}/${params.controller}/getDpDefaultCustomersTp";
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {dpId:dpId, asOfDate: $('#asOfDate').val()},
            url: actionUrl,
            success: function (data, textStatus) {
                if (data == 0) {
                    $('#gridTP').hide();
                    MessageRenderer.render({
                        "messageBody": "No Available Trade Partners!",
                        "messageTitle": "Security Deposit",
                        "type": "0"
                    });
                    return false;
                } else {
                    clearTp();
                    loadTpsGrid(data);
                    $('#gridTP').show();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText)
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    function loadTpsGrid(data) {
        $("#jqgrid-tp-grid").jqGrid({
            datatype: "local",
            data: data,
            colNames: [
                'SI',
                '',
                'Customer Name',
                'Customer ID',
                'ID',
                'Security Deposit Balance',
                'Receivable Amount',
                'Security Deposit Balance After Withdrawal'
            ],
            colModel: [
                {name: 'si', index: 'si', width: 30},
                {name: 'select', index: 'select', width: 30},
                {name: 'customer_name', index: 'customer_name', width: 130},
                {name: 'code', index: 'code', width: 110},
                {name: 'customer_master_id', index: 'customer_master_id', hidden: true},
                {name: 'amount', index: 'amount', width: 160},
                {name: 'receivableAmount', index: 'receivableAmount', width: 130},
                {name: 'amount', index: 'amount', width: 210}
            ],
            rowNum: 10,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-tp-pager',
            sortname: 'customer_name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Available Trade Partners",
            autowidth: true,
            height: 120,
//            footerrow:true,
            scrollOffset: 0,
            loadComplete: function () {
                var totalAmount = 0;
                var ids = jQuery("#jqgrid-tp-grid").jqGrid('getDataIDs');
                for (key in ids) {
                    var id = $("#jqgrid-tp-grid").getCell(ids[key], 'customer_master_id');
                    var amount = $("#jqgrid-tp-grid").getCell(ids[key], 'amount');

                    totalAmount += parseFloat(amount);

                    $("#jqgrid-tp-grid").jqGrid('setRowData', ids[key], {
                        si: ids[key],
                        select: '<input name="tp-select" type="radio" id="tp-pool-radio-' + id + '" class="tp-pool-radio" val="' +
                        amount + '" onclick="getTotalDepositAmount(' + id + ',' + amount + ',' + ids[key] + ');" />'
                    });
                }

                $('#totalAvailableSD').val(totalAmount.toFixed(2));
            },
            onSelectRow: function (rowid, status) {
                var id = $("#jqgrid-tp-grid").getCell(rowid, 'customer_master_id');
                var amount = $("#jqgrid-tp-grid").getCell(rowid, 'amount');
                var receivableAmount = $("#jqgrid-tp-grid").getCell(rowid, 'receivableAmount')
                if (receivableAmount) {
                    $('#receivableAmount').val(receivableAmount)
                }
                else {
                    $('#receivableAmount').val(0)
                }
                $('#customerMaster').val(id)
                if (amount) {
                    $('#securityDepositBalance').val(amount)
                }
                else {
                    $('#securityDepositBalance').val(0)
                }
                $('#tp-pool-radio-' + id).attr('checked', true);
                getTotalDepositAmount(id, amount, rowid);
            }
        });
        $("#jqgrid-tp-grid").jqGrid('navGrid', '#jqgrid-tp-pager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });

        jQuery("#jqgrid-tp-grid")
                .jqGrid('setGridParam',
                {
                    datatype: 'local',
                    data: data
                })
                .trigger("reloadGrid");
    }

    function getTotalDepositAmount(id, amount, rowId) {
        if ($('#tp-pool-radio-' + id).is(':checked')) {
            $('#tpId').val(id);
            setCustomerValues(id, amount, rowId)
        }
    }

    function withdrawSecurityDeposit() {
        if (!$('#tpId').val()) {
            MessageRenderer.render({
                messageTitle: 'Security Deposit',
                type: 0,
                messageBody: 'Please select a trade partner first.'
            });
            return false;
        }

//        if (!executePreConditionBankBranch()) {
//            return false;
//        }
        var actionUrl = null;

        actionUrl = "${request.contextPath}/${params.controller}/create";

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormCashTroughBank").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionWithdrawSecurityDeposit(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status = 0) {
                    $("#gridTP").trigger("reloadGrid");
                    $("#withdrawalAmount").val("");
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else {
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
        return false;
        %{--var url = '${resource(dir:'viewAndEditSecurityDeposit', file:'popupDefaultCustomerListPanel')}';--}%
        %{--var params = {customerId:$('#tpId').val(), date:$('#asOfDate').val()};--}%
        //alert('Under Development!');
    }


    function executePostConditionWithdrawSecurityDeposit(result) {

        if (result.type == 1) {
            //$("#jqgrid-tp-grid").trigger("reset");
            $("#withdrawalAmount").val("");
            $('#gFormCashTroughBank').trigger("reset");
            $("#jqgrid-tp-grid").jqGrid('clearGridData');

        }
        MessageRenderer.render(result);
    }


    function setCustomerValues(rowid, amount, rowid) {
        var id = $("#jqgrid-tp-grid").getCell(rowid, 'customer_master_id');
        var amount = $("#jqgrid-tp-grid").getCell(rowid, 'amount');
        var receivableAmount = $("#jqgrid-tp-grid").getCell(rowid, 'receivableAmount')
        if (receivableAmount) {
            $('#receivableAmount').val(receivableAmount)
        }
        else {
            $('#receivableAmount').val(0)
        }
        $('#customerMaster').val(id)
        if (amount) {
            $('#securityDepositBalance').val(amount)
        }
        else {
            $('#securityDepositBalance').val(0)
        }

    }

    function getOtherCustomersSd(cId) {
        var url = '${resource(dir:'viewAndEditSecurityDeposit', file:'getOtherCustomersSd')}';
        var params = {cId: cId, date: $('#asOfDate').val()};
        DocuAjax.json(url, params, function (data) {
            $("#availableDPSecurityDeposit").val(data.securityDeposit ? data.securityDeposit : 0);
        });
    }

    var SelectIsCashPayment = function () {
        $('#bankRef').hide();
        $('#lblBankRef').hide();
    }
    var SelectIsBankPayment = function () {
        $('#bankRef').show();
        $('#lblBankRef').show();
    }
</script>