<style>
#gview_jqgrid-grid2 .ui-state-highlight { background: limegreen !important; }
</style>
<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('Division')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormEditSalesCommission").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormEditSalesCommission").validationEngine('attach');
        reset_form("#gFormEditSalesCommission");

        $("#jqgrid-grid2").jqGrid({
            datatype: "local",
            colNames: [
                'Sl',
                'Name',
                'Branch Commission',
                'Salesman Commission',
                'Delete',
            ],
            colModel: [
                {name: 'id', index: 'id', width: 80, align: 'left'},
                {name: 'sales_commission_name', index: 'sales_commission_name', width: 200, align: 'left'},
                {name: 'branch_commission', index: 'branch_commission', width: 200, align: 'left'},
                {name: 'sales_man_commission', index: 'sales_man_commission', width: 200, align: 'left'},
                {name: 'delete', index:'delete', width:80, align:'center', sortable:false}
            ],
//            rowNum: 10,
//            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
//            pager: '#jqgrid-pager',
            rowNum: -1,
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Sales Commission List",
            autowidth: false,
            height: true,
            scrollOffset: 0,
            loadComplete: function (data) {
                $.each(data.rows, function (i, item) {
                    var rowData = $("#jqgrid-grid2").jqGrid('getRowData', data.rows[i].id);//' + '\'' +aData.id + '\', \'' + aData.woNumber + '\' ' + data.rows[i].id + '
                    rowData.delete = '<a  href="javascript:deleteSalesCommission(' + data.rows[i].id + ')" class="ui-icon ui-icon-trash" title="Change Item Quantity"></a>';
                    $('#jqgrid-grid2').jqGrid('setRowData', data.rows[i].id, rowData);

                });
            },
            onSelectRow: function (rowid, status) {
                    loadSalesManGridByDp(rowid);

            }
        });

        $("#jqgrid-grid").jqGrid({
            datatype: "local",
            colNames: [
                'Sl',
                'CID',
                'SID',
                'Customer Name',
                'Customer Code',
                'Effective From',
                'Effective To',
                'Edit Product',
                'Delete'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 100, align: 'left'},
                {name: 'cid', index: 'cid', width: 100, align: 'left',hidden:true},
                {name: 'sid', index: 'sid', width: 100, align: 'left'},
                {name: 'cname', index: 'cname', width: 200, align: 'left'},
                {name: 'code', index: 'code', width: 150, align: 'left'},
                {name: 'effectiveFrom', index: 'effectiveFrom', width: 150, align: 'left'},
                {name: 'effectiveTo', index: 'effectiveTo', width: 150, align: 'left'},
                {name: 'edit', index:'edit', width:80, align:'center', sortable:false},
                {name: 'delete', index:'delete', width:80, align:'center', sortable:false}
            ],
//            rowNum: 10,
//            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
//            pager: '#jqgrid-pager',
            rowNum: -1,
            sortname: 'cid',
            viewrecords: true,
            sortorder: "desc",
            caption: "Customer List",
            autowidth: false,
            height: true,
            scrollOffset: 0,
            loadComplete: function (data) {
                $.each(data.rows, function (i, item) {
                var rowData = $("#jqgrid-grid").jqGrid('getRowData', data.rows[i].id);//' + '\'' +aData.id + '\', \'' + aData.woNumber + '\' ' + data.rows[i].id + '
                rowData.edit = '<a  href="javascript:updateProductSalesCommissionPopup(' + data.rows[i].id + ')" class="ui-icon ui-icon-pencil" title="Change Item Quantity"></a>';
                rowData.delete = '<a  onclick="deleteCustomerSalesCommission(' + data.rows[i].id + ')" class="ui-icon ui-icon-trash" title="Delete"></a>';
                $('#jqgrid-grid').jqGrid('setRowData', data.rows[i].id, rowData);

            });

            },
            onSelectRow: function (rowid, status) {

            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });


        %{--$("#jqgrid-grid2").jqGrid({--}%
            %{--url: '${resource(dir:'salesCommission', file:'loadProductGrid')}',--}%
            %{--datatype: "json",--}%
            %{--colNames: [--}%
                %{--'Product ID',--}%
                %{--'Product Name'--}%
            %{--],--}%
            %{--colModel: [--}%
                %{--{name: 'productInfoId', index: 'productInfoId', width: 90, align: 'left'},--}%
                %{--{name: 'productInfo', index: 'productInfo', width: 340, align: 'left'}--}%
            %{--],--}%
%{--//            pager: '#jqgrid-pager2',--}%
            %{--rowNum: -1,--}%
            %{--sortname: 'productInfoId',--}%
            %{--viewrecords: true,--}%
            %{--sortorder: "desc",--}%
            %{--caption: "Product List",--}%
            %{--autowidth: false,--}%
            %{--height: true,--}%
            %{--multiselect: true,--}%
            %{--scrollOffset: 0,--}%
            %{--loadComplete: function () {--}%
            %{--},--}%
            %{--onSelectRow: function (rowid, status) {--}%

            %{--}--}%
        %{--});--}%
//        $("#jqgrid-grid2").jqGrid('navGrid', '#jqgrid-pager2', {edit: false, add: false, del: false, search: false});
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
                        reset_form("#gFormEditSalesCommission");
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if (XMLHttpRequest.status = 0) {
                        jQuery("#jqgrid-grid").clearGridData();
                        reset_form("#gFormEditSalesCommission");
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
//        loadSalesManGridByDp(dpId);
        loadSalesCommissionGridByDp(dpId)
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
    function loadSalesCommissionGridByDp(dpId) {
        if (dpId) {
            $("#jqgrid-grid2").jqGrid().setGridParam(
                    {url: "${resource(dir:'salesCommission', file:'listSalesCommissionByDP')}?" + 'dpId=' + dpId,
                        datatype: "json"
                    }).trigger("reloadGrid", [
                {page: 1}
            ]);

        } else {
            $("#jqgrid-grid2").jqGrid('clearGridData');
        }
    }
    function loadSalesManGridByDp(scId) {
        if (scId) {
            $("#jqgrid-grid").jqGrid().setGridParam(
                    {url: "${resource(dir:'salesCommission', file:'listCustomerBySalesCommissionDP')}?" + 'scId=' + scId,
                    datatype: "json"
                    }).trigger("reloadGrid", [
                {page: 1}
            ]);

        } else {
            $("#jqgrid-grid").jqGrid('clearGridData');
        }
    }
function updateProductSalesCommissionPopup(id){
//    alert(id);
//    alert(cId);
    $.ajax({
        type: "POST",
        url: "${resource(dir:'salesCommission', file:'updateProductSalesCommissionPopup')}?cscId=" + id,
        beforeSend: function (jqXHR, settings) {
            $("#loader_icon").show();
        },
        success: function (html) {
            $.fancybox(html);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if(XMLHttpRequest.status = 0){
                MessageRenderer.renderErrorText("Network Problem: Time out");
            }else{
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
            }
        },
        complete: function () {
            $("#loader_icon").hide();
        },
        dataType: 'html'
    });
}

function deleteCustomerSalesCommission(cscId){
//        alert(pid);
        SubmissionLoader.showTo();
        jQuery.ajax({
            type:'post',
            data: {cscId:cscId},
            url: "${resource(dir:'salesCommission', file:'deleteCustomerSalesCommission')}",
            success:function(data, textStatus) {
                $("#jqgrid-grid").trigger("reloadGrid");
                MessageRenderer.render(data);
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {

                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);

            },
            complete:function(){
                SubmissionLoader.hideFrom();
            },
            dataType:'json'
        });
        return false;

}



function deleteSalesCommission(salesCommissionId){
//    alert(salesCommissionId);
    jQuery.ajax({
        type: 'post',
        url: "${resource(dir:'salesCommission', file:'deleteCheckSalesCommission')}?salesCommissionId=" + salesCommissionId,
        success: function (data) {
            if(data.type == 0){
                MessageRenderer.render(data);
            }
            else{
                $("#jqgrid-grid2").trigger("reloadGrid");
                MessageRenderer.render(data);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
        },
        dataType: 'json'
    });

}




//    function executePreConditionSalesCommission() {
//        if (!$("#gFormSalesCommission").validationEngine('validate')) {
//            return false;
//        }
//        return true;
//    }

    %{--function executeAjaxSalesCommission() {--}%
        %{--if (!executePreConditionSalesCommission()) {--}%
            %{--return false;--}%
        %{--}--}%

        %{--var allData = {};--}%

        %{--allData["territoryId"] = $("#territory").val();--}%
        %{--allData["distributionPointId"] = $("#dp").val();--}%
        %{--allData["branchCommission"] = $("#branchCommission").val();--}%
        %{--allData["salesManCommission"] = $("#salesManCommission").val();--}%

        %{--var allCustomers = [];--}%
        %{--var allProducts = [];--}%
        %{--var rowKey = jQuery("#jqgrid-grid").jqGrid('getGridParam', 'selarrrow');--}%
        %{--var rowKeyProduct = jQuery("#jqgrid-grid2").jqGrid('getGridParam', 'selarrrow');--}%

        %{--if (rowKey != '') {--}%
            %{--for (key in rowKey) {--}%
                %{--allCustomers.push(jQuery("#jqgrid-grid").getRowData(rowKey[key]));--}%
            %{--}--}%
        %{--} else {--}%
            %{--MessageRenderer.render({--}%
                %{--"messageBody": "Please select at least one customer.",--}%
                %{--"messageTitle": "Setup Sales Commission",--}%
                %{--"type": "0"--}%
            %{--});--}%
            %{--return false--}%
        %{--}--}%

        %{--if (rowKeyProduct != '') {--}%
            %{--for (key in rowKeyProduct) {--}%
                %{--allProducts.push(jQuery("#jqgrid-grid2").getRowData(rowKeyProduct[key]));--}%
            %{--}--}%
        %{--} else {--}%
            %{--MessageRenderer.render({--}%
                %{--"messageBody": "Please add at least one product.",--}%
                %{--"messageTitle": "Setup Sales Commission",--}%
                %{--"type": "0"--}%
            %{--});--}%
            %{--return false--}%
        %{--}--}%
        %{--if (allCustomers.length > 0) {--}%
            %{--for (var i = 0; i < allCustomers.length; i++) {--}%
                %{--allData["customerList.customer[" + i + "].customerMaster"] = allCustomers[i].customer_id;--}%
            %{--}--}%
        %{--} else {--}%
            %{--MessageRenderer.render({--}%
                %{--"messageBody": "Please select at least one customer.",--}%
                %{--"messageTitle": "Setup Sales Commission",--}%
                %{--"type": "0"--}%
            %{--});--}%
            %{--return false--}%
        %{--}--}%

        %{--if (allProducts.length > 0) {--}%
            %{--for (var i = 0; i < allProducts.length; i++) {--}%
                %{--allData["productList.product[" + i + "].productId"] = allProducts[i].productInfoId;--}%
            %{--}--}%
        %{--} else {--}%
            %{--MessageRenderer.render({--}%
                %{--"messageBody": "Please add at least one product.",--}%
                %{--"messageTitle": "Setup Sales Commission",--}%
                %{--"type": "0"--}%
            %{--});--}%
            %{--return false--}%
        %{--}--}%

%{--//        alert(JSON.stringify(allProducts));--}%
        %{--var actionUrl = null;--}%
        %{--if ($('#gFormSalesCommission input[name = id]').val()) {--}%
            %{--actionUrl = "${request.contextPath}/${params.controller}/create";--}%
        %{--} else {--}%
            %{--actionUrl = "${request.contextPath}/${params.controller}/create";--}%
        %{--}--}%

        %{--SubmissionLoader.showTo();--}%
        %{--jQuery.ajax({--}%
            %{--type: 'post',--}%
            %{--data: allData,--}%
            %{--url: actionUrl,--}%
            %{--success: function (data, textStatus) {--}%
                %{--executePostConditionSalesCommission(data);--}%
            %{--},--}%
            %{--error: function (XMLHttpRequest, textStatus, errorThrown) {--}%
                %{--if (XMLHttpRequest.status = 0) {--}%
                    %{--$("#jqgrid-grid").clearGridData();--}%
                    %{--$('#jqgrid-grid2').clearGridData();--}%
                    %{--reset_form("#gFormSalesCommission");--}%
                    %{--MessageRenderer.renderErrorText("Network Problem: Time out");--}%
                %{--} else {--}%
                    %{--MessageRenderer.renderErrorText(XMLHttpRequest.responseText);--}%
                %{--}--}%
            %{--},--}%
            %{--complete: function () {--}%
                %{--SubmissionLoader.hideFrom();--}%
            %{--},--}%
            %{--dataType: 'json'--}%
        %{--});--}%
        %{--return false;--}%
    %{--}--}%

//    var rowId = 1;
//    function addNewItemToProductCollectionGrid() {
//        var myGrid = $("#jqgrid-grid2");
//        if ($("#productInfo").val()) {
//            var productInfoId = $("#productInfo").val();
//            var productInfo = $("#productInfo option:selected").text();
//        } else {
//            MessageRenderer.render({
//                "messageBody": "Please select a product.",
//                "messageTitle": "Setup Sales Commission",
//                "type": "0"
//            });
//            return false;
//        }
//
//        if ($('#add-button').val() == 'Update') {
//            var selRowId = myGrid.jqGrid('getGridParam', 'selrow');
//            myGrid.jqGrid('setCell', selRowId, 'productInfoId', productInfoId);
//            myGrid.jqGrid('setCell', selRowId, 'productInfo', productInfo);
//        } else {
//            var dataItem = {
//                productInfo: productInfo,
//                productInfoId: productInfoId,
//                remove: '<a  href="javascript:removeProductRow(' + rowId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
//            };
//            myGrid.addRowData(rowId, dataItem, "last");
//            rowId++;
//        }
//    }

//    function executePostConditionSalesCommission(result) {
//        if (result.type == 1) {
//            $("#jqgrid-grid").clearGridData();
////            $('#jqgrid-grid2').clearGridData();
//            reset_form("#gFormSalesCommission");
//            jQuery('#jqgrid-grid2').trigger('reloadGrid');
//        }
//        MessageRenderer.render(result);
//    }

//    function removeProductRow(id) {
//        $("#jqgrid-grid2").jqGrid("delRowData", id);
//    }
</script>