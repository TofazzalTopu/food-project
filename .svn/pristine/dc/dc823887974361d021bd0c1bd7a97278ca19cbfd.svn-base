<script type="text/javascript" language="Javascript">
    var qbRowId = 1;
    var qbDelSlabIds = [];
    var qbDelCustomerIds = [];
    $(document).ready(function () {
        $("#qbEffectiveDateFrom").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $("#qbEffectiveDateTo").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $('#qbEffectiveDateFrom').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#qbEffectiveDateTo').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

//        Incentive Slab
        $("#qb-jqgrid-incentive-slab-grid").jqGrid({
            datatype: "local",
            data: ${quantityBasedIncentiveSlabList?quantityBasedIncentiveSlabList:''},
            colNames: [
                'SI',
                'ID',
                '',
                'Product',
                'Eligible Qty From',
                'Eligible Qty TO',
                'Incentive Amount in BDT',
                'Remove'
            ],
            colModel: [
                {name: 'si', index: 'si', width: 30, align: 'left'},
                {name: 'qbisId', index: 'qbisId', width: 30, align: 'left', hidden:true},
                {name: 'productId', index: 'productId', width: 30, align: 'left', hidden:true},
                {name: 'productName', index: 'productName', width: 160, align: 'left'},
                {name: 'quantityFrom', index: 'quantityFrom', width: 110, align: 'left'},
                {name: 'quantityTo', index: 'quantityTo', width: 100, align: 'left'},
                {name: 'incentiveAmount', index: 'incentiveAmount', width: 155, align: 'left'},
                {name: 'remove', index: 'remove', width: 60, align: 'center'}
            ],
            rowNum: 10,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#qb-jqgrid-incentive-slab-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "asc",
            caption: "Incentive Slab List",
            autowidth: false,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
                var grid = $("#qb-jqgrid-incentive-slab-grid");
                var rowIds = grid.jqGrid('getDataIDs');
                for(key in rowIds){
                    grid.jqGrid('setRowData', rowIds[key],{
                        si:qbRowId,
                        remove: '<a  href="javascript:removeQbIncentiveRow(' + qbRowId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                    });
                    qbRowId++
                }
            },
            onSelectRow: function (rowid, status) {

            }
        });
        $("#qb-jqgrid-incentive-slab-grid").jqGrid('navGrid', '#qb-jqgrid-incentive-slab-pager', {edit: false, add: false, del: false, search: false});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });

