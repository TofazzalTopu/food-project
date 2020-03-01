<script type="text/javascript">
    var allElements = new Array();
    var elementIndex = 0;
    var lastSel = -1;
    var qtyText = '';
    var ids = new Array();
    function checkForValue1(elem) {
        if (!elem) {
            return [false, "Please enter marks."];
        }
        else {
            return [true, ""];
        }
    }

    var updateProductBatch = {
        onEnterKeyPressToGridCell: function () {
            var callback = {
                type: 'keydown',
                fn: function (e) {
                    var key = e.charCode || e.keyCode;
                    if (!((key >= 48 && key <= 57) || (key >= 96 && key <= 105) || (key == 9) || (key == 13) || (key == 8) || (key == 110) || (key == 190) || (key == 46) || (key == 39) || (key == 37))) {
                        e.preventDefault()
                    }
                    if (key == 9) {
                        if (this.name == allElements[allElements.length - 1].name) {
                            e.preventDefault();
                            jQuery('#jqgrid-grid-product-batch').restoreRow(lastSel);
                            updateProductBatch.editGridCell(lastSel)
                            elementIndex = 0;
                            allElements = new Array();
                            var index = ids.indexOf(lastSel)
                            lastSel = ids[index + 1]
                            jQuery("#jqgrid-grid-product-batch").jqGrid('editRow', lastSel, 'updated_qty', true);

                        }
                    }
                    else if (key == 13) {
                        jQuery('#jqgrid-grid-product-batch').restoreRow(lastSel);
                        updateProductBatch.editGridCell(lastSel)
                        elementIndex = 0;
                        allElements = new Array();
                        var index = ids.indexOf(lastSel)
                        lastSel = ids[index + 1]
                        jQuery("#jqgrid-grid-product-batch").jqGrid('editRow', ids[index + 1], 'updated_qty', true);

                    }
                }
            };

            return callback;
        },

        editGridCell: function (rowid) {
            var qty = qtyText.value
            if (qty && rowid) {
                var order_qty = $("#jqgrid-grid-product-batch").jqGrid('getCell', rowid, 'quantity');
                if (isNaN(qty)) {
                    var msg = {
                        "class": "com.docu.common.Message",
                        "messageBody": ["Please enter number as quantity"],
                        "messageTitle": "Message",
                        "type": 0
                    }
                    MessageRenderer.render(msg);
                }

                if (parseFloat(order_qty) < parseFloat(qty)) {
                    var msg = {
                        "class": "com.docu.common.Message",
                        "messageBody": ["Batch amount can not be grater than order amount"],
                        "messageTitle": "Message",
                        "type": 0
                    }
                    MessageRenderer.render(msg);
                }
                else {
                    $("#jqgrid-grid-product-batch").jqGrid('setCell', rowid, 'updated_qty', qty);
                    var ids = $("#jqgrid-grid-product-batch").jqGrid('getDataIDs');
                    var total = 0;
                    for (key in ids) {
                        var updatedQty = $("#jqgrid-grid-product-batch").jqGrid('getCell', ids[key], 'updated_qty');
                        if (updatedQty) {
                            total += parseFloat(updatedQty);
                        }

                    }
                    $('#pickQty').val(total)
                    if (parseFloat(total) > parseFloat($('#orderQty').val())) {
                        $("#jqgrid-grid-product-batch").jqGrid('setCell', rowid, 'updated_qty', 0);
                        var msg = {
                            "class": "com.docu.common.Message",
                            "messageBody": ["Total selected quantity can not be grater than order quantity"],
                            "messageTitle": "Message",
                            "type": 0
                        }
                        MessageRenderer.render(msg);
                        $('#pickQty').val(total - qty)
                    }
                }

            }

            else {
                var msg = {
                    "class": "com.docu.common.Message",
                    "messageBody": ["Please enter quantity"],
                    "messageTitle": "Message",
                    "type": 0
                }
                MessageRenderer.render(msg);
            }


        }
    }
    $(document).ready(function () {

        var actionUrl = ''
        var productId = $('#product_id').val();
        var warehouseId = $("#warehouseId").val();
        if (productId) {
            actionUrl = '${resource(dir:'processDemandOrder', file:'productBatchDetails')}?id=' + productId + "&warehouseId=" + warehouseId
        }
        $("#jqgrid-grid-product-batch").jqGrid({
            url: actionUrl,
            datatype: "json",
            colNames: [
                'SL',
                'id',
                '&#10004;',
                'Batch',
                'Available Qty',
                'Batch Date',
                'Updated Qty'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30},
                {name: 'id', index: 'id', width: 30, hidden: true},
                {name: 'checkBatch', index: 'checkBatch', width: 30,hidden:true},
                {name: 'batch_no', index: 'batch_no', width: 200},
                {name: 'quantity', index: 'quantity', width: 100, align: 'center', formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 0}},
                {name: 'batch_date', index: 'batch_date', width: 100, align: 'center'},
                {
                    name: 'updated_qty',
                    index: 'updated_qty',
                    width: 100,
                    align: 'left',
                    editable: true,
                    edittype: 'text',
                    resizable: true,
                    editoptions: {
                        dataInit: function (elem) {
                            qtyText = elem;
                            allElements[elementIndex] = elem;
                            elementIndex++;
                            $(elem).focus(function () {
                                this.select();
                            })
                        }, dataEvents: [updateProductBatch.onEnterKeyPressToGridCell()]
                    },
                    editrules: {custom: true, custom_func: checkForValue1}
                }
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Product Batch Information",
            autowidth: true,
            height: 200,
            width: 500,
            scrollOffset: 0,
            loadComplete: function () {
                var ids = $("#jqgrid-grid-product-batch").jqGrid('getDataIDs');
                for (key in ids) {
                    var rowData = $("#jqgrid-grid-product-batch").getRowData(ids[key]);
                    $("#jqgrid-grid-product-batch").jqGrid('setRowData', ids[key], {checkBatch: '<input type="checkbox"   class="checkBatch" value="' + ids[key] + '" />'});
                }
            },
            onSelectRow: function (rowid, status) {
                elementIndex = 0;
                allElements = new Array();
                if (rowid && rowid !== lastSel) {

                    jQuery('#jqgrid-grid-product-batch').restoreRow(lastSel);
                    $("#jqgrid-grid-product-batch").editRow(rowid, true, false, false, false, false);
                    lastSel = rowid;
                }
                if (rowid == lastSel) {
                    jQuery("#jqgrid-grid-product-batch").jqGrid('editRow', rowid, true);
                    lastSel = rowid;

                }
            }
        });

    });
