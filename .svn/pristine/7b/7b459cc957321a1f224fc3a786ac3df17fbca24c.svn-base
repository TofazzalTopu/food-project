<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormMushak").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormMushak").validationEngine('attach');
//        reset_form("#gFormMushak");
        $("#jqgrid-grid-mushak").jqGrid({
            url: '${resource(dir:'mushak', file:'listDetails')}?id=' + ${mushak.id},
            datatype: "json",
            colNames: [
                'ID',
                'Product ID',
                'Product Name',
                'Quantity',
                'Unit Price',
                'SD Imposable Price',
                'SD Percentage',
                'SD Amount',
                'VAT Imposable Price',
                'VAT Percentage',
                'VAT Amount',
                'Total Price',
                ''
            ],
            colModel: [
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'productId', index: 'productId', width: 0, hidden: true},
                {name: 'finishProduct', index: 'finishProduct', width: 150, align: 'left'},
                {
                    name: 'quantity', index: 'quantity', width: 70, align: 'left', sorttype: 'number',
                    formatter: "number",
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 0},
                    editable: true,
                    editrules: {number: true}
                },
                {name: 'unitPrice', index: 'unitPrice', width: 0, align: 'left', hidden: true},
                {name: 'sdImposable', index: 'sdImposable', width: 100, align: 'right'},
                {name: 'sdPercent', index: 'sdPercent', width: 0, hidden: true},
                {name: 'sdAmount', index: 'sdAmount', width: 80, align: 'right'},
                {name: 'vatImposable', index: 'vatImposable', width: 100, align: 'right'},
                {name: 'vatPercent', index: 'vatPercent', width: 0, hidden: true},
                {name: 'vatAmount', index: 'vatAmount', width: 80, align: 'right'},
                {name: 'totalPrice', index: 'totalPrice', width: 100, align: 'right'},
                {name: 'delete', index: 'delete', width: 40, align: 'center'}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-mushak',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Product Price List",
            width: 795,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            cellEdit: true,
            cellsubmit: 'clientArray',
            cellurl: null,
            afterSaveCell: function (id, name, val, iRow, iCol) {
                calculatePrice(id);
            },
            loadComplete: function () {
//                calculateTotal();
            },
            onSelectRow: function (rowid, status) {
            }
        });

        $('#challanHandoverDate,#deliveryDate').datepicker({
            dateFormat: 'dd-mm-yy',
            changeMonth: true,
            changeYear: true
        });
        $('#challanHandoverDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#deliveryDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#challanHandoverTime').timepicker({
            timeFormat: 'hh:mm tt',
            ampm: true
        });

        loadData();
    });

    function loadData(){
        $("#create").remove();
        $("#create-button-mushak").val('Update');
        $("#deleteSpan").removeAttrs('hidden');
        $("#search-btn-customer-invoice-id").remove();
        $("#searchInvoiceKey").addClass('width170');
        $("#searchInvoiceKey").val('${mushak.invoice.code}');
        $("#searchInvoiceKey").attr('disabled', true);
        $("#invoice").val(${mushak.invoice.id});
        $("#id").val(${mushak.id});
        $("#version").val(${mushak.version});
        $("#deliveryDate").val('${mushak.deliveryDate.format('dd-MM-yyyy')}');
        $("#challanHandoverDate").val('${mushak.challanHandoverDate.format('dd-MM-yyyy')}');
    }

    function calculatePrice(id) {
        var rowData = $("#jqgrid-grid-mushak").jqGrid('getRowData', id);
        rowData.sdImposable = parseFloat(rowData.unitPrice) * parseFloat(rowData.quantity);
        rowData.sdAmount = rowData.sdImposable * parseFloat(rowData.sdPercent) / 100;
        rowData.vatImposable = rowData.sdAmount + rowData.sdImposable;
        rowData.vatAmount = rowData.vatImposable * parseFloat(rowData.vatPercent) / 100;
        rowData.totalPrice = rowData.vatImposable + rowData.vatAmount;
        $('#jqgrid-grid-mushak').jqGrid('setRowData', id, rowData);
        calculateTotal();
    }

    function calculateTotal(){
        var col = $("#jqgrid-grid-mushak").jqGrid('getCol', 'totalPrice');
        var total = 0;
        for(var i = 0; i < col.length; i++){
            if(col[i]) {
                total = total + parseFloat(col[i]);
            }
        }
        $("#totalMushakAmount").val(total);
    }

    function deleteItemFromGrid(id) {
        $("#dialog").dialog("destroy");
        $("#dialog-confirm-mushak").dialog({
            resizable: false,
            height: 150,
            modal: true,
            title: 'Delete alert',
            buttons: {
                'Delete Item': function () {
                    $(this).dialog('close');
                    var myGrid = $("#jqgrid-grid-mushak");
                    myGrid.jqGrid('delRowData', id);
                    calculateTotal();
                    SubmissionLoader.showTo();
                    jQuery.ajax({
                        type: 'post',
                        url: '${resource(dir:'mushak', file:'deleteDetail')}?id=' + id + '&mushakId=' + ${mushak.id} + '&amount=' + $("#totalMushakAmount").val(),
                        success: function (data, textStatus) {
                            MessageRenderer.render(data);
                            $("#jqgrid-grid-mushak").trigger("reloadGrid");
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            if(XMLHttpRequest.status = 0){
                                MessageRenderer.renderErrorText("Network Problem: Time out");
                            }else{
                                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                            }
                        },
                        complete: function (data) {
                            calculateTotal();
                            SubmissionLoader.hideFrom();
                        },
                        dataType: 'json'
                    });
                },
                Cancel: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    function executePreConditionMushak() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormMushak").validationEngine('validate')) {
            return false;
        }
        return true;
    }

    function executeAjaxMushak(){
        if (!executePreConditionMushak()) {
            return false;
        }
        var data = $("#gFormMushak").serializeArray();
        var gd = $("#jqgrid-grid-mushak").jqGrid('getRowData');
        var length = gd.length;
        for (var i = 0; i < length; i++) {
            data.push({'name': 'items.mushakDetails[' + i + '].id', 'value': gd[i].id});
            data.push({'name': 'items.mushakDetails[' + i + '].quantity', 'value': gd[i].quantity});
            data.push({'name': 'items.mushakDetails[' + i + '].sdAmount', 'value': gd[i].sdAmount});
            data.push({'name': 'items.mushakDetails[' + i + '].vatAmount', 'value': gd[i].vatAmount});
            data.push({'name': 'items.mushakDetails[' + i + '].totalAmount', 'value': gd[i].totalPrice});
        }

        var actionUrl = "${request.contextPath}/${params.controller}/update";
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: actionUrl,
            success: function (data, textStatus) {
                if (data.type == 1) {
                    $('#popEmpDetails').html('');
                    $("#jqgrid-grid-mushak-update").trigger("reloadGrid");
                }
                MessageRenderer.render(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $('#popEmpDetails').html('');
                    $("#jqgrid-grid-mushak-update").trigger("reloadGrid");

                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }
                else{
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

    function deleteAjaxMushak(){
        $("#dialog").dialog("destroy");
        $("#dialog-confirm-mushak").dialog({
            resizable: false,
            height: 150,
            modal: true,
            title: 'Delete alert',
            buttons: {
                'Delete Item': function () {
                    $(this).dialog('close');
                    jQuery.ajax({
                        type: 'post',
                        url: '${resource(dir:'mushak', file:'delete')}?id=' + ${mushak.id},
                        success: function (data, textStatus) {
                            $('#popEmpDetails').html('');
                            $("#jqgrid-grid-mushak-update").trigger("reloadGrid");
                            MessageRenderer.render(data);
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            if(XMLHttpRequest.status = 0){
                                MessageRenderer.renderErrorText("Network Problem: Time out");
                            }else{
                                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                            }
                        },
                        complete: function (data) {
                        },
                        dataType: 'json'
                    });
                },
                Cancel: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

</script>