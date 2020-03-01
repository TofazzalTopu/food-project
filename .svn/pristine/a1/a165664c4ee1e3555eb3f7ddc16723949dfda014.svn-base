<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormWithdrawCashFromDepositPool").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormWithdrawCashFromDepositPool").validationEngine('attach');
        %{--reset_form("#gFormWithdrawCashFromDepositPool");--}%

        $("#jqgrid-data-grid").jqGrid({
            %{--url: '${request.contextPath}/${params.controller}/listCurrencyDenomination',--}%
            datatype: "json",
            colNames: [
                'Id',
                'Denomination',
                'Qty'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'amount', index: 'amount', width: 100, align: 'left'},
                {name: 'qty', index: 'qty', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Deposited Notes",
            width: 200,
            height:true,
//            footerrow: false,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
            },
            afterSaveCell: function (rowid) {
            }
        });

        setPreviousDatePicker('date');
        $('#date').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

        if ('${list.size()}' == '1') {
            loadDp();
            loadDepositPool();
        }
    });

    function loadDp() {
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'customerPayment', file:'loadDp')}?entId=" + $("#enterprise").val(),
            success: function (data, textStatus) {
                //alert('fd')
                var option = '';
                if (data.length == 1) {
                    //alert('one')
                    option = '<option value="' + data[0].id + '" name="' + data[0].customer + '">' + data[0].name + '</option>';
                }
                else {
                    //alert('more than one')
                    var option = '<option value="">Please Select</option>';
                    for (var i = 0; i < data.length; i++) {
                        option += '<option value="' + data[i].id + '" name="' + data[i].customer + '">' + data[i].name + '</option>';
                    }
//                    $("#distributionPoint").html(option);
                }

                $("#distributionPoint").html(option);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
            },
            complete: function () {
                var dpID = document.getElementById('distributionPoint').value;
                if (dpID) {
                    setDefaultCustomer();
                }

            },
            dataType: 'json'
        });
    }

    function loadDepositPool() {

        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'customerPayment', file:'loadDepositPool')}?entId=" + $("#enterprise").val(),
            success: function (data, textStatus) {
                var option = '';
                if (data.length == 1) {
                    //alert('one')
                    option = '<option value="' + data[0].id + '">' + data[0].name + '</option>';
                }
                else {
                    option = '<option value="">Please Select</option>';
                    for (var i = 0; i < data.length; i++) {
                        option += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                    }
                }
                $("#depositPool").html(option);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
            },
            complete: function () {
            },
            dataType: 'json'
        });
    }

    function setDefaultCustomer() {
        $("#defaultCustomer").val($("#distributionPoint").find(':selected').attr('name'));
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'customerPayment', file:'loadCashPool')}?dpId=" + $("#distributionPoint").val(),
            success: function (data, textStatus) {
                var option = '';
                if (data.length == 1) {
                    option = '<option value="' + data[0].id + '" name="' + data[0].amount + '">' + data[0].name + '</option>';
                } else {
                    option = '<option value="">Please Select</option>';
                    for (var i = 0; i < data.length; i++) {
                        option += '<option value="' + data[i].id + '" name="' + data[i].amount + '">' + data[i].name + '</option>';
                    }
                }

                $("#cashPool").html(option);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
            },
            complete: function () {
                var cashPoolId = $("#cashPool").val();
                if (cashPoolId) {
                    loadCashPoolBalance(cashPoolId);
                }
            },
            dataType: 'json'
        });
    }

    function loadCashPoolBalance(cashPoolId) {
        $("#availableCash").val("0");
        if (cashPoolId) {
            jQuery.ajax({
                type: 'post',
                url: "${resource(dir:'customerPayment', file:'fetchCashPoolBalance')}?cashPoolId=" + cashPoolId,
                success: function (data, textStatus) {
                    $("#availableCash").val(data.amountBalance);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                },
                complete: function () {
                    loadTransactionNo();
                },
                dataType: 'json'
            });
        }
    }

    function loadTransactionNo() {
        $("#jqgrid-data-grid").jqGrid("clearGridData");
        if ($("#cashPool").val() == '' || $("#depositPool").val() == '' || $("#date").val() == '') {
            return false;
        }
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'customerPayment', file:'fetchTransactionNo')}?cashPoolId=" + $("#cashPool").val()
            + "&depositPoolId=" + $("#depositPool").val() + "&date=" + $("#date").val(),
            success: function (data, textStatus) {
                var option = '';
                if (data.length == 1) {
                    option = '<option value="' + data[0].transaction_no + '" name="' + data[0].total + '">' + data[0].transaction_no + '</option>';
                } else {
                    option = '<option value="">Please Select</option>';
                    for (var i = 0; i < data.length; i++) {
                        option += '<option value="' + data[i].transaction_no + '" name="' + data[i].total + '">' + data[i].transaction_no + '</option>';
                    }
                }

                $("#transactionNo").html(option);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
            },
            complete: function () {
                var transactionNo = $("#transactionNo").val();
                if (transactionNo) {
                    loadTransactionAmount();
                    $("#jqgrid-data-grid").setGridParam({
                        url: '${resource(dir:'customerPayment', file:'listDenomination')}?tranNo=' + $("#transactionNo").val()
                    });
                    $("#jqgrid-data-grid").trigger("reloadGrid");
                } else {
                    $("#withdrawAmount").val('0');
                }
            },
            dataType: 'json'
        });
    }

    function loadTransactionAmount() {
        $("#withdrawAmount").val($("#transactionNo").find(':selected').attr('name'));
        $("#jqgrid-data-grid").setGridParam({
            url: '${resource(dir:'customerPayment', file:'listDenomination')}?tranNo=' + $("#transactionNo").val()
        });
        $("#jqgrid-data-grid").trigger("reloadGrid");
    }

    function executePreConditionWithdrawCashFromDepositPool() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormWithdrawCashFromDepositPool").validationEngine('validate')) {
            return false;
        }
        return true;
    }

    function executeAjaxWithdrawCashFromDepositPool() {
        if (!executePreConditionWithdrawCashFromDepositPool()) {
            return false;
        }

        var data = $("#gFormWithdrawCashFromDepositPool").serializeArray();

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: "${resource(dir:'customerPayment', file:'withdrawCashFromDepositPool')}",
            success: function (data, textStatus) {
                executePostConditionWithdrawCashFromDepositPool(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
        return false;
    }

    function executePostConditionWithdrawCashFromDepositPool(result) {
        if (result.type == 1) {
            loadTransactionNo();
            $("#jqgrid-data-grid").jqGrid("clearGridData");
//            reset_form('#gFormWithdrawCashFromDepositPool');
        }
        MessageRenderer.render(result);
    }

    function reset_form(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
        $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
    }

    function trim_form() {
        $(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            this.value = $.trim(this.value);
        });
    }
</script>