function getBatchWiseProductInfo(productId){
    var pickQty = Number($('#pickQty').val());
    var orderQty = Number($('#orderQty').val());
    if(pickQty > 0){
        if(pickQty != orderQty){
            var msg = {
                "class": "com.docu.common.Message",
                "messageBody": ["Please Allocate Batch Wise Quantity Properly"],
                "messageTitle": "Message",
                "type": 0
            };
            MessageRenderer.render(msg);
            return
        }
        var ids = $("#jqgrid-grid-product-batch").jqGrid('getDataIDs');
        var batchWiseSelectedProduct='';
        var batchWiseHiddenQty='';

        for (key in ids) {
            var updatedQty = $("#jqgrid-grid-product-batch").jqGrid('getCell', ids[key], 'updated_qty');
            var batch_no = $("#jqgrid-grid-product-batch").jqGrid('getCell', ids[key], 'batch_no');
            var id = $("#jqgrid-grid-product-batch").jqGrid('getCell', ids[key], 'id');

            if(updatedQty){
                batchWiseSelectedProduct += batch_no + "("+updatedQty+")"
                batchWiseHiddenQty += batch_no + "_" + id + "_" + updatedQty+","
            }
        }
        if(batchWiseHiddenQty!=''){
            batchWiseHiddenQty=batchWiseHiddenQty.substring(0,batchWiseHiddenQty.length-1)
        }
        $("#jqgrid-grid-item-available").jqGrid('setCell', productId, 'batch_qty', batchWiseSelectedProduct);
        $("#jqgrid-grid-item-available").jqGrid('setCell', productId, 'hidden_qty', batchWiseHiddenQty);
        $.fancybox.close();

    } else{
        var msg = {
            "class": "com.docu.common.Message",
            "messageBody": ["Please Update Batch Wise Quantity"],
            "messageTitle": "Message",
            "type": 0
        }
        MessageRenderer.render(msg);
    }
}

</script>