<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $("#gFormMiscellaneousTransactions").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        showEntertainmentDp();
        var enDpId=document.getElementById('entertainmentDistributionPoint').value
        if(enDpId){
            getEntertainmentInventoryListByDp(enDpId)
        }

        $("#gFormMiscellaneousTransactions").validationEngine('attach');
        //reset_form("#gFormMiscellaneousTransactions");


        $('#selectEntertainmentProduct').blur(function () {
            if ($('#selectEntertainmentProduct').val() == '') {
                $('#entertainmentProduct').val("");
            }
        });

        jQuery('#selectEntertainmentProduct').autocomplete({
            minLength: '1',
            source: function (request, response) {
                if ($('#entertainmentSubInventory').val()) {
                    $('#popEmpDetails').html("");
                    var data = {searchKey: request.term};
                    var url = "${request.contextPath}/${params.controller}/getProductListBySubInventory?subInventoryId=" + $('#entertainmentSubInventory').val() + "&query=" + $('#selectEntertainmentProduct').val()+"&dpId=" + $('#entertainmentDistributionPoint').val();
                    DocuAutoComplete.setSpinnerSelector('selectEntertainmentProduct').execute(response, url, data, function (item) {
                        item['label'] = item['name'] + " [" + item['code'] + "]";
                        item['value'] = item['label'];
                        return item;
                    });
                } else {
                    MessageRenderer.render({
                        "messageBody": "Please select a sub inventory.",
                        "messageTitle": "Miscellaneous Transactions",
                        "type": "0"
                    });
                    return false;
                }
            },
            select: function (event, ui) {
                EntertainmentProductInfo.setProductInformation(ui.item.id, ui.item.value);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var productDetails = "";
            if (item.type) {
                productDetails = '<div style="font-size: 9px; color:#326E93;">' + " Name: " + item.name + "[" + item.code + "]" + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + productDetails).appendTo(ul);
        };

        $("#jqgrid-grid-entertainmentProducts").jqGrid({
            datatype: "local",
            colNames: [
                'S/L No.',
                'dpId',
                'departmentId',
                'inventoryId',
                'subInventoryId',
                'productId',
                'Product Name',
                'Quantity',
                'remarks'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: true, align: 'center'},
                {name: 'dpId', index: 'dpId', width: 0, hidden: true},
                {name: 'departmentId', index: 'departmentId', width: 0, hidden: true},
                {name: 'inventoryId', index: 'inventoryId', width: 0, hidden: true},
                {name: 'subInventoryId', index: 'subInventoryId', width: 0, hidden: true},
                {name: 'productId', index: 'productId', width: 0, hidden: true},
                {name: 'productName', index: 'productName', width: 100},
                {name: 'entertainmentQty', index: 'entertainmentQty', width: 80},
                {name: 'remarks', index: 'remarks', width: 80, hidden: true}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-entertainmentProducts',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Products",
            autowidth: true,
            autoheight: true,
            height: 80,
            scrollOffset: 17,
            altRows: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
            }
        });

        $('#entertainmentQty').keypress(function (event) {
            if ((event.which != 46 || $(this).val().indexOf('.') != -1) &&
                    ((event.which < 48 || event.which > 57) &&
                    (event.which != 0 && event.which != 8))) {
                event.preventDefault();
            }

            var text = $(this).val();

            if ((text.indexOf('.') != -1) &&
                    (text.substring(text.indexOf('.')).length > 2) &&
                    (event.which != 0 && event.which != 8) &&
                    ($(this)[0].selectionStart >= text.length - 2)) {
                event.preventDefault();
            }
        });

    });

    function executePreConditionEntertainmentMiscellaneousTransactions() {
        // trim field vales before process.
        trim_form();
        var products = '';
        products = $("#jqgrid-grid-entertainmentProducts").jqGrid('getDataIDs');
        if (products == '') {
            MessageRenderer.render({
                "messageBody": "No Entertainment Product and Quantity found.",
                "messageTitle": "Miscellaneous Transactions",
                "type": "0"
            });
            return false;
        }
        return true;
    }
    function executeAjaxEntertainmentMiscellaneousTransactions() {
        if (!executePreConditionEntertainmentMiscellaneousTransactions()) {
            return false;
        }
        var dataList = $("#jqgrid-grid-entertainmentProducts").jqGrid('getRowData');
        var allData = {};
        for (var i = 0; i < dataList.length; i++) {
            allData['products.item[' + i + '].distributionPoint.id'] = dataList[i].dpId;
            allData['products.item[' + i + '].department.id'] = dataList[i].departmentId;
            allData['products.item[' + i + '].inventory.id'] = dataList[i].inventoryId;
            allData['products.item[' + i + '].subInventory.id'] = dataList[i].subInventoryId;
            allData['products.item[' + i + '].product.id'] = dataList[i].productId;
            allData['products.item[' + i + '].entertainmentQty'] = dataList[i].entertainmentQty;
            allData['products.item[' + i + '].remarks'] = dataList[i].remarks;
        }

        var actionUrl = null;
        if ($('#gFormMiscellaneousTransactions input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/createEntertainment";
        }

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: allData,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionEntertainmentMiscellaneousTransactions(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-entertainmentProducts").jqGrid("clearGridData");
                    reset_form('#gFormMiscellaneousTransactions');
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
    function executePostConditionEntertainmentMiscellaneousTransactions(result) {
        if (result.type == 1) {
            $("#jqgrid-grid-entertainmentProducts").jqGrid("clearGridData");
            reset_form('#gFormMiscellaneousTransactions');
        }
        MessageRenderer.render(result);
    }

    function reset_form(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = '';
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
    function showEntertainmentDp() {
        var size;
        var options = '';
        if ('${dpList}' != '') {
            size = ${dpSize};
            if(size==1){
                options = '<option value="' + ${dpList}[0].id + '">' + ${dpList}[0].name + '</option>';
            }else{
                options = '<option value="">Please Select</option>';
                for (var i = 0; i < size; i++) {
                    options += '<option value="' + ${dpList}[i].id + '">' + ${dpList}[i].name + '</option>';
                }
            }
           $("#entertainmentDistributionPoint").html(options);
        }
    }
    function getEntertainmentInventoryListByDp(dpId) {
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {dpId: dpId},
            url: '${request.contextPath}/${params.controller}/getInventoryListByDp',
            success: function (data, textStatus) {
                if (data) {
                    $('#entertainmentInventory option').remove();
                    $('#entertainmentInventory').append('<option value="">' + "Select Inventory..." + '</option>');
                    for (key in data) {
                        $('#entertainmentInventory').append('<option value="' + data[key].id + '">' + data[key].name + '</option>');
                    }
                } else {
                    $('#entertainmentInventory option').remove();
                    $('#entertainmentInventory').append('<option value="">' + "Select Inventory..." + '</option>');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $('#entertainmentInventory option').remove();
                    $('#entertainmentInventory').append('<option value="">' + "Select Inventory..." + '</option>');
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    function getEntertainmentSubInventoryLisByInventory(id) {
        if(!id){
            return false;
        }
        var salable = '[Salable]';
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {inventoryId: id},
            url: '${request.contextPath}/${params.controller}/getSubInventoryListByInventory',
            success: function (data, textStatus) {
                if (data) {
                    $('#entertainmentSubInventory option').remove();
                    $('#entertainmentSubInventory').append('<option value="">' + "Select Sub Inventory..." + '</option>');
                    for (key in data) {
                        $('#entertainmentSubInventory').append('<option value="' + data[key].id + '">' + data[key].name + ' ['+data[key].type+']'  + '</option>');
                    }
                } else {
                    $('#entertainmentSubInventory option').remove();
                    $('#entertainmentSubInventory').append('<option value="">' + "Select Sub Inventory..." + '</option>');
                }
                $("#entertainmentSubInventory option:contains(" + salable + ")").attr('selected', 'selected');
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $('#entertainmentSubInventory option').remove();
                    $('#entertainmentSubInventory').append('<option value="">' + "Select Sub Inventory..." + '</option>');
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                getEntertainmentProductListBySubInventory($('#entertainmentSubInventory').val());
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    function getEntertainmentProductListBySubInventory(id) {
        if(!id){
            return false;
        }
//        alert($('#distributionPoint').val());
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {subInventoryId: id, dpId: $('#entertainmentDistributionPoint').val()},
            url: '${request.contextPath}/${params.controller}/getProductListBySubInventory',
            success: function (data, textStatus) {
                if (data) {
                    $('#entertainmentProduct option').remove();
                    $('#entertainmentProduct').append('<option value="">' + "Select Product..." + '</option>');
                    for (key in data) {
                        $('#entertainmentProduct').append('<option value="' + data[key].id + '">' + data[key].name + '</option>');
                    }
                } else {
                    $('#entertainmentProduct option').remove();
                    $('#entertainmentProduct').append('<option value="">' + "Select Sub Product..." + '</option>');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $('#entertainmentProduct option').remove();
                    $('#entertainmentProduct').append('<option value="">' + "Select Sub Product..." + '</option>');
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    var entertainmentProductRowId = 1;
    function addEntertainmentProductIntoGrid() {
        var myGrid = $("#jqgrid-grid-entertainmentProducts");

        var rowData = myGrid.getRowData(1);

        var dpId = $("#entertainmentDistributionPoint").val();
        var departmentId = $("#entertainmentDepartment").val();
        var inventoryId = $("#entertainmentInventory").val();
        var subInventoryId = $("#entertainmentSubInventory").val();
        var productId = $("#entertainmentProduct").val();
        var productName = $("#selectEntertainmentProduct").val();
        var entertainmentQty = parseFloat($("#entertainmentQty").val());
        var remarks = $("#entertainmentRemarks").val();

        if (!$.isEmptyObject(rowData) && (rowData.departmentId != departmentId)) {
            MessageRenderer.render({
                "messageBody": "You can select one department at a time.",
                "messageTitle": "Miscellaneous Transactions",
                "type": "0"
            });
            return false;
        }

        if (departmentId && inventoryId && subInventoryId && productId && productName && entertainmentQty) {
            var dataItem = {
                sl: entertainmentProductRowId,
                dpId: dpId,
                departmentId: departmentId,
                inventoryId: inventoryId,
                subInventoryId: subInventoryId,
                productId: productId,
                productName: productName,
                entertainmentQty: entertainmentQty,
                remarks: remarks
            };
            myGrid.addRowData(entertainmentProductRowId, dataItem, "last");
            entertainmentProductRowId++;
        } else {
            MessageRenderer.render({
                "messageBody": "Please enter data for all required fields.",
                "messageTitle": "Miscellaneous Transactions",
                "type": "0"
            });
            return false;
        }
    }

    $('#search-btn-entertainment-product-id').click(function () {
        if ($('#entertainmentSubInventory').val()) {
            tabIndex = 'entertainment';
            EntertainmentProductInfo.popupProductListPanel($('#entertainmentSubInventory').val());
        }
        else {
            MessageRenderer.render({
                "messageBody": "Please select a sub inventory.",
                "messageTitle": "Miscellaneous Transactions",
                "type": "0"
            });
            return false;
        }
    });

    var EntertainmentProductInfo = {
        productCoreInfoId: null,
        popupProductListPanel: function (subInventoryId) {
            var url = '${resource(dir:'miscellaneousTransactions', file:'getDistributionProductListBySubInventory')}';
            var params = {subInventoryId: subInventoryId, dpId: $('#entertainmentDistributionPoint').val()};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },

        setProductInformation: function (productCoreInfoId, productCoreInfo) {
            SubmissionLoader.showTo();
            $("#selectEntertainmentProduct").val(productCoreInfo);
            $("#entertainmentProduct").val(productCoreInfoId);
            EntertainmentProductInfo.productCoreInfoId = productCoreInfoId;
            SubmissionLoader.hideFrom();
        }
    }

</script>