//        Eligible Territory
        $("#qb-jqgrid-eligible-territory-grid").jqGrid({
            datatype: "local",
            data:${territoryConfigurationList},

            colNames: [
                'All Territory',
                'Territory ID'
            ],
            colModel: [
                {name: 'name', index: 'territoryName', width: 150, align: 'left'},
                {name: 'id', index: 'territoryId', width:0, hidden:true}

            ],

            rowNum: ${territoryConfigurationSize},
            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Eligible Territory",
            autowidth: false,
            height: true,
            multiselect: true,
            scrollOffset: 17,
            loadComplete: function () {

            },
            onSelectRow: function (rowid, status) {
                jQuery("#qb-jqgrid-eligible-geo-grid").clearGridData();
                jQuery("#qb-jqgrid-eligible-pt-grid").clearGridData();
                jQuery("#qb-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#qb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                $('#cb_qb-jqgrid-eligible-territory-grid').attr('checked',false);
                var selectedRows = $('#qb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var ids = getRowIdsAll(selectedRows);

                if(ids==''){
                    return false;
                }

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:ids},
                    url:  "${request.contextPath}/${params.controller}/getTerritoryWiseGeoAndCustomers",
                    success: function (data, textStatus) {
                        if(data.geoList[0]) {
                            jQuery("#qb-jqgrid-eligible-geo-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.geoList.length,
                                        datatype: 'local',
                                        data: data.geoList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#qb-jqgrid-eligible-geo-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#qb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                            jQuery("#qb-jqgrid-eligible-geo-grid").clearGridData();
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
                jQuery("#qb-jqgrid-eligible-geo-grid").clearGridData();
                jQuery("#qb-jqgrid-eligible-pt-grid").clearGridData();
                jQuery("#qb-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#qb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                var selectedRows = $('#qb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var ids = getRowIdsAll(selectedRows);

                if(ids==''){
                    return false;
                }

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:ids},
                    url:  "${request.contextPath}/${params.controller}/getTerritoryWiseGeoAndCustomers",
                    success: function (data, textStatus) {
                        if(data.geoList[0]) {
                            jQuery("#qb-jqgrid-eligible-geo-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.geoList.length,
                                        datatype: 'local',
                                        data: data.geoList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#qb-jqgrid-eligible-geo-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#qb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                            jQuery("#qb-jqgrid-eligible-geo-grid").clearGridData();
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

//        Eligible Geographic Location
        $("#qb-jqgrid-eligible-geo-grid").jqGrid({
            datatype: "local",
            data:'',

            colNames: [
                'All Geographic Location',
                'GEO ID'
            ],
            colModel: [
                {name: 'name', index: 'territoryName', width: 170, align: 'left'},
                {name: 'id', index: 'territoryId', width:0, hidden:true}

            ],

            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Eligible Geographic Location",
            autowidth: false,
            height: true,
            multiselect: true,
            scrollOffset: 17,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                jQuery("#qb-jqgrid-eligible-pt-grid").clearGridData();
                jQuery("#qb-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#qb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                $('#cb_qb-jqgrid-eligible-geo-grid').attr('checked',false);
                var selectedTerritoryRows = $('#qb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#qb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds},
                    url:  "${request.contextPath}/${params.controller}/getGeoWiseTpAndCustomers",
                    success: function (data, textStatus) {
                        if(data.ptList[0]) {
                            jQuery("#qb-jqgrid-eligible-pt-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.ptList.length,
                                        datatype: 'local',
                                        data: data.ptList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#qb-jqgrid-eligible-pt-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#qb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                            jQuery("#qb-jqgrid-eligible-pt-grid").clearGridData();
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
                jQuery("#qb-jqgrid-eligible-pt-grid").clearGridData();
                jQuery("#qb-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#qb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                var selectedTerritoryRows = $('#qb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#qb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds},
                    url:  "${request.contextPath}/${params.controller}/getGeoWiseTpAndCustomers",
                    success: function (data, textStatus) {
                        if(data.ptList[0]) {
                            jQuery("#qb-jqgrid-eligible-pt-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.ptList.length,
                                        datatype: 'local',
                                        data: data.ptList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#qb-jqgrid-eligible-pt-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#qb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                            jQuery("#qb-jqgrid-eligible-pt-grid").clearGridData();
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

//        Eligible Partner Type
        $("#qb-jqgrid-eligible-pt-grid").jqGrid({
            datatype: "local",
            data:'',

            colNames: [
                'All Partner Type',
                'Partner Type ID'
            ],
            colModel: [
                {name: 'name', index: 'territoryName', width: 140, align: 'left'},
                {name: 'id', index: 'territoryId', width:0, hidden:true}

            ],

            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Eligible Partner Type",
            autowidth: false,
            height: true,
            multiselect: true,
            scrollOffset: 17,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                jQuery("#qb-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#qb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                $('#cb_qb-jqgrid-eligible-pt-grid').attr('checked',false);
                var selectedTerritoryRows = $('#qb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#qb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#qb-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);
                var ptIds = getRowIdsAll(selectedPtRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds, ptIds:ptIds},
                    url:  "${request.contextPath}/${params.controller}/getPtWiseScAndCustomers",
                    success: function (data, textStatus) {
                        if(data.scList[0]) {
                            jQuery("#qb-jqgrid-eligible-sc-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.scList.length,
                                        datatype: 'local',
                                        data: data.scList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#qb-jqgrid-eligible-sc-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#qb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                            jQuery("#qb-jqgrid-eligible-sc-grid").clearGridData();
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
                jQuery("#qb-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#qb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                var selectedTerritoryRows = $('#qb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#qb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#qb-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);
                var ptIds = getRowIdsAll(selectedPtRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds, ptIds:ptIds},
                    url:  "${request.contextPath}/${params.controller}/getPtWiseScAndCustomers",
                    success: function (data, textStatus) {
                        if(data.scList[0]) {
                            jQuery("#qb-jqgrid-eligible-sc-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.scList.length,
                                        datatype: 'local',
                                        data: data.scList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#qb-jqgrid-eligible-sc-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#qb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                            jQuery("#qb-jqgrid-eligible-sc-grid").clearGridData();
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

//        Eligible Sales Channel
        $("#qb-jqgrid-eligible-sc-grid").jqGrid({
            datatype: "local",
            data:'',

            colNames: [
                'All Sales Channel',
                'Sales Channel ID'
            ],
            colModel: [
                {name: 'name', index: 'territoryName', width: 140, align: 'left'},
                {name: 'id', index: 'territoryId', width:0, hidden:true}

            ],

            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Eligible Sales Channel",
            autowidth: false,
            height: true,
            multiselect: true,
            scrollOffset: 17,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                jQuery("#qb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                $('#cb_qb-jqgrid-eligible-sc-grid').attr('checked',false);
                var selectedTerritoryRows = $('#qb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#qb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#qb-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedScRows = $('#qb-jqgrid-eligible-sc-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);
                var ptIds = getRowIdsAll(selectedPtRows);
                var scIds = getRowIdsAll(selectedScRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds, ptIds:ptIds, scIds:scIds},
                    url:  "${request.contextPath}/${params.controller}/getScWiseCcAndCustomers",
                    success: function (data, textStatus) {
                        if(data.ccList[0]) {
                            jQuery("#qb-jqgrid-eligible-cc-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.ccList.length,
                                        datatype: 'local',
                                        data: data.ccList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#qb-jqgrid-eligible-cc-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#qb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                            jQuery("#qb-jqgrid-eligible-cc-grid").clearGridData();
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
                jQuery("#qb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                var selectedTerritoryRows = $('#qb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#qb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#qb-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedScRows = $('#qb-jqgrid-eligible-sc-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);
                var ptIds = getRowIdsAll(selectedPtRows);
                var scIds = getRowIdsAll(selectedScRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds, ptIds:ptIds, scIds:scIds},
                    url:  "${request.contextPath}/${params.controller}/getScWiseCcAndCustomers",
                    success: function (data, textStatus) {
                        if(data.ccList[0]) {
                            jQuery("#qb-jqgrid-eligible-cc-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.ccList.length,
                                        datatype: 'local',
                                        data: data.ccList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#qb-jqgrid-eligible-cc-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#qb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                            jQuery("#qb-jqgrid-eligible-cc-grid").clearGridData();
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

//        Eligible Customer Category
        $("#qb-jqgrid-eligible-cc-grid").jqGrid({
            datatype: "local",
            data:'',

            colNames: [
                'All Customer Category',
                'Category ID'
            ],
            colModel: [
                {name: 'name', index: 'categoryName', width: 165, align: 'left'},
                {name: 'id', index: 'categoryId', width:0, hidden:true}

            ],

            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Eligible Customer Category",
            autowidth: false,
            height: true,
            multiselect: true,
            scrollOffset: 17,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                $('#cb_qb-jqgrid-eligible-cc-grid').attr('checked',false);
                var selectedTerritoryRows = $('#qb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#qb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#qb-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedScRows = $('#qb-jqgrid-eligible-sc-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedCcRows = $('#qb-jqgrid-eligible-cc-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);
                var ptIds = getRowIdsAll(selectedPtRows);
                var scIds = getRowIdsAll(selectedScRows);
                var ccIds = getRowIdsAll(selectedCcRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds, ptIds:ptIds, scIds:scIds, ccIds:ccIds},
                    url:  "${request.contextPath}/${params.controller}/getCcWiseCustomers",
                    success: function (data, textStatus) {
                        if(data.customerList[0]){
                            jQuery("#qb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
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
                jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                var selectedTerritoryRows = $('#qb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#qb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#qb-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedScRows = $('#qb-jqgrid-eligible-sc-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedCcRows = $('#qb-jqgrid-eligible-cc-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);
                var ptIds = getRowIdsAll(selectedPtRows);
                var scIds = getRowIdsAll(selectedScRows);
                var ccIds = getRowIdsAll(selectedCcRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds, ptIds:ptIds, scIds:scIds, ccIds:ccIds},
                    url:  "${request.contextPath}/${params.controller}/getCcWiseCustomers",
                    success: function (data, textStatus) {
                        if(data.customerList[0]){
                            jQuery("#qb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#qb-jqgrid-eligible-customers-grid").clearGridData();
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
        $("#qb-jqgrid-eligible-customers-grid").jqGrid({
            datatype: "local",
            data:'',

            colNames: [
                'Customer Name',
                'ID',
                'Customer ID',
                'Remove'
            ],
            colModel: [
                {name: 'name', index: 'name', width: 200, align: 'left'},
                {name: 'id', index: 'id', width:100, hidden:true},
                {name: 'code', index: 'code', width:100},
                {name: 'remove', index: 'remove', width:60}

            ],
            rowNum: 10,
            rowList: [10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#qb-jqgrid-eligible-customers-pager',
            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Eligible Customers",
            autowidth: false,
            height: true,
            multiselect: true,
            scrollOffset: 17,
            loadComplete: function () {
                var rowIds = $("#qb-jqgrid-eligible-customers-grid").jqGrid('getDataIDs')
                for(key in rowIds){
                    $("#qb-jqgrid-eligible-customers-grid").jqGrid('setRowData', rowIds[key],{
                        remove: '<a  href="javascript:removeQbCustomersRow(' + rowIds[key] + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                    })
                }
            },
            onSelectRow: function (rowid, status) {
                $('#cb_qb-jqgrid-eligible-customers-grid').attr('checked',false);
            }
        });

        $("#qb-jqgrid-eligible-customers-grid").jqGrid('navGrid', '#qb-jqgrid-eligible-customers-pager', {edit: false, add: false, del: false, search: false});

//        Eligible Customers for Update
        $("#qb-update-jqgrid-eligible-customers-grid").jqGrid({
            datatype: "local",
            data: ${quantityBasedIncentiveCustomersList?quantityBasedIncentiveCustomersList:''},

            colNames: [
                'SI',
                'qbicId',
                'Customer Name',
                'Customer ID',
                '',
                'Territory',
                'Geo Location',
                'Partner Type',
                'Sales Channel',
                'Customer Category',
                'Remove'
            ],
            colModel: [
                {name: 'si', index: 'si', width: 30, align: 'left'},
                {name: 'qbicId', index: 'qbicId', width: 60, align: 'left', hidden:true},
                {name: 'name', index: 'name', width: 120, align: 'left'},
                {name: 'code', index: 'code', width:100},
                {name: 'id', index: 'id', width:100, hidden:true},
                {name: 'territory', index: 'territory', width:120},
                {name: 'geo', index: 'geo', width:150},
                {name: 'partnerType', index: 'partnerType', width:100},
                {name: 'salesChannel', index: 'salesChannel', width:100},
                {name: 'customerCategory', index: 'customerCategory', width:100},
                {name: 'remove', index: 'remove', width:60}

            ],
            rowNum: 10,
            rowList: [10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#qb-update-jqgrid-eligible-customers-pager',
//            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Existing Customers List",
            autowidth: false,
            height: true,
            multiselect: false,
            scrollOffset: 17,
            loadComplete: function () {
                var i = 1;
                var rowIds = $("#qb-update-jqgrid-eligible-customers-grid").jqGrid('getDataIDs');
                for(key in rowIds){
                    $("#qb-update-jqgrid-eligible-customers-grid").jqGrid('setRowData', rowIds[key],{
                        si:i,
                        remove: '<a  href="javascript:removeQbUpdateCustomersRow(' + rowIds[key] + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                    });
                    i++;
                }
            },
            onSelectRow: function (rowid, status) {
                $('#cb_qb-update-jqgrid-eligible-customers-grid').attr('checked',false);
            }
        });

        $("#qb-update-jqgrid-eligible-customers-grid").jqGrid('navGrid', '#qb-update-jqgrid-eligible-customers-pager', {edit: false, add: false, del: false, search: false});

        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });

//        Grid Max Height
        $('div.ui-jqgrid-bdiv').css("max-height","250px");

//        Check 100% Achievement From
        $('#qbQuantityFrom').keypress(function(event) {
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

//        Check 100% Achievement To
        $('#qbQuantityTo').keypress(function(event) {
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

//        Check Incentive Amount
        $('#qbIncentiveAmount').keypress(function(event) {
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

    function addQbIncentive() {
        var myGrid = $("#qb-jqgrid-incentive-slab-grid");

        var slabDataList = myGrid.jqGrid('getRowData');

        var qbProductId = $("#qbProduct").val();
        var qbProductName = $("#qbProduct option:selected").text();
        var qbQuantityFrom = $("#qbQuantityFrom").val();
        var qbQuantityTo = $("#qbQuantityTo").val();
        var qbIncentiveAmount = $("#qbIncentiveAmount").val();

        if(slabDataList.length > 0){
            for(var i=0; i<slabDataList.length; i++){
                if(qbProductId == slabDataList[i].productId){
                    if(((parseFloat(qbQuantityFrom) >= parseFloat(slabDataList[i].quantityFrom)) && (parseFloat(qbQuantityFrom) <= parseFloat(slabDataList[i].quantityTo))) || ((parseFloat(qbQuantityTo) >= parseFloat(slabDataList[i].quantityFrom)) && (parseFloat(qbQuantityTo) <= parseFloat(slabDataList[i].quantityTo)))){
                        MessageRenderer.render({
                            "messageBody": "Overlapping product quantity slab for the same product is not allowed.",
                            "messageTitle": "Quantity Based Incentive",
                            "type": "0"
                        });
                        return false;
                    }
                }
            }
        }

        if (qbProductId && qbQuantityFrom && qbQuantityTo && qbIncentiveAmount) {
            var dataItem = {
                si: qbRowId,
                productId:qbProductId,
                productName: qbProductName,
                quantityFrom: qbQuantityFrom,
                quantityTo: qbQuantityTo,
                incentiveAmount: qbIncentiveAmount,
                remove: '<a  href="javascript:removeQbIncentiveRow(' + qbRowId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
            };
            myGrid.addRowData(qbRowId,dataItem, "last");
            $("#qbProduct").val('');
            $("#qbUom").val('');
            $("#qbQuantityFrom").val('');
            $("#qbQuantityTo").val('');
            $("#qbIncentiveAmount").val('');
            qbRowId++;
        }else{
            if(!qbProductId){
                MessageRenderer.render({
                    "messageBody": "Please select a product first.",
                    "messageTitle": "Quantity Based Incentive",
                    "type": "0"
                });
                return false;
            }
            else if(!qbQuantityFrom){
                MessageRenderer.render({
                    "messageBody": "Please enter quantity from.",
                    "messageTitle": "Quantity Based Incentive",
                    "type": "0"
                });
                return false;
            }
            else if(!qbQuantityTo){
                MessageRenderer.render({
                    "messageBody": "Please enter quantity to.",
                    "messageTitle": "Quantity Based Incentive",
                    "type": "0"
                });
                return false;
            }
            else if(!qbIncentiveAmount){
                MessageRenderer.render({
                    "messageBody": "Please enter incentive amount.",
                    "messageTitle": "Quantity Based Incentive",
                    "type": "0"
                });
                return false;
            }
        }
    }

    function removeQbIncentiveRow(id){
        var grid = $("#qb-jqgrid-incentive-slab-grid");
        var slabId = grid.jqGrid('getCell',id,'qbisId');
        if(slabId){
            qbDelSlabIds.push(slabId);
        }
        grid.jqGrid("delRowData", id);
    }

    function removeQbCustomersRow(id){
        $("#qb-jqgrid-eligible-customers-grid").jqGrid("delRowData", id);
    }

    function removeQbUpdateCustomersRow(id){
        var grid = $("#qb-update-jqgrid-eligible-customers-grid");
        var slabId = grid.jqGrid('getCell',id,'qbicId');
        if(slabId){
            qbDelCustomerIds.push(slabId);
        }
        grid.jqGrid("delRowData", id);
    }

    function executeQbPrecondition(){
        if(!$("#gFormSetupIncentive").validationEngine('validate')){
            return false;
        }
        return true;
    }

    function executeAjaxIncentiveQuantityBased(){
        if(!executeQbPrecondition()){
            return false;
        }

        var allData = {};

        allData["programName"] = $("#qbProgramName").val();
        allData["effectiveDateFrom"] = $("#qbEffectiveDateFrom").val();
        allData["effectiveDateTo"] = $("#qbEffectiveDateTo").val();

        var allCustomers = [];
        var allIncentive = $("#qb-jqgrid-incentive-slab-grid").jqGrid('getRowData');
        var selectedCustomerRows = $('#qb-jqgrid-eligible-customers-grid').jqGrid('getGridParam', 'selarrrow');
        var customerRowIds = filterListOfIds(selectedCustomerRows);

        var qbIncentiveId = $('#qbIncentiveId').val()
        var qbIncentiveVersion = $('#qbIncentiveVersion').val()

        if(allIncentive.length>0){
            for(var i = 0; i < allIncentive.length; i++){
                allData["allIncentive.incentive["+ i +"].id"] = allIncentive[i].qbisId;
                allData["allIncentive.incentive["+ i +"].productId"] = allIncentive[i].productId;
                allData["allIncentive.incentive["+ i +"].quantityFrom"] = allIncentive[i].quantityFrom;
                allData["allIncentive.incentive["+ i +"].quantityTo"] = allIncentive[i].quantityTo;
                allData["allIncentive.incentive["+ i +"].incentiveAmount"] = allIncentive[i].incentiveAmount;
            }
        }else{
            MessageRenderer.render({
                "messageBody": "Please insert at least one incentive slab into the list.",
                "messageTitle": "Quantity Based Incentive",
                "type": "0"
            });
            return false;
        }

        if(customerRowIds == '' && qbIncentiveId == ''){
            MessageRenderer.render({
                "messageBody": "Please select at least one customer from the customer list.",
                "messageTitle": "Quantity Based Incentive",
                "type": "0"
            });
            return false;
        }else{
            for(key in customerRowIds){
                allCustomers.push($("#qb-jqgrid-eligible-customers-grid").getRowData(customerRowIds[key]));
            }

            for(var i = 0; i < allCustomers.length; i++){
                allData["customerList.customer["+ i +"].customerId"] = allCustomers[i].id;
            }
        }

        var url = '';

        if(qbIncentiveId){
            for(var i=0; i<qbDelSlabIds.length; i++){
                allData['incentiveSlabDeleteList.delete['+ i +'].id'] = qbDelSlabIds[i];
            }

            for(var i=0; i<qbDelCustomerIds.length; i++){
                allData['incentiveCustomersDeleteList.delete['+ i +'].id'] = qbDelCustomerIds[i];
            }

            var qbExistingCustomers = $("#qb-update-jqgrid-eligible-customers-grid").jqGrid('getDataIDs');
            if(customerRowIds == '' && qbExistingCustomers == ''){
                MessageRenderer.render({
                    "messageBody": "Please select at least one customer from the customer list.",
                    "messageTitle": "Quantity Based Incentive",
                    "type": "0"
                });
                return false;
            }

            url = "${request.contextPath}/${params.controller}/updateQuantityBasedIncentive?id="+qbIncentiveId+"&version="+qbIncentiveVersion;
        }else{
            url = "${request.contextPath}/${params.controller}/createQuantityBasedIncentive";
        }

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: allData,
            url:  url,
            success: function (data, textStatus) {
                if(data.type == 1){
//                    if(qbIncentiveId){
//                        $("#fragment-3").load(location.href + " #fragment-3");
//                        $("#qbReload").load(location.href+" #qbReload>*","");
//                    }else{
//                        clearQbAll();
//                    }
                    clearQbAll();
                }
                MessageRenderer.render(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    clearQbAll();
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

    function getQbUom(id){
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {id:id},
            url:  "${request.contextPath}/${params.controller}/getQbUomByProduct",
            success: function (data, textStatus) {
                if(data){
                    $('#qbUom').val(data.name);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
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

    function clearQbAll(){
        $('#qbProgramName').val('');
        $('#qbEffectiveDateFrom').val('');
        $('#qbEffectiveDateTo').val('');

        $("#qb-jqgrid-incentive-slab-grid").clearGridData();
        $("#qb-jqgrid-eligible-territory-grid").trigger("reloadGrid");
        $("#qb-jqgrid-eligible-geo-grid").clearGridData();
        $("#qb-jqgrid-eligible-pt-grid").clearGridData();
        $("#qb-jqgrid-eligible-sc-grid").clearGridData();
        $("#qb-jqgrid-eligible-cc-grid").clearGridData();

        $("#qb-jqgrid-eligible-customers-grid").clearGridData();
        $("#qb-update-jqgrid-eligible-customers-grid").clearGridData();
    }
</script>