<script type="text/javascript" language="Javascript">
    var subAreaId = 1;
    var cross_string = '<font style="font-weight: bold;color: red;">X</font>';
    var tick_string = '<font style="font-weight: bold;color: green;">&radic;</font>';
    $(document).ready(function () {
        $('#delete-button').hide();
        $("#batchDate").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $('#timeTransaction').timepicker({showPeriod: true, showLeadingZero: true});
        $('#ui-widget-header-text').html('Finish Goods');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }
        $("#gFormFinishGood").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
//        $("#gFormFinishGoodStock").validationEngine('attach');
        $('#subInventoryList_input').val('');
        $("#subInventoryList").flexbox('Sub Inventory', {
            watermark: "Sub Inventory",
            width: 340,
            onSelect: function () {
                $("#subWarehouse").val($('#subInventoryList_hidden').val());
//                        loadBusinessUnit($('#enterpriselist_hidden').val());
            }
        });
        $('#subInventoryList_input').val('');
        $('#subInventoryList_input').addClass("validate[required]");
        $('#subInventoryList_input').blur(function () {
            if ($('#subInventoryList_input').val() == '') {
                $("#subWarehouse").val("");
            }
        });

        $('#product').blur(function () {
            if ($('#product').val() == '') {
                $("#productId").val('');
            }
        });

        jQuery('#product').autocomplete({
            minLength: '1',
            source: function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                $('#popEmpDetails').html("");
                var data = {searchKey: request.term};
                var url = "${request.contextPath}/${params.controller}/productListForFinishGood?query=" + $('#product').val();
                DocuAutoComplete.setSpinnerSelector('product').execute(response, url, data, function (item) {
                    item['label'] = item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                CustomerInfo.setCustomerInformation(ui.item.id, ui.item.value);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var customerDetails = "";
            customerDetails = '<div style="font-size: 9px; color:#326E93;">' + " Name: " + item.name+ '</div>';
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + customerDetails).appendTo(ul);

        };

        $("#jqgrid-grid-finishGoodStock").jqGrid({
            datatype: "local",
            colNames: [
                'Batch No',
                'Batch Control',
                'Date',
                'Time',
                'Inventory',
                'Sub Inventory',
                'Product',
                'Quantity',
                'Cost value',
                'Confirm Cost value',
                'warehouse',
                'subwarehouse',
                'productId',
                'batchHidden'
                //'Delete'
            ],
            colModel: [
                {name: 'batchNo', index: 'batchNo', width: 80},
                {name: 'batchCtl', index: 'batchCtl', width: 50},
                {name: 'batchDate', index: 'batchDate', width: 70, align: 'left'},
                {name: 'batchTime', index: 'batchTime', width: 50, align: 'left'},
                {name: 'inventory', index: 'inventory', width: 110, align: 'left'},
                {name: 'sub_inventory', index: 'sub_inventory', width: 110, align: 'left'},
                {name: 'product', index: 'product', width: 150, align: 'left'},
                {name: 'quantity', index: 'quantity', width: 60, align: 'center'},
                {name: 'cost_value', index: 'cost_value', width: 60, align: 'right'},
                {name: 'confirm_cost_value', index: 'confirm_cost_value', width: 70, align: 'right'},
                {name: 'warehouse', index: 'warehouse', width: 0, 'hidden': true},
                {name: 'subWarehouse', index: 'subWarehouse', width: 0, 'hidden': true},
                {name: 'productId', index: 'productId', width: 0, 'hidden': true},
                {name: 'batchHidden', index: 'batchHidden', width: 0, 'hidden': true}
                //{name: 'delete', index: 'delete', width: 50, align: 'center'}
            ],
            rowNum: -1,
//            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Added Finish Good Information",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
            }
        });
        $("#cost").format({precision: 2, allow_negative: false, autofix: true});
        $("#confirmCost").format({precision: 2, allow_negative: false, autofix: true});
        $("#quantity").format({precision: 0, allow_negative: false, autofix: true});
    });

    function executePreConditionFinishGoodStock() {
        // trim field vales before process.

        trim_form();

        if (!$("#gFormFinishGood").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function subInventoryList() {
        if (!executePreConditionFinishGoodStock()) {
            return false;
        }

        var allIds = {};
        var gridCollection = jQuery("#jqgrid-grid-finishGoodStock").jqGrid('getRowData');
        var dataLength = gridCollection.length;
        for (var i = 0; i < dataLength; i++) {
            allIds['items.finishGood[' + i + '.finishProduct.id'] = gridCollection[i].productId;
            allIds['items.finishGood[' + i + '.quantity'] = gridCollection[i].quantity;
            allIds['items.finishGood[' + i + '.cost'] = gridCollection[i].cost_value;
            allIds['items.finishGood[' + i + '.confirmCost'] = gridCollection[i].confirm_cost_value;
        }


        var actionUrl = null;
        actionUrl = "${request.contextPath}/${params.controller}/create";
        if(gridCollection.length > 0){
            allIds['batchNo'] = gridCollection[0].batchNo
            allIds['isBatchControl'] = gridCollection[0].batchHidden
            allIds['dateTransaction'] = gridCollection[0].batchDate
            allIds['timeTransaction'] = gridCollection[0].batchTime
            allIds['warehouse.id'] = gridCollection[0].warehouse
            allIds['subWarehouse.id'] = gridCollection[0].subWarehouse
            allIds['finishProductId'] = gridCollection[0].productId
            SubmissionLoader.showTo();
            jQuery.ajax({
                type: 'post',
                data: allIds,
                url: actionUrl,
                success: function (data, textStatus) {
                    executePostConditionFinishGoodStock(data);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText)
                },
                complete: function () {
                    SubmissionLoader.hideFrom();
                },
                dataType: 'json'
            });
        }
        else{
            var msg={"class":"com.docu.common.Message","messageBody":["Please select at least one product"],"messageTitle":"Message","type":0}
            MessageRenderer.render(msg);
        }

        return false;
    }
    function executePostConditionFinishGoodStock(result) {
        if (result.type == 1) {
//            $("#jqgrid-grid").trigger("reloadGrid");
//            reset_form('#gFormFinishGood');
        }
        MessageRenderer.render(result);
    }

    function selectSubInventory(inventoryId) {
        $.ajax({
            type: "POST",
            url: "${resource(dir:'finishGoodWarehouse', file:'selectSubwarehouse')}?id=" + inventoryId,
            beforeSend: function (jqXHR, settings) {
                $("#loader_icon").show();
            },
            success: function (data) {
                var subwareHouseMap = new Object(); // or var map = {};
                subwareHouseMap["results"] = data;
                subwareHouseMap["total"] = data;
                $("#subInventoryList").empty();
                $("#subInventoryList").flexbox(subwareHouseMap, {
                    watermark: "Sub inventory",
                    width: 350,
                    onSelect: function () {
                        $("#subWarehouse").val($('#subInventoryList_hidden').val());
                    }
                });
                $('#subInventoryList_input').val('');
                $('#subInventoryList_input').addClass("validate[required]");
                $('#subInventoryList_input').blur(function () {
                    if ($('#subInventoryList_input').val() == '') {
                        $("#subWarehouse").val("");
                    }
                });
            },
            complete: function (data) {
                $("#loader_icon").hide();
            },
            dataType: 'json'
        });
    }

    $('#search-btn-customer-register-id').click(function () {
        CustomerInfo.popupCustomerListPanel();
    });

    var CustomerInfo = {
        customerCoreInfoId: null,
        popupCustomerListPanel: function (customerCoreInfoId) {
            var url = '${resource(dir:'finishGoodWarehouse', file:'productListPopupForFinishGood')}';
            var params = {id: customerCoreInfoId};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },

        setCustomerInformation: function (customerCoreInfoId, customerCoreInfo) {
            $("#product").val(customerCoreInfo);
            $("#productId").val(customerCoreInfoId);
            CustomerInfo.customerCoreInfoId = customerCoreInfoId;
        }
    };

    function reset_form(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
        $(formName).find('select').each(function () {
            this.value = "";
        });
        var gridId = $("#jqgrid-grid-finishGoodStock");
        for(var k=(subAreaId-1);k>=1;k--){
            jQuery('#jqgrid-grid-finishGoodStock').delRowData(k);
        }

        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
        $(formName + ' input[name = select-button').attr('value', 'Select All')
    }

    function checkUnCheck() {
        //alert("alert notify")
        if ($('#select-button').val() == 'Select All') {
            $('.disChck').each(function () {
                this.checked = true;
                $('#select-button').attr('value', 'Deselect All')
            })
        }
        else {
            $('.disChck').each(function () {
                this.checked = false;
                $('#select-button').attr('value', 'Select All')
            })
        }
    }

    function buttonStatusChange() {
        var isSelected = true;
        $('.disChck').each(function () {
            if (this.checked) {
                $('#select-button').attr('value', 'Deselect All');
                isSelected = false
            }
        });

        if (isSelected) {
            $('#select-button').attr('value', 'Select All')
        }
    }

    function addRowInGrid() {
        var val_1 = ($("input[name='isBatchControl']:checked").val()).toString();
        var batch_ctl = cross_string
        if (val_1 == 'true') {
            batch_ctl = tick_string
            if (($("#batchNo").val())=='' || ($("#timeTransaction").val())=='' || ($("#dateTransaction").val())=='') {
                var msg = {
                    "class": "com.docu.common.Message",
                    "messageBody": ["Please enter Batch No Or Date Transaction Or Time Transaction correctly"],
                    "messageTitle": "Message",
                    "type": 0
                }
                MessageRenderer.render(msg);
                return false;
            }
        }
        if (!executePreConditionFinishGoodStock()) {
            return false;
        }
        if (isNaN($("#cost").val()) || isNaN($("#confirmCost").val())) {
            var msg={"class":"com.docu.common.Message","messageBody":["Please check the cost  value is entered correctly"],"messageTitle":"Message","type":0}
            MessageRenderer.render(msg);
        } else {
            if ($('#cost').val() == $('#confirmCost').val()) {
                var gridId = $("#jqgrid-grid-finishGoodStock");
                var exist='false'
                var ids = $("#jqgrid-grid-finishGoodStock").jqGrid('getDataIDs');
                for (key in ids) {
                    if(exist=='false'){
                        rowData = $("#jqgrid-grid-finishGoodStock").jqGrid('getRowData', ids[key]);
                        if (rowData['productId'] == $("#productId").val() && rowData['batchNo'] == $("#batchNo").val()) {
                            exist='true'
                            if(rowData['confirm_cost_value']==$("#confirmCost").val()){
                                var existingVal= $("#jqgrid-grid-finishGoodStock").jqGrid('getCell', ids[key], 'quantity');
                                var newVal=parseFloat(existingVal)+parseFloat($("#quantity").val())
                                $("#jqgrid-grid-finishGoodStock").jqGrid('setCell', ids[key], 'quantity',newVal);
                            }
                            else{
                                var msg={"class":"com.docu.common.Message","messageBody":["Product cost must be same as it is already added in list"],"messageTitle":"Message","type":0}
                                MessageRenderer.render(msg);
                            }
                        }
                    }
                }

                if(exist=='false'){
                    var data = {
                        batchNo: $("#batchNo").val(),
                        batchCtl: batch_ctl,
                        batchDate: $("#batchDate").val(),
                        batchTime: $("#timeTransaction").val(),
                        inventory: $("#inventoryList_input").val(),
                        sub_inventory: $("#subInventoryList_input").val(),
                        product: $("#product").val(),
                        quantity: $("#quantity").val(),
                        cost_value: $("#cost").val(),
                        confirm_cost_value: $("#confirmCost").val(),
                        warehouse: $("#warehouse").val(),
                        subWarehouse: $("#subWarehouse").val(),
                        productId: $("#productId").val(),
                        batchHidden: $("input[name='isBatchControl']:checked").val()
                        //delete: '<a  href="javascript:deleteGridRow(' + subAreaId + ')">delete[' + cross_string + ']</a>'
                    }
                    gridId.addRowData(subAreaId, data, "first");
                    subAreaId++;
                    gridCollection = gridId.jqGrid('getRowData');
                }
            }
            else {
                var msg={"class":"com.docu.common.Message","messageBody":["Please check the cost and confirm cost in same"],"messageTitle":"Message","type":0}
                MessageRenderer.render(msg);
            }
        }

        executeAjaxFinishGoodStock();

        return true
    }

    function deleteGridRow(id) {
        $('#jqgrid-grid-finishGoodStock').jqGrid('delRowData', id);
    }

    function executeAjaxFinishGoodStock() {
        if (!executePreConditionFinishGoodStock()) {
            return false;
        }

        var allIds = {};
        var gridCollection = jQuery("#jqgrid-grid-finishGoodStock").jqGrid('getRowData');
        var dataLength = gridCollection.length;
        for (var i = 0; i < 1; i++) {
            allIds['items.finishGood[' + i + '.finishProduct.id'] = $("#productId").val();
            allIds['items.finishGood[' + i + '.quantity'] = $("#quantity").val();
            allIds['items.finishGood[' + i + '.cost'] = $("#cost").val();
            allIds['items.finishGood[' + i + '.confirmCost'] = $("#confirmCost").val();
        }


        var actionUrl = null;
        actionUrl = "${request.contextPath}/${params.controller}/create";
        if(gridCollection.length > 0){
            allIds['batchNo'] = $("#batchNo").val();
            allIds['isBatchControl'] = $("input[name='isBatchControl']:checked").val();
            allIds['dateTransaction'] = $("#batchDate").val();
            allIds['timeTransaction'] = $("#timeTransaction").val();
            allIds['warehouse.id'] = $("#warehouse").val();
            allIds['subWarehouse.id'] = $("#subWarehouse").val();
            allIds['finishProductId'] = $("#productId").val();
            SubmissionLoader.showTo();
            jQuery.ajax({
                type: 'post',
                data: allIds,
                url: actionUrl,
                success: function (data, textStatus) {
                    executePostConditionFinishGoodStock(data);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText)
                },
                complete: function () {
                    SubmissionLoader.hideFrom();
                },
                dataType: 'json'
            });
        }
        else{
            var msg={"class":"com.docu.common.Message","messageBody":["Please select at least one product"],"messageTitle":"Message","type":0}
            MessageRenderer.render(msg);
        }

        return false;
    }

</script>