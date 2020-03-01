<script type="text/javascript">
    var allElements = new Array();
    var elementIndex = 0;
    var lastSel = -1;
    var qtyText = '';
    function checkForValue(elem) {
        if (!elem) {
            return [false, "Please enter marks."];
        }
        else {
            return [true, ""];
        }
    }
    var updateOrderEditor = {
        onEnterKeyPressToGridCell: function () {
            var callback = {
                type: 'keydown',
                fn: function (e) {
                    var key = e.charCode || e.keyCode;

                    if (key == 9) {
                        if (this.name == allElements[allElements.length - 1].name) {
                            e.preventDefault();
                            jQuery('#jqgrid-grid-update-order').restoreRow(lastSel);
                            updateOrderEditor.editGridCell(lastSel)
                        }
                    }
                    else if (key == 13) {
                        jQuery('#jqgrid-grid-update-order').restoreRow(lastSel);
                        updateOrderEditor.editGridCell(lastSel)
                    }
                }
            };

            return callback;
        },

        editGridCell: function (rowid) {
            var qty = qtyText.value;
            if(qty && rowid){
                if(isNaN(qty)){
                    var msg={"class":"com.docu.common.Message","messageBody":["Please enter number as quantity"],"messageTitle":"Message","type":0}
                    MessageRenderer.render(msg);
                }
                else{
                    $.ajax({
                        type: "POST",
                        url: "${resource(dir:'processDemandOrder', file:'secondaryDemandOrderDetailsUpdate')}?id=" + rowid + "&qty=" + qty,
                        datatype: 'json',
                        beforeSend: function (jqXHR, settings) {
                            $("#loader_icon").show();
                        },
                        success: function (data) {
                            $("#jqgrid-grid-update-order").jqGrid('setCell', (rowid), 'qty', qty);

                            $("#jqgrid-grid-update-order").setGridParam({
                                url: '${resource(dir:'processDemandOrder', file:'updateSecondaryDemandOrderDetails')}?id=' + $('#orderId').val()+"&orderNo="+$('#demandOrder').val()
                            });
                            $("#jqgrid-grid-update-order").trigger("reloadGrid");
                            // Reload Secondary Order
                            $("#jqgrid-grid-secondaryOrder").trigger("reloadGrid");
                            $("#jqgrid-grid-item-available").trigger("reloadGrid");
                            MessageRenderer.render(data);
                        },
                        complete: function () {
//                            $("#loader_icon").hide();
                        }
                    });
                }
            }

            else{
                var msg={"class":"com.docu.common.Message","messageBody":["Please enter quantity"],"messageTitle":"Message","type":0}
                MessageRenderer.render(msg);
            }
        }
    };

    $(document).ready(function () {
        var actionUrl='';
        if($('#orderId').val()){
         actionUrl='${resource(dir:'processDemandOrder', file:'updateSecondaryDemandOrderDetails')}?id=' + $('#orderId').val()+"&orderNo="+$('#demandOrder').val()
        }
        $("#jqgrid-grid-update-order").jqGrid({
            url: actionUrl,
            datatype: "json",
            colNames: [
                'SL',
                'id',
                'pid',
                'Product Name',
                'Product Id',
                'Order Quantity',
                'Processed Quantity',
                'Rate',
                'Amount'

            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, align: 'center'},
                {name: 'id', index: 'id', width: 30, hidden: true},
                {name: 'product_id', index: 'product_id', width: 30, hidden: true},
                {name: 'name', index: 'name', width: 200},
                {name: 'pid', index: 'pid', width: 200},
                {name: 'order_qty', index: 'order_qty', width: 100, align: 'center', editable: true, edittype: 'text', resizable: true,
                    editoptions: {dataInit: function (elem) {
                        qtyText = elem;
                        $(elem).focus(function () {
                            this.select();
                        })
                    }, dataEvents: [updateOrderEditor.onEnterKeyPressToGridCell()]}, editrules: {custom: true, custom_func: checkForValue}},
                {name: 'qty', index: 'qty', width: 100, align: 'center'},
                {name: 'rate', index: 'rate', width: 100, align: 'right'},
                {name: 'amount', index: 'amount', width: 100, align: 'right'}
            ],
            rowNum: -1,
            pager: '#jqgrid-update-order-pager',
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Product Information",
            autowidth: true,
            height: 200,
            scrollOffset: 0,
            footerrow:true,
            userDataOnFooter: true,
            loadComplete: function () {
//                var ids = $("#jqgrid-grid").jqGrid('getDataIDs');
//                for (key in ids) {
//                    var rowData = $("#jqgrid-grid").getRowData(ids[key]);
//                    $("#jqgrid-grid").jqGrid('setRowData', ids[key], {checkOrder: '<input type="checkbox"  onchange="buttonStatusChange();"  class="orderChk" value="' + ids[key] + '" />'});
//                }
                var $grid = $('#jqgrid-grid-update-order');
                var colSum = $grid.jqGrid('getCol', 'amount', false, 'sum');
                $grid.jqGrid('footerData', 'set', { amount: colSum, rate:'Total  ' });

            },
            onSelectRow: function (rowid, status) {
                elementIndex = 0;
                allElements = new Array();
                if (rowid && rowid !== lastSel) {

                    jQuery('#jqgrid-grid-update-order').restoreRow(lastSel);
                    $("#jqgrid-grid-update-order").editRow(rowid, true, false, false, false, false);
                    lastSel = rowid;
                }
                if (rowid == lastSel) {
                    jQuery("#jqgrid-grid-update-order").jqGrid('editRow', rowid, true);
                    lastSel = rowid;

                }
            }
        });

    });

    function setPriceValue(){
        if($('#pId').val()){
           var priceValue=($('#pId').val().split('-'))
           $('#productId').val(priceValue[0])
            $('#rate').val(priceValue[1])
        }
    }

    function addPrimaryDemandOrderDetails(){
        if($('#productId').val() && $('#rate').val() && $('#qty').val()  ){
            var qnty;
            var url = "${resource(dir:'processDemandOrder', file:'createSecondaryDemandOrderDetails')}?secondaryDemandOrder.id=" + ${params?.id} + "&finishProduct.id=" +  $('#productId').val()+"&rate=" +  $('#rate').val()+"&quantity=" +  $('#qty').val();
            var myGrid = $("#jqgrid-grid-update-order");
            var dataGridIds = myGrid.jqGrid('getDataIDs');
            for(var i = 0;i < dataGridIds.length ; i++){
                var rowTo = myGrid.jqGrid('getRowData', dataGridIds[i])
                if(rowTo['product_id'] == $('#productId').val()){
                    qnty = parseFloat(rowTo['order_qty']);
                    qnty += parseFloat($('#qty').val());
                    url = "${resource(dir:'processDemandOrder', file:'secondaryDemandOrderDetailsUpdate')}?id=" + dataGridIds[i].toString() + "&qty=" + qnty;
                    break;
                }
            }
            $.ajax({
                type: "POST",
                url: url,
                datatype: 'json',
                beforeSend: function (jqXHR, settings) {
                    $("#loader_icon").show();
                },
                success: function (data) {
                    $("#jqgrid-grid-update-order").setGridParam({
                        url: '${resource(dir:'processDemandOrder', file:'updateSecondaryDemandOrderDetails')}?id=' + $('#orderId').val()
                    });
                    $("#jqgrid-grid-update-order").trigger("reloadGrid");
                    MessageRenderer.render(data);
                },
                complete: function () {
//                            $("#loader_icon").hide();
                }
            });
        }
    }

</script>