<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $("#gFormMiscellaneousTransactions").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });

        showReturnDp();

        var smDpId=document.getElementById('rtpDistributionPoint').value
        if(smDpId){
            getRtpInventoryListByDp(smDpId)
        }

        $("#gFormMiscellaneousTransactions").validationEngine('attach');
        //reset_form("#gFormMiscellaneousTransactions");

        $('#selectRtpProduct').blur(function () {
            if ($('#selectRtpProduct').val() == '') {
                $('#rtpProduct').val("");
            }
        });

        jQuery('#selectRtpProduct').autocomplete({
            minLength: '1',
            source: function (request, response) {
                if ($('#rtpSubInventory').val()) {
                    $('#popEmpDetails').html("");
                    var data = {searchKey: request.term};
                    var url = "${request.contextPath}/${params.controller}/getProductListBySubInventory?subInventoryId=" + $('#rtpSubInventory').val() + "&query=" + $('#selectRtpProduct').val() + "&dpId=" + $('#rtpDistributionPoint').val();
                    DocuAutoComplete.setSpinnerSelector('selectRtpProduct').execute(response, url, data, function (item) {
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
                RtpProductInfo.setProductInformation(ui.item.id, ui.item.value);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var productDetails = "";
            if (item.type) {
                productDetails = '<div style="font-size: 9px; color:#326E93;">' + " Name: " + item.name + "[" + item.code + "]" + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + productDetails).appendTo(ul);
        };

        $("#jqgrid-grid-rtpProducts").jqGrid({
            datatype: "local",
            colNames: [
                'S/L No.',
                'dpId',
                'inventoryId',
                'subInventoryId',
                'productId',
                'Product Name',
                'Qty',
                'remarks'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 40, sortable: true, align: 'center'},
                {name: 'dpId', index: 'dpId', width: 0, hidden: true},
                {name: 'inventoryId', index: 'inventoryId', width: 0, hidden: true},
                {name: 'subInventoryId', index: 'subInventoryId', width: 0, hidden: true},
                {name: 'productId', index: 'productId', width: 0, hidden: true},
                {name: 'productName', index: 'productName', width: 200},
                {name: 'rtpQty', index: 'rtpQty', width: 65, align: 'center'},
                {name: 'remarks', index: 'remarks', width: 80, hidden: false}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-rtpProducts',
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

        $("#jqgrid-grid-rtpInvProducts").jqGrid({
            datatype: "local",
            colNames: [
                'S/L No.',
                'id',
                'Product Name',
                'ID',
                'Qty'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: true, align: 'center'},
                {name: 'id', index: 'id', width: 30, hidden: true, align: 'left'},
                {name: 'name', index: 'name', width: 120},
                {name: 'code', index: 'code', width: 0, hidden: true},
                {name: 'quantity', index: 'quantity', width: 65, align: 'center'}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-rtpInvProducts',
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
                var ids = $("#jqgrid-grid-rtpInvProducts").jqGrid('getDataIDs');
                var i = 1;
                for (key in ids) {
                    $("#jqgrid-grid-rtpInvProducts").jqGrid('setRowData', ids[key], {
                        sl: i
                    });
                    i++;
                }
            },
            onSelectRow: function (rowid, status) {
            }
        });

        $('#rtpQty').keypress(function (event) {
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

    function executePreConditionRtpMiscellaneousTransactions() {
        // trim field vales before process.
        trim_form();
        var products = '';
        products = $("#jqgrid-grid-rtpProducts").jqGrid('getDataIDs');
        if (products == '') {
            MessageRenderer.render({
                "messageBody": "No Returned Product and Quantity found.",
                "messageTitle": "Miscellaneous Transactions",
                "type": "0"
            });
            return false;
        }
        return true;
    }
    function executeAjaxRtpMiscellaneousTransactions() {
        if (!executePreConditionRtpMiscellaneousTransactions()) {
            return false;
        }
        var dataList = $("#jqgrid-grid-rtpProducts").jqGrid('getRowData');
        var allData = {};
        for (var i = 0; i < dataList.length; i++) {
            allData['products.item[' + i + '].distributionPoint.id'] = dataList[i].dpId;
            allData['products.item[' + i + '].inventory.id'] = dataList[i].inventoryId;
            allData['products.item[' + i + '].subInventory.id'] = dataList[i].subInventoryId;
            allData['products.item[' + i + '].product.id'] = dataList[i].productId;
            allData['products.item[' + i + '].returnQty'] = dataList[i].rtpQty;
            allData['products.item[' + i + '].remarks'] = dataList[i].remarks;
        }

        var actionUrl = null;
        if ($('#gFormMiscellaneousTransactions input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/createRtp";
        }

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: allData,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionRtpMiscellaneousTransactions(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-rtpProducts").jqGrid("clearGridData");
                    $("#jqgrid-grid-rtpInvProducts").jqGrid("clearGridData");
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
    function executePostConditionRtpMiscellaneousTransactions(result) {
        if (result.type == 1) {
            $("#jqgrid-grid-rtpProducts").jqGrid("clearGridData");
            $("#jqgrid-grid-rtpInvProducts").jqGrid("clearGridData");
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

    function showReturnDp() {
        var size;
        var options = '';
        if ('${factoryDpList}' != '') {
            size = ${factoryDpSize};
            //alert(size)
            if(size==1){
                options = '<option value="' + ${factoryDpList}[0].id + '">' + ${factoryDpList}[0].name + '</option>';
            }else{
                options = '<option value="">Please Select</option>';
                for (var i = 0; i < size; i++) {
                    options += '<option value="' + ${factoryDpList}[i].id + '">' + ${factoryDpList}[i].name + '</option>';
                }
            }
           $("#rtpDistributionPoint").html(options);
        }
    }

    function getRtpInventoryListByDp(dpId) {
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {dpId: dpId},
            url: '${request.contextPath}/${params.controller}/getInventoryListByDp',
            success: function (data, textStatus) {
                if (data) {
                    $('#rtpInventory option').remove();
                    $('#rtpInventory').append('<option value="">' + "Select Inventory..." + '</option>');
                    for (key in data) {
                        $('#rtpInventory').append('<option value="' + data[key].id + '">' + data[key].name + '</option>');
                    }
                } else {
                    $('#rtpInventory option').remove();
                    $('#rtpInventory').append('<option value="">' + "Select Inventory..." + '</option>');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $('#rtpInventory option').remove();
                    $('#rtpInventory').append('<option value="">' + "Select Inventory..." + '</option>');

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
    }

    function getRtpSubInventoryLisByInventory(id) {
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
                    $('#rtpSubInventory option').remove();
                    $('#rtpSubInventory').append('<option value="">' + "Select Sub Inventory..." + '</option>');
                    for (key in data) {
                        $('#rtpSubInventory').append('<option value="' + data[key].id + '">' + data[key].name + ' ['+data[key].type+']' + '</option>');
                    }
                } else {
                    $('#rtpSubInventory option').remove();
                    $('#rtpSubInventory').append('<option value="">' + "Select Sub Inventory..." + '</option>');
                }

                $("#rtpSubInventory option:contains(" + salable + ")").attr('selected', 'selected');
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $('#rtpSubInventory option').remove();
                    $('#rtpSubInventory').append('<option value="">' + "Select Sub Inventory..." + '</option>');

                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }
                else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                getRtpProductListBySubInventory($('#rtpSubInventory').val());
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    function getRtpProductListBySubInventory(id) {
        if(!id){
            return false;
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {subInventoryId: id, dpId: $('#rtpDistributionPoint').val()},
            url: '${request.contextPath}/${params.controller}/getProductListBySubInventory',
            success: function (data, textStatus) {
                if (data) {
                    $("#jqgrid-grid-rtpInvProducts").clearGridData();
                    $("#jqgrid-grid-rtpInvProducts")
                            .jqGrid('setGridParam',
                            {
                                datatype: 'local',
                                data: data
                            })
                            .trigger("reloadGrid");

                    $('#rtpProduct option').remove();
                    $('#rtpProduct').append('<option value="">' + "Select Product..." + '</option>');
                    for (key in data) {
                        $('#rtpProduct').append('<option value="' + data[key].id + '">' + data[key].name + '</option>');
                    }
                } else {
                    $("#jqgrid-grid-rtpInvProducts").clearGridData();
                    $('#rtpProduct option').remove();
                    $('#rtpProduct').append('<option value="">' + "Select Sub Product..." + '</option>');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-rtpInvProducts").clearGridData();
                    $('#rtpProduct option').remove();
                    $('#rtpProduct').append('<option value="">' + "Select Sub Product..." + '</option>');

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
    }

    var rtpProductRowId = 1;
    function addRtpProductIntoGrid() {
        var myGrid = $("#jqgrid-grid-rtpProducts");

        var dpId = $("#rtpDistributionPoint").val();
        var inventoryId = $("#rtpInventory").val();
        var subInventoryId = $("#rtpSubInventory").val();
        var productId = $("#rtpProduct").val();
        var productName = $("#selectRtpProduct").val();
        var rtpQty = parseFloat($("#rtpQty").val());
        var remarks = $("#rtpRemarks").val();

        if (dpId && inventoryId && subInventoryId && productId && productName && damageQty) {
            var dataItem = {
                sl: rtpProductRowId,
                dpId: dpId,
                inventoryId: inventoryId,
                subInventoryId: subInventoryId,
                productId: productId,
                productName: productName,
                rtpQty: rtpQty,
                remarks: remarks
            };
            myGrid.addRowData(rtpProductRowId, dataItem, "last");
            rtpProductRowId++;
        } else {
            MessageRenderer.render({
                "messageBody": "Please enter data for all required fields.",
                "messageTitle": "Miscellaneous Transactions",
                "type": "0"
            });
            return false;
        }
    }

    $('#search-btn-rtp-product-id').click(function () {
        if ($('#rtpSubInventory').val()) {
            tabIndex = 'rtp';
            RtpProductInfo.popupProductListPanel($('#rtpSubInventory').val());
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

    var RtpProductInfo = {
        productCoreInfoId: null,
        popupProductListPanel: function (subInventoryId) {
            var url = '${resource(dir:'miscellaneousTransactions', file:'getDistributionProductListBySubInventory')}';
            var params = {subInventoryId: subInventoryId, dpId: $('#rtpDistributionPoint').val()};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },

        setProductInformation: function (productCoreInfoId, productCoreInfo) {
            SubmissionLoader.showTo();
            $("#selectRtpProduct").val(productCoreInfo);
            $("#rtpProduct").val(productCoreInfoId);
            RtpProductInfo.productCoreInfoId = productCoreInfoId;
            SubmissionLoader.hideFrom();
        }
    }

</script>