<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $("#asOfDate").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $('#asOfDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#territory').focus();
        $('#gridTP').hide();
        $('#defaultArea').hide();
    });

    function clearData() {
        $('#dpId').val('');
        $('#interest').val('');
        $('#quarter').val('');
        $('#lastQob').val('');
        $('#nextQob').val('');
        $('#defaultCustomer').val('');
        $('#defaultCustomerId').val('');
        $('#defaultCustomerCode').val('');
        $('#availableDPSecurityDeposit').val('');
        $('#gridTP').hide();
        $('#defaultArea').hide();
    }

    function clearAll() {
        $('#territory').val('');
        $('#distributionPoint').val('');
        $('#dpId').val('');
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
                if (XMLHttpRequest.status = 0) {
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
    }

    function loadDP(id) {
        if (!id) {
            MessageRenderer.render({
                "messageBody": "Select Territory!",
                "messageTitle": "Security Deposit",
                "type": "2"
            });
            return false;
        }
        else {
            var actionUrl = "${request.contextPath}/${params.controller}/getDpListByTerritory";
            SubmissionLoader.showTo();
            jQuery.ajax({
                type: 'post',
                data: {territoryId: id},
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
                    if (XMLHttpRequest.status = 0) {
                        $('#distributionPoint option').remove();
                        $('#distributionPoint').append('<option value="">' + " - Select DP - " + '</option>');
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
            data: {dpId: id, date: $('#quarter').val()},
            url: actionUrl,
            success: function (data, textStatus) {
                clearData();
//                debugger
                if (data == 0) {
                    MessageRenderer.render({
                        "messageBody": "No default customer is found!",
                        "messageTitle": "Security Deposit",
                        "type": "0"
                    });
                    return false;
                } else {
                    $('#dpId').val(data.dpCode);
                    $('#defaultCustomer').val(data.customerName);
                    $('#defaultCustomerId').val(data.default_customer_id);
                    $('#defaultCustomerCode').val(data.code);
//                    $('#availableDPSecurityDeposit').val(data.securityDeposit);
                    $('#quarter').attr('disabled', false);
                    $('#defaultArea').show();
                    if ($('#defaultCustomerId').val()) {
                        loadDpDefaultCustomersTp(data.default_customer_id)
                    }
                    else {
                        $('#quarter').attr('disabled', true);

                    }
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status = 0) {
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
    }

    function loadDpDefaultCustomersTp(id) {
        var actionUrl = "${request.contextPath}/${params.controller}/getDpDefaultCustomersTp";
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {customerId: id, date: $('#quarter').val()},
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
                if (XMLHttpRequest.status = 0) {
                    clearTp();
                    loadTpsGrid(data);
                    $('#gridTP').show();
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
    }

    var calc = false;
    function loadTpsGrid(data,q) {
        if(q == 'c'){
            calc = true;
        }else{
            calc = false;
        }

        if (data == 0) {
            $("#jqgrid-tp-grid").jqGrid("clearGridData", true).trigger("reloadGrid");
            return false;
        }else{
            $("#jqgrid-tp-grid").jqGrid("clearGridData", true).trigger("reloadGrid");
        }

        $("#jqgrid-tp-grid").jqGrid({
            datatype: "local",
            data: data,
            colNames: [
                'SI',
                'Customer Name',
                'ID',
                'Customer ID',
                'Last Quarter Closing Balance',
                'Next Quarter Opening Balance',
                'Interest Amount'
            ],
            colModel: [
                {name: 'si', index: 'si', width: 30},
                {name: 'customer_name', index: 'customer_name', width: 130},
                {name: 'customer_master_id', index: 'customer_master_id', hidden: true},
                {name: 'code', index: 'code', width: 110},
                {name: 'lastQob', index: 'lastQob', width: 200},
                {name: 'nextQob', index: 'nextQob', width: 200},
                {name: 'interestAmount', index: 'interestAmount'}
            ],
            rowNum: 10,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-tp-pager',
            sortname: 'customer_name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Trade Partners Security Deposit:",
            autowidth: false,
            height: 120,
//            footerrow:true,
            scrollOffset: 0,
            loadComplete: function () {
                var ids = jQuery("#jqgrid-tp-grid").jqGrid('getDataIDs');
                for (key in ids) {
                    $("#jqgrid-tp-grid").jqGrid('setRowData', ids[key], {
                        si: ids[key]
                    });
                }

                if ($('#quarter').val() && $('#interest').val() && calc == true) {
                    var lastBalance = 0;
                    var nextBalance = 0;
                    var interest = parseFloat($('#interest').val());

                    for (key in ids) {
                        var id = $("#jqgrid-tp-grid").getCell(ids[key], 'customer_master_id');
                        var lastAmount = $("#jqgrid-tp-grid").getCell(ids[key], 'lastQob');
                        if (lastAmount) {
                            lastAmount = parseFloat(lastAmount);
                        } else {
                            lastAmount = 0;
                        }
                        var interestAmount = lastAmount * ((interest / 4) / 100);
                        var nextAmount = interestAmount + lastAmount;

                        lastBalance += parseFloat(lastAmount);
                        $("#jqgrid-tp-grid").jqGrid('setRowData', ids[key], {
                            lastQob: lastAmount.toFixed(2),
                            nextQob: nextAmount.toFixed(2),
                            interestAmount: interestAmount.toFixed(2)
                        });
                    }

                    nextBalance = lastBalance * ((interest / 4) / 100) + lastBalance;
                    $('#lastQob').val(lastBalance.toFixed(2));
                    $('#nextQob').val(nextBalance.toFixed(2));
                }
            },
            onSelectRow: function (rowid, status) {

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


    function checkInterestCreated() {
        if ($('#isAlreadyCreated').val() == 'true') {
            MessageRenderer.render({
                "messageBody": "Security Deposit Interest Already Calculated!",
                "messageTitle": "Security Deposit Interest",
                "type": "0"
            });
            return true;
        }

        if ($('#isLasQuarterCalculated').val() == 'false') {
            MessageRenderer.render({
                "messageBody": "Please Process the Last Quarter First!",
                "messageTitle": "Security Deposit Interest",
                "type": "0"
            });
            return true;
        }

        if ($('#interest').val() == '') {
            MessageRenderer.render({
                "messageBody": "Please Enter Interest % Per Year.",
                "messageTitle": "Security Deposit Interest",
                "type": "0"
            });
            return true;
        }

        if ($('#quarter').val() == '') {
            MessageRenderer.render({
                "messageBody": "Please Select Quarter to Calculate.",
                "messageTitle": "Security Deposit Interest",
                "type": "0"
            });
            return true;
        }
    }

    function calculateSdInterest() {
        if (checkInterestCreated()) {
            return false;
        }
        checkInterest($('#quarter').val(),'c');
        /*
        var actionUrl = "${request.contextPath}/${params.controller}/getCalculatedInterestByQuarter";
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {customerId: $('#defaultCustomerId').val(), quarter: $('#quarter').val()},
            url: actionUrl,
            success: function (data, textStatus) {
                if (data == 0) {
//                    $('#gridTP').hide();
                    MessageRenderer.render({
                        "messageBody": "No Available Trade Partners!",
                        "messageTitle": "Security Deposit",
                        "type": "0"
                    });
                    loadTpsGrid(data);
                } else {
                    clearTp();
                    loadTpsGrid(data);
//
//                    $('#gridTP').show();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status = 0) {
                    clearTp();
                    loadTpsGrid(data);
                    checkInterest($('#quarter').val());
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

        */
    }

    function saveSdInterest() {
        if (checkInterestCreated()) {
            return false;
        }

        var data = {};
        var defaultCustomerId = $("#defaultCustomerId").val();
        if (!defaultCustomerId) {
            MessageRenderer.renderErrorText("Default Customer is not available");
            return
        }
        data["defaultCustomerId"] = defaultCustomerId;

        var lastQob = Number($("#lastQob").val()).toFixed(2);
        var nextQob = Number($("#nextQob").val()).toFixed(2);

        if (lastQob != 0 && nextQob != 0) {
            if (nextQob >= lastQob) {
                data["interestAmount"] = nextQob - lastQob;
            } else {
                MessageRenderer.renderErrorText("Next Quarter Balance cannot less than Last Quarter Balance");
                return
            }
        } else {
            MessageRenderer.renderErrorText("Last Quarter Balance and Next Quarter Balance cannot 0");
            return
        }

        var ids = jQuery("#jqgrid-tp-grid").jqGrid('getDataIDs');
        data['interestPercentage'] = $('#interest').val();
        data['quarter'] = $('#quarter').val();
        for (key in ids) {
            data['interest.sd[' + ids[key] + '].customerId'] = $("#jqgrid-tp-grid").getCell(ids[key], 'customer_master_id');
            data['interest.sd[' + ids[key] + '].interestAmount'] = $("#jqgrid-tp-grid").getCell(ids[key], 'interestAmount');
            data['interest.sd[' + ids[key] + '].lastQuarterBalance'] = $("#jqgrid-tp-grid").getCell(ids[key], 'lastQob');
        }

        if (ids) {
            var actionUrl = "${request.contextPath}/${params.controller}/create";
            SubmissionLoader.showTo();
            jQuery.ajax({
                type: 'post',
                data: data,
                url: actionUrl,
                success: function (data, textStatus) {
                    if (data.type == 1) {
//                        $("#jqgrid-tp-grid").jqGrid("clearGridData", true).trigger("reloadGrid");
                        clearTp();
                        clearAll();
                        MessageRenderer.render(data)
                    } else {
                        MessageRenderer.render({
                            "messageBody": "Security Deposit Interest not Created!",
                            "messageTitle": "Security Deposit Interest",
                            "type": "0"
                        });
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if (XMLHttpRequest.status = 0) {
                        clearTp();
                        clearAll();
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
        } else {
            MessageRenderer.render({
                "messageBody": "Trade Partners Security Deposit Not Found!",
                "messageTitle": "Security Deposit Interest",
                "type": "0"
            });
        }
    }

    function checkInterest(id,q) {
        if (!id) {
            MessageRenderer.render({
                "messageBody": "Please Select Quarter !",
                "messageTitle": "Security Deposit Interest",
                "type": "2"
            });
            return false
        }
//        var date = new Date();
        var currentMonth = new Date().getMonth()+1;
        var msg = '';
        if(id=='1'){
            if(currentMonth < 1){
                msg = 'This quarter(Q-1) is not applicable yet!'
            }
        }else if(id=='2'){
            if(currentMonth < 4){
                msg = 'This quarter(Q-2) is not applicable yet!'
            }
        }else if(id=='3'){
            if(currentMonth < 7){
                msg = 'This quarter(Q-3) is not applicable yet!'
            }
        }else if(id=='4'){
            if(currentMonth < 10){
                msg = 'This quarter(Q-4) is not applicable yet!'
            }
        }

        if(msg){
            MessageRenderer.render({
                "messageBody": msg,
                "messageTitle": "Security Deposit Interest",
                "type": "2"
            });
            return false
        }

        var data = {};
        var ids = jQuery("#jqgrid-tp-grid").jqGrid('getDataIDs');

        data['quarter'] = id;
        data['customerId'] = $('#defaultCustomerId').val();

        var actionUrl = "${request.contextPath}/${params.controller}/getCalculatedInterestByQuarter";
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: actionUrl,
            success: function (data, textStatus) {
//                console.log(data);
                loadTpsGrid(data.customerList,q)
                if (data.isCalculated > 0) {
                    $('#isAlreadyCreated').val(true);
//                    $('#lastQob').val(data.lastQuarterBalance);
//                    MessageRenderer.render(data)
                    MessageRenderer.render({
                        "messageBody": "Security Deposit Interest Already Calculated!",
                        "messageTitle": "Security Deposit Interest",
                        "type": "0"
                    });
                } else {
                    $('#isAlreadyCreated').val('');
                    if (data.isLasQuarterCalculated > 0) {
                        $('#isLasQuarterCalculated').val(true);
//                        $('#lastQob').val(data.lastQuarterBalance);
                    } else {
                        $('#isLasQuarterCalculated').val(false);
                        MessageRenderer.render({
                            "messageBody": "Please Process the Last Quarter First!",
                            "messageTitle": "Security Deposit Interest",
                            "type": "0"
                        });
                    }
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status = 0) {
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
    }
</script>