<script type="text/javascript" language="Javascript">
    var productSeq = 1;
    var promotionalProductSeq = 1;
    var deletedIds = [];
    var deleteSize = 0;

    $(document).ready(function () {
        $('#ui-widget-header-text').html('TerritoryConfiguration');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormSetupBonusPromotion").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormSetupBonusPromotion").validationEngine('attach');

        $(".date-picker").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });

        $('.date-picker').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

        //        Eligible Customer Category
        $("#tb-jqgrid-eligible-cc-grid").jqGrid({
            datatype: "local",
            data:${customerCategoryList},

            colNames: [
                'All Category',
                'Category ID'
            ],
            colModel: [
                {name: 'name', index: 'categoryName', width: 150, align: 'left'},
                {name: 'id', index: 'categoryId', width:0, hidden:true}

            ],

            rowNum: ${customerCategoryListSize},
            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Customer Category",
            autowidth: false,
            height: 160,
            multiselect: true,
            scrollOffset: 28,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                $('#cb_tb-jqgrid-eligible-cc-grid').attr('checked',false);
                var selectedTerritoryRows = $('#tb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#tb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedCcRows = $('#tb-jqgrid-eligible-cc-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);
                var ccIds = getRowIdsAll(selectedCcRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds, ccIds:ccIds},
                    url:  "${request.contextPath}/${params.controller}/getCcWiseCustomers",
                    success: function (data, textStatus) {
                        if(data.customerList[0]){
                            jQuery("#tb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                            MessageRenderer.renderErrorText("Network Problem: Time out");
                        } else{
                            MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                        }
                    },
                    complete: function () {
                        SubmissionLoader.hideFrom();
                    },
                    dataType: 'json'
                });
            },
            onSelectAll: function(aRowids, status) {
                jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                var selectedTerritoryRows = $('#tb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#tb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedCcRows = $('#tb-jqgrid-eligible-cc-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);
                var ccIds = getRowIdsAll(selectedCcRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds, ccIds:ccIds},
                    url:  "${request.contextPath}/${params.controller}/getCcWiseCustomers",
                    success: function (data, textStatus) {
                        if(data.customerList[0]){
                            jQuery("#tb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                            MessageRenderer.renderErrorText("Network Problem: Time out");
                        } else{
                            MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                        }
                    },
                    complete: function () {
                        SubmissionLoader.hideFrom();
                    },
                    dataType: 'json'
                });
            }
        });

        //        Eligible Territory
        $("#tb-jqgrid-eligible-territory-grid").jqGrid({
            datatype: "local",
            data:${territoryConfigurationList},

            colNames: [
                'All Territory',
                'Territory ID'
            ],
            colModel: [
                {name: 'name', index: 'name', width: 150, align: 'left'},
                {name: 'id', index: 'id', width:0, hidden:true}

            ],

            rowNum: ${territoryConfigurationListSize},
            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Territory",
            autowidth: false,
            height: 160,
            multiselect: true,
            scrollOffset: 28,
            loadComplete: function () {

            },
            onSelectRow: function (rowid, status) {
                jQuery("#tb-jqgrid-eligible-geo-grid").clearGridData();
                jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                $('#cb_tb-jqgrid-eligible-territory-grid').attr('checked',false);
//                var selectedRows = $('#tb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
//                var ids = getRowIdsAll(selectedRows);

                var selectedTerritoryRows = $('#tb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#tb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedCcRows = $('#tb-jqgrid-eligible-cc-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);
                var ccIds = getRowIdsAll(selectedCcRows);

//                if(ids==''){
//                    return false;
//                }

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds,geoIds:geoIds,ccIds:ccIds},
                    url:  "${request.contextPath}/${params.controller}/getTerritoryWiseGeoAndCustomers",
                    success: function (data, textStatus) {
                        if(data.geoList[0]) {
                            jQuery("#tb-jqgrid-eligible-geo-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.geoList.length,
                                        datatype: 'local',
                                        data: data.geoList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-geo-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#tb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#tb-jqgrid-eligible-geo-grid").clearGridData();
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                            MessageRenderer.renderErrorText("Network Problem: Time out");
                        } else{
                            MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                        }
                    },
                    complete: function () {
                        SubmissionLoader.hideFrom();
                    },
                    dataType: 'json'
                });
            },
            onSelectAll: function(aRowids, status) {
                jQuery("#tb-jqgrid-eligible-geo-grid").clearGridData();
                jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                var selectedTerritoryRows = $('#tb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#tb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedCcRows = $('#tb-jqgrid-eligible-cc-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);
                var ccIds = getRowIdsAll(selectedCcRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds,geoIds:geoIds,ccIds:ccIds},
                    url: "${request.contextPath}/${params.controller}/getTerritoryWiseGeoAndCustomers",
                    success: function (data, textStatus) {
                        if(data.geoList[0]) {
                            jQuery("#tb-jqgrid-eligible-geo-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.geoList.length,
                                        datatype: 'local',
                                        data: data.geoList
                                    })
                                    .trigger("reloadGrid");

                        }else{
                            jQuery("#tb-jqgrid-eligible-geo-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#tb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#tb-jqgrid-eligible-geo-grid").clearGridData();
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                            MessageRenderer.renderErrorText("Network Problem: Time out");
                        } else{
                            MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                        }                    },
                    complete: function () {
                        SubmissionLoader.hideFrom();
                    },
                    dataType: 'json'
                });
            }
        });

