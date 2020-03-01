<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        // just for the demos, avoids form submit
        jQuery.validator.setDefaults({
            debug: true,
            success: "valid"
        });

        selectDistributionPoint();
        selectExpenditureHeads();

        var idValue = document.getElementById("distributionPoint").value;
        if (idValue) {
            selectCashPool(idValue);
            fetchAvailableCash(idValue);
        }
        //For Amount Varification
        $("#expenseAmount").keypress(function (event) {
            if ((event.which != 46 || $(this).val().indexOf('.') != -1) &&
                    ((event.which < 48 || event.which > 57) &&
                            (event.which != 0 && event.which != 8))) {
                event.preventDefault();
            }
        });

        $("#gFormExpenseFromDPCashPool").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormExpenseFromDPCashPool").validationEngine('attach');
        //reset_form("#gFormExpenseFromDPCashPool");
        $("#jqgrid-grid-expenseFromDPCashPool").jqGrid({
            url: '${resource(dir:'expenseFromDPCashPool', file:'list')}',
            postData: {dpId: idValue},
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Distribution Point',
                'Cash Pool',
                'Expenditure Heads',
                'Expense Amount',
                'Transaction No',
                'Date',
                'Remarks'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: true, align: 'center'},
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'distributionPoint', index: 'distributionPoint', width: 80, align: 'left'},
                {name: 'cashPool', index: 'cashPool', width: 100, align: 'left'},
                {name: 'expenditureHeads', index: 'expenditureHeads', width: 100, align: 'left'},
                {name: 'expenseAmount', index: 'expenseAmount', width: 70, align: 'right', formatter: 'number', formatoptions: {thousandsSeparator: ","}},
                {name: 'transactionNo', index: 'transactionNo', width: 90, align: 'center'},
                {name: 'dateCreated', index: 'dateCreated', width: 120, align: 'center', formatter: 'date', formatoptions: {srcformat: 'Y-m-d H:i:sO', newformat: 'd-m-Y g:i A'}},
                {name: 'remarks', index: 'remarks', width: 100, align: 'left'}
            ],
            rowNum: 20,
            rowList: [10, 20, 30, 50, -1],
            pager: '#jqgrid-pager-expenseFromDPCashPool',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Expense From DPC ash Pool List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            altRows: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
            }
        });
    });

    function getSelectedExpenseFromDPCashPoolId() {
        var expenseFromDPCashPoolId = null;
        var rowid = $("#jqgrid-grid-expenseFromDPCashPool").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            expenseFromDPCashPoolId = $("#jqgrid-grid-expenseFromDPCashPool").jqGrid('getCell', rowid, 'id');
        }
        if (expenseFromDPCashPoolId == null) {
            expenseFromDPCashPoolId = $('#gFormExpenseFromDPCashPool input[name = id]').val();
        }
        return expenseFromDPCashPoolId;
    }

    function executePreConditionExpenseFromDPCashPool() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormExpenseFromDPCashPool").validationEngine('validate')) {
            return false;
        }
        return true;
    }

    function executeAjaxExpenseFromDPCashPool() {
        if (!executePreConditionExpenseFromDPCashPool()) {
            return false;
        }
        var expenseAmount = Number($("#expenseAmount").val());
        var availableCash = Number($("#availableCash").val());
        if (expenseAmount > availableCash) {
            MessageRenderer.renderErrorText("Cash Pool does not have Available Cash");
            return false;
        }
        var actionUrl = "${request.contextPath}/${params.controller}/create";
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormExpenseFromDPCashPool").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionExpenseFromDPCashPool(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status = 0) {
                    $("#jqgrid-grid-expenseFromDPCashPool").trigger("reloadGrid");
                    reset_form('#gFormExpenseFromDPCashPool');
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }
                else {
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
    function executePostConditionExpenseFromDPCashPool(result) {
        if (result.type == 1) {
            $("#jqgrid-grid-expenseFromDPCashPool").trigger("reloadGrid");
            reset_form('#gFormExpenseFromDPCashPool');
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
        $('#create-button-expenseFromDPCashPool').show();
    }

    function trim_form() {
        $(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            this.value = $.trim(this.value);
        });
    }

    function selectExpenditureHeads() {
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'expenseFromDPCashPool', file:'fetchExpenditureHeadsList')}?",
            success: function (data) {
                options = '<option value="">Select Expenditure Head</option>';
                $.each(data, function (key, val) {
                    options += '<option value="' + val.id + '">' + val.chartOfAccountName + '</option>';
                });
                $("#expenditureHeads").html(options);
            },

            dataType: 'json'
        });
    }

    function selectCashPool(id) {
        if (id == '') {
            var options = '<option value="">Select Cash Pool</option>';
            $("#cashPool").html(options);
            return false;
        }

        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'expenseFromDPCashPool', file:'fetchCashPoolList')}?id=" + id,
            success: function (data) {
                var options = '<option value="">Select Cash Pool</option>';
                $.each(data, function (key, val) {
                    options += '<option value="' + val.id + '">' + val.name + '</option>';
                });
                $("#cashPool").html(options);
            },

            dataType: 'json'
        });
    }

    function selectDistributionPoint() {

        var size;
        var options = '';
        if ('${disList}' != '') {
            size = ${distSize};
            if (size == 1) {
                options = '<option value="' + ${disList}[0].id + '">' + ${disList}[0].dpName + '</option>';
            } else {
                options = '<option value="">Please Select</option>';
                for (var i = 0; i < size; i++) {
                    options += '<option value="' + ${disList}[i].id + '">' + ${disList}[i].dpName + '</option>';
                }
            }
            $("#distributionPoint").html(options);
        }
    }

    function fetchAvailableCash(cashPoolId) {
        $("#availableCash").val("0");
        var dpId = document.getElementById('distributionPoint').value;
        if (cashPoolId) {
            jQuery.ajax({
                type: 'post',
                //Change For Expense for DP CashPool
                url: "${resource(dir:'customerPayment', file:'fetchCashPoolBalanceForCashInHandFromCOA')}?dpId=" + dpId,

                success: function (data, textStatus) {
                    $("#availableCash").val(data[0].amountBalance);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if (XMLHttpRequest.status = 0) {
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    } else {
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete: function () {
                },
                dataType: 'json'
            });
        }
    }
</script>