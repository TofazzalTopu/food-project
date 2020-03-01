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
        var idValue = document.getElementById("distributionPoint").value;

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
        $("#jqgrid-grid-expenseFromDPCashPool").jqGrid({
            url: '${resource(dir:'expenseFromDPCashPool', file:'listExpenseRollback')}',
            postData: {dpId: idValue},
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Transaction Number',
                'Cash Pool',
                'Expenditure Heads',
                'Expense Amount',
                'Remarks',
                'Expense Date',
                'Cancel Expense'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: true, align: 'center'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'transactionNo', index: 'transactionNo', width: 100, align: 'center'},
                {name: 'cashPool', index: 'cashPool', width: 100, align: 'left'},
                {name: 'expenditureHeads', index: 'expenditureHeads', width: 100, align: 'left'},
                {name: 'expenseAmount', index: 'expenseAmount', width: 100, align: 'right', formatter: 'number', formatoptions: {thousandsSeparator: ","}},
                {name: 'remarks', index: 'remarks', width: 100, align: 'left'},
                {name: 'dateCreated', index: 'dateCreated', width: 100, align: 'center', formatter: 'date', formatoptions: {srcformat: 'Y-m-d H:i:sO', newformat: 'd-m-Y g:i A'}},
                {name: 'cancel', index: 'cancel', width: 80, align: 'left'}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-expenseFromDPCashPool',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Expense From DPC ash Pool List",
            autowidth: true,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            loadComplete: function (data) {
                var $grid = $("#jqgrid-grid-expenseFromDPCashPool");
                $.each(data.rows, function (i, item) {
                    var rowData = $grid.jqGrid('getRowData', data.rows[i].id);
                    rowData.cancel = '<input type="button" name="receive-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Cancel" onclick="cancelExpense(' + data.rows[i].id + ',\'' + rowData.transactionNo + '\');" />';
                    $grid.jqGrid('setRowData', data.rows[i].id, rowData);
                });
            },
            onSelectRow: function (rowid, status) {
            }
        });
    });

    function cancelExpense(id, transactionNo) {
        $("#cancelExpenseMsg").html("System will permanently delete all record set about the transaction no <b>" + transactionNo + "</b>. Do you really want to proceed to cancel payment?");
        $("#dialog-confirm-expenseFromDPCashPool").dialog({
            resizable: false,
            height: 150,
            modal: true,
            title: 'Confirm to Cancel Expense',
            buttons: {
                Yes: function () {
                    $(this).dialog('close');
                    SubmissionLoader.showTo();
                    $.ajax({
                        url: '${application.contextPath}/${params.controller}/cancelExpense',
                        type: "POST",
                        data: {id: id, transNo: transactionNo},
                        dataType: "json",
                        beforeSend: function () {
                        },
                        success: function (result) {
                            if (result.type == 1) {
                                $("#jqgrid-grid-expenseFromDPCashPool").trigger("reloadGrid");
                            }
                            MessageRenderer.render(result);
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
                        }
                    });
                },
                No: function () {
                    $(this).dialog('close');
                }
            }
        });
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
</script>