//        Eligible Geographic Location
        $("#tb-jqgrid-eligible-geo-grid").jqGrid({
            datatype: "local",
            data:'',

            colNames: [
                'All Geo Location',
                'GEO ID'
            ],
            colModel: [
                {name: 'name', index: 'id', width: 150, align: 'left'},
                {name: 'id', index: 'id', width:0, hidden:true}

            ],

            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Geographic Location",
            autowidth: false,
            height: 160,
            multiselect: true,
            scrollOffset: 28,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                $('#cb_tb-jqgrid-eligible-geo-grid').attr('checked',false);

                var selectedTerritoryRows = $('#tb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#tb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedCcRows = $('#tb-jqgrid-eligible-cc-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);
                var ccIds = getRowIdsAll(selectedCcRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds, ccIds:ccIds},
                    url:  "${request.contextPath}/${params.controller}/getGeoWiseTpAndCustomers",
                    success: function (data, textStatus) {
                        if(data.customerList[0]){
                            jQuery("#tb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#tb-jqgrid-eligible-pt-grid").clearGridData();
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                            MessageRenderer.renderErrorText("Network Problem: Time out");
                        } else{
                            MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                        }
                    },
                    complete: function () {
                        SubmissionLoader.hideFrom();
                    },
                    dataType: 'json'
                });
            },
            onSelectAll: function(aRowids, status) {
                jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                var selectedTerritoryRows = $('#tb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#tb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedCcRows = $('#tb-jqgrid-eligible-cc-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);
                var ccIds = getRowIdsAll(selectedCcRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds, ccIds:ccIds},
                    url:  "${request.contextPath}/${params.controller}/getGeoWiseTpAndCustomers",
                    success: function (data, textStatus) {
                        if(data.customerList[0]){
                            jQuery("#tb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
//                            jQuery("#tb-jqgrid-eligible-pt-grid").clearGridData();
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                            MessageRenderer.renderErrorText("Network Problem: Time out");
                        } else{
                            MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                        }
                    },
                    complete: function () {
                        SubmissionLoader.hideFrom();
                    },
                    dataType: 'json'
                });
            }
        });

//        Eligible Customers
        $("#tb-jqgrid-eligible-customers-grid").jqGrid({
            datatype: "local",
            data:'',

            colNames: [
                'Customer',
                'ID',
                'ID',
                'Remove'
            ],
            colModel: [
                {name: 'name', index: 'name', width: 140, align: 'left'},
                {name: 'id', index: 'id', width:50, hidden:true},
                {name: 'code', index: 'code', width:80},
                {name: 'remove', index: 'remove', width:60, hidden: true}

            ],
            rowNum: 10,
            rowList: [10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#tb-jqgrid-eligible-customers-pager',
            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Customers",
            autowidth: false,
            height: 160,
            multiselect: true,
            scrollOffset: 32,
            loadComplete: function () {
                var rowIds = $("#tb-jqgrid-eligible-customers-grid").jqGrid('getDataIDs')
                for(key in rowIds){
                    $("#tb-jqgrid-eligible-customers-grid").jqGrid('setRowData', rowIds[key],{
                        remove: '<a  href="javascript:removeTbCustomersRow(' + rowIds[key] + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                    })
                }
            },
            onSelectRow: function (rowid, status) {
            },
            onSelectAll: function(aRowids, status) {
            }
        });
        $("#tb-jqgrid-eligible-customers-grid").jqGrid('navGrid', '#tb-jqgrid-eligible-customers-pager', {edit: false, add: false, del: false, search: false});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });

