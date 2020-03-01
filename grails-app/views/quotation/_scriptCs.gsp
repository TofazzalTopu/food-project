<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormCs").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormCs").validationEngine('attach');
//        reset_form("#gFormOnePercentBonus");

        $("#jqgrid-grid-quotation").jqGrid({
            url: '${resource(dir:'quotation', file:'listQuotationNo')}?entId=' + ${entId},
            datatype: "json",
            colNames: [
                'ID',
                'Select',
                'Quotation No',
                'Customer',
                'added'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 0, hidden: true},
                {
                    name: 'select', width: 50, align: 'center',
                    formatter: cboxFormatterQuotation, formatoptions: {disabled: false},
                    edittype: "checkbox", editoptions: {value: "true:false", defaultValue: "false"},
                    stype: "select", searchoptions: {
                    sopt: ["eq", "ne"],
                    value: ":Any;true:true;false:false"
                }
                },
                {name: 'quotationNo', index: 'quotationNo', width: 150, align: 'left'},
                {name: 'customerName', index: 'customerName', width: 150, align: 'left'},
                {name: 'selected', index: 'selected', width: 0, hidden: true}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-quotation',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Quotation List",
            width: 395,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
            }
        });

        $("#jqgrid-grid-product").jqGrid({
            %{--url: '${resource(dir:'onePercentBonus', file:'list')}',--}%
            datatype: "json",
            colNames: [
                'ID',
                'Select',
                'Product Name',
                'Product Code',
                'added'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 0, hidden: true},
                {
                    name: 'select', width: 50, align: 'center',
                    formatter: cboxFormatterProduct, formatoptions: {disabled: false},
                    edittype: "checkbox", editoptions: {value: "true:false", defaultValue: "false"},
                    stype: "select", searchoptions: {
                    sopt: ["eq", "ne"],
                    value: ":Any;true:true;false:false"
                }
                },
                {name: 'productName', index: 'productName', width: 150, align: 'left'},
                {name: 'productCode', index: 'productCode', width: 150, align: 'left'},
                {name: 'selected', index: 'selected', width: 0, hidden: true}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-product',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Product List",
            width: 395,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
            }
        });

        $("#jqgrid-grid-cs").jqGrid({
            %{--url: '${resource(dir:'onePercentBonus', file:'listCategory')}?entId=' + $("#enterpriseConfiguration").val(),--}%
            datatype: "json",
            colNames: [
                'ID',
                'Product ID',
                'Product Name',
                'Product Code',
                'Quantity',
                'Unit Price',
                'Total Price',
                'Tentative Customer',
                'Quotation No',
                'Quotation Date',
                'Valid From',
                'Valid To'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'productId', index: 'productId', width: 0, hidden: true},
                {name: 'productName', index: 'productName', width: 130, align: 'left'},
                {name: 'productCode', index: 'productCode', width: 150, align: 'left'},
                {name: 'quantity', index: 'quantity', width: 70, align: 'left'},
                {name: 'unitPrice', index: 'unitPrice', width: 50, align: 'right'},
                {name: 'totalPrice', index: 'totalPrice', width: 65, align: 'right'},
                {name: 'customerName', index: 'customerName', width: 130, align: 'left'},
                {name: 'quotationNo', index: 'quotationNo', width: 85, align: 'left'},
                {name: 'quotationDate', index: 'quotationDate', width: 80, align: 'center'},
                {name: 'validFrom', index: 'validFrom', width: 80, align: 'center'},
                {name: 'validTo', index: 'validTo', width: 80, align: 'center'}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-cs',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Category List",
            autowidth: true,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
//                executeEditOnePercentBonus();
            }
        });

    });

    function cboxFormatterQuotation(cellvalue, options, rowObject) {
        return '<input type="checkbox" ' + (cellvalue == 'true' ? ' checked="checked"' : '') +
                'value="false" onchange="if(this.checked){selected(' + options.rowId + ', true, 4)}else{selected(' + options.rowId + ', false, 4)} loadProduct();"/>';
    }

    function cboxFormatterProduct(cellvalue, options, rowObject) {
        return '<input type="checkbox" ' + (cellvalue == 'true' ? ' checked="checked"' : '') +
                'value="false" onchange="if(this.checked){selected(' + options.rowId + ', true, 0)}else{selected(' + options.rowId + ', false, 0)} loadCs();"/>';
    }

    function selected(id, val, key) {
        if (key == '0') {
            $("#jqgrid-grid-product").jqGrid('setCell', id, 'selected', val);
        } else {
            $("#jqgrid-grid-quotation").jqGrid('setCell', id, 'selected', val);
        }
    }

    function loadProduct(){
        var colData = $("#jqgrid-grid-quotation").jqGrid('getRowData');
        var ids = [];
        var x = 0;
        for (var i = 0; i < colData.length; i++) {
            if (colData[i].selected == 'true') {
                ids[x] = colData[i].id;
                x++;
            }
        }
        if (ids.length == 0) {
            $("#jqgrid-grid-product").jqGrid('clearGridData');
        } else {
            $("#jqgrid-grid-product").setGridParam({
                url: '${resource(dir:'quotation', file:'listProduct')}?quotations=' + ids
            });
            $("#jqgrid-grid-product").trigger("reloadGrid");
        }
    }

    function loadCs(){
        var colData = $("#jqgrid-grid-product").jqGrid('getRowData');
        var product = [];
        var quotation = [];
        var x = 0;
        for (var i = 0; i < colData.length; i++) {
            if (colData[i].selected == 'true') {
                product[x] = colData[i].id;
                x++;
            }
        }
        x = 0;
        colData = $("#jqgrid-grid-quotation").jqGrid('getRowData');
        for (i = 0; i < colData.length; i++) {
            if (colData[i].selected == 'true') {
                quotation[x] = colData[i].id;
                x++;
            }
        }
        if (product.length == 0) {
            $("#jqgrid-grid-cs").jqGrid('clearGridData');
        } else {
            $("#jqgrid-grid-cs").setGridParam({
                url: '${resource(dir:'quotation', file:'listCs')}?quotations=' + quotation + '&products=' + product
            });
            $("#jqgrid-grid-cs").trigger("reloadGrid");
        }
    }

    function executeAjaxQuotation(){
        var colData = $("#jqgrid-grid-quotation").jqGrid('getRowData');
        var x = 0;
        for (var i = 0; i < colData.length; i++) {
            if (colData[i].selected == 'true') {
                x++;
            }
        }
        if(x <= 1){
            MessageRenderer.render({
                messageTitle: 'CS Creation',
                type: 2,
                messageBody: 'Please select multiple Quotations.'
            });
        }else {
            MessageRenderer.render({
                messageTitle: 'CS Creation',
                type: 2,
                messageBody: 'Please Create the Customer and Negotiated Price List in order to initiate a sales.'
            });
        }
    }

</script>