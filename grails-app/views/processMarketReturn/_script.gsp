<script type="text/javascript" language="Javascript">

    var editId = 0;
    var rowIndex = -1;
    var total = 100;
    var dpId = 0;

    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormProcessMarketReturn").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormProcessMarketReturn").validationEngine('attach');
        reset_form("#gFormProcessMarketReturn");
        if ('${dpList}' != '') {
            $("#dpId").val(${dpList}[0].id);
            dpId = ${dpList}[0].id;
        }
        $("#dpId").val(dpId);

        $("#acceptedQuantity").format({precision: 0, allow_negative: false, autofix: true});
        $("#jqgrid-grid-processMarketReturn").jqGrid({
            %{--url: '${resource(dir:'processMarketReturn', file:'list')}',--}%
            datatype: "local",
            colNames: [
                'ID',
                'Unit Price',
                'Product Name',
                'Product Code',
                'MR Type',
                'Accepted Quantity',
                'Failed Quantity',
                ''
            ],
            colModel: [
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'price', index: 'price', width: 0, align: 'right', hidden: true},
                {name: 'productName', index: 'productName', width: 150, sortable: true, align: 'left'},
                {name: 'productCode', index: 'productCode', width: 150, align: 'left'},
                {name: 'mrType', index: 'mrType', width: 100, align: 'left'},
                {name: 'acceptedQty', index: 'acceptedQty', width: 100, align: 'left'},
                {name: 'failedQty', index: 'failedQty', width: 100, align: 'left'},
                {name: 'delete', index: 'delete', width: 50, align: 'left'}
            ],
            rowNum: -1,
            rowList: [10, 20, 30],
//            pager: '#jqgrid-pager-processMarketReturn',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All Processed Market Return" + '<span class="mendatory_field">*</span>',
            autowidth: true,
            height: true,
            scrollOffset: 0,
            altRows: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditProcessMarketReturn();
            }
        });
//        $("#jqgrid-grid-processMarketReturn").jqGrid('navGrid', '#jqgrid-pager-processMarketReturn', {
//            edit: false,
//            add: false,
//            del: false,
//            search: false
//        });

        $("#mr-grid").jqGrid({
            url: '${resource(dir:'processMarketReturn', file:'listMrNo')}?dpId=' + $("#dpId").val(),
            datatype: "json",
            colNames: [
                'ID',
                'Mr No',
                'Customer Name',
                'MR Date',
                ''
            ],
            colModel: [
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'mrNo', index: 'mrNo', width: 100, align: 'left'},
                {name: 'customerName', index: 'customerName', width: 110, align: 'left'},
                {name: 'mrDate', index: 'mrDate', width: 80, align: 'center'},
                {name: 'reject', index: 'reject', width: 85, align: 'center'}
            ],
            rowNum: -1,
            rowList: [10, 20, 30],
//            pager: '#mr-grid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All Registered Market Return",
            autowidth: true,
            autoheight: true,
            scrollOffset: 17,
            altRows: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                listDetails(rowid);
            }
        });
//        $("#mr-grid").jqGrid('navGrid', '#mr-grid-pager', {
//            edit: false,
//            add: false,
//            del: false,
//            search: false
//        });

        $("#mr-details-grid").jqGrid({
            %{--url: '${resource(dir:'processMarketReturn', file:'list')}',--}%
            datatype: "local",
            colNames: [
                'ID',
                'Product Name',
                'Product Code',
                'Leak Quantity',
                'Short Quantity',
                'Market Return',
                'Challan Shortage',
                'Damage',
                'Unit Price',
                'totalQty'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'productName', index: 'productName', width: 100, align: 'left'},
                {name: 'productCode', index: 'productCode', width: 0, hidden: true},
                {name: 'leakQty', index: 'leakQty', width: 60, align: 'right'},
                {name: 'shortQty', index: 'shortQty', width: 60, align: 'right'},
                {name: 'mrQty', index: 'mrQty', width: 60, align: 'right'},
                {name: 'challanShort', index: 'challanShort', width: 70, align: 'right'},
                {name: 'damage', index: 'damage', width: 60, align: 'right'},
                {name: 'price', index: 'price', width: 0, align: 'right', hidden: true},
                {name: 'totalQty', index: 'totalQty', width: 0, align: 'right', hidden: true}
            ],
            rowNum: -1,
            rowList: [10, 20, 30],
//            pager: '#mr-details-grid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Details of Registered Market Return",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            altRows: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                showInfo(rowid);
            }
        });