//      Eligible Products
        $("#jqgrid-eligible-products-grid").jqGrid({
            datatype: "local",
            data:[],

            colNames: [
                'SL',
                'ID',
                'Stock ID',
                'Product Name',
                'Product ID',
                'Purchase Quantity',
                'Rate',
                'Quantity Limit',
                'Remove'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 40, align: 'left'},
                {name: 'detailId', index: 'detailId', width: 50, align: 'left', hidden:true},
                {name: 'stockId', index: 'stockId', width: 70, align: 'left', hidden:true},
                {name: 'productName', index: 'productName', width: 150, align: 'left'},
                {name: 'productId', index: 'productId', width:100, hidden:true},
                {name: 'purchaseQuantity', index: 'purchaseQuantity', width:130},
                {name: 'productRate', index: 'productRate', width:100},
                {name: 'quantityLimit', index: 'quantityLimit', width:100},
                {name: 'remove', index: 'remove', width:80}
            ],
            rowNum: 10,
            rowList: [10,20,30,40,50,60,70,80,90,100,-1],
//            pager: '#jqgrid-eligible-products-pager',
            sortname: 'sl',
            viewrecords: true,
            sortorder: "asc",
            caption: "Product List",
            autowidth: false,
            height: true,
            multiselect: false,
            scrollOffset: 0,
            loadComplete: function () {
                /*var grid = $("#jqgrid-eligible-products-grid");
                var rowIds = grid.jqGrid('getDataIDs');
                for(key in rowIds){
                    grid.jqGrid('setRowData', rowIds[key],{
                        sl:productSeq,
                        remove: '<a  href="javascript:removeProductRow(' + productSeq + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                    })
                    productSeq++
                }*/
            },
            onSelectRow: function (rowid, status) {
            },
            onSelectAll: function(aRowids, status) {
            }
        });
//        $("#jqgrid-eligible-products-grid").jqGrid('navGrid', '#jqgrid-eligible-products-pager', {edit: false, add: false, del: false, search: false});

//      Eligible Promotional Products
        $("#jqgrid-eligible-promotional-products-grid").jqGrid({
            datatype: "local",
            data:[],
            colNames: [
                'SL',
                'ID',
                'Stock ID',
                'Product Name',
                'Product ID',
                'Inventory Name',
                'Inventory ID',
                'Bonus Quantity',
                'Rate',
                'Remove'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 50, align: 'left'},
                {name: 'detailId', index: 'detailId', width: 50, align: 'left', hidden:true},
                {name: 'stockId', index: 'stockId', width: 50, align: 'left', hidden:true},
                {name: 'productName', index: 'productName', width: 150, align: 'left'},
                {name: 'productId', index: 'productId', width:100, hidden:true},
                {name: 'inventoryName', index: 'inventoryName', width: 150, align: 'left'},
                {name: 'inventoryId', index: 'inventoryId', width:100, hidden:true},
                {name: 'bonusQuantity', index: 'bonusQuantity', width:130},
                {name: 'productRate', index: 'productRate', width:100},
                {name: 'remove', index: 'remove', width:80}
            ],
            rowNum: 10,
            rowList: [10,20,30,40,50,60,70,80,90,100,-1],
//            pager: '#jqgrid-eligible-products-pager',
            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Promotional Product List",
            autowidth: false,
            height: true,
            multiselect: false,
            scrollOffset: 0,
            loadComplete: function () {
                /*var rowIds = $("#jqgrid-eligible-promotional-products-grid").jqGrid('getDataIDs')
                for(key in rowIds){
                    $("#jqgrid-eligible-promotional-products-grid").jqGrid('setRowData', rowIds[key],{
                        remove: '<a  href="javascript:removeTbCustomersRow(' + rowIds[key] + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                    })
                }*/
            },
            onSelectRow: function (rowid, status) {
            },
            onSelectAll: function(aRowids, status) {
            }
        });
