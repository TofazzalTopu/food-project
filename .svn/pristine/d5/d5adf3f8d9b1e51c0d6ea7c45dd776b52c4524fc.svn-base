<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $("#gFormMiscellaneousTransactions").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });

        showDamageDp();

        var dmDpId=document.getElementById('damageDistributionPoint').value
        if(dmDpId){
            getDamageInventoryListByDp(dmDpId)
        }

        $("#gFormMiscellaneousTransactions").validationEngine('attach');
        //reset_form("#gFormMiscellaneousTransactions");



        $('#selectDamageProduct').blur(function () {
            if ($('#selectDamageProduct').val() == '') {
                $('#damageProduct').val("");
            }
        });

        jQuery('#selectDamageProduct').autocomplete({
            minLength: '1',
            source: function (request, response) {
                if ($('#damageSubInventory').val()) {
                    $('#popEmpDetails').html("");
                    var data = {searchKey: request.term};
                    var url = "${request.contextPath}/${params.controller}/getProductListBySubInventory?subInventoryId=" + $('#damageSubInventory').val() + "&query=" + $('#selectDamageProduct').val() + "&dpId=" +  $('#damageDistributionPoint').val();
                    DocuAutoComplete.setSpinnerSelector('selectDamageProduct').execute(response, url, data, function (item) {
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
                DamageProductInfo.setProductInformation(ui.item.id, ui.item.value);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var productDetails = "";
            if (item.type) {
                productDetails = '<div style="font-size: 9px; color:#326E93;">' + " Name: " + item.name + "[" + item.code + "]" + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + productDetails).appendTo(ul);
        };

        $("#jqgrid-grid-damageProducts").jqGrid({
            datatype: "local",
            colNames: [
                'S/L No.',
                'dpId',
                'inventoryId',
                'subInventoryId',
                'productId',
                'Product Name',
                'Damage Qty',
                'remarks'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: true, align: 'center'},
                {name: 'dpId', index: 'dpId', width: 0, hidden: true},
                {name: 'inventoryId', index: 'inventoryId', width: 0, hidden: true},
                {name: 'subInventoryId', index: 'subInventoryId', width: 0, hidden: true},
                {name: 'productId', index: 'productId', width: 0, hidden: true},
                {name: 'productName', index: 'productName', width: 200},
                {name: 'damageQty', index: 'damageQty', width: 65, align: 'center'},
                {name: 'remarks', index: 'remarks', width: 0, hidden: true}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-damageProducts',
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

        $("#jqgrid-grid-damageInvProducts").jqGrid({
            datatype: "local",
            colNames: [
                'S/L No.',
                'id',
                'Product Name',
                'ID',
                'Available Qty'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: true, align: 'center'},
                {name: 'id', index: 'id', width: 30, hidden: true, align: 'left'},
                {name: 'name', index: 'name', width: 120},
                {name: 'code', index: 'code', width: 130},
                {name: 'quantity', index: 'quantity', width: 65, align: 'center'}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-damageInvProducts',
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
                var ids = $("#jqgrid-grid-damageInvProducts").jqGrid('getDataIDs');
                var i = 1;
                for (key in ids) {
                    $("#jqgrid-grid-damageInvProducts").jqGrid('setRowData', ids[key], {
                        sl: i
                    });
                    i++;
                }
            },
            onSelectRow: function (rowid, status) {
            }
        });

        $('#damageQty').keypress(function (event) {
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

    function executePreConditionDamageMiscellaneousTransactions() {
        // trim field vales before process.
        trim_form();
        var products = '';
        products = $("#jqgrid-grid-damageProducts").jqGrid('getDataIDs');

        var  dmg= $("#damageSubInventory option:selected").text();
        var patt = /Damage/i
        var result = dmg.match(patt);
        if(result == 'Damage'){
            MessageRenderer.renderErrorText("Please deselect Damage from Sub Inventory List");
            return false;
        }
        if (products == '') {
            MessageRenderer.render({
                "messageBody": "No Damage Product and Quantity found.",
                "messageTitle": "Miscellaneous Transactions",
                "type": "0"
            });
            return false;
        }
        return true;
    }
    function executeAjaxDamageMiscellaneousTransactions() {
        if (!executePreConditionDamageMiscellaneousTransactions()) {
            return false;
        }
        var dataList = $("#jqgrid-grid-damageProducts").jqGrid('getRowData');
        var allData = {};
        for (var i = 0; i < dataList.length; i++) {
            allData['products.item[' + i + '].distributionPoint.id'] = dataList[i].dpId;
            allData['products.item[' + i + '].inventory.id'] = dataList[i].inventoryId;
            allData['products.item[' + i + '].subInventory.id'] = dataList[i].subInventoryId;
            allData['products.item[' + i + '].product.id'] = dataList[i].productId;
            allData['products.item[' + i + '].damageQty'] = dataList[i].damageQty;
            allData['products.item[' + i + '].remarks'] = dataList[i].remarks;
        }

        var actionUrl = null;
        if ($('#gFormMiscellaneousTransactions input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/createDamage";
        }

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: allData,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionDamageMiscellaneousTransactions(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-damageProducts").jqGrid("clearGridData");
                    $("#jqgrid-grid-damageInvProducts").jqGrid("clearGridData");
                    reset_form('#gFormMiscellaneousTransactions');
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
        return false;
    }
    function executePostConditionDamageMiscellaneousTransactions(result) {
        if (result.type == 1) {
            $("#jqgrid-grid-damageProducts").jqGrid("clearGridData");
            $("#jqgrid-grid-damageInvProducts").jqGrid("clearGridData");
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

    function getDamageInventoryListByDp(dpId) {
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {dpId: dpId},
            url: '${request.contextPath}/${params.controller}/getInventoryListByDp',
            success: function (data, textStatus) {
                if (data) {
                    $('#damageInventory option').remove();
                    $('#damageInventory').append('<option value="">' + "Select Inventory..." + '</option>');
                    for (key in data) {
                        $('#damageInventory').append('<option value="' + data[key].id + '">' + data[key].name + '</option>');
                    }
                } else {
                    $('#damageInventory option').remove();
                    $('#damageInventory').append('<option value="">' + "Select Inventory..." + '</option>');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $('#damageInventory option').remove();
                    $('#damageInventory').append('<option value="">' + "Select Inventory..." + '</option>');
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

    function showDamageDp() {
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
            $("#damageDistributionPoint").html(options);
        }
    }

    function getDamageSubInventoryLisByInventory(id) {
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
                    $('#damageSubInventory option').remove();
                    $('#damageSubInventory').append('<option value="">' + "Select Sub Inventory..." + '</option>');
                    for (key in data) {
                        $('#damageSubInventory').append('<option value="' + data[key].id + '">' + data[key].name + ' ['+data[key].type+']' + '</option>');
                    }
                } else {
                    $('#damageSubInventory option').remove();
                    $('#damageSubInventory').append('<option value="">' + "Select Sub Inventory..." + '</option>');
                }

                $("#damageSubInventory option:contains(" + salable + ")").attr('selected', 'selected');
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $('#damageSubInventory option').remove();
                    $('#damageSubInventory').append('<option value="">' + "Select Sub Inventory..." + '</option>');
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                getDamageProductListBySubInventory($('#damageSubInventory').val());
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    function getDamageProductListBySubInventory(id) {
        if(!id){
            return false;
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {subInventoryId: id, dpId:  $('#damageDistributionPoint').val()},
            url: '${request.contextPath}/${params.controller}/getProductListBySubInventory',
            success: function (data, textStatus) {
                if (data) {
                    $("#jqgrid-grid-damageInvProducts").clearGridData();
                    $("#jqgrid-grid-damageInvProducts")
                            .jqGrid('setGridParam',
                            {
                                datatype: 'local',
                                data: data
                            })
                            .trigger("reloadGrid");

                    $('#damageProduct option').remove();
                    $('#damageProduct').append('<option value="">' + "Select Product..." + '</option>');
                    for (key in data) {
                        $('#damageProduct').append('<option value="' + data[key].id + '">' + data[key].name + '</option>');
                    }
                } else {
                    $("#jqgrid-grid-damageInvProducts").clearGridData();
                    $('#damageProduct option').remove();
                    $('#damageProduct').append('<option value="">' + "Select Sub Product..." + '</option>');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-damageInvProducts").clearGridData();
                    $('#damageProduct option').remove();
                    $('#damageProduct').append('<option value="">' + "Select Sub Product..." + '</option>');
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

    var damageProductRowId = 1;
    function addDamageProductIntoGrid() {


        var myGrid = $("#jqgrid-grid-damageProducts");

        var dpId = $("#damageDistributionPoint").val();
        var inventoryId = $("#damageInventory").val();
        var subInventoryId = $("#damageSubInventory").val();
        var productId = $("#damageProduct").val();
        var productName = $("#selectDamageProduct").val();
        var damageQty = parseFloat($("#damageQty").val());
        var remarks = $("#damageRemarks").val();

        if (dpId && inventoryId && subInventoryId && productId && productName && damageQty) {
            var dataItem = {
                sl: damageProductRowId,
                dpId: dpId,
                inventoryId: inventoryId,
                subInventoryId: subInventoryId,
                productId: productId,
                productName: productName,
                damageQty: damageQty,
                remarks: remarks
            };
            myGrid.addRowData(damageProductRowId, dataItem, "last");
            damageProductRowId++;
        } else {
            MessageRenderer.render({
                "messageBody": "Please enter data for all required fields.",
                "messageTitle": "Miscellaneous Transactions",
                "type": "0"
            });
            return false;
        }
    }

    $('#search-btn-damage-product-id').click(function () {
        if ($('#damageSubInventory').val()) {
            tabIndex = 'damage';
            DamageProductInfo.popupProductListPanel($('#damageSubInventory').val());
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

    var DamageProductInfo = {
        productCoreInfoId: null,
        popupProductListPanel: function (subInventoryId) {
            var url = '${resource(dir:'miscellaneousTransactions', file:'getDistributionProductListBySubInventory')}';
            var params = {subInventoryId: subInventoryId, dpId: $('#damageDistributionPoint').val()};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },

        setProductInformation: function (productCoreInfoId, productCoreInfo) {
            SubmissionLoader.showTo();
            $("#selectDamageProduct").val(productCoreInfo);
            $("#damageProduct").val(productCoreInfoId);
            DamageProductInfo.productCoreInfoId = productCoreInfoId;
            SubmissionLoader.hideFrom();
        }
    }

</script>