//        $("#mr-details-grid").jqGrid('navGrid', '#mr-details-grid-pager', {
//            edit: false,
//            add: false,
//            del: false,
//            search: false
//        });
//                .navButtonAdd('#jqgrid-pager-processMarketReturn', {
//                    caption: "Edit",
//                    buttonicon: "ui-icon-edit",
//                    onClickButton: function () {
//                        executeEditProcessMarketReturn();
//                    },
//                    position: "last"
//                })
//                .navButtonAdd('#jqgrid-pager-processMarketReturn', {
//                    caption: "Delete",
//                    buttonicon: "ui-icon-del",
//                    onClickButton: function () {
//                        deleteAjaxProcessMarketReturn();
//                    },
//                    position: "last"
//                });

        if('${dpList}' != ''){
            $("#dpId").val(${dpList}[0].id);
        }
//        loadOrderNo();
        setDateRangeNoLimit('dateFrom', 'dateTo');
    });

    $(window).unload(function () {
        jQuery.ajax({
            type: 'post',
            url: '${resource(dir:'processMarketReturn', file:'statusChange')}',
            success: function (data, textStatus) {
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
            },
            dataType: 'json'
        });
        return null;
    });

    function loadOrderNo() {
        jQuery('#searchOrderKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                $('#popEmpDetails').html("");
                var data = {searchKey: request.term};
                var url = '${resource(dir:'marketReturn', file:'listMrForAutoComplete')}';
                DocuAutoComplete.setSpinnerSelector('searchOrderKey').execute(response, url, data, function (item) {
                    item['label'] = item['mr_no'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {

                $("#searchOrderKey").val(ui.item.mr_no);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + "Customer Name: " + item.customer_name +
                ", Source DP: " + item.source_dp + ", Destination DP: " + item.destination_dp + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);

        }
    }

    function loadDataByOrder() {
        $("#mr-grid").setGridParam({
            url: '${resource(dir:'processMarketReturn', file:'listMrNo')}?orderNo=' + $('#searchOrderKey').val()
            + '&dateFrom=' + $('#dateFrom').val() + '&dateTo=' + $('#dateTo').val() + '&dpId=' + $('#dpId').val()
        });
        $("#mr-grid").trigger("reloadGrid");

    }

    function deleteItemFromGrid(id) {
        jQuery.ajax({
            type: 'post',
            url: '${resource(dir:'processMarketReturn', file:'rejectMarketReturn')}?id=' + id,
            success: function (data, textStatus) {
                if (data.type == 1) {
                    $("#mr-grid").trigger("reloadGrid");
                }
                MessageRenderer.render(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
            },
            dataType: 'json'
        });
    }

    function listDetails(id) {
        $("#mrId").val(id);
        %{--$("#mr-details-grid").setGridParam({--}%
            %{--url: '${resource(dir:'processMarketReturn', file:'listDetails')}?id=' + id,--}%
            %{--dataType: 'json'--}%
        %{--});--}%
        jQuery("#mr-details-grid").jqGrid().setGridParam({url:
                '${resource(dir:'processMarketReturn', file:'listDetails')}?id=' + id,
            datatype: "json", mtype: "POST"}).trigger("reloadGrid");
//        $("#mr-details-grid").trigger("reloadGrid");
        $("#jqgrid-grid-processMarketReturn").jqGrid("clearGridData");
    }

    function showInfo(id) {
        var name = $("#mr-details-grid").jqGrid('getCell', id, 'productName');
        var code = $("#mr-details-grid").jqGrid('getCell', id, 'productCode');
        var productId = $("#mr-details-grid").jqGrid('getCell', id, 'id');
        var totalQty = parseFloat($("#mr-details-grid").jqGrid('getCell', id, 'leakQty'));
        totalQty = totalQty + parseFloat($("#mr-details-grid").jqGrid('getCell', id, 'shortQty'));
        totalQty = totalQty + parseFloat($("#mr-details-grid").jqGrid('getCell', id, 'mrQty'));
        totalQty = totalQty + parseFloat($("#mr-details-grid").jqGrid('getCell', id, 'challanShort'));
        totalQty = totalQty + parseFloat($("#mr-details-grid").jqGrid('getCell', id, 'damage'));
        total = totalQty;
        var price = $("#mr-details-grid").jqGrid('getCell', id, 'price');
        $("#finishProductName").val(name);
        $("#finishProductCode").val(code);
        $("#finishProduct").val(productId);
        $("#mrType").val('');
        $("#availableQty").val('0');
        $("#price").val(price);
        editId = id;
    }

    function setAvailable(mrType) {
        $("#acceptedQuantity").val('');
        $("#failedQuantity").val('');
        if (editId == 0) {
            $("#availableQty").val(0);
            return false;
        }
        var qty = '';
        if (mrType == '') {
            qty = 0;
        } else if (mrType == "Leak Pack") {
            qty = $("#mr-details-grid").jqGrid('getCell', editId, 'leakQty');
            $("#subType").attr('hidden',true);
        } else if (mrType == "Short Pack") {
            qty = $("#mr-details-grid").jqGrid('getCell', editId, 'shortQty');
            $("#subType").attr('hidden',true);
        } else if (mrType == "Market Return") {
            qty = $("#mr-details-grid").jqGrid('getCell', editId, 'mrQty');
            $("#subType").attr('hidden',false);
        } else if (mrType == "Short Supply from Challan") {
            qty = $("#mr-details-grid").jqGrid('getCell', editId, 'challanShort');
            $("#subType").attr('hidden',true);
        } else if (mrType == "Damage") {
            qty = $("#mr-details-grid").jqGrid('getCell', editId, 'damage');
            $("#subType").attr('hidden',true);
        }
        $("#availableQty").val(qty);
    }

    function checkValue(qty) {
        if (qty == '') {
            return false;
        }
        if (!$("#finishProduct").val()) {
            MessageRenderer.render({
                messageTitle: 'Data Error',
                type: 2,
                messageBody: 'Please select product to process.'
            });
            $("#acceptedQuantity").val('');
            return false;
        }
        if (parseFloat(qty) > parseFloat($("#availableQty").val())) {
            MessageRenderer.render({
                messageTitle: 'Data Error',
                type: 2,
                messageBody: 'Accepted quantity can not be greater than available quantity.'
            });
            $("#acceptedQuantity").val('');
            return false;
        }
        $("#failedQuantity").val(parseFloat($("#availableQty").val()) - parseFloat(qty));
    }

    function addItemToGrid() {
        total = total - parseFloat($("#availableQty").val());
        var myGrid = $("#jqgrid-grid-processMarketReturn");
        var productId = $("#finishProduct").val();
        var quantity = $("#acceptedQuantity").val();
        var mrType = $("#mrType").val();
        if (productId == '' || quantity == '' || mrType == '') {
            MessageRenderer.render({
                messageTitle: 'Missing Info',
                type: 2,
                messageBody: 'Please fill the required information before adding to grid.'
            });
            return false;
        }
        var dataItem = {
            id: productId,
            price: $("#price").val(),
            productName: $('#finishProductName').val(),
            productCode: $("#finishProductCode").val(),
            mrType: mrType,
            acceptedQty: quantity,
            failedQty: $('#failedQuantity').val(),
            delete: '<a  href="javascript:deleteProductFromGrid(' + rowIndex + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
        };
        myGrid.addRowData(rowIndex, dataItem, "last");
        rowIndex++;
        $("#mrType").val('');
        $("#availableQty").val(0);
        $("#acceptedQuantity").val('');
        $('#failedQuantity').val('');
        var myGrid2 = $("#mr-details-grid");
        myGrid2.jqGrid('setCell', editId, 'totalQty', (parseFloat(myGrid2.jqGrid('getCell', editId, 'totalQty')) + parseFloat(quantity)));
        if (mrType == "Leak Pack") {
            myGrid2.jqGrid('setCell', editId, 'leakQty', 0);
        } else if (mrType == "Short Pack") {
            myGrid2.jqGrid('setCell', editId, 'shortQty', 0);
        } else if (mrType == "Market Return") {
            myGrid2.jqGrid('setCell', editId, 'mrQty', 0);
        } else if (mrType == "Short Supply from Challan") {
            myGrid2.jqGrid('setCell', editId, 'challanShort', 0);
        } else if (mrType == "Damage") {
            myGrid2.jqGrid('setCell', editId, 'damage', 0);
        }
    }

    function deleteProductFromGrid(id) {
        var myGrid = $("#jqgrid-grid-processMarketReturn");
        var myGrid2 = $("#mr-details-grid");
        var mr = myGrid.jqGrid('getCell', id, 'mrType');
        var qty = myGrid.jqGrid('getCell', id, 'acceptedQty');
        qty = parseFloat(qty) + parseFloat(myGrid.jqGrid('getCell', id, 'failedQty'));
        var price = myGrid.jqGrid('getCell', id, 'price');
        var gdId = myGrid2.jqGrid('getDataIDs');
        var proId = '';
        for(var i = 0; i < gdId.length; i++){
            if(parseFloat(price) == parseFloat(myGrid2.jqGrid('getCell', gdId[i], 'price'))){
                proId = gdId[i];
                break;
            }
        }

        myGrid.jqGrid('delRowData', id);
        if (mr == "Leak Pack") {
            myGrid2.jqGrid('setCell', proId, 'leakQty', qty);
        } else if (mr == "Short Pack") {
            myGrid2.jqGrid('setCell', proId, 'shortQty', qty);
        } else if (mr == "Market Return") {
            myGrid2.jqGrid('setCell', proId, 'mrQty', qty);
        } else if (mr == "Short Supply from Challan") {
            myGrid2.jqGrid('setCell', proId, 'challanShort', qty);
        } else if (mr == "Damage") {
            myGrid2.jqGrid('setCell', proId, 'damage', qty);
        }
        total = total + qty;
        $("#mrType").val('');
        $("#availableQty").val(0);
        $("#acceptedQuantity").val('');
        $('#failedQuantity').val('');
    }

    function executePreConditionProcessMarketReturn() {
        // trim field vales before process.
        trim_form();
        if ($("#gFormProcessMarketReturn").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }

    function executeAjaxProcessMarketReturn() {
        if (!executePreConditionProcessMarketReturn()) {
            return false;
        }
        if(dpId != 0){
            $("#dpId").val(dpId);
        }else{
            MessageRenderer.renderErrorText("DP not found!");
        }
        if(total > 0){
            MessageRenderer.render({
                messageTitle: 'Missing Info',
                type: 2,
                messageBody: 'Process all claimed quantity before saving.'
            });
            return false;
        }
        if($("#mrId").val() == ''){
            MessageRenderer.render({
                messageTitle: 'Missing Info',
                type: 2,
                messageBody: 'Select MR to process.'
            });
            return false;
        }
        var actionUrl = null;
        if ($('#gFormProcessMarketReturn input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/save";
        }

        var data = jQuery("#gFormProcessMarketReturn").serializeArray();
        var gd = $("#jqgrid-grid-processMarketReturn").jqGrid('getRowData');
        var gd2 = $("#mr-details-grid").jqGrid('getRowData');
        var length = gd.length;
        for (var i = 0; i < length; i++) {
            data.push({'name': 'items.processMarketReturnDetails[' + i + '].finishProduct.id', 'value': gd[i].id});
            data.push({'name': 'items.processMarketReturnDetails[' + i + '].mrType', 'value': gd[i].mrType});
            data.push({'name': 'items.processMarketReturnDetails[' + i + '].qcQuantity', 'value': gd[i].acceptedQty});
            data.push({'name': 'items.processMarketReturnDetails[' + i + '].qcFailedQuantity', 'value': gd[i].failedQty});
            data.push({'name': 'items.processMarketReturnDetails[' + i + '].mrSubType', 'value': ''});
            data.push({'name': 'items.processMarketReturnDetails[' + i + '].subTypeQuantity', 'value': ''});
            data.push({'name': 'items.processMarketReturnDetails[' + i + '].rate', 'value': gd[i].price});
        }
        for (i = 0; i < gd2.length; i++) {
            data.push({'name': 'itemsWarehouse.finishGoodWarehouseDetails[' + i + '].finishProduct.id', 'value': gd2[i].id});
            data.push({'name': 'itemsWarehouse.finishGoodWarehouseDetails[' + i + '].cost', 'value': gd2[i].price});
            data.push({'name': 'itemsWarehouse.finishGoodWarehouseDetails[' + i + '].confirmCost', 'value': gd2[i].price});
            data.push({'name': 'itemsWarehouse.finishGoodWarehouseDetails[' + i + '].quantity', 'value': gd2[i].totalQty});
        }

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionProcessMarketReturn(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#mr-grid").trigger("reloadGrid");
                    $("#mr-details-grid").jqGrid("clearGridData");
                    $("#jqgrid-grid-processMarketReturn").jqGrid("clearGridData");
                    reset_form('#gFormProcessMarketReturn');

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

    function executePostConditionProcessMarketReturn(result) {
        if (result.type == 1) {
            $("#mr-grid").trigger("reloadGrid");
            $("#mr-details-grid").jqGrid("clearGridData");
            $("#jqgrid-grid-processMarketReturn").jqGrid("clearGridData");
            reset_form('#gFormProcessMarketReturn');
        }
        MessageRenderer.render(result);
    }

    function getSelectedProcessMarketReturnId() {
        var processMarketReturnId = null;
        var rowid = $("#jqgrid-grid-processMarketReturn").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            processMarketReturnId = $("#jqgrid-grid-processMarketReturn").jqGrid('getCell', rowid, 'id');
        }
        if (processMarketReturnId == null) {
            processMarketReturnId = $('#gFormProcessMarketReturn input[name = id]').val();
        }
        return processMarketReturnId;
    }

    function deleteAjaxProcessMarketReturn() {    // Delete record
        var processMarketReturnId = getSelectedProcessMarketReturnId();
        if (executePreConditionForDeleteProcessMarketReturn(processMarketReturnId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-processMarketReturn").dialog({
                resizable: false,
                height: 150,
                modal: true,
                title: 'Delete alert',
                buttons: {
                    'Delete item(s)': function () {
                        $(this).dialog('close');
                        $.ajax({
                            type: "POST",
                            dataType: "json",
                            data: jQuery("#gFormProcessMarketReturn").serialize(),
                            url: "${resource(dir:'processMarketReturn', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteProcessMarketReturn(message);
                            }
                        });
                    },
                    Cancel: function () {
                        $(this).dialog('close');
                    }
                }
            }); //end of dialog
        }
    }

    function executePreConditionForEditProcessMarketReturn(processMarketReturnId) {
        if (processMarketReturnId == null) {
            alert("Please select a processMarketReturn to edit");
            return false;
        }
        return true;
    }
    function executeEditProcessMarketReturn() {
        var processMarketReturnId = getSelectedProcessMarketReturnId();
        if (executePreConditionForEditProcessMarketReturn(processMarketReturnId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'processMarketReturn', file:'edit')}?id=" + processMarketReturnId,
                success: function (entity) {
                    executePostConditionForEditProcessMarketReturn(entity);
                },
                dataType: 'json'
            });
        }

    }
    function executePostConditionForEditProcessMarketReturn(data) {
        if (data == null) {
            alert('Selected processMarketReturn might have been deleted by someone');  //Message renderer
        } else {
            showProcessMarketReturn(data);
        }
    }
    function executePreConditionForDeleteProcessMarketReturn(processMarketReturnId) {
        if (processMarketReturnId == null) {
            alert("Please select a processMarketReturn to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteProcessMarketReturn(data) {
        if (data.type == 1) {
            $("#jqgrid-grid-processMarketReturn").trigger("reloadGrid");
            reset_form('#gFormProcessMarketReturn');
        }
        MessageRenderer.render(data)
    }
    function showProcessMarketReturn(entity) {
        $('#gFormProcessMarketReturn input[name = id]').val(entity.id);
        $('#gFormProcessMarketReturn input[name = version]').val(entity.version);

        $('#mrNo').val(entity.mrNo);
        $('#qcPersonName').val(entity.qcPersonName);
        $('#mrProcessedBy').val(entity.mrProcessedBy);
        $('#qcPersonPin').val(entity.qcPersonPin);
        $('#qcPerformingTime').val(entity.qcPerformingTime);
        if (entity.userCreated) {
            $('#userCreated').val(entity.userCreated.id).attr("selected", "selected");
        }
        else {
            $('#userCreated').val(entity.userCreated);
        }
        if (entity.userUpdated) {
            $('#userUpdated').val(entity.userUpdated.id).attr("selected", "selected");
        }
        else {
            $('#userUpdated').val(entity.userUpdated);
        }
        $('#dateCreated').val(entity.dateCreated);
        $('#dateUpdated').val(entity.dateUpdated);
        $('#create-button-processMarketReturn').attr('value', 'Update');
        $('#delete-button-processMarketReturn').show();
    }
    function executeSearchProcessMarketReturn(fieldName, fieldValue) {
        if (executePreConditionForSearchProcessMarketReturn(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'processMarketReturn', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchProcessMarketReturn(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchProcessMarketReturn(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormProcessMarketReturn");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchProcessMarketReturn(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormProcessMarketReturn"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showProcessMarketReturn(data);
        }
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