//        $("#jqgrid-eligible-products-grid").jqGrid('navGrid', '#jqgrid-eligible-products-pager', {edit: false, add: false, del: false, search: false});

        $('#discountAmount').keypress(function(){
            var productList = $("#jqgrid-eligible-promotional-products-grid").jqGrid('getRowData');
            if(productList.length>0){
                $(this).val('');
                MessageRenderer.renderErrorText("You already set products for this promotion.");
                return false;
            }
        });
    });

    function getRowIdsAll(selectedRows){
        var result = [];
        $.each(selectedRows, function(i, e) {
            if ($.inArray(e, result) == -1) result.push(e);
        });
        var selectedRowsFinal = result.filter(function(n){ return n != undefined });
        var ids = ''
        for(key in selectedRowsFinal){
            if(ids){
                ids += ','+selectedRowsFinal[key]
            }else{
                ids = selectedRowsFinal[key]
            }
        }
        return ids;
    }

    function executePsPrecondition(){
        if(!$("#gFormSetupBonusPromotion").validationEngine('validate')){
            return false;
        }
        return true;
    }

    function executeAjaxPromotionSetup(){
        if(!executePsPrecondition()){
            return false;
        }

        var allData = {};

        allData.promotionId = $('#promotionId').val();
        allData.packageName = $('#packageName').val();
        allData.discountAmount = $('#discountAmount').val();
        allData.remarks = $('#remarks').val();

        var customerGrid = $("#tb-jqgrid-eligible-customers-grid");
        var productGrid = $("#jqgrid-eligible-products-grid");
        var promotionalProductGrid = $("#jqgrid-eligible-promotional-products-grid");

        var customerList = customerGrid.jqGrid('getGridParam', 'selarrrow');
        var customerIds = getRowIdsAll(customerList);
        var productList = productGrid.jqGrid('getRowData');
        var promotionalProductList = promotionalProductGrid.jqGrid('getRowData');

        if(customerIds.length == 0){
            MessageRenderer.render({
                "messageBody": "Please select at least one customer.",
                "messageTitle": "Setup Bonus Promotion",
                "type": "0"
            });
            return false;
        }
        if(productList.length == 0){
            MessageRenderer.render({
                "messageBody": "Please add at least one product.",
                "messageTitle": "Setup Bonus Promotion",
                "type": "0"
            });
            return false;
        }
        if((promotionalProductList.length == 0) && $('#discountAmount').val() == ''){
            MessageRenderer.render({
                "messageBody": "Please add at least one promotional product or Enter discount amount.",
                "messageTitle": "Setup Bonus Promotion",
                "type": "0"
            });
            return false;
        }

        allData.customerIds = customerIds;
        allData.productDetails = JSON.stringify(productList);
        allData.promotionalProductDetails = JSON.stringify(promotionalProductList);

        var url = "${request.contextPath}/${params.controller}/create";

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: allData,
            url: url,
            success: function (data, textStatus) {
                if(data.type == 1){
                    resetAll();
                }
                MessageRenderer.render(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    clearTbAll();
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    function setProductRate(id,rate){
        $('#'+id).val(rate);
    }

    function addProduct() {
        var productGrid = $("#jqgrid-eligible-products-grid");
        if (!$("#productId").val()) {
            MessageRenderer.renderErrorText("Please select product.");
            return false;
        }
        if (!$("#productRate").val()) {
            MessageRenderer.renderErrorText("Please input product rate.");
            return false;
        }
        if (!$("#purchaseQuantity").val()) {
            MessageRenderer.renderErrorText("Please input purchase quantity.");
            return false;
        }
        if (!$("#quantityLimit").val()) {
            MessageRenderer.renderErrorText("Please input quantity limit");
            return false;
        }

        var factoryStockId = $("#productId").find(':selected').attr('data-stock-id');
        var productName = $('#productId :selected').text();
        var productId = $('#productId').val();
        var purchaseQuantity = $('#purchaseQuantity').val();
        var productRate = $('#productRate').val();
        var quantityLimit = $('#quantityLimit').val();

        var productList = productGrid.jqGrid('getRowData');
        if(productList.length>0){
            for(var i=0; i<productList.length; i++){
                if(productList[i].productId == productId){
                    MessageRenderer.renderErrorText("Product: '"+productName+"' has already been exist;");
                    return false;
                    break;
                }
            }
        }
        var dataItem = {
            sl: productSeq,
            detailId: '',
            stockId: factoryStockId,
            productName: productName,
            productId: productId,
            purchaseQuantity: purchaseQuantity,
            productRate: productRate,
            quantityLimit: quantityLimit,
            remove: '<a  href="javascript:removeProductRow(' + productSeq + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
        };
        productGrid.addRowData(productSeq, dataItem, "last");
        productSeq++;
        resetProductFields();
    }

    function getPromotionalProductList(id){
        $('#promotionalProductId').html('');
        var options = '<option>Select Product...</option>'
        $('#promotionalProductId').html(options);
        if(!id) return false;

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {stockId:id},
            url: '${application.contextPath}/${params.controller}/getPromotionalProductList',
            success: function (data, textStatus) {
                for(var i=0; i<data.length; i++){
                    options += '<option value="'+data[i].id+'" data-stock-id="'+data[i].stockId+'" data-product-rate="'+data[i].rate+'">'+data[i].name+'</option>'
                }
                $('#promotionalProductId').html('');
                $('#promotionalProductId').html(options);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    clearTbAll();
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    function addPromotionalProduct() {
        if($('#discountAmount').val()>0){
            MessageRenderer.renderErrorText("You already set the discount amount.");
            return false;
        }

        var promotionalProductGrid = $("#jqgrid-eligible-promotional-products-grid");
        if (!$("#subInventoryId").val()) {
            MessageRenderer.renderErrorText("Please select sub inventory.");
            return false;
        }

        if (!$("#promotionalProductId").val()) {
            MessageRenderer.renderErrorText("Please select promotion product.");
            return false;
        }
        if (!$("#promotionalProductRate").val()) {
            MessageRenderer.renderErrorText("Please input promotion product rate.");
            return false;
        }
        if (!$("#bonusQuantity").val()) {
            MessageRenderer.renderErrorText("Please input bonus quantity.");
            return false;
        }

        var factoryStockId = $("#promotionalProductId").find(':selected').attr('data-stock-id');
        var subInventoryName = $('#subInventoryId :selected').text();
        var subInventoryId = $('#subInventoryId').val();
        var productName = $('#promotionalProductId :selected').text();
        var productId = $('#promotionalProductId').val();
        var productRate = $('#promotionalProductRate').val();
        var bonusQuantity = $('#bonusQuantity').val();

        var productList = promotionalProductGrid.jqGrid('getRowData');
        if(productList.length>0){
            for(var i=0; i<productList.length; i++){
                if(productList[i].productId == productId){
                    MessageRenderer.renderErrorText("Product: '"+productName+"' has already been exist;");
                    return false;
                    break;
                }
            }
        }

        var dataItem = {
            sl: promotionalProductSeq,
            detailId: '',
            stockId: factoryStockId,
            inventoryName: subInventoryName,
            inventoryId: subInventoryId,
            productName: productName,
            productId: productId,
            bonusQuantity: bonusQuantity,
            productRate: productRate,
            remove: '<a  href="javascript:removePromotionalProductRow(' + promotionalProductSeq + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
        };
        promotionalProductGrid.addRowData(promotionalProductSeq, dataItem, "last");
        promotionalProductSeq++;
        resetProProductFields();
    }

    function removeProductRow(rowId) {
        $("#jqgrid-eligible-products-grid").jqGrid("delRowData", rowId);
    }

    function removePromotionalProductRow(rowId) {
        $("#jqgrid-eligible-promotional-products-grid").jqGrid("delRowData", rowId);
    }

    function resetAll() {
        $('#promotionId').val('');
        $('#packageName').val('');
        $('#discountAmount').val('');
        $('#remarks').val('');

        jQuery('.cbox').attr('checked',false);
//        jQuery('#cb_tb-jqgrid-eligible-geo-grid').attr('checked',false);
        jQuery("#tb-jqgrid-eligible-geo-grid").clearGridData();
//        jQuery('#cb_tb-jqgrid-eligible-customers-grid').attr('checked',false);
        jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
        jQuery("#jqgrid-eligible-products-grid").clearGridData();
        jQuery("#jqgrid-eligible-promotional-products-grid").clearGridData();
    }

    function resetProductFields() {
        $('#productId').val('');
        $('#productRate').val('');
        $('#purchaseQuantity').val('');
        $('#quantityLimit').val('');
    }

    function resetProProductFields() {
        $('#subInventoryId').val('');
        $('#promotionalProductRate').val('');
        $('#promotionalProductId').val('');
        $('#bonusQuantity').val('');
    }

</script>