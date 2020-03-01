<script type="text/javascript" language="Javascript">

    var showHideGrid = function () {
        if ($("#isDpCustomers").is(":checked")) {
            $('#gridTP').show()
        }
        else {
            $('#gridTP').hide()
        }
    }

    $(document).ready(function () {

        $("#amountAdjusted").format({precision: 2, allow_negative: false, autofix: true});
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
        populateCustomer();
    });

    function clearData() {
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
                if (XMLHttpRequest.status == 0) {
                    $("#gFormAdjustSecurityDeposit").trigger('reset')
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
                "messageTitle": "Adjust Security Deposit with Invoice",
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
                    if (XMLHttpRequest.status == 0) {
                        $("#gFormAdjustSecurityDeposit").trigger('reset')
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
            //$('#gridTP').hide();
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
                    $('#defaultCustomer').val(data.defaultCustomerName);
                    $('#defaultCustomerCode').val(data.defaultCustomerCode);
                    $('#defaultCustomerId').val(data.defaultCustomerId);


                    //getOtherCustomersSd(data.default_customer_id);
                    $('#defaultArea').show();
                    if ($('#defaultCustomerId').val()) {
                        loadDpDefaultCustomersTp(data.defaultCustomerId)
                    }
                    $('#availableDPSecurityDeposit').val(data.defaultCustomerSD_Balance);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status == 0) {
                    $("#gFormAdjustSecurityDeposit").trigger('reset')
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
//            data: {customerId: id, date: $('#asOfDate').val()},
            data: {dpId: $('#distributionPoint').val(), asOfDate: $('#asOfDate').val()},
            url: actionUrl,
            success: function (data, textStatus) {
                if (data == 0) {
                    showHideGrid();
                    MessageRenderer.render({
                        "messageBody": "No Available Trade Partners!",
                        "messageTitle": "Security Deposit",
                        "type": "0"
                    });
                    return false;
                } else {
                    clearTp();
                    loadTpsGrid(data);
                    showHideGrid();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status == 0) {
                    $("#gFormAdjustSecurityDeposit").trigger('reset')
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
        if ($("#isDpCustomers").is(":checked")) {
            $('#gridTP').show()
        }
        else {
            $('#gridTP').hide()
        }
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
                'Security Deposit Balance',
                'Receivable Amount'
            ],
            colModel: [
                {name: 'si', index: 'si', width: 30},
                {name: 'select', index: 'select', width: 30},
                {name: 'customer_name', index: 'customer_name', width: 130},
                {name: 'customer_master_id', index: 'customer_master_id', width: 110, hidden: true},
                {
                    name: 'amount',
                    index: 'amount',
                    width: 160,
                    align: 'right',
                    formatter: 'currency',
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 2}
                },
                {
                    name: 'receivableAmount',
                    index: 'receivableAmount',
                    align: 'right',
                    width: 130,
                    formatter: 'currency',
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 2}
                }
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
                        amount + '" onclick="getTotalDepositAmount(' + id + ',' + amount + ');" />'
                    });
                }

            },
            onSelectRow: function (rowid, status) {
                var id = $("#jqgrid-tp-grid").getCell(rowid, 'customer_master_id');
                var amount = $("#jqgrid-tp-grid").getCell(rowid, 'amount');
                $('#tp-pool-radio-' + id).attr('checked', true);
                getTotalDepositAmount(id, amount);
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

    function getTotalDepositAmount(id, amount) {
        if ($('#tp-pool-radio-' + id).is(':checked')) {
            $('#tpId').val(id);
        }
    }

    function executePreConditionMarketReturn() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormAdjustSecurityDeposit").validationEngine('validate')) {
            return false;
        }
        return true;
    }

    function adjustWithInvoice() {
        if (!executePreConditionMarketReturn()) {
            return false;
        }

        if ($("#isDpCustomers").is(":checked")) {
            if (!$('#tpId').val()) {
                MessageRenderer.render({
                    messageTitle: 'Adjust Security Deposit',
                    type: 0,
                    messageBody: 'Please select a trade partner first.'
                });
                return false;
            }
        }


        else {
            if (!$('#defaultCustomerId').val()) {
                MessageRenderer.render({
                    messageTitle: 'Adjust Security Deposit',
                    type: 0,
                    messageBody: 'Please select distribution point.'
                });
                return false;
            }

        }
        var url = '${resource(dir:'viewAndEditSecurityDeposit', file:'popupDefaultCustomerListPanel')}';
        var params = {customerId: $('#tpId').val(), date: $('#asOfDate').val()};


        var actionUrl = null;

        actionUrl = "${request.contextPath}/${params.controller}/create";
        var isDpCustomer = ""

        if ($("#isDpCustomers").is(":checked")) {
            isDpCustomer = "true"
        }
        else {
            isDpCustomer = "false"
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormAdjustSecurityDeposit").serialize() + '&isDpCustomer=' + isDpCustomer,

            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionAdjustSecurityDeposit(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status == 0) {
                    $("#gFormAdjustSecurityDeposit").trigger('reset')
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

    }

    function getOtherCustomersSd(cId) {
        var url = '${resource(dir:'viewAndEditSecurityDeposit', file:'getOtherCustomersSd')}';
        var params = {cId: cId, date: $('#asOfDate').val()};
        DocuAjax.json(url, params, function (data) {
            $("#availableDPSecurityDeposit").val(data.securityDeposit ? data.securityDeposit : 0);
        });
    }

    function executePostConditionAdjustSecurityDeposit(result) {

        if (result.type == 1) {
            //$("#gFormAdjustSecurityDeposit").trigger('reset')
            $("#jqgrid-tp-grid").setGridParam({
                url: "${request.contextPath}/${params.controller}/getDpDefaultCustomersTp?dpId=" + $('#distributionPoint').val() + "&date=" + $('#asOfDate').val()

            });
            $("#jqgrid-tp-grid").trigger("reloadGrid");

            $("#gFormAdjustSecurityDeposit").trigger('reset')
            //reset_bankBranch_form('#gFormBankBranch');
        }
        MessageRenderer.render(result);
    }

    var SelectIsDpCustomers = function () {

        if ($("#isDpCustomers").is(":checked")) {
            $('#gridTP').show();
            $('#dpRow').attr('hidden', false);
            $('#territoryRow').attr('hidden', false);
            $('#customerRow').attr('hidden', true);
        }
        else {
            $('#gridTP').hide();
            $('#dpRow').attr('hidden', true);
            $('#territoryRow').attr('hidden', true);
            $('#customerRow').attr('hidden', false);
        }

    };

    function populateCustomer() {
        jQuery('#searchKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                var data = {searchKey: request.term, cat: '2'};
                var url = '${resource(dir:'marketReturn', file:'fetchCustomer')}';
                DocuAutoComplete.setSpinnerSelector('searchKey').execute(response, url, data, function (item) {
                    item['label'] = item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                CustomerInfo.setCustomerInformation(ui.item.id, ui.item.value, ui.item.code, ui.item.category);
//                    loadProduct(ui.item.id);
//                    $('#searchProductKey').focus();
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var custometDetailts = "";
            if (item) {
                custometDetailts = '<div style="font-size: 9px; color:#326E93;">' + " Code: " + item.code + ", Name: " + item.name + ", Category: " + item.category + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + custometDetailts).appendTo(ul);
        };

        $('#search-btn-primary-customer-id').click(function () {
            CustomerInfo.popupCustomerListPanel();
        });

    }

    var CustomerInfo = {
        customerCoreInfoId: null,
        popupCustomerListPanel: function () {
            var url = '${resource(dir:'marketReturn', file:'popupCustomerListPanel')}';
            var params = {};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },
        setCustomerInformation: function (customerCoreInfoId, customerCoreInfo, code, category) {
            $("#searchKey").val(customerCoreInfo);
            %{--$("#searchKey").val('${customer.name}');--}%
            $("#primaryCustomer").val(customerCoreInfoId);
            loadCustomerSd(customerCoreInfoId);
            CustomerInfo.customerCoreInfoId = customerCoreInfoId;
        }
    };

    function loadCustomerSd(id) {
        if (id == '') {
            clearData();
            //$('#gridTP').hide();
            return false
        }
        var actionUrl = "${request.contextPath}/${params.controller}/fetchDepositBalance";
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {customerId: id, date: $('#asOfDate').val()},
            url: actionUrl,
            success: function (data, textStatus) {
                clearData();
                if (data == 0) {
                    MessageRenderer.render({
                        "messageBody": "No data found!",
                        "messageTitle": "Security Deposit",
                        "type": "0"
                    });
                    return false;
                } else {
                    $('#defaultCustomer').val(data.name);
                    $('#defaultCustomerCode').val(data.code);
                    $('#defaultCustomerId').val(data.default_customer_id);
                    //getOtherCustomersSd(data.default_customer_id);
                    $('#defaultArea').show();
                    $('#availableDPSecurityDeposit').val(data.totalAvailableSd);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status == 0) {
                    $("#gFormAdjustSecurityDeposit").trigger('reset')
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