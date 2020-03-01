<script type="text/javascript" language="Javascript">
    var sabRowId = 1;
    var sabDelSlabIds = [];
    var sabDelCustomerIds = [];
    $(document).ready(function () {
        $("#aBeffectiveDateFrom").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $("#aBeffectiveDateTo").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $('#aBeffectiveDateFrom').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#aBeffectiveDateTo').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

//        Incentive Slab
        $("#ab-jqgrid-incentive-slab-grid").jqGrid({
            datatype: "local",
            data: ${salesAmountBasedIncentiveSlabList?salesAmountBasedIncentiveSlabList:''},
            colNames: [
                'SI',
                'ID',
                'Sales Value From',
                'Sales Value To',
                'Incentive Amount',
                'Incentive % of Sales',
                'Remove'
            ],
            colModel: [
                {name: 'si', index: 'si', width: 30, align: 'left'},
                {name: 'sabisId', index: 'sabisId', width: 30, align: 'left',hidden:true},
                {name: 'salesValueFrom', index: 'salesValueFrom', width: 130, align: 'left'},
                {name: 'salesValueTo', index: 'salesValueTo', width: 130, align: 'left'},
                {name: 'incentiveAmount', index: 'incentiveAmount', width: 130, align: 'left'},
                {name: 'incentiveInPct', index: 'incentiveInPct', width: 130, align: 'left'},
                {name: 'remove', index: 'remove', width: 60, align: 'center'}
            ],
            rowNum: 10,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#ab-jqgrid-incentive-slab-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "asc",
            caption: "Incentive Slab List",
            autowidth: false,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
                var grid = $("#ab-jqgrid-incentive-slab-grid");
                var rowIds = grid.jqGrid('getDataIDs');
                for(key in rowIds){
                    grid.jqGrid('setRowData', rowIds[key],{
                        si:sabRowId,
                        remove: '<a  href="javascript:removeAbIncentiveRow(' + sabRowId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                    });
                    sabRowId++
                }
            },
            onSelectRow: function (rowid, status) {

            }
        });
        $("#ab-jqgrid-incentive-slab-grid").jqGrid('navGrid', '#ab-jqgrid-incentive-slab-pager', {edit: false, add: false, del: false, search: false});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });

