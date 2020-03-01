<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('Division')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormSalesCommission").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormSalesCommission").validationEngine('attach');
        setDateRangeNoLimit('dateEffectiveFrom','dateEffectiveTo');
        reset_form("#gFormSalesCommission");
        $("#jqgrid-grid").jqGrid({
            datatype: "local",
            colNames: [
                'ID',
                'Customer Name',
                'Customer ID',
                'Legacy ID'
            ],
            colModel: [
                {name: 'customer_id', index: 'customer_id', width: 100, align: 'left', hidden: true},
                {name: 'customer_name', index: 'customer_name', width: 250, align: 'left'},
                {name: 'code', index: 'code', width: 100, align: 'left'},
                {name: 'legacy_id', index: 'legacy_id', width: 100, align: 'left'}
            ],
//            rowNum: 10,
//            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
//            pager: '#jqgrid-pager',
            rowNum: -1,
            sortname: 'customer_name',
            viewrecords: true,
            sortorder: "desc",
            caption: "Customer List",
            autowidth: false,
            height: true,
            multiselect: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {

            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
//        $(".ui-pg-selbox").children().each(function () {
//            if ($(this).val() == -1) {
//                $(this).html('All')
//            }
//        });


        $("#jqgrid-grid2").jqGrid({
            url: '${resource(dir:'salesCommission', file:'loadProductGrid')}',
            datatype: "json",
            colNames: [
                'Product ID',
                'Product Name'
            ],
            colModel: [
                {name: 'productInfoId', index: 'productInfoId', width: 90, align: 'left'},
                {name: 'productInfo', index: 'productInfo', width: 340, align: 'left'}
            ],
//            pager: '#jqgrid-pager2',
            rowNum: -1,
            sortname: 'productInfoId',
            viewrecords: true,
            sortorder: "desc",
            caption: "Product List",
            autowidth: false,
            height: true,
            multiselect: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {

            }
        });
        $("#jqgrid-grid2").jqGrid('navGrid', '#jqgrid-pager2', {edit: false, add: false, del: false, search: false});
//        $(".ui-pg-selbox").children().each(function () {
//            if ($(this).val() == -1) {
//                $(this).html('All')
//            }
//        });

        $('#branchCommission').keypress(function (event) {
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

    function selectTerritory(id) {
        if (!id) {
            MessageRenderer.render({
                "messageBody": "Select Territory!",
                "messageTitle": "Sales Commission",
                "type": "2"
            });
            return false;
        }
        else {
            var options = '<option value="">Please Select...</option>';

            SubmissionLoader.showTo();
            jQuery.ajax({
                type: 'post',
                url: "${resource(dir:'salesCommission', file:'fetchDPDropdownList')}?id=" + id,
                success: function (data) {
                    if (data[0]) {
                        options = '<option value="">Select DP...</option>';
                        $.each(data, function (key, val) {
                            options += '<option value="' + val.id + '">' + val.name + '</option>';
                        });

                        $("#customerName").val('');
                        $("#customerId").val('');
                        jQuery("#jqgrid-grid").clearGridData();
                        $("#dp").html(options);
                    } else {
                        jQuery("#jqgrid-grid").clearGridData();
                        reset_form("#gFormSalesCommission");
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if (XMLHttpRequest.status = 0) {
                        jQuery("#jqgrid-grid").clearGridData();
                        reset_form("#gFormSalesCommission");
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    } else {
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete: function () {
                    SubmissionLoader.hideFrom();
                },
                dataType: 'json'
            });
        }
    }

    function selectDP(dpId) {
        loadDefaultCustomer(dpId);
        loadSalesManGridByDp(dpId);
    }

    function loadDefaultCustomer(dpId){
        if(dpId){
            jQuery.ajax({
                type: 'post',
                url: "${resource(dir:'salesCommission', file:'listDefaultCustomerByDP')}?id=" + dpId,
                success: function (data) {
                    if (data[0]) {
                        $("#customerName").val(data[0].name);
                        $("#customerId").val(data[0].code);
                    } else {
                        $("#customerName").val('');
                        $("#customerId").val('');
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if (XMLHttpRequest.status = 0) {
                        $("#customerName").val('');
                        $("#customerId").val('');
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    } else {
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                dataType: 'json'
            });
        }else{
            $("#customerName").val('');
            $("#customerId").val('');
        }
    }

    function loadSalesManGridByDp(dpId) {
        if (dpId) {
            $("#jqgrid-grid").jqGrid().setGridParam(
                    {url: "${resource(dir:'salesCommission', file:'listCustomerByDP')}?" + 'dpId=' + dpId,
                    datatype: "json"
                    }).trigger("reloadGrid", [
                {page: 1}
            ]);

            %{--jQuery.ajax({--}%
                %{--type: 'post',--}%
                %{--url: "${resource(dir:'salesCommission', file:'listCustomerByDP')}?" + "dpId=" + id,--}%

                %{--success: function (data) {--}%
                    %{--if (data[0]) {--}%
                        %{--jQuery("#jqgrid-grid")--}%
                                %{--.jqGrid('setGridParam',--}%
                                %{--{--}%
                                    %{--datatype: 'local',--}%
                                    %{--data: data--}%
                                %{--})--}%
                                %{--.trigger("reloadGrid");--}%
                    %{--} else {--}%
                        %{--jQuery("#jqgrid-grid").clearGridData();--}%
                    %{--}--}%
                %{--},--}%
                %{--error: function (XMLHttpRequest, textStatus, errorThrown) {--}%
                    %{--if (XMLHttpRequest.status = 0) {--}%
                        %{--jQuery("#jqgrid-grid").clearGridData();--}%
                        %{--MessageRenderer.renderErrorText("Network Problem: Time out");--}%
                    %{--} else {--}%
                        %{--MessageRenderer.renderErrorText(XMLHttpRequest.responseText);--}%
                    %{--}--}%
                %{--},--}%
                %{--complete: function () {--}%
                %{--},--}%
                %{--dataType: 'json'--}%
            %{--});--}%
        } else {
            $("#jqgrid-grid").jqGrid('clearGridData');
        }
    }

    function insertSalesManCommission(val) {
        var val = parseFloat(val);
        if (val <= 100) {
            $('#salesManCommission').val((100 - val).toFixed(2));
        } else {
            $('#salesManCommission').val('');
        }
    }

    function checkBranchCommission() {
        var val = parseFloat($('#branchCommission').val());
        if (val > 100.00) {
            $('#salesManCommission').val('');
            return " * Branch Commission % can’t be more than 100.00";
        }
    }

    function executePreConditionSalesCommission() {
        if (!$("#gFormSalesCommission").validationEngine('validate')) {
            return false;
        }
        return true;
    }

    function executeAjaxSalesCommission() {
        if (!executePreConditionSalesCommission()) {
            return false;
        }

        var allData = {};

        allData["territoryId"] = $("#territory").val();
        allData["distributionPointId"] = $("#dp").val();
        allData["branchCommission"] = $("#branchCommission").val();
        allData["salesManCommission"] = $("#salesManCommission").val();
        allData["dateEffectiveFrom"] = $("#dateEffectiveFrom").val();
        allData["dateEffectiveTo"] = $("#dateEffectiveTo").val();
        allData["salesCommissionName"] = $("#salesCommissionName").val();

        var allCustomers = [];
        var allProducts = [];
        var rowKey = jQuery("#jqgrid-grid").jqGrid('getGridParam', 'selarrrow');
        var rowKeyProduct = jQuery("#jqgrid-grid2").jqGrid('getGridParam', 'selarrrow');

        if (rowKey != '') {
            for (key in rowKey) {
                allCustomers.push(jQuery("#jqgrid-grid").getRowData(rowKey[key]));
            }
        } else {
            MessageRenderer.render({
                "messageBody": "Please select at least one customer.",
                "messageTitle": "Setup Sales Commission",
                "type": "0"
            });
            return false
        }

        if (rowKeyProduct != '') {
            for (key in rowKeyProduct) {
                allProducts.push(jQuery("#jqgrid-grid2").getRowData(rowKeyProduct[key]));
            }
        } else {
            MessageRenderer.render({
                "messageBody": "Please add at least one product.",
                "messageTitle": "Setup Sales Commission",
                "type": "0"
            });
            return false
        }
        if (allCustomers.length > 0) {
            for (var i = 0; i < allCustomers.length; i++) {
                allData["customerList.customer[" + i + "].customerMaster"] = allCustomers[i].customer_id;
            }
        } else {
            MessageRenderer.render({
                "messageBody": "Please select at least one customer.",
                "messageTitle": "Setup Sales Commission",
                "type": "0"
            });
            return false
        }

        if (allProducts.length > 0) {
            for (var i = 0; i < allProducts.length; i++) {
                allData["productList.product[" + i + "].productId"] = allProducts[i].productInfoId;
            }
        } else {
            MessageRenderer.render({
                "messageBody": "Please add at least one product.",
                "messageTitle": "Setup Sales Commission",
                "type": "0"
            });
            return false
        }

//        alert(JSON.stringify(allProducts));
        var actionUrl = null;
        if ($('#gFormSalesCommission input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: allData,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionSalesCommission(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status = 0) {
                    $("#jqgrid-grid").clearGridData();
                    $('#jqgrid-grid2').clearGridData();
                    reset_form("#gFormSalesCommission");
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else {
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

    var rowId = 1;
    function addNewItemToProductCollectionGrid() {
        var myGrid = $("#jqgrid-grid2");
        if ($("#productInfo").val()) {
            var productInfoId = $("#productInfo").val();
            var productInfo = $("#productInfo option:selected").text();
        } else {
            MessageRenderer.render({
                "messageBody": "Please select a product.",
                "messageTitle": "Setup Sales Commission",
                "type": "0"
            });
            return false;
        }

        if ($('#add-button').val() == 'Update') {
            var selRowId = myGrid.jqGrid('getGridParam', 'selrow');
            myGrid.jqGrid('setCell', selRowId, 'productInfoId', productInfoId);
            myGrid.jqGrid('setCell', selRowId, 'productInfo', productInfo);
        } else {
            var dataItem = {
                productInfo: productInfo,
                productInfoId: productInfoId,
                remove: '<a  href="javascript:removeProductRow(' + rowId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
            };
            myGrid.addRowData(rowId, dataItem, "last");
            rowId++;
        }
    }

    function executePostConditionSalesCommission(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").clearGridData();
//            $('#jqgrid-grid2').clearGridData();
            reset_form("#gFormSalesCommission");
            jQuery('#jqgrid-grid2').trigger('reloadGrid');
        }
//        alert(result.messageBody);
//        console.log(result);
        MessageRenderer.render(result);
    }

    function removeProductRow(id) {
        $("#jqgrid-grid2").jqGrid("delRowData", id);
    }
</script>