//        Eligible Territory
        $("#ab-jqgrid-eligible-territory-grid").jqGrid({
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
                jQuery("#ab-jqgrid-eligible-geo-grid").clearGridData();
                jQuery("#ab-jqgrid-eligible-pt-grid").clearGridData();
                jQuery("#ab-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#ab-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                $('#cb_ab-jqgrid-eligible-territory-grid').attr('checked',false);
                var selectedRows = $('#ab-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
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
                            jQuery("#ab-jqgrid-eligible-geo-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.geoList.length,
                                        datatype: 'local',
                                        data: data.geoList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#ab-jqgrid-eligible-geo-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#ab-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#ab-jqgrid-eligible-geo-grid").clearGridData();
                            jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
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
                jQuery("#ab-jqgrid-eligible-geo-grid").clearGridData();
                jQuery("#ab-jqgrid-eligible-pt-grid").clearGridData();
                jQuery("#ab-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#ab-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                var selectedRows = $('#ab-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
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
                            jQuery("#ab-jqgrid-eligible-geo-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.geoList.length,
                                        datatype: 'local',
                                        data: data.geoList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#ab-jqgrid-eligible-geo-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#ab-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#ab-jqgrid-eligible-geo-grid").clearGridData();
                            jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
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
        $("#ab-jqgrid-eligible-geo-grid").jqGrid({
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
                jQuery("#ab-jqgrid-eligible-pt-grid").clearGridData();
                jQuery("#ab-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#ab-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                $('#cb_ab-jqgrid-eligible-geo-grid').attr('checked',false);
                var selectedTerritoryRows = $('#ab-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#ab-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds},
                    url:  "${request.contextPath}/${params.controller}/getGeoWiseTpAndCustomers",
                    success: function (data, textStatus) {
                        if(data.ptList[0]) {
                            jQuery("#ab-jqgrid-eligible-pt-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.ptList.length,
                                        datatype: 'local',
                                        data: data.ptList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#ab-jqgrid-eligible-pt-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#ab-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                            jQuery("#ab-jqgrid-eligible-pt-grid").clearGridData();
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
                jQuery("#ab-jqgrid-eligible-pt-grid").clearGridData();
                jQuery("#ab-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#ab-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                var selectedTerritoryRows = $('#ab-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#ab-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds},
                    url:  "${request.contextPath}/${params.controller}/getGeoWiseTpAndCustomers",
                    success: function (data, textStatus) {
                        if(data.ptList[0]) {
                            jQuery("#ab-jqgrid-eligible-pt-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.ptList.length,
                                        datatype: 'local',
                                        data: data.ptList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#ab-jqgrid-eligible-pt-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#ab-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                            jQuery("#ab-jqgrid-eligible-pt-grid").clearGridData();
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
        $("#ab-jqgrid-eligible-pt-grid").jqGrid({
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
                jQuery("#ab-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#ab-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                $('#cb_ab-jqgrid-eligible-pt-grid').attr('checked',false);
                var selectedTerritoryRows = $('#ab-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#ab-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#ab-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
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
                            jQuery("#ab-jqgrid-eligible-sc-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.scList.length,
                                        datatype: 'local',
                                        data: data.scList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#ab-jqgrid-eligible-sc-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#ab-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                            jQuery("#ab-jqgrid-eligible-sc-grid").clearGridData();
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
                jQuery("#ab-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#ab-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                var selectedTerritoryRows = $('#ab-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#ab-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#ab-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
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
                            jQuery("#ab-jqgrid-eligible-sc-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.scList.length,
                                        datatype: 'local',
                                        data: data.scList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#ab-jqgrid-eligible-sc-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#ab-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                            jQuery("#ab-jqgrid-eligible-sc-grid").clearGridData();
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
        $("#ab-jqgrid-eligible-sc-grid").jqGrid({
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
                jQuery("#ab-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                $('#cb_ab-jqgrid-eligible-sc-grid').attr('checked',false);
                var selectedTerritoryRows = $('#ab-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#ab-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#ab-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedScRows = $('#ab-jqgrid-eligible-sc-grid').jqGrid('getGridParam', 'selarrrow');
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
                            jQuery("#ab-jqgrid-eligible-cc-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.ccList.length,
                                        datatype: 'local',
                                        data: data.ccList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#ab-jqgrid-eligible-cc-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#ab-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                            jQuery("#ab-jqgrid-eligible-cc-grid").clearGridData();
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
                jQuery("#ab-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                var selectedTerritoryRows = $('#ab-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#ab-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#ab-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedScRows = $('#ab-jqgrid-eligible-sc-grid').jqGrid('getGridParam', 'selarrrow');
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
                            jQuery("#ab-jqgrid-eligible-cc-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.ccList.length,
                                        datatype: 'local',
                                        data: data.ccList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#ab-jqgrid-eligible-cc-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#ab-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                            jQuery("#ab-jqgrid-eligible-cc-grid").clearGridData();
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
        $("#ab-jqgrid-eligible-cc-grid").jqGrid({
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
                jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                $('#cb_ab-jqgrid-eligible-cc-grid').attr('checked',false);
                var selectedTerritoryRows = $('#ab-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#ab-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#ab-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedScRows = $('#ab-jqgrid-eligible-sc-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedCcRows = $('#ab-jqgrid-eligible-cc-grid').jqGrid('getGridParam', 'selarrrow');
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
                            jQuery("#ab-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
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
                jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                var selectedTerritoryRows = $('#ab-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#ab-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#ab-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedScRows = $('#ab-jqgrid-eligible-sc-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedCcRows = $('#ab-jqgrid-eligible-cc-grid').jqGrid('getGridParam', 'selarrrow');
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
                            jQuery("#ab-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#ab-jqgrid-eligible-customers-grid").clearGridData();
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
        $("#ab-jqgrid-eligible-customers-grid").jqGrid({
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
                {name: 'id', index: 'id', width:100,hidden:true},
                {name: 'code', index: 'code', width:100},
                {name: 'remove', index: 'remove', width:60}

            ],
            rowNum: 10,
            rowList: [10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#ab-jqgrid-eligible-customers-pager',
            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Eligible Customers",
            autowidth: false,
            height: true,
            multiselect: true,
            scrollOffset: 17,
            loadComplete: function () {
                var rowIds = $("#ab-jqgrid-eligible-customers-grid").jqGrid('getDataIDs')
                for(key in rowIds){
                    $("#ab-jqgrid-eligible-customers-grid").jqGrid('setRowData', rowIds[key],{
                        remove: '<a  href="javascript:removeAbCustomersRow(' + rowIds[key] + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                    })
                }
            },
            onSelectRow: function (rowid, status) {
                $('#cb_ab-jqgrid-eligible-customers-grid').attr('checked',false);
            }
        });

        $("#ab-jqgrid-eligible-customers-grid").jqGrid('navGrid', '#ab-jqgrid-eligible-customers-pager', {edit: false, add: false, del: false, search: false});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });

//        Eligible Customers for Update
        $("#ab-update-jqgrid-eligible-customers-grid").jqGrid({
            datatype: "local",
            data:${salesAmountBasedIncentiveCustomersList?salesAmountBasedIncentiveCustomersList:''},
            colNames: [
                'SI',
                'sabicId',
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
                {name: 'sabicId', index: 'sabicId', width: 60, align: 'left',hidden:true},
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
            pager: '#ab-update-jqgrid-eligible-customers-pager',
//            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Existing Customer List",
            autowidth: false,
            height: true,
            multiselect: false,
            scrollOffset: 17,
            loadComplete: function () {
                var i = 1;
                var rowIds = $("#ab-update-jqgrid-eligible-customers-grid").jqGrid('getDataIDs');
                for(key in rowIds){
                    $("#ab-update-jqgrid-eligible-customers-grid").jqGrid('setRowData', rowIds[key],{
                        si:i,
                        remove: '<a  href="javascript:removeAbUpdateCustomersRow(' + rowIds[key] + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                    });
                    i++;
                }
            },
            onSelectRow: function (rowid, status) {
                $('#cb_ab-update-jqgrid-eligible-customers-grid').attr('checked',false);
            }
        });

        $("#ab-update-jqgrid-eligible-customers-grid").jqGrid('navGrid', '#ab-update-jqgrid-eligible-customers-pager', {edit: false, add: false, del: false, search: false});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });

//        Grid Max Height
        $('div.ui-jqgrid-bdiv').css("max-height","250px");

//        Check 100% Achievement From
        $('#aBachievementFrom').keypress(function(event) {
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
        $('#aBachievementTo').keypress(function(event) {
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
        $('#aBincentiveAmount').keypress(function(event) {
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

//        Check Incentive % of sales
        $('#aBincentivePct').keypress(function(event) {
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

    function addAbIncentive() {
        var myGrid = $("#ab-jqgrid-incentive-slab-grid");
        var slabDataList = myGrid.jqGrid('getRowData');

        var achievementFrom = $("#aBachievementFrom").val();
        var achievementTo = $("#aBachievementTo").val();
        var incentiveType = '';
        var aBincentiveAmount = $("#aBincentiveAmount").val();
        var aBincentivePct = $("#aBincentivePct").val();
        if($('input[name=incentiveType]').is(':checked')){
            incentiveType = $("input[name=incentiveType]:checked").val();
        }

        if(slabDataList.length > 0){
            for(var i=0; i<slabDataList.length; i++){
                if(((parseFloat(achievementFrom) >= parseFloat(slabDataList[i].salesValueFrom)) && (parseFloat(achievementFrom) <= parseFloat(slabDataList[i].salesValueTo))) || ((parseFloat(achievementTo) >= parseFloat(slabDataList[i].salesValueFrom)) && (parseFloat(achievementTo) <= parseFloat(slabDataList[i].salesValueTo)))){
                    MessageRenderer.render({
                        "messageBody": "Overlapping incentive slab between sales value from and sales value to is not allowed.",
                        "messageTitle": "Sales Amount Based Incentive",
                        "type": "0"
                    });
                    return false;
                }
            }
        }

        if (achievementFrom && achievementTo && incentiveType) {
            if((incentiveType == 'amt') && (aBincentiveAmount == '')){
                MessageRenderer.render({
                    "messageBody": "Please insert incentive amount.",
                    "messageTitle": "Sales Amount Based Incentive",
                    "type": "0"
                });
                return false;
            }
            else if((incentiveType == 'pct') && (aBincentivePct == '')){
                MessageRenderer.render({
                    "messageBody": "Please insert incentive % of sales.",
                    "messageTitle": "Sales Amount Based Incentive",
                    "type": "0"
                });
                return false;
            }

            var dataItem = {
                si: sabRowId,
                salesValueFrom: achievementFrom,
                salesValueTo: achievementTo,
                incentiveAmount: aBincentiveAmount,
                incentiveInPct: aBincentivePct,
                remove: '<a  href="javascript:removeAbIncentiveRow(' + sabRowId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
            };
            myGrid.addRowData(sabRowId,dataItem, "last");
            $("#aBachievementFrom").val('');
            $("#aBachievementTo").val('');
            $("#aBincentiveAmount").val('');
            $("#aBincentivePct").val('');
            $("input[name=incentiveType]:checked").attr('checked',false);
            sabRowId++;
        }else{
            if(!achievementFrom){
                MessageRenderer.render({
                    "messageBody": "Please enter sales value from.",
                    "messageTitle": "Sales Amount Based Incentive",
                    "type": "0"
                });
                return false;
            }
            else if(!achievementTo){
                MessageRenderer.render({
                    "messageBody": "Please enter sales value to.",
                    "messageTitle": "Sales Amount Based Incentive",
                    "type": "0"
                });
                return false;
            }
            else if(!incentiveType){
                MessageRenderer.render({
                    "messageBody": "Please select incentive amount or percentage.",
                    "messageTitle": "Sales Amount Based Incentive",
                    "type": "0"
                });
                return false;
            }
        }
    }

    function removeAbIncentiveRow(id){
        var grid = $("#ab-jqgrid-incentive-slab-grid");
        var slabId = grid.jqGrid('getCell',id,'sabisId');
        if(slabId){
            sabDelSlabIds.push(slabId);
        }
        grid.jqGrid("delRowData", id);
    }

    function removeAbCustomersRow(id){
        var grid = $("#ab-jqgrid-eligible-customers-grid");
        grid.jqGrid("delRowData", id);
    }

    function removeAbUpdateCustomersRow(id){
        var grid = $("#ab-update-jqgrid-eligible-customers-grid");
        var slabId = grid.jqGrid('getCell',id,'sabicId');
        if(slabId){
            sabDelCustomerIds.push(slabId);
        }
        grid.jqGrid("delRowData", id);
    }

    function executeAbPrecondition(){
        if(!$("#gFormSetupIncentive").validationEngine('validate')){
            return false;
        }
        return true;
    }

    function executeAjaxIncentiveAmountBased(){
        if(!executeAbPrecondition()){
            return false;
        }

        var allData = {};

        allData["programName"] = $("#aBprogramName").val();
        allData["effectiveDateFrom"] = $("#aBeffectiveDateFrom").val();
        allData["effectiveDateTo"] = $("#aBeffectiveDateTo").val();

        var allCustomers = [];
        var allIncentive = $("#ab-jqgrid-incentive-slab-grid").jqGrid('getRowData');
        var selectedCustomerRows = $('#ab-jqgrid-eligible-customers-grid').jqGrid('getGridParam', 'selarrrow');
        var customerRowIds = filterListOfIds(selectedCustomerRows);

        var abIncentiveId = $('#abIncentiveId').val();
        var abIncentiveVersion = $('#abIncentiveVersion').val();

        if(allIncentive.length>0){
            for(var i = 0; i < allIncentive.length; i++){
                allData["allIncentive.incentive["+ i +"].id"] = allIncentive[i].sabisId;
                allData["allIncentive.incentive["+ i +"].achievementFrom"] = allIncentive[i].salesValueFrom;
                allData["allIncentive.incentive["+ i +"].achievementTo"] = allIncentive[i].salesValueTo;
                allData["allIncentive.incentive["+ i +"].incentiveAmount"] = allIncentive[i].incentiveAmount;
                allData["allIncentive.incentive["+ i +"].incentivePct"] = allIncentive[i].incentiveInPct;
            }
        }else{
            MessageRenderer.render({
                "messageBody": "Please insert at least one incentive slab into the list.",
                "messageTitle": "Sales Amount Based Incentive",
                "type": "0"
            });
            return false;
        }

        if(customerRowIds == '' && abIncentiveId == ''){
            MessageRenderer.render({
                "messageBody": "Please select at least one customer from the customer list.",
                "messageTitle": "Sales Amount Based Incentive",
                "type": "0"
            });
            return false;
        }else{
            for(key in customerRowIds){
                allCustomers.push($("#ab-jqgrid-eligible-customers-grid").getRowData(customerRowIds[key]));
            }

            for(var i = 0; i < allCustomers.length; i++){
                allData["customerList.customer["+ i +"].customerId"] = allCustomers[i].id;
            }
        }

        var url = '';

        if(abIncentiveId){
            for(var i=0; i<sabDelSlabIds.length; i++){
                allData['incentiveSlabDeleteList.delete['+ i +'].id'] = sabDelSlabIds[i];
            }

            for(var i=0; i<sabDelCustomerIds.length; i++){
                allData['incentiveCustomersDeleteList.delete['+ i +'].id'] = sabDelCustomerIds[i];
            }

            var abExistingCustomers = $("#ab-update-jqgrid-eligible-customers-grid").jqGrid('getDataIDs');
            if(customerRowIds == '' && abExistingCustomers == ''){
                MessageRenderer.render({
                    "messageBody": "Please select at least one customer from the customer list.",
                    "messageTitle": "Sales Amount Based Incentive",
                    "type": "0"
                });
                return false;
            }

            url = "${request.contextPath}/${params.controller}/updateSalesAmountBasedIncentive?id="+abIncentiveId+"&version="+abIncentiveVersion;
        }else{
            url = "${request.contextPath}/${params.controller}/createSalesAmountBasedIncentive";
        }

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: allData,
            url: url,
            success: function (data, textStatus) {
                if(data.type == 1){
                    clearAbAll();
                }
                MessageRenderer.render(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    clearAbAll();
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


    function clearAbAll(){
        $('#aBprogramName').val('');
        $('#aBeffectiveDateFrom').val('');
        $('#aBeffectiveDateTo').val('');

        $("#ab-jqgrid-incentive-slab-grid").clearGridData();
        $("#ab-jqgrid-eligible-territory-grid").trigger("reloadGrid");
        $("#ab-jqgrid-eligible-geo-grid").clearGridData();
        $("#ab-jqgrid-eligible-pt-grid").clearGridData();
        $("#ab-jqgrid-eligible-sc-grid").clearGridData();
        $("#ab-jqgrid-eligible-cc-grid").clearGridData();

        $("#ab-jqgrid-eligible-customers-grid").clearGridData();
        $("#ab-update-jqgrid-eligible-customers-grid").clearGridData();
    }
</script>